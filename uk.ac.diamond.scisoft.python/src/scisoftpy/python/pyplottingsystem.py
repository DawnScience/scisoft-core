'''
Python side implementation of DatasetManager connection
'''

# import pyplot as _plot # We are going to reuse its _get_rpcclient()

from py4j.java_gateway import java_import

from . import py4jutils as _py4j

def getPlottingSystem(plottingSystemName):
    jvm = _py4j.get_gateway().jvm
    java_import(jvm, 'org.eclipse.dawnsci.plotting.api.*')
    
    return jvm.PlottingFactory.getPlottingSystem(plottingSystemName, True)

def createColor(r, g, b):
    '''
    Creates a color that can be used with the plotting system
    For instance to set trace color.
    '''
    jvm = _py4j.get_gateway().jvm
    java_import(jvm, 'org.eclipse.swt.graphics.*')
    
    return jvm.Color(None, r, g, b)

def createHistogramBound(position, rgb):
    '''
    Creates a histogram bound which is a color and a position for the bound
    '''
    jvm = _py4j.get_gateway().jvm
    java_import(jvm, 'org.eclipse.dawnsci.plotting.api.histogram.*')
    
    return jvm.HistogramBound(position, rgb)


def getService(serviceClass):
    '''
    Get an implementation of an OSGi service
    '''
    jvm = _py4j.get_gateway().jvm
    java_import(jvm, 'org.dawb.common.services.*')
    
    return jvm.Activator.getService(serviceClass)
