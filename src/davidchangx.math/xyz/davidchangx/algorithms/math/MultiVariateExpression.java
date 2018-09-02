package xyz.davidchangx.algorithms.math;
import java.util.ArrayDeque;
import java.util.Scanner;
//import java.util.HashMap;
import xyz.davidchangx.algorithms.math.operator.Operator;
import xyz.davidchangx.algorithms.math.Operand;
import xyz.davidchangx.algorithms.math.Unknown;
import xyz.davidchangx.algorithms.math.ExpressionItem;
import java.util.regex.Pattern;
import java.util.ArrayList;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
import java.util.ListIterator;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import xyz.davidchangx.algorithms.math.operator.Head;
import xyz.davidchangx.algorithms.math.operator.Tail;
import java.util.Arrays;
import java.util.function.ToIntBiFunction;
import java.util.stream.Stream;
import xyz.davidchangx.algorithms.math.Expression;
import java.io.StringReader;
import java.util.function.Predicate;
import java.io.CharArrayWriter;
import java.io.IOException;
/**
 * Suffix expression constructed from an infix expression.
 *
 * To contruct an {@code MultiVariateExpression} you need an infix expression String and a dictionary of {@link Operator}. To simplize the construction of operator map, we provide {@code xyz.davidchangx.mathtool.OperatorMapGenerator} to generate operator map conveniently. You can use the operators provided in package {@code xyz.davidchangx.algorithms.math.operator}, and also you can contribute your own operators by inherit class {@link Operator}. 
 *
 * This class inherits {@link Operator} class so that a contributed {@code MultiVariateExpression} can be used as a new operator in another {@code MultiVariateExpression} or {@link Expression}. 
 *
 * @version 6.0
 * @since 3.0
 * @author David Chang
 */
public class MultiVariateExpression extends Operator
{
	private String strSufix;
	private ArrayList<ExpressionItem> sufix;
	private double value;
	private Map<String,Operator> operatorMap;
	private ArrayDeque<Double> opdStack;
	private boolean newestOrNot,setOrNot;
	/**
	 * Constructs an {@code MultiVariateExpression} object with unknown from an infix string, an operator map and the character of unknown. 
	 *
	 * @param infix the infix expression string
	 * @param operatorMap the operator map
	 * @param x the character of unknown
	 * @throws IllegalArgumentException if the infix cannot be correctly scanned.
	 *
	 * @since 4.5
	 */
	@SafeVarargs
	public MultiVariateExpression(String infix,Map<String,Operator> operatorMap,String... x) throws IllegalArgumentException,IOException //contribute an MultiVariateExpression with unknown character
	{
		this("f",15,1,infix,operatorMap,x);
	}
	/**
	 * Constructs an {@code MultiVariateExpression} object from given function name, in-stack and out-stack priority, infix string, an operator map and the character of unknown. 
	 *
	 * The returned object can be used as a new {@link Operator}, string format of which is {@code functionName}, group mode of which is {@code OperatorGroupMode.NEEDING_CLOSED}, operand count of which is the count of the unknowns. 
	 *
	 * @param functionName a given function name that can be used as a new operator's string format
	 * @param inStackPriority in-stack priority. We advise to set this priority as 15. 
	 * @param outStackPriority out-stack priority. We advise to set this priority as 1. 
	 * @param infix the infix expression string
	 * @param operatorMap the operator map
	 * @param x the character of unknown
	 * @throws IllegalArgumentException if the infix cannot be correctly scanned.
	 *
	 * @since 4.5
	 */
	@SafeVarargs
	public MultiVariateExpression(String functionName,int inStackPriority,int outStackPriority,String infix,Map<String,Operator> operatorMap,String... x) throws IllegalArgumentException,IOException //contribute an Expression with detailed customized operator attributes, the new Expression would be used as a new Operator
	{
		super(functionName + "(",inStackPriority,outStackPriority,x.length,OperatorGroupMode.NEEDING_CLOSED);
		
		//this.unknowCount = x.length;
		this.operatorMap = operatorMap;
		this.operatorMap.put("$",new Head());
		this.operatorMap.put("#",new Tail());
		this.opdStack = new ArrayDeque<Double>(); //the operand stack
		
		infix.trim();
		infix += " #";
		StringReader stream = new StringReader(infix);
		ArrayDeque<Operator> stack = new ArrayDeque<>();
		Predicate<String> deliPat = Pattern.compile("\\s").asPredicate(),
			wordPat = Pattern.compile("\\w").asPredicate();
		Predicate<String> numPat = Pattern.compile("-?\\d+(\\.\\d*)?|\\.\\d+").asPredicate(),
			unknownPat = str->Arrays.stream(x).anyMatch(un->str.equals(un)),
			opPat = str->this.operatorMap.containsKey(str);
		Predicate<String> elePat = numPat.or(unknownPat).or(opPat);
		Unknown[] unknowns = Stream.<Unknown>iterate(new Unknown(opdStack,0),
				unknown->unknown.getId()<x.length,
				unknown->new Unknown(opdStack,unknown.getId()+1))
			.toArray(Unknown[]::new);
		ToIntBiFunction<String[],String> indexOf = (array,symbol)->{
			int i = 0;
			for(;i<array.length&&!array[i].equals(symbol);i++) ;
			return i==array.length?-1:i;
		};
		CharArrayWriter buffer = new CharArrayWriter();
		stack.push(new Head());
		StringBuilder strSuffix = new StringBuilder();
		sufix = new ArrayList<>();
		Predicate<CharArrayWriter> eleHandle = new Predicate<>(){
			public boolean test(CharArrayWriter buff)
			{
				String str = buff.toString();
				if(numPat.test(str))
				{
					//System.out.println("node a: " + str);
					double theNum = Double.parseDouble(str);
					strSuffix.append(theNum + " ");
					sufix.add(new Operand(theNum,opdStack));
					buff.reset();
					return true;
				}
				else if(unknownPat.test(str))
				{
					//System.out.println("node b: " + str);
					strSuffix.append(str + " ");
					sufix.add(unknowns[indexOf.applyAsInt(x,str)]);
					buff.reset();
					return true;
				}
				else
				{
					for(int n = str.length();n>0;n--)
					{
						if(opPat.test(str.substring(0,n)))
						{
							String realOpStr = str.substring(0,n);
							//System.out.println("node c: " + realOpStr);
							Operator nextOperator = MultiVariateExpression.this.operatorMap.get(realOpStr);
							Operator topOperator = stack.peek();
							for(int priority = nextOperator.getInStackPriority();topOperator.getOutStackPriority()>=priority;topOperator = stack.peek())
							{
								if(realOpStr.equals("#")&&(stack.size()==1))
									break;
								strSuffix.append(topOperator + " ");
								Operator topClone = (Operator)topOperator.clone();
								topClone.setStack(MultiVariateExpression.this.opdStack);
								sufix.add(topClone);
								stack.pop();
								if(topOperator.needsClosed())
									break;
							}
							if(!nextOperator.isClosing())
								stack.push(nextOperator);
							buff.reset();
							if(n==str.length())
								return true;
							buff.append(str.substring(n));
							return this.test(buff);
						}
					}
					return false;
				}
			}
		};
		boolean firstPunc = true;
		for(int ch = stream.read();ch!=-1;ch = stream.read())
		{
			String newChar = String.valueOf((char)ch);
			if(deliPat.test(newChar))
			{
				//System.out.println("node 1: " + buffer + "," + newChar);
				if(!eleHandle.test(buffer))
					throw new IllegalArgumentException("The infix string cannot be scanned correctly, maybe you should check your orthography of operators and unknown or check if you have separate neighboring operands and operators by whitespace characters. The inputed expression is: \n\t" + infix.substring(0,infix.length()-1));
			}
			else if(wordPat.test(newChar))
			{
				//System.out.println("node 2: " + buffer + "," + newChar);
				if(!firstPunc)
				{
					//System.out.println("node 2.1: " + buffer + "," + newChar);
					firstPunc = true;
					eleHandle.test(buffer);
				}
				buffer.append(newChar);
			}
			else
			{
				//System.out.println("node 3: " + buffer + "," + newChar);
				if(firstPunc)
				{
					//System.out.println("node 3.1: " + buffer + "," + newChar);
					firstPunc = false;
					eleHandle.test(buffer);
				}
				buffer.append(newChar);
			}
		}
		if(!eleHandle.test(buffer))
		{
			//System.out.println("node 4");
			throw new IllegalArgumentException("The infix string cannot be scanned correctly, maybe you should check your orthography of operators and unknown or check if you have separate neighboring operands and operators by whitespace characters. The inputed expression is: \n\t" + infix.substring(0,infix.length()-1));
		}
		this.strSufix = strSuffix.toString().trim();
		//System.out.println("node 5: " + strSufix);
		
		newestOrNot = false;
		setOrNot = false;
	}
	private MultiVariateExpression(String functionName,int inStackPriority,int outStackPriority,int unknowCount) //create an empty MultiVariateExpression, used in clone() method
	{
		super(functionName,inStackPriority,outStackPriority,unknowCount,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Gets a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		MultiVariateExpression expression = new MultiVariateExpression(super.operator,super.inStackPriority,super.outStackPriority,super.operandCount);
		expression.strSufix = this.strSufix;
		expression.sufix = new ArrayList<>();
		this.sufix.stream().parallel().forEach(item->expression.sufix.add((ExpressionItem)item.clone()));
		expression.operatorMap = this.operatorMap;
		expression.opdStack = new ArrayDeque<>();
		expression.sufix.stream().parallel().forEach(item->item.setStack(expression.opdStack));
		expression.newestOrNot = expression.setOrNot = false;
		return expression;
	}
	/**
	 * Solves the value of the {@code Expression} object. This method returns nothing. The result of calculation should be obtained by invoking {@link getValue()}. 
	 *
	 * @param k the value of unknown
	 * @return this object
	 * @since 6.0
	 */
	public MultiVariateExpression calculate(double... k) //solve the value of the Expression, use k as the values of unknown character if there are several, the solved value should be obtained by invoking getValue()
	{
		value = this.solve(k);
		setOrNot = true;
		newestOrNot = true;
		return this;
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x) //this method will not update the expression value, you can't use getValue() to get the result of this method
	{
		double value;
		synchronized(sufix)
		{
			opdStack.clear();
			sufix.stream().forEach(item->item.execute(x));
			value = opdStack.pop();
		}
		return value;
	}
	/**
	 * Check if the present value which can be returned by invoking method {@link getValue()} is the newest. 
	 *
	 * If the value has just solved out by {@link calculate(double...)} and hasn't been returned by method {@link getValue()}, it is the newest. 
	 *
	 * @return if the present expression value is the newest
	 */
	public boolean isNewest() //check if the present value which can be returned by method getValue() is the newest (if the value has just solved out by calculate(double...) or and hasn't been returned by getValue(), it is the newest).
	{
		return newestOrNot;
	}
	/**
	 * Gets the calculation result. 
	 *
	 * To guarantee that you can always get the newest value, you'd better invoke this method immediately after invoking {@link calculate(double...)} or use {@link isNewest()} to check before invoking this method. 
	 *
	 * @return the calculation result. 
	 */
	public double getValue() //get the solved value, to guarantee that you can get the newest value, you'd better invoke calculate(double...) method to calculate or use isNewest() to check before you invoke this method
	{
		if(!setOrNot)
		{
			double[] valueOfUnknowns = new double[this.operandCount];
			Arrays.fill(valueOfUnknowns,0.);
			this.calculate(valueOfUnknowns);
		}
		newestOrNot = false;
		return this.value;
	}
	/**
	 * Returns the string format of suffix. 
	 *
	 * @return the string format of the suffix expression
	 */
	public String getSuffix()
	{
		return this.strSufix;
	}
	/**
	 * Returns the suffix expression in format {@code ArrayList<ExpressionItem>}. 
	 *
	 * This method in versions that are earlier than 6.0 is dangerous for it simply returns the reference of the internal @{code ArrayList}. In the new version, this method will create a deep clone of the internal @{code ArrayList} and return it. 
	 *
	 * @return the suffix expression in format {@code ArrayList<ExpressionItem>}
	 */
	protected ArrayList<ExpressionItem> getArraySuffix()
	{
		ArrayList<ExpressionItem> newSuffix = new ArrayList<>();
		this.sufix.stream().parallel().forEach(item->newSuffix.add((ExpressionItem)item.clone()));
		return newSuffix;
	}
	/**
	 * Returns the used operator map of this {@code MultiVariateExpression} object. 
	 *
	 * @return the used operator map
	 *
	 * @since 4.5
	 */
	public Map<String,Operator> getOperators()
	{
		return this.operatorMap;
	}
	/**
	 * Please use {@link getOperandCount()} instead. 
	 *
	 * Returns the count of unknown symbols. 
	 *
	 * @deprecated
	 * @return the count of unknown symbols
	 * @since 5.0
	 */
	@Deprecated
	public int getUnknownCount()
	{
		return this.operandCount;
	}
}
