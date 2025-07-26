def get_input():
    n = int(input("Enter the number of elements in the array: "))
    arr = []
    for i in range(n):
        element = int(input(f"Enter element {i+1}: "))
        arr.append(element)
    return arr

def selection_sort(arr):
    """Selection Sort Algorithm"""
    for i in range(len(arr)):
        min_idx = i
        for j in range(i + 1, len(arr)):
            if arr[j] < arr[min_idx]:
                min_idx = j

        arr[i], arr[min_idx] = arr[min_idx], arr[i]


arr = get_input()

print("Original array:", arr)
selection_sort(arr)
print("Sorted array:", arr)
