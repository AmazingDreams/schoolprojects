package calc;
import java.awt.event.*;
import java.awt.*;

/**
 * @file         ButtonHandler.java
 * @description  Handle's what happen's when the button is pressed.
 */
class ButtonHandler implements ActionListener {

	/**
	 * @var  CalcDisplay  Holds instance of the calculators display
	 */
	private CalcDisplay display;

	/**
	 * @var  String  Holds the last operator that was pressed
	 */
	private String lastOp;

	/**
	 * @var  String  Holds the string value of the number
	 */
	private String strVal;

	/**
	 * @var  double  The current total
	 */
	private double total;

	/**
	 * @var  double  Used to store the new numbers
	 */
	private double number;

	/**
	 * @var  boolean  Flag used to determine if OP is pressed for the first time
	 */
	private boolean firsttime;

	/**
	 * @var  boolean  Flag used to determine if an operator has been pressed
	 */
	private boolean operatorPressed;

	/**
	 * Initializes the button handler
	 *
	 * @param  CalcDisplay  The calculators display
	 */
	ButtonHandler( CalcDisplay d ) {
		display = d;
		firsttime = true;
		strVal = "";
	}

	/**
	 * This method is called automatically when a button is pressed
	 *
	 * @param  ActionEvent  The action event
	 */
	public void actionPerformed( ActionEvent e )
	{
		//store the instance of the button that was pressed
		ButtonComponent button = (ButtonComponent) e.getSource();

		//get the button's label
		String s = button.getLabel().trim();

		//determine if the button was a number or operator button
		if ( Character.isDigit(s.charAt(0)))
			handleNumber( s );
		else
			calculate( s );
	}

//determine's whether the operator is unary or binary
//and does calculation's according to operator type
	/**
	 * Determines whether the operator is unary or binary
	 * and does calculations according to the operator type
	 *
	 * @param  String  The operator pressed
	 */
	void calculate( String op )
	{
		operatorPressed = true;

		/**
		 * If it's the first time the button has been pressed after:
		 * - the program first starts
		 * - the equal sign has been pressed
		 * - or the calculator has been cleared
		 *
		 * set the first number displayed on the display to total
		 */
		if ( firsttime && ! Operator.isUnary( op ) ){
			total = getNumberOnDisplay();
			firsttime = false;
		}

		if ( Operator.isUnary( op ) ) {
			handleUnaryOp( op );
		}
		else if ( lastOp != null ) {
			handleBinaryOp( lastOp );
		}

		// Store the calculator's last op -- important for binary operators
		if ( !Operator.isUnary( op ) ) {
			 lastOp = op;
		}
	}

	/**
	 * This method handles unary operators
	 *
	 * @param  String  Operator
	 */
	void handleUnaryOp( String op )
	{
		// Negate the number on the display screen
		if ( op.equals( Operator.NEGATE )) {
			number = negate( getNumberOnDisplay()+"");
			display.append(  number + "" );

			return;
		}

		// Handle the dot operator
		if ( op.equals( Operator.DOT ) ) {
			handleDecPoint();

			return;
		}

		// Calculate the square root of the number on the display
		if ( op.equals( Operator.SQRT ) ) {
			number = Math.sqrt( getNumberOnDisplay() );
			display.append( number+"" );

			return;
		}

		// If a binary operator was pressed before the equals
		// complete the operation first
		if ( op.equals( Operator.EQUALS ) ) {
			if ( lastOp != null && !Operator.isUnary( lastOp ) ) {
				handleBinaryOp( lastOp );
			}

			lastOp = null;
			firsttime = true;

			return;
		}

		clear();
	}

	// handles operators that require two operands
	/**
	 * Handles operators that require two operands
	 *
	 * @param  String  Operator
	 */
	void handleBinaryOp( String op )
	{
		if ( op.equals( Operator.ADD ) )
			 total += number;
		else if ( op.equals( Operator.SUBTRACT ) )
			 total -= number;
		else if ( op.equals( Operator.MULTIPLY ) )
			 total *= number;
		else if ( op.equals( Operator.DIVIDE ) )
			 total /= number;
		else if ( op.equals( Operator.POW ) )
			 total = Math.pow( total, number );

		display.append( total+"" );
	}

	/**
	 * THis method is called each time a number is pressed
	 * A string object is used to concatenate each number pressed in succession
	 * which then is converted into a double data type
	 *
	 * @param  String  The pressed number
	 */
	void handleNumber( String s )
	{
		//concatenate to strVal if an operator was pressed before the current
		//button
		if ( ! operatorPressed) {
			strVal += s;

		// If an operator was pressed, clear strVal and store the first number
		// pressed
		} else {
			operatorPressed = false;
			strVal          = s;
		}

		// Convert strVal to double
		number = new Double( strVal ).doubleValue();
		display.append( strVal );
	}

	/**
	 * This method is called when the decimal point button has been pressed
	 */
	void handleDecPoint()
	{
		operatorPressed = false;

		// Put a decimal point at the end of strVal only if there is no
		// decimal point already
		if ( strVal.indexOf( "." ) < 0 ) {
			 strVal += Operator.DOT;
		}

		display.append( strVal );
	}

	/**
	 * Method used to negate a value
	 *
	 * @param   String  The value to negate
	 * @return  double  The negated value
	 */
	double negate( String s )
	{
		operatorPressed = false;

		// If number is a whole number, get rid of the '0' at the end of the
		// number to allow more number's to be added to the right of the
		// decimal point.
		if ( number == ( int ) number )
			s = s.substring( 0, s.indexOf( "." ) );

		// Add negative sign to the number if it doesn't exist
		if ( s.indexOf( "-" ) < 0 )
			strVal = "-" + s;

		// If a negative sign exist's, remove it
		else
			strVal = s.substring( 1 );

		return new Double( strVal ).doubleValue();
	}

	/**
	 * Get the number that is currently on the display screen and
	 * convert it to double
	 *
	 * @return  double  The number on the dsiplay screen
	 */
	double getNumberOnDisplay() {
		return new Double( display.getText() ).doubleValue();
	}

	/**
	 * Clear the screen and reset all variables
	 */
	void clear()
	{
		firsttime = true;
		lastOp = null;
		strVal = "";
		total = 0;
		number = 0;
		display.clear();
		display.append( "0" );
	}

}
