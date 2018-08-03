//Divide.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
/**
 * Division operator.
 * @version 2.1
 * @author David Chang
 */
public class Divide extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Divide()
	{
		super("/",8,8,2,OperatorGroupMode.SINGLE);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return x[0]/x[1];
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Divide();
	}
}
