###
# Copyright 2014 Diamond Light Source Ltd.
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
Test linalg class
import unittest
unittest.TestProgram(argv=["linalg_test"])
'''
import unittest

import scisoftpy as np
import scisoftpy.linalg as la

def toInt(o):
    return int(o)

def toAny(o):
    return o

class Test(unittest.TestCase):

    def checkitems(self, la, ds, convert=toAny):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertAlmostEquals(convert(la[i]), ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertAlmostEquals(convert(la[i][j]), ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertAlmostEquals(convert(la[i][j][k]), ds[i, j, k])

    def testDot(self):
        a = np.arange(1,9).reshape(2, 2, 2)
        b = np.array([-2, -3, 5, 7]).reshape(2, 2)
        self.checkitems([35, 63], np.tensordot(a, b))
        self.checkitems([63, 70], np.tensordot(b, a))
        a = np.arange(60.).reshape(3,4,5)
        b = np.arange(24.).reshape(4,3,2)
        self.checkitems([[ 4400.,  4730.], [ 4532.,  4874.],
       [ 4664.,  5018.], [ 4796.,  5162.], [ 4928.,  5306.]], np.tensordot(a,b, axes=([1,0],[0,1])))

    def testPower(self):
        a = np.array([[0, 1], [-1, 0]]) # matrix equiv. of the imaginary unit
        self.checkitems([[0, -1], [1, 0]], np.linalg.matrix_power(a, 3))
        self.checkitems([[1, 0], [0, 1]], np.linalg.matrix_power(a, 0))
        self.checkitems([[0, 1], [-1, 0]], np.linalg.matrix_power(a, -3))

    def testNorm(self):
        a = np.arange(9) - 4
        b = a.reshape((3, 3))
        self.assertAlmostEqual(7.745966692414834, la.norm(a))
        self.assertAlmostEqual(7.745966692414834, la.norm(b))
        self.assertAlmostEqual(7.745966692414834, la.norm(b, 'fro'))
        self.assertAlmostEqual(4, la.norm(a, np.inf))
        self.assertAlmostEqual(9, la.norm(b, np.inf))
        self.assertAlmostEqual(0, la.norm(a, -np.inf))
        self.assertAlmostEqual(2, la.norm(b, -np.inf))
        self.assertAlmostEqual(20, la.norm(a, 1))
        self.assertAlmostEqual(7, la.norm(b, 1))
        self.assertAlmostEqual(-4.6566128774142013e-010, la.norm(a, -1))
        self.assertAlmostEqual(6, la.norm(b, -1))
        self.assertAlmostEqual(7.745966692414834, la.norm(a, 2))
        self.assertAlmostEqual(7.3484692283495345, la.norm(b, 2))
#        self.assertTrue(np.isnan(la.norm(a, -2)))
        self.assertAlmostEqual(1.8570331885190563e-016, la.norm(b, -2))
        self.assertAlmostEqual(5.8480354764257312, la.norm(a, 3))
#        self.assertTrue(np.isnan(la.norm(a, -3)))

    def testCond(self):
        a = np.array([[1, 0, -1], [0, 1, 0], [1, 0, 1]])
        self.assertAlmostEqual(1.4142135623730951, np.linalg.cond(a))
        self.assertAlmostEqual(3.1622776601683795, np.linalg.cond(a, 'fro'))
        self.assertAlmostEqual(2.0, np.linalg.cond(a, np.inf))
        self.assertAlmostEqual(1.0, np.linalg.cond(a, -np.inf))
        self.assertAlmostEqual(2.0, np.linalg.cond(a, 1))
        self.assertAlmostEqual(1.0, np.linalg.cond(a, -1))
        self.assertAlmostEqual(1.4142135623730951, np.linalg.cond(a, 2))
        self.assertAlmostEqual(0.70710678118654746, np.linalg.cond(a, -2))

    def testDet(self):
        a = np.array([[1, 2], [3, 4]])
        self.assertAlmostEqual(-2.0, np.linalg.det(a))

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
