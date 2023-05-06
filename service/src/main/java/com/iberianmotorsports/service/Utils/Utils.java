package com.iberianmotorsports.service.Utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class Utils {

    public static final Pageable defaultPageable = PageRequest.of(0, 10);
}
