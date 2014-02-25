package calc;

/**
 * Operator.java
 * Utility class used for the calculator's operators
 */
class Operator {

	/**
	 * Describe the different operators
	 */
	final static String ADD      = "+",
	                    SUBTRACT = "-",
	                    MULTIPLY = "x",
	                    DIVIDE   = "/",
	                    POW      = "pow",
	                    SQRT     = "sqrt",
	                    CLEAR    = "C",
	                    EQUALS   = "=",
	                    NEGATE   = "+/-",
	                    DOT      = ".",
	                    LOG      = "log",
	                    COS      = "cos",
	                    SIN      = "sin",
	                    TAN      = "tan";

	/**
	 * Is private to prevent class from being instantiated
	 */
	private Operator() {}

	/**
	 * Determine if the operator is unary
	 * (operator that needs only one operand)
	 *
	 * @param   String   The operator string
	 * @return  boolean  Whether the operator is unary
	 */
	static boolean isUnary( String s )
	{
		return s.equals( EQUALS )
			|| s.equals( CLEAR )
			|| s.equals( SQRT )
			|| s.equals( NEGATE )
			|| s.equals( DOT )
			|| s.equals( LOG )
			|| s.equals( COS )
			|| s.equals( SIN )
			|| s.equals( TAN );
	}

}
