/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Rich
 */
public abstract class Entity {
    protected float x,y;
    protected int width,height;
    protected Rectangle hitbox;
    public Entity(float x,float y,int width,int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    protected void drawHitbox(Graphics g,int xlvloffset){
        g.setColor(Color.pink);
        g.drawRect(hitbox.x-xlvloffset, hitbox.y, hitbox.width, hitbox.height);
    }
    
    protected void initHitbox(float x,float y,int width,int height){
        hitbox = new Rectangle((int)x, (int)y, width, height);
    }
//    public void updatehitbox(){
//        hitbox.x = (int)x+20-15;
//        hitbox.y = (int)y+40-5;
//    }

    public Rectangle getHitbox() {
        return hitbox;
    }
    
    abstract public void update();
    
    abstract public void render(Graphics g,int xlvloffset);
    
    interface movementable{
    void updatapose();
    }

    interface attackable{
        void updateability();
    }

    interface healthable{
        void updataHealth();
    }
}
