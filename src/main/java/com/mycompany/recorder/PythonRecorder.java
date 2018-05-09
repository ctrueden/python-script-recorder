
package com.mycompany.recorder;

import java.awt.BorderLayout;
import java.security.Provider.Service;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

import org.scijava.Context;
import org.scijava.event.EventHandler;
import org.scijava.log.LogService;
import org.scijava.module.Module;
import org.scijava.module.ModuleItem;
import org.scijava.module.event.ModuleExecutedEvent;
import org.scijava.plugin.Parameter;

public class PythonRecorder {

	private JFrame frame;
	private JTextPane textPane;

	@Parameter
	private LogService log;


	public PythonRecorder(final Context context) {
		context.inject(this);
	}

	public void show() {
		frame = new JFrame("Python Recorder");
		final JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		textPane = new JTextPane();
		textPane.setText("#@ ModuleService module\n");
		contentPane.add(new JScrollPane(textPane), BorderLayout.CENTER);
		frame.setContentPane(contentPane);
		frame.setSize(700, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	@EventHandler
	protected void onEvent(final ModuleExecutedEvent evt) {
		if (!frame.isVisible()) return;
		final Module m = evt.getModule();
		final String id = m.getInfo().getIdentifier();
		final StringBuilder sb = new StringBuilder();
		sb.append("module.run(module.getModuleById(\"" + id + "\"), True, {");
		for (final ModuleItem<?> input : m.getInfo().inputs()) {
			final String key = input.getName();
			final String value = stringify(key, m.getInput(key));
			if (value == null) continue;
			sb.append(", " + key + ": " + value);
		}
		sb.append("})");
		textPane.setText(textPane.getText() + sb.toString() + "\n");
	}

	private String stringify(final String name, final Object value) {
		if (value instanceof Context || value instanceof Service) return null;
		if (value instanceof String) return "\"" + value + "\"";
		if (value instanceof Number) return value.toString(); // no quotes for number literal
		if (value instanceof Boolean) return ((Boolean) value) ? "True" : "False";
		// TODO: Support more types, especially image types.
		log.debug("Skipping input '" + name + "' of unsupported type: " + value
			.getClass().getName());
		return null;
	}
}
