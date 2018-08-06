package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Sections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//класс для работы со список категорий продукции

public class CollectionSectionList {
    //список категорий
    private ObservableList<Sections> sectionList =
            FXCollections.observableArrayList();
    //загрузка категорий
    public void fillData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/store";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "SELECT * FROM `sections`";
                System.out.println(sql_query);
                ResultSet rs = stmt.executeQuery(sql_query);

                while (rs.next()) {
                    String str = "{\nid = " + rs.getString("id")
                            + ",\nname = " + rs.getString("name")
                            + ";\n}";
                    System.out.println("info: " + str);
                    String Id = rs.getString("id");
                    String Name =rs.getString("name");
                    sectionList.add(new Sections(Id, Name));
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //вернем список категорий
    public ObservableList<Sections> getSectionList() {
        return sectionList;
    }
}
