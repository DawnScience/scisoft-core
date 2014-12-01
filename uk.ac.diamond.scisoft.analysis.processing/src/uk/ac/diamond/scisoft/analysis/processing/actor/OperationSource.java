package uk.ac.diamond.scisoft.analysis.processing.actor;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.dawb.passerelle.actors.data.config.ISliceInformationProvider;
import org.dawb.passerelle.actors.data.config.JSONSliceParameter;
import org.dawb.passerelle.common.DatasetConstants;
import org.dawb.passerelle.common.actors.AbstractDataMessageSource;
import org.dawb.passerelle.common.actors.ActorUtils;
import org.dawb.passerelle.common.message.DataMessageException;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.message.DataMessageComponent;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.slice.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.api.slice.Slicer;
import org.eclipse.dawnsci.analysis.api.slice.SourceInformation;
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

	private Queue<ILazyDataset>  queue;
	private IOperationContext    context; // Might be null if pipeline rerun from UI
	
	// a counter for indexing each generated message in the complete sequence that this source generates
	private long msgCounter;
	// a unique sequence identifier for each execution of this actor within a single parent workflow execution
	private long msgSequenceID;
	
	// Attributes
	private ResourceParameter  path;
	private StringParameter    datasetPath;
	private JSONSliceParameter slicing;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1014225092138237014L;

	public OperationSource(CompositeEntity container, String name) throws NameDuplicationException, IllegalActionException {
		
		super(container, name);
		
		// Data file
		path = new ResourceParameter(this, "Path", "Data File", LoaderFactory.getSupportedExtensions().toArray(new String[0]));
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
                	return LoaderFactory.getData(path.getExpression()).getNames();
                } catch (Exception ne) {
                	return new String[]{"Please select a Data File"};
                }
			}
			@Override
			public Map<String,String> getVisibleChoices() {
			    return null;
			}
		}, 1 << 2); // Single selection bit
		setDescription(datasetPath, Requirement.ESSENTIAL, VariableHandling.NONE, "A dataset name to read for slicing. Please set the path before setting the dataset names. If the path is an expand, use a temperary (but typical) file so that the name list can be determined in the builder.");
		registerConfigurableParameter(datasetPath);

		
		// Slicing
		slicing = new JSONSliceParameter(this, "Data Set Slice");
		registerConfigurableParameter(slicing);
		setDescription(slicing, Requirement.ESSENTIAL, VariableHandling.NONE, "Slicing can only be done if one dataset is being exctracted from the data at a time. Set the '"+datasetPath.getDisplayName()+"' attribute first. You can use expands inside the slicing dialog.");
	}
	
	@Override
	public void doPreInitialize() {
		queue = null;
	}

	@Override
	protected void doInitialize() throws InitializationException {
		msgCounter = 0;
		msgSequenceID = MessageFactory.getInstance().createSequenceID();

        try {
    		if (!isTriggerConnected()) createQueue();
		} catch (Exception e) {
			throw new InitializationException(ErrorCode.FATAL, e.getMessage(), this, e);
		}
        super.doInitialize();
	}

	private void createQueue() throws Exception {
		if (context!=null) {
			queue = Slicer.getSlices(context.getData(), context.getSlicing());
		} else {
			final IDataHolder  dh = LoaderFactory.getData(path.getExpression());
			final ILazyDataset lz = dh.getLazyDataset(datasetPath.getExpression());
			queue = Slicer.getSlices(lz, slicing.getValue());
		}
	}

	public boolean hasNoMoreMessages() {
		if (queue == null)   return true;
		return queue.isEmpty() && super.hasNoMoreMessages();
	}
	
	protected ManagedMessage getDataMessage() throws ProcessingException {

		if (queue == null)   return null;
		if (queue.isEmpty()) return null;
		
		if (isFinishRequested()) {
			queue.clear();
			return null;
		}

		// Required to stop too many slugs going into a threading actor.
		ActorUtils.waitWhileLocked();
		
		final ILazyDataset info = queue.poll();
		if (info==null) return null;
		
        ManagedMessage msg = MessageFactory.getInstance().createMessageInSequence(msgSequenceID, msgCounter++, hasNoMoreMessages(), getStandardMessageHeaders());
    
		try {
			msg.setBodyHeader("TITLE", info.getName());
			msg.setBodyContent(getData(info), DatasetConstants.CONTENT_TYPE_DATA);
		} catch (MessageException e) {
			msg = MessageFactory.getInstance().createErrorMessage(new PasserelleException(ErrorCode.MSG_CONSTRUCTION_ERROR, "Cannot set map of data in message body!", this, e));
			queue.clear();
		} catch (Exception ne) {
			queue.clear();
			throw new DataMessageException("Cannot read data from '"+info.getName()+"'", this, ne);
		}

		if (context!=null && context.getMonitor()!=null) {
			context.getMonitor().subTask(info.getName());
		}

		return msg;

	}
	
	public boolean isFinishRequested() {
		if (super.isFinishRequested()) return true;
		if (context!=null && context.getMonitor()!=null && context.getMonitor().isCancelled()) return true;
		return false;
	}

	private DataMessageComponent getData(ILazyDataset info) throws Exception {
		
		DataMessageComponent ret = new DataMessageComponent();
		
		final IDataset slice = info.getSlice();
		SliceFromSeriesMetadata ssm = info.getMetadata(SliceFromSeriesMetadata.class).get(0);
		slice.setMetadata(ssm);
		
		ret.setList(slice);
		ret.setSlice(info);
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
    		SourceInformation sinfo = md.get(0).getSourceInfo();
            
        	path.setExpression(sinfo.getFilePath());
        	datasetPath.setExpression(sinfo.getDatasetName());
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
			createQueue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String[] getDataSetNames() {
        try {
        	return new String[]{datasetPath.getExpression()};
        } catch (Exception ne) {
        	return new String[]{"Please select a Data File"};
        }
	}

	@Override
	public String getSourcePath() {
		return path.getExpression();
	}

}
