package com.warrior.luminate.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warrior.luminate.domain.MessageTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author WARRIOR
* @description 针对表【message_template(消息模板信息)】的数据库操作Mapper
* @createDate 2023-04-03 14:14:00
* @Entity com.warrior.com.warrior.luminate.com.warrior.com.warrior.luminate.domain.MessageTemplate
*/
@Mapper
@Repository
public interface MessageTemplateMapper extends BaseMapper<MessageTemplate> {

}




