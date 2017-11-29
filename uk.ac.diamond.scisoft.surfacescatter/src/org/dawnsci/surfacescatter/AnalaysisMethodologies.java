package org.dawnsci.surfacescatter;

public class AnalaysisMethodologies {

	public enum Methodology {
		TWOD(0, "2D"),
		X(1, "X") ,
		Y(2, "Y"), 
		SECOND_BACKGROUND_BOX(3, "Second Background Box"), 
		OVERLAPPING_BACKGROUND_BOX(4, "O'l'ing Bg Box");
		
		private int methodNumber;
		private String methodName;

		Methodology(int a, String b) {

			this.methodNumber = a;
			this.methodName = b;

		}

		
	}

	public static String toString(Methodology methodology){
		
		return methodology.methodName;
	}
	
	public static Methodology toMethodology(String in){
		
		for(Methodology m :Methodology.values()) {
			if(in.equals(m.methodName)) {
				return m;
			}
			
		}
		
		return null;
	}
	
	
	public static Methodology toMethodology(int in){
		
		if(in == -1) {
			return Methodology.TWOD;
		}
		
		for(Methodology m :Methodology.values()) {
			if(in == m.methodNumber) {
				return m;
			}
			
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
				return "2D Gaussian (Slow)";
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
		else if (in.equals("2D Gaussian (Slow)")){
			return FitPower.TWOD_GAUSSIAN;
		}
		else if (in.equals("2D Exponential")){
			return FitPower.TWOD_EXPONENTIAL;
		}
		return null;
	}
}
