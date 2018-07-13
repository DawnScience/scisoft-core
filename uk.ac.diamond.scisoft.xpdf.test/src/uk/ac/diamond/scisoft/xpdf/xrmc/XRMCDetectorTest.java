package uk.ac.diamond.scisoft.xpdf.xrmc;

import static org.junit.Assert.*;

import java.util.Arrays;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import org.junit.Test;

public class XRMCDetectorTest {

	@Test
	public void detectorAlignmentSimpleTest() {
		XRMCDetector det = new XRMCDetector(getSimpleDetectorStringArray());
	
		 int[] npx = det.getNPixels();
		// The centre of the detector should be at (0, 0.0, 0.2), when measured in metres
		Vector3d centrePoint = det.labFromPixel(new Vector2d(npx[0]*0.5, npx[1]*0.5));
		assertArrayEquals("Centre point is not at centre", new double[] {0.0, 0.0, 0.2}, new double[] {centrePoint.x, centrePoint.y, centrePoint.z}, 1e-6);
	}

	@Test
	public void detectorAlignmentComplexTest() {
		XRMCDetector det = new XRMCDetector(getComplexDetectorStringArray());
	
		 int[] npx = det.getNPixels();
		// The centre of the detector should be at (0, 0.2, 0.2), when measured in metres
		Vector3d centrePoint = det.labFromPixel(new Vector2d(npx[0]*0.5, npx[1]*0.5));
		assertArrayEquals("Centre point is not at centre", new double[] {0.0, 0.2, 0.2}, new double[] {centrePoint.x, centrePoint.y, centrePoint.z}, 1e-6);
		double[] originPoint = new double[3];
		det.labFromPixel(new Vector2d(0,0)).get(originPoint);
		System.out.println(Arrays.toString(originPoint));
		// Because of the size and orientation of the detector, the root(2)s
		// combine to put the origin exactly 0.4 m above the sample
		assertArrayEquals("Origin incorrectly placed", new double[] {0.0, 0.4, 0.0}, originPoint, 1e-6);
	}

	
	private String[] getSimpleDetectorStringArray() {
		return new String[]{
				";Detector array parameter file",
		        "Newdevice detectorarray		; Device type",
		        "AlignedDetector				; Device name",
		        "SourceName SimpleBeam			; Source input device name",
		        "NPixels 40 40					; Pixel number (NX x NY)",
		        "PixelSize 1 1					; Pixel size (cm)",
		        "Shape 0						; Pixel shape (0=>rectangular, 1=>elliptical)",
		        "dOmegaLim 1e-2					; Cut on dOmega (default = 2*PI)",
		        "X	0	0	20					; screen x, y, z",
		        "								; Screen orientation:",
		        "uk	0	0	-1					; z direction (normal to screen)",
		        "ui	1	0	0					; x direction",
		        "ExpTime 1.0					; Exposure Time (sec)",
		        "PhotonNum 10000				; Multiplicity of simulated events per pixel",
		        "RandomPixelFlag 1				; Enable random points on pixels (0/1)",
		        "PoissonFlag 0					; Enable Poisson statistics on pix. counts (0/1)",
		        "RoundFlag 0					; Round counts to integer (0/1)",
		        "HeaderFlag 1                    ; Use header in output file (0/1)",
		        "PixelType 2			; Pixel content type:",
		        "				; 0: fluence,      1: energy fluence,",
		        "				; 2: fluence(E),   3: energy fluence(E)",
		        "Emin 0				; Emin",
		        "Emax 80			; Emax",
		        "NBins 1000			; NBins",
		        ";SaturateEmin 0			; Saturate energies lower than Emin (0/1)",
		        ";SaturateEmax 0			; Saturate energies greater than Emax (0/1)",
		        "Seeds 10      			; Seeds for random number generation",
		        "113450				; 1st seed",
		        "113451				; ...",
		        "113452				; ...",
		        "113453				; ...",
		        "113454				; ...",
		        "113455				; ...",
		        "113456				; ...",
		        "113457				; ...",
		        "113458				; ...",
		        "113459				; last seed",
		        "",
		        ";Rotate 0 0 0 1 0 0 -20		; rotation around axis x, u, angle theta",
		        ";Rotate 0 0 0 0 0 1 -30		; rotation around axis x, u, angle theta",
		        "",
		        "End",

		};
	}

	private String[] getComplexDetectorStringArray() {
		return new String[]{
				";Detector array parameter file",
		        "Newdevice detectorarray		; Device type",
		        "AlignedDetector				; Device name",
		        "SourceName SimpleBeam			; Source input device name",
		        "NPixels 40 40					; Pixel number (NX x NY)",
		        "PixelSize 1 1					; Pixel size (cm)",
		        "Shape 0						; Pixel shape (0=>rectangular, 1=>elliptical)",
		        "dOmegaLim 1e-2					; Cut on dOmega (default = 2*PI)",
		        "X	0	20	20					; screen x, y, z",
		        "								; Screen orientation:",
		        "uk	0	-0.707107	-0.707107	; z direction (normal to screen)",
		        "ui	-0.707107	-0.5	0.5		; x direction",
		        "ExpTime 1.0					; Exposure Time (sec)",
		        "PhotonNum 10000				; Multiplicity of simulated events per pixel",
		        "RandomPixelFlag 1				; Enable random points on pixels (0/1)",
		        "PoissonFlag 0					; Enable Poisson statistics on pix. counts (0/1)",
		        "RoundFlag 0					; Round counts to integer (0/1)",
		        "HeaderFlag 1                    ; Use header in output file (0/1)",
		        "PixelType 2			; Pixel content type:",
		        "				; 0: fluence,      1: energy fluence,",
		        "				; 2: fluence(E),   3: energy fluence(E)",
		        "Emin 0				; Emin",
		        "Emax 80			; Emax",
		        "NBins 1000			; NBins",
		        ";SaturateEmin 0			; Saturate energies lower than Emin (0/1)",
		        ";SaturateEmax 0			; Saturate energies greater than Emax (0/1)",
		        "Seeds 10      			; Seeds for random number generation",
		        "113450				; 1st seed",
		        "113451				; ...",
		        "113452				; ...",
		        "113453				; ...",
		        "113454				; ...",
		        "113455				; ...",
		        "113456				; ...",
		        "113457				; ...",
		        "113458				; ...",
		        "113459				; last seed",
		        "",
		        ";Rotate 0 0 0 1 0 0 -20		; rotation around axis x, u, angle theta",
		        ";Rotate 0 0 0 0 0 1 -30		; rotation around axis x, u, angle theta",
		        "",
		        "End",

		};
	}
}
