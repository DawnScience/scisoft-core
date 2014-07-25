package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IOperationModel;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;

public class ThresholdMask implements IOperation {

	private IOperationModel model;

	@Override
	public String getOperationDescription() {
		return "Threshold Mask";
	}

	@Override
	public String getId() {
		return " uk.ac.diamond.scisoft.analysis.processing.operations.thresholdMask";
	}

	@Override
	public void setDataset(IRichDataset... data) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public OperationData execute(OperationData slice, IMonitor monitor) throws OperationException {
		
		final BooleanDataset mask = (BooleanDataset)slice.getMask();
		if (!isCompatible(slice.getData().getShape(), mask.getShape())) {
			throw new OperationException(this, "Mask is incorrect shape!");
		}
		
		try {
			Double upper  = (Double)model.get("Upper");
			Double lower  = (Double)model.get("Lower");
			
			// Apply a threshold
			PositionIterator it = new PositionIterator(mask.getShape());
			while (it.hasNext()) {
								
				int[] pos = it.getPos();
				if (slice.getData().getDouble(pos)>upper || slice.getData().getDouble(pos)<lower) {
					mask.set(false, pos);
				}
			}
			monitor.worked(1);
			
			return slice;
		} catch (Exception ne) {
			throw new OperationException(this, ne);
		}
	}

	protected static boolean isCompatible(final int[] ashape, final int[] bshape) {

		List<Integer> alist = new ArrayList<Integer>();

		for (int a : ashape) {
			if (a > 1) alist.add(a);
		}

		final int imax = alist.size();
		int i = 0;
		for (int b : bshape) {
			if (b == 1)
				continue;
			if (i >= imax || b != alist.get(i++))
				return false;
		}

		return i == imax;
	}

	@Override
	public void setModel(IOperationModel model) throws IllegalArgumentException {
		this.model = model;
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
