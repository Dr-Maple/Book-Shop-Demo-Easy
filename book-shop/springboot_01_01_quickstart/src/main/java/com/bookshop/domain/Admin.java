package com.bookshop.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

//lombok
@Data
public class Admin {
    private String id;
    private String username;
    private String password;
    private String profilePhoto;

}
