package com.codingseo.tddmembership.entities.sub;

import com.codingseo.tddmembership.entities.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberShip extends BaseEntity {
    private String membershipName;
    private Integer point;
}
