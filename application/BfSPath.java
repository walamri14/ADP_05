package application;

import supportStuff.applicationSupport.Coordinate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



public class BfSPath implements PathFinder_I {



        private final int[][] maze;
        private final Coordinate start;
        private final Coordinate destination;
        private final List<Coordinate> shortestPath;
        private final Queue<Coordinate> queue;
        private int shortestPathLength;

        public BfSPath(final Maze maze) {
            this.maze = maze.getMazeField();
            this.start = maze.getStart();
            this.destination = maze.getDestination();
            this.shortestPath = new ArrayList<>();
            this.queue = new LinkedList<>();
            this.shortestPathLength = Integer.MAX_VALUE;
        }

        @Override
        public List<Coordinate> getShortestPath() {
            maze[start.getX()][start.getY()] = 1;
            queue.add(start);
            while (!queue.isEmpty()) {
                Coordinate currentField = queue.remove();
                if (currentField.equals(destination)) {
                    updateShortestPath();
                } else if (maze[currentField.getX()][currentField.getY()] < shortestPathLength) {
                    addNextFieldToQueue(currentField);
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

            if (addToPathIfValid(currentX, currentY - 1, cost - 1)) {
                field = new Coordinate(currentX, currentY - 1);
            } else if (addToPathIfValid(currentX + 1, currentY, cost - 1)) {
                field = new Coordinate(currentX + 1, currentY);
            } else if (addToPathIfValid(currentX, currentY + 1, cost - 1)) {
                field = new Coordinate(currentX, currentY + 1);
            } else if (addToPathIfValid(currentX - 1, currentY, cost - 1)) {
                field = new Coordinate(currentX - 1, currentY);
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

        private void addNextFieldToQueue(Coordinate field) {
            int currentX = field.getX();
            int currentY = field.getY();
            int minCostOfNextField = maze[currentX][currentY] + 1;

            addToQueueIfValid(currentX + 1, currentY, minCostOfNextField);
            addToQueueIfValid(currentX, currentY + 1, minCostOfNextField);
            addToQueueIfValid(currentX - 1, currentY, minCostOfNextField);
            addToQueueIfValid(currentX, currentY - 1, minCostOfNextField);



        }

        private void addToQueueIfValid(int x, int y, int cost) {
            if (x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && cost < maze[x][y]) {
                maze[x][y] = cost;
                queue.add(new Coordinate(x, y));
            }
        }
    }
