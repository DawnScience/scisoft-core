#
# Use with
# $ python all_test.py
# or
# $ python -m unittest all_test
# or else just:
# $ python -m unittest discover src "*_test.py"

import os
def load_tests(loader, standard_tests, pattern):
    this_dir = os.path.dirname(__file__)
    package_tests = loader.discover(start_dir=this_dir, pattern='*_test.py')
    standard_tests.addTests(package_tests)
    return standard_tests

if __name__ == '__main__':
    import unittest
    unittest.main()
