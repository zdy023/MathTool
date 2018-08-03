package xyz.davidchangx.algorithms.math;
import java.util.Arrays;
//import java.util.stream.DoubleStream;
import java.util.function.DoubleUnaryOperator;
import java.util.Scanner;
/**
 * A simple regression calculator.
 *
 * @version 2.1
 * @author David Chang
 */
public class RegressionCalculator
{
	private double[] x, y;
	private int n;
	private double aveX,aveY;
	private double sumX,sumY;
	private double sumX2,sumY2,sumXY;
	private double sumdX2,sumdY2,sumdXdY;
	private double deno,nume;
	private double a,b,sx,sy,sb,r;
	/**
	 * Constructs a {@code RegressionCalculator} from given datas. We consider {@code x} as independent variables and {@code y} as depedent variables. 
	 *
	 * @param x independent variables
	 * @param y depedent variables
	 */
	public RegressionCalculator(double[] x,double[] y)
	{
		n = x.length>y.length?y.length:x.length;
		this.x = Arrays.copyOf(x,n);
		this.y = Arrays.copyOf(y,n);

		this.calculate();
	}
	/**
	 * Set a group of new data. This method won't start to calculate. 
	 *
	 * @param x independent variables
	 * @param y depedent variables
	 */
	public void setData(double[] x,double[] y)
	{
		n = x.length>y.length?y.length:x.length;
		this.x = Arrays.copyOf(x,n);
		this.y = Arrays.copyOf(y,n);
	}
	/**
	 * Calculate the regression parameters. 
	 */
	private void calculate() //calculate the regression parameters
	{
		sumX = Arrays.stream(x).sum();
		sumY = Arrays.stream(y).sum();
		aveX = sumX/n;
		aveY = sumY/n;
		DoubleUnaryOperator square = x->x*x;
		sumX2 = Arrays.stream(x).map(square).sum();
		sumY2 = Arrays.stream(y).map(square).sum();
		sumXY = 0;
		for(int i = 0;i<n;i++)
			sumXY += x[i]*y[i];
		deno = n*sumX2-sumX*sumX;
		nume = n*sumXY-sumX*sumY;
		sumdX2 = deno/n;
		sumdY2 = sumY2-sumY*sumY/n;
		sumdXdY = nume/n;

		b = nume/deno;
		a = aveY-b*aveX;
		sx = Math.sqrt(sumdX2/(n-1));
		sy = Math.sqrt(sumdY2/(n-1));
		sb = sy/Math.sqrt(sumdX2);
		r = sumdXdY/Math.sqrt(sumdX2*sumdY2);
	}
	/**
	 * Returns the constant term of linear regression equation y=a+b*x . 
	 *
	 * The constant term is also known as zero-input response. 
	 *
	 * @return the constant term a in y=a+b*x
	 */
	public double getA() //a in y=a+b*x
	{
		return a;
	}
	/**
	 * Returns the slope of linear regression equation y=a+b*x. 
	 *
	 * The slope is that coefficient of linear term. 
	 *
	 * @return the slope b in y=a+b*x
	 */
	public double getB() //b in y=a+b*x
	{
		return b;
	}
	/**
	 * Returns standard deviation of independent variables. 
	 *
	 * @return standard deviation of independent variables
	 */
	public double getSx() //standard deviation of x
	{
		return sx;
	}
	/**
	 * Returns standard deviation of depedent variables.
	 *
	 * @return standard deviation of depedent variables
	 */
	public double getSy() //standard deviation of y
	{
		return sy;
	}
	/**
	 * Returns standard deviation of scope b in "y=a+b*x". 
	 *
	 * @return standard deviation of scope b in "y=a+b*x"
	 */
	public double getSb() //standard deviation of b
	{
		return sb;
	}
	/**
	 * Returns the correlation coefficient. 
	 * 
	 * @return the correlation coefficient
	 */
	public double getCorrelationCoefficient() //correlation coefficient
	{
		return r;
	}
	/**
	 * Returns the sum of independent variables.
	 *
	 * @return the sum of independent variables
	 */
	public double getSumX()
	{
		return sumX;
	}
	/**
	 * Returns the average of independent variables. 
	 *
	 * @return the average of independent variables
	 */
	public double getAveX()
	{
		return aveX;
	}
	/**
	 * Returns the quadratic sum of independent variables. 
	 *
	 * @return the quadratic sum of independent variables
	 */
	public double getQuadraticSumX()
	{
		return sumX2;
	}
	/**
	 * Returns the sum of depedent variables. 
	 *
	 * @return the sum of depedent variables
	 */
	public double getSumY()
	{
		return sumY;
	}
	/**
	 * Returns the average of depedent variables. 
	 *
	 * @return the average of depedent variables
	 */
	public double getAveY()
	{
		return aveY;
	}
	/**
	 * Returns the quadratic sum of depedent variables.
	 *
	 * @return the quadratic sum of depedent variables
	 */
	public double getQuadraticSumY()
	{
		return sumY2;
	}
	/**
	 * Returns the sum of the product of corresponding independent variable and depedent variable. 
	 *
	 * @return the sum of the product of corresponding independent variable and depedent variable
	 */
	public double getSumOfProduct() //get sum of x*y
	{
		return sumXY;
	}
	/**
	 * A simple executable to calculate regression parameters. 
	 */
	public static void main(String[] args) //a simple calculator application using this tool class
	{
		Scanner s = new Scanner(System.in);
		System.out.print("Please input n: ");
		int n = s.nextInt();
		System.out.print("Please input x: ");
		double[] x = new double[n],y = new double[n];
		for(int i = 0;i<n;i++)
			x[i] = s.nextDouble();
		System.out.print("Please input y: ");
		for(int i = 0;i<n;i++)
			y[i] = s.nextDouble();
		RegressionCalculator cal = new RegressionCalculator(x,y);
		System.out.println("y=a+bx");
		System.out.println("a= " + cal.getA() + " b= " + cal.getB() + " r= " + cal.getCorrelationCoefficient());
		System.out.println("Sum of x: " + cal.getSumX() + " Sum of y: " + cal.getSumY());
		System.out.println("Average of x: " + cal.getAveX() + " Average of y: " + cal.getAveY());
		System.out.println("Sum of x^2: " + cal.getQuadraticSumX() + " Sum of y^2: " + cal.getQuadraticSumY() + " Sum of x*y: " + cal.getSumOfProduct());
		System.out.println("Sx= " + cal.getSx() + " Sy= " + cal.getSy() + " Sb= " + cal.getSb());
	}
}
