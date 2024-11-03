/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamemenu;

import Constantes.Constants.GameMenu;
import Entities.DarkEntity;
import Entities.DarkEntitySpeed;
import Entities.Player;
import Level.LevelManager;
import Level.LevelState;
import cat_element.Game;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *
 * @author Rich
 */
public class Playing extends GamePage implements Runnable{
    private Player player;
    private LevelManager levelManager;
    public int StartLevel;
    private int timeout;
    
    private int xlvloffset;
    private int leftBorder = (int)(0.5*Game.til_WIDTH);
    private int rightBorder = (int)(0.5*Game.til_WIDTH);
    private int maxlvlx;
    
    private ArrayList<DarkEntity> entity = new ArrayList<DarkEntity>();
    
    private Thread time;
    private boolean Istime;

    public Playing(Game game,int[][] level,int x,int y,int lvl,int health,int key) {
        super(game);
        StartLevel = lvl;
        initClass(game,level,x,y,health,key);
        timeout=180;
        Istime=true;
        time = new Thread(this);
        time.start();
    }
    
    
    
    private void initClass(Game game,int[][] level,int x,int y,int health,int key){
        levelManager = new LevelManager(game,level);
        maxlvlx = (levelManager.getCurrentLevel().getLvlData()[0].length-Game.WIDTH)*Game.til_size;
        player = new Player(x,y,80,60,health,key,entity);
        player.loadlvlData(levelManager.getCurrentLevel().getLvlData());
        for(int i=0;i<levelManager.getPosentity().size();i++){
            if(levelManager.entitymode.get(i)==60){
                entity.add(new DarkEntity(levelManager.getPosentity().get(i).x, levelManager.getPosentity().get(i).y, 80, 80,3,player,this));
                entity.get(i).loadlvlData(levelManager.getCurrentLevel().getLvlData());
            }else if(levelManager.entitymode.get(i)==62){
                entity.add(new DarkEntitySpeed(levelManager.getPosentity().get(i).x, levelManager.getPosentity().get(i).y, 80, 80,3,player,this));
                entity.get(i).loadlvlData(levelManager.getCurrentLevel().getLvlData());
            }
        }
    }
    
    @Override
    public void run() {
        while (Istime) {
            timeout--;
            if (timeout <= 0) {
                setTimesStop();
                game.getReplay().setShowstring("You lose");
                game.GameStatus = GameMenu.REPLAY;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
    
    @Override
    public void update(){
        checkCloseToBorder();
        if(player.getHealth()<=0){
            setTimesStop();
            game.getReplay().setShowstring("You lose");
            game.GameStatus = GameMenu.REPLAY;
        }
        
        for(int i=0;i<entity.size();i++){
            if(entity.get(i).getHealth()<=0){
                entity.get(i).setThread();
                entity.remove(i);
                i=0;
            }
        }
    }
    
    public void checkCloseToBorder(){
        int playerx = (int)player.getHitbox().x;
        int near = playerx - xlvloffset;
        if(near>rightBorder){
            xlvloffset+=near-rightBorder;
        }else if(near<leftBorder){
            xlvloffset += near-leftBorder;
        }
        
        if(xlvloffset>maxlvlx){
            xlvloffset = maxlvlx;
        }else if(xlvloffset<0){
            xlvloffset = 0;
        }
    }
    
    @Override
    public void render(Graphics g){
        levelManager.draw(g,xlvloffset);
        player.render(g,xlvloffset);
        for(int i=0;i<entity.size();i++){
            entity.get(i).render(g, xlvloffset);
        }
        g.setFont(new Font("",Font.BOLD,23));
        g.drawString("time left "+timeout, 25, 80);
    }
    
    public void windowlost(){
        player.resterpos();
    }
    
    public Player getPlayer(){
        return player;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setTimesStop() {
        Istime = false;
        player.setrun();
        for(int i=0;i<entity.size();i++){
            entity.get(i).setThread();
        }
        levelManager.setThread();
    }

    public int getXlvloffset() {
        return xlvloffset;
    }

    public ArrayList<DarkEntity> getEntity() {
        return entity;
    }
    
}
