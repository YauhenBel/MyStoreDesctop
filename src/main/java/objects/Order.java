package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.text.Text;

//объект, хранящий данные о заказе
public class Order {

    private SimpleStringProperty id = new SimpleStringProperty();
    private Text nameProduct;
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty surname = new SimpleStringProperty();
    private SimpleStringProperty phone = new SimpleStringProperty();
    private Text adress;
    private SimpleStringProperty cost = new SimpleStringProperty();
    private SimpleStringProperty isClose = new SimpleStringProperty();

    public Order(){}

    public Order(String _id, Text _nameProduct,
                 String _name, String _surname, String _phone,
                 Text _adress, String _cost, String _isClose){
        this.id = new SimpleStringProperty(_id);
        this.nameProduct =  _nameProduct;
        this.name = new SimpleStringProperty(_name);
        this.surname = new SimpleStringProperty(_surname);
        this.phone = new SimpleStringProperty(_phone);
        this.adress = _adress;
        this.cost = new SimpleStringProperty(_cost);
        this.isClose = new SimpleStringProperty(_isClose);
    }

    public void setId(String id) {this.id.set(id);}

    public String getId() {return id.get();}

    public void setNameProduct(Text nameProduct) {this.nameProduct = nameProduct;}

    public Text getNameProduct() {return nameProduct;}

    public void setName(String name) {this.name.set(name);}

    public String getName() {return name.get();}

    public void setSurname(String surname) {this.surname.set(surname);}

    public String getSurname() {return surname.get();}

    public void setPhone(String phone) {this.phone.set(phone);}

    public String getPhone() {return phone.get();}

    public void setAdress(Text adress) {this.adress = adress;}

    public Text getAdress() {return adress;}

    public void setCost(String cost) {this.cost.set(cost);}

    public String getCost() {return cost.get();}

    public void setIsClose(String isClose) {this.isClose.set(isClose);}

    public String getIsClose() {return isClose.get();}

    public SimpleStringProperty idProperty() {return id;}

    public SimpleStringProperty nameProperty() {return name;}

    public SimpleStringProperty surnameProperty() {return surname;}

    public SimpleStringProperty phoneProperty() {return phone;}

    public SimpleStringProperty costProperty() {return cost;}

    public SimpleStringProperty isCloseProperty() {
        if(isClose.get().equals("0")){
            return new SimpleStringProperty("Выполняется");
        } else {
            return new SimpleStringProperty("Закрыт");
        }
    }
}

