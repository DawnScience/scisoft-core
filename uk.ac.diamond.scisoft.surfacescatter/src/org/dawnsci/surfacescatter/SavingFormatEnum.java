package org.dawnsci.surfacescatter;

public class SavingFormatEnum {

	public enum SaveFormatSetting {
		GenX("GenX", 0), Anarod("Anarod", 1), int_format(".int", 2), ASCII("X/Y/Ye", 3);

		private String displayName;
		private int position;

		SaveFormatSetting(String aV, int pos) {

			displayName = aV;
			position = pos;

		}

		public String getDisplayName() {
			return displayName;
		}

		public int getPosition() {
			return position;
		}

		public static String toString(SaveFormatSetting methodology) {

			return methodology.getDisplayName();

		}

		public static SaveFormatSetting toMethod(String in) {

			for (SaveFormatSetting sfs : SaveFormatSetting.values()) {
				if (in.equals(sfs.getDisplayName())) {
					return sfs;
				}
			}

			return null;

		}

		public static int toInt(SaveFormatSetting in) {

			return in.getPosition();

		}

		public static SaveFormatSetting toMethod(int in) {

			for (SaveFormatSetting sfs : SaveFormatSetting.values()) {
				if (in == sfs.getPosition()) {
					return sfs;
				}
			}

			return null;
		}

	}

}
