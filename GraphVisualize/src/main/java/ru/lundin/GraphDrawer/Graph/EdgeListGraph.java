package ru.lundin.GraphDrawer.Graph;

import ru.lundin.GraphDrawer.Drawing.DrawingApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class EdgeListGraph extends Graph {

    public static class Edge {
        Node first;
        Node second;

        Edge(Node a, Node b) {
            first = a;
            second = b;
        }
    }

    public static class Node {
        List<Edge> edges;

        Node() {
            edges = new ArrayList<>();
        }
    }

    List<Edge> edges;

    public EdgeListGraph(DrawingApi drawingApi, List<Edge> edges) {
        super(drawingApi);
        this.edges = edges;
    }

    public static List<Edge> readGraph() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Node> listNode = new ArrayList<>();
        List<Edge> listEdge = new ArrayList<>();
        try {
            int NodeNum = Integer.parseInt(br.readLine());
            int EdgeNum = Integer.parseInt(br.readLine());
            for (int i = 0; i < NodeNum; i++) {
                listNode.add(new Node());
            }
            for (int i = 0; i < EdgeNum; i++) {
                Integer[] edge = Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
                listEdge.add(new Edge(listNode.get(edge[0] - 1), listNode.get(edge[1] - 1)));
            }
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException | IOException e) {
            System.out.println("Wrong EdgeListGraph format");
            System.exit(1);
        }
        return listEdge;
    }

    @Override
    public void drawGraph() {
        int index = 0;
        Map<Node, Integer> map = new HashMap<>();
        for (Edge edge : edges) {
            if (!map.containsKey(edge.first)) {
                map.put(edge.first, index);
                index++;
            }
            if (!map.containsKey(edge.second)) {
                map.put(edge.second, index);
                index++;
            }
        }

        for (Edge edge : edges) {
            drawLine(map.get(edge.first), map.get(edge.second), map.size());
        }

//            for (int i = 0; i < matrix.size(); i++) {
//                for (int j = 0; j < i; j++) {
//                    if (matrix.get(i).get(j)) {
//                        drawLine(i, j, matrix.size());
//                    }
//                }
//            }
        drawVertices(map.size());
    }
}
