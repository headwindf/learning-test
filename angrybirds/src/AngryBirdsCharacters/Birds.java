package AngryBirdsCharacters;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import java.awt.Image;

import javax.swing.ImageIcon;

import AngryBirdsApplication.Constants;

public class Birds extends BodyInfo {
	private Body bird;
	private World world;
	private Vec2 pos;
	private static int count = 0;
	
	public Birds(World world, Vec2 pos) {
		super(world, pos);
		this.world = world;
		pos.set(2 + count*2f, -3.5f);
		count ++;
		this.pos = pos;
	}
	
	@Override
	public Body create(float radius, float hafHeight, float hafWidth, String name) {
		System.out.println(getClass().getName() + ": create");
		CircleShape charactershape = new CircleShape();
		getCharacterdef().position.set(pos);
		getCharacterdef().linearDamping=0.01f;
		getCharacterfixdef().filter.groupIndex=-1;
		getCharacterfixdef().shape = charactershape;
		
		bird = world.createBody(getCharacterdef());
		charactershape.m_radius = radius;
		setName(name);
		setHafheight(hafHeight);
		setHafwidth(hafWidth);
		bird.m_userData = this;
		bird.createFixture(getCharacterfixdef());
		return bird;
	}
	
	@Override
	public Image getAppearance() {
		ImageIcon imageIcon = new ImageIcon(
				Constants.RES_PATH + "birds.png");
        return imageIcon.getImage();
    }

	public void say() throws ActionErrorException {
		String name = getName();
		//TODO change his name
		System.out.println("My name is : " + name);
		throw new ActionErrorException();
	}
}
