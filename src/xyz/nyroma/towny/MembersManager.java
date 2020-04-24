package xyz.nyroma.towny;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MembersManager implements Serializable {
    private List<String> members = new ArrayList<>();
    private City city;

    public MembersManager(City city){
        this.city = city;
    }

    public List<String> getMembers() {
        return this.members;
    }

    public City getCity() {
        return city;
    }

    public boolean addMember(String name){
        return this.members.add(name);
    }

    public boolean removeMember(String name) throws NotExistException {
        if(this.members.contains(name)){
            return this.members.remove(name);
        } else {
           throw new NotExistException();
        }
    }

    public boolean isMember(String name){
        return this.members.contains(name);
    }
}
