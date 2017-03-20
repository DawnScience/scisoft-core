package org.dawnsci.surfacescatter;

public class ProcessingMethodsEnum {

	public enum ProccessingMethod {
		AUTOMATIC, MANUAL;

			
		public static String toString(ProccessingMethod methodology){
			
			switch(methodology){
				case AUTOMATIC:
					return "AUTOMATIC";
				case MANUAL:
					return "MANUAL";
			}
			return null;
		}
		
		public static ProccessingMethod toMethodology(String in){
			
			if (in.equals("AUTOMATIC")){
				return ProccessingMethod.AUTOMATIC;
			}
			else if (in.equals("MANUAL")){
				return ProccessingMethod.MANUAL;
			}
			return null;
		}	
	
		public static int toInt(ProccessingMethod num){
			
			switch(num){
				case AUTOMATIC:
					return 0;
				case MANUAL:
					return 1;
				}
			return (Integer) null;
		}
			
		public static ProccessingMethod toMethodology(int in){
			
			if(in == 0){
				return ProccessingMethod.AUTOMATIC;
			}
			else if (in == 1){
				return ProccessingMethod.MANUAL;
			}
			return null;
		}
	}
}