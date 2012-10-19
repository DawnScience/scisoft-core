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

'''
Region of interest package

Classes available:
    point is a point ROI
    line is a linear ROI
    rectangle (rect) is a rectangular ROI
    sector (sect) is a sector ROI
    circle is a circular ROI
    ellipse is an elliptical ROI
    point_list is a list of point ROIs
    line_list (linelist) is a list of linear ROIs
    rectangle_list (rectlist) is a list of rectangular ROIs
    sector_list (sectlist) is a list of sector ROIs
    circle_list is a list of circular ROIs
    ellipse_list is a list of elliptical ROIs
'''

import os
if os.name == 'java':
    import jython.jyroi as _roi #@UnusedImport
else:
    import python.pyroi as _roi #@Reimport

 

_roibase = _roi.roibase
point = _roi.point
line = _roi.line
rectangle = rect = _roi.rectangle
sector = sect = _roi.sector
circle = _roi.circle
ellipse = _roi.ellipse

point_list = _roi.point_list
line_list = linelist = _roi.line_list
rectangle_list = rectlist = _roi.rectangle_list
sector_list = sectlist = _roi.sector_list
circle_list = _roi.circle_list
ellipse_list = _roi.ellipse_list

def isroi(r):
    '''True if r is a ROI
    '''
    return issubclass(type(r), _roibase)

profile = _roi.profile

