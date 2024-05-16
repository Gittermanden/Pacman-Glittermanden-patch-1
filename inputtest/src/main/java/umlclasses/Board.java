package umlclasses;
import java.util.Random;

public class Board {
    public Node[][] tileSet;
    public BigPill[] powerPills;
    public Pill[] pills;
    Gamemodes mode;
    private Random random = new Random();

    public Board() {
        this.tileSet = TileSetGenerator.generateMap();
        this.powerPills = generatePowerPills(tileSet);
        this.pills = generatePills(tileSet);
        this.mode = null;
    }
    public BigPill[] generatePowerPills(Node[][] tileset) {
        BigPill[] pills = new BigPill[4];
        int x = 0;
        while (x < 4) {
            int i = random.nextInt(19);
            int j = random.nextInt(21);
            System.out.println(i + ", " + j);
            if (tileset[i][j+2].getType() == Ruleset.Path) {
                pills[x] = new BigPill(tileset[i][j+2]);
                x++;
            }
        }
        return pills;
    }

    private Pill[] generatePills(Node[][] tileSet){
        Pill[] pills = new Pill[howEverManyPaths(tileSet)];
        int x = 0;
        for(int i = 1; i < 19; i++){
            for(int j = 3; j < 22; j++){
                System.out.println(i + ", " + j);
                if(tileSet[i][j].getType() == Ruleset.Path && !checkLists(tileSet[i][j])) {
                    pills[x] = new Pill(tileSet[i][j]);
                    x++;
                }
            }
        }
        return pills;
    }
    private boolean checkLists(Node tile) {
        for (int i = 0; i < powerPills.length; i++) {
            if (powerPills[i].getLocation() == tile) {
                return true;
            }
        }
    return false;
    }
    private int howEverManyPaths(Node[][] tileSet) {
        int paths = 0;
        for (int i = 1; i < 19; i++) {
            for (int j = 3; j < 22; j++) {
                if (tileSet[i][j].getType() == Ruleset.Path && !checkLists(tileSet[i][j])) {
                    paths++;
                }
            }
        }
    return paths;
    }
}
