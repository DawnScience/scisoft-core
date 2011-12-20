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
Analysis RPC Package
'''

import os
if os.name == 'java':
    import jython.jyrpc as _rpc #@UnusedImport
    import jython.jywrapper as _wrapper #@UnusedImport
    import jython.jyflatten as _flatten #@UnusedImport
else:
    import python.pyrpc as _rpc #@Reimport
    import python.pywrapper as _wrapper #@Reimport
    import python.pyflatten as _flatten #@Reimport

rpcserver=_rpc.rpcserver
rpcclient=_rpc.rpcclient
typednone=_wrapper.typednone
abstractdatasetdescriptor=_wrapper.abstractdatasetdescriptor
binarywrapper=_wrapper.binaryWrapper
settemplocation=_flatten.settemplocation
