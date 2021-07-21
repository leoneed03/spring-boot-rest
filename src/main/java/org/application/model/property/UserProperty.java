package org.application.model.property;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
//@SqlResultSetMapping(name = "propQuery",
//        entities = {
//                @EntityResult(entityClass = UserProperty.class, fields = {
//                        @FieldResult(name = "id", column = "PROP_ID"),
//                        @FieldResult(name = "user_id", column = "OWNER_USER_ID"),
//                        @FieldResult(name = "description", column = "PROP_DESCRIPTION")})}
//)
@Table(name = "TBL_PROP")
public class UserProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROP_ID")
    private Long id;

    @Column(name = "OWNER_USER_ID")
    private Long user_id;

    @Column(name = "PROP_DESCRIPTION")
    private String description;
}
