package com.like.firstboot.core.netty;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.like.firstboot.core.netty.config.NettyConfig;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Netty 服务启动监听器
 * @author 12678
 */
@Component
public class NettyServerListener {
	/**
	 * NettyServerListener 日志输出器
	 */
	private static final Logger _log = LoggerFactory.getLogger(NettyServerListener.class);
	
	/**
	 * 创建 bootstrap
	 */
	ServerBootstrap serverBootstrap = new ServerBootstrap();
	/**
	 * Worker
	 */
	EventLoopGroup  worker = new NioEventLoopGroup();
	/**
	 * BOSS
	 */
	EventLoopGroup  boss = new NioEventLoopGroup();
	 /**
     * 通道适配器
     */
    @Resource
    private ServerChannelHandlerAdapter channelHandlerAdapter;
	/**
	 * Netty 服务器配置类
	 */
	@Resource
	private NettyConfig nettyConfig;
	/**
	 * 关闭服务器方法
	 */
	@PreDestroy
	public void close() {
		_log.info("关闭服务器......");
		boss.shutdownGracefully();
		worker.shutdownGracefully();
	}
	/**
	 * 开启服务器
	 */
	@PostConstruct
	public void start() {
		int  port = nettyConfig.getPort();
		//
		serverBootstrap.group(boss, worker)
						.channel(NioServerSocketChannel.class)
						.option(ChannelOption.SO_BACKLOG, 100)
						.handler(new LoggingHandler(LogLevel.INFO));
		try {
			serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast(new LengthFieldBasedFrameDecoder(NettyConstant.getMaxFrameLength(), 
							0, 2,0,2));
					pipeline.addLast(new LengthFieldPrepender(2));
					//pipeline.addLast(new ObjectCodec());

	                pipeline.addLast(channelHandlerAdapter);
				}
			});
			_log.info("Netty服务器在[{}]端口启动监听",port);
			ChannelFuture future = serverBootstrap.bind(port).sync();
			future.channel().closeFuture().sync();
		}catch (Exception e) {
			_log.info("[出现异常] 释放资源");
	         boss.shutdownGracefully();
	         worker.shutdownGracefully();
		}
	}
}
