public class Jorth {

    public static String run(String input) {
        if (input == null || input.isBlank()) {
            return "[]";
        }

        java.util.List<Integer> stack = new java.util.ArrayList<>();
        String[] tokens = input.trim().split("\\s+");

        for (String token : tokens) {
            try {
                stack.add(Integer.parseInt(token));
            } catch (NumberFormatException e) {
                // Ignoriere Tokens, die keine Ganzzahlen sind
            }
        }

        return stack.toString();
    }

    public static void main(String[] args) {
        String program = String.join(" ", args);
        System.out.println(run(program));
    }
}