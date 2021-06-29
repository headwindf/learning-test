package AngryBirdsApplication;

import java.awt.geom.AffineTransform;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;

/**
 * This class provide Rotation and Translation Position that used for rendering game.
 * @author Sumomoxiao
 */
public class AngryBirdsViewportTransform {
    private OBBViewportTransform vpt = new OBBViewportTransform();
    private float scale = 18.0f;
    private Vec2 center = new Vec2();
    private Vec2 offset = new Vec2();
    
    public AngryBirdsViewportTransform(AngryBirdsPanel v) {
        vpt.setYFlip(true);
        vpt.setExtents(v.getWidth()/2, v.getHeight()/2);
        center.set(v.getWidth()/2, v.getHeight()/2+170f);
        offset.set(-v.getWidth()/2, 170f);
    }
    
    public void getWorldtoScreen(Vec2 worldpos,Vec2 out) {
        worldpos.x = (worldpos.x)*scale;
        worldpos.y = worldpos.y*scale;
        vpt.getWorldToScreen(worldpos, out);
        out.addLocal(offset);
    }
    
    public void getScreentoWorld(Vec2 screenpos,Vec2 worldpos) {
        screenpos.subLocal(offset);
        vpt.getScreenToWorld(screenpos, worldpos);     
        screenpos.x = screenpos.x/scale;
        screenpos.y = screenpos.y/scale;
    }
    
    public AffineTransform rotatePoint(AffineTransform dtrans,float angle,Vec2 anchorp) {
        //Create AffineTransform Instance
        dtrans.rotate(angle, anchorp.x, anchorp.y);//Rotate Round the Anchorpoint.
        return dtrans;
    }

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}
