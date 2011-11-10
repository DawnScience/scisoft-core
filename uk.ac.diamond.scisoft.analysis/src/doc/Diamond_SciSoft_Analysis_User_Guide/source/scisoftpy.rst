Scisoftpy
=========
This section documents version 1.0 of the Scisoft analysis plugin or version
8.8 of GDA.

The whole Jython package will emulate behaviour of NumPy [#Numpy]_ together
with some I/O and plotting routines. At its core, it contains an ndarray class
and a set of subclasses that wrap around the Scisoft generic dataset classes.

The set of utility modules: ``random``, ``fft``, ``fit``, ``signal``, ``image``,
``plot``, ``roi`` and ``io`` provide random number generators, function fitting,
fast Fourier transforms, signal processing, image processing, plotting, regions
of interest, and file loading and saving.

The basic way to start at the Jython console is to enter::

    import scisoftpy as dnp

This imports basic analysis tools into the Jython namespace under ``dnp``. There is
some help available on the console with the various packages::

    import scisoftpy as dnp
    help(dnp)
    help(dnp.random)

    import scisoftpy.random as drd
    help(drd)


References
----------
.. [#Numpy] NumPy: http://www.numpy.org
