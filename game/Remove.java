package game;

public class Remove extends CreatureAction {
    String name;
    Creature owner;

    public Remove(String _name, Creature _owner) {
        super(_owner);
        name = _name;
        owner = _owner;

        System.out.println("Remove: setting name to " + _name);
    }
}
