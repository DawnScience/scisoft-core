
from jyhdf5io import HDF5Loader as _h5loader

#from jyhdf5io import SDS

import scisoftpy.nexus as _nx

class NXLoader(_h5loader):
    def _mkgroup(self, name, link, attrs, parent):
        cls = link.getDestination().getAttribute('NX_class')
        if cls is not None:
            cls = cls.getFirstElement()
            if cls in _nx.NX_CLASSES:
                g = _nx.NX_CLASSES[cls](attrs, parent)
        elif name == '/' or parent is None:
            g = _nx.NXroot(link.getSource().getFilename(), attrs)
        else:
            g = _nx.NXgroup(attrs, parent)

        return g

