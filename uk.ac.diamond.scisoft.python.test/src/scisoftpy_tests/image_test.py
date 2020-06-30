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
Test image analysis functions
import unittest
unittest.TestProgram(argv=["image_test"])
'''

from __future__ import print_function

import os
if os.name == 'java':
    import unittest

    import scisoftpy as np
    import scisoftpy.image as img
    import scisoftpy.random as rnd
    import scisoftpy.roi as roi

    class Test(unittest.TestCase):
        """
        A test case for image alignment
        """

        def setUp(self):
            self.da = [0.1, 1.5, 2.3, 4.1]

        def tearDown(self):
            pass

        def tryImageShift(self, dx=2, dy=3, cx=23, cy=13):
            da = np.zeros([40,40])
            da[cy,cx] = 50
            da += rnd.poisson(2, da.shape)
            db = np.zeros([40,40])
            db[cy+dy,cx+dx] = 65
            db += rnd.poisson(2, db.shape)
            sx = max(dx,5)
            sy = max(dy,5)
            r = roi.rectangle(point=[cx-2*sx,cy-2*sy],lengths=[4*sx,4*sy],angle=0)
            shift = img.findshift(da, db, r)
            print(shift)
            self.assertAlmostEqual(-dy, shift[0], places=0)
            self.assertAlmostEqual(-dx, shift[1], places=0)

        def tryImageShiftGaussian(self, dx=2, dy=3, cx=23, cy=13):
            da = np.zeros([40,40])
            da[cy,cx] = 50
            da += rnd.poisson(2, da.shape)
            db = np.zeros([40,40])
            db[cy+dy,cx+dx] = 65
            db += rnd.poisson(2, db.shape)
            sx = max(dx,10)
            sy = max(dy,10)
            r = roi.rectangle(point=[cx-2*sx,cy-2*sy],lengths=[4*sx,4*sy],angle=0)
            shift = img.findshift(da, db, r)
            print(shift)
            self.assertAlmostEqual(-dy, shift[0], places=0)
            self.assertAlmostEqual(-dx, shift[1], places=0)

        def testShifts(self):
            for x in range(4):
                for y in range(4):
                    self.tryImageShift(x,y)

    if __name__ == '__main__':
        unittest.main(verbosity=2)
