package uk.ac.diamond.scisoft.analysis.processing.actor.actors;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dawb.passerelle.actors.data.config.ISliceInformationProvider;
import org.dawb.passerelle.actors.data.config.JSONSliceParameter;
import org.dawb.passerelle.common.DatasetConstants;
import org.dawb.passerelle.common.actors.AbstractDataMessageSource;
import org.dawb.passerelle.common.actors.ActorUtils;
import org.dawb.passerelle.common.message.DataMessageException;
import org.dawb.passerelle.common.message.IVariable;
import org.dawb.passerelle.common.message.IVariable.VARIABLE_TYPE;
import org.dawb.passerelle.common.message.MessageUtils;
import org.dawb.passerelle.common.message.Variable;
import org.dawb.passerelle.common.parameter.ParameterUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.message.DataMessageComponent;
import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceViewIterator;
import org.eclipse.dawnsci.analysis.dataset.slicer.Slicer;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ptolemy.data.expr.StringParameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

import com.isencia.passerelle.actor.InitializationException;
import com.isencia.passerelle.actor.ProcessingException;
import com.isencia.passerelle.core.ErrorCode;
import com.isencia.passerelle.core.PasserelleException;
import com.isencia.passerelle.message.ManagedMessage;
import com.isencia.passerelle.message.MessageException;
import com.isencia.passerelle.message.MessageFactory;
import com.isencia.passerelle.util.EnvironmentUtils;
import com.isencia.passerelle.util.ptolemy.IAvailableChoices;
import com.isencia.passerelle.util.ptolemy.ResourceParameter;
import com.isencia.passerelle.util.ptolemy.StringChoiceParameter;
import com.isencia.util.StringConvertor;

/**
 * TODO This class is not currently editable in the UI.
 * This task can be completed by creating attributes which edit the 
 * slice setup @see DataImportSource 
 *
 * For now the class is created in memory to run the operation service pipeline.
 * 
 * To avoid too much data in memory the Q size is defaulted on this class
 * 
 * @author fcp94556
 *
 */
public class OperationSource extends AbstractDataMessageSource implements ISliceInformationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(OperationSource.class);

	private SliceViewIterator generator;
	private ManagedMessage message;
//	private Queue<SliceInfo>  queue;
	private IOperationContext    context; // Might be null if pipeline rerun from UI
	
	// a counter for indexing each generated message in the complete sequence that this source generates
	private long msgCounter;
	// a unique sequence identifier for each execution of this actor within a single parent workflow execution
	private long msgSequenceID;
	
	// Attributes
	public final  ResourceParameter  path;
	public final  StringParameter    datasetPath;
	public final  JSONSliceParameter slicing;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1014225092138237014L;

	public OperationSource(CompositeEntity container, String name) throws NameDuplicationException, IllegalActionException {
		
		super(container, name);
		
		// Data file
		path = new ResourceParameter(this, "Data File", "Data File", LoaderFactory.getSupportedExtensions().toArray(new String[0]));
		setDescription(path, Requirement.ESSENTIAL, VariableHandling.EXPAND, "The path to the data to read. May be an external file (full path to file) or a file in the workspace ('relative' file) or a folder which will iterate over all contained files and use the filter.");
		try {
			URI baseURI = new File(StringConvertor.convertPathDelimiters(EnvironmentUtils.getApplicationRootFolder())).toURI();
			path.setBaseDirectory(baseURI);
		} catch (Exception e) {
			logger.error("Cannot set base directory for "+getClass().getName(), e);
		}
		registerConfigurableParameter(path);
		
		// Its data
		datasetPath = new StringChoiceParameter(this, "Data Set", new IAvailableChoices() {		
			@Override
			public String[] getChoices() {
                try {
                	return LoaderFactory.getData(getSourcePath()).getNames();
                } catch (Exception ne) {
                	return new String[]{"Please select a Data File"};
                }
			}
			@Override
			public Map<String,String> getVisibleChoices() {
			    return null;
			}
		}, 1 << 2); // Single selection bit
		setDescription(datasetPath, Requirement.ESSENTIAL, VariableHandling.EXPAND, "A dataset name to read for slicing. Please set the path before setting the dataset names. If the path is an expand, use a temperary (but typical) file so that the name list can be determined in the builder.");
		registerConfigurableParameter(datasetPath);

		
		// Slicing
		slicing = new JSONSliceParameter(this, "Data Set Slice");
		registerConfigurableParameter(slicing);
		setDescription(slicing, Requirement.ESSENTIAL, VariableHandling.NONE, "Slicing can only be done if one dataset is being exctracted from the data at a time. Set the '"+datasetPath.getDisplayName()+"' attribute first. You can use expands inside the slicing dialog.");
	}
	
	@Override
	public void doPreInitialize() {
		generator = null;
	}

	@Override
	protected void doInitialize() throws InitializationException {
		msgCounter = 0;
		msgSequenceID = MessageFactory.getInstance().createSequenceID();

        try {
    		if (!isTriggerConnected()) createQueue(null);
		} catch (Exception e) {
			throw new InitializationException(ErrorCode.FATAL, e.getMessage(), this, e);
		}
        super.doInitialize();
	}

	private void createQueue(ManagedMessage msg) throws Exception {
		message = msg;
		
		if (context!=null) {
			generator = Slicer.getSliceViewGenerator(context.getData(), context.getSlicing());
			
		} else {
			final IDataHolder  dh = LoaderFactory.getData(getSourcePath(msg));
			final ILazyDataset lz = dh.getLazyDataset(getDatasetPath(msg));
			generator = Slicer.getSliceViewGenerator(lz, slicing.getValue(HashMap.class));
			
		}
	}

	public boolean hasNoMoreMessages() {
		if (generator == null)   return true;
		return super.hasNoMoreMessages();
	}
	
	protected ManagedMessage getDataMessage() throws ProcessingException {

		if (generator == null)   return null;
		if (!generator.hasNext()) {
			generator = null;
			return null;
		}
		
		if (isFinishRequested()) {
			generator = null;
			return null;
		}

		// Required to stop too many slugs going into a threading actor.
		ActorUtils.waitWhileLocked();
		
		ILazyDataset lazy = generator.getCurrentView();
		if (lazy == null) return null;
		final SliceInfo info = new SliceInfo(lazy, message);
		
        ManagedMessage msg = MessageFactory.getInstance().createMessageInSequence(msgSequenceID, msgCounter++, hasNoMoreMessages(), getStandardMessageHeaders());
    
		try {
			msg.setBodyHeader("TITLE", info.getName());
			msg.setBodyContent(getData(info), DatasetConstants.CONTENT_TYPE_DATA);
		} catch (MessageException e) {
			msg = MessageFactory.getInstance().createErrorMessage(new PasserelleException(ErrorCode.MSG_CONSTRUCTION_ERROR, "Cannot set map of data in message body!", this, e));
			generator = null;
		} catch (Exception ne) {
			generator = null;
			throw new DataMessageException("Cannot read data from '"+info.getName()+"'", this, ne);
		}

		return msg;

	}
	
	public boolean isFinishRequested() {
		if (super.isFinishRequested()) return true;
		if (context!=null && context.getMonitor()!=null && context.getMonitor().isCancelled()) return true;
		return false;
	}

	private DataMessageComponent getData(SliceInfo info) throws Exception {
		
		DataMessageComponent ret = new DataMessageComponent();
		
		final IDataset slice = info.getSlice();
		ret.setList(slice);
		
		if (getSourcePath()!=null) {
			ret.putScalar("file_path", getSourcePath());
			ret.putScalar("file_name", new File(getSourcePath(info.getTrigger())).getName());
			ret.putScalar("file_dir",  new File(getSourcePath(info.getTrigger())).getParentFile().getAbsolutePath());
			ret.putScalar("dataset_path",  getDatasetPath(info.getTrigger()));
			ret.putScalar("slice_name",    info.getName());
		}

		return ret;
	}

	@Override
	protected boolean mustWaitForTrigger() {
		return false;
	}

	public IOperationContext getContext() {
		return context;
	}

	/**
	 * Until there are attributes available for setting this run up in the UI,
	 * you must define the message using this setter.
	 * 
	 * @param context
	 * @throws Exception 
	 */
	public void setContext(IOperationContext context) throws Exception {
		this.context = context;
		
        if (context.getFilePath()!=null && context.getDatasetPath()!=null) {
        	path.setExpression(context.getFilePath());
        	datasetPath.setExpression(context.getDatasetPath());
        } else {
    		ILazyDataset lz = context.getData();
    		List<SliceFromSeriesMetadata> md = lz.getMetadata(SliceFromSeriesMetadata.class);
            
    		if (md!=null) { // This means they cannot open up the workflow and have it run directly.
        		SourceInformation sinfo = md.get(0).getSourceInfo();
        		if (sinfo!=null) {
		        	path.setExpression(sinfo.getFilePath());
		        	datasetPath.setExpression(sinfo.getDatasetName());
        		}
    		}
        }
        slicing.setValue(context.getSlicing());
	}

	
	/**
	 * "callback"-method that can be overridden by TriggeredSource implementations,
	 * if they want to act e.g. on the contents of a received trigger message.
	 * 
	 * @param triggerMsg
	 */
	protected void acceptTriggerMessage(ManagedMessage triggerMsg) {
		try {
			createQueue(triggerMsg);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String[] getDataSetNames() {
        try {
        	return new String[]{getDatasetPath(null)};
        } catch (Exception ne) {
        	return new String[]{"Please select a Data File"};
        }
	}

	@Override
	public String getSourcePath() {
		return getSourcePath(null);
	}

	private String getSourcePath(final ManagedMessage manMsg) {
		try {
			final DataMessageComponent comp = manMsg!=null ? MessageUtils.coerceMessage(manMsg) : null;
			String sourcePath = ParameterUtils.getSubstituedValue(path, comp);
			
			try {
				final IResource res = ResourcesPlugin.getWorkspace().getRoot().findMember(sourcePath, true);
				sourcePath = res.getLocation().toOSString();
				return sourcePath;
			} catch (NullPointerException ne) {
				
			}

			final File file = new File(sourcePath);
			if (!file.exists()) return null;
			
			return file.getAbsolutePath();
			
		} catch (Exception e) {
			return null;
		}
	}
	
	private String getDatasetPath(final ManagedMessage manMsg) {
		try {
			final DataMessageComponent comp = manMsg!=null ? MessageUtils.coerceMessage(manMsg) : null;
			return ParameterUtils.getSubstituedValue(datasetPath, comp);

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<IVariable> getOutputVariables() {
		
		final List<IVariable> ret = new ArrayList<IVariable>(7);
		if (getSourcePath()==null)  {
			final String msg = "Invalid Path '"+path.getExpression()+"'";
			ret.add(new Variable("file_path",    VARIABLE_TYPE.PATH,   msg, String.class));
			ret.add(new Variable("file_name",    VARIABLE_TYPE.SCALAR, msg, String.class));
			ret.add(new Variable("file_dir",     VARIABLE_TYPE.PATH,   msg, String.class));
			ret.add(new Variable("dataset_path", VARIABLE_TYPE.SCALAR, msg, String.class));
			ret.add(new Variable("slice_name",   VARIABLE_TYPE.SCALAR, msg, String.class));
			return ret;
		}
		
		ret.add(new Variable("file_path",    VARIABLE_TYPE.PATH,   getSourcePath(), String.class));
		ret.add(new Variable("file_name",    VARIABLE_TYPE.SCALAR, new File(getSourcePath()).getName(), String.class));
		ret.add(new Variable("file_dir",     VARIABLE_TYPE.PATH,   new File(getSourcePath()).getParentFile().getAbsolutePath(), String.class));
		ret.add(new Variable("dataset_path", VARIABLE_TYPE.SCALAR, datasetPath.getExpression(), String.class));
		ret.add(new Variable("slice_name",   VARIABLE_TYPE.SCALAR, String.class));
				
		return ret;
	}

	
	private class SliceInfo {
		private ILazyDataset   slice;
		private ManagedMessage trigger;
		
		
		public SliceInfo(ILazyDataset slice, ManagedMessage trigger) {
			super();
			this.slice = slice;
			this.trigger = trigger;
		}
		public List<? extends MetadataType> getMetadata(Class<? extends MetadataType> class1) throws Exception {
			return slice.getMetadata(class1);
		}
		public String getName() {
			return slice.getName();
		}
		public IDataset getSlice() {
			IDataset s = slice.getSlice();
			return s;
		}
		public void setSlice(ILazyDataset slice) {
			this.slice = slice;
		}
		public ManagedMessage getTrigger() {
			return trigger;
		}
		public void setTrigger(ManagedMessage trigger) {
			this.trigger = trigger;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((slice == null) ? 0 : slice.hashCode());
			result = prime * result
					+ ((trigger == null) ? 0 : trigger.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SliceInfo other = (SliceInfo) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (slice == null) {
				if (other.slice != null)
					return false;
			} else if (!slice.equals(other.slice))
				return false;
			if (trigger == null) {
				if (other.trigger != null)
					return false;
			} else if (!trigger.equals(other.trigger))
				return false;
			return true;
		}
		private OperationSource getOuterType() {
			return OperationSource.this;
		}
		
	}
}
