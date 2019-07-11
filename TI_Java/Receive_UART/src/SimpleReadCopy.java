//https://www.java-samples.com/showtutorial.php?tutorialid=11

import java.io.*;
import java.util.*;
import gnu.io.*;
import java.net.*;

public class SimpleReadCopy implements Runnable, SerialPortEventListener 
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
                    SimpleReadCopy reader = new SimpleReadCopy();
                    Network_connect network_c = new Network_connect(reader);            	    	
                 }
            }
        }
    }

    public SimpleReadCopy() throws UnknownHostException, IOException 
    {
        try 
        {
            serialPort = (SerialPort) portId.open("SimpleReadCopyApp", 2000);
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
    	String sensor_Data = "";
    	String sensorData = "";
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
	                
	                //System.out.println(new String(readBuffer));
	                
	                sensor_Data = new String(readBuffer);
	                //sensor_output = "";
	                //sensor_output += sensor_Data.charAt(0);
	                set_SensorData(new String(readBuffer));
	                //sensor_output = null;
	            } 
	            catch (IOException e) 
	            {
	            	System.out.println(e);
	            }
	            break;
        }
    }
    
    public void set_SensorData(String data)
    {
    	sensor_output = data;
    }
    
    public String provide_SensorData()
    {
    	return sensor_output;
    }
    
    public void resetSensorData()
    {
    	sensor_output = null;
    }
}

class Network_connect extends Thread
{
	String sensor_output = "";
	SimpleReadCopy si;
	Socket socket;
	OutputStream output;
	PrintWriter writer;
	
	public Network_connect(SimpleReadCopy simple) throws UnknownHostException, IOException 
	{	    
		//sensor_output = simple.provide_SensorData();
		si = simple;
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run()
	{
		String hostname = "192.168.137.92";
	    int port = Integer.parseInt("2016");
	    String r = "R";
	    String u = "U";
	    String c = "C";
	    String l = "L";
	    String sensor_output_cut = null;
	    
	    try {
			socket = new Socket(hostname, port);
			System.out.println("Connected to Server");
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			output = socket.getOutputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    writer = new PrintWriter(output, true);
	    writer.println("Radar Sensor connected");
	    
		while(true)
		{
			sensor_output = si.provide_SensorData();
			//System.out.println("Sensor Output: " + sensor_output + ".");
			//System.out.println("Sensor Output Cut " + sensor_output_cut +".");
			
			if(sensor_output != null)
			{
				sensor_output_cut = "";
				sensor_output_cut += sensor_output.charAt(0);
				//System.out.println("check " + sensor_output_cut);
				sensor_output = null;
			}
			else
			{
				sensor_output_cut = "";
			}
			//System.out.println("check " + sensor_output_cut + ".");
			if(sensor_output_cut.contentEquals(c))
			{
				//System.out.println("check");
				writer.println("enable; set:target 1;");
				System.out.println("+1");
				//writer.println(sensor_output_cut);
				sensor_output_cut = null;
				si.resetSensorData();
			}
			else if(sensor_output_cut.contentEquals(l))
			{
				writer.println("enable; set:target -1;");
				System.out.println("-1");
				//writer.println(sensor_output_cut);
				sensor_output_cut = null;
				si.resetSensorData();
			}
			else
			{
				//System.out.println("No Gesture");
				sensor_output_cut = null;
			}
			try
			{
				sleep(200);
			}
			catch(InterruptedException e)
			{
				System.out.println(e);
			}
		}
	}	
}


