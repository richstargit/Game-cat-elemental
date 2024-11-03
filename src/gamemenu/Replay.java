/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamemenu;

import Constantes.Constants.GameMenu;
import Constantes.HelpMethods;
import Entities.Player;
import Level.Level;
import Level.LevelManager;
import Level.LevelState;
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
import javax.imageio.ImageIO;

/**
 *
 * @author Rich
 */
public class Replay extends GamePage{
    private BufferedImage[] background;
    public int mousex,mousey;
    
    private Rectangle Playbox;
    private Rectangle Exit;
    
    private String showstring="";

    public Replay(Game game) {
        super(game);
        initClass(game);
        importImage();
    }
    
    public void importImage(){
        background = new BufferedImage[2];
        try{
            InputStream is;
            is = getClass().getResourceAsStream("/Res/1.png");
            background[0] = ImageIO.read(is);
            is = getClass().getResourceAsStream("/Res/5.png");
            background[1] = ImageIO.read(is);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void initClass(Game game){
        Playbox = new Rectangle(Game.til_WIDTH/2-100, Game.til_HEIGHT/2-50, 200, 100);
        Exit = new Rectangle(Game.til_WIDTH/2-100, Game.til_HEIGHT/2+100, 200, 100);
        
    }

    public void setShowstring(String showstring) {
        this.showstring = showstring;
    }
    
    @Override
    public void update(){
        if(game.IsClick==true){
            if(HelpMethods.CheckDotOnRect(mousex,mousey,Playbox)){
                int lvl = game.getPlaying().StartLevel;
                LevelState.LevelDetail leveldetail = game.leveldetail.get(lvl);
                game.setPlaying(new Playing(game,leveldetail.getLevel(),leveldetail.getXstart(),leveldetail.getYstart(),lvl,leveldetail.getHealth(),leveldetail.getKey()));
                game.GameStatus = GameMenu.PLAY;
                mousex = 0;
                mousey = 0;
            }
            if(HelpMethods.CheckDotOnRect(mousex,mousey,Exit)){
                game.GameStatus = GameMenu.MENU;
                mousex = 0;
                mousey = 0;
            }
        }
    }
    
    
    
    @Override
    public void render(Graphics g){
        g.drawImage(background[0], 0,0,til_WIDTH, til_HEIGHT, null);
        g.drawImage(background[1],0, 0,til_WIDTH, til_HEIGHT, null);
        game.getPlaying().getLevelManager().draw(g,game.getPlaying().getXlvloffset());
        g.setFont(new Font("", Font.BOLD, 150));
        g.setColor(Color.BLACK);
        g.drawString(showstring, Game.til_WIDTH/2-100-220, Game.til_HEIGHT/2-50-10);
        g.setColor(HelpMethods.CheckDotOnRect(mousex,mousey,Playbox)?Color.GRAY:Color.WHITE);
        g.fillRect(Playbox.x,Playbox.y,Playbox.width,Playbox.height);
        g.setFont(new Font("", Font.BOLD, 50));
        g.setColor(Color.BLACK);
        g.drawString("Replay", Playbox.x+Playbox.width/2-80, Playbox.y+Playbox.height/2+15);
        g.setColor(HelpMethods.CheckDotOnRect(mousex,mousey,Exit)?Color.GRAY:Color.WHITE);
        g.fillRect(Exit.x,Exit.y,Exit.width,Exit.height);
        g.setColor(Color.BLACK);
        g.drawString("Return", Exit.x+Exit.width/2-80, Exit.y+Exit.height/2+15);
    }
}
