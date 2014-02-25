package calc;

/**
 * Memory.java
 * Utility class used for the calculators memory buttons
 */
class Memory {

	/**
	 * Describe the different buttons
	 */
	final static String CLEAR    = "MC",
	                    READ     = "MR",
	                    STORE    = "MS",
	                    ADD      = "M+",
	                    SUBTRACT = "M-";

	/**
	 * Is private to prevent class from being instantiated
	 */
	private Memory() {}

	/**
	 * Determine whether the given string belongs to a memory button
	 *
	 * @return  boolean  Belongs to a memory button or not
	 */
	public static boolean isMemoryButton(String s) {
		return s.equals( CLEAR )
			|| s.equals( READ )
			|| s.equals( STORE )
			|| s.equals( ADD )
			|| s.equals( SUBTRACT );
	}

}
