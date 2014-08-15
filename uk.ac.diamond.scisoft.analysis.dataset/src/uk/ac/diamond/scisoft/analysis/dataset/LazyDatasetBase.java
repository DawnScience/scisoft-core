/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.IMetaData;
import uk.ac.diamond.scisoft.analysis.metadata.MetadataType;
import uk.ac.diamond.scisoft.analysis.metadata.Sliceable;

/**
 * Common base for both lazy and normal dataset implementations
 */
public abstract class LazyDatasetBase implements ILazyDataset, Serializable {

	protected static final Logger logger = LoggerFactory.getLogger(LazyDatasetBase.class);

	protected String name = "";

	/**
	 * The shape or dimensions of the dataset
	 */
	protected int[] shape;

	protected Map<Class<? extends MetadataType>, List<MetadataType>> metadata = null;

	/**
	 * @return type of dataset item
	 */
	abstract public int getDtype();

	@Override
	public Class<?> elementClass() {
		return AbstractDataset.elementClass(getDtype());
	}

	@Override
	public LazyDatasetBase clone() {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!getClass().equals(obj.getClass())) {
			return false;
		}
	
		LazyDatasetBase other = (LazyDatasetBase) obj;
		if (!Arrays.equals(shape, other.shape)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = getDtype() * 17 + getElementsPerItem();
		int rank = shape.length;
		for (int i = 0; i < rank; i++) {
			hash = hash*17 + shape[i];
		}
		return hash;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int[] getShape() {
		return shape.clone();
	}

	@Override
	public int getRank() {
		return shape.length;
	}

	@Override
	public ILazyDataset squeeze() {
		return squeeze(false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends MetadataType> void setMetadata(T metadata) {
		if (metadata == null)
			return;

		List<T> ml = null;
		try {
			ml = (List<T>) getMetadata(metadata.getClass());
		} catch (Exception e) {
			logger.error("Problem retreiving metadata of class {}: {}", metadata.getClass().getCanonicalName(), e);
		}

		if (ml == null) {
			addMetadata(metadata);
		} else {
			ml.clear();
			ml.add(metadata);
		}
	}

	@Override
	public IMetaData getMetadata() {
		List<IMetaData> ml = null;
		try {
			ml = getMetadata(IMetaData.class);
		} catch (Exception e) {
			logger.error("Problem retreiving metadata of class {}: {}", IMetaData.class.getCanonicalName(), e);
		}

		return ml == null ? null : ml.get(0);
	}

	@Override
	public <T extends MetadataType> void addMetadata(T metadata) {
		if (metadata == null)
			return;

		if (this.metadata == null) {
			this.metadata = new HashMap<Class<? extends MetadataType>, List<MetadataType>>();
		}

		Class<? extends MetadataType> clazz = findMetadataTypeSubInterfaces(metadata.getClass());
		if (!this.metadata.containsKey(clazz)) {
			this.metadata.put(clazz, new ArrayList<MetadataType>());
		}
		this.metadata.get(clazz).add(metadata);
	}

	/**
	 * Dig down to interface (or class) that directly extends (or implements) MetadataType
	 * @param clazz
	 * @return sub-interface
	 */
	@SuppressWarnings("unchecked")
	static Class<? extends MetadataType> findMetadataTypeSubInterfaces(Class<? extends MetadataType> clazz) {
		Class<?> sclazz = clazz.getSuperclass();
		if (sclazz != null && !sclazz.equals(Object.class)) // recurse up class hierarchy
			return findMetadataTypeSubInterfaces((Class<? extends MetadataType>) sclazz);

		for (Class<?> c : clazz.getInterfaces()) {
			if (c.equals(MetadataType.class))
				return clazz;
			if (MetadataType.class.isAssignableFrom(c)) {
				return findMetadataTypeSubInterfaces((Class<? extends MetadataType>) c);
			}
		}
		assert false; // should not be able to get here!!!
		logger.error("Somehow the search for metadata type interface ended in a bad place");
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends MetadataType> List<T> getMetadata(Class<T> clazz) throws Exception {
		if (metadata == null)
			return null;

		if (clazz == null) {
			List<T> all = new ArrayList<T>();
			for (Class<? extends MetadataType> c : metadata.keySet()) {
				all.addAll((Collection<? extends T>) metadata.get(c));
			}
			return all;
		}

		return (List<T>) metadata.get(findMetadataTypeSubInterfaces(clazz));
	}

	protected Map<Class<? extends MetadataType>, List<MetadataType>> copyMetadata() {
		if (metadata == null)
			return null;

		HashMap<Class<? extends MetadataType>, List<MetadataType>> map = new HashMap<Class<? extends MetadataType>, List<MetadataType>>();

		for (Class<? extends MetadataType> c : metadata.keySet()) {
			List<MetadataType> l = metadata.get(c);
			List<MetadataType> nl = new ArrayList<MetadataType>(l.size());
			map.put(c, nl);
			for (MetadataType m : l) {
				nl.add(m.clone());
			}
		}
		return map;
	}

	interface MetadatasetAnnotationOperation {
		Class<? extends Annotation> getAnnClass();
		ILazyDataset run(ILazyDataset lz);
	}

	class MdsSlice implements MetadatasetAnnotationOperation {
		private int[] start;
		private int[] stop;
		private int[] step;
		private int[] oShape;

		public MdsSlice(final int[] start, final int[] stop, final int[] step, final int[] oShape) {
			this.start = start;
			this.stop = stop;
			this.step = step;
			this.oShape = oShape;
		}

		@Override
		public Class<? extends Annotation> getAnnClass() {
			return Sliceable.class;
		}

		@Override
		public ILazyDataset run(ILazyDataset lz) {
			if (start.length != lz.getRank()) throw new IllegalArgumentException("Slice dimensions do not match dataset!");
			
			int[] stt = start.clone();
			int[] stp = stop.clone();
			int[] ste = step.clone();
			
			int rank = lz.getRank();
			int[] shape = lz.getShape();
			
			for (int i = 0; i < rank; i++) {
				if (shape[i] == oShape[i]) continue;
				if (shape[i] == 1) {
					stt[i] = 0;
					stp[i] = 1;
					ste[1] = 1;
				}
				if (shape[i] != oShape[i] && shape[i] != 1) throw new IllegalArgumentException("Sliceable dataset has invalid size!");
			}
			
			return lz.getSliceView(stt,stp,ste);
			
		}
	}

	/**
	 * Slice all datasets in metadata that are annotated by @Sliceable. Call this on the new sliced
	 * dataset after cloning the metadata
	 * 
	 * @param start
	 * @param stop
	 * @param step
	 * @param oShape
	 */
	protected void sliceMetadata(final int[] start, final int[] stop, final int[] step, final int[] oShape) {
		processAnnotatedMetadata(new MdsSlice(start, stop, step, oShape));
	}

	@SuppressWarnings("unchecked")
	private void processAnnotatedMetadata(MetadatasetAnnotationOperation op) {
		if (metadata == null)
			return;

		for (Class<? extends MetadataType> c : metadata.keySet()) {
			for (MetadataType m : metadata.get(c)) {
				Class<? extends MetadataType> mc = m.getClass();
				do { // iterate over super-classes
					processClass(op, m, mc);
					Class<?> sclazz = mc.getSuperclass();
					if (!MetadataType.class.isAssignableFrom(sclazz))
						break;
					mc = (Class<? extends MetadataType>) sclazz;
				} while (true);
			}
		}
	}

	private static void processClass(MetadatasetAnnotationOperation op, MetadataType m, Class<? extends MetadataType> mc) {
		for (Field f : mc.getDeclaredFields()) {
			if (!f.isAnnotationPresent(op.getAnnClass()))
				continue;

			try {
				f.setAccessible(true);
				Object o = f.get(m);
				if (o == null)
					continue;

				if (o instanceof ILazyDataset) {
					ILazyDataset l = op.run((ILazyDataset)o);
					if (l != null) {
						f.set(m, l);
					}
				} else if (o.getClass().isArray()) {
					processArray(op, o);
				} else if (o instanceof List<?>) {
					List<?> list = (List<?>) o;
					processList(op, list);
				} else if (o instanceof Map<?,?>) {
					Map<?, ?> map = (Map<?, ?>) o;
					processMap(op, map);
				}
			} catch (Exception e) {
				logger.error("Problem occurred when processing metadata of class {}: {}", mc.getCanonicalName(), e);
			}
		}
	}
	
	private static void processArray(MetadatasetAnnotationOperation op, Object o){
		Object r = null;
		int l = Array.getLength(o);
		if (l > 0) {
			for (int j = 0; r == null && j < l; j++) {
				r = Array.get(o, j);
			}
			if (r == null)
				return;
			
			if (r instanceof ILazyDataset) {
				for (int i = 0; i < l; i++) {
					ILazyDataset ld = (ILazyDataset) Array.get(o, i);
					Array.set(o, i, op.run(ld));
				}
			} else if (r.getClass().isArray()) {
				for (int i = 0; i < l; i++) {
					processArray(op, Array.get(o, i));
				}
			} else if (r instanceof List<?>) {
				for (int i = 0; i < l; i++) {
					List<?> list = (List<?>) Array.get(o, i);
					processList(op, list);
				}
			} else if (r instanceof Map<?,?>) {
				for (int i = 0; i < l; i++) {
					Map<?, ?> map = (Map<?, ?>) Array.get(o, i);
					processMap(op, map);
				}
			}
		}
	}
	
	private static void processList(MetadatasetAnnotationOperation op, List<?> list) {
		Object r = null;
		int l = list.size();
		if (l > 0) {
			for (int j = 0; r == null && j < l; j++) {
				r = list.get(j);
			}
			if (r == null)
				return;

			if (r instanceof ILazyDataset) {
				@SuppressWarnings("unchecked")
				List<ILazyDataset> ldList = (List<ILazyDataset>) list;
				for (int i = 0; i < l; i++) {
					ldList.set(i, op.run(ldList.get(i)));
				}
			}else if (r.getClass().isArray()) {
				for (int i = 0; i < l; i++) {
					processArray(op, list.get(i));
				}
			} else if (r instanceof List<?>) {
				for (int i = 0; i < l; i++) {
					List<?> l2 = (List<?>) list.get(i);
					processList(op, l2);
				}
			} else if (r instanceof Map<?,?>) {
				for (int i = 0; i < l; i++) {
					Map<?, ?> map = (Map<?, ?>) list.get(i);
					processMap(op, map);
				}
			}
		}
	}
	
	private static void processMap(MetadatasetAnnotationOperation op, Map<?,?> map) {
		Object r = null;
		int l = map.size();
		if (l > 0) {
			Iterator<?> kit = map.keySet().iterator();
			Object k = null;
			while (r == null && kit.hasNext()) {
				k = kit.next();
				r = map.get(k);
			}
			if (r == null)
				return;

			if (r instanceof ILazyDataset) {
				@SuppressWarnings("unchecked")
				Map<Object, ILazyDataset> ldMap = (Map<Object, ILazyDataset>) map; 
				ILazyDataset ld = (ILazyDataset) r;
				ldMap.put(k, op.run(ld));
				while (kit.hasNext()) {
					k = kit.next();
					ld = ldMap.get(k);
					ldMap.put(k, op.run(ld));
				}
			} else if (r.getClass().isArray()) {
				processArray(op, map.get(k));
				while (kit.hasNext()) {
					k = kit.next();
					processArray(op, map.get(k));
				}
			} else if (r instanceof List<?>) {
				List<?> list = (List<?>) r;
				processList(op, list);
				while (kit.hasNext()) {
					k = kit.next();
					list = (List<?>)map.get(k);
					processList(op, list);
				}
			} else if (r instanceof Map<?,?>) {
				Map<?, ?> m2 = (Map<?, ?>) r;
				processMap(op, m2);
				while (kit.hasNext()) {
					k = kit.next();
					m2 = (Map<?, ?>)map.get(k);
					processMap(op, m2);
				}
			}
		}
	}
}
