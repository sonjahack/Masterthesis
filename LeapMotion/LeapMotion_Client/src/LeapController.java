import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics; 
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;


// class LeapListener is a subclass of class Listener 
// -> access to all attributes and methods of superclass
class LeapListener extends Listener
{
	Fenster fenster = new Fenster();
	//Server server = new Server();
	private double vol = 0;
	
	public void onInit(Controller controller)
	{
		System.out.println("Initialized");
	}
	
	public void onConnect(Controller controller)
	{
		System.out.println("Connected to Motion Sensor");
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}
	
	public void onDisconnect(Controller controller)
	{
		System.out.println("Motion Sensor disconnected");
	}
	
	public void onExit(Controller controller)
	{
		System.out.println("Exited");
	}
	
	public double provideStatus()
	{
			return vol;
	}
	
	public void onFrame(Controller controller)
	{
		//get Frame from Controller
		Frame frame = controller.frame();
		
		//float instantaneousFrameRate = frame.currentFramesPerSecond();
		//System.out.println(instantaneousFrameRate);
		/*
		System.out.println("Frame id: " + frame.id()
							+ ", Timestamp: " + frame.timestamp()
							+ ", Hands: " + frame.hands().count()
							+ ", Fingers " + frame.fingers().count()
							+ ", Tools " + frame.tools().count()
							+ ", Gestures: " + frame.gestures().count());	
		*/
		
		/*
		for(Hand hand : frame.hands())
		{
			String handType = hand.isLeft() ? "Left Hand" : "Right Hand";
			System.out.println(handType + "  " + "id: " + hand.id()
								+ ", Palm Position: " + hand.palmPosition());
			
			// normal vector is the vector that is orthogonal to palm position 
			// of the hand
			Vector normal = hand.palmNormal();
			// direction from palm to fingers
			Vector direction = hand.direction();
			
			System.out.println("Pitch: " + Math.toDegrees(direction.pitch())
								+ "Roll: " + Math.toDegrees(normal.roll())
								+ "Yaw: " + Math.toDegrees(direction.yaw()));	
		}
		*/
		
		/*
		for(Finger finger : frame.fingers())
		{
			System.out.println("Finger Type: " + finger.type()
								+ " Finger ID: " + finger.id()
								+ " Length (mm): " + finger.length()
								+ " Width (mm) " + finger.width());
			
			for(Bone.Type boneType : Bone.Type.values())
			{
				Bone bone = finger.bone(boneType);
				System.out.println("Bone Type: " + bone.type()
									+ " Start: " + bone.prevJoint()
									+ " End: " + bone.nextJoint()
									+ " Direction: " + bone.direction());
			}
		}
		*/
		
		/*
		for(Tool tool : frame.tools())
		{
			System.out.println("Tool ID: " + tool.id()
								+ " Tip position: " + tool.tipPosition()
								+ " Direction: " + tool.direction()
								+ " Width: " + tool.width()
								+ " Touch Distance (mm): " + tool.touchDistance());
		}
		*/
		
		GestureList gestures = frame.gestures();
		for(int i = 0; i < gestures.count(); i++)
		{
			Gesture gesture = gestures.get(i);
			
			switch (gesture.type()) 
			{
				// Circle Gesture detection has to be enabled (onConnect) and recognized here
				case TYPE_CIRCLE:
					// every type of gesture has it's own properties
					// so one has to create a new object for each gesture type
					// typecast to circle gesture
					CircleGesture circle = new CircleGesture(gesture);
					
					String clockwiseness;
					if(circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/4)
					{
						// angle less than 90 degrees
						clockwiseness = "clockwise";
						
						if(vol < 5000)
						{
							vol += 1;
							//vol += circle.progress();
						}
						else
						{
							vol = 5000;
						}
					}
					else
					{
						clockwiseness = "counter-clockwise";
						
						if (vol > 0)
						{
							vol -= 1;
							//vol -= circle.progress();
						}
						else
						{
							vol = 0;
						}
					}
					
					double sweptAngle = 0;
					if(circle.state() != State.STATE_START)
					{
						// circle hasn't just started
						CircleGesture previous = new CircleGesture(controller.frame(1).gesture(circle.id()));
						sweptAngle = (circle.progress() - previous.progress()) * 2 * Math.PI;
					}
					
					/*System.out.println("Circle ID: " + circle.id()
										+ " State: " + circle.state()
										+ " Progress: " + circle.progress()  
										//+ " Radius: " + circle.radius()
										//+ " Angle: " + Math.toDegrees(sweptAngle)
										+ " Direction: " + clockwiseness);*/
					
					vol = Math.round(vol * 100.0) / 100.0;
					//System.out.println("Volume: " + vol);
					
					fenster.getStatus(vol);
					//fenster.getStatus(circle.progress());
					break;
			default:
				break;
					
				// Swipe Gesture detection has to be enabled (onConnect) and recognized here	
				/*
				case TYPE_SWIPE:
					SwipeGesture swipe = new SwipeGesture(gesture);
					System.out.println(" Swipe ID: " + swipe.id()
										+ " State: " + swipe.state()
										+ " Position: " + swipe.position()
										+ " Direction: " +swipe.direction()
										+ " Speed: (mm/s) " + swipe.speed());
					break;
				*/
				
				/*
				case TYPE_SCREEN_TAP:
					ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
					System.out.println("Tap ID: " + screenTap.id()
										+ " State: " + screenTap.state()
										+ " Position: " + screenTap.position()
										+ " Direction: " + screenTap.direction()
										+ " Pointable: " + screenTap.pointable());
					break;
				*/
				
				/*
				case TYPE_KEY_TAP:
					KeyTapGesture keyTap = new KeyTapGesture(gesture);
					System.out.println("KeyTap id: " + keyTap.id()
										+ " Direction: " + keyTap.direction());
					break; 
				*/
			}
		}
	}
}

class Fenster extends JFrame
{
	private String circleStatus ="0";
	
	public Fenster()
	{
		super("Leap Motion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new MyPanel());
		pack();
		//setSize(400,200);
		//setLocation(100,100);
		setVisible(true);
	}
	
	private class MyPanel extends JPanel 
	{
		public MyPanel() 
		{
			setBackground(Color.white);
			setPreferredSize(new Dimension(600, 400));
		}
		
		protected void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRoundRect(60, 60, 480, 280, 60, 60);
			g.setColor(Color.white);
			g.setFont(new Font("Monospaced", Font.BOLD, 48));
			g.drawString(circleStatus, 250, 220);
			repaint();
		}
	}
	
	public void getStatus(double progress)
	{
		circleStatus = Double.toString(progress);
	}
}

public class LeapController {

	public static void main(String[] args) 
	{
		//if(args.length<1) return;
		LeapListener listener = new LeapListener();
		Controller controller = new Controller();
		int lmOutputLast = 0;
		double lmOutput = 0;
		int lmOutputInt = 0;
		controller.addListener(listener);
		
		String hostname = "192.168.137.28";
        int port = Integer.parseInt("2016");
        
		//System.out.println("Press Enter to quit");		
		try (Socket socket = new Socket(hostname, port))
		{
			OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
			
            while(true)
			{
			        lmOutput = (listener.provideStatus());
			        lmOutputInt = (int) lmOutput;
			        //System.out.println(lmOutput);
			        //System.out.println("Last " + lmOutputLast);
			        
			        //if((lmOutputLast < lmOutputInt)|| (lmOutputLast> lmOutputInt))
			        //{
			        if(lmOutputLast == lmOutputInt)
			        {
			        	System.out.println("Same");	
			        }
			        else
			        {
			        	int lmOutputDelta = (lmOutputInt - lmOutputLast);
			        	lmOutputLast = lmOutputInt;
			        	writer.println("enable;");
				        writer.println("set:target " + lmOutputDelta + ";");
				        System.out.println(lmOutputInt);
				        //writer.println(new Date().toString());
			        }
			        //}
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
		controller.removeListener(listener);
	}
}

