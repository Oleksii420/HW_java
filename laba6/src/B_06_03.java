public class B_06_03 {
    public static void main(String[] args) {
        String expr1 = "+2 - 57*33 + 25/ - 4";
        String expr2 = "-10 + 5 * -3 / +2";
        String expr3 = "2 ++ 3";

        System.out.println(checkExpression(expr1));
        System.out.println(checkExpression(expr2));
        System.out.println(checkExpression(expr3));
    }

    public static boolean checkExpression(String text) {
        text = text.replaceAll("[\\u2212\\p{Pd}]", "-");
        String expr = text.trim().replaceAll("\\s+", "");

        if (!expr.matches("[0-9+\\-*/]+")) return false;
        if (expr.matches("^[*/].*") || expr.matches(".*[+\\-*/]$")) return false;

        char[] arr = expr.toCharArray();
        for (int i = 1; i < arr.length; i++) {
            if (isOp(arr[i]) && isOp(arr[i - 1])) {
                if (!((arr[i] == '+' || arr[i] == '-') && (arr[i - 1] == '*' || arr[i - 1] == '/'))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isOp(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
