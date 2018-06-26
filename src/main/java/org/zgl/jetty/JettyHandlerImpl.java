package org.zgl.jetty;

import org.springframework.stereotype.Component;
import org.zgl.jetty.builder_clazz.OperateCommandRecive;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.player.UserMap;
import org.zgl.utils.DateUtils;
import org.zgl.utils.ProtostuffUtils;
import org.zgl.utils.StringUtils;
import org.zgl.utils.logger.LoggerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @作者： big
 * @创建时间： 2018/5/19
 * @文件描述：
 */
@Component
public class JettyHandlerImpl {

    public void handle(HttpServletRequest request, HttpServletResponse response) {
        try (DataInputStream dataInputStream = new DataInputStream(request.getInputStream())) {
            int head = dataInputStream.readInt();
            if (head != -777888)
                return;
            short id = dataInputStream.readShort();
            short leng = dataInputStream.readShort();
            byte[] buf = dataInputStream.readAllBytes();
            if (buf.length != leng)
                return;
            String[] params = null;
            if (buf.length > 0) {
                Msg msg = ProtostuffUtils.deserializer(buf, Msg.class);
                if (msg.getMsg() != null && !msg.getMsg().equals("")) {
                    if(id == 10001){
                        String account = StringUtils.substringAfterLast(msg.getMsg(),",");
                        String param = StringUtils.substringBeforeLast(msg.getMsg(),",");
                        params = new String[]{param,account};
                    }else {
                        params = StringUtils.split(msg.getMsg(), ",");
                    }
                }
            }
            OperateCommandAbstract op = OperateCommandRecive.getInstance().recieve(id, params);
            op.setCmdId(id);
            op.setHttpServletResponse(response);
            //加入线程中执行 由于httpServletResponse无法放到别的线程去用 所以不能开多线程
            UserMap userMap = SessionManager.getSession(op.getAccount());
            op.run();
            //超时处理s
            if (userMap != null)
                userMap.setLoginTime(DateUtils.currentTime());
        } catch (Exception e) {
            LoggerUtils.getLogicLog().info("数据读取异常", e);
        }
    }

    public void write(HttpServletResponse httpServletResponse, short cmdId, Object o) {
        try (DataOutputStream dos = new DataOutputStream(httpServletResponse.getOutputStream())) {
            byte[] buf1 = null;
            if (o != null)
                buf1 = ProtostuffUtils.serializer(o);
            dos.writeInt(-777888);
            dos.writeShort(cmdId);
            short leng = (short) (buf1 == null ? 0 : buf1.length);
            dos.writeShort(leng);
            if (buf1 != null)
                dos.write(buf1);
            httpServletResponse.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
