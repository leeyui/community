package com.community.community.service;

import com.community.community.domain.Question;
import com.community.community.domain.User;
import com.community.community.dto.QuestionDto;
import com.community.community.mapper.QuestionMapper;
import com.community.community.mapper.UserMapper;
import com.community.community.util.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public void create(String title, String info, String tag, Integer creator) {
        System.out.println(title + tag + info);
        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(info);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(creator);
        questionMapper.create(question);
    }

    public Page list(int currentPage, int size) {
        if (currentPage == 0) {
            currentPage = 1;
        }
        List<Question> questions = questionMapper.list((currentPage-1)*size,size);
        List<QuestionDto> questionDtoList = questions.stream().map((e) -> {
            User user = userMapper.findById(e.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(e, questionDto);
            questionDto.setUser(user);
            return questionDto;
        }).collect(Collectors.toList());

        Integer totalRow = questionMapper.count();
        Page<QuestionDto> page = new Page<>(currentPage,size,totalRow);
        page.setList(questionDtoList);
        return page;
    }

    public QuestionDto findById(Integer id) {
        Question question = questionMapper.findById(id);
        QuestionDto questionDto = new QuestionDto();
        User user = userMapper.findById(question.getCreator());
        BeanUtils.copyProperties(question,questionDto);
        questionDto.setUser(user);
        return questionDto;
    }

    public void modifyById(String title, String info, String tag, Integer id) {
        Long gmt = System.currentTimeMillis();
        questionMapper.modifyById(title,tag,info,gmt,id);
    }

    public Page listByCreator(Integer currentPage, int size, int id) {
        if (currentPage == 0) {
            currentPage = 1;
        }
        List<Question> questions = questionMapper.listByCreator((currentPage-1)*size,size,id);
        List<QuestionDto> questionDtoList = questions.stream().map((e) -> {
            User user = userMapper.findById(e.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(e, questionDto);
            questionDto.setUser(user);
            return questionDto;
        }).collect(Collectors.toList());

        Integer totalRow = questionMapper.countByCreator(id);
        Page<QuestionDto> page = new Page<>(currentPage,size,totalRow);
        page.setList(questionDtoList);
        return page;
    }

    public Page<QuestionDto> listByCondition(Integer currentPage, int size, String condition) {
        if (currentPage == 0) {
            currentPage = 1;
        }
        List<Question> questions = questionMapper.listByCondition((currentPage-1)*size,size,condition);
        List<QuestionDto> questionDtoList = questions.stream().map((e) -> {
            User user = userMapper.findById(e.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(e, questionDto);
            questionDto.setUser(user);
            return questionDto;
        }).collect(Collectors.toList());

        Integer totalRow = questionMapper.countByCondition(condition);
        Page<QuestionDto> page = new Page<>(currentPage,size,totalRow);
        page.setList(questionDtoList);
        return page;

    }

    public Page listByCreatorAndCondition(Integer currentPage, int size, Integer id, String condition) {
        if (currentPage == 0) {
            currentPage = 1;
        }
        List<Question> questions = questionMapper.listByCreatorAndCondition((currentPage-1)*size,size,id,condition);
        List<QuestionDto> questionDtoList = questions.stream().map((e) -> {
            User user = userMapper.findById(e.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(e, questionDto);
            questionDto.setUser(user);
            return questionDto;
        }).collect(Collectors.toList());

        Integer totalRow = questionMapper.countByCreator(id);
        Page<QuestionDto> page = new Page<>(currentPage,size,totalRow);
        page.setList(questionDtoList);
        return page;
    }
}
