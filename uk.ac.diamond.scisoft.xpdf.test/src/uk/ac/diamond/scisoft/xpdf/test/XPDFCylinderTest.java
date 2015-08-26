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
import uk.ac.diamond.scisoft.xpdf.XPDFComponentGeometry;
import junit.framework.TestCase;

public class XPDFCylinderTest extends TestCase {

	public void testGetUpstreamPathLength() {
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

		double rmsError = absorptionCorrectionCommon(true, true);
		double rmsErrorTarget = 1e-6;
		assertTrue("Error in sample-sample absorption correction too large: " + rmsError, rmsError < rmsErrorTarget);
	}

	public void testAbsorptionCorrectionsSC() {

		double rmsError = absorptionCorrectionCommon(true, false);
		double rmsErrorTarget = 1e-6;
		assertTrue("Error in sample-capillary absorption correction too large: " + rmsError, rmsError < rmsErrorTarget);		
	}

	public void testAbsorptionCorrectionsCS() {

		double rmsError = absorptionCorrectionCommon(false, true);
		double rmsErrorTarget = 1e-6;
		assertTrue("Error in capillary-sample absorption correction too large: " + rmsError, rmsError < rmsErrorTarget);
	}
	
	public void testAbsorptionCorrectionsCC() {

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
}
