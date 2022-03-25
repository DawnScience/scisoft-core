/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.nio.file.Files;
import java.util.Objects;

import org.eclipse.dawnsci.analysis.api.persistence.IMarshallerService;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import uk.ac.diamond.scisoft.analysis.processing.metadata.OperationMetadata;
import uk.ac.diamond.scisoft.diffraction.powder.DiffractionImageData;
import uk.ac.diamond.scisoft.diffraction.powder.MovingBeamCalibrationConfig;
import uk.ac.diamond.scisoft.diffraction.powder.NexusCalibrationExportUtils;

@Atomic
public class SplitAndCalibrateFramesOperation extends AbstractOperationBase<SplitAndCalibrateFramesModel, OperationData>
		implements IExportOperation {

	private PropertyChangeListener listener;
	private volatile MovingBeamCalibrationScanSplitter cache;
	private volatile MovingBeamCalibrationConfig cachedConfig;
	public static String ctag = "_calibrations";

	private BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
	IMarshallerService m = bundleContext.getService(bundleContext.getServiceReference(IMarshallerService.class));
	IPersistenceService ps = bundleContext.getService(bundleContext.getServiceReference(IPersistenceService.class));

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam.SplitAndCalibrateFramesOperation";
	}

	@Override
	public OperationRank getInputRank() {
		// TODO Auto-generated method stub
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		// TODO Auto-generated method stub
		return OperationRank.TWO;
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor) throws OperationException {

		SliceFromSeriesMetadata ssm = slice.getFirstMetadata(SliceFromSeriesMetadata.class);
		SourceInformation info = ssm.getSourceInfo();
		String scanName = (new File(info.getFilePath())).getName().replace(".nxs", "");
		
		String cFolder = getOutputDirectory(slice) + File.separatorChar + scanName + ctag;

		MovingBeamCalibrationScanSplitter lcache = cache;

		if (Objects.isNull(lcache)) {
			synchronized (this) {
				lcache = cache;
				if (Objects.isNull(lcache)) {
						MovingBeamCalibrationScanSplitter newcache;
						try {
							cachedConfig = parseJSONConfig(model.getPathToCalibrationDescriptionConfig(), m);
							newcache = new MovingBeamCalibrationScanSplitter(slice, model, cachedConfig);
							validateOutputFolder(cFolder);
	
						} catch (RuntimeException e) {
							throw new OperationException(this,
									"Error reported during initialisation of the calibrated frame splitter. "
											+ e.getMessage());
						} catch (Exception e) {
							throw new OperationException(this, "Error loading claibration configuration from file");
						}
	
						if (Objects.isNull(newcache))
							throw new OperationException(this, "Error initialising the calibrated frame splitter");
						cache = lcache = newcache;
				}
			}
		}

		try {			
			DiffractionImageData dimg = cache.calibrateFrameFromSlice(slice);

			NexusCalibrationExportUtils.saveToMovingBeamCompatibleNexusFile(slice,
					DatasetFactory.createFromObject(cache.getCalibratedPosition(ssm)), dimg.getMetaData(),
					dimg.getCalibrationInfo(), cache.getFileNameForFrame(cFolder, ssm, scanName),
					cachedConfig.getXReferenceAxisPath(), cachedConfig.getYReferenceAxisPath());
		} catch (DatasetException e) {
			throw new OperationException(this, "Error calibrating the frame: " + ssm.getSliceInfo().getSliceNumber());
		} catch (RuntimeException e) {
			throw new OperationException(this, "Error on cache interaction: " + e.getMessage());
		} catch (Exception e) {
			throw new OperationException(this, "Error on writing the calibration to file");
		}

		return new OperationData(slice);  
	}

	@Override
	public void setModel(SplitAndCalibrateFramesModel model) {

		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					cache = null;
					cachedConfig = null;
				}
			};
		} else {
			((AbstractOperationModel) this.model).removePropertyChangeListener(listener);
		}

		((AbstractOperationModel) this.model).addPropertyChangeListener(listener);
	}

	@Override
	public void init() {
		cache = null;

	}

	private void validateOutputFolder(String oDir) throws OperationException {
		File od = new File(oDir);
		if (od.isDirectory())
			throw new OperationException(this, "Output folder already exists! cannot overwrite!");
		if (!od.mkdir())
			throw new OperationException(this, String
					.format("Output directory: %s for calibrations cannot be created. Check write permissions", oDir));

	}

	/**
	 * parse the JSON config used for configuring the individual frame
	 * calibrations
	 * 
	 * @param path path to the config containing the frame calibration
	 * parameters
	 * @param marheller service used for parsing the json: typically
	 * {@link org.eclipse.dawnsci.json.MarshallerService}.
	 * @return
	 * @throws Exception
	 */
	public static MovingBeamCalibrationConfig parseJSONConfig(String path, IMarshallerService mservice)
			throws Exception {
		String json = "";
		MovingBeamCalibrationConfig lcache;
		File pathToJSON = new File(path);

		json = new String(Files.readAllBytes(pathToJSON.toPath()));
		lcache = mservice.unmarshal(json, MovingBeamCalibrationConfig.class);
		return lcache;
	}
	
	
	private String getOutputDirectory(IDataset input) {
		String outputDirectory = null;
		
		if (model.getPathToOutputFolder() != null && !model.getPathToOutputFolder().isEmpty()) {
			outputDirectory = model.getPathToOutputFolder();
		} else {
			OperationMetadata omd = input.getFirstMetadata(OperationMetadata.class);
			if (omd.getOutputFilename() != null) {
				File f = new File(omd.getOutputFilename());
				outputDirectory = f.getParent();
			}
		}
		if (outputDirectory == null || outputDirectory.isEmpty()) throw new OperationException(this, "Output directory not set!");
		
		return outputDirectory;
		}
}
