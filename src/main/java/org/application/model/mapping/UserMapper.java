package org.application.model.mapping;

import org.application.model.UserData;
import org.application.model.UserDataDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserData toUserData(UserDataDTO userDataDTO) {

        return modelMapper.map(userDataDTO, UserData.class);
    }

    public UserDataDTO toUserDataDTO(UserData userData) {

        return modelMapper.map(userData, UserDataDTO.class);
    }
}
