package org.zgl.jetty;

import org.springframework.stereotype.Controller;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.message.IoMessage;
import org.zgl.jetty.message.IoMessageJavaTpeImpl;
import org.zgl.remote.IProxy;
import org.zgl.utils.ProtostuffUtils;
import org.zgl.utils.builder_clazz.PublicPackage.CodeModel;
import org.zgl.utils.builder_clazz.PublicPackage.GetFileAllInit;
import org.zgl.utils.builder_clazz.ann.Protocol;
import org.zgl.utils.logger.LoggerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @作者： big
 * @创建时间： 2018/6/15
 * @文件描述：
 */
@Controller
public class RpcHandlerImpl {
    private final Map<String,Class<?>> registMap;
    private RpcHandlerImpl(){
        registMap = new HashMap<>();
        scan("org.zgl");
    }
    private void scan(String path){
        List<Class> clazzList = GetFileAllInit.getClasssFromPackage(path);
        if (clazzList.size()<=0)
            return;
        for (Class c:clazzList) {
            Annotation proxy = c.getAnnotation(IProxy.class);
            if(proxy instanceof IProxy){
                Class i = c.getInterfaces()[0];
                registMap.put(i.getName(),c);
            }
        }
    }
    public void handle(HttpServletRequest request, HttpServletResponse response){
        try (DataInputStream dataInputStream = new DataInputStream(request.getInputStream())) {
            int head = dataInputStream.readInt();
            if (head != -777888)
                return;
            //读取数据长度
            byte[] data = dataInputStream.readAllBytes();
            IoMessageJavaTpeImpl ioMessage = ProtostuffUtils.deserializer(data, IoMessageJavaTpeImpl.class);
            handler(ioMessage,response);
        } catch (Exception e) {
            Throwable ex = e.getCause();
            if(ex instanceof GenaryAppError){
                GenaryAppError g = (GenaryAppError) ex;
                try (DataOutputStream dos = new DataOutputStream(response.getOutputStream())) {
                    dos.writeInt(-777888);
                    dos.writeShort(404);
                    dos.writeShort(g.getErrorCode());
                    response.getOutputStream().flush();
                } catch (IOException ey) {
                    e.printStackTrace();
                }
            }else {
                LoggerUtils.getLogicLog().error(e);
            }
        }
    }
    private void handler(IoMessageJavaTpeImpl ioMessage, HttpServletResponse response) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = registMap.get(ioMessage.getInterfaceName());
        if(clazz == null)
            new GenaryAppError(AppErrorCode.DATA_ERR);
        Method method = clazz.getDeclaredMethod(ioMessage.getMethodName(),ioMessage.getParamTypes());
        Object o = null;
        if(method.getReturnType().equals(void.class)){
            //不返回
            method.invoke(clazz.getDeclaredConstructor().newInstance(), ioMessage.getArgs());
        }else {
            o = method.invoke(clazz.getDeclaredConstructor().newInstance(), ioMessage.getArgs());
            //返回
            write(response,o);
        }
    }
    private void write(HttpServletResponse httpServletResponse, Object o) {
        try (DataOutputStream dos = new DataOutputStream(httpServletResponse.getOutputStream())) {
            if (o == null){
                new GenaryAppError(AppErrorCode.DATA_ERR);
            }
            byte[] buf1 = ProtostuffUtils.serializer(o);
            dos.writeInt(-777888);
            dos.writeShort(-200);
            dos.write(buf1);
            httpServletResponse.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RpcHandlerImpl f = new RpcHandlerImpl();
        System.out.println("xxx");
    }
}
