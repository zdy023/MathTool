//Discretization.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
import xyz.davidchangx.algorithms.math.operator.DirichletFunction;
/**
 * Multiply this function to ad continuous function in math expression to get a discrete sample of the continuous function.
 *
 * This function accept two arguments: x and T. Usually x is the independent variable and T is invariable parameter - period. This function returns 1 only when T|x . 
 *
 * sum_delta( x , T ) = sum_{n=-\infinity}^{+\infinity}delta(x-n*T)
 *
 * In the expression above, delta(x) means Dirichlet function. 
 *
 * @see DirichletFunction
 *
 * @version 2.1
 * @author David Chang
 */
public class Discretization extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public Discretization()
	{
		super("sum_delta(",15,1,2,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		return x[0]%x[1]==0.?1.:0.;
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Discretization();
	}
}
