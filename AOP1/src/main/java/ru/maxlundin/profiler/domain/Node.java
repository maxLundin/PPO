package ru.maxlundin.profiler.domain;

import java.io.PrintWriter;
import java.util.*;

public class Node {
    private String path;
    private Map<String, Node> children = new HashMap<>();
    private List<Stat> methodStatistic = new ArrayList<>();

    public Node(String path) {
        this.path = path;
    }

    public void addNode(String[] path, Stat statistic) {
        addNode(path, 0, statistic);
    }

    private void addNode(String[] path, int cur, Stat statistic) {
        if (cur == path.length) {
            methodStatistic.add(statistic);
            return;
        }
        String tmpPath = path[cur];
        Node node = children.get(tmpPath);
        if (node == null) {
            node = new Node(tmpPath);
            children.put(tmpPath, node);
        }
        node.addNode(path, cur + 1, statistic);
    }

    public void compressPath() {
        if (methodStatistic.size() == 0 && children.size() == 1) {
            Node node = (Node) children.values().toArray()[0];
            children = node.getChildren();
            methodStatistic = node.getMethodStatistic();
            path = path + '.' + node.getPath();
            compressPath();
        }
        children.values().forEach(Node::compressPath);
    }

    public void print(PrintWriter printWriter) {
        print(printWriter, "", true);
    }

    private void print(PrintWriter printWriter, String prefix, boolean last) {
        printWriter.print(prefix);
        if (path.length() != 0) {
            if (!last)
                prefix += "|";
            StringBuilder sb = new StringBuilder();
            for (int i = 0 ; i < path.length(); ++i) {
                sb.append(" ");
            }
            prefix += sb.toString();
            if (prefix.length() - path.length() != 0)
                printWriter.print("⌞ ");
        }
        printWriter.println(path);

        methodStatistic.sort(Comparator.comparing(Stat::getMethodName));
        for (Stat stat : methodStatistic) {
            printWriter.print(prefix);
            printWriter.print("⌞ ");
            printWriter.println(stat);
        }
        int size = 0;
        for (Node node : children.values()) {
            size++;
            node.print(printWriter, prefix, size == children.size());
        }
    }

    private String getPath() {
        return path;
    }

    public Map<String, Node> getChildren() {
        return children;
    }

    public List<Stat> getMethodStatistic() {
        return methodStatistic;
    }
}