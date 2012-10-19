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
binarywrapper=_wrapper.binarywrapper
settemplocation=_flatten.settemplocation
