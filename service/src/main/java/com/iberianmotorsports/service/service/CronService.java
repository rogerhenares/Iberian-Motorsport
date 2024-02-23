package com.iberianmotorsports.service.service;

import java.io.IOException;

public interface CronService {

    public void exportAndCreateServerACC();

    public void stopServerACC() throws IOException;

    public void importServerResultsACC();
}
