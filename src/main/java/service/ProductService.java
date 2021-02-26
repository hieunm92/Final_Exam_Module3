package service;
import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ProductService implements IProductService {
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/productmanagerr",
                    "root",
                    "123456"
            );
        } catch (ClassNotFoundException e) {
            System.out.println("không có driver");
        } catch (SQLException throwables) {
            System.out.println("Không kết nối được");
        }
        System.out.println("ket noi thanh cong");
        return connection;
    }
    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from product");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int price = resultSet.getInt(3);
                int amount = resultSet.getInt(4);
                String color = resultSet.getString(5);
                String description = resultSet.getString(6);
                String category = resultSet.getString(7);
                Product product = new Product(id,name, price, amount, color, description, category);
                productList.add(product);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productList;
    }
    @Override
    public Product save(Product product) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into product(name,price,amount,color,description,category) value  (?,?,?,?,?,?)");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, product.getAmount());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setString(6, product.getCategory());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }
    @Override
    public Product edit(int id, Product product) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update product set name = ?, price= ?, amount= ?, color= ?,description=?, category=?where id=?;");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, product.getAmount());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setString(6, product.getCategory());
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }
    @Override
    public Product findById(int id) {
        Product product = null;
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from product where id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int amount = resultSet.getInt("amount");
                String color = resultSet.getString("color");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                product = new Product(id, name, price, amount, color, description, category);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }
    @Override
    public void delete(int id) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from product where id=?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    public List<Product> findByName(String name) {
        List<Product> productList = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from product where name like ?");
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name1 = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int amount = resultSet.getInt("amount");
                String color = resultSet.getString("color");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                productList.add(new Product(id, name1, price, amount, color, description, category));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}