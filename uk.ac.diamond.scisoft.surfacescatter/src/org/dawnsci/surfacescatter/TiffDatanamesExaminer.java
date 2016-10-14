package org.dawnsci.surfacescatter;

import java.util.Arrays;
import java.util.StringJoiner;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import java.util.StringJoiner;

public class TiffDatanamesExaminer {
	
	public static void main(String[] args){
		
		IDataHolder dh = null;
		
		String filepath = "/scratch/Eclipse mars workspace 1/runtime-uk.ac.diamond.dawn.product/data/examples/p3Image1024112.tif";
		
		try {
			dh = LoaderFactory.getData(filepath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//if (dh == null) throw new OperationException(op,"Error opening file: " + filepath);
		
		String[] id = dh.getNames();

		System.out.println(Arrays.toString(id));
		
		//if (id == null) throw new OperationException(op,"Error reading dataset: " + datasetName);
		
		
		
	}

}
