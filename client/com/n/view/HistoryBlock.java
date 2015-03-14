package com.n.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class HistoryBlock extends JPanel {
	JLabel lblSender;
	JLabel lblDate;
	JTextArea textArea;
	JPanel fileHistoryPanel;
	Color color[] = { Color.black, new Color(34, 144, 220) };

	public HistoryBlock(String sender, String message, Date date, boolean isUser) {
		setLayout(new BorderLayout());

		setBackground(Color.white);

		// TextArea
		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 12));
		textArea.setEditable(false);
		textArea.setTabSize(4);
		textArea.setMargin(new Insets(0, 5, 0, 0));
		textArea.setBackground(Color.WHITE);
		textArea.setText(message);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JPanel textAreaHolder = new JPanel(new BorderLayout());
		textAreaHolder.add(textArea, BorderLayout.CENTER);
		// Sender
		lblSender = new JLabel(sender);
		lblSender.setHorizontalTextPosition(JLabel.LEFT);
		lblSender.setVerticalAlignment(SwingConstants.TOP);
		lblSender.setFont(new Font("Arial", Font.PLAIN, 13));

		JPanel senderHolder = new JPanel(new BorderLayout());
		senderHolder.setBackground(Color.white);
		senderHolder.setLayout(new BorderLayout());
		senderHolder.setBackground(Color.WHITE);
		senderHolder.setPreferredSize(new Dimension(130, 0));
		senderHolder.add(lblSender, BorderLayout.CENTER);
		// Date
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
		lblDate = new JLabel(dateFormat.format(date));
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate.setVerticalAlignment(SwingConstants.TOP);
		SimpleDateFormat f = new SimpleDateFormat(
				"EEEE, MMMM d, yyy, hh:mm:ss a");
		lblDate.setToolTipText(f.format(date));
		JPanel dateHolder = new JPanel(new BorderLayout());
		dateHolder.setBackground(Color.white);
		dateHolder.setLayout(new BorderLayout());
		dateHolder.setPreferredSize(new Dimension(100, 0));
		dateHolder.add(lblDate, BorderLayout.NORTH);

		if (!isUser) {
			lblDate.setForeground(color[1]);
			lblSender.setForeground(color[1]);
		}

		add(senderHolder, BorderLayout.WEST);
		add(textAreaHolder, BorderLayout.CENTER);
		add(dateHolder, BorderLayout.EAST);
		setSize(600, textArea.getPreferredSize().height);

	}
}
