NeXus/HDF5 Tree View
====================

Introduction
------------
HDF is the Hierarchical Data Format developed and supported by the HDF Group
[#HDF]_. HDF5 [#HDF5]_ is their most recent data format. The NeXus [#Nexus]_
standard is a common data storage format for neutron, x-ray and muon
science. NeXus uses HDF5 as its default file container.

The tree view allows the data held in a Nexus/HDF5 file to be explored with a
graphical user interface and visualized with a simple set of plotting tools
in a plot view. A selected data item can be shown in a plot that has fewer
dimensions than the item by choosing which data dimensions to use for plot
axes.

Interaction
-----------
NeXus files can be loaded into the viewer by using the Jython console or by
clicking on the toolbar button at the top right of the viewer.

To load and view a NeXus file::

    import scisoftpy as dnp
    nt = dnp.io.load("/path/to/file.nxs")

    dnp.plot.viewnexus(nt)

where the tree is sent to a Nexus viewer called "nexusTreeViewer". To use
a viewer with another name, use the optional keyword argument ``name``.

The table-tree representing the Nexus structure can be expanded node by
node using a left mouse click on the node. The columns of the table-tree
display the node name, class, value type, dimensions, value. Right clicking
on the table header will bring up a context menu that allows columns to be
hidden or made visible.

To select an item to plot, double click on a node that belongs to the NXdata.
This will plot the data item if it has a signal attribute. Otherwise, double
click on any item of class SDS (scientific data set) to plot that item alone.

.. figure:: images/ntvstack01.png

   NeXus tree viewer

The selected data item will have its name shown in the part of the panel below
the table-tree at the left hand side. This is the axes selection panel and
allows a choice of any (compatible) axis data item or an automatically
configured axis to be selected for each dimension in the data.

Once the axes are chosen, the user then moves on to the right hand panel to
configure a plot. There are a set of four tabs: one for each type of plot
available. Within a tab, the plot axes can be selected from the drop-down
combination boxes. These boxes allow different dimensions of the data to used
as plot axes.

Below the drop-down boxes in a plot tab, a set of sliders allows any remaining
dimensions of the data (not chosen to act a plot axes) to have their index
chosen. These sliders allow the user to visualize slices through their data.

References
----------
.. [#HDF] HDF: http://www.hdfgroup.org
.. [#HDF5] HDF5: http://www.hdfgroup.org/HDF5
.. [#Nexus] NeXus: http://www.nexusformat.org

