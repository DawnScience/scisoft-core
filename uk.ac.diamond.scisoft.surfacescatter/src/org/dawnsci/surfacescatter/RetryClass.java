package org.dawnsci.surfacescatter;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

public class RetryClass {

	public Dataset retryConcat(IDataset[] rawImageArray, int cutOff, int flag) {
		
		Dataset rawImageConcat = DatasetFactory.createFromObject(0);
		
		try {
			rawImageConcat = DatasetUtils.concatenate(rawImageArray, 0);
		} catch (Exception e) {
			// connection failed, try again.
			try {
				Thread.sleep(1000);
				flag++;
				if(flag>= cutOff) {
					return null;
				}
			} catch (InterruptedException f) {
			};

			retryConcat(rawImageArray, cutOff, flag);
		}
		
		
		return rawImageConcat;
	}
	
}
