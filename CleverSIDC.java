/**
 * @author Vasu Champaneria and Yash Nathani
 * Comp 352
 */

public class CleverSIDC {
    private class Node {
        private long key;
        private HelperStudent value;
        private Node next;
        private Node prev;
        /**
         * @param key, initializes the key attribute of the Node object with the value passed to the constructor.
         * @param value, initializes the value attribute of the Node object with the Student object passed to the constructor.
         */
        public Node(long key, HelperStudent value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
        /**
         *
         * @param key
         * @param value
         * @param prev
         * @param next
         */
        public Node(long key , HelperStudent value , Node prev , Node next) {
            this(key , value);
            this.next = next;
            this.prev = prev;
        }
    }
    /**
     * It will be a linkedlist implementation, then the variable would be set to true. Double Linked List
     * It will be an array using hashTable if false
     * declaring threshold size and max capacity of array
     */
    private boolean isLinkedList = false;
    private long ThresholdSize;
    private Node[] hashTable;
    private long max_capacity;
    private Node Head = null, Tail = null;

    /**
     * Initializing size of the hash table
     */
    public CleverSIDC() {
        this.max_capacity = 200;
        this.hashTable = new Node[(int) max_capacity];
    }
    /**
     * Method CleverSIDC
     * @param size
     * if the size grows more than 5000 then a linklist set to true and implemented
     * if the size is less than 5000 than an array using hashTable is implemented.
     */
    public CleverSIDC(long size) {
        
        if(size > 5000){
            isLinkedList = true;
        }
        else{
            hashTable = new Node[(int) size];
            this.ThresholdSize = 0;
            max_capacity = size * 2;
        }
    }
    /**
     * Sets the threshold size for the CleverSIDC data structure.
     * Depending on the specified size, the method adjusts the internal
     * representation of the data structure, either using a linked list or
     * resizing the hash table.
     *
     * @param size The new threshold size to set for the CleverSIDC data structure.
     * Also doubling the size of the array and max capacity
     */
    public void setSIDCThreshold(int size) {        // O(N), where N represents the number of elements in the largest data structure 
        if (size > 5000 && !isLinkedList) {
            Head = new Node(hashTable[0].key , hashTable[0].value);
            Node recent = Head;
            for (int i = 1; i < ThresholdSize; i++) {
                recent.next = new Node(hashTable[i].key, hashTable[i].value);
                recent.next.prev = recent;
                recent = recent.next;
            }
            Tail = recent;
            ThresholdSize = size;
            hashTable = null;
            isLinkedList = true;
        }
        else if (size >= this.max_capacity){
            // make a new array
            Node[] temp = new Node[(int)max_capacity * 2];
            // copying from the old array.
            for (int i = 0; i < ThresholdSize; i++) {
                temp[i] = hashTable[i];
            }
            hashTable = temp;
            // doubling the size of the array and max capacity
            max_capacity *= 2;
        }
        else if(size < ThresholdSize){
            Node[] temp = new Node[(int) size*2];
            // copy everything from the old array.
            for (int i = 0; i < size ; i++) {
                temp[i] = hashTable[i];
            }
            hashTable = temp;
            // increase the size and max capacity.
            max_capacity = size * 2;
        }
        else{
            //do nothing because if the size is greater than the size of the TrashHold size but smaller max capacity.
        }
    }
    /**
     * Generates a random 8-digit long key that is not already present in the data structure.
     * The method utilizes the existing keys to ensure uniqueness.
     *
     * @return A randomly generated 8-digit long key that is not already present.
     * Time complexity = O(n)
     */
    public long generate() {
        long[] temp = allKeys();
        long generatedKey;
        do {
            generatedKey = (long) (Math.random() * 90000000) + 10000000; // Generates a random 8-digit number
        } while (binarySearch(temp, generatedKey) >= 0);
    
        return generatedKey;
    }
    /**
     * Retrieves all keys from the CleverSIDC data structure in sorted order.
     * @return An array containing all keys in sorted order.
     * If linked list mode is enabled, it iterates through the linked list to find all keys and returns them in sorted order.
     * If hash table mode is enabled, it finds the range of keys, counts occurrences, and reconstructs the sorted keys array.
     * The method returns a sorted array containing all keys in the data structure.
     * Complexity = O(n)
     */
    public long[] allKeys(){   //SPce complexity :O(R + T), where R is the range of the keys and T is the ThresholdSize
        if (ThresholdSize <= 1) {
            // No need for sorting with 1 or fewer elements
            long [] temp = new long[1];
            if(isLinkedList){
                temp[0] = Head.key;
            }
            else{
                temp[0] = hashTable[0].key;
            }
            return temp;
        }
        else {
            long maxKey = Long.MIN_VALUE;
            long minKey = Long.MAX_VALUE;
            // Find max and min keys to determine the range for Buckets
            if(isLinkedList){ 
                Node recentNode = Head;
                while(recentNode != null){
                    maxKey = Math.max(maxKey, recentNode.key);
                    minKey = Math.min(minKey, recentNode.key);
                    recentNode = recentNode.next;
                }
            }
            else {
                for (int i = 0; i < ThresholdSize; i++) {
                    if (hashTable[i] != null) {
                        maxKey = Math.max(maxKey, hashTable[i].key);
                        minKey = Math.min(minKey, hashTable[i].key);
                    }
                }
            }
            int range = (int) (maxKey - minKey + 1); // Range of keys (casting to int)
            int[] Buckets = new int[range];
            // Count occurrences of keys
            if(isLinkedList){
                Node recentNode = Head;
                while(recentNode != null){
                    Buckets[(int) (recentNode.key - minKey)]++;
                    recentNode = recentNode.next;
                }
            }
            else{
                for (int i = 0; i < ThresholdSize; i++) {
                    if (hashTable[i] != null) {
                        Buckets[(int) (hashTable[i].key - minKey)]++;
                    }
                }
            }
            // Reconstruct sorted keys
            int ReSize = 0;
            for (int bucket : Buckets) {
                ReSize += bucket;
            }
            long[] sortedKeys = new long[ReSize];
            int index = 0;
            for (int i = 0; i < range; i++) {
                for (int j = 0; j < Buckets[i]; j++) {
                    sortedKeys[index++] = i + minKey;
                }
            }
            return sortedKeys;
        }
    }
    /**
     * Adds an entry to the CleverSIDC data structure with the specified key and value.
     * @param key   The key of the entry to be added.
     * @param value The value associated with the key.
     * If linked list mode is enabled, the entry is added to the linked list.
     * If the size exceeds a threshold or the maximum capacity, the data structure transitions to linked list mode.
     * If the size is at the maximum capacity, the hash table is resized and the entry is added.
     * Otherwise, the entry is directly added to the hash table.
     * Worst Complexity - O(n) , average complexity - 0(1) for array
     * worst complexity - 0(1) for linkedList
     */
    public void add(long key, HelperStudent value) {    //space complexity of O(N), where N represents the size of the hashTable
        // Add an entry for the given key and value
        if (this.isLinkedList) {
            if (Head == null) {
                Head = new Node(key, value);
                Tail = Head;
            } 
            else {
                Node newNode = new Node(key, value, Tail, null);
                Tail.next = newNode;
                Tail = newNode;
            }
            this.ThresholdSize += 1;
        }
        else if (ThresholdSize + 1 >= 5000 || max_capacity * 2 >= 10000) {
            // swift to linked list
            this.isLinkedList = true;
            this.Head = hashTable[0];
            Node tempNode = Head;
        
            for (int i = 1; i < ThresholdSize; i++) {
                if (hashTable[i] != null) {
                    Node newNode = new Node(hashTable[i].key, hashTable[i].value);
                    tempNode.next = newNode;
                    newNode.prev = tempNode;
                    tempNode = tempNode.next;
                }
            }
            hashTable = null;
            Tail = tempNode;
            ThresholdSize++;
        }
        else if(this.ThresholdSize + 1 == this.max_capacity){
            // make a new array
            Node[] temp = new Node[(int)max_capacity * 2];
            // copying from the old array.
            for (int i = 0; i < ThresholdSize; i++) {
                temp[i] = hashTable[i];
            }
            hashTable = null;
            hashTable = temp;
            // increase the size and max capacity.
            max_capacity *= 2;
            //add the new value:
            hashTable[ (int) ThresholdSize] = new Node(key , value);
            ThresholdSize++;
        }
        else{
            hashTable[(int) ThresholdSize] = new Node(key , value);
            ThresholdSize++;
        }
    }
    /**
     * Removes an entry from the CleverSIDC data structure with the specified key.
     * @param key The key of the entry to be removed.
     * If linked list mode is enabled, the entry is removed from the linked list.
     * If the removed entry is the head or tail, the respective pointers are updated accordingly.
     * If the size drops below a threshold, the data structure transitions out of linked list mode.
     * If hash table mode is enabled, the entry is removed from the hash table, and the table is rearranged accordingly.
     * Worst complexity - 0(n)
     */
    public void remove(long key) {  // O(1), or constant space complexity.
        if(isLinkedList){
            if(Head.key == key){
                Head = Head.next;
                if (Head != null) {
                    Head.prev = null;
                }
                else {
                    Tail = null; // No node found
                }
                return;
            }
            else if(Tail.key == key){
                Tail = Tail.prev;
                if (Tail != null) {
                    Tail.next = null;
                }
                else {
                    Head = null; //no node found, empty
                }
                return;
            }
            Node tempNode = Head.next;
            while(tempNode != Tail){
                if(tempNode.key == key){
                    tempNode.prev.next = tempNode.next;
                    tempNode.next.prev = tempNode.prev;
                    tempNode.next = null;
                    tempNode.prev = null;
                    return; // key found and removed.
                }
                tempNode = tempNode.next;
            }
        }
        else {
            for (int i = 0; i < ThresholdSize; i++) {
                if (hashTable[i] != null && hashTable[i].key == key) {
                    while (i < ThresholdSize - 1) {
                        hashTable[i] = hashTable[i + 1];
                        i++;
                    }
                    ThresholdSize--;
                    hashTable[(int) ThresholdSize] = null;
                }
            }
        }
    }
    /**
     * Retrieves the value associated with the specified key from the CleverSIDC data structure.
     * @param key The key for which the associated value is to be retrieved.
     * @return The value associated with the specified key if found; otherwise, returns null.
     * If linked list mode is enabled, the key is searched in the linked list.
     * If the key is found, the corresponding value is returned; otherwise, null is returned.
     * If hash table mode is enabled, the key is searched in the array-based structure.
     * If the key is found, the corresponding value is returned; otherwise, null is returned.
     * Complexity - O(n) both best and worse as transverse in both methods below
     */
    public HelperStudent getValue(long key) {   //The space complexity of the getValue method is O(1)

        if (isLinkedList) {
            Node recent = Head;
            while (recent != null) {
                if (recent.key == key) {
                    return recent.value; // Return if the key is found in the linked list
                }
                recent = recent.next;
            }
            return null; // Key not found in the linked list
        }
        else {
            for (int i = 0; i < ThresholdSize; i++) {
                if (hashTable[i] != null && hashTable[i].key == key) {
                    return hashTable[i].value; // Return the value if the key is found in the array-based structure
                }
            }
            return null; // Key not found in the array ADT
        }
    }
    /**
     * Retrieves the next key in sorted order following the specified key from the CleverSIDC data structure.
     * @param key The key for which the next key is to be retrieved.
     * @return The next key in sorted order if found; otherwise, returns -1.
     * If linked list mode is enabled, the key is searched in the linked list, and the next key is returned if it exists.
     * If the key is not found or there is no next key, -1 is returned.
     * If hash table mode is enabled, the key is searched in the array-based structure, and the next key is returned if it exists.
     * If the key is not found or there is no next key, -1 is returned.
     * Complexity - O(n) for linked list, O(1) for array-based structure.
     */
    public long nextKey(long key) {
        if (isLinkedList) {
            Node recent = Head;
            while (recent != null && recent.key != key) {
                recent = recent.next;
            }
            if (recent != null && recent.next != null) {
                return recent.next.key;
            }
            return -1;
        }
        else {
            for (int i = 0; i < this.ThresholdSize; i++) {
                if (hashTable[i] != null && hashTable[i].key == key && i < this.ThresholdSize - 1) {
                    return hashTable[i + 1].key;
                }
            }
            return -1; // If key not found or next key doesn't exist
        }
    }
    /**
     * Retrieves the previous key in sorted order preceding the specified key from the CleverSIDC data structure.*
     * @param key The key for which the previous key is to be retrieved.
     * @return The previous key in sorted order if found; otherwise, returns -1.
     * If linked list mode is enabled, the key is searched in the linked list, and the previous key is returned if it exists.
     * If the key is not found or there is no previous key, -1 is returned.
     * If hash table mode is enabled, the key is searched in the array-based structure, and the previous key is returned if it exists.
     * If the key is not found or there is no previous key, -1 is returned.
     * Complexity - O(n) for linked list, O(1) for array-based structure.
     */
    public long prevKey(long key) {
        if (isLinkedList) {
            Node recent = Head;
            while (recent != null && recent.key != key) {
                recent = recent.next;
            }
            if (recent != null && recent.prev != null) {
                return recent.prev.key;
            }
            return -1;
        }
        else {
            for (int i = 1; i < this.ThresholdSize; i++) {
                if (hashTable[i] != null && hashTable[i].key == key) {
                    return hashTable[i - 1].key;
                }
            }
            return -1; // If key not found or previous key doesn't exist
        }
    }
    /**
     * Retrieves all keys within the specified range from the CleverSIDC data structure.
     *@param key1 The starting key of the range (inclusive).
     * @param key2 The ending key of the range (inclusive).
     * @return An array containing all keys within the specified range in sorted order.
     *
     * If linked list mode is enabled, it iterates through the linked list to count and store keys within the specified range.
     * If hash table mode is enabled, it counts and stores keys within the specified range in the array-based structure.
     * The method returns a sorted array containing all keys within the specified range in the data structure.
     * Complexity - O(n) both best and worst case, as it traverses the structure to find keys within the range.
     */
    public long[] rangeKey(long key1, long key2) {   // method is O(C), where C is the count of keys within the specified range (key1 to key2)
        if (isLinkedList) {
            int count = 0;
            Node recent = Head;
            // Count keys within the specified range
            while (recent != null) {
                if (recent.key >= key1 && recent.key <= key2) {
                    count++;
                }
                recent = recent.next;
            }
            long[] keysInRange = new long[count];    // Create an array for keys within the range
            int index = 0;
            recent = Head;
            // Store keys within the range in the array
            while (recent != null) {
                if (recent.key >= key1 && recent.key <= key2) {
                    keysInRange[index++] = recent.key;
                }
                recent = recent.next;
            }
            return keysInRange;
        }
        else {
            int count = 0;
            // Count keys within the range
            for (int i = 0; i < ThresholdSize; i++) {
                if (hashTable[i] != null && hashTable[i].key >= key1 && hashTable[i].key <= key2) {
                    count++;
                }
            }
            long[] keysInRange = new long[count];
            int index = 0;
            // Store keys within the range in the array
            for (int i = 0; i < ThresholdSize; i++) {
                if (hashTable[i] != null && hashTable[i].key >= key1 && hashTable[i].key <= key2) {
                    keysInRange[index++] = hashTable[i].key;
                }
            }
            return keysInRange;
        }
    }
    /**
     * Performs a binary search on a sorted array to find the index of a specified key.
     *
     * @param arr The sorted array to be searched.
     * @param key The key to be searched for in the array.
     * @return The index of the key in the array if found; otherwise, returns -1.
     */
    public static int binarySearch(long[] arr, long key) {   // Space complexity of the binarySearch method is O(1),
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] == key) {
                return mid;
            } else if (arr[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1; // Key not found
    }
    /**
     * @return A string containing details about whether the CleverSIDC is using a linked list or an array-based structure,
     *         the size threshold, and the maximum capacity. Additionally, it includes the contents of the data structure,
     *         displaying keys and values in a readable format.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("CleverSIDC Information:\n");
        str.append("Is using LinkedList: ").append(isLinkedList).append("\n");
        str.append("Threshold Size: ").append(ThresholdSize).append("\n");
        str.append("Maximum Capacity: ").append(max_capacity).append("\n");
        if (isLinkedList) {
            str.append("LinkedList Contents:\n");
            Node recentNode = Head;
            while (recentNode != null) {
                str.append("Key: ").append(recentNode.key).append(", Value: ").append(recentNode.value).append("\n");
                recentNode = recentNode.next;
            }
        }
        else {
            str.append("\nArray Contents:\n");
            for (int i = 0; i < ThresholdSize; i++) {
                if (hashTable[i] != null) {
                    str.append("Key: ").append(hashTable[i].key).append(", Value: ").append(hashTable[i].value).append("\n");
                }
            }
        }
        return str.toString();
    }
}
