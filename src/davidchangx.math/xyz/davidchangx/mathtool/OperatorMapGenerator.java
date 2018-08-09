//OperatorMapGenerator.java
package xyz.davidchangx.mathtool;
import java.util.HashMap;
import xyz.davidchangx.algorithms.math.operator.Operator;
import java.util.regex.Pattern;
import java.io.File;
import java.util.Scanner;
import java.lang.reflect.InvocationTargetException;
import java.io.FileNotFoundException;
/**
 * Tool class to generate operator map required by Expression conveniently.
 *
 * Required a list of available Operator classes. The list shoule be in format:
 *
 * <code>
 * +	xyz.davidchangx.algorithms.math.operator.Plus	basic
 * -	xyz.davidchangx.algorithms.math.operator.Minus	basic
 * </code>
 *
 * which consist of lines like "format_in_expression_string\tcomplete_classname\toptional_group_info".
 *
 * We suggest that you provide a group info field so that you can use {@link getOperatorMapByGroupPattern(File,String)} method to generate operator map more conveniently. 
 *
 * We provide package {@code xyz.davidchangx.algorithms.math.operator} as the default {@link Operator} library and "operators.lst" as a list sample. You can directly use this list file when you use this class to generate operator maps with {@link Operator}-s in {@code xyz.davidchangx.algorithms.math.operator}. 
 *
 * @version 2.1
 * @author David Chang
 */
public class OperatorMapGenerator
{
	/**
	 * Generates an operator map containing all the operators listed in the given operator list. 
	 *
	 * @param operatorList the operator list
	 * @throws ClassNotFoundException if one operator class file wasn't found
	 * @throws NoSuchMethodException if the proper construction method wasn't found
	 * @throws InstantiationException if one operator object cannot be instantiated correctly
	 * @throws IllegalAccessException if {@code OperatorMapGenerator} doesn't have the privilege to access an operator class or its construction method. 
	 * @throws InvocationTargetException
	 * @throws FileNotFoundException if the given operator list file wasn't found
	 */
	public static HashMap<String,Operator> getCompleteOperatorMap(File operatorList) throws ClassNotFoundException,NoSuchMethodException,InstantiationException,IllegalAccessException,InvocationTargetException,FileNotFoundException
	{
		HashMap<String,Operator> operatorMap = new HashMap<>();
		Scanner opFinder = new Scanner(operatorList);
		opFinder.useDelimiter("(\\n\\r?|\\r)+");
		for(;opFinder.hasNext();)
		{
			String opDefStat = opFinder.next();
			String[] opDefStr = opDefStat.split(";"); //ignore the commet
			if(opDefStr[0].isEmpty())
				continue;
			String[] opDefs = opDefStr[0].split("\\s");
			String opChar = opDefs[0];
			String className = opDefs[1];
			operatorMap.put(opChar,(Operator)(Class.forName(className).getDeclaredConstructor().newInstance()));
		}
		return operatorMap;
	}
	/**
	 * Generates an operator map containing the operators belonging to the group name of which matches the given regex pattern. 
	 *
	 * @param operatorList the operator list
	 * @param opGroupPattern the regex pattern to determine the target group
	 * @throws ClassNotFoundException if one operator class file wasn't found
	 * @throws NoSuchMethodException if the proper construction method wasn't found
	 * @throws InstantiationException if one operator object cannot be instantiated correctly
	 * @throws IllegalAccessException if {@code OperatorMapGenerator} doesn't have the privilege to access an operator class or its construction method. 
	 * @throws InvocationTargetException
	 * @throws FileNotFoundException if the given operator list file wasn't found
	 */
	public static HashMap<String,Operator> getOperatorMapByGroupPattern(File operatorList,String opGroupPattern) throws ClassNotFoundException,NoSuchMethodException,InstantiationException,IllegalAccessException,InvocationTargetException,FileNotFoundException
	{
		HashMap<String,Operator> operatorMap = new HashMap<>();
		Scanner opFinder = new Scanner(operatorList);
		opFinder.useDelimiter("(\\n\\r?|\\r)+");
		Pattern p = Pattern.compile(opGroupPattern);
		var pre = p.asPredicate();
		for(;opFinder.hasNext();)
		{
			String opDefStat = opFinder.next();
			String[] opDefStr = opDefStat.split(";"); //ignore the commet
			if(opDefStr[0].isEmpty())
				continue;
			String[] opDefs = opDefStr[0].split("\\s");
			String opChar = opDefs[0];
			String className = opDefs[1];
			if(opDefs.length>2)
			{
				String opGroup = opDefs[2];
				if(pre.test(opGroup))
					operatorMap.put(opChar,(Operator)(Class.forName(className).getDeclaredConstructor().newInstance()));
			}
		}
		return operatorMap;
	}
	/**
	 * Generates an operator map containing the operators belonging to the group "basic".
	 *
	 * @param operatorList the operator list
	 * @throws ClassNotFoundException if one operator class file wasn't found
	 * @throws NoSuchMethodException if the proper construction method wasn't found
	 * @throws InstantiationException if one operator object cannot be instantiated correctly
	 * @throws IllegalAccessException if {@code OperatorMapGenerator} doesn't have the privilege to access an operator class or its construction method. 
	 * @throws InvocationTargetException
	 * @throws FileNotFoundException if the given operator list file wasn't found
	 */
	public static HashMap<String,Operator> getBasicOperatorMap(File operatorList) throws ClassNotFoundException,NoSuchMethodException,InstantiationException,IllegalAccessException,InvocationTargetException,FileNotFoundException
	{
		return getOperatorMapByGroupPattern(operatorList,"basic");
	}
	/**
	 * Generates an operator map containing the operators belonging to the group "trigonometric".
	 *
	 * @param operatorList the operator list
	 * @throws ClassNotFoundException if one operator class file wasn't found
	 * @throws NoSuchMethodException if the proper construction method wasn't found
	 * @throws InstantiationException if one operator object cannot be instantiated correctly
	 * @throws IllegalAccessException if {@code OperatorMapGenerator} doesn't have the privilege to access an operator class or its construction method. 
	 * @throws InvocationTargetException
	 * @throws FileNotFoundException if the given operator list file wasn't found
	 */
	public static HashMap<String,Operator> getTrigonometricOperatorMap(File operatorList) throws ClassNotFoundException,NoSuchMethodException,InstantiationException,IllegalAccessException,InvocationTargetException,FileNotFoundException
	{
		return getOperatorMapByGroupPattern(operatorList,"trigonometric");
	}
	/**
	 * Generates an operator map containing the operators belonging to the group "inverse_trigonometric".
	 *
	 * @param operatorList the operator list
	 * @throws ClassNotFoundException if one operator class file wasn't found
	 * @throws NoSuchMethodException if the proper construction method wasn't found
	 * @throws InstantiationException if one operator object cannot be instantiated correctly
	 * @throws IllegalAccessException if {@code OperatorMapGenerator} doesn't have the privilege to access an operator class or its construction method. 
	 * @throws InvocationTargetException
	 * @throws FileNotFoundException if the given operator list file wasn't found
	 */
	public static HashMap<String,Operator> getInvTrigonometricOperatorMap(File operatorList) throws ClassNotFoundException,NoSuchMethodException,InstantiationException,IllegalAccessException,InvocationTargetException,FileNotFoundException
	{
		return getOperatorMapByGroupPattern(operatorList,"inverse_trigonometric");
	/**
	 * Generates an operator map containing the operators belonging to the group "hyperbolic".
	 *
	 * @param operatorList the operator list
	 * @param opGroupPattern the regex pattern to determine the target group
	 * @throws ClassNotFoundException if one operator class file wasn't found
	 * @throws NoSuchMethodException if the proper construction method wasn't found
	 * @throws InstantiationException if one operator object cannot be instantiated correctly
	 * @throws IllegalAccessException if {@code OperatorMapGenerator} doesn't have the privilege to access an operator class or its construction method. 
	 * @throws InvocationTargetException
	 * @throws FileNotFoundException if the given operator list file wasn't found
	 */
	}
	public static HashMap<String,Operator> getHyperbolicTrigonometricOperatorMap(File operatorList) throws ClassNotFoundException,NoSuchMethodException,InstantiationException,IllegalAccessException,InvocationTargetException,FileNotFoundException
	{
		return getOperatorMapByGroupPattern(operatorList,"hyperbolic");
	}
	/**
	 * Generates an operator map containing the operators belonging to the group "singular".
	 *
	 * @param operatorList the operator list
	 * @throws ClassNotFoundException if one operator class file wasn't found
	 * @throws NoSuchMethodException if the proper construction method wasn't found
	 * @throws InstantiationException if one operator object cannot be instantiated correctly
	 * @throws IllegalAccessException if {@code OperatorMapGenerator} doesn't have the privilege to access an operator class or its construction method. 
	 * @throws InvocationTargetException
	 * @throws FileNotFoundException if the given operator list file wasn't found
	 */
	public static HashMap<String,Operator> getSingularFunctionoOperatorMap(File operatorList) throws ClassNotFoundException,NoSuchMethodException,InstantiationException,IllegalAccessException,InvocationTargetException,FileNotFoundException
	{
		return getOperatorMapByGroupPattern(operatorList,"singular");
	}
	/**
	 * Generates an operator map containing the operators belonging to the group "logical".
	 *
	 * @param operatorList the operator list
	 * @throws ClassNotFoundException if one operator class file wasn't found
	 * @throws NoSuchMethodException if the proper construction method wasn't found
	 * @throws InstantiationException if one operator object cannot be instantiated correctly
	 * @throws IllegalAccessException if {@code OperatorMapGenerator} doesn't have the privilege to access an operator class or its construction method. 
	 * @throws InvocationTargetException
	 * @throws FileNotFoundException if the given operator list file wasn't found
	 */
	public static HashMap<String,Operator> getLogicalOperatorMap(File operatorList) throws ClassNotFoundException,NoSuchMethodException,InstantiationException,IllegalAccessException,InvocationTargetException,FileNotFoundException
	{
		return getOperatorMapByGroupPattern(operatorList,"logical");
	}
	/**
	 * Generates an operator map containing the operators belonging to the group "integer_theory".
	 *
	 * @param operatorList the operator list
	 * @throws ClassNotFoundException if one operator class file wasn't found
	 * @throws NoSuchMethodException if the proper construction method wasn't found
	 * @throws InstantiationException if one operator object cannot be instantiated correctly
	 * @throws IllegalAccessException if {@code OperatorMapGenerator} doesn't have the privilege to access an operator class or its construction method. 
	 * @throws InvocationTargetException
	 * @throws FileNotFoundException if the given operator list file wasn't found
	 */
	public static HashMap<String,Operator> getIntegerTheoryOperatorMap(File operatorList) throws ClassNotFoundException,NoSuchMethodException,InstantiationException,IllegalAccessException,InvocationTargetException,FileNotFoundException
	{
		return getOperatorMapByGroupPattern(operatorList,"integer_theory");
	}
}
