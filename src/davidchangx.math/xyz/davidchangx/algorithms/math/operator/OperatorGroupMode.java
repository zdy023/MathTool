//OperatorGroupMode.java
package xyz.davidchangx.algorithms.math.operator;
/**
 * The operator group mode.
 *
 * This enum type is to determine whether an operator is a SINGLE one, a NEEDING_CLOSED one or a CLOSING_ONE. A SINGLE one means it doesn't belong to any combined operator and is a single unary or multivariate operator such as "+", "-", "*", "/", "mod". A NEEDING_CLOSED one means it's the left part of a combined operator and needs closed such as "sin(", "arcsin(", "relu(". A CLOSING_ONE means it's the right part of a combined operator and could close a NEEDING_CLOSED operator such as ")", "]". Always, the CLOSING_ONE operator won't be appended to suffix expression. 
 *
 * @version 2.1
 * @author David Chang
 */
public enum OperatorGroupMode
{
	/** It means it's the right part of a combined operator that an operator is a CLOSING_ONE. */
	CLOSING_ONE, //operator used to close another operator, the right part of composite operators
	/** It means it's the left part of a combined operator that an operator is NEEDING_CLOSED. */
	NEEDING_CLOSED, //operator needed to be closed, the left part of composite operators
	/** It means it does'nt belong to any combined operator that an operator is a SINGLE one. */
	SINGLE; //single operator, not associated with other operators
}
