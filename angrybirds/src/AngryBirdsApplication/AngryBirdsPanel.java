package AngryBirdsApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.jbox2d.common.Vec2;

/**
 *   This class is for display the game screen.
 * 
 *   @author Sumomoxiao
 */
@SuppressWarnings("serial")
class AngryBirdsPanel extends JPanel {
	/*
     * define settings,variables and source Image.
     */
    private final AngryBirdsDraw sdraw;
    private Graphics2D dbg = null;
    private Image dbImage = null;
    private ImageIcon backgrondImg = new ImageIcon("src/AngryBirdsImagePack/background.jpg");
    private ImageIcon cursor = new ImageIcon("src/AngryBirdsImagePack/Finger.png");
    
    private boolean flag = false;
    private Vec2 cursorpoint = new Vec2();
    public AngryBirdsPanel() {
        super();
        sdraw = new AngryBirdsDraw(this);
        this.setBackground(null);
        this.setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
      
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
            	cursorpoint.set(me.getX(), me.getY());                 
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            	cursorpoint.set(e.getX(), e.getY());     
            }
        }); 
    }
    

    public AngryBirdsDraw getSDDraw() {
        return sdraw;
    }
    
    public Graphics2D getDBDraw(){
        return dbg;
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
    	dbg.setColor(null);
    	dbg.drawImage(backgrondImg.getImage(), 0, 0, 
    			Constants.WIDTH, Constants.HEIGHT, null); 
        return true;
    }
    
    public void paintscence(){
        if(Constants.WIDTH!=0 && Constants.HEIGHT!=0) {
            flag=true;
        }
        Graphics2D g = (Graphics2D) this.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
        		RenderingHints.VALUE_ANTIALIAS_ON);
        if(g != null && dbImage != null) {
	        g.drawImage(dbImage, 0, 0, null);
	        Toolkit.getDefaultToolkit().sync();
	        g.dispose();
	    }
    }
    
    public void setStageController(AngryBirdsController birdsController) {
    }
    
    public void drawCursor(Graphics2D g) {
        g.drawImage(cursor.getImage(), (int)cursorpoint.x, 
        		(int)cursorpoint.y, 25, 35, this);
    }
    
    @SuppressWarnings("all")
    public boolean isPainting() {
        return flag;
    }
}
