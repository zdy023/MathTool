//Comma.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
/**
 * Comma operator and does nothing except to seperate multiple operands of multivariate operator.
 * @version 2.1
 * @author David Chang
 */
public class Comma extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Comma()
	{
		super(",",15,15,1,OperatorGroupMode.SINGLE);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return x[0];
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Comma();
	}
}
