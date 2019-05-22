//https://www.java-samples.com/showtutorial.php?tutorialid=11

import java.io.*;
import java.util.*;
import gnu.io.*;
import java.net.*;

public class SimpleRead implements Runnable, SerialPortEventListener 
{
    static CommPortIdentifier portId;
    static Enumeration portList;
    
    public static String sensor_output;

    InputStream inputStream;
    SerialPort serialPort;
    Thread readThread;

    public static void main(String[] args) throws UnknownHostException, IOException 
    {
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) 
        {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) 
            {
                 if (portId.getName().equals("COM8")) 
                 {
			//                if (portId.getName().equals("/dev/term/a")) {
                    SimpleRead reader = new SimpleRead();
            		
                    String hostname = "192.168.137.30";
            	    int port = Integer.parseInt("2016");
            	    
            	    Socket socket = new Socket(hostname, port);
            	    OutputStream output = socket.getOutputStream();
            	    PrintWriter writer = new PrintWriter(output, true);
            	    writer.println("Radar Sensor connected");            	    	
                 }
            }
        }
    }

    public SimpleRead() throws UnknownHostException, IOException 
    {
    	//Network_connection network_connect = new Network_connection();    	
    	/*
    	int connected_to_server =  network_connection();
    	if (connected_to_server == 1 )
    	{
    		System.out.println("Connected to server");
    	}
    	else
    	{
    		System.out.println("Not connected to server");
    	}
    	*/
        try 
        {
            serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
        } 
        catch (PortInUseException e) 
        {
        	System.out.println(e);
        }
        try 
        {
            inputStream = serialPort.getInputStream();
        } 
        catch (IOException e) 
        {
        	System.out.println(e);
        }
	try 
	{
		serialPort.addEventListener(this);
	} 
	catch (TooManyListenersException e) 
	{
		System.out.println(e);
	}
        serialPort.notifyOnDataAvailable(true);
        try 
        {
            serialPort.setSerialPortParams(921600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        } 
        catch (UnsupportedCommOperationException e) 
        {
        	System.out.println(e);
        }
        readThread = new Thread(this);
        readThread.start();
    }

    public void run() 
    {
        try 
        {
            Thread.sleep(20000);
        } 
        catch (InterruptedException e) 
        {
        	System.out.println(e);
        }
    }

    public void serialEvent(SerialPortEvent event) 
    {	    
        switch(event.getEventType()) 
        {
	        case SerialPortEvent.BI:
	        case SerialPortEvent.OE:
	        case SerialPortEvent.FE:
	        case SerialPortEvent.PE:
	        case SerialPortEvent.CD:
	        case SerialPortEvent.CTS:
	        case SerialPortEvent.DSR:
	        case SerialPortEvent.RI:
	        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
	            break;
	        case SerialPortEvent.DATA_AVAILABLE:
	            byte[] readBuffer = new byte[20];
	
	            try 
	            {
	                while (inputStream.available() > 0) 
	                {
	                    int numBytes = inputStream.read(readBuffer);
	                }
	                System.out.println(new String(readBuffer));
	                provide_sensor_output(new String(readBuffer));
	            } 
	            catch (IOException e) 
	            {
	            	System.out.println(e);
	            }
	            break;
        }
    }
    
    public int provide_sensor_output(String output)
    {
    	System.out.println("Function called " + output);
    	if(output == "R")
    	{
    		//System.out.println("Function called; 1");
    		return 1;
    	}
    	else if(output == "U")
    	{
    		//System.out.println("Function called; 2");
    		return 2;
    	}
    	else
    	{
    		//System.out.println("Function called; 0");
    		return 0;
    	}
    }
}

