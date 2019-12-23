package test;

import java.io.Serializable;

/**
 * @Author jsonlog
 * @Date 2019-12-14
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1510634274152200118L;

    private int id;
    private String passWord;

    public User() {
        System.out.println("Create user... ");
    }

    public User(int id, String passWord) {
        this.id = id;
        this.passWord = passWord;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", passWord=" + passWord + "]";
    }

    public void printInfo(){
        System.out.println(toString());
    }
}