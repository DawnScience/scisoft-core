package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.image.IImageTransform;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.image.AffineTransformImageModel;

public class AffineTransformImageOperation extends AbstractSimpleImageOperation<AffineTransformImageModel> {

	private final Logger logger = LoggerFactory.getLogger(AffineTransformImageOperation.class);

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.AffineTransformImageOperation";
	}

	@Override
	public IDataset processImage(IDataset dataset, IMonitor monitor) {
		IImageTransform service = getImageTransformService();
		double a11 = ((AffineTransformImageModel)model).getA11();
		double a12 = ((AffineTransformImageModel)model).getA12();
		double a21 = ((AffineTransformImageModel)model).getA21();
		double a22 = ((AffineTransformImageModel)model).getA22();
		double dx = ((AffineTransformImageModel)model).getDx();
		double dy = ((AffineTransformImageModel)model).getDy();
		boolean resize = ((AffineTransformImageModel)model).isResize();
		IDataset transformed = null;
		try {
			transformed = service.affineTransform(dataset, a11, a12, a21, a22, dx, dy, !resize);
		} catch (Exception e) {
			logger.error("An error occured while performing the affine transformation:", e);
		}
		return transformed;
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
