package origami.graphics;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import origami.administration.CustomFile;
import origami.administration.OrigamiException;
import origami.administration.functionality.DiagramFileManager;
import origami.graphics.listeners.OpenFileListener;

public class MainWindow {

    public static Display display;

    public static Shell shell;
    
    public static Text console;

	public MainWindow() {
		display = new Display();
		shell = new Shell(display);
		shell.setSize(700, 900);
		addComponents();
    }
    
    private void addComponents(){
    	//add Menubar
    	Menu mainMenu = new Menu(shell, SWT.BAR);
    	Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
    	
    	MenuItem fileMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
    	fileMenuItem.setText("Archivo ");
    	fileMenuItem.setMenu(fileMenu);
    	
    	MenuItem openMenuItem = new MenuItem(fileMenu, SWT.PUSH);
    	openMenuItem.setText("Abrir...                      Ctrl+A");
    	openMenuItem.addSelectionListener(new OpenFileListener());
    	
    	shell.setMenuBar(mainMenu);
    	
    	
    	//addConsole
    	console = new Text(shell, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
    	console.setBounds(0,0,600,800);
    	console.forceFocus();
    	
    }

    private void show() {
    	shell.open();
    }


    public Text getConsole() {
		return console;
	}

	public void setConsole(Text console) {
		this.console = console;
	}
	
    public static void main(String args[]) throws OrigamiException {
	try {
	    MainWindow mainWindowAnalysis = new MainWindow();
	    mainWindowAnalysis.show();
	    
	    while (!shell.isDisposed()) {
		while (!display.readAndDispatch()) {
		    display.sleep();
		}
	    }
	} catch (Exception e) {
	    throw new OrigamiException(e);
	}
    }
}
