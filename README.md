# MathTool

## Introduction

This library provides a Java module containing several simple math APIs: `expression`, `equation`, `regression` and `plottor`. 

`expression` is an API to provide a convenient way to convert a math expression in String to a computable model. `equation` is an API to solve the zero point of a function using "secant method". `regression` is an API to solve problem of simple linear regression. `plottor` is an API to draw the graph of a function. 

## API `expression`

To simply use this API you may need class `Expression` and `OperatorMapGenerator`. You may use `OperatorMapGenerator` to generate a `HashMap` with structure `<String,Operator>` needed by class Expression and use `Expression` to construct a computable math expression model. 

As the current API cannot correctly process negative number with negative sign ("-"), please always use the negative operator in used operator set instead of writing negative sign straightly in front of a number, aka. Please write "-(3)" instead of writing "-3" if you're using the default operator set provided by us. 

Here is an example: 

```java
var operatorMap = OperatorMapGenerator.getBasicOperatorMap(new File("operators.lst")); //generate operator map
Expression exp = new Expression("3+2",operatorMap); //construct an instance of Expression
exp.calculate(); //calculate the value
System.out.println(exp.getValue());
```

 Class `Expression` always tries to parse the longest substring from start of a string consisting of neighbouring punctuations as an operator. Sometimes several operators may confuse with each other, for example "|(" (`AbsoluteLeft`), "|" (`Or`), "(" (`LeftBracket`) may confuse. Remember to insert white space at proper position to avoid confusing and wrong parsing. 

 About the usage of `OperatorMapGenerator`, refer to [About `OperatorMapGenerator`](#about-operatormapgenerator). 

Also you can construct an `Expression` with unknown character, and use it as a math function. Our `Expression` class implements interface `DoubleUnaryOperator` so that you can use it as an instance of `DoubleUnaryOperator`. Here is an example: 

```java
var operatorMap = OperatorMapGenerator.getBasicOperatorMap(new File("operators.lst")); //generate operator map
Expression exp = new Expression("4*x+5",operatorMap,"x"); //construct an instance of Expression
exp.calculate(3); //calculate the value
System.out.println(exp.getValue());
DoubleStream.iterate(0,x->x<=20,x->x+1).map(exp).forEach(x->{System.out.print(x + " ");}); //use this Expression as a DoubleUnaryOperator
System.out.println();
```

We provide `MultiVariateExpression` to support a multivariate function as well. The interfaces of `MultiVariateExpression` is greatly similar to these of `Expression`. However, `MultiVariateExpression` cannot be used as an instance of `DoubleUnaryOperator`. Here is a simple example of `MultiVariateExpression`: 

```java
var operatorMap = OperatorMapGenerator.getOperatorMapByGroupPattern(new File("operators.lst"),"basic|trigonometric"); //generate operator map
var exp = new MultiVariateExpression("3*sin(2*x+4)-5*cos(y-3)",operatorMap,"x","y"); construct an instance of MultiVariateExpression
exp.calculate(3,4); //calculate the value
System.out.println(exp.getValue());
```

## API `equation`

This is a simple API to solve the zero point of a function. To use this API in your programme, you may need class `SecantRoot`. This is a tool class providing a static method to solve a root of a function: 

```java
int a = -1,b = 1; //the boundary of interval
int root;
root = SecantRoot.solve(a,b,x->x*x*x,0.001); //solve the root
System.out.println(root);
```

The `solve(double,double,DoubleUnaryOperator,double)` method requires two `double`s as the boundary of interval, a `DoubleUnaryOperator` as the model of a function and a `double` as the expected accuracy as parameters. 

And also we provide a simple executable `SolveEquation` which solves zero point of a function using tool `SecantRoot`. This executable requires an argument pointing to the path of operator list. About operator list, please refer to [About `OperatorMapGenerator`](#about-operatormapgenerator). 

## API `regression`

This is a simple API to solve several simple problems of linear regression. We provide a class `RegressionCalculator` to offer this function. You may use this class to construct a simple model for computation and compute for regression arguments. And this class can also be considered as an executable class so that you can simply execute this problem to solve several simple problems. And you can refer to `src/davidchangx.math/xyz/davidchangx/algorithms/math/RegressionCalculator.java` for a simple example. Also we will offer another demo below: 

```java
double[] x = DoubleStream.iterate(0,x->x<=20,x->x+1).toArray();
double[] y = Arrays.stream(x).map(x->5-4*x).toArray();
var regressionModel = new RegressionCalculator(x,y); //construct model and compute
System.out.println("y = " + regressionModel.getA() + " + " + regressionModel.getB() + " * x"); //print the linear model
y[3] = 3;
regressionModel.setData(x,y); //change the statistics data
regressionModel.calculate() //calculate
System.out.println("y = " + regressionModel.getA() + " + " + regressionModel.getB() + " * x"); //print the linear model
```

## API `plottor`

To simply use this API you may need class `FunctionDrawer`. With this class you can simply draw a graph of a function in a `JFrame`. Here is a simple example: 

```java
DoubleUnaryOperator function = x->x+4;
var plottor = new FunctionDrawer(function,800,400,0,0,30,30,.05); //construct FunctionDrawer and draw the graph
plottor.setFunction(x->x*x); //change the function and draw the graph
plottor.renderFunction(x->-x*x); //straightly render the new function on the old graph without clearing frame
```

And you can also execute class `FunctionDrawer` as an executable to draw a function graph simply by a command. This executable also requires a command argument pointing to the path of operator list because the class `Expression` is used in the main method.

## About `OperatorMapGenerator`

To offer better flexibility and portability, we allow user of `Expression` to contribute own operator set. Also we provide a default operator set. To use `Expression`, need to provide an operator map with structure `<String,Operator>`, in which `String` represent the `String` format of the operator, and `Operator` is an instance of subclass of class `Operator`. In some situations, to contribute such a map may be annoying and cumbersome, so we offer `OperatorMapGenerator` to help contributing the operator map. 

To use `OperatorMapGenerator` you need to provide an operator list in such a format: 

```
+	xyz.davidchangx.algorithms.math.operator.Plus	basic	;5,5,2
-	xyz.davidchangx.algorithms.math.operator.Minus	basic	;5,5,2
*	xyz.davidchangx.algorithms.math.operator.Multiply	basic	;8,8,2
/	xyz.davidchangx.algorithms.math.operator.Divide	basic	;8,8,2

&	xyz.davidchangx.algorithms.math.operator.And	logical	;4,4,2
|	xyz.davidchangx.algorithms.math.operator.Or	logical	;3,3,2
~	xyz.davidchangx.algorithms.math.operator.Not	logical	;15,1,1
(+)	xyz.davidchangx.algorithms.math.operator.Xor	logical	;3,3,2
```

1. The first column should be the string format of the operator. 
2. The second column should be the complete format of the class name. 
3. The third column is optional to provide the information about group. If you provide the information of the group, you can use the method `HashMap<String,Operator> getOperatorMapByGroupPattern(File,String)` to get the operator map. 
4. The fourth column after semicolon in my example is in form "in-stack priority, out-stack priority, operand count". `OperatorMapGenerator` doesn't interpret the content after a semicolon, so you can use a semicolon to lead the comments. That means you'd better not use a semicolon in the string format of you own operator if you will use `OperatorMapGenerator` as well, though our `Expression` supports all kinds of format of operators. 

You can refer to `operators.lst` for a strict normative example of operator list. I'm sure that you have a look at this file helps you to deal with class `Expression` and class `OperatorMapGenerator` better. 

## Customized Operator Suggestions

1. You need to set the in-stack priority, out-stack priority, operand count and group mode for your operator. 
2. For single binary operator obeying associative laws like "+" (plus), the in-stack and out-stack priorities may be equivalent. 
3. For single binary operator not obeying associative laws but obeying left associative law like "-" (minus), the in-stack priority should be less than or equivalent to out-stack priority. 
4. For single binary operator not obeying associative laws but obeying right associative law like "^" (power), the in-stack priority should be greater than out-stack priority. 
5. For operator with group mode `OperatorGroupMode.NEEDING_CLOSED` like "sin(", you should ensure its in-stack priority is almost the greatest (like 15) and its out-stack priority is the least (like 1 or 0). 
6. The operand count of an operator needing no operands and returning nothing like "," (comma) should be set to 1. 
7. For pseudo operator like "pi" to offer a science constant, you could set both its in-stack priority and out-stack priority to the greatest (like 15) and its operand count to 0. 


For a more detailed document of the API, please refer to `docs/index.html`. To get this document, execute

```
make docs
```

For a jar archive, execute

```
make jar
```
