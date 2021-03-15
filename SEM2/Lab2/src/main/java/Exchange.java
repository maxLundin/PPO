public class Exchange {

    private static final double RUB_c = 1;
    private static final double EUR_c = 84;
    private static final double USD_c = 75;

    public enum Currency {
        RUB, EUR, USD
    }

    static Currency fromString(String name) {
        switch (name) {
            case "RUB":
                return Currency.RUB;
            case "EUR":
                return Currency.EUR;
            case "USD":
                return Currency.USD;
            default:
                throw new RuntimeException("Invalid Currency");
        }
    }

    static double convertTo(double value, Currency to) {
        switch (to) {
            case RUB:
                return value / RUB_c;
            case EUR:
                return value / EUR_c;
            case USD:
                return value / USD_c;
            default:
                throw new RuntimeException("Invalid Currency");
        }
    }

    static double convertFrom(double value, Currency from) {
        switch (from) {
            case RUB:
                return value * RUB_c;
            case EUR:
                return value * EUR_c;
            case USD:
                return value * USD_c;
            default:
                throw new RuntimeException("Invalid Currency");
        }
    }
}

