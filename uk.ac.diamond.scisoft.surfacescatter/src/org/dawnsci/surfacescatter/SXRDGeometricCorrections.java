/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.dawnsci.surfacescatter;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.AggregateDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.SliceND;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class SXRDGeometricCorrections {
	
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
		
	public static ILazyDataset DiffData (ExampleModel model, String choice) {
		
		IDataHolder dh1 = null;
			
		try {
				dh1 = LoaderFactory.getData(model.getFilepath());			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ILazyDataset ild =dh1.getLazyDataset(choice); 

		return ild;
	}

	
	public static ILazyDataset getArb(ExampleModel model, String choice) {
		SliceND slice1 = new SliceND(new int[] {1});
		slice1.setSlice(0, model.getImageNumber(), model.getImageNumber()+1, 1);
		ILazyDataset arb = null;
		arb =  DiffData(model, choice);
//		arb.squeezeEnds();
		return arb;
	}
	
	
	public static ILazyDataset geth(ExampleModel model) {
		return getArb(model, "h");
	}
	
	public static ILazyDataset getk(ExampleModel model) {
		return getArb(model, "k");
		}
	
	public static ILazyDataset  getl(ExampleModel model) {
		return getArb(model, "l");
		}
	
	public  static ILazyDataset  getAlpha(ExampleModel model) {
		return getArb(model, ALPHA);
		}
	
	public static ILazyDataset  getDelta(ExampleModel model) {
		return getArb(model, DELTA);
		}
	
	public static ILazyDataset  getGamma(ExampleModel model) {
		return getArb(model, GAMMA);
		}
	
	public static ILazyDataset  getOmega(ExampleModel model) {
		return getArb(model, OMEGA);
		}
	
	public static ILazyDataset  getChi(ExampleModel model) {
		return getArb(model, CHI);
		}
	
	public static ILazyDataset  getPhi(ExampleModel model) {
		return getArb(model, PHI);
		}
	
	
	public static ILazyDataset  getExposureTime(ExampleModel model) {
		return getArb(model, "ExposureTime");
		}
	
		
	public static Dataset lorentz (ExampleModel model) throws DatasetException{	
				
		double pc = Math.PI/180;
		
		ILazyDataset alpha3 = getAlpha(model);
				//ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), ALPHA);
	//	SliceND slicea = new SliceND(alpha3.getShape());
	//	IDataset alpha4 = alpha3.getSlice(slicea);
		
		ILazyDataset delta3 = getDelta(model);
//		SliceND sliced = new SliceND(alpha3.getShape());
//		IDataset delta4 = delta3.getSlice(sliced);
		
		ILazyDataset gamma3 = getGamma(model);
//		SliceND sliceg = new SliceND(gamma3.getShape());
//		IDataset gamma4 = gamma3.getSlice(sliceg);
//		
		Dataset a = Maths.multiply(alpha3,pc);
		Dataset d = Maths.multiply(delta3,pc);
		Dataset g = Maths.multiply(gamma3,pc);
//		
//		System.out.println("alpha4 rank: " + alpha3.getRank());
//		System.out.println("alpha4 first value:" + alpha4.getDouble(0));
//		System.out.println("delta4 rank: " + delta4.getRank());
//		System.out.println("gamma4 rank: " + gamma4.getRank());
		
		
		Dataset lorentzcor = Maths.multiply(Maths.cos(d),(Maths.sin(Maths.subtract(g, a)))); 
		
//		System.out.println("lorentzcor rank: " + lorentzcor.getRank());
		
		return lorentzcor;

	}
	
	
	public static Dataset polarisation (ExampleModel model, double IP, double OP) throws DatasetException{	
		
		double pc = Math.PI/180;
		
		ILazyDataset delta3 = getDelta(model);
//		SliceND sliced = new SliceND(delta3.getShape());
//		IDataset delta4 = delta3.getSlice(sliced);
//		
		ILazyDataset gamma3 = getGamma(model);
//		SliceND sliceg = new SliceND(gamma3.getShape());
//		IDataset gamma4 = gamma3.getSlice(sliceg);
//		
		Dataset d = Maths.multiply(delta3,pc);
		Dataset g = Maths.multiply(gamma3,pc);
		
		Dataset inplane = Maths.subtract(1,Maths.power(Maths.sin(d), 2));
		Dataset outplane = Maths.subtract(1,(Maths.power((Maths.multiply(Maths.cos(d),Maths.sin(g))),2)));
		
		
		Dataset IPdat = DatasetFactory.zeros(delta3.getShape(),Dataset.FLOAT64);
		IPdat = Maths.add(IPdat, IP);
		
		Dataset OPdat = DatasetFactory.zeros(delta3.getShape(),Dataset.FLOAT64);
		OPdat = Maths.add(OPdat, OP);
	
		Dataset polar = Maths.divide(1,(Maths.add((Maths.multiply(OPdat,outplane)),(Maths.multiply(IPdat,inplane)))));
		return polar;
	}

	public static double f_beam(double x, double z, double InPlaneSlits, double OutPlaneSlits, double BeamInPlane, double BeamOutPlane){
		double w = (Math.exp(-2.77 * Math.pow(x, 2)/Math.pow(BeamInPlane, 2)) * Math.exp(-2.77 * Math.pow(z, 2)/Math.pow(BeamOutPlane, 2)));
		if (Math.abs(x) > InPlaneSlits/2){
			w = 0; 
		}
		if (Math.abs(z) > OutPlaneSlits/2){
			w = 0;
		}
		else{
            w = (Math.exp(((-2.77 * (Math.pow(x,2)))/(Math.pow(BeamInPlane,2))))*Math.exp(((-2.77 * (Math.pow(z,2)))/(Math.pow(BeamOutPlane,2)))));
		}
		
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
		if ((Math.pow(x,2) + (Math.pow(y,2)))> (0.25*Math.pow(sampleSize,2))){
			q =0;
		}
		else{
			q = 1;
		}
//		Dataset q1 = DatasetFactory.createFromObject(q);
		return q;
	}	
		
	public static Dataset areacor (ExampleModel model, boolean BeamCor, boolean Specular, 
		double SampleSize, double OutPlaneSlits, double InPlaneSlits, double BeamInPlane, double BeamOutPlane, 
		double DetectorSlits) throws DatasetException {	
			
		double pc = Math.PI/180;
		
		ILazyDataset alpha3 = getAlpha(model);
		//ProcessingUtils.getLazyDataset(null, DiffData(input).getFilePath(), ALPHA);
//		SliceND slicea = new SliceND(alpha3.getShape());
//		IDataset alpha4 = alpha3.getSlice(slicea);
//		
		ILazyDataset delta3 = getDelta(model);
//		SliceND sliced = new SliceND(alpha3.getShape());
//		IDataset delta4 = delta3.getSlice(sliced);
//		
		ILazyDataset gamma3 = getGamma(model);
//		SliceND sliceg = new SliceND(gamma3.getShape());
//		IDataset gamma4 = gamma3.getSlice(sliceg);

		
		Dataset a = Maths.multiply(alpha3,pc);
		Dataset d = Maths.multiply(delta3,pc);
		Dataset g = Maths.multiply(gamma3,pc);
		
		
		Dataset area_cor = DatasetFactory.zeros(delta3.getShape(),Dataset.FLOAT64);
		Dataset ylimitdat = DatasetFactory.zeros(delta3.getShape(),Dataset.FLOAT64);
		
		
		if (BeamCor == true) {
			if (Specular == true) {
				Dataset y_sum = DatasetFactory.createFromObject(0);
				Dataset betain = a;
				double ylimit = 10;
								
				
				ylimitdat = Maths.add(ylimitdat, ylimit);
						
				for (int i=0; i<=delta3.getShape()[0]; i++){
				
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
					Dataset ystep1 = DatasetFactory.createFromObject(ystep);
						
					for (double y = (-1* ylimitdat.getDouble(i)); y <= (ylimitdat.getDouble(i) + ystep); y+=ystep) {
						Dataset c = 	Maths.multiply(f_onsample(0, y, SampleSize) , f_beam(0,y*Math.sin(betain.getDouble(i)), 
								 InPlaneSlits, OutPlaneSlits, BeamInPlane, BeamOutPlane));						
						y_sum = Maths.add(y_sum , c);
					}
					
					area_cor.set( (Maths.divide(1, Maths.multiply(y_sum , ystep)).getObject(0)) , i);
				}
			}
				
				else { 
					Dataset betain = a;
					Dataset c1 = Maths.sin(betain);
					Dataset c2 = Maths.cos(d);
					Dataset c3 = Maths.sin(d);
					double  xlimit = 0.1;
					double ylimit = 10;
				
					ylimitdat = Maths.add(ylimitdat, ylimit);
					
					if (InPlaneSlits> 0.01){
	                    xlimit =InPlaneSlits / 2 + 0.01;
					}
	                else{
	                    xlimit = 0.1;
	                }
					
					
					for (int i=0; i<delta3.getShape()[0]; i++){
						
					
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
								double fb = f_beam(x, y*c1.getDouble(i), 
										 InPlaneSlits, OutPlaneSlits, BeamInPlane, BeamOutPlane);
								double fd = f_detector(x*c2.getDouble(i) - y*c3.getDouble(i), DetectorSlits);
								
								double fo = f_onsample(x, y, SampleSize);
								
								if (fb!=0 && fo!=0 && fd !=0){
									
									System.out.println("@@@@@@@@@@~~~~~~~~~~~~~~~~~~$$$$$$$$$$$$$$$$$$$REALRESULT!{{{{{{}}}}}}}}}}}}}}}}$$$$$$$$$$$$$$$%%%%%%%%%%");
									
									if(fb != 0){
										System.out.println("fb :  " + fb + "  x = " +x + "  y = " +y);
									}
									else if(fo != 0){
										System.out.println("fo :  " + fo +"  x = " +x + "  y = " +y);
									}
									else if(fd != 0){
										System.out.println("fd :  " + fd +"  x = " +x + "  y = " +y);
									}

								}
								
								if(fd != 0){
									System.out.println("fd :  " + fd +"  x = " +x + "  y = " +y);
								}
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