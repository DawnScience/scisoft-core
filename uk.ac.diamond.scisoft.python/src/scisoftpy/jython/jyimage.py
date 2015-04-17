###
# Copyright 2011 Diamond Light Source Ltd.
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
###

'''
Image processing package
'''

import org.eclipse.dawnsci.analysis.dataset.impl.Image as _image
import uk.ac.diamond.scisoft.analysis.dataset.function.MapToShiftedCartesian as _mapshift
from jycore import _wrap
#import ImageFilterServiceCreator and instanciate it
import org.dawnsci.boofcv.BoofCVImageFilterServiceCreator as _creator
_image.setImageFilterService(_creator.createFilterService())

@_wrap
def findshift(a, b, rect=None):
    '''Find translation vector from b to a using rectangular ROI rect'''
    return _image.findTranslation2D(a, b, rect._jroi())

@_wrap
def shiftimage(image, shift):
    '''Translate an image by specified shift'''
    sfn = _mapshift(shift[0], shift[1])
    return sfn.value([image])[0]


import uk.ac.diamond.scisoft.analysis.dataset.function.BicubicInterpolator as _bicubic

@_wrap
def bicubic(image, newshape):
    '''Make a new image which has the new shape by taking the bicubic interpolation of the input image'''
    bicube = _bicubic(newshape)
    return bicube.value([image])[0]

@_wrap
def threshold_global_mean(image, down=True):
    '''Applies a global mean threshold across the whole image with the mean pixel intensity value as a threshold value'''
    return _image.globalMeanThreshold(image, down)

@_wrap
def threshold_global(image, threshold, down=True):
    '''Applies a global threshold across the whole image. If 'down' is true, then pixels with values <= to 'threshold' 
    are set to 1 and the others set to 0. If 'down' is false, then pixels with values >= to 'threshold' are set to 1 
    and the others set to 0.'''
    return _image.globalThreshold(image, threshold, down)

@_wrap
def threshold_global_otsu(image, down=True):
    '''Applies a global mean threshold across the whole image with the variance based threshold using Otsu's method'''
    return _image.globalOtsuThreshold(image, down)

@_wrap
def threshold_global_entropy(image, down=True):
    '''Applies a global mean threshold across the whole image with the threshold which maximizes the entropy between the
    foreground and background regions.'''
    return _image.globalEntropyThreshold(image, down)

@_wrap
def threshold_adaptive_square(image, radius, down=True):
    '''Thresholds the image using an adaptive threshold that is computed using a local square region centered on each 
    pixel. The threshold is equal to the average value of the surrounding pixels plus the bias. If down is true then
    b(x,y) = I(x,y) <= T(x,y) + bias ? 1 : 0. Otherwise b(x,y) = I(x,y) >= T(x,y) + bias ? 0 : 1'''
    return _image.adaptiveSquareThreshold(image, radius, down)

@_wrap
def threshold_adaptive_gaussian(image, radius, down=True):
    '''Thresholds the image using an adaptive threshold that is computed using a local square region centered on each
    pixel. The threshold is equal to the gaussian weighted sum of the surrounding pixels plus the bias. If down is
    true then b(x,y) = I(x,y) <= T(x,y) + bias ? 1 : 0. Otherwise b(x,y) = I(x,y) >= T(x,y) + bias ? 0 : 1'''
    return _image.adaptiveGaussianThreshold(image, radius, down)

@_wrap
def threshold_adaptive_sauvola(image, radius, down=True):
    '''Applies Sauvola thresholding to the input image. Intended for use with text image'''
    return _image.adaptiveSauvolaThreshold(image, radius, down)
