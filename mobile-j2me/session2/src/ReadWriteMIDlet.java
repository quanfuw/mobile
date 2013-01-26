import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;

import data.SearchFilter;

public class ReadWriteMIDlet extends MIDlet implements CommandListener{
	private Display display;
	private Alert alert;
	private Form form; 
	private Command exit, start, cmExit, cmFind; 
	private RecordStore rs;

	private StringItem siMatch;
	private TextField tfFind;
	static final String REC_STORE = "db_1"; 
	public ReadWriteMIDlet(){
		display = Display.getDisplay(this);
		start = new Command("Start", Command.SCREEN, 1);
		exit = new Command("Exit", Command.SCREEN, 1);
		form = new Form("Record Search");
		form.addCommand(start);
		//form.addCommand(exit);		
		//form.setCommandListener(this);
		//display = Display.getDisplay(this); 	// Define textfield, stringItem and commands 	
		tfFind = new TextField("Find", "", 10, TextField.ANY); 	
		siMatch = new StringItem(null, null); 	
		cmExit = new Command("Exit", Command.EXIT, 1); 	
		cmFind = new Command("Find", Command.SCREEN, 2); 	// Create the form, add commands 
		form.addCommand(cmExit); 	
		form.addCommand(cmFind); 	// Append textfield and stringItem 	
		form.append(tfFind); 
		form.append(siMatch); 	// Capture events 	
		form.setCommandListener(this); 	// Open and write to record store 	
		openRecStore(); // Create the record store 	
		writeTestData(); // Write a series of records 	
	}


	private void db(String str) { 		
		System.err.println("Msg: " + str);  
	}
	public void openRecStore() { 		
		try { 		// Create record store if it does not exist 		    
			rs = RecordStore.openRecordStore(REC_STORE, true ); 		
		} 		catch (Exception e) { 		
			db(e.toString()); 		
		}  
	}



	public void startApp(){
		display.setCurrent(form);
	}

	public void pauseApp(){}

	public void destroyApp(boolean unconditional){
		notifyDestroyed();
	}

	public void commandAction(Command command, Displayable displayable){
		if(command == exit){
			destroyApp(true);
		}else if(command == cmFind) {
			searchRecordStore();

		} else if(command == start){
			try{
				rs = RecordStore.openRecordStore("Sandeep Kumar Suman", true );

				String outputData = "First Record Completed...";
				byte[] byteOutputData = outputData.getBytes();
				rs.addRecord(byteOutputData, 0, byteOutputData.length);

				byte[] byteInputData = new byte[1]; 
				int length = 0;
				for (int x = 1; x <= rs.getNumRecords(); x++){
					if (rs.getRecordSize(x) > byteInputData.length){
						byteInputData = new byte[rs.getRecordSize(x)];
					}
					length = rs.getRecord(x, byteInputData, 0);
				}
				alert = new Alert("Reading", new String(byteInputData, 0, length), null, AlertType.WARNING); 
				alert.setTimeout(Alert.FOREVER); 
				display.setCurrent(alert); 

				rs.closeRecordStore();
			}catch (Exception e){}

			if(RecordStore.listRecordStores() != null){
				try{
					RecordStore.deleteRecordStore("Sandeep Kumar Suman");
				}catch (Exception e){}
			}      
		}
	}




	public void writeTestData() {
		String[] names = {"Lan : Lop C04 CNTT HVCNBCVT", 
				"Thu : K45 CNTT Dai Hoc Bach Khoa HN", 
				"Hoai Anh : K39 QTDN Truong Kinh Te Quoc Dan",
		"Yen Chi : Lop Anh Ngu Truong Dai Hoc Ngoai Ngu HN"};
		boolean[] sex = {true, false, true, true};    
		int[] rank = {3, 0, 1, 2};
		writeStream(names, sex, rank); 
	}

	public void writeStream(String[] sData, boolean[] bData, int[] iData) {
		try {// Write data into an internal byte array
			ByteArrayOutputStream strmBytes = new ByteArrayOutputStream();
			DataOutputStream strmDataType = new DataOutputStream(strmBytes); 
			byte[] record;
			for (int i = 0; i < sData.length; i++) {    // Write Java data types 
				strmDataType.writeUTF(sData[i]);      strmDataType.writeBoolean(bData[i]);
				strmDataType.writeInt(iData[i]);          strmDataType.flush(); // Clear any buffered data
				record = strmBytes.toByteArray(); // Get stream data into byte array and write record
				rs.addRecord(record, 0, record.length);      
				strmBytes.reset();  }
			strmBytes.close();    strmDataType.close(); }
		catch (Exception e) {
			db(e.toString());  }  
	}


	private void searchRecordStore()  {
		try {    // Record store is not empty
			if (rs.getNumRecords() > 0) {// Setup the search filter with the user requested text
				SearchFilter search =  new SearchFilter(tfFind.getString());
				RecordEnumeration re = 
					rs.enumerateRecords(search, null, false);       
				if (re.numRecords() > 0)      // A match was found using the filter 
				{                  // Read from the specified byte array
					ByteArrayInputStream strmBytes =   new ByteArrayInputStream(re.nextRecord());
					DataInputStream strmDataType =  new DataInputStream(strmBytes); // Read Java data types from the above byte array
					siMatch.setText(strmDataType.readUTF()); // Show matching result in stringItem component on form
					search.searchFilterClose(); // Close record filter
					strmBytes.close();          // Close stream
					strmDataType.close();       // Close stream
					re.destroy();               // Free enumerator
				}   }   }
		catch (Exception e)  {
			db(e.toString());
		}
	}


}