package game;

public class ChangeDisplayedType extends CreatureAction {
    String name;
    Creature owner;

    public ChangeDisplayedType(String _name, Creature _owner) {
        super(_owner);
        name = _name;
        owner = _owner;

        System.out.println("ChangedDisplayedType: setting name to " + _name);
    }
    
}
