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

Classes available:
    ndarrayA - boolean dataset
    ndarrayB - byte dataset
    ndarrayS - short dataset
    ndarrayI - int dataset
    ndarrayL - long dataset
    ndarrayF - float dataset
    ndarrayD - double dataset
    ndarrayCB - compound byte dataset
    ndarrayCS - compound short dataset
    ndarrayCI - compound int dataset
    ndarrayCL - compound long dataset
    ndarrayCF - compound float dataset
    ndarrayCD - compound double dataset
    ndarrayC - complex float dataset
    ndarrayZ - complex double dataset
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
    arange(start, stop=None, step=1, dtype=None):
    array(object, dtype=None):
    asarray(data, dtype=None)
    asanyarray(data, dtype=None)
    ones(shape, dtype=float64):
    zeros(shape, dtype=float64):
    empty = zeros
    eye(N, M=None, k=0, dtype=float64):
    identity(n, dtype=float64):
    diag(v, k=0):
    diagflat(v, k=0):
    take(a, indices, axis=None):
    put(a, indices, values):
    concatenate(a, axis=0):
    vstack(tup):
    hstack(tup):
    dstack(tup):
    split(ary, indices_or_sections, axis=0):
    array_split(ary, indices_or_sections, axis=0):
    vsplit(ary, indices_or_sections):
    hsplit(ary, indices_or_sections):
    dsplit(ary, indices_or_sections):
    sort(a, axis=-1):
    tile(a, reps):
    repeat(a, repeats, axis=-1):
    cast(a, dtype):
    any(a, axis=None):
    all(a, axis=None):
    squeeze(a):
    transpose(a, axes=None):
    swapaxes(a, axis1, axis2):
    argmax(a, axis=None):
    argmin(a, axis=None):
    maximum(a, b):
    minimum(a, b):
    meshgrid(*a):
    indices(dimensions, dtype=int32):
    norm(a, allelements=True):
    compoundarray(a, view=True):

    Check also in maths, comparisons, fft, random, io, plot and signal sub-modules
'''
import sys
if sys.hexversion < 0x02040000:
    raise 'Must use python of at least version 2.4'

import os
if os.name == 'java':
    from jython.jycore import *
    from jython.jymaths import *
    from jython.jycomparisons import *
    from jython.jyscisoft import *

    import image
else:
    from python.pycore import *
    from python.pymaths import *
    from python.pycomparisons import *
    from python.pyscisoft import *

from signal import convolve, correlate

'''
Imports should work with python+numpy only agreed with MB 11 Nov 2011
'''
try:
    import nexus
except Exception, e:
    print >> sys.stderr, "Could not import nexus"

try:
    import io
except Exception, e:
    print >> sys.stderr, "Could not import input/output routines"
    print >> sys.stderr, e

try:
    import plot
except Exception, e:
    print >> sys.stderr, "Could not import plotting routines"
    print >> sys.stderr, e

try:
    import random
except Exception, e:
    print >> sys.stderr, "Could not import random routines"
    print >> sys.stderr, e

try:
    import flatten

except Exception, e:
    print >> sys.stderr, "Could not import flatten API"
    print >> sys.stderr, e

try:
    import rpc
except Exception, e:
    print >> sys.stderr, "Could not import rpc API"
    print >> sys.stderr, e

try:
    import fft
except Exception, e:
    print >> sys.stderr, "Could not import plotting routines"
    print >> sys.stderr, e

try:
    import fit
except Exception, e:
    print >> sys.stderr, "Could not import fit"
