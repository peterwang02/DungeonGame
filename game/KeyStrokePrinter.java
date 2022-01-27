package game;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.*;

public class KeyStrokePrinter implements InputObserver, Runnable {

    private static int DEBUG = 0;
    private static String CLASSID = "KeyStrokePrinter";
    private static Queue<Character> inputQueue = null;
    private ObjectDisplayGrid displayGrid;
    private Player player;
    private int[][] map;
    private Stack<Item>[][] item_map;
    private Stack<Creature>[][] creature_map;
    public List<Item> pack;
    private boolean open_pack = false;
    private static int moves = 0;
    private static int hmoves = 0;
    private boolean hallu = false;
    private boolean end = false;
    private Random rand = new Random();
    
    public void open_pack_f(){
        if(open_pack){
            if(pack.isEmpty()){
                displayGrid.displayString(7, displayGrid.getGameHeight() - 3,"Inventory is Empty");
            } else {
                int previous = 0;
                String foo = "";
                String bar = "";
                for(int i=0; i<pack.size(); i++) {
                    foo = "";
                    bar = "";
                    if (pack.get(i).getClass() == Armor.class) {
                        bar = "("+Integer.toString(pack.get(i).intval)+")";
                        if ((Armor) pack.get(i) == player.armor) {
                            foo = " (a)";
                        }
                    } else if (pack.get(i).getClass() == Sword.class) {
                        bar = "("+Integer.toString(pack.get(i).intval)+")";
                        if ((Sword) pack.get(i) == player.sword) {
                            foo = " (w)";
                        }           
                    }
                    displayGrid.displayString(7 + previous, displayGrid.getGameHeight() - 3, Integer.toString(i + 1) + ": " + pack.get(i).name + foo + bar + " ");
                    previous += (Integer.toString(i + 1) + ": " + pack.get(i).name + foo + bar + " ").length();
                }
            }
        } else {
            displayGrid.displayString(7, displayGrid.getGameHeight() - 3,"");
        }
    }

    public KeyStrokePrinter(ObjectDisplayGrid grid, Player _player, int[][] _map, Stack<Item>[][] _item_map, Stack<Creature>[][] _c_map) {
        inputQueue = new ConcurrentLinkedQueue<>();
        displayGrid = grid;
        player = _player;
        map = _map;
        item_map = _item_map;
        creature_map = _c_map;
        pack = player.pack;
    }

    @Override
    public void observerUpdate(char ch) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".observerUpdate receiving character " + ch);
        }
        inputQueue.add(ch);
    }

    private void rest() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean processInput() {

        char ch;

        boolean processing = true;
        end = false;
        while (processing) {
            if (inputQueue.peek() == null) {
                processing = false;
            } else {
                ch = inputQueue.poll();
                if (DEBUG > 1) {
                    System.out.println(CLASSID + ".processInput peek is " + ch);
                }
                if (ch == 'X') {
                    System.out.println("got an X, ending input checking");
                    return false;
                }if (ch == 'k') {
                    moves++;
                    checkhallu();
                    checkHpMoves();
                    if(map[player.posX][player.posY - 1] != 0){
                        Stack<Creature> creature = creature_map[player.posX][player.posY - 1];
                        if(!creature.isEmpty()) {
                            combat(creature);
                            if (end) return false;
                        } else {
                            displayGrid.removeObjectFromDisplay(player.posX, player.posY);
                            player.posY -= 1;
                            displayGrid.addObjectToDisplay(new Char('@'), player.posX, player.posY);
                        }
                    }
                }if (ch == 'h') {
                    moves++;
                    checkhallu();
                    checkHpMoves();
                    if(map[player.posX - 1][player.posY] != 0){
                        Stack<Creature> creature = creature_map[player.posX - 1][player.posY];
                        if(!creature.isEmpty()) {
                            combat(creature);
                            if (end) return false;
                        } else {
                            displayGrid.removeObjectFromDisplay(player.posX, player.posY);
                            player.posX -= 1;
                            displayGrid.addObjectToDisplay(new Char('@'), player.posX, player.posY);
                        }
                    }
                }if (ch == 'j') {
                    moves++;
                    checkhallu();
                    checkHpMoves();
                    if(map[player.posX][player.posY + 1] != 0){
                        Stack<Creature> creature = creature_map[player.posX][player.posY + 1];
                        if(!creature.isEmpty()) {
                            combat(creature);
                            if (end) return false;
                        } else {
                            displayGrid.removeObjectFromDisplay(player.posX, player.posY);
                            player.posY += 1;
                            displayGrid.addObjectToDisplay(new Char('@'), player.posX, player.posY);
                        }
                    }
                }if (ch == 'l') {
                    moves++;
                    checkhallu();
                    checkHpMoves();
                    if(map[player.posX + 1][player.posY] != 0){
                        Stack<Creature> creature = creature_map[player.posX + 1][player.posY];
                        if(!creature.isEmpty()) {
                            combat(creature);
                            if (end) return false;
                        } else {
                            displayGrid.removeObjectFromDisplay(player.posX, player.posY);
                            player.posX += 1;
                            displayGrid.addObjectToDisplay(new Char('@'), player.posX, player.posY);
                        }
                    }
                }if(ch == 'i'){
                    open_pack = !open_pack;
                    open_pack_f();
                }if (ch == 'd') {
                    while(inputQueue.peek() == null){}
                    ch = inputQueue.poll();
                    int num = Character.getNumericValue(ch) - 1;
                    if(num < pack.size() && num >= 0 && !pack.isEmpty()) {
                        item_map[player.posX][player.posY].push(pack.get(num));
                        displayGrid.removeObjectFromDisplay(player.posX, player.posY); //get player out
                        displayGrid.addObjectToDisplay(new Char(pack.get(num).t), player.posX, player.posY);
                        displayGrid.addObjectToDisplay(new Char('@'), player.posX, player.posY); //put player back in
                        if (player.armor == pack.get(num)) {
                            player.armor = null;
                        } else if (player.sword == pack.get(num)) {
                            player.sword = null;
                        }
                        pack.remove(num);
                    } else {
                        displayGrid.displayInfo("Item number specified not in pack");
                    }
                }if(ch == 'p' && creature_map[player.posX][player.posY].isEmpty()){
                    if(!item_map[player.posX][player.posY].isEmpty()){
                        Item new_item = item_map[player.posX][player.posY].peek();
                        pack.add(new_item);
                        displayGrid.removeObjectFromDisplay(player.posX, player.posY);
                        displayGrid.removeObjectFromDisplay(player.posX, player.posY);
                        displayGrid.addObjectToDisplay(new Char('@'), player.posX, player.posY); //put player back in
                        item_map[player.posX][player.posY].pop();
                    }
                }if(ch == 'E'){
                    while(inputQueue.peek() == null){}
                    ch = inputQueue.poll();
                    if(ch == 'Y' || ch == 'y'){
                        System.exit(1);
                    }
                }if(ch == 'H'){
                    while(inputQueue.peek() == null){}
                    ch = inputQueue.poll();
                    if(ch == 'h') { System.out.println("h: move left 1 space."); }
                    else if(ch == 'l') { System.out.println("l: move right 1 space."); }
                    else if(ch == 'k') { System.out.println("k: move up 1 space."); }
                    else if(ch == 'j') { System.out.println("l: move down 1 space."); }
                    else if(ch == 'i') { System.out.println("i: inventory -- show pack contents");}
                    else if(ch == 'c') { System.out.println("c: take off/change armor"); }
                    else if(ch == 'd') { System.out.println("d: drop <item number> item from pack"); }
                    else if(ch == 'p') { System.out.println("p: pick up item under player and put in pack"); }
                    else if(ch == 'r') { System.out.println("r: read scroll <item number> item from pack"); }
                    else if(ch == 't') { System.out.println("t: take out weapon from pack"); }
                    else if(ch == 'w') { System.out.println("w: wear armor <item number> item from pack"); }
                }if(ch == 'c') {
                    if (player.armor != null) {
                        player.armor = null;
                    } else {
                        displayGrid.displayInfo("No Armor is Equipped");
                    }
                }if(ch == 'r') {
                    while(inputQueue.peek() == null){}
                    ch = inputQueue.poll();
                    int num = Character.getNumericValue(ch) - 1;
                    if(num < pack.size() && num >= 0 && !pack.isEmpty()) {
                        Item item = pack.get(num);
                        if (item.getClass() != Scroll.class) {
                            displayGrid.displayInfo("Cannot Read: Item specified is not a scroll");
                        } else {
                            Scroll scroll = (Scroll) item; 
                            for (ItemAction action : scroll.actions) {
                                executeAction(action);
                            }
                            pack.remove(num);
                        }
                    } else {
                        displayGrid.displayInfo("Cannot Read: Item specified not in pack");
                    }
                }if(ch == 'T') {
                    while(inputQueue.peek() == null){}
                    ch = inputQueue.poll();
                    int num = Character.getNumericValue(ch) - 1;
                    if(num < pack.size() && num >= 0 && !pack.isEmpty()) {
                        Item item = pack.get(num);
                        if (item.getClass() != Sword.class) {
                            displayGrid.displayInfo("Cannot Wield: Item specified is not a sword");
                        } else {
                            Sword sword = (Sword) item;
                            player.setWeapon(sword); 
                        }
                    } else {
                        displayGrid.displayInfo("Cannot Wield: Item specified not in pack");
                    }                    
                }if(ch == 'w') {
                    while(inputQueue.peek() == null){}
                    ch = inputQueue.poll();
                    int num = Character.getNumericValue(ch) - 1;
                    if(num < pack.size() && num >= 0 && !pack.isEmpty()) {
                        Item item = pack.get(num);
                        if (item.getClass() != Armor.class) {
                            displayGrid.displayInfo("Cannot Equip: Item specified is not an armor");
                        } else {
                            Armor armor = (Armor) item;
                            player.setArmor(armor); 
                        }
                    } else {
                        displayGrid.displayInfo("Cannot Equip: Item specified not in pack");
                    }                      
                }
            }
        }
        return true;
    }

    @Override
    public void run() {
        displayGrid.registerInputObserver(this);
        boolean working = true;
        while (working) {
            rest();
            working = (processInput());
        }
    }

    public void combat(Stack<Creature> creature) {
        Boolean ret = true;
        int dmgP = rand.nextInt(player.maxHit + 1);
        if (player.sword != null) {
            dmgP += player.sword.intval;
        }
        creature.peek().setHp(creature.peek().hp - dmgP);
        for (CreatureAction action : creature.peek().hitAction) {
            executeAction(action);
        }
        if (creature.peek().hp <= 0) {
            for (CreatureAction action: creature.peek().deathAction) {
                executeAction(action);
                ret = false;
            }
        }
        
        int dmgC;
        if(creature.peek().hp > 0) {
            dmgC = rand.nextInt(creature.peek().maxHit + 1);
            if (player.armor != null) {
                dmgC -= player.armor.intval;
            }
            dmgC = (dmgC < 0) ? 0 : dmgC;
            player.setHp(player.hp - dmgC);
            for (CreatureAction action: player.hitAction) {
                executeAction(action);
            }
            if(player.hp <= 0) {
                for (CreatureAction action: player.deathAction) {
                    executeAction(action);
                    ret = false;
                }
            }
        } else {
            dmgC = 0;
            //displayGrid.removeObjectFromDisplay(creature.peek().posX, creature.peek().posY);
            creature.pop();
        }
        displayGrid.displayString(1, 0, "Remaining Hp: " + player.hp + "    Score: 0");
        if (ret) {
            displayGrid.displayInfo("Creature received: " + Integer.toString(dmgP) + " damage    " + 
            "Player received: " + Integer.toString(dmgC) + " damage");
        }
    }

    public void checkHpMoves() {
        if (moves == player.hpMoves) {
            player.hp++;
            moves = 0;
        }
        displayGrid.displayString(1, 0, "Remaining Hp: " + player.hp + "    Score: 0");
    }

    public void executeAction(Action action) {
        Creature owner = null;
        if (action != null) {
            switch (action.name) {
                case "EndGame" :
                    end = true;
                    break;
                case "DropPack" :
                    if (!pack.isEmpty()) {
                        item_map[player.posX][player.posY].add(pack.get(0));
                        displayGrid.removeObjectFromDisplay(player.posX, player.posY);
                        displayGrid.addObjectToDisplay(new Char(pack.get(0).t), player.posX, player.posY);
                        displayGrid.addObjectToDisplay(new Char('@'), player.posX, player.posY);
                        pack.remove(0);
                    }
                    break;
                case "YouWin" :
                    break;
                case "Remove" :
                    displayGrid.removeObjectFromDisplay(((CreatureAction) action).owner.posX, ((CreatureAction) action).owner.posY);
                    break;
                case "ChangeDisplayedType" :
                    owner = ((CreatureAction) action).owner;
                    owner.t = action.c;
                    break;
                case "Teleport" :
                int x = 0;
                int y =0;
                while (true) {
                    y = rand.nextInt(map[0].length);
                    x = rand.nextInt(map.length);
                    if (map[x][y] != 0 && creature_map[x][y].isEmpty() && item_map[x][y].isEmpty()) {
                        Creature creature = ((CreatureAction) action).owner;
                        displayGrid.removeObjectFromDisplay(creature.posX, creature.posY);
                        creature.posX = x;
                        creature.posY = y;
                        displayGrid.addObjectToDisplay(new Char(((CreatureAction) action).owner.t), x, y);
                        break;
                    }
                }
                break;
                case "UpdateDisplay" :
                    owner = ((CreatureAction) action).owner;
                    displayGrid.removeObjectFromDisplay(owner.posX, owner.posY);
                    displayGrid.addObjectToDisplay(new Char(owner.t), owner.posX, owner.posY);
                    displayGrid.displayString(1, 0, "Remaining Hp: " + player.hp + "    Score: 0");
                    break;
                case "EmptyPack" :
                    displayGrid.removeObjectFromDisplay(player.posX, player.posY);
                    for (Item item : pack) {
                        item_map[player.posX][player.posY].add(item);        
                        displayGrid.addObjectToDisplay(new Char(pack.get(0).t), player.posX, player.posY);
                        pack.remove(item);
                    }
                    displayGrid.addObjectToDisplay(new Char('@'), player.posX, player.posY);
                    break;
                case "BlessArmor" :
                case "BlessSword" :
                case "CurseArmor" :
                case "CurseSword" :
                    if (action.c == 'a') {
                        if (player.armor != null) {
                            player.armor.intval += action.v;
                            displayGrid.displayInfo("Armor cursed! " + Integer.toString(action.v) + " taken from its effectiveness");
                        } else {
                            displayGrid.displayInfo("Scroll of cursing does nothing because armor is not worn");
                        }
                    } else {
                        if (player.sword != null) {
                            player.sword.intval += action.v;
                            displayGrid.displayInfo("Sword cursed! " + Integer.toString(action.v) + " taken from its effectiveness");
                        } else {
                            displayGrid.displayInfo("Scroll of cursing does nothing because sword is not equipped");
                        }
                    }
                    break;
                case "Hallucinate" :
                    hmoves = action.v;
                    hallu = true;
                    break;
                default: 
                    break;
            }
            if (action.msg != null) displayGrid.displayInfo(action.msg);
            if (action.name.equals("Hallucinate")) {
                displayGrid.displayString(7 + action.msg.length(), displayGrid.getGameHeight()-1, " Hallucination will last " + Integer.toString(action.v));
            }
        }
    }
    public void checkhallu() {
        if (hallu) {
            if (hmoves > 0) {
                displayGrid.hallucinate();
            } else {
                displayGrid.restore();
                hallu = false;
            }
            hmoves = (hmoves > 0) ? hmoves - 1: 0;
        }
    }
}