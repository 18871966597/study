package demo.user.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class User implements Serializable {


    private static final long serialVersionUID = 8155006669034464654L;

    public String name;
    public String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
