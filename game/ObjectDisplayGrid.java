package game;
import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;

public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject {
    int gameHeight;
    int topHeight;
    int bottomHeight;
    //int width;
    private static final int DEBUG = 0;
    private static final String CLASSID = ".ObjectDisplayGrid";

    private static AsciiPanel terminal;
    private Stack<Char>[][] objectGrid = null;

    private List<InputObserver> inputObservers = null;

    private static int height;
    private static int width;
    private String displayChars = "X.#+])?@TSH";


    public int getGameHeight() {
        return height;
    }

    public int getGameWidth() {
        return width;
    }

    public void setTopMessageHeight(int _topHeight) {
        System.out.println("ObjectDisplayGrid: setTopMessageHeight");
        topHeight = _topHeight;
    }

    public void setbottomHeight(int _bottomHeight) {
        System.out.println("ObjectDisplayGrid: setting bottomHeight to " + Integer.toString(_bottomHeight));
        bottomHeight = _bottomHeight;
    }

    public void displayString(int x, int y, String msg){
        for(int i=0; i < msg.length(); i++){
            addObjectToDisplay(new Char(msg.charAt(i)), x + i, y);
        }

        for(int i = msg.length(); i < width; i++){
            addObjectToDisplay(new Char(' '), x + i, y);
        }
    }

    public Stack<Char>[][] getObjectGrid() {
        return objectGrid;
    }

    public void displayInfo(String msg) {
        displayString(7, height - 1, msg);
    }

    public void clear_string(int y){
        for(int i = 0; i < width; i ++){
            addObjectToDisplay(new Char(' '), i, y);
        }
    }

    public ObjectDisplayGrid(int _width, int _height) {
        width = _width;
        height = _height;

        terminal = new AsciiPanel(width, height);

        objectGrid =  new Stack[width][height];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                objectGrid[i][j] = new Stack<Char>();
            }
        }

        initializeDisplay();

        super.add(terminal);
        super.setSize(width * 9 + 14, height * 16 + 37);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // super.repaint();
        // terminal.repaint( );
        super.setVisible(true);
        terminal.setVisible(true);
        super.addKeyListener(this);
        inputObservers = new ArrayList<>();
        super.repaint();
    }

    @Override
    public void registerInputObserver(InputObserver observer) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        }
        inputObservers.add(observer);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        }
        KeyEvent keypress = (KeyEvent) e;
        notifyInputObservers(keypress.getKeyChar());
    }

    private void notifyInputObservers(char ch) {
        for (InputObserver observer : inputObservers) {
            observer.observerUpdate(ch);
            if (DEBUG > 0) {
                System.out.println(CLASSID + ".notifyInputObserver " + ch);
            }
        }
    }

    // we have to override, but we don't use this
    @Override
    public void keyPressed(KeyEvent even) {
    }

    // we have to override, but we don't use this
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public final void initializeDisplay() {
        Char ch = new Char(' ');
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                addObjectToDisplay(ch, i, j);
            }
        }
        terminal.repaint();
    }

    public void addObjectToDisplay(Char ch, int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y].push(ch);
                writeToTerminal(x, y);
            }
        }
    }

    public void removeObjectFromDisplay( int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y].pop();
                if (!objectGrid[x][y].isEmpty()) {
                    writeToTerminal(x, y);
                }
            }
        }
    }

    private void writeToTerminal(int x, int y) {
        char ch = objectGrid[x][y].peek().getChar();
        terminal.write(ch, x, y);
        terminal.repaint();
    }

    public void hallucinate() {
        Random rand = new Random();
        for (int i = 0; i < objectGrid[0].length; i++) {
            for (int j = topHeight; j < gameHeight; j++) {
                if (displayChars.contains(""+objectGrid[i][j].peek().getChar())) {
                    terminal.write(displayChars.charAt(rand.nextInt(11)), i, j);
                }
            }
        }
    }

    public void restore() {
        for (int i = 0; i < objectGrid[0].length; i++) {
            for (int j = topHeight; j < gameHeight; j++) {
                terminal.write(objectGrid[i][j].peek().getChar(), i, j);
            }
        }        
    }

    public void refresh(){
        terminal.repaint();
    }
}
