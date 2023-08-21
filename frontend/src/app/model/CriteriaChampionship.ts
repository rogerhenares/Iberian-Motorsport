export class CriteriaChampionship {
    disabled: boolean;
    finished: boolean;
    started: boolean;
    logged: boolean;

    constructor() {
        this.disabled = false;
        this.logged = false;
        this.finished = false;
        this.started = false;
    }

    criteriaStarted() {
        let criteria: CriteriaChampionship = new CriteriaChampionship()
        criteria.disabled = false;
        criteria.logged = false;
        criteria.finished = false;
        criteria.started = true;
        return criteria;
    }

    criteriaLogged() {
        let criteria: CriteriaChampionship = new CriteriaChampionship()
        criteria.disabled = false;
        criteria.logged = true;
        criteria.finished = false;
        criteria.started = false;
        return criteria;
    }

    criteriaDisabled() {
        let criteria: CriteriaChampionship = new CriteriaChampionship()
        criteria.disabled = true;
        criteria.logged = false;
        criteria.finished = false;
        criteria.started = false;
        return criteria;
    }

    criteriaFinished() {
        let criteria: CriteriaChampionship = new CriteriaChampionship()
        criteria.disabled = false;
        criteria.logged = false;
        criteria.finished = true;
        criteria.started = false;
        return criteria;
    }
}