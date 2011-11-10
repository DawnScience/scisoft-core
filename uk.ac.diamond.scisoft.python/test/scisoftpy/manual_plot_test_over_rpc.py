'''
Run this test manually to sanity check plotting in a running SDA when this 
script is launched from the host Eclipse Dev
This tests checks that the RPC Client in Python and Jython works by making a plotting
call to SDA over RPC (as opposed to how manual_plot_test does it in the user
friendly way and uses scisoftpy.plot)
Launch this file twice, once for python, once for jython
''' 

# Note: manual add of path is needed when this is run as an automated test by:
# AutomatedManualPlottingPluginTest.java
import os, sys
scisoftpath = os.getcwd() + '/../uk.ac.diamond.scisoft.python/src'
sys.path.append(scisoftpath)
import scisoftpy as dnp

a = dnp.arange(100)
a.shape = (10,10)

client = dnp.rpc.rpcclient(8610)
# This is only an example of how to use RPCClient, do not use it to call 
# plotting functions, use scisoftpy.plot for that!
if os.name == "java":
    plotName = "Plot 1 RPC Java"
else:
    plotName = "Plot 1 RPC Python"
client.request("SDAPlotter", ("imagePlot", plotName, a))
