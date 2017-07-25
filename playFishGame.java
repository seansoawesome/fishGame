import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import sun.audio.*;
import java.io.*;
//import java.util.Timer;
//import java.util.TimerTask;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class playFishGame extends JPanel implements MouseMotionListener
{
    private JFrame board;//the main board
    private JLabel fish;
    private JLabel enemyFishS;
    private JLabel enemyFishS2;
    private JLabel enemyFishS3;
    private JLabel enemyFishS4;
    private JLabel enemyFishM;
    private JLabel enemyFishM2;
    private JLabel enemyFishM3;
    private JLabel enemyFishL;
    private JLabel enemyFishL2;
    private ImageIcon fishPic;
    private ImageIcon enemyFishSPic;
    private ImageIcon winBackground= new ImageIcon("data/");
    private ImageIcon background= new ImageIcon("data/background3.png");
    private ImageIcon loseBackground= new ImageIcon("data/background4.png");
    private Timer timer;
    private Timer getOrigin;
    ImageIcon fishSmall1r= new ImageIcon("data/fishSmall1r.png");
    ImageIcon fishSmall1l= new ImageIcon("data/fishSmall1l.png");
    ImageIcon fishSmall2r= new ImageIcon("data/fishSmall2r.png");
    ImageIcon fishSmall2l= new ImageIcon("data/fishSmall2l.png");
    ImageIcon fishSmall4r= new ImageIcon("data/fishSmall4r.png");
    ImageIcon fishSmall4l= new ImageIcon("data/fishSmall4l.png");
    ImageIcon fm1r=new ImageIcon("data/fishMedium1r.png");
    ImageIcon fm1l=new ImageIcon("data/fishMedium1l.png");
    ImageIcon fm2r=new ImageIcon("data/fishMedium3r.png");
    ImageIcon fm2l=new ImageIcon("data/fishMedium3l.png");
    ImageIcon fm4r=new ImageIcon("data/fishMedium4r.png");
    ImageIcon fm4l=new ImageIcon("data/fishMedium4l.png");
    ImageIcon fl1r=new ImageIcon("data/fishLarge1r.png");
    ImageIcon fl1l=new ImageIcon("data/fishLarge1l.png");
    ImageIcon fl2r=new ImageIcon("data/fishLarge2r.png");
    ImageIcon fl2l=new ImageIcon("data/fishLarge2l.png");
    ImageIcon fl4r=new ImageIcon("data/fishLarge4r.png");
    ImageIcon fl4l=new ImageIcon("data/fishLarge4l.png");
    private fish fishFish;
    private fish enemyFishSFish;
    private fish enemyFishSFish2;
    private fish enemyFishSFish3;
    private fish enemyFishSFish4;
    private fish enemyFishMFish;
    private fish enemyFishMFish2;
    private fish enemyFishMFish3;
    private fish enemyFishLFish;
    private fish enemyFishLFish2;
    private int origin;
    private int level;
    private boolean contact,win;
    Cursor blankCursor = null;
    private AudioStream BGM;//music
    private AudioStream BGM1;
    public static void main(String args[])
    {
        playFishGame play= new playFishGame();
    }

    public playFishGame()
    {
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage("data/.pn"), new Point(0, 0), "blankCursor"); // blank.png is any tranparent image.
        board= new JFrame("Play Fish Game");
        board.setSize(1300,700);
        BackgroundPanel panel=new BackgroundPanel(background.getImage());
        board.add(panel);
        //JLabel contentpane= new JLabel();
        //contentpane.setLayout(new BorderLayout());
        //contentpane.setIcon(background);
        //board.add(contentpane);
        JPanel dragPanel= new JPanel(new DragLayout(true));
        panel.add(new JScrollPane(dragPanel));
        //contentpane.setOpaque(true);
        //.setContentPane(contentpane);
        //board.setOpaque(false);
        // contentpane.setOpaque(true);
        board.setCursor(blankCursor);
        //1233,622
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dragPanel.addMouseMotionListener(this);
        board.setVisible(true);
        ImageIcon fishPic= new ImageIcon("data/sfr.gif");
        fish=new JLabel (fishPic);
        enemyFishS= new JLabel (" ");
        enemyFishS2= new JLabel (" ");
        enemyFishS3= new JLabel (" ");
        enemyFishS4= new JLabel(" ");
        enemyFishM= new JLabel (" ");
        enemyFishM2= new JLabel (" ");
        enemyFishM3= new JLabel (" ");
        enemyFishL= new JLabel (" ");
        enemyFishL2= new JLabel (" ");
        fishFish=new fish(617,0,3);
        enemyFishSFish= new fish(1300,350,0);
        enemyFishSFish2= new fish(0,500,0);
        enemyFishSFish3= new fish(1300,200,0);
        enemyFishSFish4= new fish(0,0,0);
        enemyFishMFish= new fish(0,0,1);
        enemyFishMFish2= new fish(0,0,1);
        enemyFishMFish3= new fish(0,0,1);
        enemyFishLFish= new fish(0,0,2);
        enemyFishLFish2= new fish(0,0,2);
        dragPanel.add(fish);
        dragPanel.add(enemyFishS);
        dragPanel.add(enemyFishS2);
        dragPanel.add(enemyFishS3);
        dragPanel.add(enemyFishS4);
        dragPanel.add(enemyFishM);
        dragPanel.add(enemyFishM2);
        dragPanel.add(enemyFishM3);
        dragPanel.add(enemyFishL);
        dragPanel.add(enemyFishL2);
        contact=false;
        win=false;
        repaint();
        music();
        ActionListener listener= new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                timerAction();
            }
        };
        Timer timer = new Timer(70, listener);
        timer.start();
        timer.setRepeats(true);
        board.setState(Frame.ICONIFIED);
        board.setState(Frame.NORMAL);
    }

    public void music()
    {
        AudioPlayer MGP = AudioPlayer.player;
        AudioData MD;
        ContinuousAudioDataStream loop = null;
        try
        {
            InputStream main = new FileInputStream("data/burndtJamv1.wav");
            InputStream champ= new FileInputStream("data/champions.wav");
            InputStream loser=new FileInputStream("data/gameOver.wav");
            if((contact==false)&&(win==false))
            {
                BGM= new AudioStream(main);
            }else if(contact==true)
            {
                BGM=new AudioStream(loser);
            }else if(win==true)
                BGM= new AudioStream(champ);
            AudioPlayer.player.start(BGM);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        //MGP.start(loop);
    }

    public void soundEffects()
    {
        AudioPlayer MGP = AudioPlayer.player;
        AudioData MD;
        ContinuousAudioDataStream loop = null;
        try
        {
            InputStream chomp = new FileInputStream("data/chomp.wav");
            BGM1= new AudioStream(chomp);
            AudioPlayer.player.start(BGM1);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
    }

    public void mouseMoved(MouseEvent evt){
        //System.out.println(evt.getPoint().x + ", " + evt.getPoint().y);

        if((evt.getPoint().x<1231)&&(evt.getPoint().y<623))
        {
            fish.setLocation(evt.getPoint().x-level,evt.getPoint().y-level);
            fishFish.override(evt.getPoint().x,evt.getPoint().y);
        }
        ActionListener listener= new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                origin=fishFish.getX();
                if(fishFish.sFish<=9)
                    level=20;
                if((fishFish.sFish>9)&&(fishFish.mFish<10))
                    level=60;
                if(fishFish.mFish>=10)
                    level=110;
                //fish.setLocation(fishFish.getX(),fishFish.getY());
            }
        };
        Timer timer = new Timer(1, listener);
        timer.start();
        timer.setRepeats(true);
        int posneg=origin-evt.getPoint().x;
        ImageIcon sfr= new ImageIcon("data/sfr.gif");
        ImageIcon sfl= new ImageIcon("data/sfl.gif");
        ImageIcon mfr= new ImageIcon("data/mfr.gif");
        ImageIcon mfl= new ImageIcon("data/mfl.gif");
        ImageIcon lfr= new ImageIcon("data/lfr.gif");
        ImageIcon lfl= new ImageIcon("data/lfl.gif");
        if(posneg<0)
        {
            if(fishFish.sFish<=9)
                fish.setIcon(sfr);
            if((fishFish.sFish>9)&&(fishFish.mFish<9))
                fish.setIcon(mfr);
            if(fishFish.mFish>9)
                fish.setIcon(lfr);
        }
        if(posneg>0)
        {
            if(fishFish.sFish<=9)
                fish.setIcon(sfl);
            if((fishFish.sFish>9)&&(fishFish.mFish<9))
                fish.setIcon(mfl);
            if(fishFish.mFish>9)
                fish.setIcon(lfl);
        }
        if(fishFish.lFish==5)
        {
            win=true;
            //AudioPlayer.player.stop(BGM);
            //music();
            fishFish.lFish++;
            gameOver();
        }
    }

    public void mouseDragged(MouseEvent evt){
    }
    //95/34
    public void contact(fish enemyFishFish, JLabel enemyFish)
    {
        if((Math.abs(fishFish.getX()-enemyFish.getX()))<48&&(Math.abs(fishFish.getY()-enemyFish.getY())<36))
        {
            if(enemyFishFish.fishType==0)
            {
                fishFish.sFish++;
                soundEffects();
                enemyFishFish.initiate=true;
            }
            if((enemyFishFish.fishType==1)&&(fishFish.sFish>9))//if eaten 10 small fish
            {
                fishFish.mFish++;//then allowed to eat middleFish
                soundEffects();
                enemyFishFish.initiate=true;
            }else if((enemyFishFish.fishType==1)&&(fishFish.sFish<=9))//less than 10 small fishes(10 and counting down)
            {
                contact=true;//middle fish eat
                soundEffects();
                gameOver();
            }
            if((enemyFishFish.fishType==2)&&(fishFish.mFish>9))
            {
                fishFish.lFish++;
                soundEffects();
                enemyFishFish.initiate=true;
            }else if((enemyFishFish.fishType==2)&&(fishFish.mFish<=9))
            {
                contact=true;
                soundEffects();
                gameOver();
            }
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if((contact==false)&&(win==false))
            g.drawImage(background.getImage(), 0,0,null);
        if(contact==true)
            g.drawImage(loseBackground.getImage(), 0,0,null);
        if(win==true)
            g.drawImage(winBackground.getImage(), 0,0,null);
    }

    public void gameOver()
    {
        AudioPlayer.player.stop(BGM);
        music();
        repaint();
        if (JOptionPane.showConfirmDialog(null, "Play Again?","message",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            reset();
        }
        else
        {
            board.dispatchEvent(new WindowEvent(board, WindowEvent.WINDOW_CLOSING));
        }

    }

    //resets entire game
    public void reset()
    {
        AudioPlayer.player.stop(BGM);
        fishFish.sFish=0;
        fishFish.mFish=0;
        fishFish.lFish=0;
        contact=false;
        win=false;
        music();
        repaint();
    }

    public void randomFishLarge(fish changeFishFish, JLabel changeFish)
    {
        int chooseType=(int)(Math.random()*3);
        //int chooseType=2;
        int chooseSide=(int)(Math.random()*2);//left=0right=1
        int chooseSpeed=(int)(Math.random()*12)+6;
        int choosePlacement=(int)(Math.random()*1288)+15;
        changeFishFish.chooseType=chooseType;
        changeFishFish.chooseSide=chooseSide;
        changeFishFish.chooseSpeed=chooseSpeed;
        changeFishFish.choosePlacement=choosePlacement;
        if(chooseType==0)
        {
            if(chooseSide==0)
            {
                changeFish.setIcon(fl1l);
                changeFishFish.override(1300,choosePlacement);
                //changeFishFish.
            }else{
                changeFish.setIcon(fl1r);
                changeFishFish.override(0,choosePlacement);
            }
            changeFish.setLocation(changeFishFish.getX(),changeFishFish.getY());
        }else if(chooseType==1)
        {
            if(chooseSide==0)
            {
                changeFish.setIcon(fl2l);
                changeFishFish.override(1300,choosePlacement);
            }else{
                changeFish.setIcon(fl2r);
                changeFishFish.override(0,choosePlacement);
            }
            changeFish.setLocation(changeFishFish.getX(),changeFishFish.getY());
        }else if(chooseType==2)
        {
            if(chooseSide==0)
            {
                changeFish.setIcon(fl4l);
                changeFishFish.override(1300,choosePlacement);
                //changeFishFish.
            }else{
                changeFish.setIcon(fl4r);
                changeFishFish.override(0,choosePlacement);
            }
            changeFish.setLocation(changeFishFish.getX(),changeFishFish.getY());
        }
    }

    public void randomFishMedium(fish changeFishFish, JLabel changeFish)
    {
        int chooseType=(int)(Math.random()*3);
        //int chooseType=2;
        int chooseSide=(int)(Math.random()*2);//left=0right=1
        int chooseSpeed=(int)(Math.random()*12)+6;
        int choosePlacement=(int)(Math.random()*1288)+15;
        changeFishFish.chooseType=chooseType;
        changeFishFish.chooseSide=chooseSide;
        changeFishFish.chooseSpeed=chooseSpeed;
        changeFishFish.choosePlacement=choosePlacement;
        if(chooseType==0)
        {
            if(chooseSide==0)
            {
                changeFish.setIcon(fm1l);
                changeFishFish.override(1300,choosePlacement);
                //changeFishFish.
            }else{
                changeFish.setIcon(fm1r);
                changeFishFish.override(0,choosePlacement);
            }
            changeFish.setLocation(changeFishFish.getX(),changeFishFish.getY());
        }else if(chooseType==1)
        {
            if(chooseSide==0)
            {
                changeFish.setIcon(fm2l);
                changeFishFish.override(1300,choosePlacement);
            }else{
                changeFish.setIcon(fm2r);
                changeFishFish.override(0,choosePlacement);
            }
            changeFish.setLocation(changeFishFish.getX(),changeFishFish.getY());
        }else if(chooseType==2)
        {
            if(chooseSide==0)
            {
                changeFish.setIcon(fm4l);
                changeFishFish.override(1300,choosePlacement);
                //changeFishFish.
            }else{
                changeFish.setIcon(fm4r);
                changeFishFish.override(0,choosePlacement);
            }
            changeFish.setLocation(changeFishFish.getX(),changeFishFish.getY());
        }
    }

    public void randomFishSmall(fish changeFishFish, JLabel changeFish)
    {
        int chooseType=(int)(Math.random()*3);
        //int chooseType=2;
        int chooseSide=(int)(Math.random()*2);//left=0right=1
        int chooseSpeed=(int)(Math.random()*12)+6;
        int choosePlacement=(int)(Math.random()*1288)+15;
        changeFishFish.chooseType=chooseType;
        changeFishFish.chooseSide=chooseSide;
        changeFishFish.chooseSpeed=chooseSpeed;
        changeFishFish.choosePlacement=choosePlacement;
        if(chooseType==0)
        {
            if(chooseSide==0)
            {
                changeFish.setIcon(fishSmall1l);
                changeFishFish.override(1300,choosePlacement);
                //changeFishFish.
            }else{
                changeFish.setIcon(fishSmall1r);
                changeFishFish.override(0,choosePlacement);
            }
            changeFish.setLocation(changeFishFish.getX(),changeFishFish.getY());
        }else if(chooseType==1)
        {
            if(chooseSide==0)
            {
                changeFish.setIcon(fishSmall2l);
                changeFishFish.override(1300,choosePlacement);
            }else{
                changeFish.setIcon(fishSmall2r);
                changeFishFish.override(0,choosePlacement);
            }
            changeFish.setLocation(changeFishFish.getX(),changeFishFish.getY());
        }else if(chooseType==2)
        {
            if(chooseSide==0)
            {
                changeFish.setIcon(fishSmall4l);
                changeFishFish.override(1300,choosePlacement);
                //changeFishFish.
            }else{
                changeFish.setIcon(fishSmall4r);
                changeFishFish.override(0,choosePlacement);
            }
            changeFish.setLocation(changeFishFish.getX(),changeFishFish.getY());
        }
    }

    public void moveSide( fish whatFishFish, JLabel whatFish)
    {
        if(whatFishFish.initiate==true)
        {
            if(whatFishFish.fishType==0)
                randomFishSmall(whatFishFish, whatFish);
            if(whatFishFish.fishType==1)
                randomFishMedium(whatFishFish, whatFish);//picks image and starting point/side
            if(whatFishFish.fishType==2)
                randomFishLarge(whatFishFish, whatFish);
            whatFishFish.initiate=false;
        }
        if(whatFishFish.chooseSide==0)
        {
            whatFishFish.moveLeft(whatFishFish.chooseSpeed);
            if(whatFishFish.getX()<5)
            {
                whatFishFish.initiate=true;
            }
        }else if(whatFishFish.chooseSide==1)
        {
            whatFishFish.moveRight(whatFishFish.chooseSpeed);
            if(whatFishFish.getX()>1295)
            {
                whatFishFish.initiate=true;
            }
        }
    }

    public void timerAction()
    {
        moveSide(enemyFishSFish,enemyFishS);
        moveSide(enemyFishSFish2,enemyFishS2);
        moveSide(enemyFishSFish3, enemyFishS3);
        moveSide(enemyFishSFish4, enemyFishS4);
        moveSide(enemyFishMFish, enemyFishM);
        moveSide(enemyFishMFish2, enemyFishM2);
        moveSide(enemyFishMFish3, enemyFishM3);
        moveSide(enemyFishLFish, enemyFishL);
        moveSide(enemyFishLFish2, enemyFishL2);
        enemyFishS.setLocation(enemyFishSFish.getX(),enemyFishSFish.getY());
        contact(enemyFishSFish, enemyFishS);
        enemyFishS2.setLocation(enemyFishSFish2.getX(),enemyFishSFish2.getY());
        contact(enemyFishSFish2, enemyFishS2);
        enemyFishS3.setLocation(enemyFishSFish3.getX(),enemyFishSFish3.getY());
        contact(enemyFishSFish3, enemyFishS3);
        enemyFishS4.setLocation(enemyFishSFish4.getX(),enemyFishSFish4.getY());
        contact(enemyFishSFish4, enemyFishS4);
        enemyFishM.setLocation(enemyFishMFish.getX(),enemyFishMFish.getY());
        contact(enemyFishMFish, enemyFishM);
        enemyFishM2.setLocation(enemyFishMFish2.getX(),enemyFishMFish2.getY());
        contact(enemyFishMFish2, enemyFishM2);
        enemyFishM3.setLocation(enemyFishMFish3.getX(),enemyFishMFish3.getY());
        contact(enemyFishMFish3, enemyFishM3);
        enemyFishL.setLocation(enemyFishLFish.getX(),enemyFishLFish.getY());
        contact(enemyFishLFish, enemyFishL);
        enemyFishL2.setLocation(enemyFishLFish2.getX(),enemyFishLFish2.getY());
        contact(enemyFishLFish2, enemyFishL2);
    }
}