package objects;

import javafx.beans.property.SimpleStringProperty;

//объект, хранящий категорию продукта

public class Sections {
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty nameSection = new SimpleStringProperty();

    public Sections(){}

    public Sections(String _id, String _nameSection){
        this.id = new SimpleStringProperty(_id);
        this.nameSection = new SimpleStringProperty(_nameSection);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getId() {
        return id.get();
    }

    public void setNameSection(String nameSection) {
        this.nameSection.set(nameSection);
    }

    public String getNameSection() {
        return nameSection.get();
    }

    public SimpleStringProperty nameSectionProperty() {
        return nameSection;
    }

    @Override
    public String toString() {
        return  nameSection.get();
    }
}
