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

    
    

