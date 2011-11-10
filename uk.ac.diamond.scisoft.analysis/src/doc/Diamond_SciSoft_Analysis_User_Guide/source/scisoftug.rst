
======================================================
User Guide to the Diamond Scisoft Data Analysis Plugin
======================================================

---------------------------------------------------
How to do stuff with uk.ac.diamond.scisoft.analysis
---------------------------------------------------


Introduction
============
Diamond Light Source Ltd (DLS) [#DLS]_ is a not-for-profit company jointly
owned by the UK government's STFC and Wellcome Trust. Its purpose is to handle all
aspects of planning, building and running of a major scientific research
facility: a synchrotron light source.

The u.a.d.s.a plugin holds the DLS Scientific Software team's data
analysis and visualization package. It is implemented in Java using the
Eclipse framework, Jython, jReality and other libraries. The analysis package 
originally was part of the Generic Data Acquisition (GDA) suite [#GDA]_. With
their move to a plugin architecture, the code was split into relatively
independent parts.

The basic data analysis was conceived to be driven by Jython, the Java
implementation of the Python programming language. The package has been
designed as a client/server model with multiple graphical clients which can
replicate each other's actions.

The command server can be controlled by Jython commands sent by clients. It,
in turn, can direct certain actions of the clients.

Jython
======
The Jython aspect of the plugin comprises a Jython console and a command
line with an editor (taken from PyDev).

Everything from Jython is available on the console. In addition, there
are a number of Java classes available to aid data analysis. The
following sections details those Java classes as wrapped for Jython.

See the Jython documentation at its website [#Jython]_.





References
==========
.. [#DLS] DLS: http://www.diamond.ac.uk
.. [#GDA] GDA: http://www.gda.ac.uk
.. [#Jython] Jython: http://www.jython.org
