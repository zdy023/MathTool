//SecantRoot.java
package xyz.davidchangx.algorithms.equation;
import java.util.function.DoubleUnaryOperator;
/**
 * Use secant method to solve the root of a function.
 *
 * @version 2.1
 * @author David Chang
 */
public final class SecantRoot
{
	/**
	 * Use secant method to solve the root of a function. 
	 * 
	 * Secant method may converge to zero point more quickly than dichotomy, but it may not converge for several functions. If you fail to get zero point using this method, please try other methods like dichotomy, and tangent method (Newton's method). 
	 *
	 * @param a one of the end point of interval
	 * @param b one of the end point of interval
	 * @param f the function root of which will be solved
	 * @param accuracy the target accuracy of root
	 * @return the gotten root of given function
	 *
	 * @throws ArithmeticException while accuracy isn't positive or an {@code ArithmeticException} occurs while calculating {@code f}
	 */
	public static double solve(double a,double b,DoubleUnaryOperator f,double accuracy) throws ArithmeticException
	{
		if(accuracy<=0)
			throw new ArithmeticException("Wrong accuracy!The accuracy should be strictly bigger than zero!");
		double fa,fb,x;
		try
		{
			fa = f.applyAsDouble(a);
			fb = f.applyAsDouble(b);
		}
		catch(ArithmeticException e)
		{
			throw e;
		}
		try
		{
			x = (a*fb-b*fa)/(fb-fa);
		}
		catch(ArithmeticException e)
		{
			x = (a+b)/2.;
		}
		double fx;
		try
		{
			fx = f.applyAsDouble(x);
		}
		catch(ArithmeticException e)
		{
			throw e;
		}
		for(;Math.abs(fx)>=accuracy;)
		{
			if(Math.abs(x-a)<=Math.abs(x-b))
			{
				b = x;
				fb = fx;
			}
			else
			{
				a = x;
				fa = fx;
			}
			try
			{
				x = (a*fb-b*fa)/(fb-fa);
			}
			catch(ArithmeticException e)
			{
				x = (a+b)/2.;
			}
			try
			{
				fx = f.applyAsDouble(x);
			}
			catch(ArithmeticException e)
			{
				throw e;
			}
		}
		return x;
	}
}
