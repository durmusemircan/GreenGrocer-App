package application;

import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;




public class CustomerController {
	
	  @FXML
	    private TableView<Order> orderTable;
	  
	  @FXML
	    private TableColumn<Order, String> carrier;

	    @FXML
	    private TableColumn<Order, Number> delivered;

	    @FXML
	    private TableColumn<Order, String> deliveryDate;
	    
	    @FXML
	    private TableColumn<Order, String> orderDate;

	    @FXML
	    private TableColumn<Order, String> products;
	    
	    @FXML
	    private TableColumn<Order, Number> totalCost;
	
    @FXML
    private VBox fruitsVBox;
    
    @FXML
    private VBox vegetablesVBox;

    @FXML
    private Button userDetailsButton;
    
    @FXML
    private Button shoppingCartButton;
    
    @FXML
    private Button logOutButton;
    
    private User currentUser;
    
    private ShoppingCart shoppingCart;
    
    private ObservableList<Order> ordersForCustomer = FXCollections.observableArrayList();

    public CustomerController() {
        shoppingCart = new ShoppingCart();
    }
    
    public void initialize() throws SQLException {
    	DatabaseAdapter.OutOfStockDeletion();
    	String currentUsername = LoginController.getLoggedInUsername();
        currentUser = DatabaseAdapter.getUserDetails(currentUsername);
        userDetailsButton.setText(currentUser.getUsername()); 
        loadProducts();
        setOrderTable();
        loadOrdersForCustomer();
    }

    @FXML
    void editUser(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/userDetails.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) userDetailsButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("User Details");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void goToCart(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/shoppingCart.fxml"));
            Parent root = loader.load();

            ShoppingCartController shoppingCartController = loader.getController();
            shoppingCartController.setShoppingCart(this.shoppingCart);

            Stage stage = (Stage) shoppingCartButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
    void logOutUser(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadProducts() throws SQLException {
        List<Product> fruits = DatabaseAdapter.getProductsByType("fruit");
        List<Product> vegetables = DatabaseAdapter.getProductsByType("vegetable");

        loadProductCategory(fruits, fruitsVBox);
        loadProductCategory(vegetables, vegetablesVBox);
    }
    


    private void loadProductCategory(List<Product> products, VBox vBox) throws SQLException {
        for (Product product : products) {
            HBox hbox = new HBox(10);
            hbox.getChildren().add(createProductView(product));
            vBox.getChildren().add(hbox);
            DatabaseAdapter.checkThreshold(product.getId());
        }
        
    }

    private HBox createProductView(Product product){
        
        HBox productView = new HBox(1); 

        ImageView imageView = createImageView(product.getImageLocation());

        Label nameLabel = new Label(product.getName());
        Label priceLabel = new Label(product.getPrice() + " TL per KG");

        TextField quantityTextField = new TextField("1");
        quantityTextField.setPrefWidth(50);

        Button addToCartButton = new Button("Add To Cart");
        addToCartButton.setOnAction(event -> {
            try {
                double quantity = Double.parseDouble(quantityTextField.getText());
                addProductToCart(product, quantity);
            } catch (NumberFormatException e) {

            }
        });

        productView.getChildren().addAll(imageView, nameLabel, priceLabel, quantityTextField, addToCartButton);

        return productView;
    }
    
    private ImageView createImageView(String imagePath) {
        try {
            Image image = new Image(imagePath, true);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(60);
            imageView.setFitWidth(60);
            return imageView;
        } catch (Exception e) {
            e.printStackTrace();
            return new ImageView();
        }
    }

    private void addProductToCart(Product product, double quantity) {
        if (product.getStock() >= quantity) {
        	shoppingCart.addItem(product, quantity);
        } else {

        }
    }


    
    private void setOrderTable() {
        orderDate.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        deliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryTime"));
        products.setCellValueFactory(new PropertyValueFactory<>("products"));
        totalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        delivered.setCellValueFactory(new PropertyValueFactory<>("isDelivered"));
    }
    
    private void loadOrdersForCustomer() throws SQLException {
    	String currentUsername = LoginController.getLoggedInUsername();
        currentUser = DatabaseAdapter.getUserDetails(currentUsername);
        ordersForCustomer.addAll(DatabaseAdapter.getOrdersByUser(currentUsername));
		orderTable.setItems(ordersForCustomer);
    }
}

