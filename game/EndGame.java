package game;

public class EndGame extends CreatureAction {
    String name;
    Creature owner;

    public EndGame(String _name, Creature _owner) {
        super(_owner);
        name = _name;
        owner = _owner;
        
        // System.out.println("EndGame: setting name to " + _name);
    }
    
}
