package org.dawnsci.surfacescatter;

public class MethodSettingEnum {

	public enum MethodSetting {
		SXRD(0, "SXRD"),
		Reflectivity_with_Flux_Correction_Gaussian_Profile(1, "Reflectivity with Flux Correction with Gaussian Profile"), 
		Reflectivity_without_Flux_Correction_Gaussian_Profile(2,"Reflectivity without Flux Correction with Gaussian Profile"),
		Reflectivity_with_Flux_Correction_Simple_Scaling(3,"Reflectivity with Flux Correction with Top Hat Profile"), 
		Reflectivity_without_Flux_Correction_Simple_Scaling(4,"Reflectivity without Flux Correction with Top Hat Profile"),
		Reflectivity_NO_Correction (5, "Reflectivity with NO Corrections");

		private String correctionsName;
		private int correctionsNumber;

		MethodSetting(int a, String b){
			this.correctionsNumber= a;
			this.correctionsName = b;
		}

		public String getCorrectionsName() {
			return correctionsName;
		}

		public int getCorrectionsNumber() {
			return correctionsNumber;
		}

		public static String toString(MethodSetting methodology){
			return methodology.getCorrectionsName();
		}

		public static int toInt(MethodSetting methodology){
			return methodology.getCorrectionsNumber();
		}

		public static MethodSetting toMethod(String in){

			for(MethodSettingEnum.MethodSetting xA : MethodSettingEnum.MethodSetting.values()){
				if(xA.getCorrectionsName().equals(in)){
					return xA;
				}
			}
			//			
			//			
			//			if (in.equals("SXRD")){
			//				return MethodSetting.SXRD;
			//			}
			//			else if (in.equals("Reflectivity with Flux Correction")){
			//				return MethodSetting.Reflectivity_with_Flux_Correction;
			//			}
			//			else if (in.equals("Reflectivity without Flux Correction")){
			//				return MethodSetting.Reflectivity_without_Flux_Correction;
			//			}
			//			
			//			else if (in.equals("NO Correction")){
			//				return MethodSetting.Reflectivity_NO_Correction;
			//			}
			//			
			return null;
		}

		//		public static int toInt(MethodSetting in){
		//
		//			if (in.equals(MethodSetting.SXRD)){
		//				return 0;
		//			}
		//			else if (in.equals(MethodSetting.Reflectivity_with_Flux_Correction)){
		//				return 1;
		//			}
		//			else if (in.equals(MethodSetting.Reflectivity_without_Flux_Correction)){
		//				return 2;
		//			}
		//
		//			else if (in.equals(MethodSetting.Reflectivity_NO_Correction)){
		//				return 3;
		//			}
		//
		//			return (Integer) null;
		//		}

		public static MethodSetting toMethod(int in){

			for(MethodSettingEnum.MethodSetting xA : MethodSettingEnum.MethodSetting.values()){
				if(xA.getCorrectionsNumber() == in){
					return xA;
				}
			}
			//			
			//			
			//			if (in == 0){
			//				return MethodSetting.SXRD;
			//			}
			//			else if (in == 1){
			//				return MethodSetting.Reflectivity_with_Flux_Correction;
			//			}
			//			else if (in == 2){
			//				return MethodSetting.Reflectivity_without_Flux_Correction;
			//			}
			//
			//			else if (in == 3){
			//				return MethodSetting.Reflectivity_NO_Correction;
			//			}

			return null;
		}
	}	
}
