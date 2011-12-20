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

