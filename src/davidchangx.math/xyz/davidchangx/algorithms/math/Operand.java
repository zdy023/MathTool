//Operand.java
package xyz.davidchangx.algorithms.math;
import xyz.davidchangx.algorithms.math.ExpressionItem;
import java.util.ArrayDeque;
import xyz.davidchangx.algorithms.math.Expression;
/**
 * The operand in Expression.
 *
 * @version 3.0
 * @author David Chang
 */
class Operand implements ExpressionItem,Comparable<Operand>
{
	private double value;
	private ArrayDeque<Double> stack;
	/**
	 * Construct an {@code Operand} object. 
	 *
	 * @param value the value of the operand
	 * @param stack the operand stack which will be used while calculating in class {@link Expression}
	 */
	public Operand(double value,ArrayDeque<Double> stack) //contributed from a constance value and a stack of double
	{
		this.value = value;
		this.stack = stack;
	}
	/**
	 * Invoked by class {@link Expression} to solve the value of suffix expression. For an {@code Operand} object, this method will push this operand into the operand stack. 
	 *
	 * @param x useless for {@code Operand} object. 
	 */
	@Override
	public void execute(double... x)
	{
		stack.push(value);
	}
	/**
	 * Change an operand stack. 
	 *
	 * @param stack the operand stack
	 */
	public void setStack(ArrayDeque<Double> stack)
	{
		this.stack = stack;
	}
	/**
	 * Get the operand stack.
	 *
	 * @return the operand stack
	 */
	public ArrayDeque<Double> getStack()
	{
		return this.stack;
	}
	/**
	 * Modify the value of this operand object. 
	 *
	 * @param value the new value of this object
	 */
	public void setValue(double value)
	{
		this.value = value;
	}
	/**
	 * Returns the value of this operand object. 
	 *
	 * @return the value of this object.
	 */
	public double getValue()
	{
		return this.value;
	}
	/**
	 * Get the string format of this object.
	 *
	 * @return the string format of the numeric value of this object.
	 */
	@Override
	public String toString()
	{
		return String.valueOf(value);
	}
	/**
	 * Compares this operand to another one. 
	 *
	 * This method returns - 
	 *
	 * 1 - if this operand is greater than {@code x}
	 * 0 - if this operand equals to {@code x}
	 * -1 - if this operand is less than {@code x}
	 *
	 * @param x another {@code Operand} instance
	 * @return the comparison result
	 */
	@Override
	public int compareTo(Operand x)
	{
		double diff = value-x.getValue();
		return diff>0.?1:(diff<0.?-1:0);
	}
	/**
	 * Compares this {@code Operand} object to another object. 
	 *
	 * This method will only compare the double value of two {@code Operand} objects. 
	 *
	 * @return if this operand equals to the another object.
	 */
	@Override
	public boolean equals(Object x)
	{
		if(x instanceof Operand)
			return value==((Operand)x).getValue();
		return false;
	}
}
