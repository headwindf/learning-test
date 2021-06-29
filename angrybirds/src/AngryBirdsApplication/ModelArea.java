package AngryBirdsApplication;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.WeldJoint;
import org.jbox2d.dynamics.joints.WeldJointDef;

import AngryBirdsCharacters.Ground;
import AngryBirdsCharacters.GameModel;
import AngryBirdsCharacters.GameModel.BIRDTYPE;

/**
 *
 * @author Sumomoxiao
 */
public class ModelArea extends AngryBirdsArea {
	
    public ModelArea() {
        super();
    }

    @Override
    public void initStage() {
        Ground angryBirdsGround = new Ground();
        GameModel model = new GameModel();
        ground = angryBirdsGround.createGround(sworld);
        Vec2 pos = new Vec2();

        //create birds
        for (int i = 0; i < Constants.BIRDS_NUMBER; i++) {
        	this.getBirds().add(model.createBirds(sworld, 
            		BIRDTYPE.LITTLE_BIRD, pos));
		}
        
        //create pigs,wood
        pos.set(40, -3.5f);
        float dheight = 1.2f;
        for (int j = 0; j < 3; j++) {
            if (j == 0) {
                dheight = 0;
            }
            this.getObstacles().add(model.createPigs(sworld, 
            		pos.set(pos.x, pos.y + dheight), (float) Math.PI / 2));

            dheight = 1.2f;
            this.getObstacles().add(model.createWood(sworld, 
            		pos.set(pos.x, pos.y + dheight), 0));
        }

        birdbullets = 0;
        pos.set(8f, 1f);
        WeldJointDef weldJointDef = new WeldJointDef();
        slingAnchor.set(pos);
        weldJointDef.bodyA = ground;
        weldJointDef.bodyB = getBirds().get(0);
        weldJointDef.localAnchorA.set(pos.sub(ground.getPosition()));
        attachDef = weldJointDef;
        attach = (WeldJoint) sworld.createJoint(weldJointDef);
        getBirds().get(0).setTransform(pos, 0);
    }
}
