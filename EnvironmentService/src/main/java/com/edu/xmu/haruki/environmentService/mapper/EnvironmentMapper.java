package com.edu.xmu.haruki.environmentService.mapper;

import com.edu.xmu.haruki.environmentService.model.environment.BasicEnvironment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author haruki_9
 * @date 2022/7/14
 */
@Mapper
public interface EnvironmentMapper {
    int totalNum();

    List<BasicEnvironment> getAllEnvironment();

    List<BasicEnvironment> getEnvironmentByName(String name);

    List<BasicEnvironment> getAllAvailableEnvs();

    BasicEnvironment getEnvironmentById(int envId);

    int insertEnvironment(BasicEnvironment environment);

    int updateEnvironmentStatus(int envId, int status);
}
