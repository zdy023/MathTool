//KroneckFunction.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.IversonLeft;
import xyz.davidchangx.algorithms.math.operator.IversonRight;
/**
 * Kroneck Function.
 *
 * This function accepts two arguments: i and j. "delta[i,j]" equals 1 only when i equals to j. 
 *
 * This function equals to "[i=j]" (writing in Iverson mark) . 
 *
 * @see IversonLeft
 * @see IversonRight
 *
 * @version 2.1
 * @author David Chang
 */
public class KroneckFunction extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public KroneckFunction()
	{
		super("delta[",15,1,2,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		boolean boolValue = x[0]==x[1];
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
		return new KroneckFunction();
	}
}
