public class Jorth {

    public static String run(String input) {
        if (input == null || input.isBlank()) {
            return "[]";
        }

        java.util.List<Object> stack = new java.util.ArrayList<>();
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(input);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                stack.add(matcher.group(1));
            } else {
                String token = matcher.group(2);
                if (token.equals("+")) {
                    if (stack.size() >= 2) {
                        Object second = stack.remove(stack.size() - 1);
                        Object first = stack.remove(stack.size() - 1);
                        
                        if (first instanceof Integer && second instanceof Integer) {
                            stack.add((Integer) first + (Integer) second);
                        } else if (first instanceof String && second instanceof String) {
                            stack.add((String) first + (String) second);
                        } else {
                            // Mischformen oder unbekannte Typen: zurück auf den Stack
                            stack.add(first);
                            stack.add(second);
                        }
                    }
                } else if (token.equals("-")) {
                    if (stack.size() >= 2) {
                        Object second = stack.remove(stack.size() - 1);
                        Object first = stack.remove(stack.size() - 1);
                        if (first instanceof Integer && second instanceof Integer) {
                            stack.add((Integer) first - (Integer) second);
                        } else {
                            stack.add(first);
                            stack.add(second);
                        }
                    }
                } else if (token.equals(".")) {
                    if (!stack.isEmpty()) {
                        System.out.println(stack.get(stack.size() - 1));
                    }
                } else {
                    try {
                        stack.add(Integer.parseInt(token));
                    } catch (NumberFormatException e) {
                        // Unbekannte Tokens ignorieren
                    }
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