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
Core package contains wrappers for Java dataset classes
'''

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset as _abstractds
import uk.ac.diamond.scisoft.analysis.dataset.AbstractCompoundDataset as _abscompoundds

import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset as _booleands
import uk.ac.diamond.scisoft.analysis.dataset.ByteDataset as _byteds
import uk.ac.diamond.scisoft.analysis.dataset.ShortDataset as _shortds
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset as _integerds
import uk.ac.diamond.scisoft.analysis.dataset.LongDataset as _longds
import uk.ac.diamond.scisoft.analysis.dataset.CompoundByteDataset as _compoundbyteds
import uk.ac.diamond.scisoft.analysis.dataset.CompoundShortDataset as _compoundshortds
import uk.ac.diamond.scisoft.analysis.dataset.CompoundIntegerDataset as _compoundintegerds
import uk.ac.diamond.scisoft.analysis.dataset.CompoundLongDataset as _compoundlongds
import uk.ac.diamond.scisoft.analysis.dataset.FloatDataset as _floatds
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset as _doubleds
import uk.ac.diamond.scisoft.analysis.dataset.CompoundFloatDataset as _compoundfloatds
import uk.ac.diamond.scisoft.analysis.dataset.CompoundDoubleDataset as _compounddoubleds
import uk.ac.diamond.scisoft.analysis.dataset.RGBDataset as _rgbds
import uk.ac.diamond.scisoft.analysis.dataset.ComplexFloatDataset as _complexfloatds
import uk.ac.diamond.scisoft.analysis.dataset.ComplexDoubleDataset as _complexdoubleds

import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils as _dsutils
from uk.ac.diamond.scisoft.python.PythonUtils import convertToJava as _cvt2j
from uk.ac.diamond.scisoft.python.PythonUtils import getSlice as _getslice
from uk.ac.diamond.scisoft.python.PythonUtils import setSlice as _setslice

import org.apache.commons.math.complex.Complex as _jcomplex #@UnresolvedImport

import Jama.Matrix as _matrix #@UnresolvedImport

import jymaths as _maths

import types as _types

import java.lang.ArrayIndexOutOfBoundsException as _jarrayindex_exception #@UnresolvedImport

class ndgeneric(object):
    pass # there is no array scalars at the moment

generic = ndgeneric

class _dtype(object):
    '''
    Dataset type has two properties:

    value = Java dataset type object
    elements = number of elements per item
    '''
    def __init__(self, value, elements=1, name=None):
        self.value = value
        self.elements = elements
        self.name = name
    def __str__(self):
        if self.name:
            s = self.name
        else:
            s = ""
        if self.elements > 1:
            s += "(%d)" % self.elements
        return s

bool = _dtype(_abstractds.BOOL, name='bool') #@ReservedAssignment
int8 = _dtype(_abstractds.INT8, name='int8')
int16 = _dtype(_abstractds.INT16, name='int16')
int32 = _dtype(_abstractds.INT32, name='int32')
int64 = _dtype(_abstractds.INT64, name='int64')
cint8 = lambda e : _dtype(_abstractds.ARRAYINT8, e, 'cint8')
cint16 = lambda e : _dtype(_abstractds.ARRAYINT16, e, 'cint16')
cint32 = lambda e : _dtype(_abstractds.ARRAYINT32, e, 'cint32')
cint64 = lambda e : _dtype(_abstractds.ARRAYINT64, e, 'cint64')
float32 = _dtype(_abstractds.FLOAT32, name='float32')
float64 = _dtype(_abstractds.FLOAT64, name='float64')
cfloat32 = lambda e : _dtype(_abstractds.ARRAYFLOAT32, e, 'cfloat32')
cfloat64 = lambda e : _dtype(_abstractds.ARRAYFLOAT64, e, 'cfloat64')
complex64 = _dtype(_abstractds.COMPLEX64, name='complex64')
complex128 = _dtype(_abstractds.COMPLEX128, name='complex128')

rgb = _dtype(_abstractds.RGB, 3, 'rgb')

# tuple of floating point types
_floattype = (_abstractds.FLOAT32, _abstractds.FLOAT64, _abstractds.ARRAYFLOAT32, _abstractds.ARRAYFLOAT64)

# dictionaries to map from Java dataset types to Jython types
__jdtype2jytype = { _abstractds.BOOL : bool, _abstractds.INT8 : int8, _abstractds.INT16 : int16,
                    _abstractds.INT32 : int32, _abstractds.INT64 : int64,
                    _abstractds.FLOAT32 : float32, _abstractds.FLOAT64 : float64,
                    _abstractds.COMPLEX64 : complex64, _abstractds.COMPLEX128 : complex128,
                    _abstractds.RGB : rgb }

__jcdtype2jytype = { _abstractds.ARRAYINT8 : cint8, _abstractds.ARRAYINT16 : cint16,
                    _abstractds.ARRAYINT32 : cint32, _abstractds.ARRAYINT64 : cint64,
                    _abstractds.ARRAYFLOAT32 : cfloat32, _abstractds.ARRAYFLOAT64 : cfloat64 }

# get dtype from object
def _getdtypefromobj(jobj):
    jdtype = _abstractds.getDTypeFromObject(jobj)
    if jdtype in __jdtype2jytype:
        return __jdtype2jytype[jdtype]
    raise ValueError, "Java dataset type unknown"

# get dtype from Java dataset
def _getdtypefromjdataset(jobj):
    d = jobj.getDtype()
    if d in __jdtype2jytype:
        return __jdtype2jytype[d]
    if d in __jcdtype2jytype:
        return __jcdtype2jytype[d](jobj.getElementsPerItem())
    raise ValueError, "Java dataset type unknown"

# check for native python type
def _translatenativetype(dtype):
    if isinstance(dtype, _dtype) or dtype in __jcdtype2jytype.values():
        return dtype
    if dtype is int:
        return int32
    elif dtype is float:
        return float64
    elif dtype is complex:
        return complex128
    raise ValueError, "Dataset type is not recognised"

# default types
int_ = int32
float_ = float64
complex_ = complex128

# native types
int = int #@ReservedAssignment
float = float #@ReservedAssignment
complex = complex #@ReservedAssignment

from jarray import array as _array
_arraytype = type(_array([0], 'f')) # this is used for testing if returned object is a Java array

import java.util.List as _jlist #@UnresolvedImport
import java.util.Map as _jmap #@UnresolvedImport

def Sciwrap(a):
    """
    This wrapper function is required for any Java method that returns a dataset
    """
    if a is None:
        raise ValueError, "No value given"
    if isinstance(a, ndarray):
        return a
    if isinstance(a, _rgbds):
        return ndarrayRGB(a, True)
    if isinstance(a, _abscompoundds):
        return __cdtype2jythoncls[a.getDtype()](a, True)
    if isinstance(a, _abstractds):
        return __dtype2jythoncls[a.getDtype()](a, True)
    return a

_ndwrapped = _maths.ndarraywrapped

def asIterable(items):
    """
    Ensure entity is an iterable by making it a tuple if not
    """
    t = type(items)
    if t is _types.ListType or t is _types.TupleType or t is _arraytype:
        pass
    elif isinstance(items, _jlist) or isinstance(items, _jmap):
        pass
    else: # isinstance(items, _abstractds) or isinstance(items, _dataset):
        items = (items,)
    return items

def toList(listdata):
    '''Convert a list or tuple to list of datasets'''
    return [ d for d in asIterable(listdata) ]

def scalarToPython(ascalar):
    '''Convert an array scalar to a python type
    '''
    return ascalar # there is no array scalars at the moment

def fromDS(data):
    '''Convert from a DataSet'''
#    if isinstance(data, _dataset):
#        return Sciwrap(_dataset.convertToDoubleDataset())
    if isinstance(data, _abstractds):
        return Sciwrap(data)
    return data

def asDataset(data, dtype=None, force=False):
    """
    Used for arithmetic ops to coerce a sequence to a dataset otherwise leave as single item
    """
#    if isinstance(data, _dataset):
#        return Sciwrap(_dataset.convertToDoubleDataset())
    if isinstance(data, _abstractds):
        if dtype is None or data.dtype == dtype:
            return data
        else:
            return array(data, dtype=dtype, copy=False)

    try:
        iter(data)
    except:
        if not force:
            if isinstance(data, complex):
                return _jcomplex(data.real, data.imag)
            return data

    return array(data, dtype)

asarray = asDataset
asanyarray = asDataset

@_ndwrapped
def asfarray(data, dtype=None):
    data = asDataset(data)
    if data.isComplex():
        raise TypeError, 'can\'t convert complex to float'
    if data.hasFloatingPointElements():
        return data

    dt = _getdtypefromjdataset(data)
    if dtype is not None:
        dtype = _translatenativetype(dtype)
    if dtype is None or dtype.value not in _floattype:
        if dt.elements == 1:
            return data.cast(_abstractds.FLOAT64)
        return data.cast(_abstractds.ARRAYFLOAT64)
    return data.cast(dtype.value)

def asDatasetList(dslist):
    """
    Used to coerce a list of DataSets to a list of datasets
    """
    return [ fromDS(d) for d in asIterable(dslist) ]

def asDatasetDict(dsdict):
    """
    Used to coerce a dictionary of DataSets to a dictionary of datasets
    """
    rdict = {}
    for k in dsdict:
        rdict[k] = fromDS(dsdict[k])
    return rdict

def _isslice(rank, shape, key):
#    print rank, shape, key
    key = asIterable(key)
    if rank < len(shape):
        raise IndexError, "Rank and shape do not correspond"

    if len(key) < rank:
        return True
    elif len(key) > rank:
        raise IndexError, "Too many indices"

    for k in key:
        if isinstance(k, slice) or k is Ellipsis:
            return True
    return False

import jycomparisons as _cmps

# prevent incorrect coercion of Python booleans causing trouble with overloaded Java methods
import java.lang.Boolean as _jbool #@UnresolvedImport
_jtrue = _jbool(1)
_jfalse = _jbool(0)

class ndarray:
    """
    Class to hold special methods and non-overloading names
    """
    def __add__(self, o):
        return _maths.add(self, asDataset(o))
    def __radd__(self, o):
        return _maths.add(self, asDataset(o))
    def __iadd__(self, o):
        return self.iadd(asDataset(o, self.dtype))

    def __sub__(self, o):
        return _maths.subtract(self, asDataset(o))
    def __rsub__(self, o):
        return _maths.subtract(asDataset(o), self)
    def __isub__(self, o):
        return self.isubtract(asDataset(o, self.dtype))

    def __mul__(self, o):
        return _maths.multiply(self, asDataset(o))
    def __rmul__(self, o):
        return _maths.multiply(self, asDataset(o))
    def __imul__(self, o):
        return self.imultiply(asDataset(o, self.dtype))

    def __div__(self, o):
        return _maths.divide(self, asDataset(o))
    def __rdiv__(self, o):
        return _maths.divide(asDataset(o), self)
    def __idiv__(self, o):
        return self.idivide(asDataset(o, self.dtype))

    def __truediv__(self, o):
        return _maths.divide(self, asDataset(o))
    def __itruediv__(self, o):
        return self.idivide(asDataset(o, self.dtype))

    def __floordiv__(self, o):
        return _maths.floor_divide(self, asDataset(o))
    def __ifloordiv__(self, o):
        return self.ifloordivide(asDataset(o, self.dtype))

    def __mod__(self, o):
        return _maths.remainder(self, asDataset(o))
    def __imod__(self, o):
        return self.iremainder(asDataset(o, self.dtype))

    def __neg__(self):
        return _maths.negative(self)
    def __pos__(self):
        return self
    def __pow__(self, o):
        return _maths.power(self, asDataset(o))
    def __ipow__(self, o):
        return self.ipower(asDataset(o, self.dtype))

    def __eq__(self, o):
        e = _cmps.equal(self, asDataset(o))
        if self.size == 1:
            return e.getBoolean([])
        return e

    def __ne__(self, o):
        return _cmps.not_equal(self, o)

    def __lt__(self, o):
        return _cmps.less(self, o)

    def __le__(self, o):
        return _cmps.less_equal(self, o)

    def __gt__(self, o):
        return _cmps.greater(self, o)

    def __ge__(self, o):
        return _cmps.greater_equal(self, o)


    def __len__(self):
        if len(self.shape) > 0:
            return self.shape[0]
        return 0

    @_ndwrapped
    def __copy__(self):
        return self.clone()

    def _toslice(self, key):
        '''Transform key to proper slice if necessary
        '''
        if self.rank == 1:
            if isinstance(key, list) and len(key) == 1:
                key = key[0]
            if isinstance(key, slice) or key is Ellipsis:
                return True, key
            if isinstance(key, tuple):
                if len(key) > 1:
                    raise IndexError, "too many indices"
                return False, key
            return False, (key,)

        if _isslice(self.rank, self.shape, key):
            return True, key
        return False, key

    @_ndwrapped
    def __getitem__(self, key):
        if isinstance(key, _booleands):
            return self.getByBoolean(key)
        if isinstance(key, _integerds):
            return self.getByIndex(key)

        isslice, key = self._toslice(key)
        try:
            if isslice:
                return _getslice(self, key)
            return self.getObject(key)
        except _jarrayindex_exception:
            raise IndexError

    def __setitem__(self, key, value):
        value = fromDS(value)
        if isinstance(key, _booleands):
            return self.setByBoolean(value, key)
        if isinstance(key, _integerds):
            return self.setByIndex(value, key)

        isslice, key = self._toslice(key)
        try:
            if isslice:
                _setslice(self, value, key)
                return self
            value = _cvt2j(value)
            return self.set(value, key)
        except _jarrayindex_exception:
            raise IndexError

    def __iter__(self):
        def ndgen(d):
            iterator = d.getIterator()
            while iterator.hasNext():
                yield d.getObjectAbs(iterator.index)
        return ndgen(self)

    def conj(self):
        return _maths.conj(self)

    @_ndwrapped
    def ravel(self):
        return self.flatten()

    @_ndwrapped
    def sum(self, axis=None, dtype=None): #@ReservedAssignment
        if dtype is None:
            dtval = self.getDtype()
        else:
            dtval = _translatenativetype(dtype).value
        if axis is None:
            return self.typedSum(dtval)
        else:
            return self.typedSum(dtval, axis)

    @_ndwrapped
    def prod(self, axis=None, dtype=None):
        if dtype is None:
            dtval = self.getDtype()
        else:
            dtval = _translatenativetype(dtype).value
        if axis is None:
            return self.typedProduct(dtval)
        else:
            return self.typedProduct(dtval, axis)

    @_ndwrapped
    def var(self, axis=None, ddof=0):
        if ddof == 1:
            if axis is None:
                return self.variance()
            else:
                return self.variance(axis)
        else:
            if axis is None:
                v = self.variance()
                n = self.getStoredValue("stats").getN()
            else:
                v = Sciwrap(self.variance(axis))
                n = Sciwrap(self.getStoredValue("count-%d" % axis))
            f = (n - 1.)/(n - ddof)
            return v * f

    @_ndwrapped
    def std(self, axis=None, ddof=0):
        if ddof == 1:
            if axis is None:
                return self.stdDeviation()
            else:
                return self.stdDeviation(axis)
        else:
            if axis is None:
                s = self.stdDeviation()
                n = self.getStoredValue("stats").getN()
            else:
                s = Sciwrap(self.stdDeviation(axis))
                n = Sciwrap(self.getStoredValue("count-%d" % axis))
            import math as _mm
            f = _mm.sqrt((n - 1.)/(n - ddof))
            return s * f

    @_ndwrapped
    def rms(self, axis=None):
        if axis is None:
            return self.rootMeanSquare()
        else:
            return self.rootMeanSquare(axis)

    @_ndwrapped
    def ptp(self, axis=None):
        if axis is None:
            return self.peakToPeak()
        else:
            return self.peakToPeak(axis)

    def clip(self, a_min, a_max):
        return _maths.clip(self, a_min, a_max)

    @_ndwrapped
    def argmax(self, axis=None, ignore_nans=False):
        if axis is None:
            if ignore_nans:
                return self.argMax(_jtrue)
            return self.argMax()
        else:
            if ignore_nans:
                return self.argMax(_jtrue, axis)
            return self.argMax(axis)

    @_ndwrapped
    def argmin(self, axis=None, ignore_nans=False):
        if axis is None:
            if ignore_nans:
                return self.argMin(_jtrue)
            return self.argMin()
        else:
            if ignore_nans:
                return self.argMin(_jtrue, axis)
            return self.argMin(axis)

    # properties
    @property
    @_ndwrapped
    def transpose(self):
        return self.transpose()

    @property
    @_ndwrapped
    def view(self, cls=None):
        '''Return a view of dataset'''
        if cls is None or cls == self.__class__:
            return self.getView()
        else:
            return cast(self, cls.dtype)

    @property
    @_ndwrapped
    def indices(self):
        '''Return an index dataset'''
        return self.getIndices()

    def __get_shape(self):
        return tuple(self.getShape())

    def __set_shape(self, *shape):
        if len(shape) == 1:
            shape = asIterable(shape[0])
        self.setShape(shape)

    shape = property(__get_shape, __set_shape) # python 2.5 rather than using @shape.setter

    @property
    def dtype(self):
        return _getdtypefromjdataset(self)

    @property
    def ndim(self):
        '''Return number of dimensions'''
        return self.getRank()

    @property
    def real(self):
        return self

    def append(self, other, axis=None):
        return append(self, other, axis)

    def item(self):
        '''Return first item of dataset. Dataset must be of size 1'''
        if self.size != 1:
            raise ValueError, "can only work for size-1 datasets"
        return self.getObject([])

class ndarrayA(ndarray, _booleands):
    """
    Wrap boolean dataset
    """
    def __init__(self, *arg):
        _booleands.__init__(self, *arg) #@UndefinedVariable
        self.ndcls = _booleands

    def copy(self): # override to keep superclass's methods
        return ndarrayA(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)


class ndarrayB(ndarray, _byteds):
    """
    Wrap byte dataset
    """
    def __init__(self, *arg):
        _byteds.__init__(self, *arg) #@UndefinedVariable
        self.ndcls = _byteds

    def copy(self): # override to keep superclass's methods
        return ndarrayB(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)


class ndarrayS(ndarray, _shortds):
    """
    Wrap short dataset
    """
    def __init__(self, *arg):
        _shortds.__init__(self, *arg) #@UndefinedVariable
        self.ndcls = _shortds

    def copy(self): # override to keep superclass's methods
        return ndarrayS(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)


class ndarrayI(ndarray, _integerds):
    """
    Wrap integer dataset
    """
    def __init__(self, *arg):
        _integerds.__init__(self, *arg) #@UndefinedVariable
        self.ndcls = _integerds

    def copy(self): # override to keep superclass's methods
        return ndarrayI(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)


class ndarrayL(ndarray, _longds):
    """
    Wrap long dataset
    """
    def __init__(self, *arg):
        _longds.__init__(self, *arg) #@UndefinedVariable
        self.ndcls = _longds

    def copy(self): # override to keep superclass's methods
        return ndarrayL(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)


class ndarrayF(ndarray, _floatds):
    """
    Wrap float dataset
    """
    def __init__(self, *arg):
        _floatds.__init__(self, *arg) #@UndefinedVariable
        self.ndcls = _floatds

    def copy(self): # override to keep superclass's methods
        return ndarrayF(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)


class ndarrayD(ndarray, _doubleds):
    """
    Wrap double dataset
    """
    def __init__(self, *arg):
        _doubleds.__init__(self, *arg) #@UndefinedVariable
        self.ndcls = _doubleds

    def copy(self): # override to keep superclass's methods
        return ndarrayD(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)


class ndarrayC(ndarray, _complexfloatds):
    """
    Wrap complex float dataset
    """
    def __init__(self, *arg):
        _complexfloatds.__init__(self, *arg) #@UndefinedVariable
        self.ndcls = _complexfloatds

    def copy(self): # override to keep superclass's methods
        return ndarrayC(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)

    @property
    @_ndwrapped
    def real(self):
        return self.ndcls.real(self)

    @property
    @_ndwrapped
    def imag(self):
        return self.ndcls.imag(self)

    def __getitem__(self, key):
        z = ndarray.__getitem__(self, key)
        if isinstance(z, _jcomplex):
            return complex(z.getReal(), z.getImaginary())
        return z

    def item(self):
        z = ndarray.item(self)
        return complex(z.getReal(), z.getImaginary())

class ndarrayZ(ndarray, _complexdoubleds):
    """
    Wrap complex double dataset
    """
    def __init__(self, *arg):
        _complexdoubleds.__init__(self, *arg) #@UndefinedVariable
        self.ndcls = _complexdoubleds

    def copy(self): # override to keep superclass's methods
        return ndarrayZ(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)

    @property
    @_ndwrapped
    def real(self):
        return self.ndcls.real(self)

    @property
    @_ndwrapped
    def imag(self):
        return self.ndcls.imag(self)

    def __getitem__(self, key):
        z = ndarray.__getitem__(self, key)
        if isinstance(z, _jcomplex):
            return complex(z.getReal(), z.getImaginary())
        return z

    def item(self):
        z = ndarray.item(self)
        return complex(z.getReal(), z.getImaginary())

class ndarrayCB(ndarray, _compoundbyteds):
    """
    Wrap compound byte dataset
    """
    def __init__(self, *args):
        """
        Two constructors:
        (elements, shape) or (ndarray)
        elements - number of elements in an item
        shape    - shape of dataset
        """
        _compoundbyteds.__init__(self, *args) #@UndefinedVariable
        self.ndcls = _compoundbyteds

    def copy(self): # override to keep superclass's methods
        return ndarrayCB(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)

class ndarrayCS(ndarray, _compoundshortds):
    """
    Wrap compound short dataset
    """
    def __init__(self, *args):
        """
        Two constructors:
        (elements, shape) or (ndarray)
        elements - number of elements in an item
        shape    - shape of dataset
        """
        _compoundshortds.__init__(self, *args) #@UndefinedVariable
        self.ndcls = _compoundshortds

    def copy(self): # override to keep superclass's methods
        return ndarrayCS(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)

class ndarrayCI(ndarray, _compoundintegerds):
    """
    Wrap compound integer dataset
    """
    def __init__(self, *args):
        """
        Two constructors:
        (elements, shape) or (ndarray)
        elements - number of elements in an item
        shape    - shape of dataset
        """
        _compoundintegerds.__init__(self, *args) #@UndefinedVariable
        self.ndcls = _compoundintegerds

    def copy(self): # override to keep superclass's methods
        return ndarrayCI(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)

class ndarrayCL(ndarray, _compoundlongds):
    """
    Wrap compound long dataset
    """
    def __init__(self, *args):
        """
        Two constructors:
        (elements, shape) or (ndarray)
        elements - number of elements in an item
        shape    - shape of dataset
        """
        _compoundlongds.__init__(self, *args) #@UndefinedVariable
        self.ndcls = _compoundlongds

    def copy(self): # override to keep superclass's methods
        return ndarrayCL(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)

class ndarrayCF(ndarray, _compoundfloatds):
    """
    Wrap compound float dataset
    """
    def __init__(self, *args):
        """
        Two constructors:
        (elements, shape) or (ndarray)
        elements - number of elements in an item
        shape    - shape of dataset
        """
        _compoundfloatds.__init__(self, *args) #@UndefinedVariable
        self.ndcls = _compoundfloatds

    def copy(self): # override to keep superclass's methods
        return ndarrayCF(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)

class ndarrayCD(ndarray, _compounddoubleds):
    """
    Wrap compound double dataset
    """
    def __init__(self, *args):
        """
        Two constructors:
        (elements, shape) or (ndarray)
        elements - number of elements in an item
        shape    - shape of dataset
        """
        _compounddoubleds.__init__(self, *args) #@UndefinedVariable
        self.ndcls = _compounddoubleds

    def copy(self): # override to keep superclass's methods
        return ndarrayCD(self)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)

class ndarrayRGB(ndarray, _rgbds):
    """
    Wrap RGB dataset
    """
    def __init__(self, *arg):
        _rgbds.__init__(self, *arg) #@UndefinedVariable
        self.ndcls = _rgbds

    def copy(self): # override to keep superclass's methods
        return ndarrayRGB(self)

    @_ndwrapped
    def get_red(self, dtype=None):
        if dtype is None:
            dtype = int16
        else:
            dtype = _translatenativetype(dtype)
        return self.createRedDataset(dtype.value)

    @_ndwrapped
    def get_green(self, dtype=None):
        if dtype is None:
            dtype = int16
        else:
            dtype = _translatenativetype(dtype)
        return self.createGreenDataset(dtype.value)

    @_ndwrapped
    def get_blue(self, dtype=None):
        if dtype is None:
            dtype = int16
        else:
            dtype = _translatenativetype(dtype)
        return self.createBlueDataset(dtype.value)

    @_ndwrapped
    def get_grey(self, cweights=None, dtype=None):
        '''Get grey image
        
        Arguments:
        cweights -- optional set of weight for combining the colour channel
        dtype    -- optional dataset type (default is int16)'''
        if dtype is None:
            dtype = int16
        else:
            dtype = _translatenativetype(dtype)
        if cweights:
            cweights = asIterable(cweights)
            if len(cweights) != 3:
                raise ValueError, 'three colour channel weights needed'
            csum = float(sum(cweights))
            return self.createGreyDataset(cweights[0]/csum, cweights[1]/csum, cweights[2]/csum, dtype.value)
        return self.createGreyDataset(dtype.value)

    red = property(get_red)
    green = property(get_green)
    blue = property(get_blue)
    grey = property(get_grey)

    # non-specific code that needs ndcls
    # this code cannot put in ndarray superclass as there is a problem when
    # wrapping methods with same name in superclass
    @_ndwrapped
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.max(self, ignore_nans)
        else:
            return self.ndcls.max(self, ignore_nans, axis)

    @_ndwrapped
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        ignore_nans = _jtrue if ignore_nans else _jfalse
        if axis is None:
            return self.ndcls.min(self, ignore_nans)
        else:
            return self.ndcls.min(self, ignore_nans, axis)

    @_ndwrapped
    def mean(self, axis=None):
        if axis is None:
            return self.ndcls.mean(self)
        else:
            return self.ndcls.mean(self, axis)

    def sort(self, axis=-1):
        return self.ndcls.sort(self, axis)

    @_ndwrapped
    def transpose(self, axes=None):
        if axes is None:
            axes = ()
        return self.ndcls.transpose(self, asIterable(axes))

    def put(self, indices, values):
        return self.ndcls.put(self, asIterable(indices), asIterable(values))

    @_ndwrapped
    def take(self, indices, axis=None):
        return self.ndcls.take(self, asIterable(indices), axis)

    @_ndwrapped
    def all(self, axis=None):
        if axis is None:
            return self.ndcls.all(self)
        else:
            return self.ndcls.all(self, axis)

    @_ndwrapped
    def any(self, axis=None):
        if axis is None:
            return self.ndcls.any(self)
        else:
            return self.ndcls.any(self, axis)

    @_ndwrapped
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.ndcls.reshape(self, shape)

# dictionaries to map from dtype.value to nd array class
__dtype2jythoncls = { _abstractds.BOOL:ndarrayA, _abstractds.INT8:ndarrayB, _abstractds.INT16:ndarrayS,
                     _abstractds.INT32:ndarrayI, _abstractds.INT64:ndarrayL,
                     _abstractds.FLOAT32:ndarrayF, _abstractds.FLOAT64:ndarrayD,
                     _abstractds.COMPLEX64:ndarrayC, _abstractds.COMPLEX128:ndarrayZ }

__cdtype2jythoncls = { _abstractds.ARRAYINT8:ndarrayCB, _abstractds.ARRAYINT16:ndarrayCS,
                      _abstractds.ARRAYINT32:ndarrayCI, _abstractds.ARRAYINT64:ndarrayCL,
                      _abstractds.ARRAYFLOAT32:ndarrayCF, _abstractds.ARRAYFLOAT64:ndarrayCD,
                      _abstractds.COMPLEX64:ndarrayC, _abstractds.COMPLEX128:ndarrayZ }

# map atomic dataset type to compound type
__dtype2cdtype = { int8:cint8, int16:cint16, int32:cint32, int64:cint64,
                  float32:cfloat32, float64:cfloat64 }

@_ndwrapped
def arange(start, stop=None, step=1, dtype=None):
    '''Create a 1D dataset of given type where values range from specified start up to
    but not including stop in given steps

    Arguments:
    start -- optional starting value, defaults to 0
    stop  -- exclusive stop value
    step  -- difference between neighbouring values, defaults to 1
    dtype -- defaults to None which means the type is inferred from given start, stop, step values
    '''
    if stop is None:
        stop = start
        start = 0
    if dtype is None:
        if type(start) is _types.ComplexType or type(stop) is _types.ComplexType or type(step) is _types.ComplexType: 
            dtype = complex128
        elif type(start) is _types.FloatType or type(stop) is _types.FloatType or type(step) is _types.FloatType: 
            dtype = float64
        elif type(start) is _types.IntType or type(stop) is _types.IntType or type(step) is _types.IntType: 
            dtype = int32
        else:
            raise ValueError, "Unknown or invalid type of input value"
    else:
        dtype = _translatenativetype(dtype)
    if dtype == bool:
        return None

    return _abstractds.arange(start, stop, step, dtype.value)

@_ndwrapped
def array(obj, dtype=None, copy=True):
    '''Create a dataset of given type from a sequence or JAMA matrix'''
    if isinstance(obj, _abstractds):
        if copy:
            if dtype is None or dtype == obj.dtype:
                return obj.clone()
            else:
                return obj.cast(_translatenativetype(dtype).value)
        else:
            if dtype is None:
                dtype = obj.dtype
            return obj.cast(_translatenativetype(dtype).value)

    if not isinstance(obj, list):
        if isinstance(obj, _matrix): # cope with JAMA matrices
            if dtype is None:
                dtype = float64
            obj = obj.getArray()

    obj = _cvt2j(obj)
    if dtype is None:
        dtype = _getdtypefromobj(obj)
    else:
        dtype = _translatenativetype(dtype)

    return _abstractds.array(obj, dtype.value)

@_ndwrapped
def ones(shape, dtype=float64):
    '''Create a dataset filled with 1'''
    dtype = _translatenativetype(dtype)
    return _abstractds.ones(dtype.elements, asIterable(shape), dtype.value)

def zeros(shape, dtype=float64, elements=1):
    '''Create a dataset filled with 0'''
    shape = asIterable(shape)
    if dtype is rgb:
        return ndarrayRGB(shape)

    dtype = _translatenativetype(dtype)
    if elements != 1:
        if dtype in __dtype2cdtype:
            dtype = __dtype2cdtype[dtype](elements)
        else:
            dtype = dtype(elements)
    if dtype.elements != 1:
        return __cdtype2jythoncls[dtype.value](dtype.elements, shape)
    return __dtype2jythoncls[dtype.value](shape)

empty = zeros

@_ndwrapped
def zeros_like(a):
    return _abstractds.zeros(a)

empty_like = zeros_like

@_ndwrapped
def ones_like(a):
    return _abstractds.zeros(a).fill(1)

def linspace(start, stop, num=50, endpoint=True, retstep=False):
    '''Create a 1D dataset from start to stop in given number of steps
    
    Arguments:
    start    -- starting value
    stop     -- stopping value
    num      -- number of steps, defaults to 50
    endpoint -- if True (default), include the stop value
    retstep  -- if False (default), do not include the calculated step value as part of return tuple
    '''
    if not endpoint:
        step = (stop - start) / (num - 1.)
        stop -= step

    dtype = _getdtypefromobj(((start, stop)))

    if dtype.value < float64.value:
        dtype = float64

    if dtype.value >= complex64.value:
        dtype = complex128

        if type(start) is _types.IntType:
            start = start+0j
        if type(stop) is _types.IntType:
            stop = stop+0j
        rresult = _dsutils.linSpace(start.real, stop.real, num, float64.value)
        iresult = _dsutils.linSpace(start.imag, stop.imag, num, float64.value)
        result = Sciwrap(_complexdoubleds(rresult, iresult))
        del rresult, iresult
    else:
        result = Sciwrap(_dsutils.linSpace(start, stop, num, dtype.value))

    if retstep:
        step = result[1] - result[0]
        return (step, result)
    else:
        return result

@_ndwrapped
def logspace(start, stop, num=50, endpoint=True, base=10.0):
    '''Create a 1D dataset of values equally spaced on a logarithmic scale'''
    if complex(start).imag == 0 and complex(stop).imag == 0:
        return _dsutils.logSpace(start, stop, num, base, endpoint)
    else:
        result = linspace(start, stop, num, endpoint)
        return _maths.power(base, result)

@_ndwrapped
def eye(N, M=None, k=0, dtype=float64):
    if M is None:
        M = N

    dtype = _translatenativetype(dtype)
    return _dsutils.eye(N, M, k, dtype.value)

def identity(n, dtype=float64):
    return eye(n,n,0,dtype)

@_ndwrapped
def diag(v, k=0):
    x = asDataset(v)
    return _dsutils.diag(x, k)

@_ndwrapped
def diagflat(v, k=0):
    x = asDataset(v).flatten()
    return _dsutils.diag(x, k)

def take(a, indices, axis=None):
    return a.take(indices, axis)

@_ndwrapped
def put(a, indices, values):
    return a.put(indices, values)

@_ndwrapped
def concatenate(a, axis=0):
    return _dsutils.concatenate(toList(a), axis)

@_ndwrapped
def vstack(tup):
    return _dsutils.concatenate(toList(tup), 0)

@_ndwrapped
def hstack(tup):
    return _dsutils.concatenate(toList(tup), 1)

@_ndwrapped
def dstack(tup):
    return _dsutils.concatenate(toList(tup), 2)

@_ndwrapped
def split(ary, indices_or_sections, axis=0):
    return _dsutils.split(ary, indices_or_sections, axis, True)

@_ndwrapped
def array_split(ary, indices_or_sections, axis=0):
    return _dsutils.split(ary, indices_or_sections, axis, False)

def vsplit(ary, indices_or_sections):
    return split(ary, indices_or_sections, 0)

def hsplit(ary, indices_or_sections):
    return split(ary, indices_or_sections, 1)

def dsplit(ary, indices_or_sections):
    return split(ary, indices_or_sections, 2)

@_ndwrapped
def sort(a, axis=-1):
    return _dsutils.sort(a, axis)

@_ndwrapped
def tile(a, reps):
    return _dsutils.tile(a, asIterable(reps))

@_ndwrapped
def repeat(a, repeats, axis=-1):
    return _dsutils.repeat(a, asIterable(repeats), axis)

@_ndwrapped
def cast(a, dtype):
    return _dsutils.cast(a, dtype.value)

def squeeze(a):
    a.squeeze()
    return a

@_ndwrapped
def transpose(a, axes=None):
    if axes is None:
        axes = ()
    return _dsutils.transpose(a, asIterable(axes))

@_ndwrapped
def swapaxes(a, axis1, axis2):
    return _dsutils.swapAxes(a, axis1, axis2)

def amax(a, axis=None):
    return a.max(axis)

def amin(a, axis=None):
    return a.min(axis)

def nanmax(a, axis=None):
    return a.max(axis, True)

def nanmin(a, axis=None):
    return a.min(axis, True)

def argmax(a, axis=None):
    return a.argmax(axis)

def argmin(a, axis=None):
    return a.argmin(axis)

def nanargmax(a, axis=None):
    return a.argmax(axis, True)

def nanargmin(a, axis=None):
    return a.argmin(axis, True)

@_ndwrapped
def maximum(a, b):
    return _dsutils.maximum(a, b)

@_ndwrapped
def minimum(a, b):
    return _dsutils.minimum(a, b)

def meshgrid(*a):
    axes = [ asDataset(x) for x in reversed(a) ]
    coords = _dsutils.meshGrid(axes)
    return tuple([ Sciwrap(x) for x in reversed(coords) ])

@_ndwrapped
def indices(dimensions, dtype=int32):
    ind = _dsutils.indices(asIterable(dimensions))
    dtype = _translatenativetype(dtype)
    if dtype != int32:
        ind = _dsutils.cast(ind, dtype.value)
    return ind

@_ndwrapped
def compoundarray(a, view=True):
    '''Create a compound array from an nd array by grouping last axis items into compound items
    '''
    return _dsutils.createCompoundDatasetFromLastAxis(a, view)

@_ndwrapped
def append(arr, values, axis=None):
    '''Append values to end of array
    Keyword argument:
    axis -- if None, then append flattened values to flattened array 
    '''
    v = array(values)
    if axis is None:
        return _dsutils.append(arr.flatten(), v.flatten(), 0)
    return _dsutils.append(arr, v, axis)
