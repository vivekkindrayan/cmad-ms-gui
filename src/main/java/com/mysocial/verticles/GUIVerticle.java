package com.mysocial.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class GUIVerticle extends AbstractVerticle {

	public static final int DEFAULT_WORKER_POOL_SIZE = 10;
	public static final String VERTICLE_NAME = GUIVerticle.class.getName();
	public static final int HTTP_PORT = 7000;
	
	@Override
	public void start(Future<Void> startFuture)
	{
		System.out.println(VERTICLE_NAME + " started");
		startFuture.complete();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void stop(Future stopFuture) throws Exception
	{
		System.out.println(VERTICLE_NAME + " stopped");
		stopFuture.complete();
	}
	
	public void deploy(Vertx vertx, Router router, HttpServer server) throws Exception
	{
		vertx.deployVerticle(VERTICLE_NAME, new Handler<AsyncResult<String>>() {
			public void handle(AsyncResult<String> event) {
				
				router.route().handler(StaticHandler.create()::handle);				
				System.out.println(VERTICLE_NAME + " deployment complete");
			}
		});
	}
	
	public static void main(String[] args) throws Exception 
	{
		VertxOptions options = new VertxOptions().setWorkerPoolSize(DEFAULT_WORKER_POOL_SIZE);
		Vertx vertx = Vertx.vertx(options);
		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);
		
		GUIVerticle msv = new GUIVerticle();
		msv.deploy(vertx, router, server);
		server.requestHandler(router::accept).listen(HTTP_PORT);
	}
}
