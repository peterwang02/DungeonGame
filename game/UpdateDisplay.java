package game;

public class UpdateDisplay extends CreatureAction {
    String name;
    Creature owner;

    public UpdateDisplay(String _name, Creature _owner) {
        super(_owner);
        name = _name;
        owner = _owner;

        System.out.println("UpdateDisplay: setting name to " + _name);
    }
}
