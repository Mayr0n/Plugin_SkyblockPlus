package xyz.nyroma.towny;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MembersManager implements Serializable {
    private List<String> members = new ArrayList<>();

    public List<String> getMembers() {
        return this.members;
    }

    public boolean addMember(String name){
        return this.members.add(name);
    }

    public boolean removeMember(String name) {
        if(this.members.contains(name)){
            return this.members.remove(name);
        } else {
           return false;
        }
    }

    public boolean isMember(String name){
        return this.members.contains(name);
    }
}
