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
'''

import numpy as _np #@UnresolvedImport

newaxis = _np.newaxis

ndarray = _np.ndarray
generic = ndgeneric = _np.generic
bool = _np.bool #@ReservedAssignment
int8 = _np.int8
_uint8 = _np.uint8 # used for PIL saving
_uint16 = _np.uint16 # used for PIL saving and fixing a PIL bug
int16 = _np.int16
int32 = _np.int32
int64 = _np.int64
cint8 = lambda e : _np.dtype([('', _np.int8)]*e)
cint16 = lambda e : _np.dtype([('', _np.int16)]*e)
cint32 = lambda e : _np.dtype([('', _np.int32)]*e)
cint64 = lambda e : _np.dtype([('', _np.int64)]*e)
float32 = _np.float32
float64 = _np.float64
cfloat32 = lambda e : _np.dtype([('', _np.float32)]*e)
cfloat64 = lambda e : _np.dtype([('', _np.float64)]*e)
complex64 = _np.complex64
complex128 = _np.complex128
rgb = _np.dtype([('r', int16), ('g', int16), ('b', int16)])

_pyint = int
_pyfloat = float
_pycomplex = complex
int_ = int64
int = int_ #@ReservedAssignment
float_ = float64
float = float_ #@ReservedAssignment
complex_ = complex128
complex = complex_ #@ReservedAssignment

import types as _types

def asIterable(items):
    """
    Ensure entity is an iterable by making it a tuple if not
    """
    t = type(items)
    if t is _types.ListType or t is _types.TupleType:
        pass
    elif t is _types.DictType:
        items = [ i for i in items.items() ]
    else:
        items = (items,)
    return items

asDatasetDict = lambda x: x
asDatasetList = lambda x: x

def toList(listdata):
    '''Convert a list or tuple to list of datasets'''
    return [ d for d in asIterable(listdata) ]

def scalarToPython(ascalar):
    '''Convert an array scalar to a python type
    '''
    if not isinstance(ascalar, _np.generic):
        raise ValueError, 'Not an array scalar'
    if isinstance(ascalar, _np.bool_):
        return True if ascalar else False
    if isinstance(ascalar, _np.integer):
        return ascalar.__int__()
    if isinstance(ascalar, _np.floating):
        return ascalar.__float__()
    if isinstance(ascalar, _np.complexfloating):
        return ascalar.__complex__()
    raise ValueError, 'Array scalar type not supported'

iscomplexobj = _np.iscomplexobj

isrealobj = _np.isrealobj

asarray = _np.asarray

asfarray = _np.asfarray

asanyarray = _np.asanyarray

arange = _np.arange

array = _np.array

ones = _np.ones

ones_like = _np.ones_like

def zeros(shape, dtype=float, order='C', elements=None):
    '''Create a dataset filled with 0'''
    shape = asIterable(shape)
    if type(dtype) is _types.FunctionType:
        if elements is not None:
            dtype = dtype(elements)
        else:
            raise ValueError, 'Given data-type is a function and needs elements defining'
    elif elements is not None:
        raise SyntaxWarning, 'Defined elements ignored as data-type is not a function'

    if dtype is rgb:
        return ndarrayRGB(shape, dtype=dtype, order=order)

    return _np.zeros(shape, dtype=dtype, order=order)

zeros_like = _np.zeros_like

empty = _np.empty

empty_like = _np.empty_like

linspace = _np.linspace

logspace = _np.logspace

eye = _np.eye

identity = _np.identity

diag = _np.diag

diagflat = _np.diagflat

take = _np.take

put = _np.put

select = _np.select

choose = _np.choose

concatenate = _np.concatenate

vstack = _np.vstack

hstack = _np.hstack

dstack = _np.dstack

split = _np.split

array_split = _np.array_split

hsplit = _np.hsplit

vsplit = _np.vsplit

dsplit = _np.dsplit

sort = _np.sort

tile = _np.tile

repeat = _np.repeat

append = _np.append

cast = _np.cast

reshape = _np.reshape

resize = _np.resize

ravel = _np.ravel

squeeze = _np.squeeze

transpose = _np.transpose

swapaxes = _np.swapaxes

amax = _np.amax

amin = _np.amin

argmax = _np.argmax

argmin = _np.argmin

maximum = _np.maximum

minimum = _np.minimum

meshgrid = _np.meshgrid

indices = _np.indices

roll = _np.roll

rollaxis = _np.rollaxis

#compoundarray(a, view=True):
#    '''Create a compound array from an nd array by grouping last axis items into compound items
#    '''
class ndarrayCB(ndarray):
    """
    Wrap compound byte dataset
    """
    def __new__(cls, elements, shape):
        obj = _np.ndarray.__new__(cls, shape, cint8(elements), None, 0, None, None)
        obj.elementsPerItem = elements
        return obj

    def __array_finalize__(self, obj):
        if obj is None: return
        self.elementsPerItem = getattr(obj, 'elementsPerItem', 1)

class ndarrayCS(ndarray):
    """
    Wrap compound short dataset
    """
    def __new__(cls, elements, shape):
        obj = _np.ndarray.__new__(cls, shape, cint16(elements), None, 0, None, None)
        obj.elementsPerItem = elements
        return obj

    def __array_finalize__(self, obj):
        if obj is None: return
        self.elementsPerItem = getattr(obj, 'elementsPerItem', 1)

class ndarrayCI(ndarray):
    """
    Wrap compound integer dataset
    """
    def __new__(cls, elements, shape):
        obj = _np.ndarray.__new__(cls, shape, cint32(elements), None, 0, None, None)
        obj.elementsPerItem = elements
        return obj

    def __array_finalize__(self, obj):
        if obj is None: return
        self.elementsPerItem = getattr(obj, 'elementsPerItem', 1)

class ndarrayCL(ndarray):
    """
    Wrap compound long dataset
    """
    def __new__(cls, elements, shape):
        obj = _np.ndarray.__new__(cls, shape, cint64(elements), None, 0, None, None)
        obj.elementsPerItem = elements
        return obj

    def __array_finalize__(self, obj):
        if obj is None: return
        self.elementsPerItem = getattr(obj, 'elementsPerItem', 1)

#class ndarray(_np.ndarray):
#    def __new__(cls, shape, dtype=int16, buffer=None, offset=0, strides=None, order=None):
#        obj = _np.ndarray.__new__(cls, shape, dtype, buffer, offset, strides, order)
#        return obj
#    def __array_finalize__(self, obj):
#        if self.shape[-1] == 1:
#            self.shape = self.shape[:-1]
#        if obj is None: return

class ndarrayRGB(ndarray):
    """
    Wrap RGB dataset
    """

    def __new__(cls, shape, dtype=rgb, buffer=None, offset=0, strides=None, order=None):
        obj = super(ndarrayRGB, cls).__new__(cls, shape, dtype, buffer, offset, strides, order)
        obj._oldshape = shape
        return obj

    def __array_finalize__(self, obj):
        if obj is None: return
        self._oldshape = getattr(obj, '_oldshape', None)
        if self._oldshape is None:
            self._oldshape = obj.shape[:-1]

#        if self.dtype != rgb and self.shape[-1] == 3:
#            self.dtype = rgb
#        if self.shape[-1] == 1:
#            self.shape = self.shape[:-1]
#        if self.dtype != rgb and self.shape[-1] == 3:
#            self.shape = self.shape[:-1]


#        if self.dtype == ndarrayRGB._rgbdtype:
#            self.elementsPerItem = getattr(obj, 'elementsPerItem', 3)
#        else:
#            self.elementsPerItem = getattr(obj, 'elementsPerItem', 1)
#        if self.shape[-1] == 1:
#            self.shape = self.shape[:-1]

#    def __get_shape(self):
#        return self._oldshape
#    def __set_shape(self, *shape):
#        if len(shape) == 1:
#            shape = asIterable(shape[0])
#
#        shape.append(3)
#        super(ndarrayRGB, self).shape = shape
#        self._oldshape = shape
#    shape = property(__get_shape, __set_shape) # python 2.5 rather than using @shape.setter

    def get_red(self, dtype=None):
        if dtype is None:
            dtype = int16
        return self['r'].astype(dtype)

    def get_green(self, dtype=None):
        if dtype is None:
            dtype = int16
        return self['g'].astype(dtype)

    def get_blue(self, dtype=None):
        if dtype is None:
            dtype = int16
        return self['b'].astype(dtype)

    def get_grey(self, cweights=None, dtype=None):
        '''Get grey image
        
        Arguments:
        cweights -- optional set of weight for combining the colour channel
        dtype    -- optional dataset type (default is int16)'''
        if dtype is None:
            dtype = int16
        if cweights is None:
            cweights = [0.299, 0.587, 0.114]
        else:
            cweights = asIterable(cweights)
            if len(cweights) != 3:
                raise ValueError, 'three colour channel weights needed'

        csum = float(sum(cweights))
        g = self.get_red(float)*(cweights[0]/csum)
        g += self.get_green(float)*(cweights[1]/csum)
        g += self.get_blue(float)*(cweights[2]/csum)
        return g.astype(dtype)

    red = property(get_red)
    green = property(get_green)
    blue = property(get_blue)
    grey = property(get_grey)

empty = zeros

def compoundarray(a, view=True):
    '''Create a compound array from an nd array by grouping last axis items into compound items
    '''
    d = a.dtype
    e = a.shape[-1]
    ndtype = _np.dtype([('', d)]*e)
    return a.view(ndtype).squeeze()

def _key2slice(key, shape):
    '''Convert a key to a slice
    Returns a tuple of a list of slices and a list of booleans to indicate which dimensions were sliced
    '''
    key = asIterable(key)
    rank = len(shape)
    if rank < len(key):
        raise IndexError, 'too many indices'

    hasEllipsis = False
    s = []
    b = []
    for k in key:
        if k is Ellipsis:
            if not hasEllipsis:
                hasEllipsis = True
                l = rank - len(key) + 1
                s.extend([None]*l)
                b.extend([True]*l)
            else:
                s.append(None)
                b.append(True)
        elif isinstance(k, slice):
            s.append(k)
            b.append(True)
        else:
            s.append(slice(k,k+1))
            b.append(False)

    if len(s) < rank:
        l = rank - len(key)
        s.extend([None]*l)
        b.extend([True]*l)
        
    return s, b
