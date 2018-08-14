package xyz.davidchangx.algorithms.math;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.HashMap;
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
/**
 * Suffix expression constructed from an infix expression.
 *
 * To contruct an {@code MultiVariantExpression} you need an infix expression String and a dictionary of {@link Operator}. To simplize the construction of operator map, we provide {@code xyz.davidchangx.mathtool.OperatorMapGenerator} to generate operator map conveniently. You can use the operators provided in package {@code xyz.davidchangx.algorithms.math.operator}, and also you can contribute your own operators by inherit class {@link Operator}. 
 *
 * This class inherits {@link Operator} class so that a contributed {@code MultiVariantExpression} can be used as a new operator in another {@code MultiVariantExpression} or {@link Expression}. 
 *
 * @version 3.0
 * @since 3.0
 * @author David Chang
 */
public class MultiVariantExpression extends Operator
{
	private String strSufix,infix;
	private ArrayList<ExpressionItem> sufix;
	private double value;
	private char[] x;
	private Unknown[] unknowns;
	private HashMap<String,Operator> operatorMap;
	private ArrayDeque<Double> opdStack;
	private boolean newestOrNot,setOrNot;
	/**
	 * Constructs an {@code MultiVariantExpression} object with unknown from an infix string, an operator map and the character of unknown. 
	 *
	 * @param infix the infix expression string
	 * @param operatorMap the operator map
	 * @param x the character of unknown
	 * @throws IllegalArgumentException if the infix cannot be correctly scanned. <br>The neighboring operands and operators in infix should be separate with whitespace characters, or an @{code IllegalArgumentException will be throwed.
	 */
	public MultiVariantExpression(String infix,HashMap<String,Operator> operatorMap,char... x) throws IllegalArgumentException //contribute an MultiVariantExpression with unknown character
	{
		this("f",15,1,infix,operatorMap,x);
	}
	/**
	 * Constructs an {@code MultiVariantExpression} object from given function name, in-stack and out-stack priority, infix string, an operator map and the character of unknown. 
	 *
	 * The returned object can be used as a new {@link Operator}, string format of which is {@code functionName}, group mode of which is {@code OperatorGroupMode.NEEDING_CLOSED}, operand count of which is the count of the unknowns. 
	 *
	 * @param functionName a given function name that can be used as a new operator's string format
	 * @param inStackPriority in-stack priority. We advise to set this priority as 15. 
	 * @param outStackPriority out-stack priority. We advise to set this priority as 1. 
	 * @param infix the infix expression string
	 * @param operatorMap the operator map
	 * @param x the character of unknown
	 * @throws IllegalArgumentException if the infix cannot be correctly scanned. <br>The neighboring operands and operators in infix should be separate with whitespace characters, or an @{code IllegalArgumentException will be thrown.
	 */
	public MultiVariantExpression(String functionName,int inStackPriority,int outStackPriority,String infix,HashMap<String,Operator> operatorMap,char[] x) throws IllegalArgumentException //contribute an Expression with detailed customized operator attributes, the new Expression would be used as a new Operator
	{
		super(functionName + "(",inStackPriority,outStackPriority,x.length,OperatorGroupMode.NEEDING_CLOSED);
		
		this.infix = infix;
		this.x = Arrays.copyOf(x,x.length);
		this.operatorMap = Expression.cloneMap(operatorMap);
		this.operatorMap.put("$",new Head());
		this.operatorMap.put("#",new Tail());
		this.opdStack = new ArrayDeque<Double>(); //the operand stack
		this.operatorMap.forEach((String oprName,Operator oprtr)->oprtr.setStack(opdStack));
		this.unknowns = Stream.<Unknown>iterate(new Unknown(opdStack,0),unknown->unknown.getId()<x.length,unknown->new Unknown(opdStack,unknown.getId()+1)).toArray(Unknown[]::new);
		
		Scanner s = new Scanner(infix + " #"); //In operatorMap there must be the infomation about '$'(head mark) and '#'(ending mark)
		ArrayDeque<Operator> stack = new ArrayDeque<Operator>(); //the operator stack
		Pattern opPat = Pattern.compile("(?:[" + String.valueOf(x) + "]?[a-zA-Z_]+)*\\W*"),unknownPat = Pattern.compile(String.valueOf(x));
		ToIntBiFunction<char[],Character> indexOf = (array,symbol)->{
			int i = 0;
			for(;array[i]!=symbol;i++) ;
			return i;
		};
		Operator nextOperator,topOperator;
		String nxtOpt;
		stack.push(this.operatorMap.get("$"));
		double theNum;
		StringBuilder strSufix = new StringBuilder();
		sufix = new ArrayList<ExpressionItem>();
		for(;s.hasNext();)
		{
			if(s.hasNextDouble())
			{
				theNum = s.nextDouble();
				strSufix.append(theNum + " ");
				sufix.add(new Operand(theNum,opdStack));
			}
			else if(s.hasNext(unknownPat))
			{
				nxtOpt = s.next(unknownPat);
				strSufix.append(nxtOpt + " ");
				sufix.add(unknowns[indexOf.applyAsInt(x,nxtOpt.charAt(0))]);
			}
			else if(s.hasNext(opPat))
			{
				nxtOpt = s.next(opPat);
				nextOperator = this.operatorMap.get(nxtOpt);
				topOperator = stack.peek();
				for(int priority = nextOperator.getInStackPriority();topOperator.getOutStackPriority()>=priority;topOperator = stack.peek())
				{
					if(nxtOpt.equals("#")&&(stack.size()==1))
						break;
					strSufix.append(topOperator + " ");
					sufix.add(topOperator);
					stack.pop();
					if(topOperator.needsClosed())
						break;
				}
				if(!nextOperator.isClosing())
					stack.push(nextOperator);
			}
			else
				throw new IllegalArgumentException("The infix string cannot be scanned correctly, maybe you should check your orthography of operators and unknown or check if you have separate neighboring operands and operators by whitespace characters. The inputed expression is: \n\t" + infix);
		}
		this.strSufix = strSufix.substring(0,strSufix.length()-1);
		
		newestOrNot = false;
		setOrNot = false;
	}
	/**
	 * Gets a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new MultiVariantExpression(this.operator.substring(0,this.operator.length()-1),this.inStackPriority,this.outStackPriority,this.infix,this.operatorMap,this.x);
	}
	/**
	 * Solves the value of the {@code Expression} object. This method returns nothing. The result of calculation should be obtained by invoking {@link getValue()}. 
	 *
	 * @param k the value of unknown
	 */
	public void calculate(double... k) //solve the value of the Expression, use k as the values of unknown character if there are several, the solved value should be obtained by invoking getValue()
	{
		value = this.solve(k);
		setOrNot = true;
		newestOrNot = true;
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x) //this method will not update the expression value, you can't use getValue() to get the result of this method
	{
		opdStack.clear();
		ListIterator<ExpressionItem> it = sufix.listIterator();
		for(;it.hasNext();)
			it.next().execute(x);
		return opdStack.pop();
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
			double[] valueOfUnknowns = new double[this.x.length];
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
	 * @return the suffix expression in format {@code ArrayList<ExpressionItem>}
	 */
	protected ArrayList<ExpressionItem> getArraySuffix()
	{
		return this.sufix;
	}
	/**
	 * Returns the used operator map of this {@code MultiVariantExpression} object. 
	 *
	 * @return the used operator map
	 */
	public HashMap<String,Operator> getOperators()
	{
		return this.operatorMap;
	}
}
