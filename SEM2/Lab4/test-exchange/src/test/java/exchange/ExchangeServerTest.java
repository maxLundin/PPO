package exchange;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeServerTest {

    @ClassRule
    public static GenericContainer simpleWebServer
            = new FixedHostPortGenericContainer("exchange:1.0-SNAPSHOT")
            .withFixedExposedPort(8081, 8081)
            .withExposedPorts(8081);

    private void testSend(String req, String... resp) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8081/" + req))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        boolean res = false;
        for (String s : resp) {
            res = res || s.equals(response.body());
        }
        Assert.assertTrue(res);
    }

    @Before
    public void drop() throws URISyntaxException, IOException, InterruptedException {
        testSend("drop", "");
    }

    @Test
    public void testaddcompany() throws Exception {
        testSend("add_company?id=1&name=itmo", "ok");

    }

    @Test
    public void testaddtrader() throws Exception {
        testSend("add_trader?id=1&name=max&money=100", "ok");
    }

    @Test
    public void testaddtraderRepeat() throws Exception {
        testSend("add_trader?id=1&name=max&money=100", "ok");

        testSend("add_trader?id=1&name=someone&money=100", "Already exist");
    }

    @Test
    public void testListShares() throws Exception {
        testSend("add_trader?id=1&name=max&money=100", "ok");
        testSend("list_shares?id=1", "Total money: 100\nMoney in shares: 0\n");
    }

    @Test
    public void buyShares() throws Exception {
        testSend("add_company?id=1&name=itmo", "ok");
        testSend("add_trader?id=1&name=max&money=100", "ok");
        testSend("add_shares?id=1&amount=100", "ok");
        testSend("change_price?id=1&price=3", "ok");
        testSend("buy_shares?id=1&idComp=1&amount=3", "ok");
        testSend("list_shares?id=1", "itmo 3\nTotal money: 91\nMoney in shares: 9\n");
    }

    @Test
    public void buySharesChangePrice() throws Exception {
        testSend("add_company?id=1&name=itmo", "ok");
        testSend("add_trader?id=1&name=max&money=100", "ok");
        testSend("add_shares?id=1&amount=100", "ok");
        testSend("change_price?id=1&price=3", "ok");
        testSend("buy_shares?id=1&idComp=1&amount=3", "ok");
        testSend("change_price?id=1&price=5", "ok");
        testSend("list_shares?id=1", "itmo 3\nTotal money: 91\nMoney in shares: 15\n");
    }

    @Test
    public void complexScenario() throws Exception {
        testSend("add_company?id=1&name=itmo", "ok");
        testSend("add_company?id=2&name=yandex", "ok");
        testSend("add_trader?id=1&name=max&money=100", "ok");
        testSend("add_trader?id=2&name=alexander&money=100", "ok");
        testSend("add_shares?id=1&amount=100", "ok");
        testSend("add_shares?id=2&amount=10", "ok");
        testSend("change_price?id=1&price=3", "ok");
        testSend("change_price?id=2&price=30", "ok");
        testSend("buy_shares?id=1&idComp=1&amount=3", "ok");
        testSend("buy_shares?id=2&idComp=2&amount=3", "ok");
        testSend("change_price?id=1&price=5", "ok");
        testSend("list_shares?id=1", "itmo 3\nTotal money: 91\nMoney in shares: 15\n");
        testSend("list_shares?id=2", "yandex 3\nTotal money: 10\nMoney in shares: 90\n");
        testSend("buy_shares?id=2&idComp=1&amount=2", "ok");
        testSend("sell_shares?id=1&idComp=1&amount=3", "ok");
        testSend("list_shares?id=1", "Total money: 106\nMoney in shares: 0\n");
        testSend("change_price?id=1&price=3", "ok");
        testSend("list_shares?id=2", "itmo 2\nyandex 3\nTotal money: 0\nMoney in shares: 96\n", "yandex 3\nitmo 2\nTotal money: 0\nMoney in shares: 96\n");
    }

}
