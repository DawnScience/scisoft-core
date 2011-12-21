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


class roibase(object):
    _SPT = "spt"
    _PLOT = "plot"

    def __init__(self, spt=[0.0,0.0], plot=False, **kwargs):
        super(roibase, self).__init__()
        self.spt = spt
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

class line(roibase):
    _LEN = "len"
    _ANG = "ang"
    _CROSS_HAIR = "crossHair"

    def __init__(self, len=0.0, ang=0.0, crossHair=False, **kwargs):
        super(line, self).__init__(**kwargs)
        self.len = len
        self.ang = ang
        self.crossHair = crossHair

class rect(roibase):
    _LEN = "len"
    _ANG = "ang"
    _CLIPPING_COMPENSATION = "clippingCompensation"
    
    def __init__(self, len=[0.0,0.0], ang=0.0, clippingCompensation=False, **kwargs):
        super(rect, self).__init__(**kwargs)
        self.len = len
        self.ang = ang
        self.clippingCompensation = clippingCompensation
    
    def getAngleDegrees(self):
        return _math.degrees(self.ang)

class sect(roibase):
    _ANG = "ang";
    _RAD = "rad";
    _CLIPPING_COMPENSATION = "clippingCompensation";
    _SYMMETRY = "symmetry";
    _COMBINE_SYMMETRY = "combineSymmetry";
    _AVERAGE_AREA = "averageArea";
    
    # Symmetry options
    NONE = 0;
    FULL = 1;
    XREFLECT = 2;
    YREFLECT = 3;
    CNINETY = 4;
    ACNINETY = 5;
    INVERT = 6;

    def __init__(self, ang=[0.0, 0.0], rad=[0.0, 0.0], clippingCompensation=False, symmetry=NONE, combineSymmetry=False, averageArea=False, **kwargs):
        super(sect, self).__init__(**kwargs)
        self.ang = ang
        self.rad = rad
        self.clippingCompensation = clippingCompensation
        self.symmetry = symmetry
        self.combineSymmetry = combineSymmetry
        self.averageArea = averageArea

class roilist(list):
    def __init__(self):
        super(roilist, self).__init__()

    def __eq__(self, other):
        return isinstance(other, self.__class__) and super(roilist, self).__eq__(other)

    def __ne__(self, other):
        return not self.__eq__(other)

class linelist(roilist):
    def __init__(self):
        super(linelist, self).__init__()

class rectlist(roilist):
    def __init__(self):
        super(rectlist, self).__init__()

class sectlist(roilist):
    def __init__(self):
        super(sectlist, self).__init__()


def profile(*args):
    #TODO
    print "Unimplemented"
