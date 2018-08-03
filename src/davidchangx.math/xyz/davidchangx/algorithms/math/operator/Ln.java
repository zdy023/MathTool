//Ln.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
import xyz.davidchangx.algorithms.math.operator.Lg;
/**
 * Netural logarithm with e (2.71828...) as base number.
 *
 * @see Ln
 *
 * @version 2.1
 * @author David Chang
 */
public class Ln extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Ln()
	{
		super("ln(",15,1,1,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return Math.log(x[0]);
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Ln();
	}
}
