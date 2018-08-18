//SolveEquation.java
package xyz.davidchangx.mathtool;
import xyz.davidchangx.algorithms.equation.SecantRoot;
import java.util.Scanner;
import xyz.davidchangx.algorithms.math.Expression;
import java.util.HashMap;
import xyz.davidchangx.algorithms.math.operator.Operator;
import java.lang.reflect.InvocationTargetException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * A simple application to solve the roots of a function using class {@link xyz.davidchangx.algorithms.equation.SecantRoot}.
 *
 * @version 2.1
 * @author David Chang
 */
public class SolveEquation
{
	/**
	 * This is a simple executable to solve the roots of a function. 
	 */
	public static void main(String[] args)
	{
		HashMap<String,Operator> operatorMap = new HashMap<>();
		try
		{
			operatorMap = OperatorMapGenerator.getCompleteOperatorMap(new File(args[0]));
		}
		catch(ClassNotFoundException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(NoSuchMethodException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(InstantiationException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(IllegalAccessException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(InvocationTargetException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Please input the path of operator list.");
			System.out.println(e);
			System.exit(0);
		}
		Scanner s = new Scanner(System.in);
		System.out.print("Please input an expected interval with root as [a,b]: \n\ta=");
		double a = s.nextDouble();
		System.out.print("\tb=");
		double b = s.nextDouble();
		System.out.print("Please input the expected accuracy: ");
		double accuracy = s.nextDouble();
		System.out.print("Please input the function (f(x)): \n\ty = ");
		s.useDelimiter("\\n|\\r|\\n\\r");
		try
		{
			Expression function = new Expression(s.next(),operatorMap,'x');
			double root = SecantRoot.solve(a,b,function,accuracy);
			System.out.println("The root is " + root + ". ");
		}
		catch(ArithmeticException e)
		{
			System.out.println(e);
			System.out.println("The secant method does not work or your function is wrong!");
			System.exit(0);
		}
		catch(IllegalArgumentException e)
		{
			System.out.println(e);
			System.exit(0);
		}
		catch(IOException e)
		{
			System.out.println(e);
			System.exit(0);
		}
	}
}
