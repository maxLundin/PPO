package Servers;

import Servers.db.Client;
import Servers.db.Mongo;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.Success;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Servers.ReportServerTest.testDB;

public class ManagerServerTest {

    Mongo mongo;
    ManagerServer server;

    @Before
    public void clear() {
        mongo = new Mongo(MongoClients.create(), testDB);
        mongo.drop();
        server = new ManagerServer(mongo, 3333);
    }

    @Test
    public void testManger() {
        Map<String, List<String>> params = new HashMap<>();
        params.put("id", List.of("33"));
        params.put("start", List.of("15-02-2021"));
        params.put("finish", List.of("13-06-2021"));
        server.addClient(params);
        params.put("id", List.of("33"));
        params.put("start", List.of("14-06-2021"));
        params.put("finish", List.of("15-06-2021"));
        server.addClient(params);

        List<Client> clients = mongo.getAllClients(33);
        Assert.assertEquals(2, clients.size());
        Client ticket = clients.get(1);
        Assert.assertEquals(33, ticket.getId());
        Assert.assertEquals("Mon Jun 14 00:00:00 MSK 2021", ticket.getStart().toString());
        Assert.assertEquals("Tue Jun 15 00:00:00 MSK 2021", ticket.getFinish().toString());
    }
}