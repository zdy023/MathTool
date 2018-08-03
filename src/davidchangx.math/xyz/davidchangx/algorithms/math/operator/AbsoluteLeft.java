package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
/**
 * The left part of operator to solve the absolute value of a double.
 *
 * @see AbsoluteRight
 *
 * @version 2.1
 * @author David Chang
 */
public class AbsoluteLeft extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public AbsoluteLeft()
	{
		super("|(",15,1,1,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return Math.abs(x[0]);
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new AbsoluteLeft();
	}
}
