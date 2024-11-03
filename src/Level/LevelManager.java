/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Level;

import Constantes.Constants;
import Entities.Player;
import cat_element.Game;
import static cat_element.Game.til_HEIGHT;
import static cat_element.Game.til_WIDTH;
import static cat_element.Game.til_size;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Rich
 */
public class LevelManager implements Runnable{
    private Game game;
    private BufferedImage[] levelSprit;
    private Level level;
    private BufferedImage[] background;
    private BufferedImage ObjKey;
    private Rectangle Door;
    private BufferedImage[] doorimg;
    private BufferedImage darkdoor;
    
    public ArrayList<Rectangle> keyrect = new ArrayList<Rectangle>();
    public ArrayList<Rectangle> DoorDarkrect = new ArrayList<Rectangle>();
    public ArrayList<Rectangle> posentity = new ArrayList<Rectangle>();
    public ArrayList<Integer> entitymode = new ArrayList<Integer>();
    
    private boolean start = true;
    
    private Thread thread;
    
    public LevelManager(Game game,int[][] lvl){
        this.game = game;
        importImage();
        level = new Level(LevelState.getlvl(lvl));
        addObj();
        thread = new Thread(this);
        thread.start();
        
    }
    
    public void importImage(){
        levelSprit = new BufferedImage[8];
        background = new BufferedImage[2];
        doorimg = new BufferedImage[2];
        InputStream is = getClass().getResourceAsStream("/Res/Dirt.png");
        try{
            levelSprit[0] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/wood.png");
            levelSprit[1] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/water.png");
            levelSprit[2] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/waterdown.png");
            levelSprit[3] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/icewater.png");
            levelSprit[4] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/icedown.png");
            levelSprit[5] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/darkarea1.png");
            levelSprit[6] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/darkarea2.png");
            levelSprit[7] = ImageIO.read(is);
            
            is = getClass().getResourceAsStream("/Res/1.png");
            background[0] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/5.png");
            background[1] = ImageIO.read(is);
            
            is = getClass().getResourceAsStream("/Res/obj/Key.png");
            ObjKey = ImageIO.read(is);
            
            is = getClass().getResourceAsStream("/Res/lock.png");
            doorimg[0] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/open.png");
            doorimg[1] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/darkdoor.png");
            darkdoor = ImageIO.read(is);
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void addObj(){
        for(int j =0;j< level.getHeight();j++){
            for(int i=0;i<level.getWidth(j);i++){
                if(level.getSpriteIndex(i, j)==10){
                    keyrect.add(new Rectangle(i*til_size, j*til_size,til_size,til_size));
                    level.setlvlData(i, j, 0);
                }else if(level.getSpriteIndex(i, j)==98){
                    Door = new Rectangle(i*til_size, j*til_size,til_size,til_size);
                }else if(level.getSpriteIndex(i, j)==61){
                    DoorDarkrect.add(new Rectangle(i, j,til_size,til_size));
                }else if(level.getSpriteIndex(i, j)==60){
                    entitymode.add(60);
                    posentity.add(new Rectangle(i*til_size, j*til_size,80,80));
                    level.setlvlData(i, j, 0);
                }else if(level.getSpriteIndex(i, j)==62){
                    entitymode.add(62);
                    posentity.add(new Rectangle(i*til_size, j*til_size,80,80));
                    level.setlvlData(i, j, 0);
                }
            }
        }
    }
    
    public void draw(Graphics g,int xlvloffset){
        Player player = game.getPlaying().getPlayer();
        g.drawImage(background[0], 0,0,til_WIDTH, til_HEIGHT, null);
        g.drawImage(background[1],0, 0,til_WIDTH, til_HEIGHT, null);
        
        for(int i=0;i<keyrect.size();i++){
            g.drawImage(ObjKey, keyrect.get(i).x-xlvloffset, keyrect.get(i).y, keyrect.get(i).width,keyrect.get(i).height, null);
            //g.drawRect(keyrect.get(i).x-xlvloffset, keyrect.get(i).y, keyrect.get(i).width,keyrect.get(i).height);
        }
        
        if(player.getKeynow()>=player.getKey()){
            g.drawImage(doorimg[1], Door.x-xlvloffset, Door.y, Door.width,Door.height, null);
            //g.drawRect(Door.x-xlvloffset, Door.y, Door.width,Door.height);
        }else{
            g.drawImage(doorimg[0], Door.x-xlvloffset, Door.y, Door.width,Door.height, null);
            //g.drawRect(Door.x-xlvloffset, Door.y, Door.width,Door.height);
        }
        int xstart=player.getHitbox().x/til_size-25;
        int xend = player.getHitbox().x/til_size+25;
        if(xstart<0){
            xstart=0;
        }
        for(int j =0;j< level.getHeight();j++){
            if(xend>level.getWidth(j)){
                xend = level.getWidth(j);
            }
            for(int i=xstart;i<xend;i++){
                if(level.getSpriteIndex(i, j)!=0&&level.getSpriteIndex(i, j)!=10&&level.getSpriteIndex(i, j)!=98
                        &&level.getSpriteIndex(i, j)!=61&&level.getSpriteIndex(i, j)!=60){
                    if(game.getPlaying().getPlayer().mode==3){
                        if(level.getSpriteIndex(i, j)==7){
                            level.setlvlData(i, j, 8);
                        }
                    }else{
                        if(level.getSpriteIndex(i, j)==8){
                            level.setlvlData(i, j, 7);
                        }
                    }
                    g.drawImage(levelSprit[level.getSpriteIndex(i, j)-1], i*til_size-xlvloffset, j*til_size,til_size,til_size, null);
                }else if(level.getSpriteIndex(i, j)==61){
                    g.drawImage(darkdoor, i*til_size-xlvloffset, j*til_size,til_size,til_size, null);
                }
            }
        }
        
        g.drawImage(ObjKey, 1100, 50, 50,50, null);
        g.setFont(new Font("", Font.BOLD, 50));
        g.setColor(Color.BLACK);
        g.drawString(game.getPlaying().getPlayer().getKeynow()+"/"+game.getPlaying().getPlayer().getKey(), 1150, 100);
    }
    
    public void update(){
        try {
            Player player = game.getPlaying().getPlayer();
        for(int i=0;i<keyrect.size();i++){
            if(player.getHitbox().intersects(keyrect.get(i))){
                keyrect.remove(i);
                player.addKeynow();
                i=0;
            }
        }
        if(game.getPlaying().getEntity().size()<=0){
            for(int i=0;i<DoorDarkrect.size();i++){
                if(level.getSpriteIndex(DoorDarkrect.get(i).x, DoorDarkrect.get(i).y)!=0){
                    level.setlvlData(DoorDarkrect.get(i).x, DoorDarkrect.get(i).y, 0);
                }
            }
        }else{
            for(int i=0;i<DoorDarkrect.size();i++){
                if(level.getSpriteIndex(DoorDarkrect.get(i).x, DoorDarkrect.get(i).y)!=61){
                    level.setlvlData(DoorDarkrect.get(i).x, DoorDarkrect.get(i).y, 61);
                }
            }
        }
        if(player.getKeynow()>=player.getKey()){
            if(player.getHitbox().intersects(Door)){
                game.getPlaying().setTimesStop();
                game.getReplay().setShowstring("You win!");
                game.GameStatus = Constants.GameMenu.REPLAY;
            }
        }
        } catch (Exception e) {
        }
    }
    @Override
    public void run() {
        double timeper = 1000000000.0/60;
        long last = System.nanoTime();
        double dela = 0;
        while(start){
            long curr = System.nanoTime();
            dela += (curr-last)/timeper;
            last = curr;
            if(dela>=1){
                update();
                dela--;
            }
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
    }
    
    public Level getCurrentLevel(){
        return level;
    }

    public ArrayList<Rectangle> getPosentity() {
        return posentity;
    }
    
    public void setThread() {
        start = false;
    }
    
}
