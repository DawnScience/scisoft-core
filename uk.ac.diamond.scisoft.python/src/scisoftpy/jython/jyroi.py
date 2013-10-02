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

        
# base class for implementing point property
class _iroi(object):
    def _jroi(self):
        return self._roi

    def _getPoint(self):
        return self._roi.getPoint()
 
    def _setPoint(self, pt):
        self._roi.setPoint([float(p) for p in pt])

    point = property(_getPoint, _setPoint)

    def _getName(self):
        return self._roi.getName()
 
    def _setName(self, n):
        self._roi.setName(str(n))

    name = property(_getName, _setName)

    def _isPlot(self):
        return self._roi.isPlot()
 
    def _setPlot(self, p):
        self._roi.setPlot(bool(p))

    plot = property(_isPlot, _setPlot)

    def __repr__(self):
        return "%s(%s)" % (self.__class__.__name__, self._roi.toString())

# class to mix-in for implementing angle property
class _angleprop(object):
    def _getAngle(self):
        return self._roi.getAngle()
  
    def _setAngle(self, ang):
        self._roi.setAngle(float(ang))

    angle = property(_getAngle, _setAngle)

    def _getAngleD(self):
        return self._roi.getAngleDegrees()
  
    def _setAngleD(self, ang):
        self._roi.setAngleDegrees(float(ang))

    angledegrees = property(_getAngleD, _setAngleD)

class roibase(_iroi):
    pass

class point(_iroi):
    def __init__(self, jroi=None, point=None, **kwargs):
        if jroi is None:
            jroi = _roi.PointROI(**kwargs)
        self._roi = jroi
        if point is not None:
            self.point = point

class line(_iroi, _angleprop):
    def __init__(self, jroi=None, point=None, **kwargs):
        if jroi is None:
            jroi = _roi.LinearROI(**kwargs)
        self._roi = jroi
        if point is not None:
            self.point = point

    def _getLength(self):
        return self._roi.getLength()
  
    def _setLength(self, ang):
        self._roi.setLength(float(ang))

    length = property(_getLength, _setLength)


class rectangle(_iroi, _angleprop):
    def __init__(self, jroi=None, point=None, **kwargs):
        if jroi is None:
            jroi = _roi.RectangularROI(**kwargs)
        self._roi = jroi
        if point is not None:
            self.point = point

    def _getLengths(self):
        return self._roi.getLengths()
  
    def _setLengths(self, lens):
        self._roi.setLengths([float(l) for l in lens])

    lengths = property(_getLengths, _setLengths)

class sector(_iroi):
    def __init__(self, jroi=None, point=None, **kwargs):
        if jroi is None:
            jroi = _roi.SectorROI(**kwargs)
        self._roi = jroi
        if point is not None:
            self.point = point

    def _getRadii(self):
        return self._roi.getRadii()
  
    def _setRadii(self, rads):
        self._roi.setRadii([float(r) for r in rads])

    radii = property(_getRadii, _setRadii)

    def _getAngles(self):
        return self._roi.getAngles()
 
    def _setAngles(self, angs):
        self._roi.setAngles([float(a) for a in angs])

    angles = property(_getAngles, _setAngles)

    def _getAnglesD(self):
        return self._roi.getAnglesDegrees()
 
    def _setAnglesD(self, angs):
        self._roi.setAnglesDegrees([float(a) for a in angs])

    anglesdegrees = property(_getAnglesD, _setAnglesD)

class circle(_iroi):
    def __init__(self, jroi=None, point=None, **kwargs):
        if jroi is None:
            jroi = _roi.CircularROI(**kwargs)
        self._roi = jroi
        if point is not None:
            self.point = point

    def _getRadius(self):
        return self._roi.getRadius()
  
    def _setRadius(self, rad):
        self._roi.setRadius(float(rad))

    radius = property(_getRadius, _setRadius)

class ellipse(_iroi, _angleprop):
    def __init__(self, jroi=None, point=None, **kwargs):
        if jroi is None:
            jroi = _roi.EllipticalROI(**kwargs)
        self._roi = jroi
        if point is not None:
            self.point = point

    def _getSAxes(self):
        return self._roi.getSemiAxes()
  
    def _setSAxes(self, axes):
        self._roi.setSemiAxes([float(a) for a in axes])

    semiaxes = property(_getSAxes, _setSAxes)

def _roi_wrap(arg):
    # ring when implemented
    if isinstance(arg, _roi.SectorROI):
        return sector(arg)
    # mxgrid, grid, ?axis*BoxROI, when implemented
    elif isinstance(arg, _roi.RectangularROI):
        return rectangle(arg)
    # polygon, freedraw, polyline when implemented
    elif isinstance(arg, _roi.PointROI):
        return point(arg)
    # line 3d when implemented
    elif isinstance(arg, _roi.LinearROI):
        return line(arg)
    # elliptical fit when implemented
    elif isinstance(arg, _roi.EllipticalROI):
        return ellipse(arg)
    # circular fit when implemented
    elif isinstance(arg, _roi.CircularROI):
        return circle(arg)

point_list = _roi.PointROIList
line_list = _roi.LinearROIList
rectangle_list = _roi.RectangularROIList
sector_list = _roi.SectorROIList
circle_list = _roi.CircularROIList
ellipse_list = _roi.EllipticalROIList

def _create_list(arg):
    if isinstance(arg, sector):
        return sector_list()
    elif isinstance(arg, rectangle):
        return rectangle_list()
    elif isinstance(arg, point):
        return point_list()
    elif isinstance(arg, line):
        return line_list()
    elif isinstance(arg, ellipse):
        return ellipse_list()
    elif isinstance(arg, circle):
        return circle_list()

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
