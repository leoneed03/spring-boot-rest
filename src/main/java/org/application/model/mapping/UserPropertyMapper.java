package org.application.model.mapping;

import org.application.model.property.UserProperty;
import org.application.model.property.UserPropertyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserPropertyMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public UserPropertyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserProperty toUserData(UserPropertyDTO UserPropertyDTO) {

        return modelMapper.map(UserPropertyDTO, UserProperty.class);
    }

    public UserPropertyDTO toUserDataDTO(UserProperty UserProperty) {

        return modelMapper.map(UserProperty, UserPropertyDTO.class);
    }

    public List<UserPropertyDTO> toUserDataList(List<UserProperty> userDataList) {
        return userDataList.stream()
                .map(this::toUserDataDTO)
                .collect(Collectors.toList());
    }
}
