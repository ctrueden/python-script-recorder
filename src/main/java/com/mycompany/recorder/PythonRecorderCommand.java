/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

package com.mycompany.recorder;

import org.scijava.Context;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * A simple command for launching the Python script recorder.
 */
@Plugin(type = Command.class, menuPath = "Plugins>Utilities>Record Python...")
public class PythonRecorderCommand implements Command {

	@Parameter
	private Context context;

	@Override
	public void run() {
		final PythonRecorder recorder = new PythonRecorder(context);
		recorder.show();
	}

}
