package xyz.davidchangx.algorithms.math.operator;
/**
 * Unit step function.
 * u(x) = 1 if x&gt;=0; 0 if x&lt;0 .
 * @version 2.1
 * @author David Chang
 */
public class StepFunction extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public StepFunction()
	{
		super("u(",15,1,1,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return x[0]>=0.?1.:0.;
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new StepFunction();
	}
}
