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
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

/**
 * @author Timothy Spain (rkl37156), timothy.spain@diamond.ac.uk
 * @since 2015-09-11
 */
public class XPDFAbsorptionMaps {
	private Map<String, Dataset> theMaps;
	private Dataset delta;
	private Dataset gamma;
	private List<XPDFComponentForm> formList;
	private XPDFBeamData beamData;
	private Map<String, Boolean> isUpstreamMap;
	private Map<String, Boolean> isDownstreamMap;
	
	/**
	 * Creates an empty Map.
	 */
	public XPDFAbsorptionMaps() {
		theMaps = new HashMap<String, Dataset>();
		formList = new ArrayList<XPDFComponentForm>();
		isUpstreamMap = new HashMap<String, Boolean>();
		isDownstreamMap = new HashMap<String, Boolean>();
	}
	
	/**
	 * Copy constructor.
	 * @param inMaps object to be copied.
	 */
	public XPDFAbsorptionMaps(XPDFAbsorptionMaps inMaps) {
		if (this.theMaps == null) {
			this.theMaps = new HashMap<String, Dataset>();
		} else {
			this.theMaps.clear();
		}
		this.theMaps.putAll(inMaps.theMaps);
	}
	
	/**
	 * Sets an absorption correction map in the map collection. 
	 * @param iScatterer index of the scattering object in the list of XPDFTargetComponents.
	 * @param iAttenuator index of the attenuating object in the list of XPDFTargetComponents.
	 * @param inMap The Dataset holding the absorption map of the pair of objects.
	 */
	public void setAbsorptionMap(int iScatterer, int iAttenuator, Dataset inMap) {
		theMaps.put(XPDFAbsorptionMaps.stringifier(iScatterer, iAttenuator), inMap);
	}

	/**
	 * Sets absorption maps by the target component form.
	 * <p>
	 * Given a pair of XPDFComponentForm objects for the scatterer and the
	 * attenuator, this method sets the corresponding absorption correction map
	 * in the map collection.
	 * @param formScatterer
	 * 					the XPDFComponentForm of the scattering object.
	 * @param formAttenuator
	 * 					the XPDFComponentForm of the attenuating object.
	 * @param inMap
	 * 			the absorption map to be set.
	 */
	public void setAbsorptionMap(XPDFComponentForm formScatterer, XPDFComponentForm formAttenuator, Dataset inMap) {
		this.setAbsorptionMap(this.indexFromForm(formScatterer), this.indexFromForm(formAttenuator), inMap);
	}
	
	/**
	 * Calculates the absorption maps.
	 * <p>
	 * Given the stored list of target component form objects, this method 
	 * will calculate and store the full set of absorption maps.
	 */
	public void calculateAbsorptionMaps() {
		for (XPDFComponentForm formScatterer : formList) {
			for (XPDFComponentForm formAttenuator : formList) {
				this.setAbsorptionMap(formScatterer, formAttenuator,
						formScatterer.getGeom().calculateAbsorptionCorrections(
								gamma, delta, formAttenuator.getGeom(),
								formAttenuator.getAttenuationCoefficient(beamData.getBeamEnergy()), beamData,
								isUpstreamMap.get(XPDFAbsorptionMaps.stringifier(this.indexFromForm(formScatterer), this.indexFromForm(formAttenuator))),
								isDownstreamMap.get(XPDFAbsorptionMaps.stringifier(this.indexFromForm(formScatterer), this.indexFromForm(formAttenuator)))));
			}
		}
	}
	
	
	/**
	 * Gets a stored absorption map.
	 * @param iScatterer index of the scattering object in the list of XPDFTargetComponents.
	 * @param iAttenuator index of the attenuating object in the list of XPDFTargetComponents.
	 * @return the requested Dataset if it exists, else null.
	 */
	public Dataset getAbsorptionMap(int iScatterer, int iAttenuator) {
		return theMaps.get(XPDFAbsorptionMaps.stringifier(iScatterer, iAttenuator));
	}
	
	/**
	 * Gets a stored absorption map by the target component form.
	 * <p>
	 * Given a  pair of XPDFComponentForm objects for the scatterer and the
	 * attenuator, this method returns the corresponding, previously stored
	 * absorption map.
	 * @param formScatterer
	 * 					the XPDFComponentForm of the scattering object.
	 * @param formAttenuator
	 * 					the XPDFComponentForm of the attenuating object.
	 * @return the requested absorption map.
	 */
	public Dataset getAbsorptionMap(XPDFComponentForm formScatterer, XPDFComponentForm formAttenuator) {
		return this.getAbsorptionMap(this.indexFromForm(formScatterer), this.indexFromForm(formAttenuator));
	}
	
	/**
	 * Sets the horizontal scattering angle.
	 * <p>
	 * The angle of scattering perpendicular to the beam, and parallel to the
	 * cylinder axis. Measured in radians.  
	 * @param delta
	 * 				Dataset containing the horizontal scattering angle for every point.
	 */
	public void setDelta(Dataset delta) {
		this.delta = delta;
	}

	/**
	 * Sets the vertical scattering angle.
	 * <p>
	 * The angle of scattering perpendicular to both the beam and the cylinder
	 * axis. Measured in radians. 
	 * @param gamma
	 * 				Dataset containing the vertical scattering angle for every point.
	 */
	public void setGamma(Dataset gamma) {
		this.gamma = gamma;
	}

	/**
	 * Sets the beam data for the radiation that the absorption map.
	 * characterizes.
	 * @param beamData
	 * 				XPDFBeamData object describing the absorbed radiation.
	 */
	public void setBeamData(XPDFBeamData beamData) {
		this.beamData = beamData;
	}

	/**
	 * Adds a form to the list of component forms.
	 * <p>
	 * The forms in this list are considered in order from inner to outer. The
	 * first, innermost form is taken to be that of the sample elsewhere, so it
	 * is best to observe this convention. This method also sets the default 
	 * streamality: both up and downstream of every other component. Use 
	 * this.setStreamality() to set it otherwise.
	 * @param format
	 * 			The target component form to add to the list.
	 */
	public void addForm(XPDFComponentForm form) {
		this.formList.add(form);
		for (XPDFComponentForm iComponent : formList) {
			setStreamality(iComponent, form, true, true);
			setStreamality(form, iComponent, true, true);
		}
	}
	
	/**
	 * Gets the index of the Form from the list.
	 * <p>
	 * Given an XPDF target component form, this method returns the position in
	 * the list of forms at which it appears. Woe betide them that ask for the 
	 * index of a form that is not in the list. 
	 * @param form
	 * 			the XPDFComponentForm to find
	 * @return the index at which it appears.
	 */
	private int indexFromForm(XPDFComponentForm form) {
		for (int iform = 0; iform < formList.size(); iform++)
			if (formList.get(iform) == form) return iform;
		return -1; // Ugh
	}
	

	public void setStreamality(XPDFComponentForm formScatterer, XPDFComponentForm formAttenuator, boolean isAttenuatorUp, boolean isAttenuatorDown) {
		isUpstreamMap.put(XPDFAbsorptionMaps.stringifier(this.indexFromForm(formScatterer), this.indexFromForm(formAttenuator)), isAttenuatorUp);
		isDownstreamMap.put(XPDFAbsorptionMaps.stringifier(this.indexFromForm(formScatterer), this.indexFromForm(formAttenuator)), isAttenuatorDown);
	}
	
	/**
	 * Converts the pairs of integers to the keying string in a consistent manner. 
	 * @param iScatterer index of the scattering object in the list of XPDFTargetComponents.
	 * @param iAttenuator index of the attenuating object in the list of XPDFTargetComponents.
	 * @return The encoded string.
	 */
	private static String stringifier(int iScatterer, int iAttenuator) {
		return Integer.toString(iScatterer, Character.MAX_RADIX)+" "+Integer.toString(iAttenuator, Character.MAX_RADIX);
	}

	public boolean checkFormList(List<XPDFComponentForm> inFormList) {
		if (formList == null || inFormList == null) return false;
		boolean sameForms = (inFormList.size() == formList.size());
		for (int i = 0; i < formList.size(); i++) {
			if (!sameForms) break;
			sameForms &= formList.get(i).isEqualToForAbsorption(inFormList.get(i));
		}
		
		return sameForms;
	}
	
	
}
