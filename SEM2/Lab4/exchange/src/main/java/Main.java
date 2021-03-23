import exchange.AbstractServer;
import exchange.ExchangeServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        AbstractServer tinOutServer = new ExchangeServer(8081);
        ExecutorService threadPool = Executors.newFixedThreadPool(1);

        threadPool.submit(tinOutServer);
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException ignored) {
        }
    }
}