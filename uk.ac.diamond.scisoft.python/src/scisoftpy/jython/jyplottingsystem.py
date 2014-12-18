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
