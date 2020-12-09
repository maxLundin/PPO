package ru.maxlundin.profiler.service;

import org.aspectj.lang.Signature;
import ru.maxlundin.profiler.domain.Node;
import ru.maxlundin.profiler.domain.Stat;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ProfilerService {
    Map<String, Stat> methodCalls = new HashMap<>();

    public void registerCall(Signature method, long timeNs) {
        Stat statistic = methodCalls.get(method.toString());
        if (statistic == null) {
            statistic = new Stat(method.getDeclaringTypeName(), method.getName());
            methodCalls.put(method.toString(), statistic);
        }
        statistic.addCalls(timeNs);
    }

    public void visualizeTree() {
        Node root = new Node("");
        methodCalls.values().forEach(statistic -> root.addNode(statistic.getPackageName().split("\\."), statistic));
        root.getChildren().values().forEach(Node::compressPath);
        PrintWriter printWriter = new PrintWriter(System.out);
        root.getChildren().values().forEach(node -> node.print(printWriter));
        printWriter.flush();
    }
}
