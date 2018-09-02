//Operator.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.ExpressionItem;
import java.util.ArrayDeque;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
import xyz.davidchangx.algorithms.math.Expression;
/**
 * Abstract super-class of all available operators.
 *
 * @version 6.0
 * @author David Chang
 */
public abstract class Operator extends ExpressionItem
{
	protected final String operator;
	protected final int inStackPriority,outStackPriority; //priority to decide the order of pushing and poping, should be in [0,15)
	protected final int hash;
	protected final OperatorGroupMode groupMode;
	protected final int operandCount;
	/**
	 * Constructs an instance of class {@code Operator}. 
	 *
	 * You need to determine an in-stack priority and an out-stack priority for this operator. We use an operator stack to transform the infix expression to suffix expression. When a new operator is scanned from infix, the programme will compare its in-stack priority and the out-stack priority of the top element in stack, only when the in-stack priority of new operator is strictly greater than the out-stack priority of the top element, the new priority will be pushed into stack, or the top element will be poped out of stack and be appended to suffix expression. 
	 *
	 * The {@code groupMode} may be the following values: 
	 *
	 * <ul>
	 * <li>{@code OperatorGroupMode.SINGLE} - if this operator is used as a single unary or multivariate operator such as "+", "-" </li>
	 * <li>{@code OperatorGroupMode.NEEDING_CLOSED} - if this operator is the left part of a combined operator such as "(", "|(", "sin(", "[" </li>
	 * <li>{@code OperatorGroupMode.CLOSING_ONE} - if this operator is the right part of a combined operator such as ")", "|)", "]" </li>
	 * </ul>
	 *
	 * @param operator the string format of this operator
	 * @param inStackPriority the in-stack priority
	 * @param outStackPriority the out-stack priority
	 * @param operandCount the operand count of this operator
	 * @param groupMode the group mode of this operator. <br>Valid values are: {@code OperatorGroupMode.SINGLE}, {@code OperatorGroupMode.NEEDING_CLOSED}, {@code OperatorGroupMode.CLOSING_ONE}
	 */
	protected Operator(String operator,int inStackPriority,int outStackPriority,int operandCount,OperatorGroupMode groupMode)
	{
		this.operator = operator;
		this.inStackPriority = inStackPriority&15;
		this.outStackPriority = outStackPriority&15;
		this.operandCount = operandCount%3;
		this.groupMode = groupMode;
		this.stack = null;
		
		int hash = operator.hashCode()&0x0000ffff;
		hash += inStackPriority<<16;
		hash += outStackPriority<<24;
		hash += operandCount<<20;
		hash += groupMode.ordinal()<<28;
		this.hash = hash;
	}
	/**
	 * Creates a clone for this operator object. 
	 *
	 * @return the clone of this operator object
	 */
	@Override
	public abstract Object clone();
	/**
	 * Return operand count. 
	 *
	 * @return operand count
	 * @since 6.0
	 */
	public int getOperandCount()
	{
		return this.operandCount;
	}
	/**
	 * Get the in-stack priority of this operator. 
	 *
	 * @return the in-stack priority
	 */
	public final int getInStackPriority()
	{
		return this.inStackPriority;
	}
	/**
	 * Get the out-stack priority of this operator. 
	 *
	 * @return the out-stack priority
	 */
	public final int getOutStackPriority()
	{
		return this.outStackPriority;
	}
	/**
	 * Check if this operator needs closed. It means it's the left part of a combined operator that an operator needs closed. 
	 *
	 * @return if the operator needs closed
	 */
	public final boolean needsClosed()
	{
		return this.groupMode==OperatorGroupMode.NEEDING_CLOSED;
	}
	/**
	 * Check if this operator could close another operator. It means it's the right part of a combined operator that an operator could close another operator. 
	 *
	 * @return if the operator could close another operator.
	 */
	public final boolean isClosing()
	{
		return this.groupMode==OperatorGroupMode.CLOSING_ONE;
	}
	/**
	 * calculate as a math operator. 
	 *
	 * @param x the array of double operands
	 * @return the result of this operation
	 */
	public abstract double solve(double[] x); //the hook which is required to be implemented by child types.
	/**
	 * Invoked by class {@link Expression} to solve the value of suffix expression. For an {@code Operator} object, this method will invoke the {@link solve(double[])} method to calculate an math operation. 
	 *
	 * @param x useless for {@code Operator} object
	 */
	@Override
	@SafeVarargs
	public final void execute(double... x) //inherits from ExpressionItem, used in Expression
	{
		/*if(operandCount==0&&groupMode!=OperatorGroupMode.SINGLE)
			return;*/
		double[] y = new double[operandCount];
		for(int i = operandCount-1;i>=0;i--)
		{
			y[i] = stack.pop();
		}
		stack.push(this.solve(y));
	}
	/**
	 * Returns the string format of this operator. 
	 *
	 * @return the string format of this operator
	 */
	@Override
	public final String toString()
	{
		return this.operator;
	}
	/**
	 * Get hash code.
	 *
	 * The hash code is contributed as: 
	 *
	 * x,x,(m,m),(o,o,o,o);(c,c,c,c),(i,i,i,i);(s,s,s,s,s,s,s,s;s,s,s,s,s,s,s,s)
	 *
	 * <ul>
	 * <li>x: meaningless</li>
	 * <li>m: group mode</li>
	 * <li>o: out-stack priority</li>
	 * <li>c: count of operand</li>
	 * <li>i: in-stack priority</li>
	 * <li>s: the lower two bytes of hash code of operator string</li>
	 * </ul>
	 *
	 * @return the hash code of this operator
	 */
	@Override
	public final int hashCode()
	{
		return this.hash;
	}
	/**
	 * Test if this operator equals to another object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(Object x)
	{
		if(x instanceof Operator)
		{
			Operator y = (Operator)x;
			return (this.operator.equals(y.toString()))&&(this.hash==y.hashCode());
		}
		return false;
	}
}
