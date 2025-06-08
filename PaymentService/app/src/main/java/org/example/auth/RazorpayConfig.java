package org.example.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@ConfigurationProperties(prefix = "payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RazorpayConfig {
    private String key;
    private String secret;
}
