//Or.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
import xyz.davidchangx.algorithms.math.operator.Operator;
/**
 * Logical or operator.
 * @version 2.1
 * @author David Chang
 */
public class Or extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Or()
	{
		super("|",3,3,2,OperatorGroupMode.SINGLE);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		boolean boolValue = x[0]!=0.||x[1]!=0.;
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
		return new Or();
	}
}
