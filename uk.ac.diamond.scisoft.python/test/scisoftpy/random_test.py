'''
Test random class
import unittest
unittest.TestProgram(argv=["random_test"])
'''
import unittest

import scisoftpy as np
import scisoftpy.random as rnd

class Test(unittest.TestCase):


    def testRandom(self):
        import os
        if os.name == 'java':
            import jarray
            ja = jarray.array([1,2], 'i')
        else:
            ja = np.array([1,2])
        print np.asIterable(ja)

        print rnd.rand()
        print rnd.rand(1)
        print rnd.rand(2,4)
        print rnd.randn()
        print rnd.randn(1)
        print rnd.randn(2,4)
        for i in range(10):
            print i, rnd.randint(1)
        print rnd.randint(2)
        print rnd.randint(5, size=[2,4])
        print rnd.random_integers(1)
        print rnd.random_integers(5, size=[2,4])
        print rnd.exponential(1.1)
        print rnd.exponential(1.1, [2,4])
        print rnd.poisson(1.1)
        print rnd.poisson(1.1, [2,4])
        a = np.ones([2,3])
        print rnd.poisson(1.2, a.shape)
        rnd.seed()
        print rnd.rand(2,3)
        rnd.seed()
        print rnd.rand(2,3)
        rnd.seed(12343)
        print rnd.rand(2,3)
        rnd.seed(12343)
        print rnd.rand(2,3)
        a = rnd.rand(200,300)
        print a.mean(), a.rms()



if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
