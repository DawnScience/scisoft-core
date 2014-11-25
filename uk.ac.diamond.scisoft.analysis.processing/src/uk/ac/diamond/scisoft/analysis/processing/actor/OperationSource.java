package uk.ac.diamond.scisoft.analysis.processing.actor;

import java.util.Queue;

import org.dawb.passerelle.common.DatasetConstants;
import org.dawb.passerelle.common.actors.AbstractDataMessageSource;
import org.dawb.passerelle.common.message.DataMessageException;
import org.eclipse.dawnsci.analysis.api.message.DataMessageComponent;
import org.eclipse.dawnsci.analysis.api.processing.IOperationContext;
import org.eclipse.dawnsci.analysis.api.slice.SliceInfo;
import org.eclipse.dawnsci.analysis.api.slice.Slicer;

import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

import com.isencia.passerelle.actor.InitializationException;
import com.isencia.passerelle.actor.ProcessingException;
import com.isencia.passerelle.core.ErrorCode;
import com.isencia.passerelle.core.PasserelleException;
import com.isencia.passerelle.message.ManagedMessage;
import com.isencia.passerelle.message.MessageException;
import com.isencia.passerelle.message.MessageFactory;

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
public class OperationSource extends AbstractDataMessageSource {

	private Queue<SliceInfo>  queue;
	private IOperationContext context;
	
	// a counter for indexing each generated message in the complete sequence that this source generates
	private long msgCounter;
	// a unique sequence identifier for each execution of this actor within a single parent workflow execution
	private long msgSequenceID;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1014225092138237014L;

	public OperationSource(CompositeEntity container, String name) throws NameDuplicationException, IllegalActionException {
		super(container, name);
		
		// TODO The first actor in the pipeline should throttle it...
		// this.receiverQueueCapacityParam.setToken(new IntToken(2));
		
		// TODO Methods so that this actor might be humanly editable?
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
    		if (!isTriggerConnected()) queue = Slicer.getSlices(context.getData(), context.getSlicing());
		} catch (Exception e) {
			throw new InitializationException(ErrorCode.FATAL, e.getMessage(), this, e);
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

		//ActorUtils.waitWhileLocked();
		
		final SliceInfo info = queue.poll();
		if (info==null) return null;
		
        ManagedMessage msg = MessageFactory.getInstance().createMessageInSequence(msgSequenceID, msgCounter++, hasNoMoreMessages(), getStandardMessageHeaders());
    
		try {
			msg.setBodyContent(getData(info), DatasetConstants.CONTENT_TYPE_DATA);
		} catch (MessageException e) {
			msg = MessageFactory.getInstance().createErrorMessage(new PasserelleException(ErrorCode.MSG_CONSTRUCTION_ERROR, "Cannot set map of data in message body!", this, e));
			queue.clear();
		} catch (Exception ne) {
			queue.clear();
			throw new DataMessageException("Cannot read data from '"+info.getSliceName()+"'", this, ne);
		}
			
		try {
			msg.setBodyHeader("TITLE", info.getSliceName());
		} catch (MessageException e) {
			msg = MessageFactory.getInstance().createErrorMessage(new PasserelleException(ErrorCode.MSG_CONSTRUCTION_ERROR, "Cannot set header in message!", this, e));
		}

		return msg;

	}

	private DataMessageComponent getData(SliceInfo info) {
		DataMessageComponent ret = new DataMessageComponent();
		ret.setList(info.slice());
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
	 */
	public void setContext(IOperationContext context) {
		this.context = context;
	}

	
	/**
	 * "callback"-method that can be overridden by TriggeredSource implementations,
	 * if they want to act e.g. on the contents of a received trigger message.
	 * 
	 * @param triggerMsg
	 */
	protected void acceptTriggerMessage(ManagedMessage triggerMsg) {
		try {
			queue = Slicer.getSlices(context.getData(), context.getSlicing());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
