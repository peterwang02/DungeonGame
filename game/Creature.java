package game;
import java.util.*;

public class Creature extends Displayable {
    List<CreatureAction> deathAction;
    List<CreatureAction> hitAction;

    public Creature() {
        deathAction = new LinkedList<>();
        hitAction = new LinkedList<>();
        System.out.println("Creature: Created new creature");
    }

    public void setDeathAction(CreatureAction da) {
        deathAction.add(da);
        System.out.println("Creature: setting DeathAction");
    }

    public void setHitAction(CreatureAction ha) {
        hitAction.add(ha);
        System.out.println("Creature: setting HitAction");
    }
}
