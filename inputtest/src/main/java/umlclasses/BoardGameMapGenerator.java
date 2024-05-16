package umlclasses;

public class BoardGameMapGenerator {
    // Change this to adjust the size of the board
    private static final int boardWidth = 19; 
    private static final int boardHeight = 25;


    public static void main(String[] args) {
        Node[][] board = generateRandomMap();

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
            System.out.println();
            }
        }
    }
    public static Node[][] generateRandomMap() {
        Node[][] board = new Node[boardWidth][boardHeight];

        // Generates border nodes
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                board[i][j] = new Node();
                if (i == 0 || i == boardWidth-1) {
                    board[i][j].createNode(null, null, null, null, i, j, Ruleset.GhostFields);
                } 
                else if (j == 2 || j == boardHeight-3) {
                    board[i][j].createNode(null, null, null, null, i, j, Ruleset.GhostFields);
                } 
                //Generates wall nodes
                else if ((i == 1 || i == 17) &&
                (j == 7 || j == 16)) {
                    board[i][j].createNode(null, null, null, null, i, j, Ruleset.Wall);
                } 
                else if ((i == 2 || i == 16) &&
                (j == 4 || j == 5 || j == 7 || j == 9 || j == 10 || j == 12 || j == 13 || j == 14 || j == 18 || j == 20)) {
                    board[i][j].createNode(null, null, null, null, i, j, Ruleset.Wall);
                } 
                else if ((i == 3 || i == 15) &&
                (j == 4 || j == 10 || j == 14 || j == 16 || j == 17 || j == 18 || j == 20)) {
                    board[i][j].createNode(null, null, null, null, i, j, Ruleset.Wall);
                } 
                else if ((i == 4 || i == 14) &&
                (j == 4 || j == 6 || j == 7 || j == 8 || j == 12)) {
                    board[i][j].createNode(null, null, null, null, i, j, Ruleset.Wall);
                } 
                else if ((i == 5 || i == 13) &&
                (j == 4 || j == 8 || j == 10 || j == 11 || j == 12 || j == 14 || j == 15 || j == 16 || j == 18 || j == 19 || j == 20 || j == 21)) {
                    board[i][j].createNode(null, null, null, null, i, j, Ruleset.Wall);
                } 
                else if ((i == 6 || i == 12) &&
                (j == 6 || j == 14 || j == 18)) {
                    board[i][j].createNode(null, null, null, null, i, j, Ruleset.Wall);
                } 
                else if ((i == 7 || i == 11) &&
                (j == 3 || j == 4 || j == 6 || j == 8 || j == 10 || j == 11 || j == 12 || j == 14 || j  == 16 || j == 18 || j == 20)) {
                    board[i][j].createNode(null, null, null, null, i, j, Ruleset.Wall);
                } 
                else if ((i == 8 || i == 10) &&
                (j == 8 || j == 10 || j == 12 || j == 16 || j == 20)) {
                    board[i][j].createNode(null, null, null, null, i, j, Ruleset.Wall);
                } 
                else if ((i == 9) &&
                (j == 4 || j == 5 || j == 6 || j == 8 || j == 12 || j == 14 || j == 15 || j == 16 || j == 18 || j == 19 || j == 20)) {
                    board[i][j].createNode(null, null, null, null, i, j, Ruleset.Wall);
                } 
                else {
                    board[i][j].createNode(null, null, null, null, i, j, Ruleset.Path);
                } 
            }
        }
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
    return board;
    }
 }