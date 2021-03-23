package exchange;

import java.util.HashMap;
import java.util.Map;

public class Trader {
    public String name;
    Map<Company, Integer> shares;
    int money;

    public Trader(String name, int money) {
        this.name = name;
        this.shares = new HashMap<>();
        this.money = money;
    }
}
