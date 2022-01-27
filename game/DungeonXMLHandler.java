package game;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DungeonXMLHandler extends DefaultHandler {
    public enum DisplayType {
        ROOM, PASSAGE, MONSTER, PLAYER, SCROLL, SWORD, ARMOR
    }

    private DisplayType displayType = null; 
    
    private static final int DEBUG = 1;
    private static final String CLASSID = "DungeonXMLHandler";

    private StringBuilder data = null;

    private Dungeon dungeonBeingparsed = null;
    public ObjectDisplayGrid odgBeingParsed = null;
    private Room roomBeingParsed = null;
    private Passage passageBeingParsed = null;
    private Monster monsterBeingParsed = null;
    private Player playerBeingParsed = null;
    private Scroll scrollBeingParsed = null;
    private Sword swordBeingParsed = null;
    private Armor armorBeingParsed = null;
    private Action actionBeingParsed = null;

    private boolean inPlayer = false;
    private boolean bvisible = false;
    private boolean bposX = false;
    private boolean bposY = false;
    private boolean bwidth = false;
    private boolean bheight = false;
    private boolean btype = false;
    private boolean bhp = false;
    private boolean bhpMoves = false;
    private boolean bmaxhit = false;
    private boolean bitemintval = false;
    private boolean bactionintval = false;
    private boolean bactionmessage = false;
    private boolean bactioncharval = false;

    public int width;
    public int gameHeight;
    public int topHeight;
    public int bottomHeight;
    
    public DungeonXMLHandler() {

    }

    public Dungeon getDungeon() {
        return dungeonBeingparsed;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (DEBUG > 1) {
            System.out.println(CLASSID + ".startElement qName: " + qName);
        }

        if (qName.equalsIgnoreCase("Dungeon")) {

            String name = attributes.getValue("name");
            width = Integer.parseInt(attributes.getValue("width"));
            gameHeight = Integer.parseInt(attributes.getValue("gameHeight"));
            topHeight = Integer.parseInt(attributes.getValue("topHeight"));
            bottomHeight = Integer.parseInt(attributes.getValue("bottomHeight"));
            dungeonBeingparsed = new Dungeon();

            dungeonBeingparsed.getDungeon(name, width, gameHeight);
        } else if (qName.equalsIgnoreCase("Rooms")) {
        } else if (qName.equalsIgnoreCase("Room")) {
            int id = Integer.parseInt(attributes.getValue("room"));
            roomBeingParsed = new Room(Integer.toString(id));
            roomBeingParsed.setID(id);
            displayType = DisplayType.ROOM;
            inPlayer = false;
        } else if (qName.equalsIgnoreCase("Passages")){
        } else if (qName.equalsIgnoreCase("Passage")) {
            int room1 = Integer.parseInt(attributes.getValue("room1"));
            int room2 = Integer.parseInt(attributes.getValue("room2"));
            passageBeingParsed = new Passage();
            passageBeingParsed.setID(room1, room2);
            displayType = DisplayType.PASSAGE;
            inPlayer = false;
        } else if (qName.equalsIgnoreCase("Monsters")) {      
        } else if (qName.equalsIgnoreCase("Monster")) {
            monsterBeingParsed = new Monster();
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            monsterBeingParsed.setID(room, serial);
            monsterBeingParsed.setName(name);
            displayType = DisplayType.MONSTER;
        } else if (qName.equalsIgnoreCase("Player")) {
            playerBeingParsed = new Player();
            displayType = DisplayType.PLAYER;
            inPlayer = true;            
        } else if (qName.equalsIgnoreCase("Scroll")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            scrollBeingParsed = new Scroll(name);
            scrollBeingParsed.setID(room, serial);
            scrollBeingParsed.t = '?';
            displayType = DisplayType.SCROLL;
        } else if (qName.equalsIgnoreCase("Sword")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            swordBeingParsed = new Sword(name);
            swordBeingParsed.setID(room, serial);
            swordBeingParsed.t = ')';
            displayType = DisplayType.SWORD;
        } else if (qName.equalsIgnoreCase("armor")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            armorBeingParsed = new Armor(name);
            armorBeingParsed.setID(room, serial);
            armorBeingParsed.t = ']';
            displayType = DisplayType.ARMOR;
        } else if (qName.equalsIgnoreCase("CreatureAction") || qName.equalsIgnoreCase("ItemAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");

            switch (name) {
                case "ChangeDisplayedType" :
                    if (displayType == DisplayType.MONSTER) {
                        actionBeingParsed = new ChangeDisplayedType(type, monsterBeingParsed);
                    } else {
                        actionBeingParsed = new ChangeDisplayedType(type, playerBeingParsed);
                    }
                    break;
                case "DropPack" :
                    if (displayType == DisplayType.MONSTER) {
                        actionBeingParsed = new DropPack(type, monsterBeingParsed);
                    } else {
                        actionBeingParsed = new DropPack(type, playerBeingParsed);
                    }                    
                    break;
                case "EndGame" :
                    if (displayType == DisplayType.MONSTER) {
                        actionBeingParsed = new EndGame(type, monsterBeingParsed);
                    } else {
                        actionBeingParsed = new EndGame(type, playerBeingParsed);
                    }                    
                    break;
                case "Remove" :
                    if (displayType == DisplayType.MONSTER) {
                        actionBeingParsed = new Remove(type, monsterBeingParsed);
                    } else {
                        actionBeingParsed = new Remove(type, playerBeingParsed);
                    }                    
                    break;
                case "Teleport" :
                    if (displayType == DisplayType.MONSTER) {
                        actionBeingParsed = new Teleport(type, monsterBeingParsed);
                    } else {
                        actionBeingParsed = new Teleport(type, playerBeingParsed);
                    }                    
                    break;
                case "UpdateDisplay" :
                    if (displayType == DisplayType.MONSTER) {
                        actionBeingParsed = new UpdateDisplay(type, monsterBeingParsed);
                    } else {
                        actionBeingParsed = new UpdateDisplay(type, playerBeingParsed);
                    }                    
                    break;
                case "YouWin" :
                    if (displayType == DisplayType.MONSTER) {
                        actionBeingParsed = new YouWin(type, monsterBeingParsed);
                    } else {
                        actionBeingParsed = new YouWin(type, playerBeingParsed);
                    }                    
                    break;
                case "Hallucinate" :
                    if (displayType == DisplayType.ARMOR) {
                        actionBeingParsed = new Hallucinate(armorBeingParsed);
                    } else if (displayType == DisplayType.SCROLL) {
                        actionBeingParsed = new Hallucinate(scrollBeingParsed);
                    } else {
                        actionBeingParsed = new Hallucinate(swordBeingParsed);
                    }
                    break;
                case "BlessCurseOwner" :
                    if (displayType == DisplayType.ARMOR) {
                        actionBeingParsed = new BlessCurseOwner(armorBeingParsed);
                    } else if (displayType == DisplayType.SCROLL) {
                        actionBeingParsed = new BlessCurseOwner(scrollBeingParsed);
                    } else {
                        actionBeingParsed = new BlessCurseOwner(swordBeingParsed);
                    }
                    break;
                case "BlessArmor" :
                    if (displayType == DisplayType.ARMOR) {
                        actionBeingParsed = new BlessArmor(armorBeingParsed);
                    } else if (displayType == DisplayType.SCROLL) {
                        actionBeingParsed = new BlessArmor(scrollBeingParsed);
                    } else {
                        actionBeingParsed = new BlessArmor(swordBeingParsed);
                    }
                    break;
                default: 
                    System.out.println("Unknown action: " + name);
                    break;
            }
            actionBeingParsed.setName(name);
            if (type.equals("death")) {
                CreatureAction ca = (CreatureAction) actionBeingParsed;
                if (inPlayer) {
                    playerBeingParsed.setDeathAction(ca);
                } else {
                    monsterBeingParsed.setDeathAction(ca);
                }
            } else if (type.equals("hit")) {
                CreatureAction ca = (CreatureAction) actionBeingParsed;
                if (inPlayer) {
                    playerBeingParsed.setHitAction(ca);
                } else {
                    monsterBeingParsed.setHitAction(ca);
                }
            }
            if (qName.equalsIgnoreCase("ItemAction")) {
                scrollBeingParsed.actions.add((ItemAction)actionBeingParsed);
            }
        } else if (qName.equalsIgnoreCase("Visible")) {
            bvisible = true;
        } else if (qName.equalsIgnoreCase("posX")) {
            bposX = true;
        } else if (qName.equalsIgnoreCase("posY")) {
            bposY = true;
        } else if (qName.equalsIgnoreCase("width")) {
            bwidth = true;
        } else if (qName.equalsIgnoreCase("height")) {
            bheight = true;
        } else if (qName.equalsIgnoreCase("type")) {
            btype = true;
        } else if (qName.equalsIgnoreCase("hp")) {
            bhp = true;
        } else if (qName.equalsIgnoreCase("maxhit")) {
            bmaxhit = true;
        } else if (qName.equalsIgnoreCase("hpMoves")) {
            bhpMoves = true;
        } else if (qName.equalsIgnoreCase("ItemIntValue")) {
            bitemintval = true;
        } else if (qName.equalsIgnoreCase("actionIntValue")) {
            bactionintval = true;
        } else if (qName.equalsIgnoreCase("actionMessage")) {
            bactionmessage = true;
        } else if (qName.equalsIgnoreCase("actionCharValue")) {
            bactioncharval = true;
        } else {
            System.out.println("Unknown qname: " + qName);
        }

        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bvisible) {
            if (Integer.parseInt(data.toString()) == 1) {
                if (displayType == DisplayType.ROOM) {
                    roomBeingParsed.setVisible();
                } else if (displayType == DisplayType.PASSAGE) {
                    passageBeingParsed.setVisible();
                } else if (displayType == DisplayType.MONSTER) {
                    monsterBeingParsed.setVisible();
                } else if (displayType == DisplayType.PLAYER) {
                    playerBeingParsed.setVisible();
                } else if (displayType == DisplayType.SCROLL) {
                    scrollBeingParsed.setVisible();
                } else if (displayType == DisplayType.SWORD) {
                    swordBeingParsed.setVisible();
                } else if (displayType == DisplayType.ARMOR) {
                    armorBeingParsed.setVisible();
                }
            } else {
                if (displayType == DisplayType.ROOM) {
                    roomBeingParsed.setInvisible();
                } else if (displayType == DisplayType.PASSAGE) {
                    passageBeingParsed.setInvisible();
                } else if (displayType == DisplayType.MONSTER) {
                    monsterBeingParsed.setInvisible();
                } else if (displayType == DisplayType.PLAYER) {
                    playerBeingParsed.setInvisible();
                } else if (displayType == DisplayType.SCROLL) {
                    scrollBeingParsed.setInvisible();
                } else if (displayType == DisplayType.SWORD) {
                    swordBeingParsed.setInvisible();
                } else if (displayType == DisplayType.ARMOR) {
                    armorBeingParsed.setInvisible();
                }
            }   
            bvisible = false;
        } else if (bposX) {
            int x = Integer.parseInt(data.toString());
            if (displayType == DisplayType.ROOM) {
                roomBeingParsed.setPosX(x);
            } else if (displayType == DisplayType.PASSAGE) {
                passageBeingParsed.setPosX(x);
            } else if (displayType == DisplayType.MONSTER) {
                monsterBeingParsed.setPosX(x + roomBeingParsed.posX);
            } else if (displayType == DisplayType.PLAYER) {
                playerBeingParsed.setPosX(x + roomBeingParsed.posX);
            } else if (displayType == DisplayType.SCROLL) {
                scrollBeingParsed.setPosX(x + roomBeingParsed.posX);
            } else if (displayType == DisplayType.SWORD) {
                swordBeingParsed.setPosX(x + roomBeingParsed.posX);
            } else if (displayType == DisplayType.ARMOR) {
                armorBeingParsed.setPosX(x + roomBeingParsed.posX);
            }            
            bposX = false;
        } else if (bposY) {
            int x = Integer.parseInt(data.toString());
            if (displayType == DisplayType.ROOM) {
                roomBeingParsed.setPosY(x + topHeight);
            } else if (displayType == DisplayType.PASSAGE) {
                passageBeingParsed.setPosY(x + topHeight);
            } else if (displayType == DisplayType.MONSTER) {
                monsterBeingParsed.setPosY(x + roomBeingParsed.posY);
            } else if (displayType == DisplayType.PLAYER) {
                playerBeingParsed.setPosY(x + roomBeingParsed.posY);
            } else if (displayType == DisplayType.SCROLL) {
                scrollBeingParsed.setPosY(x + roomBeingParsed.posY);
            } else if (displayType == DisplayType.SWORD) {
                swordBeingParsed.setPosY(x + roomBeingParsed.posY);
            } else if (displayType == DisplayType.ARMOR) {
                armorBeingParsed.setPosY(x + roomBeingParsed.posY);
            }                
            bposY = false;
        } else if (bwidth) {
            int x = Integer.parseInt(data.toString());
            if (displayType == DisplayType.ROOM) {
                roomBeingParsed.setWidth(x);
            } else if (displayType == DisplayType.PASSAGE) {
                passageBeingParsed.setWidth(x);
            } 
            bwidth = false;
        } else if (bheight) {
            int x = Integer.parseInt(data.toString());
            if (displayType == DisplayType.ROOM) {
                roomBeingParsed.setHeight(x);
            } else if (displayType == DisplayType.PASSAGE) {
                passageBeingParsed.setHeight(x);
            }
            bheight = false;
        } else if (btype) {
            String type = data.toString();
            char t = type.charAt(0);
            monsterBeingParsed.setType(t);
            btype = false;
        } else if (bhp) {
            int x = Integer.parseInt(data.toString());
            if (displayType == DisplayType.MONSTER) {
                monsterBeingParsed.setHp(x);
            } else if (displayType == DisplayType.PLAYER) {
                playerBeingParsed.setHp(x);
            }
            bhp = false;
        } else if (bmaxhit) {
            int x = Integer.parseInt(data.toString());
            if (displayType == DisplayType.MONSTER) {
                monsterBeingParsed.setMaxHit(x);
            } else if (displayType == DisplayType.PLAYER) {
                playerBeingParsed.setMaxHit(x);
            }
            bmaxhit = false;
        } else if (bhpMoves) {
            int x = Integer.parseInt(data.toString());
            if (displayType == DisplayType.MONSTER) {
                monsterBeingParsed.setHpMoves(x);
            } else if (displayType == DisplayType.PLAYER) {
                playerBeingParsed.setHpMoves(x);
            }
            bhpMoves = false;
        } else if (bitemintval) {
            int x = Integer.parseInt(data.toString());
            System.out.println("here" + data.toString());
            if (displayType == DisplayType.SCROLL) {
                scrollBeingParsed.setIntValue(x);
            } else if (displayType == DisplayType.SWORD) {
                swordBeingParsed.setIntValue(x);
            } else if (displayType == DisplayType.ARMOR) {
                armorBeingParsed.setIntValue(x);
            }  
            bitemintval = false;
        } else if (bactionintval) {
            int x = Integer.parseInt(data.toString());
            actionBeingParsed.setIntValue(x);
            bactionintval = false;
        } else if (bactionmessage) {
            String message = data.toString();
            actionBeingParsed.setMessage(message);
            bactionmessage = false;
        } else if (bactioncharval) {
            String foo = data.toString();
            char val = foo.charAt(0);
            actionBeingParsed.setCharValue(val);
            bactioncharval = false;
        }
    
        if (qName.equalsIgnoreCase("Dungeon")) {
        } else if (qName.equalsIgnoreCase("Rooms") || qName.equalsIgnoreCase("Passages")) {
        } else if (qName.equalsIgnoreCase("Room")) {
            dungeonBeingparsed.addRoom(roomBeingParsed);
            roomBeingParsed = null;
            displayType = null;
        } else if (qName.equalsIgnoreCase("Passage")) {
            dungeonBeingparsed.addPassage(passageBeingParsed);
            passageBeingParsed = null;
            displayType = null;
        } else if (qName.equalsIgnoreCase("Monster")) {
            roomBeingParsed.setCreature(monsterBeingParsed);
            dungeonBeingparsed.addCreature(monsterBeingParsed);
            monsterBeingParsed = null;
            displayType = null;
        } else if (qName.equalsIgnoreCase("Player")) {
            roomBeingParsed.setCreature(playerBeingParsed);
            dungeonBeingparsed.addCreature(playerBeingParsed);
            playerBeingParsed = null;
            displayType = null;           
            inPlayer = false; 
        } 
        else if (qName.equalsIgnoreCase("Scroll")) {
            if (inPlayer) {
                scrollBeingParsed.setOwner(playerBeingParsed);
                playerBeingParsed.pack.add(scrollBeingParsed);
            }
            dungeonBeingparsed.addItem(scrollBeingParsed);
            scrollBeingParsed = null;
            displayType = null;
        } else if (qName.equalsIgnoreCase("Sword")) {
            if (inPlayer) {
                swordBeingParsed.setOwner(playerBeingParsed);
                playerBeingParsed.pack.add(swordBeingParsed);
            }
            dungeonBeingparsed.addItem(swordBeingParsed);
            swordBeingParsed = null;
            displayType = null;
        } else if (qName.equalsIgnoreCase("Armor")) {
            if (inPlayer) {
                armorBeingParsed.setOwner(playerBeingParsed);
                playerBeingParsed.pack.add(armorBeingParsed);
            }
            dungeonBeingparsed.addItem(armorBeingParsed);
            armorBeingParsed = null;
            displayType = null;
        } else if (qName.equalsIgnoreCase("CreatureAction") || qName.equalsIgnoreCase("ItemAction")) {
            actionBeingParsed = null;
        }
    }


    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
        if (DEBUG > 1) {
            System.out.println(CLASSID + ".characters: " + new String(ch, start, length));
            System.out.flush();
        }
    }
}
