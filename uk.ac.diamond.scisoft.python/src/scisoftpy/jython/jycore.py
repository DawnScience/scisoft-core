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

import org.eclipse.january.dataset.Dataset as _ds
import org.eclipse.january.dataset.LazyDataset as _lds
import org.eclipse.january.dataset.ShapeUtils as _sutils
import org.eclipse.january.dataset.DatasetFactory as _df

import org.eclipse.january.dataset.BooleanDataset as _booleands
import org.eclipse.january.dataset.ByteDataset as _byteds
import org.eclipse.january.dataset.ShortDataset as _shortds
import org.eclipse.january.dataset.IntegerDataset as _integerds
import org.eclipse.january.dataset.LongDataset as _longds
import org.eclipse.january.dataset.CompoundByteDataset as _cpdbyteds
import org.eclipse.january.dataset.CompoundShortDataset as _cpdshortds
import org.eclipse.january.dataset.CompoundIntegerDataset as _cpdintegerds
import org.eclipse.january.dataset.CompoundLongDataset as _cpdlongds
import org.eclipse.january.dataset.RGBDataset as _rgbds
import org.eclipse.january.dataset.FloatDataset as _floatds
import org.eclipse.january.dataset.DoubleDataset as _doubleds
import org.eclipse.january.dataset.ComplexFloatDataset as _cpxfloatds
import org.eclipse.january.dataset.ComplexDoubleDataset as _cpxdoubleds
import org.eclipse.january.dataset.CompoundFloatDataset as _cpdfloatds
import org.eclipse.january.dataset.CompoundDoubleDataset as _cpddoubleds
import org.eclipse.january.dataset.DateDataset as _dateds
import org.eclipse.january.dataset.StringDataset as _stringds

import org.eclipse.january.dataset.DatasetUtils as _dsutils
import org.eclipse.january.dataset.InterfaceUtils as _ifutils
from uk.ac.diamond.scisoft.python.PythonUtils import convertToJava as _cvt2j
from uk.ac.diamond.scisoft.python.PythonUtils import getSlice as _getslice
from uk.ac.diamond.scisoft.python.PythonUtils import setSlice as _setslice
from uk.ac.diamond.scisoft.python.PythonUtils import convertToSlice as _cvt2js
from uk.ac.diamond.scisoft.python.PythonUtils import getInterfaceFromObject as _getdt
from uk.ac.diamond.scisoft.python.PythonUtils import createFromObject as _create

import org.apache.commons.math3.complex.Complex as _jcomplex #@UnresolvedImport

import Jama.Matrix as _matrix #@UnresolvedImport

import inspect

import java.lang.ArrayIndexOutOfBoundsException as _jarrayindex_exception #@UnresolvedImport
import java.lang.IllegalArgumentException as _jillegalargument_exception #@UnresolvedImport

from functools import wraps

def quieten_logging(netty_only=True):
    '''
    Quieten logging to warnings and above
    
    :param netty_only: True then only quieten messages from Netty's DefaultChannelPipeline
    '''
    try:
        if netty_only:
            logger_name = 'org.python.netty.channel.DefaultChannelPipeline'
        else:
            from org.slf4j import Logger as LGR
            logger_name = LGR.ROOT_LOGGER_NAME
        from org.slf4j import LoggerFactory as LGF
        # assumes we are using logback
        from ch.qos.logback.classic import Level as LBL
        LGF.getLogger(logger_name).setLevel(LBL.WARN)
    except:
        pass

quieten_logging()

class ndgeneric(object):
    pass # there is no array scalars at the moment

generic = ndgeneric

newaxis = None

class _dtype(object):
    '''
    Dataset type has two properties:

    value = Java dataset interface
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

bool = _dtype(_booleands, name='bool') #@ReservedAssignment
int8 = _dtype(_byteds, name='int8')
int16 = _dtype(_shortds, name='int16')
int32 = _dtype(_integerds, name='int32')
int64 = _dtype(_longds, name='int64')
cint8 = lambda e : _dtype(_cpdbyteds, e, 'cint8')
cint16 = lambda e : _dtype(_cpdshortds, e, 'cint16')
cint32 = lambda e : _dtype(_cpdintegerds, e, 'cint32')
cint64 = lambda e : _dtype(_cpdlongds, e, 'cint64')
float32 = _dtype(_floatds, name='float32')
float64 = _dtype(_doubleds, name='float64')
cfloat32 = lambda e : _dtype(_cpdfloatds, e, 'cfloat32')
cfloat64 = lambda e : _dtype(_cpddoubleds, e, 'cfloat64')
complex64 = _dtype(_cpxfloatds, name='complex64')
complex128 = _dtype(_cpxdoubleds, name='complex128')
string = _dtype(_stringds, name='S')
rgb = _dtype(_rgbds, 3, 'rgb')

def _populate_dtype_maps(*types):
    d = {}
    for t in types:
        d[t.value] = t
    return d

# dictionaries to map from Java dataset types to Jython types
__jdtype2jytype = _populate_dtype_maps(bool, int8, int16, int32, int64, float32, float64, complex64, complex128, string, rgb)

__jcdtype2jytype = {_cpdbyteds:cint8, _cpdshortds:cint16, _cpdintegerds:cint32, _cpdlongds:cint64, _cpdfloatds: cfloat32, _cpddoubleds:cfloat64}
_compound_dtypes = (cint8, cint16, cint32, cint64, cfloat32, cfloat64)

# get dtype from object
def _getdtypefromobj(jobj):
    jdtype = _getdt(jobj)
    if jdtype in __jdtype2jytype:
        return __jdtype2jytype[jdtype]
    raise ValueError("Java dataset type unknown")

# get dtype from Java dataset
def _getdtypefromjdataset(jobj):
    d = _ifutils.getInterface(jobj)
    if d in __jdtype2jytype:
        return __jdtype2jytype[d]
    if d in __jcdtype2jytype:
        return __jcdtype2jytype[d](jobj.getElementsPerItem())
    raise ValueError("Java dataset type unknown")

# check for native python type
def _translatenativetype(dtype):
    if dtype is None:
        return None
    elif isinstance(dtype, _dtype) or dtype in _compound_dtypes:
        return dtype
    elif dtype is int:
        return int32
    elif dtype is float:
        return float64
    elif dtype is complex:
        return complex128
    raise ValueError("Dataset type is not recognised")

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
from jarray import zeros as _zeros
_empty_boolean_array = _zeros(0, 'z') # this is used for resolving some overloaded methods

import java.util.List as _jlist #@UnresolvedImport
import java.util.Map as _jmap #@UnresolvedImport

from scisoftpy.dictutils import ListDict as _ldict

def Sciwrap(a):
    '''
    This wrapper function is required for any Java method that returns a dataset
    '''
    if a is None:
        raise ValueError("No value given")
    if isinstance(a, _jcomplex): # convert to complex
        return complex(a.getReal(), a.getImaginary())
    if isinstance(a, ndarray):
        return a
    if isinstance(a, _rgbds):
        return ndarrayRGB(buffer=a)
    if isinstance(a, _ds):
        return ndarray(buffer=a)
    return a

def _jinput(arg): # strip for java input
    if isinstance(arg, dict):
        d = dict()
        for k,v in arg.items():
            d[_jinput(k)] = _jinput(v)
        return d
    elif isinstance(arg, list):
        return [ _jinput(a) for a in arg ]
    elif isinstance(arg, tuple):
        return tuple([ _jinput(a) for a in arg ])
    elif isinstance(arg, _jlist):
        return [ _jinput(a) for a in arg ]
    elif type(arg) is _arraytype:
        if len(arg.typecode) == 1: # only check non-primitives
            return arg
        return [ _jinput(a) for a in arg if a is not None]
    elif isinstance(arg, ndarray):
        return arg._jdataset()
    elif isinstance(arg, lazyarray):
        return None # no equivalent at the moment
    elif isinstance(arg, complex):
        return _jcomplex(arg.real, arg.imag)

    return arg

def _joutput(result): # wrap java output
    if isinstance(result, (list, tuple, _jlist)):
        return tuple([ Sciwrap(r) for r in result ])
    elif type(result) is _arraytype:
        return [ Sciwrap(r) for r in result if r is not None ]
    return Sciwrap(result)

def _convertjArgs(newargs, newkwargs):
    nargs = [ _jinput(a) for a in newargs ]
    nkwargs = dict()
    for k,v in newkwargs.iteritems():
        nkwargs[k] = _jinput(v)
    return nargs, nkwargs

def _checkArgnames(argnames, args_to_convert):
    for inputname in args_to_convert:
        if not inputname in argnames:
            raise ValueError('Input name %s is not an argument of the function' % str(inputname))

def _convertArgsToArray(argnames, args_to_convert, args, kwargs):
    newkwargs = {}
    newargs = [array(a) if (n in args_to_convert and not isinstance(a, ndarray)) else a for a, n in zip(args, argnames)]
    for k, v in kwargs.iteritems():
        newkwargs[k] = array(v) if (k in args_to_convert and not isinstance(v, ndarray)) else v
    return newargs, newkwargs

def _wrap(*args_to_convert):
    '''
    Ensure selected arguments are ndarrays by converting to ndarray if they are array_like
    '''
    if (len(args_to_convert) == 1 and callable(args_to_convert[0])):
        def decorator(f):
            @wraps(f)
            def new_f(*args, **kwargs):
                nargs, nkwargs = _convertjArgs(args, kwargs)
                return _joutput(f(*nargs, **nkwargs))
            return new_f
        return decorator(args_to_convert[0])
    def decorator(f):
        argnames = inspect.getargspec(f).args
        _checkArgnames(argnames, args_to_convert)
        @wraps(f)
        def new_f(*args, **kwargs):
            newargs, newkwargs = _convertArgsToArray(argnames, args_to_convert, args, kwargs)
            nargs, nkwargs = _convertjArgs(newargs, newkwargs)
            return _joutput(f(*nargs, **nkwargs))
        return new_f
    return decorator

def _wrapin(*args_to_convert):
    '''
    Ensure selected arguments are ndarrays by converting to ndarray if they are array_like
    '''
    if (len(args_to_convert) == 1 and callable(args_to_convert[0])):
        def decorator(f):
            @wraps(f)
            def new_f(*args, **kwargs):
                nargs, nkwargs = _convertjArgs(args, kwargs)
                return f(*nargs, **nkwargs)
            return new_f
        return decorator(args_to_convert[0])
    def decorator(f):
        argnames = inspect.getargspec(f).args
        _checkArgnames(argnames, args_to_convert)
        @wraps(f)
        def new_f(*args, **kwargs):
            newargs, newkwargs = _convertArgsToArray(argnames, args_to_convert, args, kwargs)
            nargs, nkwargs = _convertjArgs(newargs, newkwargs)
            return f(*nargs, **nkwargs)
        return new_f
    return decorator

def _wrapout(*args_to_convert): # wrap output only
    '''
    Ensure selected arguments are ndarrays by converting to ndarray if they are array_like
    '''
    if (len(args_to_convert) == 1 and callable(args_to_convert[0])):
        def decorator(f):
            @wraps(f)
            def new_f(*args, **kwargs):
                return _joutput(f(*args, **kwargs))
            return new_f
        return decorator(args_to_convert[0])
    def decorator(f):
        argnames = inspect.getargspec(f).args
        _checkArgnames(argnames, args_to_convert)
        @wraps(f)
        def new_f(*args, **kwargs):
            newargs, newkwargs = _convertArgsToArray(argnames, args_to_convert, args, kwargs)
            return _joutput(f(*newargs, **newkwargs))
        return new_f
    return decorator

def _argsToArrayType(*args_to_convert):
    '''
    Ensure selected arguments are ndarrays by converting to ndarray if they are array_like
    '''
    if (len(args_to_convert) == 1 and callable(args_to_convert[0])):
        def decorator(f):
            @wraps(f)
            def new_f(*args, **kwargs):
                return f(*args, **kwargs)
            return new_f
        return decorator(args_to_convert[0])
    def decorator(f):
        argnames = inspect.getargspec(f).args
        _checkArgnames(argnames, args_to_convert)
        @wraps(f)
        def new_f(*args, **kwargs):
            newargs, newkwargs = _convertArgsToArray(argnames, args_to_convert, args, kwargs)
            return f(*newargs, **newkwargs)
        return new_f
    return decorator

def asIterable(items):
    '''
    Ensure entity is an iterable by making it a tuple if not
    '''
    if isinstance(items, (list, tuple)) or type(items) is _arraytype:
        pass
    elif isinstance(items, (dict, _jmap)):
        items = [ i for i in items.items() ]
    elif isinstance(items, _jlist):
        pass
    else: # isinstance(items, _ds):
        items = (items,)
    return items

def _as_int_array(items): # get integer array or list
    
    if isinstance(items, ndarray):
        items = items._jdataset()
    if isinstance(items, _ds):
        if not _ifutils.isInteger(items.getClass()):
            raise TypeError("Shape must be an integer array or list")
        if items.getRank() > 1:
            raise TypeError("Shape must not be an array with rank > 1")
        return items.getBuffer()

    return asIterable(items)

def toList(listdata):
    '''Convert a list or tuple to list of datasets'''
    return [ d for d in asIterable(listdata) ]

def scalarToPython(ascalar):
    '''Convert an array scalar to a python type
    '''
    return ascalar # there is no array scalars at the moment

def fromDS(data):
    '''Convert from a Dataset'''
    if isinstance(data, _ds):
        return Sciwrap(data)
    if isinstance(data, _lds):
        return lazyarray(data)
    return data

def asDataset(data, dtype=None, force=False):
    '''
    Used for arithmetic ops to coerce a sequence to a dataset otherwise leave as single item
    '''
    if isinstance(data, ndarray):
        dtype = _translatenativetype(dtype)
        if dtype is None or dtype == data.dtype:
            return data
        return ndarray(buffer=data, dtype=dtype, copy=False)

    if isinstance(data, _ds):
        return ndarray(buffer=data, dtype=dtype, copy=False)

    try:
        iter(data)
    except:
        if not force:
            if isinstance(data, complex):
                return _jcomplex(data.real, data.imag)
            return data
    return ndarray(buffer=data, dtype=dtype, copy=False)

def iscomplexobj(x):
    if isinstance(x, ndarray):
        return x.dtype == complex64 or x.dtype == complex128
    return isinstance(x, complex)

def isrealobj(x):
    return not iscomplexobj(x)

@_argsToArrayType('data')
def asarray(data, dtype=None):
    return asDataset(data, dtype=dtype, force=True)

asanyarray = asarray

def _keepdims(func):
    '''
    Retain dimensions of output array
    '''
    @wraps(func)
    def new_f(*args, **kwargs):
        keepdims = kwargs.get('keepdims', False)
        axis = kwargs.get('axis', None)    
        if keepdims:
            if axis is None:
                dimensionality = (1,) * len(args[0].shape)
            elif isinstance(axis, int):
                dimensionality = list(args[0].shape)
                dimensionality[axis] = 1
            elif isinstance(axis, tuple):
                dimensionality = list(args[0].shape)
                for a in axis:
                    dimensionality[a] = 1
            output_array = asarray(func(*args, **kwargs))
            output_array.shape = dimensionality
            return output_array
        else:
            return func(*args, **kwargs)
    return new_f

@_wrap('data')
def asfarray(data, dtype=None):
    jdata = __cvt_jobj(data, copy=False, force=True)
    if jdata.isComplex():
        raise TypeError("can't convert complex to float")
    if jdata.hasFloatingPointElements():
        return jdata

    dt = _getdtypefromjdataset(jdata)
    dtype = _translatenativetype(dtype)
    if dtype is None or not _ifutils.isFloating(dtype.value):
        if dt.elements == 1:
            return jdata.cast(_doubleds)
        return jdata.cast(_cpddoubleds)
    return jdata.cast(dtype.value)

def asDatasetList(dslist):
    '''
    Used to coerce a list of Datasets to a list of datasets
    '''
    return [ fromDS(d) for d in asIterable(dslist) ]

def asDatasetDict(dsdict):
    '''
    Used to coerce a dictionary of Datasets to a dictionary of datasets
    '''
    rdict = {}
    for k in dsdict:
        rdict[k] = fromDS(dsdict[k])
    return rdict

def _toslice(rank, key):
    '''Transform key to proper slice if necessary
    '''
    if rank == 1:
        if isinstance(key, int):
            return False, key
        if isinstance(key, tuple):
            nk = len(key)
            if nk == 1:
                key = key[0]
            elif nk > 1:
                has_slice = False
                for k in key:
                    if isinstance(k, slice):
                        if has_slice:
                            raise IndexError("too many slices")
                        has_slice = True
                    elif k is not Ellipsis and k is not newaxis:
                        return False, key
                return True, key

        if isinstance(key, slice) or key is Ellipsis:
            return True, key
        if isinstance(key, list):
            key = asarray(key)
        if isinstance(key, ndarray):
            key = key._jdataset()
        return False, key

    adv = False
    if isinstance(key, list): # strip any arrays
        adv = True
        key = [k if isinstance(k, slice) else asarray(k)._jdataset() for k in key]
    elif isinstance(key, tuple): # strip any arrays
        nkeys = []
        for k in key:
            if isinstance(k, (tuple, list)):
                k = asarray(k)
            if isinstance(k, ndarray):
                nkeys.append(k._jdataset())
                adv = True
            else:
                nkeys.append(k)
        key = nkeys
    elif isinstance(key, ndarray):
        ds = key._jdataset()
        if ds.getRank() == rank and isinstance(ds, _booleands):
            return False, ds
        return False, (ds,)

    if adv:
        return False, [k if not isinstance(k, slice) else _cvt2js(k) for k in key]
    return _isslice(rank, key), key

def _isslice(rank, key):
    if not isinstance(key, (tuple, list)):
        key = (key,)
    nk = len(key)
    if rank > 0:
        if nk < rank:
            return True
        elif nk > rank and newaxis not in key:
            raise IndexError("Too many indices")
    else:
        if nk > 0:
            if key[0] is Ellipsis:
                return True
            raise ValueError("Cannot slice 0-d dataset")

    for k in key:
        if isinstance(k, slice) or k is Ellipsis or k is newaxis:
            return True
    return False

def _contains_ints_bools_newaxis(sequence):
    if not isinstance(sequence, (tuple, list)):
        return False
    if len(sequence) == 0:
        return False

    for s in sequence:
        if isinstance(s, (_integerds, _booleands)):
            return True

    for s in sequence:
        if s is newaxis:
            return True

    return False

def __cvt_jobj(obj, dtype=None, copy=True, force=False):
    '''Convert object to java object'''
    if isinstance(obj, ndarray):
        obj = obj._jdataset()

    dtype = _translatenativetype(dtype)
    if isinstance(obj, _ds):
        if copy:
            if dtype is None or dtype.value.isAssignableFrom(obj.getClass()):
                return obj.clone()
            else:
                return obj.cast(dtype.value)
        else:
            if dtype is None:
                return obj
            return obj.cast(dtype.value)

    if not isinstance(obj, list):
        if isinstance(obj, _matrix): # cope with JAMA matrices
            if dtype is None:
                dtype = float64
            obj = obj.getArray()
    elif len(obj) == 0 and dtype is None:
        dtype = float64

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

    return _create(dtype.value, obj)

# prevent incorrect coercion of Python booleans causing trouble with overloaded Java methods
import java.lang.Boolean as _jbool #@UnresolvedImport
_jtrue = _jbool(1)
_jfalse = _jbool(0)
import java.lang.Integer as _jint

from . import jymaths as _maths
from . import jycomparisons as _cmps

_jempty = tuple()

class lazyarray(object):
    def __init__(self, dataset, shape=None, dtype=None, maxshape=None, attrs={}, parent=None):
        '''
        '''
        super(lazyarray, self).__setattr__('attrs', _ldict(attrs, lock=True))
        self.__data = dataset

        if shape is None:
            shape = tuple(dataset.getShape())
        self.__shape = shape
        self.rank = len(shape)

        dtype = _translatenativetype(dtype)
        if dtype is None:
            if isinstance(dataset, _lds) or isinstance(dataset, _ds):
                dtype = _getdtypefromjdataset(dataset)
        self.__dtype = dtype

        if maxshape is None:
            maxshape = shape
        self.__maxshape = maxshape

    @property
    def shape(self):
        return self.__shape

    @property
    def maxshape(self):
        '''Maximum shape (-1 indicates an unlimited dimension)
        '''
        return self.__maxshape

    @property
    def dtype(self):
        return self.__dtype

    @_wrapout
    def __getitem__(self, key):
        data = self._getdata()
        if isinstance(data, _lds):
            isslice, key = _toslice(self.rank, key)
            if not isslice: # single item
                key = tuple([ slice(k,k+1) for k in key ])
                v = _getslice(data, key)
                v = v.getAbs(0)
            else:
                v = _getslice(data, key)
            return v

        return asarray(data)[key]

    def __str__(self):
        s = "   @shape = %s\n" % (self.__shape,)
        s += "   @maxshape = %s\n" % (self.__maxshape,)
        for a in self.attrs:
            s += "   @%s = %s\n" % (a, self.attrs[a]) 
        return s

    def _getdata(self):
        return self.__data


class ndarray(object):
    '''
    Class to hold special methods and non-overloading names
    '''
    def __init__(self, shape=None, dtype=None, buffer=None, copy=False): #@ReservedAssignment
        # check what buffer is and convert if necessary
        if buffer is not None:
            self.__dataset = __cvt_jobj(_jinput(buffer), dtype=dtype, copy=copy, force=True)
            if shape is not None:
                self.__dataset.setShape(asIterable(shape))
        else:
            dtype = _translatenativetype(dtype)
            self.__dataset = _df.zeros(dtype.elements, dtype.value, asIterable(shape))

    def _jdataset(self): # private access to Java dataset class
        return self.__dataset

    def __iter__(self):
        def ndgen(d):
            r = d.getRank()
            if r <= 1:
                iterator = d.getIterator()
                while iterator.hasNext():
                    yield d.getObjectAbs(iterator.index)
            else:
                axes = list(range(1, r))
                iterator = d.getPositionIterator(axes)
                pos = iterator.getPos()
                hit = iterator.getOmit()
                shape = self.shape[1:]
                while iterator.hasNext():
                    result = Sciwrap(d.getSlice(d.getSliceIteratorFromAxes(pos, hit)))
                    result.shape = shape
                    yield result
        return ndgen(self.__dataset)

    # attributes
    # MISSING: flags
    def __get_shape(self):
        return tuple(self.__dataset.getShape())

    def __set_shape(self, *shape):
        if len(shape) == 1:
            shape = asIterable(shape[0])
        self.__dataset.setShape(shape)

    shape = property(__get_shape, __set_shape) # python 2.5 rather than using @shape.setter

    # MISSING: strides

    @property
    def ndim(self):
        '''Return number of dimensions'''
        return self.__dataset.getRank()

    @property
    def data(self):
        return self.__dataset.getBuffer()

    @property
    def size(self):
        '''Return number of items'''
        return self.__dataset.getSize()

    @property
    def itemsize(self):
        '''Return number of bytes per item'''
        return self.__dataset.getItemBytes()

    @property
    def nbytes(self):
        '''Return total bytes used by items of array'''
        return self.__dataset.getNbytes()

    @property
    def dtype(self):
        return _getdtypefromjdataset(self.__dataset)

    # MISSING: base

    @property
    def T(self):
        return self.transpose()

    @_wrapout
    def _get_real(self):
        return self.__dataset.getRealView()

    def _set_real(self, value):
        value = fromDS(value)
        if isinstance(value, ndarray):
            value = value._jdataset()
        _setslice(self.__dataset.getRealView(), value, Ellipsis)

    real = property(_get_real, _set_real)

    @_wrapout
    def _get_imag(self):
        if iscomplexobj(self):
            return self.__dataset.getImaginaryView()
        return zeros(self.shape, dtype=self.dtype)

    def _set_imag(self, value):
        if iscomplexobj(self):
            value = fromDS(value)
            if isinstance(value, ndarray):
                value = value._jdataset()
            _setslice(self.__dataset.getImaginaryView(), value, Ellipsis)

    imag = property(_get_imag, _set_imag)

    @property
    def flat(self):
        def ndgen(d):
            iterator = d.getIterator()
            while iterator.hasNext():
                yield d.getObjectAbs(iterator.index)
        return ndgen(self.__dataset)

    # MISSING: ctypes

    # methods
    #  conversion
    def item(self, index=None, *args):
        '''Return first item of dataset'''
        if self.size == 1:
            rank = self.ndim
            if index is None:
                if rank > 1:
                    raise ValueError("incorrect number of indices")
            elif index:
                if args:
                    raise ValueError("incorrect number of indices")
                raise IndexError("index out of bounds")
            if args:
                if (len(args) + 1) > rank:
                    raise ValueError("incorrect number of indices")
                for a in args:
                    if a:
                        raise IndexError("index out of bounds")
            r = self.__dataset.getObject()
        else:
            if index is None:
                raise ValueError("Need an integer or a tuple of integers")
            try:
                if args:
                    r = self.__dataset.getObject(index, *args)
                else:
                    r = self.__dataset.getObjectAbs(index)
            except _jarrayindex_exception:
                raise IndexError("index out of bounds")
            except _jillegalargument_exception:
                raise ValueError("incorrect number of indices")

        if isinstance(r, _jcomplex):
            return complex(r.getReal(), r.getImaginary())
        return r

    @staticmethod
    def _tolist(a):
        return [ (i if not isinstance(i, ndarray) else ndarray._tolist(i)) for i in a  ]

    def tolist(self):
        return ndarray._tolist(self)

    # MISSING: itemset, setasflat, tostring, tofile, dump, dumps
    # MISSING: byteswap

    @_wrapout
    def astype(self, dtype):
        return self.__dataset.cast(_translatenativetype(dtype).value)

    def copy(self):
        return ndarray(buffer=self.__dataset, copy=True)

    @_wrapout
    def view(self, cls=None):
        '''Return a view of dataset'''
        if cls is None or cls == self.__class__:
            return self.__dataset.getView(True)
        else:
            return cast(self, cls.dtype)

    # MISSING: getfield, setflags

    def fill(self, value):
        self.__dataset.fill(_cvt2j(value))
        return self

    #  shape manipulation
    @_wrapout
    def reshape(self, *shape):
        '''Return a dataset with same data but new shape'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        return self.__dataset.reshape(shape)

    def resize(self, *shape, **kwarg):
        '''Change shape and size of dataset in-place'''
        if len(shape) == 1:
            shape = asIterable(shape[0])
        self.__dataset.resize(shape)

    @_wrapout
    def transpose(self, axes=None):
        if axes is None:
            axes = _jempty
        return self.__dataset.getTransposedView(axes)

    @_wrapout
    def swapaxes(self, axis1, axis2):
        return self.__dataset.swapAxes(axis1, axis2)

    @_wrapout
    def flatten(self):
        '''Return a 1D dataset with copy of data'''
        return self.__dataset.flatten().clone()

    @_wrapout
    def ravel(self):
        return self.__dataset.flatten()

    def squeeze(self, axis=None): # TODO support 1.7 axis argument
        os = self.__dataset.getShapeRef()
        if os == _sutils.squeezeShape(os, _jfalse):
            return self
        return Sciwrap(self.__dataset.squeeze())

    #  item selection and manipulation
    @_wrapout
    def take(self, indices, axis=None):
        if isinstance(indices, ndarray):
            return _dsutils.take(self.__dataset, indices._jdataset(), axis)
        return _dsutils.take(self.__dataset, asIterable(indices), axis)

    def put(self, indices, values):
        if isinstance(indices, ndarray):
            inds = indices._jdataset()
        else:
            inds = asIterable(indices)
        if isinstance(values, ndarray):
            vals = values._jdataset()
        else:
            vals = asIterable(values)
        _dsutils.put(self.__dataset, inds, vals)

    def repeat(self, repeats, axis=None):
        return repeat(self, repeats, axis=axis)

    def choose(self, choices, mode='raise'):
        return choose(self, choices, mode=mode)

    def sort(self, axis=-1):
        self.__dataset.sort(axis)

    def nonzero(self):
        return _cmps.nonzero(self)

    @_wrapout
    def argsort(self, axis=-1):
        return _dsutils.indexSort(self.__dataset, axis)

    # MISSING: searchsorted
    # MISSING: compress, diagonal

    #  calculation
    @_wrapout
    @_keepdims
    def max(self, axis=None, ignore_nans=False, keepdims=False): #@ReservedAssignment
        if axis is None:
            if ignore_nans:
                return self.__dataset.max(_jtrue)
            return self.__dataset.max(_empty_boolean_array)
        elif isinstance(axis, int):
            if ignore_nans:
                return self.__dataset.max(_jint(axis), _jtrue)
            return self.__dataset.max(_jint(axis))
        elif isinstance(axis, tuple):
            if ignore_nans:
                return self.__dataset.max(asarray([_jint(x) for x in axis]), _jtrue)
            return self.__dataset.max(asarray([_jint(x) for x in axis]))

    @_wrapout
    @_keepdims
    def min(self, axis=None, ignore_nans=False, keepdims=False): #@ReservedAssignment
        if axis is None:
            if ignore_nans:
                return self.__dataset.min(_jtrue)
            return self.__dataset.min(_empty_boolean_array)
        elif isinstance(axis, int):
            if ignore_nans:
                return self.__dataset.min(_jint(axis), _jtrue)
            return self.__dataset.min(_jint(axis))
        elif isinstance(axis, tuple):
            if ignore_nans:
                return self.__dataset.min(asarray([_jint(x) for x in axis]), _jtrue)
            return self.__dataset.min(asarray([_jint(x) for x in axis]))

    @_wrapout
    def argmax(self, axis=None, ignore_nans=False):
        if axis is None:
            if ignore_nans:
                return self.__dataset.argMax(_jtrue)
            return self.__dataset.argMax(_empty_boolean_array)
        else:
            if ignore_nans:
                return self.__dataset.argMax(_jint(axis), _jtrue)
            return self.__dataset.argMax(_jint(axis))

    @_wrapout
    def argmin(self, axis=None, ignore_nans=False):
        if axis is None:
            if ignore_nans:
                return self.__dataset.argMin(_jtrue)
            return self.__dataset.argMin(_empty_boolean_array)
        else:
            if ignore_nans:
                return self.__dataset.argMin(_jint(axis), _jtrue)
            return self.__dataset.argMin(_jint(axis))

    @_wrapout
    @_keepdims
    def ptp(self, axis=None, keepdims=False):
        if axis is None:
            return self.__dataset.peakToPeak(_empty_boolean_array)
        elif isinstance(axis, int):
            return self.__dataset.peakToPeak(_jint(axis))
        elif isinstance(axis, tuple):
            return self.__dataset.peakToPeak(asarray([_jint(x) for x in axis]))

    def clip(self, a_min, a_max):
        return _maths.clip(self, a_min, a_max)

    def conj(self):
        return _maths.conj(self)

    # MISSING: round, trace
    @_wrapout
    def sum(self, axis=None, dtype=None, keepdims=False):
        return _maths.sum(self, axis, dtype, keepdims=keepdims)

    def cumsum(self, axis=None, dtype=None):
        return _maths.cumsum(self, axis, dtype, keepdims=keepdims)

    @_wrapout
    @_keepdims
    def mean(self, axis=None, keepdims=False):
        if axis is None:
            return self.__dataset.mean(_empty_boolean_array)
        elif isinstance(axis, int):
            return self.__dataset.mean(_jint(axis))
        elif isinstance(axis, tuple):
            return self.__dataset.mean(asarray([_jint(x) for x in axis]))

    @_wrapout
    @_keepdims
    def var(self, axis=None, ddof=0, keepdims=False):
        is_pop = _jbool(ddof == 0)
        if axis is None:
            return self.__dataset.variance(is_pop)
        elif isinstance(axis, int):
            return self.__dataset.variance(_jint(axis), is_pop)
        elif isinstance(axis, tuple):
            return self.__dataset.variance(asarray([_jint(x) for x in axis]), is_pop)

    @_wrapout
    @_keepdims
    def std(self, axis=None, ddof=0, keepdims=False):
        is_pop = _jbool(ddof == 0)
        if axis is None:
            return self.__dataset.stdDeviation(is_pop)
        elif isinstance(axis, int):
            return self.__dataset.stdDeviation(_jint(axis), is_pop)
        elif isinstance(axis, tuple):
            return self.__dataset.stdDeviation(asarray([_jint(x) for x in axis]), is_pop)

    @_wrapout
    def rms(self, axis=None):
        if axis is None:
            return self.__dataset.rootMeanSquare()
        return self.__dataset.rootMeanSquare(_jint(axis))

    def prod(self, axis=None, dtype=None, keepdims=False):
        return _maths.prod(self, axis, dtype, keepdims=keepdims)

    def cumprod(self, axis=None, dtype=None):
        return _maths.cumprod(self, axis, dtype)

    @_wrapout
    @_keepdims
    def all(self, axis=None, keepdims=False): #@ReservedAssignment
        if axis is None:
            return self.__dataset.all()
        return self.__dataset.all(axis)

    @_wrapout
    @_keepdims
    def any(self, axis=None, keepdims=False): #@ReservedAssignment
        if axis is None:
            return self.__dataset.any()
        return self.__dataset.any(axis)

    #  comparison operators
    def __lt__(self, o):
        return _cmps.less(self, o)

    def __le__(self, o):
        return _cmps.less_equal(self, o)

    def __gt__(self, o):
        return _cmps.greater(self, o)

    def __ge__(self, o):
        return _cmps.greater_equal(self, o)

    def __eq__(self, o):
        e = _cmps.equal(self, o)
        if self.ndim == 0 and self.size == 1:
            return e._jdataset().getBoolean()
        return e

    def __ne__(self, o):
        return _cmps.not_equal(self, o)

    def __nonzero__(self):
        if self.size > 1:
            raise ValueError("The truth value of an array with more than one element is ambiguous. Use a.any() or a.all()")
        return self.item() != 0


    #  unary operators
    def __neg__(self):
        return _maths.negative(self)
    def __pos__(self):
        return self
    def __abs__(self):
        return _maths.abs(self)
    def __invert__(self):
        if self.dtype is bool:
            return _cmps.logical_not(self)
        return _maths.invert(self)

    #  arithmetic operators
    def __add__(self, o):
        return _maths.add(self, asDataset(o))
    def __sub__(self, o):
        return _maths.subtract(self, asDataset(o))
    def __mul__(self, o):
        return _maths.multiply(self, asDataset(o))
    def __div__(self, o):
        return _maths.divide(self, asDataset(o))
    def __truediv__(self, o):
        return _maths.divide(self, asDataset(o))
    def __floordiv__(self, o):
        return _maths.floor_divide(self, asDataset(o))
    def __mod__(self, o):
        return _maths.remainder(self, asDataset(o))
    def __pow__(self, o, z=None):
        return _maths.power(self, asDataset(o))
    def __divmod__(self, o):
        return (_maths.floor_divide(self, asDataset(o)), _maths.remainder(self, asDataset(o)))
    def __lshift__(self, o):
        return _maths.left_shift(self, asDataset(o))
    def __rshift__(self, o):
        return _maths.right_shift(self, asDataset(o))
    def __and__(self, o):
        d = asDataset(o, force=True)
        if self.dtype is bool and d.dtype is bool:
            return _cmps.logical_and(self, d)
        return _maths.bitwise_and(self, d)
    def __or__(self, o):
        d = asDataset(o, force=True)
        if self.dtype is bool and d.dtype is bool:
            return _cmps.logical_or(self, d)
        return _maths.bitwise_or(self, d)
    def __xor__(self, o):
        d = asDataset(o, force=True)
        if self.dtype is bool and d.dtype is bool:
            return _cmps.logical_xor(self, d)
        return _maths.bitwise_xor(self, d)

    def __radd__(self, o):
        return _maths.add(self, asDataset(o))
    def __rsub__(self, o):
        return _maths.subtract(asDataset(o), self)
    def __rmul__(self, o):
        return _maths.multiply(self, asDataset(o))
    def __rdiv__(self, o):
        return _maths.divide(asDataset(o), self)


    #  in-place arithmetic operators
    def __iadd__(self, o):
        self.__dataset.iadd(__cvt_jobj(o, dtype=self.dtype, copy=False))
        return self
    def __isub__(self, o):
        self.__dataset.isubtract(__cvt_jobj(o, dtype=self.dtype, copy=False))
        return self
    def __imul__(self, o):
        self.__dataset.imultiply(__cvt_jobj(o, dtype=self.dtype, copy=False))
        return self
    def __idiv__(self, o):
        _maths.divide(self, o, self)
        return self
    def __itruediv__(self, o):
        _maths.divide(self, o, self)
        return self
    def __ifloordiv__(self, o):
        _maths.floor_divide(self, o, self)
        return self
    def __imod__(self, o):
        _maths.remainder(self, o, self)
        return self
    def __ipow__(self, o):
        self.__dataset.ipower(__cvt_jobj(o, dtype=self.dtype, copy=False))
        return self
    def __ilshift__(self, o):
        _maths.left_shift(self, o, self)
        return self
    def __irshift__(self, o):
        _maths.right_shift(self, o, self)
        return self
    def __iand__(self, o):
        if self.dtype is bool:
            _cmps.logical_and(self, o, self)
        else:
            _maths.bitwise_and(self, o, self)
        return self
    def __ior__(self, o):
        if self.dtype is bool:
            _cmps.logical_or(self, o, self)
        else:
            _maths.bitwise_or(self, o, self)
        return self
    def __ixor__(self, o):
        if self.dtype is bool:
            _cmps.logical_xor(self, o, self)
        else:
            _maths.bitwise_xor(self, o, self)
        return self

    # Special methods

    #  for standard library functions
    @_wrapout
    def __copy__(self, order=None):
        return self.__dataset.clone()

    # MISSING: __deepcopy__, __reduce__, __setstate__

    #  basic customization
    # MISSING: __new__, __array__, __array_wrap__

    #  container customization
    def __len__(self):
        if len(self.shape) > 0:
            return self.shape[0]
        raise TypeError("len() of unsized object")

    @_wrapout
    def __getitem__(self, key):
        isslice, key = _toslice(self.ndim, key)
        try:
            if not isslice:
                if isinstance(key, _booleands):
                    return self.__dataset.getByBoolean(key)
                if isinstance(key, _integerds):
                    return self.__dataset.getBy1DIndex(key)
                if _contains_ints_bools_newaxis(key):
                    return self.__dataset.getByIndexes(key)
                return self.__dataset.getObject(key)

            return _getslice(self.__dataset, key)
        except _jarrayindex_exception:
            raise IndexError

    def __setitem__(self, key, value):
        value = fromDS(value)
        if isinstance(value, ndarray):
            value = value._jdataset()
        else:
            value = _cvt2j(value)

        # workaround lack of broadcasting support in setters for compound datasets
        if self.dtype.elements > 1 and isinstance(value, _ds) and value.getSize() == 1:
            value = value.getBuffer()

        isslice, key = _toslice(self.ndim, key)

        try:
            if not isslice:
                if isinstance(key, _booleands):
                    return self.__dataset.setByBoolean(value, key)
                if isinstance(key, _integerds):
                    return self.__dataset.setBy1DIndex(value, key)
                if _contains_ints_bools_newaxis(key):
                    return self.__dataset.setByIndexes(value, key)
                return self.__dataset.set(value, key)

            _setslice(self.__dataset, value, key)
            return self
        except _jarrayindex_exception:
            raise IndexError

    # NOT NEEDED: __getslice__, __setslice__
    # MISSING: __contains__

    #  conversion
    def __int__(self):
        if self.size > 1:
            raise TypeError("only length-1 arrays can be converted to Python scalars")
        return int(self.__dataset.getObject([]))

    def __long__(self):
        if self.size > 1:
            raise TypeError("only length-1 arrays can be converted to Python scalars")
        return int(self.__dataset.getObject([]))

    def __float__(self):
        if self.size > 1:
            raise TypeError("only length-1 arrays can be converted to Python scalars")
        return float(self.__dataset.getObject([]))

    def __oct__(self):
        if self.size > 1:
            raise TypeError("only length-1 arrays can be converted to Python scalars")
        return oct(self.__dataset.getObject([]))

    def __hex__(self):
        if self.size > 1:
            raise TypeError("only length-1 arrays can be converted to Python scalars")
        return hex(self.__dataset.getObject([]))

    #  string representations
    def __str__(self):
        return self.__dataset.toString(True)

    def __repr__(self):
        dt = _getdtypefromjdataset(self.__dataset)
        if dt is int_ or dt is float_ or dt is complex_:
            return 'array(' + self.__dataset.toString(True) + ')'
        return 'array(' + self.__dataset.toString(True) + ', dtype=%s)' % (dt,)
        return self.__dataset.toString(True)

    # extra method
    @_wrapout
    def get_elements(self, n):
        '''Retrieve n-th elements from each item in array as view
        '''
        me = self.__dataset.getElementsPerItem()
        if n < 0:
            n += me
        if n >= me or n < 0:
            raise IndexError("Element number is out of range")
        if me == 1:
            return self.__dataset.getView(True)
        return self.__dataset.getElements(n)

class ndarrayRGB(ndarray):
    '''
    Wrap RGB dataset
    '''
    def __init__(self, shape=None, dtype=None, buffer=None, copy=False): #@ReservedAssignment
        super(ndarrayRGB, self).__init__(shape=shape, dtype=dtype, buffer=buffer, copy=copy)

    @_wrapout
    def get_red(self, dtype=None):
        dtype = _translatenativetype(dtype)
        if dtype is None:
            dtype = int16
        if dtype != int16:
            return self._jdataset().createRedDataset(dtype.value)
        return self._jdataset().getRedView()

    @_wrapout
    def get_green(self, dtype=None):
        dtype = _translatenativetype(dtype)
        if dtype is None:
            dtype = int16
        if dtype != int16:
            return self._jdataset().createGreenDataset(dtype.value)
        return self._jdataset().getGreenView()

    @_wrapout
    def get_blue(self, dtype=None):
        dtype = _translatenativetype(dtype)
        if dtype is None:
            dtype = int16
        if dtype != int16:
            return self._jdataset().createBlueDataset(dtype.value)
        return self._jdataset().getBlueView()

    @_wrapout
    def get_grey(self, cweights=None, dtype=None):
        '''Get grey image
        
        Arguments:
        cweights -- optional set of weight for combining the colour channel
        dtype    -- optional dataset type (default is int16)'''
        dtype = _translatenativetype(dtype)
        if dtype is None:
            dtype = int16
        if cweights:
            cweights = asIterable(cweights)
            if len(cweights) != 3:
                raise ValueError("three colour channel weights needed")
            csum = float(sum(cweights))
            return self._jdataset().createGreyDataset(dtype.value, cweights[0]/csum, cweights[1]/csum, cweights[2]/csum)
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
    dtype = _translatenativetype(dtype)
    if dtype is None:
        if isinstance(start, complex) or isinstance(stop, complex) or isinstance(step, complex):
            dtype = complex128
        elif isinstance(start, float) or isinstance(stop, float) or isinstance(step, float):
            dtype = float64
        elif isinstance(start, int) or isinstance(stop, int) or isinstance(step, int):
            dtype = int32
        else:
            raise ValueError("Unknown or invalid type of input value")
    if dtype == bool:
        return None

    return _df.createRange(dtype.value, start, stop, step)

def array(obj, dtype=None, copy=True):
    '''Create a dataset of given type from a sequence or JAMA matrix'''
    return ndarray(shape=None, dtype=dtype, buffer=obj, copy=copy)

@_wrapout
def ones(shape, dtype=float64):
    '''Create a dataset filled with 1'''
    dtype = _translatenativetype(dtype)
    return _df.ones(dtype.elements, dtype.value, _as_int_array(shape))

@_wrap('a')
def ones_like(a, dtype=None):
    o = _df.ones(a)
    if dtype is not None:
        dtype = _translatenativetype(dtype)
        o = o.cast(dtype.value)
    return o

from types import FunctionType as _fntype

@_wrapout
def zeros(shape, dtype=float64, elements=None):
    '''Create a dataset filled with 0'''
    dtype = _translatenativetype(dtype)
    if elements is not None:
        if type(dtype) is _fntype:
            dtype = dtype(elements)
        else:
            dtype.elements = elements
    elif type(dtype) is _fntype:
        raise ValueError("Given data-type is a function and needs elements defining")

    return _df.zeros(dtype.elements, dtype.value, _as_int_array(shape))

@_wrap('a')
def zeros_like(a, dtype=None):
    z = _df.zeros(a)
    if dtype is not None:
        dtype = _translatenativetype(dtype)
        z = z.cast(dtype.value)
    return z

empty = zeros

empty_like = zeros_like

@_wrapout
def full(shape, fill_value, dtype=None, elements=None):
    '''Create a dataset filled with fill_value'''
    dtype = _translatenativetype(dtype)
    if dtype is None:
        dtype = _getdtypefromobj(fill_value)
    if elements is not None:
        if type(dtype) is _fntype:
            dtype = dtype(elements)
        else:
            dtype.elements = elements
    elif type(dtype) is _fntype:
        raise ValueError("Given data-type is a function and needs elements defining")

    return _df.zeros(dtype.elements, dtype.value, _as_int_array(shape)).fill(fill_value)

@_argsToArrayType('a')
def full_like(a, fill_value, dtype=None, elements=None):
    f = full(a.shape, fill_value, elements=elements)
    if dtype is not None:
        f = f.astype(dtype)
    return f

def linspace(start, stop, num=50, endpoint=True, retstep=False, dtype=None):
    '''Create a 1D dataset from start to stop in given number of steps
    
    Arguments:
    start    -- starting value
    stop     -- stopping value
    num      -- number of steps, defaults to 50
    endpoint -- if True (default), include the stop value
    retstep  -- if False (default), do not include the calculated step value as part of return tuple
    '''
    if not endpoint:
        stop = ((num - 1) * stop + start)/num

    dtype = _translatenativetype(dtype)
    if dtype is None:
        dtype = _getdtypefromobj((start, stop))

        if _ifutils.isInteger(dtype.value):
            dtype = float64

    if _ifutils.isComplex(dtype.value):
        if isinstance(start, int):
            start = start+0j
        if isinstance(stop, int):
            stop = stop+0j
        rresult = _df.createLinearSpace(float64.value, start.real, stop.real, num)
        iresult = _df.createLinearSpace(float64.value, start.imag, stop.imag, num)
        result = Sciwrap(_dsutils.createCompoundDataset(complex128.value, (rresult, iresult)))
        del rresult, iresult
    else:
        result = Sciwrap(_df.createLinearSpace(dtype.value, start, stop, num))

    if retstep:
        step = result[1] - result[0]
        return (result, step)
    else:
        return result

@_wrap
def logspace(start, stop, num=50, endpoint=True, base=10.0, dtype=None):
    '''Create a 1D dataset of values equally spaced on a logarithmic scale'''
    if not endpoint:
        stop = ((num - 1) * stop + start)/num

    dtype = _translatenativetype(dtype)
    if complex(start).imag == 0 and complex(stop).imag == 0:
        if dtype is None:
            dtype = _getdtypefromobj((start, stop))
            if _ifutils.isInteger(dtype.value):
                dtype = float64

        return _df.createLogSpace(dtype.value, start, stop, num, base)
    else:
        result = linspace(start, stop, num, endpoint, False, dtype)
        return _maths.power(base, result)

@_wrap
def eye(N, M=None, k=0, dtype=float64):
    if M is None:
        M = N

    dtype = _translatenativetype(dtype)
    return _dsutils.eye(dtype.value, N, M, k)

def identity(n, dtype=float64):
    return eye(n,n,0,dtype)

@_wrap('v')
def diag(v, k=0):
    x = asDataset(v)._jdataset()
    return _dsutils.diag(x, k)

@_wrap('v')
def diagflat(v, k=0):
    x = asDataset(v).flatten()._jdataset()
    return _dsutils.diag(x, k)

@_argsToArrayType('a', 'indices')
def take(a, indices, axis=None):
    return a.take(indices, axis)

@_wrap('indices', 'values')
def put(a, indices, values):
    return a.put(indices, values)

@_wrap
def select(condlist, choicelist, default=0):
    '''Return dataset with items drawn from choices according to conditions'''
    return _dsutils.select(condlist, choicelist, default)

@_wrap
def choose(a, choices, mode='raise'):
    '''Return dataset with items drawn from choices according to conditions'''
    if mode == 'raise':
        rf = True
        cf = False
    else:
        rf = False
        if mode == 'clip':
            cf = True
        elif mode == 'wrap':
            cf = False
        else:
            raise ValueError("mode is not one of raise, clip or wrap")
    return _dsutils.choose(a, choices, rf, cf)

@_wrapin
def _jatleast_1d(arrays):
    res = []
    for a in arrays:
        a = __cvt_jobj(a, dtype=None, copy=False, force=True)
        res.append(a.reshape(1) if a.getRank() == 0 else a)
    return res

@_wrapout
def atleast_1d(*arrays):
    '''Return list of datasets that are at least 1d'''
    res = _jatleast_1d(arrays)
    return res if len(res) > 1 else res[0]

@_wrapin
def _jatleast_2d(arrays):
    res = []
    for a in arrays:
        a = __cvt_jobj(a, dtype=None, copy=False, force=True)
        r = a.getRank()
        if r == 0:
            a = a.reshape(1, 1)
        elif r == 1:
            a = a.reshape(1, a.getSize())
        res.append(a)
    return res

@_wrapout
def atleast_2d(*arrays):
    '''Return list of datasets that are at least 2d'''
    res = _jatleast_2d(arrays)
    return res if len(res) > 1 else res[0]

@_wrapin
def _jatleast_3d(arrays):
    res = []
    for a in arrays:
        a = __cvt_jobj(a, dtype=None, copy=False, force=True)
        r = a.getRank()
        if r == 0:
            a = a.reshape(1,1,1)
        elif r == 1:
            a = a.reshape(1, a.getSize(), 1)
        elif r == 2:
            a = a.reshape(list(a.getShape()) + [1])
        res.append(a)
    return res

@_wrapout
def atleast_3d(*arrays):
    '''Return list of datasets that are at least 3d'''
    res = _jatleast_3d(arrays)
    return res if len(res) > 1 else res[0]

@_wrapout
def concatenate(a, axis=0):
    a = [array(arr_like) if (not isinstance(arr_like, ndarray)) else arr_like for arr_like in a]
    a = [_jinput(arr) for arr in a]
    return _dsutils.concatenate(a, axis)

@_wrapout
def vstack(tup):
    arr = [atleast_2d(t)._jdataset() for t in tup ]
    return _dsutils.concatenate(arr, 0)

@_wrapout
def hstack(tup):
    arr = _jatleast_1d(toList(tup))
    return _dsutils.concatenate(arr, 0) if arr[0].getRank() == 1 else _dsutils.concatenate(arr, 1)

@_wrapout
def dstack(tup):
    arr = [atleast_3d(t)._jdataset() for t in tup ]
    return _dsutils.concatenate(arr, 2)

@_wrapout
def column_stack(tup):
    arr = [t._jdataset() if t.ndim >= 2 else atleast_2d(t).T._jdataset() for t in tup ]
    return _dsutils.concatenate(arr, 1)

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

@_wrap('a')
def sort(a, axis=-1):
    if axis is None:
        return _dsutils.sort(a)
    return _dsutils.sort(a, axis)

@_wrap('a')
def argsort(a, axis=-1):
    return _dsutils.indexSort(a, axis)

@_wrap('a', 'reps')
def tile(a, reps):
    return _dsutils.tile(a, _as_int_array(reps))

@_wrap('a')
def repeat(a, repeats, axis=None):
    if axis is None:
        axis = -1
    if a.getRank() == 0: # FIXME bug in january 2.2
        a = a.reshape(1)
    return _dsutils.repeat(a, asIterable(repeats), axis)

@_wrap('arr', 'values')
def append(arr, values, axis=None):
    '''Append values to end of array
    Keyword argument:
    axis -- if None, then append flattened values to flattened array 
    '''
    if not isinstance(values, _ds):
        values = __cvt_jobj(values, dtype=None, copy=False, force=True)
    if axis is None:
        return _dsutils.append(arr.flatten(), values.flatten(), 0)
    return _dsutils.append(arr, values, axis)

@_wrap('a')
def cast(a, dtype):
    return _dsutils.cast(dtype.value, a)

@_wrapout('a')
def copy(a):
    return a.__copy__()

@_argsToArrayType('a')
def reshape(a, newshape):
    return asDataset(a).reshape(newshape)

@_wrap('a')
def resize(a, new_shape):
    return _dsutils.resize(a, new_shape)

@_argsToArrayType('a')
def ravel(a):
    return asDataset(a).ravel()

@_argsToArrayType('a')
def squeeze(a):
    a.squeeze()
    return a

@_wrap('a')
def transpose(a, axes=None):
    if axes is None:
        axes = ()
    return _dsutils.transpose(a, asIterable(axes))

@_wrap('a')
def swapaxes(a, axis1, axis2):
    return _dsutils.swapAxes(a, axis1, axis2)

@_argsToArrayType('a')
def amax(a, axis=None, keepdims=False):
    return a.max(axis, keepdims=keepdims)

@_argsToArrayType('a')
def amin(a, axis=None, keepdims=False):
    return a.min(axis, keepdims=keepdims)

@_argsToArrayType('a')
def nanmax(a, axis=None, keepdims=False):
    return a.max(axis, ignore_nans=True, keepdims=keepdims)

@_argsToArrayType('a')
def nanmin(a, axis=None, keepdims=False):
    return a.min(axis, ignore_nans=True, keepdims=keepdims)

@_argsToArrayType('a')
def argmax(a, axis=None):
    return a.argmax(axis)

@_argsToArrayType('a')
def argmin(a, axis=None):
    return a.argmin(axis)

@_argsToArrayType('a')
def nanargmax(a, axis=None):
    return a.argmax(axis, ignore_nans=True)

@_argsToArrayType('a')
def nanargmin(a, axis=None):
    return a.argmin(axis, ignore_nans=True)

def meshgrid(*a, **kwargs):
    indexing = kwargs.get('indexing', 'xy')
    if indexing == 'ij':
        a = [a[1], a[0]] + (a[2:] if len(a) > 2 else [])
    elif indexing != 'xy':
        raise ValueError('indexing value is not valid')
    axes = [ asDataset(x)._jdataset() for x in reversed(a) ]
        
    coords = _dsutils.meshGrid(axes)
    if indexing == 'ij':
        coords = [coords[1], coords[0]] + (coords[2:] if len(coords) > 2 else [])
    return tuple([ Sciwrap(x) for x in reversed(coords) ])

@_wrap
def indices(dimensions, dtype=int32):
    ind = _dsutils.indices(asIterable(dimensions))
    dtype = _translatenativetype(dtype)
    if dtype != int32:
        ind = _dsutils.cast(dtype.value, ind)
    return ind

def ix_(*args):
    '''Create an open mesh from 1D sequences
    '''
    n = len(args)
    index = []
    for i,a in enumerate(args):
        d = asarray(a)
        if d.ndim > 1:
            raise ValueError('sequences must be 1D')
        shape = [1]*n
        shape[i] = d.size
        d.shape = shape
        index.append(d)
    return tuple(index)

# need to define r_, c_, s_, index_exp objects
class _slice_translate(object):
    def __init__(self, axis):
        self.axis = axis
        pass
    def __getitem__(self, key):
        pass

r_ = _slice_translate(0)
c_ = _slice_translate(-1)
s_ = _slice_translate(2)

# also mgrid, ogrid

@_wrap('a')
def fliplr(a):
    return _dsutils.flipLeftRight(a)

@_wrap('a')
def flipud(a):
    return _dsutils.flipUpDown(a)

@_wrap('a')
def roll(a, shift, axis=None):
    return _dsutils.roll(a, shift, axis)

@_wrap('a')
def rot90(a, k=1):
    return _dsutils.rotate90(a, k)

@_wrap('a')
def rollaxis(a, axis, start=0):
    return _dsutils.rollAxis(a, axis, start)

@_wrap('a')
def compoundarray(a, view=True):
    '''Create a compound array from an nd array by grouping last axis items into compound items
    '''
    return _dsutils.createCompoundDatasetFromLastAxis(a, view)

@_wrap('a')
def nan_to_num(a):
    '''Create a copy with infinities replaced by max/min values and NaNs replaced by 0s
    '''
    c = a.copy()
    _dsutils.removeNansAndInfinities(c)
    return c

@_wrap('indices')
def unravel_index(indices, dims):
    '''Converts a flat index (or array of them) into a tuple of coordinate arrays
    '''
    if isinstance(indices, (tuple, list)):
        indices = ndarray(buffer=indices)._jdataset()
    if not isinstance(indices, _ds):
        return tuple(_sutils.getNDPositionFromShape(indices, dims))
    return tuple(_dsutils.calcPositionsFromIndexes(indices, dims))


_prep_mode = {'raise':0, 'wrap':1, 'clip':2}

@_wrap('multi_index')
def ravel_multi_index(multi_index, dims, mode='raise'):
    '''Converts a tuple of coordinate arrays to an array of flat indexes
    '''
    if isinstance(mode, tuple):
        mode = [_prep_mode.get(m, -1) for m in mode]
    else:
        mode = _prep_mode.get(mode, -1)

    if isinstance(multi_index, _ds): # split single array
        multi_index = [ _getslice(multi_index, i) for i in range(multi_index.shape[0]) ]

    single = False
    if isinstance(multi_index[0], int):
        single = True
        multi_index = [ array(m)._jdataset() for m in multi_index ]


    pos = _dsutils.calcIndexesFromPositions(multi_index, dims, mode)
    if single:
        return pos.getObject([])
    return pos

