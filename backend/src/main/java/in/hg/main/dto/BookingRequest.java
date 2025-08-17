package in.hg.main.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingRequest {
	 private Long carId;
	    private Long userId;
	    private LocalDateTime startTime;
	    private LocalDateTime endTime;
	    private BigDecimal totalCharge;
	    private String aadharCardPath;
	    private String drivingLicensePath;
	    
    // getters and setters
    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public BigDecimal getTotalCharge() { return totalCharge; }
    public void setTotalCharge(BigDecimal totalCharge) { this.totalCharge = totalCharge; }
    public String getAadharCardPath() { return aadharCardPath; }
    public void setAadharCardPath(String aadharCardPath) { this.aadharCardPath = aadharCardPath; }
    public String getDrivingLicensePath() { return drivingLicensePath; }
    public void setDrivingLicensePath(String drivingLicensePath) { this.drivingLicensePath = drivingLicensePath; }
}
