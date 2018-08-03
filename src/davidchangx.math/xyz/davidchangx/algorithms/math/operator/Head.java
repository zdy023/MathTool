//Head.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
import xyz.davidchangx.algorithms.math.operator.Tail;
/**
 * Required operator to mark the start of a math expression.
 *
 * @see Tail
 *
 * @version 2.1
 * @author David Chang
 */
public class Head extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Head()
	{
		super("$",1,1,1,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return x[0];
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Head();
	}
}
