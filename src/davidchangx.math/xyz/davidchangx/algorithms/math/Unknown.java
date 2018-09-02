//Unknown.java
package xyz.davidchangx.algorithms.math;
import xyz.davidchangx.algorithms.math.ExpressionItem;
import java.util.ArrayDeque;
/**
 * Unknown character in Expression.
 *
 * @version 6.0
 * @author David Chang
 */
class Unknown extends ExpressionItem
{
	private final int id;
	/**
	 * Constructs an {@code Unknown} object with an operand stack. 
	 *
	 * @param stack an operand stack
	 */
	public Unknown(ArrayDeque<Double> stack)
	{
		this.stack = stack;
		this.id = 0;
	}
	/**
	 * Constructs an {@code Unknown} object with an operand stack and an integer id. 
	 *
	 * @param stack an operand stack
	 * @param id the id of this unknown
	 */
	public Unknown(ArrayDeque<Double> stack,int id)
	{
		this.stack = stack;
		this.id = id;
	}
	/**
	 * Invoked by class {@link Expression} to solve the value of suffix expression. For an {@code Unknown} object, this method will push the given value into operand stack. 
	 * 
	 * @param x the given value of unknown
	 */
	@Override
	public void execute(double... x)
	{
		stack.push(x[this.id]);
	}
	/**
	 * Returns the id of this unknown. 
	 *
	 * @since 3.0
	 * @return the id of this unknown
	 */
	public int getId()
	{
		return this.id;
	}
	/**
	 * Creates a clone for this unknown object. 
	 * 
	 * @return a clone of this object
	 * @since 6.0
	 */
	@Override
	public Object clone()
	{
		return new Unknown(null,id);
	}
}
