'''
Test fast Fourier transforms

import unittest
unittest.TestProgram(argv=["fft_test"])
'''
import unittest
import scisoftpy as np
import scisoftpy.fft as fft

class Test(unittest.TestCase):


    def setUp(self):
        pass

    def tearDown(self):
        pass

    def checkrealitems(self, dr, ds, places=7):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertAlmostEquals(dr[i], ds[i].real, places)
                self.assertAlmostEquals(0, ds[i].imag, places)
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertAlmostEquals(dr[i, j], ds[i, j].real, places)
                    self.assertAlmostEquals(0, ds[i, j].imag, places)
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertAlmostEquals(dr[i, j, k], ds[i, j, k].real, places)
                        self.assertAlmostEquals(0, ds[i, j, k].imag, places)

    def checkcomplexitems(self, lz, ds, places=7):
        if ds.ndim == 1:
            for i in range(ds.shape[0]):
                self.assertAlmostEquals(lz[i].real, ds[i].real, places)
                self.assertAlmostEquals(lz[i].imag, ds[i].imag, places)
        elif ds.ndim == 2:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    self.assertAlmostEquals(lz[i][j].real, ds[i, j].real, places)
                    self.assertAlmostEquals(lz[i][j].imag, ds[i, j].imag, places)
        elif ds.ndim == 3:
            for i in range(ds.shape[0]):
                for j in range(ds.shape[1]):
                    for k in range(ds.shape[2]):
                        self.assertAlmostEquals(lz[i][j][k].real, ds[i, j, k].real, places)
                        self.assertAlmostEquals(lz[i][j][k].imag, ds[i, j, k].imag, places)

    def testFFT1(self):
        az = [ 66. +0.j        ,  -6.+22.39230485j,  -6.+10.39230485j,
        -6. +6.j        ,  -6. +3.46410162j,  -6. +1.60769515j,
        -6. +0.j        ,  -6. -1.60769515j,  -6. -3.46410162j,
        -6. -6.j        ,  -6.-10.39230485j,  -6.-22.39230485j]
        dsa = np.arange(12.)
        ada = fft.fft(dsa)
        self.checkcomplexitems(az, ada)
        ida = fft.ifft(ada)
        self.checkrealitems(dsa, ida)

        dsa = np.arange(12,dtype=np.float32)
        ada = fft.fft(dsa)
        self.checkcomplexitems(az, ada, places=5)
        ida = fft.ifft(ada)
        self.checkrealitems(dsa, ida, places=5)

        dsa = np.arange(12.)
        dsa.shape = (3,4)
        bz = [[  6.+0.j,  -2.+2.j,  -2.+0.j,  -2.-2.j],
       [ 22.+0.j,  -2.+2.j,  -2.+0.j,  -2.-2.j],
       [ 38.+0.j,  -2.+2.j,  -2.+0.j,  -2.-2.j]]
        ada = fft.fft(dsa)
        self.checkcomplexitems(bz, ada)
        ida = fft.ifft(ada)
        self.checkrealitems(dsa, ida)

        dsa = np.arange(12,dtype=np.float32)
        dsa.shape = (3,4)
        ada = fft.fft(dsa)
        self.checkcomplexitems(bz, ada, places=5)
        ida = fft.ifft(ada)
        self.checkrealitems(dsa, ida, places=5)

        dsa = np.arange(12.)
        dsa.shape = (3,4)
        cz = [[ 12.+0.j        ,  15.+0.j        ,  18.+0.j        ,  21.+0.j        ],
       [ -6.+3.46410162j,  -6.+3.46410162j,  -6.+3.46410162j,
         -6.+3.46410162j],
       [ -6.-3.46410162j,  -6.-3.46410162j,  -6.-3.46410162j,
         -6.-3.46410162j]]
        ada = fft.fft(dsa,axis=0)
        self.checkcomplexitems(cz, ada)
        ida = fft.ifft(ada, axis=0)
        self.checkrealitems(dsa, ida)

        dsa = np.arange(12,dtype=np.float32)
        dsa.shape = (3,4)
        ada = fft.fft(dsa,axis=0)
        self.checkcomplexitems(cz, ada, places=5)
        ida = fft.ifft(ada, axis=0)
        self.checkrealitems(dsa, ida, places=5)

        dsa = np.arange(12.)
        dsa.shape = (3,4)
        dz = [[  6.00000000 + 0.j        , -3.73606798 - 0.36327126j,
          0.73606798 - 1.53884177j, 0.73606798 + 1.53884177j,
         - 3.73606798 + 0.36327126j],
       [ 22.00000000 + 0.j        , -4.97213595 - 4.16749733j,
          3.97213595 - 3.88998278j, 3.97213595 + 3.88998278j,
         - 4.97213595 + 4.16749733j],
       [ 38.00000000 + 0.j        , -6.20820393 - 7.97172339j,
          7.20820393 - 6.24112379j, 7.20820393 + 6.24112379j,
         - 6.20820393 + 7.97172339j]]
        ada = fft.fft(dsa, n=5)
        self.checkcomplexitems(dz, ada)
        ida = fft.ifft(ada, n=5)
        self.checkrealitems(dsa, ida[:,:4])

        dsa = np.arange(12,dtype=np.float32)
        dsa.shape = (3,4)
        ada = fft.fft(dsa, n=5)
        self.checkcomplexitems(dz, ada, places=5)
        ida = fft.ifft(ada, n=5)
        self.checkrealitems(dsa, ida[:,:4], places=5)

        dsa = np.arange(12, dtype=np.complex)

if __name__ == "__main__":
    #import sys
    #sys.argv = ['', 'Test.testCorrelate']
    unittest.main()
