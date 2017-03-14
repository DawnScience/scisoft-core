package org.dawnsci.surfacescatter;

public class MethodSettingEnum {

	public enum MethodSetting {
		SXRD,
		Reflectivity_with_Flux_Correction, 
		Reflectivity_without_Flux_Correction,
		Reflectivity_NO_Correction;

		public static String toString(MethodSetting methodology){
			
			switch(methodology){
				case SXRD:
					return "SXRD";
				case Reflectivity_with_Flux_Correction:
					return "Reflectivity with Flux Correction";
				case Reflectivity_without_Flux_Correction:
					return "Reflectivity without Flux Correction";
				case Reflectivity_NO_Correction:
					return "Reflectivity with NO Corrections";
			}
			return null;
		}
		
		public static MethodSetting toMethod(String in){
			
			if (in.equals("SXRD")){
				return MethodSetting.SXRD;
			}
			else if (in.equals("Reflectivity with Flux Correction")){
				return MethodSetting.Reflectivity_with_Flux_Correction;
			}
			else if (in.equals("Reflectivity without Flux Correction")){
				return MethodSetting.Reflectivity_without_Flux_Correction;
			}
			
			else if (in.equals("NO Correction")){
				return MethodSetting.Reflectivity_NO_Correction;
			}
			
			return null;
		}
		
		public static int toInt(MethodSetting in){
			
			if (in.equals(MethodSetting.SXRD)){
				return 0;
			}
			else if (in.equals(MethodSetting.Reflectivity_with_Flux_Correction)){
				return 1;
			}
			else if (in.equals(MethodSetting.Reflectivity_without_Flux_Correction)){
				return 2;
			}
			
			else if (in.equals(MethodSetting.Reflectivity_NO_Correction)){
				return 3;
			}
			
			return (Integer) null;
		}
		
		public static MethodSetting toMethod(int in){
			
			if (in == 0){
				return MethodSetting.SXRD;
			}
			else if (in == 1){
				return MethodSetting.Reflectivity_with_Flux_Correction;
			}
			else if (in == 2){
				return MethodSetting.Reflectivity_without_Flux_Correction;
			}
			
			else if (in == 3){
				return MethodSetting.Reflectivity_NO_Correction;
			}
			
			return null;
		}
		
	}	
}
