package searchers.actors;

import akka.actor.UntypedActor;
import searchers.SearchSite;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChildActor extends UntypedActor {
    final SearchSite site;

    public ChildActor(SearchSite site) {
        this.site = site;
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof String) {
            String msg = (String) o;
            URI uri = URI.create(String.format("http://%s:%d/?q=%s",
                    site.name, site.port, msg));

            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();

            String response = client
                    .send(request, HttpResponse.BodyHandlers.ofString())
                    .body().intern();

            sender().tell(new SearchResult(response, site), getSelf());
        }
    }
}
