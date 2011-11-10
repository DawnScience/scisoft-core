import os, sys
scisoftpath = os.getcwd() + '/../uk.ac.diamond.scisoft.python/src'
sys.path.append(scisoftpath)
import scisoftpy as dnp #@UnresolvedImport
import thread

interal_rpcclient = dnp.rpc.rpcclient(8715)
rpcserver = dnp.rpc.rpcserver(8714)
rpcserver.add_handler("loopback", lambda arg: arg)
rpcserver.add_handler("loopback_after_local", lambda arg: interal_rpcclient.loopback(arg))
thread.start_new_thread(rpcserver.serve_forever, ())

# This is the server that is going to loopback locally to python
rpcserver = dnp.rpc.rpcserver(8715)
rpcserver.add_handler("loopback", lambda arg: arg)
rpcserver.serve_forever()
