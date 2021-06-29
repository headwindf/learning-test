package AngryBirdsApplication;

import java.awt.*;
import java.awt.image.MemoryImageSource;
import javax.swing.*;

/**
 *
 * @author Sumomoxiao
 */
@SuppressWarnings("serial")
public class AngryBirdsViewFrame extends JFrame{
    private JPanel layoutpanel;
    private CardLayout layout = new CardLayout();
    private int[] pixels = new int[16 * 16];

    AngryBirdsViewFrame(AngryBirdsMenu menu,AngryBirdsPanel game){
        super();
        layoutpanel = new JPanel(layout);
        layoutpanel.add(game,"game");
        layoutpanel.add(menu,"menu");
        Image transparent = Toolkit.getDefaultToolkit().createImage(
        		new MemoryImageSource(16, 16, pixels, 0, 16));
        this.add(layoutpanel);
        this.setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setResizable(false);
        this.setSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
        this.setTitle("Angry Birds");
        this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
        		transparent,new Point(0,0), "transparent"));
        layout.show(layoutpanel, "menu");
        pack();
    }

    public CardLayout getLayout() {
        return layout;
    }

    public JPanel getLayoutpanel() {
        return layoutpanel;
    }
}

