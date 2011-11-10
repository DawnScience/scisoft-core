import uk.ac.diamond.scisoft.analysis.rpc.FlatteningService as _flatservice

_flatten=_flatservice.getFlattener()

flatten=_flatten.flatten
unflatten=_flatten.unflatten
canflatten=_flatten.canFlatten
canunflatten=_flatten.canUnFlatten
settemplocation=_flatten.setTempLocation
addhelper=_flatten.addHelper
