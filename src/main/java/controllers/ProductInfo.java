package controllers;

import interfaces.CollectionListProducts;
import interfaces.CollectionSectionList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Product;
import objects.Sections;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

//класс редактирования записи или создания новой записи
//приязан к productInfo.fxml

public class ProductInfo {
    //фото продукта
    public ImageView imageView;
    //имя и описание продукта
    public TextArea taName, taDescription;
    //стоимость продукта
    public TextField tfCost;
    //наличие продукта
    public CheckBox checkBoxIsAvailable;
    //список категорий продуктов
    public ChoiceBox choiceBoxSection;
    //кнопки загрузки изображения, сохранения и закрытия окна
    public Button btnNewImage, btnSave, btnClose;
    //поле текущего статуса сохранения информации
    public Label labelStatusSave;

    private Product product;
    //объект, хранящий список категорий продукции
    private CollectionSectionList collectionSectionList = new CollectionSectionList();
    //объект, хранящий список продукции
    private CollectionListProducts collectionListProducts = new CollectionListProducts();
    //список категорий
    private ObservableList<Sections> sectionList =
            FXCollections.observableArrayList();

    private Stage mainStage;
    //выбор файла для загрузки
    final FileChooser fileChooser = new FileChooser();
    //адрес сервлета, с помощью которого загружается файл на сервер
    static final String UPLOAD_URL = "http://localhost:8080/upload";
    static final int BUFFER_SIZE = 4096;
    Image image;
    //адрес, откуда загружается изображение
    static final String URL_TO_IMAGE = "http://127.0.0.1/my%20portable%20files/image/";
    File files;
    Integer num;
    //инициализирует объект "product" данными, пришедшими с главного окна
    public void setProduct(Product product, Integer _num){
        //загружаем данные для choicebox
        collectionSectionList.fillData();
        sectionList = collectionSectionList.getSectionList();
        choiceBoxSection.setItems(sectionList);

        labelStatusSave.setText("");
        this.num = _num;
        //если объект пришел пустой
        if (product == null){
            product = new Product();
            this.product = product;
            image = new Image(URL_TO_IMAGE + "image_icon.jpg");
            imageView.setImage(image);
            taName.clear();
            taDescription.clear();
            tfCost.clear();
            checkBoxIsAvailable.setSelected(false);
            files = null;
            choiceBoxSection.getSelectionModel().select(0);
            return;
        }

        this.product = product;
        image = new Image(URL_TO_IMAGE + "image_icon.jpg");
        image = product.getImage().getImage();
        imageView.setImage(image);
        taName.setText(product.getName().getText());
        taDescription.setText(product.getDescription().getText());
        tfCost.setText(product.getCost());
        sectionList = collectionSectionList.getSectionList();

        //заполняем choiceBox данными
        choiceBoxSection.getSelectionModel().select(Integer.parseInt(product.getIdSection())-1);
        if (product.getAvailable().equals("0")){
            checkBoxIsAvailable.setSelected(false);
        }else {
            checkBoxIsAvailable.setSelected(true);
        }

    }
    //обработчик нажатий на кнопки - сохранить, закрыть, загузить изображение
    public void ActionBtnPersInfo(ActionEvent actionEvent) throws IOException, InterruptedException {
        Object object = actionEvent.getSource();
        if (!(object instanceof Button)){
            return;
        }
        Button button = (Button) object;
        switch (button.getId()) {
            case "btnSave":
                labelStatusSave.setText("Сохранение...");
                UpdateProduct();
                if (num == 1){
                    collectionListProducts.update(product);
                }
                if (num == 2){
                    product = collectionListProducts.add(product);
                }

                labelStatusSave.setText("Сохранено");
                break;
            case "btnClose":
                sectionList.clear();
                Node source = (Node) actionEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                break;
            case "btnNewImage":
                files = fileChooser.showOpenDialog(mainStage);
                sendFile(files);
                image = new Image(URL_TO_IMAGE + files.getName());
                imageView.setImage(image);
                break;
        }
    }
    //отправлем файл на сервер
    private void sendFile(File file) throws IOException, InterruptedException {
        if (file == null) {
            return;
        }
            UploadFile(file.getAbsolutePath());
        }
    //отправка файла на сервер через сервлет
    private void UploadFile(String filePuth) throws IOException, InterruptedException {
        String filePath = filePuth;
        File uploadFile = new File(filePath);
        System.out.println("File to upload: " + filePath);
        // creates a HTTP connection
        URL url = new URL(UPLOAD_URL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("POST");
        // sets file name as a HTTP header
        httpConn.setRequestProperty("fileName", uploadFile.getName());
        System.out.println("httpConn1: " + httpConn);
        // opens output stream of the HTTP connection for writing data
        OutputStream outputStream = httpConn.getOutputStream();
        // Opens input stream of the file for reading data
        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
        System.out.println("Start writing data...");
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        System.out.println("Data was written.");
        outputStream.close();
        inputStream.close();
        // always check HTTP response code from server
        System.out.println("httpConn: " + httpConn);
        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // reads server's response
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            String response = reader.readLine();
            System.out.println("Server's response: " + response);
        } else {
            System.out.println("Server returned non-OK code: " + responseCode);
        }


    }
    //заполнение объекта новой информацией
    private void UpdateProduct(){

        if (checkBoxIsAvailable.isSelected()){
            product.setAvailable("1");
        }else {
            product.setAvailable("0");
        }

        ImageView imageView1 = new ImageView(imageView.getImage());
        imageView1.setFitWidth(80);
        imageView1.setPreserveRatio(true);
        product.setImage(imageView1);
        if(tfCost.getText().isEmpty()){
            product.setCost("0");
        }else {
            product.setCost(tfCost.getText());
        }
        Text text = null;
        if (taDescription.getText().isEmpty()){
            text = new Text("none");
        }else{
            text = new Text(taDescription.getText());
        }
        text.setWrappingWidth(440);
        product.setDescription(text);
        Text textName = null;
        if (taName.getText().isEmpty()){
            textName = new Text("none");
        }else{
            textName = new Text(taName.getText());
        }
        textName.setWrappingWidth(200);
        product.setName(textName);
        if (files != null) {
            product.setPhoto(files.getName());
        }

        Sections sections = (Sections) choiceBoxSection.getSelectionModel().getSelectedItem();
        product.setIdSection(sections.getId());

    }
    //возвразаем объект
    public Product getProduct(){return product;}
}
