External functions
==================
The ``external`` module contains a function that allows another function to be
executed in an external Python process::

    create_function(function, module=None, exe=None, path=None, extra_path=None, dls_module=None, keep=True)

creates a function that will run in an external python. The parameters are
``function``, ``module``, ``exe``, ``path``, ``extra_path``, ``dls_module``, ``keep``.
The mandatory argument ``function`` is a function or the name of a function; if
it is the name then ``module`` should be specified. The other optional
arguments will customise the Python process that is executed. ``dls_module``
can be set to ``True`` if NumPy package in Diamond's module system is required;
any other module name can be used to determine the Python setup as required.
``extra_path`` can be used to specify other file locations to search for the
given ``module`` argument. ``keep`` defaults to ``True`` determines whether to
keep the Python process alive between calls; this avoids the time penalty of
starting up a new process on each call. 

For example, you have a module called blah with a function foo then::
 
    ext_foo = create_function("foo", "blah", dls_module=True)
    ext_foo(1.2, 3.4, k=True)
    
If blah is in your current python path then::

    from blah import foo
    ext_foo = create_function(foo, dls_module=True)

The external function called can be passed any native Python objects and also
NumPy's and ScisoftPy's ``ndarray``\ s. The result returned is also converted
back. Exceptions raised in the external function are also handled and re-raised
in the calling process.
