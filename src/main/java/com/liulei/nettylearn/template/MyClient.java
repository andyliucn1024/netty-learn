package com.liulei.nettylearn.template;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyClientInitializer());
        try {
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8818).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {

            eventLoopGroup.shutdownGracefully();
        }


    }
}
