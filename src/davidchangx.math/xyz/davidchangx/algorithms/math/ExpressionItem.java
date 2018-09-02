//ExpressionItem.java
package xyz.davidchangx.algorithms.math;
import java.util.ArrayDeque;
/**
 * The item in Expression.
 *
 * @version 6.0
 * @author David Chang
 */
public abstract class ExpressionItem implements Cloneable
{
	protected ArrayDeque<Double> stack;
	/**
	 * Invoked by class {@link Expression} to solve the value of suffix expression. 
	 *
	 * @param x a parameter possibly in need
	 */
	public abstract void execute(double... x);
	/**
	 * Set the stack needed during expression calculating. 
	 *
	 * @param operandStack the stack of operand
	 * @since 6.0
	 */
	public final void setStack(ArrayDeque<Double> operandStack)
	{
		this.stack = operandStack;
	}
	/**
	 * Returns the stack corresponding to this item. 
	 *
	 * @return the stack of operand
	 * @since 6.0
	 */
	public final ArrayDeque<Double> getStack()
	{
		return this.stack;
	}
	/**
	 * Creates a clone for this operator object. 
	 *
	 * @return the clone of this item object
	 * @since 6.0
	 */
	@Override
	public abstract Object clone();
}
