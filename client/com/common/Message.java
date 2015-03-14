package com.common;

import java.io.Serializable;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int type;
	public int callBackId;
	public String sender;
	public String content;
	public String recipient;
	public java.util.Date date;

	public Message(int type, String sender, String content, String recipient,
			int callBackId) {
		this.type = type;
		this.sender = sender;
		this.content = content;
		this.recipient = recipient;
		this.callBackId = callBackId;
		this.date = null;
	}

	@Override
	public String toString() {
		return "Type Code: " + type + "\nSender: " + sender + "\nContent: "
				+ content + "\nRecipient: " + recipient + "\nCallback Id: "
				+ callBackId + "\n";
	}

}
