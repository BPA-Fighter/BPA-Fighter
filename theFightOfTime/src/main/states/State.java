/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.states;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;

public abstract class State {

	// init State var
	private static State currState = null;
	
	// setter for State
	public static void setState(State state) {
		currState = state;
	}
	
	// getter for State
	public static State getState() {
		return currState;
	}
	
	// init Game var
	protected Game game;
	
	// constructor
	public State(Game game) {
		this.game = game;
	}
	
	// nethods that all extended classes will share...
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void music();
		
	// hitbox methods
	public abstract Rectangle getIceGuyHitBounds();	
	public abstract Rectangle getIceGuyAttackBounds();	
	public abstract Rectangle getMafiaHitBounds();	
	public abstract Rectangle getMafiaAttackBounds();	
	public abstract int getIceGuyX();	
	public abstract int getMafiaX();	
}
