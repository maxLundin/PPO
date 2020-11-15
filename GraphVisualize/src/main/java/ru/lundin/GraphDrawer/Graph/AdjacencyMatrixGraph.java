package ru.lundin.GraphDrawer.Graph;

import ru.lundin.GraphDrawer.Drawing.DrawingApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdjacencyMatrixGraph extends Graph {

    List<List<Boolean>> matrix;

    public AdjacencyMatrixGraph(DrawingApi drawingApi, List<List<Boolean>> matrix) {
        super(drawingApi);
        assert (matrix.size() > 0 && matrix.size() == matrix.get(0).size());
        this.matrix = matrix;
    }

    public static List<List<Boolean>> readGraph() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<List<Boolean>> list = new ArrayList<>();
        try {
            int num = Integer.parseInt(br.readLine());
            for (int i = 0; i < num; i++) {
                list.add(Arrays.asList(Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt).map(val -> val == 1).toArray(Boolean[]::new)));
            }
        } catch (IllegalArgumentException | IOException e) {
            System.out.println("Wrong AdjacencyMatrixGraph format");
            System.exit(1);
        }
        return list;
    }

    @Override
    public void drawGraph() {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (matrix.get(i).get(j)) {
                    drawLine(i, j, matrix.size());
                }
            }
        }
        drawVertices(matrix.size());
    }
}
