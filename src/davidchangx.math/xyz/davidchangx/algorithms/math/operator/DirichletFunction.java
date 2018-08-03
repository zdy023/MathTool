//DirichletFunction.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
import xyz.davidchangx.algorithms.math.operator.Operator;
/**
 * Dirichlet function.
 * Returns 1 only when x equals to 0. 
 * @version 2.1
 * @author David Chang
 */
public class DirichletFunction extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public DirichletFunction()
	{
		super("delta(",15,1,1,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return x[0]==0.?1.:0.;
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new DirichletFunction();
	}
}
