package org.dawnsci.surfacescatter;



import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
 
public class FileViewer2 extends Composite {
 
	Text mText;
	Button mButton;
	String title = null;
 
	public FileViewer2(Composite parent) {
		super(parent, SWT.NULL);
		createContent();
	}
 
	public void createContent() {
		GridLayout layout = new GridLayout(2, false);
		setLayout(layout);
 
		mText = new Text(this, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		gd.minimumWidth = 100;
		gd.minimumHeight = 100;
		mText.setLayoutData(gd);
 
 
		mButton = new Button(this, SWT.NONE);
		mButton.setText("...");
		mButton.setLayoutData(gd);
		mButton.addSelectionListener(new SelectionListener() {
 
			public void widgetDefaultSelected(SelectionEvent e) {
			}
 
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(mButton.getShell(),  SWT.OPEN  );
				dlg.setText("Open");
				String path = dlg.open();
				if (path == null) return;
				mText.setText(path);
			}
		});
	}
 
	public String getText() {
		return mText.getText();
 
	}
 
	public Text getTextControl() {
		return mText;		
	}
 
	public File getFile() {
		String text = mText.getText();
		if (text.length() == 0) return null;
		return new File(text);
	}
 
	public String getTitle() {
		return title;
	}
 
	public void setTitle(String title) {
		this.title = title;
	}
}
//
//
//public void createPartControl(Composite parent) {
//		// Here is the layered layout of the Composite
//		// parent -> top -> banner
//		// -> text
//		Composite top = new Composite(parent, SWT.NONE);// embedded Composite
// 
//		// setup the layout of top to be GridLayout.
//		GridLayout layout = new GridLayout();
//		layout.marginHeight = 0;
//		layout.marginWidth = 0;
//		top.setLayout(layout);
// 
//		// top banner
//		Composite banner = new Composite(top, SWT.NONE);// banner is added to
//														// "top"
//		banner.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL,
//				GridData.VERTICAL_ALIGN_BEGINNING, true, false));
//		layout = new GridLayout();
//		layout.marginHeight = 5;
//		layout.marginWidth = 10;
// 
//		layout.numColumns = 2;
//		banner.setLayout(layout);
// 
//		// setup bold font
//		Font boldFont = JFaceResources.getFontRegistry().getBold(
//				JFaceResources.DEFAULT_FONT);
// 
//		// 1st row
//		Label l = new Label(banner, SWT.WRAP);
//		l.setText("Regular Expression:");
//		l.setFont(boldFont);
// 
//		final Text reg = new Text(banner, SWT.BORDER | SWT.FILL);
//		GridData gridData = new GridData();
//		gridData.horizontalAlignment = SWT.FILL;
//		gridData.minimumWidth = 400;
//		gridData.minimumHeight = 50;
//		gridData.grabExcessHorizontalSpace = true;
//		reg.setLayoutData(gridData);
//		reg.setText("(\\s*)//(\\s*).*");
// 
//		// l = new Label(banner, SWT.WRAP);
//		// l.setText("This is a message about the cool Eclipse RCP!");
// 
//		// 2nd row
//		l = new Label(banner, SWT.PUSH);
//		l.setText("Author:");
//		l.setFont(boldFont);
// 
//		final Link link = new Link(banner, SWT.NONE);
//		link.setText("<a>programcreek.com</a>");
//		link.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				MessageDialog
//						.openInformation(getSite().getShell(),
//								"Not Implemented",
//								"Imagine the address book or a new message being created now.");
//			}
//		});
// 
//		// 3rd row
//		l = new Label(banner, SWT.WRAP);
//		l.setText("Source File:");
//		l.setFont(boldFont);
// 
// 
//		final FileViewer2 fileChooser = new FileViewer2(banner);
//		gridData.heightHint = 25;
//		fileChooser.setLayoutData(gridData);
//		//fileChooser.setLayout(SWT.WRAP);
// 
// 
//		// 4th row
//		l = new Label(banner, SWT.WRAP);
//		l.setText("Source File:");
//		l.setFont(boldFont);
// 
//		Button runButton = new Button(banner, SWT.WRAP);
//		runButton.setText("Select a File");
// 
// 
// 
//		// message contents
//		final Text text = new Text(top, SWT.MULTI | SWT.WRAP);
//		// here like the banner, text is added to "top".
//		text.setText("");
//		text.setLayoutData(new GridData(GridData.FILL_BOTH));
// 
// 
//		runButton.addListener(SWT.Selection,  new Listener() {
//		      public void handleEvent(Event event) {
// 
//		    	  ArrayList<String> list = null;
//					try {
//						list  = FilterText.Filter(fileChooser.getText(), reg.getText());
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
// 
//					for(String temp : list){
// 
//						text.append(temp + "\n");
// 
//					}
// 
//		        }
//		      });
// 
//	}
