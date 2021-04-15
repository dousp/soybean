package com.dsp.soy.auth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class UaaOauth2ExceptionSerializer extends StdSerializer<UaaOAuth2Exception> {

    public UaaOauth2ExceptionSerializer() {
        super(UaaOAuth2Exception.class);
    }

    @Override
    public void serialize(UaaOAuth2Exception value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        log.info("UaaOauth2ExceptionSerializer......");
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        gen.writeStartObject();
        gen.writeStringField("error", String.valueOf(value.getOAuth2ErrorCode()));
        gen.writeStringField("code", String.valueOf(value.getHttpErrorCode()));
        gen.writeStringField("msg", value.getMessage());
        gen.writeStringField("path", request.getServletPath());
        if (value.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}
