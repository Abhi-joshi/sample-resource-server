package com.abhishek.sampleresourceserver.modal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("enduser_role")
public class EndUserRole {

    @Id
    @Column("enduser_role_id")
    private long endUserRoleId;

    @Column("username")
    private String username;

    @Column("role")
    private String role;

    @Column("enduser_id")
    private long endUserId;

    public long getEndUserRoleId() {
        return endUserRoleId;
    }

    public void setEndUserRoleId(long endUserRoleId) {
        this.endUserRoleId = endUserRoleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(long endUserId) {
        this.endUserId = endUserId;
    }
}
