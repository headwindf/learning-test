package AngryBirdsApplication;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import org.jbox2d.common.Vec2;
    
/**
 *
 * @author Sumomoxiao
 */
@SuppressWarnings("serial")
public class AngryBirdsMenu extends JPanel{
	private ImageIcon background = new ImageIcon(Constants.MENU_RES_PATH + "menuBackground.jpg");
	private ImageIcon title = new ImageIcon(Constants.MENU_RES_PATH + "title.png");
	private ImageIcon cursor = new ImageIcon(Constants.RES_PATH + "finger.png");
	private ArrayList<ImageIcon> start = new ArrayList<ImageIcon>();
	private ArrayList<ImageIcon> exit = new ArrayList<ImageIcon>();
    private int startButtonScale = 0, 
    		exitButtonScale = 0;
    private int tx = 150;
    private int ty = 191/2, ty1 = 75/2;
    private boolean flag = false;
    private Graphics2D dbg;
    private Image dbImage;
    private Vec2 cursorpoint = new Vec2();
    private RenderingHints rh = new RenderingHints(
             RenderingHints.KEY_RENDERING,
             RenderingHints.VALUE_RENDER_DEFAULT);

    public AngryBirdsMenu() {
        super();
        this.setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
        this.setDoubleBuffered(true);
        start.add(new ImageIcon(Constants.MENU_RES_PATH + "startButtonSmall.png"));
        start.add(new ImageIcon(Constants.MENU_RES_PATH + "startButtonBig.png"));
        exit.add(new ImageIcon(Constants.MENU_RES_PATH + "exitSmall.png"));
        exit.add(new ImageIcon(Constants.MENU_RES_PATH + "exitBig.png"));
        rh.add(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
        		RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
    }
    
    public void setCursorpoint(int x, int y) {
    	cursorpoint.set(x, y);
	}

    @Override
    public int getHeight() {
        return Constants.HEIGHT;
    }

    @Override
    public int getWidth() {
        return Constants.WIDTH;
    }

    public boolean render() {
		if (dbImage == null) {
			dbImage = createImage(Constants.WIDTH, Constants.HEIGHT);
			if (dbImage == null) {
				return false;
			}
			dbg = (Graphics2D) dbImage.getGraphics();  
		}
		dbg.setColor(Color.DARK_GRAY);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		dbg.drawImage(background.getImage(), 0, 0, Constants.WIDTH,Constants.HEIGHT,null);
		dbg.drawImage(start.get(startButtonScale).getImage(), Constants.WIDTH/2-tx, Constants.HEIGHT/2-ty+20,null);
		dbg.drawImage(title.getImage(), Constants.WIDTH/2-330, 0, null);
		dbg.drawImage(exit.get(exitButtonScale).getImage(), 0, Constants.HEIGHT-80-ty1,null);
		dbg.drawImage(cursor.getImage(),(int) cursorpoint.x,(int) cursorpoint.y, 25, 35, this);
		return true;
    }

    public void paintscence(){
    	flag = false;
        if(Constants.WIDTH!=0 && Constants.HEIGHT!=0) {
            flag = true;
        }
        if(flag) {
	        Graphics2D g = (Graphics2D) this.getGraphics();
	        if(g != null && dbImage != null) {
		        g.drawImage(dbImage, 0, 0, null);
		        Toolkit.getDefaultToolkit().sync();
		        g.dispose();
	        }
        }
    }
    
    public Graphics getMenuDBDraw() {
        return dbImage.getGraphics();
    }
    
    @SuppressWarnings("all")
    public boolean isPainting() {
        return flag;
    }
    
    public void addController(AngryBirdsMenuController menuController){
        this.addMouseListener(menuController);
        this.addMouseMotionListener(menuController);
    }

	public int getExitButtonScale() {
		return exitButtonScale;
	}

	public void setExitButtonScale(int exitButtonScale) {
		this.exitButtonScale = exitButtonScale;
	}

	public int getStartButtonScale() {
		return startButtonScale;
	}

	public void setStartButtonScale(int startButtonScale) {
		this.startButtonScale = startButtonScale;
	}

	public int getTx() {
		return tx;
	}

	public void setTx(int tx) {
		this.tx = tx;
	}

	public int getTy() {
		return ty;
	}

	public void setTy(int ty) {
		this.ty = ty;
	}

	public int getTy1() {
		return ty1;
	}

	public void setTy1(int ty1) {
		this.ty1 = ty1;
	}
}
