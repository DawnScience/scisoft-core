from __future__ import print_function
import sys

# Should be there
import org.eclipse.dawnsci.analysis.api.RMIClientProvider as RMIClientProvider


def getPlottingSystem(plottingSystemName):
    uri    = "PlottingSystem:"+plottingSystemName # This string is also defined in IPlottingSystem.
    psStub = RMIClientProvider.getInstance().lookup(None, uri)
   
    return psStub

def createColor(r, g, b):
    '''
    Creates a color that can be used with the plotting system
    For instance to set trace color.
    '''
    
    try:
        import org.eclipse.swt.graphics.Color as Color
    
        return Color(None, r, g, b)
    
    except Exception as e:
        print("Could not create swt color, maybe jython interpreter old config", file=sys.stderr)
        print(e, file=sys.stderr)

def createHistogramBound(position, rgb):
    '''
    Creates a histogram bound which is a color and a position for the bound
    '''

    try:
        import org.eclipse.dawnsci.plotting.api.histogram.HistogramBound as HistogramBound
    
        return HistogramBound(position, rgb)
    
    except Exception as e:
        print("Could not create HistogramBound, maybe jython interpreter old config", file=sys.stderr)
        print(e, file=sys.stderr)


def getService(serviceClass):
    '''
    Get any service from OSGi
    '''
    raise Exception("definition not implemented for Jython : getService(...). TODO, implement it :)")
