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
Analysis RPC Package
'''

import os
if os.name == 'java':
    import jython.jyrpc as _rpc #@UnusedImport
    import jython.jywrapper as _wrapper #@UnusedImport
    import jython.jyflatten as _flatten #@UnusedImport
else:
    import python.pyrpc as _rpc #@Reimport
    import python.pywrapper as _wrapper #@Reimport
    import python.pyflatten as _flatten #@Reimport

rpcserver=_rpc.rpcserver
rpcclient=_rpc.rpcclient
typednone=_wrapper.typednone
abstractdatasetdescriptor=_wrapper.abstractdatasetdescriptor
binarywrapper=_wrapper.binarywrapper
settemplocation=_flatten.settemplocation


if __name__ == '__main__':
    # When run as a script, launches an RPC Server
    import sys

    serverPort = int(sys.argv[1])
    server = rpcserver(serverPort)

    def addHandlers(code, handler_names):
        ''' Add new handlers to the running server.
            The code is 'exec'uted with custom dictionaries, the names
            in handler_names must be defined by code and then
            are added to the server
        '''
        g = dict(globals())
        l = dict()
        exec(code, g, l)
        for handler_name in handler_names:
            server.add_handler(handler_name, l[handler_name])
    server.add_handler('addHandlers', addHandlers)

    def setPlottingPort(port):
        if port > 0:
            import scisoftpy.plot as plot
            plot.setremoteport(rpcport=port)
    server.add_handler('setPlottingPort', setPlottingPort)

    # Run the server's main loop
    server.serve_forever()
