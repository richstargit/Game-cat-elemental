/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import gamemenu.Playing;

/**
 *
 * @author Rich
 */
public class DarkEntitySpeed extends DarkEntity{
    
    public DarkEntitySpeed(float x, float y, int width, int height, int health, Player player, Playing playing) {
        super(x, y, width, height, health, player, playing);
        plrspeed = 10;
    }
    
}
