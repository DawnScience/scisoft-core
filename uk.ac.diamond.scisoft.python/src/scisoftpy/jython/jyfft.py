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


'''FFT package
'''

import org.eclipse.dawnsci.analysis.dataset.FFT as _fft
from jycore import _wrap

@_wrap
def fft(a, n=None, axis=-1):
    '''Perform 1D FFT along given axis'''
    axis = a.checkAxis(axis)
    if n is None:
        n = a.shape[axis]
    return _fft.fft(a, n, axis)

@_wrap
def ifft(a, n=None, axis=-1):
    '''Perform inverse 1D FFT along given axis'''
    axis = a.checkAxis(axis)
    if n is None:
        n = a.shape[axis]
    return _fft.ifft(a, n, axis)

@_wrap
def fft2(a, s=None, axes=(-2,-1)):
    '''Perform 2D FFT of shape s along given axes'''
    return _fft.fft2(a, s, axes)

@_wrap
def ifft2(a, s=None, axes=(-2,-1)):
    '''Perform inverse 2D FFT of shape s along given axes'''
    return _fft.ifft2(a, s, axes)

@_wrap
def fftn(a, s=None, axes=None):
    '''Perform nD FFT of shape s along given axes'''
    return _fft.fftn(a, s, axes)

@_wrap
def ifftn(a, s=None, axes=None):
    '''Perform inverse nD FFT of shape s along given axes'''
    return _fft.ifftn(a, s, axes)

@_wrap
def fftshift(a, axes=None):
    '''Swap half-spaces
    
    End up with zero frequency component at centre position

    axes -- list of axes to swap and if None then all axes swapped

    '''
    return _fft.fftshift(a, axes)

@_wrap
def ifftshift(a, axes=None):
    '''Swaps back half-spaces
    
    End up with zero frequency component is at origin

    axes -- list of axes to swap and if None then all axes swapped 

    '''
    return _fft.ifftshift(a, axes)

@_wrap
def fftfreq(n, d=1.0):
    '''Sample frequencies for DFT
    n -- number of samples
    d -- sample spacing
    '''
    return _fft.sampleFrequencies(n, d)
