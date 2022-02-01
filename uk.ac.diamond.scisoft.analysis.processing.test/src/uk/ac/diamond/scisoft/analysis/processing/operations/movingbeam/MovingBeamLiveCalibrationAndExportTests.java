package uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.diffraction.IPowderCalibrationInfo;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.persistence.IMarshallerService;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.json.MarshallerService;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.OriginMetadata;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import uk.ac.diamond.scisoft.analysis.crystallography.CalibrationFactory;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionMetadataUtils;
import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam.MovingBeamTestUtils.MovingBeamConstants;
import uk.ac.diamond.scisoft.diffraction.powder.DiffractionImageData;
import uk.ac.diamond.scisoft.diffraction.powder.MovingBeamCalibrationConfig;
import uk.ac.diamond.scisoft.diffraction.powder.NexusCalibrationExportUtils;
import uk.ac.diamond.scisoft.diffraction.powder.PowderCalibrationInfoImpl;
import uk.ac.diamond.scisoft.diffraction.powder.SimpleCalibrationParameterModel;

public class MovingBeamLiveCalibrationAndExportTests extends AbstractMovingBeamCalibrationTest {

	Dataset position = DatasetFactory.createFromObject(new double[] { 1, 2 });
	IDiffractionMetadata dmeta;
	IDataset img;

	IDataset testFrame;

	TemporaryFolder tmpFolder;
	File tmp;
	File scan;
	IMarshallerService marshaller = new MarshallerService();
	MovingBeamCalibrationConfig config;
	Integer[] ringSet = new Integer[] { 1, 2, 3, 4 };
	SimpleCalibrationParameterModel cmod = new SimpleCalibrationParameterModel();
	NexusFileFactoryHDF5 factory = new NexusFileFactoryHDF5();

	private static String STANDARD = "CeO2";
	private static String REGEXPATTERN = ".*_frame_\\d\\d\\d.nxs";
	private static int ZeroPad = 5;
	private static double[] referenceFramePos = new double[] { 1, 5 };

	private double[] xpos = new double[] { -1, 0, 1 };
	private double[] ypos = new double[] { -1, 0, 1 };
	private int[] scanFrames = new int[] { 0, 2, 1 };
	private int[] scanShape = new int[] { 3, 1679, 1475 };
	private SliceND sampling = new SliceND(scanShape, new Slice(0, 1, 1), new Slice(), new Slice());
	private String pathToReferenceCalibration = "";

	@Before
	public void setUp() throws IOException {
		dmeta = getDefaultPilatusMetadata();
		img = makeImage(dmeta);
		tmpFolder = new TemporaryFolder();
		tmpFolder.create();
		tmp = tmpFolder.newFolder("test");
		cmod.setAutomaticCalibration(false);
		cmod.setRingSet(new HashSet<Integer>(Arrays.asList(ringSet)));
		cmod.setnPointsPerRing(400);
		cmod.setMaxSearchSize(20);

		cmod.setIsPointCalibration(true); // as these variables alter variables
											// on classes
											// within the main class the order
											// of application
											// matters and can cause 1 test to
											// fail intermittently.
		cmod.setFloatEnergy(false);
		cmod.setNumberOfRings(ringSet.length);

		File calRef = File.createTempFile("mockCalibration-", ".nxs", tmp);// generate
																			// a
																			// temporary
																			// calibration
																			// as
																			// the
																			// MovingBeamCalibrationScanSplitter
																			// needs
																			// a
																			// reference
																			// seed
		try {
			writeReferenceMetadata(calRef);
			pathToReferenceCalibration = calRef.getAbsolutePath();
		} catch (Exception e) {
			Assert.fail("Check write access privaleges!");
		}
		setMovingBeamConfig();

	}

	@Test
	public void testParseMovingBeamCalibrationConfig() throws Exception {
		MovingBeamCalibrationScanSplitter frameSplitter = initialiseFrameSplitterAndTestFrame(null);
		MovingBeamCalibrationConfig conf = frameSplitter.getConfig();
		SimpleCalibrationParameterModel lmodel = conf.getModel();

		lmodel.setIsPointCalibration(true);
		lmodel.setFloatEnergy(conf.getFloatEnergy());// Construction of the
		// SimpleCalibrationParameterModel
		// object from the
		// marshaler does
		// not set the
		// variables on the
		// subclasses;
		// direct equality tests will fail without explicit
		// post-initialisation setting of the values for floatEnergy and
		// isPointCalibration.

		Assert.assertEquals("Dataset path not correct", MovingBeamConstants.DATASETPATH, conf.getDatasetPath());
		Assert.assertEquals("Calibration path not correct", pathToReferenceCalibration,
				conf.getInitialCalibration());
		Assert.assertEquals("X axis path not correct", MovingBeamConstants.XAXISREFERENCE,
				conf.getXReferenceAxisPath());
		Assert.assertEquals("Y axis path not correct", MovingBeamConstants.YAXISREFERENCE,
				conf.getYReferenceAxisPath());
		Assert.assertEquals("Standard name not correct", STANDARD, conf.getStandard());
		Assert.assertEquals("Pad with zeros not correct", ZeroPad, conf.getPadWithZeros());
		Assert.assertArrayEquals("Position assigned to reference calibration not correct", referenceFramePos,
				conf.getReferenceAxesPositionXY(), 1e-6);
		Assert.assertTrue("Local calibration parameter model differs from expected model",
				compareSimpleCalibrationParameterModels(lmodel, cmod));

	}

	@Test
	public void testIndividualCalibratedFrameMetadata() throws Exception {
		setMovingBeamConfig();

		MovingBeamCalibrationScanSplitter scanSplitter = initialiseFrameSplitterAndTestFrame(null);
		MovingBeamCalibrationConfig conf = scanSplitter.getConfig();
		conf.setReferenceAxesPositionXY(new double[2]);
		scanSplitter.setConfig(conf);

		conf.getModel().setIsPointCalibration(true);
		conf.getModel().setFloatEnergy(false);
		DiffractionImageData dimage = scanSplitter.calibrateFrameFromSlice(testFrame);

		IPowderCalibrationInfo calInfo = dimage.getCalibrationInfo();
		Assert.assertTrue("Calibrant name doesnt match!", calInfo.getCalibrantName().equals(STANDARD));
		Assert.assertTrue("Incorrect decription", calInfo.getMethodDescription()
				.equals("Manual powder diffraction image calibration using point parameters"));

		DoubleDataset ds = DatasetUtils.cast(DoubleDataset.class, calInfo.getCalibrantDSpaceValues());
		double[] actual = CalibrationFactory.getCalibrationStandards().getCalibrationPeakMap(STANDARD).getHKLs()
				.stream().mapToDouble(hkl -> hkl.getDNano() * 10.).toArray();

		Assert.assertArrayEquals("incorrect dspacings used in calibration", ds.getData(), actual, 1e-6);

	}

	@Test
	public void testUseOfReferenceFramePosition() throws Exception {
		MovingBeamCalibrationScanSplitter scanSplitter = initialiseFrameSplitterAndTestFrame(null);
		MovingBeamCalibrationConfig conf = scanSplitter.getConfig();
		double[] ref = new double[] { 10, 5 };
		conf.setReferenceAxesPositionXY(ref);
		scanSplitter.setConfig(conf);

		double[] test = scanSplitter.getCalibratedPositionOfReference();
		Assert.assertArrayEquals("Calibrated position has not been updated", ref, test, 1e-6);

	}

	@Test
	public void testFrameCalibrationHasFixedEnergy() throws Exception {

		setMovingBeamConfig();
		MovingBeamCalibrationScanSplitter scanSplitter = initialiseFrameSplitterAndTestFrame(config);
		MovingBeamCalibrationConfig conf = scanSplitter.getConfig();
		conf.setReferenceAxesPositionXY(new double[2]);

		SimpleCalibrationParameterModel lmodel = conf.getModel();
		lmodel.setIsPointCalibration(true);
		lmodel.setFloatEnergy(false);
		conf.setModel(lmodel);

		scanSplitter.setConfig(conf);
		SliceFromSeriesMetadata ssm = testFrame.getFirstMetadata(SliceFromSeriesMetadata.class);
		DiffractionImageData dimage = scanSplitter.calibrateFrameFromSlice(testFrame);

		DiffractionCrystalEnvironment expectedMeta = NexusDiffractionCalibrationReader
				.getDiffractionMetadataFromNexus(pathToReferenceCalibration, ssm.getParent())
				.getDiffractionCrystalEnvironment();
		DiffractionCrystalEnvironment actualMeta = dimage.getMetaData().getDiffractionCrystalEnvironment();
		Assert.assertEquals("Unexpected beam energy change!", expectedMeta.getEnergy(), actualMeta.getEnergy(), 1e-6);
	}

	/**
	 * Integration test to check exported frame compatibility with the metadata
	 * created by extrapolation; n.b. the
	 * {@link MovingBeamTestUtils#exportCalibratedFrame(IDataset, IDataset, IDiffractionMetadata, IPowderCalibrationInfo, String, String, String)}
	 * method is used here as
	 * {@link NexusCalibrationExportUtils#saveToMovingBeamCompatibleNexusFile(IDataset, IDataset, IDiffractionMetadata, IPowderCalibrationInfo, String, String, String)}
	 * is not usable in testing, but the implementations should be the same.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExportOfCalibratedScanFramesForExtrapolation() throws Exception {

		setMovingBeamConfig();

		MovingBeamCalibrationScanSplitter scanSplitter = initialiseFrameSplitterAndTestFrame(config);
		MovingBeamCalibrationConfig conf = scanSplitter.getConfig();

		conf.setReferenceAxesPositionXY(new double[2]);
		scanSplitter.setConfig(conf);

		conf.getModel().setIsPointCalibration(true);
		conf.getModel().setFloatEnergy(false);

		SliceFromSeriesMetadata ssm = testFrame.getFirstMetadata(SliceFromSeriesMetadata.class);
		DiffractionImageData dimage = scanSplitter.calibrateFrameFromSlice(testFrame);

		File cPath = new File(
				scanSplitter.getFileNameForFrame(tmp.getAbsolutePath(), ssm, scan.getName().replace(".nxs", "")));

		double[] frameMotorPos = scanSplitter.getCalibratedPosition(ssm);

		IDiffractionMetadata centralMetadata = DiffractionMetadataUtils
				.getOffsetMetadata(getDefaultPilatusMetadata(), new double[] { xpos[1], ypos[1], 0 });
		IDiffractionMetadata overrideMeta = DiffractionMetadataUtils.getOffsetMetadata(getDefaultPilatusMetadata(),
				new double[] { xpos[0], ypos[0], 0 });

		MovingBeamTestUtils.exportCalibratedFrame(testFrame, DatasetFactory.createFromObject(frameMotorPos),
				overrideMeta, dimage.getCalibrationInfo(), cPath.getAbsolutePath(), config.getXReferenceAxisPath(),
				config.getYReferenceAxisPath()); // pretend the frame at
		// (xpos[0],ypos[0]) has
		// been calibrated;

		MovingBeamOffsetMetadataCache cacheModel = new MovingBeamOffsetMetadataCache(testFrame,
				MovingBeamTestUtils.getConfiguredOffsetCacheModel(cPath), scan.getAbsolutePath());

		SliceND testSlice = new SliceND(scanShape, new int[] { 1, 0, 0 },
				new int[] { 2, scanShape[0], scanShape[1] }, new int[] { 1, 1, 1 }); // check
		// the
		// extrapolated
		// metadata
		// for
		// frame
		// (xpos[1],
		// ypos[1])

		SliceInformation slinfo = new SliceInformation(testSlice, testSlice, sampling, new int[] { 1, 2 }, 3, 0);

		SliceFromSeriesMetadata ssmtest = new SliceFromSeriesMetadata(ssm.getSourceInfo(), slinfo);
		IDiffractionMetadata dmetaTest = cacheModel.getDiffractionMetadata(ssmtest);

		DetectorProperties dpAct = dmetaTest.getDetector2DProperties();
		DetectorProperties dpRef = centralMetadata.getDetector2DProperties();
		Assert.assertTrue("Offset Metadata not as expected", dpAct.equals(dpRef));

		ReferencePosition2DMetadata pMeta = cacheModel.getPositionMeta(ssmtest);
		double[] position = pMeta.getReferencePosition().getData();
		Assert.assertArrayEquals("Source offsets not assigned correctly",
				new double[] { xpos[1] - xpos[0], ypos[1] - ypos[0], 0 }, position, 1e-6);
	}

	@Test
	public void testScanIO() throws Exception {

		setMovingBeamConfig();

		File scan = genererateScanFile();
		IDataHolder dh = MovingBeamTestUtils.loadScan(scan);

		DoubleDataset xReadback = DatasetUtils.cast(DoubleDataset.class,
				DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset(config.getXReferenceAxisPath())));
		DoubleDataset yReadback = DatasetUtils.cast(DoubleDataset.class,
				DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset(config.getYReferenceAxisPath())));
		IntegerDataset frameKeys = DatasetUtils.cast(IntegerDataset.class, DatasetUtils.sliceAndConvertLazyDataset(
				dh.getLazyDataset(buildFrameKeysPath(scan.getAbsolutePath(), false, true))));

		Assert.assertArrayEquals(xReadback.getData(), new double[] { -1., 0, 1 }, 1e-6);
		Assert.assertArrayEquals(yReadback.getData(), new double[] { -1, 0, 1 }, 1e-6);
		Assert.assertArrayEquals(frameKeys.getData(), new int[] { 0, 2, 1 });

	}

	/**
	 * Test using the scanSplitter to isolate the frames and source position
	 * data; the compatibility of the format of the saved data is then tested
	 * for compatibility with loading using
	 * {@link NearestDiffractionMetadataImportOperation}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMovingBeamMetadataNearestCacheCompatibility() throws Exception {
		setMovingBeamConfig();

		MovingBeamCalibrationScanSplitter scanSplitter = initialiseFrameSplitterAndTestFrame(config);
		MovingBeamCalibrationConfig conf = scanSplitter.getConfig();

		conf.setReferenceAxesPositionXY(new double[2]);
		conf.setPadWithZeros(3); // set to match the hardcoded regex of
									// REGEXPATTERN
		scanSplitter.setConfig(conf);

		SimpleCalibrationParameterModel lmodel = conf.getModel();
		lmodel.setIsPointCalibration(true);
		lmodel.setFloatEnergy(false);

		DiffractionImageData dimage = scanSplitter.calibrateFrameFromSlice(testFrame);
		SliceFromSeriesMetadata ssm = testFrame.getFirstMetadata(SliceFromSeriesMetadata.class);

		IDataHolder scanHolder = MovingBeamTestUtils.loadScan(scan);
		Dataset images = DatasetUtils
				.sliceAndConvertLazyDataset(scanHolder.getLazyDataset(MovingBeamConstants.DATASETPATH));

		IndexIterator sit = images.getSliceIterator(
				new SliceND(scanShape, new int[] { 0, 0, 0 }, scanShape, new int[] { 1, scanShape[1], scanShape[2] }));
		sit.reset();

		SliceND currentSlice;
		SliceInformation slinfo;
		Dataset frame = null;
		File cPath = null;
		IDiffractionMetadata frameMeta = null;

		while (sit.hasNext()) {
			int[] pos = sit.getPos();

			currentSlice = new SliceND(images.getShape(), pos, new int[] { pos[0] + 1, scanShape[1], scanShape[2] },
					new int[] { 1, 1, 1 });
			slinfo = new SliceInformation(currentSlice, currentSlice, sampling, new int[] { 1, 2 }, scanShape[0],
					pos[0]);
			SliceFromSeriesMetadata ssmSlice = new SliceFromSeriesMetadata(ssm.getSourceInfo(), slinfo);
			frame = images.getSlice(currentSlice);
			frame.addMetadata(ssmSlice);

			cPath = new File(scanSplitter.getFileNameForFrame(tmp.getAbsolutePath(), ssmSlice,
					scan.getName().replace(".nxs", ""))); // this is the path to
															// the file in the
															// calibration
															// folder

			double[] frameMotorPos = scanSplitter.getCalibratedPosition(ssmSlice);

			frameMeta = DiffractionMetadataUtils.getOffsetMetadata(getDefaultPilatusMetadata(),
					Arrays.copyOf(frameMotorPos, 3));

			MovingBeamTestUtils.exportCalibratedFrame(frame, DatasetFactory.createFromObject(frameMotorPos), frameMeta,
					dimage.getCalibrationInfo(), cPath.getAbsolutePath(), config.getXReferenceAxisPath(),
					config.getYReferenceAxisPath());

		}

		NearestDiffractionMetadataImportModel iModel = getConfiguredNearestCacheModel(cPath.getParentFile(), scan);
		NearestMetadataCache testCache = new NearestMetadataCache(frame, iModel, scan.getAbsolutePath());

		ReferencePosition2DMetadata pos2D = testCache
				.getPositionMeta(frame.getFirstMetadata(SliceFromSeriesMetadata.class));

		Assert.assertEquals("Error in assigned frame index",
				testCache.getFrameIDForPosition(frame.getFirstMetadata(SliceFromSeriesMetadata.class)), scanFrames[2]);

		Assert.assertArrayEquals("Error in position returned from cache!", new double[] { xpos[2], ypos[2] },
				pos2D.getReferencePosition().getData(), 1e-6);
		IDiffractionMetadata rmeta = testCache
				.getDiffractionMetadata(frame.getFirstMetadata(SliceFromSeriesMetadata.class));

		Assert.assertTrue("Incorrect metadata retrieved from the cache",
				rmeta.getDetector2DProperties().equals(frameMeta.getDetector2DProperties()));

	}

	/**
	 * Write a reference frame for using as a seed calibration geometry.
	 * <p>
	 * run before initialising a frame splitter
	 * 
	 * @param filePath
	 */
	private void writeReferenceMetadata(File filePath) throws Exception {
		PowderCalibrationInfoImpl tinfo = new PowderCalibrationInfoImpl(STANDARD, MovingBeamConstants.DATASETPATH,
				"diffraction");
		DoubleDataset tmpCal = DatasetFactory.createRange(5);
		tinfo.setPostCalibrationInformation("Temporary description", tmpCal.clone().idivide(2), tmpCal, 0.); // mock
																												// some
																												// calibration
																												// result
																												// data
		MovingBeamTestUtils.saveToNexusFile(img, dmeta, tinfo, filePath.getAbsolutePath());
	}

	/**
	 * Generate a scan file and return a test slice [0,:,:] from the scan.
	 * 
	 * @return A slice representing a numerically generated frame with the
	 * correctly configured SliceFromSeriesMetadata.
	 */
	private Dataset generateScanAndLoadTestSlice() throws DatasetException, Exception {

		int frameID = 0;
		this.scan = genererateScanFile();
		IDataHolder dh = MovingBeamTestUtils.loadScan(scan);

		DoubleDataset images = DatasetUtils.cast(DoubleDataset.class,
				DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset(config.getDatasetPath())));

		int[] shape = images.getShape();

		SliceND currentSlice = new SliceND(images.getShape(), new int[] { frameID, 0, 0 },
				new int[] { frameID + 1, shape[1], shape[2] }, new int[] { 1, 1, 1 });
		Dataset frame = images.getSlice(currentSlice);
		SliceND sampling = new SliceND(images.getShape(), new int[] { 0, 0, 0 },
				new int[] { shape[0], shape[1], shape[2] }, new int[] { 1, 1, 1 });

		SourceInformation sinfo = null;
		SliceInformation slinfo;
		OriginMetadata sOrigin = frame.getFirstMetadata(OriginMetadata.class);
		sinfo = new SourceInformation(scan.getAbsolutePath(), sOrigin.getDatasetName(), sOrigin.getParent());
		slinfo = new SliceInformation(currentSlice, currentSlice, sampling, new int[] { 1, 2 }, shape[0], 1);

		frame.addMetadata(new SliceFromSeriesMetadata(sinfo, slinfo));
		return frame;

	}

	/**
	 * Initialise the scanSplitter
	 * <p>
	 * In calling this, a a mock scan, a mock .json calibration configuration
	 * and a configured {@link SplitAndCalibrateFramesModel} will be created. It
	 * requires that a calibration example file exists in the location specified
	 * in conf.
	 * 
	 * @param conf - configuration containing parameters relevant to the moving
	 * beam calibration process; if null, a default config will be loaded.
	 * @return a correctly configured scan splitter for use in tests.
	 * @throws Exception
	 */
	public MovingBeamCalibrationScanSplitter initialiseFrameSplitterAndTestFrame(MovingBeamCalibrationConfig conf)
			throws Exception {

		testFrame = generateScanAndLoadTestSlice();
		SplitAndCalibrateFramesModel model = getConfiguredSplitAndCalibrateFramesModel();
		if (conf == null)
			conf = SplitAndCalibrateFramesOperation.parseJSONConfig(model.getPathToCalibrationDescriptionConfig(),
					marshaller);

		return new MovingBeamCalibrationScanSplitter(testFrame, model, conf);

	}

	/**
	 * get a correctly configured NearestDiffractionMetadata import model for
	 * testing.
	 * 
	 * @param cFolder - Folder where the calibrations are stored
	 * @param scan - path to the scan file
	 * @return
	 */
	private NearestDiffractionMetadataImportModel getConfiguredNearestCacheModel(File cFolder, File scan) {
		NearestDiffractionMetadataImportModel cacheModel = new NearestDiffractionMetadataImportModel();
		cacheModel.setCalibsFolder(cFolder.getAbsolutePath());
		cacheModel.setDetectorDataset(MovingBeamConstants.DATASETPATH);
		cacheModel.setPositionZeroDataset(MovingBeamConstants.XAXISREFERENCE);
		cacheModel.setPositionOneDataset(MovingBeamConstants.YAXISREFERENCE);
		cacheModel.setRegex(REGEXPATTERN);
		cacheModel.setFilePath(scan.getAbsolutePath());
		return cacheModel;

	}

	/**
	 * Write an example movingBeamCalibration configuration to file in the
	 * temporary directory
	 * 
	 * @param tmp
	 * @return
	 * @throws Exception
	 */
	private File writeMovingBeamConfig(File tmp) throws Exception {
		String json = marshaller.marshal(config);
		File tmpFile = File.createTempFile("config", ".tmp", tmp);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile.getAbsolutePath()))) {
			writer.write(json);
		} catch (IOException e) {
			Assert.fail("Error writing json to file");
		}
		return tmpFile;
	}

	private SplitAndCalibrateFramesModel getConfiguredSplitAndCalibrateFramesModel() throws Exception {

		File configFile = writeMovingBeamConfig(tmp);

		SplitAndCalibrateFramesModel lmodel = new SplitAndCalibrateFramesModel();
		lmodel.setOverrideRoll(true);
		lmodel.setNewRoll(0.);
		lmodel.setPathToCalibrationDescriptionConfig(configFile.getAbsolutePath());
		lmodel.setPathToOutputFolder(tmp.getAbsolutePath());

		return lmodel;

	}

	/**
	 * get the correct path for the location of the scan's unique keys.
	 * Depending on when a scan was written, this path can have two formats. To
	 * use the newest format useNewFormat must be passed in as {@value true}.
	 * 
	 * @param filePath
	 * @param isolateDatasetName
	 * @return
	 */
	private String buildFrameKeysPath(String filePath, boolean isolateDatasetName, boolean useNewFormat) {
		String path = AbstractMovingBeamMetadataCache.buildFrameKeysPath(filePath, config.getScanKeyPath(),
				useNewFormat);
		return (isolateDatasetName) ? path.substring(path.lastIndexOf(Node.SEPARATOR) + 1) : path;
	}

	/**
	 * initialise the configuration for the moving beam auto-calibration. The
	 * {@link #pathToReferenceCalibration, #cmod, #referenceFramePos, and
	 * #ZeroPad} field variables must be set ahead of calling this method, or
	 * incorrect defaults will be used.
	 * 
	 */
	private void setMovingBeamConfig() {
		config = new MovingBeamCalibrationConfig();
		config.setDatasetPath(MovingBeamConstants.DATASETPATH);
		config.setInitialCalibration(pathToReferenceCalibration); // the
																	// calibration
																	// needs to
																	// exist
																	// beforehand
																	// TODO
																	// write
																	// this
		config.setFloatEnergy(false);
		config.setModel(cmod);
		config.setReferenceAxesPositionXY(
				new double[] { position.getElementDoubleAbs(0), position.getElementDoubleAbs(1) });
		config.setXReferenceAxisPath(MovingBeamConstants.XAXISREFERENCE);
		config.setYReferenceAxisPath(MovingBeamConstants.YAXISREFERENCE);
		config.setStandard(STANDARD);
		config.setScanKeyPath("/entry/diamond_scan/keys/");
		config.setReferenceAxesPositionXY(referenceFramePos);
		config.setPadWithZeros(ZeroPad);

	}

	/**
	 * generate a mock scan file for a moving beam dataset
	 * 
	 * @return
	 * @throws Exception
	 */
	private File genererateScanFile() throws Exception {

		File scan = File.createTempFile("k11-MockData", ".nxs", tmp);

		IDiffractionMetadata ometa = getDefaultPilatusMetadata();

		Dataset xpositions = DatasetFactory.createFromObject(xpos, new int[] {3, 1});
		Dataset ypositions = DatasetFactory.createFromObject(ypos, new int[] {3, 1});
		Dataset keys = DatasetFactory.createFromObject(scanFrames, new int[] {3, 1});

		List<IDataset> imageData = IntStream.range(0, 3).mapToObj(i -> {
			IDiffractionMetadata doff = DiffractionMetadataUtils.getOffsetMetadata(ometa,
					new double[] { xpos[i], ypos[i], 0 });
			return makeImage(doff);
		}).collect(Collectors.toList());

		Dataset images = DatasetFactory.zeros(scanShape);

		IndexIterator sit = images.getSliceIterator(
				new SliceND(scanShape, new int[] { 0, 0, 0 }, scanShape, new int[] { 1, scanShape[1], scanShape[2] }));

		sit.reset();
		while (sit.hasNext()) {
			int[] pos = sit.getPos();

			// make image()
			images.setSlice(imageData.get(pos[0]), new SliceND(images.getShape(), pos,
					new int[] { pos[0] + 1, scanShape[1], scanShape[2] }, new int[] { 1, 1, 1 }));

		}

		try (NexusFileHDF5 nexusFile = new NexusFileHDF5(scan.getAbsolutePath(), false)) {
			GroupNode gn = NexusTreeUtils.createNXGroup(NexusConstants.ENTRY);

			GroupNode gndiff = NexusTreeUtils.createNXGroup(NexusConstants.DATA);
			gndiff.addAttribute(TreeFactory.createAttribute(NexusConstants.DATA_AXES,
					new String[] { "kb_y", "kb_x", NexusConstants.DATA_AXESEMPTY, NexusConstants.DATA_AXESEMPTY }));

			gndiff.addDataNode("kb_x", NexusTreeUtils.createDataNode("", xpositions, null));
			gndiff.addDataNode("kb_y", NexusTreeUtils.createDataNode("", ypositions, null));
			gndiff.addDataNode("data",
					NexusTreeUtils.createDataNode("", DatasetFactory.createFromList(imageData), null));

			gn.addNode("diffraction", gndiff);

			GroupNode kgroup = NexusTreeUtils.createNXGroup(NexusConstants.COLLECTION);

			kgroup.addDataNode(buildFrameKeysPath(scan.getAbsolutePath(), true, true),
					NexusTreeUtils.createDataNode("", keys, null));

			GroupNode scanGroup = NexusTreeUtils.createNXGroup(NexusConstants.COLLECTION);
			scanGroup.addGroupNode("keys", kgroup);
			gn.addGroupNode("diamond_scan", scanGroup);

			nexusFile.openToWrite(true);
			nexusFile.addNode("/entry", gn);

		} catch (NexusException e) {
			throw e;
		}
		return scan;

	}

	/**
	 * perform a less stringent but more reliable comparison between two
	 * SimpleCalibrationParameterModel classes, just based on their setting for
	 * pointOptions.
	 * 
	 * @param comp
	 * @param ref
	 * @return
	 */
	private boolean compareSimpleCalibrationParameterModels(SimpleCalibrationParameterModel comp,
			SimpleCalibrationParameterModel ref) {

		if (comp == ref)
			return true;
		if (ref.isAutomaticCalibration() != comp.isAutomaticCalibration())
			return false;
		if (ref.isPointCalibration() != comp.isPointCalibration())
			return false;
		if (ref.getMaxSearchSize() != comp.getMaxSearchSize())
			return false;
		if (ref.getMinimumSpacing() != comp.getMinimumSpacing())
			return false;
		if (ref.getnIgnoreCentre() != comp.getnIgnoreCentre())
			return false;
		if (ref.getnPointsPerRing() != comp.getnPointsPerRing())
			return false;
		if (ref.getNumberOfRings() != comp.getNumberOfRings())
			return false;
		if (!ref.getRingSet().equals(comp.getRingSet()))
			return false;
		if (ref.isFixDetectorRoll() != comp.isFixDetectorRoll())
			return false;
		if (ref.isFloatEnergy() != comp.isFloatEnergy())
			return false;
		if (ref.isFloatTilt() != comp.isFloatTilt())
			return false;
		if (ref.isFloatDistance() != comp.isFloatDistance())
			return false;

		return true;

	}

}
