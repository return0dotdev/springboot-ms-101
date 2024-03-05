package com.return0.dev.backend.mapper;


import com.return0.dev.backend.entity.User;
import com.return0.dev.backend.model.MRegisterRequest;
import com.return0.dev.backend.model.MRegisterResponse;
import com.return0.dev.backend.model.MUserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    MRegisterResponse toRegisterRespone(User user);
    MUserProfile toUserProfile(User user);
}
