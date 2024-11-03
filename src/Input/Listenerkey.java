/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Input;

import Constantes.Constants;
import Constantes.Constants.GameMenu;
import cat_element.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Rich
 */
public class Listenerkey implements KeyListener{
    private GamePanel gamePanel;

    public Listenerkey(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gamePanel.getGame().GameStatus == Constants.GameMenu.PLAY) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                gamePanel.getGame().getPlaying().getPlayer().up = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                gamePanel.getGame().getPlaying().getPlayer().left = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                gamePanel.getGame().getPlaying().getPlayer().down = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                gamePanel.getGame().getPlaying().getPlayer().right = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                gamePanel.getGame().getPlaying().getPlayer().useability = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_1) {
                gamePanel.getGame().getPlaying().getPlayer().mode = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_2) {
                gamePanel.getGame().getPlaying().getPlayer().mode = 1;
            }
            if (e.getKeyCode() == KeyEvent.VK_3) {
                gamePanel.getGame().getPlaying().getPlayer().mode = 2;
            }
            if (e.getKeyCode() == KeyEvent.VK_4) {
                gamePanel.getGame().getPlaying().getPlayer().mode = 3;
            }
            
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                gamePanel.getGame().getPlaying().setTimesStop();
                gamePanel.getGame().GameStatus=GameMenu.MENU;
                gamePanel.getGame().getPlaying();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W){
            gamePanel.getGame().getPlaying().getPlayer().up=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_A){
            gamePanel.getGame().getPlaying().getPlayer().left=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_S){
            gamePanel.getGame().getPlaying().getPlayer().down=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_D){
            gamePanel.getGame().getPlaying().getPlayer().right=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            gamePanel.getGame().getPlaying().getPlayer().useability=false;
        }
    }
    
}
