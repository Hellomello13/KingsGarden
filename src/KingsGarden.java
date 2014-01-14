import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class KingsGarden extends StateBasedGame {

	public static final int MAINMENUSTATE		= 0;
	public static final int GAMEPLAYSTATE		= 1;
	public static final int OPTIONSMENUSTATE	= 2;
	public static final int PAUSESTATE		= 3;
	//public static final int LEVELSELECTSTATE	= 4;
	
	public static int FPS = 60;
	
	public KingsGarden()
	{
		super("King's Garden");
	}
	
	public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new KingsGarden());
 
         app.setDisplayMode(800, 640, false);
         app.setTargetFrameRate(FPS);
         app.start();
    }
	
	@Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MainMenuState(MAINMENUSTATE));
        //this.addState(new LevelSelectState(LEVELSELECTSTATE));
        //this.addState(new OptionsMenuState(OPTIONSMENUSTATE));
        this.addState(new GameplayState(GAMEPLAYSTATE));
        //this.addState(new PauseState(PAUSESTATE));
    }
}