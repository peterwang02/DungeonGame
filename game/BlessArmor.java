package game;

public class BlessArmor extends ItemAction {
    Item owner;

    public BlessArmor(Item _owner) {
        super(_owner);
        owner = _owner;

        System.out.println("BlessArmor: Setting owner");
    }    
}
