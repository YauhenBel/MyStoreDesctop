package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import objects.Order;
import objects.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//класс, для работы со списком заказов

public class CollectionListOrders implements ListObjects {
//список заказов
    private ObservableList<Order> orderList =
            FXCollections.observableArrayList();
    //изменить статус заказа на "Завершено", в БД для этого проставляется "1" в соответсвующий столбик
    public void close(Order order){
        String id;
        id = order.getId();
        System.out.println("Закрываем заказ под номерм " + id);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/store?useUnicode=true&characterEncoding=utf-8";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);

            try {
                Statement stmt = con.createStatement();
                String sql_query = "UPDATE `store`.`orders` SET `isClose` =" + "'" + 1 + "'"
                        + " WHERE `orders`.`id` = " + id;
                System.out.println(sql_query);
                int rs = stmt.executeUpdate(sql_query);
                System.out.println(rs);
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        order.setIsClose("1");
    }

    @Override
    public Product add(Product product) {

        return product;
    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void delete(Product product) {

    }

    //загрузка закаазов
    @Override
    public void fillData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/store";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "SELECT `orders`.`id`, `products`.`name` AS `nameProduct`, " +
                        "`orders`.`name`, `orders`.`surname`, `orders`.`phone`, `orders`.`adress`, " +
                        "`products`.`cost`, `orders`.`isClose` FROM `orders` JOIN `products` " +
                        "ON `products`.`id` = `orders`.`id_product`";
                System.out.println(sql_query);
                ResultSet rs = stmt.executeQuery(sql_query);

                while (rs.next()) {
                    String str = "{\nid = " + rs.getString("id")
                            + ",\nnameProduct = " + rs.getString("nameProduct")
                            + ",\nname = " + rs.getString("name")
                            + ",\nsurname = " + rs.getString("surname")
                            + ",\nphone = " + rs.getString("phone")
                            + ",\nadress = " + rs.getString("adress")
                            + ",\ncost = " + rs.getString("cost")
                            + ",\nisClose = " + rs.getString("isClose")
                            + ";\n}";
                    System.out.println("info: " + str);
                    String id = rs.getString("id");
                    String nameProduct = rs.getString("nameProduct");
                    Text textNameProduct = new Text(nameProduct);
                    textNameProduct.setWrappingWidth(180);
                    String name =rs.getString("name");
                    String surname =rs.getString("surname");
                    String phone = rs.getString("phone");
                    String adress = rs.getString("adress");
                    Text textAdress = new Text(adress);
                    textAdress.setWrappingWidth(215);
                    String cost = rs.getString("cost") + "р.";
                    String isClose = rs.getString("isClose");

                    orderList.add(new Order(id, textNameProduct, name, surname,
                            phone, textAdress, cost, isClose));
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
    //вернуть список заказов
    public ObservableList<Order> getListOrder() {return orderList;}
}
