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
_GATEWAY = None

def _get_gateway():
    global _GATEWAY
    import sys
    if _GATEWAY is None:
        try:
            from py4j.java_gateway import JavaGateway
            from py4j.protocol import Py4JNetworkError
            _GATEWAY = JavaGateway(eager_load=True) # flag to check it's okay immediately
            return _GATEWAY
        except ImportError, ie:
            print >> sys.stderr, "No Py4J found - check your python installation"
            print >> sys.stderr, ie
        except Py4JNetworkError, ne:
            print >> sys.stderr, "Dawn JVM not found - switch on Py4J server in Window > Preferences > Py4J Default Server"
            print >> sys.stderr, ne
    else:
        return _GATEWAY

def _mk_line_roi(roi):
    j = _get_gateway().jvm
    l = j.uk.ac.diamond.scisoft.analysis.roi.LinearROI()
    l.setPoint(roi.point[0], roi.point[1])
    l.setLength(roi.length)
    l.setAngleDegrees(roi.angledegrees)
    return l

def _mk_rect_roi(roi):
    j = _get_gateway().jvm
    r = j.uk.ac.diamond.scisoft.analysis.roi.RectangularROI()
    r.setPoint(roi.point[0], roi.point[1])
    r.setLengths(roi.lengths[0], roi.lengths[1])
    r.setAngleDegrees(roi.angledegrees)
    return r

def _mk_sect_roi(roi):
    j = _get_gateway().jvm
    s = j.uk.ac.diamond.scisoft.analysis.roi.SectorROI()
    s.setPoint(roi.point[0], roi.point[1])
    s.setRadii(roi.radii[0], roi.radii[1])
    s.setAnglesDegrees(roi.anglesdegrees[0], roi.anglesdegrees[1])
    return s

from numpy import save as _asave, load as _aload  # @UnresolvedImport
from scisoftpy import ndarray
from pyroi import line, rectangle, sector
from os import path as _path, remove as _remove, rmdir as _rmdir
import tempfile as _tmp

def _pysave_arrays(args, dir=None):  # @ReservedAssignment
    p = _tmp.mkdtemp(prefix='py4j-pyargs', dir=dir)
    n = 0
    names = []
    for arg in args:
        if isinstance(arg, ndarray):
            name = _path.join(p, "p%03d.npy" % n)
            _asave(name, arg)
            n += 1
            names.append(name)
    return names

def _jsave_datasets(datasets, dir=None):  # @ReservedAssignment
    p = _tmp.mkdtemp(prefix='py4j-jargs', dir=dir)
    n = 0
    j = _get_gateway().jvm
    dh = j.uk.ac.diamond.scisoft.analysis.io.DataHolder()
    svr = j.uk.ac.diamond.scisoft.analysis.io.NumPyFileSaver
    names = []
    for d in datasets:
        if d is None:
            continue
        name = _path.join(p, "p%03d.npy" % n)
        dh.clear()
        dh.addDataset(str(n), d)
        fs = svr(name)
        fs.saveFile(dh)
        n += 1
        names.append(name)
    return names

def _pyload_arrays(names):
    arrays = []
    for n in names:
        try:
            arrays.append(_aload(n))
        finally:
            _remove(n)
    _rmdir(_path.dirname(names[0]))
    return arrays

def _jload_datasets(names):
    datasets = []
    ldr = _get_gateway().jvm.uk.ac.diamond.scisoft.analysis.io.NumPyFileLoader
    for n in names:
        try:
            datasets.append(ldr(n).loadFile().getDataset(0))
        finally:
            _remove(n)
    _rmdir(_path.dirname(names[0]))
    return datasets

def profile(data, roi, step=None, mask=None):
    '''Calculate a profile with given roi (a step value is required for a linear ROI)
    mask is used when clipping compensation is set true (for rectangular and sector ROI)
    '''
    j = _get_gateway().jvm
    profile = j.uk.ac.diamond.scisoft.analysis.roi.ROIProfile
    if isinstance(roi, line):
        roi  = _mk_line_roi(roi)
        if step is None:
            raise ValueError, "step value required"
        names = _pysave_arrays([data])
        datasets = _jload_datasets(names)
        pdatasets = profile.line(datasets[0], roi, float(step))
        pnames = _jsave_datasets(pdatasets)
        return _pyload_arrays(pnames)
    if isinstance(roi, rectangle):
        roi  = _mk_rect_roi(roi)
        names = _pysave_arrays([data, mask])
        datasets = _jload_datasets(names)
        if mask is None:
            pdatasets = profile.box(datasets[0], roi)
        else:
            pdatasets = profile.box(datasets[0], datasets[1], roi)
        pnames = _jsave_datasets(pdatasets)
        return _pyload_arrays(pnames)
    if isinstance(roi, sector):
        roi  = _mk_sect_roi(roi)
        names = _pysave_arrays([data, mask])
        datasets = _jload_datasets(names)
        if mask is None:
            pdatasets = profile.sector(datasets[0], roi)
        else:
            pdatasets = profile.sector(datasets[0], datasets[1], roi)
        pnames = _jsave_datasets(pdatasets)
        return _pyload_arrays(pnames)
    raise TypeError, "roi is not of known type"

if __name__ == '__main__':
    import scisoftpy as dnp
    im = dnp.io.load('/scratch/images/test.png')[0]
#     names = _pysave_arrays([im])
#     datasets = _jload_datasets(names)
    names = _pysave_arrays([im])
    datasets = _jload_datasets(names)
    pnames = _jsave_datasets(datasets)
    arrays = _pyload_arrays(pnames)
#     l = dnp.roi.line(point=[64, 111], length=242, angle=1.0)
#     p = dnp.roi.profile(im, l, 0.5)
    print 'end!'
