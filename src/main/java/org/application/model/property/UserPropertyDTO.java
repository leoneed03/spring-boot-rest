package org.application.model.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UserPropertyDTO {
    private Long PROP_ID;
    private Long OWNER_USER_ID;
    private String PROP_DESCRIPTION;
}
