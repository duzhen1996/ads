package DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by DZ on 2016/4/30.
 */
@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer getCountByusernameAndpassword( String username , String password )
    {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql , new Object[]{username , password} , Integer.class);
    }
}
