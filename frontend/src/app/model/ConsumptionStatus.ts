export class ConsumptionStatus {

    processMessage: string;
    processStatus: string;

    constructor() {
        this.processMessage = '';
        this.processStatus = '';
    }

    updateConsumptionStatus(consumptionStatus: ConsumptionStatus) {
        this.processMessage = consumptionStatus.processMessage;
        this.processStatus = consumptionStatus.processStatus;
        this.processStatus = consumptionStatus.processMessage + consumptionStatus.processStatus;
    }

}
