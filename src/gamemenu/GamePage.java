/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamemenu;

import cat_element.Game;
import java.awt.Graphics;

/**
 *
 * @author Rich
 */
public abstract class GamePage {
    protected Game game;

    public GamePage(Game game) {
        this.game = game;
    }
    
    abstract public void update();
    
    abstract public void render(Graphics g);
    
    
}
