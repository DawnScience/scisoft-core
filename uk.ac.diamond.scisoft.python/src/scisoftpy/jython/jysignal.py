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
Signal processing package
'''

import uk.ac.diamond.scisoft.analysis.dataset.Signal as _signal

from jymaths import ndarraywrapped as _npwrapped
from jycore import asfarray as _asf

@_npwrapped
def correlate(f, g=None, mode='valid', old_behavior=False, axes=None):
    '''Perform a cross (or auto if g is None) correlation along given axes'''
    if old_behavior:
        raise NotImplementedError, 'Not implemented'

    if g is None:
        return _signal.correlate(_asf(f), axes)
    return _signal.correlate(_asf(f), _asf(g), axes)

@_npwrapped
def phasecorrelate(f, g, axes=None, includeinv=False):
    '''Perform a phase cross correlation along given axes (can include inverse of cross-power spectrum)'''
    ans = _signal.phaseCorrelate(_asf(f), _asf(g), axes, includeinv)
    if includeinv:
        return ans[0], ans[1]
    else:
        return ans[0]

@_npwrapped
def convolve(f, g, mode='full', axes=None):
    '''Perform a convolution along given axes'''
    if mode == 'same':
        return _signal.convolveToSameShape(_asf(f), _asf(g), axes)
    elif mode == 'valid':
        return _signal.convolveForOverlap(_asf(f), _asf(g), axes)
    elif mode == 'full':
        return _signal.convolve(_asf(f), _asf(g), axes)
    raise ValueError, 'mode keyword has unrecognised value'
