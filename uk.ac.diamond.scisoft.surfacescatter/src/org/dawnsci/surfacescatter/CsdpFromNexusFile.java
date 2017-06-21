package org.dawnsci.surfacescatter;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.tree.impl.DataNodeImpl;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.dawnsci.nexus.INexusFileFactory;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;

public class CsdpFromNexusFile {

	private static INexusFileFactory nexusFileFactory = new NexusFileFactoryHDF5();
	
	public static CurveStitchDataPackage CsdpFromNexusFileGenerator(String filename){
		
		final CurveStitchDataPackage csdp = new CurveStitchDataPackage();
		
		String[] nodeNames = new String[]{"/Corrected_Intensity_Dataset",
										  "/Fhkl_Dataset",
										  "/Raw_Intensity_Dataset",
										  "/Scanned_Variable_Dataset"};
		
		NexusFile file = nexusFileFactory.newNexusFile(filename);
		
		try {
			file.openToRead();
		} catch (NexusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i =0; i<nodeNames.length;i++){
			
			DataNode inputNode = new DataNodeImpl(i);
			
			IDataset data = nodeToDataset(inputNode, nodeNames[i], file);
			
			switch(i){
			
			case 0:
				csdp.setSplicedCurveY(data);
			case 1:
				csdp.setSplicedCurveYFhkl(data);
			case 2:
				csdp.setSplicedCurveYRaw(data);
			case 3:
				csdp.setSplicedCurveX(data);
		
			}
		}
		
		csdp.setName(filename);
		
		return csdp;
	}
	
	private static IDataset nodeToDataset(DataNode inputNode,
								   String dataName,
								   NexusFile file){
		
		try {
			inputNode = file.getData(dataName);
		} catch (NexusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int[] inputDatasetShape =  inputNode.getDataset().getShape();
		SliceND inputDatasetSlice = new SliceND(inputDatasetShape);
		
		ILazyDataset inputLazyDataset = inputNode.getDataset();
		
		IDataset outputIDataset = DatasetFactory.createFromObject(0);
		
		try {
			outputIDataset = (IDataset) inputLazyDataset.getSlice(inputDatasetSlice);
		} catch (DatasetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return outputIDataset;
	}
}
