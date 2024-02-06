public class HelperStudent {
    private long ID;
    private String lastName;
    private String firstName;
    public HelperStudent(String firstName, String lastName,long ID) {
        this.setlastName(lastName);
        this.setFirstName(firstName);
        this.setID(ID);
    }
        /**
         * getter and setter for ID
         * @return (long) The method is returning a long value, specifically the value of the variable
         * ID.
         */
        public long getID() {
            return ID;
        }
        public void setID(long ID) {
            if (ID > 0) {
                this.ID = ID;
            }
            else {
                System.out.println("Error: ID must be a positive number; Unable to set new ID");
            }
        }
        /**
         * getter and setter for lastNme
         * @return (string) The family name.
         */
        public String getlastName() {
            return lastName;
        }
        public void setlastName(String lastName) {
            if (lastName != null && !lastName.isEmpty()) { // error checking.
                this.lastName = lastName;
            }
            else {
                System.out.println("Error: Last name cannot be empty.");
            }
        }
        /**
         * getter and setter for firstName
         * @return The method is returning the value of the variable "firstName".
         */
        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            if (firstName != null && !firstName.isEmpty()) {
                this.firstName = firstName;
            }
            else {
                System.out.println("Error: First Name cannot be empty.");
            }
        }
    /**
     * The toString() function returns a string representation of a student object, including their
     * unique ID and full name.
     * @return The toString() method is returning a string representation of a student's ID and name.
     */
    @Override
    public String toString() {
        return "\tName: " + firstName + " " + lastName + " & Student ID: " + ID;
    }
}