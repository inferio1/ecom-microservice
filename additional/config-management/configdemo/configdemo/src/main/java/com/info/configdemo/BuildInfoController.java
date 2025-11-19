package com.info.configdemo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
//@AllArgsConstructor
public class BuildInfoController {

//    private BuildInfo buildInfo;

    @Value("${build.id:default}")
    private String buildId;

    @Value("${build.version:default}")
    private String buildVersion;

    @Value("${build.name:default}")
    private String buildName;

    @Value("${build.type:default}")
    private String buildType;

    @GetMapping("/build-info")
    public String buildInfo()
    {
        return "Build id: "+buildId+", version: "+buildVersion+", name: "+buildName+", type:"+buildType;
    }

}
