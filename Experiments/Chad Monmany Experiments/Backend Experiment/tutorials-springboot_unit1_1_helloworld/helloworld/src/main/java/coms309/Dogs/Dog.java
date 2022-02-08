package coms309.Dogs;

public class Dog {
    private String name;
    private int age;
    private String owner;
    private String ownerContact;

    public Dog(String name,int age, String owner, String ownerContact)
    {
        this.name = name;
        this.age = age;
        this.owner = owner;

        this.ownerContact = ownerContact;
    }

    public String getName(){
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public String getOwner(){
        return owner;
    }

    public String getOwnerContact()
    {
        return ownerContact;
    }
}
