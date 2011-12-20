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
Test rudimentary aspects of scisoft package

import unittest
unittest.TestProgram(argv=["scisoft_test"])
'''
import unittest
import scisoftpy as np

import os
isjava = os.name == 'java'

def toInt(o):
    return int(o)

def toAny(o):
    return o

class Test(unittest.TestCase):

    def setUp(self):
        pass
#        import pydevd
#        pydevd.settrace(stdoutToServer=True, stderrToServer=True)

    def checkitems(self, la, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertEquals(convert(la[i]), ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertEquals(convert(la[i][j]), ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertEquals(convert(la[i][j][k]), ds[i, j, k])

    def testMethods(self):
        print np.arange(6, dtype=np.int32)
        print np.arange(6, dtype=np.float)
        a = np.array([4,3.])
        print type(a)
        print np.sort(a, None)
        print a.sort()
        a = np.arange(6, dtype=np.float)
        print a.take([0, 2, 4], 0)
        d = np.take(a, [0, 2, 4], 0)
        print type(d), d
        d = np.diag([0, 2, 3])
        print type(d), d
#        print a.sort()

    def testScisoft(self):
        a = np.ones([3,4])
        print a.shape
        a.shape = [2,6]
        print a.shape
        a.shape = 12
        print a.shape
        a.shape = (2,6)
        print a.shape
        print a
        print a*2
        b = np.arange(12)
        print b
        print b[0]
        b[2] = 2.3
        print b[1:8:3]
        b[6:2:-1] = -2.1
        b.shape = [2,6]
        print b + 2
        print 2 + b
        b += 2.
        print b[1,3]
        b[0,5] = -2.3
        print b[0,2:5]
        b[:,1] = -7.1
        print b
        try:
            c = np.add(a, b)
            print c
        except:
            print "Failed with an IAE as expected"

    def testReshape(self):
        a = np.arange(10.)
        a.reshape(2,5)
        a.reshape((2,5))

    def testCompounds(self):
        a = np.arange(24).reshape(3,4,2)
        oa = np.compoundarray(a)
        ca = oa.copy()
        self.assertEquals(ca.shape[0], 3)
        self.assertEquals(ca.shape[1], 4)
        la = ca[1,2]
        print la
        self.assertEquals(la[0], 12)
        self.assertEquals(la[1], 13)
        ca[2,2] = (0,0)
        self.assertEquals(ca[2,2][0], 0)
        self.assertEquals(ca[2,2][1], 0)
        ca[2,2] = (-2, 1)
        self.assertEquals(ca[2,2][0], -2)
        self.assertEquals(ca[2,2][1], 1)
        ca[1:,3:] = (-1,-1)
        self.assertEquals(ca[2,3][0], -1)
        self.assertEquals(ca[2,3][1], -1)
        ca[1:,3:] = (3,-4)
        self.assertEquals(ca[2,3][0], 3)
        self.assertEquals(ca[2,3][1], -4)
        if isjava:
            ia = np.array([2, -7])
            print 'Integer index testing'
            ca = oa.copy()
            cb = ca[ia]
            print cb
            self.assertEquals(cb.shape[0], 2)
            self.assertEquals(cb[0][0], 4)
            self.assertEquals(cb[0][1], 5)
            self.assertEquals(cb[1][0], 10)
            self.assertEquals(cb[1][1], 11)
            ca[ia] = [1,2]
            self.assertEquals(ca[0,2][0], 1)
            self.assertEquals(ca[0,2][1], 2)
            self.assertEquals(ca[1,1][0], 1)
            self.assertEquals(ca[1,1][1], 2)

        print 'Boolean index testing'
        ba = np.array([[0, 0, 1, 0], [1, 0, 0, 0], [0, 1, 0, 1]], dtype=np.bool)
        ca = oa.copy()
        cc = ca[ba]
        # index dataset does not work
        # test boolean too
        print cc
        self.assertEquals(cc.shape[0], 4)
        self.assertEquals(cc[0][0], 4)
        self.assertEquals(cc[0][1], 5)
        self.assertEquals(cc[1][0], 8)
        self.assertEquals(cc[1][1], 9)
        self.assertEquals(cc[2][0], 18)
        self.assertEquals(cc[2][1], 19)
        self.assertEquals(cc[3][0], 22)
        self.assertEquals(cc[3][1], 23)
        ca[ba] = (1,2)
        self.assertEquals(ca[0,2][0], 1)
        self.assertEquals(ca[0,2][1], 2)
        self.assertEquals(ca[1,0][0], 1)
        self.assertEquals(ca[1,0][1], 2)
        self.assertEquals(ca[2,1][0], 1)
        self.assertEquals(ca[2,1][1], 2)
        self.assertEquals(ca[2,3][0], 1)
        self.assertEquals(ca[2,3][1], 2)

    def testBools(self):
        b = np.array([False, True], dtype=np.bool)
        self.assertEquals(b[0], False)
        self.assertEquals(b[1], True)

    def testHelp(self):
        import sys
        if sys.executable is None:
            if len(sys.path) > 0:
                sys.executable = sys.path[0]
            else:
                sys.executable = sys.__file__ #@UndefinedVariable

        help(np)

if __name__ == "__main__":
    #import sys
    #sys.argv = ['', 'Test.testName']
    unittest.main()
