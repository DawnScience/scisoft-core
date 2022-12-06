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
Wrapper of plotting functionality in DAWN
'''

from __future__ import print_function
import os
import sys


if os.name == 'java':
    from .jython import jyplot as _plot
    _plot_set_port = _plot.setremoteport
    from .jython import jycore as _core #@Reimport @UnusedImport
    from .jython import jybeans as _beans #@Reimport @UnusedImport
else:
    from .python import pyplot as _pyplot
    _plot_set_port = _pyplot.setremoteport
    _plot = _pyplot.plotter()
    from .python import pycore as _core #@Reimport
    from .python import pybeans as _beans #@Reimport

_plot_line = _plot.plot_line
_plot_addline = _plot.plot_addline
_plot_updateline = _plot.plot_updateline
_plot_image = _plot.plot_image
_plot_images = _plot.plot_images
_plot_setupimagegrid = _plot.plot_setupimagegrid
_plot_imagetogrid = _plot.plot_imagetogrid
_plot_surface = _plot.plot_surface
_plot_stack = _plot.plot_stack
_plot_addstack = _plot.plot_addstack
_plot_updatestack = _plot.plot_updatestack
_plot_points2d = _plot.plot_points2d
_plot_updatepoints2d = _plot.plot_updatepoints2d
_plot_points3d = _plot.plot_points3d
_plot_updatepoints3d = _plot.plot_updatepoints3d
_plot_scanforimages = _plot.plot_scanforimages
_plot_viewtree = _plot.plot_viewtree
_plot_volume = _plot.plot_volume

_plot_createaxis = _plot.plot_createaxis
_plot_renameactiveaxis = {'x':_plot.plot_renameactivexaxis, 'y':_plot.plot_renameactiveyaxis}

_plot_clear = _plot.plot_clear
_plot_export = _plot.plot_export

__orders = _plot.plot_orders

_exception = _beans.exception
parameters = _beans.parameters
plotmode = _beans.plotmode
_guibean = _beans.guibean
bean = _guibean
axismapbean = _beans.axismapbean
datasetwithaxisinformation = _beans.datasetwithaxisinformation
databean = _beans.databean

_plot_getbean = _plot.plot_getbean
_plot_setbean = _plot.plot_setbean
_plot_getdatabean = _plot.plot_getdatabean
_plot_setdatabean = _plot.plot_setdatabean

getguinames = _plot.plot_getguinames
window_manager = _plot.plot_window_manager

def volume(v, x=None, y=None,z = None, name=None):
    '''Plot a volume dataset in remote volume view
    '''
    
    if name is None:
        name = _PVNAME
    
    _plot_volume(name, x, y, z, v)


from . import roi


_toList = _core.toList

_PVNAME = "Plot 1"

def setdefname(name):
    '''Assign a default plot view name used by all plotters
    This default name starts as "Plot 1"
    '''
    global _PVNAME
    _PVNAME = name
    
def setremoteport(rpcport=0, rmiport=0):
    '''Connect over RMI or Analysis RPC to the given port.
       If RMI is used (default in Jython for SDS) rpcport is ignored.
       If Analysis RPC is used (default in Python) rmiport is ignored.
       If CORBA is used, both rpc and rmi ports are ignored.
       Values of 0 mean use the default port.'''
    _plot_set_port(rpcport=rpcport, rmiport=rmiport)
    
def _order(order):
    try:
        return __orders[order]
    except KeyError:
        raise ValueError("Given order not one of none, alpha, chrono")

def clear(name=None):
    '''Clear plot

    Argument:
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    _clear_axis(name)
    _plot_clear(name)

_FILE_TYPES = {"SVG File":("svg",), "Postscript File":("ps", "eps"),
               "PNG/JPEG File":("png", "jpg", "jpeg")}

def export(path=None, format=None, name=None):  # @ReservedAssignment
    '''Export plot to svg, png, jpg, eps, ps

    Argument:
    path -- full path and filename of the file to export to (if none, the filename will be 'exported.' + format)
    format -- format of the file to export to: can be 'svg', 'png', 'jpg', 'eps' or 'ps' (if None, svg is used by default)
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    if format is None:
        format = "svg"  # @ReservedAssignment
    else:
        format = format.lower()  # @ReservedAssignment
    eformat = None
    for f in _FILE_TYPES:
        if format in _FILE_TYPES[f]:
            eformat = f
            break
    if not eformat:
        raise ValueError("format '%s' is not known" % format)
    if path is None:
        path = "exported." + format

    _plot_export(name, eformat, path)

'''
Store a global list of x and y axes names in a per-horizontal/vertical dictionary per plot name
'''
_DEF_NAMES = {'x':'X-Axis', 'y':'Y-Axis'}

_AXES_NAMES = { 'x':{}, 'y':{} }

def _parselinearg(x, y, title, name):
    '''x and y can be lists of arrays, tuples or single-item dicts
    (each tuple comprises an array and label)
    (each dict comprises an axis name (or tuple of axis name/position) and array (or array/label tuple))
    '''
    if y is None:
        if isinstance(x, (dict, tuple)):
            yl = [x]
        else:
            yl = _toList(x)
        x = None
    elif isinstance(y, (dict, tuple)):
        yl = [y]
    else:
        yl = _toList(y)

    if x is None:
        xl = None
    else:
        if isinstance(x, dict):
            xl = [x]
        else:
            xl = _toList(x)
        if len(xl) == 1:
            x = xl[0]
            if isinstance(x, dict): # has axis name
                x = list(x.values())[0]
            xLength = x.shape[0]
            for i in yl:
                if isinstance(i, dict): # has axis name
                    i = list(i.values())[0]
                if isinstance(i, (list, tuple)): # has y dataset labelling
                    i = i[0]
                if xLength != i.shape[0]:
                    raise AttributeError("length of y does not match the length of x" )
        elif len(xl) != len(yl):
            raise ValueError("number of x datasets should be equal to number of y datasets")
        else:
            for n in range(len(xl)):
                i = xl[n]
                j = yl[n]
                if isinstance(i, dict): # has axis name
                    i = list(i.values())[0]
                if isinstance(j, dict): # has axis name
                    j = list(j.values())[0]
                if isinstance(j, (list, tuple)): # has y dataset labelling
                    j = j[0]
                if i is None:
                    i = _core.arange(j.size, dtype=_core.int)
                    i.shape = j.shape
                    xl[n] = i
                elif i.shape[0] != j.shape[0]:
                    raise AttributeError("length of y does not match the length of x")

    return name, title, xl, yl

_AXES_SIDES = { 'x':{'default':_plot.axis_bottom, 'top':_plot.axis_top, 'bottom':_plot.axis_bottom},
              'y':{'default':_plot.axis_left, 'left':_plot.axis_left, 'right':_plot.axis_right} }

def _setup_axes(al, dirn, name, allow_rename):
    c = 0 # count use of default axis
    for a in al:
        if isinstance(a, dict): # has axis name
            n = list(a.keys())[0]
            if isinstance(n, tuple): # has side info
                n = n[0]
            if n == _DEF_NAMES[dirn]:
                c += 1
        else:
            c += 1

    rename = c == 0 and allow_rename
    an = []
    for a in al:
        if isinstance(a, dict): # has axis name
            n = list(a.keys())[0]
            rename, n = _setup_axis(rename, n, dirn, name)
            an.append(n)
        else:
            an.append(_DEF_NAMES[dirn])
    return an

def _setup_axis(rename, n, dirn, name):
    '''
    n is axis name (or axis name and side)
    '''
    if isinstance(n, tuple): # has side info
        s = _AXES_SIDES[dirn][n[1]]
        n = n[0]
    else:
        s = _AXES_SIDES[dirn]['default']
    al = _AXES_NAMES[dirn].get(name)
    if al is None: # create initial list of axis name for this named plot
        al = [_DEF_NAMES[dirn]]
        _AXES_NAMES[dirn][name] = al

    if n not in al:
        if rename:
            _plot_renameactiveaxis[dirn](name, n) # use default/selected axis
            al[0] = n
            rename = False
        else:
            _plot_createaxis(name, n, s)
            al.append(n)
    return rename, n

def _clear_axis(name):
    for dirn in ['x', 'y']:
        al = _AXES_NAMES[dirn].get(name)
        if al:
            al[0] = _DEF_NAMES[dirn]
            for n in al[1:]:
                al.remove(n)

def _process_line(x, y, title, name, mode):
    name, t, xl, yl = _parselinearg(x, y, title, name)

    allow_rename = mode is None
    if allow_rename:
        _plot_clear(name)
        _clear_axis(name)

    if xl is not None:
        ax = _setup_axes(xl, 'x', name, allow_rename)
    else:
        ax = None
    ay = _setup_axes(yl, 'y', name, allow_rename)

    # generate list of axes
    xs = []
    ys = []
    yn = [] # list of y names
    for i in range(len(yl)):
        if xl is not None:
            xi = xl[0] if len(xl) == 1 else xl[i]
            if isinstance(xi, dict): # has axis name
                _, xi = list(xi.items())[0]
            if len(xl) == 1:
                if i == 0:
                    xs.append(xi)
            else:
                xs.append(xi)

        yi = yl[i]
        yt = None
        if isinstance(yi, dict): # has axis name
            _, yi = list(yi.items())[0]
        if isinstance(yi, (list, tuple)): # has y dataset labelling
            yi, yt = yi[0], yi[1] 
        ys.append(yi)
        yn.append(yt)

    for a in xs: # if all None then make it None
        if a is not None:
            break
    else:
        xs = None
    for a in yn: # if all None then make it None
        if a is not None:
            break
    else:
        yn = None

    if mode is None:
        _plot_line(name, t, xs, ys, yn, ax, ay)
    else:
        _plot_addline(name, t, xs, ys, yn, ax, ay)

def line(x, y=None, title=None, name=None):
    '''
    Plot y dataset (or list of datasets), optionally against any
    given x dataset in the named view

    Arguments:
    :param x: optional dataset or list of datasets for x values; can have None as placeholders for index values
    :param y: dataset or list of datasets
    :param title: title of plot
    :param name: name of plot view to use (if None, use default name)

    For example,
    >>> import scisoftpy as dnp
    >>> a = dnp.arange(1,10.)
    >>> b = dnp.arange(3,14.)
    >>> dnp.plot.line([a,a+12.3]) # plots two lines against array index
    >>> dnp.plot.line(2*a, [a,a+12.3]) # plots two lines against 2*a
    >>> dnp.plot.line([2*a, 3.5*b], [a,b]) # plots two lines against defined x values
    >>> dnp.plot.line([2*a, None], [a,b]) # plots two lines against defined x values and index values

    To plot with alternative axes, x and y can be single-item dictionaries or lists of
    dictionaries. Each dictionary pairs an axis as a key with the value being a dataset.
    The axis key can be a string which is used as an axis name or a tuple of name and
    position. The position is a string with recognised values: "bottom" (default) and
    "top", "left" (default) and "right" for horizontal and vertical axes, respectively.

    For example,
    >>> rads = dnp.linspace(0, dnp.pi, 21)
    >>> degs = dnp.linspace(0, 180, 31)
    >>> ysin = dnp.sin(rads)
    >>> ycos = dnp.cos(dnp.radians(degs))
    >>> dnp.plot.line([rads, {"degrees":degs}], [ysin, {("cos","right"):ycos}])

    Finally, key labels can be added to each line by specifying a tuple of dataset and string
    for each y dataset.
    >>> dnp.plot.line([rads, {"degrees":degs}], [(ysin, "sin in rads") , {("cos", "right"):(ycos, "cos in degs")}])
    '''
    if name is None:
        name = _PVNAME
    _process_line(x, y, title, name, None)

def addline(x, y=None, title=None, name=None):
    '''Add line(s) to existing plot, optionally against
    any given x dataset in the named view

    Arguments:
    x -- optional dataset or list of datasets for x values
    y -- dataset or list of datasets
    title -- title of plot
    name -- name of plot view to use (if None, use default name)
    
    See also:
    line
    '''
    if name is None:
        name = _PVNAME
    _process_line(x, y, title, name, 'add')

def updateline(x, y=None, title=None, name=None):
    '''Update existing plot by changing displayed y dataset (or list of datasets), optionally against
    any given x dataset in the named view

    Arguments:
    x -- optional dataset or list of datasets for x values
    y -- dataset or list of datasets
    title -- title of plot
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    n, t, xl, yl = _parselinearg(x, y, title, name)
    _plot_updateline(n, t, xl, yl, None, None)

plot = line
updateplot = updateline

def _checkimagearg(x, y, im):
    '''x and y can be arrays or single-item dicts
    (each dict comprises an axis name (or tuple of axis name/position) and array (can be None))
    '''

    if x is not None:
        if isinstance(x, dict): # has axis name
            x = list(x.values())[0]
        if x is not None and x.shape[0] != im.shape[1]:
            raise AttributeError("Width of image does not match the length of x" )

    if y is not None:
        if isinstance(y, dict): # has axis name
            y = list(y.values())[0]
        if y is not None and y.shape[0] != im.shape[0]:
            raise AttributeError("Height of image does not match the length of y" )

def _process_image(x, y, im, name, resetaxes):
    _checkimagearg(x, y, im)

    if resetaxes is True:
        _plot_clear(name)
        _clear_axis(name)

    if x is not None:
        ax = _setup_axes([x], 'x', name, resetaxes)[0]
    else:
        ax = None
    if y is not None:
        ay = _setup_axes([y], 'y', name, resetaxes)[0]
    else:
        ay = None

    if isinstance(x, dict): # has axis name
        _, x = list(x.items())[0]

    if isinstance(y, dict): # has axis name
        _, y = list(y.items())[0]

    _plot_image(name, x, y, im, ax, ay)

def image(im, x=None, y=None, name=None, resetaxes=True):
    '''Plot a 2D dataset as an image in the named view with optional x and y axes

    Arguments:
    im -- image dataset
    x -- optional dataset or single-item dict for x-axis
    y -- optional dataset or single-item dict for y-axis
    name -- name of plot view to use (if None, use default name)
    resetaxes -- reset axes (True/False: if not defined, axes are reset)
    '''
    if name is None:
        name = _PVNAME

    _process_image(x, y, im, name, resetaxes)

def images(im, x=None, y=None, name=None):
    '''Plot 2D datasets as an image in the named view with optional x and y axes

    Arguments:
    im -- image datasets (one or more)
    x -- optional dataset for x-axis
    y -- optional dataset for y-axis
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    if x is None:
        y = None
    if y is None:
        x = None

    raise NotImplementedError("Old implementation removed; not currently implemented")
#     _plot_images(name, x, y, _toList(im))

def setupimagegrid(row, column, name=None):
    '''Set up a new image grid for an image explorer view with the specified number of rows and columns

    Arguments:
    row -- number of start rows
    column -- number of start columns
    name -- optional name of Image Explorer view to use (if None, default name - Image Explorer - will be used)

    '''
    if name is None:
        name = 'Image Explorer'

    _plot_setupimagegrid(name, row, column)

def imagetogrid(im, name=None, x=None, y=None, store=None):
    '''Plot images to the grid of an image explorer view

    Arguments:
    im -- images dataset or filename
    name -- optional name of Image Explorer view to use (if None, default name - Image Explorer - will be used)
    x -- optional : gridX use -1 to automatically place
    y -- optional : gridY use -1 to automatically place
    store -- optional : if true, create a copy of image as a temporary file
    '''
    if name is None:
        name = 'Image Explorer'
    if x is None:
        x = -1
    if y is None:
        y = -1
    if store is None:
        store = False

    if isinstance(im, str):
        _plot_imagetogrid(name, im, x, y)
    else:
        _plot_imagetogrid(name, im, x, y, store)

def imagestogrid(im, name=None, store=None):
    '''Plot images to the grid of an image explorer view

    Arguments:
    im -- images dataset
    name -- optional name of Image Explorer view to use (if None, default name - Image Explorer - will be used)
    store -- optional : if true, create a copy of image as a temporary file
    '''
    if name is None:
        name = 'Image Explorer'
    if store is None:
        store = False

    _plot_imagetogrid(name, im, store)

def surface(s, x=None, y=None, name=None):
    '''Plot the 2D dataset as a surface in the named view with optional x and y axes

    Arguments:
    s -- surface (height field) dataset
    x -- optional dataset for x-axis
    y -- optional dataset for y-axis
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    if x is None:
        y = None
    if y is None:
        x = None
    _checkimagearg(x, y, s)

    _plot_surface(name, x, y, s)

def stack(x, y=None, z=None, name=None):
    '''Plot all of the given 1D y datasets, optionally against any given x datasets,
    as a 3D stack of lines with optional z coordinates in the named view

    Arguments:
    x -- optional dataset or list of datasets for x-axis
    y -- dataset or list of datasets
    z -- optional dataset for z-axis
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    if not y:
        y = _toList(x)
        l = 0
        for d in y:
            if d.size > l:
                l = d.size
        x = [ _core.arange(l) ]

    _plot_stack(name, _toList(x), _toList(y), z)

def addstack(x, y=None, z=None, name=None):
    '''Add line(s) to existing stack plot, optionally against any given x datasets
    with optional z coordinates in the named view

    Arguments:
    x -- optional dataset or list of datasets for x-axis
    y -- dataset or list of datasets
    z -- optional dataset for z-axis
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    if not y:
        y = _toList(x)
        l = 0
        for d in y:
            if d.size > l:
                l = d.size
        x = [ _core.arange(l) ]

    _plot_addstack(name, _toList(x), _toList(y), z)

def updatestack(x, y=None, z=None, name=None):
    '''Update existing 3D line stack by changing displayed y dataset (or list of datasets),
    optionally against any given x dataset with optional z coordinates in the named view

    Arguments:
    x -- optional dataset or list of datasets for x-axis
    y -- dataset or list of datasets
    z -- optional dataset for z-axis
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    if not y:
        y = _toList(x)
        l = 0
        for d in y:
            if d.size > l:
                l = d.size
        x = [ _core.arange(l) ]

    _plot_updatestack(name, _toList(x), _toList(y), z)

def points(x, y=None, z=None, size=0, name=None):
    '''Plot points with given coordinates. If y is missing then x must contain
    a dataset of coordinate pairs or a list of such datasets

    Arguments:
    x -- dataset of x coords or coord pairs
    y -- optional dataset of y coords
    z -- optional dataset of z coords
    size -- integer size or dataset of sizes
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    size = _core.asarray(size)
    if z is None:
        if y is None:
            _plot_points2d(name, _toList(x), _toList(size))
        else:
            _plot_points2d(name, x, y, size)
    else:
        raise NotImplementedError("Old implementation removed; not currently implemented")
        #_plot_points3d(name, x, y, z, size)

def addpoints(x, y, z=None, size=0, name=None):
    '''Update existing plot by adding points of given coordinates

    Arguments:
    x -- dataset of x coords
    y -- dataset of y coords
    z -- optional dataset of z coords
    size -- integer size or dataset of sizes
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    size = _core.asarray(size)
    if z is None:
        _plot_updatepoints2d(name, x, y, size)
    else:
        raise NotImplementedError("Old implementation removed; not currently implemented")
        #_plot_updatepoints3d(name, x, y, z, size)


_IMAGEEXPNAME = "ImageExplorer View"

def scanforimages(path, order="none", prefix=None, suffices=None, columns=-1, rowMajor=True, name=_IMAGEEXPNAME):
    '''Scan for images in path and load into given image explorer view

    order can be "none", "alpha", "chrono"
    prefix is the start of a regular expression to match the beginnings of filenames
    suffices can be a list of filename endings (eg. ["png", "tif"], each can be a regex)
    columns is the number of columns to use in grid (-1 makes grid square)
    rowMajor determines whether images are laid out row-wise or column-wise
    
    returns number of images loaded
    '''
    maxint = ((1<<30) - 1) + (1<<30) # maximum value for signed 32-bit integer
    return _plot_scanforimages(name, path, _order(order), prefix, suffices, columns, rowMajor, maxint, 1)

def _get_roi(rl, name): # get first ROI in list that has given name
    for r in rl:
        if r.name == name:
            return r
    return None

def _set_roi(rl, roi, warn): # replace first ROI in list that has given name
    name = roi.name
    for i, r in enumerate(rl):
        if r.name == name:
            rl[i] = roi
            if warn:
                print('Warning: replaced', name, 'in ROI list with current ROI')
            return
    rl.append(roi) # or append to list

def getbean(name=None):
    '''Get GUI bean (contains information from named view)

    Arguments:
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    bean = _plot_getbean(name)
    if parameters.roi in bean and parameters.roilist in bean:
        bean_name = bean[parameters.roi]
        # need to present bean with roi instead of name
        if bean[parameters.roilist]:
            bean[parameters.roi] = _get_roi(bean[parameters.roilist], bean_name)
    
    return bean

def setbean(bean, name=None, warn=True):
    '''Set GUI bean

    Arguments:
    bean -- GUI bean
    name -- name of plot view to use (if None, use default name)
    warn -- if True, warn of any issues
    '''
    if name is None:
        name = _PVNAME
    if bean is not None:
        cr = bean.get(parameters.roi)
        if cr:
            # ensure current roi is in list
            nbean = _guibean(bean)
            rl = bean.get(parameters.roilist)
            if not rl:
                rl = roi._create_list(cr)
                nbean[parameters.roilist] = rl
            n = cr.name
            if not n: # add unique name if missing
                nl = [ r.name for r in rl if r.name ]
                np = cr.__class__.__name__ 
                for i in range(max(1,len(nl))):
                    n = '%s-%d' % (np, i)
                    if not n in nl:
                        break
                cr.name = n
            nbean[parameters.roi] = n
            _set_roi(rl, cr, warn)
            bean = nbean

        _plot_setbean(name, bean)

def getdatabean(name=None):
    '''Get data bean (contains data from named view)

    Arguments:
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    return _plot_getdatabean(name)

def setdatabean(bean, name=None):
    '''Set data bean

    Arguments:
    bean -- data bean
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    if bean is not None:
        _plot_setdatabean(name, bean)

def getroi(bean=None, roi=None, name=None):
    '''Get region of interest from bean

    Arguments:
    bean -- GUI bean (if None, retrieve from plot view of given name)
    roi  -- class of ROI to retrieve. If None, then get anyway
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    if bean is None:
        bean = getbean(name)
    if not parameters.roi in bean:
        return None
    r = bean[parameters.roi]
    if roi is None:
        return r
    if r is None or not isinstance(r, roi):
        return None
    return r

def setroi(bean, roi=None, send=False, name=None, warn=True):
    '''Set region of interest in bean

    Arguments:
    bean -- GUI bean
    roi  -- ROI to set (if None, then use first arg as roi and retrieve bean from and update to plot view of given name)
    send -- flag to update plot
    name -- name of plot view to use (if None, use default name)
    warn -- if True, warn of any issues
    '''
    if name is None:
        name = _PVNAME
    if roi is None:
        roi = bean
        send = True
        bean = getbean(name)
    if isinstance(bean, _guibean):
        bean[parameters.roi] = roi

    if send:
        setbean(bean, name, warn=warn)
    return bean

def delroi(bean=None, roi=None, send=False, name=None, warn=True):
    '''Delete region of interest from bean

    Arguments:
    bean -- GUI bean (if None, retrieve from and update to plot view of given name)
    roi  -- class of ROI to remove. If None, then remove anyway
    send -- flag to update plot
    name -- name of plot view to use (if None, use default name)
    warn -- if True, warn of any issues
    '''
    if name is None:
        name = _PVNAME
    if bean is None:
        send = True
        bean = getbean(name)

    r = bean.get(parameters.roi)
    if r:
        dr = None
        if roi:
            if not issubclass(roi, _iroi):
                raise ValueError("roi should be a subclass of ROI")
            if isinstance(r, roi):
                bean[parameters.roi] = None
                dr = r
        else:
            bean[parameters.roi] = None
            dr = r
        if dr:
            dn = dr.name
            rl = bean.get(parameters.roilist)
            if rl:
                if dn:
                    for i in range(len(rl)):
                        if dn == rl[i].name:
                            rl.pop(i)
                            break
                else:
                    rl.remove(dr)

    if send:
        setbean(bean, name, warn=warn)
    return bean

from scisoftpy.dictutils import ListDict

class roi_list(ListDict):
    def __init__(self, data=[]):
        super(roi_list, self).__init__(data=data, warn=False, lock=False, interactive=False)

def getrois(bean=None, roi=None, name=None):
    '''Get list/dict of regions of interest from bean

    Arguments:
    bean -- GUI bean (if None, retrieve from plot view of given name)
    roi  -- class of ROI to retrieve. If None, then get anyway
    name -- name of plot view to use (if None, use default name)
    '''
    if name is None:
        name = _PVNAME
    if bean is None:
        bean = getbean(name)
    if not parameters.roilist in bean:
        return None
    rs = bean[parameters.roilist]
    if not rs:
        return None
    try:
        iter(rs)
    except:
        rs = [rs]
    if roi is None:
        rl = [(r.name, r) for r in rs]
    else:
        rl = [(r.name, r) for r in rs if isinstance(r, roi)]
    return roi_list(rl)

def _to_roilist(roilist):
    if not roilist:
        return None

    if isinstance(roilist, (roi.point_list, roi.line_list, roi.rectangle_list, roi.sector_list, roi.ellipse_list, roi.circle_list)):
        return roilist

    r = roilist[0]
    if isinstance(r, roi.point):
        rtype = roi.point
        nlist = roi.point_list()
    elif isinstance(r, roi.line):
        rtype = roi.line
        nlist = roi.line_list()
    elif isinstance(r, roi.rectangle):
        rtype = roi.rectangle
        nlist = roi.rectangle_list()
    elif isinstance(r, roi.sector):
        rtype = roi.sector
        nlist = roi.sector_list()
    elif isinstance(r, roi.ellipse):
        rtype = roi.ellipse
        nlist = roi.ellipse_list()
    elif isinstance(r, roi.circle):
        rtype = roi.circle
        nlist = roi.circle_list()
    else:
        raise TypeError("Type of first item not supported")

    if isinstance(roilist, roi_list):
        for k in roilist:
            r = roilist[k]
            if isinstance(r, rtype):
                nlist.append(r)
    else:
        for r in roilist:
            if isinstance(r, rtype):
                nlist.append(r)
    return nlist


def setrois(bean, roilist=None, send=False, name=None, warn=True):
    '''Set list/dict of regions of interest in bean
    Arguments:
    bean -- GUI bean
    roilist  -- ROI list to set (if None, then use first arg as roilist and retrieve bean from and update to plot view of given name)
    send    -- flag to update plot
    name    -- name of plot view to use (if None, use default name)
    warn -- if True, warn of any issues
    '''
    if name is None:
        name = _PVNAME

    if roilist is None:
        roilist = bean
        send = True
        bean = getbean(name)

    roilist = _to_roilist(roilist)
    bean[parameters.roilist] = roilist
    cr = bean.get(parameters.roi)
    if cr: # replace current ROI with one from list
        cname = cr.name
        for r in roilist:
            if r.name == cname:
                bean[parameters.roi] = r
                break
    elif roilist:
        bean[parameters.roi] = roilist[0]

    if send:
        setbean(bean, name, warn=warn)
    return bean

def delrois(bean=None, roi=None, send=False, name=None, warn=True):
    '''Delete list/dict of regions of interest from bean

    Arguments:
    bean -- GUI bean (if None, retrieve from and update to plot view of given name)
    roi  -- class of ROI or ROI list to remove. If None, then remove anyway
    send -- flag to update plot
    name -- name of plot view to use (if None, use default name)
    warn -- if True, warn of any issues
    '''
    if name is None:
        name = _PVNAME
    if bean is None:
        send = True
        bean = getbean(name)

    if parameters.roilist in bean:
        rl = bean[parameters.roilist]
        if roi is None or rl is None or isinstance(rl, roi) or isinstance(rl[0], roi):
            bean[parameters.roilist] = None
    if send:
        setbean(bean, name, warn=warn)
    return bean

def getline(bean=None, name=None):
    '''Get linear region of interest'''
    if name is None:
        name = _PVNAME
    return getroi(bean, roi=roi.line, name=name)

def getlines(bean=None, name=None):
    '''Get list of linear regions of interest'''
    if name is None:
        name = _PVNAME
    return getrois(bean, roi=roi.line, name=name)

def getrect(bean=None, name=None):
    '''Get rectangular region of interest'''
    if name is None:
        name = _PVNAME
    return getroi(bean, roi=roi.rectangle, name=name)

def getrects(bean=None, name=None):
    '''Get list of rectangular regions of interest'''
    if name is None:
        name = _PVNAME
    return getrois(bean, roi=roi.rectangle, name=name)

def getsect(bean=None, name=None):
    '''Get sector region of interest'''
    if name is None:
        name = _PVNAME
    return getroi(bean, roi=roi.sector, name=name)

def getsects(bean=None, name=None):
    '''Get list of sector regions of interest'''
    if name is None:
        name = _PVNAME
    return getrois(bean, roi=roi.sector, name=name)


def getfiles(bean):
    '''Get list of selected files'''
    try:
        fn = bean[parameters.fileselect]
    except KeyError:
        print("No selection has been made and sent to server")
        return None
    if fn is None:
        print("No selection has been made and sent to server")
        return None
    fl = []
    for f in fn:
        fl.append(f)
    fl.sort()
    return fl

_H5TVNAME="hdf5TreeViewer"
def viewtree(tree, name=_H5TVNAME):
    '''View a NeXus/HDF5 tree in the named view'''
    _plot_viewtree(name, tree)

viewnexus = viewtree



if os.name == 'java':
    from .jython import jyplottingsystem as _ps  # @UnusedImport
else:
    from .python import pyplottingsystem as _ps  # @Reimport


'''
name  - the name of the plot to get or if none, the current active plotting system.
'''
def getPlottingSystem(name=None):
    if name is None:
        name = _PVNAME
    return _ps.getPlottingSystem(name)

'''
Creates a color that can be used with the plotting system
For instance to set trace color.
'''  
def createColor(r, g, b):
    return _ps.createColor(r,g,b)

'''
Creates a color that can be used with the plotting system
For instance to set trace color.
'''  
def createHistogramBound(position, r, g=None, b=None):
    rgb = []
    if r:
        rgb.append(r)
    if g:
        rgb.append(g)
    if b:
        rgb.append(b)
    if len(rgb) == 0:
        rgb = None
    return _ps.createHistogramBound(position, rgb)

'''
Get an implementation of an OSGi service
'''
def getService(serviceClass):
    return _ps.getService(serviceClass)
