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

    def __init__(self, spt=[0.0,0.0], plot=False, **kwargs):
        super(roibase, self).__init__()
        self.spt = [float(p) for p in spt]
        self.plot = plot

    # rois are not hashable because they are mutable
    __hash__ = None
    
    def __eq__(self, other):
        return (isinstance(other, self.__class__)
            and self.__dict__ == other.__dict__)

    def __ne__(self, other):
        return not self.__eq__(other)

    def __repr__(self):
        return '%s(%s)' % (self.__class__.__name__, self.__dict__.__repr__())

    def setPoint(self, *point):
        self.spt = [float(p) for p in point]

    def getPoint(self):
        return self.spt

    point = property(getPoint, setPoint)

class point(roibase):
    def __init__(self, **kwargs):
        super(point, self).__init__(**kwargs)

class line(roibase):
    _LEN = "len"
    _ANG = "ang"
    _CROSS_HAIR = "crossHair"

    def __init__(self, len=0.0, ang=0.0, crossHair=False, **kwargs): #@ReservedAssignment
        super(line, self).__init__(**kwargs)
        self.len = float(len)
        self.ang = float(ang)
        self.crossHair = crossHair

class rectangle(roibase):
    _LEN = "len"
    _ANG = "ang"
    _CLIPPING_COMPENSATION = "clippingCompensation"
    
    def __init__(self, len=[0.0,0.0], ang=0.0, clippingCompensation=False, **kwargs): #@ReservedAssignment
        super(rectangle, self).__init__(**kwargs)
        self.len = [float(l) for l in len]
        self.ang = float(ang)
        self.clippingCompensation = clippingCompensation
    
    def getAngleDegrees(self):
        return _math.degrees(self.ang)

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

    def __init__(self, ang=[0.0, 0.0], rad=[0.0, 0.0], clippingCompensation=False, symmetry=NONE, combineSymmetry=False, averageArea=False, **kwargs):
        super(sector, self).__init__(**kwargs)
        self.ang = [float(a) for a in ang]
        self.rad = [float(r) for r in rad]
        self.clippingCompensation = clippingCompensation
        self.symmetry = symmetry
        self.combineSymmetry = combineSymmetry
        self.averageArea = averageArea

class circle(roibase):
    _RAD = "rad"
    
    def __init__(self, rad=1.0, **kwargs): #@ReservedAssignment
        super(circle, self).__init__(**kwargs)
        self.rad = float(rad)

class ellipse(roibase):
    _SAXIS = "saxis"
    _ANG = "ang"
    
    def __init__(self, saxis=[0.0,0.0], ang=0.0, **kwargs): #@ReservedAssignment
        super(ellipse, self).__init__(**kwargs)
        self.saxis = [float(l) for l in saxis]
        self.ang = float(ang)
    
    def getAngleDegrees(self):
        return _math.degrees(self.ang)

class roi_list(list):
    def __init__(self):
        super(roi_list, self).__init__()

    def __eq__(self, other):
        return isinstance(other, self.__class__) and super(roi_list, self).__eq__(other)

    def __ne__(self, other):
        return not self.__eq__(other)

    def add(self, item): # cover Java list usage
        self.append(item)

class point_list(roi_list):
    def __init__(self):
        super(point_list, self).__init__()

class line_list(roi_list):
    def __init__(self):
        super(line_list, self).__init__()

class rectangle_list(roi_list):
    def __init__(self):
        super(rectangle_list, self).__init__()

class sector_list(roi_list):
    def __init__(self):
        super(sector_list, self).__init__()

class circle_list(roi_list):
    def __init__(self):
        super(circle_list, self).__init__()

class ellipse_list(roi_list):
    def __init__(self):
        super(ellipse_list, self).__init__()

def profile(*args):
    #TODO
    print "Unimplemented"
