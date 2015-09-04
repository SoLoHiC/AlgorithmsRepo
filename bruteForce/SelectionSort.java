public class SelectionSort {
    public static void main(String[] args) {
        int[] arr = {1, 3, 7, 5, 9, 3, 8, 2, 10, 53, 26, 46, 23};

        for (int i = 0; i < arr.length; i++ )
            System.out.print(arr[i]+",");

        System.out.println();

        selectionSort(arr);

        for (int i = 0; i < arr.length; i++ )
            System.out.print(arr[i]+",");

    }

    public static void selectionSort(int[] array) {
        for (int i = 0;i < array.length-1; i++) {
            int min = i;
            for(int j = i; j < array.length; j++) {
                if (array[j] < array[i])
                    min = j;
            }
            int tmp = array[min];
            array[min] = array[i];
            array[i] = tmp;
        }
    }
}
