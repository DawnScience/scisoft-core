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

import uk.ac.diamond.scisoft.analysis.dataset.ADataset as _ads
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset as _abstractds
import uk.ac.diamond.scisoft.analysis.dataset.AbstractCompoundDataset as _abscompoundds

import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset as _booleands
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset as _integerds
import uk.ac.diamond.scisoft.analysis.dataset.RGBDataset as _rgbds
import uk.ac.diamond.scisoft.analysis.dataset.ComplexDoubleDataset as _complexdoubleds

import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils as _dsutils
from uk.ac.diamond.scisoft.python.PythonUtils import convertToJava as _cvt2j
from uk.ac.diamond.scisoft.python.PythonUtils import getSlice as _getslice
from uk.ac.diamond.scisoft.python.PythonUtils import setSlice as _setslice

import org.apache.commons.math3.complex.Complex as _jcomplex #@UnresolvedImport

import Jama.Matrix as _matrix #@UnresolvedImport

import types as _types

import java.lang.ArrayIndexOutOfBoundsException as _jarrayindex_exception #@UnresolvedImport
import java.lang.IllegalArgumentException as _jillegalargument_exception #@UnresolvedImport

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

bool = _dtype(_ads.BOOL, name='bool') #@ReservedAssignment
int8 = _dtype(_ads.INT8, name='int8')
int16 = _dtype(_ads.INT16, name='int16')
int32 = _dtype(_ads.INT32, name='int32')
int64 = _dtype(_ads.INT64, name='int64')
cint8 = lambda e : _dtype(_ads.ARRAYINT8, e, 'cint8')
cint16 = lambda e : _dtype(_ads.ARRAYINT16, e, 'cint16')
cint32 = lambda e : _dtype(_ads.ARRAYINT32, e, 'cint32')
cint64 = lambda e : _dtype(_ads.ARRAYINT64, e, 'cint64')
float32 = _dtype(_ads.FLOAT32, name='float32')
float64 = _dtype(_ads.FLOAT64, name='float64')
cfloat32 = lambda e : _dtype(_ads.ARRAYFLOAT32, e, 'cfloat32')
cfloat64 = lambda e : _dtype(_ads.ARRAYFLOAT64, e, 'cfloat64')
complex64 = _dtype(_ads.COMPLEX64, name='complex64')
complex128 = _dtype(_ads.COMPLEX128, name='complex128')

rgb = _dtype(_ads.RGB, 3, 'rgb')

# tuple of floating point types
_floattype = (_ads.FLOAT32, _ads.FLOAT64, _ads.ARRAYFLOAT32, _ads.ARRAYFLOAT64)

# dictionaries to map from Java dataset types to Jython types
__jdtype2jytype = { _ads.BOOL : bool, _ads.INT8 : int8, _ads.INT16 : int16,
                    _ads.INT32 : int32, _ads.INT64 : int64,
                    _ads.FLOAT32 : float32, _ads.FLOAT64 : float64,
                    _ads.COMPLEX64 : complex64, _ads.COMPLEX128 : complex128,
                    _ads.RGB : rgb }

__jcdtype2jytype = { _ads.ARRAYINT8 : cint8, _ads.ARRAYINT16 : cint16,
                    _ads.ARRAYINT32 : cint32, _ads.ARRAYINT64 : cint64,
                    _ads.ARRAYFLOAT32 : cfloat32, _ads.ARRAYFLOAT64 : cfloat64 }

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
int_ = int32 # TODO should be 64 for 64-bit OS
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
    if isinstance(a, _jcomplex): # convert to complex
        return complex(a.getReal(), a.getImaginary())
    if isinstance(a, ndarray):
        return a
    if isinstance(a, _rgbds):
        return ndarrayRGB(buffer=a)
    if isinstance(a, _abstractds):
        return ndarray(buffer=a)
    return a

def _jinput(arg): # strip for java input
    if type(arg) is _types.DictType:
        d = dict()
        for k,v in arg.items():
            d[_jinput(k)] = _jinput(v)
        return d
    elif type(arg) is _types.ListType:
        return [ _jinput(a) for a in arg ]
    elif type(arg) is _types.TupleType:
        return tuple([ _jinput(a) for a in arg ])
    elif isinstance(arg, _jlist):
        return [ _jinput(a) for a in arg ]
    elif type(arg) is _arraytype:
        return [ _jinput(a) for a in arg if a is not None]
    elif isinstance(arg, ndarray):
        return arg._jdataset()
    return arg

from decorator import decorator as _decorator

def _joutput(result): # wrap java output
    if type(result) is _types.ListType:
        return [ Sciwrap(r) for r in result ]
    elif type(result) is _types.TupleType:
        return tuple([ Sciwrap(r) for r in result ])
    elif isinstance(result, _jlist):
        return [ Sciwrap(r) for r in result ]
    elif type(result) is _arraytype:
        return [ Sciwrap(r) for r in result if r is not None ]
    return Sciwrap(result)

@_decorator
def _wrap(func, *args, **kwargs): # strip input and wrap output
    nargs = [ _jinput(a) for a in args ]
    nkwargs = dict()
    for k,v in kwargs.iteritems():
        nkwargs[k] = _jinput(v)

    return _joutput(func(*nargs, **nkwargs))

@_decorator
def _wrapin(func, *args, **kwargs): # strip input
    nargs = [ _jinput(a) for a in args ]
    nkwargs = dict()
    for k,v in kwargs.iteritems():
        nkwargs[k] = _jinput(v)

    return func(*nargs, **nkwargs)

@_decorator
def _wrapout(func, *args, **kwargs): # wrap output only
    return _joutput(func(*args, **kwargs))

def asIterable(items):
    """
    Ensure entity is an iterable by making it a tuple if not
    """
    t = type(items)
    if t is _types.ListType or t is _types.TupleType or t is _arraytype:
        pass
    elif t is _types.DictType or t is _jmap:
        items = [ i for i in items.items() ]
    elif isinstance(items, _jlist):
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
    '''Convert from a Dataset'''
    if isinstance(data, _abstractds):
        return Sciwrap(data)
    return data

def asDataset(data, dtype=None, force=False):
    """
    Used for arithmetic ops to coerce a sequence to a dataset otherwise leave as single item
    """
#    if isinstance(data, _dataset):
#        return Sciwrap(_dataset.convertToDoubleDataset())
    if isinstance(data, ndarray):
        return data

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

    return ndarray(buffer=data, dtype=dtype, copy=False)

def asarray(data, dtype=None):
    return asDataset(data, dtype=dtype, force=True)

asanyarray = asarray

@_wrap
def asfarray(data, dtype=None):
    jdata = __cvt_jobj(data, copy=False, force=True)
    if jdata.isComplex():
        raise TypeError, 'can\'t convert complex to float'
    if jdata.hasFloatingPointElements():
        return jdata

    dt = _getdtypefromjdataset(jdata)
    if dtype is not None:
        dtype = _translatenativetype(dtype)
    if dtype is None or dtype.value not in _floattype:
        if dt.elements == 1:
            return jdata.cast(_ads.FLOAT64)
        return jdata.cast(_ads.ARRAYFLOAT64)
    return jdata.cast(dtype.value)

def asDatasetList(dslist):
    """
    Used to coerce a list of Datasets to a list of datasets
    """
    return [ fromDS(d) for d in asIterable(dslist) ]

def asDatasetDict(dsdict):
    """
    Used to coerce a dictionary of Datasets to a dictionary of datasets
    """
    rdict = {}
    for k in dsdict:
        rdict[k] = fromDS(dsdict[k])
    return rdict

def _isslice(rank, shape, key):
#    print rank, shape, key
    if rank > 0:
        key = asIterable(key)

        if len(key) < rank:
            return True
        elif len(key) > rank:
            raise IndexError, "Too many indices"
    else:
        if key is Ellipsis:
            return True

    for k in key:
        if isinstance(k, slice) or k is Ellipsis:
            return True
    return False

def __cvt_jobj(obj, dtype=None, copy=True, force=False):
    '''Convert object to java object'''
    if isinstance(obj, ndarray):
        obj = obj._jdataset()

    if isinstance(obj, _abstractds):
        if copy:
            if dtype is None or _translatenativetype(dtype).value == obj.dtype:
                return obj.clone()
            else:
                return obj.cast(_translatenativetype(dtype).value)
        else:
            if dtype is None:
                return obj
            return obj.cast(_translatenativetype(dtype).value)

    if not isinstance(obj, list):
        if isinstance(obj, _matrix): # cope with JAMA matrices
            if dtype is None:
                dtype = float64
            obj = obj.getArray()

    obj = _cvt2j(obj)
    try:
        iter(obj)
    except:
        if not force:
            if isinstance(obj, complex):
                return _jcomplex(obj.real, obj.imag)
            return obj

    if dtype is None:
        dtype = _getdtypefromobj(obj)
    else:
        dtype = _translatenativetype(dtype)

    return _abstractds.array(obj, dtype.value)

# prevent incorrect coercion of Python booleans causing trouble with overloaded Java methods
import java.lang.Boolean as _jbool #@UnresolvedImport
_jtrue = _jbool(1)
_jfalse = _jbool(0)

import jymaths as _maths
import jycomparisons as _cmps

class ndarray(object):
    """
    Class to hold special methods and non-overloading names
    """

    def __str__(self):
        return self.__dataset.toString(True)

    def __repr__(self):
        dt = _getdtypefromjdataset(self.__dataset)
        if dt is int_ or dt is float_ or dt is complex_:
            return 'array(' + self.__dataset.toString(True) + ')'
        return 'array(' + self.__dataset.toString(True) + ', dtype=%s)' % (dt,)
        return self.__dataset.toString(True)

    def __init__(self, shape=None, dtype=None, buffer=None, copy=False):
        # check what buffer is and convert if necessary
        if buffer is not None:
            self.__dataset = __cvt_jobj(_jinput(buffer), dtype=dtype, copy=copy, force=True)
            if shape is not None:
                self.__dataset.setShape(asIterable(shape))
        else:
            dtype = _translatenativetype(dtype)
            self.__dataset = _abstractds.zeros(dtype.elements, asIterable(shape), dtype.value)

    def _jdataset(self): # private access to Java dataset class
        return self.__dataset

    # arithmetic operators
    def __add__(self, o):
        return _maths.add(self, asDataset(o))
    def __radd__(self, o):
        return _maths.add(self, asDataset(o))
    def __iadd__(self, o):
        self.__dataset.iadd(__cvt_jobj(o, dtype=self.dtype, copy=False))
        return self

    def __sub__(self, o):
        return _maths.subtract(self, asDataset(o))
    def __rsub__(self, o):
        return _maths.subtract(asDataset(o), self)
    def __isub__(self, o):
        self.__dataset.isubtract(__cvt_jobj(o, dtype=self.dtype, copy=False))
        return self

    def __mul__(self, o):
        return _maths.multiply(self, asDataset(o))
    def __rmul__(self, o):
        return _maths.multiply(self, asDataset(o))
    def __imul__(self, o):
        self.__dataset.imultiply(__cvt_jobj(o, dtype=self.dtype, copy=False))
        return self

    def __div__(self, o):
        return _maths.divide(self, asDataset(o))
    def __rdiv__(self, o):
        return _maths.divide(asDataset(o), self)
    def __idiv__(self, o):
        self.__dataset.idivide(__cvt_jobj(o, dtype=self.dtype, copy=False))
        return self

    def __truediv__(self, o):
        return _maths.divide(self, asDataset(o))
    def __itruediv__(self, o):
        self.__dataset.idivide(__cvt_jobj(o, dtype=self.dtype, copy=False))
        return self

    def __floordiv__(self, o):
        return _maths.floor_divide(self, asDataset(o))
    def __ifloordiv__(self, o):
        self.__dataset.ifloordivide(__cvt_jobj(o, dtype=self.dtype, copy=False))
        return self

    def __mod__(self, o):
        return _maths.remainder(self, asDataset(o))
    def __imod__(self, o):
        self.__dataset.iremainder(__cvt_jobj(o, dtype=self.dtype, copy=False))
        return self

    def __neg__(self):
        return _maths.negative(self)
    def __pos__(self):
        return self
    def __pow__(self, o):
        return _maths.power(self, asDataset(o))
    def __ipow__(self, o):
        self.__dataset.ipower(__cvt_jobj(o, dtype=self.dtype, copy=False))
        return self

    # comparison operators
    def __eq__(self, o):
        e = _cmps.equal(self.__dataset, asDataset(o, force=True)._jdataset())
        if self.size == 1:
            return e._jdataset().getBoolean([])
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

    @_wrapout
    def __copy__(self):
        return self.__dataset.clone()

    def _toslice(self, key):
        '''Transform key to proper slice if necessary
        '''
        rank = self.ndim
        if rank == 1:
            if isinstance(key, list) and len(key) == 1:
                key = key[0]
            if isinstance(key, slice) or key is Ellipsis:
                return True, key
            if isinstance(key, tuple):
                if len(key) > 1:
                    raise IndexError, "too many indices"
                return False, key
            return False, (key,)

        if _isslice(rank, self.shape, key):
            return True, key
        return False, key

    @_wrapout
    def __getitem__(self, key):
        if isinstance(key, ndarray):
            key = key._jdataset()
            if isinstance(key, _booleands):
                return self.__dataset.getByBoolean(key)
            if isinstance(key, _integerds):
                return self.__dataset.getByIndex(key)
# FIXME add integers indexing
        isslice, key = self._toslice(key)
        try:
            if isslice:
                return _getslice(self.__dataset, key)
            return self.__dataset.getObject(key)
        except _jarrayindex_exception:
            raise IndexError

    def __setitem__(self, key, value):
        value = fromDS(value)
        if isinstance(value, ndarray):
            value = value._jdataset()

        if isinstance(key, ndarray):
            key = key._jdataset()
            if isinstance(key, _booleands):
                return self.__dataset.setByBoolean(value, key)
            if isinstance(key, _integerds): #FIXME
                return self.__dataset.setByIndex(value, key)

        isslice, key = self._toslice(key)
        try:
            if isslice:
                _setslice(self.__dataset, value, key)
                return self
            value = _cvt2j(value)
            return self.__dataset.set(value, key)
        except _jarrayindex_exception:
            raise IndexError

    def __iter__(self):
        def ndgen(d):
            r = d.getRank()
            if r <= 1:
                iterator = d.getIterator()
                while iterator.hasNext():
                    yield d.getObjectAbs(iterator.index)
            else:
                axes = range(1, r)
                iterator = d.getPositionIterator(axes)
                pos = iterator.getPos()
                hit = iterator.getOmit()
                while iterator.hasNext():
                    yield _joutput(d.getSlice(d.getSliceIteratorFromAxes(pos, hit)))
        return ndgen(self.__dataset)

    def conj(self):
        return _maths.conj(self)

    @_wrapout
    def ravel(self):
        return self.__dataset.flatten()

    @_wrapout
    def sum(self, axis=None, dtype=None): #@ReservedAssignment
        if dtype is None:
            dtval = self.__dataset.getDtype()
        else:
            dtval = _translatenativetype(dtype).value
        if axis is None:
            return self.__dataset.typedSum(dtval)
        else:
            return self.__dataset.typedSum(dtval, axis)

    @_wrapout
    def prod(self, axis=None, dtype=None):
        if dtype is None:
            dtval = self.__dataset.getDtype()
        else:
            dtval = _translatenativetype(dtype).value
        if axis is None:
            return self.__dataset.typedProduct(dtval)
        else:
            return self.__dataset.typedProduct(dtval, axis)

    @_wrapout
    def var(self, axis=None, ddof=0):
        if ddof == 1:
            if axis is None:
                return self.__dataset.variance()
            else:
                return self.__dataset.variance(axis)
        else:
            if axis is None:
                v = self.__dataset.variance()
                n = self.__dataset.count()
            else:
                v = Sciwrap(self.__dataset.variance(axis))
                n = Sciwrap(self.__dataset.count(axis))
            f = (n - 1.)/(n - ddof)
            return v * f

    @_wrapout
    def std(self, axis=None, ddof=0):
        if ddof == 1:
            if axis is None:
                return self.__dataset.stdDeviation()
            else:
                return self.__dataset.stdDeviation(axis)
        else:
            if axis is None:
                s = self.__dataset.stdDeviation()
                n = self.__dataset.count()
            else:
                s = Sciwrap(self.__dataset.stdDeviation(axis))
                n = Sciwrap(self.__dataset.count(axis))
            import math as _mm
            f = _mm.sqrt((n - 1.)/(n - ddof))
            return s * f

    @_wrapout
    def rms(self, axis=None):
        if axis is None:
            return self.__dataset.rootMeanSquare()
        else:
            return self.__dataset.rootMeanSquare(axis)

    @_wrapout
    def ptp(self, axis=None):
        if axis is None:
            return self.__dataset.peakToPeak()
        else:
            return self.__dataset.peakToPeak(axis)

    def clip(self, a_min, a_max):
        return _maths.clip(self, a_min, a_max)

    @_wrapout
    def argmax(self, axis=None, ignore_nans=False):
        if axis is None:
            if ignore_nans:
                return self.__dataset.argMax(_jtrue)
            return self.__dataset.argMax()
        else:
            if ignore_nans:
                return self.__dataset.argMax(_jtrue, axis)
            return self.__dataset.argMax(axis)

    @_wrapout
    def argmin(self, axis=None, ignore_nans=False):
        if axis is None:
            if ignore_nans:
                return self.__dataset.argMin(_jtrue)
            return self.__dataset.argMin()
        else:
            if ignore_nans:
                return self.__dataset.argMin(_jtrue, axis)
            return self.__dataset.argMin(axis)

    def maxpos(self, ignore_nans=False):
        '''Return position of first maxima'''
        if ignore_nans:
            return self.__dataset.maxPos(True)
        return self.__dataset.maxPos()

    def minpos(self, ignore_nans=False):
        '''Return position of first minima'''
        if ignore_nans:
            return self.__dataset.minPos(True)
        return self.__dataset.minPos()

    # properties
    @property
    @_wrapout
    def transpose(self):
        return self.__dataset.transpose()

    @property
    @_wrapout
    def view(self, cls=None):
        '''Return a view of dataset'''
        if cls is None or cls == self.__class__:
            return self.__dataset.getView()
        else:
            return cast(self, cls.dtype)

    @property
    @_wrapout
    def indices(self):
        '''Return an index dataset'''
        return self.__dataset.getIndices()

    def __get_shape(self):
        return tuple(self.__dataset.getShape())

    def __set_shape(self, *shape):
        if len(shape) == 1:
            shape = asIterable(shape[0])
        self.__dataset.setShape(shape)

    shape = property(__get_shape, __set_shape) # python 2.5 rather than using @shape.setter

    @property
    def dtype(self):
        return _getdtypefromjdataset(self.__dataset)

    @property
    def itemsize(self):
        '''Return number of bytes per item'''
        return self.__dataset.getItemsize()

    @property
    def ndim(self):
        '''Return number of dimensions'''
        return self.__dataset.getRank()

    @property
    def size(self):
        '''Return number of items'''
        return self.__dataset.getSize()

    @property
    @_wrapout
    def real(self):
        return self.__dataset.real()

    @property
    def data(self):
        return self.__dataset.getBuffer()

    def append(self, other, axis=None):
        return append(self, other, axis)

    def item(self, index=None, *args):
        '''Return first item of dataset'''
        if self.size == 1:
            rank = self.ndim
            if index is None:
                if rank > 1:
                    raise ValueError, "incorrect number of indices"
            elif index:
                raise ValueError, "index out of bounds"
            elif rank == 0:
                raise ValueError, "incorrect number of indices"
            if args:
                if (len(args) + 1) > rank:
                    raise ValueError, "incorrect number of indices"
                for a in args:
                    if a:
                        raise ValueError, "index out of bounds"
            r = self.__dataset.getObject([])
        else:
            if index is None:
                raise ValueError, "Need an integer or a tuple of integers"
            try:
                if args:
                    r = self.__dataset.getObject(index, *args)
                else:
                    r = self.__dataset.getObjectAbs(index)
            except (_jarrayindex_exception, _jillegalargument_exception):
                raise ValueError

        if isinstance(r, _jcomplex):
            return complex(r.getReal(), r.getImaginary())
        return r

    def copy(self):
        return ndarray(buffer=self.__dataset, copy=True)

    def fill(self, value):
        self.__dataset.fill(_cvt2j(value))
        return self

    @_wrapout
    def max(self, axis=None, ignore_nans=False): #@ReservedAssignment
        if axis is None:
            if ignore_nans:
                return self.__dataset.max(ignore_nans)
            return self.__dataset.max()
        else:
            return self.__dataset.max(ignore_nans, axis)

    @_wrapout
    def min(self, axis=None, ignore_nans=False): #@ReservedAssignment
        if axis is None:
            if ignore_nans:
                return self.__dataset.min(ignore_nans)
            return self.__dataset.min()
        else:
            return self.__dataset.min(ignore_nans, axis)

    @_wrapout
    def mean(self, axis=None):
        if axis is None:
            return self.__dataset.mean()
        else:
            return self.__dataset.mean(axis)

    def sort(self, axis=-1):
        self.__dataset.sort(axis)

    def put(self, indices, values):
        self.__dataset.put(asIterable(indices), asIterable(values))

    @_wrapout
    def take(self, indices, axis=None):
        return self.__dataset.take(asIterable(indices), axis)

    @_wrapout
    def all(self, axis=None): #@ReservedAssignment
        if axis is None:
            return self.__dataset.all()
        else:
            return self.__dataset.all(axis)

    @_wrapout
    def any(self, axis=None): #@ReservedAssignment
        if axis is None:
            return self.__dataset.any()
        else:
            return self.__dataset.any(axis)

    @_wrapout
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.__dataset.reshape(shape)

    @_wrapout
    def flatten(self):
        '''Return a 1D dataset with same data'''
        return self.__dataset.flatten()

class ndarrayRGB(ndarray):
    """
    Wrap RGB dataset
    """
    def __init__(self, shape=None, dtype=None, buffer=None, copy=False):
        super(ndarrayRGB, self).__init__(shape=shape, dtype=dtype, buffer=buffer, copy=copy)

    @_wrapout
    def get_red(self, dtype=None):
        if dtype is None:
            dtype = int16
        else:
            dtype = _translatenativetype(dtype)
        return self._jdataset().createRedDataset(dtype.value)

    @_wrapout
    def get_green(self, dtype=None):
        if dtype is None:
            dtype = int16
        else:
            dtype = _translatenativetype(dtype)
        return self._jdataset().createGreenDataset(dtype.value)

    @_wrapout
    def get_blue(self, dtype=None):
        if dtype is None:
            dtype = int16
        else:
            dtype = _translatenativetype(dtype)
        return self._jdataset().createBlueDataset(dtype.value)

    @_wrapout
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
            return self._jdataset().createGreyDataset(cweights[0]/csum, cweights[1]/csum, cweights[2]/csum, dtype.value)
        return self._jdataset().createGreyDataset(dtype.value)

    red = property(get_red)
    green = property(get_green)
    blue = property(get_blue)
    grey = property(get_grey)


# map atomic dataset type to compound type
__dtype2cdtype = { int8:cint8, int16:cint16, int32:cint32, int64:cint64,
                  float32:cfloat32, float64:cfloat64 }

@_wrapout
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

def array(obj, dtype=None, copy=True):
    '''Create a dataset of given type from a sequence or JAMA matrix'''
    return ndarray(shape=None, dtype=dtype, buffer=obj, copy=copy)

@_wrapout
def ones(shape, dtype=float64):
    '''Create a dataset filled with 1'''
    dtype = _translatenativetype(dtype)
    return _abstractds.ones(dtype.elements, asIterable(shape), dtype.value)

@_wrapout
def zeros(shape, dtype=float64, elements=None):
    '''Create a dataset filled with 0'''
    dtype = _translatenativetype(dtype)
    if elements is not None:
        if type(dtype) is _types.FunctionType:
            dtype = dtype(elements)
        else:
            dtype.elements = elements
    elif type(dtype) is _types.FunctionType:
        raise ValueError, 'Given data-type is a function and needs elements defining'

    return _abstractds.zeros(dtype.elements, asIterable(shape), dtype.value)

empty = zeros

@_wrap
def zeros_like(a):
    return _abstractds.zeros(a)

empty_like = zeros_like

@_wrap
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

@_wrap
def logspace(start, stop, num=50, endpoint=True, base=10.0):
    '''Create a 1D dataset of values equally spaced on a logarithmic scale'''
    if complex(start).imag == 0 and complex(stop).imag == 0:
        return _dsutils.logSpace(start, stop, num, base, endpoint)
    else:
        result = linspace(start, stop, num, endpoint)
        return _maths.power(base, result)

@_wrap
def eye(N, M=None, k=0, dtype=float64):
    if M is None:
        M = N

    dtype = _translatenativetype(dtype)
    return _dsutils.eye(N, M, k, dtype.value)

def identity(n, dtype=float64):
    return eye(n,n,0,dtype)

@_wrap
def diag(v, k=0):
    x = asDataset(v)._jdataset()
    return _dsutils.diag(x, k)

@_wrap
def diagflat(v, k=0):
    x = asDataset(v).flatten()._jdataset()
    return _dsutils.diag(x, k)

def take(a, indices, axis=None):
    return a.take(indices, axis)

@_wrap
def put(a, indices, values):
    return a.put(indices, values)

@_wrap
def concatenate(a, axis=0):
    return _dsutils.concatenate(toList(a), axis)

@_wrap
def vstack(tup):
    return _dsutils.concatenate(toList(tup), 0)

@_wrap
def hstack(tup):
    return _dsutils.concatenate(toList(tup), 1)

@_wrap
def dstack(tup):
    return _dsutils.concatenate(toList(tup), 2)

@_wrap
def split(ary, indices_or_sections, axis=0):
    return _dsutils.split(ary, indices_or_sections, axis, True)

@_wrap
def array_split(ary, indices_or_sections, axis=0):
    return _dsutils.split(ary, indices_or_sections, axis, False)

def vsplit(ary, indices_or_sections):
    return split(ary, indices_or_sections, 0)

def hsplit(ary, indices_or_sections):
    return split(ary, indices_or_sections, 1)

def dsplit(ary, indices_or_sections):
    return split(ary, indices_or_sections, 2)

@_wrap
def sort(a, axis=-1):
    return _dsutils.sort(a, axis)

@_wrap
def tile(a, reps):
    return _dsutils.tile(a, asIterable(reps))

@_wrap
def repeat(a, repeats, axis=-1):
    return _dsutils.repeat(a, asIterable(repeats), axis)

@_wrap
def cast(a, dtype):
    return _dsutils.cast(a, dtype.value)

def reshape(a, newshape):
    return asDataset(a).reshape(newshape)

def ravel(a):
    return asDataset(a).ravel()

def squeeze(a):
    a.squeeze()
    return a

@_wrap
def transpose(a, axes=None):
    if axes is None:
        axes = ()
    return _dsutils.transpose(a, asIterable(axes))

@_wrap
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

@_wrap
def maximum(a, b):
    return _dsutils.maximum(a, b)

@_wrap
def minimum(a, b):
    return _dsutils.minimum(a, b)

def meshgrid(*a):
    axes = [ asDataset(x) for x in reversed(a) ]
    coords = _dsutils.meshGrid(axes)
    return tuple([ Sciwrap(x) for x in reversed(coords) ])

@_wrap
def indices(dimensions, dtype=int32):
    ind = _dsutils.indices(asIterable(dimensions))
    dtype = _translatenativetype(dtype)
    if dtype != int32:
        ind = _dsutils.cast(ind, dtype.value)
    return ind

@_wrap
def roll(a, shift, axis=None):
    return _dsutils.roll(a, shift, axis)

@_wrap
def rollaxis(a, axis, start=0):
    return _dsutils.rollAxis(a, axis, start)

@_wrap
def compoundarray(a, view=True):
    '''Create a compound array from an nd array by grouping last axis items into compound items
    '''
    return _dsutils.createCompoundDatasetFromLastAxis(a, view)

@_wrap
def append(arr, values, axis=None):
    '''Append values to end of array
    Keyword argument:
    axis -- if None, then append flattened values to flattened array 
    '''
    if not isinstance(values, _abstractds):
        values = __cvt_jobj(values, dtype=None, copy=False, force=True)
    if axis is None:
        return _dsutils.append(arr.flatten(), values.flatten(), 0)
    return _dsutils.append(arr, values, axis)

@_wrap
def nan_to_num(a):
    '''Create a copy with infinities replaced by max/min values and NaNs replaced by 0s
    '''
    c = a.copy()
    _dsutils.removeNansAndInfinities(c)
    return c
