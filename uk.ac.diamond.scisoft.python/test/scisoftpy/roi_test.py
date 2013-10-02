###
# Copyright 2013 Diamond Light Source Ltd.
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
Test roi module
import unittest
unittest.TestProgram(argv=["roi_test"])
'''
import unittest

import math
import scisoftpy as np

class Test(unittest.TestCase):

    def testROIs(self):
        r = np.plot.roi.point()
        r.point = 50, -50
        print r
        self.assertEqual(50, r.point[0])
        self.assertEqual(-50, r.point[1])
        
        r = np.plot.roi.point(point=[50, -50])
        print r
        self.assertEqual(50, r.point[0])
        self.assertEqual(-50, r.point[1])

        r = np.plot.roi.line()
        r.point = 50, -50
        r.length = 100
        r.angledegrees = 45
        print r
        self.assertEqual(50, r.point[0])
        self.assertEqual(-50, r.point[1])
        self.assertEqual(100, r.length)
        self.assertEqual(45, r.angledegrees)

        r = np.plot.roi.line(point=[50, -50], length=100, angle=math.pi*0.25)
        print r
        self.assertEqual(50, r.point[0])
        self.assertEqual(-50, r.point[1])
        self.assertEqual(100, r.length)
        self.assertEqual(45, r.angledegrees)

        r = np.plot.roi.rectangle()
        r.point = 50, -50
        r.lengths = 100, 23
        r.angledegrees = 45
        print r
        self.assertEqual(50, r.point[0])
        self.assertEqual(-50, r.point[1])
        self.assertEqual(100, r.lengths[0])
        self.assertEqual(23, r.lengths[1])
        self.assertEqual(45, r.angledegrees)
        
        r = np.plot.roi.rectangle(point=[50, -50], lengths=[100,23], angle=math.pi*0.25)
        print r
        self.assertEqual(50, r.point[0])
        self.assertEqual(-50, r.point[1])
        self.assertEqual(100, r.lengths[0])
        self.assertEqual(23, r.lengths[1])
        self.assertEqual(45, r.angledegrees)

        r = np.plot.roi.sector()
        r.point = 50,-50
        r.anglesdegrees = -60, 24
        r.radii = 10, 24
        print r
        self.assertEqual(50, r.point[0])
        self.assertEqual(-50, r.point[1])
        self.assertAlmostEqual(300, r.anglesdegrees[0])
        self.assertAlmostEqual(384, r.anglesdegrees[1])
        self.assertEqual(10, r.radii[0])
        self.assertEqual(24, r.radii[1])
        
        r = np.plot.roi.sector(point=[50,-50], angles=[0,math.pi], radii=[30,50])
        print r
        self.assertEqual(50, r.point[0])
        self.assertEqual(-50, r.point[1])
        self.assertEqual(0, r.angles[0])
        self.assertEqual(180, r.anglesdegrees[1])
        self.assertEqual(30, r.radii[0])
        self.assertEqual(50, r.radii[1])

        r = np.plot.roi.circle()
        r.point = 50, -50
        r.radius = 100
        print r
        self.assertEqual(50, r.point[0])
        self.assertEqual(-50, r.point[1])
        self.assertEqual(100, r.radius)

        r = np.plot.roi.circle(point=[50, -50], radius=100)
        print r
        self.assertEqual(50, r.point[0])
        self.assertEqual(-50, r.point[1])
        self.assertEqual(100, r.radius)

        r = np.plot.roi.ellipse()
        r.point = 50, -50
        r.semiaxes = 100, 75
        r.angledegrees = 45
        print r
        self.assertEqual(50, r.point[0])
        self.assertEqual(-50, r.point[1])
        self.assertEqual(100, r.semiaxes[0])
        self.assertEqual(75, r.semiaxes[1])
        self.assertEqual(45, r.angledegrees)

        r = np.plot.roi.ellipse(point=[50, -50], semiaxes=[100,75], angle=math.pi*0.25)
        print r
        self.assertEqual(50, r.point[0])
        self.assertEqual(-50, r.point[1])
        self.assertEqual(100, r.semiaxes[0])
        self.assertEqual(75, r.semiaxes[1])
        self.assertEqual(45, r.angledegrees)

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
