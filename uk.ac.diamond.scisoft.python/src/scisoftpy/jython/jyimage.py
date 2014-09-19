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
