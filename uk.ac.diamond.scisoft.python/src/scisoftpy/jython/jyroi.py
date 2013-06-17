###
# Copyright 2011 Diamond Light Source Ltd.
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
###

import uk.ac.diamond.scisoft.analysis.roi as _roi
from jycore import asDataset as _asDs
from jycore import _wrap

_iroi = _roi.IROI

roibase = _roi.ROIBase

point = _roi.PointROI
line = _roi.LinearROI
rectangle = _roi.RectangularROI
sector = _roi.SectorROI
circle = _roi.CircularROI
ellipse = _roi.EllipticalROI

point_list = _roi.PointROIList
line_list = _roi.LinearROIList
rectangle_list = _roi.RectangularROIList
sector_list = _roi.SectorROIList
circle_list = _roi.CircularROIList
ellipse_list = _roi.EllipticalROIList

ROIProfile = _roi.ROIProfile

@_wrap
def profile(data, roi, step=None, mask=None):
    '''Calculate a profile with given roi (a step value is required for a linear ROI)
    mask is used when clipping compensation is set true (for rectangular and sector ROI)
    '''
    data = _asDs(data)
    if isinstance(roi, line):
        if step is None:
            raise ValueError, "step value required"
        return _roi.ROIProfile.line(data, roi, step)
    if isinstance(roi, rectangle):
        if mask is None:
            return _roi.ROIProfile.box(data, roi)
        else:
            return _roi.ROIProfile.box(data, mask, roi)
    if isinstance(roi, sector):
        if mask is None:
            return _roi.ROIProfile.sector(data, roi)
        else:
            return _roi.ROIProfile.sector(data, mask, roi)
    raise ValueError, "roi is not of known type"
