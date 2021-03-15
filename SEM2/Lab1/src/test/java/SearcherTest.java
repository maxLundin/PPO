import org.junit.Test;
import scala.concurrent.duration.Duration;
import searchers.SearchResults;
import searchers.SearchSite;
import searchers.Searcher;
import servers.StubServer;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SearcherTest {

    private static final Duration NO_TIMEOUT = Duration.create(0, TimeUnit.MILLISECONDS);
    private static final Duration DEFAULT_TIMEOUT = Duration.create(1000, TimeUnit.MILLISECONDS);

    private static final int PORT1 = 33333;
    private static final int PORT2 = 44444;
    private static final int PORT3 = 55555;

    @Test
    public void test1Server() {
        try (StubServer server = new StubServer(PORT1, NO_TIMEOUT)) {
            Searcher searcher = new Searcher();
            searcher.addNew(new SearchSite("localhost", PORT1));
            SearchResults searchResult = searcher.search("google", DEFAULT_TIMEOUT);
            Map<String, String> res = searchResult.get();

            assertEquals(res.size(), 1);
            assertTrue(res.containsKey("localhost:" + PORT1));
            assertNotNull(res.get("localhost:" + PORT1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3Servers() {
        try (StubServer server1 = new StubServer(PORT1, NO_TIMEOUT)) {
            try (StubServer server2 = new StubServer(PORT2, NO_TIMEOUT)) {
                try (StubServer server3 = new StubServer(PORT3, NO_TIMEOUT)) {
                    Searcher searcher = new Searcher();
                    searcher.addNew(new SearchSite("localhost", PORT1));
                    searcher.addNew(new SearchSite("localhost", PORT2));
                    searcher.addNew(new SearchSite("localhost", PORT3));
                    SearchResults searchResult = searcher.search("google", DEFAULT_TIMEOUT);
                    Map<String, String> res = searchResult.get();

                    assertEquals(res.size(), 3);
                    assertTrue(res.containsKey("localhost:" + PORT1));
                    assertTrue(res.containsKey("localhost:" + PORT2));
                    assertTrue(res.containsKey("localhost:" + PORT3));
//                    assertNotNull(res.get("localhost"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1ServerWTimeout() {
        try (StubServer server = new StubServer(PORT1, DEFAULT_TIMEOUT)) {
            Searcher searcher = new Searcher();
            searcher.addNew(new SearchSite("localhost", PORT1));
            SearchResults searchResult = searcher.search("google", NO_TIMEOUT);
            Map<String, String> res = searchResult.get();

            assertEquals(res.size(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
