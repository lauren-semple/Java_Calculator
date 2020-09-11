package cs260Project;

import java.awt.*;
import java.awt.event.*; 
import javax.swing.*; 
import java.lang.Math;

/**
 * @author Lauren Semple
 * @version 04/14/2020
 * This class models a simple calculator using the Swing toolkit and implementing the ActionListener interface
 */
public class Calculator extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; //had to add this to suppress an error
	//instance data
	private JButton buttons[] = new JButton[19];
	private String captions[] = {"1","2","3","+",
									"4","5","6","-",
									"7","8","9","/",
									"0",".","SQRT","*",
									"=","AC","Back"};
	private JPanel buttonPanel = new JPanel(new GridLayout(5, 4));
	private JTextField output = new JTextField(20);
	private Font  font  = new Font(Font.SANS_SERIF,  Font.BOLD, 16);
	Container frame;
	
	private String numberOne;
	private String numberTwo;
	private double result;
    private String operator;
    private boolean postResult; //used to allow new operation to start w/o pressing AC
	
    
    //instance methods
    
	/**
	 * Constructor which sets up the GUI of the Calculator and initializes instance data
	 * Constructor also gives each button an ActionListener
	 */
	public Calculator()
	{
		frame = getContentPane();
		for (int count=0;count<19;count++)
		{
			buttons[count] = new JButton(captions[count]);
			buttonPanel.add(buttons[count]);
			buttons[count].setPreferredSize(new Dimension(80, 60));
			buttons[count].addActionListener(this);
			buttons[count].setFont(font);
		}
		frame.setLayout(new BorderLayout());
		frame.add(output,BorderLayout.NORTH);
		
		frame.add(buttonPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
		this.numberOne = "";
	    this.numberTwo = "";
	    this.operator = "";
	    this.postResult = false;
	}
	
	/**
	 * This method is the event handler, and responds to each possible button press on the calculator
	 */
	public void actionPerformed(ActionEvent ae) 
    {
		//gets the value of the most recent button press
		String currentButton = ae.getActionCommand();
		
		//if the bottom is a number or the decimal the digit is store
		if((currentButton.charAt(0) >= '0' && currentButton.charAt(0) <= '9') || currentButton == ".")
		{
			//if the operator has already been pressed digit is stored in second number
			if (!operator.equals(""))
			{
				this.numberTwo = numberTwo + currentButton;
			}
			//checks if a equal has just been performed allowing new calculation to start w/o pressing AC
			//tells calculator to override instead of append for first letter
			else if (postResult == true)
			{
				this.numberOne = currentButton;
				postResult = false;
			}
			//if no operator is set the digit is stored in the first number
			else
			{
				this.numberOne = numberOne + currentButton;
			}
			
			//current input is displayed on screen
			output.setText(numberOne + operator + numberTwo);
		}
		
		//the following four if statements set the operator to the value pressed and display input
		if(currentButton == "+")
		{
			this.operator = "+";
			//current input is displayed on screen
			output.setText(numberOne + operator + numberTwo);
		}
		
		if(currentButton == "-")
		{
			this.operator = "-";
			//current input is displayed on screen
			output.setText(numberOne + operator + numberTwo);
		}
		
		if(currentButton == "*")
		{
			this.operator = "*";
			//current input is displayed on screen
			output.setText(numberOne + operator + numberTwo);
		}
		
		if(currentButton == "/")
		{
			this.operator = "/";
			//current input is displayed on screen
			output.setText(numberOne + operator + numberTwo);
		}
			
		//sqrt is a unary operator so it is calculate on the press of the sqrt button
		if (currentButton == "SQRT")
		{
			double temp = Double.parseDouble(numberOne);
			result = Math.sqrt(temp);
			//output stored in number one so it can be used in further calculation
			this.numberOne = String.valueOf(result);
			//displaying output
			output.setText(String.valueOf(result));
		}
		
		//equals button performs operation and allows result to be operated on
		if (currentButton == "=")
		{
			if (this.operator == "+")
			{
				this.result = Double.parseDouble(numberOne) + Double.parseDouble(numberTwo);
				//displaying output
				output.setText(String.valueOf(result));
				//output stored in number one so it can be used in further calculation
				this.numberOne = String.valueOf(result);
				//resetting values for new operation
				this.numberTwo = "";
				this.operator = "";
				this.postResult = true; //allows new operation to start w/o pressing AC
			}
			else if (this.operator == "-")
			{
				this.result = Double.parseDouble(numberOne) - Double.parseDouble(numberTwo);
				//displaying output
				output.setText(String.valueOf(result));
				//output stored in number one so it can be used in further calculation
				this.numberOne = String.valueOf(result);
				//resetting values for new operation
				this.numberTwo = "";
				this.operator = "";
				this.postResult = true; //allows new operation to start w/o pressing AC
			}
			else if (this.operator == "*")
			{
				this.result = Double.parseDouble(numberOne) * Double.parseDouble(numberTwo);
				//displaying output
				output.setText(String.valueOf(result));
				//output stored in number one so it can be used in further calculation
				this.numberOne = String.valueOf(result);
				//resetting values for new operation
				this.numberTwo = "";
				this.operator = "";
				this.postResult = true; //allows new operation to start w/o pressing AC
			}
			else if (this.operator == "/")
			{
				double denom = Double.parseDouble(numberTwo);
				//note: because we are dividing by a double the result of a divide by zero is infinity
				
				try
				{
					this.result = Double.parseDouble(numberOne) / denom;
					//displaying output
					output.setText(String.valueOf(result));
					//output stored in number one so it can be used in further calculation
					this.numberOne = String.valueOf(result);
					//resetting values for new operation
					this.numberTwo = "";
					this.operator = "";
					this.postResult = true; //allows new operation to start w/o pressing AC
				}
				catch (ArithmeticException e) //catching divide by zero integer error
				{
					output.setText("Error");
					this.numberOne = "";
					this.numberTwo = "";
					this.operator = "";
				}	
			}
		}
		
		//clears screen and memory
		if (currentButton == "AC")
		{
			this.numberOne = "";
			this.numberTwo = "";
			this.operator = "";
			this.postResult = false;
			
			//current input is displayed on screen
			output.setText(numberOne + operator + numberTwo);
		}
		
		//allows user to delete last input
		if (currentButton == "Back")
		{
			//numberTwo becomes substring minus last input
			if (!numberTwo.equals(""))
			{
				this.numberTwo = numberTwo.substring(0, numberTwo.length()-1);
				//current input is displayed on screen
				output.setText(numberOne + operator + numberTwo);
			}
			//operator is cleared
			else if (!operator.equals(""))
			{
				this.operator = "";
				//current input is displayed on screen
				output.setText(numberOne + operator + numberTwo);
			}
			//numberOne becomes substring minus last input
			else
			{
				this.numberOne = numberOne.substring(0, numberOne.length()-1);
				//current input is displayed on screen
				output.setText(numberOne + operator + numberTwo);
			}
		}
    }
}
