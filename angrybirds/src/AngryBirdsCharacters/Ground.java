package AngryBirdsCharacters;

import javax.swing.ImageIcon;

import AngryBirdsApplication.Constants;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;

import java.awt.Image;

/**
 *
 * @author SONY
 */
public class Ground extends BodyInfo {
    public BodyDef gdef = new BodyDef();
    public FixtureDef gfix = new FixtureDef();
    public PolygonShape gshape = new PolygonShape();
    ImageIcon gimage = new ImageIcon(Constants.RES_PATH + "ground.png");

    public Ground() {
        gdef.position.set(32f, -5.5f);
        gfix.friction = 0.7f;
        gfix.density = 0f;
        gshape.setAsBox(60f, 0.5f);
        gfix.shape = gshape;
        gfix.filter.groupIndex = 0;
    }

    public Body createGround(World dad) {
        Body ground = dad.createBody(gdef);
        ground.m_userData = this;
        ground.createFixture(gfix);
        return ground;
    }

	@Override
	public Image getAppearance() {
		return gimage.getImage();
	}

	@Override
	public void say() {
		// TODO Auto-generated method stub
		System.out.println("My name is : " + getName());
	}
}
