package searchers;

import java.util.HashMap;
import java.util.Map;

public class SearchResults {
    final private Map<String, String> res = new HashMap<>();

    public void add(String s1, String s2) {
        res.put(s1, s2);
    }

    public Map<String, String> get() {
        return res;
    }

    public int size() {
        return res.size();
    }
}
