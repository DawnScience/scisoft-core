package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.image.IImageTransform;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.image.RotateImageModel.AngleUnit;

public class RotateImageOperation extends AbstractSimpleImageOperation<RotateImageModel> {

	private final Logger logger = LoggerFactory.getLogger(RotateImageOperation.class);

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.RotateImageOperation";
	}

	@Override
	public IDataset processImage(IDataset dataset, IMonitor monitor) {
		IImageTransform service = getImageTransformService();
		double angle = ((RotateImageModel)model).getAngle();
		AngleUnit unit = ((RotateImageModel)model).getUnit();
		boolean resize = ((RotateImageModel)model).isResize();
		IDataset rotated = null;
		if (unit == AngleUnit.RADIAN)
			angle = Math.toDegrees(angle);
		try {
			rotated = service.rotate(dataset, angle, !resize);
		} catch (Exception e) {
			logger.error("An error occured while rotating:", e);
		}
		return rotated;
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
