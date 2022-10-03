
/*
 * Zadanie 2
 * Złożoność obliczeniowa O(log n) - wyszukkiwanie binarne
 * Złożoność pamięciowa O(n) - liczba elementów tablicy
 */
public class Task2 {

    private boolean search(int[] numbers, int x){
        int begin = 0;
        int end = numbers.length - 1;
        while (begin < end) {
            int middle = (begin+end)/2;
            if (numbers[middle] == x) {
                return true;
            } else if (numbers[middle] < x) {
                end = middle;
            } else {
                begin = middle + 1;
            }
        }
        if (numbers[begin] == x) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] tab = {32, 30, 29, 21, 17, 8, 4, 3, 1, 0, -5, -133};
        Task2 task2 = new Task2();
        System.out.println(task2.search(tab, 7));
    }
}