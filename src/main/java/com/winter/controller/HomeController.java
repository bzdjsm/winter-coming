package com.winter.controller;


import com.winter.vo.ZombieVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Controller
@RequestMapping(value = "/wintercoming")
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    int size = 0;
    Map<Integer, ZombieVo> zombieList = new ConcurrentHashMap<>();

    @RequestMapping(value = "/start")
    public String startGame(Map<String, Object> map) {
        int xlabel = (int) (Math.random() * 10);
        ZombieVo zombie = new ZombieVo(xlabel, 0, size, "zombie alive");
        zombieList.put(size, zombie);
        map.put("num", size);
        size++;
        System.out.println(zombie);
        return "startGame";
    }

    @RequestMapping(value = "/shoot", method = RequestMethod.POST)
    public String shoot(@RequestParam(value = "xlabel", required = true) int x,
                        @RequestParam(value = "ylabel", required = true) int y,
                        @RequestParam(value = "num", required = true) int num,
                        Map<String, Object> map) {
        if(num >= size) {
            return "startGame";
        }
        map.put("num", num);
        ZombieVo zombie = zombieList.get(num);
        if(x == zombie.getXlabel() && y == zombie.getYlabel()) {
            System.out.println("kill");
            return "ZombieKilled";
        } else {
            System.out.println("Missed");
            return "missed";
        }
    }

    public synchronized void move(int num) {

        if(num < size) {
            ZombieVo zombie = zombieList.get(num);
            if(zombie.getYlabel() >= 30) return;
            int xlabel = (int) (Math.random() * 10);
            zombie.setXlabel(xlabel);
            zombie.setYlabel(zombie.getYlabel() + 1);
            zombieList.put(num, zombie);
            //System.out.println(zombie);
        }
    }

//    @Scheduled(fixedRate = 2000)
    @RequestMapping(value = "/check")
    @ResponseBody
    public String check(@RequestParam(value = "num", required = true) int num) {
//
//        map.put("message1", "Hello, Spring Boot!");
//
//        map.put("message2", "Hello, Spring Boot!");
//        if(zombie == null) {
//            zombie = new ZombieVo(-1, -1, "not start");
//            //return zombie;
//        } else {
//            //return zombie;
//        }
//        map.put("zombie", zombie);
//        return "coordinates";
        if(num >= size) {
            return "not start";
        }
        ZombieVo zombie = zombieList.get(num);
        if(zombie.getYlabel() >= 30) {
            return "The zombie reached the wall, you loss!";
        }
        String res = "The zombie was at (" + zombie.getXlabel() + ", " + zombie.getYlabel() + ") two seconds ago. Hurry to shoot!";
        move(num);
        return res;
    }
}
