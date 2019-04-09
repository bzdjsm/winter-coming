package com.winter.vo;

import lombok.Data;

@Data
public class ZombieVo {

    int xlabel;

    int ylabel;

    int num;

    String message;

    public ZombieVo() {}

    public ZombieVo(int x, int y, int n, String message) {
        this.xlabel = x;
        this.ylabel = y;
        this.num = n;
        this.message = message;
    }

}
