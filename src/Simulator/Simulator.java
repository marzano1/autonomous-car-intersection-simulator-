package Simulator;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


public class Simulator  extends StateBasedGame{


	
	public static final String programName = "Simulator";
	public static final int intersectionView = 0;
	
	public Simulator(String name) {
		super(name);
		this.addState(new View(intersectionView));
	}
	
	
	public static void main(String[] args) {
	    Simulation simulation = new Simulation();
	    simulation.start();
		AppGameContainer agc;
		try {
			agc = new AppGameContainer(new Simulator(programName));
			agc.setDisplayMode(1200, 800, false);
			agc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}	
				
	}
	
		
	

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		this.getState(intersectionView).init(gc, this);
		this.enterState(intersectionView);
	}
	
	


}
