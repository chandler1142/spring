package com.imooc.disruptor.chapter6;

import com.imooc.disruptor.chapter6.disruptor.MessageConsumer;
import com.imooc.disruptor.chapter6.disruptor.MessageConsumerImpl4Server;
import com.imooc.disruptor.chapter6.disruptor.RingbufferPoolFactory;
import com.imooc.disruptor.chapter6.server.NettyServer;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);

        MessageConsumer[] consumers = new MessageConsumer[4];
        for (int i = 0; i < consumers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Server("code:sessionId:00" + i);
            consumers[i] = messageConsumer;
        }
        RingbufferPoolFactory.getInstance().intiAndStart(
                ProducerType.MULTI,
                1024 * 1024,
                new BlockingWaitStrategy(),
                consumers
        );

        new NettyServer();
    }

}
