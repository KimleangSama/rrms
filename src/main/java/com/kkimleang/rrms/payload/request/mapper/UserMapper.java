package com.kkimleang.rrms.payload.request.mapper;

import com.kkimleang.rrms.entity.*;
import com.kkimleang.rrms.payload.request.user.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.time.*;

@Slf4j
public class UserMapper {
    public static void updateUserFromEditContactRequest(User entity, EditContactRequest contactRequest) {
        try {
            if (contactRequest == null) {
                return;
            }
            ModelMapper modelMapper = new ModelMapper();
            Condition<?, ?> skipNulls =
                    context -> context.getSource() != null;
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT)
                    .setPropertyCondition(skipNulls);
            modelMapper.map(contactRequest, entity);
        } catch (Exception e) {
            log.error("Failed to update user contact from edit contact request", e);
        }
    }

    public static void updateUserFromEditBasicRequest(User targetUser, EditBasicRequest request) {
        try {
            if (request == null) {
                return;
            }
            ModelMapper modelMapper = new ModelMapper();
            Condition<?, ?> skipNulls =
                    context -> context.getSource() != null;
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT)
                    .setPropertyCondition(skipNulls);
            modelMapper.map(request, targetUser);
        } catch (Exception e) {
            log.error("Failed to update user from edit basic request", e);
        }
    }
}
