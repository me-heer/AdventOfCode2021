package com.aoc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day12 extends AdventOfCode {
    ArrayList<Vertex> graph = new ArrayList<>();
    int totalPaths = 0;

    @Override
    public void solve() {
        Scanner in = super.input;
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] vertices = line.split("-");
            Vertex fromNode =
                    graph.stream().filter(vertex -> vertex.key.equals(vertices[0])).findFirst().orElse(new Vertex(vertices[0], new ArrayList<>()));
            Vertex toNode =
                    graph.stream().filter(vertex -> vertex.key.equals(vertices[1])).findFirst().orElse(new Vertex(vertices[1], new ArrayList<>()));
            fromNode.adjacentVertices.add(toNode);
            toNode.adjacentVertices.add(fromNode);

            if (!graph.stream().anyMatch(vertex -> vertex.key.equals(fromNode.key))) {
                graph.add(fromNode);
            }
            if (!graph.stream().anyMatch(vertex -> vertex.key.equals(toNode.key))) {
                graph.add(toNode);
            }
        }

        Vertex startNode = graph.stream().filter(vertex -> vertex.key.equals("start")).findFirst().get();
        visit(startNode, new LinkedList<>());
        System.out.println("Total Paths: " + totalPaths);
    }

    public void visit(Vertex vertex, LinkedList<Vertex> currentVisitedPath) {
        if (vertex.key.equals("end")) {
            currentVisitedPath.add(vertex);
            printPath(currentVisitedPath);
            totalPaths++;
            currentVisitedPath.removeLastOccurrence(vertex);
            return;
        }
        currentVisitedPath.add(vertex);
        List<Vertex> adjacentVertices = vertex.adjacentVertices;
        for (Vertex adjacentVertex: adjacentVertices) {
            if (!adjacentVertex.key.equals("start") && !(isSmallCave(adjacentVertex) && currentVisitedPath.contains(adjacentVertex))) {
                visit(adjacentVertex, currentVisitedPath);
            }
        }
        currentVisitedPath.removeLastOccurrence(vertex);
    }

    private boolean isSmallCave(Vertex vertex) {
        return !vertex.key.equals("start") && !vertex.key.equals("end") && vertex.key.matches(".*[a-z].*");
    }

    void printPath(List<Vertex> visitedVertices) {
        for (Vertex vertex: visitedVertices)
            System.out.print(vertex.key + ", ");
        System.out.println();
    }

    class Vertex {
        public String key;
        public List<Vertex> adjacentVertices;

        public Vertex(String key, List<Vertex> adjacentVertices) {
            this.key = key;
            this.adjacentVertices = adjacentVertices;
        }
    }
}
