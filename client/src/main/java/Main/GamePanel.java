package Main;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final Game game;
    public GamePanel (Game game) {
        this.game = game;
        setPanelSize();
        MouseInputs mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseMotionListener(mouseInputs);
        addMouseListener(mouseInputs);
    }


    public Game getGame () {
        return this.game;
    }

    public  void setPanelSize () {
        Dimension size = new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);
        setPreferredSize(size);
    }


    public void draw (Graphics g) {
        this.game.draw(g);
    }


    public void update () {
    }


    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        this.draw(g);
    }
}
