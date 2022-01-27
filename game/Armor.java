package game;

public class Armor extends Item {
    int room;
    int serial;

    public Armor(String _name) {
        super(_name);
    }

    public void setName(String _name) {
        System.out.println("Armor: setName");
    }

    public void setID(int _room, int _serial) {
        room = _room;
        serial = _serial;

        System.out.println("Armor: setting room to " + Integer.toString(_room) + ", serial to " + Integer.toString(_serial));
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
