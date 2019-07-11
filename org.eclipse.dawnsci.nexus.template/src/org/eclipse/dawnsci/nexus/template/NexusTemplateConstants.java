package org.eclipse.dawnsci.nexus.template;

/**
 * 
 * TODO javadoc
 * 
 * @author wgp76868
 *
 */
public class NexusTemplateConstants {

	public enum ApplicationMode { // TODO remove if not necessary
		ON_DISK, IN_MEMORY
	}

	public static final char GROUP_SUFFIX = '/';
	public static final char ATTRIBUTE_SUFFIX = '@';
	public static final char COPY_GROUP_SUFFIX = '*';
	public static final String NODE_TYPE_SUFFIX_CHARS = 
			"" + GROUP_SUFFIX + ATTRIBUTE_SUFFIX + COPY_GROUP_SUFFIX;
	public static final String ATTRIBUTE_NAME_NX_CLASS = "NX_class";
	public static final String MAPPING_NAME_VALUE = "value";
	public static final String MAPPING_NAME_LINK = "link";
	public static final String MAPPING_NAME_NODE_PATH = "nodePath";
	public static final String MAPPING_NAME_AXIS_SUBSTITUTIONS = "axisSubstitutions";

	private NexusTemplateConstants() {
		// private constructor to prevent instantiation
	}
	
}
