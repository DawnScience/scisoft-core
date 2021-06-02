'''
Python side implementation of DatasetManager connection
'''

# import pyplot as _plot # We are going to reuse its _get_rpcclient()

def getPlottingSystem(plottingSystemName):
    raise NotImplementedError('Function no longer supported in Python')

def createColor(r, g, b):
    '''
    Creates a color that can be used with the plotting system
    For instance to set trace color.
    '''
    raise NotImplementedError('Function no longer supported in Python')

def createHistogramBound(position, rgb):
    '''
    Creates a histogram bound which is a color and a position for the bound
    '''
    raise NotImplementedError('Function no longer supported in Python')


def getService(serviceClass):
    '''
    Get an implementation of an OSGi service
    '''
    raise NotImplementedError('Function no longer supported in Python')
