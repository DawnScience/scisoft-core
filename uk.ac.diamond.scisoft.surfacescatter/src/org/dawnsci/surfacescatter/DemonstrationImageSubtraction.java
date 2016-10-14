package org.dawnsci.surfacescatter;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

public class DemonstrationImageSubtraction {
	
	public static IDataset TestSubtraction(IDataset image){
		
		IDataset imageCopy = image.clone();
		IDataset imageMod = Maths.subtract(image, imageCopy, null);
		
		return imageMod;
	}

	public static IDataset TestCopy(IDataset image){
		
		IDataset imageCopy = image.clone();

		return imageCopy;
	}
	
}
