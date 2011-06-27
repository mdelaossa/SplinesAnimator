/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SplinesJPanel.java
 *
 * Created on Jun 26, 2011, 7:17:46 PM
 */
package com.maquant;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.SwingWorker;

/**
 *
 * @author mario
 */
public class SplinesJPanel extends javax.swing.JPanel {

    private ArrayList<VelocityPoint> points = new ArrayList<VelocityPoint>();
    private final static int BUFFER = 10; //Used to leave a margin on the panel
    

    /** Creates new form SplinesJPanel */
    public SplinesJPanel() {
        initComponents();
        
        //TODO: Remove test code
        //points.add(new VelocityPoint(2,3,2,3));
        //points.add(new VelocityPoint(20,30,20,30));
        //points.add(new VelocityPoint(40,60,40,60));
        //points.add(new VelocityPoint(60,90,60,90));
        java.util.Random rand = new java.util.Random();
        for (int i = 0 ; i < 5000 ; i++) {
            int x = rand.nextInt(100);
            int y = rand.nextInt(100);
            int vx = rand.nextInt(50);
            int vy = rand.nextInt(50);
            VelocityPoint newPoint = new VelocityPoint(x,y,vx,vy);
            points.add(newPoint);
        }
        //END TEST CODE---------
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new animator(), 50, 50);
    }

    public ArrayList<VelocityPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<VelocityPoint> points) {
        this.points = points;
    }
    
    public void addPoint(VelocityPoint point) {
        this.points.add(point);
    }
    
    public void removePoint(VelocityPoint point) {
        this.points.remove(point);
    }
    
    
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("Times New Roman", Font.BOLD, 30));
        
        Iterator pointIterator = points.iterator();
        while(pointIterator.hasNext()) {
            VelocityPoint currentPoint = (VelocityPoint) pointIterator.next();
            int x = (currentPoint.x > this.getWidth()-BUFFER)?currentPoint.x-BUFFER:currentPoint.x;
            x = (currentPoint.x < BUFFER)?currentPoint.x+BUFFER:currentPoint.x;
            int y = (currentPoint.y > this.getWidth()-BUFFER)?currentPoint.y-BUFFER:currentPoint.y;
            y = (currentPoint.y < BUFFER)?currentPoint.y+BUFFER:currentPoint.y;
            g2.drawString(".", x, y);
        }

    }
    
    
    private void animatePoints() {
        SwingWorker<Void,Void> animator = new SwingWorker<Void,Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                Iterator pointIterator = points.iterator();
                while(pointIterator.hasNext()) {
                    VelocityPoint currentPoint = (VelocityPoint) pointIterator.next();
                    if (currentPoint.x + currentPoint.getVelocityX() + BUFFER < getWidth() && currentPoint.x + currentPoint.getVelocityX() > 0) {
                        currentPoint.x = currentPoint.x + currentPoint.getVelocityX();
                    } else {
                        currentPoint.setVelocityX(-1*currentPoint.getVelocityX());
                    }
                    if (currentPoint.y + currentPoint.getVelocityY() + BUFFER < getHeight() && currentPoint.y + currentPoint.getVelocityY() > 0) {
                        currentPoint.y = currentPoint.y + currentPoint.getVelocityY();
                    } else {
                        currentPoint.setVelocityY(-1*currentPoint.getVelocityY());
                    }
                }
                repaint();
                return null;
            }
        };
        
        animator.execute();
    }
    
    private class animator extends java.util.TimerTask {

        @Override
        public void run() {
            animatePoints();
        }
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}