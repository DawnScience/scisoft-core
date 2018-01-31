package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EnumMap;

public class GeometricParametersModel {

	private Boolean beamCorrection = false;
	private Double beamInPlane = 0.3;
	private Double beamOutPlane = 0.3;
	private Double covar = 1.0;
	private Double detectorSlits = 0.2;
	private Double inPlaneSlits = 0.5;
	private Double inplanePolarisation = 0.0;
	private Double outPlaneSlits = 0.5;
	private Double outplanePolarisation = 1.0;
	private Double reflectivityA = 1.0;
	private Double sampleSize = 10.0;
	private Double normalisationFactor = 10.0;
	private Boolean specular = false;
	private String imageName = "file_image";
	private String xName = "l";
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private Double scalingFactor = 10.0;
	private double beamHeight = 0.06;
	private double footprint = 190;
	private double angularFudgeFactor = 0;
	private String savePath = "/scratch/runtime-uk.ac.diamond.dawn.product/data/examples";
	private String fluxPath = "NO"; // "/scratch/233990.dat";
	private String xNameRef = "qsdcd";
	private double energy = 12500;
	private int theta = 0;
	private EnumMap<SXRDAngleAliasEnum, String> sXRDMap;
	private EnumMap<ReflectivityAngleAliasEnum, String> reflectivityAnglesMap;
	private EnumMap<ReflectivityFluxParametersAliasEnum, String> reflectivityFluxMap;
	private boolean useInternalFlux;
	private MethodSettingEnum.MethodSetting experimentMethod = MethodSettingEnum.MethodSetting.SXRD;
	private Boolean useNegativeQ = false;
	private boolean poke = true;

	public boolean getUseNegativeQ() {
		return useNegativeQ;
	}

	public void setUseNegativeQ(boolean useNegativeQ) {
		firePropertyChange("useNegativeQ", this.useNegativeQ, this.useNegativeQ = useNegativeQ);
	}

	public Double getNormalisationFactor() {
		return normalisationFactor;
	}

	public void setNormalisationFactor(Double normalisationfactor) {
		firePropertyChange("normalisationfactor", this.normalisationFactor, this.normalisationFactor = normalisationfactor);
	}

	public Boolean getBeamCorrection() {
		return beamCorrection;
	}

	public void setBeamCorrection(Boolean beamCorrection) {
		firePropertyChange("beamCorrection", this.beamCorrection, this.beamCorrection = beamCorrection);
	}

	public Double getBeamInPlane() {
		return beamInPlane;
	}

	public void setBeamInPlane(Double beamInPlane) {
		firePropertyChange("beamInPlane", this.beamInPlane, this.beamInPlane= beamInPlane);
	}

	public Double getBeamOutPlane() {
		return beamOutPlane;
	}

	public void setBeamOutPlane(Double beamOutPlane) {
		firePropertyChange("beamOutPlane", this.beamOutPlane, this.beamOutPlane= beamOutPlane);
	}

	public Double getCovar() {
		return covar;
	}

	public void setCovar(Double covar) {
		firePropertyChange("covar", this.covar, this.covar= covar);
	}

	public Double getDetectorSlits() {
		return detectorSlits;
	}

	public void setDetectorSlits(Double detectorSlits) {
		firePropertyChange("detectorSlits", this.detectorSlits, this.detectorSlits= detectorSlits);
	}

	public Double getInPlaneSlits() {
		return inPlaneSlits;
	}

	public void setInPlaneSlits(Double inPlaneSlits) {
		firePropertyChange("inPlaneSlits", this.inPlaneSlits, this.inPlaneSlits= inPlaneSlits);
	}

	public Double getOutPlaneSlits() {
		return outPlaneSlits;
	}

	public void setOutPlaneSlits(Double outPlaneSlits) {
		firePropertyChange("outPlaneSlits", this.outPlaneSlits, this.outPlaneSlits= outPlaneSlits);
	}

	public Double getReflectivityA() {
		return reflectivityA;
	}

	public void setReflectivityA(Double reflectivityA) {
		firePropertyChange("reflectivityA", this.reflectivityA, this.reflectivityA= reflectivityA);
	}

	public Double getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(Double sampleSize) {
		firePropertyChange("sampleSize", this.sampleSize, this.sampleSize= sampleSize);
	}

	public Boolean getSpecular() {
		return specular;
	}

	public void setSpecular(Boolean specular) {
		firePropertyChange("specular", this.specular, this.specular= specular);
	}

	public Double getInplanePolarisation() {
		return inplanePolarisation;
	}

	public void setInplanePolarisation(Double inplanePolarisation) {
		firePropertyChange("inplanePolarisation", this.inplanePolarisation, this.inplanePolarisation= inplanePolarisation);
	}

	public Double getOutplanePolarisation() {
		return outplanePolarisation;
	}

	public void setOutplanePolarisation(Double outplanePolarisation) {
		firePropertyChange("outplanePolarisation", this.outplanePolarisation, this.outplanePolarisation= outplanePolarisation);
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		firePropertyChange("imageName", this.imageName, this.imageName= imageName);
	}

	public String getxName() {
		return xName;
	}

	public void setxName(String xName) {
		firePropertyChange("xName", this.xName, this.xName= xName);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	public Double getScalingFactor() {
		return scalingFactor;
	}

	public void setScalingFactor(Double scalingFactor) {
		firePropertyChange("scalingFactor", this.scalingFactor, this.scalingFactor= scalingFactor);
	}

	public double getBeamHeight() {
		return beamHeight;
	}

	public void setBeamHeight(double beamHeight) {
		firePropertyChange("beamHeight", this.beamHeight, this.beamHeight= beamHeight);
	}

	public double getFootprint() {
		return footprint;
	}

	public void setFootprint(double footprint) {
		firePropertyChange("footprint", this.footprint, this.footprint= footprint);
	}

	public double getAngularFudgeFactor() {
		return angularFudgeFactor;
	}

	public void setAngularFudgeFactor(double angularFudgeFactor) {
		firePropertyChange("angularFudgeFactor", this.angularFudgeFactor, this.angularFudgeFactor= angularFudgeFactor);
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		firePropertyChange("savePath", this.savePath, this.savePath= savePath);
	}

	public String getFluxPath() {
		return fluxPath;
	}

	public void setFluxPath(String fluxPath) {
		firePropertyChange("fluxPath", this.fluxPath, this.fluxPath= fluxPath);
	}

	public String getxNameRef() {
		return xNameRef;
	}

	public void setxNameRef(String xNameRef) {
		firePropertyChange("xNameRef", this.xNameRef, this.xNameRef= xNameRef);
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		firePropertyChange("energy", this.energy, this.energy= energy);
	}

	public int getTheta() {
		return theta;
	}

	public void setTheta(int theta) {
		firePropertyChange("theta", this.theta, this.theta= theta);
	}

	public EnumMap<SXRDAngleAliasEnum, String> getsXRDMap() {
		return sXRDMap;
	}

	public void setsXRDMap(EnumMap<SXRDAngleAliasEnum, String> sXRDMap) {
		firePropertyChange("sXRDMap", this.sXRDMap, this.sXRDMap= sXRDMap);
	}
	

	public EnumMap<ReflectivityAngleAliasEnum, String> getReflectivityAnglesMap() {
		return reflectivityAnglesMap;
	}

	public void setReflectivityAnglesMap(EnumMap<ReflectivityAngleAliasEnum, String> reflectivityAnglesMap) {
		firePropertyChange("reflectivityAnglesMap", this.reflectivityAnglesMap, this.reflectivityAnglesMap= reflectivityAnglesMap);
	}

	public EnumMap<ReflectivityFluxParametersAliasEnum, String> getReflectivityFluxMap() {
		return reflectivityFluxMap;
	}

	public void setReflectivityFluxMap(EnumMap<ReflectivityFluxParametersAliasEnum, String> reflectivityFluxMap) {
		firePropertyChange("reflectivityFluxMap", this.reflectivityFluxMap, this.reflectivityFluxMap= reflectivityFluxMap);
	}

	public boolean getUseInternalFlux() {
		return useInternalFlux;
	}

	public void setUseInternalFlux(boolean useInternalFlux) {
		firePropertyChange("useInternalFlux", this.useInternalFlux, this.useInternalFlux= useInternalFlux);
	}

	public String getExperimentMethod() {
		return experimentMethod.getCorrectionsName();
	}
	
	public MethodSettingEnum.MethodSetting getExperimentMethodEnum() {
		return experimentMethod;
	}

	public void setExperimentMethod(String experimentMethod) {
		
		firePropertyChange("experimentMethod", this.experimentMethod, this.experimentMethod= MethodSettingEnum.MethodSetting.toMethod(experimentMethod));
	}

	public void setExperimentMethod(MethodSettingEnum.MethodSetting experimentMethod) {
		
		firePropertyChange("experimentMethod", this.experimentMethod, this.experimentMethod= experimentMethod);
	}
	
	private void setPoke(boolean poke) {
		firePropertyChange("poke", this.poke, this.poke = poke);
	}

	public void poke() {
		setPoke(!poke);

	}
}
