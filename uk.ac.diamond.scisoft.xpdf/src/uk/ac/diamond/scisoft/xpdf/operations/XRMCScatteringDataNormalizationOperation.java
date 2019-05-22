/*-
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.Collection;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.xpdf.metadata.XRMCMetadata;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCDetector;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCSource;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCSpectrum;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCSpectrum.IPolarizedComponent;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCSpectrum.IUnpolarizedComponent;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCSpectrum.SpectrumComponent;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCToDetectorProperties;

public class XRMCScatteringDataNormalizationOperation extends AbstractOperation<XRMCScatteringNormalizationModel, OperationData> {

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
		XRMCSource xrmcSource = xrmcMetadata.getSource();
		
		DetectorProperties detProp = XRMCToDetectorProperties.get(xrmcDet, xrmcSource);

		// convert from photons to photons per unit solid angle
		Dataset saCorrected = model.isNormalizeOmega() ? 
					correctSolidAngle(DatasetUtils.convertToDataset(input), detProp) :
					DatasetUtils.convertToDataset(input);

		XRMCSpectrum xrmcSpec = xrmcMetadata.getSpectrum();

		// normalize by total photon flux.
		Dataset normed = normalizeByFlux(saCorrected, xrmcSpec, xrmcSource);

		normed.setMetadata(xrmcMetadata);

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

		double[] beamSize = source.getBeamSpotSize(); // beam size in cm
		double beamArea = beamSize[0] * 0.01 * beamSize[1] * 0.01; // beam area in m^2
		
		
		List<XRMCSpectrum.SpectrumComponent> spectrumComponents = spectrum.getSpectrum();
		double totalIntensity = sumIntensities(spectrumComponents);
		double totalFlux = totalIntensity;// integrated flux

		double areaFlux = totalIntensity / beamArea; // Area flux: photons per square metre
		
		return Maths.divide(unnormed, totalFlux);

	}

}
