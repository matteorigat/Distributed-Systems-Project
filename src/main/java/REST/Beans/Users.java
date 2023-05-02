package REST.Beans;
/**
 * Created by civi on 26/04/16.
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Users {

    @XmlElement(name="my_users")
    private List<User> userslist;

    private static Users instance;

    private Users() {
        userslist = new ArrayList<User>();
    }

    //singleton
    public synchronized static Users getInstance(){
        if(instance==null)
            instance = new Users();
        return instance;
    }

    public synchronized List<User> getUserslist() {

        return new ArrayList<>(userslist);

    }

    public synchronized void setUserslist(List<User> userslist) {
        this.userslist = userslist;
    }

    public synchronized void add(User u){
        userslist.add(u);
    }


    public User getByName(String name){
        List<User> usersCopy = getUserslist();
        for(User u: usersCopy)
            if(u.getName().toLowerCase().equals(name.toLowerCase()))
                return u;
        return null;
    }

}