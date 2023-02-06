package com.opensw.master.global.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "master-server")
@Getter @Setter
public class MasterProperties {

    @NotEmpty
    private String uri;
}
