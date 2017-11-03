package Util;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;

public class Utils {
	
	public static final float carHeight = 30.0f;
	public static final float carWidth = 20.0f; 
	
	
	public static final float lane1InitX1 = 669.75f - (carWidth/2);
	public static final float lane1InitX2 = 625.25f - (carWidth/2);
	public static final float lane1InitY = 800.0f; //o que anda
	
	public static final float lane2InitX1 = 574.75f - (carWidth/2);
	public static final float lane2InitX2 = 530.25f - (carWidth/2);
	public static final float lane2InitY = -1*carHeight;  //o que anda
	
	public static final float lane3InitX = 1200.0f; //o que anda
	public static final float lane3InitY1 = 330.25f - (carWidth/2);
	public static final float lane3InitY2 = 372.25f - (carWidth/2);
	
	public static final float lane4InitX = -1*carHeight; //o que anda
	public static final float lane4InitY1 = 425.25f - (carWidth/2);
	public static final float lane4InitY2 = 469.75f - (carWidth/2);
	
	
	public static int GetDestiny(int l) {
		Random r = new Random();
		if(l == 1) {			
			return r.nextBoolean() ? 1 : 8;
		}
		else if(l == 2) {			
			return r.nextBoolean() ? 2 : 6;
		}
		else if(l == 3) {			
			return r.nextBoolean() ? 3 : 7;
		}
		else if(l == 4) {			
			return r.nextBoolean() ? 4 : 5;
		}
		else if(l == 5) {			
			return r.nextBoolean() ? 5 : 1;
		}
		else if(l == 6) {			
			return r.nextBoolean() ? 6 : 3;
		}
		else if(l == 7) {			
			return r.nextBoolean() ? 7 : 2;
		}
		else return r.nextBoolean() ? 8 : 4;		
		
	}
	
	public static Polygon RectangleToPolygon(Rectangle rect) {
	    Polygon result = new Polygon();
	    result.addPoint(rect.getX(), rect.getY());
	    result.addPoint(rect.getX() + rect.getWidth(), rect.getY());
	    result.addPoint(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
	    result.addPoint(rect.getX(), rect.getY() + rect.getHeight());
	    return result;
	}
	
	public static Color RandomColor() {
		return Color.red;
	}
	
	public static Color WhiteColor() {
		return Color.white;
	}
	
	public static float GetLaneX(int l) {
		if(l == 1) {
			return lane1InitX1;			
		}
		else if(l == 2){
			return lane1InitX2;			
		}
		else if(l == 3){
			return lane2InitX1;			
		}
		else if(l == 4){
			return lane2InitX2;			
		}
		else if(l == 5 || l == 6){
			return lane3InitX;			
		}
		else{
			return lane4InitX;			
		}		
	}
	
	public static float GetLaneY(int l) {
		if(l == 1 || l == 2) {
			return lane1InitY;			
		}
		else if(l == 3 || l == 4){
			return lane2InitY;			
		}
		else if(l == 5){
			return lane3InitY1;			
		}
		else if(l == 6){
			return lane3InitY2;			
		}
		else if(l == 7){
			return lane4InitY1;			
		}
		else{
			return lane4InitY2;			
		}	
	}

	public static int GetRoad(int l) {
		if(l == 1 || l == 2) {
			return 1;			
		}
		else if(l == 3 || l == 4) {
			return 2;			
		}
		else if(l == 5 || l == 6) {
			return 3;			
		}
		else {
			return 4;
		}
		
	}

}
