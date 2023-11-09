package dk.aau.student.AktuelMenu;

public class Option{
    private String internalName;
    private String displayName;
    private int price;

    String getInternalName(){
        return internalName;
    }

    public Option(String internalName, String displayName, int price){
        this.internalName = internalName;
        this.displayName = displayName;
        this.price = price;
    }
}