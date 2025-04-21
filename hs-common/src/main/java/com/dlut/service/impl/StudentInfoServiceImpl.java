package com.dlut.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.PageResult;
import com.dlut.ResponseResult;
import com.dlut.constants.SystemConstants;
import com.dlut.dto.StudentInfoExcelDto;
import com.dlut.entity.MajorInfo;
import com.dlut.entity.ParentInfo;
import com.dlut.entity.StudentInfo;
import com.dlut.entity.StudentRank;
import com.dlut.enums.AppHttpCodeEnum;
import com.dlut.enums.SuccessHttpMessageEnum;
import com.dlut.mapper.MajorInfoMapper;
import com.dlut.mapper.ParentInfoMapper;
import com.dlut.mapper.StudentInfoMapper;
import com.dlut.mapper.StudentRankMapper;
import com.dlut.service.StudentInfoService;
import com.dlut.utils.PasswordEncryptor;
import com.dlut.utils.UserThreadLocalUtil;
import com.dlut.vo.StudentInfoListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements StudentInfoService {

    private final StudentInfoMapper studentInfoMapper;
    private final ParentInfoMapper parentInfoMapper;
    private final MajorInfoMapper majorInfoMapper;
    private final StudentRankMapper studentRankMapper;

    public StudentInfoServiceImpl(
            StudentInfoMapper studentInfoMapper,
            ParentInfoMapper parentInfoMapper,
            MajorInfoMapper majorInfoMapper,
            StudentRankMapper studentRankMapper
    ) {
        this.studentInfoMapper = studentInfoMapper;
        this.parentInfoMapper = parentInfoMapper;
        this.majorInfoMapper = majorInfoMapper;
        this.studentRankMapper = studentRankMapper;
    }

    @Override
    public ResponseResult<?> batchUpload(MultipartFile file) {
        try {
            // 1.读取Excel内容
            List<StudentInfoExcelDto> dataList = EasyExcel.read(file.getInputStream())
                    .head(StudentInfoExcelDto.class)
                    .sheet()
                    .doReadSync();

            if (CollectionUtils.isEmpty(dataList)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.EXCEL_EMPTY);
            }

            // 2.转换为StudentInfo 实体
            List<StudentInfo> studentList = dataList.stream().map(model -> {
                StudentInfo info = new StudentInfo();
                BeanUtils.copyProperties(model, info);
                return info;
            }).toList();

            // 3.获取所有导入学号
            List<String> importStudentIds = studentList.stream()
                    .map(StudentInfo::getStudentNumber)
                    .map(String::valueOf)
                    .collect(Collectors.toList());

            // 4.查询已存在的学号
            List<StudentInfo> existingStudents = studentInfoMapper.selectBatchIds(importStudentIds);
            Set<String> existingIds = existingStudents.stream()
                    .map(s -> String.valueOf(s.getStudentNumber()))
                    .collect(Collectors.toSet());
            // 改为List<String>并排序
            List<String> sortedFailedIds = existingIds.stream()
                    .sorted()
                    .collect(Collectors.toList());

            // 5.过滤出待插入的新学生
            List<StudentInfo> toInsertList = studentList.stream()
                    .filter(s -> !existingIds.contains(String.valueOf(s.getStudentNumber())))
                    .collect(Collectors.toList());

            // 6.插入学生数据
            if (!CollectionUtils.isEmpty(toInsertList)) {
                studentInfoMapper.batchInsert(toInsertList);

                // 7.初始化家长信息
                toInsertList.forEach(student -> {
                    ParentInfo parent = new ParentInfo();
                    parent.setStudentNumber(Long.valueOf(student.getStudentNumber()));
                    parent.setPassword(PasswordEncryptor.encrypt(SystemConstants.ORIGIN_PASSWORD));
                    parent.setPhoneNumber(null);
                    parentInfoMapper.insert(parent);
                });

                // 10.初始化学生排名信息
                toInsertList.forEach(studentInfo -> {
                    StudentRank rank = new StudentRank();
                    rank.setStudentNumber(Long.valueOf(studentInfo.getStudentNumber()));
                    studentRankMapper.insert(rank);
                });
            }

            // 8.返回导入结果
            if (existingIds.isEmpty()) {
                return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.BATCH.getMsg());
            } else {
                String failedMessage = "以下学号已存在未导入：\n" + String.join(", ", sortedFailedIds);
                return ResponseResult.okResult(SystemConstants.SUCCESS, failedMessage);
            }

        } catch (IOException e) {
            return ResponseResult.errorResult(AppHttpCodeEnum.FILE_FORMAT_INCORRECT);
        } catch (Exception e) {
            return ResponseResult.errorResult(AppHttpCodeEnum.BATCH_ERROR);
        }
    }

    @Override
    public ResponseResult<?> getStudentInfoList(Integer pageNum, Integer pageSize) {
        // 获取当前书院的学生列表
        String academyName = UserThreadLocalUtil.getUser().getAcademyName();
        LambdaQueryWrapper<StudentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentInfo::getAcademyName, academyName);

        Page<StudentInfo> page = new Page<>(pageNum, pageSize);
        Page<StudentInfo> studentInfoPage = studentInfoMapper.selectPage(page, queryWrapper);

        // 数据map
        List<MajorInfo> majorInfos = majorInfoMapper.selectList(null);
        Map<Long, MajorInfo> majorInfoMap = majorInfos.stream()
                .collect(Collectors.toMap(MajorInfo::getMajorId, Function.identity()));

        List<StudentInfoListVo> voList = studentInfoPage.getRecords().stream()
                .map(student -> {
                    StudentInfoListVo vo = new StudentInfoListVo();
                    BeanUtils.copyProperties(student, vo);

                    MajorInfo major = majorInfoMap.get(Long.parseLong(student.getMajorId()));
                    if (major != null) {
                        // 1.组装majorFullName
                        String fullName = major.getMajorCategory();
                        if (StringUtils.hasText(major.getMajorName())) {
                            fullName += "-" + major.getMajorName();
                        }
                        vo.setMajorFullName(fullName);
                        // 2.设置isDivert 状态
                        vo.setIsDivert(SystemConstants.DB_DIVERTED.equals(major.getIsDivert()) ? SystemConstants.DIVERTED : SystemConstants.NOT_DIVERTED);
                    }
                    return vo;
                }).collect(Collectors.toList());
        return ResponseResult.okResult(new PageResult(voList, page.getTotal(), page.getSize(), page.getCurrent(), page.getPages()));
    }
}
