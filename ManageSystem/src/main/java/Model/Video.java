package Model;

/**
 * Created by DZ on 2016/5/1.
 */
public class Video {
    Integer id;
    private String dirname;
    private String name;

    public Video() {
    }

    public Video(Integer id, String dirname, String name) {
        this.id = id;
        this.dirname = dirname;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDirname() {
        return dirname;
    }

    public void setDirname(String dirname) {
        this.dirname = dirname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", dirname='" + dirname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
