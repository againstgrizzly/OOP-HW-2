
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

public class HW2_4737 {

    public static void main(String[] args) {

        Converter converter = new Converter();
    }
}

class Converter extends JFrame implements KeyListener {

    private String arabicNumeral = ""; //Holds text inputted into arabicText TextField
    private String romanNumeral = ""; //Holds text inputted into romanText TextField
    private Label arabicLabel;
    private Label romanLabel;
    private TextField arabicText;
    private TextField romanText;

    public Converter() {

        JPanel panel = new JPanel(new GridLayout(2, 2));

        arabicLabel = new Label("Arabic Numeral"); //Arabic Numeral label
        romanLabel = new Label("Roman Numeral"); //Roman Numeral label
        arabicText = new TextField();
        romanText = new TextField();

        //Add components to panel
        panel.add(arabicLabel);
        panel.add(arabicText);
        panel.add(romanLabel);
        panel.add(romanText);

        //add listeners to text fields
        romanText.addKeyListener(this);
        arabicText.addKeyListener(this);

        //add to JFrame
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Roman< -- > Arabic"); //Window title
        setSize(300, 100); //window size
        setResizable(false);
        setVisible(true);
    }

    //ActionEvent for keyReleases
    //Used to activate text fields
    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getSource() == arabicText){
            arabicNumeralTextChecker(e);
        }

        if(e.getSource() == romanText){
            romanNumeralTextChecker(e);
        }
    }

    //Checks that valid arabic numeral is inputted
    //Rejects invalid arabic numerals
    //Sets romanText blank when arabicText is blank
    //Corrects caret position
    private void arabicNumeralTextChecker(KeyEvent e){
        int currentInput;

            if (arabicText.getText().equals("")) {
                romanText.setText("");
            }
            try {
                currentInput = Integer.valueOf(arabicText.getText());

                if (currentInput < 1 || currentInput > 3999) {
                    arabicText.setText(arabicNumeral);
                } else {
                    arabicToRoman(currentInput);
                    arabicNumeral = String.valueOf(currentInput);
                    arabicText.setText(arabicNumeral);
                }
                arabicText.setCaretPosition(arabicText.getText().toCharArray().length);


            } catch (Exception ex) {

                if(arabicText.getText().equals("")){
                    arabicText.setText("");
                }

                else {
                    arabicText.setText(arabicNumeral);
                    arabicText.setCaretPosition(arabicText.getText().toCharArray().length);
                }
            }

        if (arabicText.getText().equals("")) {
            romanText.setText("");
        }


    }

    //Converts arabic numerals to roman numerals
    //Updates roman text
    private void arabicToRoman(int arabic){

        StringBuilder arabicBuilder = new StringBuilder();
        arabicBuilder.append(arabic);
        int holder[];

        switch(arabicBuilder.length()){
            case 1 : {
                holder = new int[1];
                holder[0] = Integer.valueOf(arabicBuilder.substring(0,1));
                romanText.setText(romanLookupTable(holder));
            }
                break;
            case 2 : {
                holder = new int[2];
                holder[0] = Integer.valueOf(arabicBuilder.substring(0,1)) * 10;
                holder[1] = Integer.valueOf(arabicBuilder.substring(1,2));
                romanText.setText(romanLookupTable(holder));
            }
                break;
            case 3 : {
                holder = new int[3];
                holder[0] = Integer.valueOf(arabicBuilder.substring(0,1)) * 100;
                holder[1] = Integer.valueOf(arabicBuilder.substring(1,2)) * 10;
                holder[2] = Integer.valueOf(arabicBuilder.substring(2,3));
                romanText.setText(romanLookupTable(holder));
            }
                break;
            case 4 : {
                holder = new int[4];
                holder[0] = Integer.valueOf(arabicBuilder.substring(0,1)) * 1000;
                holder[1] = Integer.valueOf(arabicBuilder.substring(1,2)) * 100;
                holder[2] = Integer.valueOf(arabicBuilder.substring(2,3)) * 10;
                holder[3] = Integer.valueOf(arabicBuilder.substring(3,4));
                romanText.setText(romanLookupTable(holder));
            }
                break;
            default: {
                holder = new int[1];
                holder[0] = -1;
                System.err.print("ERROR");
            }
        }
    }

    //Checks that roman numeral input is valid, corrects caret position,
    //updates arabicText, makes sure that when romanText is empty so is arabicText,
    //rejects invalid input
    private void romanNumeralTextChecker(KeyEvent e){

        int inArabic;

        String currentInput = romanText.getText();

        if(currentInput.endsWith("M") || currentInput.endsWith("m") || currentInput.endsWith("D") ||currentInput.endsWith("d") ||
           currentInput.endsWith("C") || currentInput.endsWith("c") || currentInput.endsWith("L") ||currentInput.endsWith("l") ||
           currentInput.endsWith("X") || currentInput.endsWith("x") || currentInput.endsWith("V") ||currentInput.endsWith("v") ||
           currentInput.endsWith("I") || currentInput.endsWith("i")){
            romanText.setText(currentInput);
            romanNumeral = currentInput;
            romanText.setCaretPosition(romanText.getText().toCharArray().length);
            inArabic = romanToArabic(romanNumeral);
            arabicText.setText(String.valueOf(inArabic));
        }

        else if(romanText.getText().equals("")){
            romanNumeral = "";
            arabicText.setText("");
        }

        else{
            romanText.setText(romanNumeral);
            romanText.setCaretPosition(romanText.getText().toCharArray().length);
            romanToArabic(romanNumeral);
        }
    }

    //Converts roman numerals to the arabic representation
    private int romanToArabic(String romanNumeral){

        int total = 0;

        String array[] = new String[romanNumeral.toCharArray().length];
        List<Integer> list = new ArrayList<>();

        //Split string into individual characters in an array
        for(int i = 0; i < array.length; i++){
            array[i] = String.valueOf(romanNumeral.toCharArray()[i]);
            array[i] = array[i].toUpperCase();
        }

        //Take contents of array and store numeric representation of characters
        //into list
        for(String contents: array){
            switch(contents){
                case "M" : list.add(1000);
                    break;
                case "D" : list.add(500);
                    break;
                case "C" : list.add(100);
                    break;
                case "L" : list.add(50);
                    break;
                case "X" : list.add(10);
                    break;
                case "V" : list.add(5);
                    break;
                case "I" : list.add(1);
            }
        }

        //Summation of list contents with special considerations
        //of roman numeral conversion taken into account
        for(int i = 0; i < list.size(); i++){

            if(list.size() > i+1 && list.get(i) < list.get(i+1)){
                    total += list.get(i+1) - list.get(i);
                    i++;
            }
            else{
                total += list.get(i);
            }
        }
        return total;
    }

    //Takes in an int array containing the inputted number in expanded form
    //and returns it as a string in roman numeral form
    private String romanLookupTable(int format[]){

        StringBuilder builder = new StringBuilder();

        //key value pairs for the roman numerals representation
        //of valid integer input
        HashMap<Integer, String> lookup = new HashMap<>();
        lookup.put(3000, "MMM");
        lookup.put(2000, "MM");
        lookup.put(1000, "M");
        lookup.put(900, "CM");
        lookup.put(800, "DCCC");
        lookup.put(700, "DCC");
        lookup.put(600, "DC");
        lookup.put(500, "D");
        lookup.put(400, "CD");
        lookup.put(300, "CCC");
        lookup.put(200, "C");
        lookup.put(100, "C");
        lookup.put(90, "XC");
        lookup.put(80, "LXXX");
        lookup.put(70, "LXX");
        lookup.put(60, "LX");
        lookup.put(50, "L");
        lookup.put(40, "XL");
        lookup.put(30, "XXX");
        lookup.put(20, "XX");
        lookup.put(10, "X");
        lookup.put(9, "IX");
        lookup.put(8, "VIII");
        lookup.put(7, "VII");
        lookup.put(6, "VI");
        lookup.put(5, "V");
        lookup.put(4, "IV");
        lookup.put(3, "III");
        lookup.put(2, "II");
        lookup.put(1, "I");
        lookup.put(0, "");

        for(int x: format){
            builder.append(lookup.get(x));
        }
        return builder.toString();
    }

    @Override //Not used
    public void keyTyped(KeyEvent e) {


    }

    @Override //Not used
    public void keyPressed(KeyEvent e) {

    }
}
