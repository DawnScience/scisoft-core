package uk.ac.diamond.scisoft.analysis.processing.operations.expressions;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.expressions.IExpressionEngine;
import org.eclipse.dawnsci.analysis.api.expressions.IExpressionService;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.osgi.services.ServiceProvider;

public class Expression2DOperation<T extends Expression2DModel> extends Expression1DOperation<T> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.expressions.Expression2DOperation";
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
		addAdditionalValues(input,engine);
		
		IDataset outdata = evaluateData(input, model.getDataExpression(),2);
		IDataset outaxisx = evaluateData(axes[0], model.getAxisExpressionX(), axes[0].getRank());
		IDataset outaxisy = evaluateData(axes[1], model.getAxisExpressionY(), axes[1].getRank());
		
		outdata.setName("custom_expression");
		if (model.getAxisExpressionY() != null && !model.getAxisExpressionY().isEmpty()) outaxisy.setName("custom_y_axis");
		//outaxisy.setShape(new int[]{outaxisy.getShape()[0],1});
		if (model.getAxisExpressionX() != null && !model.getAxisExpressionX().isEmpty()) outaxisx.setName("custom_x_axis");
		//outaxisx.setShape(new int[]{1,outaxisx.getShape()[0]});
		
		if (Arrays.equals(input.getShape(), outdata.getShape())) {
			copyMetadata(input, outdata);
		}
		
		AxesMetadata am;
		try {
			am = MetadataFactory.createMetadata(AxesMetadata.class, 2);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
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
		if (axes != null) {
			try {
				if (axes[1] != null) {
					axisx= axes[1].getSlice().squeeze();
				}

				if (axes[0] != null) {
					axisy= axes[0].getSlice().squeeze();
				}
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
		}
		
		if (axisx == null) axisx = DatasetFactory.createRange(input.getShape()[0]);
		if (axisy == null) axisy = DatasetFactory.createRange(input.getShape()[1]);
		
		engine.addLoadedVariable("xaxis", axisx);
		engine.addLoadedVariable("yaxis", axisy);
		
		return new IDataset[]{axisx,axisy};
	}
	
	protected void addAdditionalValues(IDataset input, IExpressionEngine engine) throws OperationException {
		//do nothing
	}
}
