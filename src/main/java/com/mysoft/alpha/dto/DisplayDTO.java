package com.mysoft.alpha.dto;

public class DisplayDTO {

    private Integer userId;

    private String name;//人员

    private Integer level;//层级


    private Long achievementAmount;//完成

    private Long taskAmount;//总量


    private Long dValue;//差额

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getAchievementAmount() {
        return achievementAmount;
    }

    public void setAchievementAmount(Long achievementAmount) {
        this.achievementAmount = achievementAmount;
    }

    public Long getTaskAmount() {
        return taskAmount;
    }

    public void setTaskAmount(Long taskAmount) {
        this.taskAmount = taskAmount;
    }

    public Long getdValue() {
        return dValue;
    }

    public void setdValue(Long dValue) {
        this.dValue = dValue;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DisplayDTO{");
        sb.append("userId=").append(userId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", level=").append(level);
        sb.append(", achievementAmount=").append(achievementAmount);
        sb.append(", taskAmount=").append(taskAmount);
        sb.append(", dValue=").append(dValue);
        sb.append('}');
        return sb.toString();
    }
}
