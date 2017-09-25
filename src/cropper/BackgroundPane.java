/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cropper;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Lock'
 */
public class BackgroundPane extends JPanel {

    private Point mouseAnchor;
    private Point dragPoint;
    private SelectionPane selectionPane;
    private Point newDragPoint;
    private double maxX, minX, maxY, minY;

    public SelectionPane getPane() {
        return selectionPane;
    }

    public void addSelectionPane() {
        selectionPane = new SelectionPane();
        setLayout(null);
        add(selectionPane);

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseAnchor = e.getPoint();
                dragPoint = null;
                selectionPane.setLocation(mouseAnchor);
                selectionPane.setSize(0, 0);

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                dragPoint = e.getPoint();
                int width = dragPoint.x - mouseAnchor.x;
                int height = dragPoint.y - mouseAnchor.y;

                int x = mouseAnchor.x;
                int y = mouseAnchor.y;

                if (width < 0) {
                    x = dragPoint.x;
                    width *= -1;
                }
                if (height < 0) {
                    y = dragPoint.y;
                    height *= -1;
                }
                selectionPane.setBounds(x, y, width, height);
                selectionPane.revalidate();
                repaint();
            }

        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

    }

    public BackgroundPane(JFrame parentFrame) throws IOException {
        if (Window.background != null) {
            Dimension d = new Dimension(Window.background.getWidth(), Window.background.getHeight());
            parentFrame.setPreferredSize(d);
            this.setPreferredSize(d);
            addSelectionPane();
        } else {
            Dimension d = new Dimension(500, 500);
            parentFrame.setPreferredSize(d);
            this.setPreferredSize(d);
            addSelectionPane();
        }

    }

    public void disposePane() {
        remove(selectionPane);
        addSelectionPane();
        Window.frame.add(new ControlPane(this));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (Window.background != null) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage(Window.background, 0, 0, this);
            g2d.dispose();
        }

    }

    public void disposeAll() {
        remove(selectionPane);
    }

    

}

