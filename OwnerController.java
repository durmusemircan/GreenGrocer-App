package application;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OwnerController {
    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Integer> idColumn;

    @FXML
    private TableColumn<Order, String> orderTimeColumn;

    @FXML
    private TableColumn<Order, String> deliveryTimeColumn;

    @FXML
    private TableColumn<Order, String> productsColumn;

    @FXML
    private TableColumn<Order, Integer> userIdColumn;

    @FXML
    private TableColumn<Order, String> carrierColumn;

    @FXML
    private TableColumn<Order, Boolean> isDeliveredColumn;

    @FXML
    private TableColumn<Order, Double> costColumn;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button LoadButton;

    @FXML
    private Button imageButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField stockField;

    @FXML
    private TextField thresholdField;

    @FXML
    private TextField typeField;
    
    @FXML
    private ImageView loadedImage;
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private TextField passwordField;
    
    @FXML
    private TextField eMailField;
    
    @FXML
    private Button removeButton;
    
    @FXML
    private Button addButton;
    
    @FXML
    private ListView<String> carrierList;
    
    @FXML
    private ListView<String> productList;
    
    
    private String imageLocation;
    
    @FXML
    public void initialize() throws SQLException {
    	idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderTimeColumn.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        deliveryTimeColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryTime"));
        productsColumn.setCellValueFactory(new PropertyValueFactory<>("products"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        carrierColumn.setCellValueFactory(new PropertyValueFactory<>("carrier"));
        isDeliveredColumn.setCellValueFactory(new PropertyValueFactory<>("isDelivered"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        loadOrders();
        loadCarriers();
        loadProducts();
    }
    
    private void loadOrders() throws SQLException {
        List<Order> orders = DatabaseAdapter.getAllOrders();
        orderTable.setItems(FXCollections.observableArrayList(orders));
    }
    
    private void loadCarriers() {
        List<String> carriers = DatabaseAdapter.getCarriersWithRole("carrier");
        carrierList.getItems().setAll(carriers);
    }
    
    private void loadProducts() {
        List<String> products = DatabaseAdapter.getProducts();
        productList.getItems().setAll(products);
    }

    @FXML
    void DeleteProduct(ActionEvent event) {
    	String selectedProduct = productList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null && !selectedProduct.isEmpty()) {
        	DatabaseAdapter.deleteProduct(selectedProduct);
        	productList.getItems().remove(selectedProduct);

        }
    }

    @FXML
    void LoadProduct(ActionEvent event) {
        String type = typeField.getText();
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());
        double stock = Double.parseDouble(stockField.getText());
        double threshold = Double.parseDouble(thresholdField.getText());
        DatabaseAdapter.addProduct(type, name, price, stock, threshold, imageLocation);
        productList.getItems().add(name);
    }

    @FXML
    void LogOut(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Hata işleme
        }
    
    }

    @FXML
    void loadImage(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imageLocation = selectedFile.toURI().toString();
            Image image = new Image(imageLocation);
            loadedImage.setImage(image);
        }
    }
    
    @FXML
    void addCarrier(ActionEvent event) {
    	String username = usernameField.getText();
        String email = eMailField.getText();
        String password = passwordField.getText();
        DatabaseAdapter.addCarrier(username, email, password);
        carrierList.getItems().add(username);
    }
    
    @FXML
    void removeCarrier(ActionEvent event) {
    	String selectedCarrier = carrierList.getSelectionModel().getSelectedItem();
        if (selectedCarrier != null && !selectedCarrier.isEmpty()) {
        	DatabaseAdapter.deleteCarrier(selectedCarrier);
        	carrierList.getItems().remove(selectedCarrier);

        }
    }

}
