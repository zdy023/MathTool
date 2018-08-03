//And.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
import xyz.davidchangx.algorithms.math.operator.Operator;
/**
 * The operator to execute logical and operation.
 *
 * We consider a double not equal to 0 as false, 0 as true. 
 * 
 * @version 2.1
 * @author David Chang
 */
public class And extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public And()
	{
		super("&",4,4,2,OperatorGroupMode.SINGLE);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		boolean boolValue = x[0]!=0.&&x[1]!=0.;
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
		return new And();
	}
}
