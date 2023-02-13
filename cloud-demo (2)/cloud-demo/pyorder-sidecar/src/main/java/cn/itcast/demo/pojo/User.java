package cn.itcast.demo.pojo;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String address;
    private String Language;


    @Override
    public boolean equals(Object obj) {

        // 首先判断传进来的obj是否是调用equals方法对象的this本身，提高判断效率
        if (obj == this) {return true;}
        // 判断传进来的obj是否是null，提高判断效率
        if (obj == null) {return false;}
        // 判断传进来的obj是否是User对象，防止出现类型转换的异常
        if (obj instanceof User) {
            User user = (User) obj;
            boolean flag = this.username.equals(user.username) && this.id == user.id && this.address.equals(user.address);
            return flag;
        }
        // 如果没有走类型判断语句说明两个比较的对象它们的类型都不一样，结果就是false了
        return false;
    }

    @Override
    public int hashCode() {
        // 重写hashcode 方法
        int result = id.hashCode();
        result = 17 * result + username.hashCode();
        result = 17 * result + address.hashCode();

        return result;

    }
}