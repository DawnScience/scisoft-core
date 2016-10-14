/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class RodScanCorrections {
	
	/*
	 * Mathematics to perform geometric corrections for SXRD.
	 * 
	 */
	
	private static final String ALPHA = "alpha";
	private static final String DELTA = "delta";
	private static final String GAMMA = "gamma";
	private static final String OMEGA = "omega";
	private static final String CHI = "chi";
	private static final String PHI = "phi";
		
	public static SliceFromSeriesMetadata DiffData (IDataset input) {
	
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		
		return ssm;
	}

	public static ILazyDataset  geth(IDataset input) {
		ILazyDataset h = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), "h");
		return h;
	}
	
	public static ILazyDataset  getk(IDataset input) {
		ILazyDataset k = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), "k");
		return k;
	}
	
	public static ILazyDataset  getl(IDataset input) {
		ILazyDataset l = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), "l");
		return l;
	}
	
	public  static ILazyDataset  getalpha(IDataset input) {
		ILazyDataset alpha = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), ALPHA);
		return alpha;
	}
	
	public static ILazyDataset  getdelta(IDataset input) {
		ILazyDataset delta = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), DELTA);
		return delta;
	}
	
	public static ILazyDataset  getgamma(IDataset input) {
		ILazyDataset gamma = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), GAMMA);
		return gamma;
	}
	
	public static ILazyDataset  getomega(IDataset input) {
		ILazyDataset omega = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), OMEGA);
		return omega;
	}
	
	public static ILazyDataset  getchi(IDataset input) {
		ILazyDataset chi = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), CHI);
		return chi;
	}
	
	public static ILazyDataset  getphi(IDataset input) {
		ILazyDataset phi = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), PHI);
		return phi;
	}
	
	
	public static ILazyDataset  getExposureTime(IDataset input) {
		ILazyDataset ExposureTime = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), "ExposureTime");
		return ExposureTime;
	}
	
	
	public static double getScalingFactor(RodScanPolynomial2DModel model){
		double ScalingFactor = model.getScalingFactor();
		return ScalingFactor;
	}
		
	public static Dataset lorentz (IDataset input) throws DatasetException{	
				
		double pc = Math.PI/180;
		
		ILazyDataset alpha3 = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), ALPHA);
		IDataset alpha4 = DiffData(input).getMatchingSlice(alpha3);
		
		ILazyDataset delta3 = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), DELTA);
		IDataset delta4 = DiffData(input).getMatchingSlice(delta3);
		
		ILazyDataset gamma3 = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), GAMMA);
		IDataset gamma4 = DiffData(input).getMatchingSlice(gamma3);
		
		Dataset a = Maths.multiply(alpha3,pc);
		Dataset d = Maths.multiply(delta3,pc);
		Dataset g = Maths.multiply(gamma4,pc);
		
//		System.out.println("alpha4 rank: " + alpha4.getRank());
//		System.out.println("alpha4 first value:" + alpha4.getDouble(0));
//		System.out.println("delta4 rank: " + delta4.getRank());
//		System.out.println("gamma4 rank: " + gamma4.getRank());
		
		
		Dataset lorentzcor = Maths.multiply(Maths.cos(d),(Maths.sin(Maths.subtract(g, a)))); 
		
		System.out.println("lorentzcor rank: " + lorentzcor.getRank());
		
		return lorentzcor;

	}
	
	
	public static Dataset polarisation (IDataset input, double IP, double OP) throws DatasetException{	
		
		double pc = Math.PI/180;
		
		ILazyDataset delta3 = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), DELTA);
		IDataset delta4 = DiffData(input).getMatchingSlice(delta3);
		
		ILazyDataset gamma3 = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), GAMMA);
		IDataset gamma4 = DiffData(input).getMatchingSlice(gamma3);
		
		Dataset d = Maths.multiply(delta4,pc);
		Dataset g = Maths.multiply(gamma4,pc);
		
		Dataset inplane = Maths.subtract(1,Maths.power(Maths.sin(d), 2));
		Dataset outplane = Maths.subtract(1,(Maths.power((Maths.multiply(Maths.cos(d),Maths.sin(g))),2)));
		
		
		Dataset IPdat = DatasetFactory.zeros(delta4.getShape(),Dataset.FLOAT64);
		IPdat = Maths.add(IPdat, IP);
		
		Dataset OPdat = DatasetFactory.zeros(delta4.getShape(),Dataset.FLOAT64);
		OPdat = Maths.add(OPdat, OP);
	
		Dataset polar = Maths.divide(1,(Maths.add((Maths.multiply(OPdat,outplane)),(Maths.multiply(IPdat,inplane)))));
		return polar;
	}

	public static double f_beam(double x, double z, double InPlaneSlits, double OutPlaneSlits, double BeamInPlane, double BeamOutPlane){
		double w = Math.exp(-2.77 * Math.pow(x, 2)/Math.pow(BeamInPlane, 2)) * Math.exp(-2.77 * Math.pow(z, 2)/Math.pow(BeamOutPlane, 2));
		if (Math.abs(x) > InPlaneSlits/2);
			w = 0; 
		if (Math.abs(z) > OutPlaneSlits/2);
			w = 0;
		return w;
	}
	
	public static double f_detector (double x, double DetectorSlits){
		double e = 1;
		if (Math.abs(x) > DetectorSlits/2);
			e = 0; 
		return e;
	}
	
	public static double f_onsample (double x, double y, double sampleSize){
		double q = 1;
		if ((Math.pow(x,2) + (Math.pow(y,2)))> (0.25*Math.pow(sampleSize,2)));
			q =0;
		return q;
	}	
		
	public static Dataset areacor (IDataset input, boolean BeamCor, boolean Specular, 
		double SampleSize, double OutPlaneSlits, double InPlaneSlits, double BeamInPlane, double BeamOutPlane, 
		double DetectorSlits) throws DatasetException {	
			
		double pc = Math.PI/180;
		
		ILazyDataset alpha3 = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), ALPHA);
		IDataset alpha4 = DiffData(input).getMatchingSlice(alpha3);
		
		ILazyDataset delta3 = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), DELTA);
		IDataset delta4 = DiffData(input).getMatchingSlice(delta3);
		
		ILazyDataset gamma3 = ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), GAMMA);
		IDataset gamma4 = DiffData(input).getMatchingSlice(gamma3);
		
		Dataset a = Maths.multiply(alpha4,pc);
		Dataset d = Maths.multiply(delta4,pc);
		Dataset g = Maths.multiply(gamma4,pc);
		
		
		Dataset area_cor = DatasetFactory.zeros(delta4.getShape(),Dataset.FLOAT64);
		Dataset ylimitdat = DatasetFactory.zeros(delta4.getShape(),Dataset.FLOAT64);
		
		
		if (BeamCor == true) {
			if (Specular == true) {
				double y_sum = 0;
				Dataset betain = a;
				double ylimit = 10;
								
				
				ylimitdat = Maths.add(ylimitdat, ylimit);
						
				for (int i=0; i<=delta4.getShape()[0]; i++){
				
					if (SampleSize > 0.01) {
						ylimitdat = Maths.subtract(ylimitdat, ylimit);
						ylimitdat = Maths.add(ylimitdat, SampleSize * 0.6);
					}
			
					else if (Math.abs((2 * (OutPlaneSlits))/Math.sin(0.001 + betain.getDouble(i))) < ylimit) { 
						ylimitdat.set(Math.abs(2 * OutPlaneSlits / Math.sin(betain.getDouble(i) + 0.001)),i);
					}
					if (1.1*OutPlaneSlits / (2*Math.sin(betain.getDouble(i) + 0.001)) < ylimit) {
						ylimitdat.set(1.1 *OutPlaneSlits / (2* Math.sin(betain.getDouble(i))),i);
					}
							
					double ystep = (ylimit / 50);
						
					for (double y = (-1* ylimitdat.getDouble(i)); y <= (ylimitdat.getDouble(i) + ystep); y+=ystep) {
						double c = 	f_onsample(0, y, SampleSize) * f_beam(0,y*Math.sin(betain.getDouble(i)), 
								 InPlaneSlits, OutPlaneSlits, BeamInPlane, BeamOutPlane);						
						y_sum = y_sum + c;
					}
					
					area_cor.set( 1 / (y_sum * ystep) , i);
				}
			}
				
				else { 
					Dataset betain = a;
					Dataset c1 = Maths.sin(betain);
					Dataset c2 = Maths.cos(d);
					Dataset c3 = Maths.sin(d);
					double  xlimit = 1;
					double ylimit = 10;
				
					ylimitdat = Maths.add(ylimitdat, ylimit);
					
					for (int i=0; i<=delta4.getShape()[0]; i++){
						
					
						if (InPlaneSlits > 0.01) {
							xlimit  = 0.01 + InPlaneSlits / 2;	
						}
						
						double xstep = xlimit / 50;
				
						if (SampleSize > 0.01){
							ylimitdat = Maths.subtract(ylimitdat, ylimit);
							ylimitdat = Maths.add(ylimitdat, SampleSize * 0.6);
						}
				
						if (Math.abs(2* OutPlaneSlits / Math.sin(0.001 + betain.getDouble(i))) < ylimit){
							ylimitdat.set(Math.abs(2 * OutPlaneSlits / Math.sin(betain.getDouble(i) + 0.001)),i);
						}
				
						if (1.1 * OutPlaneSlits /(2* Math.sin(0.001 + betain.getDouble(i))) < ylimit) {
							ylimitdat.set(1.1 *OutPlaneSlits / (2* Math.sin(betain.getDouble(i))),i);
						}
				
						double ystep = ylimit / 50;
				
						double com = 0;
						double x = (-1 *xlimit);
						double y = (-1 *ylimit);
						double area_sum = 0;
				
						for ( x = (-1* xlimit); x <= (xlimit + 0.01 + xstep) ; x += xstep){
							for ( y = -1 * ylimit ;  y <= ylimit + 0.01 + ystep; y += ystep){
								area_sum = f_beam(x, y*c1.getDouble(i), 
										 InPlaneSlits, OutPlaneSlits, BeamInPlane, BeamOutPlane) 
										* f_detector(x*c2.getDouble(i) - y*c3.getDouble(i), DetectorSlits) * f_onsample(x, y, SampleSize);
								com = com +area_sum;
							}
						}
						
						area_sum = com;
						double bs_eff = 0;
						com = 0;
				
				
						for (x = -1*xlimit; x<=xlimit+xstep/10; x +=xstep/10){
							bs_eff = f_beam(x, 0, InPlaneSlits, OutPlaneSlits, BeamInPlane, BeamOutPlane);
							com = com + bs_eff;
						}
						bs_eff =com;
						bs_eff = bs_eff * (xstep/10);
						area_cor.set((bs_eff)/(area_sum * xstep * ystep),i);
				
					}
				}
		}
			
			else {
				
				//boolean flag = false;
				//if (Specular == true) flag = true;
		
				if (Specular = false){
					Dataset sinbetaout =  Maths.multiply(Maths.cos(d),Maths.sin(Maths.subtract(g,a)));
					Dataset betaout = Maths.multiply(pc, Maths.arcsin(sinbetaout)) ;
					Dataset cosbetaout = Maths.cos(betaout);
					area_cor = Maths.divide(Maths.sin(d),cosbetaout);		
				}
			
			else{
				area_cor = Maths.sin(a);
				
			}
		}
		
		
		return area_cor;
		
		//
	}	
	

}

//TEST
