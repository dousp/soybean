package com.dsp.soy.uaa.model;

import lombok.Data;

/**
 * @author Administrator
 * @version 1.0
 **/
@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}
