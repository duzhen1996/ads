package Service;

import DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by DZ on 2016/4/30.
 */
@Service
public class LoginService {
    @Autowired
    private UserDAO userDAO;

    public boolean loginjudge(String username, String password) {

        if (userDAO.getCountByusernameAndpassword(username , password).equals(0))
        {
            return false;
        }

        return true;
    }
}
