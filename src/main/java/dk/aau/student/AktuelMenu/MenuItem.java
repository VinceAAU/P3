package dk.aau.student.AktuelMenu;

public class MenuItem{
    private int price;
    private String internalName;
    private String displayName;
    private Discount discount = null;

    private OptionList options;

    public String getInternalName() {
        return internalName;
    }
}