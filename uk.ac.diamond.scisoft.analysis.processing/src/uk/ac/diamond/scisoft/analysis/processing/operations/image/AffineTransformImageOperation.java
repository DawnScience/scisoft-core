package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.image.IImageTransform;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.ErrorMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
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
		//check if dimensions of the data changed after processing
		if (input.getShape()[0] == out.getShape()[0] && input.getShape()[1] == out.getShape()[1]) {
			copyMetadata(input, out);
		} else {
			try {
				List<MetadataType> metadata = input.getMetadata(null);
				boolean axes_found = false;
				for (MetadataType m : metadata) {
					if (m instanceof ErrorMetadata) continue;
					if (axes_found == false && m instanceof AxesMetadata) {
						axes_found = true;
						ILazyDataset[] axes = ((AxesMetadata) m).getAxes();
						Dataset axis_old_x = DatasetUtils.sliceAndConvertLazyDataset(axes[0]);
						Dataset axis_new_x = null;
						//check if axes is 1 or 2 dimensional
						if (axis_old_x.getRank() == 1) {
							//1D
							axis_new_x = DatasetFactory.zeros(new int[]{out.getShape()[0]}, axis_old_x.getDType());
							for (int i = 0 ; i < out.getShape()[0] ; i++) {
								double new_value = axis_old_x.getDouble(0) + (axis_old_x.getDouble(axis_old_x.getShape()[0]-1) - axis_old_x.getDouble(0)) * i / (out.getShape()[0]-1);
								axis_new_x.set(new_value, i);
							}
						} else {
							//2D
							axis_new_x = DatasetFactory.zeros(out.getShape(), axis_old_x.getDType());
							for (int i = 0 ; i < out.getShape()[0] ; i++) {
								double new_value = axis_old_x.getDouble(0, 0) + (axis_old_x.getDouble(axis_old_x.getShape()[0]-1, 0) - axis_old_x.getDouble(0, 0)) * i / (out.getShape()[0]-1);
								for (int j = 0 ; j < out.getShape()[1] ; j++) {
									//System.out.println("i: " + i + " j: " + j);
									axis_new_x.set(new_value, i, j);
								}
							}
						}

						Dataset axis_old_y = DatasetUtils.sliceAndConvertLazyDataset(axes[1]);
						Dataset axis_new_y = null;
						//check if axes is 1 or 2 dimensional
						if (axis_old_y.getRank() == 1) {
							//1D
							axis_new_y = DatasetFactory.zeros(new int[]{out.getShape()[1]}, axis_old_y.getDType());
							for (int j = 0 ; j < out.getShape()[1] ; j++) {
								double new_value = axis_old_y.getDouble(0) + (axis_old_y.getDouble(axis_old_y.getShape()[0]-1) - axis_old_y.getDouble(0)) * j / (out.getShape()[1]-1);
								axis_new_y.set(new_value, j);
							}
						} else {
							//2D
							axis_new_y = DatasetFactory.zeros(out.getShape(), axis_old_y.getDType());
							for (int j = 0 ; j < out.getShape()[1] ; j++) {
								double new_value = axis_old_y.getDouble(0, 0) + (axis_old_y.getDouble(0, axis_old_y.getShape()[1]-1) - axis_old_y.getDouble(0, 0)) * j / (out.getShape()[1]-1);
								for (int i = 0 ; i < out.getShape()[0] ; i++) {
									axis_new_y.set(new_value, i, j);
								}
							}
						}
						AxesMetadataImpl amd = new AxesMetadataImpl(2);
						axis_new_x.setName(axis_old_x.getName());
						axis_new_y.setName(axis_old_y.getName());
						amd.setAxis(0, axis_new_x);
						amd.setAxis(1, axis_new_y);
						out.setMetadata(amd);
					}
					else {
						out.setMetadata(m);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new OperationData(out);
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
