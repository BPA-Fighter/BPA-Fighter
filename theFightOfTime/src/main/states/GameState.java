
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import entities.IceGuy;
import entities.Mafia;
import gfx.*;
import main.Game;

public class GameState extends State {
	
	// init iceGuy, mafia
	private IceGuy iceGuy;
	private Mafia mafia;
	
	// constructor
	public GameState(Game game) {
		super(game);
		
		// create instance of iceGuy, mafia 
		iceGuy = new IceGuy(game, 60, 280);
		mafia = new Mafia(game, 224 * 2, 280);
		
	}
	
	@Override
	public void tick() {
		// update hitboxes, attack boxes
		iceGuy.getAttackBounds();
		iceGuy.getHitBounds();
		
		mafia.getAttackBounds();
		mafia.getHitBounds();
		
		// update entire instance
		iceGuy.tick();
		mafia.tick();
	
	}

	@Override
	public void render(Graphics g) {
				
		// get images for ui
		ImageIcon healthBar = new ImageIcon("healthBar.png");
		ImageIcon iceGuyFont = new ImageIcon("iceGuyFont.png");
		ImageIcon mafiaFont = new ImageIcon("mafiaFont.png");

		// print ui for iceGuy
		g.setColor(Color.yellow);
		double percentIceGuy = iceGuy.getHealth() / 100.0;	
		g.fillRect(61, 19, (int) (173 * percentIceGuy), 11);
		g.drawImage(iceGuyFont.getImage(), mafiaFont.getIconWidth() - 35, 40, 48, 16, null);
		
		// print ui for mafia
		g.setColor(Color.yellow);
		double percentMafia  = mafia.getHealth() / 100.0;	
		g.fillRect(99 + 29 + 144, 19, (int) (173 * percentMafia) , 11);
		g.drawImage(mafiaFont.getImage(), (Game.WIDTH * Game.SCALE) - 2 * mafiaFont.getIconWidth() + mafiaFont.getIconWidth() / 2 + 32, 40, 48, 16, null);
		
		// drawn last so that rect and ui could be under
		g.drawImage(healthBar.getImage(),  60, 16, (int) (healthBar.getIconWidth() * 1.2), (int) (healthBar.getIconHeight() * 1.2) ,null);

		
		// render instances
		iceGuy.render(g);
		mafia.render(g);	
				
		
		g.setColor(Color.BLACK);
		// end game
		if (mafia.getHealth() <= 0) {
			// win screen
			String iceGuyWin = "ICEGUY WINS THE GAME!";
			g.fillRect(0, 0, Game.WIDTH * 2, Game.HEIGHT * 2);
			g.setColor(Color.WHITE);
			int width = g.getFontMetrics().stringWidth(iceGuyWin);
			g.drawString(iceGuyWin, Game.WIDTH - width/2, Game.HEIGHT);
		} else if (iceGuy.getHealth() <= 0) {
			// win screen
			String mafiaWin = "MAFIA WINS THE GAME!";
			g.fillRect(0, 0, Game.WIDTH * 2, Game.HEIGHT * 2);
			g.setColor(Color.WHITE);
			int width = g.getFontMetrics().stringWidth(mafiaWin);
			g.drawString(mafiaWin, Game.WIDTH - width/2, Game.HEIGHT);
		}
		
	}

	@Override
	public void music() {
		// empty for now because Java 8 API issues on school computers
	}
	
	// GETTERS AND SETTERS:
	
	public Rectangle getIceGuyHitBounds() {
		return iceGuy.getHitBounds();
	}
	
	public Rectangle getIceGuyAttackBounds() {
		return iceGuy.getAttackBounds();
	}
	
	public Rectangle getMafiaHitBounds() {
		return mafia.getHitBounds();
	}
	
	public Rectangle getMafiaAttackBounds() {
		return mafia.getAttackBounds();
	}
	
	public int getIceGuyX() {
		return iceGuy.getIceGuyX();
	}

	public int getMafiaX() {
		return mafia.getMafiaX();
	}
	
}
