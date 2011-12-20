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
Created on 1 May 2011

@author: Jonah
'''
import unittest
import scisoftpy.python.pyrpc as rpc
import thread

PORT = 8713

def catTwoStrings(string1, string2):
    return string1 + string2

class Test(unittest.TestCase):

    def testBasic(self):
        
        rpcserver = rpc.rpcserver(PORT)
        rpcserver.add_handler("cat", catTwoStrings)
        
        thread.start_new_thread(rpcserver.serve_forever, ())
        try:
            rpcclient = rpc.rpcclient(PORT)
            result = rpcclient.cat("Hello, ", "World!")
            
            self.assertEqual("Hello, World!", result)
        finally:
            rpcserver.shutdown()
            rpcserver.close()


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
