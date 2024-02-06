package application;

import javafx.beans.property.*;

public class Order {
    private final IntegerProperty id;
    private final StringProperty orderTime;
    private final StringProperty deliveryTime;
    private final StringProperty products;
    private final StringProperty user;
    private final IntegerProperty userID;
    private final StringProperty userAddress;
    private final StringProperty carrier;
    private final BooleanProperty isDelivered;
    private final DoubleProperty totalCost;

    public Order(int id, String orderTime, String deliveryTime, String products,
                 String user, int userID, String userAddress, String carrier,
                 boolean isDelivered, double totalCost) {
        this.id = new SimpleIntegerProperty(id);
        this.orderTime = new SimpleStringProperty(orderTime);
        this.deliveryTime = new SimpleStringProperty(deliveryTime);
        this.products = new SimpleStringProperty(products);
        this.user = new SimpleStringProperty(user);
        this.userID = new SimpleIntegerProperty(userID);
        this.userAddress = new SimpleStringProperty(userAddress);
        this.carrier = new SimpleStringProperty(carrier);
        this.isDelivered = new SimpleBooleanProperty(isDelivered);
        this.totalCost = new SimpleDoubleProperty(totalCost);
    }


    public IntegerProperty idProperty() {
        return id;
    }

    public final int getId() {
        return idProperty().get();
    }

    public final void setId(int id) {
        idProperty().set(id);
    }

    public StringProperty orderTimeProperty() {
        return orderTime;
    }

    public StringProperty deliveryTimeProperty() {
        return deliveryTime;
    }

    public StringProperty productsProperty() {
        return products;
    }

    public StringProperty userProperty() {
        return user;
    }

    public IntegerProperty userIDProperty() {
        return userID;
    }

    public StringProperty userAddressProperty() {
        return userAddress;
    }

    public StringProperty carrierProperty() {
        return carrier;
    }

    public BooleanProperty isDeliveredProperty() {
        return isDelivered;
    }

    public final boolean isIsDelivered() {
        return isDeliveredProperty().get();
    }

    public final void setIsDelivered(boolean isDelivered) {
        isDeliveredProperty().set(isDelivered);
    }

    public DoubleProperty totalCostProperty() {
        return totalCost;
    }
}
