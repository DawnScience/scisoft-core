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

import org.eclipse.dawnsci.analysis.dataset.impl.LinearAlgebra as _linalg
from org.eclipse.dawnsci.analysis.dataset.impl.LinearAlgebra import NormOrder as _normorder

# from jycore import asIterable as _asiter
from jycore import _wrap, _jinput

from java.lang.Double import isInfinite as _isinf #@UnresolvedImport

class LinAlgError(Exception):
    pass

@_wrap
def eig(a):
    '''Eigen decomposition
    '''
    try:
        return _linalg.calcEigenDecomposition(a)
    except Exception, e:
        raise LinAlgError(e)

@_wrap
def eigvals(a):
    '''Eigenvalues
    '''
    try:
        return _linalg.calcEigenvalues(a)
    except Exception, e:
        raise LinAlgError(e)
@_wrap
def cholesky(a):
    '''Cholesky decomposition
    '''
    try:
        return _linalg.calcCholeskyDecomposition(a)
    except Exception, e:
        raise LinAlgError(e)

@_wrap
def qr(a, mode='full'):
    '''QR decomposition
    '''
    try:
        return _linalg.calcQRDecomposition(a)
    except Exception, e:
        raise LinAlgError(e)

@_wrap
def svd(a, full_matrices=1, compute_uv=1):
    '''Singular value decomposition
    '''
    try:
        return _linalg.calcSingularValueDecomposition(a)
    except Exception, e:
        raise LinAlgError(e)

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

    try:
        return _linalg.norm(x, order)
    except Exception, e:
        raise LinAlgError(e)

def cond(a, order=None):
    '''Condition number
    x -- input array
    ord -- None  = 2-norm computed directly using SVD
           'fro' = Frobenius norm
           inf   = max(sum(abs(x),axis=1))
           -inf  = min(sum(abs(x),axis=1))
           0     = n/a                     | sum(x != 0)
           1     = max(sum(abs(x),axis=0))
           -1    = min(sum(abs(x),axis=0))
           2     = 2-norm (largest s.v.)  
           -2    = smallest singular value
           other = n/a                    
    Return norm
    '''
    if order is None:
        try:
            return _linalg.calcConditionNumber(_jinput(a))
        except Exception, e:
            raise LinAlgError(e)
    return norm(a, order)*norm(pinv(a), order)

@_wrap
def det(a):
    '''Determinant
    '''
    try:
        return _linalg.calcDeterminant(a)
    except Exception, e:
        raise LinAlgError(e)

@_wrap
def solve(a, b):
    '''Solve equation a x = b
    '''
    try:
        return _linalg.solve(a, b)
    except Exception, e:
        raise LinAlgError(e)

@_wrap
def inv(a):
    '''Inverse of square array
    '''
    try:
        return _linalg.calcInverse(a)
    except Exception, e:
        raise LinAlgError(e)

@_wrap
def pinv(a, rcond=1e-15):
    '''Pseudo-inverse of array
    '''
    try:
        return _linalg.calcPseudoInverse(a)
    except Exception, e:
        raise LinAlgError(e)

@_wrap
def matrix_power(a, n):
    '''Raise matrix to given power
    '''
    try:
        return _linalg.power(a, n)
    except Exception, e:
        raise LinAlgError(e)

