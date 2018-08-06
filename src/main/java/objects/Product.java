package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

//объект, хранящий данные о продукте

public class Product {
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty cost = new SimpleStringProperty();
    private SimpleStringProperty available = new SimpleStringProperty();
    private SimpleStringProperty idSection = new SimpleStringProperty();
    private ImageView image;
    private Text name, description;
    private SimpleStringProperty photo = new SimpleStringProperty();

    public Product(){}

    public Product(String _id, Text _description,
                   String _cost, String _available, String _idSection, ImageView _image, Text _name, String _photo){
        this.id = new SimpleStringProperty(_id);
        this.cost = new SimpleStringProperty(_cost);
        this.available = new SimpleStringProperty(_available);
        this.idSection = new SimpleStringProperty(_idSection);
        this.image = _image;
        this.image.setPreserveRatio(true);
        this.image.setFitWidth(80);
        this.name = _name;
        this.name.setWrappingWidth(200);
        this.description = _description;
        this.description.setWrappingWidth(440);
        this.photo = new SimpleStringProperty(_photo);
    }

    public void setId(String id) {this.id.set(id);}

    public String getId() {return id.get();}

    public void setCost(String cost) {this.cost.set(cost);}

    public String getCost() {return cost.get();}

    public void setAvailable(String available) {this.available.set(available);}

    public String getAvailable() {return available.get();}

    public void setIdSection(String idSection) {this.idSection.set(idSection);}

    public String getIdSection() {return idSection.get();}

    public SimpleStringProperty costProperty() {
        return cost;
    }

    public SimpleStringProperty availableProperty() {
        if (available.get().equals("0")){
            return new SimpleStringProperty("Нет в наличии");
        } else {
            return new SimpleStringProperty("Есть в наличии");
        }
    }

    public void setImage(ImageView value) {
        image = value;
    }

    public ImageView getImage() {
        return image;
    }

    public void setName(Text label) {
        this.name = label;
    }

    public Text getName() {
        return name;
    }


    public void setDescription(Text description) {
        this.description = description;
    }

    public Text getDescription() {
        return description;
    }

    public void setPhoto(String photo) {this.photo.set(photo);}

    public String getPhoto() {return photo.get();}
}
