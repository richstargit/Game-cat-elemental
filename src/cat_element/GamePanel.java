/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat_element;

import Entities.Player;
import Input.ListenerMouse;
import Input.Listenerkey;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Rich
 */
public class GamePanel extends JPanel{
    private Game game;
    public GamePanel(Game game){
        this.game = game;
        setPreferredSize(new Dimension(Game.til_WIDTH,Game.til_HEIGHT));
        addMouseMotionListener(new ListenerMouse(this));
        addMouseListener(new ListenerMouse(this));
        addKeyListener(new Listenerkey(this));
        setFocusable(true);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);
    }
    
    public Game getGame(){
        return game;
    }
}
