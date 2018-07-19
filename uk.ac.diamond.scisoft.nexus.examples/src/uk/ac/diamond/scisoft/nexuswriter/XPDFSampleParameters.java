/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.nexuswriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.nexus.NXentry;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXshape;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.builder.NexusFileBuilder;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.xpdf.XPDFComponentCylinder;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentForm;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentGeometry;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentPlate;
import uk.ac.diamond.scisoft.xpdf.XPDFComposition;
import uk.ac.diamond.scisoft.xpdf.XPDFSubstance;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;

/**
 * Holds the data relating to a sample.
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
class XPDFSampleParameters {
	private int id;
	private List<XPDFPhase> phases;
	private List<Double> fractions; // mass fractions for the above phases.
	private XPDFTargetComponent component;
	
	private static final int nDim = 3;
	
	/**
	 * default ctor
	 */
	public XPDFSampleParameters() {
		this.component = new XPDFTargetComponent();
		this.phases = new ArrayList<XPDFPhase>();
		this.fractions = new ArrayList<Double>();
	}

	/**
	 * Construct a new sample parameters designated as an actual sample.
	 * @param isSample
	 */
	public XPDFSampleParameters(boolean isSample) {
		this();
		this.component.setSample(isSample);
	}
	
	/**
	 * Copy constructor
	 */
	public XPDFSampleParameters(XPDFSampleParameters inSamp) {
		this.component = new XPDFTargetComponent(inSamp.component);
		this.phases = new ArrayList<XPDFPhase>(inSamp.phases);
		this.fractions = new ArrayList<Double>(inSamp.fractions);
	}
	
	/**
	 * @return the name of the sample
	 */
	public String getName() {
		return component.getName();
	}
	/**
	 * @param name
	 * 			 the name of the sample to set
	 */
	public void setName(String name) {
		component.setName(name);
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the phases present in the sample
	 */
	public List<XPDFPhase> getPhases() {
		return phases;
	}
	/**
	 * @param phases sets the phases present in the sample
	 */
	public void setPhases(List<XPDFPhase> phases) {
		this.phases = phases;
	}

	public void clearPhases() {
		this.phases = new ArrayList<XPDFPhase>();
		this.fractions = new ArrayList<Double>();
	}
	
	public void addPhase(XPDFPhase phase) {
		this.addPhase(phase, 1.0);
	}
	
	public void addPhase(XPDFPhase phase, double weighting) {
		phases.add(phase);
		fractions.add(phases.indexOf(phase), weighting);
	}
	
	public List<Double> getPhaseWeightings() {
		return fractions;
	}
	
	public void setPhaseWeightings(List<Double> weightings) {
		fractions = weightings;
	}
	
	public void setPhaseWeighting(XPDFPhase phase, double weighting) {
		if (phases.contains(phase)) {
			fractions.add(phases.indexOf(phase), weighting);
		}
	}
	
	public double getPhaseWeighting(XPDFPhase phase) {
		if (phases.contains(phase))
			return fractions.get(phases.indexOf(phase));
		else
			return 0.0;
	}
	
	/**
	 * @return the isSample
	 */
	public boolean isSample() {
		return component.isSample();
	}

	/**
	 *  Sets the object as representing a sample
	 */
	public void setAsSample() {
		component.setSample(true);
	}

	/**
	 * Sets the object as a container
	 */
	public void setAsContainer() {
		component.setSample(false);
	}
	
	/**
	 * @return the substance
	 */
	public XPDFSubstance getSubstance() {
		return getForm().getSubstance();
	}
	
	/**
	 * @param substance the substance to set
	 */
	public void setSubstance(XPDFSubstance substance) {
		getForm().setMatName(substance.getMaterialName());
	}
	
	// Getters and setters for the properties of the substance
	/**
	 * @return the ASCII formula of the substance
	 */
	public String getComposition() {
		
		if (phases.isEmpty()) {
			if (component.getForm() == null || component.getForm().getSubstance().getComposition() == null)
				return "-";
			else
				return component.getForm().getSubstance().getComposition().getHallNotation(true);
		} else {
			List<XPDFComposition> phaseCompositions = new ArrayList<XPDFComposition>();
			for (XPDFPhase phase : phases)
				phaseCompositions.add(phase.getComposition());
			double totalWeight = 0.0;
			for (double weight : fractions)
				totalWeight += weight;
			for (XPDFComposition compo : phaseCompositions)
				compo.weight(fractions.get(phaseCompositions.indexOf(compo))/totalWeight);
			XPDFComposition totalComposition = new XPDFComposition("");
			for (XPDFComposition compo : phaseCompositions)
				totalComposition.add(compo);
			return totalComposition.getHallNotation(true);
		}
	}
	/**
	 * @param compoString
	 * 					the composition to be set
	 */
	public void setComposition(String compoString) {
		if (phases.isEmpty())
			getForm().setMatName(compoString);
		// set the typical powder packing fraction
		getForm().setPackingFraction(0.6);
	}
	/**
	 * @return the crystallographic density of the material in g/cm³.
	 */
	public double getDensity() {
		
		if (phases.isEmpty()) {
			if (component != null && component.getForm() != null)
				return component.getForm().getDensity();
			else
				return 0.0;
		} else {
			// Weighted density of all phases
			// TODO: check the weighting
			double overallDensity = 0;
			double totalWeight = 0.0;
			for (double weight : fractions)
				totalWeight += weight;
			for (XPDFPhase phase : phases)
				overallDensity += phase.getDensity()*getPhaseWeighting(phase)/totalWeight;
			return overallDensity;
		}
	}
	/**
	 * @param density
	 * 				the crystallographic density of the material in g/cm³.
	 */
	public void setDensity(double density) {
		getForm().setDensity(density);
	}
	/**
	 * @return the powder packing fraction (probably 0.6).
	 */
	public double getPackingFraction() {
		return getForm().getPackingFraction();
	}
	/**
	 * @param fraction
	 * 				the powder packing fraction to set.
	 */
	public void setPackingFraction(double fraction) {
		getForm().setPackingFraction(fraction);
	}
	
	/**
	 * Gets the name of the shape of the sample or container.
	 * @return the name of the shape.
	 */
	public String getShapeName() {
		if (getForm().getGeom() == null)
			return "Defined by container";
		else if (getForm().getGeom() instanceof XPDFComponentCylinder)
			return "Cylinder";
		else
			return "Plate";
	}
	
	/**
	 * Sets the shape of the sample of container by name.
	 * @param shapeName name of the shape.
	 */
	public void setShape(String shapeName) {
		if (shapeName == null)
			getForm().setGeom(null);
		else
			switch(shapeName.toLowerCase()) {
			case("cylinder") :
				getForm().setGeom((getForm().getGeom() == null) ? new XPDFComponentCylinder() : new XPDFComponentCylinder(getForm().getGeom()));
				break;
			case("plate") :
				getForm().setGeom((getForm().getGeom() == null) ? new XPDFComponentPlate() : new XPDFComponentPlate(getForm().getGeom()));
				break;
			default:
				getForm().setGeom(null);
			}
	}
	
	/**
	 * Gets the dimensions of the sample or container.
	 * @return Size of the container as a 2-element array of floating point
	 * numbers.
	 */
	public double[] getDimensions() {
		if (getForm().getGeom() == null)
			return null;
		else
			return getForm().getGeom().getDistances();
	}

	/**
	 * Sets the dimensions of the sample or container.
	 * @param dims
	 * 			dimensions of the container as a pair of floating point
	 * 			numbers.
	 */
	public void setDimensions(double[] dims) {
		if (dims != null) {
			if (dims.length == 1) {
				getForm().getGeom().setDistances(0, dims[0]);
			} else if (dims.length > 1) {
				getForm().getGeom().setDistances(dims[0], dims[1]);
			}
		}
	}

	/**
	 * Sets the dimensions of the sample or container.
	 * @param inDim
	 * 				inner radius (cylinder) or up-stream distance (plate).
	 * @param outDim
	 * 				outer radius (cylinder) or down-stream distance (plate).
	 */
	public void setDimensions(double inDim, double outDim) {
		getForm().getGeom().setDistances(inDim, outDim);
	}
	
	@Override
	public String toString() {
		String dataString = id + ": " + getName() + ", " + getForm().getMaterialName() + " " + 
				getDensity() + " " + getPackingFraction();
		if (getForm().getGeom() == null)
			dataString += " shape defined by container.";
		else
			dataString += " " + getShapeName() + " of " + getDimensions() + " mm.";
		
		return dataString;
	}

	/**
	 * Creates a NeXus data structure from the sample.
	 * <p>
	 * Creates an XPDF NeXus data structure, using the supplied Nexus File
	 * Builder, as specified. Only works for samples, for now.
	 * @param builder
	 * 				the {@link NexusFileBuilder} to write the sample data to
	 */
	public NXsample getNXsample(NexusFileBuilder builder) {
		
//		if (!isSample()) return null;
		
		NXsample sample = NexusNodeFactory.createNXsample();
		NXentry sampleEntry;
		try {
			sampleEntry = builder.newEntry("entry1").getNXentry();
		} catch (NexusException nE) {
			System.err.println("Failed to create new entry with NeXus file builder: " + nE.toString());
			return null;
		}
		sampleEntry.setSample(sample);
		
		// begin building
		sample.setNameScalar(getName());
//		sample.setShort_titleScalar(getName());
		sample.setDescriptionScalar(getName() + ", " + getComposition() + ", " + getShapeName());
		// comments
		// QR code
		// components
		int nComp = phases.size();
		// Use Java 8 streams to get the contents of the phases from the list of phases
		sample.setComponent(DatasetFactory.createFromList(phases.stream().map(a -> a.getName()).collect(Collectors.toList())));
		sample.setChemical_formula(DatasetFactory.createFromObject(phases.stream().map(a -> a.getComposition().getHallNotation(false)).collect(Collectors.toList()), nComp, 1));
		sample.setField("chemical_formula_weight", DatasetFactory.createFromObject(phases.stream().map(a -> a.getComposition().getFormulaMass()).collect(Collectors.toList())));
		
		
		// Unit cell parameters; is there a way to make an array of arrays into a Dataset?
		double [] unitCellParams = new double[nComp * 2*nDim];
		boolean isAnyCrystalline = false;
		for (int i = 0; i < phases.size(); i++)
			if (phases.get(i).isCrystalline()) {
				isAnyCrystalline = true;
				for (int j = 0; j < nDim; j++) {
					// TODO: z_formula_per_unit_cell
					unitCellParams[i * 2*nDim + j] = phases.get(i).getUnitCellLength(j);
					unitCellParams[i * 2*nDim + j + nDim] = phases.get(i).getUnitCellAngle(j);
				}
			}
		if (isAnyCrystalline) {
			sample.setUnit_cell(DatasetFactory.createFromObject(unitCellParams, nComp, 2*nDim));
			String aa = "angstrom", oo = "degrees";
			sample.setAttribute("unit_cell", "units", DatasetFactory.createFromObject(new String[]{aa, aa, aa, oo, oo, oo}));
			sample.setAttribute("unit_cell", "signal", 0);
			sample.setUnit_cell_volume(DatasetFactory.createFromList(phases.stream().map(a -> a.getUnitCellVolume()).collect(Collectors.toList())));
			sample.setAttribute("unit_cell_volume", "units", aa+"³");
			sample.setUnit_cell_class(DatasetFactory.createFromList(phases.stream().map(a -> a.getCrystalSystem().getName()).collect(Collectors.toList())));
			sample.setSpace_group(DatasetFactory.createFromList(phases.stream().map(a -> a.getSpaceGroup().getNumber() + ": " + a.getSpaceGroup().getName()).collect(Collectors.toList())));

			// TODO: Crystal structure
		}
		// Densities and volume fractions and concentrations
		DoubleDataset theoreticalDensities =  DatasetFactory.createFromList(DoubleDataset.class, phases.stream().map(a -> a.getDensity()).collect(Collectors.toList()));
		sample.setField("theoretical_density", theoreticalDensities);
		sample.setAttribute("theoretical_density", "units", "g cm⁻³");
		// Assuming volume fraction of the total volume, not the non-void volume.
		// TODO: sort out mass versus volume fractions
		// mass fractions of the non-void matter
		DoubleDataset massFractions = DatasetFactory.createFromList(DoubleDataset.class, fractions);

		// The overall density of the powder is the sum of masses divided by the sum of masses divided by densities
		double overallDensity = ((double) massFractions.sum()) / ((double) Maths.divide(massFractions, theoreticalDensities).sum()); 
		// The net density is the density of the powder times the volume fraction
		sample.setDensityScalar(overallDensity * this.getPackingFraction());
		
		// volume fraction of the phase in the powder
		DoubleDataset volumeFractions = (DoubleDataset) Maths.multiply(Maths.divide(massFractions, theoreticalDensities), overallDensity);
		// volume fraction of the overall volume
		volumeFractions.imultiply(this.getPackingFraction());
		// concentrations: mass divided by total volume
		DoubleDataset concentrations = (DoubleDataset) Maths.multiply(theoreticalDensities, volumeFractions);
		sample.setConcentration(concentrations);
		sample.setVolume_fraction(volumeFractions);
		// total mass of the sample

		// Actual beam added during data collection, proposed beam not contained in this class
		{
			// TODO: Theoretical PDF not yet calculable 
		}
			// Dark frame added during data collection
			// Calibration added during data collection (at least of the calibration)
			// Mask added during data collection
			// Sample images added during data collection
		return sample;
	}
	
	public Map<String, GroupNode> getNexusGeometry() {
		Map<String, GroupNode> geometryNodes = new HashMap<String, GroupNode>();

		// add a shape to the sample, if the geometry is not null.
		if (component.getForm().getGeom() != null) {
			NXshape shape = NexusNodeFactory.createNXshape();
			XPDFComponentGeometry geom = component.getForm().getGeom();
			switch (geom.getShape().toLowerCase()) {
			case ("cylinder") :
				shape.setShapeScalar("nxcylinder");
			double cylinderHeight = 5.0; // a 5 mm capillary?
				// Use 2 cylinder objects to define the capillary
				Dataset cylinderParameters = DatasetFactory.createFromObject(new double[] {
						geom.getDistances()[0], cylinderHeight, 0, 1, 0,
						geom.getDistances()[1], cylinderHeight, 0, 1, 0
				}, new int[] {2, 5});
				shape.setSize(cylinderParameters);
			
			break;
			case ("plate") :
				shape.setShapeScalar("nxbox");
			break;
			}
			
			geometryNodes.put("shape", shape);
			
		}

		return geometryNodes;
	}
	
	
	// Simulate the pair distribution function of that this sample parameterizes
	public Dataset getSimulatedPDF() {
		// TODO Make this actually simulate a PDF
		Dataset r = DatasetFactory.createRange(DoubleDataset.class, 0.01, 50.0, 0.02);
		Dataset ceria = DatasetFactory.createFromList(Arrays.asList(new double[]
				{
				-0.051018955, -0.14392168, -0.21143420, -0.24290696, -0.23565999,
				 -0.19560127, -0.13595894, -0.074397602, -0.029142807, -0.014957260,
				 -0.039850182, -0.10324699, -0.19603303, -0.30247881, -0.40364252,
				 -0.48151786, -0.52302439, -0.52296111, -0.48526083, -0.42224711,
				 -0.35202960, -0.29458426, -0.26736157, -0.28138217, -0.33868693,
				 -0.43172488, -0.54484657, -0.65761097, -0.74921198, -0.80307361,
				 -0.81061042, -0.77331549, -0.70268941, -0.61798534, -0.54221839,
				 -0.49726864, -0.49911180, -0.55419493, -0.65773729, -0.79433115,
				 -0.94073036, -1.0702532, -1.1578907, -1.1850721, -1.1431315,
				 -1.0348069, -0.87353643, -0.68078045, -0.48200395, -0.30219915,
				 -0.16187523, -0.074278203, -0.044279620, -0.068968073, -0.13959259,
				 -0.24423227, -0.37046522, -0.50739877, -0.64667192, -0.78237802,
				 -0.91018835, -1.0261925, -1.1260450, -1.2048960, -1.2583144,
				 -1.2840582, -1.2842072, -1.2669480, -1.2472656, -1.2459789,
				 -1.2869303, -1.3926212, -1.5790544, -1.8508842, -2.1980720,
				 -2.5950610, -3.0030242, -3.3751043, -3.6638719, -3.8296592,
				 -3.8481067, -3.7153008, -3.4492780, -3.0873815, -2.6798141,
				 -2.2805662, -1.9375141, -1.6837242, -1.5318099, -1.4725702,
				 -1.4782408, -1.5096778, -1.5259157, -1.4939805, -1.3967451,
				 -1.2370131, -1.0368347, -0.83212149, -0.66369730, -0.56676134,
				 -0.56115098, -0.64467233, -0.79113562, -0.95372159, -1.0731361,
				 -1.0889446, -0.95175365, -0.63369237, -0.13498928, 0.51473392,
				 1.2607044, 2.0318595, 2.7524579, 3.3540458, 3.7853998, 4.0186056,
				 4.0503653, 3.8987153, 3.5963424, 3.1823536, 2.6945551, 2.1639826,
				 1.6126974, 1.0549170, 0.50062576, -0.039844647, -0.55199892,
				 -1.0155944, -1.4060734, -1.6988709, -1.8755449, -1.9300221,
				 -1.8730438, -1.7332203, -1.5538835, -1.3859926, -1.2784312,
				 -1.2678375, -1.3704249, -1.5779434, -1.8590642, -2.1662054,
				 -2.4464728, -2.6542852, -2.7626903, -2.7705273, -2.7034534,
				 -2.6082639, -2.5415668, -2.5553377, -2.6828067, -2.9282491,
				 -3.2634965, -3.6324840, -3.9632282, -4.1847425, -4.2450005,
				 -4.1255365, -3.8488004, -3.4759067, -3.0946209, -2.7998275,
				 -2.6707361, -2.7502109, -3.0314986, -3.4562326, -3.9251446,
				 -4.3199287, -4.5318480, -4.4906387, -4.1865941, -3.6796660,
				 -3.0919071, -2.5831382, -2.3136385, -2.4010428, -2.8807084,
				 -3.6789998, -4.6070631, -5.3789822, -5.6533835, -5.0925554,
				 -3.4290085, -0.52704872, 3.5730423, 8.6381051, 14.257734, 19.891803,
				 24.943215, 28.844216, 31.141714, 31.566819, 30.076364, 26.859091,
				 22.305424, 16.946288, 11.371828, 6.1442653, 1.7197961, -1.6077109,
				 -3.7328225, -4.7319016, -4.8271867, -4.3301518, -3.5759193,
				 -2.8612879, -2.3972877, -2.2835334, -2.5068727, -2.9619237,
				 -3.4870697, -3.9070762, -4.0730810, -3.8922186, -3.3421001,
				 -2.4690404, -1.3724599, -0.18054088, 0.97650859, 1.9894972,
				 2.7851169, 3.3306010, 3.6288000, 3.7065167, 3.6002291, 3.3434584,
				 2.9590588, 2.4579710, 1.8439627, 1.1221432, 0.30800953, -0.56629121,
				 -1.4509301, -2.2834922, -2.9992898, -3.5444133, -3.8881174,
				 -4.0311279, -4.0073525, -3.8780790, -3.7196302, -3.6071543,
				 -3.5983021, -3.7207138, -3.9664239, -4.2946919, -4.6427408,
				 -4.9419382, -5.1355541, -5.1937376, -5.1219035, -4.9602118,
				 -4.7739084, -4.6365019, -4.6095538, -4.7238215, -4.9663793,
				 -5.2771466, -5.5562421, -5.6811891, -5.5307660, -5.0107286,
				 -4.0760808, -2.7451640, -1.1024500, 0.71079825, 2.5163899, 4.1258938,
				 5.3691589, 6.1194341, 6.3106849, 5.9443881, 5.0852390, 3.8473462,
				 2.3741665, 0.81632668, -0.68854920, -2.0307613, -3.1364074,
				 -3.9680767, -4.5197120, -4.8079743, -4.8627613, -4.7191753,
				 -4.4123860, -3.9757433, -3.4414905, -2.8427523, -2.2152864,
				 -1.5977897, -1.0302059, -0.55028241, -0.18929300, 0.031796000,
				 0.10451787, 0.032727960, -0.16994399, -0.48426160, -0.88988159,
				 -1.3687550, -1.9063189, -2.4900805, -3.1061028, -3.7347366,
				 -4.3474344, -4.9064293, -5.3684215, -5.6922890, -5.8495054,
				 -5.8347541, -5.6735471, -5.4237565, -5.1689398, -5.0030689,
				 -5.0084181, -5.2304478, -5.6550156, -6.1937002, -6.6822016,
				 -6.8947007, -6.5740455, -5.4742508, -3.4087526, -0.29584553,
				 3.8077293, 8.6927431, 14.007693, 19.293820, 24.040909, 27.755773,
				 30.032563, 30.613167, 29.427314, 26.605225, 22.460352, 17.444884,
				 12.085442, 6.9096919, 2.3760007, -1.1826382, -3.5920533, -4.8436775,
				 -5.0754912, -4.5324286, -3.5147921, -2.3246635, -1.2197639,
				 -0.38192479, 0.096149275, 0.20602762, 0.0042890612, -0.41508930,
				 -0.94973662, -1.5129991, -2.0488126, -2.5356707, -2.9807697,
				 -3.4075997, -3.8414135, -4.2969560, -4.7716848, -5.2458269,
				 -5.6885172, -6.0675211, -6.3590761, -6.5544504, -6.6608488,
				 -6.6960279, -6.6779346, -6.6123407, -6.4823449, -6.2434947,
				 -5.8271157, -5.1524800, -4.1461404, -2.7646667, -1.0156690,
				 1.0282457, 3.2269266, 5.3837893, 7.2700488, 8.6586646, 9.3622390,
				 9.2678286, 8.3617394, 6.7389147, 4.5942284, 2.1963478, -0.15182913,
				 -2.1595022, -3.5929800, -4.3113910, -4.2867433, -3.6048354,
				 -2.4470341, -1.0564485, 0.30515512, 1.4017564, 2.0607865, 2.1975581,
				 1.8224960, 1.0305844, -0.024171337, -1.1637060, -2.2230718,
				 -3.0824062, -3.6874740, -4.0549618, -4.2617903, -4.4209041,
				 -4.6486841, -5.0307984, -5.5936677, -6.2876961, -6.9862217,
				 -7.5011741, -7.6132396, -7.1115168, -5.8357068, -3.7131815,
				 -0.78393947, 2.7916095, 6.7442373, 10.725781, 14.353078, 17.259260,
				 19.144346, 19.817103, 19.221503, 17.443598, 14.697822, 11.295102,
				 7.5980454, 3.9704998, 0.72949540, -1.8930340, -3.7732510, -4.9003944,
				 -5.3641472, -5.3277646, -4.9925695, -4.5605633, -4.2017541,
				 -4.0314537, -4.1005409, -4.3989940, -4.8704125, -5.4332754,
				 -6.0037009, -6.5146344, -6.9276136, -7.2352474, -7.4548280,
				 -7.6155746, -7.7434133, -7.8476401, -7.9132063, -7.9008734,
				 -7.7554523, -7.4202698, -6.8543606, -6.0480719, -5.0329834,
				 -3.8832462, -2.7073681, -1.6316720, -0.77862797, -0.24453731,
				 -0.081326402, -0.28641037, -0.80288183, -1.5300535, -2.3421433,
				 -3.1111515, -3.7291447, -4.1254153, -4.2752705, -4.1991960,
				 -3.9533703, -3.6144225, -3.2624765, -2.9666193, -2.7759716,
				 -2.7177631, -2.8016964, -3.0279907, -3.3953424, -3.9049863,
				 -4.5581522, -5.3462628, -6.2357102, -7.1513277, -7.9640993,
				 -8.4887218, -8.4951843, -7.7357237, -5.9849006, -3.0868999,
				 0.99863596, 6.1621200, 12.129893, 18.476522, 24.664983, 30.110841,
				 34.261380, 36.676978, 37.100527, 35.501970, 32.088861, 27.279703,
				 21.643506, 15.815268, 10.401693, 5.8934049, 2.5987598, 0.61038415,
				 -0.19060605, -0.094782995, 0.50237820, 1.1808351, 1.5743942,
				 1.4327601, 0.65659545, -0.69923960, -2.4525867, -4.3434612,
				 -6.0944681, -7.4690679, -8.3159303, -8.5913405, -8.3568427,
				 -7.7548257, -6.9693566, -6.1822844, -5.5349505, -5.1037966,
				 -4.8943335, -4.8532729, -4.8942674, -4.9296390, -4.8994057,
				 -4.7900175, -4.6381619, -4.5190328, -4.5225670, -4.7243125,
				 -5.1590438, -5.8046017, -6.5808816, -7.3650238, -8.0196323,
				 -8.4272951, -8.5227005, -8.3137637, -7.8854408, -7.3838279,
				 -6.9828457, -6.8402025, -7.0523653, -7.6191800, -8.4272886,
				 -9.2578252, -9.8187972, -9.7971470, -8.9209350, -7.0194056,
				 -4.0685292, -0.21205158, 4.2473370, 8.8863020, 13.222400, 16.787973,
				 19.203025, 20.233826, 19.826761, 18.111665, 15.374720, 12.006629,
				 8.4361812, 5.0616349, 2.1921885, 0.0092914624, -1.4466634,
				 -2.2637822, -2.6245792, -2.7583555, -2.8899233, -3.1944025,
				 -3.7658266, -4.6040232, -5.6204283, -6.6599553, -7.5334329,
				 -8.0538433, -8.0697312, -7.4904830, -6.3002775, -4.5598412,
				 -2.3972226, 0.0097310959, 2.4557968, 4.7323013, 6.6496640, 8.0564227,
				 8.8533549, 9.0018876, 8.5263666, 7.5100171, 6.0847044, 4.4150460,
				 2.6780847, 1.0405633, -0.36334112, -1.4504139, -2.1995037,
				 -2.6526043, -2.9046411, -3.0827043, -3.3177008, -3.7133434,
				 -4.3185703, -5.1094892, -5.9856130, -6.7826289, -7.3006450,
				 -7.3434525, -6.7615704, -5.4903820, -3.5749745, -1.1754186,
				 1.4501379, 3.9827359, 6.0940551, 7.5025729, 8.0234888, 7.6022503,
				 6.3244792, 4.3997092, 2.1216762, -0.18717590, -2.2358625, -3.8174218,
				 -4.8430187, -5.3505103, -5.4857684, -5.4608855, -5.4984306,
				 -5.7740459, -6.3701669, -7.2513553, -8.2671022, -9.1819743,
				 -9.7269166, -9.6607403, -8.8283666, -7.2028558, -4.9015651,
				 -2.1722579, 0.64853763, 3.1965601, 5.1475649, 6.2775221, 6.5024937,
				 5.8897199, 4.6376371, 3.0292856, 1.3693871, -0.081017760, -1.1578851,
				 -1.8235365, -2.1654060, -2.3642505, -2.6387359, -3.1787201,
				 -4.0823595, -5.3117129, -6.6778833, -7.8606476, -8.4602567,
				 -8.0721698, -6.3703747, -3.1827279, 1.4571041, 7.2922253, 13.864173,
				 20.570731, 26.750052, 31.776304, 35.150012, 36.567512, 35.958092,
				 33.483600, 29.502269, 24.504800, 19.035285, 13.611363, 8.6570877,
				 4.4585424, 1.1471861, -1.2896253, -2.9764403, -4.1046815, -4.8848989,
				 -5.5055348, -6.1047915, -6.7585470, -7.4834969, -8.2516346,
				 -9.0104253, -9.7028073, -10.282340, -10.720961, -11.009254,
				 -11.151265, -11.157135, -11.036982, -10.798551, -10.449505,
				 -10.003372, -9.4866393, -8.9437307, -8.4368614, -8.0390231,
				 -7.8202443, -7.8293666, -8.0752866, -8.5124675, -9.0352342,
				 -9.4839240, -9.6636227, -9.3734877, -8.4421194, -6.7627065,
				 -4.3211364, -1.2111222, 2.3674821, 6.1280267, 9.7316604, 12.831885,
				 15.121856, 16.376635, 16.483457, 15.455210, 13.425311, 10.625365,
				 7.3498882, 3.9143321, 0.61345134, -2.3134927, -4.7058105, -6.4902495,
				 -7.6757652, -8.3366876, -8.5884725, -8.5610125, -8.3743051,
				 -8.1202669, -7.8529301, -7.5875041, -7.3071799, -6.9753663,
				 -6.5504242, -5.9999348, -5.3120055, -4.5019143, -3.6133287,
				 -2.7142276, -1.8883812, -1.2237400, -0.79935115, -0.67249189,
				 -0.86763264, -1.3686680, -2.1155841, -3.0063720, -3.9045260,
				 -4.6518815, -5.0858634, -5.0595136, -4.4620309, -3.2371368,
				 -1.3964934, 0.97426415, 3.7185341, 6.6212850, 9.4302263, 11.883323,
				 13.739213, 14.806424, 14.967214, 14.192540, 12.545865, 10.175200,
				 7.2945621, 4.1576436, 1.0276443, -1.8523099, -4.2840034, -6.1310260,
				 -7.3259976, -7.8680819, -7.8123459, -7.2537386, -6.3090896,
				 -5.1004743, -3.7426055, -2.3357593, -0.96437098, 0.29985796,
				 1.3924694, 2.2509485, 2.8120343, 3.0133548, 2.7999856, 2.1352448,
				 1.0138383, -0.52539534, -2.3902396, -4.4321949, -6.4519204,
				 -8.2137343, -9.4683561, -9.9815515, -9.5651036, -8.1057851,
				 -5.5879096, -2.1056298, 2.1376281, 6.8435095, 11.645854, 16.147716,
				 19.963877, 22.763486, 24.307256, 24.474261, 23.274677, 20.846755,
				 17.438467, 13.376409, 9.0263509, 4.7509003, 0.87006725, -2.3701067,
				 -4.8170691, -6.4175991, -7.2103197, -7.3072713, -6.8684833,
				 -6.0742820, -5.0999962, -4.0968081, -3.1809527, -2.4315970,
				 -1.8959303, -1.5986154, -1.5520780, -1.7642682, -2.2414692,
				 -2.9852407, -3.9843186, -5.2038605, -6.5754529, -7.9915207,
				 -9.3071269, -10.350704, -10.943330, -10.924125, -10.177673,
				 -8.6584526, -6.4072861, -3.5559326, -0.31788374, 3.0341257,
				 6.2009055, 8.8949088, 10.877539, 11.989579, 12.169822, 11.459066,
				 9.9892567, 7.9601634, 5.6081135, 3.1724619, 0.86555255, -1.1491421,
				 -2.7679453, -3.9465645, -4.6902326, -5.0380197, -5.0457540,
				 -4.7717599, -4.2684340, -3.5808404, -2.7514724, -1.8285754,
				 -0.87439593, 0.030353904, 0.78860459, 1.2966938, 1.4607755,
				 1.2168752, 0.55019845, -0.49113544, -1.7920847, -3.1788527,
				 -4.4392897, -5.3529954, -5.7263545, -5.4263630, -4.4069186,
				 -2.7223325, -0.52500929, 1.9529077, 4.4311531, 6.6209664, 8.2659839,
				 9.1774504, 9.2579362, 8.5099705, 7.0288628, 4.9818977, 2.5784701,
				 0.037106640, -2.4445423, -4.7117439, -6.6637941, -8.2537425,
				 -9.4786930, -10.364455, -10.949138, -11.269999, -11.356504,
				 -11.230639, -10.913294, -10.433796, -9.8386403, -9.1955498,
				 -8.5900757, -8.1139229, -7.8464541, -7.8329598, -8.0646972,
				 -8.4660321, -8.8931096, -9.1464625, -8.9972050, -8.2235268,
				 -6.6517156, -4.1944406, -0.87891070, 3.1411454, 7.5931391, 12.112999,
				 16.288807, 19.714237, 22.043421, 23.038504, 22.602258, 20.790702,
				 17.804008, 13.957612, 9.6386219, 5.2547756, 1.1839763, -2.2681768,
				 -4.8973554, -6.6131334, -7.4353716, -7.4770326, -6.9178881,
				 -5.9743113, -4.8699980, -3.8112552, -2.9688122, -2.4664022,
				 -2.3750488, -2.7113396, -3.4380658, -4.4663200, -5.6591703,
				 -6.8379885, -7.7930343, -8.2997226, -8.1410787, -7.1353558,
				 -5.1659999, -2.2095621, 1.6437651, 6.1821992, 11.080930, 15.928752,
				 20.271238, 23.665718, 25.740594, 26.249927, 25.114149, 22.439396,
				 18.510999, 13.760841, 8.7125842, 3.9126835, -0.14237302, -3.0712987,
				 -4.6556019, -4.8623329, -3.8389629, -1.8820408, 0.61442366,
				 3.2217392, 5.5399044, 7.2514507, 8.1578613, 8.1941745, 7.4214610,
				 6.0007224, 4.1546799, 2.1254078, 0.13562874, -1.6401225, -3.0909149,
				 -4.1709359, -4.8894486, -5.2935439, -5.4488717, -5.4231202,
				 -5.2755514, -5.0538163, -4.7971155, -4.5430589, -4.3347143,
				 -4.2244856, -4.2725553, -4.5393485, -5.0733779, -5.8974084,
				 -6.9967297, -8.3132010, -9.7476441, -11.171328, -12.445143,
				 -13.443098, -14.075463, -14.306549, -14.162931, -13.729646,
				 -13.134315, -12.521636, -12.022837, -11.725901, -11.652512,
				 -11.746586, -11.877194, -11.856053, -11.467015, -10.502787,
				 -8.8027000, -6.2851358, -2.9691176, 1.0185709, 5.4519554, 10.028811,
				 14.404454, 18.231371, 21.199054, 23.068725, 23.698702, 23.057731,
				 21.225386, 18.380308, 14.778399, 10.723990, 6.5374415, 2.5226858,
				 -1.0620394, -4.0274799, -6.2684693, -7.7693507, -8.5990343,
				 -8.8959654, -8.8443597, -8.6441959, -8.4785121, -8.4823363,
				 -8.7178765, -9.1602364, -9.6968057, -10.141665, -10.264040,
				 -9.8273814, -8.6335066, -6.5647824, -3.6170143, 0.083369703,
				 4.2820903, 8.6208890, 12.680114, 16.034274, 18.312264, 19.252756,
				 18.745611, 16.852197, 13.800765, 9.9570861, 5.7746466, 1.7320517,
				 -1.7325972, -4.2801824, -5.7130337, -5.9952800, -5.2487177,
				 -3.7258232, -1.7651050, 0.26347118, 2.0141243, 3.2135017, 3.6948043,
				 3.4137673, 2.4455795, 0.96478673, -0.78743110, -2.5428157,
				 -4.0462580, -5.0911291, -5.5454499, -5.3663347, -4.6019148,
				 -3.3815268, -1.8961797, -0.37213362, 0.95913101, 1.8911373,
				 2.2695003, 2.0128358, 1.1249287, -0.30367856, -2.1055084, -4.0569284,
				 -5.9079517, -7.4176088, -8.3901664, -8.7067185, -8.3467148,
				 -7.3949783, -6.0316941, -4.5055002, -3.0927967, -2.0491595,
				 -1.5607522, -1.7043795, -2.4240422, -3.5295333, -4.7190576,
				 -5.6236495, -5.8670578, -5.1315556, -3.2184872, -0.092690400,
				 4.0977414, 9.0308927, 14.249225, 19.221055, 23.415635, 26.379628,
				 27.802365, 27.559178, 25.725836, 22.562150, 18.468075, 13.920272,
				 9.4001816, 5.3257164, 1.9975688, -0.43191397, -1.9636176, -2.7313804,
				 -2.9618892, -2.9231595, -2.8713719, -3.0053607, -3.4359519,
				 -4.1741327, -5.1383834, -6.1781180, -7.1076654, -7.7439344,
				 -7.9409704, -7.6158449, -6.7623738, -5.4515594, -3.8199169,
				 -2.0486121, -0.33737044, 1.1226260, 2.1730478, 2.7070076, 2.6801064,
				 2.1132250, 1.0873251, -0.26869615, -1.7954762, -3.3239331,
				 -4.6969418, -5.7894131, -6.5242470, -6.8818688, -6.9016333,
				 -6.6743161, -6.3261666, -5.9964196, -5.8115174, -5.8602763,
				 -6.1745751, -6.7196446, -7.3966671, -8.0582978, -8.5352437,
				 -8.6696477, -8.3492393, -7.5354845, -6.2795503, -4.7218186,
				 -3.0736470, -1.5835668, -0.49342670, 0.0075569950, -0.17811109,
				 -1.0321636, -2.4172693, -4.0963549, -5.7720088, -7.1392700,
				 -7.9420845, -8.0225005, -7.3526155, -6.0422800, -4.3200942,
				 -2.4904396, -0.87414807, 0.25606993, 0.73288593, 0.53276723,
				 -0.21266877, -1.2355361, -2.1781828, -2.6592772, -2.3474623,
				 -1.0288251, 1.3446906, 4.6354440, 8.5331371, 12.598969, 16.333365,
				 19.255365, 20.979588, 21.277209, 20.110312, 17.633990, 14.166392,
				 10.132778, 5.9941161, 2.1733552, -1.0075000, -3.3687194, -4.8861066,
				 -5.6750739, -5.9498496, -5.9666099, -5.9620437, -6.0993698,
				 -6.4320889, -6.8921540, -7.3044854, -7.4247481, -6.9929623,
				 -5.7926169, -3.7039878, -0.74146810, 2.9323444, 7.0188430, 11.120215,
				 14.794794, 17.621635, 19.263382, 19.516804, 18.342582, 15.869514,
				 12.372756, 8.2301571, 3.8644473, -0.31867510, -3.9860078, -6.9166857,
				 -9.0202232, -10.330658, -10.979109, -11.151004, -11.036966,
				 -10.787396, -10.479841, -10.105636, -9.5783084, -8.7617925,
				 -7.5123899, -5.7254131, -3.3762954, -0.54680864, 2.5701506,
				 5.6895117, 8.4741742, 10.589319, 11.760662, 11.825196, 10.764081,
				 8.7105221, 5.9303266, 2.7781911, -0.36225456, -3.1432022, -5.3095925,
				 -6.7369769, -7.4428069, -7.5690039, -7.3395696, -7.0022312,
				 -6.7665565, -6.7518690, -6.9563802, -7.2545930, -7.4240808,
				 -7.1964424, -6.3219078, -4.6338759, -2.0993254, 1.1563114, 4.8560970,
				 8.6100862, 11.978469, 14.547179, 16.000970, 16.179300, 15.103702,
				 12.971161, 10.114903, 6.9407526, 3.8521067, 1.1788371, -0.87564941,
				 -2.2589797, -3.0619792, -3.4813619, -3.7621171, -4.1329327,
				 -4.7492060, -5.6561876, -6.7800613, -7.9483431, -8.9343236,
				 -9.5148627, -9.5278860, -8.9160657, -7.7463742, -6.2007349,
				 -4.5396129, -3.0466022, -1.9664612, -1.4506386, -1.5227470,
				 -2.0720344, -2.8766567, -3.6518734, -4.1126699, -4.0370262,
				 -3.3158352, -1.9783914, -0.18776108, 1.7929878, 3.6562695, 5.1149604,
				 5.9625943, 6.1156047, 5.6284858, 4.6787884, 3.5255598, 2.4506822,
				 1.6962542, 1.4118936, 1.6234655, 2.2298261, 3.0278397, 3.7596139,
				 4.1710244, 4.0682527, 3.3597388, 2.0744679, 0.35304061, -1.5857644,
				 -3.4946712, -5.1519749, -6.4073268, -7.2086776, -7.6043704,
				 -7.7202825, -7.7178279, -7.7431791, -7.8801997, -8.1188191,
				 -8.3470841, -8.3696973, -7.9497253, -6.8647243, -4.9650184,
				 -2.2210742, 1.2509738, 5.1920489, 9.2315427, 12.941767, 15.904961,
				 17.779791, 18.354510, 17.576880, 15.555923, 12.536407, 8.8524368,
				 4.8704202, 0.93318209, -2.6840388, -5.7983319, -8.3219572,
				 -10.245697, -11.609299, -12.467999, -12.864241, -12.811680,
				 -12.294881, -11.283788, -9.7580816, -7.7339303, -5.2849619,
				 -2.5506944, 0.27122368, 2.9485642, 5.2494567, 6.9813274, 8.0252000,
				 8.3575026, 8.0541649, 7.2756671, 6.2359176, 5.1614184, 4.2492948,
				 3.6329528, 3.3623497, 3.4025465, 3.6501327, 3.9632023, 4.1977077,
				 4.2418422, 4.0408419, 3.6070321, 3.0134825, 2.3734356, 1.8108626,
				 1.4293575, 1.2867179, 1.3809962, 1.6509436, 1.9903241, 2.2723586,
				 2.3783155, 2.2235039, 1.7747663, 1.0557843, 0.13952654, -0.86975055,
				 -1.8601328, -2.7353195, -3.4318665, -3.9264301, -4.2316868,
				 -4.3825278, -4.4166242, -4.3549098, -4.1875890, -3.8699341,
				 -3.3297104, -2.4851463, -1.2696384, 0.34248538, 2.3153925, 4.5417278,
				 6.8463812, 9.0043625, 10.770075, 11.912815, 12.251974, 11.685437,
				 10.206099, 7.9038101, 4.9529858, 1.5888147, -1.9230433, -5.3175681,
				 -8.3570618, -10.849701, -12.658454, -13.701163, -13.944424,
				 -13.394888, -12.091526, -10.101334, -7.5192648, -4.4713103,
				 -1.1181108, 2.3442428, 5.6893236, 8.6732763, 11.055490, 12.624859,
				 13.227621, 12.791678, 11.342328, 9.0054941, 5.9966180, 2.5960367,
				 -0.88577357, -4.1476640, -6.9355712, -9.0723784, -10.475117,
				 -11.157078, -11.215157, -10.805401, -10.111693, -9.3134813,
				 -8.5582775, -7.9433212, -7.5088084, -7.2426723, -7.0947152,
				 -6.9962741, -6.8808597, -6.7014248, -6.4409726, -6.1148316,
				 -5.7647420, -5.4465327, -5.2143242, -5.1046822, -5.1239483,
				 -5.2411860, -5.3880243, -5.4654006, -5.3560503, -4.9407402,
				 -4.1157996, -2.8094644, -0.99486877, 1.3019357, 3.9998767, 6.9669278,
				 10.029955, 12.989398, 15.637380, 17.777492, 19.244284, 19.920374,
				 19.749262, 18.742218, 16.978210, 14.596614, 11.783343, 8.7519409,
				 5.7219273, 2.8971256, 0.44675652, -1.5083508, -2.9028748, -3.7269665,
				 -4.0197257, -3.8578382, -3.3416652, -2.5812513, -1.6843962,
				 -0.74821746, 0.14532221, 0.93039804, 1.5560202, 1.9839855, 2.1875041,
				 2.1513958, 1.8739628, 1.3698358, 0.67247511, -0.16521486, -1.0734424,
				 -1.9718514, -2.7792266, -3.4251884, -3.8617055, -4.0719394,
				 -4.0742152, -3.9197637, -3.6841517, -3.4537247, -3.3096004,
				 -3.3124723, -3.4914922, -3.8397592, -4.3175691, -4.8628611,
				 -5.4066148, -5.8896965, -6.2771455, -6.5662830, -6.7862863,
				 -6.9887463, -7.2308301, -7.5545365, -7.9667193, -8.4247718,
				 -8.8320094, -9.0449895, -8.8926210, -8.2044155, -6.8431506,
				 -4.7359973, -1.8980910, 1.5563559, 5.4181978, 9.4028605, 13.182565,
				 16.426355, 18.841658, 20.210907, 20.417706, 19.458964, 17.441840,
				 14.566877, 11.100791, 7.3436757, 3.5957567, 0.12822024, -2.8386371,
				 -5.1483879, -6.7127024, -7.5082792, -7.5687052, -6.9736124,
				 -5.8371023, -4.2966789, -2.5031356, -0.61121173, 1.2294407,
				 2.8833396, 4.2382424, 5.2135806, 5.7671518, 5.8987397, 5.6495006,
				 5.0965140, 4.3427397, 3.5035544, 2.6918238, 2.0038825, 1.5087185,
				 1.2420744, 1.2061994, 1.3748336, 1.7019385, 2.1319745, 2.6093215,
				 3.0848195, 3.5182638, 3.8768305, 4.1305488, 4.2467837, 4.1860407,
				 3.9011316, 3.3409148, 2.4586140, 1.2234231, -0.36697128, -2.2758934,
				 -4.4220678, -6.6809949, -8.8941218, -10.885064, -12.480653,
				 -13.533499, -13.942322, -13.666595, -12.733018, -11.232815,
				 -9.3104408, -7.1457613, -4.9327490, -2.8581003, -1.0828615,
				 0.27072430, 1.1261383, 1.4535056, 1.2657434, 0.61173838, -0.43160150,
				 -1.7669790, -3.2832910, -4.8611911, -6.3782544, -7.7146113,
				 -8.7597482, -9.4206407, -9.6306517, -9.3579190, -8.6114962,
				 -7.4434854, -5.9458498, -4.2414846, -2.4702491, -0.77178224,
				 0.73224246, 1.9534348, 2.8468777, 3.4143549, 3.7000743, 3.7797798,
				 3.7454181, 3.6883580, 3.6843503, 3.7829420, 4.0030094, 4.3347002,
				 4.7466855, 5.1965317, 5.6414582, 6.0468577, 6.3906949, 6.6630650,
				 6.8615117, 6.9838468, 7.0209022, 6.9517083, 6.7430134, 6.3539627,
				 5.7454062, 4.8920254, 3.7945612, 2.4891258, 1.0509726, -0.40888794,
				 -1.7544167, -2.8412831, -3.5369834, -3.7409323, -3.4010192,
				 -2.5237206, -1.1760185, 0.52114746, 2.4066984, 4.2981345, 6.0121329,
				 7.3839733, 8.2832773, 8.6244956, 8.3716770, 7.5380587, 6.1817038,
				 4.3986636, 2.3149781, 0.078336980, -2.1503692, -4.2058853,
				 -5.9283189, -7.1741267, -7.8282389, -7.8165075, -7.1169478,
				 -5.7677544, -3.8699932, -1.5833027, 0.88613620, 3.3048350, 5.4365439,
				 7.0690443, 8.0394128, 8.2539247, 7.6994160, 6.4441831, 4.6281646,
				 2.4438838, 0.11112239, -2.1507464, -4.1485963, -5.7358818,
				 -6.8249142, -7.3900331, -7.4622120, -7.1169941, -6.4585361,
				 -5.6028143, -4.6627145, -3.7369187, -2.9034287, -2.2174937,
				 -1.7128605, -1.4048134, -1.2934641, -1.3661510, -1.5984528,
				 -1.9540402, -2.3841727, -2.8279471, -3.2143582, -3.4668421,
				 -3.5103615, -3.2803924, -2.7325748, -1.8514257, -0.65647431,
				 0.79552578, 2.4130797, 4.0771795, 5.6525855, 7.0018521, 8.0003904,
				 8.5507350, 8.5943102, 8.1193363, 7.1639836, 5.8144091, 4.1978127,
				 2.4711042, 0.80615013, -0.62712021, -1.6782301, -2.2326098,
				 -2.2252476, -1.6498807, -0.56234521, 0.92280278, 2.6425774,
				 4.4020878, 5.9957134, 7.2305370, 7.9490996, 8.0483885, 7.4923079,
				 6.3156904, 4.6191024, 2.5550795, 0.30777569, -1.9309462, -3.9854027,
				 -5.7164462, -7.0354575, -7.9103123, -8.3630819, -8.4606346,
				 -8.3004008, -7.9941568, -7.6526590, -7.3733640, -7.2324729,
				 -7.2813794, -7.5465861, -8.0315094, -8.7184806, -9.5696821,
				 -10.526593, -11.508536, -12.411803, -13.111316, -13.466696,
				 -13.333835, -12.581862, -11.113829, -8.8880771, -5.9363000,
				 -2.3741238, 1.5992139, 5.7139556, 9.6553627, 13.099014, 15.751090,
				 17.387506, 17.885497, 17.242119, 15.576156, 13.112608, 10.151915,
				 7.0287210, 4.0667846, 1.5372815, -0.37295211, -1.5785436, -2.0960742,
				 -2.0289079, -1.5400313, -0.81848176, -0.045723786, 0.63213104,
				 1.1219155, 1.3887585, 1.4496782, 1.3581580, 1.1839761, 0.99310874,
				 0.83201765, 0.71925655, 0.64542377, 0.58049765, 0.48595817,
				 0.32816362, 0.089389345, -0.22628136, -0.59360974, -0.97309465,
				 -1.3222206, -1.6080704, -1.8179287, -1.9649253, -2.0868824,
				 -2.2380998, -2.4754706, -2.8417134, -3.3493252, -3.9689268,
				 -4.6249691, -5.2004354, -5.5504779, -5.5232003, -4.9843815,
				 -3.8420843, -2.0669712, 0.29524478, 3.1215638, 6.2204293, 9.3504703,
				 12.246786, 14.651200, 16.342390, 17.161864, 17.032443, 15.966959,
				 14.066259, 11.506974, 8.5208217, 5.3681573, 2.3091453, -0.42392919,
				 -2.6499287, -4.2550696, -5.2003653, -5.5197361, -5.3094044,
				 -4.7102996, -3.8860774, -2.9998938, -2.1931769, -1.5692813,
				 -1.1841259, -1.0448030, -1.1158811, -1.3318937, -1.6135344,
				 -1.8845280, -2.0861355, -2.1867877, -2.1853370, -2.1076903,
				 -1.9978895, -1.9057888, -1.8741108, -1.9277228, -2.0674332,
				 -2.2695802, -2.4913888, -2.6807767, -2.7882883, -2.7783518,
				 -2.6371999, -2.3755568, -2.0254074, -1.6315645, -1.2400199,
				 -0.88591007, -0.58413461, -0.32516322, -0.077445449, 0.20367885,
				 0.56224621, 1.0260730, 1.5929362, 2.2220688, 2.8336001, 3.3170230,
				 3.5478831, 3.4100511, 2.8195612, 1.7453739, 0.22272864, -1.6440522,
				 -3.6905695, -5.7122305, -7.4920008, -8.8314720, -9.5799921,
				 -9.6568928, -9.0630649, -7.8800372, -6.2569331, -4.3877749,
				 -2.4831852, -0.74131162, 0.67734119, 1.6674700, 2.1868145, 2.2540236,
				 1.9380268, 1.3416748, 0.58304755, -0.22226584, -0.97531847,
				 -1.6029033, -2.0613314, -2.3356725, -2.4358977, -2.3915338,
				 -2.2461550, -2.0524401, -1.8678412, -1.7503609, -1.7537083,
				 -1.9212718, -2.2788781, -2.8270448, -3.5341602, -4.3324954,
				 -5.1189795, -5.7621593, -6.1157519, -6.0378699, -5.4136076,
				 -4.1775556, -2.3322309, 0.041424403, 2.7843771, 5.6719528, 8.4376673,
				 10.805650, 12.527172, 13.415781, 13.375494, 12.417332, 10.661271,
				 8.3229008, 5.6865269, 3.0686094, 0.77690738, -0.92867466, -1.8679302,
				 -1.9605430, -1.2321991, 0.19359558, 2.1160841, 4.2866056, 6.4420955,
				 8.3378813, 9.7754425, 10.621925, 10.819680, 10.385648, 9.4017953,
				 7.9988293, 6.3359808, 4.5797283, 2.8840548, 1.3742591, 0.13565453,
				 -0.79220792, -1.4157667, -1.7825955, -1.9723027, -2.0842837,
				 -2.2236211, -2.4865568, -2.9470355, -3.6457935, -4.5833227,
				 -5.7177210, -6.9679631, -8.2224894, -9.3523086, -10.227121,
				 -10.732433, -10.785350, -10.346785, -9.4282188, -8.0919027,
				 -6.4443037, -4.6236190, -2.7830809, -1.0724095, 0.37996028,
				 1.4814050, 2.1844654, 2.4899267, 2.4435025, 2.1269419, 1.6451370,
				 1.1112689, 0.63213674, 0.29558771, 0.16146963, 0.25688766,
				 0.57587680, 1.0830233, 1.7201534, 2.4149906, 3.0906501, 3.6749403,
				 4.1086186, 4.3519384, 4.3889923, 4.2294950, 3.9077729, 3.4788838,
				 3.0119984, 2.5814701, 2.2563790, 2.0897019, 2.1085689, 2.3072038,
				 2.6440425, 3.0441240, 3.4071648, 3.6208391, 3.5778230, 3.1943020,
				 2.4270740, 1.2862426, -0.15911044, -1.7839870, -3.4196748,
				 -4.8739984, -5.9570755, -6.5089172, -6.4246367, -5.6731555,
				 -4.3061261, -2.4552215, -0.31775036, 1.8675716, 3.8512395, 5.4054427,
				 6.3535526, 6.5922330, 6.1029862, 4.9516699, 3.2764449, 1.2664215,
				 -0.86536311, -2.9102924, -4.6895542, -6.0730386, -6.9895404,
				 -7.4276530, -7.4283075, -7.0711786, -6.4579323, -5.6954353,
				 -4.8816121, -4.0957593, -3.3940283, -2.8096983, -2.3570169,
				 -2.0369106, -1.8428419, -1.7654391, -1.7951393, -1.9227797,
				 -2.1386625, -2.4309750, -2.7844921, -3.1802418, -3.5963739,
				 -4.0099700, -4.3991390, -4.7445662, -5.0298041, -5.2399720,
				 -5.3590878, -5.3668272, -5.2359205, -4.9315167, -4.4135896,
				 -3.6428402, -2.5896841, -1.2449814, 0.36960084, 2.1940960, 4.1279886,
				 6.0348940, 7.7546817, 9.1221118, 9.9896097, 10.250666, 9.8597914,
				 8.8451379, 7.3108568, 5.4278587, 3.4136150, 1.5036277, -0.081184211,
				 -1.1660042, -1.6473377, -1.5076355, -0.81673546, 0.28025200,
				 1.5866755, 2.8857570, 3.9744524, 4.6941502, 4.9532195, 4.7379177,
				 4.1102486, 3.1936473, 2.1494235, 1.1483638, 0.34252172, -0.15806745,
				 -0.30012992, -0.091436264, 0.40535528, 1.0874666, 1.8321964,
				 2.5186822, 3.0474253, 3.3545149, 3.4186976, 3.2608592, 2.9368226,
				 2.5253880, 2.1140852, 1.7851416, 1.6037573, 1.6100643, 1.8153303,
				 2.2022152, 2.7283544, 3.3322710, 3.9406114, 4.4758679, 4.8639936,
				 5.0415206, 4.9618884, 4.6006585, 3.9591710, 3.0660773, 1.9761523,
				 0.76594195, -0.47384123, -1.6487146, -2.6725308, -3.4796389,
				 -4.0351003, -4.3409604, -4.4369965, -4.3951797, -4.3081804,
				 -4.2734193, -4.3751783, -4.6679044, -5.1639031, -5.8280477,
				 -6.5810053, -7.3109656, -7.8922423, -8.2077098, -8.1711311,
				 -7.7452300, -6.9519450, -5.8725847, -4.6373735, -3.4058088,
				 -2.3409835, -1.5822228, -1.2208142, -1.2831689, -1.7245210,
				 -2.4344596, -3.2535379, -3.9982724, -4.4904010, -4.5855472,
				 -4.1965780, -3.3078864, -1.9784025, -0.33303948, 1.4558236,
				 3.1936666, 4.6918367, 5.7938721, 6.3962532, 6.4607685, 6.0171400,
				 5.1561132, 4.0146263, 2.7557114, 1.5463203, 0.53627208, -0.15894714,
				 -0.46961596, -0.37651461, 0.090961699, 0.86238550, 1.8380310,
				 2.9028836, 3.9409990, 4.8484748, 5.5436629, 5.9737128, 6.1170488,
				 5.9818720, 5.6012230, 5.0254993, 4.3135766, 3.5238098, 2.7061745,
				 1.8966353, 1.1145106, 0.36315289, -0.36625159, -1.0884881,
				 -1.8168878, -2.5561127, -3.2966330, -4.0121601, -4.6608177,
				 -5.1901791, -5.5455528, -5.6802123, -5.5657698, -5.2006943,
				 -4.6151402, -3.8707753, -3.0551060, -2.2707602, -1.6211340,
				 -1.1945638, -1.0495971, -1.2039158, -1.6289902, -2.2516869,
				 -2.9629422, -3.6324430, -4.1272276, -4.3314037, -4.1639259,
				 -3.5916131, -2.6353014, -1.3680891, 0.094118452, 1.6083418,
				 3.0241212, 4.2039519, 5.0414290, 5.4743563, 5.4909066, 5.1280487,
				 4.4627081, 3.5972915, 2.6420979, 1.6976140, 0.83967393, 0.10995063,
				 -0.48667382, -0.97750051, -1.4110701, -1.8419114, -2.3138145,
				 -2.8447996, -3.4167549, -3.9720022, -4.4179104, -4.6392954,
				 -4.5169463, -3.9494270, -2.8745463, -1.2867115, 0.75316272,
				 3.1174642, 5.6221886, 8.0468119, 10.161167, 11.755473, 12.668982,
				 12.812728, 12.182670, 10.860942, 9.0047770, 6.8246644, 4.5550118,
				 2.4218554, 0.61261772, -0.74742338, -1.6084258, -1.9972268,
				 -2.0067440, -1.7756911, -1.4623180, -1.2167674, -1.1567071,
				 -1.3501549, -1.8080036, -2.4869542, -3.3016992, -4.1436181,
				 -4.9022161, -5.4852377, -5.8338435, -5.9303340, -5.7974239,
				 -5.4897011, -5.0793503, -4.6392112, -4.2266147, -3.8711534,
				 -3.5686695, -3.2824888, -2.9515212, -2.5035752, -1.8713092,
				 -1.0078265, 0.10094249, 1.4292821, 2.9113678, 4.4476180, 5.9166415,
				 7.1908188, 8.1530374, 8.7120226, 8.8140273, 8.4493020, 7.6526197,
				 6.4980301, 5.0888368, 3.5443984, 1.9857012, 0.52169862, -0.76181645,
				 -1.8094841, -2.5990746, -3.1395476, -3.4650508, -3.6259747,
				 -3.6785539, -3.6746730, -3.6534623, -3.6359709, -3.6237074,
				 -3.6012030, -3.5420729, -3.4174277, -3.2050284, -2.8973768,
				 -2.5070426, -2.0679587, -1.6321162, -1.2619474, -1.0195568,
				 -0.95467284, -1.0936017, -1.4314571, -1.9294866, -2.5184690,
				 -3.1080554, -3.6007639, -3.9083468, -3.9676325, -3.7528496,
				 -3.2819270, -2.6152568, -1.8467551, -1.0884994, -0.45149218,
				 -0.025933047, 0.13539638, 0.027495821, -0.30480595, -0.77368102,
				 -1.2629283, -1.6478439, -1.8167770, -1.6904709, -1.2356901,
				 -0.47070180, 0.53828262, 1.6889007, 2.8603225, 3.9334130
				}));
		
		try {
			AxesMetadata theRAxis = MetadataFactory.createMetadata(AxesMetadata.class, 1);
			theRAxis.setAxis(0, r);
			ceria.setMetadata(theRAxis);
		} catch (MetadataException e) {
			// do nothing
		}
		
		return ceria;
	}
	
	private XPDFComponentForm getForm() {
		if (component.getForm() == null)
			component.setForm(new XPDFComponentForm());
		return component.getForm();
	}
	
}
