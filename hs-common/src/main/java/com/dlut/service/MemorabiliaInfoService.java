package com.dlut.service;

import com.dlut.ResponseResult;
import com.dlut.entity.MemorabiliaInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MemorabiliaInfoService extends IService<MemorabiliaInfo> {

    ResponseResult<?> addMemorabilia(MemorabiliaInfo memorabiliaInfo);

    ResponseResult<List<MemorabiliaInfo>> getAcademyMemorabiliaList(String academyName, Integer year, Integer month, String keyword);

    ResponseResult<?> editMemorabilia(MemorabiliaInfo memorabiliaInfo);

    ResponseResult<MemorabiliaInfo> getMemorabilia(Long memorabiliaId);

    ResponseResult<?> deleteMemorabilia(Long memorabiliaId);
}
