//LeftBracket.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
import xyz.davidchangx.algorithms.math.operator.RightBracket;
/**
 * Operator to process with left bracket in math expression.
 *
 * @see RightBracket
 *
 * @version 2.1
 * @author David Chang
 */
public class LeftBracket extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public LeftBracket()
	{
		super("(",15,1,1,OperatorGroupMode.NEEDING_CLOSED);
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
		return new LeftBracket();
	}
}
