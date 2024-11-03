/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Input;

import Constantes.Constants.GameMenu;
import cat_element.Game;
import cat_element.GamePanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.*;

/**
 *
 * @author Rich
 */
public class ListenerMouse implements MouseMotionListener,MouseListener{
    private GamePanel gamepanel;
    public ListenerMouse(GamePanel gamepanel) {
        this.gamepanel = gamepanel;
    }
    

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(gamepanel.getGame().GameStatus==GameMenu.MENU){
            gamepanel.getGame().getMenu().mousex = e.getX();
            gamepanel.getGame().getMenu().mousey = e.getY();
        }else if(gamepanel.getGame().GameStatus==GameMenu.SELECTLEVEL){
            gamepanel.getGame().getSelectlevel().mousex = e.getX();
            gamepanel.getGame().getSelectlevel().mousey = e.getY();
        }else if(gamepanel.getGame().GameStatus==GameMenu.REPLAY){
            gamepanel.getGame().getReplay().mousex = e.getX();
            gamepanel.getGame().getReplay().mousey = e.getY();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(gamepanel.getGame().GameStatus==GameMenu.MENU||gamepanel.getGame().GameStatus==GameMenu.SELECTLEVEL||gamepanel.getGame().GameStatus==GameMenu.REPLAY){
            gamepanel.getGame().IsClick = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gamepanel.getGame().IsClick = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
}
