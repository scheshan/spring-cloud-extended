package com.heshan.cloud.sample.producer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleProducerController
 *
 * @author heshan
 * @date 2020/2/15
 */
@RestController
@RequestMapping("/")
public class SampleProducerController {

    @GetMapping("/")
    public String index() {
        return "Message from Sample-Producer";
    }
}
