package com.gjq.dto.fire;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "火灾事件更新DTO")
public class FireEventUpdateDTO {

    @NotNull(message = "事件ID不能为空")
    @Schema(description = "事件ID")
    private Long id;

    @Schema(description = "摄像头ID")
    private Long cameraId;

    @Schema(description = "位置")
    private String location;

    @Schema(description = "火情等级：0-无火情，1-可疑火情，2-轻微火情，3-中等火情，4-严重火情，5-特别严重")
    private Integer level;

    @Schema(description = "图片URL")
    private String imageUrl;

    @Schema(description = "事件描述")
    private String description;
} 