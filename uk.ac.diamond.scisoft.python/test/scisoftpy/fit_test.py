'''
Test random class
import unittest
unittest.TestProgram(argv=["fit_test"])
'''
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
                self.assertAlmostEquals(pl*t, pl*f, places=0)

    def testFit(self):
        d = dnp.array([ 3.5733e+00, 2.1821e+00, 1.8313e+00, 1.9893e+00, 8.3145e-01,
            9.8761e-01, 7.1809e-01, 8.6756e-01, 2.3144e-01, 6.6659e-01, 3.8420e-01,
            3.2560e-01, 6.0712e-01, 5.0191e-01, 5.1308e-01, 2.8631e-01, 2.3811e-01,
            7.6472e-02, 2.1317e-01, 9.1819e-02 ])
        fr = fit.fit([myfunc, fit.function.offset], self.x, d, [2.5, 1.2, 0.1], [(0,4), 0, (-0.2,0.7)])
    
        print 'Fit: ', fr  # print fit result
        self.checkitems([3., 1.5], fr.parameters)

        fr = fit.fit([myfunc, fit.function.offset], self.x, d[:-1], [2.5, 1.2, 0.1], [(0,4), 0, (-0.2,0.7)])

    def testPoly(self):
        fr = fit.polyfit(self.x, self.y, 1)
    
        print 'Poly: ', fr  # print polynomial coeffs
        self.checkitems([3.2, 0.35], fr, 1)

        fr = fit.polyfit(self.x, self.z, 2)
    
        print 'Poly: ', fr  # print polynomial coeffs
        self.checkitems([3.2, -12.2, 0.35], fr, 1)

        v = fit.polyval(fr, [0,1])
        print 'value is', dnp.abs(v-0.3)
        self.checkitems([0.1, -9], v, 1)

    def testPolyG(self):
        fr = fit.fit([fit.function.linear], self.x, self.y, [2.5, 0.8], [(0.1,4), (0.,1.7)], seed=123)
    
        print 'PolyG: ', fr  # print fit result
        self.checkitems([3.2, 0.35], fr.parameters, 1)

        fr = fit.fit([fit.function.quadratic], self.x, self.z, [2.5, -20., 0.8], [(0.1,4), (-40, 10), (0.,1.7)], seed=123)
    
        print 'PolyG: ', fr  # print fit result
        self.checkitems([4.0, -12.2, 0.35], fr.parameters, 1)

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
