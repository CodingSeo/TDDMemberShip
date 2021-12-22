package com.codingseo.tddmembership.entities.master;

import com.codingseo.tddmembership.entities.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = false)
    private Integer pass;

    @Builder
    public User(@NonNull String name,@NonNull Integer pass) {
        this.name = name;
        this.pass = pass;
    }
}
