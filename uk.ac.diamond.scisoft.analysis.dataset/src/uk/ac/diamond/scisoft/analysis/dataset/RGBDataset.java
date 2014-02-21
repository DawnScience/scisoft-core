/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to hold colour datasets as red, green, blue tuples of short integers
 */
public class RGBDataset extends CompoundShortDataset {
	// pin UID to base class
	private static final long serialVersionUID = AbstractDataset.serialVersionUID;

	/**
	 * Setup the logging facilities
	 */
	transient private static final Logger logger = LoggerFactory.getLogger(RGBDataset.class);

	private static final int ISIZE = 3; // number of elements per item

	@Override
	public int getDtype() {
		return RGB;
	}

	public RGBDataset() {
		super(ISIZE);
	}

	public RGBDataset(final int... shape) {
		super(ISIZE, shape);
	}

	/**
	 * Copy a dataset
	 * @param dataset
	 */
	public RGBDataset(final RGBDataset dataset) {
		super(dataset);
	}

	@Override
	public RGBDataset clone() {
		return new RGBDataset(this);
	}

	/**
	 * Create a dataset using given data (red, green and blue parts are given separately)
	 * @param redData
	 * @param greenData
	 * @param blueData
	 * @param shape (can be null to create 1D dataset)
	 */
	public RGBDataset(final int[] redData, final int[] greenData, final int[] blueData, int... shape) {
		int dsize = redData.length > greenData.length ? greenData.length : redData.length;
		dsize = dsize > blueData.length ? blueData.length : dsize;
		if (shape == null || shape.length == 0) {
			shape = new int[] {dsize};
		}
		isize = ISIZE;
		size = calcSize(shape);
		if (size != dsize) {
			logger.error("Shape is not compatible with size of data array");
			throw new IllegalArgumentException("Shape is not compatible with size of data array");
		}
		this.shape = shape.clone();

		odata = data = createArray(size);

		for (int i = 0, n = 0; i < size; i++) {
			data[n++] = (short) redData[i];
			data[n++] = (short) greenData[i];
			data[n++] = (short) blueData[i];
		}
	}

	/**
	 * Create a dataset using given data (red, green and blue parts are given separately)
	 * @param redData
	 * @param greenData
	 * @param blueData
	 * @param shape (can be null to create 1D dataset)
	 */
	public RGBDataset(final short[] redData, final short[] greenData, final short[] blueData, int... shape) {
		int dsize = redData.length > greenData.length ? greenData.length : redData.length;
		dsize = dsize > blueData.length ? blueData.length : dsize;
		if (shape == null || shape.length == 0) {
			shape = new int[] {dsize};
		}
		isize = ISIZE;
		size = calcSize(shape);
		if (size != dsize) {
			logger.error("Shape is not compatible with size of data array");
			throw new IllegalArgumentException("Shape is not compatible with size of data array");
		}
		this.shape = shape.clone();

		odata = data = createArray(size);

		for (int i = 0, n = 0; i < size; i++) {
			data[n++] = redData[i];
			data[n++] = greenData[i];
			data[n++] = blueData[i];
		}
	}

	/**
	 * Create a dataset using given data (red, green and blue parts are given separately)
	 * @param redData
	 * @param greenData
	 * @param blueData
	 * @param shape (can be null to create 1D dataset)
	 */
	public RGBDataset(final byte[] redData, final byte[] greenData, final byte[] blueData, int... shape) {
		int dsize = redData.length > greenData.length ? greenData.length : redData.length;
		dsize = dsize > blueData.length ? blueData.length : dsize;
		if (shape == null || shape.length == 0) {
			shape = new int[] {dsize};
		}
		isize = ISIZE;
		size = calcSize(shape);
		if (size != dsize) {
			logger.error("Shape is not compatible with size of data array");
			throw new IllegalArgumentException("Shape is not compatible with size of data array");
		}
		this.shape = shape.clone();

		odata = data = createArray(size);

		for (int i = 0, n = 0; i < size; i++) {
			data[n++] = (short) (0xff & redData[i]);
			data[n++] = (short) (0xff & greenData[i]);
			data[n++] = (short) (0xff & blueData[i]);
		}
	}

	/**
	 * Create a dataset using given colour data (colour components are given separately)
	 * @param red
	 * @param green
	 * @param blue
	 */
	public RGBDataset(final ADataset red, final ADataset green, final ADataset blue) {
		super(ISIZE, red.getShapeRef());
		red.checkCompatibility(green);
		red.checkCompatibility(blue);

		if (red.max().doubleValue() > Short.MAX_VALUE || red.min().doubleValue() < Short.MIN_VALUE ||
				green.max().doubleValue() > Short.MAX_VALUE || green.min().doubleValue() < Short.MIN_VALUE || 
				blue.max().doubleValue() > Short.MAX_VALUE || blue.min().doubleValue() < Short.MIN_VALUE) {
			logger.warn("Some values are out of range and will be ");
		}

		IndexIterator riter = red.getIterator();
		IndexIterator giter = green.getIterator();
		IndexIterator biter = blue.getIterator();

		for (int i = 0; riter.hasNext() && giter.hasNext() && biter.hasNext();) {
			data[i++] = (short) red.getElementLongAbs(riter.index);
			data[i++] = (short) green.getElementLongAbs(riter.index);
			data[i++] = (short) blue.getElementLongAbs(riter.index);
		}
	}

	/**
	 * Create a dataset using given grey data
	 * @param grey
	 */
	public RGBDataset(final ADataset grey) {
		super(ISIZE, grey.getShapeRef());

		IndexIterator giter = grey.getIterator();

		for (int i = 0; giter.hasNext();) {
			final short g = (short) grey.getElementLongAbs(giter.index); 
			data[i++] = g;
			data[i++] = g;
			data[i++] = g;
		}
	}

	/**
	 * Create a RGB dataset from a compound dataset (no normalisation performed)
	 * @param a
	 * @return RGB dataset
	 */
	public static RGBDataset createFromCompoundDataset(final AbstractCompoundDataset a) {
		if (a instanceof RGBDataset)
			return (RGBDataset) a;
		final int is = a.isize;
		if (is < 3) {
			return new RGBDataset(a);
		}
		final RGBDataset rgb = new RGBDataset(a.shape);
		final IndexIterator it = a.getIterator();

		int n = 0;
		while (it.hasNext()) {
			rgb.data[n++] = (short) a.getElementLongAbs(it.index);
			rgb.data[n++] = (short) a.getElementLongAbs(it.index + 1);
			rgb.data[n++] = (short) a.getElementLongAbs(it.index + 2);
		}

		return rgb;
	}

	@Override
	public RGBDataset getSlice(final int[] start, final int[] stop, final int[] step) {
		IndexIterator siter = getSliceIterator(start, stop, step);

		RGBDataset result = new RGBDataset(siter.getShape());
		short[] rdata = result.data; // PRIM_TYPE
		IndexIterator riter = result.getIterator();

		while (siter.hasNext() && riter.hasNext()) {
			for (int i = 0; i < isize; i++)
				rdata[riter.index + i] = data[siter.index + i];
		}

		return result;
	}

	@Override
	public RGBDataset getView() {
		RGBDataset view = new RGBDataset();
		view.name = new String(name);
		view.size = size;
		view.shape = shape.clone();
		view.odata = view.data = data;
		view.metadataStructure = metadataStructure;

		return view;
	}

	/**
	 * @param i
	 * @return red value in given position
	 */
	public short getRed(final int i) {
		return data[get1DIndex(i)];
	}

	/**
	 * @param i
	 * @param j
	 * @return red value in given position
	 */
	public short getRed(final int i, final int j) {
		return data[get1DIndex(i, j)];
	}

	/**
	 * @param pos
	 * @return red value in given position
	 */
	public short getRed(final int... pos) {
		return data[get1DIndex(pos)];
	}

	/**
	 * @param i
	 * @return green value in given position
	 */
	public short getGreen(final int i) {
		return data[get1DIndex(i) + 1];
	}

	/**
	 * @param i
	 * @param j
	 * @return green value in given position
	 */
	public short getGreen(final int i, final int j) {
		return data[get1DIndex(i, j) + 1];
	}

	/**
	 * @param pos
	 * @return green value in given position
	 */
	public short getGreen(final int... pos) {
		return data[get1DIndex(pos) + 1];
	}

	/**
	 * @param i
	 * @return blue value in given position
	 */
	public short getBlue(final int i) {
		return data[get1DIndex(i) + 2];
	}

	/**
	 * @param i
	 * @param j
	 * @return blue value in given position
	 */
	public short getBlue(final int i, final int j) {
		return data[get1DIndex(i, j) + 2];
	}

	/**
	 * @param pos
	 * @return blue value in given position
	 */
	public short getBlue(final int... pos) {
		return data[get1DIndex(pos) + 2];
	}

	/**
	 * Get a red value from given absolute index as a short - note this index does not
	 * take in account the item size so be careful when using with multi-element items
	 * 
	 * @param n
	 * @return red value
	 */
	public short getRedAbs(int n) {
		return data[n*isize];
	}

	/**
	 * Get a green value from given absolute index as a short - note this index does not
	 * take in account the item size so be careful when using with multi-element items
	 * 
	 * @param n
	 * @return green value
	 */
	public short getGreenAbs(int n) {
		return data[n*isize + 1];
	}

	/**
	 * Get a blue value from given absolute index as a short - note this index does not
	 * take in account the item size so be careful when using with multi-element items
	 * 
	 * @param n
	 * @return blue value
	 */
	public short getBlueAbs(int n) {
		return data[n*isize + 2];
	}


	// weights from NTSC formula aka ITU-R BT.601 for mapping RGB to luma
	private static final double Wr = 0.299, Wg = 0.587, Wb = 0.114;

	/**
	 * Convert colour dataset to a grey-scale one using the NTSC formula, aka ITU-R BT.601, for RGB to luma mapping
	 * @param dtype
	 * @return a grey-scale dataset of given type
	 */
	public AbstractDataset createGreyDataset(final int dtype) {
		return createGreyDataset(Wr, Wg, Wb, dtype);
	}

	/**
	 * Convert colour dataset to a grey-scale one using given RGB to luma mapping
	 * @param red weight
	 * @param green weight
	 * @param blue weight
	 * @param dtype
	 * @return a grey-scale dataset of given type
	 */
	public AbstractDataset createGreyDataset(final double red, final double green, final double blue, final int dtype) {
		final AbstractDataset grey = AbstractDataset.zeros(shape, dtype);
		final IndexIterator it = getIterator();

		int i = 0;
		while (it.hasNext()) {
			grey.setObjectAbs(i++, red*data[it.index] + green*data[it.index + 1] + blue*data[it.index + 2]);
		}
		return grey;
	}

	/**
	 * Extract red colour channel
	 * @param dtype
	 * @return a dataset of given type
	 */
	public AbstractDataset createRedDataset(final int dtype) {
		return createColourChannelDataset(0, dtype, "red");
	}

	/**
	 * Extract green colour channel
	 * @param dtype
	 * @return a dataset of given type
	 */
	public AbstractDataset createGreenDataset(final int dtype) {
		return createColourChannelDataset(1, dtype, "green");
	}

	/**
	 * Extract blue colour channel
	 * @param dtype
	 * @return a dataset of given type
	 */
	public AbstractDataset createBlueDataset(final int dtype) {
		return createColourChannelDataset(2, dtype, "blue");
	}

	private AbstractDataset createColourChannelDataset(final int channelOffset, final int dtype, final String cName) {
		final AbstractDataset channel = AbstractDataset.zeros(shape, dtype);

		final StringBuilder cname = name == null ? new StringBuilder() : new StringBuilder(name);
		if (cname.length() > 0) {
			cname.append('.');
		}
		cname.append(cName);
		channel.setName(cname.toString());

		final IndexIterator it = getIterator();

		int i = 0;
		while (it.hasNext()) {
			channel.setObjectAbs(i++, data[it.index + channelOffset]);
		}
		return channel;
	}

	@Override
	public Number max(boolean... ignored) {
		short max = Short.MIN_VALUE;
		final IndexIterator it = getIterator();

		while (it.hasNext()) {
			for (int i = 0; i < ISIZE; i++) {
				final short value = data[it.index + i];
				if (value > max)
					max = value;
			}
		}
		return max;
	}

	@Override
	public Number min(boolean... ignored) {
		short max = Short.MAX_VALUE;
		final IndexIterator it = getIterator();

		while (it.hasNext()) {
			for (int i = 0; i < ISIZE; i++) {
				final short value = data[it.index + i];
				if (value < max)
					max = value;
			}
		}
		return max;
	}
}
