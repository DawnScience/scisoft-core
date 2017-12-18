package org.dawnsci.surfacescatter;

import java.util.ArrayList;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.StringDataset;

public class CsdpFromNexusFile {

	public static CurveStitchDataPackage CsdpFromNexusFileGenerator(String filename){

		final CurveStitchDataPackage csdp = new CurveStitchDataPackage();

		final String entryString = "/" + NeXusStructureStrings.getEntry() + "/" ;
		final String reducedDataString = entryString + NeXusStructureStrings.getReducedDataDataset() + "/";
		final String goodPointsArrayRoot = entryString + NeXusStructureStrings.getOverviewOfFrames() + "/";
		final String goodPointsArrayString =  NeXusStructureStrings.getIsGoodPointArray()[1];
		
		String scannedVariable = reducedDataString + NeXusStructureStrings.getScannedVariableDataset();

		String[] nodeNames = new String[]{scannedVariable,
				reducedDataString + NeXusStructureStrings.getCorrectedIntensityDataset(),
				reducedDataString + NeXusStructureStrings.getCorrectedIntensityDatasetErrors(),
				reducedDataString + NeXusStructureStrings.getFhklDataset(),
				reducedDataString + NeXusStructureStrings.getFhklDatasetErrors(),
				reducedDataString + NeXusStructureStrings.getRawIntensityDataset(),
				reducedDataString + NeXusStructureStrings.getRawIntensityDatasetErrors()};

		NexusFile file = new NexusFileFactoryHDF5().newNexusFile(filename);

		try {
			file.openToRead();
		} catch (NexusException e) {
			e.printStackTrace();
		}

		boolean done = false;
		
		for(int i =0; i<nodeNames.length;i++){

			IDataset data = DatasetFactory.createFromObject(0);

			
			try{
				data = nodeToDataset(nodeNames[i], file);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}

			if(nodeNames[i].equals(scannedVariable) && !done){
				try{
					data = nodeToDataset(reducedDataString + data.getString(), file);
				}
				catch(Exception f){
					System.out.println(f.getMessage());
				}
				
				done = true;
			}

			if(data.getSize() ==0) {
				throw new NullPointerException();
			}
			try {
				data.getSize(); 
				data.getDouble(0);
			}
			catch(Exception o ) {
				throw new NullPointerException();
			}
			
			switch(i){

			case 0:
				csdp.setSplicedCurveX(data);
				break;
			case 1:
				csdp.setSplicedCurveY(data);
				break;
			case 2:
				csdp.setSplicedCurveYError(data);
				break;
			case 3:
				csdp.setSplicedCurveYFhkl(data);
				break;
			case 4:
				csdp.setSplicedCurveYFhklError(data);
				break;
			case 5:
				csdp.setSplicedCurveYRaw(data);
				break;
			case 6:
				csdp.setSplicedCurveYRawError(data);
				break;
			default:
				//defensive
				break;

			}
		}

		IDataset bd = getStringAttribute(goodPointsArrayString, file, goodPointsArrayRoot);
		
		csdp.setSplicedGoodPointIDataset(bd);
		
		csdp.setRodName(filename);

		return csdp;
	}

	private static IDataset nodeToDataset(String dataName,
			NexusFile file){

		DataNode inputNode = null;
		
		try {
			inputNode = file.getData(dataName);
		} catch (NexusException e) {
			e.printStackTrace();
		}

		int[] inputDatasetShape =  inputNode.getDataset().getShape();
		SliceND inputDatasetSlice = new SliceND(inputDatasetShape);

		ILazyDataset inputLazyDataset = inputNode.getDataset();

		IDataset outputIDataset = DatasetFactory.createFromObject(0);

		try {
			outputIDataset = (IDataset) inputLazyDataset.getSlice(inputDatasetSlice);
		} catch (DatasetException e) {
			e.printStackTrace();
		}

		return outputIDataset;
	}

	private static IDataset getStringAttribute(String desired, NexusFile file, String gN) {
		
		GroupNode g = null;
		
		try {
			g = file.getGroup(gN, false);
		} catch (NexusException e) {
			e.printStackTrace();
		}
		
		StringDataset sd = DatasetUtils.cast(StringDataset.class, g.getAttribute(desired).getValue());
		
		ArrayList<Boolean> ob = new ArrayList<>();
		
	
				
		for(int i =0; i< sd.getSize(); i++) {
			
			Boolean f = Boolean.valueOf(sd.get(i));
			ob.add(f);
		}
		
		
		return DatasetFactory.createFromList(ob);
		
	}


}
