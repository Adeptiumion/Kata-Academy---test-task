import java.util.*;


/*
    Здравствуй, дорогой проверяющий:
    Программа не учитывает разное количество пробелов между числами или их отсутствие!
    Да можно было по-другому, да можно было короче, но после 13 часов смены в К&Б желание только на коленке делать...
    Я очень хочу выбраться из этого ада, поэтому надеюсь на понимание)
 */

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println(calc(scan.nextLine()));
    }

    public static String calc(String input) {

        // Мапа для перевода арабских цифр в римские c запоминанием порядка
        Map<String, String> romanTranslator = new LinkedHashMap<String, String>();
        romanTranslator.put("100", "C");
        romanTranslator.put("90", "XC");
        romanTranslator.put("50", "L");
        romanTranslator.put("40", "XL");
        romanTranslator.put("10", "X");
        romanTranslator.put("9", "IX");
        romanTranslator.put("8", "VIII");
        romanTranslator.put("7", "VII");
        romanTranslator.put("6", "VI");
        romanTranslator.put("5", "V");
        romanTranslator.put("4", "IV");
        romanTranslator.put("3", "III");
        romanTranslator.put("2", "II");
        romanTranslator.put("1", "I");

        boolean isRomanExpression = false;
        boolean aIsDigit = true;
        boolean bIsDigit = true;
        boolean isNormalOperation = true;
        int a;
        int b;
        String operation;

        String[] partsBuffer = input.split(" ");

        // Если чисел Больше или меньше 2 - выплёвываю исключение
        if (partsBuffer.length != 3)
            throw new IllegalArgumentException();

        isRomanExpression = isRoman(input);

        if (isRomanExpression) {
            for (Map.Entry<String, String> m : romanTranslator.entrySet()) {
                if (Objects.equals(m.getValue(), partsBuffer[0])) {
                    partsBuffer[0] = m.getKey();
                }
                if (Objects.equals(m.getValue(), partsBuffer[2])) {
                    partsBuffer[2] = m.getKey();
                }
            }
        }

        // Если операция не: +, -, *, / - выплёвываю исключение
        if (!partsBuffer[1].matches("(\\+|\\-|\\*|\\/)"))
            isNormalOperation = false;

        // Проверяю, является ли аргумент слева числом
        for (char ch : partsBuffer[0].toCharArray()) {
            if (!Character.isDigit(ch))
                aIsDigit = false;
        }

        // Проверяю, является ли аргумент справа числом
        for (char ch : partsBuffer[2].toCharArray()) {
            if (!Character.isDigit(ch))
                bIsDigit = false;
        }

        // Если всё норм - кидаю числа и операцию в переменные
        if (aIsDigit && bIsDigit && isNormalOperation) {
            a = Integer.parseInt(partsBuffer[0]);
            b = Integer.parseInt(partsBuffer[2]);
            operation = partsBuffer[1];
        } else {
            throw new IllegalArgumentException(); // Если что-то не так - выплёвываю исключение
        }


        switch (operation) {
            case "+":
                if (!isRomanExpression)
                    return String.valueOf(a + b);
                else
                    return convertArabianToRoman(String.valueOf(a + b), romanTranslator);
            case "-":
                if (a - b < 0)
                    throw new ArithmeticException();
                if (!isRomanExpression)
                    return String.valueOf(a - b);
                else
                    return convertArabianToRoman(String.valueOf(a - b), romanTranslator);
            case "*":
                if (!isRomanExpression)
                    return String.valueOf(a * b);
                else
                    return convertArabianToRoman(String.valueOf(a * b), romanTranslator);
            case "/":
                if (!isRomanExpression)
                    return String.valueOf(a / b);
                else
                    return convertArabianToRoman(String.valueOf(a / b), romanTranslator);
            default:
                throw new IllegalArgumentException();
        }


    }

    public static boolean isRoman(String input) {
        return input.matches("(I|II|III|IV|V|VI|VII|VIII|IX|X) [\\+\\-\\*\\/] (I|II|III|IV|V|VI|VII|VIII|IX|X)");
    }

    // Переводит арабское число в римское
    public static String convertArabianToRoman(String arabian, Map<String, String> romanTranslator) {
        StringBuilder answer = new StringBuilder();
        int value = Integer.parseInt(arabian);


        while (value != 0) {

            for (Map.Entry<String, String> m : romanTranslator.entrySet()) {
                if (value - Integer.parseInt(m.getKey()) >= 0) {
                    answer.append(m.getValue());
                    value -= Integer.parseInt(m.getKey());
                }
            }
        }
        return answer.toString();
    }


}

