package org.dawnsci.surfacescatter;

public class SavingFormatEnum {

	public enum SaveFormatSetting {
		GenX,
		Anarod, 
		int_format,
		ASCII;

		public static String toString(SaveFormatSetting methodology){
			
			switch(methodology){
				case GenX:
					return "GenX";
				case Anarod:
					return "Anarod";
				case int_format:
					return ".int";
				case ASCII:
					return "X/Y/Ye";
			}
			return null;
		}
		
		public static SaveFormatSetting  toMethod(String in){
			
			if (in.equals("GenX")){
				return SaveFormatSetting.GenX;
			}
			else if (in.equals("Anarod")){
				return SaveFormatSetting.Anarod;
			}
			else if (in.equals(".int")){
				return SaveFormatSetting.int_format;
			}
			else if (in.equals("X/Y/Ye")){
				return SaveFormatSetting.ASCII;
			}
			
			return null;
		}
		
		public static int toInt(SaveFormatSetting   in){
			
			if (in.equals(SaveFormatSetting.GenX)){
				return 0;
			}
			else if (in.equals(SaveFormatSetting.Anarod)){
				return 1;
			}
			else if (in.equals(SaveFormatSetting.int_format)){
				return 2;
			}
			else if (in.equals(SaveFormatSetting.ASCII)){
				return 3;
			}
			
			return (Integer) null;
		}
		
		public static SaveFormatSetting toMethod(int in){
			
			if (in == 0){
				return SaveFormatSetting.GenX;
			}
			else if (in == 1){
				return SaveFormatSetting.Anarod;
			}
			else if (in == 2){
				return SaveFormatSetting.int_format;
			}
			else if (in == 3){
				return SaveFormatSetting.ASCII;
			}
			return null;
		}
		
	}		
	
	
	
	
}
