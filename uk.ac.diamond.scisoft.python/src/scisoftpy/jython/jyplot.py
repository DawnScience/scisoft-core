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

from uk.ac.diamond.scisoft.analysis import SDAPlotter as _plotter,\
    PlotServiceProvider as _provider, RMIClientProvider as _rmiprovider

try:
    from uk.ac.diamond.scisoft.analysis.rcp.plotting import RMIPlotWindowManger as _manager
except:
    ## This code has special handling because the RCP classes may not be available
    import sys
    print >> sys.stderr, "Could not import Plot Window Manager"
    _manager = None

from jycore import _wrapin

plot_clear = _plotter.clearPlot

@_wrapin
def plot_line(*arg, **kwarg):
    _plotter.plot(*arg, **kwarg)

@_wrapin
def plot_addline(*arg, **kwarg):
    _plotter.addPlot(*arg, **kwarg)

@_wrapin
def plot_updateline(*arg, **kwarg):
    _plotter.updatePlot(*arg, **kwarg)

@_wrapin
def plot_stack(*arg, **kwarg):
    _plotter.stackPlot(*arg, **kwarg)

@_wrapin
def plot_updatestack(*arg, **kwarg):
    _plotter.updateStackPlot(*arg, **kwarg)

@_wrapin
def plot_image(*arg, **kwarg):
    _plotter.imagePlot(*arg, **kwarg)

@_wrapin
def plot_images(*arg, **kwarg):
    _plotter.imagesPlot(*arg, **kwarg)

@_wrapin
def plot_surface(*arg, **kwarg):
    _plotter.surfacePlot(*arg, **kwarg)

@_wrapin
def plot_points2d(*arg, **kwarg):
    _plotter.scatter2DPlot(*arg, **kwarg)

@_wrapin
def plot_updatepoints2d(*arg, **kwarg):
    _plotter.scatter2DPlotOver(*arg, **kwarg)

@_wrapin
def plot_points3d(*arg, **kwarg):
    _plotter.scatter3DPlot(*arg, **kwarg)
@_wrapin
def plot_updatepoints3d(*arg, **kwarg):
    _plotter.scatter3DPlotOver(*arg, **kwarg)

plot_createaxis = _plotter.createAxis
plot_renameactivexaxis = _plotter.renameActiveXAxis
plot_renameactiveyaxis = _plotter.renameActiveYAxis

@_wrapin
def plot_scanforimages(*arg, **kwarg):
    _plotter.scanForImages(*arg, **kwarg)

from uk.ac.diamond.scisoft.analysis.plotserver import AxisOperation as _axisop
axis_top = _axisop.TOP
axis_bottom = _axisop.BOTTOM
axis_left = _axisop.LEFT
axis_right = _axisop.RIGHT

from jyio import h5manager as _h5mgr

def plot_viewnexustree(name, tree):
    if not isinstance(tree, _h5mgr):
        import sys #@Reimport
        print >> sys.stderr, "Only tree from loadnexus works for now"
        return
#        import jyhdf5io._tojavatree as _tojtree
#        tree = _tojtree(tree)
    _plotter.viewHDF5Tree(name, tree.gettree())

plot_volume = _plotter.volumePlot

from jybeans import parameters as _jyparams
from jybeans import guibean as _jyguibean

from jyroi import _roi_wrap, _create_list

def _wrap_gui_bean(jb):
    if _jyparams.roi in jb:
        jb[_jyparams.roi] = _roi_wrap(jb[_jyparams.roi])
    if _jyparams.roilist in jb:
        jb[_jyparams.roilist] = [_roi_wrap(r) for r in jb[_jyparams.roilist]]

def _unwrap_gui_bean(ob, nb):
    for k in ob:
        v = ob[k]
        if k == _jyparams.roi:
            v = v._jroi()
        elif k == _jyparams.roilist:
            ov = v
            v = _create_list(ov[0])
            for r in ov:
                v.add(r._jroi())
        nb[k] = v
    return nb

def plot_getbean(name):
    jb = _plotter.getGuiBean(name)
    _wrap_gui_bean(jb)
    return jb

def plot_setbean(name, bean):
    _plotter.setGuiBean(name, _unwrap_gui_bean(bean, _jyguibean()))

def plot_getdatabean(name):
    jdb = _plotter.getDataBean(name)
    jgb = jdb.getGuiParameters()
    _wrap_gui_bean(jgb)
    return jdb

def plot_setdatabean(name, bean):
    gb = bean.getGuiParameters()
    _plotter.setDataBean(name, _unwrap_gui_bean(gb, gb))

plot_getguinames = _plotter.getGuiNames

plot_orders = { "none": _plotter.IMAGEORDERNONE, "alpha": _plotter.IMAGEORDERALPHANUMERICAL, "chrono": _plotter.IMAGEORDERCHRONOLOGICAL}

def setremoteport(rmiport=0, **kwargs):
    '''Sets the RMI Connection Port to the rmiport arg'''
    _rmiprovider.getInstance().setPort(rmiport)
    _provider.setPlotService(None)
    if _manager is not None:
        _manager.clearManager()
    

class window_manager(object):
    '''Wrapper for IPlotWindowManager in SDA. Allows opening, duplicating and
       obtaining list of existing views'''
    def __init__(self):
        '''
        Create a new wrapper for the window manager, not intended for 
        use outside the jyplot module
        '''
        pass
    
    def open_duplicate_view(self, view_name):
        '''
        Open a duplicate view of an existing view name. View name cannot be null.
        The view's Data and Gui Bean are duplicated so each view has it's own
        copy.
        Returns the name of the newly opened view.
        '''
        return _manager.getManager().openDuplicateView(view_name)

    def open_view(self, view_name=None):
        '''
        Open a new view with the given view_name, or if None open a new view
        with a new, unique name.
        Returns the name of the newly opened view.
        '''
        return _manager.getManager().openView(view_name)

    def get_open_views(self):
        '''
        Return a list of all the open plot views.
        '''
        return _manager.getManager().getOpenViews()

if _manager is None:
    plot_window_manager = None
else:
    plot_window_manager = window_manager()

