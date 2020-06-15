package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.actions.DrawingModelAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.SetToolAction;
import hr.fer.zemris.java.hw17.jvdraw.canvas.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.color.JColorLabel;
import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.model.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.drawing.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.model.drawing.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.model.list.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.model.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.model.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.model.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.model.visitors.GeometricalObjectFormatter;
import hr.fer.zemris.java.hw17.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.NoTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.tools.ToolContext;

/**
 * An application that allows the user to draw lines, circles and filled circles
 * in many colors.
 * <p>
 * The application also allows managing the layout of the drawn objects and
 * their editing.
 * 
 * @author Vedran Kolka
 * @version 1.0
 *
 */
public class JVDraw extends JFrame implements ToolContext {

	private static final long serialVersionUID = 1L;
	/**
	 * Array of options to choose from. Used for {@link JOptionPane} dialogs with
	 * the user.
	 */
	public static Object[] YES_NO_CANCEL_OPTIONS = { "yes", "no", "cancel" };
	/** Index of the YES option in {@link #YES_NO_CANCEL_OPTIONS} */
	public static int YES = 0;
	/** Index of the NO option in {@link #YES_NO_CANCEL_OPTIONS} */
	public static int NO = 1;
	/** Index of the CANCEL option in {@link #YES_NO_CANCEL_OPTIONS} */
	public static int CANCEL = 2;
	/** currently selected tool for drawing */
	private Tool currentTool = new NoTool();
	/**
	 * The {@link DrawingModel} which this application uses for modeling drawn
	 * objects
	 */
	private DrawingModel drawingModel = new DrawingModelImpl();
	/** The canvas used for recieving users input for drawing the objects. */
	private JDrawingCanvas jdc = new JDrawingCanvas(drawingModel, () -> currentTool);
	/** The list model for the {@link JList} to show. */
	private DrawingObjectListModel listModel = new DrawingObjectListModel(drawingModel);
	/**
	 * The list of objects, shown on the right side of the application. Offers
	 * editing of the objects by a double-click.
	 */
	private JList<GeometricalObject> list = new JList<>(listModel);
	/**
	 * Foreground color provider for the application. When clicked on it offers a
	 * color chooser.
	 */
	private JColorArea foregroundColorArea = new JColorArea(Color.BLACK);
	/**
	 * Background color provider for the application. When clicked on it offers a
	 * color chooser.
	 */
	private JColorArea backgroundColorArea = new JColorArea(Color.WHITE);
	/**
	 * The path to the file where the current drawing is saved. <code>null</code> if
	 * it has yet not been saved
	 */
	private Path currentDrawingPath;
	/**
	 * Action for saving the current drawing model. Calls the
	 * {@link JVDraw#save(boolean)} with <code>saveAs</code> flag set to
	 * <code>false</code>.
	 */
	private Action save = new DrawingModelAction(drawingModel, () -> save(false));
	/**
	 * Action for saving the current drawing model. Calls the
	 * {@link JVDraw#save(boolean)} with <code>saveAs</code> flag set to
	 * <code>true</code>.
	 */
	private Action saveAs = new DrawingModelAction(drawingModel, () -> save(true));
	/** Action for opening a new drawing. Calls the {@link JVDraw#open()} method. */
	private Action open = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			open();
		}
	};
	
	/** Action for exporting the current drawing. Calls the {@link JVDraw#export()} method. */
	private Action export = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			export();
		}
	};
	/** Action for exiting the application. Calls the {@link JVDraw#exit()} method. */
	private Action exit = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			exit();
		}
	};
	/** Action that sets the current tool to {@link LineTool} */
	private Action setLineTool = new SetToolAction(new LineTool(drawingModel, foregroundColorArea, jdc), this);
	/** Action that sets the current tool to {@link CircleTool} */
	private Action setCircleTool = new SetToolAction(new CircleTool(drawingModel, foregroundColorArea, jdc), this);
	/** Action that sets the current tool to {@link FilledCircleTool} */
	private Action setFilledCircleTool = new SetToolAction(
			new FilledCircleTool(drawingModel, foregroundColorArea, backgroundColorArea, jdc), this);
	/** Action that moves the object selected in the list by one place up. */
	private Action moveUp = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			GeometricalObject selected = list.getSelectedValue();
			if (selected != null) {
				drawingModel.changeOrder(selected, 1);
				list.setSelectedValue(selected, true);
			}
		}
	};
	/** Action that moves the object selected in the list by one place down. */
	private Action moveDown = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			GeometricalObject selected = list.getSelectedValue();
			if (selected != null) {
				drawingModel.changeOrder(selected, -1);
				list.setSelectedValue(selected, true);
			}
		}
	};
	/** Action that deletes the selected object from the current drawing. */
	private Action delete = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			GeometricalObject selected = list.getSelectedValue();
			drawingModel.remove(selected);
		}
	};

	/**
	 * Contructor. Takes no arguments.
	 */
	public JVDraw() {
		setLocation(400, 200);
		initGUI();
		pack();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
	}

	/**
	 * Initializes all the GUI components of the main application frame.
	 */
	private void initGUI() {

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel(new BorderLayout());
		cp.add(centerPanel, BorderLayout.CENTER);
		configureActions();

		cp.add(createToolBar(), BorderLayout.PAGE_START);
		centerPanel.add(jdc, BorderLayout.CENTER);

		JColorLabel jcl = new JColorLabel(foregroundColorArea, backgroundColorArea);
		centerPanel.add(jcl, BorderLayout.PAGE_END);
		createMenus();

		centerPanel.add(new JScrollPane(list), BorderLayout.LINE_END);
	}

	/**
	 * Creates and sets the menu bar to the frame. The menus created:
	 * <ul>
	 * <li>edit menu
	 * <li>manage menu
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("file");
		file.add(new JMenuItem(open));
		file.addSeparator();
		file.add(new JMenuItem(save));
		file.add(new JMenuItem(saveAs));
		file.addSeparator();
		file.add(new JMenuItem(export));
		file.addSeparator();
		file.add(exit);
		menuBar.add(file);

		JMenu manage = new JMenu("manage");
		manage.add(new JMenuItem(moveUp));
		manage.add(new JMenuItem(moveDown));
		manage.add(new JMenuItem(delete));
		menuBar.add(manage);

		setJMenuBar(menuBar);
	}

	/**
	 * Creates the toolbar with {@link JColorArea}s for managing the foreground and
	 * background colors and with toggle buttons which are used for choosing the
	 * current drawing tool.
	 * 
	 * @return created toolbar
	 */
	private JToolBar createToolBar() {

		ButtonGroup toolsGroup = new ButtonGroup();
		JToggleButton lineToolButton = new JToggleButton(setLineTool);
		JToggleButton circleToolButton = new JToggleButton(setCircleTool);
		JToggleButton filledCircleToolButton = new JToggleButton(setFilledCircleTool);

		lineToolButton.setPreferredSize(new Dimension(40, 20));
		circleToolButton.setPreferredSize(new Dimension(50, 20));
		filledCircleToolButton.setPreferredSize(new Dimension(80, 20));

		toolsGroup.add(lineToolButton);
		toolsGroup.add(circleToolButton);
		toolsGroup.add(filledCircleToolButton);
		// create the toolbar
		JToolBar toolbar = new JToolBar();
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolbar.add(foregroundColorArea);
		toolbar.add(backgroundColorArea);
		toolbar.add(lineToolButton);
		toolbar.add(circleToolButton);
		toolbar.add(filledCircleToolButton);

		return toolbar;
	}

	/**
	 * Configures all the actions, giving them names, keyboard shortcuts and a
	 * description. Also sets the enabled state of all actions and registeres
	 * listeners on object (such as {@link JList} and {@link DrawingModel}) for
	 * controling the enabled state of some actions.
	 */
	private void configureActions() {

		configureAction(save, "save", KeyStroke.getKeyStroke("control S"), KeyEvent.VK_S, "Saves the current drawing.",
				false);
		configureAction(saveAs, "save as", KeyStroke.getKeyStroke("control shift S"), KeyEvent.VK_A,
				"Opens a save dialog to save the current drawing.", false);
		configureAction(open, "open", KeyStroke.getKeyStroke("control O"), KeyEvent.VK_O,
				"Opens a dialog to choose a drawing to open.", true);
		configureAction(export, "export", KeyStroke.getKeyStroke("control E"), KeyEvent.VK_E,
				"Exports the drawing as a picture.", false);
		configureAction(exit, "exit", KeyStroke.getKeyStroke("control shift E"), KeyEvent.VK_Q, "Exits the program.",
				true);
		configureAction(setLineTool, "line", null, -1, "Tool for drawinf a line.", true);
		configureAction(setCircleTool, "circle", null, -1, "Tool for drawing a circle.", true);
		configureAction(setFilledCircleTool, "filled circle", null, -1, "Tool for drawing a filled circle.", true);
		configureAction(moveUp, "move up", KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0), KeyEvent.VK_PLUS,
				"Moves the selected object one place up in the hierarchy", false);
		configureAction(moveDown, "move down", KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), KeyEvent.VK_MINUS,
				"Moves the selected object one place down in the hierarchy", false);
		configureAction(delete, "delete", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), KeyEvent.VK_DELETE,
				"Deletes the selected geometrical object", false);

		list.addListSelectionListener(e -> {
			moveDown.setEnabled(list.getSelectedIndex() != 0);
			moveUp.setEnabled(list.getSelectedIndex() != listModel.getSize() - 1);
			delete.setEnabled(list.getSelectedValue() != null);
		});

		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2)
					return;

				GeometricalObject selected = list.getSelectedValue();
				if (selected == null)
					return;

				GeometricalObjectEditor editor = selected.createGeometricalObjectEditor();
				if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "edit",
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					try {
						editor.checkEditing();
						editor.acceptEditing();
					} catch (Exception ex) {
						System.err.println(ex.getMessage());
						JOptionPane.showMessageDialog(editor, "Object not changed.", "not changed",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		drawingModel.addDrawingModelListener(new DrawingModelListener() {

			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				export.setEnabled(source.getSize() > 0);
			}

			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				export.setEnabled(source.getSize() > 0);
			}

			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				export.setEnabled(source.getSize() > 0);
			}
		});
	}

	/**
	 * Configures a sisngle <code>action</code> by putting the given properties in
	 * the action's map under standard keys<br>
	 * (such as <code>name</code> under {@link Action#NAME}).
	 * 
	 * @param action
	 * @param name
	 * @param stroke
	 * @param mnemonic
	 * @param shortDesc
	 * @param enabled
	 */
	private void configureAction(Action action, String name, KeyStroke stroke, int mnemonic, String shortDesc,
			boolean enabled) {
		action.putValue(Action.NAME, name);
		action.putValue(Action.ACCELERATOR_KEY, stroke);
		action.putValue(Action.MNEMONIC_KEY, mnemonic);
		action.putValue(Action.SHORT_DESCRIPTION, shortDesc);
		action.setEnabled(enabled);
	}

	/**
	 * Initiates the exiting procces, asking the user to save the current drawing if
	 * it has been modified since the last save operation.
	 */
	private void exit() {
		if (!drawingModel.isModified()) {
			dispose();
			return;
		}

		int choice = askToSave();

		if (choice == YES) {
			save(false);
			dispose();
		} else if (choice == NO) {
			dispose();
		}
	}

	/**
	 * Initiates the saving process, asking the user to choose a file path if the
	 * flag <code>saveAs</code> is <code>true</code> or if the drawing has not yet
	 * been saved.
	 * 
	 * @param saveAs boolean flag to indicate if the user should be asked to choose
	 *               a file path to save drawing
	 */
	private void save(boolean saveAs) {
		if (currentDrawingPath == null || saveAs) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save file");

			FileFilter filter = new JVDFileFIlter();

			jfc.setFileFilter(filter);

			if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(this, "Drawing not saved.", "Not saved", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			String selectedFile = jfc.getSelectedFile().toString();
			if (!selectedFile.endsWith(".jvd")) {
				selectedFile += ".jvd";
			}
			currentDrawingPath = Paths.get(selectedFile);

		}

		try (OutputStream os = Files.newOutputStream(currentDrawingPath)) {
			String formatted = format();
			byte[] data = formatted.getBytes();
			os.write(data);
			drawingModel.clearModifiedFlag();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error occured while saving.", "error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Initiates the process of openig a saved jvd drawing. Opens a drawing and
	 * imports it as the current {@link DrawingModel}.
	 * <p>
	 * The application can read <b>only .jvd</b> files.
	 */
	private void open() {
		if (drawingModel != null && drawingModel.isModified()) {
			int choice = askToSave();
			if (choice == YES) {
				save(false);
			} else if (choice == CANCEL) {
				return;
			}
		}

		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Open file");
		jfc.setFileFilter(new JVDFileFIlter());

		if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		String selectedFile = jfc.getSelectedFile().toString();
		if (!selectedFile.endsWith(".jvd")) {
			JOptionPane.showMessageDialog(this, "Cannot open selected file. Select a .jvd file.", "Cannot open",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		currentDrawingPath = Paths.get(selectedFile);
		try {
			List<String> lines = Files.readAllLines(currentDrawingPath);
			importObjects(lines);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Cannot read selected file: " + currentDrawingPath, "Cannot read",
					JOptionPane.ERROR_MESSAGE);
			currentDrawingPath = null;
			e.printStackTrace();
		}
	}

	/**
	 * Parses the <code>lines</code> to create {@link GeometricalObject}s saved in
	 * the format defined by {@link GeometricalObjectFormatter}
	 * <p>
	 * Only if all the lines are parsed correctly the current {@link DrawingModel}s
	 * objects are replaced by the newly created objects.
	 * 
	 * @param lines
	 */
	private void importObjects(List<String> lines) {

		List<GeometricalObject> objects = new ArrayList<>();

		try {

			for (String line : lines) {
				if (line.startsWith("LINE")) {
					objects.add(Line.parse(line));
				} else if (line.startsWith("CIRCLE")) {
					objects.add(Circle.parse(line));
				} else if (line.startsWith("FCIRCLE")) {
					objects.add(FilledCircle.parse(line));
				} else {
					throw new IllegalArgumentException("Unknown object: " + line);
				}
			}
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			throw new IllegalArgumentException(e);
		}
		// if all the lines were read correctly clear the model and add the new objects
		drawingModel.clear();
		objects.forEach(go -> drawingModel.add(go));
		drawingModel.clearModifiedFlag();
	}

	/**
	 * Initiates a dialog to ask the user if the current drawing should be saved.
	 * 
	 * @return users choice, represented by the index of the option ({@link #YES} /
	 *         {@link #NO} / {@link #CANCEL})
	 */
	private int askToSave() {
		return JOptionPane.showOptionDialog(this, "The drawing is not saved. Do You wish to save the drawing?",
				"Drawing not saved", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				YES_NO_CANCEL_OPTIONS, YES_NO_CANCEL_OPTIONS[0]);
	}

	/**
	 * Formattesthe current drawing using the {@link GeometricalObjectFormatter}.
	 * 
	 * @return formatted text defining the drawing
	 */
	private String format() {
		GeometricalObjectFormatter gof = new GeometricalObjectFormatter();
		for (GeometricalObject go : drawingModel) {
			go.accept(gof);
		}
		return gof.getFormatted();
	}

	/**
	 * Initiates the export process, offering the user to choose a format to which
	 * to drawing should be exported as well as the file path.
	 * <p>
	 * SUpported formats are:
	 * <ul>
	 * <li>jpg
	 * <li>png
	 * <li>gif
	 */
	private void export() {

		JFileChooser jfc = new JFileChooser();
		jfc.resetChoosableFileFilters();
		jfc.setFileFilter(new FileNameExtensionFilter("jpg", "jpg", ".jpg"));
		jfc.setFileFilter(new FileNameExtensionFilter("gif", "gif", ".gif"));
		jfc.setFileFilter(new FileNameExtensionFilter("png", "png", ".png"));

		int choice = jfc.showDialog(this, "export");
		if (choice != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(this, "Nothing exported.", "not exported", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Path chosen = jfc.getSelectedFile().toPath();
		String extension = jfc.getFileFilter().getDescription();

		String fileName = chosen.toString();
		if (!fileName.endsWith(".png") && !fileName.endsWith(".jpg") && !fileName.endsWith(".gif")) {
			if (fileName.contains(".")) {
				String invalidExtension = fileName.substring(fileName.lastIndexOf('.'));
				JOptionPane.showMessageDialog(this,
						"Invalid extension: " + invalidExtension + " . Drawing not exported.", "not exported",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			} else if (!extension.equals("All files")) {
				fileName += "." + extension;
				chosen = Paths.get(fileName);
			} else {
				JOptionPane.showMessageDialog(this, "No extension selected.", "not exported",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		// now the fileName has the correct extension for sure
		String ext = fileName.substring(fileName.length() - 3);
		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();

		for (GeometricalObject go : drawingModel) {
			go.accept(bbcalc);
		}

		Rectangle box = bbcalc.getBoundingBox();
		BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();
		g.translate(-box.x, -box.y);

		Color savedColor = g.getColor();
		g.setColor(Color.WHITE);
		g.fill(box);
		g.setColor(savedColor);
		GeometricalObjectPainter gop = new GeometricalObjectPainter(g);
		for (GeometricalObject go : drawingModel) {
			go.accept(gop);
		}
		g.dispose();

		try {
			ImageIO.write(image, ext, chosen.toFile());
			JOptionPane.showMessageDialog(this, "Drawing exported to: " + chosen, "exported",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error while exporting.", "not exported", JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public Tool getCurrentTool() {
		return currentTool;
	}

	@Override
	public void setCurrentTool(Tool currentTool) {
		this.currentTool = currentTool;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}

	/**
	 * A simple FileFilter which filters all non jvd files except for directories
	 * which are shown to navigate the filesmore easily.
	 * 
	 * @author Vedran Kolka
	 *
	 */
	private static class JVDFileFIlter extends FileFilter {

		@Override
		public String getDescription() {
			return "jvd";
		}

		@Override
		public boolean accept(File f) {
			return f != null && (f.toString().endsWith(".jvd") || f.isDirectory());
		}
	}

}
