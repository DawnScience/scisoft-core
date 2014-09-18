package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class RollingBallBaselineModel extends AbstractOperationModel {

	@OperationModelField(min=1, hint="Radius should be smaller than size of features",label = "Set ball radius in pixels" )
	private int ballRadius = 100;

	public int getBallRadius() {
		return ballRadius;
	}

	public void setBallRadius(int ballRadius) {
		firePropertyChange("ballRadius", this.ballRadius, this.ballRadius = ballRadius);
	}
	
	
	
}
