#!/usr/bin/make -f

version = 6.0

algorithms_opt = opt/davidchangx.math/xyz/davidchangx/algorithms
equation_opt = $(algorithms_opt)/equation
math_opt = $(algorithms_opt)/math
operator_opt = $(math_opt)/operator

mathtool_opt = opt/davidchangx.math/xyz/davidchangx/mathtool

algorithms_src = src/davidchangx.math/xyz/davidchangx/algorithms
equation_src = $(algorithms_src)/equation
math_src = $(algorithms_src)/math
operator_src = $(math_src)/operator

mathtool_src = src/davidchangx.math/xyz/davidchangx/mathtool

javac = javac --module-source-path src -d opt --module-version $(version)
java = java -p opt -m davidchangx.math

# all modules

.PHONY: all expression mathtool operator equation plottor regression operatortool
all: expression mathtool operator
expression: $(math_opt)/Expression.class $(mathtool_opt)/OperatorMapGenerator.class $(math_opt)/MultiVariateExpression.class
mathtool: equation plottor regression
equation: $(mathtool_opt)/SolveEquation.class expression operator
plottor: $(mathtool_opt)/FunctionDrawer.class expression operator
regression: $(math_opt)/RegressionCalculator.class
operatortool: $(mathtool_opt)/OperatorMapGenerator.class

# module operator

.PHONY: basic trigonometric inverse_trigonometric hyperbolic singular logical all_operators
operator: $(operator_opt)/Operator.class $(mathtool_opt)/OperatorMapGenerator.class all_operators
all_operators: basic trigonometric inverse_trigonometric hyperbolic singular logical integer_theory

basic_ops = Lg Ln Exponential Plus Minus Modulo Multiply Divide Power LeftBracket RightBracket Pi AbsoluteLeft AbsoluteRight Negate Comma
trigonometric_ops = Cosine Sine Tangent
inverse_trigonometric_ops = ArcCosine ArcSine ArcTangent
hyperbolic_ops = HyperbolicCosine HyperbolicSine HyperbolicTangent
singular_ops = PositiveOrZero Sample Signature StepFunction Discretization DirichletFunction
logical_ops = And Or Not Xor IversonLeft IversonRight KroneckFunction Greater GreaterThanOrEquals Less LessThanOrEquals Equals NotEquals
integer_theory_ops = GCD LCM

basic: $(operator_opt)/Operator.class $(addprefix $(operator_opt)/,$(addsuffix .class,$(basic_ops)))
trigonometric: $(operator_opt)/Operator.class $(addprefix $(operator_opt)/,$(addsuffix .class,$(trigonometric_ops)))
inverse_trigonometric: $(operator_opt)/Operator.class $(addprefix $(operator_opt)/,$(addsuffix .class,$(inverse_trigonometric_ops)))
hyperbolic: $(operator_opt)/Operator.class $(addprefix $(operator_opt)/,$(addsuffix .class,$(hyperbolic_ops)))
singular: $(operator_opt)/Operator.class $(addprefix $(operator_opt)/,$(addsuffix .class,$(singular_ops)))
logical: $(operator_opt)/Operator.class $(addprefix $(operator_opt)/,$(addsuffix .class,$(logical_ops)))
integer_theory: $(operator_opt)/Operator.class $(addprefix $(operator_opt)/,$(addsuffix .class,$(integer_theory_ops)))

$(operator_opt)/Operator.class: $(math_opt)/ExpressionItem.class $(operator_opt)/OperatorGroupMode.class

# module expression

$(math_opt)/Expression.class $(math_opt)/MultiVariateExpression.class: $(math_opt)/ExpressionItem.class $(operator_opt)/Operator.class $(math_opt)/Operand.class $(math_opt)/Unknown.class $(operator_opt)/Head.class $(operator_opt)/Tail.class
$(operator_opt)/Head.class $(operator_opt)/Tail.class: $(operator_opt)/Operator.class
$(math_opt)/Operand.class $(mathtool_opt)/Unknown.class: $(math_opt)/ExpressionItem.class

# other modules

$(mathtool_opt)/SolveEquation.class: expression $(equation_opt)/SecantRoot.class
$(mathtool_opt)/FunctionDrawer.class: expression 

.PHONY: test_equation test_plottor test_regression
test_equation: equation
	$(java)/xyz.davidchangx.mathtool.SolveEquation operators.lst
test_plottor: plottor
	$(java)/xyz.davidchangx.mathtool.FunctionDrawer operators.lst
test_regression: regression
	$(java)/xyz.davidchangx.algorithms.math.RegressionCalculator

opt/%.class: src/%.java
	$(javac) $<

.PHONY: jar clean docs

.ONESHELL: jar
jar: all
	cd opt/davidchangx.math/
	jar --create --file=davidchangx.math.jar --module-version=$(version) .
	mv davidchangx.math.jar ../..

clean:
	- rm -rf opt
	- rm -f davidchangx.math.jar
	- rm -rf docs

docs: 
	javadoc --module davidchangx.math --module-source-path src -p opt -d docs -html5 -version -author
