//IversonLeft.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.IversonRight;
/**
 * Iverson mark.
 *
 * It is used as "[proposition]" and returns 1 if proposition is true, returns 0 if proposition is false. 
 *
 * @see IversonRight
 *
 * @version 2.1
 * @author David Chang
 */
public class IversonLeft extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public IversonLeft()
	{
		super("[",15,1,1,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		boolean boolValue = x[0]!=0.;
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
		return new IversonLeft();
	}
}
