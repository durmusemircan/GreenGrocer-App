package application;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class ShoppingCartController {
    @FXML
    private Button backToShoppingButton;

    @FXML
    private TableView<CartItem> shoppingCartTable;

    @FXML
    private TableColumn<CartItem, String> product;

    @FXML
    private TableColumn<CartItem, Number> quantity;

    @FXML
    private TableColumn<CartItem, Number> price;
    
    @FXML
    private ComboBox<Integer> deliveryTimeComboBox;

    @FXML
    private Label totalPriceLabel;
    
    private User currentUser;

    private ShoppingCart shoppingCart;

    public ShoppingCartController() {
        shoppingCart = new ShoppingCart();
    }

    @FXML
    private void initialize() {
    
        product.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        price.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        loadCartItems();
        deliveryTimeComboBox.getItems().addAll(24, 48, 72);
    }

    private void loadCartItems() {
        shoppingCartTable.setItems(FXCollections.observableArrayList(shoppingCart.getItems()));
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = shoppingCart.getItems().stream()
                                  .mapToDouble(CartItem::getPrice)
                                  .sum();
        totalPriceLabel.setText(String.format("Total Price: %.2f", totalPrice));
    }

    @FXML
    void backToShopping(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/customer.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) backToShoppingButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Customer");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @FXML
    void cleanCart(ActionEvent event) {
        shoppingCart.clear();
        shoppingCartTable.getItems().clear();
        totalPriceLabel.setText("Total Price: 0");
    }

    @FXML
    void placeOrder(ActionEvent event) throws SQLException {
        String currentUsername = LoginController.getLoggedInUsername();
        currentUser = DatabaseAdapter.getUserDetails(currentUsername);

        Integer deliveryTimeInHours = deliveryTimeComboBox.getSelectionModel().getSelectedItem();
        LocalDateTime deliveryDateTime = LocalDateTime.now().plusHours(deliveryTimeInHours);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedOrderTime = LocalDateTime.now().format(formatter);
        String formattedDeliveryTime = deliveryDateTime.format(formatter);
        DatabaseAdapter.placeOrder(currentUser, shoppingCart.getItems(), formattedOrderTime, formattedDeliveryTime);
        for (CartItem item : shoppingCart.getItems()) {

            int quantityToDeduct = (int) Math.floor(item.getQuantity());
            DatabaseAdapter.updateStock(item.getProduct().getId(), quantityToDeduct);
        }
        shoppingCart.clear();
        shoppingCartTable.getItems().clear();
        totalPriceLabel.setText("Total Price: 0");
    }

    public void setShoppingCart(ShoppingCart cart) {
        this.shoppingCart = cart;
        shoppingCartTable.setItems(FXCollections.observableArrayList(cart.getItems()));
        updateTotalPrice();
    }
}