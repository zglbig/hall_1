package org.zgl.jetty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @作者： big
 * @创建时间： 2018/6/12
 * @文件描述：消息中转站
 */
@Controller
@RequestMapping("/game")
public class MessageTransfer {
    @Autowired
    private JettyHandlerImpl handler;
    @Autowired
    RpcHandlerImpl rpcHandler;
    @RequestMapping(value = "/handle",method = RequestMethod.POST)
    private void handle(HttpServletRequest request, HttpServletResponse response){
        handler.handle(request,response);
    }
    @RequestMapping(value = "/rpc",method = RequestMethod.POST)
    private void rpcHandle(HttpServletRequest request,HttpServletResponse response){
        rpcHandler.handle(request,response);
    }
}