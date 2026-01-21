package Person;

public abstract class Person {

    public String name="", birthDate ="", password="";
    public boolean active=true;
    protected String id;


    public Person( String name, String password, String birthDate) {}

    abstract Person register();

    abstract boolean checkPassword(String password);

    abstract boolean checkDate(String date);

    public abstract String getId();

}
