package com.dlut.mapper;

import com.dlut.entity.StudentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {

    void batchInsert(List<StudentInfo> studentList);

}
