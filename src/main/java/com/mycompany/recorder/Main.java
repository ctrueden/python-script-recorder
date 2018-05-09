package com.mycompany.recorder;

import org.scijava.Context;
import org.scijava.command.CommandService;
import org.scijava.ui.UIService;

public class Main {
	public static void main(final String... args) throws Exception {
		final Context context = new Context();
		context.service(UIService.class).showUI();
		context.service(CommandService.class).run(PythonRecorderCommand.class, true);
	}
}