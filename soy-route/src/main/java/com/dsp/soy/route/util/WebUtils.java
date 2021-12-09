package com.dsp.soy.route.util;

import cn.hutool.json.JSONUtil;
import com.dsp.soy.common.result.Result;
import com.dsp.soy.common.result.ResultCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class WebUtils {

    public static Mono<Void> writeFailedToResponse(ServerHttpResponse response, ResultCode resultCode){
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.getHeaders().set(HttpHeaders.CACHE_CONTROL, "no-cache");
        String body = JSONUtil.toJsonStr(Result.failed(resultCode));
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }
}
