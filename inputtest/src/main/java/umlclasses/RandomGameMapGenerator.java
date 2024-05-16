package umlclasses;
import java.util.Random;
import java.util.Arrays;


public class RandomGameMapGenerator {
    // Change this to adjust the size of the board
    private static final int boardWidth = 12; 
    private static final int boardHeight = 10;
    private static final Random random = new Random();

    public static void main(String[] args) {
        Node[][] board = generateRandomMap();
        System.out.println(board[0]);
        System.out.println(board[1]);
        // Print the generated map for debugging
        for (Node[] row : board) {
            for (Node node : row) {
                int[] coordinates = node.getCoordinates();
                if (node.getType() == Ruleset.Path) {
                    System.out.print("P" + coordinates[0] + "," + coordinates[1] + " ");
                } else if (node.getType() == Ruleset.Wall) {
                    System.out.print("W" + coordinates[0] + "," + coordinates[1] + " ");
                } else if (node.getType() == Ruleset.GhostFields) {
                    System.out.print("B"+ coordinates[0] + "," + coordinates[1] + " ");
                }
            }
            System.out.println();
            }
        }

    static Node[][] generateRandomMap() {
        Node[][] board = new Node[boardWidth][boardHeight];

        // Generates random nodes in a weighted manner
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                board[i][j] = new Node();
                if (i == 0 || j == 0 || i == boardWidth-1 || j == boardHeight-1) {
                    board[i][j].createNode(null, null, null, null, i, j, Ruleset.GhostFields);
                } 
                else {
                    int randomValue = random.nextInt(20);
                    if (randomValue <= 12) {
                        board[i][j].createNode(null, null, null, null, i, j, Ruleset.Path);
                    } 
                    else {
                        board[i][j].createNode(null, null, null, null, i, j, Ruleset.Wall);
                    }
                }
                
            }
        }

        // Assign the nodes to it's neighbors
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                board[i][j].setCoordinates(i, j);
                System.out.println(i + ", " + j + " has");
                if (board[i][j].getType().livesInTheHood()){
                    if (i > 0) {
                        board[i][j].setNodeUp(board[i - 1][j]);
                        System.out.print("generated a north ");
                    }
                    if (i < boardWidth - 1) {
                        board[i][j].setNodeDown(board[i + 1][j]);
                        System.out.print("generated a south ");
                    }
                    if (j > 0) {
                        board[i][j].setNodeLeft(board[i][j - 1]);
                        System.out.print("generated a west ");
                    }
                    if (j < boardHeight - 1) {
                        board[i][j].setNodeRight(board[i][j + 1]);
                        System.out.print("generated a east ");
                    }
                }
            }
        }
        //Processes the board to make it more playable. The variable h decides the order for the algorithms
        for (int h = 0; h<2;h++) {
            for (int i = 1; i < boardWidth-1; i++) {
                for (int j = 1; j < boardHeight-1; j++) {
                    int pathFatigue;
                    System.out.println("Checking tile: " + i + ", " + j);
                            // Makes it so borders only borders walls together with making pathways slimmer
                    if (oughtToBeWall(board[i][j]) && board[i][j].getType().livesInTheHood() && h == 0) {
                        board[i][j] = updateNode(board[i][j], 1);
                        System.out.println(Arrays.toString(board[i][j-1].getCoordinates()) + "The former node now has the neighbors " + Arrays.toString(checksNeighborTypes(gatherNeighbors(board[i][j-1]))));
                        System.out.println(Arrays.toString(board[i][j].getCoordinates()) + "The current node now has the neighbors " + Arrays.toString(checksNeighborTypes(gatherNeighbors(board[i][j]))));                    
                    }
                            // Makes it so unnecessary walls are turned into borders; in turn slimming the map.
                    if (oughtToBeBorder(board[i][j]) && h == 1){
                        board[i][j] = updateNode(board[i][j], 2);
                        System.out.println(Arrays.toString(board[i][j-1].getCoordinates()) + "The former node now has the neighbors " + Arrays.toString(checksNeighborTypes(gatherNeighbors(board[i][j-1]))));
                        System.out.println(Arrays.toString(board[i][j].getCoordinates()) + "The current node now has the neighbors " + Arrays.toString(checksNeighborTypes(gatherNeighbors(board[i][j]))));
                    }
                    //else if (oughtToBePath(board[i][j]) && board[i][j].getType().livesInTheHood()) {
    
                    //}
                }
            }
        }
    System.out.println(checksNeighborTypes(gatherNeighbors(board[8][8]))[1] + checksNeighborTypes(gatherNeighbors(board[8][8]))[2]);
    return board;
    }

    private static Node[] gatherNeighbors(Node tile) {
        //Returns the nodes which border the current tile
        Node[] neighborNodes = new Node[] {tile.getNodeUp(), tile.getNodeRight(), tile.getNodeDown(), tile.getNodeLeft()};
        return neighborNodes;
    }
    private static int[] checksNeighborTypes(Node[] set) {
        //Returns an array of how many of which types of neighbors the current tile has: Path[0] , Wall[1], Border[2], Null[3] (the last one is for debugging...)
        int[] neighborTypes = new int[4];
        for (int i = 0; i < set.length; i++) {
            if (set[i] != null) {
                Ruleset type = set[i].getType();
                if (type == Ruleset.Path) {
                    neighborTypes[0]++;
                } else if (type == Ruleset.Wall) {
                    neighborTypes[1]++;
                } else {
                    neighborTypes[2]++;
                }
            }   
            else {
                neighborTypes[3]++;
            }

        }
        return (neighborTypes);


        
    }
        private static boolean oughtToBeWall(Node tile) {
            //The heuristics for whether a tile is a candidate for the wall
            Node up = tile.getNodeUp();
            Node down = tile.getNodeDown();
            Node left = tile.getNodeLeft();
            Node right = tile.getNodeRight();

            if (checksNeighborTypes(gatherNeighbors(tile))[2]>0) {
                System.out.println("because border, ");
                return true;
            }
            else if (up.getNodeLeft().getType().isTraversable() && up.getType().isTraversable() && left.getType().isTraversable()||
                up.getNodeRight().getType().isTraversable() && up.getType().isTraversable() && right.getType().isTraversable()|| 
                down.getNodeLeft().getType().isTraversable() && down.getType().isTraversable() && left.getType().isTraversable()|| 
                down.getNodeRight().getType().isTraversable() && down.getType().isTraversable() && right.getType().isTraversable()) {
                System.out.println("because path, ");
                return true;
            }  
            else {
                return false;
            }
        }
    
        private static boolean oughtToBeBorder(Node tile) {
            //The heuristics for whether a tile is a candidate for the void
            if (checksNeighborTypes(gatherNeighbors(tile))[1] + checksNeighborTypes(gatherNeighbors(tile))[2] == 4) {
                System.out.println("The sum of " + Arrays.toString(tile.getCoordinates()) + "is " + checksNeighborTypes(gatherNeighbors(tile))[1] + checksNeighborTypes(gatherNeighbors(tile))[2]);
                return true;
            }
            else {
                return false;
            }
        }
        private static boolean oughtToBePath(Node tile) {
            return true;
        }
        private static Node updateNode(Node tile, int i) {
            //Updates the current node to fulfil it's future role
            System.out.println("Upgrading: " + Arrays.toString(tile.getCoordinates()));
            if (i==0) {
                tile.createNode(tile.getNodeUp(), tile.getNodeRight(), tile.getNodeDown(), tile.getNodeLeft(), tile.getCoordinates()[0], tile.getCoordinates()[1], Ruleset.Path);
            }
            if (i==1) {
                tile.createNode(tile.getNodeUp(), tile.getNodeRight(), tile.getNodeDown(), tile.getNodeLeft(), tile.getCoordinates()[0], tile.getCoordinates()[1], Ruleset.Wall);
            }

            if (i==2) {
                tile.createNode(tile.getNodeUp(), tile.getNodeRight(), tile.getNodeDown(), tile.getNodeLeft(), tile.getCoordinates()[0], tile.getCoordinates()[1], Ruleset.GhostFields);
            }


            return tile;
        }
}
