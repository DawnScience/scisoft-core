package org.dawnsci.surfacescatter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import org.apache.commons.lang.StringUtils;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileFactoryHDF5;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.StringDataset;

public class FittingParametersInputReader {

	private static Scanner in;

	public static FittingParameters reader(String title) {

		try {
			in = new Scanner(new FileReader(title));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FittingParameters fp = new FittingParameters();

		while (in.hasNextLine()) {
			String next = in.nextLine();
			if (!next.startsWith("#")) {
				String[] columns = next.split("	");
				fp.setPt0(Integer.parseInt(columns[0]));
				fp.setPt1(Integer.parseInt(columns[1]));
				fp.setLen0(Integer.parseInt(columns[2]));
				fp.setLen1(Integer.parseInt(columns[3]));
				fp.setBgMethod(AnalaysisMethodologies.toMethodology(columns[4]));
				fp.setTracker(TrackingMethodology.toTracker1(columns[5]));
				fp.setFitPower(AnalaysisMethodologies.toFitPower(columns[6]));
				fp.setBoundaryBox(Integer.parseInt(columns[7]));
				fp.setSliderPos(Integer.parseInt(columns[8]));
				fp.setXValue(Double.parseDouble(columns[9]));
				fp.setFile(columns[10]);

			}
		}

		in.close();

		return fp;
	}

	public static void readerFromNexus(String filename, int frameNumber, FrameModel m) {

		// FittingParameters fp = new FittingParameters();

		NexusFile file = new NexusFileFactoryHDF5().newNexusFile(filename);

		try {
			file.openToRead();
		} catch (NexusException e) {
			e.printStackTrace();
		}

		final String path = "/" + NeXusStructureStrings.getEntry() + "/";

		final String pointNode = path + "point_" + frameNumber;

		String[] attributeNames0 = new String[] { "Boundary_Box", "Fit_Power", "Tracker_Type", "Background_Methodology",
		"ROI_Location" };

		String[] attributeNames = new String[attributeNames0.length];

		for (int g = 0; g < attributeNames0.length; g++) {
			attributeNames[g] = pointNode + attributeNames0[g];
		}

		// List<GroupNode> parametersDNode = gn.getGroupNodes();

		GroupNode point;
		try {
			point = file.getGroup(pointNode, true);

			Attribute boundaryBoxAttribute = point.getAttribute(attributeNames0[0]);
			IntegerDataset boundaryBox0 = DatasetUtils.cast(IntegerDataset.class, boundaryBoxAttribute.getValue());

			int boundaryBox = boundaryBox0.getInt();

			m.setBoundaryBox(boundaryBox);

			Attribute fitPowerAttribute = point.getAttribute(attributeNames0[1]);
			StringDataset fitPower0 = DatasetUtils.cast(StringDataset.class, fitPowerAttribute.getValue());

			int fitPower = Integer.valueOf(fitPower0.get());

			m.setFitPower(fitPower);

			Attribute trackerTypeAttribute = point.getAttribute(attributeNames0[2]);

			StringDataset trackerType = DatasetUtils.cast(StringDataset.class, trackerTypeAttribute.getValue());

			m.setTrackingMethodology(trackerType.get());

			Attribute backgroundMethodologyAttribute = point.getAttribute(attributeNames0[3]);
			StringDataset backgroundMethodology = DatasetUtils.cast(StringDataset.class,
					backgroundMethodologyAttribute.getValue());

			m.setBackgroundMethodology(backgroundMethodology.get());

			Attribute roiAttribute = point.getAttribute(attributeNames0[4]);
			DoubleDataset roiAttributeDat = DatasetUtils.cast(DoubleDataset.class, roiAttribute.getValue());

			double[] roi = new double[8];

			for (int h = 0; h < 8; h++) {
				roi[h] = roiAttributeDat.getDouble(h);
			}

			m.setRoiLocation(roi);

		} catch (NexusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void geometricalParametersReaderFromNexus(String filename, GeometricParametersModel gm) {

		final String path = "/" + NeXusStructureStrings.getEntry() + "/";

		final String parametersPath = path + NeXusStructureStrings.getParameters();

		NexusFile file = new NexusFileFactoryHDF5().newNexusFile(filename);

		try {
			file.openToRead();
		} catch (NexusException e) {
			e.printStackTrace();
		}

		// List<GroupNode> parametersDNode = gn.getGroupNodes();
		GroupNode parametersNode;

		try {
			parametersNode = file.getGroup(parametersPath, true);

			Method[] methods = gm.getClass().getMethods();

			for (Method m : methods) {

				String mName = m.getName();
				CharSequence s = "get";

				if (mName.contains(s) && !mName.equals("getClass") && !mName.equals("getReflectivityFluxMap")
						&& !mName.equals("getReflectivityAnglesMap") && !mName.equals("getsXRDMap")) {

					String name = StringUtils.substringAfter(mName, "get");

					Attribute att = parametersNode.getAttribute(name);

					String writeName = "set" + name;

					StringDataset sd = null;
					try {
						sd = DatasetUtils.cast(StringDataset.class, att.getValue());
					} catch (Exception f) {
						System.out.println(f.getMessage());
					}
					m.getReturnType();

					for (Method m1 : methods) {
						if (m1.getName().equals(writeName)) {
							String tn = m1.getParameterTypes()[0].getName();
							try {
								switch (tn) {
								case "java.lang.Boolean":
									m1.invoke(gm, Boolean.valueOf(sd.get()));
									break;
								case "java.lang.Double":
									m1.invoke(gm, Double.valueOf(sd.get()));
									break;
								case "java.lang.String":
									m1.invoke(gm, (sd.get()));
									break;
								case "int":
									m1.invoke(gm, Integer.valueOf(sd.get()));
									break;
								case "double":
									m1.invoke(gm, Double.valueOf(sd.get()));
									break;
								default:
									break;
								}

							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
					}
				}
			}
		} catch (NexusException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void anglesAliasReaderFromNexus(String filename) {

		final String path = "/" + NeXusStructureStrings.getEntry() + "/";

		final String aliasPath = path + NeXusStructureStrings.getAliases() + "/";

		NexusFile file = new NexusFileFactoryHDF5().newNexusFile(filename);

		try {
			file.openToRead();
		} catch (NexusException e) {
			e.printStackTrace();
		}

		// List<GroupNode> parametersDNode = gn.getGroupNodes();
		GroupNode aliasNode;

		try {

			aliasNode = file.getGroup(aliasPath, true);

			String alias = "failed";

			for (SXRDAngleAliasEnum m : SXRDAngleAliasEnum.values()) {

				if (m != SXRDAngleAliasEnum.NULL) {

					alias = aliasNode.getAttribute(m.getAngleVariable()).getValue().getString();

					m.setAngleAlias(alias);
				}
			}

			for (ReflectivityAngleAliasEnum r : ReflectivityAngleAliasEnum.values()) {

				if (r != ReflectivityAngleAliasEnum.NULL) {
					alias = aliasNode.getAttribute(r.getAngleVariable()).getValue().getString();

					r.setAngleAlias(alias);
				}
			}

			for (ReflectivityFluxParametersAliasEnum f : ReflectivityFluxParametersAliasEnum.values()) {

				if (f != ReflectivityFluxParametersAliasEnum.NULL) {
					alias = aliasNode.getAttribute(f.getFluxVariable()).getValue().getString();

					f.setFluxAlias(alias);
				}
			}
		} catch (Exception g) {
			System.out.println(g.getMessage());
		}

	}

	public static FittingParameters fittingParametersFromFrameModel(FrameModel fm) {

		FittingParameters fp = new FittingParameters();

		int[][] lenPt = LocationLenPtConverterUtils.locationToLenPtConverter(fm.getRoiLocation());
		int[] len = lenPt[0];
		int[] pt = lenPt[1];

		fp.setPt0(pt[0]);
		fp.setPt1(pt[1]);
		fp.setLen0(len[0]);
		fp.setLen1(len[1]);
		fp.setLenpt(lenPt);
		fp.setBgMethod(fm.getBackgroundMethdology());
		fp.setTracker(fm.getTrackingMethodology());
		fp.setFitPower(fm.getFitPower());
		fp.setBoundaryBox(fm.getBoundaryBox());
		fp.setSliderPos(0);

		return fp;

	}

}
