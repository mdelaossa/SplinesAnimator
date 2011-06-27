/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maquant;

import java.awt.Point;

/**
 *
 * @author mario
 */
public class VelocityPoint extends Point {

    private int velocityX = 0;
    private int velocityY = 0;
    

    public VelocityPoint(int i, int i1) {
        super(i, i1);
    }
    
    public VelocityPoint(int i, int i1, int vx, int vy) {
        super(i, i1);
        this.velocityX = vx;
        this.velocityY = vy;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }
    
    
    
}
