###
# Copyright 2011 Diamond Light Source Ltd.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
###


from .hdf5 import HDF5tree as _tree
from .hdf5 import HDF5group as _group


class NeXusError(Exception):
    """NeXus Error"""

    pass


class NXobject(_group):
    """
    NXobject is the base object of NeXus and is a group, https://manual.nexusformat.org/classes/base_classes/NXobject.html
    """

    def __init__(self, attrs={}, parent=None, warn=True):
        _group.__init__(self, attrs, parent, warn)
        self.nxname = self.__class__.__name__

    def init_group(self, nodes):
        n = self.nxname
        _group.init_group(self, nodes)
        self.nxname = n


class NXroot(_tree):
    """
    NXroot node, https://manual.nexusformat.org/classes/base_classes/NXroot.html. This is a subclass of the NXobject class.

    See the NXobject documentation for more details.
    """

    def __init__(self, filename, attrs={}, native=None, warn=True):
        _tree.__init__(self, filename, attrs, native, warn)
        self.nxname = self.__class__.__name__

    def init_group(self, nodes):
        n = self.nxname
        _tree.init_group(self, nodes)
        self.nxname = n


def create_NX_class(name, contributed=False):
    subdir = "contributed_definitions" if contributed else "base_classes"
    ds = """
    %s node, https://manual.nexusformat.org/classes/%s/%s.html. This is a subclass of the NXobject class.

    See the NXobject documentation for more details.
    """ % (name, subdir, name)
    cls = type(name, (NXobject,), {"__doc__": ds})
    return cls


_BASE_CLASSES = (
    "NXaperture",
    "NXattenuator",
    "NXbeam",
    "NXbeam_stop",
    "NXbending_magnet",
    "NXcapillary",
    "NXcite",
    "NXcollection",
    "NXcollimator",
    "NXcrystal",
    "NXcylindrical_geometry",
    "NXdata",
    "NXdetector_channel",
    "NXdetector_group",
    "NXdetector_module",
    "NXdetector",
    "NXdisk_chopper",
    "NXentry",
    "NXenvironment",
    "NXevent_data",
    "NXfermi_chopper",
    "NXfilter",
    "NXflipper",
    "NXfresnel_zone_plate",
    "NXgeometry",
    "NXgrating",
    "NXguide",
    "NXinsertion_device",
    "NXinstrument",
    "NXlog",
    "NXmirror",
    "NXmoderator",
    "NXmonitor",
    "NXmonochromator",
    "NXnote",
    # "NXobject",
    "NXoff_geometry",
    "NXorientation",
    "NXparameters",
    "NXpdb",
    "NXpinhole",
    "NXpolarizer",
    "NXpositioner",
    "NXprocess",
    "NXreflections",
    # "NXroot",
    "NXsample_component",
    "NXsample",
    "NXsensor",
    "NXshape",
    "NXslit",
    "NXsource",
    "NXsubentry",
    "NXtransformations",
    "NXtranslation",
    "NXuser",
    "NXvelocity_selector",
    "NXxraylens",
)


# contributed base classes to add when they are ratified by the NIAC
_CONTRIBUTED_CLASSES = (
    "NXaberration_model_ceos",
    "NXaberration_model_nion",
    "NXaberration_model",
    "NXaberration",
    "NXadc",
    "NXaperture_em",
    "NXapm_input_ranging",
    "NXapm_input_reconstruction",
    "NXbeam_path",
    "NXbeam_splitter",
    "NXcalibration",
    "NXcg_alpha_complex",
    "NXcg_cylinder_set",
    "NXcg_ellipsoid_set",
    "NXcg_face_list_data_structure",
    "NXcg_geodesic_mesh",
    "NXcg_grid",
    "NXcg_half_edge_data_structure",
    "NXcg_hexahedron_set",
    "NXcg_marching_cubes",
    "NXcg_parallelogram_set",
    "NXcg_point_set",
    "NXcg_polygon_set",
    "NXcg_polyhedron_set",
    "NXcg_polyline_set",
    "NXcg_roi_set",
    "NXcg_sphere_set",
    "NXcg_tetrahedron_set",
    "NXcg_triangle_set",
    "NXcg_triangulated_surface_mesh",
    "NXcg_unit_normal_set",
    "NXchamber",
    "NXchemical_composition",
    "NXcircuit_board",
    "NXclustering",
    "NXcollectioncolumn",
    "NXcontainer",
    "NXcoordinate_system_set",
    "NXcorrector_cs",
    "NXcs_computer",
    "NXcs_cpu",
    "NXcs_filter_boolean_mask",
    "NXcsg",
    "NXcs_gpu",
    "NXcs_io_obj",
    "NXcs_io_sys",
    "NXcs_mm_sys",
    "NXcs_prng",
    "NXcs_profiling_event",
    "NXcs_profiling",
    "NXdac",
    "NXdeflector",
    "NXdelocalization",
    "NXdispersion_function",
    "NXdispersion",
    "NXdispersion_repeated_parameter",
    "NXdispersion_single_parameter",
    "NXdispersion_table",
    "NXdistortion",
    "NXebeam_column",
    "NXelectronanalyser",
    "NXelectrostatic_kicker",
    "NXem_ebsd_conventions",
    "NXem_ebsd_crystal_structure_model",
    "NXenergydispersion",
    "NXevent_data_em",
    "NXevent_data_em_set",
    "NXfabrication",
    "NXfiber",
    "NXgraph_edge_set",
    "NXgraph_node_set",
    "NXgraph_root",
    "NXibeam_column",
    "NXimage_set_em_adf",
    "NXimage_set_em_kikuchi",
    "NXimage_set",
    "NXinteraction_vol_em",
    "NXion",
    "NXisocontour",
    "NXlens_em",
    "NXlens_opt",
    "NXmagnetic_kicker",
    "NXmanipulator",
    "NXmatch_filter",
    "NXms_feature_set",
    "NXms_snapshot",
    "NXms_snapshot_set",
    "NXoptical_system_em",
    "NXorientation_set",
    "NXpeak",
    "NXpid",
    "NXpolarizer_opt",
    "NXprogram",
    "NXpulser_apm",
    "NXpump",
    "NXquadric",
    "NXquadrupole_magnet",
    "NXreflectron",
    "NXregion",
    "NXregistration",
    "NXscanbox_em",
    "NXseparator",
    "NXsimilarity_grouping",
    "NXslip_system_set",
    "NXsolenoid_magnet",
    "NXsolid_geometry",
    "NXspatial_filter",
    "NXspectrum_set_em_eels",
    "NXspectrum_set_em_xray",
    "NXspectrum_set",
    "NXspindispersion",
    "NXspin_rotator",
    "NXstage_lab",
    "NXsubsampling_filter",
    "NXwaveplate",
)

_m_ns = globals()
for c in _BASE_CLASSES:
    _m_ns[c] = create_NX_class(c)

for c in _CONTRIBUTED_CLASSES:
    _m_ns[c] = create_NX_class(c, True)


def _get_all_nx_classes():
    d = {}
    import sys
    import inspect

    for n, obj in inspect.getmembers(
        sys.modules[__name__], lambda m: inspect.isclass(m) and m.__module__ == __name__
    ):
        if n.startswith("NX"):
            d[n] = obj

    return d


NX_CLASSES = _get_all_nx_classes()
