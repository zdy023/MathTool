//Pi.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
/**
 * PI (3.141592653589...) .
 * @version 2.1
 * @author David Chang
 */
public class Pi extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Pi()
	{
		super("pi",15,15,0,OperatorGroupMode.SINGLE);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return Math.PI;
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Pi();
	}
}
