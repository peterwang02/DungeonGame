package game;

public class BlessCurseOwner extends ItemAction {
    Item owner;

    public BlessCurseOwner(Item _owner) {
        super(_owner);
        owner = _owner;

        System.out.println("BlessCurseOwner: Setting owner");
    }
}
