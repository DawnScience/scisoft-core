package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.NexusUtils;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;

public class SXRDNexusReader {
	
	
	public static IDataset getYFhkl(String nexusFilePath){
		
		Tree tree = null;
		
		NexusFile nexusFile = null;
		
		try {
			nexusFile = NexusFileHDF5.openNexusFileReadOnly(nexusFilePath);
			tree = NexusUtils.loadNexusTree(nexusFile);
		} catch (NexusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		DataNode dn = tree.getGroupNode().getDataNode("Fhkl_Dataset");
		ILazyDataset Fhkl_Dataset = dn.getDataset();
		
		SliceND sliced = new SliceND(Fhkl_Dataset.getShape());
		
		try {
			return Fhkl_Dataset.getSlice(sliced) ;
		} catch (DatasetException e) {
			
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static IDataset getScannedVariable(String nexusFilePath){
		
		Tree tree = null;
		
		NexusFile nexusFile = null;
		
		try {
			nexusFile = NexusFileHDF5.openNexusFileReadOnly(nexusFilePath);
			tree = NexusUtils.loadNexusTree(nexusFile);
		} catch (NexusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GroupNode gn = tree.getGroupNode();
		DataNode dn = gn.getDataNode("Scanned_Variable_Dataset");
		 
		ILazyDataset Scanned_Variable_Dataset = dn.getDataset();
		
		SliceND sliced = new SliceND(Scanned_Variable_Dataset.getShape());
		
		try {
			return Scanned_Variable_Dataset.getSlice(sliced) ;
		} catch (DatasetException e) {
			
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static IDataset[] getScannedVariableAndFhkl (String nexusFilePath){
		
		IDataset[]  output = new IDataset[2];
		
		output[0] = getScannedVariable(nexusFilePath);
		output[1] = getYFhkl(nexusFilePath);
		
		return output;
	}
	
	

}
