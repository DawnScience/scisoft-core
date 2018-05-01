package uk.ac.diamond.scisoft.xpdf.xrmc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class XRMCSpectrum extends XRMCFile {

	public XRMCSpectrum(String fileName) {
		super(fileName);
	}
	
	public XRMCSpectrum(String[] lines) {
		super(lines);
	}
	
	public boolean isSpectrumFile() {
		return isValidFile();
	}
	
	@Override
	protected String getDeviceName() {
		return "spectrum";
	}
	
	/**
	 * Returns the name of the device defined in the file.
	 * @return name of the device.
	 */
	public String getName() {
//		int iNewDevice = reader.firstIndexOf("Newdevice");
		return reader.getLine(1);
	}
	
	/**
	 * Returns whether the beam is polarized. Defaults to unpolarized beam.
	 * @return false for an unpolarized beam, true for a polarized beam.
	 */
	public boolean getPolarizedFlag() {
		return getBooleanValue("PolarizedFlag", false);
	}
	
	/**
	 * Returns whether the energy should be obtained by looping.
	 * <p>
	 * A false value indicates that the photon energies will be extracted at
	 * random points on the whole spectrum. A true value indicates that the
	 * photon energies will be obtained by looping over the defined lines and
	 * sampling from points within.
	 * @return false for photon energies drawn at random, true for looping over
	 * 			the defined lines.
	 */
	public boolean getLoopFlag() {
		return getBooleanValue("LoopFlag", false);
	}
	
	/**
	 * Returns the multiplicity of events for each interval in spectrum.
	 * @return event multiplicity.
	 */
	public int getContinuousPhotonNum() {
		return getIntegerValue("ContinuousPhotonNum");
	}
	
	/**
	 * Returns the multiplicity of events for each line in spectrum.
	 * @return line multiplicity.
	 */
	public int getLinePhotonNum() {
		return getIntegerValue("LinePhotonNum");
	}
	
	/**
	 * Returns whether to use a random energy in each interval of the
	 * continuous spectrum.
	 * <p>
	 * false:random energy disabled
	 * true: random energy enabled (recommended)
	 * @return random energy flag
	 */
	public boolean getRandomEneFlag() {
		return getBooleanValue("RandomEneFlag", true);
	}
	
	public List<SpectrumComponent> getSpectrum() {
		
		List<SpectrumComponent> components = new ArrayList<>();
		
		boolean isPolarized = getPolarizedFlag();
		
		// Lines
		if (reader.hasKey("Lines")) {
			int iLine = reader.firstIndexOf("Lines");
			int nLines = Integer.parseInt(reader.getLine(iLine+1));
			String[] lineLines = reader.getLines(iLine+2, nLines);
			for (String lineLine : lineLines)
				components.add((isPolarized) ? new PolarizedLineSample(lineLine) : new LineSample(lineLine));
		}
		
		if (reader.hasKey("ContinuousSpectrum")) {
			int iCont = reader.firstIndexOf("ContinuousSpectrum");
			int nCont = Integer.parseInt(reader.getLine(iCont+1));
			String[] contLines = reader.getLines(iCont+2, nCont);
			addContinuum(components, contLines, isPolarized);
		}
		
		if (reader.hasKey("ContinuousSpectrumFile")) {
			int iFile = reader.firstIndexOf("ContinuousSpectrumFile");
			int nCont = Integer.parseInt(reader.getLine(iFile+1));
			String[] contLines = new String[nCont];
			String filename = getValue("filename");
			try (Scanner sc = new Scanner(new File(filename))) {
				for (int iiCont = 0; iiCont < nCont; iiCont++) {
					contLines[iiCont] = sc.nextLine();
				}
			} catch (IOException ioe) {
				// Do nothing
			}
			addContinuum(components, contLines, isPolarized);
		}
		return components;
	}
	
	private static void addContinuum(List<SpectrumComponent> components, String[] contLines, boolean isPolarized) {
		for (String contLine : contLines)
			components.add((isPolarized) ? new ContinuousPolarizedSample(contLine) : new ContinuousSample(contLine));
	}
	
	public static class SpectrumComponent {
		private double energy;
		private double intensity;
		
		protected SpectrumComponent(double energy, double intensity) {
			this.energy = energy;
			this.intensity = intensity;
		}
		
		public double getEnergy() {
			return this.energy;
		}
		
		protected double getIntensityInternal() {
			return this.intensity;
		}

		protected static String getToken(String s, int i) {
			return s.split("\\s+")[i];
		}

	
	}
	
	public static interface IPolarizedComponent {
		public double getIntensity1();
		public double getIntensity2();
	}

	public static interface IUnpolarizedComponent {
		public double getIntensity();
	}

	public static interface ILineComponent {
		public double getWidth();
	}
	
	public static class ContinuousSample extends SpectrumComponent implements IUnpolarizedComponent {
		public ContinuousSample(double energy, double intensity) {
			super(energy, intensity);
		}
		
		public ContinuousSample(String s) {
			this(Double.parseDouble(getToken(s, 0)), Double.parseDouble(getToken(s, 1)));
		}

		@Override
		public double getIntensity() {
			return getIntensityInternal();
		}
		
	}

	public static class ContinuousPolarizedSample extends SpectrumComponent implements IPolarizedComponent {
		private double intensity2;
		public ContinuousPolarizedSample(double energy, double intensity1, double intensity2) {
			super(energy, intensity1);
			this.intensity2 = intensity2;
		}

		public ContinuousPolarizedSample(String s) {
			this(Double.parseDouble(getToken(s, 0)),
					Double.parseDouble(getToken(s, 1)),
					Double.parseDouble(getToken(s, 2)));
		}

		@Override
		public double getIntensity1() {
			return getIntensityInternal();
		}

		@Override
		public double getIntensity2() {
			return intensity2;
		} 		
	}
	
	public static class LineSample extends SpectrumComponent implements IUnpolarizedComponent, ILineComponent {
		private double width;

		public LineSample(double energy, double width, double intensity) {
			super(energy, intensity);
			this.width = width;
		}
		
		public LineSample(String s) {
			this(Double.parseDouble(getToken(s, 0)),
					Double.parseDouble(getToken(s, 1)),
					Double.parseDouble(getToken(s, 2)));
		}

		@Override
		public double getWidth() {
			return this.width;
		}

		@Override
		public double getIntensity() {
			return getIntensityInternal();
		}
		
	}
	
	public static class PolarizedLineSample extends SpectrumComponent implements IPolarizedComponent, ILineComponent {
		private double width;
		private double intensity2;
		
		public PolarizedLineSample(double energy, double width, double intensity1, double intensity2) {
			super(energy, intensity1);
			this.width = width;
			this.intensity2 = intensity2;
		}

		public PolarizedLineSample(String s) {
			this(Double.parseDouble(getToken(s, 0)),
					Double.parseDouble(getToken(s, 1)),
					Double.parseDouble(getToken(s, 2)),
					Double.parseDouble(getToken(s, 3)));
		}

		@Override
		public double getWidth() {
			return this.width;
		}

		@Override
		public double getIntensity1() {
			return this.getIntensityInternal();
		}

		@Override
		public double getIntensity2() {
			return this.intensity2;
		}
	}
	
}
