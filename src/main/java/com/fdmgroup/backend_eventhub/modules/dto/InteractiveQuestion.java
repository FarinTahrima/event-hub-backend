package com.fdmgroup.backend_eventhub.modules.dto;

import java.time.LocalDateTime;

public class InteractiveQuestion {
    private String id;
    private String text;
    private long votes;
    private boolean hasVoted;
    private boolean isSelected;
    private LocalDateTime timestamp;
    private ModerationStatus moderationStatus;
}
