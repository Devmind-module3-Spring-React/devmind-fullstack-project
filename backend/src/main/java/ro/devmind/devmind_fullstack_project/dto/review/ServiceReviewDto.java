package ro.devmind.devmind_fullstack_project.dto.review;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceReviewDto {
    private Integer id;
    private String title;
    private String body;
    private String username;
    private int rating;
    private LocalDateTime createdAt;

    private Integer serviceId;
    private Integer userId;
}
