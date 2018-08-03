//Unknown.java
package xyz.davidchangx.algorithms.math;
import xyz.davidchangx.algorithms.math.ExpressionItem;
import java.util.ArrayDeque;
/**
 * Unknown character in Expression.
 *
 * @version 2.1
 * @author David Chang
 */
class Unknown implements ExpressionItem
{
	private ArrayDeque<Double> stack;
	/**
	 * Constructs an {@code Unknown} object with an operand stack. 
	 *
	 * @param an operand stack
	 */
	public Unknown(ArrayDeque<Double> stack)
	{
		this.stack = stack;
	}
	/**
	 * Invoked by class {@link Expression} to solve the value of suffix expression. For an {@code Unknown} object, this method will push the given value into operand stack. 
	 * 
	 * @param x the given value of unknown
	 */
	@Override
	public void execute(double x)
	{
		stack.push(x);
	}
	/**
	 * Change an operand stack. 
	 *
	 * @param stack an operand stack
	 */
	public void setStack(ArrayDeque<Double> stack)
	{
		this.stack = stack;
	}
	/**
	 * Returns the operand stack. 
	 *
	 * @return the operand stack
	 */
	public ArrayDeque<Double> getStack()
	{
		return this.stack;
	}
}
