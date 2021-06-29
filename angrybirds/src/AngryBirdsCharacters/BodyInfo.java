package AngryBirdsCharacters;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Sumomoxiao
 */
public abstract class BodyInfo extends Character implements IBody {
    private ImageIcon appearance;
    private String name;
    private float hafwidth = 0;
    private float hafheight = 0;
    private Body body;
	private World world;
	private Vec2 pos;
	
	public BodyInfo() {
		appearance = new ImageIcon("src/AngryBirdsImagePack/cross.png");
        this.name = "unknown";
	}

    public BodyInfo(World world, Vec2 pos) {
    	appearance = new ImageIcon("src/AngryBirdsImagePack/cross.png");
        this.name = "unknown";
        this.world = world;
		this.pos = pos;
    }
    
    public Body create(float angle, float hafHeight, float hafWidth, String name) {
    	System.out.println(getClass().getName() + ": create");
		PolygonShape obsshape = new PolygonShape();
		setCharactershape(obsshape);
		obsshape.setAsBox(hafWidth, hafHeight);
		getCharacterdef().position.set(pos);
		getCharacterdef().angle = angle;
		getCharacterfixdef().filter.groupIndex = 1;
		getCharacterfixdef().density = 0.5f;
		getCharacterfixdef().restitution = 0.01f;
        getCharacterfixdef().friction = 0.5f;
        getCharacterfixdef().userData = this;
		
        body = world.createBody(getCharacterdef());
		setName(name);
		setHafheight(hafHeight);
		setHafwidth(hafWidth);
		body.m_userData = this;
		body.createFixture(getCharacterfixdef());
		return body;
	}
    
    public Body create(float hafHeight, float hafWidth) {
		CircleShape charactershape = new CircleShape();
		getCharacterdef().position.set(pos);
		getCharacterdef().linearDamping=0.01f;
		getCharacterfixdef().filter.groupIndex=-1;
		getCharacterfixdef().shape = charactershape;
		
		body = world.createBody(getCharacterdef());
		charactershape.m_radius = 0.5f;
		setName(name);
		setHafheight(hafHeight);
		setHafwidth(hafWidth);
		body.m_userData = this;
		body.createFixture(getCharacterfixdef());
		return body;
	}
    
    public void setAppearance(Image newapp){
        appearance.setImage(newapp);
    }
    
    public void setName(String newname) {
        this.name = newname;
    }

    public float getHafwidth() {
        return hafwidth;
    }

    public float getHafheight() {
        return hafheight;
    }

    public void setHafheight(float hafheight) {
        this.hafheight = hafheight;
    }

    public void setHafwidth(float hafwidth) {
        this.hafwidth = hafwidth;
    }

    public String getName() {
        return name;
    }
}
