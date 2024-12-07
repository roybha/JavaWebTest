package org.example;

import java.util.Objects;

public class User {
    private final int id;
    private String name;
    private int age;
    private String file;
    String password;
    String login;
    String gender;
    public User(Integer id,String name, int age, String photo, String password, String login, String gender) {
        this.name = name;
        this.age = age;
        this.file = photo;
        this.id = id;
        this.password = password;
        this.login = login;
        this.gender = gender;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getFile() {
        return file;
    }
    public void setFile(String file) {
        this.file = file;
    }
    public String getPassword(){
        return password;
    }
    public String getLogin(){
        return login;
    }
    public String getGender(){
        return gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, file,id);
    }

    @Override
    public boolean equals(Object obj) {
      if(this == obj)return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      User other = (User) obj;
      return Objects.equals(name, other.name) && age == other.age && Objects.equals(file, other.file) && id == other.id;
    }
}
