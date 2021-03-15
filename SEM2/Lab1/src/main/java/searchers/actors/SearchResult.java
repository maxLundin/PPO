package searchers.actors;

import searchers.SearchSite;

public class SearchResult {
    final public String responce;
    final public SearchSite searchSite;

    public SearchResult(String responce, SearchSite searchSite) {
        this.responce = responce;
        this.searchSite = searchSite;
    }
}
