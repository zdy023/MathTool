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
/**
 * Simple application to draw the plot of a function.
 *
 * @version 2.1
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
		this.xScale = xScale;
		this.yScale = yScale;
		this.dx = dx;
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
		double mx = this.getWidth()/2.,my = this.getHeight()/2.;
		g2.translate((int)(mx-xCenter*xScale),(int)(my+yCenter*yScale));
		g2.setStroke(new BasicStroke(5f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		//Ellipse2D.Double cirle = new Ellipse2D.Double(mx-10.,my-10.,mx+10.,my+10.);
		//Ellipse2D.Double cirle = new Ellipse2D.Double(-10.,-10.,10.,10.);
		//g2.draw(cirle);
		double xi,yi,x0,y0,x1,y1;
		xi = xCenter;
		yi = -function.applyAsDouble(xi)*yScale;
		double pdx = dx*xScale;
		double pxi = xi*xScale,px0,px1;
		x0 = xi;
		y0 = yi;
		px0 = pxi;
		double max = pxi+mx,mix = pxi-mx;
		//double xScale = 5.,yScale = 5.;
		for(x1 = x0+dx,px1 = px0+pdx;px1<=max;x1 += dx,px1 += pdx)
		{
			y1 = -function.applyAsDouble(x1)*yScale;
			//Line2D.Double line = Line2D.new Double(x0,y0,x1,y1);
			Line2D.Double line = new Line2D.Double(px0,y0,px1,y1);
			g2.draw(line);
			x0 = x1;
			y0 = y1;
			px0 = px1;
		}
		x0 = xi;
		y0 = yi;
		px0 = pxi;
		for(x1 = x0-dx,px1 = px0-pdx;px1>=mix;x1 -= dx,px1 -= pdx)
		{
			y1 = -function.applyAsDouble(x1)*yScale;
			//Line2D.Double line = Line2D.new Double(x0,y0,x1,y1);
			Line2D.Double line = new Line2D.Double(px0,y0,px1,y1);
			g2.draw(line);
			x0 = x1;
			y0 = y1;
			px0 = px1;
		}
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
		g.translate((int)(xCenter*xScale-this.getWidth()/2.),(int)(-(this.getHeight()/2.+yCenter*yScale)));
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
		g.translate((int)(xCenter*xScale-this.getWidth()/2.),(int)(-(this.getHeight()/2.+yCenter*yScale)));
		this.paint(g);
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
			new FunctionDrawer(new Expression(infix,operatorMap,'x'),width,height,xCenter,yCenter,xScale,yScale,dx);
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
