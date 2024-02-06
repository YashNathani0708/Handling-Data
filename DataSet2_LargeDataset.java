public class DataSet2_LargeDataset {
    public static void main(String[] args) {
        CleverSIDC sidc = new CleverSIDC(8000);

        // Add values up to the capacity limit
        for (int i = 1000; i < 2000; i++) {
            sidc.add(i, new HelperStudent("First Name" + i+"," , "Last Name" + i,i));
        }
        System.out.println(sidc.toString());

        // Performing operation - generate
        long newKey = sidc.generate();
        System.out.println("Generated Key: " + sidc.generate());
        sidc.add(newKey , new HelperStudent( "lastAdd", "LastAdd",newKey));

        System.out.println(sidc.toString());
        System.out.println("Next key after generated one: " + sidc.nextKey(newKey));
        System.out.println("Previous key before generated one: " + sidc.prevKey(newKey));

    }
}
