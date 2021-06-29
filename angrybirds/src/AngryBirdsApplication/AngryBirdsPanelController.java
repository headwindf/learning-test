package AngryBirdsApplication;

import java.awt.CardLayout;
import java.awt.event.ComponentAdapter;
import javax.swing.JPanel;

/**
 *
 * @author Sumomoxiao
 */

public class AngryBirdsPanelController extends ComponentAdapter implements Runnable{
    private AngryBirdsMenuController menuController;
    private AngryBirdsController birdsController;
    private JPanel showpanel;
    private CardLayout layout;
    private enum DISPLAYMODE {
		MENU,
		GAME
	};
    private DISPLAYMODE displayMode = DISPLAYMODE.MENU;
    
    AngryBirdsPanelController(JPanel jPanel, AngryBirdsMenuController menuController,
    		AngryBirdsController birdsController) {
        this.menuController = menuController;
        this.birdsController = birdsController;
        showpanel = jPanel;
        layout = (CardLayout) jPanel.getLayout();
    }

    @Override
    public synchronized void run() {
        while(true) {
            whoShow();
            switch(displayMode) {
                case MENU:
                    displayMenu();
                    break;
                case GAME:
                    displayGame();
                    break;
                default:
                	break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public synchronized void whoShow() {
    	//initiallize menupanel.
        if(menuController.isMenuthreadNull() && birdsController.isGameThreadNull()) {
        	displayMode = DISPLAYMODE.MENU;
        }
        //initiallize gamepanel..
        if(!menuController.isMenuthreadNull() && menuController.isStop() && 
        	menuController.isMenuthreadAlive() && birdsController.isGameThreadNull()) {
        	displayMode = DISPLAYMODE.GAME;
        }
       
        // switch between menu and game.
        if(!menuController.isMenuthreadNull() && !birdsController.isGameThreadNull()) {
	        if(!menuController.isMenuthreadAlive() && !birdsController.isGameThreadAlive()) {
	        	displayMode = DISPLAYMODE.MENU;
	        }
	        if(menuController.isMenuthreadAlive() && birdsController.isGameThreadAlive()) {
	        	displayMode = DISPLAYMODE.GAME;
	        }
        }
    }
    
    public void displayMenu() {
        if(menuController.isMenuthreadNull()) {
        	menuController.start();
            layout.show(showpanel, "menu");
        } else if(!menuController.isPainting()){                                  
        	menuController.resume();
            layout.show(showpanel, "menu");
        }
    }
    
	public void displayGame() {
		if(birdsController.isGameThreadNull()) {
			birdsController.start();
			layout.show(showpanel, "game");
        } else {
        	birdsController.resume();
            layout.show(showpanel, "game");
        }
    }
}
