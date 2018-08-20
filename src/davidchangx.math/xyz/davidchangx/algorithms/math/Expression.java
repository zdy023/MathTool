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
import java.io.StringReader;
import java.util.function.Predicate;
import java.io.CharArrayWriter;
import java.io.IOException;
/**
 * Suffix expression constructed from an infix expression.
 *
 * To contruct an {@code Expression} you need an infix expression {@link String} and a dictionary of {@link Operator}. To simplize the construction of operator map, we provide {@link xyz.davidchangx.mathtool.OperatorMapGenerator} to generate operator map conveniently. You can use the operators provided in package {@link xyz.davidchangx.algorithms.math.operator}, and also you can contribute your own operators by inherit class {@link Operator}. 
 *
 * This class inherits {@link Operator} class so that a contributed {@code Expression} can be used as a new operator in another {@code Expression}. 
 *
 * This class implements {@link DoubleUnaryOperator}. 
 *
 * @version 3.6
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
	 * @throws IllegalArgumentException if the infix cannot be correctly scanned.
	 */
	public Expression(String infix,HashMap<String,Operator> operatorMap,char x) throws IllegalArgumentException,IOException //contribute an Expression with unknown character
	{
		this("f",15,1,infix,operatorMap,x);
	}
	/**
	 * Constructs an {@code Expression} object without unknown from an infix string and an operator map. 
	 *
	 * @param infix the infix expression string
	 * @param operatorMap the operator map
	 * @throws IllegalArgumentException if the infix cannot be correctly scanned.
	 */
	public Expression(String infix,HashMap<String,Operator> operatorMap) throws IllegalArgumentException,IOException //contribute an Expression without unknown character
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
	 * @throws IllegalArgumentException if the infix cannot be correctly scanned.
	 */
	public Expression(String functionName,int inStackPriority,int outStackPriority,String infix,HashMap<String,Operator> operatorMap,char x) throws IllegalArgumentException,IOException //contribute an Expression with detailed customized operator attributes, the new Expression would be used as a new Operator
	{
		super(functionName + "(",inStackPriority,outStackPriority,1,OperatorGroupMode.NEEDING_CLOSED);
		
		this.infix = infix;
		this.x = x;
		//this.operatorMap = cloneMap(operatorMap);
		this.operatorMap = operatorMap;
		this.operatorMap.put("$",new Head());
		this.operatorMap.put("#",new Tail());
		this.opdStack = new ArrayDeque<Double>();
		//this.operatorMap.forEach((String oprName,Operator oprtr)->oprtr.setStack(opdStack));
		
		/*Scanner s = new Scanner(infix + " #"); //In operatorMap there must be the infomation about '$'(head mark) and '#'(ending mark)
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
		this.strSufix = strSufix.substring(0,strSufix.length()-1);*/

		infix += " #";
		var stream = new StringReader(infix);
		ArrayDeque<Operator> stack = new ArrayDeque<>();
		Predicate<String> deliPat = Pattern.compile("\\s").asPredicate(),wordPat = Pattern.compile("\\w").asPredicate();
		Predicate<String> numPat = Pattern.compile("-*\\d+(\\.\\d*)*|\\.\\d+").asPredicate(),unknownPat = String.valueOf(x)::equals,opPat = str->this.operatorMap.containsKey(str);
		Predicate<String> elePat = numPat.or(unknownPat).or(opPat);
		Unknown unknownObj = new Unknown(opdStack);
		var buffer = new CharArrayWriter();
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
					sufix.add(unknownObj);
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
							Operator nextOperator = Expression.this.operatorMap.get(realOpStr);
							Operator topOperator = stack.peek();
							for(int priority = nextOperator.getInStackPriority();topOperator.getOutStackPriority()>=priority;topOperator = stack.peek())
							{
								if(realOpStr.equals("#")&&(stack.size()==1))
									break;
								strSuffix.append(topOperator + " ");
								Operator topClone = (Operator)topOperator.clone();
								topClone.setStack(Expression.this.opdStack);
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
					throw new IllegalArgumentException("The infix string cannot be scanned correctly, maybe you should check your orthography of operators and unknown or check if you have separate neighboring operands and operators by whitespace characters. The inputed expression is: \n\t" + this.infix);
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
			throw new IllegalArgumentException("The infix string cannot be scanned correctly, maybe you should check your orthography of operators and unknown or check if you have separate neighboring operands and operators by whitespace characters. The inputed expression is: \n\t" + this.infix);
		}
		this.strSufix = strSuffix.toString().trim();
		//System.out.println("node 5: " + strSufix);
		
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
		try
		{
			return new Expression(this.operator.substring(0,this.operator.length()-1),this.inStackPriority,this.outStackPriority,this.infix,this.operatorMap,this.x);
		}
		catch(IOException e) {}
		finally
		{
			return null;
		}
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
