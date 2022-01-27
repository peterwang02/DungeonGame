package game;

public class Displayable {
    int maxHit, hpMoves, hp, intval, posX, posY, width, height;
    char t;

    public Displayable() {
        System.out.println("Displayable: Created new Displayable");
    }

    public void setInvisible() {
        System.out.println("Displayable: setting Invisible");
    }

    public void setVisible() {
        System.out.println("Displayable: setting Visible");
    }

    public void setMaxHit(int _maxHit) {
        maxHit = _maxHit;
        System.out.println("Displayable: setting MaxHit to " + Integer.toString(maxHit));
    }

    public void setHpMoves(int _hpMoves) {
        hpMoves = _hpMoves;
        System.out.println("Displayable: setting HpMove to " + Integer.toString(hpMoves));
    }
    public void setHp(int _hp) {
        hp = _hp;
        // System.out.println("Displayable: setting Hp to " + Integer.toString(hp));        
    }
    public void setType(char _t) {
        t = _t;
        System.out.println("Displayable: setting Type to " + Character.toString(_t));
    }

    public void setIntValue(int v) {
        intval = v;
        System.out.println("Displayable: setting IntValue " + Integer.toString(v));
    }

    public void setPosX(int x) {
        posX = x;
        System.out.println("Displayable: setting PosX to " + Integer.toString(x));
    }

    public void setPosY(int y) {
        posY = y;
        System.out.println("Displayable: setting PosY to " + Integer.toString(y));
    }

    public void setWidth(int x) {
        width = x;
        System.out.println("Displayable: setting Width to " + Integer.toString(x));
    }

    public void setHeight(int y) {
        height = y;
        System.out.println("Displayable: setting Height to " + Integer.toString(y));
    }
}
