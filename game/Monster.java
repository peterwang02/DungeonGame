package game;

public class Monster extends Creature {
    String name;
    int room;
    int serial;

    public Monster() {
        System.out.println("Monster: Created new monster");
    }

    public void setName(String _name) {
        name = _name;
        System.out.println("Monster: setting Name to " + name);
    }

    public void setID(int _room, int _serial) {
        room = _room;
        serial = _serial;

        System.out.println("Monster: setting room to " + Integer.toString(_room) + ", setting serial id to " + Integer.toString(_serial));
    }

    public String getName() {
        return name;
    }

    public int getRoom() {
        return room;
    }

    public int getSerial() {
        return serial;
    }
}
