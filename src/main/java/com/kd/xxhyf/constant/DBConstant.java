package com.kd.xxhyf.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
public class DBConstant {
    @Value("${config.fkTableName}")
    private  String FK_TABLE_NAME;
}
