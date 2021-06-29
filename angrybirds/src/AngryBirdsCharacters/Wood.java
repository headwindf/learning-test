package AngryBirdsCharacters;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.Image;

import javax.swing.ImageIcon;

import AngryBirdsApplication.Constants;

public class Wood extends BodyInfo {
	
	public Wood(World world, Vec2 pos) {
		super(world, pos);
	}
	
	@Override
	public Image getAppearance() {
		ImageIcon imageIcon = new ImageIcon(Constants.RES_PATH + "wood.png");
        return imageIcon.getImage();
    }

	@Override
	public void say() {
		// TODO Auto-generated method stub
		System.out.println("My name is : " + getName());
	}
}
