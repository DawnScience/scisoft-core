
from uk.ac.diamond.scisoft.analysis import SDAPlotter as _plotter,\
    PlotServiceProvider as _provider, RMIServerProvider

try:
    from uk.ac.diamond.scisoft.analysis.rcp.plotting import RMIPlotWindowManger as _manager
except:
    ## This code has special handling because the RCP classes may not be available
    import sys
    print >> sys.stderr, "Could not import Plot Window Manager"
    _manager = None

plot_clear = _plotter.clearPlot
plot_line = _plotter.plot
plot_updateline = _plotter.updatePlot
plot_stack = _plotter.stackPlot
plot_updatestack = _plotter.updateStackPlot
plot_image = _plotter.imagePlot
plot_images = _plotter.imagesPlot
plot_surface = _plotter.surfacePlot
plot_points2d = _plotter.scatter2DPlot
plot_updatepoints2d = _plotter.scatter2DPlotOver
plot_points3d = _plotter.scatter3DPlot
plot_updatepoints3d = _plotter.scatter3DPlotOver

plot_scanforimages = _plotter.scanForImages

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

plot_getbean = _plotter.getGuiBean
plot_setbean = _plotter.setGuiBean
plot_getdatabean = _plotter.getDataBean
plot_setdatabean = _plotter.setDataBean
plot_getguinames = _plotter.getGuiNames

plot_orders = { "none": _plotter.IMAGEORDERNONE, "alpha": _plotter.IMAGEORDERALPHANUMERICAL, "chrono": _plotter.IMAGEORDERCHRONOLOGICAL}

def setremoteport(rmiport=0, **kwargs):
    '''Sets the RMI Connection Port to the rmiport arg'''
    RMIServerProvider.getInstance().setPort(rmiport)
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
        return _manager.getManager().openDuplicateView(None, view_name)

    def open_view(self, view_name=None):
        '''
        Open a new view with the given view_name, or if None open a new view
        with a new, unique name.
        Returns the name of the newly opened view.
        '''
        return _manager.getManager().openView(None, view_name)

    def get_open_views(self):
        '''
        Return a list of all the open plot views.
        '''
        return _manager.getManager().getOpenViews()

if _manager is None:
    plot_window_manager = None
else:
    plot_window_manager = window_manager()

