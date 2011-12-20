###
# Copyright © 2011 Diamond Light Source Ltd.
# Contact :  ScientificSoftware@diamond.ac.uk
# 
# This is free software: you can redistribute it and/or modify it under the
# terms of the GNU General Public License version 3 as published by the Free
# Software Foundation.
# 
# This software is distributed in the hope that it will be useful, but 
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
# Public License for more details.
# 
# You should have received a copy of the GNU General Public License along
# with this software. If not, see <http://www.gnu.org/licenses/>.
###

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
