package org.eclipse.dawnsci.nexus.template;

/**
 * Constants for use with Nexus Templates. These constants include symbols that have special meaning in
 * the nexus template format, such as '/', '@' and '*', as well as special mapping names, such as 'value' and 'link'.
 * 
 * @author Matthew Dickie
 */
public class NexusTemplateConstants {

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
	public static final String MAPPING_NAME_EXPRESSION = "expression";

	private NexusTemplateConstants() {
		// private constructor to prevent instantiation
	}
	
}
