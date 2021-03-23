package exchange;

public class Company {
    public String name;
    public int shares;
    public int price;

    public Company(String name, int price, int shares) {
        this.name = name;
        this.shares = shares;
        this.price = price;
    }
}