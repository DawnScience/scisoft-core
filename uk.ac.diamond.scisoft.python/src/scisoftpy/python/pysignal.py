###
# Copyright 2013 Diamond Light Source Ltd.
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

import numpy as _np #@UnresolvedImport @UnusedImport

def correlate(a, v=None, mode='valid', old_behavior=False, axes=None):
    if v is None:
        return _np.correlate(a, a, mode=mode, old_behavior=old_behavior)
    return _np.correlate(a, v, mode=mode, old_behavior=old_behavior)

def convolve(a, v, mode='full', axes=None):
    return _np.convolve(a, v, mode=mode)

def phasecorrelate(f, g, axes=None, includeinv=False):
    '''Perform a phase cross correlation along given axes (can include inverse of cross-power spectrum)'''
    raise NotImplementedError, "Not implemented yet"
