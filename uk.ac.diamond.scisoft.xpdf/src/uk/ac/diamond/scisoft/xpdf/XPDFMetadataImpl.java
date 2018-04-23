/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.metadata.MetadataType;

import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Implementation of the XPDF metadata.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 */
@SuppressWarnings("serial")
public class XPDFMetadataImpl implements XPDFMetadata {

	private XPDFTargetComponent sampleData;
	private List<XPDFTargetComponent> containerData;
	private XPDFBeamData beamData;
	private XPDFAbsorptionMaps absorptionCorrectionMaps;
	private XPDFDetector tect;
	private Map<XPDFTargetComponent, XPDFBeamTrace> containerDataParameters;
	private XPDFBeamTrace sampleParameters; // no data, that is the input
	private XPDFBeamTrace emptyDataParameters;
	private double calCon;
	private double fluoro;
	private double lorchCutOff;
	private double comptonScaling = 1.0;
	private List<Dataset> incoherentScatteringData;
	
	/**
	 * Empty constructor.
	 */
	public XPDFMetadataImpl() {
		sampleData = null;
		sampleParameters = null;
		containerData = new ArrayList<XPDFTargetComponent>();
		containerDataParameters = new HashMap<XPDFTargetComponent, XPDFBeamTrace>();
		beamData = null;
		emptyDataParameters = null;
		absorptionCorrectionMaps = null;
		tect = null;
		incoherentScatteringData = new ArrayList<Dataset>();
	}
	
	/**
	 * Copy constructor.
	 * @param inMeta
	 * 				metadata object to be copied.
	 */
	public XPDFMetadataImpl(XPDFMetadataImpl inMeta) {
		this.sampleData = (inMeta.sampleData != null) ? new XPDFTargetComponent(inMeta.sampleData) : null;
		this.sampleParameters = (inMeta.sampleParameters != null) ? new XPDFBeamTrace(inMeta.getSampleTrace()) : null;
		
		// Copy the container parameters and the container data
		this.containerData = new ArrayList<XPDFTargetComponent>();
		this.containerDataParameters = new HashMap<XPDFTargetComponent, XPDFBeamTrace>();
		for (XPDFTargetComponent container : inMeta.containerData) {
			if (container != null) {
				XPDFTargetComponent newContainer = new XPDFTargetComponent(container);
				this.containerData.add(newContainer);
				this.containerDataParameters.put(newContainer, new XPDFBeamTrace(inMeta.getContainerTrace(container)));
			}
		}
			
		
		this.beamData = (inMeta.beamData != null) ? new XPDFBeamData(inMeta.beamData) : null;
		this.emptyDataParameters = (inMeta.getEmptyTrace() != null) ? new XPDFBeamTrace(inMeta.getEmptyTrace()) : null;
		
		this.absorptionCorrectionMaps = (inMeta.absorptionCorrectionMaps != null) ? new XPDFAbsorptionMaps(inMeta.absorptionCorrectionMaps) : null;
		this.tect = (inMeta.tect != null) ? new XPDFDetector(inMeta.tect) : null;
		
		// Copy the additional parameters
		this.calCon = inMeta.calCon;
		this.fluoro = inMeta.fluoro;
		this.lorchCutOff = inMeta.lorchCutOff;
		this.comptonScaling = inMeta.comptonScaling;

		// Deep copy of the incoherent scattering data
		this.incoherentScatteringData = new ArrayList<Dataset>();
		for (Dataset incohere: inMeta.incoherentScatteringData) {
			this.pushIncoherentScattering(incohere.clone());
		}
	}
	
	/**
	 * Clone method.
	 */
	@Override
	public MetadataType clone() {
		return new XPDFMetadataImpl(this);		
	}

	/**
	 * Re-orders the containers, from the sample outwards. 
	 */
	@Override
	public void reorderContainers(Map<Integer, Integer> newOrder) {
		// iterate over the keys in order
		int nKeys = newOrder.size();
		List <XPDFTargetComponent> newContainerData = new ArrayList<XPDFTargetComponent>();
		for (int iKey = 0; iKey < nKeys; iKey++) {
			newContainerData.add(iKey, this.containerData.get(newOrder.get(iKey)));
		}
		this.containerData = newContainerData;
	}

	/**
	 * Gets the containers. 
	 */
	@Override
	public List<XPDFTargetComponent> getContainers() {
		return containerData;
	}

	/**
	 *  Gets the sample.
	 */
	@Override
	public XPDFTargetComponent getSample() {
		return sampleData;
	}

	/**
	 * Getter for the beam data.
	 */
	@Override
	public XPDFBeamData getBeam() {
		return beamData;
	}

	/**
	 * Getter for the sample properties.
	 * @return
	 */
	public XPDFTargetComponent getSampleData() {
		return sampleData;
	}

	/**
	 * Setter for the sample properties.
	 * @param sampleData
	 * 					sample properties to be set.
	 */
	public void setSampleData(XPDFTargetComponent sampleData) {
		this.sampleData = sampleData;
	}

	/**
	 * Getter for all the container data.
	 * @return a list of all the containers's properties.
	 */
	public List<XPDFTargetComponent> getContainerData() {
		return containerData;
	}

	/**
	 * Setter for all the container data
	 * @param containerData
	 * 					list of all the containers's properties.
	 */
	public void setContainerData(List<XPDFTargetComponent> containerData) {
		this.containerData = containerData;
	}

	/**
	 * Setter for the beam data.
	 * @param beamData
	 * 				the beam data to be set.
	 */
	public void setBeamData(XPDFBeamData beamData) {
		this.beamData = beamData;
	}

	/**
	 * Adds a container to the container list.
	 * <p>
	 * Add a single container to the end of the container list. If the list is
	 * sorted, this is the outside of the (ordered) set of containers.
	 * @param newContainer
	 * 					container properies to be added.
	 */
	public void addContainer(XPDFTargetComponent newContainer) {
		if (containerData == null)
			containerData = new ArrayList<XPDFTargetComponent>();
		this.containerData.add(newContainer);
	}

	/**
	 * Gets the number of illuminated sample atoms.
	 * <p>
	 * A pass through function to return the number of atoms in the sample
	 * illuminated by the X-ray beam.
	 */
	@Override
	public double getSampleIlluminatedAtoms() {
		return getSample().getForm().getIlluminatedAtoms(beamData);
	}

	/**
	 * Calculates and returns the absorption maps for the target components, as
	 * ordered in the list of containers, with the sample appended in position 0.  
	 */
	@Override
	public XPDFAbsorptionMaps getAbsorptionMaps(Dataset delta, Dataset gamma) {
		// The intention is to be able to recall cached maps, if these are set 
		// in the metadata. They are not, yet, so press on with the direct
		// calculations. The map uses a pair of integers in a string as an
		// index, based on the order in which they come in the list of components 
		if (absorptionCorrectionMaps == null) {
		
			absorptionCorrectionMaps = new XPDFAbsorptionMaps();

			absorptionCorrectionMaps.setGamma(gamma);
			absorptionCorrectionMaps.setDelta(delta);
			absorptionCorrectionMaps.setBeamData(beamData);
			// Add the target component forms, starting with the sample
//			List<XPDFComponentForm> formsList = new ArrayList<XPDFComponentForm>();
//			formsList.add(sampleData.getForm());
//			for (XPDFTargetComponent container : containerData)
//				formsList.add(container.getForm());
//
//			for (XPDFComponentForm form : formsList) {
//				absorptionCorrectionMaps.addForm(form);
//			}
//
//			
			
//			for (XPDFComponentForm formScatterer : formsList) {
//				for (XPDFComponentForm formAttenuator : formsList) {
//					absorptionCorrectionMaps.setAbsorptionMap(formScatterer, formAttenuator,
//							formScatterer.getGeom().calculateAbsorptionCorrections(gamma, delta, formAttenuator.getGeom(), formAttenuator.getAttenuationCoefficient(beamData.getBeamEnergy()), beamData, true, true));
//				}
//			}
			
//			absorptionCorrectionMaps.addForm(sampleData.getForm());
//			for (XPDFTargetComponent container : containerData)
//				absorptionCorrectionMaps.addForm(container.getForm());
			for (XPDFComponentForm targetForm : this.getFormList())
				absorptionCorrectionMaps.addForm(new XPDFComponentForm(targetForm));
			
			
			absorptionCorrectionMaps.calculateAbsorptionMaps();			
			
			// Note the less than or equal to
//			for (int iScatterer = 0; iScatterer <= containerData.size(); iScatterer++) {
//				XPDFTargetComponent scatterer = (iScatterer == 0) ? sampleData : containerData.get(iScatterer-1);
//
//				for (int iAttenuator = 0; iAttenuator <= containerData.size(); iAttenuator++) {
//					XPDFTargetComponent attenuator = (iAttenuator == 0) ? sampleData : containerData.get(iAttenuator-1);
//					absorptionCorrectionMaps.setAbsorptionMap(iScatterer, iAttenuator, 
//							scatterer.getForm().getGeom().calculateAbsorptionCorrections(gamma, delta, attenuator.getForm().getGeom(), attenuator.getForm().getAttenuationCoefficient(beamData.getBeamEnergy()), beamData, true, true));
//				}
//			}

		
		
		}		
		return absorptionCorrectionMaps;
	}

	@Override
	public List<XPDFComponentForm> getFormList() {
		List<XPDFComponentForm> formList = new ArrayList<XPDFComponentForm>();
		formList.add(sampleData.getForm());
		for (XPDFTargetComponent container : containerData)
			formList.add(container.getForm());
		return formList;
	}

	/**
	 * Setter for the detector object.
	 * @param inTect
	 * 				the detector to be assigned.
	 */
	public void setDetector(XPDFDetector inTect) {
		tect = inTect;
	}
	
	@Override
	public XPDFDetector getDetector() {
		return tect;
	}

	/**
	 * Returns the sample fluorescence.
	 */
	@Override
	public Dataset getSampleFluorescence(Dataset gamma, Dataset delta) {
		
		XPDFCoordinates coords = new XPDFCoordinates();
		coords.setGammaDelta(gamma, delta);
		
		return getSampleFluorescence(coords);
	}
	
	@Override
	public Dataset getSampleFluorescence(XPDFCoordinates coords) {
		Dataset totalSampleFluorescence = DatasetFactory.zeros(coords.getGamma());

		ExecutorService ravager = Executors.newFixedThreadPool(4); // Why not 4?
		AtomicInteger counter = new AtomicInteger(0);
		long nLines = 0;
		Set<Future<Dataset>> futureSet = new HashSet<Future<Dataset>>();
		
		for (XPDFFluorescentLine line : sampleData.getFluorescences(getBeam().getBeamEnergy())) {
			futureSet.add(ravager.submit(new SampleFluorescenceLineEvaluator(coords, getFormList(), getSampleData(), line, counter)));
			nLines++;
		}
		
		// Spin, checking if all the threads have completed
		while (counter.get() < nLines)
			try {
				Thread.sleep(100);
			} catch (InterruptedException iE) {
				; // Do nothing: go to check on the results again 
			}

		// Gather the parallel results
		for (Future<Dataset> lineFuture: futureSet)
			try {
				Dataset future = lineFuture.get();
				totalSampleFluorescence.squeeze();
				totalSampleFluorescence.iadd(future);
			} catch (ExecutionException eE) {
				// Do nothing!
				// FIXME Do something!
			} catch (InterruptedException iE) {
				// Do nothing!
				// FIXME Do something!
			}
		ravager.shutdown();
		
		totalSampleFluorescence.imultiply(tect.getSolidAngle());
		return totalSampleFluorescence.squeeze();
	}

	@Override
	public void defineUndefinedSamplesContainers() throws Exception {
		// Do nothing if the geometry is defined
		if (sampleData.getForm().getGeom() != null) return;
		XPDFTargetComponent outwardContainer = null, inwardContainer = null;
		// TODO: cope with the case where the sample is not the innermost component
		{
			inwardContainer = null;
			outwardContainer = containerData.get(0);
		}
		// Assume an outward container exists
		if (outwardContainer == null) throw new Exception("No outer container defined");
		if (outwardContainer.getForm().getGeom() == null) throw new Exception("No geometry defined for the outer container");
		if (inwardContainer != null && inwardContainer.getForm().getGeom() == null) throw new Exception("No geometry defined for the inner container");
		if (inwardContainer != null && inwardContainer.getForm().getGeom().getShape() != outwardContainer.getForm().getGeom().getShape()) throw new Exception("Bounding container geometries differ") ;
		// Check both containers have the same geometry
		XPDFComponentGeometry geomMeta = null;
		switch (outwardContainer.getForm().getGeom().getShape().toLowerCase()) {
			case("cylinder") :
				geomMeta = new XPDFComponentCylinder();
			break;
			case("plate") :
				geomMeta = new XPDFComponentPlate();
		}
		double inner, outer;
		if (inwardContainer == null) 
			inner = 0.0;
		else
			inner = inwardContainer.getForm().getGeom().getDistances()[1];
		
		outer = outwardContainer.getForm().getGeom().getDistances()[0];
		
		geomMeta.setDistances(inner, outer);
		
		// the sample orientation in space should match its container
		geomMeta.setEulerAngles(outwardContainer.getForm().getGeom().getEulerAngles());
		
		sampleData.getForm().setGeom(geomMeta);
		
	}

	@Override
	public XPDFBeamTrace getSampleTrace() {
		return sampleParameters;
	}

	/**
	 * Sets the {@link XPDF BeamTrace} object of the sample.
	 * <p>
	 * The object contains the count time and the monitor-relative flux. The trace field should be null.
	 * @param traceParameters
	 * 						object to set.
	 */
	public void setSampleTrace(XPDFBeamTrace traceParameters) {
		sampleParameters = new XPDFBeamTrace(traceParameters);
	}
	
	@Override
	public XPDFBeamTrace getEmptyTrace() {
		return emptyDataParameters;
	}

	/**
	 * Sets the {@link XPDF BeamTrace} object of the empty beam.
	 * <p>
	 * The object contains the count time and the monitor-relative flux.
	 * @param traceDataParameters
	 * 							object to set
	 */
	public void setEmptyTrace(XPDFBeamTrace traceDataParameters) {
		emptyDataParameters = new XPDFBeamTrace(traceDataParameters);
	}
	
	@Override
	public XPDFBeamTrace getContainerTrace(XPDFTargetComponent container) {
		if (!containerData.contains(container))
			return null;
		return containerDataParameters.get(container);
	}
	
	/**
	 * Sets the {@link XPDF BeamTrace} object of a specific container.
	 * <p>
	 * The object contains the count time and the monitor-relative flux. The 
	 * container doesn't have to be in the List of containers, but probably should be. 
	 * @param container
	 * 					container to bind the data and parameters to.
	 * @param traceDataParameters
	 * 							object to set.
	 */
	public void setContainerTrace(XPDFTargetComponent container, XPDFBeamTrace traceDataParameters) {
		containerDataParameters.put(container, new XPDFBeamTrace(traceDataParameters));
	}

	@Override
	public double getCalibrationConstant() {
		return calCon;
	}

	@Override
	public void setCalibrationConstant(double calCon) {
		this.calCon = calCon;
	}

	@Override
	public double getFluorescenceScale() {
		return fluoro;
	}

	@Override
	public void setFluorescenceScale(double scale) {
		this.fluoro = scale;
	}

	@Override
	public double getLorchCutOff() {
		return lorchCutOff;
	}

	@Override
	public void setLorchCutOff(double cutOff) {
		this.lorchCutOff = cutOff;		
	}

	private class SampleFluorescenceLineEvaluator implements Callable<Dataset>{
		private XPDFCoordinates coords;
		private List<XPDFComponentForm> attenuatorForms;
		private XPDFTargetComponent sample;
		private XPDFFluorescentLine line;
		private AtomicInteger counter;
	
		public SampleFluorescenceLineEvaluator(XPDFCoordinates coords, List<XPDFComponentForm> attenuatorForms, XPDFTargetComponent sample, XPDFFluorescentLine line, AtomicInteger counter) {
			this.coords = coords;
			this.attenuatorForms = attenuatorForms;
			this.sample = sample;
			this.line = line;
			this.counter = counter;
		}
		
		public Dataset call() {
			List<XPDFComponentGeometry> attenuators = new ArrayList<XPDFComponentGeometry>();
			List<Double> attenuationsIn = new ArrayList<Double>(),
					attenuationsOut = new ArrayList<Double>();
			for (XPDFComponentForm componentForm : attenuatorForms) {
				attenuators.add(componentForm.getGeom());
				attenuationsIn.add(componentForm.getSubstance().getAttenuationCoefficient(beamData.getBeamEnergy()));
				attenuationsOut.add(componentForm.getSubstance().getAttenuationCoefficient(line.getEnergy()));
			}
			Dataset oneLineFluorescence = sampleData.getForm().getGeom().calculateFluorescence(coords.getGamma(), coords.getDelta(), attenuators, attenuationsIn, attenuationsOut, beamData, true, true);
			double lineXSection = line.getCrossSection();
			double lineNumberDensity = sampleData.getNumberDensity(line.getFluorescentZ());
			oneLineFluorescence.imultiply(lineXSection*lineNumberDensity);
			Dataset detectorCorrectedOLF = tect.applyTransmissionCorrection(oneLineFluorescence.squeeze(), coords.getTwoTheta(), line.getEnergy());
			counter.incrementAndGet();
			return detectorCorrectedOLF;	

		}
	
	}

	@Override
	public void setComptonScaling(double comptonScaling) {
		this.comptonScaling = comptonScaling;
	}

	@Override
	public double getComptonScaling() {
		return this.comptonScaling;
	}

	@Override
	public void pushIncoherentScattering(Dataset data) {
		this.incoherentScatteringData.add(data);
	}
	
	@Override
	public Dataset getIncoherentScattering(int index) {
		return this.incoherentScatteringData.get(index);
	}

	@Override
	public boolean isAllIncoherentScatterPresent() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
