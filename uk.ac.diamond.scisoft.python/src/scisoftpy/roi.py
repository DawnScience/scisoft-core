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

'''
Region of interest package

Classes available:
    line is a linear ROI
    rect is a rectangular ROI
    sect is a sector ROI
    linelist is a list of ROIs
    rectlist is a list of ROIs
    sectlist is a list of ROIs
'''

import os
if os.name == 'java':
    import jython.jyroi as _roi #@UnusedImport
else:
    import python.pyroi as _roi #@Reimport

 

roibase = _roi.roibase
line = _roi.line

rect = _roi.rect
sect = _roi.sect

linelist = _roi.linelist
rectlist = _roi.rectlist
sectlist = _roi.sectlist

def isroi(r):
    '''True if r is a ROI
    '''
    return issubclass(type(r), roibase)

profile = _roi.profile

