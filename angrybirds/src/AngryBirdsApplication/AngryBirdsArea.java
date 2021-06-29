package AngryBirdsApplication;

import java.util.ArrayList;
import java.util.LinkedList;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;

/**
 *
 * @author Sumomoxiao
 */
enum QueueItemType {
    MouseDown, MouseMove, MouseUp, KeyPressed, KeyReleased
}

class QueueItem {
    public QueueItemType type;
    public Vec2 vec;

    public QueueItem(QueueItemType type, Vec2 vec) {
        this.type = type;
        this.vec = vec;
    }
}

class FixtureQueryCallback implements QueryCallback {
    private final Vec2 point;
    private Fixture fixture;

    public FixtureQueryCallback() {
        point = new Vec2();
        setFixture(null);
    }

    /**
     * @see
     * org.jbox2d.callbacks.QueryCallback#reportFixture(org.jbox2d.dynamics.Fixture)
     */
    public boolean reportFixture(Fixture argFixture) {
        Body body = argFixture.getBody();
        if (body.getType() == BodyType.DYNAMIC) {
            boolean inside = argFixture.testPoint(point);
            if (inside) {
                setFixture(argFixture);
                return false;
            }
        }
        return true;
    }

	public Fixture getFixture() {
		return fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public Vec2 getPoint() {
		return point;
	}
}

public abstract class AngryBirdsArea {
    private MouseJoint mouseJoint;
    private Vec2 mouseWorld = new Vec2();
    protected final World sworld;
    private final Vec2 gravity;
    protected Vec2 slingAnchor;
    private ArrayList<Body> birdlist;
    private ArrayList<Body> oblist;
    private ArrayList<Body> piglist;
    protected WeldJoint attach;
    protected WeldJointDef attachDef;
    protected Body ground;
    public float scale = 1 / 64f;
    private float timeStep = 1.0f / 60.0f;
    private int velocityIterations = 6;
    private int positionIterations = 2;
    protected int birdbullets;
    private final LinkedList<QueueItem> inputQueue;
    /**
     * Called for mouse-up
     *
     * @param p
     */
    private long releasetime = 0;
    private long endtime = 0;
    private long duration = 0;  //duration of release the bird;
    private final AABB queryAABB = new AABB();
    private final FixtureQueryCallback callback = new FixtureQueryCallback();
    /**
     * Process MouseEvent.
     *
     * @param p,length
     */
    Vec2 length;

    abstract public void initStage();
    
    public AngryBirdsArea() {
        gravity = new Vec2(0, -10f);
        inputQueue = new LinkedList<QueueItem>();
        sworld = new World(gravity);
        birdlist = new ArrayList<Body>();
        oblist = new ArrayList<Body>();
        piglist = new ArrayList<Body>();
        slingAnchor = new Vec2();
    }

    public void step() {
        sworld.step(timeStep, velocityIterations, positionIterations);
        if (mouseJoint == null && attach == null) { 
            endtime = System.currentTimeMillis();
            duration = (endtime - releasetime) / 1000;
        }

        if (duration > 3 && attach == null) {
            if(birdbullets <= birdlist.size()) {
	            birdlist.get(birdbullets).setTransform(slingAnchor, 0);
	            attachDef.bodyB = birdlist.get(birdbullets);
	            attach = (WeldJoint) this.getWorld().createJoint(attachDef);
	            duration = 0;
            }
        }
    }

    public ArrayList<Body> getBirds() {
        return birdlist;
    }

    public ArrayList<Body> getObstacles() {
        return oblist;
    }

    public ArrayList<Body> getPigs() {
        return piglist;
    }

    public World getWorld() {
        return sworld;
    }

    public Body getGround() {
        return ground;
    }

    public void setGravity(Vec2 gra) {
        sworld.setGravity(gra);
    }

    public boolean isMouseJointNull() {
        if(mouseJoint == null) {
        	return true;
        }  
        return false;
    }

    public void update() {
        if (!inputQueue.isEmpty()) {
            synchronized (inputQueue) {
                while (!inputQueue.isEmpty()) {
                    QueueItem i = inputQueue.pop();
                    switch (i.type) {
                        case MouseDown:
                            mouseDown(i.vec);
                            break;
                        case MouseMove:
                            mouseMove(i.vec);
                            break;
                        case MouseUp:
                            mouseUp(i.vec);
                            break;
						default:
							break;
                    }
                }
            }
        }
        step();
    }

    public void mouseUp(Vec2 p) {
        float length = 0;
        Vec2 pos = new Vec2();
        pos = p.sub(slingAnchor);
        length = pos.length();

        if (length > 3) {
            pos.x /= length / 3;
            pos.y /= length / 3;
            p = pos;
        }

        if (mouseJoint != null) {
            mouseJoint.getBodyA().setFixedRotation(false);
            mouseJoint.getBodyB().setLinearVelocity(pos.negate().mul(7.5f));  //release and shoot!
            sworld.destroyJoint(mouseJoint);
            mouseJoint = null;
            if(birdbullets < birdlist.size()-1) {
            	birdbullets++;
            }
            releasetime = System.currentTimeMillis();
        }
    }

    /**
     * Called for mouse-down
     *
     * @param p
     */
    public void mouseDown(Vec2 p) {
        mouseWorld.set(p);

        if (mouseJoint != null) {
            return;
        }

        queryAABB.lowerBound.set(p.x - 0.001f, p.y - 0.001f);
        queryAABB.upperBound.set(p.x + 0.001f, p.y + 0.001f);
        callback.getPoint().set(p);
        callback.setFixture(null);
        sworld.queryAABB(callback, queryAABB);

        if (callback.getFixture() != null && callback.getFixture().m_filter.groupIndex == -1) {
            if (attach != null) {
                sworld.destroyJoint(attach);
                attach = null;
            }
            Body body = callback.getFixture().getBody();
            MouseJointDef def = new MouseJointDef();
            def.bodyA = ground;
            def.bodyB = body;
            def.target.set(p);
            def.maxForce = 1000f * body.getMass();
            body.setFixedRotation(true);//NOTE!!!!!!!!!!!!!!!!!
            mouseJoint = (MouseJoint) sworld.createJoint(def);
            body.setAwake(true);
        }
    }

    public void mouseMove(Vec2 p) {
        mouseWorld.set(p);
        Vec2 force = p.sub(slingAnchor);
        float ol = force.length();

        if (force.length() > 3f) {
            force.x /= ol / 3;
            force.y /= ol / 3;
            force.addLocal(slingAnchor);
            p = force;
        }
        if (mouseJoint != null) {
            mouseJoint.setTarget(p);
        }
    }

    public void queueMouseUp(Vec2 p) {
        synchronized (inputQueue) {
            inputQueue.addLast(new QueueItem(QueueItemType.MouseUp, p));
        }
    }

    public void queueMouseDown(Vec2 p) {
        synchronized (inputQueue) {
            inputQueue.addLast(new QueueItem(QueueItemType.MouseDown, p));
        }
    }

    public void queueMouseMove(Vec2 p) {
        synchronized (inputQueue) {
            inputQueue.addLast(new QueueItem(QueueItemType.MouseMove, p));
        }
    }
}
