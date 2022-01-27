package game;

public class Action {
    String msg;
    int v;
    char c;
    String name;
    public void setName(String _name) {
        name = _name;
    }

    public void setMessage(String _msg) {
        msg = _msg;
        System.out.println("Action: setMessage to " + msg);
    }

    public void setIntValue(int _v) {
        v = _v;
        System.out.println("Action: setIntvalue to ");
    }

    public void setCharValue(char _c) {
        c = _c;
        System.out.println("Action: setCharValue");
    }
}
