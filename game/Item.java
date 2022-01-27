package game;

public class Item extends Displayable{
    Creature owner;
    String name;
    
    public void setOwner(Creature _owner) {
        owner = _owner;

        System.out.println("Item: setting Owner");
    }
    public Item(String _name) {
        name = _name;
        System.out.println("Item: setting name to " + _name);
    }
}
