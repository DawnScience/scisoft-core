package uk.ac.diamond.scisoft.xpdf.xrmc;

public class XRMCSource extends XRMCFile {

	protected XRMCSource(String fileName) {
		super(fileName);
	}

	public XRMCSource(String[] lines) {
		super(lines);
	}
	
	@Override
	protected String getDeviceName() {
		return "source";
	}
	
	/**
	 * Gets the spectrum input device name.
	 * @return the spectrum input device name as a String.
	 */
	public String getSpectrumName() {
		return getValue("SpectrumName");
	}
	
	/**
	 * Gets the source coordinates
	 * @return source x, y, z coordinates
	 */
	public double[] getX() {
		return getAndParseValues("X");
	}
	
	/**
	 * Gets the source orientation z direction.
	 * <p>
	 * source orientation: k_s components (local z axis direction, i.e.
	 * main source direction)
	 * @return k_sx, k_sy, k_sz
	 */
	public double[] getUK() {
		return getAndParseValues("uk");
	}

	/**
	 * Gets the source orientation x direction.
	 * <p>
	 * source orientation: i_s components (local x axis direction)
	 * @return i_sx, i_sy, i_sz
	 */
	public double[] getUI() {
		return getAndParseValues("ui");
	}

	/**
	 * Gets the beam divergence.
	 * @return θ_x, θ_y
	 */
	public double[] getDivergence() {
		return getAndParseValues("Divergence");
	}

	/**
	 * Gets the source size.
	 * <p>
	 * The source is modelled as a three-dimensional Gaussian distribution.
	 * @return σ_x, σ_y, σ_z
	 * 
	 */
	public double[] getSize() {
		return getAndParseValues("Size");
	}

	/**
	 * Gets the rotation of the source around the axis passing through 
	 * x_0 and with direction u
	 * @return x0, y0, z0, u0, v0, w0, rotation angle θ (degrees)
	 */
	public double[] getRotate() {
		return getAndParseValues("Rotate");
	}
}
