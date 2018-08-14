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
/**
 * Suffix expression constructed from an infix expression.
 *
 * To contruct an {@code Expression} you need an infix expression {@link String} and a dictionary of {@link Operator}. The operator map is required to consist of two special operators: {@link Head} ("$") and {@link Tail} ("#"), which are defined as {@link xyz.davidchangx.algorithms.math.operator.Head} and {@link xyz.davidchangx.algorithms.math.operator.Tail}. To simplize the construction of operator map, we provide {@link xyz.davidchangx.mathtool.OperatorMapGenerator} to generate operator map conveniently. You can use the operators provided in package {@link xyz.davidchangx.algorithms.math.operator}, and also you can contribute your own operators by inherit class {@link Operator}. 
 *
 * This class inherits {@link Operator} class so that a contributed {@link Expression} can be used as a new operator in another {@link Expression}. 
 *
 * This class implements {@link DoubleUnaryOperator}. 
 *
 * @version 3.0
 * @author David Chang
 */
public class Expression extends Operator implements DoubleUnaryOperator
{
	private String strSufix,infix;
	private ArrayList<ExpressionItem> sufix;
	private double value;
	private char x;
	private HashMap<String,Operator> operatorMap;
	private ArrayDeque<Double> opdStack;
	private boolean newestOrNot,setOrNot;
	/**
	 * Constructs an {@code Expression} object with unknown from an infix string, an operator map and the character of unknown. 
	 *
	 * @param infix the infix expression string
	 * @param operatorMap the operator map
	 * @param x the character of unknown
	 * @throws IllegalArgumentException if the infix cannot be correctly scanned. <br>The neighboring operands and operators in infix should be separate with whitespace characters, or an @{code IllegalArgumentException will be throwed.
	 */
	public Expression(String infix,HashMap<String,Operator> operatorMap,char x) throws IllegalArgumentException //contribute an Expression with unknown character
	{
		this("f",15,1,infix,operatorMap,x);
	}
	/**
	 * Constructs an {@code Expression} object without unknown from an infix string and an operator map. 
	 *
	 * @param infix the infix expression string
	 * @param operatorMap the operator map
	 * @throws IllegalArgumentException if the infix cannot be correctly scanned. <br>The neighboring operands and operators in infix should be separate with whitespace characters, or an @{code IllegalArgumentException will be thrown.
	 */
	public Expression(String infix,HashMap<String,Operator> operatorMap) throws IllegalArgumentException //contribute an Expression without unknown character
	{
		this(infix,operatorMap,'\0');
	}
	/**
	 * Constructs an {@code Expression} object from given function name, in-stack and out-stack priority, infix string, an operator map and the character of unknown. 
	 *
	 * The returned object can be used as a new {@link Operator}, string format of which is {@code functionName}, group mode of which is {@code OperatorGroupMode.NEEDING_CLOSED}, operand count of which is 1. 
	 *
	 * @param functionName a given function name that can be used as a new operator's string format
	 * @param inStackPriority in-stack priority. We advise to set this priority as 15. 
	 * @param outStackPriority out-stack priority. We advise to set this priority as 1. 
	 * @param infix the infix expression string
	 * @param operatorMap the operator map
	 * @param x the character of unknown
	 * @throws IllegalArgumentException if the infix cannot be correctly scanned. <br>The neighboring operands and operators in infix should be separate with whitespace characters, or an @{code IllegalArgumentException will be thrown.
	 */
	public Expression(String functionName,int inStackPriority,int outStackPriority,String infix,HashMap<String,Operator> operatorMap,char x) throws IllegalArgumentException //contribute an Expression with detailed customized operator attributes, the new Expression would be used as a new Operator
	{
		super(functionName + "(",inStackPriority,outStackPriority,1,OperatorGroupMode.NEEDING_CLOSED);
		
		this.infix = infix;
		this.x = x;
		this.operatorMap = cloneMap(operatorMap);
		this.operatorMap.put("$",new Head());
		this.operatorMap.put("#",new Tail());
		this.opdStack = new ArrayDeque<Double>();
		this.operatorMap.forEach((String oprName,Operator oprtr)->oprtr.setStack(opdStack));
		
		Scanner s = new Scanner(infix + " #"); //In operatorMap there must be the infomation about '$'(head mark) and '#'(ending mark)
		ArrayDeque<Operator> stack = new ArrayDeque<Operator>();
		Pattern opPat = Pattern.compile("(?:" + x + "?[a-zA-Z_]+)*\\W*"),unknownPat = Pattern.compile(String.valueOf(x));
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
				s.next(unknownPat);
				strSufix.append(x + " ");
				sufix.add(new Unknown(opdStack));
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
	static HashMap<String,Operator> cloneMap(HashMap<String,Operator> map) //a tool method to get a deep clone of a Operator Map
	{
		HashMap<String,Operator> newMap = new HashMap<String,Operator>();
		Set<Map.Entry<String,Operator>> set = map.entrySet();
		Iterator<Map.Entry<String,Operator>> it = set.iterator();
		for(;it.hasNext();)
		{
			Map.Entry<String,Operator> ele = it.next();
			newMap.put(ele.getKey(),(Operator)ele.getValue().clone());
		}
		return newMap;
	}
	/**
	 * Gets a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new Expression(this.operator.substring(0,this.operator.length()-1),this.inStackPriority,this.outStackPriority,this.infix,this.operatorMap,this.x);
	}
	/**
	 * Use {@link calculate(double)} intead this method. 
	 *
	 * Solves the value of the {@code Expression} object. This method returns nothing. The result of calculation should be obtained by invoking {@link getValue()}. 
	 *
	 * @deprecated
	 * @param k the value of unknown
	 */
	@Deprecated
	public void solve(double k) //solve the value of the Expression, use k as the value of unknown character if there is one, the solved value should be obtained by invoking getValue()
	{
		double[] x = {k};
		value = this.solve(x);
		setOrNot = true;
		newestOrNot = true;
	}
	/**
	 * Use {@link calculate()} intead this method. 
	 *
	 * Solves the value of the {@code Expression} object. This method returns nothing. The result of calculation should be obtained by invoking {@link getValue()}. 
	 *
	 * @deprecated
	 */
	@Deprecated
	public void solve() //solve the value of the Expression, use 0 as the value of unknown character if there is one, the solved value should be obtained by invoking getValue()
	{
		this.solve(0);
	}
	/**
	 * Solves the value of the {@code Expression} object. This method returns nothing. The result of calculation should be obtained by invoking {@link getValue()}. 
	 *
	 * @since 3.0
	 * @param k the value of unknown
	 */
	public void calculate(double k) //solve the value of the Expression, use k as the value of unknown character if there is one, the solved value should be obtained by invoking getValue()
	{
		double[] x = {k};
		value = this.solve(x);
		setOrNot = true;
		newestOrNot = true;
	}
	/**
	 * Solves the value of the {@code Expression} object. This method returns nothing. The result of calculation should be obtained by invoking {@link getValue()}. 
	 *
	 * @since 3.0
	 */
	public void calculate() //solve the value of the Expression, use 0 as the value of unknown character if there is one, the solved value should be obtained by invoking getValue()
	{
		this.calculate(0);
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
			it.next().execute(x[0]);
		return opdStack.pop();
	}
	/**
	 * Solves and update the expression value and returns it immediately. 
	 *
	 * @param x the value of unknown
	 * @return the calculation result
	 */
	@Override
	public double applyAsDouble(double x) //the method implemented in DoubleUnaryOperator, solves and update the expression value an returns it immediately
	{
		this.calculate(x);
		newestOrNot = false;
		return this.value;
	}
	/**
	 * Check if the present value which can be returned by invoking method {@link getValue()} is the newest. 
	 *
	 * If the value has just solved out by {@link calculate(double)} or {@link calculate()} and hasn't been returned by method {@link applyAsDouble(double)} or {@link getValue()}, it is the newest. 
	 *
	 * @return if the present expression value is the newest
	 */
	public boolean isNewest() //check if the present value which can be returned by method getValue() is the newest (if the value has just solved out by calculate(double) or calculate() and hasn't been returned by applyAsDouble(x) or getValue(), it is the newest).
	{
		return newestOrNot;
	}
	/**
	 * Gets the calculation result. 
	 *
	 * To guarantee that you can always get the newest value, you'd better invoke this method immediately after invoking {@link calculate(double)} or {@link calculate()} or use {@link isNewest()} to check before invoking this method. 
	 *
	 * @return the calculation result. 
	 */
	public double getValue() //get the solved value, to guarantee that you can get the newest value, you'd better invoke calculate(double) or calculate() method to calculate or use isNewest() to check before you invoke this method
	{
		if(!setOrNot)
			this.calculate();
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
	 * Returns the used operator map of this {@code Expression} object. 
	 *
	 * @return the used operator map
	 */
	public HashMap<String,Operator> getOperators()
	{
		return this.operatorMap;
	}
}
