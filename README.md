# MathTool

## Introduction

This library provides a Java module containing several simple math APIs: `expression`, `equation`, `regression` and `plottor`. 

`expression` is an API to provide a conveniet way to convert a math expression in String to a computable model. `equation` is an API to solve the zero point of a function using "secant method". `regression` is an API to solve problem of simple linear regression. `plottor` is an API to draw the graph of a function. 

## API `expression`

To simply use this API you may need class `Expression` and `OperatorMapGenerator`. You may use `OperatorMapGenerator` to generate a `HashMap` with structure `<String,Operator>` needed by class Expression and use `Expression` to construct a computable math expression model. Here is an example: 

```java
var operatorMap = OperatorMapGenerator.getBasicOperatorMap(new File("operators.lst")); //generate operator map
Expression exp = new Expression("3 + 2",operatorMap); //construct an instance of Expression
exp.solve(); //calculate the value
System.out.println(exp.getValue());
```

Remember to seperator each operand and operator by space or tab or other white space character. About the usage of `OperatorMapGenerator`, refer to [About `OperatorMapGenerator`]. 

Also you can construct an `Expression` with unknown character, and use is as a math function. Our `Expression` class implements interface `DoubleUnaryOperator` so that you can use is as an instance of `DoubleUnaryOperator`. Here is an example: 

```java
var operatorMap = OperatorMapGenerator.getBasicOperatorMap(new File("operators.lst")); //generate operator map
Expression exp = new Expression("4 * x + 5",operatorMap,'x'); //construct an instance of Expression
exp.solve(3); //calculate the value
System.out.println(exp.getValue());
DoubleStream.iterate(0,x->x<=20,x->x+1).map(exp).forEach(x->{System.out.print(x + " ");}); //use this Expression as a DoubleUnaryOperator
System.out.println();
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

And also we provide a simple executable `SolveEquation` which solves zero point of a function using tool `SecantRoot`. This executable requires an argument pointing to the path of operator list. About operator list, please refer to [About `OperatorMapGenerator`]. 

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
4. The fourth column after semicolon in my exsample is in form "in-stack priority, out-stack priority, operand count". `OperatorMapGenerator` doesn't interpret the content after a semicolon, so you can use a semicolon to lead the comments. That means you'd better not use a semicolon in the string format of you own operator if you will use `OperatorMapGenerator` as well, though our `Expression` supports all kinds of format of operators. 

You can refer to `operators.lst` for a strict normative example of operator list. I suggest that you have a look at this file help you to deal with class `Expression` and class `OperatorMapGenerator` better. 


For a more detailed documation of the API, please refer to `docs/index.html`. To get this documation, execute

```
make docs
```

For a jar archieve, execute

```
make jar
```