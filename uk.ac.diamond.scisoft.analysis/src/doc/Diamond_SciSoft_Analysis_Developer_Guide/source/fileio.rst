Input/Output
============
The primary class is ScanFileHolder. This class is used to hold data from a GDA
scan. It has been extend to allow other external files to be loaded and saved.

Images from various detectors can be read into the data analysis plugin. This is
done using the following method::

    import uk.ac.diamond.scisoft.analysis.io.DataHolder;
	DataHolder dh = new ADSCImageLoader("dataFile").loadFile();

This populates the holder with image data and a dataset can be obtained using
the getDataset() method.

The 'ADSCImageLoader' object can be interchanged with any of the following:
 * CrystalisLoader()
 * MACLoader()
 * MARloader()
 * PilatusTiffLoader()
 * CBFLoader()
 * JPEGLoader()
 * PNGloader()
 * TIFFImageLoader()

These latter three image loaders can also load and convert RGB images to
grey-scale (luma) images if you supply an extra argument to the loader's
constructor as a true value::

    dh = new PNGLoader("colourimage.png", True).loadFile();

There is a facility to save the output from a DataHolder as either a .png
of .jpeg file. The following example assumes that there is a DataHolder called
{{{dh}}} which contains a dataset that is to be output as an image::

     new JPEGSaver("/your/directory/image.jpeg").saveFile(dh);
     new PNGSaver("/your/directory/image.png").saveFile(dh);

This will save a .jpeg image and a .png image containing the data held within the
DataHolder. If there are multiple datasets held within the DataHolder 
then multiple images will be saved, suffixed with a number representing the number
of the dataset within the DataHolder. There is also an issue where the image
types can only save an image to a given bit depth. To save in image that has a
greater bit depth then is it recommended that the scaled savers are used::

     new JPEGScaledSaver("/your/directory/image.jpeg").saveFile(dh);
     new PNGScaledSaver("/your/directory/image.png").saveFile(dh);

These methods will scale the data held within the DataHolder such that the
pixel values will be scaled within 0 and the maximum bit depth of the image format.

There is also raw binary loader and saver classes which using a Diamond specific
binary format to store and load a dataset.


