package workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * workqueue模型生产者
 *
 * @author Meiko
 * @version 1.0.0
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        // 1.获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 2.获取通道对象
        Channel channel = connection.createChannel();
        // 3.声明队列
        channel.queueDeclare("work", true, false, false,null);
        // 4.发布消息
        for (int i = 1;i <= 20; i++) {
            channel.basicPublish("", "work", null, ("hello work queue:" + i).getBytes());
        }
        // 5.关闭资源
        RabbitMQUtils.closeConnectionAndChanel(channel,connection);
    }
}
