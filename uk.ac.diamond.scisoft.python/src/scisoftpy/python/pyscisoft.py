###
# Copyright 2012 Diamond Light Source Ltd.
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
Scisoft-only package
'''

import numpy as _np #@UnresolvedImport

def dividez(a, b):
    '''Divide one array-like object by another with items that are zero divisors set to zero'''
    d = a/b
    d[b == 0] = 0
    return d

def cbrt(a):
    '''Cube root of input'''
    return _np.power(a, 1./3.)

def phase(a, keepzeros=False):
    '''Calculate phase of input by dividing by amplitude
    
    keepzeros -- if True, pass zeros through, else return complex NaNs
    '''
    if not _np.iscomplexobj(a):
        raise ValueError, "array must be complex"
    am = _np.absolute(a)
    if keepzeros:
        return _np.nan_to_number(a/am)
    else:
        return a/am

def normalise(a, allelements=True):
    '''Normalise array so all elements lie between 0 and 1
    Keyword argument:
    allelements -- if True, then normalise for all elements rather than per-element
    '''
    range_ = _np.ptp(a)
    bottom = a.min()
    n = a - bottom
    n /= range_
    return n

def skewness(a, axis=None):
    '''Skewness of input'''
    raise NotImplementedError


def kurtosis(a, axis=None):
    '''Kurtosis of input'''
    raise NotImplementedError

def quantile(a, q, axis=None):
    '''Quantile (or inverse cumulative distribution) function based on input

    a -- data
    q -- probability value(s)
    axis -- can be None'''
    from pycore import toList
    from math import floor
    q = toList(q)
    sa = _np.sort(a, axis=axis)
    if axis is None:
        size = sa.size
    else:
        size = sa.shape[axis]

    x = [ (size - 1)*p for p in q ]
    n = [ int(floor(f)) for f in x ]
    x = [ f - i for f, i in zip(x,n) ]
    if axis is None:
        return [ sa[i]*(1-f) + sa[i+1]*f for f, i in zip(x,n) ]
    else:
        slices = [ slice(d) for d in sa.shape ]
        result = []
        for f, i in zip(x, n):
            slices[axis] = i
            r = sa[slices]*(1-f)
            slices[axis] = i+1
            r += sa[slices]*f
            result.append(r)
        return result

def iqr(a, axis=None):
    '''Interquartile range of input'''
    result = quantile(a, [0.25, 0.75], axis=axis)
    return result[1] - result[0]

def residual(a, b, weight=None):
    '''Residual (sum of squared difference) of two inputs with optional weighting'''
    r = _np.square(a-b)
    if weight is None:
        return r
    return r * weight

def equaldataset(lhs, rhs):
    '''Compare two ndarrays for equality, or two representations of abstract datasets via abstract descriptors'''
    if isinstance(lhs, _np.ndarray) and isinstance(rhs, _np.ndarray):
        match = lhs == rhs
        if isinstance(match, _np.ndarray) and match.all():
            return True
    # TODO add check for dataset descriptors
    return False

def centroid(weights, coords=None):
    '''Calculate the centroid of an array with its (half) indexes or
    coordinates (list of 1D arrays), if given, and returns it as a list
    '''
    if coords is None:
        coords = [ _np.arange(d, dtype=_np.float) + 0.5 for d in weights.shape ]
    else:
        from pycore import toList
        coords = toList(coords)

    rank = weights.ndim
    if rank > len(coords):
        raise ValueError, "Number of coordinate arrays must match rank"

    total = weights.sum()
    cshape = [1,]*rank
    result = []
    for i in range(rank):
        cdata = coords[i]
        if cdata.ndim != 1:
            raise ValueError, "All coordinate arrays must be 1D"

        cshape[i] = cdata.shape[0]
        result.append(_np.multiply(weights, cdata.reshape(cshape)).sum()/total)
        cshape[i] = 1

    return result

def crossings(y, value, x=None):
    '''Finds the crossing points where a (poly-)line defined by a 1D y array has the given
    values and return the (linearly) interpolated index or x value if an x array is given 
    '''
    raise NotImplementedError
