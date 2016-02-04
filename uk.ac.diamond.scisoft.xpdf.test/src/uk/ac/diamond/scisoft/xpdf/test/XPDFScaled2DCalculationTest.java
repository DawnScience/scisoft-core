package uk.ac.diamond.scisoft.xpdf.test;

import static org.junit.Assert.*;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.xpdf.XPDFScaled2DCalculation;

public class XPDFScaled2DCalculationTest {

	Dataset theta, phi, fullSize, fullSize1D;
	final int nx = 4096, ny = 2048, nMax = 512;
	
	@Before
	public void setUp() throws Exception {
		// Set up the full size arrays
		// One dimensional arrays
		theta = Maths.multiply(DoubleDataset.createRange(0.5, ny+0.5, 1.0), Math.PI/ny);
		phi = Maths.multiply(DoubleDataset.createRange(0.5, nx+0.5, 1.0), 2*Math.PI/nx);
		// spread to two dimensions
		theta = DatasetUtils.repeat(theta.reshape(1, ny), new int[]{nx}, 0);
		phi = DatasetUtils.repeat(phi.reshape(nx, 1), new int[]{ny}, 1);
		fullSize = realY36(phi, theta);
		fullSize1D = realY06(theta);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRun() {
		XPDFScaled2DCalculation why36 = new XPDFScaled2DCalculation(nMax) {
			@Override
			protected Dataset calculate(Dataset phi, Dataset theta) {
				return realY36(phi, theta);
			}
		};
	
		Dataset scaled = why36.run(phi, theta);
		Dataset difference = Maths.subtract(scaled, fullSize);
		Dataset squaredDifference = Maths.square(difference);
		double rmsDifference = Math.sqrt((double) squaredDifference.mean());
		double maxError = 5e-2;
		assertTrue("Difference in scaled 2D calculation too large: " + rmsDifference, rmsDifference < maxError);
		
	}

	@Test
	public void testRunTwoTheta() {
		XPDFScaled2DCalculation why06 = new XPDFScaled2DCalculation(nMax) {
			@Override
			protected Dataset calculateTwoTheta(Dataset theta) {
				return realY06(theta);
			}
		};
		
		Dataset scaled = why06.runTwoTheta(theta);
		Dataset difference = Maths.subtract(scaled, fullSize1D);
		Dataset squaredDifference = Maths.square(difference);
		double rmsDifference = Math.sqrt((double) squaredDifference.mean());
		double maxError = 5e-2;
		assertTrue("Difference in scaled 1D calculation too large: " + rmsDifference, rmsDifference < maxError);
		
	
	}

	// ℝe(Y³₆(θ, φ))
	private Dataset realY36(Dataset phi, Dataset theta) {
		double leader = -1/32.*Math.sqrt(1365/Math.PI);
		// -1/32 √(1365/π) cos 3φ sin³ θ (11 cos³ θ - 3 cos θ)
		return Maths.multiply(
				leader,
				Maths.multiply(
						Maths.cos(Maths.multiply(3, phi)),
						Maths.multiply(
								Maths.power(Maths.sin(theta), 3),
								Maths.subtract(
										Maths.multiply(11, Maths.power(Maths.cos(theta), 3)),
										Maths.multiply(3, Maths.cos(theta))))));
	}
	// Y⁰₆(θ, φ)
	private Dataset realY06(Dataset theta) {
		double leader = 1/32.*Math.sqrt(13/Math.PI);
		Dataset cosSquared = Maths.square(Maths.cos(theta));
		// 1/32 √(13/π) (231 cos⁶ θ - 315 cos⁴ θ + 105 cos² θ - 5), evaluated using Horner's scheme
		return Maths.multiply(
				leader,
				Maths.add(-5, Maths.multiply(cosSquared,
						Maths.add(105, Maths.multiply(cosSquared,
								Maths.add(-315, Maths.multiply(cosSquared,
										231
								))
						))
				))
			);
								
	
	}
	
}
