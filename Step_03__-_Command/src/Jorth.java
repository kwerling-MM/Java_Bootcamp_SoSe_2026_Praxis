public class Jorth {

    public static String run(String input) {
    if (input == null || input.isBlank()) {
        return "[]";
    }

        java.util.List<Integer> stack = new java.util.ArrayList<>();
        String[] tokens = input.trim().split("\\s+");

        for (String token : tokens) {
            if (token.equals("+")) {
                if (stack.size() >= 2) {
                    int second = stack.remove(stack.size() - 1);
                    int first = stack.remove(stack.size() - 1);
                    stack.add(first + second);
                }
            } else if (token.equals("-")) {
                if (stack.size() >= 2) {
                    int second = stack.remove(stack.size() - 1); // Rechter Operand
                    int first = stack.remove(stack.size() - 1);  // Linker Operand
                    stack.add(first - second);
                }
            } else {
                try {
                    stack.add(Integer.parseInt(token));
                } catch (NumberFormatException e) {
                    // Unbekannte Tokens werden ignoriert
                }
            }
        }

        return stack.toString();
    }

    public static void main(String[] args) {
        String program = String.join(" ", args);
        System.out.println(run(program));
    }
}