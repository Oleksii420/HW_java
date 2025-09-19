public class B_01_03 {
    public static void main(String[] args) {
        int product = 10;
        boolean hasInteger = false;

        for (String arg : args) {
            try {
                int value = Integer.parseInt(arg);
                product *= value;
                hasInteger = true;
            } catch (NumberFormatException e) {
                System.out.println("\"" + arg + "\" не є цілим числом");
            }
        }

        if (hasInteger) {
            System.out.println("Добуток = " + product);
        } else {
            System.out.println("Жодного цілого аргументу не передано");
        }
    }
}
