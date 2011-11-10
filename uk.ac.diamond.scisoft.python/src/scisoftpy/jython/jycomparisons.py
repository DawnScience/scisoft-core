'''
Comparisons package
'''

import uk.ac.diamond.scisoft.analysis.dataset.Comparisons as _cmps

from jycore import asDatasetList as _asDList
from jymaths import ndarraywrapped as _npwrapped

@_npwrapped
def all(a, axis=None):
    '''Return true if all items are true'''
    if axis:
        return _cmps.allTrue(a, axis)
    else:
        return _cmps.allTrue(a)

@_npwrapped
def any(a, axis=None):
    '''Return true if any items are true'''
    if axis:
        return _cmps.anyTrue(a, axis)
    else:
        return _cmps.anyTrue(a)

@_npwrapped
def greater(a, b):
    '''Return true if a > b, itemwise'''
    return _cmps.greaterThan(a, b)

@_npwrapped
def greater_equal(a, b):
    '''Return true if a >= b, itemwise'''
    return _cmps.greaterThanOrEqualTo(a, b)

@_npwrapped
def less(a, b):
    '''Return true if a < b, itemwise'''
    return _cmps.lessThan(a, b)

@_npwrapped
def less_equal(a, b):
    '''Return true if a <= b, itemwise'''
    return _cmps.lessThanOrEqualTo(a, b)

@_npwrapped
def equal(a, b):
    '''Return true if a == b, itemwise'''
    if a is None or b is None:
        return False
    return _cmps.equalTo(a, b)

@_npwrapped
def not_equal(a, b):
    '''Return true if a != b, itemwise'''
    return _cmps.logicalNot(_cmps.equalTo(a, b))

@_npwrapped
def logical_not(a):
    '''Return true if a == 0, itemwise'''
    return _cmps.logicalNot(a)

@_npwrapped
def logical_and(a, b):
    '''Return true if a != 0 && b != 0, itemwise'''
    return _cmps.logicalAnd(a, b)

@_npwrapped
def logical_or(a, b):
    '''Return true if a != 0 || b != 0, itemwise'''
    return _cmps.logicalOr(a, b)

@_npwrapped
def logical_xor(a, b):
    '''Return true if a != 0 ^ b != 0, itemwise'''
    return _cmps.logicalXor(a, b)


@_npwrapped
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

@_npwrapped
def nonzero(a):
    '''Return the indices for items that are non-zero'''
    return _cmps.nonZero(a)

@_npwrapped
def select(condlist, choicelist, default=0):
    '''Return dataset with items drawn from choices according to conditions'''
    return _cmps.select(_asDList(condlist), _asDList(choicelist), default)

@_npwrapped
def where(condition, x=None, y=None):
    '''Return items from x or y depending on condition'''
    if x and y:
        return select(condition, x, y)
    elif not x and not y:
        return _cmps.nonZero(condition)
    else:
        raise ValueError, 'Both x and y must be specified'

