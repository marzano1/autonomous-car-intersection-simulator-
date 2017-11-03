package Simulator;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;
import java.awt.Font;
import Car.Car;



public class View extends BasicGameState {

	TrueTypeFont font;
	private boolean click = false;
	private static final float roadLength = 1200;
	private static final float roadWidh = 180;
	
	public static int delta = 0;
	
	private Image imgArrowL,imgArrowR,imgControl, switchOn, switchOff;
	private Rectangle rectPause = new Rectangle(150, 700, 60,60);
	private Rectangle rectPlay = new Rectangle(220, 700, 60,60);
	private Rectangle rectStop = new Rectangle(292, 700, 60,60);
	private Rectangle increaseLevel = new Rectangle(310, 625, 40,45);
	private Rectangle decreaseLevel = new Rectangle(150, 625, 40,45);
	private Rectangle rectRoad1 = new Rectangle(790, 650, 33,60);
	private Rectangle rectRoad2 = new Rectangle(890, 650, 33,60);
	private Rectangle rectRoad3 = new Rectangle(990, 650, 33,60);
	private Rectangle rectRoad4 = new Rectangle(1090, 650, 33,60);
	
	public View(int intersectionview) {
		
	}


	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
	    Font awtFont = new Font("Arial", Font.BOLD, 32);
	    font = new TrueTypeFont((java.awt.Font) awtFont, false);
	    imgArrowL = new Image("images/arrowLeft.png");
		imgArrowR = new Image("images/arrowRight.png");
		imgControl = new Image("images/control.png");
		switchOn = new Image("images/switchOn.png");
		switchOff = new Image("images/switchOff.png");
	}

	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		renderRoads(g);
		renderRoadNames(g);
		renderControlMenu(g);
		renderCars(g);
		g.draw(Simulation.intersectionZone);
	}

	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {		
		
		if(Mouse.isButtonDown(0) && !click) {
			click = true;
			int posX = Mouse.getX();
			int posY = Mouse.getY();
			posY = 800 - posY;			
			if(rectPlay.contains(posX, posY)) Simulation.Play();
			else if(rectPause.contains(posX, posY)) Simulation.Pause();
			else if(rectStop.contains(posX, posY)) Simulation.Stop();
			else if(increaseLevel.contains(posX, posY)) Simulation.IncreaseLevel();
			else if(decreaseLevel.contains(posX, posY)) Simulation.DecreaseLevel();
			else if(rectRoad1.contains(posX, posY)) Simulation.ChangeRoadStatus(1);
			else if(rectRoad2.contains(posX, posY)) Simulation.ChangeRoadStatus(2);
			else if(rectRoad3.contains(posX, posY)) Simulation.ChangeRoadStatus(3);
			else if(rectRoad4.contains(posX, posY)) Simulation.ChangeRoadStatus(4);
			
		}
		else if(click && !Mouse.isButtonDown(0)) {
			click = false;
		}
		
		View.delta = delta;
	}

	
	public int getID() {
		
		return 0;
	}
	
	
	private void renderRoads(Graphics g) {
		//roads
		Color c = new Color(75, 75, 75);
		g.setColor(c);
		g.fillRect(0, 310, roadLength, roadWidh);
		g.fillRect(510, 0, roadWidh,roadLength);
		//lane separator		
		g.setColor(Color.yellow);
		g.fillRect(0, 395, 510, 10);
		g.fillRect(690, 395, 690, 10);		
		g.fillRect(595, 0, 10, 310);
		g.fillRect(595, 490, 10, 310);
		//lanes
		g.setColor(Color.white);
		for(int i = 0; i < 18; i ++) {
			g.fillRect(490 - i*32, 350.5f, 20, 4);
			g.fillRect(490 - i*32, 445.5f, 20, 4);
			g.fillRect(690 + i*32, 350.5f, 20, 4);
			g.fillRect(690 + i*32, 445.5f, 20, 4);
		}
		
		for(int i = 0; i < 12; i ++) {
			g.fillRect(550.5f, 290 - i*32, 4, 20);
			g.fillRect(645.5f, 290 - i*32, 4, 20);
			g.fillRect(550.5f, 490 + i*32, 4, 20);			
			g.fillRect(645.5f, 490 + i*32, 4, 20);			
		}
		
	}

	private void renderRoadNames(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Road 1", 700, 770);
		g.drawString("Road 2", 440, 10);
		g.drawString("Road 3", 1130, 280);
		g.drawString("Road 4", 10, 500);
	}
	
	private void renderCars(Graphics g) {
		synchronized (Car.cars) {			
			for(Car car: Car.cars){	
				g.setColor(car.color);
				g.fill(car.shape);
			}
		}
		
	}

	private void renderControlMenu(Graphics g) throws SlickException {		
		imgControl.draw(150,700);
		imgArrowR.draw(310,625);		
		imgArrowL.draw(150,625);
		if(Simulation.GetRoadStatus(1)) switchOn.draw(790, 650);
		else switchOff.draw(790, 650);
		if(Simulation.GetRoadStatus(2)) switchOn.draw(890, 650);
		else switchOff.draw(890, 650);
		if(Simulation.GetRoadStatus(3)) switchOn.draw(990, 650);
		else switchOff.draw(990, 650);
		if(Simulation.GetRoadStatus(4)) switchOn.draw(1090, 650);
		else switchOff.draw(1090, 650);
		g.setFont(font);
		g.drawString( String.valueOf(Simulation.GetLevel()),242 , 630);
		
	}
}
