package Models;

import java.util.ArrayList;
import java.util.List;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("userlist")
public class UserList {
    
    @XStreamAlias("users")
    private List<User> users = new ArrayList<>();

    public UserList() {}

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
