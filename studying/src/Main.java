import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static Map<String, Integer> romanNumbers = new HashMap<String, Integer>();
    static boolean isRoman;
    public static void main(String[] args) throws Exception {
        romanNumbers.put("I", 1);
        romanNumbers.put("II", 2);
        romanNumbers.put("III", 3);
        romanNumbers.put("IV", 4);
        romanNumbers.put("V", 5);
        romanNumbers.put("VI", 6);
        romanNumbers.put("VII", 7);
        romanNumbers.put("VIII", 8);
        romanNumbers.put("IX", 9);
        romanNumbers.put("X", 10);

        Scanner s = new Scanner(System.in); // новый объект Scanner для ввода данных с консоли
        String expr; // переменная для хранения введенного пользователем выражения
        System.out.println("Введите выражение для подсчета!"); // сообщение пользователю
        expr = s.nextLine(); // считывает выражение из консоли
        System.out.println(calc(expr)); // выводит возвращаемый из метода результат операции

        s.close();
    }

    public static String calc(String input) throws Exception {
        int result = 0; // переменная для хранения результата операции
        String[] splitInput = input.split(" "); // массив для хранения операндов и операции отдельно друг от друга

        if (splitInput.length != 3) {
            throw new Exception("Формат математической операции не удовлетворяет заданию!");
        }

        String firstInputNum = splitInput[0];
        String secondInputNum = splitInput[2];

        int firstNum = 0; // первое слагаемое
        int secondNum = 0; // второе слагаемое

        // если обе цифры - римские
        if (romanNumbers.containsKey(firstInputNum) && romanNumbers.containsKey(secondInputNum)) {
            firstNum = romanNumbers.get(firstInputNum);
            secondNum = romanNumbers.get(secondInputNum);

            isRoman = true;
        }
        // если обе цифры не римские :)
        else if (!romanNumbers.containsKey(firstInputNum) && !romanNumbers.containsKey(secondInputNum)) {
            Matcher matcher = Pattern.compile("(\\D+)").matcher(firstInputNum+secondInputNum);
            // если в одном из чисел встречается либо запятая, либо точка - исключение
            if (matcher.find())
                throw new Exception("Неверно введено число!");

            firstNum = Integer.parseInt(firstInputNum);
            secondNum = Integer.parseInt(secondInputNum);

            if (firstNum < 1 || firstNum > 10 || secondNum < 1 || secondNum > 10)
                throw new Exception("Числа должны быть в промежутке от 1 до 10!");

            isRoman = false;
        }
        else {
            throw new Exception("Нельзя использовать сразу две системы счисления!");
        }

        // вычисления
        switch (splitInput[1]) {
            case "+" -> result = firstNum + secondNum;
            case "-" -> result = firstNum - secondNum;
            case "*" -> result = firstNum * secondNum;
            case "/" -> result = firstNum / secondNum;
            default -> throw new Exception("Такой операции не существует!");
        }

        // если числа римские, то перевести результат в римскую систему счисления
        if (isRoman)
        {
            if (result < 0)
                throw new Exception("В римской системе счисления нет отрицательных чисел!");
            return toRoman(result);
        }

        return Integer.toString(result);
    }

    // метод переводит результат в римскую систему счисления
    public static String toRoman(int number) {
        String resultNum = "";
        if (number / 100 == 1) {
            resultNum += "C";
        } else if (number == 0) {
            resultNum += "N";
        }
        else {
            int lCount = 0;
            int xCount = 0;
            int lastDigit = 0;

            if (number > 50) {
                lCount = number / 50;
                number -= 50;
            }
            if (number > 10) {
                xCount = number / 10;
                number -= xCount * 10;
            }

            if (xCount == 4 && lCount == 0)
                resultNum += "XL";
            else if (xCount == 4 && lCount == 1)
                resultNum += "XC";
            else
            {
                resultNum += "L".repeat(lCount);
                resultNum += "X".repeat(xCount);
            }

            for (String key : romanNumbers.keySet()) {
                if (number == romanNumbers.get(key)) {
                    resultNum += key;
                }
            }
        }

        return resultNum;
    }
}