package com.heshan.cloud.sample.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

/**
 * SampleConsumerController
 *
 * @author heshan
 * @date 2020/2/15
 */
@RequestMapping("")
@RestController
public class SampleConsumerController {

    @Autowired
    private RestOperations restOperations;

    @GetMapping("")
    public String index() {
        String message = "Message from consumer\r\n<br/>";

        String response = restOperations.getForObject("http://SAMPLE-PRODUCER", String.class);
        message += response;

        return message;
    }
}
