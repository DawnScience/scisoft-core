package org.eclipse.dawnsci.analysis.api.diffraction;

public enum NumberOfSymmetryFolds {
		TWO_FOLD(1,"Two fold"),
		FOUR_FOLD(2,"Four fold"),
		SIX_FOLD(3, "Six fold"),
		EIGHT_FOLD(4, "Eight fold");
		
		private final int foldsOfSymmetry;
		private final String displayName;
		
		NumberOfSymmetryFolds(int foldsOfSymmetry, String displayName) {
			this.foldsOfSymmetry = foldsOfSymmetry;
			this.displayName = displayName;
		}
		
		public int getFoldsOfSymmetry() {
			return this.foldsOfSymmetry;
		}
		
		public String getDisplayName() {
			return displayName;
		}
		
		@Override
		public String toString() {
			return getDisplayName();
		}
		
		public static NumberOfSymmetryFolds getFromName(String name) {
			for (NumberOfSymmetryFolds n : NumberOfSymmetryFolds.values()) {
				if (n.getDisplayName().equals(name)) {
					return n;
				}
			}
			
			return null;
		}
	}