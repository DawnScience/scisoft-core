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