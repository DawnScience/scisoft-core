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
Test pyplot by setting up a "fake" SDAPlotter server
'''
import unittest

import scisoftpy.python.pyplot as plot
import scisoftpy.python.pyrpc as rpc

PORT = 8715

from pyrpc_test import _start_new_thread

class Test(unittest.TestCase):

    def setUp(self):
        self.rpcserver = rpc.rpcserver(PORT)
        self.rpcserver.add_handler('SDAPlotter', lambda plotter_method_name, *args: (plotter_method_name, args))
        _start_new_thread(self.rpcserver.serve_forever)
        
        self.plot = plot.plotter()
        plot.setremoteport(PORT)

    def tearDown(self):
        self.rpcserver.shutdown()
        self.rpcserver.close()


    def testBasic(self):
        (plotter_method_name, args) = self.plot.plot_line(0)
        self.assertEqual('plot', plotter_method_name)
        self.assertEqual([0], args)
        
    def testCallByAttribute(self):
        (plotter_method_name, args) = self.plot.__getattr__('plot_line')(0)
        self.assertEqual('plot', plotter_method_name)
        self.assertEqual([0], args)

    def testNoSuchMethod(self):
        self.assertRaises(AttributeError, self.plot.__getattr__, ('unknown_plot_method',))
        try:
            self.plot.unknown_plot_method(0)
            self.fail()
        except AttributeError:
            pass

    def testOrder(self):
        '''Tests that the order returned match those expected in SDAPlotter'''
        self.assertEqual(0, self.plot.plot_orders['none'])
        self.assertEqual(1, self.plot.plot_orders['alpha'])
        self.assertEqual(2, self.plot.plot_orders['chrono'])

    def testOrderInvalid(self):
        self.assertRaises(KeyError, self.plot.plot_orders.__getitem__, 'unknown_order')

def suite():
    suite = unittest.TestSuite()
    suite.addTest(unittest.TestLoader().loadTestsFromTestCase(Test))
    return suite 

if __name__ == '__main__':
    unittest.TextTestRunner(verbosity=2).run(suite())
