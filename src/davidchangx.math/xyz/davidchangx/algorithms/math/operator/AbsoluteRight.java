package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.AbsoluteLeft;
/**
 * The right part of operator to solve the absolute of a double.
 *
 * This operator is used to close the operator {@link AbsoluteLeft} ("|(").
 *
 * @see AbsoluteLeft
 *
 * @version 2.1
 * @author David Chang
 */
public class AbsoluteRight extends Operator
{
	/**
	 * Contruct an operator. 
	 */
	public AbsoluteRight()
	{
		super(")|",0,0,1,OperatorGroupMode.CLOSING_ONE);
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
		return new AbsoluteRight();
	}
}
