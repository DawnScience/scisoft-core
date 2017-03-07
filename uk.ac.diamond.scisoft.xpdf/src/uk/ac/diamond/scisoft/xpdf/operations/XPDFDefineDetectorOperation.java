package uk.ac.diamond.scisoft.xpdf.operations;

import javax.vecmath.Matrix3d;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.twod.DiffractionMetadataImportModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.DiffractionMetadataImportOperation;
import uk.ac.diamond.scisoft.xpdf.XPDFDetector;
import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.XPDFSubstance;

/**
 * Defines the detector for XPDF processing.
 * <p>
 * An Operation class to that gathers the definition of a detector and adds
 * that information to the {@link XPDFMetadata} metadata.
 * 
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
public class XPDFDefineDetectorOperation extends XPDFInsertXMetadataOperation<XPDFDefineDetectorModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor minotaur) throws OperationException {
		
		XPDFMetadataImpl theXPDFMetadata = getAndRemoveXPDFMetadata(input);
		XPDFDetector tect = new XPDFDetector();
		tect.setSubstance(new XPDFSubstance(model.getDetectorName(), model.getDetectorMaterial(), model.getDensity(), 1.0));
		tect.setThickness(model.getThickness());
		tect.setSolidAngle(model.getSolidAngle());
		tect.setEulerAnglesinDegrees(0, 0, model.getDetectorAngle());
		
		theXPDFMetadata.setDetector(tect);
		
		input.setMetadata(theXPDFMetadata);

		if (model.getFilePath() != "") {
			// Clear any existing IDiffractioMetadata
			input.clearMetadata(IDiffractionMetadata.class);
			
			DiffractionMetadataImportOperation dmio = new DiffractionMetadataImportOperation();
			FakeDiffractionMetadataImportModel fdmim = new FakeDiffractionMetadataImportModel();
			
			// Create the fake model to pass to the Operation
			fdmim.setFilePath(model.getFilePath());
			
			dmio.setModel(fdmim);
			// Run the diffraction metadata import Operation with the fake model.
			dmio.execute(input, minotaur);
			
			// Apply the roll to the detector
			IDiffractionMetadata idm = input.getFirstMetadata(IDiffractionMetadata.class);
			Matrix3d orientation = idm.getOriginalDetector2DProperties().getOrientation();
			double croll = Math.cos(tect.getEulerAngles()[2]), sroll = Math.sin(tect.getEulerAngles()[2]);
			Matrix3d rollMatrix = new Matrix3d(croll, sroll, 0, -sroll, croll, 0, 0, 0, 1);
			orientation.mul(rollMatrix);
			idm.getOriginalDetector2DProperties().setOrientation(orientation);
			
			// Clear the old, unrolled calibration metadata
			input.clearMetadata(IDiffractionMetadata.class);
			// Set the new, transformed metadata
			input.setMetadata(idm);
		}
		
		return new OperationData(input);
	}
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFDefineDetectorOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

	private class FakeDiffractionMetadataImportModel extends DiffractionMetadataImportModel {
		private String filePath;
		
		@Override
		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
		@Override
		public String getFilePath() {
			return this.filePath;
		}
	}
	
}
