package com.kuetchat.client.utility;

import com.common.Message;

public interface CallBackable {
	public void callBack(String exception);
	public void callBack(Message msg);
	public void send(Message msg);
}
