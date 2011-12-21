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

