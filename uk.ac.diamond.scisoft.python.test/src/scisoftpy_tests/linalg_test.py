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

class Test(unittest.TestCase):

    def checkitems(self, la, ds):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertAlmostEqual(la[i], ds[i])
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertAlmostEqual(la[i][j], ds[i, j])
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertAlmostEqual(la[i][j][k], ds[i, j, k])

    def testDot(self):
        a = np.arange(1,13).reshape(2, 3, 2)
        b = np.array([-2, -3, 5, 7, 9, -11]).reshape(3, 2)
        self.checkitems([14, 44], np.tensordot(a, b))
        self.checkitems([-21, -16], np.tensordot(b.T, a))
        self.checkitems([-21, -16], np.tensordot(b, a, ((0,1), (1,0))))
        self.checkitems([-21, -16], np.tensordot(b, a, ((1,0), (0,1))))
        bt = b.T.reshape(1,2,3)
        self.checkitems([[[ -8,  19, -13], [-18,  43, -17], [-28,  67, -21]],
                         [[-38,  91, -25], [-48, 115, -29], [-58, 139, -33]]], np.matmul(a, bt))
        self.checkitems([[[ 58,  70], [-37, -44]], [[130, 142], [-79, -86]]], np.matmul(bt, a))
        self.checkitems([[[[ -8,  19, -13]], [[-18,  43, -17]], [[-28,  67, -21]]],
                         [[[-38,  91, -25]], [[-48, 115, -29]], [[-58, 139, -33]]]], np.dot(a, bt))
        self.checkitems([[[[ 58,  70], [130, 142]], [[-37, -44], [-79, -86]]]], np.dot(bt, a))
        m1 = np.array([[1.0, 0],[0 , 0.]])
        m2 = np.array([[0.70712579, -0.70708778], [0.70708777, 0.70712578]])
        self.checkitems([[0.70712579, -0.70708778], [0.0000000, 0.0000000]], np.dot(m1, m2))
        self.checkitems([[ 0.70712579+0.j, -0.70708778+0.j], [ 0. +0.j,  0. +0.j]], np.dot(m1, m2 + 0.j))
        self.checkitems([[ 0.70712579+0.5j, -0.70708778+0.5j], [ 0. +0.j,  0. +0.j]], np.dot(m1, m2 + 0.5j))

        a = np.arange(60.).reshape(3,4,5)
        b = np.arange(24.).reshape(4,3,2)
        self.checkitems([[ 4400.,  4730.], [ 4532.,  4874.], [ 4664.,  5018.], [ 4796.,  5162.], [ 4928.,  5306.]],
                        np.tensordot(a,b, axes=([1,0],[0,1])))


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
        self.assertAlmostEqual(-2.0, np.linalg.det(a+0.j))
        import os
        if os.name == 'java':
            with self.assertRaises(ValueError):
                np.linalg.det(a+0.5j)
        else:
            self.assertAlmostEqual(-2.0, np.linalg.det(a+0.5j))

    def testTrace(self):
        a = np.array([[1, 2], [3, 4]])
        self.assertAlmostEqual(5, np.trace(a))
        self.assertAlmostEqual(5, np.trace(a+0.j))
        self.assertAlmostEqual(5+1j, np.trace(a+0.5j))

if __name__ == '__main__':
    unittest.main(verbosity=2)
