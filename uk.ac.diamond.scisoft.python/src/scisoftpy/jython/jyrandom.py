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
