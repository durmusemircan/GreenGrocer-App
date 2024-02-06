package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class CarrierController {

    @FXML
    private TableView<Order> undeliveredTable;

    @FXML
    private TableColumn<Order, String> orderTimeColumn;

    @FXML
    private TableColumn<Order, String> deliveryTimeColumn;

    @FXML
    private TableColumn<Order, String> productsColumn;

    @FXML
    private TableColumn<Order, String> userAddressColumn;

    @FXML
    private TableColumn<Order, Double> totalCostColumn;

    @FXML
    private TableView<Order> deliveredTable;

    @FXML
    private TableColumn<Order, String> orderTimeColumnDONE;

    @FXML
    private TableColumn<Order, String> deliveryTimeColumnDONE;

    @FXML
    private TableColumn<Order, String> productsColumnDONE;

    @FXML
    private TableColumn<Order, String> userAddressColumnDONE;

    @FXML
    private TableColumn<Order, Double> totalCostColumnDONE;

    @FXML
    private Button deliverButton;
    
    @FXML
    private Button logOutButton;

    private ObservableList<Order> undeliveredOrders = FXCollections.observableArrayList();
    private ObservableList<Order> deliveredOrders = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws SQLException {
        setupTableColumns();
        loadUndeliveredOrders();
        loadDeliveredOrders();
    }

    private void setupTableColumns() {
        orderTimeColumn.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        deliveryTimeColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryTime"));
        productsColumn.setCellValueFactory(new PropertyValueFactory<>("products"));
        userAddressColumn.setCellValueFactory(new PropertyValueFactory<>("userAddress"));
        totalCostColumn.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
    }

    private void loadUndeliveredOrders() throws SQLException {
        undeliveredOrders.addAll(DatabaseAdapter.getundeliveredOrders());
        undeliveredTable.setItems(undeliveredOrders);
    }

    private void loadDeliveredOrders() throws SQLException {
        deliveredOrders.addAll(DatabaseAdapter.getdeliveredOrders());
        deliveredTable.setItems(deliveredOrders);
    }

    @FXML
    private void deliverOrder() {
        Order selectedOrder = undeliveredTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            DatabaseAdapter.markAsDelivered(selectedOrder.getId());
            deliveredOrders.add(selectedOrder);
            undeliveredOrders.remove(selectedOrder);
            undeliveredTable.refresh();
            deliveredTable.refresh();
        }
    }
    
    @FXML
    void logOut(ActionEvent event) {
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
}
