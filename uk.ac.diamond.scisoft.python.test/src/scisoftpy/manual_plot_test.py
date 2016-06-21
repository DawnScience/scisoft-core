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
script is launched from the host Eclipse Dev.
If launched as jython, will use RMI to access plotserver
If launched as python, will use AnalysisRpc to access plotserver, via SDAPlotter
''' 

# Note: manual add of path is needed when this is run as an automated test by:
# AutomatedManualPlottingPluginTest.java
import os, sys
sys.path.append(sys.argv[1])
import scisoftpy as dnp

# When manually testing you may want to check alternate temp locations
# dnp.rpc.settemplocation('d:/temp/bug')

a = dnp.arange(100)
a.shape = (10,10)
if os.name == "java":
    plotName = "Plot 1 DNP Java"
else:
    plotName = "Plot 1 DNP Python"
dnp.plot.image(a, name=plotName)

