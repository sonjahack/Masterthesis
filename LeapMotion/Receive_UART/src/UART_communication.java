// https://github.com/muzkat/serial-communication-java/blob/master/src/FirstSteps.java

import gnu.io.*; // RXTX
import java.util.Enumeration;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UART_communication {

	public static void main(String[] args) {
		System.out.println("Program started");
		
		//System.out.println(java.library.path);
	    CommPortIdentifier serialPortId;
	    //static CommPortIdentifier sSerialPortId;
	    Enumeration enumComm;
	    //SerialPort serialPort;

	    enumComm = CommPortIdentifier.getPortIdentifiers();
	    while (enumComm.hasMoreElements()) {
	     	serialPortId = (CommPortIdentifier) enumComm.nextElement();
	     	if(serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	    		System.out.println(serialPortId.getName());
	    	}
	     	//if(serialPortId.getName().equals("COM8"))
	     	//{
	     	//	System.out.println("found");
	     	//}
	    }

		System.out.println("Finished successfully");
	}
}