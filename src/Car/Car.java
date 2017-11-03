package Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import Controller.IntersectionController;
import Simulator.Simulation;
import Simulator.View;
import Util.Utils;

public class Car extends Thread {
	
	public int road;
	public static List<Car> cars =  Collections.synchronizedList(new ArrayList<Car>());
	public boolean isController = false;
	public Polygon shape;	
	public Color color = Utils.RandomColor();
	private int destiny;
	
	//Intersection variables
	private boolean isInsideIntersectionZone = false;
	private boolean isIntersectionLeader = false;
	private boolean intersectionAccessAllowed = false;
	private boolean accessRequested = false;
	public IntersectionController	controller = null;
	//  END
	
	
	public float speed = 0;
	public float maxSpeed = 0.0625f;
	
	public Car(int lane) {
		this.road = Utils.GetRoad(lane);
		float x=0,y=0;
		x = Utils.GetLaneX(lane);
		y = Utils.GetLaneY(lane);	
		this.destiny = Utils.GetDestiny(lane);
		Rectangle rect = new Rectangle(x, y, Utils.carWidth, Utils.carHeight);		
		this.shape = Utils.RectangleToPolygon(rect);
		if( lane> 4 ) turn(90);		
	}

	public  void run(){
		try {
			control();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void turn(float degrees) {		
		shape=(Polygon) shape.transform(Transform.createRotateTransform((float) Math.toRadians(degrees),shape.getCenterX(),shape.getCenterY()));
	}
	
	private void accelerate() {
		if(speed<maxSpeed) {
			speed+=0.00005f;
		}
		else {
			speed = maxSpeed;
		}		

	}	
	
	private void brake() {
		if(speed>0) {
			speed-=0.00015f;
		}
		else {
			speed = 0;
		}
	}
	
	private void move() {
		if(this.road == 1) {
			shape.setY(shape.getY() - speed * View.delta);
			if(destiny == 8 && shape.getCenterY() < Utils.lane4InitY2) {
				shape.setCenterY( Utils.lane4InitY2 + Utils.carWidth/2);
				turn(90);
				road = 4;
			}
			else if(destiny == 6 && shape.getCenterY() < Utils.lane3InitY2) {
				shape.setCenterY( Utils.lane3InitY2 + Utils.carWidth/2);
				turn(90);
				road = 3;
			}
		}
		else if(this.road == 2) {
			shape.setY(shape.getY() + speed * View.delta);
			if(destiny == 7 && shape.getCenterY() > Utils.lane4InitY1) {
				shape.setCenterY( Utils.lane4InitY1 + Utils.carWidth/2);
				turn(90);
				road = 4;
			}
			else if(destiny == 5 && shape.getCenterY() > Utils.lane3InitY1) {
				shape.setCenterY( Utils.lane3InitY1 + Utils.carWidth/2);
				turn(90);
				road = 3;
			}
		}
		else if(this.road == 3) {
			shape.setX(shape.getX() - speed * View.delta);
			if(destiny == 1 && shape.getCenterX() < Utils.lane1InitX1) {
				shape.setCenterX( Utils.lane1InitX1 + Utils.carWidth/2);
				turn(90);
				road = 1;
			}
			else if(destiny == 3 && shape.getCenterX() < Utils.lane2InitX1) {
				shape.setCenterX( Utils.lane2InitX1 + Utils.carWidth/2);
				turn(90);
				road = 2;
			}
		}
		else if(this.road == 4) {
			shape.setX(shape.getX() + speed * View.delta);
			if(destiny == 2 && shape.getCenterX() > Utils.lane1InitX2) {
				shape.setCenterX( Utils.lane1InitX2 + Utils.carWidth/2);
				turn(90);
				road = 1;
			}
			else if(destiny == 4 && shape.getCenterX() > Utils.lane2InitX2) {
				shape.setCenterX( Utils.lane2InitX2 + Utils.carWidth/2);
				turn(90);
				road = 2;
			}
		}
		
		
		
	}
	
	private boolean isOnRoad() {
		if(road == 1) {
			if(shape.getCenterY() >= -1*Utils.carHeight/2) return true;
		}
		else if(road == 2) {
			if(shape.getCenterY() <= 800 + Utils.carHeight/2) return true;
		}
		else if(road == 3) {
			if(shape.getCenterX() >=  -1*Utils.carHeight/2) return true;
		}
		else if(road == 4) {
			if(shape.getCenterX() <= 1200 + Utils.carHeight/2) return true;
		}		
		return false;
		
	}
	
	private boolean isAccelerationAllowed() {
		synchronized (Car.cars) {			
			for(Car c: Car.cars){		
				if(road == 1) {
					if(c != this && c.shape.contains(shape.getCenterX(), shape.getCenterY() - Utils.carHeight*1.4f))return false;
				}
				else if(road == 2) {
					if(c != this && c.shape.contains(shape.getCenterX(), shape.getCenterY() + Utils.carHeight*1.4f))return false;
				}
				else if(road == 3) {
					if(c != this && c.shape.contains(shape.getCenterX() - Utils.carHeight*1.4f, shape.getCenterY()))return false;
				}
				else if(road == 4) {
					if(c != this && c.shape.contains(shape.getCenterX() + Utils.carHeight*1.4f, shape.getCenterY()))return false;
				}
				
			}
		}
		if(!isInsideIntersectionZone && !intersectionAccessAllowed) {
			if(Simulation.intersectionZone.contains(shape)) {
				isInsideIntersectionZone= true;
				IntersectionController.requestIntersectionAccess(this);
			}			
		}
		else if(isInsideIntersectionZone && intersectionAccessAllowed) {
			if(!Simulation.intersectionZone.contains(shape)) {
				isInsideIntersectionZone= false;
			}
			return true;
		}
		else if(isInsideIntersectionZone && !intersectionAccessAllowed) {
			return false;			
		}
		return true;
	}
	
	public void setIntersectionLeadership(boolean leader) {
		if(leader) {
			color = Utils.WhiteColor();
			controller = IntersectionController.getInstance();
			isIntersectionLeader = true;
		}
		else {
			color = Utils.RandomColor();
			controller = null;
			isIntersectionLeader = false;			
			IntersectionController.SetNewLeader();
		}
	}
	
	public void setIntersectionAccess(boolean access) {	
		if(access) {
			intersectionAccessAllowed = access;
			IntersectionController.removeFromCarsOnIntersection(this);
			if(isIntersectionLeader) {
				setIntersectionLeadership(false);
			}
		}
	}
	
	private void control()  throws InterruptedException{
		
		while(isOnRoad()) {
				if(isAccelerationAllowed()) accelerate();	
				else brake();
				move();	
				sleep(1);
			
			
		}
		synchronized (Car.cars) {
			cars.remove(this);
		}
	}
}
