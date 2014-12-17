import sys

# Should be there
import org.eclipse.dawnsci.analysis.api.RMIClientProvider as RMIClientProvider


def getPlottingSystem(plottingSystemName):
        
    uri    = "PlottingSystem:"+plottingSystemName # This string is also defined in IPlottingSystem.
    psStub = RMIClientProvider.getInstance().lookup(None, uri)
   
    return psStub
