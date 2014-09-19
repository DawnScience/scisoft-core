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

import uk.ac.diamond.scisoft.analysis.dataset.CompoundDataset as _compoundds
import uk.ac.diamond.scisoft.analysis.dataset.Maths as _maths
import uk.ac.diamond.scisoft.analysis.dataset.Stats as _stats
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils as _dsutils

from jycore import _wrap
from jycore import _wrapin
from jycore import toList as _toList

@_wrap
def phase(a, keepzeros=False):
    '''Calculate phase of input by dividing by amplitude
    
    keepzeros -- if True, pass zeros through, else return complex NaNs
    '''
    return _maths.phaseAsComplexNumber(a, keepzeros)

@_wrap
def dividez(a, b):
    '''Divide one array-like object by another with items that are zero divisors set to zero'''
    return _maths.dividez(a, b)

@_wrap
def cbrt(a):
    '''Cube root of input'''
    return _maths.cbrt(a)

@_wrap
def skewness(a, axis=None):
    '''Skewness of input'''
    if axis is None:
        return _stats.skewness(a)
    else:
        return _stats.skewness(a, axis)

@_wrap
def kurtosis(a, axis=None):
    '''Kurtosis of input'''
    if axis is None:
        return _stats.kurtosis(a)
    else:
        return _stats.kurtosis(a, axis)

@_wrap
def iqr(a, axis=None):
    '''Interquartile range of input'''
    if axis is None:
        return _stats.iqr(a)
    else:
        return _stats.iqr(a, axis)

@_wrap
def quantile(a, q, axis=None):
    '''Quantile (or inverse cumulative distribution) function based on input

    a -- data
    q -- probability value(s)
    axis -- can be None'''
    q = _toList(q)
    if axis is None:
        if len(q) == 1:
            return _stats.quantile(a, q)[0]
        return _stats.quantile(a, q)
    else:
        if len(q) == 1:
            return _stats.quantile(a, axis, q)[0]
        return _stats.quantile(a, axis, q)

@_wrapin
def residual(a, b, weight=None):
    '''Residual (sum of squared difference) of two inputs with optional weighting'''
    if weight is None:
        return _stats.residual(a, b)
    return _stats.weightedResidual(a, b, weight)

@_wrap
def normalise(a, allelements=True):
    '''Normalise array so all elements lie between 0 and 1
    Keyword argument:
    allelements -- if True, then normalise for all elements rather than per-element
    '''
    if isinstance(a, _compoundds):
        return _dsutils.norm(a, allelements)
    return _dsutils.norm(a)

@_wrapin
def crossings(y, value, x=None):
    '''Finds the crossing points where a (poly-)line defined by a 1D y array has the given
    values and return the (linearly) interpolated index or x value if an x array is given 
    '''
    if x is None:
        return _dsutils.crossings(y, value)
    return _dsutils.crossings(x, y, value)

@_wrap
def centroid(weights, coords=None):
    '''Calculate the centroid of an array with its (half) indexes or
    coordinates (list of 1D arrays), if given, and returns it as a list
    '''
    if coords is None:
        return _dsutils.centroid(weights)
    from jycore import toList
    return _dsutils.centroid(weights, toList(coords))
