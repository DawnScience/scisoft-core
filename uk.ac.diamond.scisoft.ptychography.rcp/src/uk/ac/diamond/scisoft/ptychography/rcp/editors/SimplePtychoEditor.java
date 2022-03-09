package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleConstants;
import org.python.pydev.shared_interactive_console.console.ui.internal.actions.CloseScriptConsoleAction;

import uk.ac.diamond.scisoft.ptychography.rcp.Activator;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoNode;
import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoTreeUtils;
import uk.ac.diamond.scisoft.ptychography.rcp.preference.PtychoPreferenceConstants;

public class SimplePtychoEditor extends AbstractPtychoEditor {

	public static final String ID = "uk.ac.diamond.scisoft.ptychography.rcp.basicPtychoInput";

	private List<TextNode> textNodes = new ArrayList<TextNode>();
	private Composite parent, container;
	
	@Inject
	@Optional
	private void refreshView(@UIEventTopic("refreshSimplePtychoEditor")List<PtychoNode> tree) {
		this.tree = tree;
		container.dispose();
		initialise();
	}
	
	@PostConstruct
	public void createPartControl(Composite parent) {
		this.parent = parent;
		initialise();
	}
	
	private void initialise() {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite subComp = new Composite(container, SWT.NONE);
		subComp.setLayout(new GridLayout(1, false));
		subComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createLabelAndText(subComp, "path");
		
		SelectionAdapter scriptSelector = new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button source = (Button) e.getSource();
				setDefaultScript(source.getText());
				
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IViewPart part = page.findView(IConsoleConstants.ID_CONSOLE_VIEW);
				IContributionItem[] items = part.getViewSite().getActionBars().getToolBarManager().getItems();

				int count = 0;
				
				for(IContributionItem c : items) {
					if(c instanceof SubContributionItem) {
						IContributionItem subItem = ((SubContributionItem) c).getInnerItem();
						if(subItem instanceof ActionContributionItem && ((ActionContributionItem) subItem).getAction() instanceof CloseScriptConsoleAction) {
							if(source.getText().startsWith(ALTERNATE_SCRIPT))
								items[count].setVisible(false);
							else 
								items[count].setVisible(true);
							part.getViewSite().getActionBars().updateActionBars();
							break;
						}
					}
					count++;
				}
			}
		};
		
		Group scriptSelect = new Group(subComp, SWT.NONE);
		scriptSelect.setLayout(new RowLayout(SWT.VERTICAL));
		scriptSelect.setText("Select Script");
		final Button reconScript = new Button(scriptSelect, SWT.RADIO);
		reconScript.setText(RECON_SCRIPT + ": " + Activator.getPtychoPreferenceStore().getString(PtychoPreferenceConstants.RECON_SCRIPT_PATH));
		reconScript.addSelectionListener(scriptSelector);
		final Button alternateScript = new Button(scriptSelect, SWT.RADIO);
		alternateScript.setText(ALTERNATE_SCRIPT + ": " + Activator.getPtychoPreferenceStore().getString(PtychoPreferenceConstants.ALTERNATE_SCRIPT_PATH));
		alternateScript.addSelectionListener(scriptSelector);
		
		
		IPropertyChangeListener propertyListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if(event.getProperty() == PtychoPreferenceConstants.ALTERNATE_SCRIPT_PATH) {
					alternateScript.setText(ALTERNATE_SCRIPT + ": " + event.getNewValue());
				} else if(event.getProperty() == PtychoPreferenceConstants.RECON_SCRIPT_PATH) {
					reconScript.setText(RECON_SCRIPT + ": " + event.getNewValue());
				}
			}
		};
		Activator.getPtychoPreferenceStore().addPropertyChangeListener(propertyListener);

		createPythonRunCommand(container);
	}

	private void createLabelAndText(Composite parent, String name) {
		if(tree == null)
			return;
		List<PtychoNode> result = new ArrayList<PtychoNode>();
		for (PtychoNode node : tree) {
			List<PtychoNode> nodes = PtychoTreeUtils.findNodeWithName(node, name);
			if (nodes != null)
				for (PtychoNode res : nodes) {
					result.add(res);
				}
		}
		int i = 2;
		for (final PtychoNode res : result) {
			if (res != null) {
				String parentName = "";
				PtychoNode par = res.getParent();
				parentName = par != null ? par.getData().getName() : "";
				Label label = new Label(parent, SWT.NONE);
//				String lab = PtychoTreeUtils.getTreePath(tree.get(i), res);
				if (i == 2)
					label.setText("Experiment/" + parentName + "/" + name + ":");
				if (i == 3)
					label.setText("Process/common/" + parentName + "/" + name + ":");

				final Text text = new Text(parent, SWT.BORDER);
				text.setText(res.getData().getDefaultValue());
				text.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
				text.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent e) {
						res.getData().setDefaultValue(text.getText());
					}
				});
				textNodes.add(new TextNode(text, res));
				i++;
			}
		}
		if(result.isEmpty()) {
			Label label = new Label(parent, SWT.NONE);
			label.setText("The simple Ptycho Editor is currently only supported for the default template file. \n"
					+ "This can be found at /dls_sw/apps/ePi/epi_parameters_descriptions.csv. \n"
					+ "Please use the Ptycho Tree Editor for the currently selected file.");
		}
	}

	public void refresh() {
		for(TextNode textNode : textNodes) {
			PtychoNode node = textNode.getNode();
			Text text = textNode.getText();
			text.setText(node.getData().getDefaultValue());
		}
	}

	class TextNode {
		private Text text;
		private PtychoNode node;

		public TextNode(Text text, PtychoNode node) {
			this.text = text;
			this.node = node;
		}

		public Text getText() {
			return text;
		}

		public void setText(Text text) {
			this.text = text;
		}

		public PtychoNode getNode() {
			return node;
		}

		public void setNode(PtychoNode node) {
			this.node = node;
		}
	}
}
