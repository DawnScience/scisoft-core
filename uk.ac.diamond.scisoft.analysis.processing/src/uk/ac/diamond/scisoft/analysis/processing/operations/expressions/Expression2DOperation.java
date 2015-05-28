package uk.ac.diamond.scisoft.analysis.processing.operations.expressions;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.expressions.IExpressionEngine;
import org.eclipse.dawnsci.analysis.api.expressions.IExpressionService;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;

public class Expression2DOperation<T extends Expression2DModel> extends Expression1DOperation<Expression2DModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.expressions.Expression2DOperation";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		if (engine == null) {
			try {
				IExpressionService service = ExpressionServiceHolder.getExpressionService();
				engine = service.getExpressionEngine();
			} catch (Exception e) {
				throw new OperationException(this, e.getMessage());
			}
		}
		
		engine.addLoadedVariable("data", input);
		IDataset[] axes = addAxesToEngine(input);
		addAdditionalValues(input,engine);
		
		IDataset outdata = evaluateData(input, model.getDataExpression(),2);
		IDataset outaxisx = evaluateData(axes[0], model.getAxisExpressionX(), 1);
		IDataset outaxisy = evaluateData(axes[1], ((Expression2DModel)model).getAxisExpressionY(), 1);
		
		outdata.setName("custom_expression");
		if (((Expression2DModel)model).getAxisExpressionY() != null && !((Expression2DModel)model).getAxisExpressionY().isEmpty()) outaxisy.setName("custom_y_axis");
		outaxisy.setShape(new int[]{outaxisy.getShape()[0],1});
		if (model.getAxisExpressionX() != null && !model.getAxisExpressionX().isEmpty()) outaxisx.setName("custom_x_axis");
		outaxisx.setShape(new int[]{1,outaxisx.getShape()[0]});
		
		if (Arrays.equals(input.getShape(), outdata.getShape())) {
			copyMetadata(input, outdata);
		}
		
		AxesMetadata am = new AxesMetadataImpl(2);
		am.setAxis(0, outaxisy);
		am.setAxis(1, outaxisx);
		outdata.setMetadata(am);
		
		return new OperationData(outdata);
	}
	
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	
	protected IDataset[] addAxesToEngine(IDataset input) {
		//1D should be in [0]
		ILazyDataset[] axes = getFirstAxes(input);
		IDataset axisx = null;
		IDataset axisy = null;
		if (axes != null ) {
			if (axes[0] != null) {
				axisx= axes[1].getSlice().squeeze();
				
			}
			
			if (axes[0] != null) {
				axisy= axes[0].getSlice().squeeze();
				
			}
			
		}
		
		if (axisx == null) axisx = DatasetFactory.createRange(input.getShape()[0], Dataset.FLOAT64);
		if (axisy == null) axisy = DatasetFactory.createRange(input.getShape()[1], Dataset.FLOAT64);
		
		engine.addLoadedVariable("xaxis", axisx);
		engine.addLoadedVariable("yaxis", axisy);
		
		return new IDataset[]{axisx,axisy};
	}
	
	protected void addAdditionalValues(IDataset input, IExpressionEngine engine) throws OperationException {
		//do nothing
	}
}
