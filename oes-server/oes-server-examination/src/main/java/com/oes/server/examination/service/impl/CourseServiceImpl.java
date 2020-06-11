package com.oes.server.examination.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.exception.ApiException;
import com.oes.server.examination.entity.system.Course;
import com.oes.server.examination.entity.system.CourseTeacher;
import com.oes.server.examination.entity.system.Question;
import com.oes.server.examination.mapper.CourseMapper;
import com.oes.server.examination.service.ICourseService;
import com.oes.server.examination.service.ICourseTeacherService;
import com.oes.server.examination.service.IQuestionService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

  private final ICourseTeacherService courseTeacherService;
  private final IQuestionService questionService;

  @Override
  public IPage<Course> pageCourse(Course course, QueryParam param) {
    Page<Course> page = new Page<>(param.getPageNum(), param.getPageSize());
    return baseMapper.pageCourse(course, page);
  }

  @Override
  public List<Course> getList(Course course) {
    LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
    if (StrUtil.isNotBlank(course.getCourseName())) {
      wrapper.like(Course::getCourseName, course.getCourseName());
    }
    if (course.getDeptId() != null) {
      wrapper.eq(Course::getDeptId, course.getDeptId());
    }
    if (course.getCreatorId() != null) {
      wrapper.eq(Course::getCreatorId, course.getCreatorId());
    }
    return baseMapper.selectList(wrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteCourse(String[] courseIds) {
    if (hasCourse(courseIds)) {
      throw new ApiException("当前题库包含相关课程的题目，请移除相关题目后重试");
    }
    baseMapper.deleteBatchIds(Arrays.asList(courseIds));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateCourse(Course course) {
    course.setUpdateTime(new Date());
    baseMapper.updateById(course);
    // 维护课程-教师数据
    courseTeacherService.deleteByCourseId(course.getCourseId());
    setCourseTeacher(course.getCourseId(), StrUtil.split(course.getTeacherIds(), StrUtil.COMMA));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createCourse(Course course) {
    course.setCreateTime(new Date());
    baseMapper.insert(course);
    // 维护课程-教师数据
    setCourseTeacher(course.getCourseId(), StrUtil.split(course.getTeacherIds(), StrUtil.COMMA));
  }

  private void setCourseTeacher(Long courseId, String[] teacherIds) {
    List<CourseTeacher> batchObjs = new ArrayList<>(teacherIds.length);
    for (String teacherId : teacherIds) {
      batchObjs.add(new CourseTeacher(courseId, Long.parseLong(teacherId)));
    }
    courseTeacherService.saveBatch(batchObjs);
  }

  private boolean hasCourse(String[] courseIds) {
    return questionService.count(
        new LambdaQueryWrapper<Question>().in(Question::getCourseId, Arrays.asList(courseIds))) > 0;
  }
}