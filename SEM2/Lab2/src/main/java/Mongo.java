import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.Success;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import rx.Observable;
import rx.functions.Func1;

public class Mongo {
    private static final MongoClient client = MongoClients.create();
    private static final Bson emptyFilter = new BsonDocument();

    private static final String DATABASE_NAME = "products";
    private static final String USER_COLLECTION = "user";
    private static final String PRODUCT_COLLECTION = "product";

    private static <T extends Documented> Observable<Success> insert(String collection, T doc) {
        return client.getDatabase(DATABASE_NAME)
                .getCollection(collection)
                .insertOne(doc.doc());
    }

    private static <R> Observable<R> get(String collection, Bson filter, Func1<? super Document, ? extends R> function) {
        return client.getDatabase(DATABASE_NAME)
                .getCollection(collection)
                .find(filter).toObservable().map(function);
    }

    public static Observable<Success> addUser(User user) {
        return insert(USER_COLLECTION, user);
    }

    public static Observable<Success> addProduct(Product product) {
        return insert(PRODUCT_COLLECTION, product);
    }

    public static Observable<Product> getProducts() {
        return get(PRODUCT_COLLECTION, emptyFilter, doc -> new Product(doc.getString("name"), doc.getDouble("price")));
    }

    public static Observable<User> getUser(int id) {
        return get(USER_COLLECTION, Filters.eq("id", id), doc -> new User(doc.getInteger("id"), Exchange.fromString(doc.getString("cur"))));
    }

}
