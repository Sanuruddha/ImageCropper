/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cropper;

import static com.sun.java.accessibility.util.AWTEventMonitor.addComponentListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Lock'
 */
class SelectionPane extends JPanel {

    private JButton button;
    private JLabel label;
    private Point mouseAnchor;
    private Point dragPoint;
    private Point newDragPoint;
    private double maxX,minX,maxY,minY;
    
    public SelectionPane() {
        button = new JButton("Close");
        setOpaque(false);

        label = new JLabel("Rectangle");
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(4, 4, 4, 4));
        label.setBackground(Color.GRAY);
        label.setForeground(Color.WHITE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(label, gbc);

        gbc.gridy++;
        //add(button, gbc);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(SelectionPane.this).dispose();
            }
        });
        
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                label.setText("Rectangle " + getX() + "x" + getY() + "x" + getWidth() + "x" + getHeight());
            }
        });
        
        
        
        MouseAdapter adapter;
        adapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                maxX=getBounds().getMaxX();
                maxY=getBounds().getMaxY();
                minX=getBounds().getMinX();
                minY=getBounds().getMinY();
                newDragPoint = e.getPoint();
                
                if(maxX-minX-20<=newDragPoint.getX()&&newDragPoint.getX()<=maxX-minX){
                    setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                
                }
                else if(0<=newDragPoint.getX()&&newDragPoint.getX()<=20){
                    setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                
                }
                else if(maxY-minY-20<=newDragPoint.getY()&&newDragPoint.getY()<=maxY-minY){
                    setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                
                }
                else if(0<=newDragPoint.getY()&&newDragPoint.getY()<=20 ){
                    setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                
                }
                else{
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
           
            
            @Override
            public void mousePressed(MouseEvent e) {
                
                
            }

//            @Override
//            public void mouseDragged(MouseEvent e) {
//                dragPoint = e.getPoint();
//                int width = dragPoint.x - mouseAnchor.x;
//                int height = dragPoint.y - mouseAnchor.y;
//
//                int x = mouseAnchor.x;
//                int y = mouseAnchor.y;
//
//                if (width < 0) {
//                    x = dragPoint.x;
//                    width *= -1;
//                }
//                if (height < 0) {
//                    y = dragPoint.y;
//                    height *= -1;
//                }
//                setBounds(x, y, width, height);
//                revalidate();
//                getParent().repaint();
//            }

        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(128, 128, 128, 64));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        float dash1[] = {10.0f};
        BasicStroke dashed
                = new BasicStroke(3.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        10.0f, dash1, 0.0f);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(dashed);
        g2d.drawRect(0, 0, getWidth() - 3, getHeight() - 3);
        g2d.dispose();
    }

    public static Rectangle getScreenViewableBounds() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        return getScreenViewableBounds(gd);
    }

    public static Rectangle getScreenViewableBounds(GraphicsDevice gd) {
        Rectangle bounds = new Rectangle(0, 0, 0, 0);
        if (gd != null) {
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            bounds = gc.getBounds();

            Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

            bounds.x += insets.left;
            bounds.y += insets.top;
            bounds.width -= (insets.left + insets.right);
            bounds.height -= (insets.top + insets.bottom);
        }
        return bounds;
    }

}