package uk.ac.diamond.scisoft.xpdf.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Maths;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.TestCase;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamData;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentCylinder;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentForm;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentGeometry;
import uk.ac.diamond.scisoft.xpdf.XPDFSubstance;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;

public class XPDFCylinderTest extends TestCase {

	final static private String testDataDir = "/home/rkl37156/ceria_dean_data/testData/";
	
	public void testGetUpstreamPathLength() {
//		fail("Temporary fail"); //TODO: remove
		XPDFComponentCylinder cap = new XPDFComponentCylinder();
		cap.setDistances(0.15, 0.16);
		cap.setStreamality(true, true);
		
		final int xSize = 64, ySize = 64;
		Dataset pathLengthExp = DatasetFactory.zeros(DoubleDataset.class, xSize, ySize); 
		Dataset pathLength = DatasetFactory.zeros(pathLengthExp);
		Dataset r = DatasetFactory.zeros(pathLengthExp);
		Dataset xi = DatasetFactory.zeros(pathLengthExp);
		
		// Read the data from file
		String fileDirectory = testDataDir;
		Dataset[] datasets = {pathLengthExp, r, xi};
		String[] files = {"incoming", "sample_r", "sample_xi"};
		
		for (int k = 0; k<3; k++) {
		
			Path filePath = new File(fileDirectory+files[k]+".txt").toPath();
			List<String> filelines = new ArrayList<String>();
			try {
				filelines = Files.readAllLines(filePath);
			} catch (Exception e) {
				fail("Error reading " + filePath.toString());
			}
			for (int i = 0; i<ySize; i++) {
				String[] splitline = filelines.get(i).split(" ");
				for (int j = 0; j< xSize; j++) {
					double assignee = Double.parseDouble(splitline[j]);
					datasets[k].set(assignee, i, j);
				}
			}
		}
		Dataset x = Maths.multiply(r, Maths.sin(xi));
		Dataset z = Maths.multiply(r, Maths.cos(xi));
		Dataset y = DatasetFactory.zeros(x);
		pathLength = cap.getUpstreamPathLength(x, y, z);
		
		double rmsError = Math.sqrt((Double) Maths.square(Maths.subtract(pathLength, pathLengthExp)).mean());
		assertTrue("Error in upstream path length too large", rmsError < 1e-6);
		//fail("Not yet implemented");
	}

	public void testGetDownstreamPathLength() {
//		fail("Temporary fail"); //TODO: remove
		XPDFComponentCylinder cap = new XPDFComponentCylinder();
		cap.setDistances(0.15, 0.16);
		cap.setStreamality(true, true);
		
		final int xSize = 64, ySize = 64;
		Dataset pathLengthExp = DatasetFactory.zeros(DoubleDataset.class, xSize, ySize); 
		Dataset pathLength = DatasetFactory.zeros(pathLengthExp);
		Dataset r = DatasetFactory.zeros(DoubleDataset.class, xSize, ySize);
		Dataset xi = DatasetFactory.zeros(r);
		
		String[] angles = {"0.0", "15.0", "28.5", "29.5", "47.0"};
		int nAngles = angles.length;
		
		for (String angle : angles) {
			
			// Read the data from file
			String fileDirectory = testDataDir;
			Dataset[] datasets = {pathLengthExp, r, xi};
			String[] files = {"outgoing"+angle, "sample_r", "sample_xi"};

			for (int k = 0; k<3; k++) {

				Path filePath = new File(fileDirectory+files[k]+".txt").toPath();
				List<String> filelines = new ArrayList<String>();
				try {
					filelines = Files.readAllLines(filePath);
				} catch (Exception e) {
					fail("Error reading " + filePath.toString());
				}
				for (int i = 0; i<ySize; i++) {
					String[] splitline = filelines.get(i).split(" ");
					for (int j = 0; j< xSize; j++) {
						double assignee = Double.parseDouble(splitline[j]);
						datasets[k].set(assignee, i, j);
					}
				}
			}

			Dataset x = Maths.multiply(r, Maths.sin(xi));
			Dataset z = Maths.multiply(r, Maths.cos(xi));
			Dataset y = DatasetFactory.zeros(x);
			pathLength= cap.getDownstreamPathLength(x, y, z, 0.0, Math.toRadians(Double.parseDouble(angle)));

			// TODO: remove temporary write command
			try (Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(testDataDir + "outgoing"+angle+".java.txt"), "utf-8"))) {
				for (int i = 0; i<ySize; i++) {
					for (int j = 0; j < xSize; j++) {
						fileWriter.write(((Double) pathLength.getDouble(i, j)).toString()+" ");
					}
					fileWriter.write(13); // Carriage return
				}
			} catch(Exception e) {
				;
			}
			
			double rmsError = Math.sqrt((Double) Maths.square(Maths.subtract(pathLength, pathLengthExp)).mean());
			assertTrue("Error in downstream path length at " + angle + " too large: " + rmsError, rmsError < 1e-6);
		}		
	}
	
	public void testAbsorptionCorrections() throws DatasetException {

		double rmsError = absorptionCorrectionCommon(true, true);
		double rmsErrorTarget = 1e-3;
		assertTrue("Error in sample-sample absorption correction too large: " + rmsError, rmsError < rmsErrorTarget);
	}

	public void testAbsorptionCorrectionsSC() throws DatasetException {

		double rmsError = absorptionCorrectionCommon(true, false);
		double rmsErrorTarget = 1e-3;
		assertTrue("Error in sample-capillary absorption correction too large: " + rmsError, rmsError < rmsErrorTarget);		
	}

	public void testAbsorptionCorrectionsCS() throws DatasetException {

		double rmsError = absorptionCorrectionCommon(false, true);
		double rmsErrorTarget = 1e-3;
		assertTrue("Error in capillary-sample absorption correction too large: " + rmsError, rmsError < rmsErrorTarget);
	}
	
	public void testAbsorptionCorrectionsCC() throws DatasetException {

		double rmsError = absorptionCorrectionCommon(false, false);
		double rmsErrorTarget = 1e-3;
		assertTrue("Error in capillary-capillary absorption correction too large: " + rmsError, rmsError < rmsErrorTarget);
	}
	
	public double absorptionCorrectionCommon(boolean isSampleScatterer, boolean isSampleAttenuator) throws DatasetException {
		// Make the two components as ComponentGeometries
		XPDFComponentGeometry sample = new XPDFComponentCylinder();
		XPDFComponentGeometry capillary = new XPDFComponentCylinder();
		
		sample.setDistances(0.0, 0.15);
		sample.setStreamality(true, true);
		
		capillary.setDistances(0.15, 0.16);
		capillary.setStreamality(true, true);

		XPDFComponentGeometry scatterer = (isSampleScatterer) ? sample : capillary;
		XPDFComponentGeometry attenuator = (isSampleAttenuator) ? sample : capillary;
		
		// These will be calculated by the Composition class
		double quartzAttenuationCoefficient = 0.0529783195963;
		double ceriaAttenuationCoefficient = 1.86349597825;
		double attenuationCoefficient = (isSampleAttenuator) ? ceriaAttenuationCoefficient : quartzAttenuationCoefficient;
		
		XPDFBeamData beamData = new XPDFBeamData();
		beamData.setBeamEnergy(76.6);
		beamData.setBeamHeight(0.07);
		beamData.setBeamWidth(0.07);
		
		// Read the target data, and also the angles (in radians) to run
		String dataPath = testDataDir;
		IDataHolder dh = null;
		String scattererName, attenuatorName;
		scattererName = (isSampleScatterer) ? "sample" : "container";
		attenuatorName = (isSampleAttenuator) ? "sample" : "container";
		String filename = dataPath+scattererName + "_" + attenuatorName + "_abs.xy";
		try {
			dh = LoaderFactory.getData(filename);
		} catch (Exception e) {
			System.err.println("Error reading file: " + filename);
			fail("Error reading file: " + filename);
		}
		Dataset delta1D = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("Column_1"));
		Dataset delta = DatasetFactory.zeros(DoubleDataset.class, delta1D.getSize(), 1);
		for (int i = 0; i<delta1D.getSize(); i++)
			delta.set(delta1D.getDouble(i), i, 0);
		Dataset gamma = DatasetFactory.zeros(delta);
		Dataset expected = 	DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("Column_2"));
		expected.setShape(expected.getSize(), 1);
		
		Dataset calculated = scatterer.calculateAbsorptionCorrections(gamma, delta, attenuator, attenuationCoefficient, beamData, true, true);
		
		//calculated.squeeze();
		
		Dataset difference = Maths.subtract(calculated, expected);
		return Math.sqrt((Double) Maths.square(difference).mean());
		
	}
	
	///////////////////////////////////////////////////////////////////////////
	//
	// Tests using data from the autumn 2015 standards experiments
	//
	///////////////////////////////////////////////////////////////////////////
	@Test
	@Ignore("The reason for the fluorescence test being so far from correct needs more analysis")
	public void testFluorescence() throws DatasetException {
		
		XPDFSubstance ceria = new XPDFSubstance("ceria", "CeO2", 7.65, 0.6);
		XPDFSubstance bto = new XPDFSubstance("BTO", "BaTiO3", 6.05, 0.6);
		XPDFSubstance nickel = new XPDFSubstance("nickel", "Ni", 8.95, 0.6);
		XPDFSubstance quartz = new XPDFSubstance("quartz", "SiO2", 2.65, 1.0);
		
		double[] ceriumLines = new double[] {34.7196, 34.2788, 39.2576, 4.8401, 5.2629};
		double[] bariumLines = new double[] {32.1936, 31.817, 36.3784, 4.4663, 4.8275};
		double[] nickelLines = new double[] {7.4781};
		
		// create a map between fluorescence energies and target result file names
		Map<Double, String> ceriaEnergyFileMap = new HashMap<Double, String>();
		ceriaEnergyFileMap.put(34.720, "34720");
		ceriaEnergyFileMap.put(34.279, "34279");
		ceriaEnergyFileMap.put(39.258, "39258");
		ceriaEnergyFileMap.put(40.236, "40236");
		ceriaEnergyFileMap.put(4.840, "4840");
		ceriaEnergyFileMap.put(5.263, "5263");
		
		XPDFComponentGeometry cap1mm= new XPDFComponentCylinder();
		cap1mm.setDistances(0.5, 0.51);
		cap1mm.setStreamality(true, true);
		
		XPDFComponentGeometry powder1mm = new XPDFComponentCylinder();
		powder1mm.setDistances(0.0, 0.5);
		powder1mm.setStreamality(true, true);
		
		XPDFComponentForm capForm = new XPDFComponentForm();
		capForm.setGeom(cap1mm);
		capForm.setMatName("SiO2");
		XPDFTargetComponent quartzCap = new XPDFTargetComponent();
		quartzCap.setForm(capForm);
		quartzCap.setSample(false);
		quartzCap.setName("quartz capillary");
		
		XPDFBeamData beamData = new XPDFBeamData();
		beamData.setBeamEnergy(76.6);
		beamData.setBeamHeight(0.07);
		beamData.setBeamWidth(0.07);
		
		List<XPDFComponentGeometry> attenuators = new ArrayList<XPDFComponentGeometry>();
		attenuators.add(powder1mm);
		attenuators.add(cap1mm);
		
		List<Dataset> lineFluorescence = new ArrayList<Dataset>();
//		for (int iLine = 0; iLine < ceriumLines.length; iLine++) {
		for (Map.Entry<Double, String> energyEntry : ceriaEnergyFileMap.entrySet()) {
			double lineEnergy = energyEntry.getKey();
			
			List<Double> muIn = new ArrayList<Double>();
			muIn.add(ceria.getAttenuationCoefficient(beamData.getBeamEnergy()));
			muIn.add(quartz.getAttenuationCoefficient(beamData.getBeamEnergy()));

			List<Double> muOut = new ArrayList<Double>();
			muOut.add(ceria.getAttenuationCoefficient(lineEnergy));
			muOut.add(quartz.getAttenuationCoefficient(lineEnergy));
		
			// Read the target data, and also the angles (in radians) to run
			String dataPath = testDataDir;
			IDataHolder dh = null;
			String fluorName = "ceria";
			String fluorNumber = energyEntry.getValue();
			String fullFileName = dataPath+fluorName+ ".fluor." + fluorNumber + ".xy";
			try {
				dh = LoaderFactory.getData(fullFileName);
			} catch (Exception e) {
				System.err.println("Error reading file: " + fullFileName);
				fail("Error reading file: " + fullFileName);
			}
			Dataset delta1D = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("Column_1"));
			Dataset delta = DatasetFactory.zeros(DoubleDataset.class, delta1D.getSize(), 1);
			for (int i = 0; i<delta1D.getSize(); i++)
				delta.set(Math.toRadians(delta1D.getDouble(i)), i, 0);
			Dataset gamma = DatasetFactory.zeros(delta);

			// convert to radians, and rotate the detector
			double rotationAngle = Math.toRadians(120.0);
			// gamma is known to be zero, so just overwrite it
			gamma = Maths.multiply(Math.sin(rotationAngle), delta);
			delta = Maths.multiply(Math.cos(rotationAngle), delta);

			Dataset expected = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("Column_2"));

			Dataset ceriaFluor1 = powder1mm.calculateFluorescence(gamma, delta, attenuators, muIn, muOut, beamData, true, true);

			Dataset error = Maths.divide(ceriaFluor1.squeeze(), expected);
			error.isubtract(1);
			error = Maths.square(error);
			double sumError = (double) error.mean();
			sumError = Math.sqrt(sumError);

			assertTrue("Too large a difference, " + sumError + " between expected and calculated fluorescence for " + fluorName + " at " + lineEnergy + "keV.",
					sumError < 8e-2);
			lineFluorescence.add(ceriaFluor1);
		}
		
		
		
	}
	
	public void testCeriaAbsorption() throws DatasetException {

		XPDFSubstance ceria = new XPDFSubstance("ceria", "CeO2", 7.65, 0.6);

		XPDFComponentGeometry cap1mm= new XPDFComponentCylinder();
		cap1mm.setDistances(0.5, 0.51);
		cap1mm.setStreamality(true, true);
		
		XPDFComponentGeometry powder1mm = new XPDFComponentCylinder();
		powder1mm.setDistances(0.0, 0.5);
		powder1mm.setStreamality(true, true);
		
		XPDFComponentForm capForm = new XPDFComponentForm();
		capForm.setGeom(cap1mm);
		capForm.setMatName("SiO2");
		XPDFTargetComponent quartzCap = new XPDFTargetComponent();
		quartzCap.setForm(capForm);
		quartzCap.setSample(false);
		quartzCap.setName("quartz capillary");
		
		XPDFBeamData beamData = new XPDFBeamData();
		beamData.setBeamEnergy(76.6);
		beamData.setBeamHeight(0.07);
		beamData.setBeamWidth(0.07);
		
		List<XPDFComponentGeometry> attenuators = new ArrayList<XPDFComponentGeometry>();
		attenuators.add(powder1mm);
		attenuators.add(cap1mm);

		String[] scattererNames = {"NIST_Ceria", "QGCT_1mm"};
		String[] absorberNames = scattererNames;
		
		double[] mus = {ceria.getAttenuationCoefficient(beamData.getBeamEnergy()),
				capForm.getAttenuationCoefficient(beamData.getBeamEnergy())};
		
		String dataPath = "/home/rkl37156/ceria_dean_data/standards2015/absorption/";
		String fileName = "NIST_Ceria";
		IDataHolder dh = null;
		
		for (int iScatter = 0; iScatter < 2; iScatter++) {
			for (int jAbsorber = 0; jAbsorber < 2; jAbsorber++) {
				String fn = dataPath+fileName+"_"+scattererNames[iScatter] + "_in_" + absorberNames[jAbsorber]+ ".xy";
				try {
					dh = LoaderFactory.getData(fn);
				} catch (Exception e) {
					fail("File not found: " + fn);
				}

				Dataset delta1D = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("Column_1"));
				Dataset delta = DatasetFactory.zeros(DoubleDataset.class, delta1D.getSize(), 1);
				for (int i = 0; i<delta1D.getSize(); i++)
					delta.set(delta1D.getDouble(i), i, 0);
				Dataset gamma = DatasetFactory.zeros(delta);

				Dataset expected = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("Column_2"));
				
				// Calculate a result
				Dataset result = attenuators.get(iScatter).calculateAbsorptionCorrections(gamma, delta, attenuators.get(jAbsorber), mus[jAbsorber], beamData, true, true);

				Dataset difference = Maths.subtract(result, expected);
				Dataset squareDifference = Maths.square(difference);
				double rmsDifference = Math.sqrt((double) squareDifference.mean());
				double maxDifference = 2e-2;
				assertTrue("Absorption of " + scattererNames[iScatter] + " in " + absorberNames[jAbsorber] + " deviates too far from target values: "+ rmsDifference, rmsDifference < maxDifference);
			}
		}
	}

}
