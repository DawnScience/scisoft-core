package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.xpdf.metadata.XRMCMetadata;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCDetector;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCEnergyIntegrator;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCSource;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCSpectrum;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCSpectrum.IPolarizedComponent;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCSpectrum.IUnpolarizedComponent;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCSpectrum.SpectrumComponent;

public class XRMCNormalization extends AbstractOperation<EmptyModel, OperationData> {

//	DetectorProperties detProp;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XRMCNormalization";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		// Get the metadata
		XRMCMetadata xrmcMetadata = input.getFirstMetadata(XRMCMetadata.class);

		XRMCDetector xrmcDet = xrmcMetadata.getDetector();
		Vector3d originXRMC = xrmcDet.labFromPixel(new Vector2d(0, 0)); // top left of the top left pixel: DetectorProperties origin, in XRMC lab frame
		Vector3d originDP = new Vector3d(-originXRMC.x, originXRMC.z, originXRMC.y); // origin, Detector Properties frame
		Dataset eulerXYZ = DatasetFactory.createFromList(Arrays.asList(ArrayUtils.toObject(xrmcDet.getEulerAngles())));
		Dataset pixelSizeDataset = DatasetFactory.createFromList(Arrays.asList(ArrayUtils.toObject(xrmcDet.getPixelSize()))).idivide(1000);

		// Get the image size of the xrmc data
		int[] shape = input.getShape();
		int nx = shape[1], ny = shape[2];
		Dataset pixelSpacing = DatasetFactory.createFromList(Arrays.asList(ArrayUtils.toObject(xrmcDet.getPixelSize()))).idivide(1000);

		Vector3d beamVector = new Vector3d(0., 0., 1.);
		DetectorProperties detProp = XRMCEnergyIntegrator.calculateDetectorProperties(nx, ny, originDP, beamVector, eulerXYZ, pixelSpacing);

		// convert from photons to photons per unit area
		Dataset saCorrected = correctSolidAngle(DatasetUtils.convertToDataset(input), detProp);
		
		XRMCSpectrum xrmcSpec = xrmcMetadata.getSpectrum();
		XRMCSource xrmcSource = xrmcMetadata.getSource();

		// normalize by total photon flux.
		Dataset normed = normalizeByFlux(saCorrected, xrmcSpec, xrmcSource);
		
		
		return new OperationData(normed);
	}

	// Convert from counts per pixel to photons per incident photon per unit solid angle
	private Dataset correctSolidAngle(Dataset flux, DetectorProperties detProp) {
		
		Dataset omegaFlux = DatasetFactory.zeros(flux);
		
		if (detProp != null) {
			int[] shape = flux.getShape();
			for (int i = 0; i < shape[0]; i++) {
				for (int j = 0; j < shape[1]; j++) {
					double solidAngle = detProp.calculateSolidAngle(i, j);
					omegaFlux.set(flux.getDouble(i, j)/solidAngle, i, j);
				}
			}
			return omegaFlux;
			
		} else {
			return flux;
		}
	}
	
	private static double sumIntensities(Collection<SpectrumComponent> components) {
		double sum = 0.0;
		for (SpectrumComponent compo : components) {
			if (compo instanceof IUnpolarizedComponent) {
				sum += ((IUnpolarizedComponent) compo).getIntensity();
			} else if (compo instanceof IPolarizedComponent) {
				sum += ((IPolarizedComponent) compo).getIntensity1();
				sum += ((IPolarizedComponent) compo).getIntensity2();
			}
		}
		
		return sum;
	}

	private static Dataset normalizeByFlux(Dataset unnormed, XRMCSpectrum spectrum, XRMCSource source) {

		double[] sourceSize = source.getSize();
		double beamArea = sourceSize[0]*sourceSize[1];
		// convert to SI: per metre squared
		beamArea *= 1e-2 * 1e-2;

		List<XRMCSpectrum.SpectrumComponent> spectrumComponents = spectrum.getSpectrum();
		double totalIntensity = sumIntensities(spectrumComponents);
		double totalFlux = totalIntensity/beamArea; // photons per metre squared

		return Maths.divide(unnormed, totalFlux);

	}

}
