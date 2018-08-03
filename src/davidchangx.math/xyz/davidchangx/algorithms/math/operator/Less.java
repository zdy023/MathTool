//Less.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
/**
 * The operator "&lt;". Returns 1.0 if true and 0. if false. 
 *
 * @author David Chang
 * @version 2.1
 */
public class Less extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Less()
	{
		super("<",2,2,2,OperatorGroupMode.SINGLE);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return x[0]<x[1]?1.:0.;
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Less();
	}
}
