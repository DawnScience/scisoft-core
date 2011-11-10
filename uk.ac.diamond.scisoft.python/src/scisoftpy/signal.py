'''
Signal processing package
'''

import uk.ac.diamond.scisoft.analysis.dataset.Signal as _signal

from jython.jymaths import ndarraywrapped as _npwrapped

@_npwrapped
def correlate(f, g=None, axes=None):
    '''Perform a cross (or auto if g is None) correlation along given axes'''
    if g is None:
        return _signal.correlate(f, axes)
    return _signal.correlate(f, g, axes)

@_npwrapped
def phasecorrelate(f, g, axes=None, includeinv=False):
    '''Perform a phase cross correlation along given axes (can include inverse of cross-power spectrum)'''
    ans = _signal.phaseCorrelate(f, g, axes, includeinv)
    if includeinv:
        return ans[0], ans[1]
    else:
        return ans[0]

@_npwrapped
def convolve(f, g, axes=None):
    '''Perform a convolution along given axes'''
    return _signal.convolve(f, g, axes)


