package AngryBirdsApplication;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

/**
 *
 * @author Sumomoxiao
 */
public class AngryBirdsMenuController extends MouseMotionAdapter implements Runnable, MouseListener
{
    private final int width;
    private final int heigth;
    private final AngryBirdsMenu birdsMenu;
    private Thread menuthread;
    private boolean stop = true;
    private boolean inside1 = false;
    private boolean inside = false;
    private boolean shut = false;
    
    public AngryBirdsMenuController (AngryBirdsMenu birdsMenu) {
    	this.birdsMenu = birdsMenu;
    	heigth = birdsMenu.getHeight();
    	width = birdsMenu.getWidth();
    	birdsMenu.addController(this);
    }
    
    public void start() {
        menuthread = new Thread(this);
        stop = false;
        menuthread.start();
    }
    
    public boolean isStop() {
		return stop;
	}
    
    public boolean isMenuthreadNull() {
		return menuthread == null;
	}
    
    public boolean isMenuthreadAlive() {
		if (isMenuthreadNull()) {
			return false;
		}
		return menuthread.isAlive();
	}
    
    public void resume() {
        stop = false;
    }

    @Override
    public synchronized void run() {
    	while(true) {
            try {
                if(!shut || !stop) { //keeping painting menu when menu painting operation is not actully set to false .
                	if(birdsMenu.render()) {
                		birdsMenu.paintscence();
                	}
                }
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
	public void mouseMoved(MouseEvent e) {
    	birdsMenu.setCursorpoint(e.getX(), e.getY());
    	if(e.getX()<width/2+156 && e.getX()>width/2-156 && 
    			e.getY()>heigth/2-100 && e.getY()<heigth/2+100) {
    		if(!inside) {
    			birdsMenu.setStartButtonScale(1);
    			birdsMenu.setTx(164);
    			birdsMenu.setTy(209/2);
    			inside = true;
    		}
    	} else {
    		inside = false;
    		birdsMenu.setStartButtonScale(0);
    		birdsMenu.setTx(150);
    		birdsMenu.setTy(191/2);
    	}
    	if(e.getX()<85 && e.getX()>0 &&
    			e.getY()>heigth-85 && e.getY()<heigth) {
    		if(!inside1) {
    			birdsMenu.setExitButtonScale(1);
    			birdsMenu.setTy1(87/2);
    			inside1 = true;
    		}
    	} else {
    		inside1 = false;
    		birdsMenu.setExitButtonScale(0);
    		birdsMenu.setTy1(75/2);
    	}
	}
       
	public boolean isPainting(){
		return birdsMenu.isPainting();
	}

    @Override
	public void mouseClicked(MouseEvent e) {
    	if(inside) {
    		shut = true;
			stop = true;
    	}
   
    	if(inside1) {
    		System.exit(0);
    	}
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	return;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	return;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    	return;
    }

    @Override
    public void mouseExited(MouseEvent e) {
    	return;
    }
}
