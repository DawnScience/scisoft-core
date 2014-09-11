###
# Copyright 2014 Diamond Light Source Ltd.
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
Linear algebra package
'''

import uk.ac.diamond.scisoft.analysis.dataset.LinearAlgebra as _linalg
from uk.ac.diamond.scisoft.analysis.dataset.LinearAlgebra import NormOrder as _normorder

# from jycore import asIterable as _asiter
from jycore import _wrap

from java.lang.Double import isInfinite as _isinf #@UnresolvedImport

@_wrap
def norm(x, order=None):
    '''Matrix or vector norm
    x -- input array
    ord -- None  = Frobenius               | 2-norm
           'fro' = Frobenius               | n/a
           inf   = max(sum(abs(x),axis=1)) | max(abs(x))
           -inf  = min(sum(abs(x),axis=1)) | min(abs(x))
           0     = n/a                     | sum(x != 0)
           1     = max(sum(abs(x),axis=0)) | as below
           -1    = min(sum(abs(x),axis=0)) | as below
           2     = 2-norm (largest s.v.)   | as below
           -2    = smallest singular value | as below
           other = n/a                     | sum(abs(x)**ord)**(1./ord)
    Return norm
    '''
    if order is None:
        return _linalg.norm(x)
    if order == 'fro':
        order = _normorder.FROBENIUS # @UndefinedVariable
    elif _isinf(order):
        if order > 0:
            order = _normorder.POS_INFINITY # @UndefinedVariable
        else:
            order = _normorder.NEG_INFINITY # @UndefinedVariable

    return _linalg.norm(x, order)
