package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.tools.ToolContext;

/**
 * Action that sets a specific tool as the current tool when ran.
 * @author Vedran Kolka
 *
 */
public class SetToolAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	/** tool to set */
	private Tool tool;
	/** The context on which a tool can be set */
	private ToolContext toolContext;
	
	/**
	 * Constructor that takes two arguments.
	 * 
	 * @param tool to set
	 * @param toolContext on which to set the tool
	 */
	public SetToolAction(Tool tool, ToolContext toolContext) {
		this.tool = tool;
		this.toolContext = toolContext;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		toolContext.setCurrentTool(tool);
	}

}
