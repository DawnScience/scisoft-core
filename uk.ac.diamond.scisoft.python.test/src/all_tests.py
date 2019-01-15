
import os

suites = []
from scisoftpy_tests import dataset_test, scisoft_test, random_test, linalg_test, nx_test, array_zeros_like_test, keepdims_test, multiple_axes_test
suites.append(dataset_test.suite())

suites.append(scisoft_test.suite())
suites.append(random_test.suite())
suites.append(linalg_test.suite())
suites.append(nx_test.suite())
suites.append(array_zeros_like_test.suite())
suites.append(keepdims_test.suite())
suites.append(multiple_axes_test.suite())

from scisoftpy_tests import fft_test, signal_test, image_test, roi_test, io_test
suites.append(fft_test.suite())
suites.append(signal_test.suite())
if os.name == 'java':
    suites.append(image_test.suite())
suites.append(roi_test.suite())
suites.append(io_test.suite())

if os.name == 'java':
    from scisoftpy_tests import fit_test, convertArgsToArray_test
    suites.append(fit_test.suite())
    suites.append(convertArgsToArray_test.suite())
else:
    from scisoftpy_tests import pyflatten_test, pyplot_test, pyrpc_test, pyrpc_exceptions_test, pyrpc_advanced_test
    suites.append(pyflatten_test.suite())
    suites.append(pyplot_test.suite())
    suites.append(pyrpc_test.suite())
    suites.append(pyrpc_exceptions_test.suite())
    suites.append(pyrpc_advanced_test.suite())

from scisoftpy_tests import external_test
suites.append(external_test.suite())

import unittest
def suite():
    suite = unittest.TestSuite(suites)
    return suite

if __name__ == '__main__':
    unittest.TextTestRunner(verbosity=2).run(suite())
