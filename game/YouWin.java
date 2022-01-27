package game;

public class YouWin extends CreatureAction {
    String name;
    Creature owner;

    public YouWin(String _name, Creature _owner) {
        super(_owner);
        name = _name;
        owner = _owner;

        System.out.println("YouWin: setting name to " + _name);
    }
}
