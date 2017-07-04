package org.dawnsci.surfacescatter;

public class AnalaysisMethodologies {

	public enum Methodology {
		TWOD, TWOD_TRACKING, X ,Y, SECOND_BACKGROUND_BOX, OVERLAPPING_BACKGROUND_BOX
	}

	public static String toString(Methodology methodology){
		
		switch(methodology){
			case X:
				return "X";
			case Y:
				return "Y";
			case TWOD:
				return "2D";
			case TWOD_TRACKING:
				return "2D Tracking";
			case SECOND_BACKGROUND_BOX:
				return "Second Background Box";
			case OVERLAPPING_BACKGROUND_BOX:
				return "O'l'ing Bg Box";
		}
		return null;
	}
	
	public static Methodology toMethodology(String in){
		
		if (in.equals("X")){
			return Methodology.X;
		}
		else if (in.equals("Y")){
			return Methodology.Y;
		}
		else if (in.equals("2D")){
			return Methodology.TWOD;
		}
		
		else if (in.equals("TWOD")){
			return Methodology.TWOD;
		}
		
		else if (in.equals("2D Tracking")){
			return Methodology.TWOD_TRACKING;
		}
		
		else if (in.equals("TWOD_TRACKING")){
			return Methodology.TWOD_TRACKING;
		}
		
		else if (in.equals("Second Background Box")){
			return Methodology.SECOND_BACKGROUND_BOX;
		}
		else if (in.equals("O'l'ing Bg Box")){
			return Methodology.OVERLAPPING_BACKGROUND_BOX;
		}
		return null;
	}
	
	
	
	public enum FitPower {
		ZERO, ONE, TWO, THREE ,FOUR, TWOD_GAUSSIAN, TWOD_EXPONENTIAL
	}
	
	

	public static int toInt(FitPower num){
		
		switch(num){
			case ZERO:
				return 0;
			case ONE:
				return 1;
			case TWO:
				return 2;
			case THREE:
				return 3;
			case FOUR:
				return 4;
			case TWOD_GAUSSIAN:
				return 5;
			case TWOD_EXPONENTIAL:
				return 6;
		}
		return (Integer) null;
	}
	
	public static String toString (FitPower num){
		
		switch(num){
			case ZERO:
				return "0";
			case ONE:
				return "1";
			case TWO:
				return "2";
			case THREE:
				return "3";
			case FOUR:
				return "4";
			case TWOD_GAUSSIAN:
				return "2D Gaussian";
			case TWOD_EXPONENTIAL:
				return "2D Exponential";
		}
		return (String) null;
	}
	
	public static FitPower toFitPower(int in){
		
		if(in == 0){
			return FitPower.ZERO;
		}
		else if (in == 1){
			return FitPower.ONE;
		}
		else if (in == 2){
			return FitPower.TWO;
		}
		else if (in == 3){
			return FitPower.THREE;
		}
		else if (in == 4){
			return FitPower.FOUR;
		}
		else if (in == 5){
			return FitPower.TWOD_GAUSSIAN;
		}
		else if (in == 6){
			return FitPower.TWOD_EXPONENTIAL;
		}
		return null;
	}
	
	
	public static FitPower toFitPower(String in){
		
		if(in.equals("0")){
			return FitPower.ZERO;
		}
		else if(in.equals("ZERO")){
			return FitPower.ZERO;
		}
		else if (in.equals("1")){
			return FitPower.ONE;
		}
		else if (in.equals("ONE")){
			return FitPower.ONE;
		}
		else if (in.equals("2")){
			return FitPower.TWO;
		}
		else if (in.equals("TWO")){
			return FitPower.TWO;
		}
		
		else if (in.equals("3")){
			return FitPower.THREE;
		}
		else if (in.equals("THREE")){
			return FitPower.THREE;
		}
		else if (in.equals("4")){
			return FitPower.FOUR;
		}
		else if (in.equals("FOUR")){
			return FitPower.FOUR;
		}
		else if (in.equals("2D Gaussian")){
			return FitPower.TWOD_GAUSSIAN;
		}
		else if (in.equals("2D Exponential")){
			return FitPower.TWOD_EXPONENTIAL;
		}
		return null;
	}
}