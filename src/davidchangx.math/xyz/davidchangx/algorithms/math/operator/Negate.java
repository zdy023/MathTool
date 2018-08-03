package xyz.davidchangx.algorithms.math.operator;
/**
 * An unary operator to get the opposite number of a double.
 * @version 2.1
 * @author David Chang
 */
public class Negate extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Negate()
	{
		super("-(",15,1,1,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return -x[0];
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Negate();
	}
}
