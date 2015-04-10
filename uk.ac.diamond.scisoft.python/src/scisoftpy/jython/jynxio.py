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

from jyhdf5io import HDF5Loader as _h5loader
from org.eclipse.dawnsci.analysis.api.tree import Tree as _jtree
from org.eclipse.dawnsci.analysis.api.tree import TreeFile as _jtreefile

#from jyhdf5io import SDS

import scisoftpy.nexus as _nx

class NXLoader(_h5loader):
    def _mkgroup(self, name, link, attrs, parent):
        cls = link.getDestination().getAttribute('NX_class')
        if cls is not None:
            cls = cls.getFirstElement()
            if cls in _nx.NX_CLASSES:
                g = _nx.NX_CLASSES[cls](attrs, parent)
            else:
                print "Unknown Nexus class: %s" % cls
                g = super(NXLoader, self)._mkgroup(name, link, attrs, parent)
        elif name == '/' or isinstance(parent, _jtree):
            src = parent.getFilename() if isinstance(parent, _jtreefile) else parent.getSourceURI()
            g = _nx.NXroot(src, attrs)
        else:
            g = _nx.NXgroup(attrs, parent)

        return g

