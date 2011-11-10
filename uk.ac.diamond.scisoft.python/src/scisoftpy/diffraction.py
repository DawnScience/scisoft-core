'''
Diffraction package
'''

import uk.ac.diamond.scisoft.analysis.diffraction as _dfn
import uk.ac.diamond.scisoft.analysis.dataset.function.MapToQSpace as _maptoqspace #@UnresolvedImport


def loadimages(name, asdict=False):
    '''Load a file and return a list of diffraction images (or dictionary thereof)'''
    images = _dfn.DiffractionImage.loadImages(name)
    if asdict:
        idict = {}
        for i in images:
            idict[i.name] = i
        return idict
    else:
        return images

class environ(_dfn.DiffractionCrystalEnvironment):
    def __init__(self, wavelength=None, energy=None):
        '''Create a diffraction environment
        
        Specify either wavelength or energy
        Keyword arguments:
        wavelength -- in units of Angstroms
        energy -- in units of keV
        '''
        if not wavelength and not energy:
            raise ValueError, "Must specify either wavelength or energy"
        if wavelength:
            super(_dfn.DiffractionCrystalEnvironment, self).__init__(wavelength)
        else:
            super(_dfn.DiffractionCrystalEnvironment, self).__init__()
            self.setWavelengthFromEnergykeV(energy)

class detector(_dfn.DetectorProperties):
    def __init__(self, shape, size, orientation, origin, beam=None):
        '''Create a detector
        
        Arguments:
        shape -- width and height in pixels of detector image
        size -- width and height of a pixel (in mm)
        orientation -- matrix as a list of 9 floats describing outward normal of detector's input surface
        origin -- coordinates (in mm) of top left corner of (0,0) pixel
        beam -- unit vector in direction of beam, defaults to positive z-axis
        '''
        if beam:
            super(_dfn.DetectorProperties, self).__init__(origin, beam, shape[1], shape[0], size[1], size[0], orientation)
        else:
            super(_dfn.DetectorProperties, self).__init__(origin, shape[1], shape[0], size[1], size[0], orientation)

class qspace(_dfn.QSpace):
    '''q space'''
    pass

class qmapping(_maptoqspace):
    '''Mapping to q space'''
    pass
