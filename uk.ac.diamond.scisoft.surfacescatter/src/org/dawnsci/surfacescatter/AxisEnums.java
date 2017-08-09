package org.dawnsci.surfacescatter;


public class AxisEnums {

	public enum xAxes {
		SCANNED_VARIABLE(0, "Scanned Variable"), 
		Q(1, "q");
		
		private int xAxisNumber;
		private String xAxisName;
		
		xAxes(int a, String b){
			
			this.xAxisNumber =a;
			this.xAxisName = b;
			
		}
		
		public String getXAxisName(){
			return xAxisName;
		}
		
		public int getXAxisNumber(){
			return xAxisNumber;
		}
		
		
	}

	public static String toString(xAxes input){
		
		switch(input){
			case SCANNED_VARIABLE:
				return "Scanned Variable";
			case Q:
				return "Q";
		}
		return null;
	}
	
	public static xAxes toXAxis(String in){
		
		if (in.equals("Scanned Variable")){
			return xAxes.SCANNED_VARIABLE;
		}
		else if (in.equals("Q")){
			return xAxes.Q;
		}
		
		return null;
	}
	
	public enum yAxes {
		SPLICEDY(0, "Corrected Intensity"), 
		SPLICEDYRAW(1, "Raw Intensity"), 
		SPLICEDYFHKL(2, "Fhkl");
		
		private int yAxisNumber;
		private String yAxisName;
		
		yAxes(int a, String b){
			
			this.yAxisNumber =a;
			this.yAxisName = b;
			
		}
		
		public String getYAxisName(){
			return yAxisName;
		}
		
		public int getYAxisNumber(){
			return yAxisNumber;
		}
		
	}

	public static String toString(yAxes input){
		
		switch(input){
			case SPLICEDY:
				return "Corrected Intensity";
			case SPLICEDYRAW:
				return "Raw Intensity";
			case SPLICEDYFHKL:
				return "Fhkl";
		}
		return null;
	}
	
	public static int toInt(yAxes input){
		
		switch(input){
			case SPLICEDY:
				return 0;
			case SPLICEDYFHKL:
				return 1;
			case SPLICEDYRAW:
				return 2;
		}
		return 0;
	}
	
	public static yAxes toYAxis(String in){
		
		if (in.equals("Intensity")){
			return yAxes.SPLICEDY;
		}
		else if (in.equals("Uncorrected Intensity")){
			return yAxes.SPLICEDYRAW;
		}
		else if (in.equals("Fhkl")){
			return yAxes.SPLICEDYFHKL;
		}
		return null;
	}

	
	public static yAxes toYAxis(int in){
		
		if (in == 0){
			return yAxes.SPLICEDY;
		}
		else if (in  == 2){
			return yAxes.SPLICEDYRAW;
		}
		else if (in == 1){
			return yAxes.SPLICEDYFHKL;
		}
		return null;
	}
	
}
