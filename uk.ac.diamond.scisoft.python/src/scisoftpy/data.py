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
Wrapper of dataset sending and getting functionality in DAWN
'''

import os

if os.name == 'java':
    import jython.jydata as _data
else:
    import python.pydata as _data


def send(data):
    _data.send(data)
    
def get(name):
    _data.get(name)