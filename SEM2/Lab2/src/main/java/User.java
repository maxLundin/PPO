import org.bson.Document;

public class User implements Documented {
    private final int id;
    private final Exchange.Currency currency;

    User(int id, Exchange.Currency currency) {
        this.id = id;
        this.currency = currency;
    }

    public Exchange.Currency getCurrency() {
        return currency;
    }

    public Document doc() {
        return new Document().append("id", id).append("cur", currency.toString());
    }
}
