//Tail.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
import xyz.davidchangx.algorithms.math.operator.Head;
/**
 * Required operator to mark the end of a math expression.
 *
 * @see Head
 *
 * @version 2.1
 * @author David Chang
 */
public class Tail extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Tail()
	{
		super("#",0,0,1,OperatorGroupMode.CLOSING_ONE);
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
		return new Tail();
	}
}
