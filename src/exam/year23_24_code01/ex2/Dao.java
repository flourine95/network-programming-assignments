package exam.year23_24_code01.ex2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao {
    private Connection connection;

    public Dao() throws ClassNotFoundException, SQLException {
        String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
        String dbFile = "src\\exam\\year23_24_code01\\ex2\\db.accdb";
        String url = "jdbc:ucanaccess://" + dbFile;
        Class.forName(driver);
        this.connection = DriverManager.getConnection(url);
    }

    public String add(String param) {
        return "add";
    }

    public String delete(String param) {
        return "delete";
    }

    public String range(String param) {
        return "range";
    }

    public String buy(String param) {
        return "buy";
    }

    public Response<List<Product>> all() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql ="SELECT * FROM products";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Product product = new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("count")
            );
            products.add(product);
        }
        return new Response<>(Response.Status.OK, products, "All products fetched");
    }

    public Response<Product> get(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("count")
                    );
                    return new Response<>(Response.Status.OK, product, "Product found: " + product);
                } else {
                    return new Response<>(Response.Status.ERROR, "Product not found for ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching product by ID: " + e.getMessage());
            return new Response<>(Response.Status.ERROR, "Database error: " + e.getMessage());
        }
    }

}
