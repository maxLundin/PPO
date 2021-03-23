package exchange;

import io.netty.handler.codec.http.HttpResponseStatus;
import rx.Observable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExchangeServer extends AbstractServer {
    private final Map<Integer, Company> companies = new HashMap<>();
    private final Map<Integer, Trader> traders = new HashMap<>();

    public ExchangeServer(int port) {
        super(port);
    }

    @Override
    Pair act(Map<String, List<String>> queryParam, String action) {
        Pair resp = new Pair();
        switch (action) {
            case "add_company":
                resp.response = addCompany(queryParam);
                resp.status = HttpResponseStatus.OK;
                break;
            case "add_shares":
                resp.response = addShares(queryParam);
                resp.status = HttpResponseStatus.OK;
                break;
            case "change_price":
                resp.response = changePrice(queryParam);
                resp.status = HttpResponseStatus.OK;
                break;
            case "add_trader":
                resp.response = addTrader(queryParam);
                resp.status = HttpResponseStatus.OK;
                break;
            case "buy_shares":
                resp.response = buyShares(queryParam);
                resp.status = HttpResponseStatus.OK;
                break;
            case "sell_shares":
                resp.response = sellShares(queryParam);
                resp.status = HttpResponseStatus.OK;
                break;
            case "list_shares":
                resp.response = listShares(queryParam);
                resp.status = HttpResponseStatus.OK;
                break;
            case "drop":
                companies.clear();
                traders.clear();
                break;
            default:
                resp.response = Observable.just("bad request");
                resp.status = HttpResponseStatus.BAD_REQUEST;
        }
        return resp;
    }

    private Observable<String> addCompany(Map<String, List<String>> queryParam) {
        int id = Integer.parseInt(queryParam.get("id").get(0));
        String name = queryParam.get("name").get(0);
        if (companies.containsKey(id)) {
            return Observable.just("Already exist");
        }
        companies.put(id, new Company(name, 0, 0));
        return Observable.just("ok");
    }

    private Observable<String> changePrice(Map<String, List<String>> queryParam) {
        int id = Integer.parseInt(queryParam.get("id").get(0));
        int price = Integer.parseInt(queryParam.get("price").get(0));
        if (!companies.containsKey(id)) {
            return Observable.just(companies.toString());
        }
        Company comp = companies.get(id);
        comp.price = price;
        return Observable.just("ok");
    }

    private Observable<String> addTrader(Map<String, List<String>> queryParam) {
        int id = Integer.parseInt(queryParam.get("id").get(0));
        String name = queryParam.get("name").get(0);
        int money = Integer.parseInt(queryParam.get("money").get(0));
        if (traders.containsKey(id)) {
            return Observable.just("Already exist");
        }
        traders.put(id, new Trader(name, money));
        return Observable.just("ok");
    }

    private Observable<String> addShares(Map<String, List<String>> queryParam) {
        int id = Integer.parseInt(queryParam.get("id").get(0));
        int amount = Integer.parseInt(queryParam.get("amount").get(0));
        if (!companies.containsKey(id)) {
            return Observable.just("No such company");
        }
        companies.get(id).shares += amount;
        return Observable.just("ok");
    }

    private Observable<String> buyShares(Map<String, List<String>> queryParam) {
        int id = Integer.parseInt(queryParam.get("id").get(0));
        int idComp = Integer.parseInt(queryParam.get("idComp").get(0));
        int amount = Integer.parseInt(queryParam.get("amount").get(0));
        if (!companies.containsKey(idComp)) {
            return Observable.just("No such company");
        }
        if (!traders.containsKey(id)) {
            return Observable.just("No such trader");
        }
        Trader trader = traders.get(id);
        Company comp = companies.get(idComp);
        if (comp.shares < amount) {
            return Observable.just("Not enough shares");
        }
        if (trader.money < amount * comp.price) {
            return Observable.just("Not enough money");
        }
        comp.shares -= amount;
        trader.money -= amount * comp.price;
        if (trader.shares.containsKey(comp)) {
            trader.shares.put(comp, trader.shares.get(comp) + amount);
        } else {
            trader.shares.put(comp, amount);
        }
        return Observable.just("ok");
    }

    private Observable<String> sellShares(Map<String, List<String>> queryParam) {
        int id = Integer.parseInt(queryParam.get("id").get(0));
        int idComp = Integer.parseInt(queryParam.get("idComp").get(0));
        int amount = Integer.parseInt(queryParam.get("amount").get(0));
        if (!companies.containsKey(idComp)) {
            return Observable.just("No such company");
        }
        if (!traders.containsKey(id)) {
            return Observable.just("No such trader");
        }

        Trader trader = traders.get(id);
        Company comp = companies.get(idComp);

        if (!trader.shares.containsKey(comp) || trader.shares.get(comp) < amount) {
            return Observable.just("Not enough shares");
        }

        comp.shares += amount;
        trader.money += amount * comp.price;
        trader.shares.put(comp, trader.shares.get(comp) - amount);
        if (trader.shares.get(comp) == 0) {
            trader.shares.remove(comp);
        }
        return Observable.just("ok");
    }

    private Observable<String> listShares(Map<String, List<String>> queryParam) {
        int id = Integer.parseInt(queryParam.get("id").get(0));
        if (!traders.containsKey(id)) {
            return Observable.just("No such trader");
        }

        Trader trader = traders.get(id);

        StringBuilder builder = new StringBuilder();
        int money = 0;
        for (Company comp : trader.shares.keySet()) {
            builder.append(comp.name).append(" ").append(trader.shares.get(comp)).append(System.lineSeparator());
            money += comp.price * trader.shares.get(comp);
        }
        builder.append("Total money: ").append(trader.money).append(System.lineSeparator()).append("Money in shares: ").append(money).append(System.lineSeparator());

        return Observable.just(builder.toString());
    }

}
