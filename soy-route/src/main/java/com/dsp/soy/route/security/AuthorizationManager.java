package com.dsp.soy.route.security;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.dsp.soy.common.constant.AuthConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 权限验证
 */
@Component
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {

        ServerHttpRequest request = authorizationContext.getExchange().getRequest();

        // 对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        PathMatcher pathMatcher = new AntPathMatcher();
        String path = request.getURI().getPath();
        String method = request.getMethodValue();
        log.info("request：{} {}", method,path);

        // token为空拒绝访问
        String token = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION_KEY);
        if (StrUtil.isBlank(token)) {
            log.info("request：{} {}，未检测到token", method,path);
            return Mono.just(new AuthorizationDecision(false));
        }

        // 非管理端路径无需鉴权直接放行
        if (!pathMatcher.match(AuthConstants.ADMIN_URL_PATTERN, path)) {
            log.info("request：{} {}，请求无需鉴权", method,path);
            return Mono.just(new AuthorizationDecision(true));
        }

        // 缓存取资源权限角色关系列表
        Map<Object, Object> resourceRolesMap = stringRedisTemplate.opsForHash().entries(AuthConstants.PERMISSION_ROLES_KEY);
        Iterator<Object> iterator = resourceRolesMap.keySet().iterator();
        // 请求路径匹配到的资源需要的角色权限集合authorities
        List<String> authorities = new ArrayList<>();
        while (iterator.hasNext()) {
            String pattern = (String) iterator.next();
            if (pathMatcher.match(pattern, path)) {
                authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)));
            }
        }

        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(roleId -> {
                    // roleId是请求用户的角色，格式:ROLE_{roleId}
                    // authorities是请求资源所需要角色的集合
                    log.info("用户角色roleId：{}，访问路径：{}，资源需要权限authorities：{}", roleId,path,authorities);
                    return authorities.contains(roleId);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
