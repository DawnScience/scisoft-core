package org.eclipse.dawnsci.analysis.dataset;

import java.lang.reflect.Field;
import java.util.Map;

import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.LazyDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.AxesMetadata;

public class SlicingUtils {
	private SlicingUtils() {
	}

	/**
	 * Workaround a bug with slicing a sliced view of a lazy dataset that has axes metadata added after first slicing
	 * @param ld
	 * @return sliced dataset
	 * @throws DatasetException
	 */
	public static Dataset sliceWithAxesMetadata(ILazyDataset ld) throws DatasetException {
		return sliceWithAxesMetadata(null, ld, null);
	}

	/**
	 * Workaround a bug with slicing a sliced view of a lazy dataset that has axes metadata added after first slicing
	 * @param ld
	 * @param s
	 * @return sliced dataset
	 * @throws DatasetException
	 */
	public static Dataset sliceWithAxesMetadata(ILazyDataset ld, SliceND s) throws DatasetException {
		return sliceWithAxesMetadata(null, ld, s);
	}

	/**
	 * Workaround a bug with slicing a sliced view of a lazy dataset that has axes metadata added after first slicing
	 * @param mon
	 * @param ld
	 * @param s
	 * @return sliced dataset
	 * @throws DatasetException
	 */
	public static Dataset sliceWithAxesMetadata(IMonitor mon, ILazyDataset ld, SliceND s) throws DatasetException {
		AxesMetadata amd = ld.getFirstMetadata(AxesMetadata.class);
		ld.clearMetadata(AxesMetadata.class);
		Dataset d = DatasetUtils.convertToDataset((ld instanceof IDataset td) ? (s == null ? td.clone() : td.getSliceView(s)) : clearOriginalMetadata(ld).getSlice(mon, s));
		ld.setMetadata(amd);
		if (amd != null && !(ld instanceof IDataset) && s != null) {
			AxesMetadata newAM = (AxesMetadata) amd.clone();
			int rank = ld.getRank();
			Slice[] slices = s.convertToSlice();
			for (int i = 0; i < rank; i++) {
				ILazyDataset[] axes = newAM.getAxis(i);
				if (axes == null) {
					continue;
				}
				Slice slice = slices[i];
				for (int j = 0; j < axes.length; j++) {
					ILazyDataset a = axes[j];
					SliceND aSlice = new SliceND(a.getShape());
					aSlice.setSlice(i, slice);
					axes[j] = axes[j].getSliceView(aSlice);
				}
				newAM.setAxis(i, axes);
			}
			d.setMetadata(newAM);
		} else {
			d.setMetadata(amd);
		}
		return d;
	}

	private static ILazyDataset clearOriginalMetadata(ILazyDataset cData) {
		ILazyDataset lData = cData;
		// XXX hack to fix cropping signal dataset as clearing metadata does not modify original metadata
		if (cData instanceof LazyDataset) {
			lData = lData.clone();
			try {
				Field f = LazyDataset.class.getDeclaredField("oMetadata");
				f.setAccessible(true);
				Object o = f.get(lData);
				if (o instanceof Map m && m.containsKey(AxesMetadata.class)) {
					m.remove(AxesMetadata.class);
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				// do nothing
			}
		}
		return lData;
	}

}
