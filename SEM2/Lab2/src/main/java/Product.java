import org.bson.Document;

public class Product implements Documented {
    final private String name;

    public double getPrice_in_rub() {
        return price_in_rub;
    }

    final private double price_in_rub;

    public Product(String name, double price_in_rub) {
        this.name = name;
        this.price_in_rub = price_in_rub;
    }

    public Document doc() {
        return new Document().append("name", name).append("price", price_in_rub);
    }
}
