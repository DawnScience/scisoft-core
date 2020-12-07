/*-
 *******************************************************************************
 * Copyright (c) 2020 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This file was auto-generated from the NXDL XML definition.
 *******************************************************************************/

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;


import org.eclipse.dawnsci.nexus.*;

/**
 * A NeXus transliteration of a PDB file, to be validated only as a PDB
 * rather than in NeXus.
 * Use :ref:`NXpdb` to incorporate the information in an arbitrary
 * PDB into a NeXus file.
 * The main suggestion is to use this as a container
 * class for a PDB entry to describe a sample in NXsample,
 * but it may be more appropriate to place this higher in the
 * hierarchy, say in NXentry.
 * The structure has to follow the structure of a PDB
 * with each PDB data block mapped to a NeXus group of class NXpdb,
 * using a lowercase version of the data block name as the name
 * of the NeXus group, each PDB category in that data block
 * mapped to a NeXus group of class NXpdb and with each PDB column
 * mapped to a NeXus field. Each column in a looped PDB category
 * should always be presented as a 1-dimensional array. The columns
 * in an unlooped PDB category should be presented as scalar values.
 * If a PDB category specifies particular units for columns, the same
 * units should beused for the corresponding fields.
 * A PDB entry is unambigous when all information is carried as text.
 * All text data should be presented as quoted strings, with the quote
 * marks except for the null values "." or "?"
 * For clarity in NXpdb form, numeric data may be presented using the
 * numeric types specified in the mmCIF dictionary. In that case,
 * if a PDB null value, "." or "?", is contained in a numeric column, the
 * IEEE nan should be used for "?" and the IEEE inf should be used for ".".
 * An arbitrary DDL2 CIF file can be represented in NeXus using NXpdb.
 * However, if save frames are required, an NXpdb_class attribute with the
 * value "CBF_cbfsf" is required for each NeXus group representing a save
 * frame. NXpdb attributes are not required for other CIF components,
 * but may be used to provide internal documentation.
 * The nesting of NXpdb groups and datasets that correspond to a CIF with
 * two categories and one saveframe, including the NXpdb_class attribues is::
 * (datablock1):NXpdb
 * @NXpdb_class:CBF_cbfdb
 * (category1):NXpdb
 * @NXpdb_class:CBF_cbfcat
 * (column_name1):[...]
 * (column_name2):[...]
 * (column_name3):[...]
 * ...
 * (category2):NXpdb
 * @NXpdb_class:CBF_cbfcat
 * (column_name4):[...]
 * (column_name5):[...]
 * (column_name6):[...]
 * ...
 * (saveframe1):NXpdb
 * @NXpdb_class:CBF_cbfsf
 * (category3):NXpdb
 * @NXpdb_class:CBF_cbfcat
 * (column_name7):[...]
 * (column_name8):[...]
 * (column_name9):[...]
 * ...
 * ...
 * ...
 * For example, a PDB entry that begins::
 * data_1YVA
 * #
 * _entry.id 1YVA
 * #
 * _audit_conform.dict_name mmcif_pdbx.dic
 * _audit_conform.dict_version 5.279
 * _audit_conform.dict_location http://mmcif.pdb.org/dictionaries/ascii/mmcif_pdbx.dic
 * #
 * loop_
 * _database_2.database_id
 * _database_2.database_code
 * PDB 1YVA
 * RCSB RCSB031959
 * WWPDB D_1000031959
 * #
 * would produce::
 * sample:NXsample
 * 1yva:NXpdb
 * entry:NXpdb
 * id:"1YVA"
 * audit_conform:NXpdb
 * dict_name:"mmcif_pdbx.dic"
 * dict_version:"5.279"
 * dict_location:"http://mmcif.pdb.org/dictionaries/ascii/mmcif_pdbx.dic"
 * database_2:NXpdb
 * database_id:["PDB","RCSB","WWPDB"]
 * database_code:["1YVA","RCSB031959","D_1000031959"]
 * another example is the following excerpt from pdb entry 9ins, giving the sequences
 * of the two chains::
 * loop_
 * _entity_poly.entity_id
 * _entity_poly.nstd_linkage
 * _entity_poly.nstd_monomer
 * _entity_poly.pdbx_seq_one_letter_code
 * _entity_poly.pdbx_seq_one_letter_code_can
 * _entity_poly.type
 * 1 no no GIVEQCCTSICSLYQLENYCN GIVEQCCTSICSLYQLENYCN polypeptide(L)
 * 2 no no FVNQHLCGSHLVEALYLVCGERGFFYTPKA FVNQHLCGSHLVEALYLVCGERGFFYTPKA
 * polypeptide(L)
 * which converts to::
 * entity_poly:NXpdb
 * @NXpdb_class:CBF_cbfcat
 * entity_id:["1", "2"]
 * nstd_linkage:["no", "no"]
 * nstd_monomer:["no", "no"]
 * pdbx_seq_one_letter_code:["GIVEQCCTSICSLYQLENYCN","FVNQHLCGSHLVEALYLVCGERGFFYTPKA"]
 * pdbx_seq_one_letter_code_can:["GIVEQCCTSICSLYQLENYCN","FVNQHLCGSHLVEALYLVCGERGFFYTPKA"]
 * type:["polypeptide(L)", "polypeptide(L)"]
 * 
 */
public class NXpdbImpl extends NXobjectImpl implements NXpdb {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXpdbImpl() {
		super();
	}

	public NXpdbImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXpdb.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_PDB;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

}
