package com.codingseo.tddmembership.entities.sub;

import com.codingseo.tddmembership.entities.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberShip extends BaseEntity {
    @Column(nullable = false, length = 25)
    private String membershipName;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer point;
}
