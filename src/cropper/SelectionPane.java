/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cropper;

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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Lock'
 */
class SelectionPane extends JPanel {

    private JLabel label;
    private Point mouseAnchor;
    private int mouseAnchorX, mouseAnchorY, newDragPointX, newDragPointY;
    private Point newDragPoint;
    private double maxX, minX, maxY, minY;
    boolean isResizing = false;
    boolean isDragging = false;
    Edge resizeEdge;
//constructor
    public SelectionPane() {
        setOpaque(false);
        //jLabel that shows the size of the Selection Pane
        label = new JLabel("Rectangle");
        label.setBorder(new EmptyBorder(4, 4, 4, 4));
        label.setForeground(Color.WHITE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(label, gbc);

        gbc.gridy++;

        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                label.setText(getWidth() + "x" + getHeight());
            }
        });

        MouseAdapter adapter;

        adapter = new MouseAdapter() {
            //detects the dragging action and saving the anchor position
            @Override
            public void mousePressed(MouseEvent e) {
                if (detectEdge(e) != null) {
                    
                        isResizing = true;
                        resizeEdge = detectEdge(e);
                        mouseAnchor = e.getPoint();
                        mouseAnchorX = e.getPoint().x;
                        mouseAnchorY = e.getPoint().y;
                   
                } else {
                    
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isResizing) {
                    isResizing = false;
                    resizeEdge = null;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = newDragPoint.x;
                int y = newDragPoint.y;
                maxX = getBounds().getMaxX();
                maxY = getBounds().getMaxY();
                minX = getBounds().getMinX();
                minY = getBounds().getMinY();
                newDragPoint = e.getPoint();
                newDragPointX = e.getPoint().x;
                newDragPointY = e.getPoint().y;
                //when resizing create the new bounds for the jPanel and repaint
                if (isResizing) {

                    if (null != resizeEdge) {
                        switch (resizeEdge) {
                            case right:
                                setBounds((int) minX, (int) minY, (int) maxX - (int) minX + ((int) newDragPoint.x - (int) mouseAnchor.x), (int) maxY - (int) minY);
                                revalidate();
                                getParent().repaint();
                                mouseAnchor = newDragPoint;
                                break;
                            case left:
                                setBounds((int) minX + ((int) newDragPoint.x - (int) mouseAnchor.x), (int) minY, (int) maxX - (int) minX - ((int) newDragPoint.x - (int) mouseAnchor.x), (int) maxY - (int) minY);
                                revalidate();
                                getParent().repaint();
                                newDragPoint = mouseAnchor;
                                break;
                            case bottom:
                                setBounds((int) minX, (int) minY, (int) maxX - (int) minX, (int) maxY - (int) minY + ((int) newDragPoint.y - (int) mouseAnchor.y));
                                revalidate();
                                getParent().repaint();
                                mouseAnchor = newDragPoint;
                                break;
                            case top:
                                setBounds((int) minX, (int) minY + ((int) newDragPoint.y - (int) mouseAnchor.y), (int) maxX - (int) minX, (int) maxY - (int) minY - ((int) newDragPoint.y - (int) mouseAnchor.y));
                                revalidate();
                                getParent().repaint();
                                newDragPoint = mouseAnchor;
                                break;
                            case topleft:
                                setBounds((int) minX + ((int) newDragPoint.x - (int) mouseAnchor.x), (int) minY + ((int) newDragPoint.y - (int) mouseAnchor.y), (int) maxX - (int) minX - ((int) newDragPoint.x - (int) mouseAnchor.x), (int) maxY - (int) minY - ((int) newDragPoint.y - (int) mouseAnchor.y));
                                revalidate();
                                getParent().repaint();
                                newDragPoint = mouseAnchor;
                                break;
                            case topright:
                                setBounds((int) minX, (int) minY + ((int) newDragPointY - (int) mouseAnchorY), (int) maxX - (int) minX + ((int) newDragPointX - (int) mouseAnchorX), (int) maxY - (int) minY - ((int) newDragPointY - (int) mouseAnchorY));
                                revalidate();
                                getParent().repaint();
                                mouseAnchorX = newDragPointX;
                                newDragPointY = mouseAnchorY;
                                break;
                            case bottomleft:
                                setBounds((int) minX + ((int) newDragPointX - (int) mouseAnchorX), (int) minY, (int) maxX - (int) minX - ((int) newDragPointX - (int) mouseAnchorX), (int) maxY - (int) minY + ((int) newDragPointY - (int) mouseAnchorY));
                                revalidate();
                                getParent().repaint();
                                mouseAnchorY = newDragPointY;
                                newDragPointX = mouseAnchorX;
                                break;
                            case bottomright:
                                setBounds((int) minX, (int) minY, (int) maxX - (int) minX + ((int) newDragPoint.x - (int) mouseAnchor.x), (int) maxY - (int) minY + ((int) newDragPoint.y - (int) mouseAnchor.y));
                                revalidate();
                                getParent().repaint();
                                mouseAnchor = newDragPoint;
                                break;
                            default:

                                break;
                        }
                    } else {

                    }
                } 

            }
            //change cursor when hovered over edges
            @Override
            public void mouseMoved(MouseEvent e) {

                Edge edge;
                edge = detectEdge(e);
                if (null == edge) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } else {
                    switch (edge) {
                        case right:
                            setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                            break;
                        case left:
                            setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                            break;
                        case bottom:
                            setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                            break;
                        case top:
                            setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                            break;
                        case topleft:
                            setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                            break;
                        case topright:
                            setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                            break;
                        case bottomleft:
                            setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                            break;
                        case bottomright:
                            setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                            break;
                        default:
                            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            break;
                    }
                }
            }

        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

    }
    
    //Selection Pane draw
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
    //Detect when hovered over an edge
    private Edge detectEdge(MouseEvent e) {
        Edge edge = null;
        maxX = getBounds().getMaxX();
        maxY = getBounds().getMaxY();
        minX = getBounds().getMinX();
        minY = getBounds().getMinY();
        newDragPoint = e.getPoint();

        int x = newDragPoint.x;
        int y = newDragPoint.y;


        if (0 < y && y < 5 && 5 < x && x < maxX - minX - 5) {
            edge = Edge.top;
        } else if (maxY - minY - 5 < y && y < maxY - minY && 5 < x && x < maxX - minX - 5) {
            edge = Edge.bottom;
        } else if (maxX - minX - 5 < x && x < maxX - minX && 5 < y && y < maxY - minY - 5) {
            edge = Edge.right;
        } else if (0 < x && x < 5 && 5 < y && y < maxY - minY - 5) {
            edge = Edge.left;
        } else if (0 < x && x < 5 && 0 < y && y < 5) {
            edge = Edge.topleft;
        } else if (0 < x && x < 5 && maxY - minY - 5 < y && y < maxY - minY) {
            edge = Edge.bottomleft;
        } else if (maxX - minX - 5 < x && x < maxX - minX && 0 < y && y < 5) {
            edge = Edge.topright;
        } else if (maxX - minX - 5 < x && x < maxX - minX && maxY - minY - 5 < y && y < maxY - minY) {
            edge = Edge.bottomright;
        }
        return edge;
    }

}

enum Edge {
    left, right, top, bottom, topleft, topright, bottomleft, bottomright;
}
