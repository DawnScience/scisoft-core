/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.plotting.api.trace;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.dawnsci.plotting.api.metadata.Plot1DMetadata;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace.TraceType;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MaskMetadata;
import org.eclipse.january.metadata.UnitMetadata;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataPlotUtils {

	private final static Logger logger = LoggerFactory.getLogger(MetadataPlotUtils.class);
	
	public static void plotDataWithMetadata(IDataset data, final IPlottingSystem<?> system) {
		plotDataWithMetadata(data, system, true);
	}
	
	public static void plotDataWithMetadata(IDataset data, final IPlottingSystem<?> system, boolean clear) {
		if (data == null || data.getSize() == 1) {
			return;
		}
		
		IDataset x = null;
		IDataset y = null;
		IDataset mask = null;
		String dataname = data.getName();
		data = data.getSliceView().squeeze();
		data.setName(dataname);
		IDataset[] axes = getAxesFromMetadata(data);
		
		MaskMetadata mmd = data.getFirstMetadata(MaskMetadata.class);
		
		if (mmd != null) {
			mask = mmd.getMask().getSlice().squeeze();
		}
		
		if (data.getRank() == 2) {
			if (!system.is2D()) system.clear();
			x = axes == null ? null : axes[0];
			y = axes == null ? null : axes[1];
			
			if (x != null && x.getRank() == 2) {
				x = x.getSlice((Slice)null,new Slice(0,1)).squeeze();
			}
			if (y != null && y.getRank() == 2) {
				y = y.getSlice(new Slice(0,1),(Slice)null).squeeze();
			}
			
			if (x != null) x.setName(removeSquareBrackets(x.getName()));
			if (y != null) y.setName(removeSquareBrackets(y.getName()));
			
			final ITrace t = system.updatePlot2D(data, Arrays.asList(new IDataset[]{y,x}), null);
				
			final IDataset m = mask;

			Display.getDefault().syncExec(new Runnable() {

				@Override
				public void run() {
					if (t == null) return;
					((IImageTrace)t).setMask(m);
					if (!system.isDisposed())system.repaint();
				}
			});
				
			
		} else if (data.getRank() == 1) {
			x = axes == null ? null : axes[0];
			if (x != null) {
				x.setName(removeSquareBrackets(x.getName())+getUnit(x));
			}
			if (clear) {
				system.clearTraces();
				system.resetAxes();
			}
			ITrace t = system.updatePlot1D(x,Arrays.asList(new IDataset[]{data}),null).get(0);
			Plot1DMetadata pm = data.getFirstMetadata(Plot1DMetadata.class);
			if (pm != null && t instanceof ILineTrace) {
				Display d = Display.getDefault();
				d.asyncExec(() -> customizePlot(d, system, pm, (ILineTrace) t));
			}
		}
	}

	private static void customizePlot(Display d, final IPlottingSystem<?> system, Plot1DMetadata pm, ILineTrace lt) {
		lt.setLineWidth(pm.getLineWidth());
		lt.setPointSize(pm.getPointSize());
		lt.setPointStyle(pm.getPointStyle());
		TraceType ts;
		switch (pm.getLineStyle()) {
		case DASHED:
			ts = TraceType.DASH_LINE;
			break;
		case NONE:
			ts = TraceType.POINT;
			break;
		case SOLID:
		default:
			ts = TraceType.SOLID_LINE;
			break;
		}
		lt.setTraceType(ts);
		int[] c = pm.getRGBColor();
		if (c != null) {
			RGB rgb = new RGB(c[0], c[1], c[2]);
			lt.setTraceColor(new Color(d, rgb));
		}
		String n;
		n = pm.getPlotTitle();
		if (n != null) {
			system.setTitle(n);
		}
		n = pm.getXAxisName();
		if (n != null) {
			system.getSelectedXAxis().setTitle(n);
		}
		n = pm.getYAxisName();
		if (n != null) {
			system.getSelectedYAxis().setTitle(n);
		}

		n = pm.getLegendEntry();
		if (n != null) {
			lt.setName(n);
		}
	}

	private static String getUnit(IDataset ds) {
		
		UnitMetadata um = ds.getFirstMetadata(UnitMetadata.class);
		
		if (um == null) return "";
		
		return " [" + um.toString() + "]";
	}
	

	
	public static IImageTrace buildTrace(String name, IDataset data, IPlottingSystem<?> system) {
		return buildTrace(name, data, system, -1);
	
	}
	
	public static IImageTrace buildTrace(IDataset data, IPlottingSystem<?> system) {
		return buildTrace(data.getName(), data, system, -1);
	
	}
	
	public static IImageTrace buildTrace(String name, IDataset data, IPlottingSystem<?> system, int alpha) {
		IDataset x = null;
		IDataset y = null;
		
		
		IDataset[] axes = getAxesFromMetadata(data, false);
		
		x = axes == null ? null : axes[0];
		y = axes == null ? null : axes[1];
		
		if (x != null) x.setName(removeSquareBrackets(x.getName()));
		if (y != null) y.setName(removeSquareBrackets(y.getName()));
		
		IImageTrace it = system.createImageTrace(name);
		it.setAlpha(alpha);
		it.setData(data, Arrays.asList(new IDataset[]{y,x}), false);
		
		return it;
	
	}
	
	public static void switchData(String name, IDataset data, IImageTrace trace) {
		IDataset x = null;
		IDataset y = null;
		
		IDataset[] axes = getAxesFromMetadata(data, false);
		
		x = axes == null ? null : axes[0];
		y = axes == null ? null : axes[1];
		
		if (x != null) x.setName(removeSquareBrackets(x.getName()));
		if (y != null) y.setName(removeSquareBrackets(y.getName()));
		
		trace.setData(data, Arrays.asList(new IDataset[]{y,x}), false);
		trace.setDataName(name);
		
	}
	
	
	public static ILineTrace buildLineTrace(IDataset data, IPlottingSystem<?> system) {
		IDataset x = null;

		
		data = data.getSliceView().squeeze();
		
		IDataset[] axes = getAxesFromMetadata(data);
		
		x = axes == null ? null : axes[0];

		ILineTrace it = system.createLineTrace(data.getName());
		it.setData(x, data);
		
		return it;
	
	}
	
	public static IDataset[] getAxesForDimension(ILazyDataset data, int dim) {
		
		List<AxesMetadata> amd = null;
		
		try {
			amd = data.getMetadata(AxesMetadata.class);
		} catch (Exception e) {
			return new IDataset[0];
		}
		
		
		if (amd != null && !amd.isEmpty()) {
			AxesMetadata am = amd.get(0);
			ILazyDataset[] axis = am.getAxis(dim);
			IDataset[] out = new IDataset[axis.length];
			for (int i = 0; i < out.length; i++) {
				try {
					out[i] = axis[i] == null ? null : axis[i].getSlice();
				} catch (DatasetException e) {
				}
			}
			
			return out;
		}
		return null;

	}
	
	public static IDataset[] getAxesFromMetadata(IDataset data) {
		return getAxesFromMetadata(data,true);
	}
	
	public static IDataset[] getAxesFromMetadata(ILazyDataset data, boolean squeeze) {
		if (squeeze) data = data.getSliceView().squeezeEnds();
		return getAxesFromMetadata((ILazyDataset)data);
	}
	
	
	public static IDataset[] getAxesFromMetadata(ILazyDataset data) {
		data = data.getSliceView();
		
		List<AxesMetadata> amd = null;
		try {
			amd = data.getMetadata(AxesMetadata.class);
		} catch (Exception e) {
			return new IDataset[0];
		}
		
		
		if (amd != null && !amd.isEmpty()) {
			AxesMetadata am = amd.get(0);
			ILazyDataset[] axes = am.getAxes();
			IDataset[] out;
			if (axes.length > 0) {
				ILazyDataset lz0 = axes[0];
				ILazyDataset lz1 = null;
				if (data.getRank() > 1) {
					out = new IDataset[2];
					lz1 = axes[1];
				}else {
					out = new IDataset[1];
				}
				
				if (lz0 != null) {
					try {
						out[0] = DatasetUtils.sliceAndConvertLazyDataset(lz0).squeeze();
					} catch (DatasetException e) {
					}
				}
				if (lz1 != null) {
					try {
						out[1] = DatasetUtils.sliceAndConvertLazyDataset(lz1).squeeze();
					} catch (DatasetException e) {
					}
				}
				return out;
			}
		}
		return null;
	}

	private static Pattern SQUARE_BRACKET_REGEX = Pattern.compile("\\[(.+?)\\]$");

	/**
	 * Remove substring delimited by square brackets only if it is positioned at the end
	 * @param string
	 * @return edited string
	 */
	public static String removeSquareBrackets(String string) {
		if (string == null) return null;
		return SQUARE_BRACKET_REGEX.matcher(string).replaceAll("");
	}

	public static IDataset[] getAxesAsIDatasetArray(ILazyDataset data) {
		IDataset[] out = new IDataset[data.getRank()];
		
		AxesMetadata md = data.getFirstMetadata(AxesMetadata.class);
		
		if (md == null) return out;
		
		ILazyDataset[] axes = md.getAxes();
		
		if (axes == null) return out;
		
		for (int i = 0 ; i < axes.length; i++) {
			try {
				out[i] = axes[i] == null ? null : axes[i].getSlice();
			} catch (Exception e) {
				logger.error("Could not slice axes",e);
			}
			
		}
		
		return out;
	}
}
