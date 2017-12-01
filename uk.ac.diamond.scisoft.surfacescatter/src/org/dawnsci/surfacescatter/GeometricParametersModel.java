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
	private Double ScalingFactor = 10.0;
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
	private String experimentMethod = "SXRD";
	private Boolean useNegativeQ = false;
	private boolean poke = true;

	public boolean getUseNegativeQ() {
		return useNegativeQ;
	}

	public void setUseNegativeQ(boolean useNegativeQ) {
		this.useNegativeQ = useNegativeQ;
		poke();
	}

	public Double getNormalisationFactor() {
		return normalisationFactor;
	}

	public void setNormalisationFactor(Double normalisationfactor) {
		this.normalisationFactor = normalisationfactor;
		poke();
	}

	public Boolean getBeamCorrection() {
		return beamCorrection;
	}

	public void setBeamCorrection(Boolean beamCorrection) {
		this.beamCorrection = beamCorrection;
		poke();
	}

	public Double getBeamInPlane() {
		return beamInPlane;
	}

	public void setBeamInPlane(Double beamInPlane) {
		this.beamInPlane = beamInPlane;
		poke();
	}

	public Double getBeamOutPlane() {
		return beamOutPlane;
	}

	public void setBeamOutPlane(Double beamOutPlane) {
		this.beamOutPlane = beamOutPlane;
		poke();
	}

	public Double getCovar() {
		return covar;
	}

	public void setCovar(Double covar) {
		this.covar = covar;
		poke();
	}

	public Double getDetectorSlits() {
		return detectorSlits;
	}

	public void setDetectorSlits(Double detectorSlits) {
		this.detectorSlits = detectorSlits;
		poke();
	}

	public Double getInPlaneSlits() {
		return inPlaneSlits;
	}

	public void setInPlaneSlits(Double inPlaneSlits) {
		this.inPlaneSlits = inPlaneSlits;
		poke();
	}

	public Double getOutPlaneSlits() {
		return outPlaneSlits;
	}

	public void setOutPlaneSlits(Double outPlaneSlits) {
		this.outPlaneSlits = outPlaneSlits;
		poke();
	}

	public Double getReflectivityA() {
		return reflectivityA;
	}

	public void setReflectivityA(Double reflectivityA) {
		this.reflectivityA = reflectivityA;
		poke();
	}

	public Double getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(Double sampleSize) {
		this.sampleSize = sampleSize;
		poke();
	}

	public Boolean getSpecular() {
		return specular;
	}

	public void setSpecular(Boolean specular) {
		this.specular = specular;
		poke();
	}

	public Double getInplanePolarisation() {
		return inplanePolarisation;
	}

	public void setInplanePolarisation(Double inplanePolarisation) {
		this.inplanePolarisation = inplanePolarisation;
		poke();
	}

	public Double getOutplanePolarisation() {
		return outplanePolarisation;
	}

	public void setOutplanePolarisation(Double outplanePolaristaion) {
		this.outplanePolarisation = outplanePolaristaion;
		poke();
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
		poke();
	}

	public String getxName() {
		return xName;
	}

	public void setxName(String xName) {
		this.xName = xName;
		poke();
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
		return ScalingFactor;
	}

	public void setScalingFactor(Double scalingFactor) {
		ScalingFactor = scalingFactor;
		poke();
	}

	public double getBeamHeight() {
		return beamHeight;
	}

	public void setBeamHeight(double beamHeight) {
		this.beamHeight = beamHeight;
		poke();
	}

	public double getFootprint() {
		return footprint;
	}

	public void setFootprint(double footprint) {
		this.footprint = footprint;
		poke();
	}

	public double getAngularFudgeFactor() {
		return angularFudgeFactor;
	}

	public void setAngularFudgeFactor(double angularFudgeFactor) {
		this.angularFudgeFactor = angularFudgeFactor;
		poke();
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
		poke();
	}

	public String getFluxPath() {
		return fluxPath;
	}

	public void setFluxPath(String fluxPath) {
		this.fluxPath = fluxPath;
		poke();
	}

	public String getxNameRef() {
		return xNameRef;
	}

	public void setxNameRef(String xNameRef) {
		this.xNameRef = xNameRef;
		poke();
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
		poke();
	}

	public int getTheta() {
		return theta;
	}

	public void setTheta(int theta) {
		this.theta = theta;
		poke();
	}

	public EnumMap<SXRDAngleAliasEnum, String> getsXRDMap() {
		return sXRDMap;
	}

	public void setsXRDMap(EnumMap<SXRDAngleAliasEnum, String> sXRDMap) {
		this.sXRDMap = sXRDMap;
		poke();
	}
	

	public EnumMap<ReflectivityAngleAliasEnum, String> getReflectivityAnglesMap() {
		return reflectivityAnglesMap;
	}

	public void setReflectivityAnglesMap(EnumMap<ReflectivityAngleAliasEnum, String> reflectivityAnglesMap) {
		this.reflectivityAnglesMap = reflectivityAnglesMap;
		poke();
	}

	public EnumMap<ReflectivityFluxParametersAliasEnum, String> getReflectivityFluxMap() {
		return reflectivityFluxMap;
	}

	public void setReflectivityFluxMap(EnumMap<ReflectivityFluxParametersAliasEnum, String> reflectivityFluxMap) {
		this.reflectivityFluxMap = reflectivityFluxMap;
		poke();
	}

	public boolean getUseInternalFlux() {
		return useInternalFlux;
	}

	public void setUseInternalFlux(boolean useInternalFlux) {
		this.useInternalFlux = useInternalFlux;
		poke();
	}

	public String getExperimentMethod() {
		return experimentMethod;
	}

	public void setExperimentMethod(String experimentMethod) {
		this.experimentMethod = experimentMethod;
		poke();
	}

	private void setPoke(boolean poke) {
		firePropertyChange("poke", this.poke, this.poke = poke);
	}

	public void poke() {
		setPoke(!poke);

	}
}
