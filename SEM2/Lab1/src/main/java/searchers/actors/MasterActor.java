package searchers.actors;

import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import searchers.SearchSite;
import searchers.SearchResults;

import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MasterActor extends UntypedActor {

    private final List<SearchSite> sites;
    private final SearchResults result;
    private final CompletableFuture<SearchResults> futureResult;

    public MasterActor(List<SearchSite> sites, Duration duration, CompletableFuture<SearchResults> futureResult) {
        this.sites = sites;
        this.futureResult = futureResult;
        getContext().setReceiveTimeout(duration);
        result = new SearchResults();
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        if (o instanceof ReceiveTimeout) {
            returnRes();
        } else if (o instanceof String) {
            request((String) o);
        } else if (o instanceof SearchResult) {
            SearchResult childResult = (SearchResult) o;

            result.add(childResult.searchSite.name + ":" + childResult.searchSite.port,
                    childResult.responce);

            if (result.size() == sites.size())
                returnRes();
        }
    }

    private void request(String req) {
        sites.forEach(searchSite -> getContext().actorOf(Props.create(ChildActor.class, searchSite)).tell(req, getSelf()));
    }

    private void returnRes() {
        futureResult.complete(result);
        getContext().system().stop(getSelf());
    }
}
