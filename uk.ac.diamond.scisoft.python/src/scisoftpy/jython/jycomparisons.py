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
Comparisons package
'''

import org.eclipse.january.dataset.Comparisons as _cmps
import org.eclipse.january.dataset.DatasetUtils as _dsutils

from .jycore import _wrap, _argsToArrayType, _keepdims
from .jycore import asanyarray as _asany

@_keepdims
@_wrap('a')
def all(a, axis=None, keepdims=False): #@ReservedAssignment
    '''Return true if all items are true'''
    if axis:
        return _cmps.allTrue(a, axis)
    else:
        return _cmps.allTrue(a)

@_keepdims
@_wrap('a')
def any(a, axis=None, keepdims=False): #@ReservedAssignment
    '''Return true if any items are true'''
    if axis:
        return _cmps.anyTrue(a, axis)
    else:
        return _cmps.anyTrue(a)

@_wrap('a', 'b')
def greater(a, b):
    '''Return true if a > b, itemwise'''
    return _cmps.greaterThan(a, b)

@_wrap('a', 'b')
def greater_equal(a, b):
    '''Return true if a >= b, itemwise'''
    return _cmps.greaterThanOrEqualTo(a, b)

@_wrap('a', 'b')
def less(a, b):
    '''Return true if a < b, itemwise'''
    return _cmps.lessThan(a, b)

@_wrap('a', 'b')
def less_equal(a, b):
    '''Return true if a <= b, itemwise'''
    return _cmps.lessThanOrEqualTo(a, b)

@_wrap('a', 'b')
def equal(a, b):
    '''Return true if a == b, itemwise'''
    if a is None or b is None:
        return False
    return _cmps.equalTo(a, b)

@_wrap('a', 'b')
def not_equal(a, b):
    '''Return true if a != b, itemwise'''
    return _cmps.logicalNot(_cmps.equalTo(a, b))

@_wrap('a')
def logical_not(a):
    '''Return true if a == 0, itemwise'''
    return _cmps.logicalNot(a)

@_wrap('a')
def logical_and(a, b, out=None):
    '''Return true if a != 0 && b != 0, itemwise'''
    return _cmps.logicalAnd(a, b, out)

@_wrap('a')
def logical_or(a, b, out=None):
    '''Return true if a != 0 || b != 0, itemwise'''
    return _cmps.logicalOr(a, b, out)

@_wrap('a')
def logical_xor(a, b, out=None):
    '''Return true if a != 0 ^ b != 0, itemwise'''
    return _cmps.logicalXor(a, b, out)


@_wrap('a', 'b')
def allclose(a, b, rtol=1e-05, atol=1e-08, axis=None):
    '''Return true if all items are equal within given tolerances
    
    Parameters:
    rtol - relative tolerance
    atol - absolute tolerance
    '''
    if axis:
        return _cmps.allTrue(_cmps.almostEqualTo(a, b, rtol, atol), axis)
    else:
        return _cmps.allTrue(_cmps.almostEqualTo(a, b, rtol, atol))

@_wrap('a')
def nonzero(a):
    '''Return the indices for items that are non-zero'''
    return _cmps.nonZero(a)

@_wrap('condition', 'x', 'y')
def where(condition, x=None, y=None):
    '''Return items from x or y depending on condition'''
    if x and y:
        return _dsutils.select(condition, x, y)
    elif not x and not y:
        return _cmps.nonZero(condition)
    else:
        raise ValueError("Both x and y must be specified")

@_wrap('a')
def isnan(a):
    '''Return true if a is a NaN, itemwise'''
    return _cmps.isNaN(a)

@_wrap('a')
def isinf(a):
    '''Return true if a is infinite, itemwise'''
    return _cmps.isInfinite(a)

@_wrap('a')
def isposinf(a):
    '''Return true if a is positive infinite, itemwise'''
    return _cmps.isPositiveInfinite(a)

@_wrap('a')
def isneginf(a):
    '''Return true if a is negative infinite, itemwise'''
    return _cmps.isNegativeInfinite(a)

@_wrap('a')
def isfinite(a):
    '''Return true if a is not infinite and not a NaN, itemwise'''
    return _cmps.isFinite(a)

@_argsToArrayType('x')
def iscomplex(x):
    return not_equal(_asany(x).imag, 0)

@_argsToArrayType('x')
def isreal(x):
    return equal(_asany(x).imag, 0)

