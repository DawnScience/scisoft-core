'''
Region of interest package

Classes available:
    line is a linear ROI
    rect is a rectangular ROI
    sect is a sector ROI
    linelist is a list of ROIs
    rectlist is a list of ROIs
    sectlist is a list of ROIs
'''

import os
if os.name == 'java':
    import jython.jyroi as _roi #@UnusedImport
else:
    import python.pyroi as _roi #@Reimport

 

roibase = _roi.roibase
line = _roi.line

rect = _roi.rect
sect = _roi.sect

linelist = _roi.linelist
rectlist = _roi.rectlist
sectlist = _roi.sectlist

def isroi(r):
    '''True if r is a ROI
    '''
    return issubclass(type(r), roibase)

profile = _roi.profile

