Generic dataset
===============
**Warning**: This section is aimed at a Java developer.

A set of dataset classes are provided for the storage of *n*-dimensional arrays
of Java primitive types.

A Scisoft dataset is a homogeneous collection of items where each item is
accessed via a set of *n* (integer) indices - i.e. an *n*-D array. The location
of an item as specified by *n* indices is called the position (of that item) in
a dataset. An item can consist of number of elements - that number is fixed for
each dataset class. The elements are of a single Java primitive type. The shape
of a dataset is a set of positive integers which describe the upper bound of
the indices given by a position. So *0 <= p_i < s_i* for *i = 0 to n-1*. 

The class hierarchy begins with the ILazyDataset interface which is a minimal
interface to allow data access to be deferred. It is used by file loaders
particularly if the file format can handle very large datasets.

ILazyDataset
------------
The following methods are declared::

	public Class<?> elementClass();    // Java boxed primitive class

	public String getName();           // name of dataset

	public void setName(final String name); // set name

	public int getRank();              // rank of dataset

	public int getSize();              // total number of elements in dataset

	public int[] getShape();           // shape of dataset

	public void setShape(final int... shape); // set shape

	public ILazyDataset squeeze();     // remove dimensions of 1 in shape

	public ILazyDataset squeeze(boolean onlyFromEnd);
	                                   // remove dimensions of 1 in shape (from end only if true)

	public IDataset getSlice(final int[] start, final int[] stop, final int[] step);
	                                   // slice of dataset

	public IDataset getSlice(final IMonitor monitor, final int[] start, final int[] stop, final int[] step);
	                                   // slice of dataset

	public IDataset getSlice(final Slice... slice);
	                                   // slice of dataset as specified by an array of slices

	public IDataset getSlice(final IMonitor, final Slice... slice);
	                                   // slice of dataset as specified by an array of slices

	public void setMetadata(final IMetaData metadata); // set metadata

	public ILazyDataset clone();       // copy structure 

Next, the IDataset interface implements ILazyDataset and which allows
interoperability with dataset classes external to the Scisoft plugin. This
interface specifies the minimal set of methods that our plotting tools will
use. An abstract base class is defined which contains dataset-neutral methods,
common methods and static factory methods. A further abstract class extends the
base class to allow datasets of compound elements. 


IDataset
--------
The following methods are declared::

	public int getElementsperitem();   // number of elements in each item

	public int getItemsize();          // number of bytes in an item

	public Object getObject(final int... pos); // item as an object in position
	
	public String getString(final int... pos); // item as a string in position
	
	public double getDouble(final int... pos); // item as a double in position

	public float getFloat(final int... pos);   // item as a float in position

	public long getLong(final int... pos);     // item as a long in position

	public int getInt(final int... pos);       // item as an int in position

	public short getShort(final int... pos);   // item as a short in position

	public byte getByte(final int... pos);     // item as a byte in position

	public boolean getBoolean(final int... pos); // item as a boolean in position

	public void set(final Object obj, final int... pos); // set item in position

	public Number min();               // minimum item in dataset

	public Number max();               // maximum item in dataset

	public int[] minPos();             // position of first minima

	public int[] maxPos();             // position of first maxima

	public IDataset clone();           // copy structure without making new copy of data

Implementation
--------------
A dataset is implemented as a 1-D backing array of elements. The items are
stored so that elements are contiguous and the storage order is row-major.
Items in a newly-created dataset are contiguous in the backing array.

An interface is specified for various dataset iterators. These iterators
provide a means to iterate over the index of the backing data array and/or the
positions in row-major order.

A dataset can be expanded by setting a value at a position outside its shape.
Once expanded, the dataset can become discontiguous as extra space is reserved
to allow for faster future expansion. A dataset can become contiguous again
once it has expanded to fill its reserved space. Note that elements within the
new shape but outside the old shape are set to minimum values or not-a-numbers. 

