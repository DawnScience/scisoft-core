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
scisoftpy is a NumPy-like wrapper around the Diamond Scisoft Analysis plugin
----------------------------------------------------------------------------

Check the documentation at
  http://www.opengda.org/documentation/manuals/Diamond_SciSoft_Python_Guide/trunk/contents.html

Classes available:
    ndarray - dataset
    ndarrayRGB - colour RGB dataset

dtypes available:
    bool
    int8
    int16
    int32 = int
    int64
    float32
    float64 = float
    cint8
    cint16
    cint32
    cint64
    cfloat32
    cfloat64
    complex64
    complex128 = complex

Functions available:
    asarray(data, dtype=None)
    asanyarray(data, dtype=None)
    asfarray(data, dtype=None)
    arange(start, stop=None, step=1, dtype=None)
    array(object, dtype=None)
    ones(shape, dtype=float64)
    ones_like(shape, dtype=None)
    zeros(shape, dtype=float64, elements=None)
    zeros_like(shape, dtype=None)
    empty = zeros
    empty_like = zeros_like
    full(shape, fill_value, dtype=None, elements=None)
    full_like(a, fill_value, dtype=None, elements=None)
    linspace(start, stop, num=50, endpoint=True, retstep=False, dtype=None)
    logspace(start, stop, num=50, endpoint=True, base=10.0, dtype=None)
    eye(N, M=None, k=0, dtype=float64)
    identity(n, dtype=float64)
    diag(v, k=0)
    diagflat(v, k=0)
    take(a, indices, axis=None)
    put(a, indices, values)
    select(condlist, choicelist, default=0)
    choose(a, choices, mode='raise')
    atleast_1d(*arrays)
    atleast_2d(*arrays)
    atleast_3d(*arrays)
    concatenate(a, axis=0)
    vstack(tup)
    hstack(tup)
    dstack(tup)
    split(ary, indices_or_sections, axis=0)
    array_split(ary, indices_or_sections, axis=0)
    vsplit(ary, indices_or_sections)
    hsplit(ary, indices_or_sections)
    dsplit(ary, indices_or_sections)
    sort(a, axis=-1)
    tile(a, reps)
    repeat(a, repeats, axis=-1)
    append(arr, values, axis=None)
    cast(a, dtype)
    copy(a)
    reshape(a, newshape)
    resize(a, new_shape)
    ravel(a)
    squeeze(a)
    transpose(a, axes=None)
    swapaxes(a, axis1, axis2)
    amax(a, axis=None)
    amin(a, axis=None)
    nanmax(a, axis=None)
    nanmin(a, axis=None)
    argmax(a, axis=None)
    argmin(a, axis=None)
    nanargmax(a, axis=None)
    nanargmin(a, axis=None)
    meshgrid(*a)
    indices(dimensions, dtype=int32)
    ix_(*args)
    fliplr(a)
    flipud(a)
    roll(a, shift, axis=None))
    rot90(a, k=1)
    rollaxis(a, axis, start=0)
    compoundarray(a, view=True)
    nan_to_num(a)
    unravel_index(indices, dims)
    ravel_multi_index(multi_index, dims, mode='raise')

    Check also in maths, comparisons, fft, random, io, plot and signal sub-modules
'''
from __future__ import print_function
import sys
if sys.hexversion < 0x02070000:
    raise ImportError('Must use python of at least version 2.7')

import os
if os.name == 'java':
    from .jython.jycore import *
    from .jython.jymaths import *
    from .jython.jycomparisons import *
    from .jython.jyscisoft import *
else:
    from .python.pycore import *
    from .python.pymaths import *
    from .python.pycomparisons import *
    from .python.pyscisoft import *

__version__ = "2.23.0"

'''
Imports should work with python+numpy only agreed with MB 11 Nov 2011
'''
try:
    from . import nexus
except Exception as e:
    print("Could not import nexus", file=sys.stderr)
    print(e, file=sys.stderr)

try:
    from . import io
except Exception as e:
    print("Could not import input/output routines", file=sys.stderr)
    print(e, file=sys.stderr)

try:
    from . import plot
except Exception as e:
    print("Could not import plotting routines", file=sys.stderr)
    print(e, file=sys.stderr)

try:
    from . import data
except Exception as e:
    print("Could not import data routines", file=sys.stderr)
    print(e, file=sys.stderr)

try:
    from . import random
except Exception as e:
    print("Could not import random routines", file=sys.stderr)
    print(e, file=sys.stderr)

try:
    from . import linalg
except Exception as e:
    print("Could not import linear algebra routines", file=sys.stderr)
    print(e, file=sys.stderr)

try:
    from . import flatten
except Exception as e:
    print("Could not import flatten API", file=sys.stderr)
    print(e, file=sys.stderr)

try:
    from . import rpc
except Exception as e:
    print("Could not import rpc API", file=sys.stderr)
    print(e, file=sys.stderr)

try:
    from . import fft
except Exception as e:
    print("Could not import fft routines", file=sys.stderr)
    print(e, file=sys.stderr)

try:
    from .signal import convolve, correlate
except Exception as e:
    print("Could not import some signal routines", file=sys.stderr)
    print(e, file=sys.stderr)

try:
    from . import image
except Exception as e:
    print("Could not import image routines", file=sys.stderr)
    print(e, file=sys.stderr)

try:
    from . import fit
    from .fit import roots, poly1d
except Exception as e:
    print("Could not import fit", file=sys.stderr)
    print(e, file=sys.stderr)

try:
    from . import external
except Exception as e:
    print("Could not import external functions", file=sys.stderr)
    print(e, file=sys.stderr)

