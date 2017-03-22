package org.dawnsci.surfacescatter.test;

import static org.junit.Assert.*;

import org.dawnsci.surfacescatter.ui.SurfaceScatterPresenter;
import org.dawnsci.surfacescatter.ui.SurfaceScatterViewStart;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

public class RodTest {

	private SurfaceScatterViewStart ssvs;
	private SurfaceScatterPresenter ssp;
	private IDataset[] dataSimulator;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

	public void setup(){
		
//		Shell s =PlatformUI.getWorkbench().getWorkbenchWindows()[0].getShell();
		
		Shell s = new Shell();
		
		ssp = new SurfaceScatterPresenter();

		ssp.setImageFolderPath(null);

		ssvs = new SurfaceScatterViewStart(s, 
				null, 
				ssp.getNumberOfImages(), 
				ssp.getImage(0),
				ssp,
				null);

		ssp.setSsvs(ssvs);
		
	}
	
	public void generateSimulationData(){
		
		dataSimulator = new IDataset[10];
		
		for(int n =0; n<10 ; n++){
			dataSimulator[n] = DatasetFactory.ones(new int[] {1000, 1000});
		
			for(int i = 100+n*10; i<110+n*10; i++){
				for(int j = 100+n*10; j<110+n*10; j++){
					dataSimulator[n].set(100, i,j);
				}
			}
		}
	}
	
	
	
}
