package math_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mv_GUI implements ActionListener {
	private JFrame frame = new JFrame();
	
	private JPanel panel;
	private JLabel userlabel;
	private JTextField userText;
	private JButton button = new JButton();

	public mv_GUI() {

		// the panel with the button and text
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(new GridLayout(0, 1));

		// User Label
		userlabel = new JLabel("User");
		userlabel.setBounds(10, 20, 80, 25);
		panel.add(userlabel);

		// User Textfield
		userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 35);
		panel.add(userText);

		// the clickable button
		button = new JButton("Click Me");
		button.addActionListener(this);
		panel.add(button);

		// set up the frame and display it
		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(500, 500);
		frame.setTitle("GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	// process the button clicks
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			userlabel.setText("HI :)");
		}

	}

	// create one Frame
	public static void main(String[] args) {
		new mv_GUI();
	}
}
