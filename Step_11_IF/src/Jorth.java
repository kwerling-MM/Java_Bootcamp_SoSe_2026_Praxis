public class Jorth {

    public String run(String input) {
        if (input == null || input.isBlank()) {
            return "[]";
        }

        java.util.List<Object> stack = new java.util.ArrayList<>();
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(input);

        boolean active = true;       // Steuert, ob der aktuelle Token ausgeführt wird
        boolean conditionMet = true; // Speichert das Ergebnis der if-Bedingung

        while (matcher.find()) {
            String stringContent = matcher.group(1);
            String token = matcher.group(2);

            // 1. Kontrollstrukturen (werden immer verarbeitet, um den State zu steuern)
            if (token != null) {
                if (token.equals("if")) {
                    Object condition = !stack.isEmpty() ? stack.remove(stack.size() - 1) : 0;
                    conditionMet = condition instanceof Integer && (Integer) condition != 0;
                    active = conditionMet;
                    continue;
                } else if (token.equals("else")) {
                    active = !conditionMet;
                    continue;
                } else if (token.equals("endif")) {
                    active = true;
                    continue;
                }
            }

            // 2. Normale Token-Verarbeitung (nur wenn active == true)
            if (active) {
                if (stringContent != null) {
                    stack.add(stringContent);
                } else {
                    if (token.equals("+")) {
                        if (stack.size() >= 2) {
                            Object second = stack.remove(stack.size() - 1);
                            Object first = stack.remove(stack.size() - 1);
                            if (first instanceof Integer && second instanceof Integer) {
                                stack.add((Integer) first + (Integer) second);
                            } else if (first instanceof String && second instanceof String) {
                                stack.add((String) first + (String) second);
                            } else {
                                stack.add(first); stack.add(second);
                            }
                        }
                    } else if (token.equals("-")) {
                        if (stack.size() >= 2) {
                            Object second = stack.remove(stack.size() - 1);
                            Object first = stack.remove(stack.size() - 1);
                            if (first instanceof Integer && second instanceof Integer) {
                                stack.add((Integer) first - (Integer) second);
                            } else {
                                stack.add(first); stack.add(second);
                            }
                        }
                    } else if (token.equals("<") || token.equals(">") || token.equals("==") || token.equals("!=")) {
                        if (stack.size() >= 2) {
                            Object second = stack.remove(stack.size() - 1);
                            Object first = stack.remove(stack.size() - 1);
                            if (first instanceof Integer && second instanceof Integer) {
                                int a = (Integer) first; int b = (Integer) second;
                                boolean res = false;
                                switch (token) {
                                    case "<": res = a < b; break;
                                    case ">": res = a > b; break;
                                    case "==": res = a == b; break;
                                    case "!=": res = a != b; break;
                                }
                                stack.add(res ? 1 : 0);
                            } else {
                                stack.add(first); stack.add(second);
                            }
                        }
                    } else if (token.equals(".")) {
                        if (!stack.isEmpty()) System.out.println(stack.get(stack.size() - 1));
                    } else if (token.equals("._")) {
                        if (!stack.isEmpty()) System.out.print(stack.get(stack.size() - 1));
                    } else if (token.equals("drop")) {
                        if (!stack.isEmpty()) stack.remove(stack.size() - 1);
                    } else if (token.equals("dup")) {
                        if (!stack.isEmpty()) stack.add(stack.get(stack.size() - 1));
                    } else {
                        try {
                            stack.add(Integer.parseInt(token));
                        } catch (NumberFormatException e) {
                            // Ignorieren
                        }
                    }
                }
            }
        }
        return stack.toString();
    }

    public static void main(String[] args) {
        Jorth jorth = new Jorth();
        System.out.println(jorth.run(String.join(" ", args)));
    }
}