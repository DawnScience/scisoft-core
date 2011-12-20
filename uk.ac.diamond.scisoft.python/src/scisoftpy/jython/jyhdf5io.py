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
'''

from uk.ac.diamond.scisoft.analysis.io import HDF5Loader as _hdf5loader
from uk.ac.diamond.scisoft.analysis.hdf5 import HDF5Dataset as _jdataset
from uk.ac.diamond.scisoft.analysis.dataset import AbstractDataset as _adataset
from uk.ac.diamond.scisoft.analysis.dataset import LazyDataset as _ldataset

from uk.ac.diamond.scisoft.python.PythonUtils import getSlice as _getslice

from gda.analysis.io import ScanFileHolderException as io_exception

from java.io import OutputStream as _ostream #@UnresolvedImport

class _NoOutputStream(_ostream):
    def write(self, b, off, length): pass

from ..nexus.hdf5 import HDF5tree as _tree
from ..nexus.hdf5 import HDF5group as _group
from ..nexus.hdf5 import HDF5dataset as _dataset

from jycore import asarray, _isslice, _getdtypefromjdataset, Sciwrap
from jymaths import ndarraywrapped as _npwrapped

class SDS(_dataset):
    def __init__(self, dataset, attrs, parent):
        if isinstance(dataset, _jdataset):
            if dataset.isString() or not dataset.isSupported():
                dtype = 'String'
                dataset = dataset.getString()
                shape = len(dataset)
                maxshape = shape
                self.rank = 1
            else:
                maxshape = tuple(dataset.getMaxShape())
                dataset = dataset.getDataset()
                if isinstance(dataset, _ldataset) or isinstance(dataset, _adataset):
                    dtype = _getdtypefromjdataset(dataset)
                else:
                    dtype = None
                shape = tuple(dataset.getShape())
                self.rank = len(shape)
        else:
            dataset = asarray(dataset)
            shape = None
            dtype = None
            maxshape = None
            self.rank = dataset.ndim

        _dataset.__init__(self, dataset, shape, dtype, maxshape, attrs, parent)

    def _toslice(self, key):
        '''Transform key to proper slice if necessary
        '''
        if self.rank == 1:
            if isinstance(key, list) and len(key) == 1:
                key = key[0]
            if isinstance(key, slice) or key is Ellipsis:
                return True, key
            if isinstance(key, tuple):
                if len(key) > 1:
                    raise IndexError, "too many indices"
                return False, key
            return False, (key,)

        if _isslice(self.rank, self.shape, key):
            return True, key
        return False, key

    @_npwrapped
    def __getitem__(self, key):
        data = self._getdata()
        if isinstance(data, _ldataset):
            isslice,key = self._toslice(key)
            if not isslice: # single item
                key = tuple([ slice(k,k+1) for k in key ])
                v = _getslice(data, key)
                v = v.getAbs(0)
            else:
                v = _getslice(data, key)
            return v

        return data[key]

class HDF5Loader(object):
    def __init__(self, name):
        self.ldr = _hdf5loader(name)

    def load(self):
        # capture all error messages
        from java.lang import System #@UnresolvedImport
        from java.io import PrintStream #@UnresolvedImport
        oldErr = System.err
        System.setErr(PrintStream(_NoOutputStream())) #@UndefinedVariable
        tree = None
        try:
            tree = self.ldr.loadTree()
        finally:
            System.setErr(oldErr) #@UndefinedVariable

        if tree is None:
            raise io_exception, "No tree found"

        # convert tree to own tree
        r = tree.getNodeLink()
        pool = dict()
        return self._copynode(pool, r)

    def _mkgroup(self, name, link, attrs, parent):
        if parent is None:
            return _tree(link.getSource().getFilename(), attrs)
        return _group(attrs, parent)

    def _mkdataset(self, dataset, attrs, parent):
        return SDS(dataset, attrs, parent)

    def _copynode(self, pool, link, parent=None):
        node = link.getDestination()
        nid = node.getID()
#        print link.getName(), nid
        if nid in pool:
            return pool[nid]

        it = node.attributeNameIterator()
        attrs = []
        while it.hasNext():
            n = it.next()
            v = node.getAttribute(n).getValue()
            if v.size == 1:
                v = v.getObject([0])
            else:
                v = Sciwrap(v)
            attrs.append((n, v))

        if link.isDestinationAGroup():
            name = link.getName()
            g = self._mkgroup(name, link, attrs, parent)
            pool[nid] = g
            it = node.getNodeLinkIterator()
            nodes = []
            while it.hasNext():
                l = it.next()
                nodes.append((l.getName(), self._copynode(pool, l, g)))

            g.init_group(nodes)
            return g
        elif link.isDestinationADataset():
            d = self._mkdataset(node, attrs, parent)
            pool[nid] = d
            return d
        elif link.isDestinationASymLink():
            pass
        else:
            pass

    def setloadmetadata(self, load_metadata):
        pass

from uk.ac.diamond.scisoft.analysis.hdf5 import HDF5Attribute as _jattr
#from uk.ac.diamond.scisoft.analysis.hdf5 import HDF5Node as _jnode
#from uk.ac.diamond.scisoft.analysis.hdf5 import HDF5Dataset as _jdata
from uk.ac.diamond.scisoft.analysis.hdf5 import HDF5Group as _jgroup
from uk.ac.diamond.scisoft.analysis.hdf5 import HDF5File as _jfile
#from uk.ac.diamond.scisoft.analysis.hdf5 import HDF5NodeLink as _jlink
#from uk.ac.diamond.scisoft.analysis.hdf5 import HDF5SymLink as _jslink

def _tojavatree(tree):
    f = _jfile(-1, tree._filename)

    # FIXME
    g = _convertgroup()
    f.setGroup(g)
    return f

def _convertgroup(group):
    g = _jgroup(-1)
    for kg in group:
        n = group[kg]
        attrs = n.attrs
        for ka in attrs:
            ja = _jattr()
            ja.setValue(attrs[ka])
            g.addAttribute(ja)
        if isinstance(n, _group):
            pass
        elif isinstance(n, _dataset):
            pass
