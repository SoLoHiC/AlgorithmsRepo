def selection_sort(array):
    for i in range(array):
        min = i
        for j in range(array[i:]):
            if array[j]<array[i]:
                min = j
        tmp = array[j]
        array[j] = array[i]
        array[i] = tmp
    return array

if __name__ == "__main__":
    import random
    array = range(100)
    random.shuffle(array)
    print 'before sortation:', array
    array = selection_sort(array)
    print 'after sortation:', array
