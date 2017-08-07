package org.dawnsci.surfacescatter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.tree.impl.DataNodeImpl;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.dawnsci.nexus.INexusFileFactory;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.StringDataset;
import org.eclipse.january.metadata.IMetadata;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class FittingParametersInputReader {
	
	
	private static Scanner in;
	private static INexusFileFactory nexusFileFactory = new NexusFileFactoryHDF5();
	
	public static FittingParameters reader(String title){
	
		try {
			in = new Scanner(new FileReader(title));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		FittingParameters fp = new FittingParameters();
	    
		
	    while (in.hasNextLine()) {
	        String next = in.nextLine();
	    	if(!next.startsWith("#")){
		    	String[] columns = next.split("	");
	            fp.setPt0(Integer.parseInt(columns[0]));
	            fp.setPt1(Integer.parseInt(columns[1]));
	            fp.setLen0(Integer.parseInt(columns[2]));
	            fp.setLen1(Integer.parseInt(columns[3]));
	            fp.setBgMethod(AnalaysisMethodologies.toMethodology(columns[4]));
	            fp.setTracker(TrackingMethodology.toTracker1(columns[5]));
	            fp.setFitPower(AnalaysisMethodologies.toFitPower(columns[6]));
		        fp.setBoundaryBox(Integer.parseInt(columns[7]));
		        fp.setSliderPos(Integer.parseInt(columns[8]));
		        fp.setXValue(Double.parseDouble(columns[9]));
		        fp.setFile(columns[10]);
		        
	        }
	    }
	    
	    in.close();
	    
	    return fp;
	}
	
	public static void readerFromNexus (Tree tree,
										int frameNumber,
										FrameModel m){
	
		
		FittingParameters fp = new FittingParameters();

		String pointNode = "point_" + frameNumber;
		
		String[] attributeNames0 = new String[]{"Boundary_Box",
											   "Fit_Power",
											   "Tracker_Type",
											   "Background_Methodology",
											   "ROI_Location"};
		
		String[] attributeNames = new String[attributeNames0.length];
		
		for(int g =0; g<attributeNames0.length; g++){
			attributeNames[g] = pointNode + attributeNames0[g];
		}
		
		GroupNode gn = tree.getGroupNode();
		GroupNode point = gn.getGroupNode(pointNode);
			
		Attribute boundaryBoxAttribute = point.getAttribute(attributeNames0[0]);
		IntegerDataset boundaryBox0 = DatasetUtils.cast(IntegerDataset.class, boundaryBoxAttribute.getValue());		
		
		
		int boundaryBox =boundaryBox0.getInt();
				
		
		m.setBoundaryBox(boundaryBox);
		
		Attribute fitPowerAttribute = point.getAttribute(attributeNames0[1]);
		StringDataset fitPower0 = DatasetUtils.cast(StringDataset.class, fitPowerAttribute.getValue());
		
		int fitPower = Integer.valueOf(fitPower0.get());
		
		m.setFitPower(fitPower);
		
		Attribute trackerTypeAttribute = point.getAttribute(attributeNames0[2]);
		
		StringDataset trackerType = DatasetUtils.cast(StringDataset.class, trackerTypeAttribute.getValue());
		
		m.setTrackingMethodology(trackerType.get());
		
		Attribute backgroundMethodologyAttribute = point.getAttribute(attributeNames0[3]);
		StringDataset backgroundMethodology = DatasetUtils.cast(StringDataset.class, backgroundMethodologyAttribute.getValue());
		
		m.setBackgroundMethodology(backgroundMethodology.get());
		
		Attribute roiAttribute = point.getAttribute(attributeNames0[4]);
		DoubleDataset roiAttributeDat =  DatasetUtils.cast(DoubleDataset.class, roiAttribute.getValue());
				
				
		double[] roi = new double[8];
		
		for(int h =0; h<8 ; h++){
			roi[h] = roiAttributeDat.getDouble(h); 
		}
		
		m.setRoiLocation(roi);
		
	}
	
	public static void geometricalParametersReaderFromNexus (Tree tree,
															 GeometricParametersModel gm){


		GroupNode gn = tree.getGroupNode();
		
		Method[] methods = gm.getClass().getMethods();
		
		for(Method m : methods){
			
			String mName = m.getName();
			CharSequence s = "get";
			
			
			if(mName.contains(s) && !mName.equals("getClass")){
				
				String name = StringUtils.substringAfter(mName, "get");
				
				Attribute att = gn.getAttribute(name);
				
				String writeName = "set" + name;
				
				StringDataset sd = DatasetUtils.cast(StringDataset.class, att.getValue());
				
				m.getReturnType();
				
				
				for(Method m1 : methods){
					if(m1.getName().equals(writeName)){
						String tn = m1.getParameterTypes()[0].getName();
						try {
							switch(tn){
								case "java.lang.Boolean":
									m1.invoke(gm, Boolean.valueOf(sd.get()));
									break;
								case "java.lang.Double":
									m1.invoke(gm, Double.valueOf(sd.get()));
									break;
								case "java.lang.String":
									m1.invoke(gm, (sd.get()));
									break;
								case "int":
									m1.invoke(gm, Integer.valueOf(sd.get()));
									break;
								case "double":
									m1.invoke(gm, Double.valueOf(sd.get()));
									break;
								default:
									break;
							}
						
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}	
	}
	

	public static FittingParameters fittingParametersFromFrameModel(FrameModel fm){
		
		FittingParameters fp = new FittingParameters();
	    
		int[][] lenPt = LocationLenPtConverterUtils.locationToLenPtConverter(fm.getRoiLocation());
		int[] len = lenPt[0];
		int[] pt = lenPt[1];
		
	    fp.setPt0(pt[0]);
	    fp.setPt1(pt[1]);
	    fp.setLen0(len[0]);
	    fp.setLen1(len[1]);
	    fp.setLenpt(lenPt);
	    fp.setBgMethod(fm.getBackgroundMethdology());
	    fp.setTracker(fm.getTrackingMethodology());
	    fp.setFitPower(fm.getFitPower());
		fp.setBoundaryBox(fm.getBoundaryBox());
		fp.setSliderPos(0);
		
	    return fp;
		
	}
	
	
}
