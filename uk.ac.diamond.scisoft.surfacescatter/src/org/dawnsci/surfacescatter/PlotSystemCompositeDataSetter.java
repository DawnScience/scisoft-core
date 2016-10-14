package org.dawnsci.surfacescatter;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.SliceND;

public class PlotSystemCompositeDataSetter{



    private static IDataset image;
    
     
    public static IDataset imageSetter(ExampleModel model, int selection) {
        
        SliceND slice = new SliceND(model.getDatImages().getShape());
        slice.setSlice(0, 1, 2, 1);
		IDataset i = null;
		try {
			i = model.getDatImages().getSlice(slice);
		} catch (DatasetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		i.squeeze();
		image =i;
		
		    try {
				slice.setSlice(0, selection, selection+1, 1);
				i = model.getDatImages().getSlice(slice);
				i.squeeze();
				image = i;
		    } 
		    catch (Exception f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
			}
		
		return image;
    
    }
}
        
        
