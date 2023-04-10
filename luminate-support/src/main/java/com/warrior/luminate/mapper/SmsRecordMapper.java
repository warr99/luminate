package com.warrior.luminate.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warrior.luminate.domain.SmsRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author WARRIOR
* @description 针对表【sms_record(短信记录信息)】的数据库操作Mapper
* @createDate 2023-04-03 14:14:00
* @Entity com.warrior.com.warrior.luminate.com.warrior.com.warrior.luminate.domain.SmsRecord
*/

@Mapper
@Repository
public interface SmsRecordMapper extends BaseMapper<SmsRecord> {

}




