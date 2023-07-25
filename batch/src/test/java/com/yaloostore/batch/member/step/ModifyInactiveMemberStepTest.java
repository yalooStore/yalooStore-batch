package com.yaloostore.batch.member.step;

import com.yaloostore.batch.config.TestBatchConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBatchTest
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestBatchConfig.class})
class ModifyInactiveMemberStepTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void listItemReader() {
    }
}