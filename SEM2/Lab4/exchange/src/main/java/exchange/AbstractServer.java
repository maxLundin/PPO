package exchange;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

import java.util.List;
import java.util.Map;

public abstract class AbstractServer implements Runnable {
    private final int port;

    public AbstractServer(int port) {
        this.port = port;
    }

    public static class Pair {
        Observable<String> response;
        HttpResponseStatus status;

        public Pair() {
        }
    }

    abstract Pair act(Map<String, List<String>> queryParam, String action);

    @Override
    public void run() {
        HttpServer.newServer(port).start((req, resp) -> {
            String action = req.getDecodedPath().substring(1);
            Map<String, List<String>> queryParam = req.getQueryParameters();
            Pair response = act(queryParam, action);
            resp.setStatus(response.status);
            return resp.writeString(response.response);
        }).awaitShutdown();
    }
}
