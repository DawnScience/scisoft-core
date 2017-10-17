package org.dawnsci.surfacescatter;

public class AxisEnums {

	public enum xAxes {
		SCANNED_VARIABLE(0, "Scanned Variable"), Q(1, "q");

		private int xAxisNumber;
		private String xAxisName;

		xAxes(int a, String b) {

			this.xAxisNumber = a;
			this.xAxisName = b;

		}

		public String getXAxisName() {
			return xAxisName;
		}

		public int getXAxisNumber() {
			return xAxisNumber;
		}

		public xAxes getXAxesFromString(String k) {

			for (AxisEnums.xAxes xA : AxisEnums.xAxes.values()) {
				if (xA.getXAxisName().equals(k)) {
					return xA;
				}
			}

			return SCANNED_VARIABLE;
		}

		public xAxes getXAxesFromInt(int k) {

			for (AxisEnums.xAxes xA : AxisEnums.xAxes.values()) {
				if (xA.getXAxisNumber() == k) {
					return xA;
				}
			}

			return SCANNED_VARIABLE;
		}

	}

	public static xAxes toXAxis(String in) {

		for (AxisEnums.xAxes xA : AxisEnums.xAxes.values()) {
			if (in.equals(xA.xAxisName)) {
				return xA;
			}
		}

		return xAxes.SCANNED_VARIABLE;
	}

	public static xAxes toXAxis(int in) {

		for (AxisEnums.xAxes xA : AxisEnums.xAxes.values()) {
			if (in == xA.xAxisNumber) {
				return xA;
			}
		}

		return xAxes.SCANNED_VARIABLE;
	}

	public enum yAxes {
		SPLICEDY(0, "Corrected Intensity"), SPLICEDYRAW(1, "Raw Intensity"), SPLICEDYFHKL(2, "Fhkl");

		private int yAxisNumber;
		private String yAxisName;

		yAxes(int a, String b) {

			this.yAxisNumber = a;
			this.yAxisName = b;

		}

		public String getYAxisName() {
			return yAxisName;
		}

		public int getYAxisNumber() {
			return yAxisNumber;
		}

		public yAxes getYAxesFromString(String k) {

			for (AxisEnums.yAxes yA : AxisEnums.yAxes.values()) {
				if (yA.getYAxisName().equals(k)) {
					return yA;
				}
			}

			return SPLICEDY;
		}

		public yAxes getYAxesFromInt(int k) {

			for (AxisEnums.yAxes yA : AxisEnums.yAxes.values()) {
				if (yA.getYAxisNumber() == k) {
					return yA;
				}
			}

			return SPLICEDY;
		}

	}

	public static String toString(yAxes input) {

		switch (input) {
		case SPLICEDY:
			return "Corrected Intensity";
		case SPLICEDYRAW:
			return "Raw Intensity";
		case SPLICEDYFHKL:
			return "Fhkl";
		}
		return null;
	}

	public static int toInt(yAxes input) {

		switch (input) {
		case SPLICEDY:
			return 0;
		case SPLICEDYRAW:
			return 1;
		case SPLICEDYFHKL:
			return 2;
		}
		return 0;
	}

	public static yAxes toYAxis(String in) {

		for (AxisEnums.yAxes yA : AxisEnums.yAxes.values()) {
			if (in.equals(yA.yAxisName)) {
				return yA;
			}
		}

		return yAxes.SPLICEDY;
	}

	public static yAxes toYAxis(int in) {

		for (AxisEnums.yAxes yA : AxisEnums.yAxes.values()) {
			if (in == yA.yAxisNumber) {
				return yA;
			}
		}

		return yAxes.SPLICEDY;
	}
}
