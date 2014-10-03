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

from uk.ac.diamond.scisoft.analysis.roi import ROIProfile as _profile
from org.eclipse.dawnsci.analysis.api.roi import IROI as _iroi
import org.eclipse.dawnsci.analysis.dataset.roi as _roi
from jycore import _wrap

_jroi = _iroi

# base class for implementing point property
class _iroi(object):
    def _jroi(self):
        return self._roi

    def getPoint(self):
        return self._roi.getPoint()
 
    def setPoint(self, pt):
        self._roi.setPoint([float(p) for p in pt])

    point = property(getPoint, setPoint)

    def getName(self):
        return self._roi.getName()
 
    def setName(self, n):
        self._roi.setName(str(n))

    name = property(getName, setName)

    def isPlot(self):
        return self._roi.isPlot()
 
    def setPlot(self, p):
        self._roi.setPlot(bool(p))

    plot = property(isPlot, setPlot)

    def __repr__(self):
        return "%s(%s)" % (self.__class__.__name__, self._roi.toString())

    def copy(self):
        from copy import copy
        c = copy(self)
        c._roi = self._roi.copy()
        return c

# class to mix-in for implementing angle property
class _angleprop(object):
    def getAngle(self):
        return self._roi.getAngle()
  
    def setAngle(self, ang):
        self._roi.setAngle(float(ang))

    angle = property(getAngle, setAngle)

    def getAngleDegrees(self):
        return self._roi.getAngleDegrees()
  
    def setAngleDegrees(self, ang):
        self._roi.setAngleDegrees(float(ang))

    angledegrees = property(getAngleDegrees, setAngleDegrees)

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

    def getLength(self):
        return self._roi.getLength()
  
    def setLength(self, ang):
        self._roi.setLength(float(ang))

    length = property(getLength, setLength)


class rectangle(_iroi, _angleprop):
    def __init__(self, jroi=None, point=None, **kwargs):
        if jroi is None:
            jroi = _roi.RectangularROI(**kwargs)
        self._roi = jroi
        if point is not None:
            self.point = point

    def getLengths(self):
        return self._roi.getLengths()
  
    def setLengths(self, lens):
        self._roi.setLengths([float(l) for l in lens])

    lengths = property(getLengths, setLengths)

class sector(_iroi):
    def __init__(self, jroi=None, point=None, **kwargs):
        if jroi is None:
            jroi = _roi.SectorROI(**kwargs)
        self._roi = jroi
        if point is not None:
            self.point = point

    def getRadii(self):
        return self._roi.getRadii()
  
    def setRadii(self, rads):
        self._roi.setRadii([float(r) for r in rads])

    radii = property(getRadii, setRadii)

    def getAngles(self):
        return self._roi.getAngles()
 
    def setAngles(self, angs):
        self._roi.setAngles([float(a) for a in angs])

    angles = property(getAngles, setAngles)

    def getAnglesDegrees(self):
        return self._roi.getAnglesDegrees()
 
    def setAnglesDegrees(self, angs):
        self._roi.setAnglesDegrees([float(a) for a in angs])

    anglesdegrees = property(getAnglesDegrees, setAnglesDegrees)

class circle(_iroi):
    def __init__(self, jroi=None, point=None, **kwargs):
        if jroi is None:
            jroi = _roi.CircularROI(**kwargs)
        self._roi = jroi
        if point is not None:
            self.point = point

    def getRadius(self):
        return self._roi.getRadius()
  
    def setRadius(self, rad):
        self._roi.setRadius(float(rad))

    radius = property(getRadius, setRadius)

class ellipse(_iroi, _angleprop):
    def __init__(self, jroi=None, point=None, **kwargs):
        if jroi is None:
            jroi = _roi.EllipticalROI(**kwargs)
        self._roi = jroi
        if point is not None:
            self.point = point

    def getSemiAxes(self):
        return self._roi.getSemiAxes()
  
    def setSemiAxes(self, axes):
        self._roi.setSemiAxes([float(a) for a in axes])

    semiaxes = property(getSemiAxes, setSemiAxes)

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
    return arg

class _roi_list(list):
    def append(self, item):
        if isinstance(item, self._jcls):
            item = _roi_wrap(item)
        if not isinstance(item, self._pcls):
            raise TypeError, "Item is wrong type for list"
        super(_roi_list, self).append(item)

    add = append

    def _jroilist(self):
        l = self._jlcls()
        for r in self:
            l.add(r._jroi())
        return l

class point_list(_roi_list):
    _jcls = _roi.PointROI
    _jlcls = _roi.PointROIList
    _pcls = point

class line_list(_roi_list):
    _jcls = _roi.LinearROI
    _jlcls = _roi.LinearROIList
    _pcls = line

class rectangle_list(_roi_list):
    _jcls = _roi.RectangularROI
    _jlcls = _roi.RectangularROIList
    _pcls = rectangle

class sector_list(_roi_list):
    _jcls = _roi.SectorROI
    _jlcls = _roi.SectorROIList
    _pcls = sector

class circle_list(_roi_list):
    _jcls = _roi.CircularROI
    _jlcls = _roi.CircularROIList
    _pcls = circle

class ellipse_list(_roi_list):
    _jcls = _roi.EllipticalROI
    _jlcls = _roi.EllipticalROIList
    _pcls = ellipse

def _create_list(arg):
    if isinstance(arg, _iroi):
        arg = arg._jroi()
    if isinstance(arg, _roi.SectorROI):
        return sector_list()
    elif isinstance(arg, _roi.RectangularROI):
        return rectangle_list()
    elif isinstance(arg, _roi.PointROI):
        return point_list()
    elif isinstance(arg, _roi.LinearROI):
        return line_list()
    elif isinstance(arg, _roi.EllipticalROI):
        return ellipse_list()
    elif isinstance(arg, _roi.CircularROI):
        return circle_list()
    raise ValueError, "ROI not recognised"

# No-one should be using this
#ROIProfile = _profile

@_wrap
def profile(data, roi, step=None, mask=None):
    '''Calculate a profile with given roi (a step value is required for a linear ROI)
    mask is used when clipping compensation is set true (for rectangular and sector ROI)
    '''
    if isinstance(roi, line):
        roi  = roi._jroi()
        if step is None:
            raise ValueError, "step value required"
        return _profile.line(data, roi, step)
    if isinstance(roi, rectangle):
        roi  = roi._jroi()
        if mask is None:
            return _profile.box(data, roi)
        else:
            return _profile.box(data, mask, roi)
    if isinstance(roi, sector):
        roi  = roi._jroi()
        if mask is None:
            return _profile.sector(data, roi)
        else:
            return _profile.sector(data, mask, roi)
    raise TypeError, "roi is not of known type"
