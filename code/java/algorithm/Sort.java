import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

class Sort {

    public static void main(String[] args) {

        int[] oidArray = randomArray(100);
        System.out.println(Arrays.toString(oidArray));

        int[] sortArr = Arrays.copyOf(oidArray,oidArray.length);
        bubbleSort(sortArr);
        System.out.println(Arrays.toString(sortArr));

        sortArr = Arrays.copyOf(oidArray, oidArray.length);
        selectionSort(oidArray);
        System.out.println(Arrays.toString(oidArray));

        sortArr = Arrays.copyOf(oidArray, oidArray.length);
        quickSort(oidArray, 0, oidArray.length - 1);
        System.out.println(Arrays.toString(oidArray));
    }

    public static int[] randomArray(int len) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * len);
        }
        return arr;
    }

    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr.length - i - 1; j++)
                if (arr[j + 1] < arr[j]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
    }

    public static void selectionSort(int[] arr) {
        int min, temp;
        for (int i = 0; i < arr.length; i++) {
            // 初始化未排序序列中最小数据数组下标
            min = i;
            for (int j = i + 1; j < arr.length; j++) {
                // 在未排序元素中继续寻找最小元素，并保存其下标
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            // 将未排序列中最小元素放到已排序列末尾
            if (min != i) {
                temp = arr[min];
                arr[min] = arr[i];
                arr[i] = temp;
            }
        }
    }

    public static void quickSort(int[] arr, int head, int tail) {
        if (head >= tail || arr == null || arr.length <= 1) {
            return;
        }
        int i = head, j = tail, pivot = arr[(head + tail) / 2];
        while (i <= j) {
            while (arr[i] < pivot) {
                ++i;
            }
            while (arr[j] > pivot) {
                --j;
            }
            if (i < j) {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
                ++i;
                --j;
            } else if (i == j) {
                ++i;
            }
        }
        quickSort(arr, head, j);
        quickSort(arr, i, tail);
    }

}