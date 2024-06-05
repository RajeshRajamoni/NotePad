package Programs;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class NotePad extends JFrame {

	// private static final String[] = null;
	private JFrame frame;
	private JTextArea textArea;

	public NotePad() {
		frame = new JFrame("NotePad");
		textArea = new JTextArea("A simple NotePad");
		textArea.setEditable(false);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.lightGray);

		createFileMenu(menuBar);
		createEditMenu(menuBar);
		createCloseMenu(menuBar);

		frame.setJMenuBar(menuBar);
		frame.add(textArea);
		frame.setSize(550, 450);
		frame.setVisible(true);

		textArea.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textArea.setVisible(false);
			}
		});
	}

	private void createFileMenu(JMenuBar menuBar) {
		JMenu file = new JMenu("File");
		String[] fileMenuItems = { "New", "Save", "Open", "Print", "Close" };
		createMenuItems(file, fileMenuItems);
		menuBar.add(file);
	}

	private void createEditMenu(JMenuBar menuBar) {
		JMenu edit = new JMenu("Edit");
		String[] editMenuItems = { "Cut", "Copy", "Paste" };
		createMenuItems(edit, editMenuItems);
		menuBar.add(edit);
	}

	private void createCloseMenu(JMenuBar menuBar) {
		JMenu close = new JMenu("Close");
		String[] closeMenuItems = { "Close" };
		createMenuItems(close, closeMenuItems);
		menuBar.add(close);
	}

	private void createMenuItems(JMenu menu, String[] itemNames) {
		for (String itemName : itemNames) {
			JMenuItem menuItem = new JMenuItem(itemName);
			menuItem.addActionListener(e -> {
				try {
					handleMenuItemAction(itemName);
				} catch (PrinterException e1) {
					showError("Error printing." + e1);
				}
			});
			menu.add(menuItem);
		}
	}

	private void handleMenuItemAction(String command) throws PrinterException {
		switch (command) {
		case "New" -> textArea.setText("");
		// break;
		case "Save" -> saveFile();
		// break;
		case "Open" -> openFile();
		// break;
		case "Cut" -> textArea.cut();
		// break;
		case "Copy" -> textArea.copy();
		// break;
		case "Paste" -> textArea.paste();
		// break;
		case "Print" -> textArea.print();

//               try {
//                    textArea.print();
//                } catch (PrinterException ex) {
//                    showError("Error printing.");
//                }
//                break;
		case "Close" -> frame.setVisible(false);
		// break;
		}
	}

	private void saveFile() {
		JFileChooser fileChooser = getFileChooser();
		int result = fileChooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				writer.write(textArea.getText());
			} catch (IOException e) {
				showError("Error saving file.");
			}
		} else {
			showMessage("The user has cancelled the operation");
		}
	}

	private void openFile() {
		JFileChooser fileChooser = getFileChooser();
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				StringBuilder content = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					content.append(line).append("\n");
				}
				textArea.setText(content.toString());
			} catch (IOException e) {
				showError("Error opening file.");
			}
		} else {
			showMessage("The user has cancelled the operation");
		}
	}

	private JFileChooser getFileChooser() {
		return new JFileChooser("D:");
	}

	private void showError(String message) {
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void showMessage(String message) {
		JOptionPane.showMessageDialog(frame, message);
	}

	public static void main(String[] args) {
		new NotePad();
	}
}
