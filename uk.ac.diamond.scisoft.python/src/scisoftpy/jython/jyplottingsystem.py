import sys

# Should be there
import org.eclipse.dawnsci.analysis.api.RMIClientProvider as RMIClientProvider


def getPlottingSystem(plottingSystemName):
        
    uri    = "PlottingSystem:"+plottingSystemName # This string is also defined in IPlottingSystem.
    psStub = RMIClientProvider.getInstance().lookup(None, uri)
   
    return psStub

'''
Creates a color that can be used with the plotting system
For instance to set trace color.
'''   
def createColor(r, g, b):
    
    try:
        import org.eclipse.swt.graphics.Color as Color
    
        return Color(None, r, g, b)
    
    except Exception, e:
        print >> sys.stderr, "Could not create swt color, maybe jython interpreter old config"
        print >> sys.stderr, e

'''
Creates a histogram bound which is a color and a position for the bound
'''   
def createHistogramBound(position, r, g, b):    

    try:
        import org.eclipse.dawnsci.plotting.api.histogram.HistogramBound as HistogramBound
    
        return HistogramBound(position, r, g, b)
    
    except Exception, e:
        print >> sys.stderr, "Could not create HistogramBound, maybe jython interpreter old config"
        print >> sys.stderr, e


'''
Get any service from OSGi
'''
def getService(serviceClass):
        
    raise Exception("definition not implemented for Jython : getService(...). TODO, implement it :)")