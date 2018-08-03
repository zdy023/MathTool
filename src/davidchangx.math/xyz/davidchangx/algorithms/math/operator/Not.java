//Not.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
/**
 * Logical not operator.
 * We consider a non-zero double as logical true and zero as false. 
 * Returns 0 or 1. 
 * @version 2.1
 * @author David Chang
 */
public class Not extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Not()
	{
		super("~",15,1,1,OperatorGroupMode.SINGLE);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		boolean boolValue = x[0]==0.;
		return boolValue?1.:0.;
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Not();
	}
}
