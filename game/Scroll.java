package game;
import java.util.*;

public class Scroll extends Item {
    int room;
    int serial;
    List<ItemAction> actions;

    public Scroll(String _name) {
        super(_name);
        actions = new LinkedList<>();
    }
    public void setID(int _room, int _serial) {
        room = _room;
        serial = _serial;

        System.out.println("Scroll: setting room to " + Integer.toString(_room) + ", serial to " + Integer.toString(_serial));
    }

    public void setAction(ItemAction _action) {
        actions.add(_action);
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
