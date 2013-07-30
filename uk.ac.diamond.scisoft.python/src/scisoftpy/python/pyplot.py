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
scisoftpy.pyplot is the implementation of the plotting functionality that delegates
method calls to GDA/SDA via XML-RPC calls.

For SDAPlotter functions that take IDatasets, this wrapper converts numpy.ndarrays to IDatasets
via a temporary file written in NumPy file format.
'''

import scisoftpy.python.pyrpc as _rpc
import os

_RPC_CONNECTION_PORT = 0
_RPC_CLIENT = None

def setremoteport(rpcport=0, **kwargs):
    '''Sets the Analysis RPC Connection Port to the rpcport arg'''
    global _RPC_CONNECTION_PORT
    _RPC_CONNECTION_PORT = rpcport
    global _RPC_CLIENT
    _RPC_CLIENT = None # ditch cached client
        
def _get_rpcclient():
    '''Get an RPC Client for the currently selected port. This implementation allows the port
    number to be changed at runtime'''
    global _RPC_CLIENT
    if _RPC_CLIENT is None:
        if _RPC_CONNECTION_PORT == 0:
            try:
                port = int(os.getenv('SCISOFT_RPC_PORT'))
            except:
                raise Exception("Failed to determine correct port, either ensure SCISOFT_RPC_PORT or call scisoftpy.plot.setremoteport(...)")
        else:
            port = _RPC_CONNECTION_PORT
            
        _RPC_CLIENT = _rpc.rpcclient(port)
    return _RPC_CLIENT

        
_plot_name_remapper = {
    'plot_clear': 'clearPlot',
    'plot_line': 'plot',
    'plot_addline': 'addPlot',
    'plot_updateline': 'updatePlot',
    'plot_stack': 'stackPlot',
    'plot_updatestack': 'updateStackPlot',
    'plot_image': 'imagePlot',
    'plot_images': 'imagesPlot',
    'plot_surface': 'surfacePlot',
    'plot_points2d': 'scatter2DPlot',
    'plot_updatepoints2d': 'scatter2DPlotOver',
    'plot_points3d': 'scatter3DPlot',
    'plot_updatepoints3d': 'scatter3DPlotOver',
    
    'plot_scanforimages': 'scanForImages',
    'plot_viewnexustree': 'viewNexusTree',
    'plot_volume': 'volumePlot',
    
    'plot_getbean': 'getGuiBean',
    'plot_setbean': 'setGuiBean',
    'plot_getdatabean': 'getDataBean',
    'plot_setdatabean': 'setDataBean',
    'plot_getguinames': 'getGuiNames',

    'plot_createaxis': 'createAxis',
    'plot_renameactivexaxis': 'renameActiveXAxis',
    'plot_renameactiveyaxis': 'renameActiveYAxis',
}

class imageorder:
    IMAGEORDERNONE = 0
    IMAGEORDERALPHANUMERICAL = 1
    IMAGEORDERCHRONOLOGICAL = 2


class window_manager(object):
    '''Wrapper for IPlotWindowManager in SDA. Allows opening, duplicating and
       obtaining list of existing views'''
    def __init__(self):
        '''
        Create a new wrapper for the window manager, not intended for 
        use outside the pyplot module
        '''
        pass
    
    def open_duplicate_view(self, view_name):
        '''
        Open a duplicate view of an existing view name. View name cannot be null.
        The view's Data and Gui Bean are duplicated so each view has it's own
        copy.
        Returns the name of the newly opened view.
        '''
        return _get_rpcclient().PlotWindowManager("openDuplicateView", None, view_name)

    def open_view(self, view_name=None):
        '''
        Open a new view with the given view_name, or if None open a new view
        with a new, unique name.
        Returns the name of the newly opened view.
        '''
        return _get_rpcclient().PlotWindowManager("openView", None, view_name)

    def get_open_views(self):
        '''
        Return a list of all the open plot views.
        '''
        return _get_rpcclient().PlotWindowManager("getOpenViews")

class _plot_method:
    def __init__(self, doplot, plotter_method_name):
        self._doplot = doplot
        self._plotter_method_name = plotter_method_name
    def __call__(self, *args):
        return self._doplot(self._plotter_method_name, *args)


class plotter(object):
    def __init__(self):
        pass
        
    def _doplot(self, plotter_method_name, *params):
        return _get_rpcclient().SDAPlotter(plotter_method_name, *params)
    
    def __getattr__(self, plot_method_name):
        plotter_method_name = _plot_name_remapper.get(plot_method_name)
        if plotter_method_name is None:
            raise AttributeError('No such method name:%s' % plot_method_name)
        return _plot_method(self._doplot, plotter_method_name)
    
    plot_orders = { "none": imageorder.IMAGEORDERNONE, "alpha": imageorder.IMAGEORDERALPHANUMERICAL, "chrono": imageorder.IMAGEORDERCHRONOLOGICAL}
    plot_window_manager = window_manager()

    axis_top = 1 << 7
    axis_bottom = 1 << 10
    axis_left = 1 << 14
    axis_right = 1 << 17


