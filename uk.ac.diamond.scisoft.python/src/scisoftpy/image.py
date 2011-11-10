'''
Image processing package
'''

import uk.ac.diamond.scisoft.analysis.dataset.Image as _image
import uk.ac.diamond.scisoft.analysis.dataset.function.MapToShiftedCartesian as _mapshift
from jython.jymaths import ndarraywrapped as _npwrapped

def findshift(a, b, rect=None):
    '''Find translation vector from b to a using rectangular ROI rect'''
    return _image.findTranslation2D(a, b, rect)

@_npwrapped
def shiftimage(image, shift):
    '''Translate an image by specified shift'''
    sfn = _mapshift(shift[0], shift[1])
    return sfn.value([image])[0]


import uk.ac.diamond.scisoft.analysis.dataset.function.BicubicInterpolator as _bicubic

@_npwrapped
def bicubic(image, newshape):
    '''Make a new image which has the new shape by taking the bicubic interpolation of the input image'''
    bicube = _bicubic(newshape)
    return bicube.value([image])[0]
