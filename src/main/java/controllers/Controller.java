package controllers;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

//Обработка данных с формы авторизации
//приязан к sample.fxml

public class Controller implements Initializable {


    public Button btnInput;
    public CustomTextField tfLogin;
    public CustomPasswordField tfPassword;
    public Label labelError;
    private static String FXMLSection = "../layouts/worker_window.fxml";
    private Stage primaryStage;
    private Parent root;
    String name = "", surname = "", access = "";
    //метод начинающий свою работу автоматически
    public void initialize(URL location, ResourceBundle resources) {
        //добавляем в поля ввода кнопки для очистики
        setupClearButtonField(tfLogin);
        setupClearButtonField(tfPassword);
    }

    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
                    TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupClearButtonField(CustomPasswordField customPasswordField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
                    TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customPasswordField, customPasswordField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //обработчик нажатия на кнопку авторизации
    public void logIn(ActionEvent actionEvent) throws IOException {
        labelError.setAlignment(Pos.CENTER);

        if (!tfLogin.getText().isEmpty() && !tfPassword.getText().isEmpty()) {

            if (connDB()) {
                labelError.setText("Работает!");
                System.out.println("name = " + name);
                System.out.println("surname = " + surname);
                System.out.println("access = " + access);
                createGui();
                close_window(actionEvent);
            } else {
                labelError.setText("Неправильный логин или пароль!");
            }

        } else if (tfLogin.getText().isEmpty() && tfPassword.getText().isEmpty()) {
            labelError.setText("Введите логин и пароль!");
        } else if (!tfLogin.getText().isEmpty() && tfPassword.getText().isEmpty()) {
            labelError.setText("Введите пароль!");
        } else {
            labelError.setText("Введите логин!");
        }
    }
    //медот для закрытия текущего окна
    private void close_window(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    //метод для оздания нового окна и перехода на него
    private void createGui() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXMLSection));
        Parent root = fxmlLoader.load();

        WorkerWindow workerWindow = fxmlLoader.getController();
        workerWindow.setAboutUaser(name, surname, access);

        primaryStage = new Stage();
        workerWindow.setMainStage(primaryStage);
        primaryStage.setTitle("Товары и Заказы");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxWidth(primaryStage.getHeight());
        primaryStage.setMaxHeight(primaryStage.getWidth());
    }
    //подключение к БД
    private Boolean connDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/store";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();

                String sql_query = "SELECT `name`, `surname`, `access` FROM `admins` WHERE `login`='" + tfLogin.getText()
                        + "' && `password`='" + tfPassword.getText() + "'";
                System.out.println(sql_query);
                ResultSet rs = stmt.executeQuery(sql_query);

                if (rs.first()){
                    name = rs.getString("name");
                    surname = rs.getString("surname");
                    access = rs.getString("access");
                }

                if (!rs.first()) {
                    return false;
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}

