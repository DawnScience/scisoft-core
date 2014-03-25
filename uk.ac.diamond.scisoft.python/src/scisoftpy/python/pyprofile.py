###
# Copyright 2013 Diamond Light Source Ltd.
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
Implementation of profile function using Py4J so Dawn is required with the
Py4J default server activated (through Window > Preferences >
Py4J Default Server) as well as a Python installation of Py4J  
'''
from pyroi import line, rectangle, sector

import py4jutils as _py4j

def profile(data, roi, step=None, mask=None):
    '''Calculate a profile with given roi (a step value is required for a linear ROI)
    mask is used when clipping compensation is set true (for rectangular and sector ROI)
    '''
    j = _py4j.get_gateway().jvm
    profile = j.uk.ac.diamond.scisoft.analysis.roi.ROIProfile
    if isinstance(roi, line):
        roi  = _py4j.convert_line_roi(roi)
        if step is None:
            raise ValueError, "step value required"
        datasets = _py4j.convert_arrays([data])
        pdatasets = profile.line(datasets[0], roi, float(step))
        return _py4j.convert_datasets(pdatasets)
    if isinstance(roi, rectangle):
        roi  = _py4j.convert_rectangle_roi(roi)
        datasets = _py4j.convert_arrays([data, mask])
        if mask is None:
            pdatasets = profile.box(datasets[0], roi)
        else:
            pdatasets = profile.box(datasets[0], datasets[1], roi)
        return _py4j.convert_datasets(pdatasets)
    if isinstance(roi, sector):
        roi  = _py4j.convert_sector_roi(roi)
        datasets = _py4j.convert_arrays([data, mask])
        if mask is None:
            pdatasets = profile.sector(datasets[0], roi)
        else:
            pdatasets = profile.sector(datasets[0], datasets[1], roi)
        return _py4j.convert_datasets(pdatasets)
    raise TypeError, "roi is not of known type"

if __name__ == '__main__':
    import scisoftpy as dnp
    im = dnp.io.load('/scratch/images/test.png')[0]
    l = dnp.roi.line(point=[64, 111], length=242, angle=1.0)
    p = dnp.roi.profile(im, l, 0.5)
    print 'end!'
