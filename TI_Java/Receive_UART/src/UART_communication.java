// https://github.com/muzkat/serial-communication-java/blob/master/src/FirstSteps.java
// https://en.wikibooks.org/wiki/Serial_Programming/Serial_Java

import gnu.io.*; // RXTX
import java.util.Enumeration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

public class UART_communication {

	public static void main(String[] args) throws IOException, UnsupportedCommOperationException {
		System.out.println("Program started");
		
		//System.out.println(java.library.path);
	    CommPortIdentifier serialPortId;
	    //static CommPortIdentifier sSerialPortId;
	    Enumeration portIdentifier;
	    //SerialPort serialPort;

	    portIdentifier = CommPortIdentifier.getPortIdentifiers();
	    
	    CommPortIdentifier portID = null; //will only be set, if port is found
	    //String[] portIDs = {};
	    //CommPortIdentifier[] portIDs = {};
	    //int counter = 0;
	    
	    while (portIdentifier.hasMoreElements()) 
	    {
	     	serialPortId = (CommPortIdentifier) portIdentifier.nextElement();
	     	if(serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL)
	     	{
	    		System.out.println(serialPortId.getName());
	    		portID = serialPortId;
	    		//portIDs[counter] = portID.getName();
	    		//counter++;
	    	}
	     	//if(serialPortId.getName().equals("COM8"))
	     	//{
	     	//	System.out.println("found");
	     	//}
	    }
	    
	    if (portID == null)
	    {
	    	System.err.println("Could not find any serial ports");
	    }
	    else
	    {
	    	Scanner scan = new Scanner(System.in);
		    System.out.print("Please enter port name that should be opened: ");
		    String selectedPortName = scan.nextLine();
		    //System.out.println(selectedPortName);
		    
		    SerialPort port = null;
		    try
		    {
		    	port = (SerialPort)portID.open("name",1000);
		    	System.out.println("Selected Port opened");
//		    	
		    	port.setSerialPortParams(
		    							921600, 
		    							SerialPort.DATABITS_8,
		    							SerialPort.STOPBITS_1,
		    							SerialPort.PARITY_NONE);
		    	BufferedReader is = null;
		    	PrintStream os = null;
		    	
		    	try {
		    		is = new BufferedReader(new InputStreamReader(port.getInputStream()));
		    		while(true)
		    		{
		    			String response = is.readLine();
		    			System.out.println(response);
		    		}
		    	}
		    	catch(IOException e)
		    	{
		    		System.err.println("Can't open input stream " + e);
		    		is = null;
		    	}
//		    	for(int i = 0; i < portIDs.length; i++)
//		    	{
//		    		if(portID.getName() == selectedPortName)
//		    		{
//		    			port = (SerialPort)portID.open("name",1000);
//		    			System.out.println("Selected Port opened");
//		    		}
//		    	}
		    }
		    
		    catch(PortInUseException e)
		    {
		    	System.err.println("Port already in use " + e);
		    	System.exit(1);
		    }
	    }
//	    if(is != null) is.close();
//	    if(os != null) os.close();
//	    if(port != null) port.close();
	    
		System.out.println("Finished successfully");
		System.exit(1);
	}
}