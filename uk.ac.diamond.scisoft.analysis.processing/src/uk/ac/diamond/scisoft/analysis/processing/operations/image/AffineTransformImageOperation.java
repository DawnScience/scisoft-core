package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.api.image.IImageTransform;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.OperationServiceLoader;

public class AffineTransformImageOperation extends AbstractSimpleImageOperation<AffineTransformImageModel> {

	private final Logger logger = LoggerFactory.getLogger(AffineTransformImageOperation.class);

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.AffineTransformImageOperation";
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		if (imageFilterService == null)
			imageFilterService = OperationServiceLoader.getImageFilterService();
		if (imageTransformService == null)
			imageTransformService = OperationServiceLoader.getImageTransformService();

		IDataset out = processImage(input, monitor);
		int[] outShape = out.getShape();
		//check if dimensions of the data changed after processing
		if (input.getShape()[0] == outShape[0] && input.getShape()[1] == outShape[1]) {
			copyMetadata(input, out);
		} else {
			copyMetadata(input, out, false);
			ILazyDataset[] axes = getFirstAxes(input);
			if (axes != null) {
				Dataset axis_new_x = null;
				Dataset axis_new_y = null;
				
				if (axes[0] != null) {
					Dataset axis_old_x = null;
					try {
						axis_old_x = DatasetUtils.sliceAndConvertLazyDataset(axes[0]);
					} catch (DatasetException e) {
						throw new OperationException(this, e);
					}
					//check if axes is 1 or 2 dimensional
					if (axis_old_x.getRank() == 1) {
						//1D
						axis_new_x = DatasetFactory.zeros(axis_old_x.getClass(), outShape[0]);
						for (int i = 0 ; i < outShape[0] ; i++) {
							double new_value = axis_old_x.getDouble(0) + (axis_old_x.getDouble(-1) - axis_old_x.getDouble(0)) * i / (outShape[0]-1);
							axis_new_x.set(new_value, i);
						}
					} else {
						//2D
						axis_new_x = DatasetFactory.zeros(axis_old_x.getClass(), outShape);
						for (int i = 0 ; i < outShape[0] ; i++) {
							double new_value = axis_old_x.getDouble(0, 0) + (axis_old_x.getDouble(-1, 0) - axis_old_x.getDouble(0, 0)) * i / (outShape[0]-1);
							for (int j = 0 ; j < outShape[1] ; j++) {
								//System.out.println("i: " + i + " j: " + j);
								axis_new_x.set(new_value, i, j);
							}
						}
					}
					axis_new_x.setName(axis_old_x.getName());
				}
				if (axes[1] != null) {
					Dataset axis_old_y = null;
					try {
						axis_old_y = DatasetUtils.sliceAndConvertLazyDataset(axes[1]);
					} catch (DatasetException e) {
						throw new OperationException(this, e);
					}
					//check if axes is 1 or 2 dimensional
					if (axis_old_y.getRank() == 1) {
						//1D
						axis_new_y = DatasetFactory.zeros(axis_old_y.getClass(), outShape[1]);
						for (int j = 0 ; j < outShape[1] ; j++) {
							double new_value = axis_old_y.getDouble(0) + (axis_old_y.getDouble(-1) - axis_old_y.getDouble(0)) * j / (outShape[1]-1);
							axis_new_y.set(new_value, j);
						}
					} else {
						//2D
						axis_new_y = DatasetFactory.zeros(axis_old_y.getClass(), outShape);
						for (int j = 0 ; j < outShape[1] ; j++) {
							double new_value = axis_old_y.getDouble(0, 0) + (axis_old_y.getDouble(0, -1) - axis_old_y.getDouble(0, 0)) * j / (outShape[1]-1);
							for (int i = 0 ; i < outShape[0] ; i++) {
								axis_new_y.set(new_value, i, j);
							}
						}
					}
					axis_new_y.setName(axis_old_y.getName());
				}
				AxesMetadata amd = null;
				try {
					amd = MetadataFactory.createMetadata(AxesMetadata.class, 2);
				} catch (MetadataException e) {
					throw new OperationException(this, e);
				}
				amd.setAxis(0, axis_new_x);
				amd.setAxis(1, axis_new_y);
				out.addMetadata(amd);
			}
		}

		return new OperationData(out);
	}

	@Override
	public IDataset processImage(IDataset dataset, IMonitor monitor) {
		IImageTransform service = getImageTransformService();
		double a11 = model.getA11();
		double a12 = model.getA12();
		double a21 = model.getA21();
		double a22 = model.getA22();
		double dx = model.getDx();
		double dy = model.getDy();
		boolean resize = model.isResize();
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
