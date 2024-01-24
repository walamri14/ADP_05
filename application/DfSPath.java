package application;

import supportStuff.applicationSupport.Coordinate;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;




public class DfSPath implements PathFinder_I {
    private final int[][] maze;
    private final Coordinate start;
    private final Coordinate destination;
    private final List<Coordinate> shortestPath;
    private final Deque<Coordinate> stack;
    private int shortestPathLength;

    public DfSPath(final Maze maze) {
        this.maze = maze.getMazeField();
        this.start = maze.getStart();
        this.destination = maze.getDestination();
        this.shortestPath = new ArrayList<>();
        this.stack = new LinkedList<>();
        this.shortestPathLength = Integer.MAX_VALUE;
    }

    @Override
    public List<Coordinate> getShortestPath() {
        maze[start.getX()][start.getY()] = 1;
        stack.push(start);
        while (!stack.isEmpty()) {
            Coordinate currentField = stack.pop();
            if (currentField.equals(destination)) {
                updateShortestPath();
            } else if (maze[currentField.getX()][currentField.getY()] < shortestPathLength) {
                addNextFieldToStack(currentField);
            }
        }

        return shortestPath.isEmpty() ? null : shortestPath;
    }


    private void updateShortestPath() {
        shortestPath.clear();
        int cost = maze[destination.getX()][destination.getY()];
        Coordinate field = destination;
        shortestPath.add(destination);

        while (!field.equals(start)) {
            field = backtrackAndAddToShortestPath(cost, field);
            cost--;
        }
        shortestPathLength = shortestPath.size();
    }

    private Coordinate backtrackAndAddToShortestPath(int cost, Coordinate field) {
        int currentX = field.getX();
        int currentY = field.getY();

        if (addToPathIfValid(currentX + 1, currentY, cost - 1)) {
            field = new Coordinate(currentX + 1, currentY);
        } else if (addToPathIfValid(currentX, currentY + 1, cost - 1)) {
            field = new Coordinate(currentX, currentY + 1);
        } else if (addToPathIfValid(currentX - 1, currentY, cost - 1)) {
            field = new Coordinate(currentX - 1, currentY);
        } else if (addToPathIfValid(currentX, currentY - 1, cost - 1)) {
            field = new Coordinate(currentX, currentY - 1);
        }
        return field;
    }

    private boolean addToPathIfValid(int x, int y, int cost) {
        if (x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] == cost) {
            Coordinate newField = new Coordinate(x, y);
            shortestPath.add(0, newField);
            return true;
        }
        return false;
    }

    private void addNextFieldToStack(final Coordinate field) {
        final int currentX = field.getX();
        final int currentY = field.getY();
        final int minCostOfNextField = maze[currentX][currentY] + 1;

        addToStackIfValid(currentX + 1, currentY, minCostOfNextField);
        addToStackIfValid(currentX, currentY + 1, minCostOfNextField);
        addToStackIfValid(currentX - 1, currentY, minCostOfNextField);
        addToStackIfValid(currentX, currentY - 1, minCostOfNextField);
    }

    private void addToStackIfValid(int x, int y, int cost) {
        if (x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && cost < maze[x][y]) {
            maze[x][y] = cost;
            stack.push(new Coordinate(x, y));
        }
    }
}
