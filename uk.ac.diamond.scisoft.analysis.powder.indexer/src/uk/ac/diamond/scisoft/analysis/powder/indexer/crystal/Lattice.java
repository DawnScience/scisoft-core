package uk.ac.diamond.scisoft.analysis.powder.indexer.crystal;

import java.io.Serializable;

/**
 * A bean-like object which holds all of the parameters necessary to specify a 
 * periodic lattice.
 * 
 * @author Michael Wharmby
 *
 */
public class Lattice implements Serializable {

	private static final long serialVersionUID = 5083826131364460534L;
	
	protected final double a, b, c, al, be, ga;
	private final double alR, beR, gaR; //Angles in radians for convenience
	private final Double volume;
	private final PrincipleAxis principleAxis;
	private final CrystalSystem crystalSystem;
	
	/**
	 * Construct lattice object from distances a, b, c and angles alpha, beta, 
	 * gamma. Volume is set to null, crystal system defaults to TRICLINIC and 
	 * Principle axis defaults to NONE.
	 *  
	 * @param a double in Angstroms
	 * @param b double in Angstroms
	 * @param c double in Angstroms
	 * @param al double in degrees
	 * @param be double in degrees
	 * @param ga double in degrees
	 */
	public Lattice(double a, double b, double c, double al, double be, double ga) {
		this(a, b, c, al, be, ga, null, CrystalSystem.TRICLINIC, PrincipleAxis.NONE);
	}
	
	/**
	 * Construct lattice object from distances a, b, c and angles alpha, beta, 
	 * gamma, volume and crystal system. Principle axis defaults to NONE.
	 * 
	 * @param a double in Angstroms
	 * @param b double in Angstroms
	 * @param c double in Angstroms
	 * @param al double in degrees
	 * @param be double in degrees
	 * @param ga double in degrees
	 * @param volume Double in Angstroms^3
	 * @param crystalSystem {@link CrystalSystem}
	 */
	public Lattice(double a, double b, double c, double al, double be, double ga, double volume, CrystalSystem crystalSystem) {
		this(a, b, c, al, be, ga, volume, crystalSystem, PrincipleAxis.NONE);
	}
	
	/**
	 * Construct lattice object from distances a, b, c and angles alpha, beta, 
	 * gamma. Crystal system indicates the metric symmetry of the lattice. 
	 * Principle axis indicates highest symmetry axis of the lattice.
	 *  
	 * @param a double in Angstroms
	 * @param b double in Angstroms
	 * @param c double in Angstroms
	 * @param al double in degrees
	 * @param be double in degrees
	 * @param ga double in degrees
	 * @param crystalSystem {@link CrystalSystem}
	 * @param pAxis {@link PrincipleAxis}
	 */
	public Lattice(double a, double b, double c, double al, double be, double ga, Double volume, CrystalSystem crystalSystem, PrincipleAxis pAxis) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.al = al;
		this.alR = Math.toRadians(al);
		this.be = be;
		this.beR = Math.toRadians(be);
		this.ga = ga;
		this.gaR = Math.toRadians(ga);
		this.volume = volume;
		this.principleAxis = pAxis;
		this.crystalSystem = crystalSystem;
	}

	/**
	 * Return lattice a parameter
	 * @return double in Angstroms
	 */
	public double getA() {
		return a;
	}
	/**
	 * Return lattice b parameter
	 * @return double in Angstroms
	 */
	public double getB() {
		return b;
	}

	/**
	 * Return lattice c parameter
	 * @return double in Angstroms
	 */
	public double getC() {
		return c;
	}

	/**
	 * Return lattice alpha parameter
	 * @return double in degrees
	 */
	public double getAl() {
		return al;
	}

	/**
	 * Return lattice beta parameter
	 * @return double in degrees
	 */
	public double getBe() {
		return be;
	}

	/**
	 * Return lattice gamma parameter
	 * @return double in degrees
	 */
	public double getGa() {
		return ga;
	}

	/**
	 * Return lattice alpha parameter
	 * @return double in radians
	 */
	public double getAlR() {
		return alR;
	}

	/**
	 * Return lattice beta parameter
	 * @return double in radians
	 */
	public double getBeR() {
		return beR;
	}

	/**
	 * Return lattice gamma parameter
	 * @return double in radians
	 */
	public double getGaR() {
		return gaR;
	}

	/**
	 * Return the volume of the unit cell defined by the lattice.
	 * @return double in Angstroms^3
	 */
	public Double getVolume() {
		return volume;
	}

	/**
	 * Return the crystal system of this lattice.
	 * @return {@link CrystalSystem}
	 */
	public CrystalSystem getCrystalSystem() {
		return crystalSystem;
	}

	/**
	 * Return the {@link PrincipleAxis} of this lattice. Useful for example 
	 * with monoclinic unit cells, where the principle axis is that 
	 * perpendicular to plane containing the two 90degree lattice angles.
	 * @return {@link PrincipleAxis}
	 */
	public PrincipleAxis getPrincipleAxis() {
		return principleAxis;
	}
	
	@Override
	public String toString() {
		return "Lattice [a=" + a + ", b=" + b + ", c=" + c + ", al=" + al 
				+ ", be=" + be + ", ga=" + ga + ", volume=" + volume 
				+", crystalSystem=" + crystalSystem	+ ", pAxis=" + principleAxis 
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(a);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(al);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(alR);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(b);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(be);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(beR);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(c);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((crystalSystem == null) ? 0 : crystalSystem.hashCode());
		temp = Double.doubleToLongBits(ga);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(gaR);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((principleAxis == null) ? 0 : principleAxis.hashCode());
		result = prime * result + ((volume == null) ? 0 : volume.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lattice other = (Lattice) obj;
		if (Double.doubleToLongBits(a) != Double.doubleToLongBits(other.a))
			return false;
		if (Double.doubleToLongBits(al) != Double.doubleToLongBits(other.al))
			return false;
		if (Double.doubleToLongBits(alR) != Double.doubleToLongBits(other.alR))
			return false;
		if (Double.doubleToLongBits(b) != Double.doubleToLongBits(other.b))
			return false;
		if (Double.doubleToLongBits(be) != Double.doubleToLongBits(other.be))
			return false;
		if (Double.doubleToLongBits(beR) != Double.doubleToLongBits(other.beR))
			return false;
		if (Double.doubleToLongBits(c) != Double.doubleToLongBits(other.c))
			return false;
		if (crystalSystem != other.crystalSystem)
			return false;
		if (Double.doubleToLongBits(ga) != Double.doubleToLongBits(other.ga))
			return false;
		if (Double.doubleToLongBits(gaR) != Double.doubleToLongBits(other.gaR))
			return false;
		if (principleAxis != other.principleAxis)
			return false;
		if (volume == null) {
			if (other.volume != null)
				return false;
		} else if (!volume.equals(other.volume))
			return false;
		return true;
	}

}
