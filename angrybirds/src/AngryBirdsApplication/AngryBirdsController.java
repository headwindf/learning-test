package AngryBirdsApplication;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

/**
 *
 * @author Sumomoxiao
 */
public class AngryBirdsController extends MouseAdapter implements Runnable, MouseMotionListener, ContactListener {
    private AngryBirdsArea angryBirdsArea;
    private AngryBirdsPanel birdsPanel;
    private final AngryBirdsDraw drawer;
    private Thread gamethread;
    private boolean stop = true;
    private Fixture fix;

    public AngryBirdsController(AngryBirdsArea angryBirdsArea, AngryBirdsPanel birdsPanel){
        this.angryBirdsArea = angryBirdsArea;
        this.birdsPanel = birdsPanel;
        drawer = birdsPanel.getSDDraw();
        birdsPanel.setStageController(this);
        angryBirdsArea.initStage();
        drawer.setStage(angryBirdsArea);
        this.addListener();
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (!stop) {
                	angryBirdsArea.update();
                    drawer.drawStage();
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                    }
                }
                drawer.drawStage();
                Thread.sleep(12);
            } catch (InterruptedException ex) {
            	ex.printStackTrace();
            }
        }
    }

    public void start() {
        if (gamethread == null) {
            stop = false;
            gamethread = new Thread(this);
            gamethread.start();
        }
    }

    public void pause() {
        if (isPainting()) {
            stop = true;
        }
    }

    public void resume() {
        if (!isPainting()) {
            stop = false;
        }
    }

    public boolean isPainting() {
        return birdsPanel.isPainting();
    }

    public void addListener() {
    	birdsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (angryBirdsArea != null) {
                    Vec2 pos = new Vec2(e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        drawer.getScreenToWorldToOut(pos, pos);
                        angryBirdsArea.queueMouseDown(pos);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (angryBirdsArea != null) {
                    Vec2 pos = new Vec2(e.getX(), e.getY());
                    drawer.getScreenToWorldToOut(pos, pos);
                    angryBirdsArea.queueMouseUp(pos);
                }
            }
        });

    	birdsPanel.addMouseMotionListener(new MouseMotionListener() {
            final Vec2 pos = new Vec2();
            final Vec2 pos2 = new Vec2();
            public void mouseDragged(MouseEvent e) {
                pos.set(e.getX(), e.getY());
                if (angryBirdsArea != null) {
                    drawer.getScreenToWorldToOut(pos, pos);
                    angryBirdsArea.queueMouseMove(pos);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                pos2.set(e.getX(), e.getY());
                if (angryBirdsArea != null) {
                    drawer.getScreenToWorldToOut(pos2, pos2);
                    angryBirdsArea.queueMouseMove(pos2);
                }
            }
        });
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
       if (contact.m_fixtureA.m_filter.groupIndex == -1 || contact.m_fixtureB.m_filter.groupIndex == -1) {
    	   fix = contact.m_fixtureA.m_filter.groupIndex == -1?contact.m_fixtureA:contact.m_fixtureB;
    	   for (int i = 0; i < contact.getManifold().pointCount; i++) {
    		   if (impulse.normalImpulses[i] > 0.8) {
    			   drawer.pushContactPoint(fix.m_body.getPosition());
    		   }
    	   }
       }
       if (contact.m_fixtureA.m_filter.groupIndex == 1 || contact.m_fixtureB.m_filter.groupIndex == 1) {
    	   for (int i = 0; i < contact.getManifold().pointCount; i++) {
    		   if (impulse.normalImpulses[i] > 3.1f) {
    			   return;
    		   }
    	   }
       }
    }
    
    public void setStop(boolean stop) {
		this.stop = stop;
	}
    
    public boolean isGameThreadNull() {
		return gamethread == null;
	}
    
    public boolean isGameThreadAlive() {
		if (isGameThreadNull()) {
			return false;
		}
		return gamethread.isAlive();
	}

	@Override
	public void beginContact(Contact arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub

	}
}
