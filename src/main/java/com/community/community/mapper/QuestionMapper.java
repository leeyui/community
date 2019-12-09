package com.community.community.mapper;

import com.community.community.domain.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,tag,creator) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{tag},#{creator})")
    void create(Question question);

    @Select("select * from question limit #{page},#{size}")
    List<Question> list(@Param("page") Integer page, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where id = #{id}")
    Question findById(@Param("id") Integer id);

    @Update("UPDATE question SET title=#{title} ,tag=#{tag}, description=#{info},gmt_modified=#{gmt} where id = #{id}")
    void modifyById(@Param("title") String title, @Param("tag") String tag, @Param("info") String info, @Param("gmt") Long gmt, @Param("id") Integer id);

    @Select("select * from question where creator = #{creator} limit #{page},#{size}")
    List<Question> listByCreator(@Param("page") int page, @Param("size") int size, @Param("creator") int creator);

    @Select("select * from question where title like '%${condition}%' limit #{page},#{size}")
    List<Question> listByCondition(@Param("page") int page, @Param("size") int size, @Param("condition") String condition);

    @Select("select count(1) from question where creator = #{creator}")
    Integer countByCreator(@Param("creator") int creator);

    @Select("select count(1) from question where title like '%${condition}%'")
    Integer countByCondition(@Param("condition") String condition);

    @Select("select * from question where creator = #{creator} and title like '%${condition}%' limit #{page},#{size}")
    List<Question> listByCreatorAndCondition(@Param("page") int page, @Param("size") int size, @Param("creator") int creator, @Param("condition") String condition);

}
