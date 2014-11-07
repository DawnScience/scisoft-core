Dataset
=======
The dataset classes aim to emulate some of the functionality of NumPy's
``ndarray`` class. In addition, datasets can have items which contain multiple
elements - these are known as compound datasets.

Generic dataset
---------------
Capabilities
 * Indexing
 * Slicing (now returns views)
 * Boolean dataset selection
 * Flat index integer dataset selection
 * Arithmetic operations
 * Comparison and logic operations
 * Mathematical and statistical functions

Key differences
 * Incomplete implementation all NumPy's methods

Implemented NumPy methods (1.7)
 * Array attributes: shape, ndim, data, size, itemsize, nbytes, T, view, indices
 * Array methods: copy, fill, reshape, resize, transpose, flatten, squeeze,
   take, put, max, min, sum, prod, all, any, argmax, argmin, cumsum, cumprod
 * Array creation: array, zeros, ones, linspace, logspace, arange, diag, diagflat, meshgrid, indices
 * Array manipulation: append, tile, repeat, concatenate, vstack,
   hstack, dstack, array_split, split, vsplit, hsplit, dsplit, roll, reshape, resize, ravel, rollaxis
 * Array modification: fill, append
 * Array comparisons and logic operations: all, any, greater, greater_equal, less, less_equal, equal,
   not_equal, logical_not, logical_and, logical_or, logical_xor, allclose, nonzero, select, where, choose
 * Indexing operations: unravel_index, ravel_multi_index
 * Maths: add, subtract, multiply, divide, negative, power, real, imag, absolute,
   exp, log, log2, log10, expm1, log1p, sqrt, square, reciprocal, angle, conjugate,
   floor_divide, remainder, phase, signum, diff, gradient, roots, modf, fmod
 * Trig: sin, cos, tan, arcsin, arccos, arctan, arctan2, hypot, sinh, cosh,
   tanh, arcsinh, arccosh, arctanh, deg2rad, rad2deg
 * Linear algebra: trace, tensordot, eig, eigvals, cholesky, qr, svd, norm, cond, det, solve, inv, pinv, matrix_power
 * Rounding: rint, ceil, floor, trunc, fix
 * Bitwise: bitwise_and, bitwise_or, bitwise_xor, invert, left_shift, right_shift
 * Stats: amax, amin, ptp, mean, std, var, cumprod, cumsum
 * Random: rand, randint, random_integer, randn, exponential, poisson, seed

Non-NumPy
 * Array methods: cast, minpos, maxpos, index (Jython only)
 * Maths: dividez, cbrt
 * Stats: skewness, kurtosis, iqr, quantile
 * Import/export JAMA [#JAMA]_ (Jython only)
 * phase, residual, normalise, centroid, crossings

This is an example of dataset usage::

    import scisoftpy as dnp
    
    # create a 1D dataset from 0 to 9 of doubles
    a = dnp.arange(10)
    # make it have 2 rows and 5 columns
    a.shape = 2,5
    a
    # create new dataset with each element raised to power of 5/2
    b = dnp.power(a,2.5)
    # create new dataset that is sum of two datasets
    c = a + b
    # modify dataset in-place by dividing each element by corresponding
    # element in other dataset
    c /= a
    
    # reassign a to a new (integer) dataset of 2 rows and 3 columns
    a = dnp.array([[0, 1, 2], [3, 4, 5]])
    # create new dataset from a slice of dataset which takes just the 2nd column
    d = a[:,1]
    # modify dataset and set a slice to a given value
    a[1,0:1] = -2

    # b is a double dataset (highest type that can contain all entries is used)
    b = dnp.array([[-2, 2.3], [1.2, 9.3]])
    # modify dataset and set a slice to the values in another dataset
    a[:,1:] = b
    # notice the values from b are truncated to integers
    a

    # in Jython only
    import Jama.Matrix as Matrix
    #  make a dataset from a Jama Matrix
    m = Matrix([[0,1,1.5],[2,3,3.5]])
    dj = dnp.array(m) # directly
    da = dnp.array(m.getArray()) # from Java array
    db = dnp.array([m.getArray(), m.getArray()]) # from list of arrays

    import scisoftpy.random as drd
    # create dataset of shape 3,12 of uniform random numbers between 0 and 1
    a = drd.rand(3,12)
    # take item-wise sine
    dnp.sin(a)
    # create dataset of shape 3,4 of random integers from 0 to 11 inclusive
    drd.randint(12, size=(3,4))

    # comparison and logic
    # create boolean datasets where items are true where condition applies 
    c = a >= 0.3
    d = a < 0.2
    # check if all or any of items in these are true
    all(c)
    any(c)
    all(d)
    all(d)
    # how many were true
    c.sum()
    d.sum()
    # flatten 1D dataset of items which were >= 0.3
    a[c]
    # assign value where items are < 0.4
    a[a < 0.4] = 0
    
    #

RGB dataset
-----------

When a colour image is loaded (as described in the next chapter), a RGB dataset
is created. This type of dataset has items which are tuples of three 16-bit
integers. Each integer represents a value of one of the colour channels. The
channels are ordered as red, green and blue. There are four extra attributes
to an RGB dataset, ``red``, ``green``, ``blue`` and ``grey`` which retrieve
copies of the colour channel or a weighted mixture of channels in the grey case.

There are also four extra methods::

    get_red(self, dtype=None)
    get_green(self, dtype=None)
    get_blue(self, dtype=None)
    get_grey(self, cweights=None, dtype=None)

where ``dtype`` is an optional dataset type (default is ``int16``) and
``cweights`` is an optional set of weight for combining the colour channel.
The default weights are (0.299, 0.587, 0.114) which correspond to the NTSC
formula for convert RGB to luma values.

.. _lazy-dataset:

Lazy dataset
------------

Sometimes a need arises to reference a multitude of datasets or very large
datasets. This need can occur when dealing with datasets held in tree
structured-file formats. The memory usage could easily exceed most
computers' capabilities, so to facilitate this need, the concept of a lazy
dataset is required. The laziness refers to the deferred action of loading data
from file into memory. This in turn dictates that lazy datasets have few
properties:

`shape`
    shape of dataset

`name`
    name of dataset

`dtype`
	dataset type

The only access is provided by indexing or slicing a lazy dataset::

    v = lazy[0,1] # index an item
    s = lazy[1,:] # slice
    entire = lazy[...] # to load in entire dataset (do so with caution)


References
----------
.. [#JAMA] JAMA: http://math.nist.gov/javanumerics/jama/

