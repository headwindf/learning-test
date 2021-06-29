package AngryBirdsCharacters;

import java.awt.Image;

import javax.swing.ImageIcon;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import AngryBirdsApplication.Constants;

public class Pigs extends BodyInfo {
	
	public Pigs(World world, Vec2 pos) {
		super(world, pos);
	}
	
	@Override
	public Image getAppearance() {
		ImageIcon imageIcon = new ImageIcon(Constants.RES_PATH + "pigs.png");
        return imageIcon.getImage();
    }

	@Override
	public void say() {
		// TODO Auto-generated method stub
		System.out.println("My name is : " + getName());
	}
}
