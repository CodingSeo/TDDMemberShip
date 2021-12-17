package com.codingseo.tddmembership.entities.master;

import com.codingseo.tddmembership.entities.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    private String name;
    private Integer pass;
}
