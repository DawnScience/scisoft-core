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
Utilities for using Py4J to access so Dawn Java code.
So DAWN is required with the Py4J default server activated (through Window > 
Preferences > Py4J Default Server) as well as a Python installation of Py4J

1)
The py4j gateway can be accessed via get_gateway() to provide an entry
point to Java classes.
Take care that the package where the accessed Java class is located is
exported by its plug-in and add a dependency to the plugin in
uk.ac.diamond.scisoft.analysis.osgi/MANIFEST.FM.
To make this dependency explicit, a class from the desired package/plugin
should be added to uk.ac.diamond.scisoft.analysis.osgi.ClassLoaderServiceImpl.java

2)
convert_arrays(arrays) and convert_datasets(datasets) provide conversion
between (NumPy) arrays in Python and datasets in Java
'''
_GATEWAY = None

def get_gateway():
    '''
    Get Py4J gateway.
    Prints errors and return None if it does not work
    '''
    global _GATEWAY
    if _GATEWAY is not None:
        return _GATEWAY

    import sys
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

def convert_line_roi(roi):
    '''
    Convert a line ROI to its Java equivalent
    '''
    j = get_gateway().jvm
    l = j.uk.ac.diamond.scisoft.analysis.roi.LinearROI()
    l.setPoint(roi.point[0], roi.point[1])
    l.setLength(roi.length)
    l.setAngleDegrees(roi.angledegrees)
    return l

def convert_rectangle_roi(roi):
    '''
    Convert a rectangle ROI to its Java equivalent
    '''
    j = get_gateway().jvm
    r = j.uk.ac.diamond.scisoft.analysis.roi.RectangularROI()
    r.setPoint(roi.point[0], roi.point[1])
    r.setLengths(roi.lengths[0], roi.lengths[1])
    r.setAngleDegrees(roi.angledegrees)
    return r

def convert_sector_roi(roi):
    '''
    Convert a sector ROI to its Java equivalent
    '''
    j = get_gateway().jvm
    s = j.uk.ac.diamond.scisoft.analysis.roi.SectorROI()
    s.setPoint(roi.point[0], roi.point[1])
    s.setRadii(roi.radii[0], roi.radii[1])
    s.setAnglesDegrees(roi.anglesdegrees[0], roi.anglesdegrees[1])
    return s

from numpy import save as _asave, load as _aload  # @UnresolvedImport
from scisoftpy import ndarray
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
    j = get_gateway().jvm
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
    n = len(names)
    gw = get_gateway()
    datasets = gw.new_array(gw.jvm.uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset, n)
    ldr = get_gateway().jvm.uk.ac.diamond.scisoft.analysis.io.NumPyFileLoader
    for i in range(n):
        try:
            datasets[i] = ldr(names[i]).loadFile().getDataset(0)
        finally:
            _remove(names[i])
    _rmdir(_path.dirname(names[0]))
    return datasets

def convert_datasets(datasets):
    '''
    Convert sequence of Java datasets to list of NumPy arrays
    '''
    names = _jsave_datasets(datasets)
    return _pyload_arrays(names)

def convert_arrays(arrays):
    '''
    Convert sequence of NumPy arrays to (Java) array of Java datasets
    '''
    names = _pysave_arrays(arrays)
    return _jload_datasets(names)

if __name__ == '__main__':
    import scisoftpy as dnp
    im = dnp.io.load('/scratch/images/test.png')[0]
    names = _pysave_arrays([im])
    datasets = _jload_datasets(names)
    pnames = _jsave_datasets(datasets)
    arrays = _pyload_arrays(pnames)
    print 'end!'
