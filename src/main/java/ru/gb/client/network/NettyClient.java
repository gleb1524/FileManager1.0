package ru.gb.client.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import ru.gb.client.ClientHandler;

public class NettyClient {
    private static Channel channel;
    private static EventLoopGroup eventLoopGroup;

    public static EventLoopGroup getEventLoopGroup() {
        return eventLoopGroup;
    }

    public static Channel getChannel() {
        return channel;
    }

    public NettyClient() throws InterruptedException {
        eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class);
        bootstrap.remoteAddress("localhost", 45001);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel)  {
                socketChannel.pipeline().addLast(
                        new ObjectDecoder(20 * 1_000_000, ClassResolvers.cacheDisabled(null)),
                        new ObjectEncoder(),
                        new ClientHandler()
                );
            }
        });
        ChannelFuture channelFuture = bootstrap.connect().sync();
        channel = channelFuture.channel();

        channelFuture.channel().closeFuture().sync();


    }
}





