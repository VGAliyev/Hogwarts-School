package ru.hogwarts.school.dto;

import java.util.Objects;

public class AvatarDTO {
    private long id;
    private String mediaType;
    private long student_id;

    public AvatarDTO() {
    }

    public AvatarDTO(long id, String mediaType, long student_id) {
        this.id = id;
        this.mediaType = mediaType;
        this.student_id = student_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvatarDTO avatarDTO = (AvatarDTO) o;
        return id == avatarDTO.id && student_id == avatarDTO.student_id && Objects.equals(mediaType, avatarDTO.mediaType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mediaType, student_id);
    }

    @Override
    public String toString() {
        return "AvatarDTO{" +
                "id=" + id +
                ", mediaType='" + mediaType + '\'' +
                ", student_id=" + student_id +
                '}';
    }
}
