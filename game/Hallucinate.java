package game;

public class Hallucinate extends ItemAction {
    Item owner;

    public Hallucinate(Item _owner) {
        super(_owner);
        owner = _owner;

        System.out.println("Hallucinate: Setting owner");
    }
}
