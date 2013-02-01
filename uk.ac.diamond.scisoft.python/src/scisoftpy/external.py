###
# Copyright 2013 Diamond Light Source Ltd.
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

'''External python caller
'''

import os
if os.name == 'java':
    from jython.jycore import ndarray
else:
    from python.pycore import ndarray

_env = os.environ


'''
This is a way to create an external python process, execute a function,
with argument passing and output return, plus exception handling

. pickle arguments
. create python process with python path
.   setup import in process
.   unpickle arguments
.   call function
.   handle exceptions
.   pickle output
. unpickle output
. exception raising
'''

from os import path as _path

from cPickle import dump as _psave, load as _pload

def save_args(arg, dir=None): #@ReservedAssignment
    '''Save arguments as files in a temporary directory
    Use pickle for most objects but use NumPy's npy format for arrays
    arg -- sequence of arguments
    
    Return name of directory used
    '''
    import tempfile as _tmp
    d = _tmp.mkdtemp(prefix='ef-args', dir=dir)
    _n, tree = _pickle(d, arg, 0) # pickle non-sequences
    try: # now do argument structure
        f = open(_path.join(d, "tree.pkl"), 'w')
        _psave(tree, f)
    except:
        raise
    else:
        f.close()

    return d

from io import save as _asave, load as _aload

def _pickle(p, arg, n):
    '''Create structure of all data recursively and replace objects with name.
    It also saves non-sequences.

    Returns tuple of next number and either list, dictionary or name
    '''
    if isinstance(arg, dict):
        d = dict()
        for k in arg:
            n, d[k] = _pickle(p, arg[k], n)
        return n, d
    elif isinstance(arg, (list, tuple)):
        l = list()
        for a in arg:
            n, o = _pickle(p, a, n)
            l.append(o)
        if isinstance(arg, tuple):
            return n, tuple(l)
        return n, l
    elif isinstance(arg, ndarray):
        name = "p%03d.npy" % n
        _asave(_path.join(p, name), arg)
        return n+1, name
    else:
        name = "p%03d.pkl" % n
        try:
            f = open(_path.join(p, name), 'w')
            _psave(arg, f)
        except:
            raise
        else:
            f.close()
        return n+1, name

def load_args(d):
    '''Load arguments from files in a temporary directory
    returns a tuple of arguments 
    '''
    files = os.listdir(d)
    fdict = dict()
    for fa in files:
        fn = _path.join(d, fa)
        if fn.endswith('.pkl'):
            try:
                f = open(fn, 'r')
                fdict[fa] = _pload(f)
            except :
                print
            finally:
                f.close()
        else:
            fdict[fa] = _aload(fn)[0]
            
    return _recreate_args(fdict['tree.pkl'], fdict)

def _recreate_args(arg, fdict):
    '''Unserialize tree from references to dictionary
    '''
    if isinstance(arg, dict):
        d = dict()
        for k in arg:
            d[k] = _recreate_args(arg[k], fdict)
        return d
    elif isinstance(arg, (list, tuple)):
        l = list()
        for a in arg:
            o = _recreate_args(a, fdict)
            l.append(o)
        if isinstance(arg, tuple):
            return tuple(l)
        return l
    else:
        return fdict[arg]

import sys
def wrapper(func):
    '''Decorator to run a function
    '''
    def run_func(*args, **kwargs):
        try:
            ret = func(*args, **kwargs)
        except Exception:
            import traceback
            ex_type, ex_value, tb = sys.exc_info()
            error = ex_type, ex_value, ''.join(traceback.format_tb(tb))
            ret = None
        else:
            error = None
        
        return (ret, error)

    return run_func

def pyenv(exe=None, path=None):
    '''Get python environment
    exe -- python executable
    path -- list of paths
    return tuple containing python executable string, python path as list 
    '''

#    print 'ScisoftPy package is in', pkg
    if exe is None:
        if os.name == 'java':
            pyexe = 'python'
        else:
            pyexe = sys.executable
    else:
        pyexe = exe

    if path is None:
        if os.name == 'java':
            pypath = []
            # add in support to get info from pydev
        else:
            pypath = [ p for p in sys.path if not p.endswith('jar') ]
    else:
        pypath = list(path)

    # add current package
    h, _t = _path.split(__file__)
    pkg, _t = _path.split(h)
    pypath.insert(0, pkg)

    return pyexe, pypath

def get_dls_module(module='numpy', module_init='/etc/profile.d/modules.sh'):
    env = dict(_env)
    env.pop('PYTHONPATH')
    import subprocess as sub
    p = sub.Popen(['bash', '-l'], shell=False, env=env, stdin=sub.PIPE, stdout=sub.PIPE, stderr=sub.PIPE)
    p.stdin.write('source %s\n' % module_init)
    p.stdin.write('module load %s\n' % module)
    p.stdin.write('pyexe=$(which python)\n')
    p.stdin.write('echo "EXEC:$pyexe"\n')
    p.stdin.write('echo "PATH:$PYTHONPATH"\n')
    p.stdin.write('echo "LDPATH:$LD_LIBRARY_PATH"\n')
    p.stdin.close()
    exe = None
    path = None
    ldpath = None
    while True:
        l =  p.stdout.readline()
        if not l:
            break
        l = l.strip()
        if l:
            r = l.split(':')
            if r[0] == 'EXEC':
                exe = r[1]
            elif r[0] == 'PATH':
                path = [a for a in r[1:] if a]
            elif r[0] == 'LDPATH':
                ldpath = [a for a in r[1:] if a]

    if exe is None:
        raise RuntimeError, 'Problem with running external process: %s' % p.stderr.read()
    return exe, path, ldpath

def find_module_path(path, module):
    modulefile = module +".py"
    for p in path:
        p = _path.abspath(p)
        if _path.exists(_path.join(p, module)):
            return p
        if _path.exists(_path.join(p, modulefile)):
            return p
    return None

def create_function(function, module=None, exe=None, path=None, extra_path=None, dls_module=None):
    '''Create a function that will run in an external python

    function -- function or its name, if the former then module is not needed
    module -- name of module
    exe -- path of Python executable
    path -- list of Python paths
    extra_path -- list of extra Python (prepended) paths for local packages
    dls_module -- if True, use 'numpy', else if string use as module name to obtain python parameters

    returns a function object

    For example, you have a module called blah with a function foo then
    >>> ext_foo = create_function("foo", "blah", dls_module=True)
    >>> ext_foo(1.2, 3.4, k=True)
    
    If blah is in your current python path then,
    >>> from blah import foo
    >>> ext_foo = create_function(foo, dls_module=True)
    
    The dls_module argument allows a python configuration from DLS's module system
    to be used to set the external python process. 
    '''

    if not isinstance(function, str):
        fn = function
        function = fn.__name__
        if fn.__module__ == '__main__':
            raise RuntimeError, 'Cannot create function as it needs to be in a module of its own'
        if module is None:
            module = fn.__module__

    ldpath = None
    if dls_module:
        if sys.platform == 'win32':
            raise ValueError, 'Cannot use dls_module argument on Windows'
        elif sys.platform == 'darwin':
            raise ValueError, 'Cannot use dls_module argument on Mac OS X'
        elif not sys.platform.startswith('linux'):
            print 'Warning dls_module argument may not work'
        if isinstance(dls_module, str):
            exe, path, ldpath = get_dls_module(dls_module)
        else:
            exe, path, ldpath = get_dls_module()
    exe, path = pyenv(exe, path)

    p = find_module_path(path, module)
    if p is None:
        p = find_module_path(sys.path, module)
    if p and p not in path:
        path.insert(0, p)
    if extra_path:
        if p is None:
            p = find_module_path(extra_path, module)
        path = extra_path + path
    if p is None:
        raise ValueError, 'Cannot find module in path: try specifying it in extra_path'
    env = dict(_env)
    env['PYTHONPATH'] = ':'.join(path)
    if ldpath:
        env['LD_LIBRARY_PATH'] = ':'.join(ldpath)

    def func(*arg, **kwarg):
        import shutil
        argsdir = save_args((arg, kwarg))
        try:
#        modules = [(k, v.__name__) for k,v in globals().items() if isinstance(v, type(sys)) and not k.startswith('__')]
#        print func.__name__
    #    pprint(modules)
            import subprocess as sub
            p = sub.Popen([exe,], env=env, shell=False, stdin=sub.PIPE, stdout=sub.PIPE, stderr=sub.PIPE)
            p.stdin.write('from scisoftpy import external as _fwext\n')
            p.stdin.write('from %s import %s\n' % (module, function))
            p.stdin.write('_fwwrapped = _fwext.wrapper(%s)\n' % function)
            p.stdin.write('_fwiarg, _fwikwarg = _fwext.load_args(\"%s\")\n' % argsdir)
            p.stdin.write('print "FWOUT:|%s|" % _fwext.save_args(_fwwrapped(*_fwiarg, **_fwikwarg))\n')
            p.stdin.close()
            while True:
                l =  p.stdout.readline()
                if not l:
                    break
                l = l.strip()
                if l.startswith('FWOUT'):
                    r = l.split('|')
                    if len(r) > 1:
                        d = r[1]
                        try:
                            ret, err = load_args(d)
                            if err:
                                raise err
                            return ret
                        finally:
                            shutil.rmtree(d)
            raise RuntimeError, 'Problem with running external process: %s' % p.stderr.read()
        finally:
            shutil.rmtree(argsdir)

    return func
