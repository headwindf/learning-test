package AngryBirdsCharacters;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

/**
 *
 * @author SONY
 */
public class GameModel {
	private BodyInfo bodyInfo;
	
	public enum BIRDTYPE {
		LITTLE_BIRD,
		STRONG_BIRD,
		ANGRY_BIRD
	};
	
	public GameModel() {
		super();
	}

	public Body createBirds(World world, BIRDTYPE birdtype, Vec2 pos) {
		Birds birds = new Birds(world, pos);

		switch(birdtype) {
			case STRONG_BIRD:
				//TODO radius=0.7f;hafHeight=0.7f;hafWidth=0.7f;name=Strong Bird
				return null;
			case ANGRY_BIRD:
				//TODO radius=0.5f;hafHeight=0.5f;hafWidth=0.5f;name=Little Bird
				return null;
			case LITTLE_BIRD:
				return birds.create(0.8f, 0.8f, 0.8f, "Little Bird");
			default:
			    return null;
		}
	}
	
	public Body createPigs(World world, Vec2 pos, float angle) {
		//TODO  create Pigs,hafHeight=0.6f;hafWidth=0.6f;name="pig"
		Pigs pigs = new Pigs(world, pos);
		return pigs.create(angle, 0.6f, 0.6f, "pigs");
		//return null;
	}
	
	public Body createWood(World world, Vec2 pos, float angle) {
		//TODO  create wood,hafHeight=0.2f;hafWidth=1f;name="wood"
		//return null;
	}
}
   
