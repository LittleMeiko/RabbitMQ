package fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;

public class Provider {

    public static void main(String[] args) throws IOException {
        // 1.获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 2.获取通道对象
        Channel channel = connection.createChannel();
        // 3.将通道声明到指定的交换机 参数1：交换机名称 参数2：交换机类型
        channel.exchangeDeclare("logs", "fanout");
        // 4.发送消息到交换机 参数1：交换机名称 参数2：路由名称 参数3：消息持久化的额外参数 参数4：要发送的消息
        channel.basicPublish("logs", "", null, "fanout message type".getBytes());
        // 5.释放资源
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }
}
