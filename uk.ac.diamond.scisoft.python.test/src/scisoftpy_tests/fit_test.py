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
Test random class
import unittest
unittest.TestProgram(argv=["fit_test"])
'''
from __future__ import print_function

import os
if os.name == 'java':
    import unittest

    import scisoftpy as dnp
    import scisoftpy.fit as fit

    def myfunc(pa, pb, x, *arg):
        '''pa -- parameter a
        pb -- parameter b
        x -- list of coordinate datasets
        arg -- tuple of additional arguments
        '''
        return pa*dnp.exp(-pb*x[0])

    class Test(unittest.TestCase):
        def __init__(self, *args):
            unittest.TestCase.__init__(self, *args)
            self.x = dnp.linspace(0, 5, 20)
            dnp.random.seed(12347)
            self.n = dnp.random.randn(20)*0.1
            self.y = 3.2*self.x + 0.35 + self.n
            self.z = 3.2*self.x*self.x - 12.2*self.x + 0.35 + self.n

        def checkitems(self, pt, pf, places=0):
            import math.pow as pow
            pl = pow(10., places-1)
            for i in range(len(pt)):
                t = pt[i]
                f = pf[i]
                if max(t, f) > 1e-10:
                    self.assertAlmostEqual(pl*t, pl*f, places=places)

        def testFunction(self):
            print(dnp.fit.function.isjclass(dnp.fit.function.linear))
            f = dnp.fit.function.linear([2.5, -4.2])
            print(dnp.fit.function.isjclass(f))
            print(dnp.fit.function.isjclass(dnp.fit.function.linear))
            print(dnp.fit.function.isjmethod(f))
            print(dnp.fit.function.isjmethod(dnp.fit.function.linear))
            x = dnp.arange(10.)
            print(f.calculateValues(x))
            print(f.calculateValues([x]))
            pl = dnp.jython.fitcore._createparams(2, [0.4, 4.5], [])
            ff = dnp.fit.fitfunc(myfunc, myfunc.__name__, pl)
            self.assertAlmostEqual(f.val(0), -4.2)
            self.assertAlmostEqual(ff.val(0), 0.4)

            d = f.calculateValues(x)
            dd = ff.calculateValues(x)

            self.assertAlmostEqual(f.residual(True, d, None, x), 0)
            self.assertAlmostEqual(ff.residual(True, dd, None, x), 0)

        def testFit(self):
            d = dnp.array([ 3.5733e+00, 2.1821e+00, 1.8313e+00, 1.9893e+00, 8.3145e-01,
                9.8761e-01, 7.1809e-01, 8.6756e-01, 2.3144e-01, 6.6659e-01, 3.8420e-01,
                3.2560e-01, 6.0712e-01, 5.0191e-01, 5.1308e-01, 2.8631e-01, 2.3811e-01,
                7.6472e-02, 2.1317e-01, 9.1819e-02 ])
            fr = fit.fit([myfunc, fit.function.offset], self.x, d, [2.5, 1.2, 0.1], [(0,4), 0, (-0.2,0.7)])

            print('Fit: ', fr)  # print fit result
            self.checkitems([3., 1.5], fr.parameters)

            fr = fit.fit([myfunc, fit.function.offset], self.x[:-1], d[:-1], [2.5, 1.2, 0.1], [(0,4), 0, (-0.2,0.7)])
            fr = fit.fit(fit.function.offset, self.x, d, 0.3, (-0.2,1.7))
            print(fr, fr.area, fr.residual)
            self.assertAlmostEqual(fr.area, 4.27913, 5)
            self.assertAlmostEqual(fr.residual, 14.96090, 5)
            fd = fr.makefuncdata()
            fp = fr.makeplotdata()
            return fr

        def testPoly(self):
            fr = fit.polyfit(self.x, self.y, 1)

            print('Poly: ', fr)  # print polynomial coeffs
            self.checkitems([3.2, 0.37], fr, 1)

            fr = fit.polyfit(self.x, self.z, 2)

            print('Poly: ', fr)  # print polynomial coeffs
            self.checkitems([3.2, -12.2, 0.4], fr, 1)

        def testPolyVal(self):
            fr = fit.polyfit(self.x, self.y, 1)
            v = fit.polyval(fr, [0,1])
            print('value is', dnp.abs(v-0.3))
            self.checkitems([0.36, 3.55], v, 0)

        def testPolyG(self):
            fr = fit.fit([fit.function.linear], self.x, self.y, [2.5, 0.8], [(0.1,4), (0.,1.7)], seed=123)

            print('PolyG: ', fr)  # print fit result
            self.checkitems([3.2, 0.35], fr.parameters, 0)

            fr = fit.fit([fit.function.quadratic], self.x, self.z, [2.5, -20., 0.8], [(0.1,4), (-40, 10), (0.,1.7)], seed=123)

            print('PolyG: ', fr)  # print fit result
            self.checkitems([3.2, -12.2, 0.4], fr.parameters, 1)

        def testPolyRoots(self):
            p = dnp.poly1d([-1.0021380351125523e+62, -1.3325683964593362e+63, -4.3736356299729265e+62,
                           1.5174319075477495e+62, 2.7616709058592895e+61, -5.7735973730761707e+60, -4.2781613377152188e+59,
                           7.2891988413828689e+58, -1.8014489076363173e+56])
            print(p.c)
            print([r for r in p.r])

            print(p(0.5))
            self.assertAlmostEqual(p(0.5), -1.1960e+61, delta=1e+57)
            print(p(p.r))

    if __name__ == '__main__':
        unittest.main(verbosity=2)
