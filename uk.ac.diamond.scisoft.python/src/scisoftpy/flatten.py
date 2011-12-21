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
