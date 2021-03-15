package searchers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import searchers.actors.MasterActor;

import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Searcher {
    private final List<SearchSite> sites = new ArrayList<>();

    public void addNew(SearchSite s) {
        sites.add(s);
    }

    public SearchResults search(String message, Duration duration) throws ExecutionException, InterruptedException {
        ActorSystem actorSystem = ActorSystem.create("Searcher");
        CompletableFuture<SearchResults> result = new CompletableFuture<>();
        ActorRef master = actorSystem.actorOf(Props.create(MasterActor.class, sites, duration, result));
        master.tell(message, ActorRef.noSender());
        return result.get();
    }

}
