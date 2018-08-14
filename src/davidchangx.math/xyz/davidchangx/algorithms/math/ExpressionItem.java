//ExpressionItem.java
package xyz.davidchangx.algorithms.math;
/**
 * The item in Expression.
 *
 * @version 3.0
 * @author David Chang
 */
@FunctionalInterface
public interface ExpressionItem
{
	/**
	 * Invoked by class {@link Expression} to solve the value of suffix expression. 
	 *
	 * @param x a parameter possibly in need
	 */
	public void execute(double... x);
}
