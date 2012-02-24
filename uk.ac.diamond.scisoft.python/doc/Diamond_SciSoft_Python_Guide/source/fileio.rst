File Input/Output
=================
The primary package is ``scisoftpy.io``.


Loading
-------

Images and recorded data from various detectors can be read into the data
analysis plugin. This is done using the following method::

    import scisoftpy as dnp
    dh = dnp.io.load("datafile")

This loads up the file as a list/ordered dictionary of datasets. Optionally
arguments can be specified::

    dh = dnp.io.load(name, format=None, withmetadata=True, ascolour=False, warn=True)

where:

 *name* is a file name

 *format* is a list of strings that specify the formats to try

   * ``png``, ``gif``, ``jpeg``, ``tiff`` - standard image formats

   * ``adsc`` - ADSC Quantum area series detector format

   * ``crysalis`` - Oxford Diffraction CrysAlis processing software format

   * ``mar`` - Rayonix's MarCCD detector

   * ``pilatus`` - Dectris Pilatus detector version of TIFF 

   * ``cbf`` - Crystallographic Binary Format (IUCR)

   * ``xmap`` - XIA's DXP-xMAP format for x-ray spectra

   * ``srs`` - Daresbury Laboratory's Synchrotron Radiation Source format

   * ``edf`` - European Synchrotron Radiation Source data format for Pilatus images

   * ``binary`` - raw single dataset binary format

   * ``text`` - raw single dataset ASCII format

   * ``npy`` - NumPy binary format

   * ``hdf5`` - HDF5 [#HDF5]_ tree format

   * ``nx`` - NeXus [#Nexus]_ tree format

   or ``None`` (default) to attempt all above formats

 *withmetadata* is a boolean value to dictate whether to return metadata in
 addition to datasets

 *ascolour* is a boolean value to dictate whether to return any coloured image
 data as an RGB dataset or convert it to greyscale

 *warn* is a boolean value to dictate whether to print some warnings

The object returned by load is usually a data holder and acts like a list and
ordered dictionary. It has at least one attribute ``metadata`` which is
(possibly empty) list/dictionary. Values held in a data holder can be accessed
by strings or integers. They can also be accessed as attributes. For example, a
data file has a single dataset named 'voltage', then that dataset can be
accessed as follows::

    dh = dnp.io.load('measure.dat')
    len(dh)        # returns 1
    dh.keys()      # shows list of with string 'voltage'
    dh[0]          # shows array of voltage values
    dh['voltage']  # same as above
    dh.voltage     # same as above

If the data file has a metadata item that is the temperature, this can be
accessed in a similar way::

    dh.metadata.keys()          # shows string 'temperature' in a list
    dh.metadata[0]              # shows its value
    dh.metadata['temperature']  # shows its value
    dh.metadata.temperature     # shows its value

Note, the key names in a list/dictionary are transformed to valid python
variable names by substitution of underscore characters for illegal characters
and prepending an underscore if the name starts with a digit.

Currently, some loaders transform metadata items so that their names follow a
NeXus [#Nexus]_ naming convention.

The four standard image loaders will load and convert RGB images to grey-scale
(luma).

In some of the formats supported (CBF, MarCCD, ADSC), information is supplied
on the position and orientation of the detector that took the image, the size
of pixels, the position and wavelength of an illuminating beam in a diffraction
experiment. This diffraction data can be loaded in exactly the same way::

    di = dnp.io.load(name)

which returns a list (or dictionary) of diffraction images. These images can
be plotted using the plot package.

Loaders for the two tree formats (``hdf5`` and ``nx``) return data structures
that look like trees with a root node, branches to other nodes. Nodes can have
branches, i.e. it is a group, and also contain node attributes and
datasets. Node attributes within a node are collated in a list/dictionary under
the attribute ``attrs``. Group nodes and datasets are held in a list/dictionary
on the node itself. Datasets in trees can also hold attributes. It is important
to note that these datasets are lazy, see :ref:`lazy-dataset`.

Here is an example of loading a tree format file::

    t = dnp.io.load('bl20-20237.nxs') # load tree

    t.keys()       # returns the names of the nodes in the root group node 
    t.attrs.keys() # lists the names of the attributes

    t['entry1'] # retrieves the sub-node called `entry1`
    t.entry1    # same as above
    t[0]        # same as above if `entry1` is the first item in the root group

    g = t['entry1/data']    # retrieves the sub-sub-node (a group node)
    la = g.data             # retrieves the lazy dataset with that sub-sub-sub-node
    lb = t.entry1.data.data # same as above

    b = lb[...] # loads entire dataset

The tree object or root group also provides a method to find all nodes of a
given name::

    getnodes(name, group=True, data=True)

which returns a list of nodes. By default, both groups and datasets are
included in the list but this behaviour can be modified by setting the two
keyword arguments: `group` and `data`. Thus::

    t.getnodes('data', group=False) # returns datasets named "data"



Saving
------

Datasets can be saved::

    dnp.io.save(name, data, format=None, range=(), autoscale=False, signed=True, bits=None)

where:

 *name* is a file name

 *data* is a dataset or sequence of datasets

 *format* is one of following strings

   * ``png``, ``gif``, ``jpeg``, ``tiff`` - standard image formats

   * ``text`` - raw ASCII dump

   * ``binary``  - raw binary dump

   or ``None`` (default) to guess format from file name extension

 *range* is a tuple for minimum and maximum values for clipping a dataset
  before saving

 *autoscale* is a boolean value to dictate whether to scale automatically
  dataset values to fit the chosen format (only ``png`` and ``jpeg`` are
  supported for auto-scaling).

 *signed* is a boolean value to dictate whether to save as signed numbers

 *bits* is an integer value to dictate the number of bits to use when saving

If there are multiple datasets specified then multiple images will be saved,
suffixed with a number representing the number of the dataset in the sequence.
The ``bits`` keyword is supported by ``png`` and ``tiff`` savers. The latter
saver supports the ``unsigned`` keyword too. If the number of bits used is
greater than 32, the saver attempts to use 32-bit floats.

References
----------
.. [#HDF5] HDF5: http://www.hdfgroup.org/HDF5
.. [#Nexus] NeXus: http://www.nexusformat.org
