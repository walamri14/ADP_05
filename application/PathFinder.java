package application;


import java.io.Serializable;
import java.util.*;

import supportStuff.applicationSupport.Coordinate;


/**
 * Task: For information see ReadMe.txt resp. task
 * This is just a demo. It shows an easy way to support multiple alternatives.
 *
 * @version {@value #encodedVersion}
 * @author  Michael Schaefers  ([UTF-8]:"Michael Schäfers");
 *          Px@Hamburg-UAS.eu
 */



public class PathFinder implements PathFinder_I, Serializable {
    private Maze maze;
    private Coordinate start;
    private Coordinate end;
    public  PathFinder(Maze maze) {
        this.maze = maze;
    }
    @Override
    public List<Coordinate> getShortestPath() {
         start = maze.getStart();
         end = maze.getDestination();
        List<Coordinate> breadthFirstSearch = breadthFirstSearch();
        if (breadthFirstSearch != null) {
            return breadthFirstSearch;
        } else {
            Set<Coordinate> visited = new HashSet<>();
            return depthFirstSearch(maze.getStart(), visited);
        }
    }
    private List<Coordinate> depthFirstSearch(Coordinate current, Set<Coordinate> visited) {
            if (current.equals(maze.getDestination())) {
                List<Coordinate> path = new ArrayList<>();
                path.add(current);
                return path;
            } else {
                visited.add(current);
                int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

                for (int[] direction : directions) {
                    int newX = current.getX() + direction[0];
                    int newY = current.getY() + direction[1];
                    Coordinate neighbor = new Coordinate(newX, newY);

                    if (isValidMove(neighbor) && !visited.contains(neighbor)) {
                        List<Coordinate> path = depthFirstSearch(neighbor, visited);
                        if (path != null) {
                            //int i=0;
                            path.add(0, current);

                            return path;
                        }
                    }
                }
                return null;
            }


    }
    private List<Coordinate> breadthFirstSearch(){
            Queue<Coordinate> queue = new LinkedList();
            Map<Coordinate, Coordinate> parentMap = new HashMap();
            Set<Coordinate> visited = new HashSet();
            int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};


            queue.add(start);
            visited.add(start);

            while(!queue.isEmpty()) {
                Coordinate current = queue.poll();
                if (current.equals(end)) {
                    return this.getPath(parentMap, start, end);
                }

                for (int[] direction : directions) {
                    int newX = current.getX() + direction[0];
                    int newY = current.getY() + direction[1];
                    Coordinate neighbor = new Coordinate(newX, newY);

                    if (this.isValidMove(neighbor) && !visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                        parentMap.put(neighbor, current);
                    }
                }
            }

            return null;


    }

    private boolean isValidMove(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        int[][] mazeField = maze.getMazeField();
        boolean withinXBounds = x >= 0 && x < mazeField.length;
        boolean withinYBounds = withinXBounds && y >= 0 && y < mazeField[x].length;
        boolean notBlocked = withinYBounds && mazeField[x][y] != Integer.MIN_VALUE;

        return notBlocked;    }

    private List<Coordinate> getPath(Map<Coordinate, Coordinate> parentMap,Coordinate start, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        for(Coordinate current = end; !current.equals(start); current = parentMap.get(current)) {
            path.add(0, current);
        }

        path.add(0, start);
        return path;
    }

}//class
