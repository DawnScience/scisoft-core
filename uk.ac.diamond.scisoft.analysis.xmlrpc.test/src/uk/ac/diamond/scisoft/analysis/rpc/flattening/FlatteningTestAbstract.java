/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.api.rpc.AnalysisRpcRemoteException;
import org.eclipse.dawnsci.analysis.dataset.roi.CircularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.CircularROIList;
import org.eclipse.dawnsci.analysis.dataset.roi.EllipticalROI;
import org.eclipse.dawnsci.analysis.dataset.roi.EllipticalROIList;
import org.eclipse.dawnsci.analysis.dataset.roi.GridPreferences;
import org.eclipse.dawnsci.analysis.dataset.roi.LinearROI;
import org.eclipse.dawnsci.analysis.dataset.roi.LinearROIList;
import org.eclipse.dawnsci.analysis.dataset.roi.PointROI;
import org.eclipse.dawnsci.analysis.dataset.roi.PointROIList;
import org.eclipse.dawnsci.analysis.dataset.roi.ROIBase;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROIList;
import org.eclipse.dawnsci.analysis.dataset.roi.SectorROI;
import org.eclipse.dawnsci.analysis.dataset.roi.SectorROIList;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.NumPyFileSaver;
import uk.ac.diamond.scisoft.analysis.plotserver.AxisMapBean;
import uk.ac.diamond.scisoft.analysis.plotserver.DataBean;
import uk.ac.diamond.scisoft.analysis.plotserver.DataBeanException;
import uk.ac.diamond.scisoft.analysis.plotserver.DatasetWithAxisInformation;
import uk.ac.diamond.scisoft.analysis.plotserver.FileOperationBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiBean;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiParameters;
import uk.ac.diamond.scisoft.analysis.plotserver.GuiPlotMode;
import uk.ac.diamond.scisoft.analysis.rpc.internal.AnalysisRpcDoubleParser;

abstract public class FlatteningTestAbstract {

	/**
	 * Waiting period for server to start up (in milliseconds)
	 */
	public static final long SERVER_WAIT_TIME = 3000;

	protected static IRootFlattener flattener;

	/**
	 * This toggles whether to use NaNs, infinities in tests.
	 * <p>
	 * Note NaN, +/-Inf only work if AnalysisRpc is in the loop or Java is talking to Java.
	 * <p>
	 * Note the subclasses that do not support it don't matter because they were written simply to identify these types
	 * of issues.
	 * See {@link AnalysisRpcDoubleParser}
	 */
	protected static boolean handleDoubleSpecials = true;

	protected RectangularROI createRectangularROI() {
		// now the rectangular ROI null constructor will have NaNs and infinities in some fields
		return handleDoubleSpecials ? new RectangularROI(Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN, 0) : new RectangularROI(10, 0);
	}

	@BeforeClass
	public static void setUp() {
		flattener = new RootFlattener();
	}

	/**
	 * Special version of Assert.assertEquals that, for some types, uses reflection or other knowledge instead of
	 * equals() to test for equality. The reason for this is to work around insufficient or unsuitable equals
	 * implementations in those classes. Arrays, Lists and Maps are compared deeply.
	 *
	 * @param expected
	 *            first object to test
	 * @param actual
	 *            second object to test
	 */
	protected void assertFlattenEquals(Object expected, Object actual) {
		if (expected == null) {
			Assert.assertNull(actual);
		} else if (expected instanceof IROI|| expected instanceof AxisMapBean) {
			Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
		} else if (expected instanceof DataBean) {
			DataBean expDataBean = (DataBean) expected;
			DataBean actDataBean = (DataBean) actual;
			assertFlattenEquals(expDataBean.getData(), actDataBean.getData());
			assertFlattenEquals(expDataBean.getAxisData(), actDataBean.getAxisData());
// TODO fix this with HDF5 tree
//			assertFlattenEquals(Collections.emptyList(), actDataBean.getNexusTrees());
		} else if (expected instanceof DatasetWithAxisInformation) {
			DatasetWithAxisInformation expDatasetWithAxisInformation = (DatasetWithAxisInformation) expected;
			DatasetWithAxisInformation actDatasetWithAxisInformation = (DatasetWithAxisInformation) actual;
			assertFlattenEquals(expDatasetWithAxisInformation.getData(), actDatasetWithAxisInformation.getData());
			assertFlattenEquals(expDatasetWithAxisInformation.getAxisMap(), actDatasetWithAxisInformation.getAxisMap());
		} else if (expected instanceof Exception) {
			Exception expException = (Exception) expected;
			Exception actException = (Exception) actual;
			// The message gets supplemented with extra type information, make sure the original expected
			// message is present in the supplemented info
			if (!actException.getMessage().contains(expException.getMessage()))
				Assert.fail("Expected message: " + expException.getMessage() + " actual message: " + actException.getMessage());
			StackTraceElement[] expStackTrace = expException.getStackTrace();
			StackTraceElement[] actStackTrace = actException.getStackTrace();
			Assert.assertArrayEquals(expStackTrace, actStackTrace);
		} else if (expected instanceof List) {
			List<?> expectedlist = (List<?>) expected;
			List<?> actuallist = (List<?>) actual;
			Assert.assertEquals(expectedlist.size(), actuallist.size());
			for (int i = 0; i < actuallist.size(); i++) {
				assertFlattenEquals(expectedlist.get(i), actuallist.get(i));
			}
		} else if (expected instanceof byte[]) {
			byte[] expectedarray = (byte[]) expected;
			byte[] actualarray = (byte[]) actual;
			Assert.assertArrayEquals(expectedarray, actualarray);
		} else if (expected instanceof int[]) {
			int[] expectedarray = (int[]) expected;
			int[] actualarray = (int[]) actual;
			Assert.assertArrayEquals(expectedarray, actualarray);
		} else if (expected instanceof double[]) {
			double[] expectedarray = (double[]) expected;
			double[] actualarray = (double[]) actual;
			Assert.assertArrayEquals(expectedarray, actualarray, 0.0);
		} else if (expected instanceof boolean[]) {
			boolean[] expectedarray = (boolean[]) expected;
			boolean[] actualarray = (boolean[]) actual;
			Assert.assertTrue(Arrays.equals(expectedarray, actualarray));
		} else if (expected instanceof Object[]) {
			Object[] expectedarray = (Object[]) expected;
			Object[] actualarray = (Object[]) actual;
			Assert.assertEquals(expectedarray.length, actualarray.length);
			for (int i = 0; i < actualarray.length; i++) {
				assertFlattenEquals(expectedarray[i], actualarray[i]);
			}
		} else if (expected instanceof GuiBean) {
			GuiBean expectedGuiBean = (GuiBean) expected;
			GuiBean actualGuiBean = (GuiBean) actual;
			// GuiBeans "lose" some keys when being flattened because they are not
			// supported.
			Assert.assertTrue(expectedGuiBean.size() >= actualGuiBean.size());
			Set<Entry<GuiParameters, Serializable>> entrySet = expectedGuiBean.entrySet();
			for (Entry<GuiParameters, Serializable> entry : entrySet) {
				if (entry.getKey().equals(GuiParameters.GRIDPREFERENCES)
						|| entry.getKey().equals(GuiParameters.FILEOPERATION)
						|| entry.getKey().getStorageClass().equals(Serializable.class)) {
					Assert.assertFalse(actualGuiBean.containsKey(entry.getKey()));
				} else {
					Assert.assertTrue(actualGuiBean.containsKey(entry.getKey()));
					assertFlattenEquals(entry.getValue(), actualGuiBean.get(entry.getKey()));
				}
			}
		} else if (expected instanceof Map) {
			Map<?, ?> expectedMap = (Map<?, ?>) expected;
			Map<?, ?> actualMap = (Map<?, ?>) actual;
			Assert.assertEquals(expectedMap.size(), actualMap.size());
			Set<?> entrySet = expectedMap.entrySet();
			for (Object object : entrySet) {
				Entry<?, ?> entry = (Entry<?, ?>) object;
				Assert.assertTrue(actualMap.containsKey(entry.getKey()));
				assertFlattenEquals(entry.getValue(), actualMap.get(entry.getKey()));
			}
		} else {
			Assert.assertEquals(expected, actual);
		}
	}

	protected Object flattenAndUnflatten(Object inObj) {
		return flattenAndUnflatten(inObj, inObj);
	}

	private Object flattenAndUnflatten(Object inObj, Object expectedObj) {
		return flattenAndUnflatten(inObj, expectedObj, expectedObj.getClass());
	}

	protected abstract Object doActualFlattenAndUnflatten(Object inObj);

	private Object flattenAndUnflatten(Object inObj, Object expectedObj, Class<?> expectedType) {
		final Object out = doActualFlattenAndUnflatten(inObj);

		assertFlattenEquals(expectedObj, out);
		if (expectedObj != null && expectedObj.getClass().equals(expectedType)) {
			Assert.assertEquals(expectedType, out.getClass());
		} else if (expectedType != null) {
			Assert.assertTrue(expectedType.isAssignableFrom(out.getClass()));
		}

		// finally, take a unflattened item and make sure it has made something
		// that is fully flattenable still
		assertFlattenEquals(out, doActualFlattenAndUnflatten(out));

		return out;
	}

	@Test
	public void testInteger() {
		flattenAndUnflatten(18);
		flattenAndUnflatten(-7);
		flattenAndUnflatten(0);
		flattenAndUnflatten(Integer.MIN_VALUE);
		flattenAndUnflatten(Integer.MAX_VALUE);
	}

	@Test
	public void testBoolean() {
		flattenAndUnflatten(true);
		flattenAndUnflatten(false);
	}

	@Test
	public void testString() {
		flattenAndUnflatten("");
		flattenAndUnflatten("bananas");
		flattenAndUnflatten("\nhello\tgoodbye");
	}

	@Test
	public void testUnicodeString() {
		flattenAndUnflatten("\u00b0");
	}

	@Test
	public void testDouble() {
		flattenAndUnflatten(0);
		flattenAndUnflatten(Math.PI);
		flattenAndUnflatten(Double.MIN_VALUE);
		flattenAndUnflatten(Double.MAX_VALUE);
	}

	@Test
	public void testDoubleSpecialValues() {
		if (handleDoubleSpecials) {
			flattenAndUnflatten(Double.NaN);
			flattenAndUnflatten(Double.NEGATIVE_INFINITY);
			flattenAndUnflatten(Double.POSITIVE_INFINITY);
		}
	}

	@Test
	public void testByeArray() {
		flattenAndUnflatten(new byte[] { 1, 5, -7 });
		flattenAndUnflatten(new byte[0]);
		flattenAndUnflatten(new byte[1000]);
	}

	@Test
	public void testMap() {
		HashMap<String, Double> hashMap = new HashMap<String, Double>();
		hashMap.put("pi", Math.PI);
		hashMap.put("One", Double.valueOf(1));
		flattenAndUnflatten(hashMap, hashMap, Map.class);

		TreeMap<String, Double> treeMap = new TreeMap<String, Double>(hashMap);
		flattenAndUnflatten(treeMap, treeMap, Map.class);

		HashMap<String, Object> hashMap2 = new HashMap<String, Object>(hashMap);
		hashMap2.put("Integer", Integer.valueOf(0));
		hashMap2.put("Integer", Integer.valueOf(100));
		flattenAndUnflatten(hashMap2, hashMap2, Map.class);

		HashMap<Object, Object> nonStringKeys = new HashMap<Object, Object>(hashMap2);
		nonStringKeys.put(Integer.valueOf(0), Integer.valueOf(0));
		nonStringKeys.put(GuiParameters.FILENAME, "Filename");
		flattenAndUnflatten(nonStringKeys, nonStringKeys, Map.class);
	}

	@Test
	public void testOrderedMap() {
		Map<String, Double> hashMap = new LinkedHashMap<>();
		hashMap.put("pi", Math.PI);
		hashMap.put("One", Double.valueOf(1));
		flattenAndUnflatten(hashMap, hashMap, Map.class);

		Map<String, Object> hashMap2 = new LinkedHashMap<>();
		hashMap2.put("Integer", Integer.valueOf(0));
		hashMap2.put("Integer", Integer.valueOf(100));
		flattenAndUnflatten(hashMap2, hashMap2, Map.class);

		Map<Object, Object> nonStringKeys = new LinkedHashMap<>();
		nonStringKeys.put(Integer.valueOf(0), Integer.valueOf(0));
		nonStringKeys.put(GuiParameters.FILENAME, "Filename");
		flattenAndUnflatten(nonStringKeys, nonStringKeys, Map.class);
	}

	@Test
	public void testObjectArrays() {
		flattenAndUnflatten(new Object[] { Double.valueOf(1.2), Integer.valueOf(2) });

		// arrays of things which look like arrays of integers, double or booleans come out as such
		flattenAndUnflatten(new Object[] { 0, 1, 2, 3 }, new int[] { 0, 1, 2, 3 });
		flattenAndUnflatten(new Object[] { new Object[] { 0, 1, 2, 3 }, new Object[] { 4, 5, 6, 7 },
				new Object[] { 8, 9, 10, 11 }, new Object[] { 12, 13, 14, 15 } }, new int[][] {
				new int[] { 0, 1, 2, 3 }, new int[] { 4, 5, 6, 7 }, new int[] { 8, 9, 10, 11 },
				new int[] { 12, 13, 14, 15 } });
		flattenAndUnflatten(new Object[][] { { 0, 1 }, { 2, 3 } },
				new int[][] { new int[] { 0, 1 }, new int[] { 2, 3 } });

		// Empty arrays come out as array of Object[]
		flattenAndUnflatten(new Object[0], new Object[0]);
		flattenAndUnflatten(new int[0], new Object[0]);
		flattenAndUnflatten(new String[0], new Object[0]);

		// arrays of other types come out as arrays if the class of each element of the array is the same
		flattenAndUnflatten(new String[] { "one", "two" }, new String[] { "one", "two" }); // String[] --> String[]
		flattenAndUnflatten(new Object[] { "one", "two" }, new String[] { "one", "two" }); // Object[] --> String[]
		flattenAndUnflatten(new Object[] { createRectangularROI(), createRectangularROI() }, new RectangularROI[] {
				createRectangularROI(), createRectangularROI() }); // Object[] --> RectangularROI[]
		flattenAndUnflatten(new Object[] { createRectangularROI(), new LinearROI() }, new IROI[] {
				createRectangularROI(), new LinearROI() }); // ROIBase[] --> ROIBase[]
		flattenAndUnflatten(new Object[] { Integer.valueOf(0), createRectangularROI(), new LinearROI() }, new Object[] {
				Integer.valueOf(0), createRectangularROI(), new LinearROI() }); // Object[] --> Object[]
		flattenAndUnflatten(new Object[] { createRectangularROI(), new LinearROI(), Integer.valueOf(0) }, new Object[] {
				createRectangularROI(), new LinearROI(), Integer.valueOf(0) }); // Object[] --> Object[]

		// arrays with some (but not all) nulls follow the rule above
		flattenAndUnflatten(new String[] { null, "two" }, new String[] { null, "two" }); // String[] --> String[]
		flattenAndUnflatten(new String[] { "one", null }, new String[] { "one", null }); // String[] --> String[]
		flattenAndUnflatten(new Object[] { null, "two" }, new String[] { null, "two" }); // Object[] --> String[]

		// arrays with all nulls in them come out as arrays of Object[]
		flattenAndUnflatten(new String[] { null, null }, new Object[] { null, null }); // String[] --> Object[]
		flattenAndUnflatten(new Object[] { null, null }, new Object[] { null, null }); // Object[] --> Object[]

		// arrays of some special types that every element implements the same interface or extends the same
		// class unflatten as an array of that super type. But when each element is the same implementation
		// still unflatten to that implementation
		// IDataset[]
		IntegerDataset intDataset = DatasetFactory.createRange(IntegerDataset.class, 10);
		DoubleDataset fltDataset = DatasetFactory.createRange(10);
		flattenAndUnflatten(new IDataset[] { intDataset, fltDataset });
		flattenAndUnflatten(new IntegerDataset[] { intDataset, intDataset });
		// IROI[]
		flattenAndUnflatten(new IROI[] { createRectangularROI(), new SectorROI() });
		flattenAndUnflatten(new RectangularROI[] { createRectangularROI(), createRectangularROI() });
	}

	@Test
	public void testLists() {
		ArrayList<RectangularROI> rects = new ArrayList<RectangularROI>();
		rects.add(new RectangularROI(15, 0.2));
		rects.add(new RectangularROI(10.1, 11.2, 0));
		rects.add(createRectangularROI());

		// Lists are to the same type as every element in the array if they are all the same class...
		flattenAndUnflatten(rects, rects.toArray(new RectangularROI[0]));

		ArrayList<Number> nums = new ArrayList<Number>();
		nums.add(Double.valueOf(0.0));
		nums.add(Integer.valueOf(1));

		// ...otherwise they unflatten to an array of Object[]
		flattenAndUnflatten(nums, nums.toArray(new Object[0]));
	}

	@Test
	public void testNull() {
		flattenAndUnflatten(null, null, null);

		// test null within other data structures
		Map<Object, Object> map = new HashMap<Object, Object>();
		// map, null key
		map.put(null, "null");
		flattenAndUnflatten(map, map, Map.class);
		// map, null value
		map.put("null", null);
		flattenAndUnflatten(map, map, Map.class);
		// map, null key and value
		map.put(null, null);
		flattenAndUnflatten(map, map, Map.class);

		String[] array = new String[] { "null", null };
		// array, null entry
		flattenAndUnflatten(array);
		// list, null entry
		flattenAndUnflatten(Arrays.asList(array), array);
	}

	@Test
	public void testTypedNull() {
		// A typed null should come out equal
		TypedNone nullDouble = new TypedNone(Double.class);
		flattenAndUnflatten(nullDouble, nullDouble);

		// Make sure a non-built-in type is OK
		TypedNone nullRect = new TypedNone(RectangularROI.class);
		flattenAndUnflatten(nullRect, nullRect);
	}

	@Test
	public void testGuiBean() {
		GuiBean bean = new GuiBean();
		bean.put(GuiParameters.FILENAME, "myfile.txt");
		bean.put(GuiParameters.TITLE, "My Amazing Plot!");
		bean.put(GuiParameters.PLOTID, UUID.fromString("93dfd804-85ba-4074-afce-d621f7f2aac6"));
		RectangularROI rect = new RectangularROI(1.1, -2, 5.0, 10.0, 0.6, true);
		rect.setName("Rect 1");
		bean.put(GuiParameters.ROIDATA, rect.getName());
		RectangularROIList list = new RectangularROIList();
		list.add(rect);
		bean.put(GuiParameters.ROIDATALIST, list);
		ArrayList<String> fileList = new ArrayList<String>();
		fileList.add("File1.plot");
		fileList.add("File2.plot");
		bean.put(GuiParameters.FILESELECTEDLIST, fileList);
		Integer[] gridSize = { 12, 14 };
		bean.put(GuiParameters.IMAGEGRIDSIZE, gridSize);
		bean.put(GuiParameters.PLOTMODE, GuiPlotMode.SCATTER2D);
		bean.put(GuiParameters.GRIDPREFERENCES, new GridPreferences());
		bean.put(GuiParameters.FILEOPERATION, new FileOperationBean());

		flattenAndUnflatten(bean);
	}

	@Test
	public void testDataBean() throws DataBeanException {
		DataBean dataBean = new DataBean();
		dataBean.addAxis(AxisMapBean.XAXIS, DatasetFactory.createRange(IntegerDataset.class, 100));
		dataBean.addAxis(AxisMapBean.XAXIS2, DatasetFactory.createRange(100));
		dataBean.addData(DatasetWithAxisInformation.createAxisDataSet(DatasetFactory.createRange(IntegerDataset.class, 100)));
		flattenAndUnflatten(dataBean);

		// Test that nexus tree data is removed
// TODO fix this to use HDF5 tree
//		DataBean dataBeanWithNexusTree = new DataBean();
//		dataBeanWithNexusTree.setAxisData(dataBean.getAxisData());
//		dataBeanWithNexusTree.setData(dataBean.getData());
//		flattenAndUnflatten(dataBeanWithNexusTree, dataBean);
	}

	@Test
	public void testDataset() throws Exception {
		// flatten to an npy file implicitly
		flattenAndUnflatten(DatasetFactory.createRange(IntegerDataset.class, 100));

		// flatten a descriptor of a Dataset that unflattens to an abstract data set
		Dataset ds = DatasetFactory.createRange(IntegerDataset.class, 100);
		DataHolder dh = new DataHolder();
		dh.addDataset("", ds);
		File tempFile = File.createTempFile("scisofttmp-", ".npy");
		new NumPyFileSaver(tempFile.toString()).saveFile(dh);

		DatasetDescriptor descriptor = new DatasetDescriptor();
		descriptor.setFilename(tempFile.toString());
		descriptor.setDeleteAfterLoad(false);
		descriptor.setIndex(0);
		descriptor.setName(null);

		flattenAndUnflatten(descriptor, ds);

		if (!tempFile.delete()) {
			tempFile.deleteOnExit();
		}
	}

	@Test
	public void testROIBase() {
		flattenAndUnflatten(new ROIBase());

		ROIBase roiBase = new ROIBase();
		roiBase.setPoint(new double[] { -0.3, 2.0 });
		flattenAndUnflatten(roiBase);

		roiBase.setPlot(true);
		flattenAndUnflatten(roiBase);
		roiBase.setPlot(false);
		flattenAndUnflatten(roiBase);
	}

	@Test
	public void testPointROI() {
		flattenAndUnflatten(new PointROI());

		PointROI point = new PointROI();
		point.setPoint(new double[] { -0.3, 2.0 });
		flattenAndUnflatten(point);

		point.setPlot(true);
		flattenAndUnflatten(point);
		point.setPlot(false);
		flattenAndUnflatten(point);
	}

	@Test
	public void testLinearROI() {
		flattenAndUnflatten(new LinearROI(1.1, 0.6));

		double[] spt = { -0.3, 2.0 };
		double[] ept = { 20.0, -22.5 };
		LinearROI line = new LinearROI(spt, ept);
		flattenAndUnflatten(line);

		line.setCrossHair(true);
		flattenAndUnflatten(line);
	}

	@Test
	public void testRectangularROI() {
		flattenAndUnflatten(new RectangularROI(1.1, -2, 5.0, 10.0, 0.6, true));
		flattenAndUnflatten(new RectangularROI(-11.2, 2.8, 5.7, 10.2, 0.4, false));
	}

	@Test
	public void testSectorROI() {
		SectorROI sector = new SectorROI(0.2, 15.2, 0.1, 0.2, 0.01, -0.2, 1.0, true, SectorROI.XREFLECT);
		flattenAndUnflatten(sector);

		sector = new SectorROI(2.3, 1.2, 0.02, 0.04, 0.71, -0.9, 1.0, false, SectorROI.FULL);
		sector.setAverageArea(true);
		sector.setCombineSymmetry(true);
		flattenAndUnflatten(sector);
	}

	@Test
	public void testCircularROI() {
		flattenAndUnflatten(new CircularROI(1.1, -2, 5.0));
	}

	@Test
	public void testEllipticalROI() {
		flattenAndUnflatten(new EllipticalROI(1.1, 0.23, -2, 5.0, -23.5));
	}

	@Test
	public void testPointROIList() {
		PointROIList pList = new PointROIList();
		pList.add(new PointROI(1, -3.4));
		pList.add(new PointROI(-235.6, 0));
		flattenAndUnflatten(pList);
	}

	@Test
	public void testLinearROIList() {
		LinearROIList lList = new LinearROIList();
		lList.add(new LinearROI(1.1, 0.6));
		lList.add(new LinearROI(0.9, 0.36));
		flattenAndUnflatten(lList);
	}

	@Test
	public void testRectangularROIList() {
		RectangularROIList rList = new RectangularROIList();
		rList.add(new RectangularROI(1.1, -2, 5.0, 10.0, 0.6, true));
		rList.add(new RectangularROI(-1.9, -2, 5.8, 2.0, 0.9, false));
		flattenAndUnflatten(rList);
	}

	@Test
	public void testSectorROIList() {
		SectorROIList sList = new SectorROIList();
		sList.add(new SectorROI(0.2, 15.2, 0.1, 0.2, 0.01, -0.2, 1.0, true, SectorROI.XREFLECT));
		sList.add(new SectorROI(0.3, -12, 0.0, 1.1, 0.651, -0.2, 1.0, true, SectorROI.INVERT));
		flattenAndUnflatten(sList);
	}

	@Test
	public void testCircularROIList() {
		CircularROIList cList = new CircularROIList();
		cList.add(new CircularROI(1.1, -2, 5.0));
		flattenAndUnflatten(cList);
	}

	@Test
	public void testEllipticalROIList() {
		EllipticalROIList eList = new EllipticalROIList();
		eList.add(new EllipticalROI(1.1, 0.23, -2, 5.0, -23.5));
		flattenAndUnflatten(eList);
	}

	@Test
	public void testDatasetWithAxisInformation() {
		DatasetWithAxisInformation ds = DatasetWithAxisInformation.createAxisDataSet(DatasetFactory.createRange(IntegerDataset.class, 100));
		flattenAndUnflatten(ds);

		ds = DatasetWithAxisInformation.createAxisDataSet(DatasetFactory.createRange(IntegerDataset.class, 100), "Hello");
		flattenAndUnflatten(ds);

		ds = DatasetWithAxisInformation.createAxisDataSet(DatasetFactory.createRange(IntegerDataset.class, 100), "Hello", "Goodbye");
		flattenAndUnflatten(ds);

		ds = DatasetWithAxisInformation.createAxisDataSet(DatasetFactory.createRange(IntegerDataset.class, 100), "Hello", null);
		flattenAndUnflatten(ds);
	}

	@Test
	public void testAxisMapBean() {
		AxisMapBean amb = new AxisMapBean();
		String[] ids = { AxisMapBean.XAXIS, AxisMapBean.YAXIS };
		amb.setAxisID(ids);
		String[] names = { AxisMapBean.XAXIS, AxisMapBean.YAXIS };
		amb.setAxisNames(names);
		flattenAndUnflatten(amb);

		amb.setAxisNames(null);
		flattenAndUnflatten(amb);
	}

	@Test
	public void testException() {
		// Exceptions are always unflattened as AnalysisRpcRemoteException type,
		// with original type information pre-pended to the message
		Exception[] npe_in = new Exception[] {new NullPointerException("Exceptional null happened")};
		AnalysisRpcRemoteException[] npe_out
			= new AnalysisRpcRemoteException[] {new AnalysisRpcRemoteException("Exceptional null happened")};
		// Make sure the stack traces are the same
		npe_out[0].setStackTrace(npe_in[0].getStackTrace());
		flattenAndUnflatten(npe_in, npe_out);
	}

	@Test
	public void testGuiParameters() {
		// test one explicitly
		flattenAndUnflatten(GuiParameters.PLOTMODE);
		// test all the parameters
		// NOTE If the test fails here it is probably because of a mismatch between the Java and Python GuiParameters.
		// see GuiParameters.java and pybeans.py
		for (GuiParameters param : GuiParameters.values()) {
			flattenAndUnflatten(param);
		}
	}

	@Test
	public void testGuiPlotMode() {
		// test one explicitly
		flattenAndUnflatten(GuiPlotMode.EMPTY);
		// test all the modes
		for (GuiPlotMode param : GuiPlotMode.values()) {
			flattenAndUnflatten(param);
		}
	}

	@Test
	public void testUUID() {
		flattenAndUnflatten(UUID.fromString("93dfd804-85ba-4074-afce-d621f7f2aac6"));
		flattenAndUnflatten(UUID.fromString("dd09fd5c-bb75-4c8b-854b-7f3bb2c9c399"));
		flattenAndUnflatten(UUID.fromString("00000000-0000-0000-0000-000000000000"));
		flattenAndUnflatten(UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"));
		flattenAndUnflatten(UUID.fromString("FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF"));
		for (int i = 0; i < 100; i++) {
			// There is a reproducibility danger in using random UUIDs in this
			// test in that if one fails to match it cannot simply be re-run to
			// reproduce. The failing UUID should be extracted from the error message
			// stack trace and added to the list above of explicit values.
			// If it fails, the message will look something like:
			// junit.framework.AssertionFailedError: expected:<93dfd804-85ba-4074-afce-d621f7f2aac6> but
			// was:<dd09fd5c-bb75-4c8b-854b-7f3bb2c9c399>
			// ...
			flattenAndUnflatten(UUID.randomUUID());
		}
	}

	// Primitive arrays and arrays of boxed primitives unflatten as primitive arrays
	@Test
	public void testPrimitiveArrays() {
		int[] ints = { 1, 5, -7 };
		flattenAndUnflatten(ints);
		flattenAndUnflatten(ArrayUtils.toObject(ints), ints);

		double[] doubles = { 1.4, 12.6, 0, handleDoubleSpecials ? Double.NaN : -1.5 };
		flattenAndUnflatten(doubles);
		flattenAndUnflatten(ArrayUtils.toObject(doubles), doubles);

		boolean[] booleans = { true, false, false, true };
		flattenAndUnflatten(booleans);
		flattenAndUnflatten(ArrayUtils.toObject(booleans), booleans);

		double[][] doubles2d = { { 1, 5, -7 }, { 1.4, 12.6, 0 } };
		flattenAndUnflatten(doubles2d, new double[][] { doubles2d[0], doubles2d[1] });
		flattenAndUnflatten(new double[][] { doubles2d[0], doubles2d[1] });
	}

}
