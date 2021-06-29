package AngryBirdsApplication;

import AngryBirdsCharacters.BodyInfo;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 *
 * @author Sumomoxiao
 */
public class AngryBirdsDraw  {
    private AngryBirdsPanel viewport;
    private AngryBirdsArea stagetodraw;
    private ArrayList<Body> birds;
    private ArrayList<Body> pigs;
    private ArrayList<Body> obstacles;
    private LinkedList<Vec2> contactpoint;
    private Body ground;
    private AngryBirdsViewportTransform vpt;
    private AffineTransform transform = new AffineTransform();
    private ImageIcon grass = new ImageIcon("src/AngryBirdsImagePack/grass.png");
    private ImageIcon planet = new ImageIcon("src/AngryBirdsImagePack/planet.png");
    private ImageIcon sling = new ImageIcon("src/AngryBirdsImagePack/slingstick.png");
    private BodyInfo tempinfo;
    private Body tempbody;
    private Vec2 dpos = new Vec2();
    private Vec2 cpos = new Vec2();
    private float angle;
    private int height, width;
    private RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_SPEED);
    private Vec2 gpos = new Vec2();
    private float grasswidth;
    private float grassheight;
    private float planetwidth, planetheight;
    private int groundwidth, groundheight, grassx, planetx;
    private Vec2 slingAnchor1 = new Vec2();
    private Vec2 slingpos = new Vec2();
    private int swidth, sheight;

    public AngryBirdsDraw(AngryBirdsPanel v) {
        contactpoint = new LinkedList<Vec2>();
        vpt = new AngryBirdsViewportTransform(v);
        viewport = v;
    }

    public void setStage(AngryBirdsArea s) {
        stagetodraw = s;
        birds = s.getBirds();
        pigs = s.getPigs();
        ground = s.getGround();
        obstacles = s.getObstacles();
    }

    public synchronized void drawStage() {
        if (viewport.render()) {
            drawBackground();
            drawBirds();
            drawPigs();
            drawObstacles();
            drawSling();
            viewport.drawCursor(this.getGraphics());
            viewport.paintscence();
        }
    }
    
    private synchronized void drawSomething(ArrayList<Body> bodys) {
        Graphics2D pen = getGraphics();
        pen.setRenderingHints(rh);
        if (bodys.size() > 0) {
            for (int i = 0; i < bodys.size(); i++) {
                if (bodys.get(i) != null) {
                    tempbody = bodys.get(i);
                    /*
                     * change coordinates to draw...
                     */
                    angle = -tempbody.getAngle();
                    dpos = tempbody.getPosition().clone();
                    cpos = tempbody.getPosition().clone();
                    dpos.x = dpos.x - ((BodyInfo) tempbody.getUserData()).getHafwidth();
                    dpos.y = dpos.y + ((BodyInfo) tempbody.getUserData()).getHafheight();
                    
                    this.getPosToDraw(dpos, dpos);
                    this.getPosToDraw(cpos, cpos);

                    tempinfo = (BodyInfo) tempbody.getUserData();
                    height = (int) (tempinfo.getHafheight() * 2 * vpt.getScale());
                    width = (int) (tempinfo.getHafwidth() * 2 * vpt.getScale());

                    if (angle != 0) {
                        this.getTransPos(angle, cpos);
                    }
                    
                    pen.setTransform(transform);
                    pen.drawImage(tempinfo.getAppearance(), (int) dpos.x, (int) dpos.y , width, height, null);

                    //clear Transform,preparing to draw next .
                    transform.setToIdentity();
                    pen.setTransform(transform);
                }
            }
        }
    }

    private void drawPigs() {
        if (ableToDraw() == false) {
            return;
        }
        drawSomething(pigs);

    }

    private void drawBirds() {
        if (ableToDraw() == false) {
            return;
        }
        drawSomething(birds);
    }

    private void drawObstacles() {
        if (ableToDraw() == false) {
            return;
        }
        drawSomething(obstacles);
    }

    private void drawSling() {
        swidth = (int) (30 * vpt.getScale() / 18f);
        sheight = (int) (115 * vpt.getScale() / 18f);

        Stroke rubberStroke = new BasicStroke(4.0f * vpt.getScale() / 18f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        Graphics2D pen = getGraphics();
        pen.setStroke(rubberStroke);
        pen.setColor(new Color(48, 23, 8));

        slingAnchor1 = stagetodraw.getBirds().get(stagetodraw.birdbullets).getPosition().clone();
        slingpos = stagetodraw.slingAnchor.clone();
        slingpos.y += 0.5;
        slingpos.x -= 0.5f;
        getPosToDraw(slingAnchor1, slingAnchor1);
        getPosToDraw(slingpos, slingpos);
        if (!stagetodraw.isMouseJointNull()) {
            pen.drawLine((int) slingAnchor1.x, (int) slingAnchor1.y, (int) (slingpos.x + 16 * vpt.getScale() / 18f),
            		(int) (slingpos.y + 12 * vpt.getScale() / 18f));
            pen.drawLine((int) slingAnchor1.x, (int) slingAnchor1.y, (int) (slingpos.x),
            		(int) (slingpos.y + 12 * vpt.getScale() / 18f));

        }
        pen.drawImage(sling.getImage(), (int) slingpos.x - 5, (int) slingpos.y, swidth, sheight, null);
    }

    public void pushContactPoint(Vec2 cp) {
       Vec2 tempcp = new Vec2();
       this.getPosToDraw(cp, tempcp);
       contactpoint.push(tempcp);
    }

    private void drawBackground() {
        if (ableToDraw() == false) {
            return;
        }
        Graphics2D pen = getGraphics();
        pen.setRenderingHints(rh);
        gpos = ground.getPosition().clone();
        gpos.x = gpos.x - 32.0f;
        gpos.y = gpos.y + 0.5f;
        this.getPosToDraw(gpos, gpos);
        grasswidth = grass.getImage().getWidth(null) + (vpt.getScale() - 18f) * 2;
        grassheight = grasswidth * (34 / 334f);
        planetwidth = (int) (planet.getImage().getWidth(null) + (vpt.getScale() - 18f) * 2);
        planetheight = planetwidth * (56 / 348f);
        groundwidth = (int) ((((BodyInfo) ground.getUserData()).getAppearance().getWidth(null)
        		+ (vpt.getScale() - 18f) * 5) / 1.6f);
        groundheight = (int) (groundwidth * (190 / 334f));
        grassx = (int) gpos.x;
        planetx = (int) gpos.x;

        for (int i = 0; i <= 6; i++) {
            pen.drawImage(planet.getImage(), (int) planetx, (int) gpos.y - planet.getImage().getHeight(null) + 2,
            		(int) planetwidth, (int) planetheight, null);
            planetx += planetwidth;
        }

        for (int i = 0; i <= 6; i++) {
            pen.drawImage(((BodyInfo) ground.getUserData()).getAppearance(), (int) gpos.x, (int) gpos.y,
            		groundwidth, groundheight, null);
            pen.drawImage(grass.getImage(), (int) grassx, (int) gpos.y - grass.getImage().getHeight(null) + 2,
            		(int) grasswidth, (int) grassheight, null);
            gpos.x += groundwidth;
            grassx += grasswidth;
        }
    }

    public boolean ableToDraw() {
        if (stagetodraw != null) {
            return true;
        }
        System.out.print("cant draw \n");
        return false;
    }

    public Graphics2D getGraphics() {
        return viewport.getDBDraw();
    }

    public void getPosToDraw(Vec2 world, Vec2 out) {
        Vec2 tempworld = world.clone();
        vpt.getWorldtoScreen(tempworld, out);
    }

    public AffineTransform getTransPos(float angel, Vec2 anchorpoint) {
        return vpt.rotatePoint(transform, angle, anchorpoint);
    }

    public void getScreenToWorldToOut(Vec2 pos, Vec2 out) {
        vpt.getScreentoWorld(pos, out);
    }
}
