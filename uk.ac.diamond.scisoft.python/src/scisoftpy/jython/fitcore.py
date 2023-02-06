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

from __future__ import print_function
from uk.ac.diamond.scisoft.analysis.fitting.functions import Parameter as _param
from uk.ac.diamond.scisoft.analysis.fitting.functions import AFunction as _absfn
from uk.ac.diamond.scisoft.analysis.fitting.functions import CompositeFunction as _compfn
from uk.ac.diamond.scisoft.analysis.fitting import Fitter as _fitter
from org.eclipse.dawnsci.analysis.api.fitting.functions import IOperator as _operator

import scisoftpy as _dnp

_asIterable = _dnp.asIterable
_toList = _dnp.toList
_asDS = _dnp.asDataset
_sciwrap = _dnp.Sciwrap

from scisoftpy.jython.jycore import _wrap, _wrapin, _wrapout, __cvt_jobj, _jinput, _argsToArrayType

from . import function

def _createparams(np, params, bounds):
    '''Create a Parameters list with bounds, popping off items from both input lists
    np     -- number of parameters
    params -- list of initial values
    bounds -- list of tuples of bounds
    '''

    if np > len(params):
        raise ValueError("Number of parameters supplied is less than that required")

    pl = [ _param(params.pop(0)) for _i in range(np) ]

    nbound = len(bounds)
    if nbound > np:
        nbound = np

    for i in range(nbound):
        b = bounds.pop(0)
        pli = pl[i]
        if b is not None:
            b = _asIterable(b)
            if b[0] is not None:
                pli.lowerLimit = b[0]
            if len(b) > 1:
                if b[1] is not None:
                    pli.upperLimit = b[1]
#    print [(p.value, p.lowerLimit, p.upperLimit) for p in pl]
    return pl

class fitfunc(_absfn):
    '''Class to wrap an ordinary Jython function for fitting.
    That function should take two arguments:
    p -- list of parameter values
    coords -- coordinates array (or list of such)
    *args -- optional arguments
    '''
    def __init__(self, fn, name, plist, *args):
        '''
        This constructor consumes creates a fit function from given jython function and parameter list

        Arguments:
        fn     -- function
        name   -- function name
        plist  -- list of Parameter objects
        '''
        self.n_params = len(plist)
        _absfn.__init__(self, plist) #@UndefinedVariable
        self.func = fn
        self.args = args
        self.name = name

    def setNames(self):
        pass

    def getNoOfParameters(self):
        return self.n_params

    @_wrap
    def val(self, coords):
        '''Evaluate function at single set of coordinates
        '''
        try:
            l = [p for p in self.parameterValues]
            l.append([_dnp.array(c) for c in _toList(coords)])
            l.append(self.args)
            v = self.func(*l)
            return float(v.data[0])
        except ValueError:
            raise ValueError('Problem with function \"' + self.name + '\" at coord ' + coords + ' with params  ' + self.parameterValues)

    def makeDataset(self, *coords):
        return self.calculateValues(coords)

    def calculateValues(self, *coords):
        '''Evaluate function across given coordinates
        '''
        try:
            l = [p for p in self.parameterValues]
            l.append([_dnp.array(c) for c in _toList(coords)])
            l.append(self.args)
            d = self.func(*l)
            d.name = self.name
            return d
        except ValueError:
            raise ValueError("Problem with function '" + self.name + "' with params  " + self.parameterValues)

    def residual(self, allvalues, data, weight, *coords):
        '''Find residual as sum of squared differences of function and data
        
        Arguments:
        allvalues -- boolean, currently ignored
        data      -- used to subtract from evaluated function
        weight    -- weighting for each squared difference, can be None
        coords    -- coordinates over which the function is evaluated
        '''
        if len(coords) == 0: # workaround deprecated method
            coords = (weight,)
            weight = None
        try:
            l = [p for p in self.parameterValues]
            l.append([_sciwrap(c) for c in coords])
            l.append(self.args)
            d = self.func(*l)
            return _dnp.residual(d, data, weight)
        except ValueError:
            raise ValueError("Problem with function '" + self.name + "' with params  " + self.parameterValues)

class cfitfunc(_compfn):
    '''Composite function for situation where there's a mixture of jython and Java fitting functions
    '''
    def __init__(self):
        _compfn.__init__(self) #@UndefinedVariable

    @_wrap
    def val(self, coords):
        '''Evaluate function at single set of coordinates
        '''
        v = 0.
        for n in range(self.noOfFunctions):
            v += self.getFunction(n).val(coords)
        return v

    def makeDataset(self, coords):
        return self.calculateValues(coords)

    def calculateValues(self, coords):
        '''Evaluate function across given coordinates
        '''
        vt = None
        for n in range(self.noOfFunctions):
            v = _sciwrap(self.getFunction(n).calculateValues(*coords))
            if vt is None:
                vt = v
            else:
                vt += v
        return vt

    def residual(self, allvalues, data, weight, coords):
        '''Find residual as sum of squared differences of function and data
        
        Arguments:
        allvalues -- boolean, currently ignored 
        data      -- used to subtract from evaluated function
        weight    -- weighting for each squared difference, can be None
        coords    -- coordinates over which the function is evaluated
        '''
        if len(coords) == 0: # workaround deprecated method
            coords = (weight,)
            weight = None
        return _dnp.residual(self.calculateValues(coords), data, weight)


class fitresult(object):
    '''This is used to contain results from a fit
    '''
    def __init__(self, func, coords, data, weight=None):
        '''Arguments:
        func    -- function after fitting as occurred
        coords  -- coordinate(s)
        data    -- scalar data that was fitted to
        weight -- array used to weight residuals (it multiplies the squared differences between function and data)
        '''
        self.func = func
        self.coords = coords
        self.data = data
        self.weight = weight

    def _calcdelta(self, coords):
        delta = 1.
        r = coords[0].ndim
        if r > 1:
            for n in range(len(coords)):
                x = coords[n]
                if x.ndim != r:
                    raise ValueError("Given coordinates are not all of same rank")
                delta *= x.ptp()/x.shape[n]
                n += 1
        else:
            for x in coords:
                if x.ndim != 1:
                    raise ValueError("Given coordinates are not all 1D")
                delta *= x.ptp()/x.size
        return delta

    def __getitem__(self, key):
        '''Get specified parameter value
        '''
        try:
            return self.func.getParameterValue(key)
        except:
            raise IndexError

    def __len__(self):
        '''Number of parameters
        '''
        return self.func.getNoOfParameters()

    def makeplotdata(self, all=True, log10=False):
        '''Make a list of datasets to plot
        all   -- if True, then make datasets for all constituent functions and difference
        '''
        odata = self.makefuncdata(all=all)
        odata.insert(0, self.data)
        
        pdata = [ _dnp.log10(_dnp.abs(p)) for p in odata ] if log10 else odata
        if log10:
            lomin = pdata[0].min() # crop to log10(data)
            pdata = [ _dnp.maximum(p, lomin) for p in pdata ]

        if all:
            edata = self.data - odata[1]
            pdata.append(edata)
        return pdata

    def makefuncdata(self, all=True):
        '''Make a list of datasets for composite fitting function and its components
        all   -- if True, then make datasets for all constituent functions and difference
        '''
        nf = self.func.noOfFunctions
        coords = _jinput(self.coords)
        if nf >= 1:
            fdata = [_sciwrap(self.func.calculateValues(coords))]
            if all:
                if nf > 1:
                    fdata[0].name = "Composite function"
                for n in range(nf):
                    fdata.append(_sciwrap(self.func.getFunction(n).calculateValues(*coords)))
        else:
            fdata = []

        return fdata

    def plot(self, title=None, name=None, all=False, log10=False):
        '''Plot fit as 1D
        title -- title of plot
        name  -- name of plot view to use (if None, use default name)
        all   -- if True, then plot all constituent functions and difference
        log10 -- if True, then take log10 of data and function (
        '''
        pd = self.makeplotdata(all=all, log10=log10)
        if all:
            ed = pd.pop()
        _dnp.plot.line(self.coords[0], pd, title, name)
        if all:
            _dnp.plot.addline(self.coords[0], {('', 'right'):(ed, 'error')}, name)

    def _parameters(self):
        '''Array of all parameter values
        '''
        return _asDS([p for p in self.func.getParameterValues()])
    parameters = property(_parameters)

    def _parameter_bnds(self):
        '''List of all parameter bounds
        '''
        return [(p.getLowerLimit(), p.getUpperLimit()) for p in self.func.getParameters()]
    parameter_bnds = property(_parameter_bnds)

    def _residual(self):
        '''Residual of fit
        '''
        return self.func.residual(True, _jinput(self.data), None, _jinput(self.coords))
    residual = property(_residual)

    def _area(self):
        '''Area or hypervolume under fit assuming coordinates are uniformly spaced
        '''
        deltax = self._calcdelta(self.coords)
        return _sciwrap(self.func.calculateValues(_jinput(self.coords))).sum() * deltax
    area = property(_area)

    def __str__(self):
        out = "Fit parameters:\n"
        if isinstance(self.func, _operator):
            nf = self.func.noOfFunctions
            for n in range(nf):
                f = self.func.getFunction(n)
                p = [q for q in f.getParameterValues()]
                np = len(p)
                out += "    function '%s' (%d) has %d parameters = %s\n" % (f.name, n, np, p)
        else:
            f = self.func.getFunction(n)
            p = [q for q in f.getParameterValues()]
            np = len(p)
            out += "    function '%s' has %d parameters = %s\n" % (f.name, np, p)
        out += "Residual: %g\n" % self.residual
        return out

import inspect as _inspect

ALL_OPTIMIZERS = ('simplex','genetic', 'gradient','apache_nm','apache_md','apache_cg')

def fit(func, coords, data, p0, bounds=[], args=None, weight=None, ptol=1e-4, seed=None, optimizer="apache_nm"):
    '''
    Arguments:
    func      -- function (or list of functions)
    coords    -- coordinate dataset(s)
    data      -- data to fit
    p0        -- list of initial parameters
    bounds    -- list of parameter bounds, bounds are tuples of lower and upper values (any can be None)
    args      -- extra arguments (for functions)
    weight    -- dataset used to weight residuals (it multiplies the squared differences between function and data)
    ptol      -- parameter fit tolerance
    seed      -- seed value for genetic algorithm-based optimiser
    optimizer -- description of the optimizer to use, e.g. ['local','global','simplex','genetic',
                 'gradient','apache_nm','apache_md','apache_cg','apache_lm']
                 local and global are general settings, which point the one of the specific methods
                 (Nelder Mead, Genetic). If any global methods are picked, the bounds argument must also be filled in.
    Returns:
    fitresult object
    '''
    fnlist = []
    func = _toList(func)
    p0 = _toList(p0)
    if not isinstance(bounds, list):
        bounds = [bounds]
    else:
        bounds = list(bounds) # make a copy
    n_bounds = len(bounds)
    mixed = False
    for f in func:
        if isinstance(f, tuple):
            print("parameter count is no longer required")
            f = f[0]
        if function.isjclass(f):
            # create bound function object
            np = function.nparams(f)
            pl = _createparams(np, p0, bounds)
            fnlist.append(f(pl))
        elif not _inspect.isfunction(f):
            # instantiated Java function
            # np = f.getNoOfParameters()
            fnlist.append(f)
        else:
            np = len(_inspect.getargspec(f)[0]) - 1
            if np < 1:
                raise ValueError("Function needs more than one argument (i.e. at least one parameter)")
            pl = _createparams(np, p0, bounds)
            fnlist.append(fitfunc(f, f.__name__, pl, args))
            mixed = True

    if not mixed: # no jython functions
        cfunc = _compfn()
    else:
        cfunc = cfitfunc()
    for f in fnlist:
        if function.isjmethod(f): # unwrap
            f = f._jfunc()
        cfunc.addFunction(f)

    coords = list(_asIterable(coords))
    for i in range(len(coords)): # check and slice coordinates to match data
        c = coords[i]
        if c.shape != data.shape:
            ns = [slice(d) for d in data.shape]
            coords[i] = c[ns]

    if seed:
        _fitter.seed = int(seed)

    jcoords = [ __cvt_jobj(c, copy=False, force=True) for c in coords ]
    jdata = data._jdataset()
    jweight = None if weight is None else weight._jdataset()

    # use the appropriate fitter for the task
    if optimizer == 'local':
        _fitter.ApacheNelderMeadFit(jcoords, jdata, jweight, cfunc)
    elif optimizer == 'global':
        if n_bounds == 0:
            print("Using a global optimizer with no bounds is unlikely to work, please use the bounds argument to narrow the search space")
        _fitter.geneticFit(ptol, jcoords, jdata, jweight, cfunc)
    elif optimizer == 'simplex':
        _fitter.simplexFit(ptol, jcoords, jdata, jweight, cfunc)
    elif optimizer == 'gradient':
        _fitter.GDFit(ptol, jcoords, jdata, jweight, cfunc)
    elif optimizer == 'apache_nm':
        _fitter.ApacheNelderMeadFit(jcoords, jdata, jweight, cfunc)
    elif optimizer == 'apache_md':
        _fitter.ApacheMultiDirectionFit(jcoords, jdata, jweight, cfunc)
    elif optimizer == 'apache_cg':
        _fitter.ApacheConjugateGradientFit(jcoords, jdata, jweight, cfunc)
    elif optimizer == 'apache_lm':
        _fitter.ApacheLevenbergMarquardtFit(jcoords, jdata, jweight, cfunc)
    elif optimizer == 'genetic':
        if n_bounds == 0:
            print("Using a global optimizer with no bounds is unlikely to work, please use the bounds argument to narrow the search space")
        _fitter.geneticFit(ptol, jcoords, jdata, jweight, cfunc)

    return fitresult(cfunc, coords, data, weight)

# genfit = _genfit

def _polycoeff(roots):
    '''Calculate polynomial coefficients from roots'''
    nr = len(roots)
    oc = [1.0]
    for n in range(nr):
        r = -roots[n]
        nc = [r*c for c in oc]
        for m in range(n):
            oc[m+1] += nc[m]
        oc.append(nc[n])
    return oc

from uk.ac.diamond.scisoft.analysis.fitting.functions import Polynomial as _poly

from jarray import zeros as _zeros
_empty_int_array = _zeros(0, 'i') # this is used for resolving some overloaded methods

class poly1d(object):
    '''1D polynomial class'''
    def __init__(self, c_or_r, r=False, variable=None):
        if variable:
            self.variable = variable
        else:
            self.variable = 'x'
        if r:
            self.order = len(c_or_r)
            # expansion of factors
            self.c = _polycoeff(c_or_r)
            self.roots = c_or_r
        else:
            self.order = len(c_or_r) - 1
            self.c = c_or_r
            self.roots = None
        self._poly = _poly(self.c)

    def __str__(self):
        par  = self.c
        if self.order > 1:
            base = str(par[0]) + ' ' + self.variable
            spow = str(self.order)
            sup  = ' '*len(base) + spow
            base += ' '*len(spow)
        elif self.order == 1:
            base = str(par[0]) + ' ' + self.variable
            sup  = ' '*len(base)
        else:
            base = str(par[0])
            sup  = ' '*len(base)
            
        for i in range(1, self.order + 1):
            p = par[i]
            if p > 0:
                term = ' + ' + str(p)
            elif p < 0:
                term = ' - ' + str(-p)
            else:
                continue

            lpow = self.order - i
            if lpow > 1:
                term += ' ' + self.variable
                spow = str(lpow)
                sup  += ' '*len(term) + spow
                base += term + ' '*len(spow)
            elif lpow == 1:
                term += ' ' + self.variable
                sup  += ' '*len(term)
                base += term
            else:
                sup  += ' '*len(term)
                base += term

        return sup + '\n' + base + '\n'

    def __getitem__(self, k):
        return self.c[-(k+1)]

    def _getroots(self):
        if self.roots is None:
            self.roots = _asDS(self._poly.findRoots())
        return self.roots

    r = property(_getroots)

    def __call__(self, a):
        v = self._poly.calculateValues([_asDS(a, force=True)._jdataset()])
        if v.getRank() == 0:
            return v.getDouble(_empty_int_array)
        return _asDS(v)

@_wrapin('x', 'y')
def polyfit(x, y, deg, rcond=None, full=False):
    '''Linear least squares polynomial fit

    Fit a polynomial p(x) = p[0] * x**deg + ... + p[deg] of order deg to points (x, y).
    Arguments:
    x   -- x-coordinates of sample points
    y   -- y-coordinates of sample points
    deg -- order of fitting polynomial
    
    Returns:
    a vector of coefficients p that minimises the squared error and
    a fitresult object
    '''
    poly = _poly(deg)
    x = _asIterable(x)
    if rcond is None:
        rcond = 2e-16*y.size
    _fitter.polyFit(x, y, rcond, poly)
    fr = fitresult(poly, x, y)
    if full:
        return fr.parameters, fr
    else:
        return fr.parameters

@_wrapout('p', 'x')
def polyval(p, x):
    '''Evaluate polynomial at given points
    If p is of length N, this function returns the value:
    p[0]*(x**N-1) + p[1]*(x**N-2) + ... + p[N-2]*x + p[N-1]
    '''
    poly = _poly(_asDS(p, _dnp.float64)._jdataset().data)
    d = _asDS(x, _dnp.float, force=True)._jdataset()
    return poly.calculateValues([d])

@_argsToArrayType('coeffs')
def roots(coeffs):
    '''Find roots of polynomial specified by coefficients
    '''
    poly = _poly(_asDS(coeffs, _dnp.float64)._jdataset().data) # static method call fails
    return _asDS(poly.findRoots()) # cannot rely on general wrapper with arrays of stuff

# need a cspline fit function

from org.eclipse.dawnsci.analysis.dataset.roi.fitting import EllipseFitter as _efitter

def ellipsefit(x, y, geo=True, init=None):
    '''Ellipse fit
    
    Fit an ellipse to a set of points (in 2D).
    Arguments:
    x   -- x-coordinates of sample points
    y   -- y-coordinates of sample points
    geo -- flag to use for geometric (slow) or algebraic (fast) fitting
    init -- initial parameters

    Returns:
    a vector of geometric parameters (major, minor semi-axes, major axis angle, centre coordinates)
    '''
    f = _efitter()
    if geo:
        f.geometricFit(_asDS(x), _asDS(y), init)
    else:
        f.algebraicFit(_asDS(x), _asDS(y))

    return f.parameters


@_wrap
def makeellipse(p, t=None):
    '''Generate two datasets containing coordinates for points on an ellipse

    Arguments:
    p -- geometric parameters (major, minor semi-axes, major axis angle, centre coordinates)
    t -- array of angles (can be None for a 100-point array spanning 2 pi)
    '''
    if t is None:
        t = _dnp.arange(100)*_dnp.pi/50.

    return _efitter.generateCoordinates(_asDS(t), p) #@UndefinedVariable

