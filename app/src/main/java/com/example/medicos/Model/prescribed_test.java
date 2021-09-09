package com.example.medicos.Model;

import java.io.Serializable;

public class prescribed_test implements Serializable {

    public String test_name;
    public String specification;

    public prescribed_test() {

    }

    public prescribed_test(String test_name, String specification) {
        this.test_name = test_name;
        this.specification = specification;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
