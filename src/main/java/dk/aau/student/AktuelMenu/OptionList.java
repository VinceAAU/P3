package dk.aau.student.AktuelMenu;

import java.util.ArrayList;

public class OptionList{
    private ArrayList<Option> additions;
    private ArrayList<Option> options;

    private int minimum;
    private int maximum;

    public OptionList(){
        options = new ArrayList<>();
        additions = new ArrayList<>();
    }
    public void addOption(Option option){
        options.add(option);
    }

    public Option[] getOptions(){
        return options.toArray(new Option[0]);
    }
    public Option[] getAdditions(){
        return additions.toArray(new Option[0]);
    }
}