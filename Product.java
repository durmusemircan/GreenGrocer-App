package application;

public class Product {
    private int id;
    private String type;
    private String name;
    private double price;
    private int stock;
    private String imageLocation;
   
    public Product(int id, String name, String type, double price, int stock, String imageLocation) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.stock = stock;
        this.imageLocation = imageLocation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    @Override
    public String toString() {
        return name + " (" + type + "): $" + price + " per KG, " + stock + " in stock.";
    }
}
