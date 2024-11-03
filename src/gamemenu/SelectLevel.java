/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamemenu;

import Constantes.Constants;
import Constantes.HelpMethods;
import Level.LevelState;
import Level.LevelState.LevelDetail;
import cat_element.Game;
import static cat_element.Game.til_HEIGHT;
import static cat_element.Game.til_WIDTH;
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
public class SelectLevel extends GamePage{
    private BufferedImage[] background;
    public int mousex,mousey;
    private ArrayList<Rectangle> rect = new ArrayList<Rectangle>();
    public SelectLevel(Game game) {
        super(game);
        importImage();
        for(int i=1;i<=LevelState.levelnum;i++){
            if(i%6==0){
                continue;
            }
            Rectangle r = new Rectangle(225*(i%6)-150, 250*(int)(i/6)+150, 200, 200);
            rect.add(r);
        }
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
    
    @Override
    public void update() {
        if(game.IsClick==true){
            for(int i=0;i<rect.size();i++){
                if(HelpMethods.CheckDotOnRect(mousex,mousey,rect.get(i))){
                    LevelDetail leveldetail = game.leveldetail.get(i);
                    game.setPlaying(new Playing(game,leveldetail.getLevel(),leveldetail.getXstart(),leveldetail.getYstart(),i,leveldetail.getHealth(),leveldetail.getKey()));
                    game.GameStatus = Constants.GameMenu.PLAY;
                    mousex = 0;
                    mousey = 0;
                }
//                if(HelpMethods.CheckDotOnRect(mousex,mousey,rect.get(i))&&i+1==2){
//                    game.setPlaying(new Playing(game,LevelState.LEVELOne,100,500));
//                    game.GameStatus = Constants.GameMenu.MENU;
//                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background[0], 0,0,til_WIDTH, til_HEIGHT, null);
        g.drawImage(background[1],0, 0,til_WIDTH, til_HEIGHT, null);
        for(int i=0;i<rect.size();i++){
            Rectangle r = rect.get(i);
            g.setColor(HelpMethods.CheckDotOnRect(mousex,mousey,r)?Color.GRAY:Color.WHITE);
            g.fillRect(r.x,r.y,r.width,r.height);
            g.setColor(Color.BLACK);
            g.setFont(new Font("",Font.BOLD,80));
            g.drawString(""+(i+1), r.x+r.width/2-25, r.y+r.height/2+25);
        }
        
    }
    
}
