//Minus.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
/**
 * Minus operator.
 * @version 2.1
 * @author David Chang
 */
public class Minus extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Minus()
	{
		super("-",5,5,2,OperatorGroupMode.SINGLE);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return x[0]-x[1];
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Minus();
	}
}
