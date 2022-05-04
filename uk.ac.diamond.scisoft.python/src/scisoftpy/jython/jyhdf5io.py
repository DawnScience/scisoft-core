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
'''
from __future__ import print_function
from java.lang import Throwable as _throwable
try:
    from uk.ac.diamond.scisoft.analysis.io import HDF5Loader as _hdf5loader
except _throwable as e:
    import sys
    print("Could not import HDF5Loader", file=sys.stderr)
    print("Problem with path for dynamic/shared library or product bundling", file=sys.stderr)
    print(e, file=sys.stderr)
    _hdf5loader = None

from org.eclipse.dawnsci.analysis.api.tree import Tree as _jtree
from org.eclipse.dawnsci.analysis.api.tree import TreeFile as _jtreefile
from org.eclipse.dawnsci.analysis.api.tree import DataNode as _jdnode
from org.eclipse.january.dataset import Dataset as _dataset
from org.eclipse.january.dataset import LazyDataset as _ldataset

from uk.ac.diamond.scisoft.python.PythonUtils import getSlice as _getslice

from org.eclipse.dawnsci.analysis.api.io import ScanFileHolderException as io_exception

from java.io import OutputStream as _ostream #@UnresolvedImport

class _NoOutputStream(_ostream):
    def write(self, b, off, length): pass

from ..nexus.hdf5 import HDF5tree as _tree
from ..nexus.hdf5 import HDF5group as _group
from ..nexus.hdf5 import HDF5dataset as _hdataset

from .jycore import asarray, _isslice, _getdtypefromjdataset, _wrapout, Sciwrap, asIterable

def _toslice(rank, key):
    '''Transform key to proper slice if necessary
    '''
    if rank == 1:
        if isinstance(key, int):
            return False, key
        if isinstance(key, (tuple, list)):
            nk = len(key)
            if nk == 1:
                key = key[0]
            elif nk > 1:
                raise IndexError("too many indices")
        if isinstance(key, slice) or key is Ellipsis:
            return True, key
        return False, (key,)

    return _isslice(rank, key), key


class SDS(_hdataset):
    def __init__(self, dataset, attrs, parent, warn):
        if isinstance(dataset, _jdnode):
            if dataset.isString() or not dataset.isSupported():
                dataset = asarray(dataset.getString())
                shape = None
                dtype = None
                maxshape = None
                self.rank = dataset.ndim
            else:
                maxshape = tuple(dataset.getMaxShape())
                dataset = dataset.getDataset()
                if isinstance(dataset, _ldataset) or isinstance(dataset, _dataset):
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

        _hdataset.__init__(self, dataset, shape, dtype, maxshape, attrs, parent, warn)

    @_wrapout
    def __getitem__(self, key):
        data = self._getdata()
        if isinstance(data, _ldataset):
            isslice, key = _toslice(self.rank, key)
            if not isslice: # single item
                key = tuple([ slice(k,k+1) for k in asIterable(key) ])
                v = _getslice(data, key)
                v = v.getAbs(0)
            else:
                v = _getslice(data, key)
            return v

        return asarray(data)[key]

class HDF5Loader(object):
    def __init__(self, name):
        if _hdf5loader is None:
            raise io_exception("HDf5 loader could not be imported")
        self.ldr = _hdf5loader(name)

    def load(self, warn=True):
        tree = self._load_tree()
        self.warn = warn
        if tree is None:
            raise io_exception("No tree found")

        # convert tree to own tree
        r = tree.getNodeLink()
        pool = dict()
        return self._copynode(pool, r, tree)

    def _load_tree(self):
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

        return tree

    def _mkgroup(self, name, link, attrs, parent):
        if isinstance(parent, _jtree):
            src = parent.getFilename() if isinstance(parent, _jtreefile) else parent.getSourceURI()
            return _tree(src, attrs, parent)
        return _group(attrs, parent, self.warn)

    def _mkdataset(self, dataset, attrs, parent):
        return SDS(dataset, attrs, parent, self.warn)

    def _copynode(self, pool, link, parent=None):
        node = link.getDestination()
        nid = node.getID()
#        print link.getName(), nid
        if nid in pool:
            return pool[nid]

        it = node.getAttributeNameIterator()
        attrs = []
        while it.hasNext():
            n = next(it)
            v = node.getAttribute(n).getValue()
            if v.getRank() == 0:
                attrs.append((n, v.getObject()))
            else:
                attrs.append((n, Sciwrap(v)))

        if link.isDestinationGroup():
            name = link.getName()
            g = self._mkgroup(name, link, attrs, parent)
            pool[nid] = g
            nodes = [(l.getName(), self._copynode(pool, l, g)) for l in node]
            g.init_group(nodes)
            return g
        elif link.isDestinationData():
            d = self._mkdataset(node, attrs, parent)
            pool[nid] = d
            return d
        elif link.isDestinationSymbolic():
            pass
        else:
            pass

    def setloadmetadata(self, load_metadata):
        pass

from org.eclipse.dawnsci.analysis.tree.impl import AttributeImpl as _jattr
# from org.eclipse.dawnsci.analysis.tree.impl import NodeImpl as _jnode
# from org.eclipse.dawnsci.analysis.tree.impl import DataNodeImpl as _jdata
from org.eclipse.dawnsci.analysis.tree.impl import GroupNodeImpl as _jgroup
from org.eclipse.dawnsci.analysis.tree.impl import TreeFileImpl as _jfile
# from org.eclipse.dawnsci.analysis.tree.impl import NodeLinkImpl as _jlink
# from org.eclipse.dawnsci.analysis.tree.impl import SymbolicNodeImpl as _jslink

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
        elif isinstance(n, _hdataset):
            pass
