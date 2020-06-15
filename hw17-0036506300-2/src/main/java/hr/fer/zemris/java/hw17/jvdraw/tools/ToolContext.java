package hr.fer.zemris.java.hw17.jvdraw.tools;

/**
 * An object which can have a current tool set.
 * 
 * @author Vedran Kolka
 *
 */
public interface ToolContext {
	/**
	 * Returns the currently set tool.
	 * 
	 * @return the currently set tool
	 */
	Tool getCurrentTool();

	/**
	 * Sets the given tool as the current tool.
	 * 
	 * @param currentTool to set
	 */
	void setCurrentTool(Tool currentTool);

}
