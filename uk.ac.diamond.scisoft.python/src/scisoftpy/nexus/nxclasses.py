
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
    """NeXus Error"""
    pass

class NXgroup(_group):
    def __init__(self, attrs={}, parent=None):
        _group.__init__(self, attrs, parent)
        self.nxname = self.__class__.__name__

    def init_group(self, nodes):
        n = self.nxname
        _group.init_group(self, nodes)
        self.nxname = n

class NXroot(_tree):
    """
    NXroot node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    def __init__(self, filename, attrs={}):
        _tree.__init__(self, filename, attrs)
        self.nxname = self.__class__.__name__

    def init_group(self, nodes):
        n = self.nxname
        _tree.init_group(self, nodes)
        self.nxname = n

class NXentry(NXgroup):
    """
    NXentry node. This is a subclass of the NXgroup class.

    Each NXdata and NXmonitor object of the same name will be added
    together, raising an NeXusError if any of the groups do not exist 
    in both NXentry groups or if any of the NXdata additions fail. 
    The resulting NXentry group contains a copy of all the other metadata 
    contained in the first group. Note that other extensible data, such 
    as the run duration, are not currently added together. 

    See the NXgroup documentation for more details.
    """
    def __add__(self, other):
        """
        Add two NXentry objects       
        """
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
        """
        Subtract two NXentry objects
        """
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

class NXdata(NXgroup):
    """
    NXdata node. This is a subclass of the NXgroup class.
    
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

    See the NXgroup documentation for more details.
    """
#    def __init__(self, signal=None, axes=(), *items, **opts):
#        NXgroup.__init__(self, *items, **opts)
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
    """
    NXmonitor node. This is a subclass of the NXdata class.
    
    See the NXdata and NXgroup documentation for more details.
    """
    def __init__(self, signal=None, axes=(), *items, **opts):
        NXdata.__init__(self, signal=signal, axes=axes, *items, **opts)
        if "nxname" not in opts.keys():
            self.nxname = "monitor"

class NXsample(NXgroup):
    """
    NXsample node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXinstrument(NXgroup):
    """
    NXinstrument node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXaperture(NXgroup):
    """
    NXaperture node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXattenuator(NXgroup):
    """
    NXattenuator node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXbeam_stop(NXgroup):
    """
    NXbeam_stop node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXbending_magnet(NXgroup):
    """
    NXbending_magnet node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXcollimator(NXgroup):
    """
    NXcollimator node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXcrystal(NXgroup):
    """
    NXcrystal node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXdetector(NXgroup):
    """
    NXdetector node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXdisk_chopper(NXgroup):
    """
    NXdisk_chopper node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXfermi_chopper(NXgroup):
    """
    NXfermi_chopper node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXfilter(NXgroup):
    """
    NXfilter node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXflipper(NXgroup):
    """
    NXflipper node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXguide(NXgroup):
    """
    NXguide node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXinsertion_device(NXgroup):
    """
    NXinsertion_device node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXmirror(NXgroup):
    """
    NXmirror node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXmoderator(NXgroup):
    """
    NXmoderator node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXmonochromator(NXgroup):
    """
    NXmonochromator node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXpolarizer(NXgroup):
    """
    NXpolarizer node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXpositioner(NXgroup):
    """
    NXpositioner node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXsource(NXgroup):
    """
    NXsource node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXvelocity_selector(NXgroup):
    """
    NXvelocity_selector node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXevent_data(NXgroup):
    """
    NXevent_data node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXuser(NXgroup):
    """
    NXuser node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXparameter(NXgroup):
    """
    NXparameter node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXprocess(NXgroup):
    """
    NXprocess node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXcharacterization(NXgroup):
    """
    NXcharacterization node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXlog(NXgroup):
    """
    NXlog node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass


class NXnote(NXgroup):
    """
    NXnote node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXbeam(NXgroup):
    """
    NXbeam node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXgeometry(NXgroup):
    """
    NXgeometry node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXtranslation(NXgroup):
    """
    NXtranslation node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXshape(NXgroup):
    """
    NXshape node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXorientation(NXgroup):
    """
    NXorientation node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXenvironment(NXgroup):
    """
    NXenvironment node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

class NXsensor(NXgroup):
    """
    NXsensor node. This is a subclass of the NXgroup class.
    
    See the NXgroup documentation for more details.
    """
    pass

#class Unknown(NXnode):
#    """
#    Unknown group type; class does not start with NX or SDS.
#    """
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
