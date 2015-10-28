package uk.ac.diamond.scisoft.xpdf.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamData;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentCylinder;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentForm;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentGeometry;
import uk.ac.diamond.scisoft.xpdf.XPDFSubstance;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;
import junit.framework.TestCase;

public class XPDFCylinderTest extends TestCase {

	public void testGetUpstreamPathLength() {
//		fail("Temporary fail"); //TODO: remove
		XPDFComponentCylinder cap = new XPDFComponentCylinder();
		cap.setDistances(0.15, 0.16);
		cap.setStreamality(true, true);
		
		final int xSize = 64, ySize = 64;
		Dataset pathLengthExp = new DoubleDataset(xSize, ySize); 
		Dataset pathLength = new DoubleDataset(pathLengthExp);
		Dataset r = new DoubleDataset(pathLengthExp);
		Dataset xi = new DoubleDataset(pathLengthExp);
		
		// Read the data from file
		String fileDirectory = "/home/rkl37156/ceria_dean_data/";
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
		Dataset y = DoubleDataset.zeros(x);
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
		Dataset pathLengthExp = new DoubleDataset(xSize, ySize); 
		Dataset pathLength = new DoubleDataset(pathLengthExp);
		Dataset r = new DoubleDataset(xSize, ySize);
		Dataset xi = new DoubleDataset(r);
		
		String[] angles = {"0.0", "15.0", "28.5", "29.5", "47.0"};
		int nAngles = angles.length;
		
		for (String angle : angles) {
			
			// Read the data from file
			String fileDirectory = "/home/rkl37156/ceria_dean_data/";
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
			Dataset y = DoubleDataset.zeros(x);
			pathLength= cap.getDownstreamPathLength(x, y, z, 0.0, Math.toRadians(Double.parseDouble(angle)));

			// TODO: remove temporary write command
			try (Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/home/rkl37156/ceria_dean_data/outgoing"+angle+".java.txt"), "utf-8"))) {
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
	
	public void testAbsorptionCorrections() {

//		fail("Temporary fail"); //TODO: remove
		double rmsError = absorptionCorrectionCommon(true, true);
		double rmsErrorTarget = 1e-6;
		assertTrue("Error in sample-sample absorption correction too large: " + rmsError, rmsError < rmsErrorTarget);
	}

	public void testAbsorptionCorrectionsSC() {

//		fail("Temporary fail"); //TODO: remove
		double rmsError = absorptionCorrectionCommon(true, false);
		double rmsErrorTarget = 1e-6;
		assertTrue("Error in sample-capillary absorption correction too large: " + rmsError, rmsError < rmsErrorTarget);		
	}

	public void testAbsorptionCorrectionsCS() {

//		fail("Temporary fail"); //TODO: remove
		double rmsError = absorptionCorrectionCommon(false, true);
		double rmsErrorTarget = 1e-6;
		assertTrue("Error in capillary-sample absorption correction too large: " + rmsError, rmsError < rmsErrorTarget);
	}
	
	public void testAbsorptionCorrectionsCC() {

//		fail("Temporary fail"); //TODO: remove
		double rmsError = absorptionCorrectionCommon(false, false);
		double rmsErrorTarget = 1e-6;
		assertTrue("Error in capillary-capillary absorption correction too large: " + rmsError, rmsError < rmsErrorTarget);
	}
	
	public double absorptionCorrectionCommon(boolean isSampleScatterer, boolean isSampleAttenuator) {
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
		String dataPath = "/home/rkl37156/ceria_dean_data/";
		IDataHolder dh = null;
		String scattererName, attenuatorName;
		scattererName = (isSampleScatterer) ? "sample" : "container";
		attenuatorName = (isSampleAttenuator) ? "sample" : "container";
		try {
			dh = LoaderFactory.getData(dataPath+scattererName + "_" + attenuatorName + "_abs.xy");
		} catch (Exception e) {
		}
		Dataset delta1D = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_1").getSlice());
		Dataset delta = new DoubleDataset(delta1D.getSize(), 1);
		for (int i = 0; i<delta1D.getSize(); i++)
			delta.set(delta1D.getDouble(i), i, 0);
		Dataset gamma = DoubleDataset.zeros(delta);
		Dataset expected = 	DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
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
	
	public void testFluorescence() {
		
		XPDFSubstance ceria = new XPDFSubstance("ceria", "CeO2", 7.65, 0.6);
		XPDFSubstance bto = new XPDFSubstance("BTO", "BaTiO3", 6.05, 0.6);
		XPDFSubstance nickel = new XPDFSubstance("nickel", "Ni", 8.95, 0.6);
		XPDFSubstance quartz = new XPDFSubstance("quartz", "SiO2", 2.65, 1.0);
		
		double[] ceriumLines = new double[] {34.7196, 34.2788, 39.2576, 4.8401, 5.2629};
		double[] bariumLines = new double[] {32.1936, 31.817, 36.3784, 4.4663, 4.8275};
		double[] nickelLines = new double[] {7.4781};
		
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
		
		
		List<Double> muIn = new ArrayList<Double>();
		muIn.add(ceria.getAttenuationCoefficient(beamData.getBeamEnergy()));
		muIn.add(quartz.getAttenuationCoefficient(beamData.getBeamEnergy()));
		
		List<Double> muOut = new ArrayList<Double>();
		muOut.add(ceria.getAttenuationCoefficient(ceriumLines[0]));
		muOut.add(quartz.getAttenuationCoefficient(ceriumLines[1]));
		
		// Read the target data, and also the angles (in radians) to run
		String dataPath = "/home/rkl37156/ceria_dean_data/testData/";
		IDataHolder dh = null;
		String fluorName = "ceria";
		String fluorNumber = "1";
		try {
			dh = LoaderFactory.getData(dataPath+fluorName+ ".fluor" + fluorNumber + ".xy");
		} catch (Exception e) {
		}
		Dataset delta1D = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_1").getSlice());
		Dataset delta = new DoubleDataset(delta1D.getSize(), 1);
		for (int i = 0; i<delta1D.getSize(); i++)
			delta.set(delta1D.getDouble(i), i, 0);
		Dataset gamma = DoubleDataset.zeros(delta);

		Dataset expected = DatasetUtils.convertToDataset(dh.getLazyDataset("Column_2").getSlice());
		
		Dataset ceriaFluor1 = powder1mm.calculateFluorescence(gamma, delta, attenuators, muIn, muOut, beamData, true, true);
		
		Dataset error = Maths.divide(ceriaFluor1.squeeze(), expected);
		error.isubtract(1);
		error = Maths.square(error);
		double sumError = (double) error.mean();
		sumError = Math.sqrt(sumError);
		
		assertTrue("Too large a difference, " + sumError + " between expected and calculated fluorescence for " + fluorName + " at " + ceriumLines[0] + "keV.",
				sumError < 1e-2);
	}
	

}
