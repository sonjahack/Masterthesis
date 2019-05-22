// https://www.mikrocontroller.net/articles/Serielle_Schnittstelle_unter_Java#RXTX

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
//import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

//import OeffnenUndSenden.serialPortEventListener;



public class UART_Input implements Runnable 
{
	public static void main(String[] args)
	{
		Runnable runnable = new UART_Input();
		new Thread(runnable).start();
	}

	CommPortIdentifier serialPortId;
	Enumeration enumComm;
	SerialPort serialPort;
	InputStream inputStream;
	Boolean serialPortOpened = false;

	int baudrate = 921600;
	int dataBits = SerialPort.DATABITS_8;
	int stopBits = SerialPort.STOPBITS_1;
	int parity = SerialPort.PARITY_NONE;
	String portName = "COM8";
	
	int secondsRuntime = 60;

	public UART_Input()
	{
		//System.out.println("Konstruktor: EinfachSenden");
	}
	
    public void run()
    {
        if (open_SerialPort(portName) != true)
        	return;
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) { }
    	
    }
    
	boolean open_SerialPort(String portName)
	{
		Boolean foundPort = false;
		if (serialPortOpened != false) {
			System.out.println("Serialport already open");
			return false;
		}
		//System.out.println("Open serial port");
		enumComm = CommPortIdentifier.getPortIdentifiers();
		while(enumComm.hasMoreElements()) {
			serialPortId = (CommPortIdentifier) enumComm.nextElement();
			if (portName.contentEquals(serialPortId.getName())) {
				foundPort = true;
				break;
			}
		}
		if (foundPort != true) {
			System.out.println("Serialport nicht gefunden: " + portName);
			return false;
		}
		try {
			serialPort = (SerialPort) serialPortId.open("Öffnen und Senden", 2000);
		} catch (PortInUseException e) {
			System.out.println("Port belegt");
		}
/*
		try {
			outputStream = serialPort.getOutputStream();
		} catch (IOException e) {
			System.out.println("Keinen Zugriff auf OutputStream");
		}
*/
		try {
			inputStream = serialPort.getInputStream();
		} catch (IOException e) {
			System.out.println(e);
		}
		try {
			serialPort.addEventListener(new serialPortEventListener());
		} catch (TooManyListenersException e) {
			System.out.println("TooManyListenersException für Serialport");
		}
		serialPort.notifyOnDataAvailable(true);
		try {
			serialPort.setSerialPortParams(baudrate, dataBits, stopBits, parity);
		} catch(UnsupportedCommOperationException e) {
			System.out.println("Konnte Schnittstellen-Paramter nicht setzen");
		}
		
		serialPortOpened = true;
		return true;
	}

//	void schliesseSerialPort()
//	{
//		if ( serialPortOpened == true) {
//			System.out.println("Schließe Serialport");
//			serialPort.close();
//			serialPortOpened = false;
//		} else {
//			System.out.println("Serialport bereits geschlossen");
//		}
//	}
	
	void serialPortDataAvailable() throws UnknownHostException, IOException {
		//Network_connection network_connect = new Network_connection();
		try {
			byte[] data = new byte[150];
			int num;
			while(inputStream.available() > 0) {
				num = inputStream.read(data, 0, data.length);
				System.out.println("Data: "+ new String(data, 0, num));
				//network_connect.send_data_to_server(new String(data, 0, num));
			}
		} catch (IOException e) {
			System.out.println("Fehler beim Lesen empfangener Daten");
		}
	}

	class serialPortEventListener implements SerialPortEventListener 
	{
		public void serialEvent(SerialPortEvent event) {
			System.out.println("serialPortEventlistener");
			switch (event.getEventType()) 
			{
				case SerialPortEvent.DATA_AVAILABLE:
				try {
					serialPortDataAvailable();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					break;
				case SerialPortEvent.BI:
				case SerialPortEvent.CD:
				case SerialPortEvent.CTS:
				case SerialPortEvent.DSR:
				case SerialPortEvent.FE:
				case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
				case SerialPortEvent.PE:
				case SerialPortEvent.RI:
				default:
			}
		}
	}
	
	class Network_connection
	{
		public Network_connection() throws UnknownHostException, IOException
		{
			String hostname = "192.168.137.30";
		    int port = Integer.parseInt("2016");
		    
		    Socket socket = new Socket(hostname, port);
		    OutputStream output = socket.getOutputStream();
		    PrintWriter writer = new PrintWriter(output, true);
		    writer.println("Radar Sensor connected");
		}
		
		public void send_data_to_server(String message) throws UnknownHostException, IOException
		{
			String hostname = "192.168.137.30";
		    int port = Integer.parseInt("2016");
			Socket socket = new Socket(hostname, port);
		    OutputStream output = socket.getOutputStream();
		    PrintWriter writer = new PrintWriter(output, true);
		    writer.println(message);
		}	
	}
}

 