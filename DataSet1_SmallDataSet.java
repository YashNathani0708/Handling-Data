public class DataSet1_SmallDataSet {
    public static void main(String[] args) {
        CleverSIDC sidc = new CleverSIDC(10);

        sidc.add(101, new HelperStudent("Shah" , "Rahul" ,100 ));
        sidc.add(102, new HelperStudent("Kumar" , "Sachin" , 101));
        sidc.add(103, new HelperStudent("Fernandez" , "Anthony" , 102));
        sidc.add(104, new HelperStudent("Stark" , "Tony" , 103));
        sidc.add(105, new HelperStudent("Iyer" , "Mark" , 104));
        sidc.add(106, new HelperStudent("Patel", "Rita" , 105));


        System.out.println(sidc.toString());

        // Performing operations - getValue, nextKey, prevKey, rangeKey, remove
        System.out.println("Value for key 103: " + sidc.getValue(103));
        sidc.remove(102);
        System.out.println("After removing key 102:\n");
        System.out.println(sidc.toString());
        System.out.println("After the key 103: " + sidc.nextKey(103) );
        System.out.println("Before the key 106: " + sidc.prevKey(106));
        System.out.println("Keys in range 102 to 106:");
        long[] keysInRange = sidc.rangeKey(102, 106);

        for (long key : keysInRange) {
            System.out.print(key+" ");
        }

    }
}
