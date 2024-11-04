/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat_element;

import Constantes.Constants.GameMenu;
import Entities.Player;
import Input.ListenerMouse;
import Input.Listenerkey;
import Level.LevelManager;
import Level.LevelState;
import Level.LevelState.LevelDetail;
import gamemenu.Menu;
import gamemenu.Playing;
import gamemenu.Replay;
import gamemenu.SelectLevel;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Rich
 */
public class Game extends JFrame implements Runnable{
    private GamePanel gamePanel;
    
    private Playing playing;
    private Menu menu;
    private SelectLevel selectlevel;
    private Replay replay;
    
    public static int Pixelsize=25;
    public static int Scale = 2;
    public static int til_size = Pixelsize*Scale;
    public static int WIDTH = 25;
    public static int HEIGHT = 15;
    public static int til_WIDTH = WIDTH*til_size;
    public static int til_HEIGHT = HEIGHT*til_size;
    
    public int GameStatus = GameMenu.MENU;
    public boolean IsClick = false;
    
    private Thread gameThread;
    private int FPS = 60;
    
    public ArrayList<LevelDetail> leveldetail = new ArrayList<LevelDetail>();
    
//    private Player player;
//    
//    private LevelManager levelManager;
    
    public Game() {
        initClass();
        gamePanel = new GamePanel(this);
        add(gamePanel);
        addWindowFocusListener(new WindowFocusListener(){
            @Override
            public void windowGainedFocus(WindowEvent e) {
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowlost();
            }
        });
        gamePanel.requestFocus();
        startGameLoop();
    }
    
    private void initClass(){
        menu = new Menu(this);
        selectlevel = new SelectLevel(this);
        replay = new Replay(this);
        initLevel();
//        levelManager = new LevelManager(this);
//        player = new Player(100,100,80,60);
//        player.loadlvlData(levelManager.getCurrentLevel().getLvlData());
    }
    
    private void initLevel(){
        leveldetail.add(new LevelDetail(LevelState.LEVELOne,100,500,5,3));
        leveldetail.add(new LevelDetail(LevelState.LEVELTwo,100,500,5,3));
        leveldetail.add(new LevelDetail(LevelState.LEVELThree,100,500,5,3));
        leveldetail.add(new LevelDetail(LevelState.LEVELFour,100,500,5,3));
        leveldetail.add(new LevelDetail(LevelState.LEVELFive,100,500,5,3));
    }
    
    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void update(){
        if(GameStatus==GameMenu.MENU){
            menu.update();
        }else if(GameStatus==GameMenu.PLAY){
            playing.update();
        }else if(GameStatus==GameMenu.SELECTLEVEL){
            selectlevel.update();
        }else if(GameStatus==GameMenu.REPLAY){
            replay.update();
        }
//        player.update();
//        levelManager.update();
    }
    public void render(Graphics g){
        if(GameStatus==GameMenu.MENU){
            menu.render(g);
        }else if(GameStatus==GameMenu.PLAY){
            playing.render(g);
        }else if(GameStatus==GameMenu.SELECTLEVEL){
            selectlevel.render(g);
        }else if(GameStatus==GameMenu.REPLAY){
            replay.render(g);
        }
//        levelManager.draw(g);
//        player.render(g);
    }

    @Override
    public void run() {
        double timeper = 1000000000.0/FPS;
        long last = System.nanoTime();
        double dela = 0;
        while(true){
            long curr = System.nanoTime();
            dela += (curr-last)/timeper;
            last = curr;
            if(dela>=1){
                update();
                gamePanel.repaint();
                dela--;
            }
        }
    }
    
    public void windowlost(){
        if(GameStatus==GameMenu.PLAY){
            playing.windowlost();
        }
//        player.resterpos();
    }
    
    public Playing getPlaying(){
        return playing;
    }

    public Menu getMenu() {
        return menu;
    }

    public SelectLevel getSelectlevel() {
        return selectlevel;
    }

    public Replay getReplay() {
        return replay;
    }
    
    

    public void setPlaying(Playing playing) {
        this.playing = playing;
    }
    
    public static void main(String[] args) {
        Game j = new Game();
        j.setTitle("Cat Element");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setResizable(false);
        j.pack();
        j.setVisible(true);
        j.setLocationRelativeTo(null);
        
    }
}
