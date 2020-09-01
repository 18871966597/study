package demo.redis.enums;

/**
 * <p>
 * </p>
 *
 * @author ll Create on 20/7/10 9:03
 * @version 1.0
 */
public enum TopicEnum {
    // 规则维护通道
    FIRST_TOPIC("FIRDT-TOPIC", "第一个主题"),


    SECOND_TOPIC("SECOND-TOPIC", "第一个主题");


    private String name;

    private String describ;

    TopicEnum(String name, String describ) {
        this.name = name;
        this.describ = describ;
    }

    public String getName() {
        return name;
    }


    public String getDescrib() {
        return describ;
    }

}
