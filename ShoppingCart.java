package application;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<CartItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, double quantity) {
        CartItem newItem = new CartItem(product, quantity);
        items.add(newItem);
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items); 
    }

    public void clear() {
        items.clear();
    }


}

