package AngryBirdsCharacters;

import java.awt.Image;

import javax.swing.ImageIcon;
import AngryBirdsApplication.Constants;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 *
 * @author Sumomoxiao
 */
public class SlingStick extends BodyInfo {
	private Body slingStick;
	private World world;
	private Vec2 pos;
	
	public SlingStick(World world, Vec2 pos) {
		this.world = world;
		this.pos = pos;
	}
	
	public Body create(float angle, float hafHeight, float hafWidth, String name) {
		PolygonShape slingshape = new PolygonShape();
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		slingshape.setAsBox(0.5f, 2.7f);
		bodyDef.type = BodyType.STATIC;
		bodyDef.position = pos;
        setName("stick");
        setHafwidth(0.8f);
        setHafheight(2.7f);
        bodyDef.userData = this;
        fixtureDef.shape = slingshape;
        fixtureDef.density = 1f;
        Body stick = world.createBody(bodyDef);
        slingshape.setAsBox(0.5f,0.1f);
        pos.y = pos.y+1.2f;
        slingStick = world.createBody(bodyDef);
        slingStick.createFixture(slingshape,1);
        stick.createFixture(fixtureDef);
        return slingStick;
	}
	
	@Override
	public Image getAppearance() {
		ImageIcon imageIcon = new ImageIcon(Constants.RES_PATH + "slingstick.png");
        return imageIcon.getImage();
    }

	@Override
	public void say() {
		// TODO Auto-generated method stub
		System.out.println("My name is : " + getName());
	}
}
