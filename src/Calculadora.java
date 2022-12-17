/**
 *
 * @author amizba@gmail.com
 * Amparo Izquierdo Ba�ez
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
public class Calculadora {
    public static void main(String[] args) {
            	MarcoCalculadora mimarco = new MarcoCalculadora();
                mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mimarco.setVisible(true);
            }
      
    }
class MarcoCalculadora extends JFrame { //Creo marco
    public MarcoCalculadora() { //Constructor del marco
        setTitle("Calculadora sencilla");
        LaminaCalculadora milamina = new LaminaCalculadora(); //Instanciamos la lamina
        add(milamina); //A�ado la l�mina al marco
        pack(); //Reduzo el tama�o del marco para ver todos los componentes
        int width = 350;
        int height = 350;
        //describe los dispositivos gr�ficos que pueden estar disponibles en un entorno gr�fico particular.
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        setBounds(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2, width, height);
    }
}
class LaminaCalculadora extends JPanel {//Creo lamina
    private JButton display; 
    private JPanel panel;
    private BigDecimal result;
    private String lastCommand;
    private boolean start;
    public LaminaCalculadora() { //Constructor
        setLayout(new BorderLayout()); 
        result = BigDecimal.ZERO; 
        lastCommand = "=";
        start = true; 
        display = new JButton("0");//Por defecto, el display va a tener un 0
        display.setEnabled(false);
        display.setFont(display.getFont().deriveFont(50f));
        add(display, BorderLayout.NORTH); //Ubicaci�n del display
        ActionListener insert = new InsertAction(); //Pongo a la escucha los botones num�ricos
        ActionListener command = new CommandAction(); //Pongo a la escucha los botones aritm�ticos
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));//La disposici�n de los botones 4 filas y 4 columnas
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
        add(panel, BorderLayout.CENTER);
    }
    private void addButton(String label, ActionListener listener) {//M�todo que indica como van aparecer los n�meros en el display
        JButton button = new JButton(label);
        button.setFont(button.getFont().deriveFont(20f));
        button.addActionListener(listener);
        panel.add(button);
    }
    private class InsertAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String input = event.getActionCommand();
            if (start) {
                display.setText("");
                start = false;
            }
            display.setText(display.getText() + input);
        }
    }
    private class CommandAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            if (start) {
                if (command.equals("-")) {
                    display.setText(command);
                    start = false;
                } else lastCommand = command;
            } else {
                calcular(new BigDecimal(display.getText()));
                lastCommand = command;
                start = true;
            }
        }
    }
    public void calcular(BigDecimal x) {//M�todo que realiza los calculos
        if (lastCommand.equals("+")) result = result.add(x);
        else if (lastCommand.equals("-")) result = result.subtract(x);
        else if (lastCommand.equals("*")) result = result.multiply(x);
        else if (lastCommand.equals("/")) result = result.divide(x);
        else if (lastCommand.equals("=")) result = x;
        if (result.compareTo(BigDecimal.ZERO) == 0) {
            result = BigDecimal.ZERO;
        }
        display.setText(result.toString());
    }
}
