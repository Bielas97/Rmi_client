package domain;

import java.io.Serializable;
import java.util.Objects;

public class Artist implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String bio;


    public Artist(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
