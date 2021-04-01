package com.fy.system.repository.redis;

import com.fy.system.config.TestRedisConfiguration;
import com.fy.system.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Slf4j
@DataRedisTest
@Import(TestRedisConfiguration.class)
public class SysUserRedisRepositoryTests {

    @Autowired
    private SysUserRedisRepository sysUserRedisRepository;

    @Test
    void insert() {

        SysUser sysUser = new SysUser();
        sysUser.setAccount("admin");

        sysUserRedisRepository.save(sysUser);

        long count = sysUserRedisRepository.count();
        Optional<SysUser> admin = sysUserRedisRepository.findById(sysUser.getId());
        List<SysUser> byAccountEquals = sysUserRedisRepository.findByAccountEquals(sysUser.getAccount());

        log.info("users : {} - {}", byAccountEquals, count);
    }
}
