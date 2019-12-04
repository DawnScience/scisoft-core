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
Maths package
'''

import org.eclipse.january.dataset.Dataset as _ds
import org.eclipse.january.dataset.Maths as _maths
import org.eclipse.january.dataset.Stats as _stats

from math import pi as _ppi
from math import e as _e
from java.lang.Double import POSITIVE_INFINITY as _jinf #@UnresolvedImport
from java.lang.Double import MAX_VALUE as _jmax #@UnresolvedImport
from java.lang.Double import NaN as _jnan #@UnresolvedImport

pi = _ppi
e = _e
inf = _jinf
nan = _jnan

floatmax = _jmax # maximum float value (use sys.float_info.max for 2.6+)

from .jycore import _wrap, _jint, _argsToArrayType, _keepdims
from .jycore import asarray as _asarray
from .jycore import float64 as _f64
from .jycore import _translatenativetype, _empty_boolean_array

# these functions can call (wrapped) instance methods
@_keepdims
@_wrap('a')
def prod(a, axis=None, dtype=None, keepdims=False):
    '''Product of input'''
    if dtype is None:
        if axis is None:
            return a.product(_empty_boolean_array)
        elif isinstance(axis, int):
            return a.product(_jint(axis))
        elif isinstance(axis, tuple):
            return a.product([_jint(x) for x in axis])
    dtval = _translatenativetype(dtype).value
    if axis is None:
        return _stats.typedProduct(a, dtval)
    return _stats.typedProduct(a, dtval, axis)

@_keepdims
@_wrap('a')
def sum(a, axis=None, dtype=None, keepdims=False): #@ReservedAssignment
    '''Sum of input'''
    if dtype is not None:
        dtval = _translatenativetype(dtype).value
        a = a.cast(dtval)

    if axis is None:
        return a.sum(_empty_boolean_array)
    elif isinstance(axis, int):
        return a.sum(_jint(axis))
    elif isinstance(axis, tuple):
        return a.sum([_jint(x) for x in axis])

@_keepdims
@_argsToArrayType('a')
def mean(a, axis=None, keepdims=False):
    '''Arithmetic mean of input'''
    return a.mean(axis)

@_keepdims
@_argsToArrayType('a')
def std(a, axis=None, ddof=0, keepdims=False):
    '''Standard deviation of input'''
    return a.std(axis, ddof)

@_keepdims
@_argsToArrayType('a')
def var(a, axis=None, ddof=0, keepdims=False):
    '''Variance of input'''
    return a.var(axis, ddof)

@_keepdims
@_argsToArrayType('a')
def ptp(a, axis=None, keepdims=False):
    '''Peak-to-peak of input'''
    return a.ptp(axis)

@_keepdims
@_argsToArrayType('a')
def amax(a, axis=None, keepdims=False):
    '''Maximum of input'''
    return a.max(axis)

@_keepdims
@_argsToArrayType('a')
def amin(a, axis=None, keepdims=False):
    '''Minimum of input'''
    return a.min(axis)

@_argsToArrayType('a')
def real(a):
    '''Real part of input'''
    return _asarray(a).real

@_argsToArrayType('a')
def imag(a):
    '''Imaginary part of input'''
    return _asarray(a).imag

@_wrap('a')
def abs(a, out=None): #@ReservedAssignment
    '''Absolute value of input'''
    return _maths.abs(a, out)

absolute = abs

fabs = abs # supports complex types too

@_wrap('a')
def angle(a):
    '''Angle of complex argument'''
    return _maths.angle(a)

@_wrap('a')
def conjugate(a, out=None):
    '''Complex conjugate of input'''
    return _maths.conjugate(a, out)

conj = conjugate

@_wrap('a', 'b')
def add(a, b, out=None):
    '''Add two array-like objects together'''
    return _maths.add(a, b, out)

@_wrap('a', 'b')
def subtract(a, b, out=None):
    '''Subtract one array-like object from another'''
    return _maths.subtract(a, b, out)

@_wrap('a', 'b')
def multiply(a, b, out=None):
    '''Multiply two array-like objects together'''
    return _maths.multiply(a, b, out)

@_wrap('a', 'b')
def divide(a, b, out=None):
    '''Divide one array-like object by another'''
    return _maths.divideTowardsFloor(a, b, out)

@_wrap('a', 'b')
def floor_divide(a, b, out=None):
    '''Calculate largest integers smaller or equal to division'''
    return _maths.floorDivide(a, b, out)

@_wrap('a', 'b')
def remainder(a, b, out=None):
    '''Return remainder of division of inputs like Python's modulo operator'''
    return _maths.floorRemainder(a, b, out)

@_wrap('a')
def modf(a, out1=None, out2=None):
    '''Return fractional and integral parts of inputs'''
    ia = _maths.truncate(a, out1).cast(_f64.value)
    fa = _maths.subtract(a, ia, out2)
    return (fa, ia)

@_wrap('a', 'b')
def fmod(a, b, out=None):
    '''Return remainder of division of inputs like C's or Java's modulo operator'''
    return _maths.remainder(a, b, out)

mod = remainder

@_wrap('a')
def reciprocal(a, out=None):
    '''Calculate reciprocal of input'''
    return _maths.reciprocal(a, out)

@_wrap('a', 'b')
def bitwise_and(a, b, out=None):
    '''Return bitwise AND of inputs'''
    return _maths.bitwiseAnd(a, b, out)

@_wrap('a', 'b')
def bitwise_or(a, b, out=None):
    '''Return bitwise inclusive OR of inputs'''
    return _maths.bitwiseOr(a, b, out)

@_wrap('a', 'b')
def bitwise_xor(a, b, out=None):
    '''Return bitwise exclusive OR of inputs'''
    return _maths.bitwiseXor(a, b, out)

@_wrap('a')
def invert(a, out=None):
    '''Return bitwise inversion (or NOT) of input'''
    return _maths.bitwiseInvert(a, out)

@_wrap('a', 'b')
def left_shift(a, b, out=None):
    '''Return bitwise left shift'''
    return _maths.leftShift(a, b, out)

@_wrap('a', 'b')
def right_shift(a, b, out=None):
    '''Return bitwise right shift'''
    return _maths.rightShift(a, b, out)

@_wrap('a')
def sin(a, out=None):
    '''Sine of input'''
    return _maths.sin(a, out)

@_wrap('a')
def cos(a, out=None):
    '''Cosine of input'''
    return _maths.cos(a, out)

@_wrap('a')
def tan(a, out=None):
    '''Tangent of input'''
    return _maths.tan(a, out)

@_wrap('a')
def arcsin(a, out=None):
    '''Inverse sine of input'''
    return _maths.arcsin(a, out)

@_wrap('a')
def arccos(a, out=None):
    '''Inverse cosine of input'''
    return _maths.arccos(a, out)

@_wrap('a')
def arctan(a, out=None):
    '''Inverse tangent of input'''
    return _maths.arctan(a, out)

@_wrap('a', 'b')
def arctan2(a, b, out=None):
    '''Inverse tangent of a/b with correct choice of quadrant'''
    return _maths.arctan2(a, b, out)

@_wrap('a', 'b')
def hypot(a, b, out=None):
    '''Hypotenuse of triangle of given sides'''
    return _maths.hypot(a, b, out)

@_wrap('a')
def sinh(a, out=None):
    '''Hyperbolic sine of input'''
    return _maths.sinh(a, out)

@_wrap('a')
def cosh(a, out=None):
    '''Hyperbolic cosine of input'''
    return _maths.cosh(a, out)

@_wrap('a')
def tanh(a, out=None):
    '''Hyperbolic tangent of input'''
    return _maths.tanh(a, out)

@_wrap('a')
def arcsinh(a, out=None):
    '''Inverse hyperbolic sine of input'''
    return _maths.arcsinh(a, out)

@_wrap('a')
def arccosh(a, out=None):
    '''Inverse hyperbolic cosine of input'''
    return _maths.arccosh(a, out)

@_wrap('a')
def arctanh(a, out=None):
    '''Inverse hyperbolic tangent of input'''
    return _maths.arctanh(a, out)

@_wrap('a')
def log(a, out=None):
    '''Natural logarithm of input'''
    return _maths.log(a, out)

@_wrap('a')
def log2(a, out=None):
    '''Logarithm of input to base 2'''
    return _maths.log2(a, out)

@_wrap('a')
def log10(a, out=None):
    '''Logarithm of input to base 10'''
    return _maths.log10(a, out)

@_wrap('x')
def log1p(x, out=None):
    '''Natural logarithm of (x+1)'''
    return _maths.log1p(x, out)

@_wrap('a')
def exp(a, out=None):
    '''Exponential of input'''
    return _maths.exp(a, out)

@_wrap('x')
def expm1(x, out=None):
    '''Exponential of (x-1)'''
    return _maths.expm1(x, out)

@_wrap('a')
def sqrt(a, out=None):
    '''Square root of input'''
    return _maths.sqrt(a, out)

@_wrap('a')
def square(a, out=None):
    '''Square of input'''
    return _maths.square(a, out)

@_wrap('a', 'p')
def power(a, p, out=None):
    '''Input raised to given power'''
    return _maths.power(a, p, out)

@_wrap('a')
def floor(a, out=None):
    '''Largest integer smaller or equal to input'''
    return _maths.floor(a, out)

@_wrap('a')
def ceil(a, out=None):
    '''Smallest integer greater or equal to input'''
    return _maths.ceil(a, out)

@_wrap('a')
def rint(a, out=None):
    '''Round elements of input to nearest integers'''
    return _maths.rint(a, out)

@_wrap('a')
def trunc(a, out=None):
    '''Truncate elements of input to nearest integers'''
    return _maths.truncate(a, out)

fix = trunc

@_wrap('a')
def rad2deg(a, out=None):
    '''Convert from radian to degree'''
    return _maths.toDegrees(a, out)

@_wrap('a')
def deg2rad(a, out=None):
    '''Convert from degree to radian'''
    return _maths.toRadians(a, out)

degrees = rad2deg
radians = deg2rad

@_wrap('a')
def sign(a, out=None):
    '''Sign of input, indicated by -1 for negative, +1 for positive and 0 for zero'''
    return _maths.signum(a, out)

@_wrap('a')
def negative(a, out=None):
    '''Negate input'''
    return _maths.negative(a, out)

@_wrap('a', 'a_min', 'a_max')
def clip(a, a_min, a_max, out=None):
    '''Clip input to given bounds (replace NaNs with midpoint of bounds)'''
    return _maths.clip(a, a_min, a_max, out)

@_wrap('a', 'b')
def maximum(a, b, out=None):
    '''Item-wise maximum'''
    return _maths.maximum(a, b)

@_wrap('a', 'b')
def minimum(a, b, out=None):
    '''Item-wise minimum'''
    return _maths.minimum(a, b)

@_keepdims
@_wrap('a')
def median(a, axis=None, keepdims=False):
    '''Median of input'''
    if axis is None:
        return _stats.median(a)
    else:
        return _stats.median(a, axis)

@_wrap('a')
def cumprod(a, axis=None, dtype=None):
    '''Cumulative product of input'''
    dtype = _translatenativetype(dtype)
    if dtype is not None:
        a = a.cast(_translatenativetype(dtype).value)
    if axis is None:
        return _stats.cumulativeProduct(a)
    else:
        return _stats.cumulativeProduct(a, _jint(axis))

@_wrap('a')
def cumsum(a, axis=None, dtype=None):
    '''Cumulative sum of input'''
    dtype = _translatenativetype(dtype)
    if dtype is not None:
        a = a.cast(_translatenativetype(dtype).value)
    if axis is None:
        return _stats.cumulativeSum(a)
    else:
        return _stats.cumulativeSum(a, _jint(axis))

@_wrap('a')
def diff(a, order=1, axis=-1):
    '''Difference of input'''
    return _maths.difference(a, order, axis)

import uk.ac.diamond.scisoft.analysis.dataset.function.Histogram as _histo

@_wrap('a', 'weights')
def histogram(a, bins=10, range=None, normed=False, weights=None, density=None): #@ReservedAssignment
    '''Histogram of input'''
    if normed or density:
        raise ValueError("Option not supported yet")

    if isinstance(bins, str):
        raise ValueError("bin string option not supported yet")

    if not isinstance(bins, int):
        bins = _asarray(bins)._jdataset()
    h = _histo(bins)

    if range is not None:
        if len(range) != 2:
            raise ValueError("Need two values in range")
        h.setMinMax(range[0], range[1])

    if weights is not None:
        h.setWeights(_asarray(weights)._jdataset())

    if not isinstance(a, _ds):
        a = _asarray(a)._jdataset()
    return h.value(a)

import uk.ac.diamond.scisoft.analysis.dataset.function.Histogram2D as _histo2d

@_wrap('x', 'y', 'range', 'weights')
def histogram2d(x, y, bins=10, range=None, normed=False, weights=None): #@ReservedAssignment
    '''2d histogram of input'''
    if normed:
        raise ValueError("Option not supported yet")

    if isinstance(bins, str):
        raise ValueError("bin string option not supported yet")

    if isinstance(bins, int):
        bins = [bins]
    elif isinstance(bins, (tuple, list)):
        if not isinstance(bins[0], int):
            bins = [_asarray(b)._jdataset() for b in bins]
    h = _histo2d(bins)

    if range is not None:
        range = _asarray(range)
        if range.shape != (2,2):
            raise ValueError("Need four values in range as 2,2 array")
        range = range.ravel()
        h.setBinMinima([i for i in range[0::2]])
        h.setBinMaxima([i for i in range[1::2]])

    if weights is not None:
        h.setWeights(_asarray(weights)._jdataset())

    if not isinstance(x, _ds):
        x = _asarray(x)._jdataset()
    if not isinstance(y, _ds):
        y = _asarray(y)._jdataset()
    return h.value(x, y)

import uk.ac.diamond.scisoft.analysis.dataset.function.HistogramND as _histond

@_wrap('s', 'weights')
def histogramdd(s, bins=10, range=None, normed=False, weights=None): #@ReservedAssignment
    '''d-d histogram of input'''
    if normed:
        raise ValueError("Option not supported yet")

    if isinstance(bins, str):
        raise ValueError("bin string option not supported yet")

    if isinstance(bins, int):
        bins = [bins]
    elif isinstance(bins, (tuple, list)):
        if not isinstance(bins[0], int):
            bins = [_asarray(b)._jdataset() for b in bins]
    h = _histond(bins)

    if range is not None:
        range = _asarray(range)
        range = range.ravel()
        h.setBinMinima([i for i in range[0::2]])
        h.setBinMaxima([i for i in range[1::2]])

    if weights is not None:
        h.setWeights(_asarray(weights)._jdataset())

    if not isinstance(s, _ds):
        s = _asarray(s)._jdataset()
    r = h.value(s)
    return r[0], [_asarray(a) for a in r[1:]]

import uk.ac.diamond.scisoft.analysis.dataset.function.BinCount as _bincount

@_wrap('a')
def bincount(a, weights=None, minlength=0):
    '''Count occurrences of values in array'''
    if weights is not None:
        if weights.getShapeRef() != a.getShapeRef():
            raise ValueError("Weights must have same shape as input")
    if a.getRank() != 1:
        raise ValueError("Input must be 1D")
    if a.hasFloatingPointElements():
        raise TypeError("Input must be integers")
    bc = _bincount()
    bc.setMinimumLength(minlength)
    bc.setWeights(weights)

    return bc.value(a)[0]

import org.eclipse.january.dataset.LinearAlgebra as _linalg

@_wrap('a', 'b')
def dot(a, b):
    '''Dot product of two arrays'''
    return _linalg.dotProduct(a, b)

@_wrap('a', 'b')
def vdot(a, b):
    '''Dot product of two vectors with first vector conjugated if complex'''
    return _linalg.dotProduct(conjugate(a.flatten()), b.flatten())

@_wrap('a', 'b')
def inner(a, b):
    '''Inner product of two arrays (sum product over last dimensions)'''
    return _linalg.tensorDotProduct(a, b, -1, -1)

@_wrap('a', 'b')
def outer(a, b):
    '''Outer product of two arrays'''
    return _linalg.outerProduct(a, b)

@_argsToArrayType('a', 'b')
def matmul(a, b):
    '''Matrix product of two datasets
    '''
    a = _asarray(a)
    b = _asarray(b)
    prepend = a.rank == 1
    if prepend:
        a.shape = 1,a.shape[0] 
    append = b.rank == 1
    if append:
        b.shape = b.shape[0],1 
    m = _asarray(_linalg.matrixProduct(a._jdataset(), b._jdataset()))
    if prepend:
        m.shape = m.shape[1:]
    if append:
        m.shape = m.shape[:-1]
    return m

@_wrap('a', 'b')
def tensordot(a, b, axes=2):
    '''Tensor dot product of two arrays
    '''
    if isinstance(axes, int):
        bx = list(range(axes))
        ao = a.getRank() - axes
        ax = [ ao + i for i in bx ]
    else:
        if isinstance(axes, (list, tuple)):
            if len(axes) == 0:
                raise ValueError("Given axes sequence should be non-empty")

            if len(axes) == 1:
                ax = axes[0]
                bx = axes[0]
            else:
                ax = axes[0]
                bx = axes[1]

            tal = isinstance(ax, (list, tuple))
            tbl = isinstance(bx, (list, tuple))
            if tal != tbl:
                if tal:
                    bx = list(bx)
                else:
                    ax = list(ax)
        else:
            raise ValueError("Given axes has wrong type")

    return _linalg.tensorDotProduct(a, b, ax, bx)

@_wrap('a', 'b')
def kron(a, b):
    '''Kronecker product of two arrays'''
    return _linalg.kroneckerProduct(a, b)

@_wrap('a')
def trace(a, offset=0, axis1=0, axis2=1, dtype=None):
    t = _linalg.trace(a, offset)

    dtype = _translatenativetype(dtype)
    if dtype is not None:
        t = t.cast(dtype.value)
    return t

@_wrap('a', 'b')
def cross(a, b, axisa=-1, axisb=-1, axisc=-1, axis=None):
    '''Cross product of two (arrays of vectors)
    
    a -- first vector
    b -- second vector
    axisa -- axis of a that defines the vector(s)
    axisb -- axis of b that defines the vector(s)
    axisc -- axis of c that will contain the cross product(s)
    axis -- override all values of axis values
    '''
    if axis is not None:
        axisa = axisb = axisc = axis

    return _linalg.crossProduct(a, b, axisa, axisb, axisc)

@_wrap
def gradient(f, *varargs):
    '''Gradient of array
    
    f -- array
    *varargs -- 0, 1, N scalars for sample distance, or (1 or N-d) datasets for sample points
    '''

    if varargs is None or len(varargs) == 0:
        g = _maths.gradient(f)
    else:
        # check for scalars, etc
        from .jycore import arange as _ar
        vl = len(varargs)
        nd = f.getRank()
        if vl == 1:
            varargs = [varargs[0]]*nd
            vl = nd
        if vl != nd:
            raise ValueError("Number of arguments must be 0, 1 or rank of f")

        xlist = []
        for i in range(vl):
            x = varargs[i]
            xlist.append(x if isinstance(x, _ds) else (_ar(f.shape[i])*x)._jdataset())
        g = _maths.gradient(f, xlist)

    if len(g) == 1:
        return g[0]
    return g

@_argsToArrayType('p')
def roots(p):
    '''Roots of polynomial'''
    from uk.ac.diamond.scisoft.analysis.fitting.functions import Polynomial as _poly
    pa = _asarray(p, dtype=_f64)
    return _asarray(_poly.findRoots(pa._jdataset().getBuffer()))

@_argsToArrayType('x')
def interp(x, xp, fp, left=None, right=None):
    '''Linearly interpolate'''
    x = _asarray(x)
        
    xp = _asarray(xp)
    fp = _asarray(fp)
    if left is None:
        left = fp[0]
    if right is None:
        right = fp[-1]
    r = _asarray(_maths.interpolate(xp._jdataset(), fp._jdataset(), x._jdataset(), left, right))
    if x.ndim == 0:
        return r.item()
    return r
