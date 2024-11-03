/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Constantes;

import Entities.Player;
import cat_element.Game;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Rich
 */
public class HelpMethods {
    public static boolean CanMoveHere(float x,float y,int witdh,int height,int[][] lvlData){
        if(!IsSolid(x, y, lvlData)){
            if(!IsSolid(x+witdh, y+height, lvlData)){
                if(!IsSolid(x+witdh, y, lvlData)){
                    if(!IsSolid(x, y+height, lvlData)){
                        if(!IsSolid(x+witdh/2, y, lvlData)&&!IsSolid(x+witdh/2, y+height, lvlData)
                                &&!IsSolid(x, y+height/2, lvlData)&&!IsSolid(x+witdh, y+height/2, lvlData)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean IsSolid(float x,float y,int[][] lvlData){
        int maxwidth = lvlData[0].length*Game.til_size;
        
        if(x<0||x>=maxwidth){
            return true;
        }
        if(y<0||y>=Game.til_HEIGHT){
            return true;
        }
        
        float xIdx = x/Game.til_size;
        float yIdx = y/Game.til_size;
        int value = lvlData[(int)yIdx][(int)xIdx];
        if(value!=0&&value!=3&&value!=4&&value!=8&&value!=98){
            return true;
        }
        return false;
    }
    
    public static int IsBlock(float x,float y,int[][] lvlData){
        int maxwidth = lvlData[0].length*Game.til_size;
        
        if(x<0||x>=maxwidth){
            return 0;
        }
        if(y<0||y>=Game.til_HEIGHT){
            return 0;
        }
        
        float xIdx = x/Game.til_size;
        float yIdx = y/Game.til_size;
        int value = lvlData[(int)yIdx][(int)xIdx];
        if(value==2||value==3||value==6||value==7){
            return value;
        }
        return 0;
    }
    
//    public static float GetEntityYPps(Rectangle hitbox,float airspeed){
//        int curr = (int)(hitbox.y/Game.til_size);
//        if(airspeed>0){
//            int ypos = curr*Game.til_size;
//            return ypos;
//        }else{
//            return curr*Game.til_size-25;
//        }
//    }
    
    public static boolean IsOnplat(Rectangle hitbox,int[][] lvlData){
        if(!IsSolid(hitbox.x, hitbox.y+hitbox.height+10, lvlData)){
            if(!IsSolid(hitbox.x+hitbox.width, hitbox.y+hitbox.height+10, lvlData)){
                if(!IsSolid(hitbox.x+hitbox.width/2, hitbox.y+hitbox.height+10, lvlData)){
                    return false;
                }
            }
        }
        return true;
    }
    
    private static void setblockwood(int x,int y,int block,int[][] lvlData){
        lvlData[y][x] = block;
        if(x+1<lvlData[y].length&&lvlData[y][x+1]==2){
            setblockwood(x+1,y,block,lvlData);
        }
        if(x-1>=0&&lvlData[y][x-1]==2){
            setblockwood(x-1,y,block,lvlData);
        }
        if(y+1<lvlData.length&&lvlData[y+1][x]==2){
            setblockwood(x,y+1,block,lvlData);
        }
        if(y-1>=0&&lvlData[y-1][x]==2){
            setblockwood(x,y-1,block,lvlData);
        }
    }
    
    private static void setblockwatertoice(int x,int y,int[][] lvlData){
        if(lvlData[y][x]==3){
            lvlData[y][x]=5;
        }else if(lvlData[y][x]==4){
            lvlData[y][x]=6;
        }
        if(x+1<lvlData[y].length&&(lvlData[y][x+1]==3||lvlData[y][x+1]==4)){
            setblockwatertoice(x+1,y,lvlData);
        }
        if(x-1>=0&&(lvlData[y][x-1]==3||lvlData[y][x-1]==4)){
            setblockwatertoice(x-1,y,lvlData);
        }
        if(y+1<lvlData.length&&(lvlData[y+1][x]==3||lvlData[y+1][x]==4)){
            setblockwatertoice(x,y+1,lvlData);
        }
        if(y-1>=0&&(lvlData[y-1][x]==3||lvlData[y-1][x]==4)){
            setblockwatertoice(x,y-1,lvlData);
        }
    }
    
    private static void setblockicetowater(int x,int y,int[][] lvlData){
        if(lvlData[y][x]==5){
            lvlData[y][x]=3;
        }else if(lvlData[y][x]==6){
            lvlData[y][x]=4;
        }
        if(x+1<lvlData[y].length&&(lvlData[y][x+1]==5||lvlData[y][x+1]==6)){
            setblockicetowater(x+1,y,lvlData);
        }
        if(x-1>=0&&(lvlData[y][x-1]==5||lvlData[y][x-1]==6)){
            setblockicetowater(x-1,y,lvlData);
        }
        if(y+1<lvlData.length&&(lvlData[y+1][x]==5||lvlData[y+1][x]==6)){
            setblockicetowater(x,y+1,lvlData);
        }
        if(y-1>=0&&(lvlData[y-1][x]==5||lvlData[y-1][x]==6)){
            setblockicetowater(x,y-1,lvlData);
        }
    }
    
    private static void setlvlblock(int x,int y,int block,int[][] lvlData,int checkblock){
        if(lvlData[y][x]==2&&checkblock==2){
            setblockwood(x,y,block,lvlData);
        }else if(lvlData[y][x]==3&&checkblock==3){
            setblockwatertoice(x,y,lvlData);
        }else if(lvlData[y][x]==6&&checkblock==6){
            setblockicetowater(x, y, lvlData);
        }
        //lvlData[y][x] = block;
    }
    
    public static int checkHitbox(Rectangle hitboxnormal,int block,int[][] lvlData,int checkblock){
        int check = 0;
        if (IsBlock(hitboxnormal.x, hitboxnormal.y, lvlData) != 0) {
            int xIdx = (hitboxnormal.x) / Game.til_size;
            int yIdx = (hitboxnormal.y) / Game.til_size;
            setlvlblock(xIdx, yIdx, block,lvlData,checkblock);
            check = 1;
        }
        if (IsBlock(hitboxnormal.x + hitboxnormal.width, hitboxnormal.y, lvlData) != 0) {
            int xIdx = (hitboxnormal.x + hitboxnormal.width) / Game.til_size;
            int yIdx = (hitboxnormal.y) / Game.til_size;
            setlvlblock(xIdx, yIdx, block,lvlData,checkblock);
            check = 1;
        }
        if (IsBlock(hitboxnormal.x, hitboxnormal.y + hitboxnormal.height, lvlData) != 0) {
            int xIdx = (hitboxnormal.x) / Game.til_size;
            int yIdx = (hitboxnormal.y + hitboxnormal.height) / Game.til_size;
            setlvlblock(xIdx, yIdx, block,lvlData,checkblock);
            check = 1;
        }
        if (IsBlock(hitboxnormal.x + hitboxnormal.width, hitboxnormal.y + hitboxnormal.height, lvlData) != 0) {
            int xIdx = (hitboxnormal.x + hitboxnormal.width) / Game.til_size;
            int yIdx = (hitboxnormal.y + hitboxnormal.height) / Game.til_size;
            setlvlblock(xIdx, yIdx, block,lvlData,checkblock);
            check = 1;
        }
        if (IsBlock(hitboxnormal.x + hitboxnormal.width, hitboxnormal.y + hitboxnormal.height / 2, lvlData) != 0) {
            int xIdx = (hitboxnormal.x + hitboxnormal.width) / Game.til_size;
            int yIdx = (hitboxnormal.y + hitboxnormal.height / 2) / Game.til_size;
            setlvlblock(xIdx, yIdx, block,lvlData,checkblock);
            check = 1;
        }
        if (IsBlock(hitboxnormal.x, hitboxnormal.y + hitboxnormal.height / 2, lvlData) != 0) {
            int xIdx = (hitboxnormal.x) / Game.til_size;
            int yIdx = (hitboxnormal.y + hitboxnormal.height / 2) / Game.til_size;
            setlvlblock(xIdx, yIdx, block,lvlData,checkblock);
            check = 1;
        }
        if (IsBlock(hitboxnormal.x+hitboxnormal.width/2, hitboxnormal.y , lvlData) != 0) {
            int xIdx = (hitboxnormal.x+hitboxnormal.width/2) / Game.til_size;
            int yIdx = (hitboxnormal.y ) / Game.til_size;
            setlvlblock(xIdx, yIdx, block,lvlData,checkblock);
            check = 1;
        }
        if (IsBlock(hitboxnormal.x+hitboxnormal.width/2, hitboxnormal.y+hitboxnormal.height , lvlData) != 0) {
            int xIdx = (hitboxnormal.x+hitboxnormal.width/2) / Game.til_size;
            int yIdx = (hitboxnormal.y+hitboxnormal.height ) / Game.til_size;
            setlvlblock(xIdx, yIdx, block,lvlData,checkblock);
            check = 1;
        }
        return check;
    }
    
    
    
    public static boolean CheckDotOnRect(int x,int y,Rectangle rect){
        if(x>=rect.getX() && x<=rect.getX()+rect.width && y>= rect.getY() && y<= rect.getY()+rect.height){
            return true;
        }
        return false;
    }
}
