
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class NetworkInput {

	public static void main(String[] args) 
	{
		String hostname = "127.0.0.1";
        int port = Integer.parseInt("2016");
        try (Socket socket = new Socket(hostname, port))
		{
			OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            System.out.println("Connected with network");
            writer.println("Leap Motion Controller connected");
			
            while(true)
			{
        		@SuppressWarnings("resource")
				Scanner scan = new Scanner(System.in);
        	    System.out.print("Please enter data that should be send: ");
        	    String Data = scan.nextLine();
                writer.println(Data);    
			}	
		}
		
		catch (UnknownHostException ex) 
		{			 
            System.out.println("Server not found: " + ex.getMessage());
        }
		catch(IOException e)
		{
			System.out.println("Server exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
