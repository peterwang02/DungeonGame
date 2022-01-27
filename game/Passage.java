package game;

import java.util.ArrayList;


public class Passage extends Structure {
    int room1;
    int room2;
    ArrayList<Integer> xList = new ArrayList<Integer>();
    ArrayList<Integer> yList = new ArrayList<Integer>();

    public void Passage() {
        System.out.println("Passage: Creating new passage");
    }

    public void setName(String name) {
        System.out.println("Passage: setName");
    }

    public void setID(int _room1, int _room2) {
        room1 = _room1;
        room2 = _room2;
        System.out.println("Passage: setting ID to " + Integer.toString(_room1) + " " + Integer.toString(_room2));
    }

    public void setPosX(int x){
        System.out.println("Adding posx" + Integer.toString(x) + "to passage");
        xList.add(x);
    }

    public void setPosY(int y){
        System.out.println("Adding posy" + Integer.toString(y) + "to passage");

        yList.add(y);
    }

    public int getRoom1() {
        return room1;
    }

    public int getRoom2() {
        return room2;
    }
}
