import uk.ac.diamond.scisoft.analysis.roi as _roi
from jymaths import ndarraywrapped as _npwrapped
from jycore import asDataset as _asDs

roibase = _roi.ROIBase

line = _roi.LinearROI

rect = _roi.RectangularROI
sect = _roi.SectorROI

linelist = _roi.LinearROIList
rectlist = _roi.RectangularROIList
sectlist = _roi.SectorROIList

ROIProfile = _roi.ROIProfile

@_npwrapped
def profile(data, roi, step=None, mask=None):
    '''Calculate a profile with given roi (a step value is required for a linear ROI)
    mask is used when clipping compensation is set true (for rectangular and sector ROI)
    '''
    data = _asDs(data)
    if isinstance(roi, line):
        if step == None:
            raise ValueError, "step value required"
        return _roi.ROIProfile.line(data, roi, step)
    if isinstance(roi, rect):
        if mask == None:
            return _roi.ROIProfile.box(data, roi)
        else:
            return _roi.ROIProfile.box(data, mask, roi)
    if isinstance(roi, sect):
        if mask == None:
            return _roi.ROIProfile.sector(data, roi)
        else:
            return _roi.ROIProfile.sector(data, mask, roi)
    raise ValueError, "roi is not of known type"
