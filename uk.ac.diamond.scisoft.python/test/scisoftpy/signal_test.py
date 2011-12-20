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

    def checkitems(self, la, ds):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                if la == None:
                    print i, ds[i]
                else:
                    if max(la[i], ds[i]) > 1e-10:
                        self.assertEquals(la[i], ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    if la == None:
                        print i,j, ds[i,j]
                    else:
                        if max(la[i][j], ds[i, j]) > 1e-10:
                            self.assertEquals(la[i][j], ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        if la == None:
                            print i,j,k, ds[i,j,k]
                        else:
                            if max(la[i][j][k], ds[i, j, k]) > 1e-10:
                                self.assertEquals(la[i][j][k], ds[i, j, k])

    def testCorrelate(self):
        print 'test correlate'
        da = np.array(self.da, np.float)
        ada = sig.correlate(da, axes=[0])
        self.checkitems(None, ada)

    def testCorrelate2(self):
        print 'test correlate2'
        da = np.zeros([10], np.float)
        da[3] = 1
        db = da.copy()
        ada = sig.correlate(da,db)
        self.checkitems(None, ada)

        db = np.zeros([10], np.float)
        db[5] = 1
        ada = sig.correlate(da,db)
        self.checkitems(None, ada)

        ada = sig.phasecorrelate(da,db)
        self.checkitems(None, ada)


    def testCorrelate2D(self):
        print 'test correlate2'
        da = np.zeros([8,8], np.float)
        da[3,3] = 20
        db = da.copy()
        ada = sig.correlate(da,db)
        print ada

        da += rnd.poisson(2, size=da.shape)
        db = np.zeros([8,8], np.float)
        db[5,6] = 20
        db += rnd.poisson(2, size=db.shape)
        ada = sig.correlate(da,db)
        print ada

if __name__ == "__main__":
    #import sys
    #sys.argv = ['', 'Test.testCorrelate']
    unittest.main()
