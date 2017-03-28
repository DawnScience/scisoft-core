package org.dawnsci.surfacescatter;

public class IntensityDisplayEnum {

	public enum IntensityDisplaySetting {
		Corrected_Intensity,
		Fhkl, 
		Raw_Intensity;

		public static String toString(IntensityDisplaySetting methodology){
			
			switch(methodology){
				case Corrected_Intensity:
					return "Corrected Intensity";
				case Fhkl:
					return "Fhkl";
				case Raw_Intensity:
					return "Raw Intensity";
			}
			return null;
		}
		
		public static IntensityDisplaySetting toMethod(String in){
			
			if (in.equals("Corrected Intensity")){
				return IntensityDisplaySetting.Corrected_Intensity;
			}
			else if (in.equals("Fhkl")){
				return IntensityDisplaySetting.Fhkl;
			}
			else if (in.equals("Raw Intensity")){
				return IntensityDisplaySetting.Raw_Intensity;
			}
			
			return null;
		}
		
		public static int toInt(IntensityDisplaySetting  in){
			
			if (in.equals(IntensityDisplaySetting .Corrected_Intensity)){
				return 0;
			}
			else if (in.equals(IntensityDisplaySetting .Fhkl)){
				return 1;
			}
			else if (in.equals(IntensityDisplaySetting.Raw_Intensity)){
				return 2;
			}
			
			return (Integer) null;
		}
		
		public static IntensityDisplaySetting toMethod(int in){
			
			if (in == 0){
				return IntensityDisplaySetting.Corrected_Intensity;
			}
			else if (in == 1){
				return IntensityDisplaySetting.Fhkl;
			}
			else if (in == 2){
				return IntensityDisplaySetting.Raw_Intensity;
			}
			
			return null;
		}
		
	}		
	
}
