/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cropper;

import static com.sun.java.accessibility.util.AWTEventMonitor.addComponentListener;
import static cropper.SelectionPane.getScreenViewableBounds;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author shihan
 */
public class SelectionRectangle {
//    public static void main(String[] args) {
//        new SelectionRectangle();
//    }

    public SelectionRectangle() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Test");
                //frame.setUndecorated(true);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                try {
                    frame.add(new BackgroundPane());
                } catch (IOException ex) {
                    Logger.getLogger(SelectionRectangle.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

        });
}
}  
class BackgroundPane extends JPanel {

        private BufferedImage background;
        private Point mouseAnchor;
        private Point dragPoint;

        private SelectionPane selectionPane;
        
        public void addToBG(){
        
        }
        
        public BackgroundPane() throws IOException {
            selectionPane = new SelectionPane();
            background =  ImageIO.read(getClass().getResource("/dog.jpg"));

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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage(background, 0, 0, this);
            g2d.dispose();
        }

}

class SelectionPane extends JPanel {

        private JButton button;
        private JLabel label;

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
            add(button, gbc);

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

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(128, 128, 128, 64));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            float dash1[] = {10.0f};
            BasicStroke dashed =
                            new BasicStroke(3.0f,
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