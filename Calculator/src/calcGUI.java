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
	
	/* This method initiates the GUI, it includes creating the JFrame of the GUI, adding the text field
	 * and the buttons, and placing them.
	 */
	
	public void init() {
		num = 0;
		operation = 0;
		JFrame frame = new JFrame ("Calculator");
		frame.setSize(250, 400);
		result = new JTextField(16);
		JPanel pane = new JPanel (new GridLayout(4, 4));
		addButtons(pane);
		frame.add(pane, "Center");
		frame.add(result, "North");
		frame.setVisible(true);
	}
	
	// This method adds all the buttons found on the calculator
	
	private void addButtons(JPanel pane) {
		addButtonsOneToThree(pane);
		addAdditionButton(pane);
		addButtonsFourToSix(pane);
		subtractButton(pane);
		addButtonsSevenToNine(pane);
		addMultiplicationButton(pane);
		addZeroButton(pane);
		addDecimelButton(pane);
		addEqualsButton(pane);
		addDivideButton(pane);		
	}

	// This method adds the division button
	
	private void addDivideButton(JPanel pane){
		JButton divide = new JButton ("/");
		divide.addActionListener(this);
		pane.add(divide);		
	}

	// This method adds the equals buttons
	
	private void addEqualsButton(JPanel pane) {
		JButton equals = new JButton ("=");
		equals.addActionListener(this);
		pane.add(equals);
		
	}

	// This method adds the decimal button 
	
	private void addDecimelButton(JPanel pane) {
		JButton decimal = new JButton (".");
		decimal.addActionListener(this);
		pane.add(decimal);
		
	}

	// This method adds the 0 button
	
	private void addZeroButton(JPanel pane) {
		JButton button = new JButton ("0");
		button.addActionListener(this);
		pane.add(button);		
	}

	// This method adds the multiplication button
	
	private void addMultiplicationButton(JPanel pane) {
		JButton multiply = new JButton ("x");
		multiply.addActionListener(this);
		pane.add(multiply);
		
	}

	// This method adds the subtraction button
	
	private void subtractButton(JPanel pane) {
		JButton subtract = new JButton ("-");
		subtract.addActionListener(this);
		pane.add(subtract);		
	}

	// This method adds the addition button
	
	private void addAdditionButton(JPanel pane) {
		JButton add = new JButton ("+");
		add.addActionListener(this);
		pane.add(add);		
	}

	// This method adds the 7 to 9 buttons
	
	private void addButtonsSevenToNine(JPanel pane) {
		for (int i = 7; i < 10; i ++) {
			JButton button = new JButton ("" + i);
			button.addActionListener(this);
			pane.add(button);
		}		
	}

	// This method adds the 4 t0 6 buttons 
	
	private void addButtonsFourToSix(JPanel pane) {
		for (int i = 4; i < 7; i ++) {
			JButton button = new JButton ("" + i);
			button.addActionListener(this);
			pane.add(button);
		}		
	}

	// This method adds the 1 - 3 buttons
	
	public void addButtonsOneToThree(JPanel pane) {
		for (int i = 1; i < 4; i ++) {
			JButton button = new JButton ("" + i);
			button.addActionListener(this);
			pane.add(button);
		}
	}

	// This method waits and responds to an action to be performed (a button to be pressed)
	
	public void actionPerformed(ActionEvent event) {
		if (isNumber(event)) {                 // when number is clicked
			treatAsNumber(event);
		} else if (isDecimal(event)) {         // when decimal point is clicked
			treatAsDecimel(event);
		} else if (isAddition(event)) {        // when addition button is clicked
			operationClicked = true;
			treatAsAddition(event);
		} else if (isSubtraction(event)) {     // when subtraction is clicked
			operationClicked = true;
			treatAsSubtraction(event);
		} else if (isDivision(event)) {        // when division is clicked
			operationClicked = true;
			treatAsDivision(event);
		} else if (isMultiplication(event)) {  // when multiplication is clicked
			operationClicked = true;
			treatAsMultiplication(event);
		} else if (isEquation(event)) {        // when equals sign is clicked
			findResult(event);
		}
		
	}
	
	// This method finds the result of the required equation after the user clicks equal sign
	
	private void findResult(ActionEvent event) {
		String text = result.getText();
		if (operation == 1) {                    // if equation was addition
			add(text);
			operation = 0;
		} else if (operation == 2) {             // if equation was subtraction
			subtract(text);
			operation = 0;
		} else if (operation == 3) {             // if equation was division
			divide(text);
			operation = 0;
		} else if (operation == 4) {             // if equation was multiplication
			multiply(text);
			operation = 0;                       // reset at value of equals
		}
		operationClicked = true;
	}

	/* This method performs the earlier stored equation, then stores a new multiplication
	 * equation
	 */
	
	private void treatAsMultiplication(ActionEvent event) {
		String text = result.getText();
		if (operation == 1) {                    // if earlier stored button was addition
			add(text);
			operation = 4;
		} else if (operation == 2) {             // if earlier stored button was subtraction
			subtract(text);
			operation = 4;
		} else if (operation == 3) {             // if earlier stored button was division
			divide(text);
			operation = 4;                      // reset value of multiplication to store for next button
		} else if (operation == 0) {            // if earlier stored button was equals
			num = 0;                            // reset values after using the equals button
			multiply(text);
		} else {
			multiply(text);                     // if there is repetitive multiplication
		}
	}

	/* This method performs the earlier stored equation, then stores a new division
	 * equation
	 */
	
	private void treatAsDivision(ActionEvent event) {
		String text = result.getText();
		if (operation == 1) {
			add(text);
			operation = 3;
		} else if (operation == 2) {
			subtract(text);
			operation = 3;
		} else if (operation == 4) {               // if earlier stored button was multiplication
			multiply(text);
			operation = 3;
		} else if (operation == 0) {
			num = 0;
			divide(text);
		} else {                                   // if there is repetitive division
			divide(text);
		}
	}

	/* This method performs the earlier stored equation, then stores a new subtraction
	 * equation
	 */
	
	private void treatAsSubtraction(ActionEvent event) {
		String text = result.getText();
		if (operation == 1) {
			add(text);
			operation = 2;
		} else if (operation == 3) {
			divide(text);
			operation = 2;
		} else if (operation == 4) {
			multiply(text);
			operation = 2;
		} else if (operation == 0) {
			num = 0;
			subtract(text);
		} else {                                      // if there is repetitive subtraction
			subtract(text);
		}
	}

	/* This method performs the earlier stored equation, then stores a new addition
	 * equation
	 */
	
	private void treatAsAddition(ActionEvent event) {
		String text = result.getText();
		if (operation == 2) {
			subtract(text);
			operation = 1;
		} else if (operation == 3) {
			divide(text);
			operation = 1;
		} else if (operation == 4) {
			multiply(text);
			operation = 1;
		} else if (operation == 0) {
			num = 0;
			add(text);
		} else {                                    // if there is repetitive addition
			add(text);
		}
	}

	//This method adds the decimal to the text field to form a decimal number
	
	private void treatAsDecimel(ActionEvent event) {
		String text = result.getText();
		text += ".";
		result.setText(text);
	}

	/*This method puts the number clicked in the text field depending on whether
	 * an operand was clicked. If an operand was clicked, the number clicked removes
	 * the numbers that were in the field and is placed instead of them. If the operand
	 * is not clicked, the number is only added to the already present sequence of numbers.
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

	/* This method multiplies two consecutively inserted numbers after the multiplication
	 * button is clicked
	 */
	
	private void multiply(String text) {
		if (num == 0.0) {                                 // to store the first number
			num = Double.parseDouble(text);
		} else if (num != 0.0) {                          // to store second number and multiply
			double newNum = Double.parseDouble(text);
			num = num * newNum;
			result.setText("" + num);
		}
		operation = 4;                         // storing given multiplication value to perform function at next button
	}

	/* This method divides two consecutively inserted numbers after the division
	 * button is clicked
	 */
	
	private void divide(String text) {
		if (num == 0.0) {
			num = Double.parseDouble(text);
		} else if (num != 0.0) {                          // to store second number and multiply
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
		if (num == 0.0) {
			num = Double.parseDouble(text);
		} else if (num != 0.0) {                         // to store second number and add
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
		if (num == 0.0) {
			num = Double.parseDouble(text);
		} else if (num != 0.0) {                         // to store second number and subtract
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

	// This method returns whether a button is a number button
	
	public boolean isNumber(ActionEvent ev) {
		return !ev.getActionCommand().contains("+") && 
				!ev.getActionCommand().contains("-") &&
				 !ev.getActionCommand().contains("x") &&
				  !ev.getActionCommand().contains("/") &&
				   !ev.getActionCommand().contains("=");
	}
	

}
