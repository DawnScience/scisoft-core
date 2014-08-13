/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.metadata;

import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;

public class ARPESMetadataImpl implements ARPESMetadata {

	double photonEnergy = 0.0;
	double workFunction = 0.0;
	double passEnergy = 0.0;
	double temperature = 0.0;
	double energyAxisGlobalOffset = 0.0;
	double angleAxisGlobalOffset = 0.0;
	ILazyDataset kineticEnergies = null;
	ILazyDataset analyserAngles = null;
	ILazyDataset polarAngles = null;
	ILazyDataset tiltAngles = null;
	ILazyDataset azimuthalAngles = null;
	ILazyDataset energyAxisOffset = null;
	ILazyDataset bindingEnergies = null;
	ILazyDataset photoelectronMomentum = null;

	public ARPESMetadataImpl() {
	}

	public ARPESMetadataImpl(ARPESMetadata metadata) {
		super();
		this.photonEnergy = metadata.getPhotonEnergy();
		this.workFunction = metadata.getWorkFunction();
		this.kineticEnergies = metadata.getKineticEnergies();
		this.analyserAngles = metadata.getAnalyserAngles();
		this.polarAngles = metadata.getPolarAngles();
		this.tiltAngles = metadata.getTiltAngles();
		this.azimuthalAngles = metadata.getAzimuthalAngles();
		this.energyAxisOffset = metadata.getEnergyAxisOffset();
		this.bindingEnergies = metadata.getBindingEnergies();
		this.photoelectronMomentum = metadata.getPhotoelectronMomentum();
	}

	@Override
	public double getPhotonEnergy() {
		return photonEnergy;
	}

	public void setPhotonEnergy(double photonEnergy) {
		this.photonEnergy = photonEnergy;
	}

	@Override
	public double getWorkFunction() {
		return workFunction;
	}

	public void setWorkFunction(double workFunction) {
		this.workFunction = workFunction;
	}

	@Override
	public double getPassEnergy() {
		return passEnergy;
	}

	public void setPassEnergy(double passEnergy) {
		this.passEnergy = passEnergy;
	}

	@Override
	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	@Override
	public double getEnergyAxisGlobalOffset() {
		return energyAxisGlobalOffset;
	}

	public void setEnergyAxisGlobalOffset(double energyAxisGlobalOffset) {
		this.energyAxisGlobalOffset = energyAxisGlobalOffset;
	}

	@Override
	public double getAngleAxisGlobalOffset() {
		return angleAxisGlobalOffset;
	}

	public void setAngleAxisGlobalOffset(double angleAxisGlobalOffset) {
		this.angleAxisGlobalOffset = angleAxisGlobalOffset;
	}

	@Override
	public ILazyDataset getKineticEnergies() {
		return kineticEnergies;
	}

	public void setKineticEnergies(ILazyDataset kineticEnergies) {
		this.kineticEnergies = kineticEnergies;
	}

	@Override
	public ILazyDataset getAnalyserAngles() {
		return analyserAngles;
	}

	public void setAnalyserAngles(ILazyDataset analyserAngles) {
		this.analyserAngles = analyserAngles;
	}

	@Override
	public ILazyDataset getPolarAngles() {
		return polarAngles;
	}

	public void setPolarAngles(ILazyDataset polarAngles) {
		this.polarAngles = polarAngles;
	}

	public void setPolarAngles(double staticPolarAngle) {
		// TODO Auto-generated method stub
	}

	@Override
	public ILazyDataset getTiltAngles() {
		return tiltAngles;
	}

	public void setTiltAngles(ILazyDataset tiltAngles) {
		this.tiltAngles = tiltAngles;
	}

	@Override
	public ILazyDataset getAzimuthalAngles() {
		return azimuthalAngles;
	}

	public void setAzimuthalAngles(ILazyDataset azimuthalAngles) {
		this.azimuthalAngles = azimuthalAngles;
	}

	@Override
	public ILazyDataset getEnergyAxisOffset() {
		return energyAxisOffset;
	}

	public void setEnergyAxisOffset(ILazyDataset energyAxisOffset) {
		this.energyAxisOffset = energyAxisOffset;
	}

	@Override
	public ILazyDataset getBindingEnergies() {
		return bindingEnergies;
	}

	public void setBindingEnergies(ILazyDataset bindingEnergies) {
		this.bindingEnergies = bindingEnergies;
	}

	@Override
	public ILazyDataset getPhotoelectronMomentum() {
		return photoelectronMomentum;
	}

	public void setPhotoelectronMomentum(ILazyDataset photoelectronMomentum) {
		this.photoelectronMomentum = photoelectronMomentum;
	}

	@Override
	public ARPESMetadataImpl clone() {
		return new ARPESMetadataImpl(this);
	}
}
