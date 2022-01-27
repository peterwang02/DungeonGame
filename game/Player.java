package game;
import java.util.*;

public class Player extends Creature {
    public Item sword = null;
    public Item armor = null;
    public List<Item> pack = new ArrayList<Item>();


    public void setWeapon(Item _sword) {
        sword = _sword;

        System.out.println("Player: setting Weapon ");
    }

    public void setArmor(Item _armor) {
        armor = _armor;

        System.out.println("Player: setting Armor ");
    } 

}
