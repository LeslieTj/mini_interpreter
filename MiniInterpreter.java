import java.util.ArrayList;
import java.util.Arrays;

/**
 * A mini-interpreter for a toy programming language that allows the following:
 * <p>
 * 1. The use of variables that consist of a single letter (e.g. A, a, ...)
 * 2. The use of whole numbers: ( e.g. -1, -20, 0, 1, 200)
 * 3. Assignment (=): ( e.g. A = B, A = 10, )
 * 4. Addition of exactly two elements (variables or constants) (+) : ( e.g. C = A + B, D = 1 + A, ... )
 * 5. The ability to "return" a value when a single variable or constant is on a line by itself ( e.g. A, B, 10)
 */
public class MiniInterpreter {
    public static ArrayList<Integer> values = new ArrayList<>();
    public static ArrayList<String> symbols = new ArrayList<>();

    /**
     * If the debugFlag is true, the interpreter would print the debug information.
     * And if it is false, there would be no debug information.
     */
    public static boolean debugFlag = true;

    /**
     * checkOperationType method checks the type of the operation(addition, assignment
     * or return). The pseudo-code is as below:
     * <p>
     * if statement contains '+':
     *      addition
     * else if statement contains '=':
     *      assignment
     * else:
     *      return
     *
     * @param string The statement to be checked.
     * @return "0" for an addition statement, "1" for an assignment statement and "2" for a return statement.
     */
    public static String checkOperationType(String string) {

        if (string.contains("+")) {
            return "0";
        } else if (string.contains("=")) {
            return "1";
        } else {
            return "2";
        }
    }

    /**
     * parseStatement method can parse a statement into an array of type String.
     * For example, when we pass the statement "A = 2 + 1" into this method,
     * the output should be:
     * [0, A, 2, 1]
     * Note that the first element in the output array is the type of the corresponding
     * statement. In the above example, the first element "0" means "A = 2 + 1" is an
     * addition statement.
     *
     * @param string The statement to be parsed.
     * @return
     */
    public static String[] parseStatement(String string) {

        String[] strings = string.split(" ");

        ArrayList<String> operandList = new ArrayList<>();
        for (String s : strings) {
            if (!s.equals(" ") && !s.equals("=") && !s.equals("+")) {
                operandList.add(s);
            }
        }

        String[] result = new String[operandList.size() + 1];

        result[0] = checkOperationType(string);
        for (int i = 0; i < operandList.size(); i++) {
            result[i + 1] = operandList.get(i);
        }

        return result;
    }

    /**
     * printDebugInformation method can print out the debug information.
     */
    public static void printDebugInformation() {
        String[] symbolArray = new String[symbols.size()];
        for (int i = 0; i < symbolArray.length; i++) {
            symbolArray[i] = symbols.get(i);
        }
        int[] valueArray = new int[values.size()];
        for (int i = 0; i < symbolArray.length; i++) {
            valueArray[i] = values.get(i);
        }
        System.out.println(Arrays.toString(symbolArray));
        System.out.println(Arrays.toString(valueArray));
        System.out.println("-------------------------");
    }

    /**
     * executeAddition method executes an addition statement. If the debug
     * flag is true, this method would print the debug information of the
     * corresponding statement, by calling the printDebugInformation method.
     *
     * @param string An addition statement.
     */
    public static void executeAddition(String[] string) {
        int sum = 0;
        if (Character.isDigit(string[2].charAt(0)) || string[2].length() > 1) {
            sum += Integer.parseInt(string[2]);
        } else {
            int index = symbols.indexOf(string[2]);
            sum += values.get(index);
        }

        if (Character.isDigit(string[3].charAt(0)) || string[3].length() > 1) {
            sum += Integer.parseInt(string[3]);
        } else {
            int index = symbols.indexOf(string[3]);
            sum += values.get(index);
        }

        if (symbols.contains(string[1])) {
            int index = symbols.indexOf(string[1]);
            values.set(index, sum);
        } else {
            symbols.add(string[1]);
            values.add(sum);
        }

        if (debugFlag) {
            System.out.println(Arrays.toString(string));
            printDebugInformation();
        }
    }

    /**
     * executeAssignment method executes an assignment statement. If the debug
     * flag is true, this method would print the debug information of the
     * corresponding statement, by calling the printDebugInformation method.
     *
     * @param string An assignment statement.
     */
    public static void executeAssignment(String[] string) {

        if (symbols.contains(string[1])) {
            int index = symbols.indexOf(string[1]);
            if (Character.isDigit(string[2].charAt(0))) {
                values.set(index, Integer.parseInt(string[2]));
            } else {
                values.set(index, values.get(symbols.indexOf(string[2])));
            }
        } else {
            symbols.add(string[1]);
            if (Character.isDigit(string[2].charAt(0)) || string[2].length() > 1) {
                values.add(Integer.parseInt(string[2]));
            } else {
                int index = symbols.indexOf(string[2]);
                values.add(values.get(index));
            }

        }

        if (debugFlag) {
            System.out.println(Arrays.toString(string));
            printDebugInformation();
        }
    }

    /**
     * executeReturn method returns the final result of a program.
     *
     * @param string A return statement.
     * @return The final result of the program.
     */
    public static int executeReturn(String[] string) {
        int index = symbols.indexOf(string[1]);
        return values.get(index);
    }

    /**
     * executeProgram controls the flow of the program execution.
     *
     * @param string The program to be executed.
     */
    public static void executeProgram(String string) {
        String[] strings = string.split("\n");
        for (String s : strings) {
            String[] statement = parseStatement(s);
            if (statement[0].equals("0")) {
                executeAddition(statement);
            } else if (statement[0].equals("1")) {
                executeAssignment(statement);
            } else {
                System.out.println("Returning...");
                System.out.println("Your program: \n" + string);
                System.out.println("Your program returned: " + executeReturn(statement));
            }
        }

        symbols = new ArrayList<>();
        values = new ArrayList<>();
    }

    // main method to test the MiniInterpreter.
    public static void main(String[] args) {
        String program1 = "A = 2\n" +
                "B = 8\n" +
                "C = A + B\n" +
                "C";

        String program2 = "A = 2\n" +
                "B = 22\n" +
                "Z = 91\n" +
                "K = A + B\n" +
                "Z = K + A\n" +
                "Z";

        String program3 = "A = 2 + 1\n" +
                "B = A + 9\n" +
                "C = A + B\n" +
                "A";

        String program4 = "A = -15\n" +
                "B = -6 + A\n" +
                "C = -1 + 2000\n" +
                "D = A + C\n" +
                "D";

        executeProgram(program1);
        System.out.println("-------------------------");

        executeProgram(program2);
        System.out.println("-------------------------");

        executeProgram(program3);
        System.out.println("-------------------------");

        executeProgram(program4);
        System.out.println("-------------------------");

    }
}
