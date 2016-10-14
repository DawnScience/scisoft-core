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
		else if (in.equals("2D Tracking")){
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
		ZERO, ONE, TWO, THREE ,FOUR
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
		}
		return (Integer) null;
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
		return null;
	}
	
	
	
}
