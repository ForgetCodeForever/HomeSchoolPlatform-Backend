package com.dlut.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dlut.PageResult;
import com.dlut.ResponseResult;
import com.dlut.constants.SystemConstants;
import com.dlut.entity.StudentInfo;
import com.dlut.entity.StudentRank;
import com.dlut.enums.AppHttpCodeEnum;
import com.dlut.enums.SuccessHttpMessageEnum;
import com.dlut.mapper.StudentInfoMapper;
import com.dlut.mapper.StudentRankMapper;
import com.dlut.service.StudentRankService;
import com.dlut.utils.UserThreadLocalUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentRankServiceImpl extends ServiceImpl<StudentRankMapper, StudentRank> implements StudentRankService {

    private final StudentRankMapper studentRankMapper;
    private final StudentInfoMapper studentInfoMapper;

    public ResponseResult<?> batchUpload(MultipartFile file) {
        try {
            // 1. 监听读取 Excel
            RankExcelListener listener = new RankExcelListener();
            EasyExcel.read(file.getInputStream(), listener)
                    .headRowNumber(0)
                    .sheet()
                    .doRead();

            // 2. 获取Map 学号 -> {rank列: 排名}
            Map<String, Map<String, Integer>> rankMap = listener.getStudentRankMap();

            // 3. 遍历更新数据库
            for (Map.Entry<String, Map<String, Integer>> entry : rankMap.entrySet()) {
                String studentNumber = entry.getKey();
                Map<String, Integer> rankValues = entry.getValue();

                updateStudentRank(studentNumber, rankValues);
            }
            return ResponseResult.okResult(SystemConstants.SUCCESS, SuccessHttpMessageEnum.BATCH.getMsg());
        } catch (Exception e) {
            return ResponseResult.errorResult(AppHttpCodeEnum.BATCH_ERROR);
        }
    }

    @Getter
    private static class RankExcelListener extends AnalysisEventListener<Map<Integer, String>> {

        private final Map<String, Map<String, Integer>> studentRankMap = new HashMap<>();
        private final String COLUMN_PREFIX = "rank";

        /**
         * EasyExcel 会在每读取一行 Excel 时调用这个方法，rowData 就是当前这行所有的单元格数据。
         */
        @Override
        public void invoke(Map<Integer, String> rowData, AnalysisContext context) {
            for (int col = 0; col < 6; col++) {
                String cell = rowData.get(col);
                if (cell == null || cell.trim().isEmpty()) {
                    continue;
                }
                int rankValue = context.readRowHolder().getRowIndex() + 1;
                String rankDBColumn = COLUMN_PREFIX + (col + 1);
                studentRankMap
                        // 如果这个学号还没出现在map中，就new一个空的子Map
                        .computeIfAbsent(cell.trim(), k -> new HashMap<>())
                        .put(rankDBColumn, rankValue);
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            // 可选清理
        }
    }

    private void updateStudentRank(String studentNumber, Map<String, Integer> rankMap) {
        if (rankMap.isEmpty()) {
            return;
        }
        UpdateWrapper<StudentRank> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(StudentRank::getStudentNumber, studentNumber);
        rankMap.forEach(wrapper::set); // wrapper.set()方法
        studentRankMapper.update(null, wrapper);
    }

    @Override
    public ResponseResult<?> getStudentRankList(Integer pageNum, Integer pageSize, Long majorId) {
        Page<StudentRank> page = new Page<>(pageNum, pageSize);
        String academyName = UserThreadLocalUtil.getUser().getAcademyName();
        LambdaQueryWrapper<StudentInfo> studentInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 选择了专业
        if (majorId != null) {
            studentInfoLambdaQueryWrapper.eq(StudentInfo::getMajorId, majorId);
        } else { // 未选择专业
            studentInfoLambdaQueryWrapper.eq(StudentInfo::getAcademyName, academyName);
        }
        studentInfoLambdaQueryWrapper.orderByAsc(StudentInfo::getStudentNumber);
        List<StudentInfo> studentInfos = studentInfoMapper.selectList(studentInfoLambdaQueryWrapper);
        List<String> studentNumbers = studentInfos.stream()
                .map(StudentInfo::getStudentNumber)
                .collect(Collectors.toList());

        LambdaQueryWrapper<StudentRank> rankLambdaQueryWrapper = new LambdaQueryWrapper<>();
        rankLambdaQueryWrapper.in(StudentRank::getStudentNumber, studentNumbers);
        Page<StudentRank> studentRankPage = studentRankMapper.selectPage(page, rankLambdaQueryWrapper);

        return ResponseResult.okResult(new PageResult(studentRankPage.getRecords(), page.getTotal(), page.getSize(), page.getCurrent(), page.getPages()));
    }
}
