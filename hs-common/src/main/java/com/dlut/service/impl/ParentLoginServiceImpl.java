package com.dlut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.ResponseResult;
import com.dlut.constants.RedisConstants;
import com.dlut.constants.SystemConstants;
import com.dlut.dto.ParentInfoDto;
import com.dlut.entity.MajorInfo;
import com.dlut.entity.ParentInfo;
import com.dlut.entity.StudentInfo;
import com.dlut.enums.AppHttpCodeEnum;
import com.dlut.enums.SuccessHttpMessageEnum;
import com.dlut.exception.SystemException;
import com.dlut.mapper.MajorInfoMapper;
import com.dlut.mapper.ParentInfoMapper;
import com.dlut.mapper.StudentInfoMapper;
import com.dlut.service.ParentLoginService;
import com.dlut.utils.JwtUtils;
import com.dlut.utils.PasswordEncryptor;
import com.dlut.vo.ParentLoginVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ParentLoginServiceImpl extends ServiceImpl<ParentInfoMapper, ParentInfo> implements ParentLoginService {

    private final ParentInfoMapper parentInfoMapper;
    private final StudentInfoMapper studentInfoMapper;
    private final MajorInfoMapper majorInfoMapper;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseResult<ParentLoginVo> login(ParentInfo parentInfo) {
        // 1.查询数据库中的用户信息
        LambdaQueryWrapper<ParentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ParentInfo::getStudentNumber, parentInfo.getStudentNumber());
        ParentInfo dbParent = parentInfoMapper.selectOne(queryWrapper);
        if (dbParent == null) {
            throw new SystemException(AppHttpCodeEnum.PARENT_NOT_EXISTS);
        }

        // 2.判断用户输入密码是否正确
        String inputPassword = parentInfo.getPassword();
        String dbPassword = dbParent.getPassword();
        if (!PasswordEncryptor.verify(inputPassword, dbPassword)) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_CORRECT);
        }

        // 3.生成JWT令牌（token）
        StudentInfo dbStudent = studentInfoMapper.selectById(dbParent.getStudentNumber());
        MajorInfo dbMajorInfo = majorInfoMapper.selectById(dbStudent.getMajorId());

        Long parentId = dbParent.getParentId();
        Long studentNumber = dbParent.getStudentNumber();
        String studentName = dbStudent.getStudentName();
        String academyName = dbStudent.getAcademyName();
        String majorCategory = dbMajorInfo.getMajorCategory();
        String majorName = dbMajorInfo.getMajorName();
        String studentClass = dbStudent.getStudentClass();
        String fullName = majorCategory + " - " + majorName;

        Map<String, Object> claims = new HashMap<>();
        claims.put(SystemConstants.PARENT_ID, parentId);
        claims.put(SystemConstants.STUDENT_NUMBER, studentNumber);
        claims.put(SystemConstants.STUDENT_NAME, studentName);
        claims.put(SystemConstants.ACADEMY_NAME, academyName);
        claims.put(SystemConstants.MAJOR_FULL_NAME, fullName);
        claims.put(SystemConstants.STUDENT_CLASS, studentClass);
        String token = JwtUtils.generateToken(claims);

        // 4.将token存入redis，并设置过期时间
        String parentTokenKey = RedisConstants.LOGIN_TOKEN_PARENT + parentId;
        stringRedisTemplate.opsForValue().set(parentTokenKey, token);
        stringRedisTemplate.expire(parentTokenKey, RedisConstants.LOGIN_TOKEN_TTL, TimeUnit.MINUTES);

        // 5.封装数据
        ParentInfoDto parentInfoDto = new ParentInfoDto(parentId, studentNumber, studentName, academyName, fullName, studentClass);
        ParentLoginVo parentLoginVo = new ParentLoginVo(token, parentInfoDto);

        return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.LOGIN.getMsg(), parentLoginVo);
    }
}
