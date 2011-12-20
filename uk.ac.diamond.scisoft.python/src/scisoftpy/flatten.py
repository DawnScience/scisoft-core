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
Flatten Package, normally for internal use by RPC only.
For documentation, refer to Java IFlattener and IRootFlattener
'''

import os
if os.name == 'java':
    import jython.jyflatten as _flatten #@UnusedImport
else:
    import python.pyflatten as _flatten #@Reimport

flatten=_flatten.flatten
unflatten=_flatten.unflatten
canflatten=_flatten.canflatten
canunflatten=_flatten.canunflatten
settemplocation=_flatten.settemplocation
addhelper=_flatten.addhelper
