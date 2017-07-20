/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

public class RodScanPolynomial2DModel extends AbstractOperationModel {
	//@OperationModelField(label="Direction of Integration", hint="The direction to integrate in.")
		//private Direction direction = Direction.X;
		
		@OperationModelField(label="Outplane Polarisation", hint = "Outplane Polarisation" )
		private double OutPlanePolarisation = 1.0;
		
		@OperationModelField(label="Inplane Polarisation", hint = "Inplane Polarisation" )
		private double InPlanePolarisation = 0.0;
		
		@OperationModelField(label="Beam Correction", hint = "Beam Correction True/False" )
		private boolean BeamCor = false;
			
		@OperationModelField(label="Specular", hint = "Specular True/False" )
		private boolean Specular = false;
		
		@OperationModelField(label="Sample Size in mm", hint = "Sample Size" )
		private double SampleSize = 10;
		
		@OperationModelField(label="Out Plane Slits", hint = "Out Plane Slits" )
		private double OutPlaneSlits = 0.2;

		@OperationModelField(label="In Plane Slits", hint = "In Plane Slits" )
		private double InPlaneSlits = 0.2;
		
		@OperationModelField(label="Beam In Plane", hint = "Beam In Plane" )
		private double BeamInPlane = 0.3;
		
		@OperationModelField(label="Beam Out Plane", hint = "Beam Out Plane" )
		private double BeamOutPlane = 0.3;
		
		@OperationModelField(label="Detector Slits", hint = "Detector Slits" )
		private double DetectorSlits = 10;
		
		@OperationModelField(label="Scaling/Normalisation Factor", hint = "Scaling/Normalisation Factor, if in reflectivity mode, \n note that this will be the division factor, \n in CRT mode, the multiplying factor" )
		private double ScalingFactor = 10;
		
		@OperationModelField(label="Reflectivity a", hint = "Reflectivity a" )
		private double a = 1;

		
		@OperationModelField(label="covar", hint = "covar" )
		private double covar = 1;
	
		public double getOutPlanePolarisation() {
			return OutPlanePolarisation;
		}
		
		public double getInPlanePolarisation() {
			return InPlanePolarisation;
		}
		
		public boolean getBeamCor() {
			return BeamCor;
		}
		
		public boolean getSpecular() {
			return Specular;
		}
		
		public double getSampleSize() {
			return SampleSize;
		}
		
		public double getOutPlaneSlits () {
			return OutPlaneSlits ;
		}
		
		public double getInPlaneSlits () {
			return InPlaneSlits ;
		}
		
		public double getBeamInPlane() {
			return BeamInPlane ;
		}

		public double getBeamOutPlane() {
			return BeamOutPlane ;
		}
		
		public double getDetectorSlits() {
			return DetectorSlits ;
		}

		public double getScalingFactor() {
			return ScalingFactor;
		}
		
		public double geta() {
			return a;
		}

		public double getCovar() {
			return covar;
		}

		public void setBeamCor(boolean BeamCor) {
			firePropertyChange("BeamCor", this.BeamCor, this.BeamCor = BeamCor);

		}
		
		public void Specular(boolean Specular) {
			firePropertyChange("Specular", this.Specular, this.Specular = Specular);

		}
		
		public void setInPlanePolarisation (double InPlanePolarisation) {
			firePropertyChange("InPlanePolarisation", this.InPlanePolarisation, this.InPlanePolarisation = InPlanePolarisation);

		}

		public void setBeamInPlane (double BeamInPlane) {
			firePropertyChange("BeamInPlane", this.BeamInPlane, this.BeamInPlane = BeamInPlane);

		}
		
		public void setBeamOutPlane (double BeamOutPlane) {
			firePropertyChange("BeamOutPlane", this.BeamOutPlane, this.BeamOutPlane = BeamOutPlane);

		}
		
		public void setOutPlanePolarisation (double OutPlanePolarisation) {
			firePropertyChange("OutPlanePolarisation", this.OutPlanePolarisation, this.OutPlanePolarisation = OutPlanePolarisation);
		}

		public void setSampleSize (double SampleSize) {
			firePropertyChange("SampleSize", this.SampleSize, this.SampleSize = SampleSize);
		}

		
		private RectangularROI box = new RectangularROI(200d, 100d, 10d, 10d, 0d);


		public RectangularROI getBox() {
			return box;
		}

		public void setBox(RectangularROI box) {
			firePropertyChange("box", this.box, this.box = box);
		}
		
		public void setOutPlaneSlits (double OutPlaneSlits) {
			firePropertyChange("OutPlaneSlits", this.OutPlaneSlits , this.OutPlaneSlits  = OutPlaneSlits );
		}

		public void setInPlaneSlits (double InPlaneSlits) {
			firePropertyChange("InPlaneSlits", this.InPlaneSlits , this.InPlaneSlits  = InPlaneSlits );
		}

		public void setDetectorSlits (double DetectorSlits) {
			firePropertyChange("DetectorSlits", this.DetectorSlits , this.DetectorSlits  = DetectorSlits );
		}

		public void setScalingFactor (double ScalingFactor) {
			firePropertyChange("ScalingFactor", this.ScalingFactor , this.ScalingFactor  = ScalingFactor);
		}

		public void seta (double a) {
			firePropertyChange("a", this.a, this.a = a);
		}
		
		
		public void setcovar (double covar) {
			firePropertyChange("covar", this.covar, this.covar = covar);
		}
		
}


//TEST
	

