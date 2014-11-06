package uk.ac.diamond.scisoft.analysis.processing.operations.expressions;

import org.dawb.common.services.ServiceManager;
import org.dawb.common.services.expressions.IExpressionEngine;
import org.dawb.common.services.expressions.IExpressionService;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;

import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadataImpl;

public class Expression1DOperation<T extends Expression1DModel> extends AbstractOperation<Expression1DModel ,OperationData> {

	IExpressionEngine engine;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.expressions.Expression1DOperation";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		if (engine == null) {
			try {
				IExpressionService service = (IExpressionService)ServiceManager.getService(IExpressionService.class);
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
		outaxis.setName("custom_x_axis");
		
		AxesMetadata am = new AxesMetadataImpl(1);
		am.setAxis(0, new ILazyDataset[]{outaxis});
		outdata.setMetadata(am);
		
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
			axis= axes[0].getSlice();
			engine.addLoadedVariable("xaxis", axis);
		} else {
			axis = DatasetFactory.createRange(input.getSize(), Dataset.FLOAT64);
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
