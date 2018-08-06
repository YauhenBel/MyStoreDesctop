package controllers;

import interfaces.CollectionListOrders;
import interfaces.CollectionListProducts;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Order;
import objects.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
//класс работы с гланым окном программы
//приязан к worker_window.fxml
//главное окно программы имеет два варианта конфигурации - для менеджера и для продавца
//для менеджера отображаетс TabPane, который содержит две вкладки - товары и заказы
//ждя продавца доступна лишь информация по товарам, работать с заказами он не может

public class WorkerWindow implements Initializable {

    private static String FXMLSample = "../layouts/sample.fxml";

    //поле для ситемных сообщений
    public Label systemMess;


    Stage primaryStage;

    //поле, отображающее имя пользователя (не логин)
    public Label labelName;

    //контейнер содержащий в себе кнопки для работы с таблицей продуктов
    public Pane paneBtnForListProducts;

    //кнопки для работы со списком товаров
    public Button btnChangeProduct;
    public Button btnCreateProduct;
    public Button btnDelProduct;

    //контейнер содержащий в себе кнопки для работы с таблицей заказов
    public Pane paneBtnForListOrders;

    //кнопка для присвоения заказу статуса "Завершено"
    public Button btnOrderClose;

    //кнопка смена пользователя
    public Button btnChangeUser;

    //кнопка выхода из программы
    public Button btnCloseProgram;

    //поля для отображения количества записей в таблицах
    public Label labelCount;
    public Label labelCountOrders;

    //контейнер для отображения вкладок и таблиц для менеджера
    public Pane paneForManager;
    //таблицы со столбцами для отображения информации по продукции
    public TableView mTvProduct;
    public TableColumn<Product, Image> mImage;
    public TableColumn<Product, Text> mColumnName;
    public TableColumn<Product, Text> description;
    public TableColumn<Product, String> mColumnAvailable;
    public TableColumn<Product, String> mColumnCost;

    //таблицы со столбцами для отображения информации по заказам
    public TableView mTvOrders;
    public TableColumn<Product, String> mColumnIdOrder;
    public TableColumn<Product, Text> mColumnNamePrOrder;
    public TableColumn<Product, String> mColumnNameClientOrder;
    public TableColumn<Product, String> mColumnSurnameOrder;
    public TableColumn<Product, String> mColumnPhoneOrder;
    public TableColumn<Product, Text> mColumnAdressOrder;
    public TableColumn<Product, String> mColumnCostOrder;
    public TableColumn<Product, String> mColumnStatus;

    //контейнер для отображения вкладок и таблиц для продавца
    public Pane paneForSeller;
    public TableView sTvProduct;
    public TableColumn<Product, Image> sImage;
    public TableColumn<Product, Text> sColumnName;
    public TableColumn<Product, Text> sDescription;
    public TableColumn<Product, String> sColumnAvailable;
    public TableColumn<Product, String> sColumnCost;


    //переменные для хранения информации о пользователе, которые перейдут с предыдущего окна
    String name = "", surname = "", access = "";

    //объект, хранящий список продуктов
    CollectionListProducts collectionListProducts = new CollectionListProducts();
    //объект хранящий писок заказов
    CollectionListOrders collectionListOrders = new CollectionListOrders();

    //переменные для создания окна редактирования текущих и созданияновых записей о продуктах
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private static String FXMLSection = "../layouts/productInfo.fxml";
    private Parent fxmlPersonalInfo;
    private Stage mainStage;

    //объект для отправки данных на создание или редактирование продуктов
    private ProductInfo productInfo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initLoader();
        systemMess.setText("");


    }
    //получаем данные с предыдущего окна из них инициализируем текущее окно
    public void setAboutUaser(String _name, String _surname, String _access){
        this.name = _name;
        this.surname = _surname;
        this.access =_access;
        labelName.setText(name + " " + surname);
        if (access.equals("1")){
            paneForManager.setVisible(true);
            paneForSeller.setVisible(false);
            paneBtnForListProducts.setVisible(true);
            paneBtnForListOrders.setVisible(false);

            labelCount.setVisible(true);
            labelCountOrders.setVisible(false);

            mImage.setCellValueFactory(new PropertyValueFactory<Product, Image>("image"));
            mColumnName.setCellValueFactory(new PropertyValueFactory<Product, Text>("name"));
            description.setCellValueFactory(new PropertyValueFactory<Product, Text>("description"));
            mColumnAvailable.setCellValueFactory(new PropertyValueFactory<Product, String>("available"));
            mColumnCost.setCellValueFactory(new PropertyValueFactory<Product, String>("cost"));
            mTvProduct.setEditable(true);

            mColumnIdOrder.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
            mColumnNamePrOrder.setCellValueFactory(new PropertyValueFactory<Product, Text>("nameProduct"));
            mColumnNameClientOrder.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            mColumnSurnameOrder.setCellValueFactory(new PropertyValueFactory<Product, String>("surname"));
            mColumnPhoneOrder.setCellValueFactory(new PropertyValueFactory<Product, String>("phone"));
            mColumnAdressOrder.setCellValueFactory(new PropertyValueFactory<Product, Text>("adress"));
            mColumnCostOrder.setCellValueFactory(new PropertyValueFactory<Product, String>("cost"));
            mColumnStatus.setCellValueFactory(new PropertyValueFactory<Product, String>("isClose"));
            mTvOrders.setEditable(true);


        }

        if (access.equals("2")){
            paneForSeller.setVisible(true);
            paneForManager.setVisible(false);
            paneBtnForListProducts.setVisible(true);
            paneBtnForListOrders.setVisible(false);
            sImage.setCellValueFactory(new PropertyValueFactory<Product, Image>("image"));
            sColumnName.setCellValueFactory(new PropertyValueFactory<Product, Text>("name"));
            sDescription.setCellValueFactory(new PropertyValueFactory<Product, Text>("description"));
            sColumnAvailable.setCellValueFactory(new PropertyValueFactory<Product, String>("available"));
            sColumnCost.setCellValueFactory(new PropertyValueFactory<Product, String>("cost"));
            sTvProduct.setEditable(true);


        }
        fillData();
        initListeners();
    }
    //загрузка данных из БД
    public void fillData(){
        collectionListProducts.fillData();
        collectionListOrders.fillData();
        if (access.equals("1")){
            mTvProduct.setItems(collectionListProducts.getListProduct());
            mTvOrders.setItems(collectionListOrders.getListOrder());
        }
        if (access.equals("2")){
            sTvProduct.setItems(collectionListProducts.getListProduct());
        }
    }
    //назначаем слушатели
    public void initListeners(){
        collectionListProducts.getListProduct().addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(Change<? extends Product> c) {
               updateCountLabel();
            }
        });
        collectionListOrders.getListOrder().addListener(new ListChangeListener<Order>() {
            @Override
            public void onChanged(Change<? extends Order> c) {
                updateCountLabel();
            }
        });

        if (access.equals("1")) {
            makeMouseClickedOnTable(mTvProduct);
        }
        if (access.equals("2")){
            makeMouseClickedOnTable(sTvProduct);
        }

    }
    //делаем таблицу кликабельной, двойной клик по записи открывает ее для редактирования
    private void makeMouseClickedOnTable(final TableView tableView){
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    Product product = (Product) tableView.getSelectionModel().getSelectedItem();
                    System.out.println("btnChangeProduct");
                    if (!productIsSelected(product)){
                        return;
                    }
                    productInfo.setProduct(product, 1);
                    createGui();
                    tableView.refresh();

                }
            }
        });
    }
    //метод возвращающий количество записей в таблице в соответствующее поле
    public void updateCountLabel(){
        labelCount.setText("Количество записей: "
        + collectionListProducts.getListProduct().size());
        labelCountOrders.setText("Количество записей: "
                + collectionListOrders.getListOrder().size());
    }
    //меняем отображаемые кнопки в зависимости от выбранной вкладки
    public void doVisiblebtnForProducts(Event event) {
        paneBtnForListProducts.setVisible(true);
        paneBtnForListOrders.setVisible(false);

        labelCount.setVisible(true);
        labelCountOrders.setVisible(false);
    }

    public void doVisiblebtnForOrder(Event event) {
        paneBtnForListProducts.setVisible(false);
        paneBtnForListOrders.setVisible(true);

        labelCount.setVisible(false);
        labelCountOrders.setVisible(true);

    }
    //закрываем окно
    @FXML
    private void close_window(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    //смена пользователя
    public void actionChangeUser(ActionEvent actionEvent) throws IOException {
        close_window(actionEvent);
        primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(FXMLSample));
        primaryStage.setTitle("Главная");
        Scene scene = new Scene(root, 250, 156);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxWidth(250);
        primaryStage.setMaxHeight(156);
    }
    //обработка нажатий кнопок редактирования записей
    public void ActionButtonPressed(ActionEvent actionEvent) {
        Object object = actionEvent.getSource();
        TableView newTable = null;
        if (access.equals("1")){
            newTable = mTvProduct;
        }
        if (access.equals("2")){
            newTable = sTvProduct;
        }
        Product product = (Product) newTable.getSelectionModel().getSelectedItem();
        Order order = (Order) mTvOrders.getSelectionModel().getSelectedItem();

        if (!(object instanceof Button)) {
            return;
        }

        Button button = (Button) object;

        switch (button.getId()){
            case "btnChangeProduct":

                System.out.println("btnChangeProduct");
                if (!productIsSelected(product)){
                    return;
                }
                productInfo.setProduct(product, 1);
                createGui();
                newTable.refresh();

                break;
            case "btnCreateProduct":
                productInfo.setProduct(null, 2);
                createGui();
                collectionListProducts.addLastSelect(productInfo.getProduct());
                System.out.println("btnCreateProduct");
                break;
            case "btnDelProduct":
                collectionListProducts.delete(product);
                System.out.println("btnDelProduct");
                break;
            case "btnOrderClose":
                collectionListOrders.close(order);
                mTvOrders.refresh();
                System.out.println("btnOrderClose");
                break;
        }

    }
    //проверка - выбран ли объект при нажатии на кнопку редактирования записи
    private boolean productIsSelected(Product product) {
        if (product == null) {
            systemMess.setText("Не выбрана запись!");
            System.out.println("Не выбран объект!");
            return false;
        }
        systemMess.setText("");
        System.out.println("Выбран объект!");
        return true;
    }
    //запуск окна создания или редактирования записи
    private void createGui() {
        if (primaryStage == null) {
            primaryStage = new Stage();
            primaryStage.setTitle("Персональная информация");
            primaryStage.setScene(new Scene(fxmlPersonalInfo));
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.initOwner(mainStage);
            primaryStage.setMaxWidth(600);
            primaryStage.setMaxHeight(500);
            primaryStage.setMinHeight(primaryStage.getHeight());
            primaryStage.setMinWidth(primaryStage.getWidth());
        }
        primaryStage.showAndWait();
    }

    private void initLoader(){
        fxmlLoader.setLocation(getClass().getResource(FXMLSection));
        try {
            fxmlPersonalInfo = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        productInfo = fxmlLoader.getController();
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
