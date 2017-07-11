/* This program creates a calculator GUI equipped with an 
 * addition, multiplication, division, and subtraction option.
 */


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class calcGUI implements ActionListener {
	
	private JTextField result;              // private field to hold result and inserted numbers
	private boolean operationClicked;       // private field to show if operand was clicked
	private double num;                     // private field to hold initial number
	private int operation;     // private field to assign values to different operands(= 0 + 1 - 2 / 3 x 4)
	
	public static void main (String[] args) {
		new calcGUI().init();
	}
	
	/* This method initiates the GUI, it includes creating 
	 * the JFrame of the GUI, adding the text field
	 * and the buttons, and placing them.
	 */
	
	public void init() {
		num = 0.0;
		operation = 0;
		JFrame frame = new JFrame ("Calculator");
		frame.setSize(250, 400);
		JPanel pane = new JPanel (new GridLayout(4, 4));
		addClearFieldAndResultButton(frame);
		addButtons(pane);
		frame.add(pane, "Center");
		frame.setVisible(true);
	}
	
	// This method adds all the result text field and the clear button
	
	private void addClearFieldAndResultButton(JFrame frame) {
		result = new JTextField(16);
		frame.add(result, "North");
		JButton clear = new JButton ("Clear");
		clear.addActionListener(this);
		frame.add(clear,"South");
	}

	// This method adds all the buttons found on the calculator
	
	private void addButtons(JPanel pane) {
		addNumberButtons(pane, 1, 4);
		addButton(pane,  "+");
		addNumberButtons(pane, 4, 7);
		addButton(pane,  "-");
		addNumberButtons(pane, 7, 10);
		addButton(pane,  "x");
		addButton(pane,  "0");
		addButton(pane, ".");
		addButton(pane,  "=");
		addButton(pane,  "/");
	}

	private void addNumberButtons(JPanel pane, int startIndex, int endIndex) {
		for (int i = startIndex; i < endIndex; i ++)
			addButton(pane,  ""+i);
	}

	/* This method waits and responds to an
	 * action to be performed (a button to be pressed)
	 */

	private void addButton(JPanel pane, String label) {
		JButton button = new JButton (label);
		button.addActionListener(this);
		pane.add(button);
	}

	public void actionPerformed(ActionEvent event) {
		if (isNumber(event)) {                 // when number is clicked
			treatAsNumber(event);
		} else if (isDecimal(event)) {         // when decimal point is clicked
			treatAsDecimel();
		} else if (isAddition(event)) {        // when addition button is clicked
			operationClicked = true;
			doStoredOperationIfAnyThenAdd();
		} else if (isSubtraction(event)) {     // when subtraction is clicked
			operationClicked = true;
			doStoredOperationIfAnyThenSubtract();
		} else if (isDivision(event)) {        // when division is clicked
			operationClicked = true;
			doStoredOperationIfAnyThenDivide();
		} else if (isMultiplication(event)) {  // when multiplication is clicked
			operationClicked = true;
			doStoredOperationIfAnyThenMultiply();
		} else if (isEquation(event)) {        // when equals sign is clicked
			doStoredOperationAndFindResult();
		} else if (isClear(event)) {
			clearResultToStartNewCalculation();
		}
		
	}

	/* This method clears the result field and allows 
	 * the user to start a new calculation after clicking clear
	 */

	private void clearResultToStartNewCalculation() {
		result.setText("");
		operation = 0;                                         // reset at value of equals
		operationClicked = true;
	}

	/* This method finds the result of the required 
	 * equation after the user clicks equal sign
	 */
	
	private void doStoredOperationAndFindResult() {
		String text = result.getText();
		if (storedOperationIsAddition()) {                    // if equation was addition
			add(text);
			operation = 0;
		} else if (storedOperationIsSubtraction()) {             // if equation was subtraction
			subtract(text);
			operation = 0;
		} else if (storedOperationIsDivision()) {             // if equation was division
			divide(text);
			operation = 0;
		} else if (storedOperationIsMultiplication()) {             // if equation was multiplication
			multiply(text);
			operation = 0;                       // reset at value of equals
		}
		operationClicked = true;
	}

	/* This method performs the earlier stored equation, 
	 * then stores a new multiplication equation
	 */
	
	private void doStoredOperationIfAnyThenMultiply() {
		String text = result.getText();
		if (storedOperationIsAddition()) {                    // if earlier stored button was addition
			add(text);
			operation = 4;
		} else if (storedOperationIsSubtraction()) {             // if earlier stored button was subtraction
			subtract(text);
			operation = 4;
		} else if (storedOperationIsDivision()) {             // if earlier stored button was division
			divide(text);
			operation = 4;                      // reset value of multiplication to store for next button
		} else if (storedOperationIsEquality()) {            // if earlier stored button was equals
			num = 0;                            // reset values after using the equals button
			multiply(text);
		} else {
			multiply(text);                     // if there is repetitive multiplication
		}
	}

	/* This method performs the earlier stored equation, 
	 * then stores a new division equation
	 */
	
	private void doStoredOperationIfAnyThenDivide() {
		String text = result.getText();
		if (storedOperationIsAddition()) {
			add(text);
			operation = 3;
		} else if (storedOperationIsSubtraction()) {
			subtract(text);
			operation = 3;
		} else if (storedOperationIsMultiplication()) {               // if earlier stored button was multiplication
			multiply(text);
			operation = 3;
		} else if (storedOperationIsEquality()) {
			num = 0;
			divide(text);
		} else {                                   // if there is repetitive division
			divide(text);
		}
	}

	/* This method performs the earlier stored equation, 
	 * then stores a new subtraction equation
	 */
	
	private void doStoredOperationIfAnyThenSubtract() {
		String text = result.getText();
		if (storedOperationIsAddition()) {
			add(text);
			operation = 2;
		} else if (storedOperationIsDivision()) {
			divide(text);
			operation = 2;
		} else if (storedOperationIsMultiplication()) {
			multiply(text);
			operation = 2;
		} else if (storedOperationIsEquality()) {
			num = 0;
			subtract(text);
		} else {                                      // if there is repetitive subtraction
			subtract(text);
		}
	}

	/* This method performs the earlier stored equation, 
	 * then stores a new addition equation
	 */
	
	private void doStoredOperationIfAnyThenAdd() {
		String text = result.getText();
		if (storedOperationIsSubtraction()) {
			subtract(text);
			operation = 1;
		} else if (storedOperationIsDivision()) {
			divide(text);
			operation = 1;
		} else if (storedOperationIsMultiplication()) {
			multiply(text);
			operation = 1;
		} else if (storedOperationIsEquality()) {
			num = 0;
			add(text);
		} else {                                    // if there is repetitive addition
			add(text);
		}
	}

	//This method adds the decimal to the text field to form a decimal number
	
	private void treatAsDecimel() {
		String text = result.getText();
		if (!text.contains(".")) {
			text += ".";
			result.setText(text);
		}
	}

	/* This method puts the number clicked in the text 
	 * field depending on whether an operand was clicked. If an 
	 * operand was clicked, the number clicked removes the numbers 
	 * that were in the field and is placed instead of them. 
	 * If the operand is not clicked, the number is only added 
	 * to the already present sequence of numbers.
	 */
	
	private void treatAsNumber(ActionEvent event) {
		String number = event.getActionCommand();
		if (!operationClicked) {                       // to have the option of big numbers
			String text = result.getText();
			text += number;
			result.setText(text);
		} else {                                       // to replace numbers present
			result.setText(number);
			operationClicked = false;                  // to restart the process
		}		
	}

	/* This method multiplies two consecutively inserted 
	 * numbers after the multiplication button is clicked
	 */
	
	private void multiply(String text) {
		if (num == 0) {                                 // to store the first number
			num = Double.parseDouble(text);
		} else if (num != 0) {                          // to store second number and multiply
			double newNum = Double.parseDouble(text);
			num = num * newNum;
			result.setText("" + num);
		}
		operation = 4;                         // storing given multiplication value to perform function at next button
	}

	/* This method divides two consecutively inserted numbers 
	 * after the division button is clicked
	 */
	
	private void divide(String text) {
		if (num == 0) {
			num = Double.parseDouble(text);
		} else if (num != 0) {                          // to store second number and multiply
			double newNum = Double.parseDouble(text);
			num = num / newNum;
			result.setText("" + num);
		}
		operation = 3;                             // storing given division value to perform function at next button
	}

	/* This method adds two consecutively inserted numbers after the addition
	 * button is clicked
	 */
	
	private void add(String text) {
		if (num == 0) {
			num = Double.parseDouble(text);
		} else if (num != 0) {                         // to store second number and add
			double newNum = Double.parseDouble(text);
			num = num + newNum;
			result.setText("" + num);
		}
		operation = 1;                           // storing given addition value to perform function at next button
	}
	
	/* This method subtracts two consecutively inserted numbers after the subtraction
	 * button is clicked
	 */
	
	private void subtract(String text) {
		if (num == 0) {
			num = Double.parseDouble(text);
		} else if (num != 0) {                         // to store second number and subtract
			double newNum = Double.parseDouble(text);
			num = num - newNum;
			result.setText("" + num);
		}
		operation = 2;                          // storing given subtraction value to perform function at next button
	}

	// This method returns whether a button is division
	
	private boolean isDivision(ActionEvent ev) {
		return ev.getActionCommand().contains("/");
	}
	
	// This method returns whether a button is multiplication
	
	private boolean isMultiplication(ActionEvent ev) {
		return ev.getActionCommand().contains("x");
	}
	
	// This method returns whether a button is subtraction
	
	private boolean isSubtraction(ActionEvent ev) {
		return ev.getActionCommand().contains("-");
	}

	// This method returns whether a button is addition
	
	private boolean isAddition(ActionEvent ev) {
		return ev.getActionCommand().contains("+");
	}

	// This method returns whether a button is a decimal button
	
	private boolean isDecimal(ActionEvent ev) {
		return ev.getActionCommand().contains(".");
	}
	
	// This method returns whether a button is equals button
	
	private boolean isEquation(ActionEvent ev) {
		return ev.getActionCommand().contains("=");
	}
	
	// This method returns whether a button is the clear button

	private boolean isClear(ActionEvent ev) {
		return ev.getActionCommand().contains("Clear");
	}

	// This method returns whether a button is a number button
	
	public boolean isNumber(ActionEvent ev) {
		return !ev.getActionCommand().contains("+") && 
				!ev.getActionCommand().contains("-") &&
				 !ev.getActionCommand().contains("x") &&
				  !ev.getActionCommand().contains("/") &&
				   !ev.getActionCommand().contains("=") &&
				    !ev.getActionCommand().contains("Clear") &&
				     !ev.getActionCommand().contains(".");
	}

	// This method returns whether the operation is an equality, presented by the value of 0

	private boolean storedOperationIsEquality() {
		return operation == 0;
	}

	// This method returns whether the operation is multiplication, presented by the value of 4

	private boolean storedOperationIsMultiplication() {
		return operation == 4;
	}

	// This method returns whether the operation is division, presented by the value of 3

	private boolean storedOperationIsDivision() {
		return operation == 3;
	}

	// This method returns whether the operation is subtraction, presented by the value of 2

	private boolean storedOperationIsSubtraction() {
		return operation == 2;
	}

	// This method returns whether the operation is addition, presented by the value of 1

	private boolean storedOperationIsAddition() {
		return operation == 1;
	}
}
