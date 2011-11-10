File Input/Output
=================
The primary package is ``scisoftpy.io``.

Images and recorded data from various detectors can be read into the data
analysis plugin. This is done using the following method::

    import scisoftpy.io as dio
    dl = dio.load("datafile")

This loads up the file as a list/ordered dictionary of datasets. Optionally
arguments can be specified::

    dl = dio.load(name, formats=None, withmetadata=True, ascolour=False)

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

   * ``binary`` - raw single dataset format

   or ``None`` (default) to attempt all above formats

 *withmetadata* is a boolean value to dictate whether to return metadata in
 addition to datasets

 *ascolour* is a boolean value to dictate whether to return any coloured image
 data as an RGB dataset or convert it to greyscale

The metadata is present as a dictionary with keys of strings. Currently, the
metadata items follow a Nexus naming convention.

The four standard image loaders will load and convert RGB images to grey-scale
(luma).

Datasets can be saved::

    dio.save(name, data, format=None, range=(), autoscale=False)

where:

 *name* is a file name

 *data* is a dataset or sequence of datasets

 *format* is one of following strings

   * ``png``,``gif``, ``jpeg``, ``tiff`` - standard image formats

   * ``text`` - raw ASCII output

   * ``binary``  - raw binary dump

   or ``None`` (default) to guess format from file name extension

 *range* is a tuple for minimum and maximum values for clipping a dataset
  before saving

 *autoscale* is a boolean value to dictate whether to scale automatically
  dataset values to fit the chosen format (only ``png`` and ``jpeg`` are
  supported for auto-scaling).

If there are multiple datasets specified then multiple images will be saved,
suffixed with a number representing the number of the dataset in the sequence.

In some of the formats supported (CBF, MarCCD, ADSC), information is supplied
on the position and orientation of the detector that took the image, the size
of pixels, the position and wavelength of an illuminating beam in a diffraction
experiment. This diffraction data can be loaded in exactly the same way::

    di = dio.load(name)

which returns a list (or dictionary) of diffraction images. These images can
be plotted using the plot package.

A NeXus [#Nexus]_ file can be loaded with::

    nt = dio.loadnexus(name)

which creates a NeXus tree.

References
----------
.. [#Nexus] NeXus: http://www.nexusformat.org
