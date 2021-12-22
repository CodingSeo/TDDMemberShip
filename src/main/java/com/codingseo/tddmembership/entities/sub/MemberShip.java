package com.codingseo.tddmembership.entities.sub;

import com.codingseo.tddmembership.entities.BaseEntity;
import com.codingseo.tddmembership.enums.MemberShipType;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberShip extends BaseEntity {
    @Column(unique = true, nullable = false)
    private Integer userNo;

    @Column(nullable = false, length = 25)
    @Enumerated(EnumType.STRING)
    private MemberShipType membershipName;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer point;

    @Builder
    public MemberShip(@NonNull Integer userNo,@NonNull MemberShipType membershipName,@NonNull Integer point) {
        this.userNo = userNo;
        this.membershipName = membershipName;
        this.point = point;
    }
}
