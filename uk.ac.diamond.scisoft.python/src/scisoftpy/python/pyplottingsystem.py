'''
Python side implementation of DatasetManager connection
'''

import pyplot as _plot # We are going to reuse its _get_rpcclient()

from py4j.java_gateway import JavaGateway, java_import


_gateway = None

def getPlottingSystem(plottingSystemName):
    
    global _gateway
    if _gateway is None:
        _gateway = JavaGateway()
        
    jvm = _gateway.jvm
    
    java_import(jvm, 'org.eclipse.dawnsci.plotting.api.*')
    
    return jvm.PlottingFactory.getPlottingSystem(plottingSystemName, True)

'''
Creates a color that can be used with the plotting system
For instance to set trace color.
'''   
def createColor(r, g, b):    

    global _gateway
    if _gateway is None:
        _gateway = JavaGateway()
        
    jvm = _gateway.jvm

    java_import(jvm, 'org.eclipse.swt.graphics.*')
    
    return jvm.Color(None, r, g, b)

'''
Creates a histogram bound which is a color and a position for the bound
'''   
def createHistogramBound(position, r, g, b):    

    global _gateway
    if _gateway is None:
        _gateway = JavaGateway()
        
    jvm = _gateway.jvm

    java_import(jvm, 'org.eclipse.dawnsci.plotting.api.histogram.*')
    
    return jvm.HistogramBound(position, r, g, b)


'''
Get an implementation of an OSGi service
'''
def getService(serviceClass):
    
    global _gateway
    if _gateway is None:
        _gateway = JavaGateway()
        
    jvm = _gateway.jvm
    
    java_import(jvm, 'org.dawb.common.services.*')
    
    return jvm.Activator.getService(serviceClass)