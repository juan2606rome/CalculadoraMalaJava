
package com.example.badcalc;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;







public class Main {
private static final Logger LOG = Logger.getLogger(Main.class.getName());

    //SE QUITA COSAS DE PUBLIC
    static ArrayList<String> history = new ArrayList<>();
    static String last = "";
    static int counter = 0;
    static final double EPSILON = 0.0000001;

    public static double doIt(String aStr, String bStr, String op) {
        double a = 0;
        double b = 0;

        a = Double.parseDouble(aStr.replace(",", "."));
        b = Double.parseDouble(bStr.replace(",", "."));

        switch (op) {
            case "+":
                return a + b;

            case "-" :
                return a - b; 
            case "*" :
                return a * b; 
            case "/" :
                if (Math.abs(b) < EPSILON) throw new ArithmeticException("eerror division 0");
                return a / b;
            
            case "^" : 
            return Math.pow(a, b); 

            case "%" : 
                if (Math.abs(b) < EPSILON) throw new ArithmeticException("eerror division 0");
                return a % b;
            

            case "sqrt" :
                 return Math.sqrt(a); 

            default:
                return 0;
        }
    }

    static String leerOperando(String o) {
    switch (o)   
    {
        case "1" : 
            return  "+";
        case "2" : 
            return  "-";
        case "3" : 
            return "*";
        case "4" : 
            return "/";
        case "5" : 
            return "^";
        case "6" : 
            return "%";
        case "7" : 
            return "sqrt";
        case "8" : 
            return "hist";
        default:
            return "";
        }
    }


    public static double compute(String aA, String bB, String op) {
        double a = parse(aA);
        double b = parse(bB);
 
        try {
            if ("+".equals(op)) return a + b;
            if ("-".equals(op)) return a - b;
            if ("*".equals(op)) return a * b;
            if ("/".equals(op)) {
                if (b == 0) {
                    throw new IllegalArgumentException("no 0");
                }
                return a / b;
            }
            if ("^".equals(op)) {
                double z = 1;
                int i = (int) b;
                while (i > 0) { z *= a; i--; }
                return z;
            }
            if ("%".equals(op)) {
                if (b == 0) {
                    throw new IllegalArgumentException("no 0");
                }
                return a % b;
            }
            if ("sqrt".equals(op)) return Math.sqrt(a);
        } catch (Exception e) {
       //comentario de excepciones
        }
        return 0;
    }

        static double parse(String s) {
            return Double.parseDouble(s.replace(",", "."));
        }
        
    public static void menu(String option) {

        String op = leerOperando(option);
        Scanner sc = new Scanner(System.in);

        String a = "";
        String b = "";

        if (op.equals("hist")) {
            for (String item : history) {
                LOG.info(item);
            }
            return;
        }

        if (op.equals("sqrt")) 
            {
            LOG.info("a: ");
            a = sc.nextLine();
            b = "0";
        }
        else 
            {
            LOG.info("a: ");
            a = sc.nextLine();

            LOG.info("b: ");
            b = sc.nextLine();
        }

        double res;

        try 
        {
            if (op.equals("sqrt")) b = "0";
            res = doIt(a, b, op);
        } 
        catch (Exception e)
         {
            LOG.log(Level.INFO, "Error: {0}", e.getMessage());
            return;
        }

        try (FileWriter fw = new FileWriter("history.txt", true)) {

            DecimalFormat df = new DecimalFormat("0.###############",
            DecimalFormatSymbols.getInstance(Locale.US));
            String line = a + "|" + b + "|" + op + "|" + df.format(res);
               history.add(line);
            last = line;

            fw.write(line + System.lineSeparator());

        } 
        catch (IOException ex) {
            LOG.log(Level.WARNING, ex.getMessage());
        }

        LOG.log(Level.INFO, "= {0}", res);

        counter++;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String opt;

        do {
            LOG.info("GOOD CALCULADORA JAVA");
            LOG.info("1) add 2) sub 3) mul 4) div 5) pow 6) mod 7) sqrt 8) hist 0) exit");
            LOG.info("opt: ");

            opt = sc.nextLine();

            if (!opt.equals("0")) 
            {
                menu(opt);
            }

        }
         while (!opt.equals("0"));

        sc.close();
    }
}
