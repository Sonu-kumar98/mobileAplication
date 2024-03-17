package com.impactqa.dto;

public class Spanish {
    private NextSteps nextSteps;
    private VaccinationInfo vaccinationInfo;
    private VerificationCodeInfo verificationCodeInfo;



    public NextSteps getNextSteps() {
        return nextSteps;
    }

    public void setNextSteps(NextSteps nextSteps) {
        this.nextSteps = nextSteps;
    }

    public VaccinationInfo getVaccinationInfo() {
        return vaccinationInfo;
    }

    public void setVaccinationInfo(VaccinationInfo vaccinationInfo) {
        this.vaccinationInfo = vaccinationInfo;
    }

    public VerificationCodeInfo getVerificationCodeInfo() {
        return verificationCodeInfo;
    }

    public void setVerificationCodeInfo(VerificationCodeInfo verificationCodeInfo) {
        this.verificationCodeInfo = verificationCodeInfo;
    }
}
