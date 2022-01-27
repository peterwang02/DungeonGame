package game;

import java.util.ArrayList;

public class Dungeon {
    ArrayList<Room> rooms = new ArrayList<Room>();
    ArrayList<Creature> creatures = new ArrayList<Creature>();
    ArrayList<Passage> passages = new ArrayList<Passage>();
    ArrayList<Item> items = new ArrayList<Item>();
    int width;
    int gameHeight;
    String name;

    public void getDungeon(String _name, int _width, int _gameHeight) {
        name = _name;
        width = _width;
        gameHeight = _gameHeight;
        System.out.println("Dungeon: setting dungeon with name " + _name + ", width " + Integer.toString(_width) + ", gameHeight " + Integer.toString(_gameHeight));
    }

    public void addRoom(Room room) {
        rooms.add(room);
        System.out.println("Dungeon: adding room " + room.getID());
    }

    public void addCreature(Creature creature) {
        creatures.add(creature);
        System.out.println("Dungeon: adding Creature");
    }

    public void addPassage(Passage passage) {
        passages.add(passage);
        System.out.println("Dungeon: adding passage to connect " + passage.getRoom1() + " " + passage.getRoom2());
    }

    public void addItem(Item item) {
        items.add(item);
        System.out.println("Dungeon: adding Item to dungeon");
    }

}