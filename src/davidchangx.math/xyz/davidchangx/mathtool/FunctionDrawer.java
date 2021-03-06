package xyz.davidchangx.mathtool;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.DoubleUnaryOperator;
import java.awt.geom.Line2D;
import java.awt.BasicStroke;
import java.util.HashMap;
import xyz.davidchangx.algorithms.math.Expression;
import xyz.davidchangx.algorithms.math.operator.*;
import java.util.Map;
import java.util.Scanner;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.lang.reflect.InvocationTargetException;
import java.io.File;
import java.io.FileNotFoundException;
import xyz.davidchangx.mathtool.OperatorMapGenerator;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
/**
 * Simple application to draw the plot of a function.
 *
 * All method considers up as the positive direction of y-axis except static render method. 
 *
 * @version 6.0
 * @author David Chang
 */
public class FunctionDrawer extends JFrame
{
	private DoubleUnaryOperator function;
	private double xScale,yScale;
	private double dx;
	private double xCenter,yCenter;
	/**
	 * Constructs a {@code FunctionDrawer} with the required parameters. 
	 * 
	 * This function drawer requires these parameters: 
	 *
	 * <ul>
	 * <li>The function graph of which is to be rendered. </li>
	 * <li>The size (width and height) of the frame. </li>
	 * <li>The coordinate in the logical coordinate system of the center point of the frame. </li>
	 * <li>The horizontal and vertical scale. Scale is how long in logical coordinate system a pixel represents. </li>
	 * <li>The horizontal step. </li>
	 *
	 * The numeric parameters should be positive. If invalid parameter is offered, this method will choose a proper valid parameter. 
	 *
	 * @param function the function graph of which is to be rendered
	 * @param width the width of frame
	 * @param height the height of frame
	 * @param xCenter the horizontal coordinate of the center point of the frame
	 * @param yCenter the vertical coordinate of the center point of the frame
	 * @param xScale the horizontal scale
	 * @param yScale the vertical scale
	 * @param dx the horizontal step
	 */
	public FunctionDrawer(DoubleUnaryOperator function,int width,int height,double xCenter,double yCenter,double xScale,double yScale,double dx) //constructed parameters: width, height of frame; the coordinate of center point; horizontal and vertical scale; increment of x while plotting
	{
		this.function = function;
		if(width<=0||height<=0)
		{
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			width = width>0?width:screenSize.width/3;
			height = height>0?height:screenSize.height/3;
		}
		double minSize = (double)Math.min(width,height);
		this.xScale = xScale>0.?xScale:minSize/12.;
		this.yScale = yScale>0.?yScale:minSize/12.;
		this.dx = dx>0.?dx:0.05;
		this.xCenter = xCenter;
		this.yCenter = yCenter;

		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		this.setSize(width,height);
		this.setVisible(true);
	}
	/**
	 * Paint the graph of the given function. 
	 * 
	 * @param g the target graph context
	 */
	@Override
	public void paint(Graphics g)
	{
		//super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform originalTrans = g2.getTransform();
		g2.transform(new AffineTransform(1.,0.,0.,1.,0.,0.));
		render(g2,this.function,this.getWidth(),this.getHeight(),this.xCenter,this.yCenter,this.xScale,this.yScale,this.dx);
		g2.setTransform(originalTrans);
	}
	/**
	 * Clear the frame and render the new function. 
	 *
	 * After invoking this method, the previous function graph will be cleared and the graph of new function will be rendered. 
	 *
	 * @param function the new function to be rendered
	 */
	public void setFunction(DoubleUnaryOperator function) //clear the frame and render the new function
	{
		this.function = function;
		Graphics g = this.getGraphics();
		//g.translate((int)(xCenter*xScale-this.getWidth()/2.),(int)(-(this.getHeight()/2.+yCenter*yScale)));
		g.clipRect(0,0,this.getWidth(),this.getHeight());
		this.paint(g);
	}
	/**
	 * Straightly render the new function without clearing the frame. 
	 *
	 * This method will straightly render the new function without clearing the old graph so that the graphs of both the old and new functions can be shown meanwhile. 
	 *
	 * @param function the new function to be rendered
	 */
	public void renderFunction(DoubleUnaryOperator function) //render the new function without clearing the frame
	{
		this.function = function;
		Graphics g = this.getGraphics();
		//g.translate((int)(xCenter*xScale-this.getWidth()/2.),(int)(-(this.getHeight()/2.+yCenter*yScale)));
		this.paint(g);
	}
	/**
	 * A static method to render a function in a given graphics context. 
	 *
	 * Defaultly, this method keeps the original positive direction of y-axix, this is <em>up</em> in most coordinate systems. 
	 *
	 * @param g the target graph context
	 * @param function the function graph of which is to be rendered
	 * @param width the width of area to render the function graph
	 * @param height the height of area to render the function graph
	 * @param xCenter the horizontal coordinate of the center point of the the area to render the function graph
	 * @param yCenter the vertical coordinate of the center point of the the area to render the function graph
	 * @param xScale the horizontal scale
	 * @param yScale the vertical scale
	 * @param dx the horizontal step
	 * @since 6.0
	 */
	public final static void render(Graphics2D g,DoubleUnaryOperator function,int width,int height,double xCenter,double yCenter,double xScale,double yScale,double dx) //static method to render a function; by default, consider down as the positive direction of y-axis in most coordinate systems
	{
		double mx = width/2.,my = height/2.;
		final int transX = (int)(mx-xCenter*xScale),transY = (int)(my-yCenter*yScale); //if we want consider up as the positive direction of y-axis, which is opposite in most of computer coodinate systems, we use "+", or we use "-"
		g.translate(transX,transY);
		g.setStroke(new BasicStroke(5f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		double xi,yi,x0,y0,x1,y1;
		xi = xCenter;
		yi = function.applyAsDouble(xi)*yScale;
		double pdx = dx*xScale;
		double pxi = xi*xScale,px0,px1;
		x0 = xi;
		y0 = yi;
		px0 = pxi;
		double max = pxi+mx,mix = pxi-mx;
		for(x1 = x0+dx,px1 = px0+pdx;px1<=max;x1 += dx,px1 += pdx)
		{
			y1 = function.applyAsDouble(x1)*yScale;
			Line2D.Double line = new Line2D.Double(px0,y0,px1,y1);
			g.draw(line);
			x0 = x1;
			y0 = y1;
			px0 = px1;
		}
		x0 = xi;
		y0 = yi;
		px0 = pxi;
		for(x1 = x0-dx,px1 = px0-pdx;px1>=mix;x1 -= dx,px1 -= pdx)
		{
			y1 = function.applyAsDouble(x1)*yScale;
			Line2D.Double line = new Line2D.Double(px0,y0,px1,y1);
			g.draw(line);
			x0 = x1;
			y0 = y1;
			px0 = px1;
		}
		g.translate(-transX,-transY);
	}
	/**
	 * A simple executable to draw a function graph. 
	 */
	public static void main(String[] args)
	{
		HashMap<String,Operator> operatorMap = new HashMap<>();
		try
		{
			operatorMap = OperatorMapGenerator.getCompleteOperatorMap(new File(args[0]));
		}
		catch(ClassNotFoundException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(NoSuchMethodException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(InstantiationException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(IllegalAccessException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(InvocationTargetException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Please input the path of operator list.");
			System.out.println(e);
			System.exit(0);
		}
		Scanner s = new Scanner(System.in);
		System.out.print("Please input the size of the frame as \"width height\": ");
		int width = s.nextInt();
		int height = s.nextInt();
		System.out.print("Please input the center point: ");
		double xCenter = s.nextDouble();
		double yCenter = s.nextDouble();
		System.out.print("Please input the scale as \"x-scale y-scale\": ");
		double xScale = s.nextDouble();
		double yScale = s.nextDouble();
		System.out.print("Please input the Δx: ");
		double dx = s.nextDouble();
		System.out.print("Please input the function as (y=) f(x): y=");
		s.useDelimiter("\\n|\\r|\\r\\n");
		String infix = s.next();
		//new FunctionDrawer(x->x==0.?1.:Math.sin(x)/x,width,height,xScale,yScale,dx);
		//new FunctionDrawer(x->x,width,height,xScale,yScale,dx);
		try
		{
			new FunctionDrawer(new Expression(infix,operatorMap,"x"),width,height,xCenter,yCenter,xScale,yScale,dx);
		}
		catch(IllegalArgumentException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(IOException e)
		{
			System.out.println(e);
			System.exit(0);
		}
	}
}
