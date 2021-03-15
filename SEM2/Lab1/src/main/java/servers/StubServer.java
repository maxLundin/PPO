package servers;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import scala.concurrent.duration.Duration;


import static org.mockserver.model.HttpStatusCode.OK_200;

public class StubServer implements AutoCloseable {
    private final ClientAndServer stubServer;

    public StubServer(int port, Duration duration) {
        this.stubServer = ClientAndServer.startClientAndServer(port);
        stubServer.when(HttpRequest.request().withMethod("GET")).respond(httpRequest -> {
            Thread.sleep(duration.toMillis());
            String name = httpRequest.getFirstQueryStringParameter("q");
            return HttpResponse.response().withStatusCode(OK_200.code()).withBody(name + "1\n" + name + "2\n" + name + "3\n" + name + "4\n" + name + "5");
        });
    }

    public void close() throws Exception {
        stubServer.close();
    }
}
