// http://www.codeplanet.eu/tutorials/java/3-taschenrechner.html

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Stellt die Schnittstelle und Klassen f�r die
// Ereignisbehandlung welche durch AWT Komponenten
// erzeugt wurden bereit


public class Calculator {

	public static void main(String[] args){
		CalculatorFrame frame = new CalculatorFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Frame sichtbar machen
		frame.setVisible(true);
	}
	
}

/**
 * Leeren Frame ohne Gr��e erzeugen.
 */

class CalculatorFrame extends JFrame{
	
	public CalculatorFrame(){
		setTitle("Calculator");	// Name der Titelleiste
		CalculatorPanel panel = new CalculatorPanel();
		add(panel);
		// Gr��e des Fensters an seine Komponenten anpassen
		pack();
	}
}

/**
 * Panel Grundfl�che f�r die Rechnertasten und Ergebnisanzeige
 * anlegen
 */

class CalculatorPanel extends JPanel{
	
	public CalculatorPanel(){
		setLayout(new BorderLayout());	// Layout-Manager festlegen
		
		result = 0;
		lastCommand = "=";
		start = true;
		
		// Display hinzuf�gen
		display = new JButton("0");
		display.setEnabled(false);
		// Display oben positionieren
		add(display, BorderLayout.NORTH);
		
		// ActionListener insert erzeugen
		ActionListener insert = new InsertAction();
		// ActionListener command erzeugen
		ActionListener command = new CommandAction();
		
		panel = new JPanel();
		// Angabe der Anzahl von Spalten und Zeilen im Konstruktor
		panel.setLayout(new GridLayout(4, 4));
		// Generierung der numerischen Tschenrechner-Tasten
		// Die Aktion insert als zweiten Parameter hinzuf�gen
		addButton("7", insert);
		addButton("8", insert);
		addButton("9", insert);
		addButton("/", command);
		
		addButton("4", insert);
		addButton("5", insert);
		addButton("6", insert);
		addButton("*", command);
		
		addButton("1", insert);
		addButton("2", insert);
		addButton("3", insert);
		addButton("-", command);
		
		addButton("0", insert);
		addButton(".", insert);
		addButton("=", command);
		addButton("+", command);
		
		// Panel Komponenten im Centerblock platzieren
		add(panel, BorderLayout.CENTER);
		
	}
	
/**
 * Tasten in die mittlere Grundfl�che einf�gen.
 * @param label Beschriftung der Taste
 * @param listener der Ereignisempf�nger f�r die Taste
 */
	
	private void addButton(String label, ActionListener listener){
		JButton button = new JButton(label);
		// Ereignisempf�nger f�r die Schaltfl�che
		button.addActionListener(listener);
		panel.add(button);
	}
	
/**
 * Diese Aktion f�gt den String der Tastenaktion an das Ende
 * des Anzeigetextes an.
 */
	
	private class InsertAction implements ActionListener{
		
		public void actionPerformed(ActionEvent event){
			
			String input = event.getActionCommand();
			// Beim Start keinen Text anzeigen
			// Das Start Flag anschlie�end auf false setzen
			if (start){
				display.setText("");
				start = false;
			}
			// Text anzeigen
			display.setText(display.getText() + input);
		}
	}
	
	private class CommandAction implements ActionListener{
		
		public void actionPerformed(ActionEvent event){
		
			String command = event.getActionCommand();
			
			// F�ge den Pr�fix "-" an den String an wenn 
			// es sich um den ersten Befehl handelt (negative zahlen)
			if (start){
				if (command.equals("-")){
					display.setText(command);
					start = false;
				}
				else
					lastCommand = command;
			}
			else{
				// Berechnung ausf�hren
				calculate(Double.parseDouble(display.getText()));
				lastCommand = command;
				start = true;
			}
		}
	}
	
/**
 * F�hrt die anstehenden Berechnung aus.
 * @param x der mit dem vorherigen Ergebnis zu berechnende Wert
 */
	
	public void calculate(double x){
		if (lastCommand.equals("+")) result += x;
		else if (lastCommand.equals("-")) result -= x;
		else if (lastCommand.equals("*")) result *= x;
		else if (lastCommand.equals("/")) result /= x;
		else if (lastCommand.equals("=")) result = x;
		display.setText("" + result);
	}
	
	private JButton display;
	private JPanel panel;
	private double result;
	private String lastCommand;
	private boolean start;
}

