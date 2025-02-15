package gameplay;

import java.util.ArrayList;
import java.util.HashMap;

public class Command{
    Integer numArgs;
    String commandType;
    //Used when args are in -argname actual_arg format
    HashMap<String,String> argsLabled;
    ArrayList<String> argArr;

    public Command(String input){
        String[] parts = input.split(" ");
        commandType = parts[0];
        if (parts.length>1){
            argsLabled = new HashMap<>();
            argArr = new ArrayList<>();
        }
        for (int i = 1;i< parts.length;i+=1){
            if (parts[i].charAt(0)=='-'){
                argsLabled.put(parts[i],parts[i+1]);
                i+=1;
            } else {
                argArr.add(parts[i]);
            }
        }
    }
}
