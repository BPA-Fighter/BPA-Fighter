/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

/**
 *
 * @author Time Crunchers
 * 
 */
public abstract class Creature {

    // every subclass of creature has an x,y
	public Creature(float x, float y) {
		super(x, y);
	}
    
}
