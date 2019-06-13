package com.liulei.nettylearn.chapter2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    public static void main(String[] args) throws InterruptedException {
        //用来接收客户端传进来的连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        //用来处理已被接收的连接
        NioEventLoopGroup work = new NioEventLoopGroup();
        //辅助类 用于帮助我们创建netty服务
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)//设置NIO模式
                .option(ChannelOption.SO_BACKLOG, 1024)//设置tcp缓冲区
                .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)//设置发送缓冲区数据大小
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)//设置接收缓冲区数据大小
                .childOption(ChannelOption.SO_KEEPALIVE, true)//保持长连接
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //通道的初始化，数据传输过来进行拦截及执行
                        ch.pipeline().addLast(new ServerHandler());

                    }
                });
        ChannelFuture f = serverBootstrap.bind(8888).sync();//绑定端口启动服务
        f.channel().closeFuture().sync();
        work.shutdownGracefully();
        boss.shutdownGracefully();


    }


}
