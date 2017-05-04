package org.dawnsci.surfacescatter;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.Metadata;

public class RodObjectNexusFileWriterLauncher {

	public OperationData RodObjectNexusFileWriterLauncher (ExampleModel model,
											   IDataset input,
					                           SuperModel sm,
					                           AnalaysisMethodologies.Methodology am,
					                           int trackingMarker,
					                           int k){
		
		OneDFittingModel odfm = new OneDFittingModel();
		odfm.setInitialLenPt(sm.getInitialLenPt());
		odfm.setLenPt(model.getLenPt());
		odfm.setFitPower(model.getFitPower());
		odfm.setBoundaryBox(model.getBoundaryBox());
		odfm.setDirection(am);
		
		if (trackingMarker != 3){
			
			double[] p = sm.getLocationList().get(k);
			int[] pt = new int[]{(int) p[0], (int) p[1]}; 
			int[] len = sm.getInitialLenPt()[0]; 
			int[][] lenPt = new int[][] {len,pt};
			if(p[0] != 0 && p[1] != 0){
				odfm.setLenPt(lenPt);
			}
			else{
				odfm.setLenPt(sm.getInitialLenPt());
			}
			
		}
		else{
			odfm.setLenPt(sm.getInitialLenPt());
		}
		
				
		Metadata md = new Metadata();
		Map<String, Integer> dumMap = new HashMap<String, Integer>();
		dumMap.put("one", 1);
		md.initialize(dumMap);
		
		ILazyDataset  ild = null;
		
		SourceInformation  si =new SourceInformation("dummy", "dummy2", ild);
		
		SliceFromSeriesMetadata sfsm = new SliceFromSeriesMetadata(si);
		
		input.setMetadata(sfsm);
		
		input.setMetadata(md);
		
		OneDFittingUsingIOperation odfuio = new OneDFittingUsingIOperation();
		odfuio.setModel(odfm);
		
		return odfuio.execute(input, null);
	}
}