//HyperbolicTangent.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
/**
 * Hyperbolic tangent function.
 * @version 2.1
 * @author David Chang
 */
public class HyperbolicTangent extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public HyperbolicTangent()
	{
		super("tanh(",15,1,1,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return Math.tanh(x[0]);
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new HyperbolicTangent();
	}
}
