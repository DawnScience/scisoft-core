package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;

public class RollingBallBaselineModel extends AbstractOperationModel {

	int ballRadius = 100;

	public int getBallRadius() {
		return ballRadius;
	}

	public void setBallRadius(int ballRadius) {
		firePropertyChange("ballRadius", this.ballRadius, this.ballRadius = ballRadius);
	}
	
	
	
}
