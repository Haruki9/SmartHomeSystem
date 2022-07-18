package com.edu.xmu.haruki.gateway.filter;

import com.edu.xmu.haruki.gateway.model.ResultMsg;
import com.edu.xmu.haruki.gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author haruki_9
 * @date 2022/7/13
 */
@Component
public class AccessTokenFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    private static String TOKEN_HEADER="AccessToken";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request=exchange.getRequest();
        ServerHttpResponse response=exchange.getResponse();

        String urlPath=request.getURI().getPath();
        if (urlPath.startsWith("/login")){
            return chain.filter(exchange);
        }

        String token=request.getHeaders().getFirst(TOKEN_HEADER);
        if (token==null){
            token=request.getQueryParams().getFirst(TOKEN_HEADER);
        }

        if (token==null){
            ResultMsg msg=new ResultMsg(401,"未登录认证！",null);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            DataBuffer result=response.bufferFactory().wrap(msg.toString().getBytes(StandardCharsets.UTF_8));
            response.writeWith(Mono.just(result));
            return response.setComplete();
        }

        if (jwtUtil.judgeTokenLegality(token)){
            ResultMsg msg=new ResultMsg(403,"Token非法或失效！",null);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            DataBuffer result=response.bufferFactory().wrap(msg.toString().getBytes(StandardCharsets.UTF_8));
            response.writeWith(Mono.just(result));
            return response.setComplete();
        }


        // 管理员路径需要校验管理员权限
        if (urlPath.startsWith("/user")||urlPath.endsWith("delete")
                ||urlPath.endsWith("insert")||urlPath.endsWith("update")
                ||urlPath.endsWith("edit")){
            Boolean admin= (Boolean) jwtUtil.getTargetClaimsFromToken(token,"admin");
            if (!admin){
                ResultMsg msg=new ResultMsg(405,"权限不足！",null);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                DataBuffer result=response.bufferFactory().wrap(msg.toString().getBytes(StandardCharsets.UTF_8));
                response.writeWith(Mono.just(result));
                return response.setComplete();
            }
        }

        request.mutate().header(TOKEN_HEADER,token);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}