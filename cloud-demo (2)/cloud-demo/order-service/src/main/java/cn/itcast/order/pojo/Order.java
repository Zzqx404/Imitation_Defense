package cn.itcast.order.pojo;

import lombok.Data;

@Data
public class Order {

    private Long id;
    private Long price;
    private String name;
    private Integer num;
    private Long userId;
    private User user;
    private String Language;

    @Override
    public boolean equals(Object obj) {

        // 首先判断传进来的obj是否是调用equals方法对象的this本身，提高判断效率
        if (obj == this) {return true;}
        // 判断传进来的obj是否是null，提高判断效率
        if (obj == null) {return false;}
        // 判断传进来的obj是否是User对象，防止出现类型转换的异常
        if (obj instanceof Order) {
            Order order = (Order) obj;
            boolean flag = this.id.equals(order.id) && this.price == order.price && this.name.equals(order.name)
                    && this.num == order.num && this.userId == order.userId && this.user.equals(order.user);
            return flag;
        }
        // 如果没有走类型判断语句说明两个比较的对象它们的类型都不一样，结果就是false了
        return false;
    }

    @Override
    public int hashCode() {
        // 重写hashcode 方法
        int result = id.hashCode();
        result = 17 * result + price.hashCode();
        result = 17 * result + name.hashCode();
        result = 17 * result + num.hashCode();
        result = 17 * result + userId.hashCode();
        result = 17 * result + user.hashCode();

        return result;

    }


}