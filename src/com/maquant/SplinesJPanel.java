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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.SwingWorker;

/**
 *
 * @author mario
 */
public class SplinesJPanel extends javax.swing.JPanel {

    private ArrayList<ArrayList<VelocityPoint>> splines = new ArrayList();
    private final static int BUFFER = 10; //Used to leave a margin on the panel
    private final static int STEPS = 100;
    private final static int MAXSPEED = 30;
    private final static int SHADOW = 5;
    private final static int INITIALPOINTS = 5;
    private final static int MAXX = 350;
    private final static int MAXY = 300;
    private int shadows = 0;
    private boolean paused = false;
    
    private java.util.Timer timer = new java.util.Timer();
    
    private java.util.Random rand = new java.util.Random();
    

    /** Creates new form SplinesJPanel */
    public SplinesJPanel() {
        initComponents();
        
        ArrayList<VelocityPoint> points = new ArrayList<VelocityPoint>();
        
        for (int i = 0 ; i < INITIALPOINTS ; i++) {
            int x = rand.nextInt(MAXX);
            int y = rand.nextInt(MAXY);
            int vx = rand.nextInt(MAXSPEED);
            int vy = rand.nextInt(MAXSPEED);
            VelocityPoint newPoint = new VelocityPoint(x,y,vx,vy);
            points.add(newPoint);
        }
        
        splines.add(points);
        
        timer.schedule(new animator(), 50, 50);
    }
    
    public boolean isPaused() {
        return paused;
    }

    public ArrayList<VelocityPoint> getPoints(int spline) {
        return splines.get(spline);
    }
    
    public void pause() {
        paused = true;
    }
    
    public void play() {
        paused = false;
    }

    public void setPoints(int spline, ArrayList<VelocityPoint> points) {
        ArrayList temp = this.splines.get(spline);
        temp = points;
    }
    
    public void addPoint(int spline, VelocityPoint point) {
        this.splines.get(spline).add(point);
    }
    
    public void addPoint(int spline) {
        addPoint(spline, 1);
    }
    
    public void addPoint(int spline, int qty) {
        for (int i = 0 ; i < qty ; i++) {
            int x = rand.nextInt(MAXX);
            int y = rand.nextInt(MAXY);
            int vx = rand.nextInt(MAXSPEED);
            int vy = rand.nextInt(MAXSPEED);
            VelocityPoint newPoint = new VelocityPoint(x,y,vx,vy);
            this.splines.get(spline).add(newPoint);
        }
    }
    
    public void removePoint(int spline, VelocityPoint point) {
        this.splines.get(spline).remove(point);
    }
    
    public void removePoint(int spline) {
        removePoint(spline, 1);
    }
    
    public void removePoint(int spline, int qty) {
        if (splines.get(spline).size()-qty > 3)
        for (int i = 0 ; i < qty ; i++) {
            splines.get(spline).remove(splines.get(spline).size()-1);
        }
    }
    
    public void addSpline() {
        ArrayList<VelocityPoint> points = new ArrayList<VelocityPoint>();
        
        for (int i = 0 ; i < INITIALPOINTS ; i++) {
            int x = rand.nextInt(MAXX);
            int y = rand.nextInt(MAXY);
            int vx = rand.nextInt(MAXSPEED);
            int vy = rand.nextInt(MAXSPEED);
            VelocityPoint newPoint = new VelocityPoint(x,y,vx,vy);
            points.add(newPoint);
        }
        
        splines.add(points);
    }
    
    public void removeSpline() {
        if (splines.size() > 1)
            splines.remove(splines.size()-1);
    }
    
    public int splineCount() {
        return splines.size();
    }
    
    public void addShadow() {
        shadows++;
    }
    
    public void removeShadow() {
        if (shadows > 0)
            shadows--;
    }
    
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        for (int i = 0 ; i < splines.size() ; i++) {
            switch (i) {
                case 0:
                    g2.setPaint(new java.awt.GradientPaint(0, 0, Color.blue, 140, 100, Color.green, true));
                    break;
                case 1:
                    g2.setPaint(new java.awt.GradientPaint(0, 0, Color.MAGENTA, 140, 100, Color.PINK, true));
                    break;
                case 2:
                    g2.setPaint(new java.awt.GradientPaint(0, 0, Color.ORANGE, 140, 100, Color.RED, true));
                    break;
                case 3:
                    g2.setPaint(new java.awt.GradientPaint(0, 0, Color.cyan, 140, 100, Color.yellow, true));
                    break;
                case 4:
                    g2.setPaint(new java.awt.GradientPaint(0, 0, Color.DARK_GRAY, 140, 100, Color.green, true));
                    break;
                case 5:
                    g2.setPaint(new java.awt.GradientPaint(0, 0, Color.blue, 140, 100, Color.yellow, true));
                    break;
                default:
                    g2.setPaint(new java.awt.GradientPaint(0, 0, Color.white, 140, 100, Color.white, true));
                    break;
            }
            drawSpline(i,g2);
        }
        
    }
    
    public void drawSpline(int spline, Graphics2D g2) {
        //Catmull-Rom
        Stroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);
        
        drawCatmullRom(spline, g2);
        for (int i = 1; i <= shadows ; i++) {
            drawCatmullShadow(spline, g2, i*SHADOW);
            drawCatmullShadow(spline, g2, -i*SHADOW);
        }
        //-------
        
        //Control points
        g2.setFont(new Font("Times New Roman", Font.BOLD, 50));
        g2.setColor(Color.white);
        
        drawControlPoints(spline, g2);
        //-------
    }
    
    public void drawControlPoints(int spline, Graphics2D g2) {
        Iterator pointIterator = this.splines.get(spline).iterator();
        while(pointIterator.hasNext()) {
            VelocityPoint currentPoint = (VelocityPoint) pointIterator.next();
            int x = (currentPoint.x > this.getWidth()-BUFFER)?currentPoint.x-BUFFER:currentPoint.x;
            x = (currentPoint.x < BUFFER)?currentPoint.x+BUFFER:currentPoint.x;
            int y = (currentPoint.y > this.getWidth()-BUFFER)?currentPoint.y-BUFFER:currentPoint.y;
            y = (currentPoint.y < BUFFER)?currentPoint.y+BUFFER:currentPoint.y;
            g2.drawString(".", x, y);
        }
    }
    
    public void drawCatmullRom(int spline, Graphics2D g2) {
        Polygon pol = new Polygon ();
        Point q = catmullPoint(spline, 2,0);
        pol.addPoint(q.x,q.y);
        for (int i = 2; i < this.splines.get(spline).size()-1; i++) {
          for (int j = 1; j <= STEPS; j++) {
            q = catmullPoint(spline, i,j/(float)STEPS);
            pol.addPoint(q.x,q.y);
          }
        }
        //This area turns the spline into a closed spline
        for (int j = 1; j <= STEPS; j++) { 
            q = catmullClosingPoint(spline, this.splines.get(spline).size()-1,j/(float)STEPS);
            pol.addPoint(q.x,q.y);
        }

        for (int j = 1; j <= STEPS; j++) { 
            q = catmullClosingPoint2(spline, this.splines.get(spline).size(),j/(float)STEPS);
            pol.addPoint(q.x,q.y);
        }
        
        for (int j = 1; j <= STEPS; j++) { 
            q = catmullClosingPoint3(spline, this.splines.get(spline).size()+1,j/(float)STEPS);
            pol.addPoint(q.x,q.y);
        }
        
        g2.drawPolyline(pol.xpoints, pol.ypoints, pol.npoints);
        //--------------------
    }
    
    public void drawCatmullShadow(int spline, Graphics2D g2, int shadow) {
        Polygon pol = new Polygon ();
        Point q = catmullPoint(spline, 2,0);
        pol.addPoint(q.x+shadow,q.y+shadow);
        for (int i = 2; i < this.splines.get(spline).size()-1; i++) {
          for (int j = 1; j <= STEPS; j++) {
            q = catmullPoint(spline, i,j/(float)STEPS);
            pol.addPoint(q.x+shadow,q.y+shadow);
          }
        }
        //This area turns the spline into a closed spline
        for (int j = 1; j <= STEPS; j++) { 
            q = catmullClosingPoint(spline, this.splines.get(spline).size()-1,j/(float)STEPS);
            pol.addPoint(q.x+shadow,q.y+shadow);
        }

        for (int j = 1; j <= STEPS; j++) { 
            q = catmullClosingPoint2(spline, this.splines.get(spline).size(),j/(float)STEPS);
            pol.addPoint(q.x+shadow,q.y+shadow);
        }
        
        for (int j = 1; j <= STEPS; j++) { 
            q = catmullClosingPoint3(spline, this.splines.get(spline).size()+1,j/(float)STEPS);
            pol.addPoint(q.x+shadow,q.y+shadow);
        }
        
        g2.drawPolyline(pol.xpoints, pol.ypoints, pol.npoints);
        //--------------------
    }
    
    
    private void animatePoints() {
        SwingWorker<Void,Void> animator = new SwingWorker<Void,Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                Iterator splineIterator = splines.iterator();
                while(splineIterator.hasNext()) {
                    
                    Iterator pointIterator = ((ArrayList<ArrayList<VelocityPoint>>)splineIterator.next()).iterator();
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
            if (!paused)
                animatePoints();
        }
        
    }
    
    float bMatrix(int i, float t) { //Matriz catmull-rom
        switch (i) {
            case -2:
              return ((-t+2)*t-1)*t/2;
            case -1:
              return (((3*t-5)*t)*t+2)/2;
            case 0:
              return ((-3*t+4)*t+1)*t/2;
            case 1:
              return ((t-1)*t*t)/2;
        }
        return 0;
    }
    
    Point catmullPoint(int spline, int i, float t) {
        float px=0;
        float py=0;
        for (int j = -2; j<=1; j++){
          px += bMatrix(j,t)*this.splines.get(spline).get(i+j).x;
          py += bMatrix(j,t)*this.splines.get(spline).get(i+j).y;
        }
        return new Point((int)Math.round(px),(int)Math.round(py));
      }
    
    Point catmullClosingPoint(int spline, int i, float t) {
        float px=0;
        float py=0;
        ArrayList<VelocityPoint> points = this.splines.get(spline);
        for (int j = -2; j<=1; j++){
            if (i+j < points.size()) {
                px += bMatrix(j,t)*points.get(i+j).x;
                py += bMatrix(j,t)*points.get(i+j).y;
            } else {
                px += bMatrix(j,t)*points.get(0).x;
                py += bMatrix(j,t)*points.get(0).y;
            }
        }
        return new Point((int)Math.round(px),(int)Math.round(py));
    }
    
    Point catmullClosingPoint2(int spline, int i, float t) {
        float px=0;
        float py=0;
        ArrayList<VelocityPoint> points = this.splines.get(spline);
        for (int j = -2; j<=1; j++){
            if (i+j < points.size()) {
                px += bMatrix(j,t)*points.get(i+j).x;
                py += bMatrix(j,t)*points.get(i+j).y;
            } else {
                px += bMatrix(j,t)*points.get(j).x;
                py += bMatrix(j,t)*points.get(j).y;
            }
        }
        return new Point((int)Math.round(px),(int)Math.round(py));
    }
    
    Point catmullClosingPoint3(int spline, int i, float t) {
        float px=0;
        float py=0;
        ArrayList<VelocityPoint> points = this.splines.get(spline);
        for (int j = -2; j<=1; j++){
            if (i+j < points.size()) {
                px += bMatrix(j,t)*points.get(i+j).x;
                py += bMatrix(j,t)*points.get(i+j).y;
            } else {
                px += bMatrix(j,t)*points.get(j+1).x;
                py += bMatrix(j,t)*points.get(j+1).y;
            }
        }
        return new Point((int)Math.round(px),(int)Math.round(py));
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
