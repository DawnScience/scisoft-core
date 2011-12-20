###
# Copyright © 2011 Diamond Light Source Ltd.
# Contact :  ScientificSoftware@diamond.ac.uk
# 
# This is free software: you can redistribute it and/or modify it under the
# terms of the GNU General Public License version 3 as published by the Free
# Software Foundation.
# 
# This software is distributed in the hope that it will be useful, but 
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
# Public License for more details.
# 
# You should have received a copy of the GNU General Public License along
# with this software. If not, see <http://www.gnu.org/licenses/>.
###

'''
Test image analysis functions
import unittest
unittest.TestProgram(argv=["image_test"])
'''

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

    def checkitems(self, la, ds):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                print i, ds[i]
#                self.assertEquals(la[i], ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    print i,j, ds[i,j]
#                    self.assertEquals(la[i][j], ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        print i,j,k, ds[i,j,k]
#                        self.assertEquals(la[i][j][k], ds[i, j, k])

    def tryImageShift(self, dx=2, dy=3, cx=23, cy=13):
        da = np.zeros([40,40])
        da[cy,cx] = 50
        da += rnd.poisson(2, da.shape)
        db = np.zeros([40,40])
        db[cy+dy,cx+dx] = 65
        db += rnd.poisson(2, db.shape)
        sx = max(dx,5)
        sy = max(dy,5)
        r = roi.rect(cx-2*sx,cy-2*sy,4*sx,4*sy,0)
        shift = img.findshift(da, db, r)
        print shift
        self.assertAlmostEquals(-dy, shift[0], places=0)
        self.assertAlmostEquals(-dx, shift[1], places=0)

    def tryImageShiftGaussian(self, dx=2, dy=3, cx=23, cy=13):
        da = np.zeros([40,40])
        da[cy,cx] = 50
        da += rnd.poisson(2, da.shape)
        db = np.zeros([40,40])
        db[cy+dy,cx+dx] = 65
        db += rnd.poisson(2, db.shape)
        sx = max(dx,10)
        sy = max(dy,10)
        r = roi.rect(cx-2*sx,cy-2*sy,4*sx,4*sy,0)
        shift = img.findshift(da, db, r)
        print shift
        self.assertAlmostEquals(-dy, shift[0], places=0)
        self.assertAlmostEquals(-dx, shift[1], places=0)

    def testShifts(self):
        for x in range(4):
            for y in range(4):
                self.tryImageShift(x,y)

if __name__ == "__main__":
    import sys;
    sys.argv = ['', 'Test.testShifts']
    unittest.main()
