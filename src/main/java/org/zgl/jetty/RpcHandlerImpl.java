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

            dos.writeInt(-777888);
            dos.writeShort(-200);
            if(o.getClass().isPrimitive() || check(o.getClass())){
                checkType(o.getClass(),o,dos);
            }else {
                byte[] buf1 = ProtostuffUtils.serializer(o);
                dos.write(buf1);
            }
            httpServletResponse.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RpcHandlerImpl f = new RpcHandlerImpl();
        System.out.println("xxx");
    }
    private void checkType(Class<?> clazz,Object o,DataOutputStream dos) throws IOException {
        if(clazz.equals(short.class))
            dos.writeShort((short) o);
        else if(clazz.equals(byte.class))
            dos.writeByte((int) o);
        else if(clazz.equals(char.class))
            dos.writeChar((int) o);
        else if(clazz.equals(boolean.class))
            dos.writeBoolean((boolean) o);
        else if(clazz.equals(int.class))
            dos.writeInt((int) o);
        else if(clazz.equals(long.class))
            dos.writeLong((long) o);
        else if(clazz.equals(float.class))
            dos.writeFloat((float) o);
        else if(clazz.equals(double.class))
            dos.writeDouble((double) o);
        else if(clazz.equals(Short.class))
            dos.writeShort((short) o);
        else if(clazz.equals(Byte.class))
            dos.writeByte((int) o);
        else if(clazz.equals(Character.class))
            dos.writeChar((int) o);
        else if(clazz.equals(Boolean.class))
            dos.writeBoolean((boolean) o);
        else if(clazz.equals(Integer.class))
            dos.writeInt((int) o);
        else if(clazz.equals(Long.class))
            dos.writeLong((long) o);
        else if(clazz.equals(Float.class))
            dos.writeFloat((float) o);
        else if(clazz.equals(Double.class))
            dos.writeDouble((double) o);
    }
    private boolean check(Class<?> clazz){
        if(clazz.equals(Short.class))
            return true;
        else if(clazz.equals(Byte.class))
            return true;
        else if(clazz.equals(Character.class))
            return true;
        else if(clazz.equals(Boolean.class))
            return true;
        else if(clazz.equals(Integer.class))
            return true;
        else if(clazz.equals(Long.class))
            return true;
        else if(clazz.equals(Float.class))
            return true;
        else if(clazz.equals(Double.class))
            return true;
        return false;
    }
}
