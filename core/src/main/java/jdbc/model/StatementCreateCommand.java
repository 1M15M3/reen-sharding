package jdbc.model;

import com.sun.org.apache.regexp.internal.RE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 表示一个创建Statement的命令,可以在多个Connection上执行,创建多个真实的Statement
 *
 *一个Command即一个"指令",也就是执行一个方法,所以它的pojo类是方法签名+参数
 * Created by aude on 2016/12/21.
 */
public class StatementCreateCommand {

    private StatementCreateMethod method;
    private Object[] args;

    public PreparedStatement preparedStatement(Connection conn) throws SQLException {
        return this.method.preparedStatement(conn, this.args);
    }

    public StatementCreateCommand(StatementCreateMethod method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    public StatementCreateMethod getMethod() {
        return method;
    }
    public void setMethod(StatementCreateMethod method) {
        this.method = method;
    }
    public Object[] getArgs() {
        return args;
    }
    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(method).append("(");
        if(args != null && args.length > 0){
            for (int i = 0; i < args.length; ++i) {
                buffer.append(args[i]);
                if (i != args.length - 1) {
                    buffer.append(", ");
                }
            }
        }
        buffer.append(")");
        return buffer.toString();
    }
}
