package xyz.davidchangx.algorithms.math.operator;
/**
 * Sample function.
 * Sa(x) = sin(x)/x .
 * @version 2.1
 * @author David Chang
 */
public class Sample extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Sample()
	{
		super("Sa(",15,1,1,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return Math.sin(x[0])/x[0];
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Sample();
	}
}
