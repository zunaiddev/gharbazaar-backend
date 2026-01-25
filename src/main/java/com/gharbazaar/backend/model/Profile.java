package com.gharbazaar.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "profiles")
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String url;

    @NotNull
    private String publicId;

    @OneToOne(mappedBy = "profile")
    @JoinColumn(nullable = false)
    private User user;

    public Profile(String url, String publicId, User user) {
        this.url = url;
        this.publicId = publicId;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", publicId='" + publicId + '\'' +
                ", user=" + user.getId() +
                '}';
    }
}