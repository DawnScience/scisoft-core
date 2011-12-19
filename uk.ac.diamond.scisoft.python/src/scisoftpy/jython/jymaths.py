'''
Maths package
'''

import uk.ac.diamond.scisoft.analysis.dataset.Maths as _maths
import uk.ac.diamond.scisoft.analysis.dataset.Stats as _stats

import types as _types

from jarray import array as _array

_arraytype = type(_array([0], 'f')) # this is used for testing if returned object is a Java array


from decorator import decorator as _decorator

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

def _wrap(func, *arg, **kwargs):
    from jycore import Sciwrap as _sciwrap
    import java.util.List as List #@UnresolvedImport
    result = func(*arg, **kwargs)
    if type(result) is _types.ListType:
        return [ _sciwrap(r) for r in result ]
    elif type(result) is _types.TupleType:
        return tuple([ _sciwrap(r) for r in result ])
    elif isinstance(result, List):
        return [ _sciwrap(r) for r in result ]
    elif type(result) is _arraytype:
        return [ _sciwrap(r) for r in result if r is not None ]
    return _sciwrap(result)

def ndarraywrapped(func):
    return _decorator(_wrap, func)


@ndarraywrapped
def add(a, b):
    '''Add two array-like objects together'''
    return _maths.add(a, b)

@ndarraywrapped
def subtract(a, b):
    '''Subtract one array-like object from another'''
    return _maths.subtract(a, b)

@ndarraywrapped
def multiply(a, b):
    '''Multiply two array-like objects together'''
    return _maths.multiply(a, b)

@ndarraywrapped
def divide(a, b):
    '''Divide one array-like object by another'''
    return _maths.divide(a, b)

@ndarraywrapped
def dividez(a, b):
    '''Divide one array-like object by another with items that are zero divisors set to zero'''
    return _maths.dividez(a, b)

@ndarraywrapped
def floor_divide(a, b):
    '''Calculate largest integers smaller or equal to division'''
    return _maths.floorDivide(a, b)

@ndarraywrapped
def remainder(a, b):
    '''Return remainder of division of inputs'''
    return _maths.remainder(a, b)
#>>> np.floor_divide(7,3)
#2
#>>> np.floor_divide([1., 2., 3., 4.], 2.5)
#array([ 0.,  0.,  1.,  1.])

# modf
#Return the fractional and integral part of a number.
#
#The fractional and integral parts are negative if the given number is negative.

#>>> np.modf(2.5)
#(0.5, 2.0)
#>>> np.modf(-.4)
#(-0.40000000000000002, -0.0)

#>>> np.remainder([4,7],[2,3])
#array([0, 1])

fmod = remainder
# FIXME these are different
# >>> np.fmod([-3, -2, -1, 1, 2, 3], 2)
# array([-1,  0, -1,  1,  0,  1])
# >>> np.mod([-3, -2, -1, 1, 2, 3], 2)
# array([1, 0, 1, 1, 0, 1])
#
mod = remainder

@ndarraywrapped
def reciprocal(a):
    '''Calculate reciprocal of input'''
    return _maths.reciprocal(a)

@ndarraywrapped
def abs(a): #@ReservedAssignment
    '''Absolute value of input'''
    return _maths.abs(a)

absolute = abs

fabs = abs # supports complex types too

@ndarraywrapped
def angle(a):
    '''Angle of complex argument'''
    return _maths.angle(a)

@ndarraywrapped
def conjugate(a):
    '''Complex conjugate of input'''
    return _maths.conjugate(a)

conj = conjugate

@ndarraywrapped
def phase(a, keepzeros=False):
    '''Calculate phase of input by dividing by amplitude
    
    keepzeros -- if True, pass zeros through, else return complex NaNs
    '''
    return _maths.phaseAsComplexNumber(a, keepzeros)

@ndarraywrapped
def sin(a):
    '''Sine of input'''
    return _maths.sin(a)

@ndarraywrapped
def cos(a):
    '''Cosine of input'''
    return _maths.cos(a)

@ndarraywrapped
def tan(a):
    '''Tangent of input'''
    return _maths.tan(a)

@ndarraywrapped
def arcsin(a):
    '''Inverse sine of input'''
    return _maths.arcsin(a)

@ndarraywrapped
def arccos(a):
    '''Inverse cosine of input'''
    return _maths.arccos(a)

@ndarraywrapped
def arctan(a):
    '''Inverse tangent of input'''
    return _maths.arctan(a)

@ndarraywrapped
def arctan2(a, b):
    '''Inverse tangent of a/b with correct choice of quadrant'''
    return _maths.arctan2(a, b)

@ndarraywrapped
def hypot(a, b):
    '''Hypotenuse of triangle of given sides'''
    return _maths.hypot(a, b)

@ndarraywrapped
def sinh(a):
    '''Hyperbolic sine of input'''
    return _maths.sin(a)

@ndarraywrapped
def cosh(a):
    '''Hyperbolic cosine of input'''
    return _maths.cosh(a)

@ndarraywrapped
def tanh(a):
    '''Hyperbolic tangent of input'''
    return _maths.tanh(a)

@ndarraywrapped
def arcsinh(a):
    '''Inverse hyperbolic sine of input'''
    return _maths.arcsinh(a)

@ndarraywrapped
def arccosh(a):
    '''Inverse hyperbolic cosine of input'''
    return _maths.arccosh(a)

@ndarraywrapped
def arctanh(a):
    '''Inverse hyperbolic tangent of input'''
    return _maths.arctanh(a)

@ndarraywrapped
def log(a):
    '''Natural logarithm of input'''
    return _maths.log(a)

@ndarraywrapped
def log2(a):
    '''Logarithm of input to base 2'''
    return _maths.log2(a)

@ndarraywrapped
def log10(a):
    '''Logarithm of input to base 10'''
    return _maths.log10(a)

@ndarraywrapped
def log1p(x):
    '''Natural logarithm of (x+1)'''
    return _maths.log1p(x)

@ndarraywrapped
def exp(a):
    '''Exponential of input'''
    return _maths.exp(a)

@ndarraywrapped
def expm1(x):
    '''Exponential of (x-1)'''
    return _maths.expm1(x)

@ndarraywrapped
def sqrt(a):
    '''Square root of input'''
    return _maths.sqrt(a)

@ndarraywrapped
def cbrt(a):
    '''Cube root of input'''
    return _maths.cbrt(a)

@ndarraywrapped
def square(a):
    '''Square of input'''
    return _maths.square(a)

@ndarraywrapped
def power(a, p):
    '''Input raised to given power'''
    return _maths.power(a, p)

@ndarraywrapped
def floor(a):
    '''Largest integer smaller or equal to input'''
    return _maths.floor(a)

@ndarraywrapped
def ceil(a):
    '''Smallest integer greater or equal to input'''
    return _maths.ceil(a)

@ndarraywrapped
def rint(a):
    '''Round elements of input to nearest integers'''
    return _maths.rint(a)

@ndarraywrapped
def rad2deg(a):
    '''Convert from radian to degree'''
    return _maths.toDegrees(a)

@ndarraywrapped
def deg2rad(a):
    '''Convert from degree to radian'''
    return _maths.toRadians(a)

degrees = rad2deg
radians = deg2rad

@ndarraywrapped
def sign(a):
    '''Sign of input, indicated by -1 for negative, +1 for positive and 0 for zero'''
    return _maths.signum(a)

@ndarraywrapped
def negative(a):
    '''Negate input'''
    return _maths.negative(a)

@ndarraywrapped
def clip(a, a_min, a_max):
    '''Clip input to given bounds (replace NaNs with midpoint of bounds)'''
    return _maths.clip(a, a_min, a_max)

@ndarraywrapped
def skewness(a, axis=None):
    '''Skewness of input'''
    if axis == None:
        return _stats.skewness(a)
    else:
        return _stats.skewness(a, axis)

@ndarraywrapped
def kurtosis(a, axis=None):
    '''Kurtosis of input'''
    if axis == None:
        return _stats.kurtosis(a)
    else:
        return _stats.kurtosis(a, axis)

@ndarraywrapped
def median(a, axis=None):
    '''Median of input'''
    if axis == None:
        return _stats.median(a)
    else:
        return _stats.median(a, axis)

@ndarraywrapped
def iqr(a, axis=None):
    '''Interquartile range of input'''
    if axis == None:
        return _stats.iqr(a)
    else:
        return _stats.iqr(a, axis)

def quantile(a, q):
    '''Quantile of input'''
    return _stats.quantile(a, q)

@ndarraywrapped
def prod(a, axis=None):
    '''Product of input'''
    if axis == None:
        return _stats.product(a)
    else:
        return _stats.product(a, axis)

# these functions call (wrapped) instance methods
def sum(a, axis=None): #@ReservedAssignment
    '''Sum of input'''
    return a.sum(axis)

def mean(a, axis=None):
    '''Arithmetic mean of input'''
    return a.mean(axis)

def std(a, axis=None, ddof=0):
    '''Standard deviation of input'''
    return a.std(axis, ddof)

def var(a, axis=None, ddof=0):
    '''Variance of input'''
    return a.var(axis, ddof)

def ptp(a, axis=None):
    '''Peak-to-peak of input'''
    return a.ptp(axis)

def amax(a, axis=None):
    '''Maximum of input'''
    return a.max(axis)

def amin(a, axis=None):
    '''Minimum of input'''
    return a.min(axis)

@ndarraywrapped
def cumprod(a, axis=None):
    '''Cumulative product of input'''
    if axis == None:
        return _stats.cumulativeProduct(a)
    else:
        return _stats.cumulativeProduct(a, axis)

@ndarraywrapped
def cumsum(a, axis=None):
    '''Cumulative sum of input'''
    if axis == None:
        return _stats.cumulativeSum(a)
    else:
        return _stats.cumulativeSum(a, axis)

def residual(a, b):
    '''Residual (sum of squared difference) of two inputs'''
    return _stats.residual(a, b)

@ndarraywrapped
def diff(a, order=1, axis=-1):
    '''Difference of input'''
    return _maths.difference(a, order, axis)

import uk.ac.diamond.scisoft.analysis.dataset.function.Histogram as _histo

@ndarraywrapped
def histogram(a, bins=10, range=None, normed=False, weights=None, new=None): #@ReservedAssignment
    '''Histogram of input'''
    if normed or weights or new:
        raise ValueError, 'Option not supported yet'

    h = None
    if range is None:
        h = _histo(bins)
    elif len(range) != 2:
        raise ValueError, 'Need two values in range'
    else:
        h = _histo(bins, range[0], range[1])

    from jycore import asDatasetList as _asList
    return h.value(_asList(a))

import uk.ac.diamond.scisoft.analysis.dataset.LinearAlgebra as _linalg

@ndarraywrapped
def dot(a, b):
    '''Dot product of two arrays'''
    return _linalg.dotProduct(a, b)

@ndarraywrapped
def vdot(a, b):
    '''Dot product of two vectors with first vector conjugated if complex'''
    return _linalg.dotProduct(conjugate(a.flatten()), b.flatten())

@ndarraywrapped
def inner(a, b):
    '''Inner product of two arrays (sum product over last dimensions)'''
    return _linalg.tensorDotProduct(a, b, -1, -1)


@ndarraywrapped
def tensordot(a, b, axes=2):
    '''Tensor dot product of two arrays
    '''
    if isinstance(axes, int):
        bx = range(axes)
        ao = a.rank - axes - 1
        ax = [ ao + i for i in bx ]
    else:
        t = type(axes)
        if t is _types.ListType or t is _types.TupleType:
            if len(t) == 0:
                raise ValueError, 'Given axes sequence should be non-empty'

            if len(t) == 1:
                ax = axes[0]
                bx = axes[0]
            else:
                ax = axes[0]
                bx = axes[1]

            ta = type(ax)
            tal = ta is _types.ListType or ta is _types.TupleType
            tb = type(bx)
            tbl = tb is _types.ListType or tb is _types.TupleType
            if tal != tbl:
                if tal:
                    bx = list(bx)
                else:
                    ax = list(ax)
        else:
            raise ValueError, 'Given axes has wrong type'

    return _linalg.tensorDotProduct(a, b, ax, bx)
