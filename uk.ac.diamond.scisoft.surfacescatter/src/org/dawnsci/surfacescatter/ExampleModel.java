package org.dawnsci.surfacescatter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import org.dawnsci.surfacescatter.AnalaysisMethodologies.Methodology;
import org.dawnsci.surfacescatter.TrackingMethodology.TrackerType1;
import org.dawnsci.surfacescatter.AnalaysisMethodologies.FitPower;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.AggregateDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;

public class ExampleModel {
	
	private int imageNumber = 0;
	private IROI ROI;
	private int[][] lenpt;
	private IDataset currentImage;
	private ArrayList<ILazyDataset> arrayILD;
	private ILazyDataset datImages;
	private int sliderPos = 0;
	private float iterationMarker =0;
	private AggregateDataset aggDat;
	private RectangularROI box;
	private IDataset input; 
	private int boundaryBox = 10;
	private Methodology methodology = Methodology.TWOD;
	private double[] trackerCoordinates = {100,100,110,100,110,100,110,110};
	private String filepath;
	private double outputNo;
	private ILazyDataset DatX;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private ILazyDataset dcdtheta;
	private ILazyDataset qdcd;
	private ILazyDataset flux;
	private ILazyDataset theta;
	private TrackerType1 trackerType = TrackerType1.TLD;
	private FitPower fitPower = FitPower.THREE;
	
	public IDataset getInput() {
		return input;
	}

	public void setInput(IDataset input) {
		this.input = input;
	}

	
	
	public double[] getTrackerCoordinates() {
		return trackerCoordinates;
	}

	public void setTrackerCoordinates(double[] trackerCoordinates) {
		this.trackerCoordinates = trackerCoordinates;
	}

	public Methodology getMethodology() {
		return methodology;
	}

	public void setMethodology(Methodology methodology) {
		this.methodology = methodology;
	}
	
	public int getBoundaryBox() {
		return boundaryBox;
	}

	public void setBoundaryBox(int boundaryBox) {
		this.boundaryBox = boundaryBox;
	}

	public FitPower getFitPower() {
		return fitPower;
	}

	public void setFitPower(FitPower fitPower1) {
		this.fitPower = fitPower1;
	}

	public RectangularROI getBox() {
		return box;
	}

	public void setBox(RectangularROI box) {
		firePropertyChange("box", this.box, this.box= box);
	}

	public AggregateDataset getAggDat() {
		return aggDat;
	}

	public void setAggDat(AggregateDataset aggDat) {
		this.aggDat = aggDat;
	}

	public float getIterationMarker() {
		return iterationMarker;
	}

	public void setIterationMarker(float iterationMarker) {
		firePropertyChange("iterationMarker", this.iterationMarker, this.iterationMarker= iterationMarker);
	}
	
	public int getSliderPos() {
		return sliderPos;
	}

	public void setSliderPos(int sliderPos) {
		this.sliderPos = sliderPos;
	}

	public ArrayList<ILazyDataset> getArrayILD() {
		return arrayILD;
	}

	public void setArrayILD(ArrayList<ILazyDataset> arrayILD) {
		this.arrayILD = arrayILD;
	}


	public IROI getROI(){
		return ROI;
	}

	public void setROI(IROI ROI) {
		IRectangularROI bounds = ROI.getBounds();
		int[] len = bounds.getIntLengths();
		int[] pt = bounds.getIntPoint();
		int[][] lenpt = new int[2][];
		lenpt[0]=len;
		lenpt[1]=pt;
		firePropertyChange("ROI", this.ROI, this.ROI= ROI);
		this.setLenPt(lenpt);
		firePropertyChange("lenpt", this.lenpt, this.lenpt= lenpt);
		
	}
	
	public int[][] getLenPt(){
		return lenpt;
	}
	
	public void setLenPt(int[][] lenpt){
		this.lenpt = lenpt;
	}

	public IDataset getCurrentImage(){
		return currentImage;
	}
	
	public void setCurrentImage(IDataset currentImage){
		firePropertyChange("currentImage", this.currentImage, this.currentImage= currentImage);
		}

	public int getImageNumber() {
		return imageNumber;
	}

	public void setImageNumber(int imageNumber) {
		firePropertyChange("imageNumber", this.imageNumber, this.imageNumber= imageNumber);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public double getOutputNo() {
		return outputNo;
	}

	public void setOutputNo(double outputNo) {
		this.outputNo = outputNo;
	}

	public ILazyDataset getDatX() {
		return DatX;
	}

	public void setDatX(ILazyDataset Datx) {
		this.DatX = Datx;
	}

	public ILazyDataset getDatImages() {
		return datImages;
	}

	public void setDatImages(ILazyDataset datImages) {
		this.datImages = datImages;
	}

	public ILazyDataset getDcdtheta() {
		return dcdtheta;
	}

	public void setDcdtheta(ILazyDataset dcdtheta) {
		this.dcdtheta = dcdtheta;
	}

	public ILazyDataset getQdcd() {
		return qdcd;
	}

	public Dataset getQdcdDat() {
		
		SliceND sl = new SliceND(qdcd.getShape());
		try {
			Dataset QdcdDat = (Dataset) qdcd.getSlice(sl);
			return QdcdDat;
		} catch (DatasetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	

	public void setQdcd(ILazyDataset qdcd) {
		this.qdcd = qdcd;
	}

	public ILazyDataset getFlux() {
		return flux;
	}

	public void setFlux(ILazyDataset flux) {
		this.flux = flux;
	}

	public ILazyDataset getTheta() {
		return theta;
	}

	public void setTheta(ILazyDataset theta) {
		this.theta = theta;
	}

	public TrackerType1 getTrackerType() {
		return trackerType;
	}

	public void setTrackerType(TrackerType1 trackerType) {
		this.trackerType = trackerType;
	}
	
	


	
}
