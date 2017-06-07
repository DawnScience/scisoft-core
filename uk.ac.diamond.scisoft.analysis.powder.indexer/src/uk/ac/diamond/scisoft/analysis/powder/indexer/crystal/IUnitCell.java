package uk.ac.diamond.scisoft.analysis.powder.indexer.crystal;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * IUnitCell describes the size of the repeating 3d tile of a crystal. It 
 * consists of a {@link Lattice} and provides methods to change the lattice 
 * parameters. Furthermore it allows the calculation of values derived or 
 * dependent on from the lattice (e.g. volume or d-spacing). 
 * 
 * @author Michael Wharmby
 *
 */
public interface IUnitCell extends Comparable<IUnitCell> {
	
	/**
	 * Return the real-space lattice parameters for this IUnitCell.
	 * @return {@link Lattice}
	 */
	Lattice getLattice();
	
	default double getA() {
		return getLattice().getA();
	}
	
	default double getB() {
		return getLattice().getB();
	}
	
	default double getC() {
		return getLattice().getC();
	}
	
	default double getAlpha() {
		return getLattice().getAl();
	}
	
	default double getBeta() {
		return getLattice().getBe();
	}
	
	default double getGamma() {
		return getLattice().getGa();
	}
	
	//TODO
	//default double getVolume
	//default CrystalSystem getCrystalSystem 
	
	/**
	 * Return the reciprocal-space lattice parameters for this IUnitCell.
	 * @return {@link Lattice}
	 */
	default Lattice getReciprocalLattice() {
		return getReciprocal().getLattice();
	}
	
	default double getAStar() {
		return getReciprocalLattice().getA();
	}
	
	default double getBStar() {
		return getReciprocalLattice().getB();
	}
	
	default double getCStar() {
		return getReciprocalLattice().getC();
	}
	
	default double getAlphaStar() {
		return getReciprocalLattice().getAl();
	}
	
	default double getBetaStar() {
		return getReciprocalLattice().getBe();
	}
	
	default double getGammaStar() {
		return getReciprocalLattice().getGa();
	}
	
	/**
	 * Return the volume of the unit cell.
	 * @return double volume in Angstrom^3
	 */
	double getVolume();
	
	/**
	 * Return the metric tensor (G-matrix) for the real-space unit cell.
	 * @return RealMatrix G-matrix
	 */
	RealMatrix getMetricTensor();
	
	/**
	 * Return the metric tensor of the reciprocal-space unit cell.
	 * @return RealMatrix reciprocal-space G-matrix
	 */
	default RealMatrix getReciprocalMetricTensor() {
		return getReciprocal().getMetricTensor();
	}
	
	/**
	 * Return the reciprocal-space equivalent of this IUnitCell.
	 * @return IUnitCell
	 */
	IUnitCell getReciprocal();
	
	/**
	 * Convert a vector in Cartesian coordinates to its equivalent in the 
	 * fractional coordinate system of this unit cell.
	 * 
	 * @param cartVector Vector3D in Cartesian coordinates
	 * @return Vector3D in fractional coordinates of the current lattice
	 */
	default Vector3D fractionalize(Vector3D cartVector) {
		return new Vector3D(getFractionalizationMatrix().operate(cartVector.toArray()));
	}
	
	/**
	 * Convert a vector in fractional coordinates of this unit cell into an 
	 * equivalent vector in Cartesian coordinates.
	 * 
	 * @param fracVector Vector3D in fractional coordinates
	 * @return Vector3D in Cartesian coordinates
	 */
	default Vector3D orthogonalize(Vector3D fracVector) {
		return new Vector3D(getOrthogonalizationMatrix().operate(fracVector.toArray()));
	}
	
	/**
	 * Return matrix to convert Cartesian coordinates into fractional 
	 * coordinates for this unit cell's lattice.
	 * @return RealMatrix
	 */
	RealMatrix getFractionalizationMatrix();
	
	/**
	 * Return matrix to convert fractional coordinates of this unit cell's 
	 * lattice into Cartesian coordinates.
	 * @return RealMatrix
	 */
	RealMatrix getOrthogonalizationMatrix();
	
	/**
	 * Calculate the length of a vector specified in fractional coordinates of 
	 * this unit cell.
	 * 
	 * @param fracVec Vector3D in fractional coordinates
	 * @return double length of vector
	 */
	double calculateLength(Vector3D fracVec);
	
	/**
	 * Calculate the distance between two sites specified in fractional 
	 * coordinates of this unit cell.
	 * 
	 * @param site1 Vector3D in fractional coordinates
	 * @param site2 Vector3D in fractional coordinates
	 * @return
	 */
	default double calculateDistance(Vector3D site1, Vector3D site2) {
		return calculateLength(site2.subtract(site1));
	}
	
	/**
	 * Calculate the angle between two vectors specified in fractional 
	 * coordinates of this unit cell.
	 * 
	 * @param fracVec1 Vector3D in fractional coordinates
	 * @param fracVec2 Vector3D in fractional coordinates
	 * @return double angle between vectors in radians
	 */
	double calculateAngle(Vector3D fracVec1, Vector3D fracVec2);
	
	/**
	 * Calculate the angle between site 1 and site3 at site2 (i.e. the angle 
	 * between the vectors site1-site2 and site2-site3, c.f. bond angle).
	 * 
	 * @param site1 Vector3D in fractional coordinates
	 * @param site2 Vector3D in fractional coordinates
	 * @param site3 Vector3D in fractional coordinates
	 * @return double angle at site2 in radians
	 */
	default double calculateAngle(Vector3D site1, Vector3D site2, Vector3D site3) {
		return calculateAngle(site2.subtract(site1), site2.subtract(site3));
	}
	
	/**
	 * Calculate the angle between the planes containing site1, site2 and 
	 * site3 and site2, site3 and site4.
	 * 
	 * @param site1 Vector3D in fractional coordinates
	 * @param site2 Vector3D in fractional coordinates
	 * @param site3 Vector3D in fractional coordinates
	 * @param site4 Vector3D in fractional coordinates
	 * @return double angle between planes in radians
	 */
	double calculateDihedralAngle(Vector3D site1, Vector3D site2, Vector3D site3, Vector3D site4);
//	
//	/**
//	 * Maximum {@link MillerIndex} for the given d-spacing limit.
//	 * 
//	 * @param dSpacing in Angstrom^-1 TODO Right?
//	 * @return MillerIndex maximum hkl observable
//	 */
//	MillerIndex getMaxMillerIndex(double dSpacing);
//	
//	/**
//	 * Return the d-space value for a specific {@link MillerIndex}.
//	 *  
//	 * @param hkl {@link MillerIndex}
//	 * @return double d-spacing in Angstrom^-1 TODO Right?
//	 */
//	double getDSpacing(MillerIndex hkl);
//	
//	/**
//	 * Determine whether this IUnitCell is similar to another one, within certain tolerances. 
//	 *  
//	 * @param other IUnitCell to compare
//	 * @param lengthTol Double length tolerance (if null, default to 0.02 - 2%)
//	 * @param angleTol Double angle tolerance (if null, default to 1degree)
//	 * @return boolean true if this and other are same within tolerance
//	 */
//	boolean isSimilar(IUnitCell other, Double lengthTol, Double angleTol);
}
