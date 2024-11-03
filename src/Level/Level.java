/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Level;

/**
 *
 * @author Rich
 */
public class Level {
    private int[][] lvlData;
    
    public Level(int[][] lvlData){
        this.lvlData = lvlData;
    }
    public int getSpriteIndex(int x,int y){
        return lvlData[y][x];
    }
    public void setlvlData(int x,int y,int block){
        lvlData[y][x]=block;
    }
    public int getHeight(){
        return lvlData.length;
    }
    
    public int getWidth(int y){
        return lvlData[y].length;
    }

    public int[][] getLvlData() {
        return lvlData;
    }
    
}
