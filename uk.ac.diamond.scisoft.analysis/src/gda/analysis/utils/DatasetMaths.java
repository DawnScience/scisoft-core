/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis.utils;

import gda.analysis.DataSet;

import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.Comparisons;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.Stats;

public class DatasetMaths {

	/**
	 * Sums two DataSets together element by element
	 * 
	 * @param g1
	 *            DataSet 1
	 * @param g2
	 *            DataSet 2
	 * @return g1+g2
	 * @throws IllegalArgumentException
	 *             This will be thrown if the two DataSets to be summed are
	 *             incompatible
	 */
	public static DataSet add(final DataSet g1, final DataSet g2) throws IllegalArgumentException {
		return DataSet.convertToDataSet(Maths.add(g1, g2));
	}

	/**
	 * Function that adds a double to all the values in a dataset
	 * 
	 * @param g
	 *            Dataset to be added to
	 * @param val
	 *            The double value to be added
	 * @return g+val
	 */
	public static DataSet add(final DataSet g, double val) {
		return DataSet.convertToDataSet(Maths.add(g, val));
	}

	/**
	 * Function that subtracts one dataset from another element by element
	 * 
	 * @param g1
	 *            The Dataset to be subtracted from
	 * @param g2
	 *            The dataset to subtract
	 * @return g1-g2
	 */
	public static DataSet subtract(final DataSet g1, final DataSet g2) {
		return DataSet.convertToDataSet(Maths.subtract(g1, g2));
	}

	/**
	 * Function to subtract a double value from all elements of a dataset
	 * 
	 * @param g
	 *            The dataset to be subtracted from
	 * @param val
	 *            The double value to subtract
	 * @return g-val
	 */
	public static DataSet subtract(final DataSet g, double val) {
		return DataSet.convertToDataSet(Maths.subtract(g, val));
	}

	/**
	 * Function to subtract a dataset from a value point by point.
	 * 
	 * @param val
	 *            The value to subtract from
	 * @param g
	 *            The dataSet to subtract
	 * @return The new Dataset
	 */
	public static DataSet subtract(double val, final DataSet g) {
		return DataSet.convertToDataSet(Maths.subtract(val, g));
	}

	/**
	 * Function to multiply two datasets together element by element
	 * 
	 * @param g1
	 *            The first dataset to be multiplied
	 * @param g2
	 *            The second dataset to be multiplied
	 * @return g1*g2
	 */
	public static DataSet multiply(final DataSet g1, final DataSet g2) {
		return DataSet.convertToDataSet(Maths.multiply(g1, g2));
	}

	/**
	 * Function to multiply all the elements of a dataset by a single double value
	 * 
	 * @param g
	 *            The dataset to be multiplied
	 * @param val
	 *            The double to multiply by
	 * @return g*val
	 */
	public static DataSet multiply(final DataSet g, double val) {
		return DataSet.convertToDataSet(Maths.multiply(g, val));
	}

	/**
	 * Function that divides one dataset element by element by another
	 * 
	 * @param g1
	 *            Dataset to divide
	 * @param g2
	 *            Dataset to divide by
	 * @return g1/g2
	 * @throws IllegalArgumentException
	 *             Throws exception if the two Datasets are incompatible
	 */
	public static DataSet divide(final DataSet g1, final DataSet g2) throws IllegalArgumentException {
		return DataSet.convertToDataSet(Maths.divide(g1, g2));
	}

	/**
	 * Function that divides a dataset by a single double.
	 * 
	 * @param g
	 *            The dataset to be divided
	 * @param val
	 *            The double value to divide by
	 * @return g/val
	 */
	public static DataSet divide(final DataSet g, double val) {
		return DataSet.convertToDataSet(Maths.divide(g, val));
	}

	/**
	 * @param val
	 * @param g
	 * @return the dataset containing the result of the division
	 */
	public static DataSet divide(double val, final DataSet g) {
		return DataSet.convertToDataSet(Maths.divide(val, g));
	}

	/**
	 * Function that divides one dataset element by element by another and catches divide-by-zero
	 * 
	 * @param g1
	 *            Dataset to divide
	 * @param g2
	 *            Dataset to divide by
	 * @return g1/g2
	 * @throws IllegalArgumentException
	 *             Throws exception if the two Datasets are incompatible
	 */
	public static DataSet dividez(final DataSet g1, final DataSet g2) throws IllegalArgumentException {
		return DataSet.convertToDataSet(Maths.dividez(g1, g2));
	}

	/**
	 * @param val
	 * @param g
	 * @return the dataset containing the result of the division and catches divide-by-zero
	 */
	public static DataSet dividez(double val, final DataSet g) {
		return DataSet.convertToDataSet(Maths.dividez(val, g));
	}

	/**
	 * Function that negates dataset element by element
	 * 
	 * @param g
	 *            The dataset to operate on
	 * @return the modified dataset
	 */
	public static DataSet negative(final DataSet g) {
		return DataSet.convertToDataSet(Maths.negative(g));
	}

	/**
	 * Function that raises the appropriate dataset element by element to a specified power
	 * 
	 * @param g
	 *            The dataset to operate on
	 * @param pow
	 *            The power to raise each element of the dataset by
	 * @return the modified dataset
	 */
	public static DataSet power(final DataSet g, double pow) {
		return DataSet.convertToDataSet(Maths.power(g, pow));
	}

	/**
	 * Function that performs the abs function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return abs(g)
	 */
	public static DataSet absolute(final DataSet g) {
		return DataSet.convertToDataSet(Maths.abs(g));
	}

	/**
	 * Rounds each element to the nearest integer
	 * 
	 * @param g
	 * @return a rounded copy of the input dataset
	 */
	public static DataSet rint(DataSet g) {
		return DataSet.convertToDataSet(Maths.rint(g));
	}

	/**
	 * Function that performs the exponential function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return exp(g)
	 */
	public static DataSet exp(final DataSet g) {
		return DataSet.convertToDataSet(Maths.exp(g));
	}

	/**
	 * Function that raise 2 to power of each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return exp2(g) = 2^g
	 */
	public static DataSet exp2(final DataSet g) {
		return DataSet.convertToDataSet(Maths.power(2, g));
	}

	/**
	 * Function that performs the log (or ln) function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return log(g) = ln(g)
	 */
	public static DataSet log(final DataSet g) {
		return DataSet.convertToDataSet(Maths.log(g));
	}

	/**
	 * Function that performs the log2 function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return log2(g)
	 */
	public static DataSet log2(final DataSet g) {
		return DataSet.convertToDataSet(Maths.log2(g));
	}

	/**
	 * Function that performs the log10 function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return log10(g)
	 */
	public static DataSet log10(final DataSet g) {
		return DataSet.convertToDataSet(Maths.log10(g));
	}

	/**
	 * Function that performs the exponential function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return expm1(g) = exp(g) - 1
	 */
	public static DataSet expm1(final DataSet g) {
		return DataSet.convertToDataSet(Maths.expm1(g));
	}

	/**
	 * Function that performs the log1p function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return log1p(g) = ln(1+g)
	 */
	public static DataSet log1p(final DataSet g) {
		return DataSet.convertToDataSet(Maths.log1p(g));
	}

	/**
	 * @param g
	 * @return the square root of the input dataset
	 */
	public static DataSet sqrt(DataSet g) {
		return DataSet.convertToDataSet(Maths.sqrt(g));
	}

	/**
	 * @param g
	 * @return the cube root of the input dataset
	 */
	public static DataSet cbrt(DataSet g) {
		return DataSet.convertToDataSet(Maths.cbrt(g));
	}

	/**
	 * @param g
	 * @return the square of the input dataset
	 */
	public static DataSet square(DataSet g) {
		return DataSet.convertToDataSet(Maths.square(g));
	}

	/**
	 * @param g
	 * @return the reciprocal of the input dataset
	 */
	public static DataSet reciprocal(DataSet g) {
		return DataSet.convertToDataSet(Maths.reciprocal(g));
	}

	/**
	 * Function that performs the cosine function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return cos(g)
	 */
	public static DataSet cos(final DataSet g) {
		return DataSet.convertToDataSet(Maths.cos(g));
	}

	/**
	 * Function that performs the sine function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return sin(g)
	 */
	public static DataSet sin(final DataSet g) {
		return DataSet.convertToDataSet(Maths.sin(g));
	}

	/**
	 * Function that performs the tangent function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return tan(g)
	 */
	public static DataSet tan(final DataSet g) {
		return DataSet.convertToDataSet(Maths.tan(g));
	}

	/**
	 * Function that performs the arc cosine function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return acos(g)
	 */
	public static DataSet arccos(final DataSet g) {
		return DataSet.convertToDataSet(Maths.arccos(g));
	}

	/**
	 * Function that performs the arc sine function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return asin(g)
	 */
	public static DataSet arcsin(final DataSet g) {
		return DataSet.convertToDataSet(Maths.arcsin(g));
	}

	/**
	 * Function that performs the arc tangent function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return sin(g)
	 */
	public static DataSet arctan(final DataSet g) {
		return DataSet.convertToDataSet(Maths.arctan(g));
	}

	/**
	 * Function that performs the arc tangent function on each pair of elements of the two datasets
	 * 
	 * @param g1
	 *            Dataset to divide
	 * @param g2
	 *            Dataset to divide by
	 * @return atan(g1/g2)
	 * @throws IllegalArgumentException
	 *             Throws exception if the two Datasets are incompatible
	 */
	public static DataSet arctan2(final DataSet g1, final DataSet g2) throws IllegalArgumentException {
		return DataSet.convertToDataSet(Maths.arctan2(g1, g2));
	}

	/**
	 * Function that finds the hypotenuse on each pair of elements of the two datasets
	 * 
	 * @param g1
	 *            Dataset to divide
	 * @param g2
	 *            Dataset to divide by
	 * @return sqrt(g1^2 + g2^2)
	 * @throws IllegalArgumentException
	 *             Throws exception if the two Datasets are incompatible
	 */
	public static DataSet hypot(final DataSet g1, final DataSet g2) throws IllegalArgumentException {
		return DataSet.convertToDataSet(Maths.hypot(g1, g2));
	}

	/**
	 * Function that performs the hyperbolic cosine function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return cosh(g)
	 */
	public static DataSet cosh(final DataSet g) {
		return DataSet.convertToDataSet(Maths.cosh(g));
	}

	/**
	 * Function that performs the hyperbolic sine function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return sinh(g)
	 */
	public static DataSet sinh(final DataSet g) {
		return DataSet.convertToDataSet(Maths.sinh(g));
	}

	/**
	 * Function that performs the hyperbolic tangent function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return tanh(g)
	 */
	public static DataSet tanh(final DataSet g) {
		return DataSet.convertToDataSet(Maths.tanh(g));
	}

	/**
	 * Function that performs the inverse hyperbolic cosine function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return arccosh(g)
	 */
	public static DataSet arccosh(final DataSet g) {
		return DataSet.convertToDataSet(Maths.arccosh(g));
	}

	/**
	 * Function that performs the inverse hyperbolic sine function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return arcsinh(g)
	 */
	public static DataSet arcsinh(final DataSet g) {
		return DataSet.convertToDataSet(Maths.arcsinh(g));
	}

	/**
	 * Function that performs the inverse hyperbolic tangent function on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return arctanh(g)
	 */
	public static DataSet arctanh(final DataSet g) {
		return DataSet.convertToDataSet(Maths.arctanh(g));
	}

	/**
	 * Function that converts degrees to radians on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return deg2rad(g)
	 */
	public static DataSet deg2rad(final DataSet g) {
		return DataSet.convertToDataSet(Maths.toRadians(g));
	}

	/**
	 * Function that converts radians to degrees on each element of the dataset
	 * 
	 * @param g
	 *            The dataset to modify
	 * @return rad2deg(g)
	 */
	public static DataSet rad2deg(final DataSet g) {
		return DataSet.convertToDataSet(Maths.toDegrees(g));
	}

	/**
	 * @param g
	 * @return the ceiling of the input dataset
	 */
	public static DataSet ceil(DataSet g) {
		return DataSet.convertToDataSet(Maths.ceil(g));
	}

	/**
	 * @param g
	 * @return the floor of the input dataset
	 */
	public static DataSet floor(DataSet g) {
		return DataSet.convertToDataSet(Maths.floor(g));
	}

	/**
	 * Function that gets the average deviation from all the values in the dataset
	 * 
	 * @param g
	 *            The dataset to analyse
	 * @return the value of the average deviation value of the dataset
	 */
	public static double getAverageDeviation(final DataSet g) {
		return (Double) Stats.averageDeviation(g);
	}

	/**
	 * Function that gets the skew from all the values in the dataset
	 * 
	 * @param g
	 *            The dataset to analyse
	 * @return the value of the skew of the dataset
	 */
	public static double skew(final DataSet g) {
		return ((Number) Stats.skewness(g)).doubleValue();
	}

	/**
	 * Function that gets the kurtosis from all the values in the dataset
	 * 
	 * @param g
	 *            The dataset to analyse
	 * @return the value of the kurtosis of the dataset
	 */
	public static double kurtosis(final DataSet g) {
		return ((Number) Stats.kurtosis(g)).doubleValue();
	}

	/**
	 * Function that gets the centroid value of a dataset, this function works out the centroid in every direction
	 * 
	 * @param g
	 *            the dataset to be analysed
	 * @param base
	 *            the optional array of base coordinates to use as weights. This defaults to the mid-point of indices
	 * @return a double array containing the centroid for each dimension.
	 */
	public static double[] centroid(DataSet g, DataSet... base) {
		return DatasetUtils.centroid(g, base);
	}

	/**
	 * Function which returns the chi-squared error between two datasets
	 * 
	 * @param a
	 *            Dataset 1
	 * @param b
	 *            Dataset 2
	 * @return a double value which represents the error value
	 */
	@Deprecated
	public static double chiSquared(DataSet a, DataSet b) {
		return residual(a, b);
	}

	/**
	 * Function which returns the residual between two datasets. The residual is the sum of squared differences.
	 * 
	 * @param a
	 *            Dataset 1
	 * @param b
	 *            Dataset 2
	 * @return a double value which represents the residual value
	 */
	public static double residual(DataSet a, DataSet b) {
		return a.residual(b);
	}

	/**
	 * Calculates the derivative of the Line (x,y) given a spread of n either side of the point
	 * 
	 * @param x
	 *            The x values of the function to take the derivative of.
	 * @param y
	 *            The y values of the function to take the derivative of.
	 * @param n
	 *            The spread the derivative is calculated from, i.e. the smoothing, the higher the value, the more
	 *            smoothing occurs.
	 * @return A dataset which contains all the derivative point for point.
	 */
	public static DataSet derivative(DataSet x, DataSet y, int n) {
		return DataSet.convertToDataSet(Maths.derivative(x, y, n));
	}

	/**
	 * Function that returns a normalised dataset which is bounded between 0 and 1
	 * 
	 * @param g
	 *            the dataset
	 * @return the normalised dataset
	 */
	public static DataSet norm(DataSet g) {
		return DataSet.convertToDataSet(DatasetUtils.norm(g));
	}

	/**
	 * Function that returns a normalised dataset which is bounded between 0 and 1 and has been distributed on a log10
	 * scale
	 * 
	 * @param g
	 *            the dataset
	 * @return the normalised dataset
	 */
	public static DataSet lognorm(DataSet g) {
		return DataSet.convertToDataSet(DatasetUtils.lognorm(g));
	}

	/**
	 * Function that returns a normalised dataset which is bounded between 0 and 1 and has been distributed on a ln
	 * scale
	 * 
	 * @param g
	 *            the dataset
	 * @return the normalised dataset
	 */
	public static DataSet lnnorm(DataSet g) {
		return DataSet.convertToDataSet(DatasetUtils.lnnorm(g));
	}

	/**
	 * Function that returns a clipped dataset which is bounded between given limits NB sets NaNs to (min+max)/2
	 * 
	 * @param g
	 *            the dataset
	 * @param min
	 * @param max
	 * @return the clipped dataset
	 */
	public static DataSet clip(DataSet g, double min, double max) {
		return DataSet.convertToDataSet(Maths.clip(g, min, max));
	}

	/**
	 * @param g
	 * @return true if all values are non-zero (Nans are non-zero)
	 */
	public static boolean all(DataSet g) {
		return Comparisons.allTrue(g);
	}

	/**
	 * @param g
	 * @return true if any value is non-zero (Nans are non-zero)
	 */
	public static boolean any(DataSet g) {
		return Comparisons.anyTrue(g);
	}

	/**
	 * Interpolated a value from dataset
	 * 
	 * @param d
	 * @param x0
	 * @return linear interpolation
	 */
	public static double getLinear(final DataSet d, final double x0) {
		return Maths.getLinear(d, x0);
	}

	/**
	 * Interpolated a value from dataset
	 * 
	 * @param d
	 * @param x0
	 * @param x1
	 * @return bilinear interpolation
	 */
	public static double getBilinear(final DataSet d, final double x0, final double x1) {
		return Maths.getBilinear(d, x0, x1);
	}

	/**
	 * Find linearly-interpolated crossing points where the given dataset crosses the given value
	 * 
	 * @param d
	 * @param value
	 * @return list of interpolated indices
	 */
	public static List<Double> crossings(DataSet d, double value) {
		return DatasetUtils.crossings(d, value);
	}

	/**
	 * This function gets the X values of all the crossing points of the dataset with the particular Y value
	 * 
	 * @param xAxis
	 *            Dataset of the X axis that needs to be looked at
	 * @param yAxis
	 *            Dataset of the Y axis that needs to be looked at
	 * @param yValue
	 *            The y value for which the x values are required
	 * @return An list of doubles containing all the X coordinates of where the line crosses.
	 */
	public static List<Double> crossings(DataSet xAxis, DataSet yAxis, double yValue) {
		return DatasetUtils.crossings(xAxis, yAxis, yValue);
	}

	/**
	 * Function that uses the crossings function but prunes the result, so that multiple crossings within a certain
	 * proportion of the overall range of the x values
	 * 
	 * @param xAxis
	 *            Dataset of the X axis
	 * @param yAxis
	 *            Dataset of the Y axis
	 * @param yValue
	 *            The y value for which the x values are required
	 * @param xRangeProportion
	 *            The proportion of the overall x spread used to prune result
	 * @return A list containing all the unique crossing points
	 */
	public static List<Double> crossings(DataSet xAxis, DataSet yAxis, double yValue, double xRangeProportion) {
		return DatasetUtils.crossings(xAxis, yAxis, yValue, xRangeProportion);
	}

}
