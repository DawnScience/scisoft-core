package org.dawnsci.surfacescatter;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;

public class FastFourierTransform {

	/**
	 * @author Orlando Selenu
	 *
	 */

	/**
	 * The Fast Fourier Transform (generic version, with NO optimizations).
	 *
	 * @param inputReal
	 *            an array of length n, the real part
	 * @param inputImag
	 *            an array of length n, the imaginary part
	 * @param DIRECT
	 *            TRUE = direct transform, FALSE = inverse transform
	 * @return a new array of length 2n
	 */
	
	
	
	public static IDataset fftModeledYValuesDataset(IDataset inputReal) {

		return computedYOutputDataset(inputReal, fft(inputReal));
		
	}
	
	public static double[] fftModeledYValuesArray (IDataset inputReal) {

		return computedYOutputArray(inputReal, fft(inputReal));
		
	}
	
	public static double[] fftModeledYValuesArray (double[] probeArray,IDataset inputReal) {

		return computedYOutputArray(probeArray, fft(inputReal));
		
	}
	
	public static double[] fftModeledYValuesArray (IDataset probeArray,IDataset inputReal) {

		return computedYOutputArray(probeArray, fft(inputReal));
		
	}
	
	public static IDataset fftModeledYValuesDataset (IDataset probeArray,IDataset inputReal) {

		return computedYOutputDataset(probeArray, fft(inputReal));
		
	}
	
	
	private static double[][] fft(IDataset inputReal) {
	
		double [] inputRealArray = new double[inputReal.getSize()];
		
		for(int i = 0; i<inputReal.getSize(); i++) {
			inputRealArray[i] = inputReal.getDouble(i);
		}
		
		return fft(inputRealArray, null, true);
	}
		
	public static double[][] fft(final double[] inputReal, double[] inputImag, boolean DIRECT) {
		// - n is the dimension of the problem
		// - nu is its logarithm in base e
		int n = inputReal.length;

		// If n is a power of 2, then ld is an integer (_without_ decimals)
		double ld = Math.log(n) / Math.log(2.0);

		// Here I check if n is a power of 2. If exist decimals in ld, I quit
		// from the function returning null.
		if (((int) ld) - ld != 0) {
			System.out.println("The number of elements is not a power of 2.");
			return null;
		}

		// Declaration and initialization of the variables
		// ld should be an integer, actually, so I don't lose any information in
		// the cast
		int nu = (int) ld;
		int n2 = n / 2;
		int nu1 = nu - 1;
		double[] xReal = new double[n];
		double[] xImag = new double[n];
		double tReal, tImag, p, arg, c, s;

		// Here I check if I'm going to do the direct transform or the inverse
		// transform.
		double constant;
		if (DIRECT)
			constant = -2 * Math.PI;
		else
			constant = 2 * Math.PI;

		// I don't want to overwrite the input arrays, so here I copy them. This
		// choice adds \Theta(2n) to the complexity.
		for (int i = 0; i < n; i++) {
			xReal[i] = inputReal[i];
			xImag[i] = inputImag[i];
		}

		// First phase - calculation
		int k = 0;
		for (int l = 1; l <= nu; l++) {
			while (k < n) {
				for (int i = 1; i <= n2; i++) {
					p = bitreverseReference(k >> nu1, nu);
					// direct FFT or inverse FFT
					arg = constant * p / n;
					c = Math.cos(arg);
					s = Math.sin(arg);
					tReal = xReal[k + n2] * c + xImag[k + n2] * s;
					tImag = xImag[k + n2] * c - xReal[k + n2] * s;
					xReal[k + n2] = xReal[k] - tReal;
					xImag[k + n2] = xImag[k] - tImag;
					xReal[k] += tReal;
					xImag[k] += tImag;
					k++;
				}
				k += n2;
			}
			k = 0;
			nu1--;
			n2 /= 2;
		}

		// Second phase - recombination
		k = 0;
		int r;
		while (k < n) {
			r = bitreverseReference(k, nu);
			if (r > k) {
				tReal = xReal[k];
				tImag = xImag[k];
				xReal[k] = xReal[r];
				xImag[k] = xImag[r];
				xReal[r] = tReal;
				xImag[r] = tImag;
			}
			k++;
		}

		// Here I have to mix xReal and xImag to have an array (yes, it should
		// be possible to do this stuff in the earlier parts of the code, but
		// it's here to readibility).
		
		
		
		double[] newArray = new double[xReal.length * 2];
		
		double[] newRealOutArray = new double[xReal.length];
		double[] newImOutArray = new double[xReal.length];
		
		double[][] output = new double[][] {newRealOutArray, newImOutArray};
		
		double radice = 1 / Math.sqrt(n);
		for (int i = 0; i < newArray.length; i += 2) {
			int i2 = i / 2;
			// I used Stephen Wolfram's Mathematica as a reference so I'm going
			// to normalize the output while I'm copying the elements.
			newRealOutArray[i] = xReal[i2] * radice;
			newImOutArray[i] = xImag[i2] * radice;
		}
		return output;
	}

	/**
	 * The reference bitreverse function.
	 */
	private static int bitreverseReference(int j, int nu) {
		int j2;
		int j1 = j;
		int k = 0;
		for (int i = 1; i <= nu; i++) {
			j2 = j1 / 2;
			k = 2 * k + j1 - 2 * j2;
			j1 = j2;
		}
		return k;
	}

	private static  IDataset computedYOutputDataset(IDataset xRange, double[][] coefficients) {
		
		IDataset yOutput = xRange.clone();
		
		//xRange is just a series of integers 0-N where N is the end
		
		
		///base frequency for each FFT bin. Remember coeffiecients[n] is the real and coeffiecients[n+1] the imaginary
		// components for 1 bin so frequency = (bin_id * freq/2) / (N/2). freq is sample frequency, N the size of the FFT (which is coefficients.length).
		
		for(int j =0; j<xRange.getSize(); j++) {
			
			double xVal = (double) j; 
			
			double yVal = 0;
			
			double baseFrequency = (1/(2*xRange.getSize())) * coefficients.length/2;
			
			double[] realArray = new double[coefficients.length/2];
			double[] imArray = new double[coefficients.length/2];
			
			for(int n = 0; n<coefficients.length; n++) {
				
				realArray[n] = coefficients[0][n]*Math.cos(baseFrequency*xVal);
				imArray[n] = -1*coefficients[1][n]*Math.sin(baseFrequency*xVal);
				
				yVal+= realArray[n];
				yVal+= imArray[n];
			}
			
			yOutput.set(yVal, j);
		}
		
		
		return yOutput;
	}
	
	private static double[] computedYOutputArray(IDataset xRange, double[][] coefficients) {
		
		double[] yOutput = new double[xRange.getSize()];
		
		//xRange is just a series of integers 0-N where N is the end
		
		
		///base frequency for each FFT bin. Remember coeffiecients[n] is the real and coeffiecients[n+1] the imaginary
		// components for 1 bin so frequency = (bin_id * freq/2) / (N/2). freq is sample frequency, N the size of the FFT (which is coefficients.length).
		
		for(int j =0; j<xRange.getSize(); j++) {
			
			double xVal = xRange.getDouble(j); 
			
			double yVal = 0;
			
			double baseFrequency = (1/(2*xRange.getSize())) * coefficients.length/2;
			
			double[] realArray = new double[coefficients.length/2];
			double[] imArray = new double[coefficients.length/2];
			
			for(int n = 0; n<coefficients.length; n++) {
				
				realArray[n] = coefficients[0][n]*Math.cos(baseFrequency*xVal);
				imArray[n] = -1*coefficients[1][n]*Math.sin(baseFrequency*xVal);
				
				yVal+= realArray[n];
				yVal+= imArray[n];
			}
			
			yOutput[j]=yVal;
		}
		
		
		return yOutput;
	}
	
	private static double[] computedYOutputArray(double[] probePoints, double[][] coefficients) {
		
		double[] yOutput = new double[probePoints.length];
		
		//xRange is just a series of integers 0-N where N is the end
		
		
		///base frequency for each FFT bin. Remember coeffiecients[n] is the real and coeffiecients[n+1] the imaginary
		// components for 1 bin so frequency = (bin_id * freq/2) / (N/2). freq is sample frequency, N the size of the FFT (which is coefficients.length).
		
		for(int j =0; j<probePoints.length; j++) {
			
			double xVal = probePoints[j]; 
			
			double yVal = 0;
			
			double baseFrequency = (1/(2*probePoints.length)) * coefficients.length/2;
			
			double[] realArray = new double[coefficients.length/2];
			double[] imArray = new double[coefficients.length/2];
			
			for(int n = 0; n<coefficients.length; n++) {
				
				realArray[n] = coefficients[0][n]*Math.cos(baseFrequency*xVal);
				imArray[n] = -1*coefficients[1][n]*Math.sin(baseFrequency*xVal);
				
				yVal+= realArray[n];
				yVal+= imArray[n];
			}
			
			yOutput[j]=yVal;
		}
		
		
		return yOutput;
	}
	
}
