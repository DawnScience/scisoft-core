package org.dawnsci.surfacescatter;

public class BatchSetupMiscellaneousProperties {

	private BatchSetupYAxes[] bsya;
	private boolean useQ;

	public BatchSetupMiscellaneousProperties() {
		bsya = new BatchSetupYAxes[AxisEnums.yAxes.values().length];

		for (int i = 0; i < AxisEnums.yAxes.values().length; i++) {
			bsya[i] = new BatchSetupYAxes(AxisEnums.toYAxis(i), true);

		}
		useQ = false;
	}

	public BatchSetupYAxes[] getBsya() {
		return bsya;
	}

	public void setBsya(BatchSetupYAxes[] bsya) {
		this.bsya = bsya;
	}

	public boolean isUseQ() {
		return useQ;
	}

	public void setUseQ(boolean useQ) {
		this.useQ = useQ;
	}

}
