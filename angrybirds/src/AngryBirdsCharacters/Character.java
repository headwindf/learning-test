package AngryBirdsCharacters;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import java.awt.Image;

/**
 *
 * @author SONY
 */
public abstract class Character {
    private BodyDef characterdef = new BodyDef();
    private FixtureDef characterfixdef = new FixtureDef();
    public abstract Image getAppearance();
    
    public Character() {
        characterdef.bullet = false;
        characterdef.type = BodyType.DYNAMIC;
        characterdef.allowSleep = true;
        characterfixdef.friction = 0.8f;
        characterfixdef.density = 1f;
        characterfixdef.restitution = 0.4f;
    }

    public void setPosition(Vec2 worldpos) {
        characterdef.position.set(worldpos);
    }

    public void setCharactershape(Shape charactershape) {
    	this.characterfixdef.shape = charactershape;
    }
    
    public void setCharacterdef(BodyDef characterdef) {
        this.characterdef = characterdef;
    }
    
    public void setCharacterfixturedef(FixtureDef characterfix) {
        this.characterfixdef = characterfix;   
    }

    public BodyDef getCharacterdef() {
        return characterdef;
    }

    public FixtureDef getCharacterfixdef() {
        return characterfixdef;
    }
}
