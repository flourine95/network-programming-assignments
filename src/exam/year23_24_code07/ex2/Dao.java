package exam.year23_24_code07.ex2;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dao {
    private Connection connection;

    public Dao() throws ClassNotFoundException, SQLException {
        String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
        String dbFile = "src\\exam\\year23_24_code07\\ex2\\db.accdb";
        String url = "jdbc:ucanaccess://" + dbFile;
        Class.forName(driver);
        this.connection = DriverManager.getConnection(url);
    }

    // Lấy tất cả sản phẩm
    public List<Product> all() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (PreparedStatement pre = connection.prepareStatement(sql);
             ResultSet rs = pre.executeQuery()) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("count")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all products: " + e.getMessage());
        }
        return products;
    }

    // Thêm sản phẩm
    public boolean add(Product product) {
        String sql = "INSERT INTO products(id, name, price, count) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, product.getId());
            pre.setString(2, product.getName());
            pre.setDouble(3, product.getPrice());
            pre.setInt(4, product.getCount());
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
            return false;
        }
    }

    // Tìm sản phẩm theo tên
    public List<Product> find(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setString(1, "%" + name + "%");
            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("count")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding product by name: " + e.getMessage());
        }
        return products;
    }

    // Tìm sản phẩm theo ID
    public Product get(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("count")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching product by ID: " + e.getMessage());
        }
        return null;
    }

    // Mua sản phẩm
    public Map<Integer, String> buy(List<Integer> productIds) {
        Map<Integer, String> result = new HashMap<>();

        for (Integer productId : productIds) {
            try {
                Product product = get(productId);
                if (product == null) {
                    result.put(productId, "NOT_FOUND");
                    continue;
                }

                if (product.getCount() > 0) {
                    String updateSql = "UPDATE products SET count = count - 1 WHERE id = ?";
                    try (PreparedStatement updatePre = connection.prepareStatement(updateSql)) {
                        updatePre.setInt(1, productId);
                        updatePre.executeUpdate();
                        result.put(productId, "SUCCESS");
                    }
                } else {
                    result.put(productId, "OUT_OF_STOCK");
                }
            } catch (SQLException e) {
                System.err.println("Error processing purchase for product ID " + productId + ": " + e.getMessage());
                result.put(productId, "UNKNOWN_ERROR");
            }
        }
        return result;
    }

    // Cập nhật thông tin sản phẩm
    public boolean update(int id, Product newProduct) {
        String sql = "UPDATE products SET  count = ? WHERE id = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, newProduct.getCount());
            pre.setInt(2, id);

            int rowsUpdated = pre.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error updating product ID " + id + ": " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, id);
            return pre.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting product ID " + id + ": " + e.getMessage());
            return false;
        }
    }
}
