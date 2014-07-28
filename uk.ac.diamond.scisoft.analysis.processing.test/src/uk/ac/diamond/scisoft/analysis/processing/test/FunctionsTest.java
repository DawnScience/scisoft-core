package uk.ac.diamond.scisoft.analysis.processing.test;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.AggregateDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.FunctionFactory;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PseudoVoigt;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.optimize.GeneticAlg;
import uk.ac.diamond.scisoft.analysis.processing.Activator;
import uk.ac.diamond.scisoft.analysis.processing.IExecutionVisitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IOperationService;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.RichDataset;
import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.FittingModel;

public class FunctionsTest {

	private static IOperationService service;
	
	/**
	 * Manually creates the service so that no extension points have to be read.
	 * 
	 * We do this use annotations
	 * @throws Exception 
	 */
	@BeforeClass
	public static void before() throws Exception {
		service = (IOperationService)Activator.getService(IOperationService.class);
		
		// Just read all these operations.
		service.createOperations(service.getClass().getClassLoader(), "uk.ac.diamond.scisoft.analysis.processing.operations");
	}
	
	@Test
	public void testPolynomial() throws Exception {
		
		final IDataset       indices = AbstractDataset.arange(1000, AbstractDataset.INT);
		final IRichDataset   rich    = new RichDataset(indices, null);

		final IOperation functionOp = service.findFirst("function");
		
		// y(x) = a_0 x^n + a_1 x^(n-1) + a_2 x^(n-2) + ... + a_(n-1) x + a_n
		final IFunction poly = FunctionFactory.getFunction("Polynomial", 3/*x^2*/, 5.3/*x*/, 9.4/*m*/);
		functionOp.setModel(new AbstractOperationModel() {
			@SuppressWarnings("unused")
			public IFunction getFunction() {
				return poly;
			}
		});
		
		service.executeSeries(rich, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				
				System.out.println(result.getData().getName());
				for (int i = 0; i < result.getData().getShape()[0]; i++) {
					
					// Get result
					double opVal   = result.getData().getDouble(i);
					
					// Calc polynomial
					double v = poly.getParameterValue(0);
					double p = indices.getDouble(i);
					for (int j = 1; j < poly.getNoOfParameters(); j++) {
						v = v * p + poly.getParameterValue(j);
					}

					if (opVal != v) {
						throw new Exception("Did not process polynomial function correctly! ");
					}
				}
			}			
		}, functionOp);

	}
	
	static final int[] defaultPeakPos;
	static {
		int[] tmp = new int[] { 100, 200, 300, 400, 500, 150, 250, 350, 450 };
		Arrays.sort(tmp);
		defaultPeakPos = tmp;
	}
	static final int defaultFWHM = 20;
	static final int defaultArea = 50;
	static final int dataRange = 550;
	static final DoubleDataset xAxis = (DoubleDataset) AbstractDataset.arange(0, dataRange, 1, Dataset.FLOAT64);
	static final boolean backgroundDominated = true;
	static final boolean autoStopping = true;
	static final double threshold = 0.10;
	static final int numPeaks = -1;
	static final int smoothing = 5;
	static final long seed = 12357L;

	private volatile int count;
	@Test
	public void testPseudoVoigtGenetic() throws Exception {
		

		final IOperation fittingOp = service.findFirst("fitting");
		
		// We do 10 Peak fits
		final AbstractDataset     pseudo = generatePseudoVoigt(defaultPeakPos.length);
		final AggregateDataset    aggy   = new AggregateDataset(true, pseudo, pseudo, pseudo, pseudo, pseudo);
		final IRichDataset   rich = new RichDataset(aggy, null);
		rich.setSlicing("all", "");
		
		fittingOp.setDataset(rich);
		// Cannot send a concrete GeneticAlg here because does not work in parallel.
		fittingOp.setModel(new FittingModel(xAxis, PseudoVoigt.class, GeneticAlg.class, 0.0001, seed, smoothing, numPeaks, threshold, autoStopping, backgroundDominated));      
	
		count = 0;
		service.executeSeries(rich, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
			@Override
			public void executed(OperationData result, IMonitor monitor) throws Exception {
				
				System.out.println(result.getData().getName());
				
				final List<CompositeFunction> fittedPeakList = (List<CompositeFunction>)result.getAuxData()[0];
				
				double[] fittedPeakPos = new double[fittedPeakList.size()];
				int i = 0;
				for (CompositeFunction p : fittedPeakList) {
					fittedPeakPos[i++] = p.getPeak(0).getPosition();
				}
				Arrays.sort(fittedPeakPos);

				assertEquals("The number of peaks found was not the same as generated", defaultPeakPos.length, fittedPeakPos.length);

				for (int k = 0; k < fittedPeakPos.length; k++) {
					assertEquals(defaultPeakPos[k], fittedPeakPos[k], 2d);
				}
				count++;

			}			
		}, fittingOp);

		
		if (count!=5) throw new Exception("Tiled 10x"+dataRange+" did not fit ten times!");
	}
	
	@Test
	public void testPseudoVoigtGeneticParallel() throws Exception {
		

		final IOperation fittingOp = service.findFirst("fitting");
		
		// We do 10 Peak fits
		final AbstractDataset     pseudo = generatePseudoVoigt(defaultPeakPos.length);
		final AggregateDataset    aggy   = new AggregateDataset(true, pseudo, pseudo, pseudo, pseudo, pseudo);
		final IRichDataset   rich = new RichDataset(aggy, null);
		rich.setSlicing("all", "");
		
		fittingOp.setDataset(rich);
		
		// Cannot send a concrete GeneticAlg here because does not work in parallel.
		fittingOp.setModel(new FittingModel(xAxis, PseudoVoigt.class, GeneticAlg.class, 0.0001, seed, smoothing, numPeaks, threshold, autoStopping, backgroundDominated));      
	
		count = 0;
		try {
			service.setParallelTimeout(Long.MAX_VALUE);
			service.executeParallelSeries(rich, new IMonitor.Stub(), new IExecutionVisitor.Stub() {
				@Override
				public void executed(OperationData result, IMonitor monitor) throws Exception {

					System.out.println(result.getData().getName());

					final List<CompositeFunction> fittedPeakList = (List<CompositeFunction>)result.getAuxData()[0];

					double[] fittedPeakPos = new double[fittedPeakList.size()];
					int i = 0;
					for (CompositeFunction p : fittedPeakList) {
						fittedPeakPos[i++] = p.getPeak(0).getPosition();
					}
					Arrays.sort(fittedPeakPos);

					assertEquals("The number of peaks found was not the same as generated", defaultPeakPos.length, fittedPeakPos.length);

					for (int k = 0; k < fittedPeakPos.length; k++) {
						assertEquals(defaultPeakPos[k], fittedPeakPos[k], 2d);
					}
					try {
						// This sleep simply introduces some random behaviour
						// on the parallel jobs so that we definitely get a different order.
						final long time = Math.round(Math.random()*1000);
						Thread.sleep(time);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					count++;

				}			
			}, fittingOp);
		} finally {
			service.setParallelTimeout(5000);
		}

		
		if (count!=5) throw new Exception("Tiled 10x"+dataRange+" did not fit ten times!");
	}

	private DoubleDataset generatePseudoVoigt(int numPeaks) {
		CompositeFunction function = new CompositeFunction();
		if (numPeaks > defaultPeakPos.length)
			numPeaks = defaultPeakPos.length;
		for (int i = 0; i < numPeaks; i++) {
			function.addFunction(new PseudoVoigt(defaultPeakPos[i] - 20, defaultPeakPos[i] + 20, defaultFWHM,
					defaultArea));
		}
		DoubleDataset data = function.calculateValues(xAxis);
		return (DoubleDataset) Maths.add(data, generateBackground());
	}
	private DoubleDataset generateBackground() {
		CompositeFunction comp = new CompositeFunction();
		comp.addFunction(new Gaussian(-10, 10, dataRange / 4, dataRange / 2));
		return comp.calculateValues(DoubleDataset.createRange(dataRange));
	}

	
}

