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
Maths package
'''

import numpy as _np #@UnresolvedImport

from math import pi as _ppi
from math import e as _e

pi = _ppi
e = _e
inf = _np.inf
nan = _np.nan

isnan = _np.isnan

import sys
floatmax = sys.float_info.max # maximum float value #@UndefinedVariable

add = _np.add

subtract = _np.subtract

multiply = _np.multiply

divide = _np.divide

floor_divide = _np.floor_divide

remainder = _np.remainder

fmod = _np.fmod

mod = _np.mod

reciprocal = _np.reciprocal

abs = _np.abs #@ReservedAssignment

absolute = _np.absolute

fabs = _np.fabs

angle = _np.angle

conjugate = _np.conjugate

conj = _np.conj

sin = _np.sin
cos = _np.cos
tan = _np.tan

arcsin = _np.arcsin
arccos = _np.arccos
arctan = _np.arctan
arctan2 = _np.arctan2
hypot = _np.hypot

sinh = _np.sinh
cosh = _np.cosh
tanh = _np.tanh

arcsinh = _np.arcsinh
arccosh = _np.arccosh
arctanh = _np.arctanh

log = _np.log
log2 = _np.log2
log10 = _np.log10
log1p = _np.log1p

exp = _np.exp
expm1 = _np.expm1

sqrt = _np.sqrt

square = _np.square
power = _np.power

floor = _np.floor
ceil = _np.ceil
rint = _np.rint

rad2deg = _np.rad2deg
deg2rad = _np.deg2rad

degrees = rad2deg
radians = deg2rad

sign = _np.sign
negative = _np.negative

clip = _np.clip

prod = _np.prod
sum = _np.sum #@ReservedAssignment

mean = _np.mean
std = _np.std
var = _np.var
ptp = _np.ptp

amax = _np.amax
amin = _np.amin

median = _np.median
cumprod = _np.cumprod
cumsum = _np.cumsum

diff = _np.diff

dot = _np.dot
vdot = _np.vdot
inner = _np.inner
#outer = _np.outer
tensordot = _np.tensordot

