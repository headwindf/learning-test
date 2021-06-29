package AngryBirdsApplication;

import java.io.IOException;

/**
 *
 * @author Sumomoxiao
 */
public class AngryBirdsMain {
    /**
     * Entrance of This Game.
     */
    public static void main(String[] args) throws InterruptedException, IOException{
		ModelArea birdsLevel = new ModelArea();
		AngryBirdsMenu angryBirdsMenu = new AngryBirdsMenu();
		AngryBirdsPanel angryBirdsPanel = new AngryBirdsPanel();
		AngryBirdsViewFrame mainframe = new AngryBirdsViewFrame(angryBirdsMenu, angryBirdsPanel);
		AngryBirdsController angryBirdsController = new AngryBirdsController(birdsLevel, angryBirdsPanel);
		AngryBirdsMenuController angryBirdsMenuController = new AngryBirdsMenuController(angryBirdsMenu);
		AngryBirdsPanelController angryBirdsPanelController = new AngryBirdsPanelController(
				mainframe.getLayoutpanel(), angryBirdsMenuController, angryBirdsController);
		birdsLevel.getWorld().setContactListener(angryBirdsController);
		
		Thread switcher = new Thread(angryBirdsPanelController);
		switcher.start();
		mainframe.setVisible(true);
    }
}
