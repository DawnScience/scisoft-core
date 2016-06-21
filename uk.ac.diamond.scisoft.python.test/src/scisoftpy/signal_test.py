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
Test signal methods

Can run test in jython console (after copying jython class into user script directory):

import unittest
unittest.TestProgram(argv=["signal_test"])
'''
import unittest

import scisoftpy as np
import scisoftpy.random as rnd
import scisoftpy.signal as sig


class Test(unittest.TestCase):


    def setUp(self):
        self.da = [0.1, 1.5, 2.3, 4.1]


    def tearDown(self):
        pass

    def checkitems(self, la, ds, places=7):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                if la == None:
                    print i, ds[i]
                else:
                    if max(la[i], ds[i]) > 1e-10:
                        self.assertAlmostEqual(la[i], ds[i], places=places)
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    if la == None:
                        print i,j, ds[i,j]
                    else:
                        if max(la[i][j], ds[i, j]) > 1e-10:
                            self.assertAlmostEqual(la[i][j], ds[i, j], places=places)
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        if la == None:
                            print i,j,k, ds[i,j,k]
                        else:
                            if max(la[i][j][k], ds[i, j, k]) > 1e-10:
                                self.assertAlmostEqual(la[i][j][k], ds[i, j, k], places=places)

    def testCorrelate(self):
        print 'test correlate'
        da = np.array(self.da, np.float)
        ada = np.correlate(da, axes=[0])
        self.checkitems(None, ada)

        a = np.array([1, 2, 3])
        b = np.array([0, 1, 0.5])
        self.checkitems([3.5], np.correlate(a, b))
        self.checkitems([2., 3.5, 3.], np.correlate(a, b, "same"))
        self.checkitems([0.5, 2., 3.5, 3., 0.], np.correlate(a, b, "full"))
        self.checkitems([3.5], np.correlate(b, a))
        self.checkitems([2., 3.5, 3.][::-1], np.correlate(b, a, "same"))
        self.checkitems([0.5, 2., 3.5, 3., 0.][::-1], np.correlate(b, a, "full"))

    def testCorrelate2(self):
        print 'test correlate2'
        da = np.zeros([10], np.float)
        da[3] = 1
        db = da.copy()
        ada = np.correlate(da,db)
        self.checkitems(None, ada)

        db = np.zeros([10], np.float)
        db[5] = 1
        ada = np.correlate(da,db)
        self.checkitems(None, ada)

        import os
        if os.name == 'java':
            ada = sig.phasecorrelate(da,db)
            self.checkitems(None, ada)


    def testCorrelate2D(self):
        import os
        if os.name != 'java':
            return
        print 'test correlate2'
        da = np.zeros([8,8], np.float)
        da[3,3] = 20
        db = da.copy()
        ada = np.correlate(da,db)
        print ada

        da += rnd.poisson(2, size=da.shape)
        db = np.zeros([8,8], np.float)
        db[5,6] = 20
        db += rnd.poisson(2, size=db.shape)
        ada = np.correlate(da,db)
        print ada

    def testConvolve(self):
        print 'test convolve'
#        da = np.array(self.da, np.float)
#        ada = np.convolve(da, axes=[0])
#        self.checkitems(None, ada)
        a = np.array([1, 2, 3])
        b = np.array([0, 1, 0.5])

        self.checkitems([0., 1., 2.5, 4., 1.5], np.convolve(a, b))
        self.checkitems([1., 2.5, 4.], np.convolve(a, b, 'same'))
        self.checkitems([2.5], np.convolve(a, b, 'valid'))
        self.checkitems([0., 1., 2.5, 4., 1.5], np.convolve(b, a))
        self.checkitems([1., 2.5, 4.], np.convolve(b, a, 'same'))
        self.checkitems([2.5], np.convolve(b, a, 'valid'))

if __name__ == "__main__":
    #import sys
    #sys.argv = ['', 'Test.testCorrelate']
    unittest.main()
