package game;

public class Sword extends Item {
    int room;
    int serial;

    public Sword(String _name) {
        super(_name);
    }

    public void setID(int _room, int _serial) {
        room = _room;
        serial = _serial;

        System.out.println("Sword: setting room to " + Integer.toString(_room) + ", serial to " + Integer.toString(_serial));
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
