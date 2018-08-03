package xyz.davidchangx.algorithms.math.operator;
/**
 * ReLU function (Rectification linear unit).
 * relu(x) = x if x&gt;=0; 0 if x&lt;=0 .
 * relu(x) = x*u(x), u(x) is unit step function. 
 * @version 2.1
 * @author David Chang
 */
public class PositiveOrZero extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public PositiveOrZero()
	{
		super("relu(",15,1,1,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return x[0]<0?0.:x[0];
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new PositiveOrZero();
	}
}
