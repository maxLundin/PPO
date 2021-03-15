import Servers.AbstractServer;
import Servers.InOutServer;
import Servers.ManagerServer;
import Servers.ReportServer;
import Servers.db.Mongo;
import com.mongodb.rx.client.MongoClients;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        Mongo mongo = new Mongo(MongoClients.create(), "product");
        AbstractServer tinOutServer = new InOutServer(mongo, 1111);
        AbstractServer managerServer = new ManagerServer(mongo, 2222);
        AbstractServer reportServer = new ReportServer(mongo, 3333, mongo.events);

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        threadPool.submit(tinOutServer);
        threadPool.submit(managerServer);
        threadPool.submit(reportServer);
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ignored) {
        }
    }
}