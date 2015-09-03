public class SequentialSearch2 {
    public static void main(String[] args) {
        int[] arr = {3,5,7,9,2,7,5,23,456,234,7,6,2367,234,5685};
        int target = 456;
        System.out.print("arr:");
        for(int i = 0;i < arr.length;i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println();
        System.out.println("target:" + target);

        int result = sequentialSearch2(arr, target);
        System.out.println("result:" + result);
    }

    public static int sequentialSearch2(int[] array, int target) {
        if(array[array.length-1] != target)
            array[array.length-1] = target;
        else
            return array.length-1;

        int i = 0;
        while(array[i] != target)
            i++;

        if (i == array.length-1)
            return -1;
        else
            return i;
    }
}
