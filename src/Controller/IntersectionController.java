package Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Car.Car;

public class IntersectionController extends Thread{
	
	
	
	private  static List<Car> carsOnIntersection =  Collections.synchronizedList(new ArrayList<Car>());
	
	private  static IntersectionController controller = null;
	
	//private boolean usingLane = false;
	
	private IntersectionController() {
		
	}
	
	public synchronized static IntersectionController  getInstance() {
		if(controller == null) {
			controller = new IntersectionController();
			controller.start();
		}
		
		return controller;		
	}
	

	public synchronized static  void requestIntersectionAccess(Car car) {
		if(carsOnIntersection.isEmpty()) {
			car.setIntersectionLeadership(true);		
		}
		synchronized (carsOnIntersection) {	
			carsOnIntersection.add(car);
		}			
	}
	
	public static synchronized void removeFromCarsOnIntersection(Car car ) {
		synchronized (carsOnIntersection) {	
			carsOnIntersection.remove(car);
		}
	}
	
	public void run() {
		try {
			simulate();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void simulate() throws InterruptedException {
		while(!carsOnIntersection.isEmpty()) {
			getRandomCar().setIntersectionAccess(true);
			for(Car c : carsOnIntersection) {
				
			}
	    		
			System.out.println("Allowing Vehicle");
			Thread.sleep(2000);
		}
		controller = null;
	}
	
	public static void SetNewLeader() {
		if(carsOnIntersection.size()>0) {
			getRandomCar().setIntersectionLeadership(true);
		}
		
	}


	private static Car getRandomCar() {
	    try {
	    		synchronized (carsOnIntersection) {
	    		return carsOnIntersection.get((new Random()).nextInt(carsOnIntersection.size()));
	    	}
	    }
	    catch (Throwable e){
	        return null;
	    }
	}



}
