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


# These classes were part of NeXpy by Paul Kienzle and
# declared public domain 

#import os
#if os.name == 'java':
#    from scisoftpy.jython.jynxs import NeXusError
#else:
#    from scisoftpy.python.pynxs import NeXusError

from .hdf5 import HDF5tree as _tree
from .hdf5 import HDF5group as _group

class NeXusError(Exception):
    '''NeXus Error'''
    pass

class NXobject(_group):
    '''
    NXobject is the base object of NeXus and is a group
    '''
    def __init__(self, attrs={}, parent=None):
        _group.__init__(self, attrs, parent)
        self.nxname = self.__class__.__name__

    def init_group(self, nodes):
        n = self.nxname
        _group.init_group(self, nodes)
        self.nxname = n

class NXroot(_tree):
    '''
    NXroot node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    def __init__(self, filename, attrs={}):
        _tree.__init__(self, filename, attrs)
        self.nxname = self.__class__.__name__

    def init_group(self, nodes):
        n = self.nxname
        _tree.init_group(self, nodes)
        self.nxname = n

class NXentry(NXobject):
    '''
    NXentry node. This is a subclass of the NXobject class.

    Each NXdata and NXmonitor object of the same name will be added
    together, raising an NeXusError if any of the groups do not exist 
    in both NXentry groups or if any of the NXdata additions fail. 
    The resulting NXentry group contains a copy of all the other metadata 
    contained in the first group. Note that other extensible data, such 
    as the run duration, are not currently added together. 

    See the NXobject documentation for more details.
    '''
    def __add__(self, other):
        '''
        Add two NXentry objects       
        '''
        result = NXentry(entries=self.nxentries, attrs=self.nxattrs)
        try:
            names = [group.nxname for group in self.nxcomponent("NXdata")]
            for name in names:
                if isinstance(other.__dict__[name], NXdata):
                    result.__dict__[name] = self.__dict__[name] + other.__dict__[name]
                else:
                    raise KeyError
            names = [group.nxname for group in self.nxcomponent("NXmonitor")]
            for name in names:
                if isinstance(other.__dict__[name], NXmonitor):
                    result.__dict__[name] = self.__dict__[name] + other.__dict__[name]
                else:
                    raise KeyError
            return result
        except KeyError:
            raise NeXusError, "Inconsistency between two NXentry groups"

    def __sub__(self, other):
        '''
        Subtract two NXentry objects
        '''
        result = NXentry(entries=self.nxentries, attrs=self.nxattrs)
        try:
            names = [group.nxname for group in self.nxcomponent("NXdata")]
            for name in names:
                if isinstance(other.__dict__[name], NXdata):
                    result.__dict__[name] = self.__dict__[name] - other.__dict__[name]
                else:
                    raise KeyError
            names = [group.nxname for group in self.nxcomponent("NXmonitor")]
            for name in names:
                if isinstance(other.__dict__[name], NXmonitor):
                    result.__dict__[name] = self.__dict__[name] - other.__dict__[name]
                else:
                    raise KeyError
            return result
        except KeyError:
            raise NeXusError, "Inconsistency between two NXentry groups"

class NXdata(NXobject):
    '''
    NXdata node. This is a subclass of the NXobject class.
    
    The constructor assumes that the first argument contains the signal and
    the second contains either the axis, for one-dimensional data, or a list
    of axes, for multidimenional data. These arguments can either be SDS 
    objects or Numpy arrays, which are converted to SDS objects with default 
    names.

    Attributes
    ----------
    nxsignal : The SDS containing the attribute 'signal' with value 1
    nxaxes   : A list of SDSs containing the signal axes
    nxerrors : The SDS containing the errors


    Examples
    --------
    >>> x = np.linspace(0, 2*np.pi, 101)
    >>> line = NXdata(sin(x), x)
    data:NXdata
      signal = float64(101)
        @axes = x
        @signal = 1
      x = float64(101)
    >>> X, Y = np.meshgrid(x, x)
    >>> z = SDS(sin(X) * sin(Y), name='intensity')
    >>> entry = NXentry()
    >>> entry.grid = NXdata(z, (x, x))
    >>> grid.nxtree()
    entry:NXentry
      grid:NXdata
        axis1 = float64(101)
        axis2 = float64(101)
        intensity = float64(101x101)
          @axes = axis1:axis2
          @signal = 1    

    See the NXobject documentation for more details.
    '''
    pass
#    def __init__(self, signal=None, axes=(), *items, **opts):
#        NXobject.__init__(self, *items, **opts)
#        if signal is not None:
#            signalname = self._setSDS(signal, "signal")
#            self.__dict__[signalname].signal = 1
#            if axes is not None:
#                if isinstance(axes,tuple) or isinstance(axes,list):
#                    axisname = {}
#                    i = 0
#                    for axis in axes:
#                        i = i + 1
#                        axisname[i] = self._setSDS(axis, "axis%s" % i)
#                    self.__dict__[signalname].axes = ":".join(axisname.values())
#                else:
#                    axisname = self._setSDS(axes, 'x')
#                    self.__dict__[signalname].axes = axisname

class NXmonitor(NXdata):
    '''
    NXmonitor node. This is a subclass of the NXdata class.
    
    See the NXdata and NXobject documentation for more details.
    '''
    pass
#    def __init__(self, signal=None, axes=(), *items, **opts):
#        NXdata.__init__(self, signal=signal, axes=axes, *items, **opts)
#        if "nxname" not in opts.keys():
#            self.nxname = "monitor"

class NXaperture(NXobject):
    '''
    NXaperture node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXattenuator(NXobject):
    '''
    NXattenuator node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXbeam(NXobject):
    '''
    NXbeam node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXbeam_stop(NXobject):
    '''
    NXbeam_stop node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXbending_magnet(NXobject):
    '''
    NXbending_magnet node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXcapillary(NXobject):
    '''
    NXcapillary node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXcharacterization(NXobject):
    '''
    NXcharacterization node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXcite(NXobject):
    '''
    NXcite node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXcollection(NXobject):
    '''
    NXcollection node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXcollimator(NXobject):
    '''
    NXcollimator node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXcrystal(NXobject):
    '''
    NXcrystal node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXdetector(NXobject):
    '''
    NXdetector node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXdetector_group(NXobject):
    '''
    NXdetector_group node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXdetector_module(NXobject):
    '''
    NXdetector_module node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXdisk_chopper(NXobject):
    '''
    NXdisk_chopper node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXenvironment(NXobject):
    '''
    NXenvironment node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXevent_data(NXobject):
    '''
    NXevent_data node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXfermi_chopper(NXobject):
    '''
    NXfermi_chopper node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXfilter(NXobject):
    '''
    NXfilter node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXflipper(NXobject):
    '''
    NXflipper node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXfresnel_zone_plate(NXobject):
    '''
    NXfresnel_zone_plate node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXgeometry(NXobject):
    '''
    NXgeometry node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXgrating(NXobject):
    '''
    NXgrating node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXguide(NXobject):
    '''
    NXguide node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXinsertion_device(NXobject):
    '''
    NXinsertion_device node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXinstrument(NXobject):
    '''
    NXinstrument node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXlog(NXobject):
    '''
    NXlog node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXmirror(NXobject):
    '''
    NXmirror node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXmoderator(NXobject):
    '''
    NXmoderator node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXmonochromator(NXobject):
    '''
    NXmonochromator node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXnote(NXobject):
    '''
    NXnote node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXorientation(NXobject):
    '''
    NXorientation node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXparameters(NXobject):
    '''
    NXparameters node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXpinhole(NXobject):
    '''
    NXpinhole node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXpolarizer(NXobject):
    '''
    NXpolarizer node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXpositioner(NXobject):
    '''
    NXpositioner node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXprocess(NXobject):
    '''
    NXprocess node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXsample(NXobject):
    '''
    NXsample node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXsample_component(NXobject):
    '''
    NXsample_component node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXsensor(NXobject):
    '''
    NXsensor node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXshape(NXobject):
    '''
    NXshape node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXslit(NXobject):
    '''
    NXslit node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXsource(NXobject):
    '''
    NXsource node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXsubentry(NXobject):
    '''
    NXsubentry node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXtransformations(NXobject):
    '''
    NXtransformations node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXtranslation(NXobject):
    '''
    NXtranslation node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXuser(NXobject):
    '''
    NXuser node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXvelocity_selector(NXobject):
    '''
    NXvelocity_selector node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

class NXxraylens(NXobject):
    '''
    NXxraylens node. This is a subclass of the NXobject class.
    
    See the NXobject documentation for more details.
    '''
    pass

#class Unknown(NXnode):
#    '''
#    Unknown group type; class does not start with NX or SDS.
#    '''
#    def __init__(self, nxname="unknown", nxclass="unknown"):
#        self.nxname = nxname
#        self.nxclass = nxclass
#
#    def __repr__(self):
#        return "Unknown('%s','%s')"%(self.nxname,self.nxclass)

def _get_all_nx_classes():
    d = {}
    import sys
    import inspect
    for n, obj in inspect.getmembers(sys.modules[__name__],
                                    lambda m : inspect.isclass(m) and m.__module__ == __name__):
        if n.startswith('NX'):
            d[n] = obj

    return d

NX_CLASSES = _get_all_nx_classes()
