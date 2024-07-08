import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static List<String> registers_name = new ArrayList<>();
    private static List<Integer> registers = new ArrayList<>();
    private static List<String> variables_name = new ArrayList<>();
    private static List<Integer> variables_values = new ArrayList<>();
    private static List<String> Labels = new ArrayList<>();
    private static List<String> Labels_number = new ArrayList<>();
    private static Stack<Integer> stack = new Stack<>();
    private static List<String> Assembly_code = new ArrayList<>();

    private static final String[] operations = {"LDA", "STR", "PUSH", "POP", "AND", "OR", "NOT", "ADD", "SUB", "DIV", "MUL", "MOD", "INC", "DEC", "BEQ", "BNE", "BBG", "BSM", "JMP", "HLT"};
    private static List<String> binaryInstructions = new ArrayList<>();

    public static void main(String[] args) {

        String filePath = "D:\\TRAVAIL\\S5\\Computer architecture\\FinaleAssignmentJava\\src\\code.txt";
        String outputPath = "D:\\TRAVAIL\\S5\\Computer architecture\\FinaleAssignmentJava\\binary_code.txt";

        registers_name.add("t0");
        registers_name.add("t1");
        registers_name.add("t2");
        registers_name.add("t3");

        registers.add(0);
        registers.add(0);
        registers.add(0);
        registers.add(0);

        binaryInstructions = readFileAndConvertToBinary(filePath);
        System.out.println(binaryInstructions);
        writeToFile(binaryInstructions, outputPath);

            System.out.println("\u001B[1;4mWELCOME TO THE ARCHITECTURE SIMULATOR !\n\u001B[0m");
            String mode;
            do {
                Scanner scanner = new Scanner(System.in);
                System.out.println("\u001B[1mCHOOSE YOUR SIMULATION MODE : \u001B[0m");
                System.out.println("Press \u001B[1m1\u001B[0m to \u001B[1mSimulation\u001B[0m !");
                System.out.println("Press \u001B[1m2\u001B[0m to \u001B[1mStep-by-Step Simulation\u001B[0m !");
                mode = scanner.next();

                if(!(mode.equals("1")) && !(mode.equals("2"))){
                    System.out.println("\u001B[31mThis option is not valid ! \u001B[0m");
                }

            }while(!(mode.equals("1")) && !(mode.equals("2")));

            System.out.println("\u001B[31;1mREGISTERS\u001B[0m");
            for (int i= 0; i < registers_name.size(); i++) {
                System.out.print(registers_name.get(i) + " : "+ registers.get(i)+"\n");
            }
            System.out.println("");

            System.out.println("\u001B[34;1mVARIABLES\u001B[0m");
            for (int i= 0; i < variables_name.size(); i++) {
                System.out.print(variables_name.get(i) + " : "+ variables_values.get(i)+"\n");
            }
            System.out.println("");

            System.out.println("\u001B[32;1mSTACK\u001B[0m");
            System.out.println(stack);
            System.out.println("");

            int index=0;
            int index_label;
            String op_code;
            String again;
            int iteration=0;

            while(index < binaryInstructions.size()) {
                if(!(binaryInstructions.get(index).equals(""))){
                op_code = binaryInstructions.get(index).substring(0, 5);
                iteration++;
                index_label = index;
                switch (op_code) {

                    case "00000":
                        LDA(index);
                        break;

                    case "00001":
                        STR(index);
                        break;

                    case "00010":
                        PUSH(index);
                        break;

                    case "00011":
                        POP(index);
                        break;

                    case "00100":
                        AND(index);
                        break;

                    case "00101":
                        OR(index);
                        break;

                    case "00110":
                        NOT(index);
                        break;

                    case "00111":
                        ADD(index);
                        break;

                    case "01000":
                        SUB(index);
                        break;

                    case "01001":
                        DIV(index);
                        break;

                    case "01010":
                        MUL(index);
                        break;

                    case "01011":
                        MOD(index);
                        break;

                    case "01100":
                        INC(index);
                        break;

                    case "01101":
                        DEC(index);
                        break;

                    case "01110":
                        index = BEQ(index);
                        break;

                    case "01111":
                        index = BNE(index);
                        break;

                    case "10000":
                        index = BBG(index);
                        break;

                    case "10001":
                        index = BSM(index);
                        break;

                    case "10010":
                        index = JMP(index);
                        break;

                    case "10011":
                        HLT(mode, index_label,iteration);
                        break;

                }
                if (mode.equals("2")) {
                    do {
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("\u001B[33;1mNEXT INSTRUCTION\u001B[0m\n" + Assembly_code.get(index_label));
                        System.out.println("");
                        System.out.println("Press \u001B[1m1\u001B[0m to \u001B[1mcontinue\u001B[0m");
                        System.out.println("Press \u001B[1m0\u001B[0m to \u001B[1mleave\u001B[0m");
                        again = scanner.next();

                        if (!(again.equals("1")) && !(again.equals("0"))) {
                            System.out.println("\u001B[31mThis option is not valid !\u001B[0m");
                        }
                    } while (!(again.equals("0")) && !(again.equals("1")));
                    if (again.equals("0")) {
                        System.exit(0);
                    } else {
                        System.out.println("\u001B[1mITERATION N°" + iteration + "\u001B[0m");
                        System.out.println("\u001B[31;1mREGISTERS\u001B[0m");
                        for (int i = 0; i < registers_name.size(); i++) {
                            System.out.print(registers_name.get(i) + " : " + registers.get(i) + "\n");
                        }
                        System.out.println("");

                        System.out.println("\u001B[34;1mVARIABLES\u001B[0m");
                        for (int i = 0; i < variables_name.size(); i++) {
                            System.out.print(variables_name.get(i) + " : " + variables_values.get(i) + "\n");
                        }
                        System.out.println("");

                        System.out.println("\u001B[32;1mSTACK\u001B[0m");
                        System.out.println(stack);
                        System.out.println("");

                    }
                }
            }
                index++;
            }

        }


    

    private static List<String> readFileAndConvertToBinary(String filePath) {
        List<String> binaryInstructions = new ArrayList<>();
        try {
            File file = new File(filePath);
            Scanner scanner1 = new Scanner(file);
            Scanner scanner2 = new Scanner(file);
            int code1 = 0;
            int code2 = 0;
            int line_number = 1;
            int line_number2 = 0;

            // LABEL RESEARCH AND ADD LIGN IN ASSEMBLY_CODE TAB//

            while (scanner1.hasNextLine()){
                String line = scanner1.nextLine();

                String[] line_split = line.split(" ");

                if (line.equals("#CODE")){
                    code1 = 1;
                }
                if(code1 == 1 && !line.equals("#CODE")){
                    Assembly_code.add(line);
                }


                if (code1 == 1){
                    if(line_split.length > 1){
                        if(line_split[1].equals(":")){
                            String label_name = line_split[0];
                            Labels.add(label_name);
                            Labels_number.add(String.valueOf(line_number2));
                        }
                    }
                    line_number2++;
                }
            }
            // ENCODING //

            while (scanner2.hasNextLine()) {
                String line = scanner2.nextLine();
                if (line.equals("#CODE")){
                    code2 = 1;
                }

                // STORE DATA //

                if(code2 == 0 && line.charAt(0) != '#'){
                    storeVariable(line);
                }


                // CONVERT CODE TO BIN //
                if(code2 == 1 && line.charAt(0) != '#'){
                    String binaryLine = convertToBinary(line, line_number);
                    binaryInstructions.add(binaryLine);
                    line_number++;
                }

            }

            scanner1.close();
            scanner2.close();
        } catch (FileNotFoundException e) {
            System.out.println("\u001B[31mFichier non trouvÃ©: \u001B[0m" + e.getMessage());
        }
        return binaryInstructions;
    }

    private static String convertToBinary(String line, int line_number) {

        String binary = "";

        String op_code = "";

        String[] line_split = line.split(" ");

        int is_operation = 0;

        for(int i = 0; i < 20; i++){
            if(line_split[0].equals(operations[i])){
                op_code = convertirEnBinaire5Bits(i);
                is_operation = 1;
            }
        }

        if (is_operation == 0 && !line_split[1].equals(":")){
            System.out.println("\u001B[31mThis operation doesn't exist. The program has stopped, line : \u001B[0m" + line_number);
            System.exit(1);
        }

        binary = op_code;


        switch (line_split[0]) {
            case "LDA", "AND", "OR", "ADD", "SUB", "DIV", "MUL", "MOD" -> {
                // REG1 //
                String reg1_LDA = line_split[1];
                char reg1_number_char_LDA = reg1_LDA.charAt(1);
                if (reg1_LDA.charAt(0) == 'T' && reg1_LDA.length() < 3) {
                    if (Character.isDigit(reg1_number_char_LDA)) {
                        int reg1_number = Integer.parseInt(String.valueOf(reg1_number_char_LDA));

                        if (reg1_number > 3) {
                            System.out.println("\u001B[31mThere is a probleme with the first register,the second character is greater than 3. The program has stopped, line : \u001B[0m" + line_number);
                            System.exit(1);
                        } else {
                            String reg1_binary_LDA = Integer.toBinaryString(reg1_number_char_LDA);
                            String reg1_zeroPadding_LDA = "00";
                            reg1_binary_LDA = (reg1_zeroPadding_LDA + reg1_binary_LDA).substring((reg1_zeroPadding_LDA + reg1_binary_LDA).length() - 2);

                            binary = binary + reg1_binary_LDA;
                        }
                    } else {
                        System.out.println("\u001B[31mThere is a probleme with the first register, the second character must be a integer. The program has stopped, line : \u001B[0m" + line_number);
                        System.exit(1);
                    }

                } else {
                    if (reg1_LDA.charAt(0) != 'T') {
                        System.out.println("\u001B[31mThere is a probleme with the first register, the first character is not 'T'. Stop the program me, line : \u001B[0m" + line_number);
                    } else {
                        System.out.println("\u001B[31mThe first register must contain 2 character only. The program has stopped, line : \u001B[0m" + line_number);
                    }
                    System.exit(1);
                }


                // REG2/VAR/CONST/ //

                String reg2_var_const_LDA = line_split[2];
                int is_var_or_const = 0;

                // REG2 //

                if (Objects.equals(reg2_var_const_LDA.charAt(0), 'T') && Character.isDigit(reg2_var_const_LDA.charAt(1)) && reg2_var_const_LDA.length() == 2) {

                    is_var_or_const = 1;

                    String reg2_LDA = line_split[2];
                    char reg2_number_char_LDA = reg2_LDA.charAt(1);

                    if (reg2_LDA.charAt(0) == 'T' && reg2_LDA.length() < 3) {
                        if (Character.isDigit(reg2_number_char_LDA)) {
                            int reg2_number_LDA = Integer.parseInt(String.valueOf(reg2_number_char_LDA));

                            if (reg2_number_LDA > 3) {
                                System.out.println("\u001B[31mThere is a probleme with the second register,the second character is greater than 3. The program has stopped, line : \u001B[0m" + line_number);
                                System.exit(1);
                            } else {
                                String reg2_binary_LDA = Integer.toBinaryString(reg2_number_char_LDA);
                                String reg2_zeroPadding_LDA = "00";
                                reg2_binary_LDA = (reg2_zeroPadding_LDA + reg2_binary_LDA).substring((reg2_zeroPadding_LDA + reg2_binary_LDA).length() - 2);

                                binary = binary + "00" + reg2_binary_LDA + "000000000000000000000";
                            }
                        } else {
                            System.out.println("\u001B[31mThere is a probleme with the second register, the second character must be a integer. The program has stopped, line : \u001B[0m" + line_number);
                            System.exit(1);
                        }

                    } else {
                        if (reg2_LDA.charAt(0) != 'T') {
                            System.out.println("\u001B[31mThere is a probleme with the second register, the first character is not 'T'. Stop the program me, line : \u001B[0m" + line_number);
                        } else {
                            System.out.println("\u001B[31mThe second register must contain 2 character only. The program has stopped, line : \u001B[0m" + line_number);
                        }
                        System.exit(1);
                    }
                }

                // CONST //

                int is_a_number_LDA = 1;
                String const_value_String_LDA = reg2_var_const_LDA.substring(1);
                for (int i = 0; i < const_value_String_LDA.length(); i++) {
                    if (!Character.isDigit(const_value_String_LDA.charAt(i))) {
                        is_a_number_LDA = 0;
                    }
                }
                if (Objects.equals(reg2_var_const_LDA.charAt(0), '$') && is_a_number_LDA == 1) {

                    is_var_or_const = 1;

                    String const_zeroPadding_LDA = "00000000000";
                    int const_value_Integer_LDA = Integer.parseInt(const_value_String_LDA);
                    String const_binary_LDA = Integer.toBinaryString(const_value_Integer_LDA);


                    const_binary_LDA = (const_zeroPadding_LDA + const_binary_LDA).substring((const_zeroPadding_LDA + const_binary_LDA).length() - 11);

                    binary = binary + "10000000000000" + const_binary_LDA;
                }
                if (Objects.equals(reg2_var_const_LDA.charAt(0), '$') && is_a_number_LDA == 0) {
                    System.out.println("\u001B[31mThere is a probleme with the variable, it must be an integer. The program has stopped, line : \u001B[0m" + line_number);
                    System.exit(1);
                }


                // VAR //

                if (is_var_or_const == 0) {
                    String var_LDA = line_split[2];
                    int number_char_var_LDA = 0;
                    String var_zeroPadding_LDA = "0000000000";
                    int var_exist = 0;

                    for (int i = 0; i < variables_name.size(); i++) {
                        String currentVar = variables_name.get(i);

                        if (currentVar.equals(var_LDA)) {
                            number_char_var_LDA = i;
                            var_exist = 1;
                        }
                    }

                    if (var_exist == 0) {
                        System.out.println("\u001B[31mThe variable doesn't exist. The program has stopped, line : \u001B[0m" + line_number);
                        System.exit(1);
                    }

                    String var_binary_LDA = Integer.toBinaryString(number_char_var_LDA);
                    ;
                    var_binary_LDA = (var_zeroPadding_LDA + var_binary_LDA).substring((var_zeroPadding_LDA + var_binary_LDA).length() - 10);

                    binary = binary + "0100" + var_binary_LDA + "00000000000";
                }
            }


            case "STR" -> {
                // VAR //


                String var_STR = line_split[1];
                int number_char_var_STR = 0;
                String var_zeroPadding_STR = "00000000000";
                int var_exist = 0;
                for (int i = 0; i < variables_name.size(); i++) {
                    String currentVar = variables_name.get(i);

                    if (currentVar.equals(var_STR)) {
                        number_char_var_STR = i;
                        var_exist = 1;
                    }
                }
                if (var_exist == 0) {
                    System.out.println("\u001B[31mThe variable doesn't exist. The program has stopped, line : \u001B[0m" + line_number);
                    System.exit(1);
                }
                String var_binary_STR = Integer.toBinaryString(number_char_var_STR);
                ;
                var_binary_STR = (var_zeroPadding_STR + var_binary_STR).substring((var_zeroPadding_STR + var_binary_STR).length() - 11);
                binary = binary + var_binary_STR;


                // REG/CONST //

                String reg_const_STR = line_split[2];
                int is_reg_const_STR = 0;

                // REG //

                if (Objects.equals(reg_const_STR.charAt(0), 'T') && Character.isDigit(reg_const_STR.charAt(1)) && reg_const_STR.length() == 2) {

                    is_reg_const_STR = 1;

                    String reg_STR = line_split[2];
                    char reg_number_char_STR = reg_STR.charAt(1);

                    if (reg_STR.charAt(0) == 'T' && reg_STR.length() < 3) {
                        if (Character.isDigit(reg_number_char_STR)) {
                            int reg_number_STR = Integer.parseInt(String.valueOf(reg_number_char_STR));

                            if (reg_number_STR > 3) {
                                System.out.println("\u001B[31mThere is a probleme with the register,the second character is greater than 3. The program has stopped, line : \u001B[0m" + line_number);
                                System.exit(1);
                            } else {
                                String reg_binary_STR = Integer.toBinaryString(reg_number_char_STR);
                                String reg2_zeroPadding_STR = "00";
                                reg_binary_STR = (reg2_zeroPadding_STR + reg_binary_STR).substring((reg2_zeroPadding_STR + reg_binary_STR).length() - 2);

                                binary = binary + "00" + reg_binary_STR + "000000000000";
                            }
                        } else {
                            System.out.println("\u001B[31mThere is a probleme with the register, the second character must be a integer. The program has stopped, line : \u001B[0m" + line_number);
                            System.exit(1);
                        }

                    } else {
                        if (reg_STR.charAt(0) != 'T') {
                            System.out.println("\u001B[31mThere is a probleme with the register, the first character is not 'T'. Stop the program me, line : \u001B[0m" + line_number);
                        } else {
                            System.out.println("\u001B[31mThe register must contain 2 character only. The program has stopped, line : \u001B[0m" + line_number);
                        }
                        System.exit(1);
                    }
                }

                // CONST //

                int is_a_number_STR = 1;
                String const_value_String_STR = reg_const_STR.substring(1);
                for (int i = 0; i < const_value_String_STR.length(); i++) {
                    if (!Character.isDigit(const_value_String_STR.charAt(i))) {
                        is_a_number_STR = 0;
                    }
                }
                if (Objects.equals(reg_const_STR.charAt(0), '$') && is_a_number_STR == 1) {

                    is_reg_const_STR = 1;

                    String const_zeroPadding_STR = "000000000000";
                    int const_value_Integer_LDA = Integer.parseInt(const_value_String_STR);
                    String const_binary_LDA = Integer.toBinaryString(const_value_Integer_LDA);


                    const_binary_LDA = (const_zeroPadding_STR + const_binary_LDA).substring((const_zeroPadding_STR + const_binary_LDA).length() - 12);

                    binary = binary + "0100" + const_binary_LDA;
                }
                if (Objects.equals(reg_const_STR.charAt(0), '$') && is_a_number_STR == 0) {
                    System.out.println("\u001B[31mThere is a probleme with the variable, it must be an integer. The program has stopped, line : \u001B[0m" + line_number);
                    System.exit(1);
                }

                if(is_reg_const_STR == 0 ){
                    System.out.println("\u001B[31mThe second argument is invalid, you should enter a constant or a register. The program has stopped, line : \u001B[0m" + line_number);
                    System.exit(1);
                }

            }



            case "PUSH" -> {

                // REG/VAR/CONST //
                String reg_var_const_PUSH = line_split[1];
                int is_reg_or_const_PUSH = 0;

                // REG //

                if (Objects.equals(reg_var_const_PUSH.charAt(0), 'T') && Character.isDigit(reg_var_const_PUSH.charAt(1)) && reg_var_const_PUSH.length() == 2) {

                    is_reg_or_const_PUSH = 1;

                    String reg_PUSH = line_split[1];
                    char reg_number_char_PUSH = reg_PUSH.charAt(1);

                    if (reg_PUSH.charAt(0) == 'T' && reg_PUSH.length() < 3) {
                        if (Character.isDigit(reg_number_char_PUSH)) {
                            int reg_number_PUSH = Integer.parseInt(String.valueOf(reg_number_char_PUSH));

                            if (reg_number_PUSH > 3) {
                                System.out.println("\u001B[31mThere is a probleme with the register,the second character is greater than 3. The program has stopped, line : \u001B[0m" + line_number);
                                System.exit(1);
                            } else {
                                String reg_binary_PUSH = Integer.toBinaryString(reg_number_char_PUSH);
                                String reg_zeroPadding_PUSH = "00";
                                reg_binary_PUSH = (reg_zeroPadding_PUSH + reg_binary_PUSH).substring((reg_zeroPadding_PUSH + reg_binary_PUSH).length() - 2);

                                binary = binary + "00" + reg_binary_PUSH + "00000000000000000000000";
                            }
                        } else {
                            System.out.println("\u001B[31mThere is a probleme with the register, the second character must be a integer. The program has stopped, line : \u001B[0m" + line_number);
                            System.exit(1);
                        }

                    } else {
                        if (reg_PUSH.charAt(0) != 'T') {
                            System.out.println("\u001B[31mThere is a probleme with the register, the first character is not 'T'. Stop the program me, line : \u001B[0m" + line_number);
                        } else {
                            System.out.println("\u001B[31mThe register must contain 2 character only. The program has stopped, line : \u001B[0m" + line_number);
                        }
                        System.exit(1);
                    }
                }

                // CONST //

                int is_a_number_PUSH = 1;
                String const_value_String_PUSH = reg_var_const_PUSH.substring(1);
                for (int i = 0; i < const_value_String_PUSH.length(); i++) {
                    if (!Character.isDigit(const_value_String_PUSH.charAt(i))) {
                        is_a_number_PUSH = 0;
                    }
                }
                if (Objects.equals(reg_var_const_PUSH.charAt(0), '$') && is_a_number_PUSH == 1) {

                    is_reg_or_const_PUSH = 1;

                    String const_zeroPadding_PUSH = "000000000000";
                    int const_value_Integer_PUSH = Integer.parseInt(const_value_String_PUSH);
                    String const_binary_PUSH = Integer.toBinaryString(const_value_Integer_PUSH);


                    const_binary_PUSH = (const_zeroPadding_PUSH + const_binary_PUSH).substring((const_zeroPadding_PUSH + const_binary_PUSH).length() - 12);

                    binary = binary + "100000000000000" + const_binary_PUSH;
                }
                if (Objects.equals(reg_var_const_PUSH.charAt(0), '$') && is_a_number_PUSH == 0) {
                    System.out.println("\u001B[31mThere is a probleme with the variable, it must be an integer. The program has stopped, line : \u001B[0m" + line_number);
                    System.exit(1);
                }


                // VAR //

                if (is_reg_or_const_PUSH == 0) {
                    String var_PUSH = line_split[1];
                    int number_char_var_PUSH = 0;
                    String var_zeroPadding_PUSH = "00000000000";
                    int var_exist = 0;

                    for (int i = 0; i < variables_name.size(); i++) {
                        String currentVar = variables_name.get(i);

                        if (currentVar.equals(var_PUSH)) {
                            number_char_var_PUSH = i;
                            var_exist = 1;
                        }
                    }

                    if (var_exist == 0) {
                        System.out.println("\u001B[31mThe variable doesn't exist. The program has stopped, line : \u001B[0m" + line_number);
                        System.exit(1);
                    }

                    String var_binary_PUSH = Integer.toBinaryString(number_char_var_PUSH);
                    ;
                    var_binary_PUSH = (var_zeroPadding_PUSH + var_binary_PUSH).substring((var_zeroPadding_PUSH + var_binary_PUSH).length() - 11);

                    binary = binary + "0100" + var_binary_PUSH + "000000000000";
                }
            }




            case "POP", "NOT", "INC", "DEC" -> {

                // REG //

                int is_reg_POP = 0;

                String reg_POP = line_split[1];
                char reg_number_char_POP = reg_POP.charAt(1);
                if (reg_POP.charAt(0) == 'T' && reg_POP.length() < 3) {
                    if (Character.isDigit(reg_number_char_POP)) {
                        int reg_number = Integer.parseInt(String.valueOf(reg_number_char_POP));

                        if (reg_number > 3) {
                            System.out.println("\u001B[31mThere is a probleme with the register,the second character is greater than 3. The program has stopped, line : \u001B[0m" + line_number);
                            System.exit(1);
                        } else {
                            is_reg_POP = 1;

                            String reg_binary_POP = Integer.toBinaryString(reg_number_char_POP);
                            String reg_zeroPadding_POP = "00";
                            reg_binary_POP = (reg_zeroPadding_POP + reg_binary_POP).substring((reg_zeroPadding_POP + reg_binary_POP).length() - 2);

                            binary = binary + reg_binary_POP + "0000000000000000000000000";
                        }
                    } else {
                        System.out.println("\u001B[31mThere is a probleme with the register, the second character must be a integer. The program has stopped, line : \u001B[0m" + line_number);
                        System.exit(1);
                    }

                } else {
                    if (reg_POP.charAt(0) != 'T') {
                        System.out.println("\u001B[31mThere is a probleme with the register, the first character is not 'T'. Stop the program me, line : \u001B[0m" + line_number);
                    } else {
                        System.out.println("\u001B[31mThe register must contain 2 character only. The program has stopped, line : \u001B[0m" + line_number);
                    }
                    System.exit(1);
                }

                if (is_reg_POP == 0){
                    System.out.println("\u001B[31mThere is a probleme with the register. The program has stopped, line : \u001B[0m" + line_number);
                    System.exit(1);
                }
            }


            case "BEQ", "BNE", "BBG", "BSM" -> {

                // REG1/VAR1/CONST1 //

                String reg1_var1_const1_BEQ = line_split[1];
                int is_var_or_const1 = 0;

                // REG1 //

                if (Objects.equals(reg1_var1_const1_BEQ.charAt(0), 'T') && Character.isDigit(reg1_var1_const1_BEQ.charAt(1)) && reg1_var1_const1_BEQ.length() == 2) {

                    is_var_or_const1 = 1;

                    String reg1_BEQ = line_split[1];
                    char reg1_number_char_BEQ = reg1_BEQ.charAt(1);

                    if (reg1_BEQ.charAt(0) == 'T' && reg1_BEQ.length() < 3) {
                        if (Character.isDigit(reg1_number_char_BEQ)) {
                            int reg1_number_BEQ = Integer.parseInt(String.valueOf(reg1_number_char_BEQ));

                            if (reg1_number_BEQ > 3) {
                                System.out.println("\u001B[31mThere is a probleme with the first register,the second character is greater than 3. The program has stopped, line : \u001B[0m" + line_number);
                                System.exit(1);
                            } else {
                                String reg1_binary_BEQ = Integer.toBinaryString(reg1_number_char_BEQ);
                                String reg1_zeroPadding_BEQ = "00";
                                reg1_binary_BEQ = (reg1_zeroPadding_BEQ + reg1_binary_BEQ).substring((reg1_zeroPadding_BEQ + reg1_binary_BEQ).length() - 2);

                                binary = binary + "00" + reg1_binary_BEQ + "0000000";
                            }
                        } else {
                            System.out.println("\u001B[31mThere is a probleme with the first register, the second character must be a integer. The program has stopped, line : \u001B[0m" + line_number);
                            System.exit(1);
                        }

                    } else {
                        if (reg1_BEQ.charAt(0) != 'T') {
                            System.out.println("\u001B[31mThere is a probleme with the first register, the first character is not 'T'. Stop the program me, line : \u001B[0m" + line_number);
                        } else {
                            System.out.println("\u001B[31mThe first register must contain 2 character only. The program has stopped, line : \u001B[0m" + line_number);
                        }
                        System.exit(1);
                    }
                }

                // CONST1 //

                int is_a_number_BEQ = 1;
                String const_value_String_BEQ = reg1_var1_const1_BEQ.substring(1);
                for (int i = 0; i < const_value_String_BEQ.length(); i++) {
                    if (!Character.isDigit(const_value_String_BEQ.charAt(i))) {
                        is_a_number_BEQ = 0;
                    }
                }
                if (Objects.equals(reg1_var1_const1_BEQ.charAt(0), '$') && is_a_number_BEQ == 1) {

                    is_var_or_const1 = 1;

                    String const_zeroPadding_BEQ = "0000";
                    int const_value_Integer_BEQ = Integer.parseInt(const_value_String_BEQ);
                    String const_binary_BEQ = Integer.toBinaryString(const_value_Integer_BEQ);


                    const_binary_BEQ = (const_zeroPadding_BEQ + const_binary_BEQ).substring((const_zeroPadding_BEQ + const_binary_BEQ).length() - 4);

                    binary = binary + "1000000" + const_binary_BEQ;
                }
                if (Objects.equals(reg1_var1_const1_BEQ.charAt(0), '$') && is_a_number_BEQ == 0) {
                    System.out.println("\u001B[31mThere is a probleme with the variable, it must be an integer. The program has stopped, line : \u001B[0m" + line_number);
                    System.exit(1);
                }


                // VAR1 //

                if (is_var_or_const1 == 0) {
                    String var_BEQ = line_split[1];
                    int number_char_var_BEQ = 0;
                    String var_zeroPadding_BEQ = "000";
                    int var_exist = 0;

                    for (int i = 0; i < variables_name.size(); i++) {
                        String currentVar = variables_name.get(i);

                        if (currentVar.equals(var_BEQ)) {
                            number_char_var_BEQ = i;
                            var_exist = 1;
                        }
                    }

                    if (var_exist == 0) {
                        System.out.println("\u001B[31mThe variable doesn't exist. The program has stopped, line : \u001B[0m" + line_number);
                        System.exit(1);
                    }

                    String var_binary_BEQ = Integer.toBinaryString(number_char_var_BEQ);

                    var_binary_BEQ = (var_zeroPadding_BEQ + var_binary_BEQ).substring((var_zeroPadding_BEQ + var_binary_BEQ).length() - 3);

                    binary = binary + "0100" + var_binary_BEQ + "0000";
                }


                // REG2/VAR2/CONST2 //


                String reg2_var2_const2_BEQ = line_split[2];
                int is_var_or_const2 = 0;

                // REG2 //

                if (Objects.equals(reg2_var2_const2_BEQ.charAt(0), 'T') && Character.isDigit(reg2_var2_const2_BEQ.charAt(1)) && reg2_var2_const2_BEQ.length() == 2) {

                    is_var_or_const2 = 1;

                    String reg2_BEQ = line_split[2];
                    char reg2_number_char_BEQ = reg2_BEQ.charAt(1);

                    if (reg2_BEQ.charAt(0) == 'T' && reg2_BEQ.length() < 3) {
                        if (Character.isDigit(reg2_number_char_BEQ)) {
                            int reg2_number_BEQ = Integer.parseInt(String.valueOf(reg2_number_char_BEQ));

                            if (reg2_number_BEQ > 3) {
                                System.out.println("\u001B[31mThere is a probleme with the second register,the second character is greater than 3. The program has stopped, line : \u001B[0m" + line_number);
                                System.exit(1);
                            } else {
                                String reg2_binary_BEQ = Integer.toBinaryString(reg2_number_char_BEQ);
                                String reg2_zeroPadding_BEQ = "00";
                                reg2_binary_BEQ = (reg2_zeroPadding_BEQ + reg2_binary_BEQ).substring((reg2_zeroPadding_BEQ + reg2_binary_BEQ).length() - 2);

                                binary = binary + "00" + reg2_binary_BEQ + "0000000";
                            }
                        } else {
                            System.out.println("\u001B[31mThere is a probleme with the second register, the second character must be a integer. The program has stopped, line : \u001B[0m" + line_number);
                            System.exit(1);
                        }

                    } else {
                        if (reg2_BEQ.charAt(0) != 'T') {
                            System.out.println("\u001B[31mThere is a probleme with the second register, the first character is not 'T'. Stop the program me, line : \u001B[0m" + line_number);
                        } else {
                            System.out.println("\u001B[31mThe second register must contain 2 character only. The program has stopped, line : \u001B[0m" + line_number);
                        }
                        System.exit(1);
                    }
                }

                // CONST2 //

                int is_a_number_BEQ2 = 1;
                String const_value_String_BEQ2 = reg2_var2_const2_BEQ.substring(1);
                for (int i = 0; i < const_value_String_BEQ2.length(); i++) {
                    if (!Character.isDigit(const_value_String_BEQ2.charAt(i))) {
                        is_a_number_BEQ2 = 0;
                    }
                }
                if (Objects.equals(reg2_var2_const2_BEQ.charAt(0), '$') && is_a_number_BEQ2 == 1) {

                    is_var_or_const2 = 1;

                    String const_zeroPadding_BEQ2 = "0000";
                    int const_value_Integer_BEQ2 = Integer.parseInt(const_value_String_BEQ2);
                    String const_binary_BEQ2 = Integer.toBinaryString(const_value_Integer_BEQ2);


                    const_binary_BEQ2 = (const_zeroPadding_BEQ2 + const_binary_BEQ2).substring((const_zeroPadding_BEQ2 + const_binary_BEQ2).length() - 4);

                    binary = binary + "1000000" + const_binary_BEQ2;
                }
                if (Objects.equals(reg2_var2_const2_BEQ.charAt(0), '$') && is_a_number_BEQ2 == 0) {
                    System.out.println("\u001B[31mThere is a probleme with the second variable, it must be an integer. The program has stopped, line : \u001B[0m" + line_number);
                    System.exit(1);
                }


                // VAR2 //

                if (is_var_or_const2 == 0) {
                    String var_BEQ2 = line_split[2];
                    int number_char_var_BEQ2 = 0;
                    String var_zeroPadding_BEQ2 = "000";
                    int var_exist = 0;

                    for (int i = 0; i < variables_name.size(); i++) {
                        String currentVar = variables_name.get(i);

                        if (currentVar.equals(var_BEQ2)) {
                            number_char_var_BEQ2 = i;
                            var_exist = 1;
                        }
                    }

                    if (var_exist == 0) {
                        System.out.println("\u001B[31mThe second variable doesn't exist. The program has stopped, line : \u001B[0m" + line_number);
                        System.exit(1);
                    }

                    String var_binary_BEQ2 = Integer.toBinaryString(number_char_var_BEQ2);

                    var_binary_BEQ2 = (var_zeroPadding_BEQ2 + var_binary_BEQ2).substring((var_zeroPadding_BEQ2 + var_binary_BEQ2).length() - 3);

                    binary = binary + "0100" + var_binary_BEQ2 + "0000";
                }

                // LABEL //

                if(line_split.length < 4){
                    System.out.println("\u001B[31mThere is no label. The program has stopped, line : \u001B[0m" + line_number);
                    System.exit(1);
                }

                String label = line_split[3];

                int is_label = 0;
                String label_number = "";
                String label_zeroPadding_BEQ = "00000";

                for(int i = 0; i < Labels.toArray().length; i++){
                    String currentLabel = Labels.get(i);
                    if(currentLabel.equals(label)){
                        is_label = 1;
                        label_number = Labels_number.get(i);
                    }
                }


                int label_binary_int_BEQ = Integer.parseInt(label_number);

                String label_binary_BEQ = Integer.toBinaryString(label_binary_int_BEQ);

                label_binary_BEQ = (label_zeroPadding_BEQ + label_binary_BEQ).substring((label_zeroPadding_BEQ + label_binary_BEQ).length() - 5);


                binary = binary + label_binary_BEQ;
            }


            case "JMP" -> {

                if(line_split.length < 2){
                    System.out.println("\u001B[31mThere is no label. The program has stopped, line : \u001B[0m" + line_number);
                    System.exit(1);
                }

                String label = line_split[1];

                int is_label = 0;
                String label_number = "";
                String label_zeroPadding_JMP = "00000";

                for(int i = 0; i < Labels.toArray().length; i++){
                    String currentLabel = Labels.get(i);
                    if(currentLabel.equals(label)){
                        is_label = 1;
                        label_number = Labels_number.get(i);
                    }
                }


                int label_binary_int_JMP = Integer.parseInt(label_number);

                String label_binary_JMP = Integer.toBinaryString(label_binary_int_JMP);

                label_binary_JMP = (label_zeroPadding_JMP + label_binary_JMP).substring((label_zeroPadding_JMP + label_binary_JMP).length() - 5);


                binary = binary + "0000000000000000000000" + label_binary_JMP;


            }

            case "HLT" -> {
                String zeroPadding_HLT = "000000000000000000000000000";
                binary = op_code + zeroPadding_HLT;
            }
            default -> {
            }

        }

        return binary;
    }
    private static void storeVariable(String line){

        String[] line_split = line.split(" ");
        variables_name.add(line_split[0]);
        variables_values.add(Integer.parseInt(line_split[1]));
    }

    private static String convertirEnBinaire5Bits(int number) {

        String binary = Integer.toBinaryString(number);
        String zeroPadding = "00000";
        return (zeroPadding + binary).substring((zeroPadding + binary).length() - 5);
    }

    public static void writeToFile(List<String> binaryInstructions, String outputPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            for (String instruction : binaryInstructions) {
                writer.write(instruction);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void LDA(int line){
        String index_reg1 = binaryInstructions.get(line).substring(5,7);
        String type = binaryInstructions.get(line).substring(7,9);

        if(type.equals("00")){
            String index_reg2 = binaryInstructions.get(line).substring(9,11);
            registers.set(Integer.parseInt(index_reg1, 2),registers.get(Integer.parseInt(index_reg2, 2)));
        }else if (type.equals("01")){
            String index_var = binaryInstructions.get(line).substring(11,21);
            registers.set(Integer.parseInt(index_reg1, 2),variables_values.get(Integer.parseInt(index_var, 2)));
        }else {
            String constant = binaryInstructions.get(line).substring(21,32);
            registers.set(Integer.parseInt(index_reg1, 2),Integer.parseInt(constant, 2));
        }
    }

    public static void STR(int line){
        String index_var = binaryInstructions.get(line).substring(5,16);
        String type = binaryInstructions.get(line).substring(16,18);

        if(type.equals("00")){
            String index_reg = binaryInstructions.get(line).substring(18,20);
            variables_values.set(Integer.parseInt(index_var, 2),registers.get(Integer.parseInt(index_reg, 2)));
        }else {
            String constant = binaryInstructions.get(line).substring(20,32);
            variables_values.set(Integer.parseInt(index_var, 2),Integer.parseInt(constant, 2));
        }

    }

    public static void PUSH(int line){
        String type = binaryInstructions.get(line).substring(5,7);

        if(type.equals("00")){
            String index_reg = binaryInstructions.get(line).substring(7,9);
            stack.push(registers.get(Integer.parseInt(index_reg,2)));
        }else if (type.equals("01")){
            String index_var = binaryInstructions.get(line).substring(9,20);
            stack.push(variables_values.get(Integer.parseInt(index_var,2)));
        }else {
            String constant = binaryInstructions.get(line).substring(20,32);
            stack.push(Integer.parseInt(constant,2));
        }

    }

    public static void POP(int line){
        String index_reg = binaryInstructions.get(line).substring(5,7);
        registers.set(Integer.parseInt(index_reg,2),stack.pop());
    }

    public static void AND(int line){
        String index_reg1 = binaryInstructions.get(line).substring(5,7);
        String type = binaryInstructions.get(line).substring(7,9);
        int reg1_value=  registers.get(Integer.parseInt(index_reg1,2));
        int result_and_int;

        if(type.equals("00")){
            String index_reg2 = binaryInstructions.get(line).substring(9,11);
            int reg2_value =  registers.get(Integer.parseInt(index_reg2,2));
            result_and_int =reg1_value & reg2_value;
            registers.set(Integer.parseInt(index_reg1,2),result_and_int);
        }else if (type.equals("01")){
            String index_var = binaryInstructions.get(line).substring(11,21);
            int var_value =  variables_values.get(Integer.parseInt(index_var,2));
            result_and_int =reg1_value & var_value;
            registers.set(Integer.parseInt(index_reg1,2),result_and_int);
        }else {
            String constant = binaryInstructions.get(line).substring(21,32);
            result_and_int =reg1_value & Integer.parseInt(constant,2);
            registers.set(Integer.parseInt(index_reg1, 2),result_and_int);
        }

    }

    public static void OR(int line) {
        String index_reg1 = binaryInstructions.get(line).substring(5, 7);
        String type = binaryInstructions.get(line).substring(7, 9);

        int reg1_value = registers.get(Integer.parseInt(index_reg1, 2));
        int result_or_int;
        if (type.equals("00")) {
            String index_reg2 = binaryInstructions.get(line).substring(9, 11);
            int reg2_value = registers.get(Integer.parseInt(index_reg2, 2));
            result_or_int = reg1_value | reg2_value;
            registers.set(Integer.parseInt(index_reg1, 2), result_or_int);
        } else if (type.equals("01")) {
            String index_var = binaryInstructions.get(line).substring(11, 21);
            int var_value = (int)variables_values.get(Integer.parseInt(index_var, 2));
            result_or_int = reg1_value | var_value;
            registers.set(Integer.parseInt(index_reg1, 2), result_or_int);
        } else {
            String constant = binaryInstructions.get(line).substring(21, 32);
            result_or_int = reg1_value | Integer.parseInt(constant, 2);
            registers.set(Integer.parseInt(index_reg1, 2), result_or_int);
        }
    }

    public static void NOT(int line){
        String index_reg = binaryInstructions.get(line).substring(5,7);
        registers.set(Integer.parseInt(index_reg,2),registers.get(Integer.parseInt(index_reg,2))^(1 << Integer.toBinaryString(registers.get(Integer.parseInt(index_reg,2))).length()) - 1);
}

    public static void ADD(int line){
        String index_reg1 = binaryInstructions.get(line).substring(5,7);
        String type = binaryInstructions.get(line).substring(7,9);

        if(type.equals("00")){
            String index_reg2 = binaryInstructions.get(line).substring(9,11);
            registers.set(Integer.parseInt(index_reg1, 2),registers.get(Integer.parseInt(index_reg2, 2))+registers.get(Integer.parseInt(index_reg1, 2)));
        }else if (type.equals("01")){
            String index_var = binaryInstructions.get(line).substring(11,21);
            registers.set(Integer.parseInt(index_reg1, 2),variables_values.get(Integer.parseInt(index_var, 2))+registers.get(Integer.parseInt(index_reg1, 2)));
        }else {
            String constant = binaryInstructions.get(line).substring(21,32);
            registers.set(Integer.parseInt(index_reg1, 2),Integer.parseInt(constant, 2)+registers.get(Integer.parseInt(index_reg1, 2)));
        }
    }

    public static void SUB(int line){
        String index_reg1 = binaryInstructions.get(line).substring(5,7);
        String type = binaryInstructions.get(line).substring(7,9);

        if(type.equals("00")){
            String index_reg2 = binaryInstructions.get(line).substring(9,11);
            registers.set(Integer.parseInt(index_reg1, 2),registers.get(Integer.parseInt(index_reg2, 2))-registers.get(Integer.parseInt(index_reg1, 2)));
        }else if (type.equals("01")){
            String index_var = binaryInstructions.get(line).substring(11,21);
            registers.set(Integer.parseInt(index_reg1, 2),variables_values.get(Integer.parseInt(index_var, 2))-registers.get(Integer.parseInt(index_reg1, 2)));
        }else {
            String constant = binaryInstructions.get(line).substring(21,32);
            registers.set(Integer.parseInt(index_reg1, 2),Integer.parseInt(constant, 2)-registers.get(Integer.parseInt(index_reg1, 2)));
        }
    }

    public static void DIV(int line){
        String index_reg1 = binaryInstructions.get(line).substring(5,7);
        String type = binaryInstructions.get(line).substring(7,9);

        if(type.equals("00")){
            String index_reg2 = binaryInstructions.get(line).substring(9,11);
            registers.set(Integer.parseInt(index_reg1, 2),registers.get(Integer.parseInt(index_reg2, 2))/registers.get(Integer.parseInt(index_reg1, 2)));

        }else if (type.equals("01")){
            String index_var = binaryInstructions.get(line).substring(11,21);
            registers.set(Integer.parseInt(index_reg1, 2),variables_values.get(Integer.parseInt(index_var, 2))/registers.get(Integer.parseInt(index_reg1, 2)));
        }else {
            String constant = binaryInstructions.get(line).substring(21,32);
            registers.set(Integer.parseInt(index_reg1, 2),Integer.parseInt(constant, 2)/registers.get(Integer.parseInt(index_reg1, 2)));
        }
    }

    public static void MUL(int line){
        String index_reg1 = binaryInstructions.get(line).substring(5,7);
        String type = binaryInstructions.get(line).substring(7,9);

        if(type.equals("00")){
            String index_reg2 = binaryInstructions.get(line).substring(9,11);
            registers.set(Integer.parseInt(index_reg1, 2),registers.get(Integer.parseInt(index_reg2, 2))*registers.get(Integer.parseInt(index_reg1, 2)));

        }else if (type.equals("01")){
            String index_var = binaryInstructions.get(line).substring(11,21);
            registers.set(Integer.parseInt(index_reg1, 2),variables_values.get(Integer.parseInt(index_var, 2))*registers.get(Integer.parseInt(index_reg1, 2)));
        }else {
            String constant = binaryInstructions.get(line).substring(21,32);
            registers.set(Integer.parseInt(index_reg1, 2),Integer.parseInt(constant, 2)*registers.get(Integer.parseInt(index_reg1, 2)));
        }
    }

    public static void MOD(int line){
        String index_reg1 = binaryInstructions.get(line).substring(5,7);
        String type = binaryInstructions.get(line).substring(7,9);

        if(type.equals("00")){
            String index_reg2 = binaryInstructions.get(line).substring(9,11);
            registers.set(Integer.parseInt(index_reg1, 2),registers.get(Integer.parseInt(index_reg2, 2))%registers.get(Integer.parseInt(index_reg1, 2)));
        }else if (type.equals("01")){
            String index_var = binaryInstructions.get(line).substring(11,21);
            registers.set(Integer.parseInt(index_reg1, 2),variables_values.get(Integer.parseInt(index_var, 2))%registers.get(Integer.parseInt(index_reg1, 2)));
        }else {
            String constant = binaryInstructions.get(line).substring(21,32);
            registers.set(Integer.parseInt(index_reg1, 2),Integer.parseInt(constant, 2)%registers.get(Integer.parseInt(index_reg1, 2)));
        }
    }

    public static void INC(int line){
        String index_reg = binaryInstructions.get(line).substring(5,7);
        registers.set(Integer.parseInt(index_reg,2),registers.get(Integer.parseInt(index_reg,2))+1);
    }

    public static void DEC(int line){
        String index_reg = binaryInstructions.get(line).substring(5,7);
        registers.set(Integer.parseInt(index_reg,2),registers.get(Integer.parseInt(index_reg,2))-1);
    }

    public static int BEQ (int line){
        String type1 = binaryInstructions.get(line).substring(5,7);
        String label = binaryInstructions.get(line).substring(27,32);;
        int value1,value2;

        if(type1.equals("00")){
            String index_value1 = binaryInstructions.get(line).substring(7,9);
            value1 = registers.get(Integer.parseInt(index_value1,2));
        }else if (type1.equals("01")){
            String index_value1 = binaryInstructions.get(line).substring(9,12);
            value1 = variables_values.get(Integer.parseInt(index_value1,2));
        }else {
            String constant = binaryInstructions.get(line).substring(12,16);
            value1 = Integer.parseInt(constant,2);
        }
        String type2 = binaryInstructions.get(line).substring(16,18);

        if(type2.equals("00")){
            String index_value2 = binaryInstructions.get(line).substring(18,20);
            value2 = registers.get(Integer.parseInt(index_value2,2));

        }else if (type2.equals("01")){
            String index_value2 = binaryInstructions.get(line).substring(20,23);
            value2 = variables_values.get(Integer.parseInt(index_value2,2));

        }else {
            String constant = binaryInstructions.get(line).substring(23,27);
            value2 = Integer.parseInt(constant,2);
        }
        if(value1==value2){
            line = Integer.parseInt(label,2)-1;
        }

        return line;
    }

    public static int BNE (int line){
        String type1 = binaryInstructions.get(line).substring(5,7);
        String label = binaryInstructions.get(line).substring(27,32);;
        int value1,value2;

        if(type1.equals("00")){
            String index_value1 = binaryInstructions.get(line).substring(7,9);
            value1 = registers.get(Integer.parseInt(index_value1,2));
        }else if (type1.equals("01")){
            String index_value1 = binaryInstructions.get(line).substring(9,12);
            value1 = variables_values.get(Integer.parseInt(index_value1,2));
        }else {
            String constant = binaryInstructions.get(line).substring(12,16);
            value1 = Integer.parseInt(constant,2);
        }

        String type2 = binaryInstructions.get(line).substring(16,18);

        if(type2.equals("00")){
            String index_value2 = binaryInstructions.get(line).substring(18,20);
            value2 = registers.get(Integer.parseInt(index_value2,2));
        }else if (type2.equals("01")){
            String index_value2 = binaryInstructions.get(line).substring(20,23);
            value2 = variables_values.get(Integer.parseInt(index_value2,2));
        }else {
            String constant = binaryInstructions.get(line).substring(23,27);
            value2 = Integer.parseInt(constant,2);
        }

        if(value1!=value2){
            line = Integer.parseInt(label,2)-1;
        }

        return line;
    }

    public static int BBG (int line){
        String type1 = binaryInstructions.get(line).substring(5,7);
        String label = binaryInstructions.get(line).substring(27,32);;
        int value1,value2;

        if(type1.equals("00")){
            String index_value1 = binaryInstructions.get(line).substring(7,9);
            value1 = registers.get(Integer.parseInt(index_value1,2));
        }else if (type1.equals("01")){
            String index_value1 = binaryInstructions.get(line).substring(9,12);
            value1 = variables_values.get(Integer.parseInt(index_value1,2));
        }else {
            String constant = binaryInstructions.get(line).substring(12,16);
            value1 = Integer.parseInt(constant,2);
        }

        String type2 = binaryInstructions.get(line).substring(16,18);

        if(type2.equals("00")){
            String index_value2 = binaryInstructions.get(line).substring(18,20);
            value2 = registers.get(Integer.parseInt(index_value2,2));
        }else if (type2.equals("01")){
            String index_value2 = binaryInstructions.get(line).substring(20,23);
            value2 = variables_values.get(Integer.parseInt(index_value2,2));
        }else {
            String constant = binaryInstructions.get(line).substring(23,27);
            value2 = Integer.parseInt(constant,2);
        }

        if(value1>value2){
            line = Integer.parseInt(label,2)-1;
        }

        return line;
    }

    public static int BSM (int line){
        String type1 = binaryInstructions.get(line).substring(5,7);
        String label = binaryInstructions.get(line).substring(27,32);
        int value1,value2;

        if(type1.equals("00")){
            String index_value1 = binaryInstructions.get(line).substring(7,9);
            value1 = registers.get(Integer.parseInt(index_value1,2));
        }else if (type1.equals("01")){
            String index_value1 = binaryInstructions.get(line).substring(9,12);
            value1 = variables_values.get(Integer.parseInt(index_value1,2));
        }else {
            String constant = binaryInstructions.get(line).substring(12,16);
            value1 = Integer.parseInt(constant,2);
        }

        String type2 = binaryInstructions.get(line).substring(16,18);

        if(type2.equals("00")){
            String index_value2 = binaryInstructions.get(line).substring(18,20);
            value2 = registers.get(Integer.parseInt(index_value2,2));
        }else if (type2.equals("01")){
            String index_value2 = binaryInstructions.get(line).substring(20,23);
            value2 = variables_values.get(Integer.parseInt(index_value2,2));
        }else {
            String constant = binaryInstructions.get(line).substring(23,27);
            value2 = Integer.parseInt(constant,2);
        }

        if(value1<value2){
            System.out.println("PETIT");
            line = Integer.parseInt(label,2)-1;
        }

        return line;
    }

    public static int JMP(int line){
        String label = binaryInstructions.get(line).substring(27,32);
        line = Integer.parseInt(label,2)-1;
        return line;
    }

    public static void HLT(String mode,int index_label,int iteration){
        String again;
        if(mode.equals("2")) {
            do {

                Scanner scanner = new Scanner(System.in);
                System.out.println("\u001B[33;1mNEXT INSTRUCTION\u001B[0m\n"+ Assembly_code.get(index_label));
                System.out.println("");
                System.out.println("Press \u001B[1m1\u001B[0m to \u001B[1mcontinue\u001B[0m");
                System.out.println("Press \u001B[1m0\u001B[0m to \u001B[1mleave\u001B[0m");
                again = scanner.next();

                if(!(again.equals("1")) && !(again.equals("0"))){
                    System.out.println("\u001B[31mThis option is not valid !\u001B[0m");
                }
            }while(!(again.equals("0")) && !(again.equals("1")));
            if(again.equals("0")){
                System.exit(0);
            }
        }
        System.out.println("\u001B[1mITERATION N°" + iteration + "\u001B[0m");
        System.out.println("\u001B[31;1mREGISTERS\u001B[0m");
            for (int i = 0; i < registers_name.size(); i++) {
                System.out.print(registers_name.get(i) + " : " + registers.get(i) + "\n");
            }
            System.out.println("");

            System.out.println("\u001B[34;1mVARIABLES\u001B[0m");
            for (int i = 0; i < variables_name.size(); i++) {
                System.out.print(variables_name.get(i) + " : " + variables_values.get(i) + "\n");
            }
            System.out.println("");

            System.out.println("\u001B[32;1mSTACK\u001B[0m");
            System.out.println(stack);
            System.out.println("");

        System.out.println("\u001B[1mEND OF THE SIMULATION! \u263A\u001B[0m");
        System.exit(0);
    }
}