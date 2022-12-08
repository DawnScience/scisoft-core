/*
 * Copyright 2021 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.utils;


// From com.github
import com.github.tschoonj.xraylib.Xraylib;
import com.github.tschoonj.xraylib.XraylibException;

import java.util.Map;

// From org.eclipse
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.IFindInTree;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.january.dataset.IDataset;


// From uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;


//@author Tim Snow


public final class AttenuationCalculationUtils {
	
	
	/**
	* This function calculates the transmission factor arising from a beam of radiation
	* interacting with some form of matter in it's path.
	* 
	* @param  beamEnergy               The incident beam's energy - in keV
	* @param  density                  The physical density of the matter - in g/cm^3
	* @param  molecularFormla          The chemical composition of the matter - as a molecular formula
	* @param  pathLength               The length of the path that the beam will traverse through the matter - in cm
	* @return                          The transmission factor arising from this interaction
	* @throws IllegalArgumentException If any of the input parameters are found to be invalid (e.g. less than zero or infinity, or an invalid chemical formula) an IllegalArgumentException will be raised
	*/
	public static double getTransmissionFactor(double beamEnergy, String molecularFormula, double density, double pathLength) throws IllegalArgumentException {
		// Let's check that the information passed on is usable
		if (beamEnergy <= 0.0 || Double.isNaN(beamEnergy)) throw new IllegalArgumentException("Invalid beam energy!");
		if (density <= 0.0 || Double.isNaN(density)) throw new IllegalArgumentException("Invalid material density!");
		if (pathLength <= 0.0 || Double.isNaN(pathLength)) throw new IllegalArgumentException("Invalid attenuation distance!");
		
		try {
			Xraylib.CompoundParser(molecularFormula);
		}
		catch (XraylibException error) {
			throw new IllegalArgumentException("Compound is not a valid chemical formula");
		}
		
		// Now let's work out the attenuation factor
		return Math.exp(-Xraylib.CS_Total_CP(molecularFormula, beamEnergy) * pathLength * density);
	}
	
	
	/**
	* This function attempts to extract a beam energy from an Eclipse January Dataset.
	* 
	* @param  inputDataset             The input dataset which contains the beam energy as metadata
	* @return                          The beam energy - in keV
	* @throws IllegalArgumentException If the beam energy cannot be found, or is less than zero, an IllegalArgumentException will be raised
	*/
	public static double getEnergyFromDatasetMetadata(IDataset inputDataset) throws IllegalArgumentException {
		// Let's check that we've got the correct metadata to start with
		IDiffractionMetadata diffractionMetadata = inputDataset.getFirstMetadata(IDiffractionMetadata.class);
		
		// Now let's go and find an energy to use
		double energy = Double.NaN;
		
		if (diffractionMetadata != null) {
			energy = diffractionMetadata.getDiffractionCrystalEnvironment().getEnergy();
		} else {
			energy = findEnergyinkeV(inputDataset.getFirstMetadata(SliceFromSeriesMetadata.class).getFilePath());
		}
		
		// Checking whether we found anything useful, before returning a result
		if (Double.isNaN(energy)) {
			throw new IllegalArgumentException("Cannot find energy in NeXus file or in supplied calibration information!");
		} else if (energy <= 0.00) {
			throw new IllegalArgumentException("The energy value found is meaningless.");
		} else {
			return energy;
		}
	}
	
	
	private static double findEnergyinkeV(String filePath) {
		// Let's open up our NeXus file and see what's inside
		Tree tree = null;
		IDataHolder dh;
		
		try {
//			dh = LoaderFactory.getData(filePath);
			dh = LoaderFactory.getData(filePath, true, false, true, null);
			tree = dh.getTree();
		} catch (Exception error) {
			// If it even really is a NeXus file, that is...
			return Double.NaN;
		}
		
		// Let's go searching through the tree to find information about...
		NodeLink firstMatchingNode;
		Map<String, NodeLink>  treeSearchHashmap;

		// The monochromator
		treeSearchHashmap = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), getFinder(NexusConstants.MONOCHROMATOR), true, null);
		
		// If we can find a monochromator entry, let's extract the information
		if (!treeSearchHashmap.isEmpty()) {
			firstMatchingNode = treeSearchHashmap.values().iterator().next();
			DiffractionCrystalEnvironment monochromatorDetails = new DiffractionCrystalEnvironment();
			NexusTreeUtils.parseMonochromator(tree, firstMatchingNode, monochromatorDetails);
			return monochromatorDetails.getEnergy(); 
		}
		
		// Beam properties
		treeSearchHashmap = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), getFinder(NexusConstants.BEAM), true, null);
		
		// If we can find a beam entry, let's extract the information
		if (!treeSearchHashmap.isEmpty()) {
			firstMatchingNode = treeSearchHashmap.values().iterator().next();
			DiffractionCrystalEnvironment beamDetails = new DiffractionCrystalEnvironment();
			NexusTreeUtils.parseBeam(tree, firstMatchingNode, beamDetails);
			return beamDetails.getEnergy();
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		// If this data could be anywhere else in a NeXus file, here would be a good place to do a search for it //
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// If we've made it this far and we can't find an energy to work with we return a NaN to show an invalid result
		return Double.NaN;
	}
	
	
	// Our primary tree searching class, will find classes matching a particular type and name
	private static IFindInTree getFinder(final String NXtype) {
		return new IFindInTree() {

			@Override
			public boolean found(NodeLink node) {

				Node dest = node.getDestination();

				if (dest instanceof GroupNode) {
					if (NexusTreeUtils.isNXClass(dest, NXtype)) {
						return true;
					}
				}
				return false;
			}
		};
	}


}
