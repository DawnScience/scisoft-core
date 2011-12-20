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

try:
    from .pyh5py import NXLoader, SDS #@UnusedImport
except Exception, e:
    import sys
    print >> sys.stderr, "h5py could not be imported", e
    print >> sys.stderr, "NAPI not supported yet"
#    from .pynapi import NXLoader, SDS #@Reimport @UnusedImport

