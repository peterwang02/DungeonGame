package game;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.concurrent.TimeUnit;
import java.util.*;

import org.xml.sax.SAXException;

public class Rogue implements Runnable{
    public static final int FRAMESPERSECOND = 60;
    public static final int TIMEPERLOOP = 1000000000 / FRAMESPERSECOND;
    private static ObjectDisplayGrid displayGrid = null;
    public static Dungeon dungeon;
    private static Player player;
    public static DungeonXMLHandler handler;
    private Thread keyStrokePrinter;
    private static int[][] map;
    private static Stack<Item>[][] item_map;
    private static Stack<Creature>[][] creature_map;


    public Rogue(int topHeight, int bottomHeight){
        int width = dungeon.width;
        int height = dungeon.gameHeight + topHeight + bottomHeight; 
        displayGrid = new ObjectDisplayGrid(width, height);
        displayGrid.setTopMessageHeight(topHeight);
        displayGrid.setbottomHeight(bottomHeight);
        map = new int[width][height];
        creature_map = new Stack[width][height];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                creature_map[i][j] = new Stack<Creature>();
            }
        }
        item_map = new Stack[width][height];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                item_map[i][j] = new Stack<Item>();
            }
        }
    }

    @Override
    public void run() {

        for(int i=0; i<dungeon.rooms.size(); i++){
            int room_width = dungeon.rooms.get(i).width;
            int room_height = dungeon.rooms.get(i).height;
            for(int j = 0; j < room_width; j++){
                for(int k = 0; k < room_height; k++){
                    if(j == 0 || j == room_width - 1 || k == 0 || k == room_height - 1){
                        displayGrid.addObjectToDisplay(new Char('X'), dungeon.rooms.get(i).posX + j, dungeon.rooms.get(i).posY + k);
                    }
                    else{
                        displayGrid.addObjectToDisplay(new Char('.'), dungeon.rooms.get(i).posX + j, dungeon.rooms.get(i).posY + k);
                        map[dungeon.rooms.get(i).posX + j][dungeon.rooms.get(i).posY + k] = dungeon.rooms.get(i).getID();
                    }
                }
            }
        }

        for(int i=0; i<dungeon.passages.size(); i++){
            for(int j=0; j<dungeon.passages.get(i).width+1; j++){
                int v_init_xpos = -1;
                int v_init_ypos = -1;                       
                int v_final_xpos = -1;
                int v_final_ypos = -1;
                for(int k=0; k<dungeon.passages.get(i).xList.size()-1; k++){
                    int init_xpos = dungeon.passages.get(i).xList.get(k);
                    int init_ypos = dungeon.passages.get(i).yList.get(k);                       
                    int final_xpos = dungeon.passages.get(i).xList.get(k+1);
                    int final_ypos = dungeon.passages.get(i).yList.get(k+1);
                    if(k == 0){
                        v_init_xpos = init_xpos;
                        v_init_ypos = init_ypos;
                    }
                    if(k == dungeon.passages.get(i).xList.size()-2){
                        v_final_xpos = final_xpos;
                        v_final_ypos = final_ypos;
                    }
                    //make smaller pos the init
                    if(final_xpos < init_xpos){
                        int temp = init_xpos;
                        init_xpos = final_xpos;
                        final_xpos = temp;
                    }
                    if(final_ypos < init_ypos){
                        int temp = init_ypos;
                        init_ypos = final_ypos;
                        final_ypos = temp;
                    }
                    int passage_width = final_ypos - init_ypos + 1;
                    int passage_height = final_xpos - init_xpos + 1;
                    for(int l = init_xpos; l < init_xpos + passage_height; l++){
                        for (int m = init_ypos; m < init_ypos + passage_width; m++){
                            displayGrid.addObjectToDisplay(new Char('#'), l, m);
                            map[l][m] = -1;
                        }
                    }
                    displayGrid.addObjectToDisplay(new Char('+'), v_init_xpos, v_init_ypos);
                    displayGrid.addObjectToDisplay(new Char('+'), v_final_xpos, v_final_ypos);
                }
            }
        }
        for(int i=0; i<dungeon.items.size(); i++){
            if (dungeon.items.get(i).owner == null) {
                if (dungeon.items.get(i).getClass() == Scroll.class) {
                    displayGrid.addObjectToDisplay(new Char('?'), dungeon.items.get(i).posX, dungeon.items.get(i).posY);
                    item_map[dungeon.items.get(i).posX][dungeon.items.get(i).posY].push(dungeon.items.get(i));
                }
                else if (dungeon.items.get(i).getClass() == Sword.class) {
                    displayGrid.addObjectToDisplay(new Char(')'), dungeon.items.get(i).posX, dungeon.items.get(i).posY);
                    item_map[dungeon.items.get(i).posX][dungeon.items.get(i).posY].push(dungeon.items.get(i));
                }
                else if (dungeon.items.get(i).getClass() == Armor.class) {
                    displayGrid.addObjectToDisplay(new Char(']'), dungeon.items.get(i).posX, dungeon.items.get(i).posY);
                    item_map[dungeon.items.get(i).posX][dungeon.items.get(i).posY].push(dungeon.items.get(i));
                }                   
            }
        }
        // Display Creatures
        for(int i=0; i<dungeon.creatures.size(); i++){
            if(dungeon.creatures.get(i).getClass() == Player.class){
                player = (Player)dungeon.creatures.get(i);
                displayGrid.addObjectToDisplay(new Char('@'),dungeon.creatures.get(i).posX, dungeon.creatures.get(i).posY);
            }
            else{
                displayGrid.addObjectToDisplay(new Char(dungeon.creatures.get(i).t), dungeon.creatures.get(i).posX, dungeon.creatures.get(i).posY);
                if (dungeon.creatures.get(i).hp > 0) {
                    creature_map[dungeon.creatures.get(i).posX][dungeon.creatures.get(i).posY].push(dungeon.creatures.get(i));
                }
            }
        }
        displayGrid.displayString(1, 0, "Remaining Hp: " + player.hp + "    Score: 0");
        displayGrid.displayString(1, displayGrid.getGameHeight() - 3, "Pack: ");
        displayGrid.displayString(1, displayGrid.getGameHeight() - 1, "Info: ");
        
        displayGrid.refresh();
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }
    
    }

    public static void main(String[] args) {

	// check if a filename is passed in.  If not, print a usage message.
	// If it is, open the file
        String fileName = "xmlFiles/testDrawing.xml";

	// Create a saxParserFactory, that will allow use to create a parser
	// Use this line unchanged
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

	// We haven't covered exceptions, so just copy the try { } catch {...}
	// exactly, // filling in what needs to be changed between the open and 
	// closed braces.
        try {
	    // just copy this
            SAXParser saxParser = saxParserFactory.newSAXParser();
	    // just copy this
            handler = new DungeonXMLHandler();
	    // just copy this.  This will parse the xml file given by fileName
            saxParser.parse(new File(fileName), handler);
        }
	// these lines should be copied exactly.
        catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace(System.out);
        }
        dungeon = handler.getDungeon();
        // Rogue test = new Rogue(dungeon.width, dungeon.gameHeight + handler.topHeight + handler.bottomHeight);
        Rogue test = new Rogue(handler.topHeight, handler.bottomHeight);
        displayGrid.gameHeight = dungeon.gameHeight;
        displayGrid.topHeight = handler.topHeight;
        Thread mainThread = new Thread(test);
        mainThread.start();
       
        try{ TimeUnit.SECONDS.sleep(1);}
        catch(InterruptedException e){e.printStackTrace(System.out);}

        test.keyStrokePrinter = new Thread(new KeyStrokePrinter(displayGrid, player, map, item_map, creature_map));
        test.keyStrokePrinter.start();

        try{mainThread.join();}
        catch(InterruptedException e){e.printStackTrace(System.out);}
        try{test.keyStrokePrinter.join();}
        catch(InterruptedException e){e.printStackTrace(System.out);}
        
    }
}