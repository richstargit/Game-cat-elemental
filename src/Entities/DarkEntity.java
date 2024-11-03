/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Constantes.HelpMethods;
import static Constantes.HelpMethods.CanMoveHere;
import static Constantes.HelpMethods.IsOnplat;
import cat_element.Game;
import gamemenu.Playing;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import Entities.Entity.*;

/**
 *
 * @author Rich
 */
public class DarkEntity extends Entity implements movementable,healthable,attackable,Runnable{
    private BufferedImage darkimg;
    private int health;
    private int[][] lvlData;
    private Player player;
    
    protected int plrspeed=2;
    private double airspeed = 0;
    private double gravity = 0.06*Game.Scale;
    private double jumpSpeed = -3.2*Game.Scale;
    private double CollisionDown = 0.5*Game.Scale;
    private boolean IsJump = true;
    
    private Playing playing;
    
    private long takedamage=0;
    private double damagacd = 1;
    private int damage = 0;
    
    private boolean start = true;
    
    Thread thread;
    
    public DarkEntity(float x, float y, int width, int height,int health,Player player,Playing playing) {
        super(x, y, width, height);
        this.health = health;
        importimg();
        initHitbox(x, y, width, height);
        this.player = player;
        this.playing = playing;
        thread = new Thread(this);
        thread.start();
    }
    
    private void importimg(){
        InputStream is;
        try{
            is = getClass().getResourceAsStream("/Res/Player/Dark.png");
            darkimg = ImageIO.read(is);
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updatapose() {
        
        float xspeed =0;
        
        if(hitbox.x>player.getHitbox().x&&Math.abs(hitbox.x-player.getHitbox().x)<=500){
            xspeed -= plrspeed;
        }
        if(hitbox.x<player.getHitbox().x&&Math.abs(hitbox.x-player.getHitbox().x)<=500){
            xspeed += plrspeed;
        }
        
        if (!IsOnplat(hitbox, lvlData)) {
            IsJump = true;
        }
        
        if(IsJump){
            if(CanMoveHere(getHitbox().x, getHitbox().y+(float)airspeed, width, height, lvlData)){
                hitbox.y+=airspeed;
                airspeed += gravity;
                updateXpos(xspeed);
            }else{
//                if(!IsSolid(hitbox.x+hitbox.width, hitbox.y+hitbox.height+10, lvlData)){
//                    hitbox.y+=1;
//                }else if(!IsSolid(hitbox.x, hitbox.y+hitbox.height+10, lvlData)){
//                    hitbox.y+=1;
//                }
                if(airspeed>0){
                    int curr = (int)(hitbox.y/Game.til_size);
                    hitbox.y=curr*Game.til_size+20-1;
                }
                //y = GetEntityYPps(hitbox,(float)airspeed);
                if(airspeed>0){
                    airspeed = 0;
                    IsJump = false;
                }else{
                    airspeed = CollisionDown;
                }
                updateXpos(xspeed);
            }
        }else{
            updateXpos(xspeed);
        }
        
    }
    
    private void updateXpos(float xspeed){
        if(CanMoveHere(getHitbox().x+xspeed, getHitbox().y, width, height, lvlData)){
            if(!((xspeed>0&&hitbox.x+xspeed>player.getHitbox().x)||(xspeed<0&&hitbox.x+xspeed<player.getHitbox().x))){
                hitbox.x += xspeed;
            }
        }else{
            if(xspeed>0){
                int curr = (int)(hitbox.x/Game.til_size);
                hitbox.x=curr*Game.til_size+20-1;
            }else{
                int curr = (int)(hitbox.x/Game.til_size);
                hitbox.x=curr*Game.til_size;
            }
        }
    }

    @Override
    public void updataHealth() {
        if((System.nanoTime()-takedamage)/1000000000.0>damagacd){
                health-=1;
                damage += 10;
                takedamage= System.nanoTime();
            }
    }
    
    public void loadlvlData(int[][] lvlData){
        this.lvlData = lvlData;
    }
    
    @Override
    public void update(){
        updatapose();
        
        updateability();
    }
    
    @Override
    public void render(Graphics g,int xlvloffset){
        g.drawImage(darkimg, (int)hitbox.x-xlvloffset+damage/2, (int)hitbox.y-5+(int)(damage/2*1.25),80-damage,80-damage, null);
        //drawHitbox(g,xlvloffset);
    }
    
    

    @Override
    public void updateability() {
        if(player.getHitbox().intersects(hitbox)){
            player.setHealth();
        }
    }

    public int getHealth() {
        return health;
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

    public void setThread() {
        start = false;
    }
    
}
