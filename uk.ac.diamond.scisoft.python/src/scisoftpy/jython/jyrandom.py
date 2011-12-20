###
# Copyright © 2011 Diamond Light Source Ltd.
# Contact :  ScientificSoftware@diamond.ac.uk
# 
# This is free software: you can redistribute it and/or modify it under the
# terms of the GNU General Public License version 3 as published by the Free
# Software Foundation.
# 
# This software is distributed in the hope that it will be useful, but 
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
# Public License for more details.
# 
# You should have received a copy of the GNU General Public License along
# with this software. If not, see <http://www.gnu.org/licenses/>.
###

'''
Random number generator package
'''

import uk.ac.diamond.scisoft.analysis.dataset.Random as _random

from jycore import asIterable as _asiter
from jymaths import ndarraywrapped as _npwrapped

@_npwrapped
def rand(*shape):
    '''Return dataset of given shape (or a single number) with samples taken
    from a uniform distribution between 0 and 1
    '''
    if len(shape) == 0:
        return _random.rand([1]).getObject([0])
    return _random.rand(shape)

@_npwrapped
def randn(*shape):
    '''Return dataset of given shape (or a single number) with samples taken
    from a normal distribution of zero mean and unit variance
    '''
    if len(shape) == 0:
        return _random.randn([1]).getObject([0])
    return _random.randn(shape)

def randint(low, high=None, size=None):
    '''Return dataset of given shape (or a single number) with samples taken
    from a discrete distribution of integers in the range [low, high)
    '''
    if high == None:
        high = low
        low = 0
    return random_integers(low, high-1, size)

@_npwrapped
def random_integers(low, high=None, size=None):
    '''Return dataset of given shape (or a single number) with samples taken
    from a discrete distribution of integers in the range [low, high]
    '''
    if high == None:
        high = low
        low = 0
    if size == None:
        return _random.random_integers(low, high, [1]).getObject([0])
    return _random.random_integers(low, high, _asiter(size))

@_npwrapped
def exponential(scale=1.0, size=None):
    '''Return dataset of given shape (or a single number) with samples taken
    from an exponential distribution of parameter scale
    '''
    if size == None:
        return _random.exponential(scale, [1]).getObject([0])
    return _random.exponential(scale, _asiter(size))

@_npwrapped
def poisson(lam=1.0, size=None):
    '''Return dataset of given shape (or a single number) with samples taken
    from a Poisson distribution of parameter lam
    '''
    if size == None:
        return _random.poisson(lam, [1]).getObject([0])
    return _random.poisson(lam, _asiter(size))

def seed(seed=None):
    '''Set seed to given value (or a value based on the current time in
    milliseconds since the Epoch)
    '''
    if seed == None:
        import time
        seed = int(time.time()*1000)
    _random.seed(seed)
