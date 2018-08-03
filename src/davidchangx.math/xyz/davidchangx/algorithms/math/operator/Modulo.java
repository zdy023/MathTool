//Modulo.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
/**
 * Modulo operator.
 * 'a mod b' equals a-floor(a/b). 
 * @version 2.1
 * @author David Chang
 */
public class Modulo extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Modulo()
	{
		super("mod",6,6,2,OperatorGroupMode.SINGLE);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return x[0]-x[1]*Math.floor(x[0]/x[1]);
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Modulo();
	}
}
