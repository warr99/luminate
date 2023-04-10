package com.warrior.luminate.utils;

import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.enums.ChannelTypeEnums;
import com.warrior.luminate.enums.MessageTypeEnums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class GroupIdUtils {

    /**
     * 将 "发送渠道.消息类型" 的所有组合作为一个 groupId
     *
     * @return groupIds
     */
    public static List<String> getAllGroupId() {
        ArrayList<String> groupIds = new ArrayList<>();
        for (ChannelTypeEnums channelType : ChannelTypeEnums.values()) {
            for (MessageTypeEnums messageType : MessageTypeEnums.values()) {
                groupIds.add(channelType.getEnCode() + "." + messageType.getEnCode());
            }
        }
        return groupIds;
    }

    public static String getGroupIdByTaskInfo(TaskInfo taskInfo) {
        ChannelTypeEnums channelType = ChannelTypeEnums.getChannelTypeByCode(taskInfo.getSendChannel());
        MessageTypeEnums messageType = MessageTypeEnums.getMessageTypeByCode(taskInfo.getMsgType());
        if (channelType != null && messageType != null) {
            return channelType.getEnCode() + "." + messageType.getEnCode();
        }
        return null;
    }

}
