package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class CartItem {
    private final SimpleStringProperty productName;
    private final SimpleDoubleProperty quantity;
    private final SimpleDoubleProperty price;
    private final Product product;

    public CartItem(Product product, double quantity) {
        this.product = product;
        this.productName = new SimpleStringProperty(product.getName());
        this.quantity = new SimpleDoubleProperty(quantity);
        this.price = new SimpleDoubleProperty(product.getPrice() * quantity);
    }

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public double getQuantity() {
        return quantity.get();
    }

    public SimpleDoubleProperty quantityProperty() {
        return quantity;
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public Product getProduct() {
        return this.product;
    }
}



