package com.kuetchat.client.utility;

import javax.swing.SwingWorker;

public class BackgroundWork extends SwingWorker<Long, Object> {
	BackgroundWorkable workable;

	public BackgroundWork(BackgroundWorkable w) {
		workable = w;
	}

	@Override
	protected Long doInBackground() {

		return workable.donInBackground();
	}

	@Override
	protected void done() {
		super.done();
		workable.finished();
	}

}
