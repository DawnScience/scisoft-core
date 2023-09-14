package uk.ac.diamond.scisoft.analysis.processing.operations.expressions;

import org.eclipse.dawnsci.analysis.api.expressions.IExpressionEngine;
import org.eclipse.dawnsci.analysis.api.expressions.IExpressionService;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.osgi.services.ServiceProvider;

public class Expression1DOperation<T extends Expression1DModel> extends AbstractOperation<T ,OperationData> {

	IExpressionEngine engine;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.expressions.Expression1DOperation";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		if (engine == null) {
			try {
				IExpressionService service = ServiceProvider.getService(IExpressionService.class);
				engine = service.getExpressionEngine();
			} catch (Exception e) {
				throw new OperationException(this, e.getMessage());
			}
		}
		
		engine.addLoadedVariable("data", input);
		IDataset[] axes = addAxesToEngine(input);
		
		IDataset outdata = evaluateData(input, model.getDataExpression(),1);
		
		IDataset outaxis = evaluateData(axes[0], model.getAxisExpressionX(), 1);
		
		outdata.setName("custom_expression");
		if (model.getAxisExpressionX() != null && !model.getAxisExpressionX().isEmpty()) outaxis.setName("custom_x_axis");
		
		AxesMetadata am;
		try {
			am = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
		am.setAxis(0, outaxis);
		outdata.setMetadata(am);
		
		if (input.getSize() == outdata.getSize()) {
			copyMetadata(input, outdata, false);
		}
		
		return new OperationData(outdata);
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	protected IDataset[] addAxesToEngine(IDataset input) {
		//1D should be in [0]
		ILazyDataset[] axes = getFirstAxes(input);
		IDataset axis;
		if (axes != null && axes[0] != null) {
			try {
				axis= axes[0].getSlice();
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
			engine.addLoadedVariable("xaxis", axis);
		} else {
			axis = DatasetFactory.createRange(input.getSize());
			engine.addLoadedVariable("xaxis", axis);
		}
		
		return new IDataset[]{axis};
	}
	
	protected IDataset evaluateData(IDataset input, String expression, int expectedRank) throws OperationException {
		if (expression ==  null || expression.isEmpty()) {
			return input;
		} else {
			IDataset outdata = null;
			try {
				engine.createExpression(expression);
			} catch (Exception e) {
				throw new OperationException(this, e.getMessage());
			}
			
			try {
				Object ob =engine.evaluate();
				if (ob instanceof IDataset && ((IDataset)ob).getRank() == expectedRank) {
					outdata = (IDataset)ob;
				} else {
					throw new OperationException(this, expression = " :expression returned invalid object");
				}
			} catch (Exception e) {
				throw new OperationException(this, e.getMessage());
			}
			
			return outdata;
		}
		
	}
	
//	protected IDataset evaluateAxis() {
//		if (model.getAxisExpressionX() ==  null) {
//			outaxis = axis;
//		} else {
//			try {
//				engine.createExpression(model.getAxisExpressionX());
//			} catch (Exception e) {
//				throw new OperationException(this, e.getMessage());
//			}
//			
//			try {
//				Object ob =engine.evaluate();
//				if (ob instanceof IDataset && ((IDataset)ob).getRank() == 1) {
//					outaxis = (IDataset)ob;
//				} else {
//					throw new OperationException(this, "Data expression returned invalid object");
//				}
//			} catch (Exception e) {
//				throw new OperationException(this, e.getMessage());
//			}
//			
//		}
//	}

}
