package uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.diffraction.powder.CalibrationOutput;
import uk.ac.diamond.scisoft.diffraction.powder.MovingBeamCalibrationParameterModel;

public abstract class AbstractMovingBeamCalibrationTest {
	private IDiffractionMetadata refMeta;
	private String traceFile ="testfiles/Sim_CeO2_SRM674b.dat";
	private IDataset[] pattern1d;
	private int[] dShape;
	protected final double distance0 =450.;
	protected final double[] bc0 = new double[]{737.5,839.5};
	protected final double[] normalAngles0 = new double[] {0., 0., 0.};
	protected final double wavelength0 = 0.344;
	protected int minSpacing = 10;
	protected int numberOfPoints = 100;
	
	
	
	
	public AbstractMovingBeamCalibrationTest() {
		this.refMeta = getDefaultPilatusMetadata();
		this.pattern1d = getSimulated1D();
		this.dShape = new int[] { refMeta.getDetector2DProperties().getPy(), refMeta.getDetector2DProperties().getPx()};
	}
	
	
	public IDiffractionMetadata getDefaultPilatusMetadata() {
		DetectorProperties dp = new DetectorProperties(100, 0, 0, 1679, 1475, 0.172, 0.172);
		DiffractionCrystalEnvironment ce = new DiffractionCrystalEnvironment(wavelength0);
		dp.setBeamCentreCoords(bc0);
		dp.setBeamCentreDistance(distance0);
		dp.setNormalAnglesInDegrees(normalAngles0);
		return new DiffractionMetadata("test",dp, ce);
	}
	
	
	public IDiffractionMetadata getPitchedPilatusDiffractionMetadata() {
		//some general metadata
		DetectorProperties dp = new DetectorProperties(100, 0, 0, 1679, 1475, 0.172, 0.172);
		DiffractionCrystalEnvironment ce = new DiffractionCrystalEnvironment(1.0);
		
		dp.setBeamCentreCoords(new double[]{500., 500.});
		dp.setBeamCentreDistance(300.);
		dp.setNormalAnglesInDegrees(0., 45., 0.);
		return new DiffractionMetadata("test",dp, ce);
	}
	
	
	public List<IDiffractionMetadata> buildOffsetMetaDatas(IDiffractionMetadata referenceMeta,Dataset offsetX, Dataset offsetY, Dataset offsetZ){
		
		IndexIterator it= offsetX.getIterator();
		// create an empty list to sore the resulting IDiffractionMetadata 
		List<IDiffractionMetadata> metadatas = new ArrayList<>(offsetX.getShape()[0]); 
		while (it.hasNext()) {
			IDiffractionMetadata m = MovingBeamCalibrationParameterModel.getOffsetMetadata(referenceMeta,offsetX.getDouble(it.index), offsetY.getDouble(it.index), offsetZ.getDouble(it.index));			
			m.setFilePath(String.format("%s_%d", "offset",it.index));
			metadatas.add(it.index,m);
		}
		
	return metadatas;
	}
	
	public Dataset[] getSimulated1D() {
		final File file = new File(traceFile);
		Dataset x;
		Dataset y;
		try {
			x = DatasetUtils.convertToDataset(LoaderFactory.getDataSet(file.getAbsolutePath(),"Column_1",null));
			y = DatasetUtils.convertToDataset(LoaderFactory.getDataSet(file.getAbsolutePath(),"Column_2",null));
		} catch (Exception e) {
			return null;
		}
		
		return new Dataset[]{x,Maths.add(y,100)};
	}
	
	public IDataset makeImage(IDiffractionMetadata meta) {
		Dataset q2D = PixelIntegrationUtils.generateQArray(dShape, meta); 
		return PixelIntegrationUtils.generate2Dfrom1D(pattern1d, q2D);
	}
	
	public IDiffractionMetadata createMetadataFromOutput(CalibrationOutput output,int i, int[] shape, double pxSize) {

		DetectorProperties dp = DetectorProperties.getDefaultDetectorProperties(shape);
		dp.setHPxSize(pxSize);
		dp.setVPxSize(pxSize);

		dp.setBeamCentreDistance(output.getDistance().getDouble(i));
		double[] bc = new double[] {output.getBeamCentreX().getDouble(i),output.getBeamCentreY().getDouble(i) };
		dp.setBeamCentreCoords(bc);

		dp.setNormalAnglesInDegrees(output.getTilt().getDouble(i)*-1, 0, output.getTiltAngle().getDouble(i)*-1);
		DiffractionCrystalEnvironment de = new DiffractionCrystalEnvironment(output.getWavelength());
		
		return new DiffractionMetadata("FromCalibration", dp, de);
		}
}
