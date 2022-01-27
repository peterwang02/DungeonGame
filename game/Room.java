package game;

import java.util.ArrayList;

public class Room extends Structure {
    int id;
    String name;
    ArrayList<Creature> creatures = new ArrayList<Creature>();

    public Room() {
        System.out.println("Room: Creating new room");
    }

    public Room(String room) {
        name = room;
        System.out.println("Room: setting room name to " + room);
    }

    public void setID(int room) {
        id = room;
        System.out.println("Room: setting room id to " + Integer.toString(room));
    }

    public void setCreature(Creature Monster) {
        creatures.add(Monster);
        System.out.println("Room: adding creature to " + Integer.toString(id));
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }
}
