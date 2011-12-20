###
# Copyright © 2011 Diamond Light Source Ltd.
# Contact :  ScientificSoftware@diamond.ac.uk
# 
# This is free software: you can redistribute it and/or modify it under the
# terms of the GNU General Public License version 3 as published by the Free
# Software Foundation.
# 
# This software is distributed in the hope that it will be useful, but 
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
# Public License for more details.
# 
# You should have received a copy of the GNU General Public License along
# with this software. If not, see <http://www.gnu.org/licenses/>.
###

'''
Test pyplot by setting up a "fake" SDAPlotter server
'''
import unittest

import scisoftpy.python.pyplot as plot
import scisoftpy.python.pyrpc as rpc
import thread

PORT = 8719

class Test(unittest.TestCase):

    def setUp(self):
        self.rpcserver = rpc.rpcserver(PORT)
        self.rpcserver.add_handler('SDAPlotter', lambda plotter_method_name, *args: (plotter_method_name, args))
        thread.start_new_thread(self.rpcserver.serve_forever, ())
        
        self.plot = plot.plotter(PORT)

    def tearDown(self):
        self.rpcserver.shutdown()
        self.rpcserver.close()


    def testBasic(self):
        (plotter_method_name, args) = self.plot.plot_line(0)
        self.assertEquals('plot', plotter_method_name)
        self.assertEquals([0], args)
        
    def testCallByAttribute(self):
        (plotter_method_name, args) = self.plot.__getattr__('plot_line')(0)
        self.assertEquals('plot', plotter_method_name)
        self.assertEquals([0], args)

    def testNoSuchMethod(self):
        self.assertRaises(AttributeError, self.plot.__getattr__, ('unknown_plot_method',))
        try:
            self.plot.unknown_plot_method(0)
            self.fail()
        except AttributeError:
            pass

    def testOrder(self):
        '''Tests that the order returned match those expected in SDAPlotter'''
        self.assertEquals(0, self.plot.order('none'))
        self.assertEquals(1, self.plot.order('alpha'))
        self.assertEquals(2, self.plot.order('chrono'))

    def testOrderInvalid(self):
        self.assertRaises(ValueError, self.plot.order, ('unknown_order',))


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
