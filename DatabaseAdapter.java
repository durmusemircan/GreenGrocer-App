package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseAdapter {

    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/greengrocerapp";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "Emir43115807.";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    
    public static boolean validateUser(String username, String password) {
        String sql = "SELECT 1 FROM UserInfo WHERE username = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addNewUser(String username, String password, String firstName, String lastName, String email, String address) {
        String sql = "INSERT INTO UserInfo (username, password, firstName, lastName, email, address, role) VALUES (?, ?, ?, ?, ?, ?, 'customer')";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, email);
            pstmt.setString(6, address);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean usernameoremailCheck(String username, String email) {
        String sql = "SELECT COUNT(*) FROM UserInfo WHERE username = ? OR email = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, username);
            pstmt.setString(2, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    public static String userRole(String username) {
        String sql = "SELECT role FROM UserInfo WHERE username = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    
    public static boolean addProduct(String type, String name, double price, double stock, double threshold, String imageLocation) {
        String sql = "INSERT INTO ProductInfo (type, name, price, stock, imagelocation, threshold) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, type);
            pstmt.setString(2, name);
            pstmt.setDouble(3, price);
            pstmt.setDouble(4, stock);
            pstmt.setString(5, imageLocation);
            pstmt.setDouble(6, threshold);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean addCarrier(String username, String email, String password) {
        String sql = "INSERT INTO userInfo (username, email, password, role) VALUES (?, ?, ?, 'carrier')";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password); 
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getCarriersWithRole(String role) {
        List<String> carrierNames = new ArrayList<>();
        String sql = "SELECT username FROM UserInfo WHERE role = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, role);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    carrierNames.add(rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carrierNames;
    }
    
    public static List<String> getProducts() {
        List<String> productNames = new ArrayList<>();
        String sql = "SELECT name FROM productInfo"; 
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                productNames.add(rs.getString("name")); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productNames;
    }
    
    public static boolean deleteCarrier(String username) {
        String sql = "DELETE FROM UserInfo WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean deleteProduct (String name) {
        String sql = "DELETE FROM productInfo WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static User getUserDetails(String username) {
        User user = null;
        String sql = "SELECT * FROM UserInfo WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String username1 = rs.getString("username");
                    String password = rs.getString("password");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String address = rs.getString("address");
                    String email = rs.getString("email");

                    user = new User(id, username1, password, firstName, lastName, address, email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public static boolean updateUser(int id, String username, String password, String firstName, String lastName, String address, String email) {
        String sql = "UPDATE UserInfo SET username = ?, password = ?, firstName = ?, lastName = ?, address = ?, email = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, address);
            pstmt.setString(6, email);
            pstmt.setInt(7, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean deleteUser(int id) {
        String sql = "DELETE FROM UserInfo WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static List<Product> getProductsByType(String type) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM ProductInfo WHERE type = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String productType = rs.getString("type");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                String imageLocation = rs.getString("imagelocation");

                products.add(new Product(id, name, productType, price, stock, imageLocation));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    
    
    public static boolean placeOrder(User user, List<CartItem> cartItems, String formattedOrderTime, String formattedDeliveryTime) {
        String products = convertCartItemsToString(cartItems);
        double totalCost = calculateTotalCost(cartItems);

        String sql = "INSERT INTO orderInfo (orderTime, deliveryTime, products, user, userID, userAddress, isDelivered, totalCost) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, formattedOrderTime.formatted(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pstmt.setString(2, formattedDeliveryTime.formatted(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pstmt.setString(3, products);
            pstmt.setString(4, user.getUsername());
            pstmt.setInt(5, user.getId());
            pstmt.setString(6, user.getAddress());
            pstmt.setBoolean(7, false); // isDelivered başlangıçta false olacak
            pstmt.setDouble(8, totalCost);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String convertCartItemsToString(List<CartItem> cartItems) {
        return cartItems.stream()
                        .map(item -> item.getProduct().getName() + " x " + item.getQuantity())
                        .collect(Collectors.joining(", "));
    }

    private static double calculateTotalCost(List<CartItem> cartItems) {
        return cartItems.stream()
                        .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                        .sum();
    }
    
    public static void updateStock(int productId, int quantityToDeduct) throws SQLException {
        String sql = "UPDATE productinfo SET stock = GREATEST(0, stock - ?) WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, quantityToDeduct);
            pstmt.setInt(2, productId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Stok güncellenemedi, ürün bulunamadı veya stok yetersiz.");
            }
        }
    }
    
    public static void checkThreshold(int productId) throws SQLException {
        String sqlCheckStock = "SELECT stock, threshold, price FROM productinfo WHERE id = ?";
        String sqlUpdatePrice = "UPDATE productinfo SET price = price * 2 WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmtCheckStock = conn.prepareStatement(sqlCheckStock);
             PreparedStatement pstmtUpdatePrice = conn.prepareStatement(sqlUpdatePrice)) {

            pstmtCheckStock.setInt(1, productId);
            ResultSet rs = pstmtCheckStock.executeQuery();
            if (rs.next()) {
                int stock = rs.getInt("stock");
                int threshold = rs.getInt("threshold");
                if (stock <= threshold) {
                    pstmtUpdatePrice.setInt(1, productId);
                    int affectedRows = pstmtUpdatePrice.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("Fiyat güncellenemedi, ürün bulunamadı.");
                    }
                }
            }
        }
    }
    public static void OutOfStockDeletion() throws SQLException {
        String sql = "DELETE FROM productinfo WHERE stock = 0";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.executeUpdate();

        }
    }
    
    public static List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orderInfo";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("id"),
                    rs.getString("orderTime"),
                    rs.getString("deliveryTime"),
                    rs.getString("products"),
                    rs.getString("user"),
                    rs.getInt("userID"),
                    rs.getString("userAddress"),
                    rs.getString("carrier"),
                    rs.getBoolean("isDelivered"),
                    rs.getDouble("totalCost")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            throw e;
        }
        return orders;
    }
    
    public static List<Order> getundeliveredOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orderInfo WHERE isDelivered = 0";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	orders.add(new Order(
                        rs.getInt("id"),
                        rs.getString("orderTime"),
                        rs.getString("deliveryTime"),
                        rs.getString("products"),
                        rs.getString("user"),
                        rs.getInt("userID"),
                        rs.getString("userAddress"),
                        rs.getString("carrier"),
                        rs.getBoolean("isDelivered"),
                        rs.getDouble("totalCost"))
                );
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
    public static List<Order> getdeliveredOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orderInfo WHERE isDelivered = 1";

        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	orders.add(new Order(
                        rs.getInt("id"),
                        rs.getString("orderTime"),
                        rs.getString("deliveryTime"),
                        rs.getString("products"),
                        rs.getString("user"),
                        rs.getInt("userID"),
                        rs.getString("userAddress"),
                        rs.getString("carrier"),
                        rs.getBoolean("isDelivered"),
                        rs.getDouble("totalCost"))
                );
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
    
    public static void markAsDelivered(int orderId) {
        String sql = "UPDATE orderInfo SET isDelivered = 1 WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<Order> getOrdersByUser(String User) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orderInfo WHERE user = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, User);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                        rs.getInt("id"),
                        rs.getString("orderTime"),
                        rs.getString("deliveryTime"),
                        rs.getString("products"),
                        rs.getString("user"),
                        rs.getInt("userID"),
                        rs.getString("userAddress"),
                        rs.getString("carrier"),
                        rs.getBoolean("isDelivered"),
                        rs.getDouble("totalCost")
                    );
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

}