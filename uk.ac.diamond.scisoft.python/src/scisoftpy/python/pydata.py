'''
Python side implementation of DatasetManager connection
'''

import pyplot as _plot # We are going to reuse its _get_rpcclient()

def send(*arg, **kwarg):
    _plot._get_rpcclient().DatasetManager("send", *arg)

def get(*arg, **kwarg):
    return _plot._get_rpcclient().DatasetManager("get", *arg)
