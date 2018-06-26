package org.zgl.jetty.session;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * @作者： big
 * @创建时间： 2018/6/14
 * @文件描述：
 */
@RequestMapping("xx")
@Controller
public class xxxx {
    @RequestMapping(value = "x",method = RequestMethod.GET)
    public void xx(){
        System.out.println("ss");
    }
}
