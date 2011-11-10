'''FFT package
'''

import uk.ac.diamond.scisoft.analysis.dataset.FFT as _fft
from jymaths import ndarraywrapped as _npwrapped

@_npwrapped
def fft(a, n=None, axis=-1):
    '''Perform 1D FFT along given axis'''
    axis = a.checkAxis(axis)
    if n == None:
        n = a.shape[axis]
    return _fft.fft(a, n, axis)

@_npwrapped
def ifft(a, n=None, axis=-1):
    '''Perform inverse 1D FFT along given axis'''
    axis = a.checkAxis(axis)
    if n == None:
        n = a.shape[axis]
    return _fft.ifft(a, n, axis)

@_npwrapped
def fft2(a, s=None, axes=(-2,-1)):
    '''Perform 2D FFT of shape s along given axes'''
    return _fft.fft2(a, s, axes)

@_npwrapped
def ifft2(a, s=None, axes=(-2,-1)):
    '''Perform inverse 2D FFT of shape s along given axes'''
    return _fft.ifft2(a, s, axes)

@_npwrapped
def fftn(a, s=None, axes=None):
    '''Perform nD FFT of shape s along given axes'''
    return _fft.fftn(a, s, axes)

@_npwrapped
def ifftn(a, s=None, axes=None):
    '''Perform inverse nD FFT of shape s along given axes'''
    return _fft.ifftn(a, s, axes)

@_npwrapped
def fftshift(a, axes=None):
    '''Swap half-spaces
    
    End up with zero frequency component at centre position

    axes -- list of axes to swap and if None then all axes swapped

    '''
    return _fft.fftshift(a, axes)

@_npwrapped
def ifftshift(a, axes=None):
    '''Swaps back half-spaces
    
    End up with zero frequency component is at origin

    axes -- list of axes to swap and if None then all axes swapped 

    '''
    return _fft.ifftshift(a, axes)
