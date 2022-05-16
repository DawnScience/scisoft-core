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

from scisoftpy.dictutils import ListDict as _ldict

class HDF5node(object):
    def __init__(self, attrs={}, parent=None, warn=True):
        '''
        '''
        super(HDF5node, self).__setattr__('attrs', _ldict(attrs, lock=True, warn=warn))
        super(HDF5node, self).__setattr__('__parent', parent)

    def _getparent(self):
        return super(HDF5node, self).__getattribute__('__parent')

    def _setparent(self, parent):
        super(HDF5node, self).__setattr__('__parent', parent)

class HDF5dataset(HDF5node):
    def __init__(self, dataset, shape=None, dtype=None, maxshape=None, attrs={}, parent=None, warn=True):
        '''
        '''
        HDF5node.__init__(self, attrs, parent, warn)
        self.__data = dataset

        if shape is None:
            shape = dataset.shape
        self.__shape = shape

        if dtype is None:
            dtype = dataset.dtype
        self.__dtype = dtype

        if maxshape is None:
            maxshape = shape
        self.__maxshape = maxshape

    @property
    def shape(self):
        return self.__shape

    @property
    def maxshape(self):
        '''Maximum shape (-1 indicates an unlimited dimension)
        '''
        return self.__maxshape

    @property
    def dtype(self):
        return self.__dtype

    def __getitem__(self, key):
        return self.__data[key]

    def __str__(self):
        s = "   @shape = %s\n" % (self.__shape,)
        s += "   @maxshape = %s\n" % (self.__maxshape,)
        for a in self.attrs:
            s += "   @%s = %s\n" % (a, self.attrs[a]) 
        return s

    def _getdata(self):
        return self.__data

class HDF5group(_ldict, HDF5node):
    def __init__(self, attrs={}, parent=None, warn=True):
        _ldict.__init__(self, warn=warn)
        HDF5node.__init__(self, attrs, parent, warn)

    def __getitem__(self, key):
        if isinstance(key, str):
            return self.__findnode(key)
        return _ldict.__getitem__(self, key)

    def __findroot(self):
        p = self._getparent()
        if p is not None:
            return p.__findroot()
        return self

    def __findnode(self, key):
        if '/' in key:
            nodes = key.split('/')
            if key.startswith('/'):
                g = self.__findroot()
            else:
                g = self
            for n in nodes:
                if n == "..":
                    p = g._getparent()
                    if p is not None:
                        g = p
                    else:
                        raise KeyError("No parent exists")
                elif n == "" or n == ".":
                    pass
                else:
                    try:
                        g = _ldict.__getitem__(g, n)
                    except: # attempt to use sanitised version
                        n = "_" + n
                        g = _ldict.__getitem__(g, n)
            return g
        elif key == '..':
            p = self._getparent()
            if p is not None:
                return p
            else:
                raise KeyError("No parent exists")
        elif key == '.':
            return self

        return _ldict.__getitem__(self, key)

    def init_group(self, nodes):
        p = self._getparent()
        a = self.attrs
        self._replacedata(nodes)
        self._setparent(p)
        self.attrs = a

    def __str__(self):
        s = ""
        for a in self.attrs:
            s += "   @%s = %s\n" % (a, self.attrs[a]) 

        for k in self:
            if not k in ["nxname", "attrs"] and not '__parent' in k:
                s += "    %s" % k
                if isinstance(_ldict.__getitem__(self, k), HDF5group):
                    s += "/\n"
                else:
                    s += "\n"
        if hasattr(self, "nxname"):
            return self.nxname + "\n" + s
        else:
            return s

    def create_group(self):
        pass

    def create_dataset(self):
        pass

class HDF5tree(HDF5group):
    def __init__(self, filename, attrs={}, native=None, warn=True):
        HDF5group.__init__(self, attrs, warn=warn)
        super(HDF5tree, self).__setattr__('__filename', filename)
        self.__native = native

    def _getnative(self):
        return self.__native

    def getnodes(self, name, group=True, data=True):
        '''Get all nodes with given name
        
        Arguments:
        name  -- name of node(s)
        group -- if True, include group nodes
        data  -- if True, include datasets

        Returns a list of nodes
        '''
        nodes = []
        self._getnodes(name, nodes, self, group, data)
        return nodes

    def _getnodes(self, key, nodes, group, addgroup, adddata):
        for k in group:
            if not k in ["nxname", "attrs"] and not '__parent' in k:
                n = group[k]
                if key == k and n not in nodes:
                    if addgroup and isinstance(n, HDF5group):
                        nodes.append(n)
                    elif adddata and isinstance(n, HDF5dataset):
                        nodes.append(n)

                if isinstance(n, HDF5group):
                    self._getnodes(key, nodes, n, addgroup, adddata)
