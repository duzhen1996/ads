package DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DZ on 2016/5/1.
 */
@Repository
public class VideoDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertVideoInfo(String dirname, String name) {

        System.out.println("in VideoDAO insertVideoInfo");
        String sql = "INSERT INTO video( dirname , name ) VALUES( ? , ? )";
        System.out.println(sql);
        jdbcTemplate.update(sql, dirname, name);
    }

    public List getAllVideoInfo() {
        System.out.println("in VideoDAO getAllVideoInfo");
        String sql = "SELECT * FROM video";
        return jdbcTemplate.queryForList(sql);
    }

    //如果想找其它的一些东西，那就在param加上where字符串
    public Integer getCountofVideoInfo(String param) {
        System.out.println("in VideoDAO getCountofVideoInfo");
        String sql = "SELECT COUNT(*) FROM video " + param;
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    //在param中传where语句
    public void deleteVideoInfo(String param) {
        System.out.println("in VideoDAO deleteVideoInfo");
        String sql = "DELETE FROM video " + param;
        System.out.println(sql);
        jdbcTemplate.update(sql);
    }

    //通过名字找出对应的路径
    public String getDirByName(String name) {

        System.out.println("in VideoDAO getDirByName");
        String sql = "SELECT dirname FROM video WHERE name = ?";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql , String.class , name);
    }

    //通过对应id找出路径
    public String getDirById(Integer id)
    {
        System.out.println("in VideoDAO getDirById");
        String sql = "SELECT dirname FROM video WHERE id = ?";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql , String.class , id);
    }

    //通过id找到名字
    public String getNameById(Integer id)
    {
        System.out.println("in VideoDAO getNameById");
        String sql = "SELECT name FROM video WHERE id = ?";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql , String.class , id);
    }
}
