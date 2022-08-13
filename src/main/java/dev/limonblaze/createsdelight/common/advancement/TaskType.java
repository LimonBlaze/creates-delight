package dev.limonblaze.createsdelight.common.advancement;

import net.minecraft.advancements.FrameType;

public enum TaskType {
    SILENT(FrameType.TASK, false, false, false),
    NORMAL(FrameType.TASK, true, false, false),
    NOISY(FrameType.TASK, true, true, false),
    EXPERT(FrameType.GOAL, true, true, false),
    SECRET(FrameType.GOAL, true, true, true);
    
    public final FrameType frame;
    public final boolean toast;
    public final boolean announce;
    public final boolean hide;
    
    TaskType(FrameType frame, boolean toast, boolean announce, boolean hide) {
        this.frame = frame;
        this.toast = toast;
        this.announce = announce;
        this.hide = hide;
    }
    
}
