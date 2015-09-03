public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {3,6,4,7,9,2,5,23,46,34,867,234,867,2342,679679,574};

        System.out.print("before bubble sort:");
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+",");
        }
        System.out.println();

        bubbleSort(arr);

        System.out.print("after bubble sort:");
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+",");
        }
    }

    public static void bubbleSort(int[] array) {
        for (int i = 0;i < array.length;i++) {
            for (int j = 0; j < array.length-1;j++) {
                if (array[j] > array[j+1]) {
                    int tmp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = tmp;
                }
            }
        }
    }
}
