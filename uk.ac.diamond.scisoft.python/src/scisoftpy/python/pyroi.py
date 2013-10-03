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

import math as _math

class _iroi(object):
    pass

class roibase(_iroi):
    _NAME = "name"
    _SPT = "spt"
    _PLOT = "plot"

    def __init__(self, name='', point=[0.0,0.0], spt=None, plot=False, **kwargs):
        super(roibase, self).__init__()
        self.name = name
        self.spt = [float(p) for p in spt] if spt is not None else [float(p) for p in point]
        self.plot = plot

    # rois are not hashable because they are mutable
    __hash__ = None
    
    def __eq__(self, other):
        return (isinstance(other, self.__class__)
            and self.__dict__ == other.__dict__)

    def __ne__(self, other):
        return not self.__eq__(other)

    def __repr__(self):
        return "%s(%s)" % (self.__class__.__name__, self.__dict__.__repr__())

    def getPoint(self):
        return self.spt

    def setPoint(self, pt):
        self.spt = [float(p) for p in pt]

    point = property(getPoint, setPoint)

    def isPlot(self):
        return self.plot

    def setPlot(self, p):
        self.plot = bool(p)

    def copy(self):
        from copy import deepcopy
        return deepcopy(self)

class point(roibase):
    def __init__(self, **kwargs):
        super(point, self).__init__(**kwargs)

class line(roibase):
    _LEN = "len"
    _ANG = "ang"
    _CROSS_HAIR = "crossHair"

    def __init__(self, length=0.0, len=None, angle=0.0, ang=None, angledegrees=None, crossHair=False, **kwargs): #@ReservedAssignment
        super(line, self).__init__(**kwargs)
        self.len = float(len) if len is not None else float(length)
        self.ang = float(ang) if ang is not None else float(angle)
        if angledegrees is not None:
            self.ang = _math.radians(angledegrees)
        self.crossHair = crossHair

    def getLength(self):
        return self.len

    def setLength(self, length):
        self.len = float(length)

    length = property(getLength, setLength)

    def getAngle(self):
        return self.ang

    def setAngle(self, angle):
        self.ang = float(angle)

    angle = property(getAngle, setAngle)

    def getAngleDegrees(self):
        return _math.degrees(self.ang)

    def setAngleDegrees(self, angle):
        self.ang = _math.radians(angle)

    angledegrees = property(getAngleDegrees, setAngleDegrees)

class rectangle(roibase):
    _LEN = "len"
    _ANG = "ang"
    _CLIPPING_COMPENSATION = "clippingCompensation"
    
    def __init__(self, lengths=[0.0,0.0], len=None, angle=0.0, ang=None, angledegrees=None, clippingCompensation=False, **kwargs): #@ReservedAssignment
        super(rectangle, self).__init__(**kwargs)
        self.len = [float(l) for l in len] if len is not None else [float(l) for l in lengths]
        self.ang = float(ang) if ang is not None else float(angle)
        if angledegrees is not None:
            self.ang = _math.radians(angledegrees)
        self.clippingCompensation = clippingCompensation

    def getLengths(self):
        return self.len

    def setLengths(self, length):
        self.len = [float(l) for l in length]

    lengths = property(getLengths, setLengths)

    def getAngle(self):
        return self.ang

    def setAngle(self, angle):
        self.ang = float(angle)

    angle = property(getAngle, setAngle)

    def getAngleDegrees(self):
        return _math.degrees(self.ang)

    def setAngleDegrees(self, angle):
        self.ang = _math.radians(angle)

    angledegrees = property(getAngleDegrees, setAngleDegrees)

class sector(roibase):
    _ANG = "ang"
    _RAD = "rad"
    _CLIPPING_COMPENSATION = "clippingCompensation"
    _SYMMETRY = "symmetry"
    _COMBINE_SYMMETRY = "combineSymmetry"
    _AVERAGE_AREA = "averageArea"
    
    # Symmetry options
    NONE = 0
    FULL = 1
    XREFLECT = 2
    YREFLECT = 3
    CNINETY = 4
    ACNINETY = 5
    INVERT = 6

    def __init__(self, angles=[0.0, 0.0], ang=None, anglesdegrees=None, radii=[0.0, 0.0], rad=None, clippingCompensation=False, symmetry=NONE, combineSymmetry=False, averageArea=False, **kwargs):
        super(sector, self).__init__(**kwargs)
        self.ang = [float(a) for a in ang] if ang is not None else [float(a) for a in angles]
        if anglesdegrees is not None:
            self.ang = [_math.radians(a) for a in anglesdegrees]
        self.rad = [float(r) for r in rad] if rad is not None else [float(r) for r in radii]
        self.clippingCompensation = clippingCompensation
        self.symmetry = symmetry
        self.combineSymmetry = combineSymmetry
        self.averageArea = averageArea

    def getRadii(self):
        return self.rad
 
    def setRadii(self, radius):
        self.rad = [float(r) for r in radius]
 
    radii = property(getRadii, setRadii)

    def _chkAngles(self):
        if len(self.ang) != 2:
            raise ValueError, "Need two angles" 
        a = self.ang[0]
        tpi = 2 * _math.pi
        while a >= self.ang[1]:
            self.ang[1] += tpi

        a += tpi
        while a < self.ang[1]:
            self.ang[1] -= tpi

        while self.ang[0] < 0:
            self.ang[0] += tpi
            self.ang[1] += tpi

        while self.ang[0] > tpi:
            self.ang[0] -= tpi
            self.ang[1] -= tpi

    def getAngles(self):
        return self.ang

    def setAngles(self, angle):
        self.ang = [float(a) for a in angle]
        self._chkAngles()

    angles = property(getAngles, setAngles)

    def getAnglesDegrees(self):
        return [_math.degrees(a) for a in self.ang]

    def setAnglesDegrees(self, angle):
        self.ang = [_math.radians(a) for a in angle]
        self._chkAngles()

    anglesdegrees = property(getAnglesDegrees, setAnglesDegrees)

class circle(roibase):
    _RAD = "rad"
    
    def __init__(self, radius=1.0, rad=None, **kwargs): #@ReservedAssignment
        super(circle, self).__init__(**kwargs)
        self.rad = float(rad) if rad is not None else float(radius)

    def getRadius(self):
        return self.rad

    def setRadius(self, radius):
        self.rad = float(radius)

    radius = property(getRadius, setRadius)

class ellipse(roibase):
    _SAXIS = "saxis"
    _ANG = "ang"
    
    def __init__(self, semiaxes=[0.0,0.0], saxis=None, angle=0.0, ang=None, angledegrees=None, **kwargs): #@ReservedAssignment
        super(ellipse, self).__init__(**kwargs)
        self.saxis = [float(l) for l in saxis] if saxis is not None else [float(l) for l in semiaxes]
        self.ang = float(ang) if ang is not None else float(angle)
        if angledegrees is not None:
            self.ang = _math.radians(angledegrees)

    def getSemiAxes(self):
        return self.saxis

    def setSemiAxes(self, axes):
        self.saxis = [float(a) for a in axes]

    semiaxes = property(getSemiAxes, setSemiAxes)

    def getAngle(self):
        return self.ang

    def setAngle(self, angle):
        self.ang = float(angle)

    angle = property(getAngle, setAngle)

    def getAngleDegrees(self):
        return _math.degrees(self.ang)

    def setAngleDegrees(self, angle):
        self.ang = _math.radians(angle)

    angledegrees = property(getAngleDegrees, setAngleDegrees)

class roi_list(list):
    def __init__(self):
        super(roi_list, self).__init__()

    def __eq__(self, other):
        return isinstance(other, self.__class__) and super(roi_list, self).__eq__(other)

    def __ne__(self, other):
        return not self.__eq__(other)

    def append(self, item):
        if not isinstance(item, self._pcls):
            raise TypeError, "Item is wrong type"
        super(roi_list, self).append(item)

    add = append # cover Java list usage

class point_list(roi_list):
    _pcls = point

class line_list(roi_list):
    _pcls = line

class rectangle_list(roi_list):
    _pcls = rectangle

class sector_list(roi_list):
    _pcls = sector

class circle_list(roi_list):
    _pcls = circle

class ellipse_list(roi_list):
    _pcls = ellipse

def profile(*args):
    #TODO
    print "Unimplemented"
