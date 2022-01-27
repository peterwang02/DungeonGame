package game;

public class DropPack extends CreatureAction {
    String name;
    Creature owner;

    public DropPack(String _name, Creature _owner) {
        super(_owner);
        name = _name;
        owner = _owner;

        System.out.println("DropPack: setting name to " + _name);
    }
    
}
