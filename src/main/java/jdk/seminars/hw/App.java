package jdk.seminars.hw;

import java.util.*;
import jdk.seminars.hw.common.Map2JSONFile;

/*
 * проверка парадокса Монти-Холла на примере трех шкатулок в одной из которыйх миллион
 *
 * 1й вариант: игрок случайно выбирает одну из шкатулок и не меняет свое решение,
 * и всегда остается при своём первом выборе
 *
 * 2й вариант: игрок случайно выбирает одну из дверей, далее ведущий убирает одну пустую из оставшихся
 * и перелагает сменить шкатулку на что пользователь соглашается
 *
 * в каждом случае подсчитаем число выигрышей во множестве итераций и переведем в процентное отношение
 *
 *
 *
 * для хот какого-то соответствия задания теме лекции я добавил
 * сохранение результатов шагов проверки (Map(step,result)) в JSON файл "result.json"
 *
 * */
public class App {
    public static void main(String[] args) {
        Random rnd = new Random();
        Map<Integer, Boolean> result = new HashMap<>(); //мапа с результами каждой итерации (непонятно зачем)))
        int maxSteps = 1000; // число итераций
        String file2result = "result.json";

        // реализация первого варианта
        for (int i = 0; i < maxSteps; i++) {
            boolean[] boxes = getBoxes(); //перемешиваем шкатулки
            int selectedBox = rnd.nextInt(3); // игрок выбирает одну из шкатулок
            result.put(i, boxes[selectedBox]);//сохраняем результат
        }

        printResult(maxSteps, result, 1); // вывод результатов первого варианта
        System.out.println();

        // реализация второго врианта
        for (int i = 0; i < maxSteps; i++) {
            boolean[] boxes = getBoxes();// перемешиваем шкатулки
            int selectedBox = rnd.nextInt(3);//игрок выбирает одну из шкатулок
            selectedBox = getSecondBox(selectedBox, boxes); //игрок меняет выбранную шкатулку на ту, что предложит ведущий
            result.put(i + maxSteps, boxes[selectedBox]);//сохраняем результат
        }

        printResult(maxSteps, result, 2);// вывод результатов второго варианта

        Map2JSONFile.toJsonFile(result,file2result); //записываем результаты шагов из Map в JSON файл
    }

    /** Просто печатаем результаты
     * @param maxSteps
     * @param result
     * @param step
     */
    private static void printResult(int maxSteps, Map<Integer, Boolean> result, int step) {
        int win = 0;
        int fail = 0;
        for (int i = maxSteps*(step-1); i < maxSteps*(step); i++) {
            if (result.get(i)) {
                win++;
            } else {
                fail++;
            }
        }
        System.out.printf("Варинт %d\n",step);
        System.out.printf("Выигрышей:\t%d\nПроигрышей:\t%d\n", win, fail);
        System.out.printf("при %d итераций: %2.2f%%\n",maxSteps,((float)win/(win+fail)*100));
    }

    /**
     * Возвращает индекс шкатулки, которую предлагает открыть ведущий
     *
     * @param userSelectedBox
     * @param boxes
     * @return
     */
    private static int getSecondBox(int userSelectedBox, boolean[] boxes) {
        Random rnd = new Random();
        int selectSecondBox;
        if (boxes[userSelectedBox]) {
            // начальный выбор игрока был правильный
            while (true) {
                //предлагаем любую из оставшихся шкатулок
                selectSecondBox = rnd.nextInt(3);
                if (selectSecondBox != userSelectedBox) return selectSecondBox;
            }
        } else {
            //если начальный выбор был проигрышный,
            // то предлагаем шкатулку с призом
            while (true) {
                for (int i = 0; i < 3; i++) {
                    if (boxes[i]) return i;
                }
            }
        }
    }

    /**
     * Возвращает массив шкатулок, в одной из которого приз
     *
     * @return
     */
    private static boolean[] getBoxes() {

        Random rnd = new Random();
        boolean[] boxes = new boolean[]{false, false, false};
        boxes[rnd.nextInt(3)] = true;
        return boxes;
    }
}