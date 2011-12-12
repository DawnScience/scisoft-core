===============================================
User Guide to the Diamond Scisoft Python Plugin
===============================================

-------------------------------------------------
How to do stuff with uk.ac.diamond.scisoft.python
-------------------------------------------------


Introduction
============
Diamond Light Source Ltd (DLS) [#DLS]_ is a not-for-profit company jointly
owned by the UK government's STFC and Wellcome Trust. Its purpose is to handle
all aspects of planning, building and running of a major scientific research
facility: a synchrotron light source.

The ScisoftPy package holds the DLS Scientific Software team's data analysis
and visualization Python/Jython package. Python [#Python]_ is a programming
language that is commonly used for scripting to combine and integrate
components from libraries written in other programming languages. The main
implementation of Python is in C and Jython [#Jython]_ is a Java-based
implementation. This Jython package is used in the Generic Data Acquisition
(GDA) suite [#GDA]_ throughout Diamond.

ScisoftPy
=========
This section documents version 1.0 of the Scisoft Python package.

This package emulates behaviour of NumPy [#Numpy]_ together with some I/O,
plotting and fitting routines. At its core, it contains an N-dimensional array
class and a set of subclasses. It is installed with GDA or SDA, or can be
installed to run in Python with the requirement of an installation of
SciPy [#Scipy]_ with matplotlib or visvis. In GDA/SDA, the package runs in
Jython and wraps many Java classes. With SciPy, it runs in CPython.
Hereinafter, references to Python also can apply to Jython. Graphical output
and interactive capability will vary depending on whether the installation
environment. 

The set of utility modules: ``random``, ``fft``, ``fit``, ``signal``,
``image``, ``plot``, ``roi`` and ``io`` provide random number generators,
function fitting, fast Fourier transforms, signal processing, image processing,
plotting, regions of interest, and file loading and saving.

The basic way to start at a Jython or Python console is to enter::

    import scisoftpy as dnp

This imports basic analysis tools into the Python namespace under ``dnp``.
There is some help available on the console with the various packages::

    import scisoftpy as dnp
    help(dnp)
    help(dnp.random)

    import scisoftpy.random as drd
    help(drd)


References
==========
.. [#DLS] DLS: http://www.diamond.ac.uk
.. [#GDA] GDA: http://www.gda.ac.uk
.. [#Python] Python: http://www.python.org
.. [#Jython] Jython: http://www.jython.org
.. [#Scipy] SciPy: http://www.scipy.org
.. [#Numpy] NumPy: http://www.numpy.org

