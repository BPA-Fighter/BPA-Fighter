/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.awt.Graphics;

/**
 *
 * 
 */
public abstract class Entity {

    // every creature has an x,y
	protected float x, y;

	// constructor
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	// update
	public abstract void tick();
	
	// draw
	public abstract void render(Graphics g);
    
}
