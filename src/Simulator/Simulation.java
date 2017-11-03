package Simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.newdawn.slick.geom.Rectangle;

import Car.Car;

public class Simulation extends Thread {
	
	private static boolean running = false;
	private static int level = 1;
	private static boolean road1Active = true;
	private static boolean road2Active = true;
	private static boolean road3Active = true;
	private static boolean road4Active = true;
	public static Rectangle road1Lane2 = new Rectangle(605, 780, 40,60);
	public static Rectangle road1Lane1 = new Rectangle(650, 780, 40,60);
	public static Rectangle road2Lane2 = new Rectangle(510, -40, 40,60);
	public static Rectangle road2Lane1 = new Rectangle(555, -40, 40,60);
	public static Rectangle road3Lane1 = new Rectangle(1180, 310, 60,40);
	public static Rectangle road3Lane2 = new Rectangle(1180, 355, 60,40);
	public static Rectangle road4Lane1 = new Rectangle(-40, 405, 60,40);
	public static Rectangle road4Lane2 = new Rectangle(-40, 450, 60,40);
	public static Rectangle intersectionZone = new Rectangle(470, 270, 260,260);
	
	public void run() {
		try {
			simulate();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}

	private void simulate() throws InterruptedException {
		while (true) {

			if (Simulation.IsRunning()) {
				int lane = randomizeLane();
				if(lane!=-1) {
					Car car = new Car(lane);
					Car.cars.add(car);
					car.start();
				}
				sleep((long) (getSleepTime() * 1000));
			}
			else {
				Thread.sleep(500);
			}
		}
	}

	public static void Play() {
		running = true;
	}

	public static void Pause() {
		//running = false;
	}

	public static void Stop() {
		running = false;
		synchronized (Car.cars) {
			Car.cars.clear();
		}
	}
	
	public static void IncreaseLevel() {
		if(level<5) level++;
	}
	
	public static void DecreaseLevel() {
		if(level>1) level--;
	}
	
	public static int GetLevel() {
		return level;
	}
	
	public static boolean IsRunning() {
		return running;
	}
	
	private int randomizeLane() {
		List<Integer> n =  new ArrayList<Integer>();
		if(road1Active) {
			if(isLaneAvailable(road1Lane1))n.add(1);
			if(isLaneAvailable(road1Lane2))n.add(2);
		}
		if(road2Active) {
			if(isLaneAvailable(road2Lane1))n.add(3);
			if(isLaneAvailable(road2Lane2))n.add(4);
		}
		if(road3Active) {
			if(isLaneAvailable(road3Lane1))n.add(5);
			if(isLaneAvailable(road3Lane2))n.add(6);
		}
		if(road4Active) {
			if(isLaneAvailable(road4Lane1))n.add(7);
			if(isLaneAvailable(road4Lane2))n.add(8);
		}
		if(n.size() == 0) return -1;
		int randomNum = ThreadLocalRandom.current().nextInt(0, n.size() );
		return n.get(randomNum);
	}
	
	private boolean isLaneAvailable(Rectangle r) {
		synchronized (Car.cars) {			
			for(Car car: Car.cars){	
				if(r.contains(car.shape.getCenterX(), car.shape.getCenterY()))return false;				
			}
		}
		return true;
	}
	
	public static void  ChangeRoadStatus(int roadNum) {
		
		if(roadNum == 1) road1Active = !road1Active;
		else if(roadNum == 2) road2Active = !road2Active;
		else if(roadNum == 3) road3Active = !road3Active;
		else  road4Active = !road4Active;
	}
	
	
	public static boolean  GetRoadStatus(int roadNum) {
		
		if(roadNum == 1) return road1Active;
		else if(roadNum == 2) return road2Active;
		else if(roadNum == 3) return road3Active;
		else  return road4Active;
	}
	
	
	private float getSleepTime() {
		if(level ==1 ) return 3;
		else if(level ==2 ) return 2;
		else if(level ==3 ) return 1;
		else if(level ==4 ) return 0.5f;
		else return 0.25f;
		}

}
