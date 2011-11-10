# Set up environment
import os, sys
scisoftpath = os.getcwd() + '/../uk.ac.diamond.scisoft.python/src'
sys.path.append(scisoftpath)
import scisoftpy as dnp #@UnresolvedImport

# define fancy function which is easier to write in Python
def python_cos(ds):
    '''Performs a cos() on ds input'''
    return dnp.cos(ds)


# Make the fancy function available
rpcserver = dnp.rpc.rpcserver(8751)
rpcserver.add_handler("cos", python_cos)
rpcserver.serve_forever()
