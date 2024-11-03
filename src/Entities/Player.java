/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Constantes.HelpMethods;
import static Constantes.HelpMethods.*;
import Level.LevelState;
import cat_element.Game;
import static cat_element.Game.Pixelsize;
import static cat_element.Game.til_size;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import Entities.Entity.*;

/**
 *
 * @author Rich
 */
public class Player extends Entity implements movementable,attackable,healthable,Runnable{
    private BufferedImage[][][] plrimg;
    private int movani = -1;
    private boolean moving;
    public boolean  left,right,down,up;
    public boolean useability;
    private int aniIdx;
    private int posIdx;
    private int plrspeed=5;
    
    private double airspeed = 0;
    private double gravity = 0.06*Game.Scale;
    private double jumpSpeed = -3.2*Game.Scale;
    private double CollisionDown = 0.5*Game.Scale;
    private boolean IsJump = true;
    
    private int health = 5;
    private int key = 3;
    private int keynow=0;
    
    private ArrayList<BufferedImage> objimg=new ArrayList<BufferedImage>();
    public int mode=0;
    //ability
    
    private Rectangle hitboxnormal;
    private double normalcd=1.5;
    private boolean IsUsenormal = false;
    private BufferedImage[] attimg;
    private long usetime=0;
    
    private Rectangle hitboxfire;
    private double firecd = 3;
    private boolean IsUsefire = false;
    private BufferedImage[] fireimg;
    private long usefiretime=0;
    private int firepos;
    
    private Rectangle hitboxice;
    private double icecd = 10;
    private boolean IsUseice = false;
    private BufferedImage iceimg;
    private long useicetime=0;
    
    private double lightcd = 5;
    private boolean IsUseligh = false;
    private BufferedImage lighimg;
    private long uselightime=0;
    
    //health
    private long takedamage=0;
    private double damagacd = 3;
    
    private int[][] lvlData;
    
    private ArrayList<DarkEntity> entity;
    
    private Thread t;
    private boolean IsRun;
    
    public Player(float x, float y,int width,int height,int health,int key,ArrayList<DarkEntity> entity) {
        super(x, y,width,height);
        this.health = health;
        this.key=key;
        this.entity = entity;
        importimg();
        initHitbox(x, y, width, height);
        hitboxnormal = new Rectangle((int)x, (int)y, 150, 100);
        hitboxfire = new Rectangle((int)x, (int)y, 50, 50);
        hitboxice = new Rectangle((int)x, (int)y, 150, 50);
        t = new Thread(this);
        IsRun=true;
        t.start();
    }
    
    private void importimg(){
        plrimg = new BufferedImage[4][2][2];
        attimg = new BufferedImage[2];
        fireimg = new BufferedImage[2];
        String[][][] imgdata = {{{"catn_standL","catn_standR"},{"catn_walkL","catn_walkR"}}
                ,{{"catfire_standL","catfire_standR"},{"catfire_walkL","catfire_walkR"}}
                ,{{"catice_standL","catice_standR"},{"catice_walkL","catice_walkR"}}
                ,{{"catlight_standL","catlight_standR"},{"catlight_walkL","catlight_walkR"}}};
        String[] imgobjdata = {"Heart","Key","Normal","Fire","Ice","Light"};
        InputStream is;
        try{
            for(int i=0;i<imgdata.length;i++){
                for(int j=0;j<imgdata[i].length;j++){
                    is = getClass().getResourceAsStream("/Res/Player/"+imgdata[i][j][0]+".png");
                    plrimg[i][j][0] = ImageIO.read(is);
                    is = getClass().getResourceAsStream("/Res/Player/"+imgdata[i][j][1]+".png");
                    plrimg[i][j][1] = ImageIO.read(is);
                }
            }
            is = getClass().getResourceAsStream("/Res/Elemental/attackL.png");
            attimg[0] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/Elemental/attackR.png");
            attimg[1] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/Elemental/fireballL.png");
            fireimg[0] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/Elemental/fireballR.png");
            fireimg[1] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/Elemental/ice.png");
            iceimg = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/Elemental/lightstar.png");
            lighimg = ImageIO.read(is);
            
            for(int i=0;i<imgobjdata.length;i++){
                is = getClass().getResourceAsStream("/Res/obj/"+imgobjdata[i]+".png");
                objimg.add(ImageIO.read(is));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void updatapose(){
        aniIdx = 0;
        if(!left&&!right&&!IsJump&&!up){
            if(!IsJump){
                if(!IsOnplat(hitbox,lvlData)){
                    IsJump=true;
                }
            }
            return;
        }
        
        float xspeed =0;
        
        if(up){
            jump();
        }
        if(left){
            xspeed -= plrspeed;
            aniIdx = 1;
            posIdx = 0;
        }
        if(right){
            xspeed += plrspeed;
            aniIdx = 1;
            posIdx = 1;
        }
        if(left&&right){
            aniIdx = 0;
        }
        
        if(!IsJump){
            if(!IsOnplat(hitbox,lvlData)){
                IsJump=true;
            }
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
                    hitbox.y=curr*Game.til_size+40-1;
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
        
        if(getHitbox().y+height+1>=Game.til_HEIGHT){
            health=0;
        }
    }
    
    private void updateXpos(float xspeed){
        if(CanMoveHere(getHitbox().x+xspeed, getHitbox().y, width, height, lvlData)){
            hitbox.x += xspeed;
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
    public void updataHealth(){
        if(IsBlock(hitbox.x+hitbox.width/2, hitbox.y+hitbox.height/2, lvlData)==3||IsBlock(hitbox.x+hitbox.width/2, hitbox.y+hitbox.height/2, lvlData)==7){
            //setHealth();
            health = 0;
        }

    }
    
    @Override
    public void updateability(){
        if(useability){
            if (mode == 0) {
                if ((System.nanoTime() - usetime)/1000000000.0 > normalcd) {
                    hitboxnormal.x = (int) hitbox.x - 5 - (40+(int) Math.pow(50, posIdx==0?1:0)) * (int) Math.pow(-1, posIdx);
                    hitboxnormal.y = (int) hitbox.y - 40 + 10;
                    //HelpMethods.checkHitbox(hitboxnormal, 0,lvlData);
                    for(int i =0;i<entity.size();i++){
                        if(hitboxnormal.intersects(entity.get(i).getHitbox())){
                            entity.get(i).updataHealth();
                        }
                    }
                    IsUsenormal = true;
                    usetime = System.nanoTime();
                }
            }else if(mode == 1){
                if ((System.nanoTime() - usefiretime)/1000000000.0 > firecd) {
                    firepos = (int)Math.pow(-1, posIdx)*-1;
                    hitboxfire.x = (int) hitbox.x;
                    hitboxfire.y = (int) hitbox.y;
                    IsUsefire = true;
                    usefiretime = System.nanoTime();
                }
            }else if(mode == 2){
                if ((System.nanoTime() - useicetime)/1000000000.0 > icecd) {
                    hitboxice.x = (int) hitbox.x-25;
                    hitboxice.y = (int) hitbox.y+10+50;
                    IsUseice = true;
                    useicetime = System.nanoTime();
                }
            }else if(mode==3){
                if ((System.nanoTime() - uselightime)/1000000000.0 > lightcd) {
                    hitboxice.x = (int) hitbox.x-25;
                    hitboxice.y = (int) hitbox.y+10+50;
                    IsUseligh = true;
                    uselightime = System.nanoTime();
                    int i = 0;
                    if (posIdx == 0) {
                        while (i < 100) {
                            updateXpos(-5);
                            i++;
                        }
                    } else if (posIdx == 1) {
                        while (i < 100) {
                            updateXpos(5);
                            i++;
                        }
                    }
                }
            }
        }
    }
    
    private void jump(){
        if(IsJump){
            return;
        }
        IsJump = true;
        airspeed = jumpSpeed;
    }
    
    @Override
    public void run() {
        double timeper = 1000000000.0/60;
        long last = System.nanoTime();
        double dela = 0;
        while (IsRun) {
            long curr = System.nanoTime();
            dela += (curr - last) / timeper;
            last = curr;
            if (dela >= 1) {
                update();
                dela--;
            }
        }
    }
    
    @Override
    public void update() {
        updatapose();
        //updatehitbox();
        updateability();

        updataHealth();
    }
    
    public void loadlvlData(int[][] lvlData){
        this.lvlData = lvlData;
    }
    
    public void resterpos(){
        up=false;
        down=false;
        left=false;
        right=false;
    }
    
    @Override
    public void render(Graphics g,int xlvloffset){

        g.drawImage(plrimg[mode][aniIdx][posIdx], (int)hitbox.x-5-xlvloffset, (int)hitbox.y-40,100,100, null);
        if(IsUsenormal&&(System.nanoTime()-usetime)/10000000<=25){
            g.drawImage(attimg[posIdx], (int)hitbox.x-5-(30+(int) Math.pow(50, posIdx==0?1:0))*(int)Math.pow(-1,posIdx)-xlvloffset, (int)hitbox.y-40+10,150,100, null);
            //g.drawRect(hitboxnormal.x-xlvloffset, hitboxnormal.y, 150, 100);
        }else{
            IsUsenormal = false;
        }
        if(IsUsefire&&(System.nanoTime()-usefiretime)/100000000<=1*10){
            g.drawImage(fireimg[firepos==-1?0:1], hitboxfire.x-xlvloffset, hitboxfire.y,hitboxfire.width,hitboxfire.height, null);
            //g.drawRect(hitboxfire.x-xlvloffset, hitboxfire.y,hitboxfire.width,hitboxfire.height);
            if(HelpMethods.checkHitbox(hitboxfire, 0,lvlData,2)==1){
                IsUsefire = false;
            }
            if(HelpMethods.checkHitbox(hitboxfire, 0,lvlData,6)==1){
                IsUsefire = false;
            }
            hitboxfire.x += 10*firepos;
        }else{
            IsUsefire = false;
        }
        
        if(IsUseice&&(System.nanoTime()-useicetime)/100000000<=3*10){
            g.drawImage(iceimg, hitboxice.x-xlvloffset, hitboxice.y-50,hitboxice.width,hitboxice.height, null);
            //g.drawRect(hitboxice.x-xlvloffset, hitboxice.y,hitboxice.width,hitboxice.height);
            HelpMethods.checkHitbox(hitboxice, 0,lvlData,3);
            hitboxice.x = (int) hitbox.x-25;
            hitboxice.y = (int) hitbox.y+10+50;
        }else{
            IsUseice = false;
        }
        
        if(IsUseligh&&(System.nanoTime()-uselightime)/100000000<=0.5*10){
            g.drawImage(lighimg, (int)hitbox.x-5-xlvloffset, (int)hitbox.y-40,100,100, null);
        }else{
            IsUseligh = false;
        }
        
        //drawHitbox(g,xlvloffset);
        
        for(int i=0;i<health;i++){
            g.drawImage(objimg.get(0), 12+i*50, 15,25,25, null);
        }
        long time = System.nanoTime();
        if((time - usetime)/1000000000.0 > normalcd){
            g.drawImage(objimg.get(2), 950+2*50, 15,25,25, null);
        }
        if((time - usefiretime)/1000000000.0 > firecd){
            g.drawImage(objimg.get(3), 950+3*50, 15,25,25, null);
        }
        if((time - useicetime)/1000000000.0 > icecd){
            g.drawImage(objimg.get(4), 950+4*50, 15,25,25, null);
        }
        if((time - uselightime)/1000000000.0 > lightcd){
            g.drawImage(objimg.get(5), 950+5*50, 15,25,25, null);
        }
        
    }

    public int getHealth() {
        return health;
    }

    public void setHealth() {
        if(System.nanoTime()-takedamage>damagacd * 1000000000){
                health-=1;
                takedamage= System.nanoTime();
            }
    }

    public int getKey() {
        return key;
    }

    public void addKeynow() {
        this.keynow+=1;
    }

    public int getKeynow() {
        return keynow;
    }

    public void setrun() {
        IsRun = false;
    }
    
    
    
}
