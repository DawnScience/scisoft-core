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

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
