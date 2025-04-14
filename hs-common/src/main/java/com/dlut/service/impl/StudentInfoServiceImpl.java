package com.dlut.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.PageResult;
import com.dlut.ResponseResult;
import com.dlut.constants.SystemConstants;
import com.dlut.dto.StudentInfoExcelDto;
import com.dlut.entity.MajorInfo;
import com.dlut.entity.ParentInfo;
import com.dlut.entity.StudentInfo;
import com.dlut.enums.SuccessHttpMessageEnum;
import com.dlut.mapper.MajorInfoMapper;
import com.dlut.mapper.ParentInfoMapper;
import com.dlut.mapper.StudentInfoMapper;
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
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements StudentInfoService {

    private final StudentInfoMapper studentInfoMapper;
    private final ParentInfoMapper parentInfoMapper;
    private final MajorInfoMapper majorInfoMapper;

    public StudentInfoServiceImpl(
            StudentInfoMapper studentInfoMapper,
            ParentInfoMapper parentInfoMapper,
            MajorInfoMapper majorInfoMapper
    ) {
        this.studentInfoMapper = studentInfoMapper;
        this.parentInfoMapper = parentInfoMapper;
        this.majorInfoMapper = majorInfoMapper;
    }

   @Override
    public ResponseResult<?> batchUpload(MultipartFile file) {
        try {
            List<StudentInfoExcelDto> dataList = EasyExcel.read(file.getInputStream())
                    .head(StudentInfoExcelDto.class)
                    .sheet()
                    .doReadSync();

            List<StudentInfo> studentList = dataList.stream().map(model -> {
                StudentInfo info = new StudentInfo();
                BeanUtils.copyProperties(model, info);
                return info;
            }).collect(Collectors.toList());

            studentInfoMapper.batchInsert(studentList);

            // 初始化家长信息
            studentList.forEach(student -> {
                ParentInfo parent = new ParentInfo();
                parent.setStudentNumber(Long.valueOf(student.getStudentNumber()));
                parent.setPassword(PasswordEncryptor.encrypt(SystemConstants.ORIGIN_PASSWORD));
                parent.setPhoneNumber(null);
                parentInfoMapper.insert(parent);
            });
        } catch (IOException e) {
            throw new RuntimeException("上传解析失败", e);
        }
        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.BATCH.getMsg());
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
