package Servers;

import Servers.db.Mongo;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.Success;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rx.observers.TestSubscriber;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportServerTest {

    Mongo mongo;
    InOutServer server;
    ManagerServer serverM;
    ReportServer serverR;

    public final static String testDB = "testDB";

    @Before
    public void clear() {
        mongo = new Mongo(MongoClients.create(), testDB);
        mongo.drop();

        server = new InOutServer(mongo, 4444);
        serverM = new ManagerServer(mongo, 3333);
        serverR = new ReportServer(mongo, 2222, mongo.events);
    }

    @Test
    public void testManger() {
        Map<String, List<String>> params = new HashMap<>();
        params.put("id", List.of("33"));
        params.put("start", List.of("13-06-2021"));
        params.put("finish", List.of("15-06-2021"));
        serverM.addClient(params);

        server.in(33, Timestamp.valueOf("2021-06-14 00:00:00"));
        server.out(33, Timestamp.valueOf("2021-06-14 20:00:00"));

        Assert.assertEquals(2, mongo.events.size());
        Assert.assertEquals(33, mongo.events.peek().getId());
        Assert.assertEquals(Timestamp.valueOf("2021-06-14 00:00:00"), mongo.events.peek().getTime());
        serverR.updateStats();
        Assert.assertEquals(0, mongo.events.size());
        TestSubscriber<String> subscriber = new TestSubscriber<>();
        serverR.dayStat().subscribe(subscriber);
        Assert.assertEquals(subscriber.getOnNextEvents().get(0),
                "Day stat:\n" +
                        "0 1 0 0 0 0 0 \n");
    }
}