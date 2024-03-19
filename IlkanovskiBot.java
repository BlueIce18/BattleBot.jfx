package bots;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import arena.BattleBotArena;
import arena.BotInfo;
import arena.Bullet;




public class IlkanovskiBot extends RandBot{

    //global variables
    boolean fireOnce = true;
    int shotTimer = 0;


    BotHelper helper = new BotHelper();

    @Override
    public void newRound() {


    }

    @Override
    public int getMove(BotInfo me, boolean shotOK, BotInfo[] liveBots, BotInfo[] deadBots, Bullet[] bullets) {
        
        double middleBotX = me.getX() + RADIUS;
        double middleBotY = me.getY() + RADIUS;
        BotInfo closeBot = helper.findClosest(me, liveBots);
        int lastMove = BattleBotArena.RIGHT;
        
        
        //Timer so I don't shoot all of my bullets away at once
        shotTimer++;
        if (shotTimer % 4 == 0){
            fireOnce = true;
        }


       //Checking if someone is near me and firing 
        
       
       if (me.getX() - 200 <= closeBot.getX() && (me.getY() + 20 >= closeBot.getY() && me.getY() - 8 <= closeBot.getY()) && shotOK == true && closeBot.getX() < me.getX() && closeBot.getTeamName() != "kinda hood kinda classy" && fireOnce == true){
            fireOnce = false;
            shotTimer = 0;
            return BattleBotArena.FIRELEFT;
    }
        if (me.getX() + 200 >= closeBot.getX() && (me.getY() + 20 >= closeBot.getY() && me.getY() - 8 <= closeBot.getY()) && shotOK == true && closeBot.getX() > me.getX() && closeBot.getTeamName() != "kinda hood kinda classy" && fireOnce == true){
            fireOnce = false;
            shotTimer = 0;
            return BattleBotArena.FIRERIGHT;
    }
        if (me.getY() + 200 >= closeBot.getY() && (me.getX() + 20 >= closeBot.getX() && me.getX() - 8 <= closeBot.getX()) && shotOK == true && closeBot.getY() > me.getY() && closeBot.getTeamName() != "kinda hood kinda classy" && fireOnce == true){
            fireOnce = false;
            shotTimer = 0;
            return BattleBotArena.FIREDOWN;
    }
        if (me.getY() - 200 <= closeBot.getY() && (me.getX() + 20 >= closeBot.getX() && me.getX() - 8 <= closeBot.getX()) && shotOK == true && closeBot.getY() < me.getY() && closeBot.getTeamName() != "kinda hood kinda classy" && fireOnce == true){
            fireOnce = false;
            shotTimer = 0;
            return BattleBotArena.FIREUP;
    }

///////////////////////////


        // Dodging code
        //Checking if there is a bullet to the left or right of us coming towards us
            if (bullets.length > 0){
                Bullet closeBullet = helper.findClosest(me, bullets);

                if ((me.getY() + RADIUS*2 + RADIUS >= closeBullet.getY() && me.getY() - RADIUS <= closeBullet.getY()) && closeBullet.getXSpeed() < 0 && closeBullet.getX() > me.getX()){ //bullet coming towards us from right
                    if (closeBullet.getY() < middleBotY){ //checking if it is aiming towards top 
                          return BattleBotArena.DOWN;
                      }
                    if (closeBullet.getY() > middleBotY){ //checking if it is aiming towards bottom
                         return BattleBotArena.UP;
                     }
                    }
                    ////////////////////////////////////
    
                if ((me.getY() + RADIUS*2 + RADIUS >= closeBullet.getY() && me.getY() - RADIUS <= closeBullet.getY()) && closeBullet.getXSpeed() > 0 && closeBullet.getX() < me.getX()){ //bullet coming towards us from left
                    if (closeBullet.getY() < middleBotY){ //checking if it is aiming towards top 
                         return BattleBotArena.DOWN;
                     }
                     if (closeBullet.getY() > middleBotY){ //checking if it is aiming towards bottom
                         return BattleBotArena.UP;
                     }
                  }
                    ////////////////////////////////////
    
                    //Checking if there is a bullet coming towards us from the top or bottom
                if ((me.getX() + RADIUS*2 + RADIUS >= closeBullet.getX() || me.getX() - RADIUS <= closeBullet.getX()) && closeBullet.getYSpeed() < 0 && closeBullet.getY() > me.getY()){ //bullet coming towards us from below
                      if (closeBullet.getX() < middleBotX){ //checking if it is aiming towards top 
                         return BattleBotArena.RIGHT;
                     }
                     if (closeBullet.getX() > middleBotX){ //checking if it is aiming towards bottom 
                         return BattleBotArena.LEFT;
                      }
                 }
                    ////////////////////////////////////
    
                if ((me.getX() + RADIUS*2 + RADIUS >= closeBullet.getX() || me.getX() - RADIUS <= closeBullet.getX()) && closeBullet.getYSpeed() > 0 && closeBullet.getY() < me.getY()){ //bullet coming towards us from above
                      if (closeBullet.getX() < middleBotX){ //checking if it is aiming towards top 
                          return BattleBotArena.RIGHT;
                      }
                      if (closeBullet.getX() > middleBotX){ //checking if it is aiming towards bottom 
                          return BattleBotArena.LEFT;
                      }
                 }
                 ////////////////////////////////////
            }
            
        
            //Dodging deadbots code
            //below
            if (deadBots.length > 0){
            BotInfo closeDead = helper.findClosest(me, deadBots);
            if (me.getY() + 30 > closeDead.getY())   {  
                lastMove = BattleBotArena.DOWN; //ensures our next move cannot be DOWN
            } 
            //above
            if (me.getY() - 30 < closeDead.getY())   {  
                lastMove = BattleBotArena.UP; //ensures our next move cannot be UP
            } 
            //right
            if (me.getX() + 30 > closeDead.getX())   {  
                lastMove = BattleBotArena.RIGHT; //ensures our next move cannot be RIGHT
            } 
            //left
            if (me.getX() - 30 < closeDead.getX())   {  
                lastMove = BattleBotArena.LEFT; //ensures our next move cannot be LEFT
            } 
        }

       //Hunting code - chasing the closest bot for a kill
 

         //Hunting code
            //below you
            if (closeBot.getY() > me.getY() && lastMove != BattleBotArena.DOWN && closeBot.getTeamName() != "kinda hood kinda classy"){           
            lastMove = BattleBotArena.DOWN;
            return BattleBotArena.DOWN;
                
        } 
            //above you
            if (closeBot.getY() < me.getY() && lastMove != BattleBotArena.UP && closeBot.getTeamName() != "kinda hood kinda classy"){                
            lastMove = BattleBotArena.UP;
            return BattleBotArena.UP;
        } 
            //to the right of you
            if (closeBot.getX() > me.getX() && lastMove != BattleBotArena.RIGHT && closeBot.getTeamName() != "kinda hood kinda classy"){                
            lastMove = BattleBotArena.RIGHT;
            return BattleBotArena.RIGHT;
        } 
            //to the left of you
            if (closeBot.getX() < me.getX() && lastMove != BattleBotArena.LEFT && closeBot.getTeamName() != "kinda hood kinda classy"){                
            lastMove = BattleBotArena.LEFT;
            return BattleBotArena.LEFT;
        } 
         
         ////////////////////////////////////


         // Making sure my bot isn't stuck to the edge of the screen

         if (me.getX() - 10 < BattleBotArena.LEFT_EDGE){
            return BattleBotArena.RIGHT;
         }
         if (me.getX() + 35 > BattleBotArena.RIGHT_EDGE){
            return BattleBotArena.LEFT;
         }
         if (me.getY() - 10 < BattleBotArena.TOP_EDGE){
            return BattleBotArena.DOWN;
         }
         if (me.getY() + 35 > BattleBotArena.BOTTOM_EDGE){
            return BattleBotArena.UP;
         }

         return BattleBotArena.STAY;
         
    }










        


    @Override
    public void draw(Graphics g, int x, int y) {
        //Drawing rectangular player
        g.setColor(Color.white);
		g.fillRect(x+2, y+2, RADIUS*2-4, RADIUS*2-4);
		
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "Jovan";
    }

    @Override
    public String getTeamName() {
        // TODO Auto-generated method stub
        return "kinda hood kinda classy";
    }

    @Override
    public String outgoingMessage() {
        return " ";
    }

    @Override
    public void incomingMessage(int botNum, String msg) {

    }

    @Override
    public String[] imageNames() {
        String[] paths = {"drone_up.png", "drone_down.png", "drone_right.png", "drone_left.png"};
		return paths;

    }

    @Override
    public void loadedImages(Image[] images) {
       
       

    }


}
