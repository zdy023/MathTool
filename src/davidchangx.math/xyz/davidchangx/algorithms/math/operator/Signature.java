package xyz.davidchangx.algorithms.math.operator;
/**
 * Sign function.
 * sgn(x) = 1 if x&gt;0; 0 if x=0; -1 if x&lt;0 .
 * @version 2.1
 * @author David Chang
 */
public class Signature extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Signature()
	{
		super("sgn(",15,1,1,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return Math.signum(x[0]);
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Signature();
	}
}
