package calculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextArea equationDisplay;
    private float result;
    private char nextOperation;

    private boolean hasInputNum;

    public Calculator() {
        clear();
        Font font = new Font("Ariel", Font.PLAIN, 22);

        setSize(450, 600);
        setLocationRelativeTo(null);
        setTitle("CIS*2430 Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        equationDisplay = new JTextArea(5, 10);
        equationDisplay.setMaximumSize(new Dimension(450, 100));
        equationDisplay.setEditable(false);
        equationDisplay.setBackground(Color.WHITE);
        equationDisplay.setFont(font);
        this.add(equationDisplay);

        this.add(initBtnPanel());
        setVisible(true);

    }

    private JPanel initBtnPanel() {
        JPanel btnPanel = new JPanel(new GridLayout(4, 4));

        String[] btnLabels = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "C", "0", "=", "+"};
        for (String btnLabel : btnLabels) {

            JButton btn = new JButton(btnLabel);
            btn.addActionListener(this);
            btn.setBackground(Color.GRAY);
            btnPanel.add(btn);

        }
        return btnPanel;
    }

    public void setDisplayText(String display) {
        this.equationDisplay.setText("\n\n" + display);
    }

    @Override
    public void actionPerformed(ActionEvent e) { //handles a few different cases

        String s = e.getActionCommand(); //gets what the user just clicks
        if(s.charAt(0) >= '0' && s.charAt(0) <= '9') { //ascii number must fall in range to be considered a number
            if(hasInputNum){ //our method checks if a number has been inputed
                setDisplayText(equationDisplay.getText().strip() + s); //strip will get rid of whitesapce
            } else{
                setDisplayText(s);
                hasInputNum = true;
            }
        }else{
            if(s.charAt(0) == 'C') {
                clear();
                setDisplayText("");
            }
            if(s.charAt(0) == '=' || s.charAt(0) == '+' || s.charAt(0) == '-' || s.charAt(0) == '/' || s.charAt(0) == '*') {
                try{
                    float inputNum = 0.0f; //assume no number
                    try{
                        inputNum = Float.parseFloat(equationDisplay.getText());
                    }catch(NumberFormatException err){
                        inputNum = 0.0f;
                    } finally{
                        this.result = eval(inputNum);
                        setDisplayText(Float.toString(this.result));
                        nextOperation = s.charAt(0);
                    }
                } catch(ArithmeticException err){
                    setDisplayText(err.getMessage());
                }
            }
            hasInputNum = false; //no input number if it bypassed all code above
        }

    }

    public float eval(float x) throws ArithmeticException { //uses divide so throw arithmetic
        switch(nextOperation){
            case '+':
                return add(x);
            case '-':
                return subtract(x);
            case '*':
                return multiply(x);
            case '/':
                return divide(x);
            case '=':
                return result;
            default:
                throw new ArithmeticException("ERR!");
        }
    }

    public float add(float x){
        return result + x;
    }

    public float subtract(float x){
        return  result - x;
    }

    public float multiply(float x){
        return result * x;
    }

    public float divide(float x) throws ArithmeticException{
        if(x == 0.0){
            clear();
            throw new ArithmeticException("Error");
        }
        return result / x;
    }

    public void clear(){
        result = 0.0f;
        nextOperation = '+';
        hasInputNum = false;
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
