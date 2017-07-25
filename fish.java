
public class fish
{
    // instance variables - replace the example below with your own
    private int x;
    private int y;
    public int sFish,mFish,lFish=0;
    int fishType;//0small1medium2large3MainFish
    int chooseType,chooseSide,chooseSpeed,choosePlacement;
    boolean initiate;
    public fish(int X, int Y, int fishTypeHere)
    {
        x = X;
        y = Y;
        initiate=true;
        fishType=fishTypeHere;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void override(int overX, int overY)
    {
        x=overX;
        y=overY;
    }

    public void moveLeft(int speed)
    {
        x-=speed;
    }

    public void moveRight(int speed)
    {
        x+=speed;
    }
}
