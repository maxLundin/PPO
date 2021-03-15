import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class Server {

    public static void main(String[] args) {
        HttpServer
                .newServer(8080)
                .start((req, resp) -> {

                    String name = req.getDecodedPath().substring(1);
                    Observable<String> response = process(name, req.getQueryParameters());

                    return resp.writeString(response);
                })
                .awaitShutdown();
    }

    private static Observable<String> process(String str, Map<String, List<String>> queryParameters) {
        try {
            switch (str) {
                case "register":
                    return register(queryParameters);
                case "add":
                    return add(queryParameters);
                case "list":
                    return list(queryParameters);
                default:
                    return Observable.just(str + " no such command!");
            }
        } catch (Exception e) {
            return Observable.just("Invalid arguments");
        }
    }

    public static Observable<String> list(Map<String, List<String>> queryParameters) {
        int id = Integer.parseInt(queryParameters.get("id").get(0));

        return Mongo.getUser(id).map(User::getCurrency)
                .flatMap(currency -> Mongo.getProducts()
                        .map(product -> Exchange.convertTo(product.getPrice_in_rub(), currency) + System.lineSeparator()));
    }

    public static Observable<String> add(Map<String, List<String>> queryParameters) {
        String name = queryParameters.get("name").get(0);
        double value = Exchange.convertFrom(
                Double.parseDouble(queryParameters.get("value").get(0)),
                Exchange.fromString(queryParameters.get("currency").get(0)));

        Mongo.addProduct(new Product(name, value));
        return Observable.just("Item added");
    }

    public static Observable<String> register(Map<String, List<String>> queryParameters) {
        int id = Integer.parseInt(queryParameters.get("id").get(0));
        Exchange.Currency currency = Exchange.fromString(queryParameters.get("currency").get(0));

        Mongo.addUser(new User(id, currency));
        return Observable.just("User registered");
    }
}
