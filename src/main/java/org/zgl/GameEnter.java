package org.zgl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zgl.dao.entity.DBUser;
import org.zgl.dao.mapper.IUserDao;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.excel_init_data.ExcelUtils;
import org.zgl.utils.logger.LoggerUtils;

@SpringBootApplication
@MapperScan("org.zgl.dao.mapper")
public class GameEnter {

    public static void main(String[] args) {
        LoggerUtils.getPlatformLog().warn("静态数据加载...");
        ExcelUtils.init("excel");
        LoggerUtils.getPlatformLog().warn("------------------------------------静态数据加载成功---------------------------------");
        SpringApplication.run(GameEnter.class, args);
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("                   _ooOoo_\n");
        sb.append("                  o8888888o\n");
        sb.append("                  88\" . \"88\n");
        sb.append("                  (| -_- |)\n");
        sb.append("                  O\\  =  /O\n");
        sb.append("               ____/`---'\\____\n");
        sb.append("             .'  \\\\|     |//  `.\n");
        sb.append("            /  \\\\|||  :  |||//  \\ \n");
        sb.append("           /  _||||| -:- |||||-  \\ \n");
        sb.append("           |   | \\\\\\  -  /// |   |\n");
        sb.append("           | \\_|  ''\\---/''  |   |\n");
        sb.append("           \\  .-\\__  `-`  ___/-. /\n");
        sb.append("         ___`. .'  /--.--\\  `. . __\n");
        sb.append("      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".\n");
        sb.append("     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |\n");
        sb.append("     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /\n");
        sb.append("======`-.____`-.___\\_____/___.-`____.-'======\n");
        sb.append("                   `=---='\n");
        sb.append("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n");
        LoggerUtils.getLogicLog().info(sb.toString());
//        IUserDao dao = SpringUtils.getBean(IUserDao.class);
//        DBUser user = dao.queryDBUserByAccount("2");
//        System.out.println(user);
    }
}
