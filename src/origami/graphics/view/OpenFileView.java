package origami.graphics.view;

import java.util.Vector;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import origami.administration.CustomFile;
import origami.administration.functionality.DiagramFileManager;
import origami.analysis.SerializeFormating;
import origami.analysis.TimeStampVector;
import origami.analysis.reporting.FileReport;
import origami.graphics.MainWindow;

public class OpenFileView {

    private OpenType openType;
    private String nameOfFile;

   // private static DiagramFileManager _serializer = new DiagramFileManager();

    public void createWindow() {
		FileDialog dialog = new FileDialog(MainWindow.shell, SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.Org", "*.*" });
		switch (openType) {
		case OPENEXAMPLE:
		    dialog.setFilterPath("ejemplos\\");
		    break;
		default:
		    break;
		}
		String fileURL = dialog.open();
		if (fileURL != null) {
			nameOfFile = dialog.getFileName();
		    openNewFile(nameOfFile, fileURL);
		}
    }

    public void openNewFile(String fileName, String address) {
	switch (openType) {
	case OPEN:	    
	    DiagramFileManager test = new DiagramFileManager();
	    CustomFile customFile = test.recoverDiagram(address);
	    Vector<String> vector = customFile.getInfo();
	    
	    //System.out.println(new SerializeFormating(vector).formingInfoVector().toString());
	    
	    
	    MainWindow.console.setText("");
	    MainWindow.shell.setText(nameOfFile);
	    
	    MainWindow.console.append(nameOfFile+ "\n");
	    //using plain text, as Serialize object is
	    //MainWindow.console.append(new SerializeFormating(vector).formatingPlainInfo().toString());
	    //System.out.println(new SerializeFormating(vector).formatingPlainInfo().toString());
	    //using formingInfoVector()
	    TimeStampVector tsv =new SerializeFormating(vector).formingInfoVector();
	    FileReport fr = new FileReport();
	    TimeStampVector report =fr.reportWithoutFullSave(tsv);
	    
	    report.standarizedTimeStampVector();
	    tsv.standarizedTimeStampVector();
	    MainWindow.console.append(report.toString());
	    
	    //System.out.println (tsv.toString());
	    System.out.println (fr.getActivityStats(report));
	    System.out.println ("Activity: " + report.size());
	    //System.out.println ("Solving time: " + fr.getSolvingTime(tsv));
	    System.out.println ("Solving time: " + fr.getSolvingTime(report));
	    
	    
	    System.out.println ( "Time - Interrumptions\tInterrumption Time\tNo. Interrumpt.\tActivity" );
	    System.out.println (fr.getSolvingTimeWithoutInterruptions(report)+"\t"+
                		fr.getInterrumptionTime() + "\t"+
                		fr.getInterrumptions() + "\t"+
                		report.size());
	    
	    System.out.println(fr.getActivityStatsByType(report));
	    //System.out.println (fr.getSolvingTime(tsv));
	    //System.out.println (fr.reportExcel(tsv));
	    //System.out.println (fr.reportExcel(report));
	    
	    //System.out.println (report.hashCode() + " "+ tsv.hashCode());
	    
	    
	    break;
	
	default:
	    break;
		}
    }

    public void setOpenType(OpenType openType) {
	this.openType = openType;
    }

    public OpenType getOpenType() {
	return openType;
    }
}
