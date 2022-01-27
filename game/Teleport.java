package game;

public class Teleport extends CreatureAction {
    String name;
    Creature owner;

    public Teleport(String _name, Creature _owner) {
        super(_owner);
        name = _name;
        owner = _owner;

        System.out.println("Teleport: setting name to " + _name);
    }
}
