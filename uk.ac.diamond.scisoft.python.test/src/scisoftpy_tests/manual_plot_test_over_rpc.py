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
sys.path.append(sys.argv[1])
import scisoftpy as dnp

a = dnp.arange(100)
a.shape = (10,10)

client = dnp.rpc.rpcclient(int(os.getenv("SCISOFT_RPC_PORT")))
# This is only an example of how to use RPCClient, do not use it to call 
# plotting functions, use scisoftpy.plot for that!
if os.name == "java":
    plotName = "Plot 1 RPC Java"
else:
    plotName = "Plot 1 RPC Python"
client.request("SDAPlotter", ("imagePlot", plotName, a))
