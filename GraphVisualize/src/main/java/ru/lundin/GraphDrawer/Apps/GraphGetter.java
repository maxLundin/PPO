package ru.lundin.GraphDrawer.Apps;

import ru.lundin.GraphDrawer.Drawing.DrawingApi;
import ru.lundin.GraphDrawer.Graph.AdjacencyMatrixGraph;
import ru.lundin.GraphDrawer.Graph.EdgeListGraph;
import ru.lundin.GraphDrawer.Graph.Graph;

public class GraphGetter {
    public static Graph getGraph(int graphType, DrawingApi api) {
        Graph graph;
        if (graphType == 2) {
            graph = new EdgeListGraph(api, EdgeListGraph.readGraph());
        } else {
            graph = new AdjacencyMatrixGraph(api, AdjacencyMatrixGraph.readGraph());
        }
        return graph;
    }
}
