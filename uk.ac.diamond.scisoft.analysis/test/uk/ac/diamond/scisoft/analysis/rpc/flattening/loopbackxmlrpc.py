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

import os, sys
scisoftpath = os.getcwd() + '/../uk.ac.diamond.scisoft.python/src'
sys.path.append(scisoftpath)
import scisoftpy as dnp #@UnresolvedImport
from SimpleXMLRPCServer import SimpleXMLRPCServer
from SimpleXMLRPCServer import SimpleXMLRPCRequestHandler

# Create server
class RequestHandler(SimpleXMLRPCRequestHandler):
    rpc_paths = ('/xmlrpc',)
server = SimpleXMLRPCServer(("127.0.0.1", 8713), requestHandler=RequestHandler, logRequests=False)
server.register_introspection_functions()

def runflat(x):
    obj = x[0]
    if not dnp.flatten.canunflatten(obj):
        raise Exception("Can't unflatten")
    unflat = dnp.flatten.unflatten(obj)
    reflat = dnp.flatten.flatten(unflat)
    return reflat
server.register_function(runflat)

def loopback(x):
    return x[0]
server.register_function(loopback)


# Run the server's main loop
server.serve_forever()
