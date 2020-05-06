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
Created on 1 May 2011

@author: Jonah
'''
import unittest
import scisoftpy.python.pyrpc as rpc

PORT = 8713

def _start_new_thread(target):
    import threading
    t = threading.Thread(target=target)
    t.start()


def catTwoStrings(string1, string2):
    return string1 + string2

class Test(unittest.TestCase):

    def testBasic(self):
        
        rpcserver = rpc.rpcserver(PORT)
        rpcserver.add_handler("cat", catTwoStrings)
        
        _start_new_thread(rpcserver.serve_forever)
        try:
            rpcclient = rpc.rpcclient(PORT)
            result = rpcclient.cat("Hello, ", "World!")
            
            self.assertEqual("Hello, World!", result)
        finally:
            rpcserver.shutdown()
            rpcserver.close()

def suite():
    suite = unittest.TestSuite()
    suite.addTest(unittest.TestLoader().loadTestsFromTestCase(Test))
    return suite 

if __name__ == '__main__':
    unittest.TextTestRunner(verbosity=2).run(suite())
